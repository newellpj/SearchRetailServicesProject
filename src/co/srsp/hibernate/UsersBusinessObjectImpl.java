package co.srsp.hibernate;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import co.srsp.hibernate.orm.Authorities;
import co.srsp.hibernate.orm.Users;

public class UsersBusinessObjectImpl extends HibernateDaoSupport implements UsersBusinessObject{

	private final static Logger log = Logger.getLogger(UsersBusinessObjectImpl.class); 
	
	@Transactional
	public void save(Users users, Authorities authorities) {
		Session session = this.getSessionFactory().openSession();
		session.save(authorities);
		session.save(users);
		session.flush();
		session.close();
	}

	@Transactional
	@Override
	public void update(Users users) {
		
		Session session = this.getSessionFactory().openSession();
		session.update(users);
		session.flush();
		session.close();
	}

	@Transactional
	@Override
	public void delete(Users users, Authorities authorities) {
		Session session = this.getSessionFactory().openSession();
		session.delete(authorities);
		session.delete(users);
		session.flush();
		session.close();
	}

	@Override
	public Users findUsersByUsername(String username) {
		
		System.out.println("username : "+username);
		
		List list = getHibernateTemplate().find(" from "+Users.class.getName()+" where username = ? ", username);

		if(list != null && list.size() > 0){
			Object obj = list.get(0);
			
			Users user = (Users)obj;
			
			return user;
		}else{
			return null;
		}

	}

	@Override
	public String encryptPassword(String plainPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(plainPassword);
	
	}

	@Override
	public boolean checkPassword(String encryptedPassword, String plainPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(plainPassword, encryptedPassword);
	}
	

}
