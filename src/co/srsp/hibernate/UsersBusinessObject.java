package co.srsp.hibernate;

import co.srsp.hibernate.orm.Authorities;
import co.srsp.hibernate.orm.Users;

public interface UsersBusinessObject {
	public void save(Users users, Authorities authorities);
	public void update(Users users);
	public void delete(Users users, Authorities authorities);
	public Users findUsersByUsername(String username);
	public String encryptPassword(String plainPassword);
	public boolean checkPassword(String encryptedPassword, String plainPassword);
}
