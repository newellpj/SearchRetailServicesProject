package co.srsp.hibernate;

import java.util.List;

import co.srsp.hibernate.orm.Authorities;

public interface AuthoritiesBusinessObject {
	public void save(Authorities authorities);
	public void update(Authorities authorities);
	public void delete(Authorities authorities);
	public List<Authorities> findAuthoritiesByUserName(String username);
}
