package co.srsp.webservices.documents;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import co.srsp.solr.SolrSearchData;

@WebService
@SOAPBinding(style = Style.RPC)
public interface QueryDocs {
	
	@WebMethod public SolrSearchData[] queryAuthor(String authorName);
	@WebMethod public SolrSearchData[] queryTitle(String title);

}
