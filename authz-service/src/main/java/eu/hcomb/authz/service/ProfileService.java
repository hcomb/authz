package eu.hcomb.authz.service;

import java.util.List;

public interface ProfileService {

	public abstract List<String> getRolesByUser(String username);


}
