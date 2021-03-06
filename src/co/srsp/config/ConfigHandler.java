package co.srsp.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfigHandler {
	
	//constants that define structure to return text files contents
	public final static String LIST_RETURN_TYPE = "list";
	public final static String ARRAY_RETURN_TYPE = "array";

	private final static Logger log = Logger.getLogger(ConfigHandler.class); 
	
	public static final String CONFIG_ROOT_DIR = "../webapps/SearchRetailServicesProject/presentationResources/";
	public static final String CONFIG_ROOT_DIR_UNIT_TEST = "./WebContent/presentationResources/";
	
	private static ConfigHandler instance = null;
	
	public static ConfigHandler getInstance(){
		if(instance == null){
			instance = new ConfigHandler();
		}
		return instance;
	}
	
	private ConfigHandler(){	
	}
	
	/**
	 * type returned will be decided by the returnType param value passed
	 * currently with string array or array list
	 * @param fileName
	 * @param returnType
	 * @return
	 */
	public Object readCSVFile(String fileName, String returnType){
	
			//read entire file
		StringBuffer sb = new StringBuffer();	
		
		String configDir = "";
		
		if(new File(CONFIG_ROOT_DIR+fileName).exists()){
			configDir = CONFIG_ROOT_DIR;
		}else{
			configDir = CONFIG_ROOT_DIR_UNIT_TEST;
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader(configDir+fileName))) {
			// load a properties file
			String line = "";
			
			while((line = br.readLine()) != null){
				sb.append(line);
			}
			
		} catch(IOException ioe){
			 ioe.printStackTrace();
			 log.error(ioe.getMessage());
		}
		
		switch(returnType){
			case LIST_RETURN_TYPE:
				
				List<String> itemsList = new ArrayList<String>();
				String[] items = sb.toString().split(",");
				
				for(int i = 0; i < items.length; i++ ){
					itemsList.add(items[i]);
				}
				
				return itemsList;
				
			case ARRAY_RETURN_TYPE:
				return sb.toString().split(",");
    	}
		
		log.info("string buffer : "+sb.toString());
		
		System.out.println("split(,) : "+sb.toString().split(",").length);
		
		return sb.toString().split(",");

	}
	
	public String readConfigProperty(String propLabel ) {
		Properties prop = new Properties();
		

		try(InputStream input = new FileInputStream("config.properties")) {
			// load a properties file
			prop.load(input);
			return prop.getProperty("propLabel");
			

		} catch(IOException ioe){
			 ioe.printStackTrace();
			 log.error(ioe.getMessage());
		}
		
		return null;

	}
	
	public void writeProperty(String propLabel, String propVal){
		
	}
	
	public static void main(String args[]){
		ConfigHandler.getInstance().readCSVFile("C:/Users/newelly/git/SearchRetailServicesProject/WebContent/presentationResources/stopwords.txt", ConfigHandler.LIST_RETURN_TYPE);
	}
}
