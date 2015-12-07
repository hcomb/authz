package eu.hcomb.authz;

import eu.hcomb.common.dto.JdbcProperties;
import eu.hcomb.common.web.BaseConfig;

public class AuthorizationConfig extends BaseConfig {

	protected JdbcProperties database = new JdbcProperties();

	public JdbcProperties getDatabase() {
		return database;
	}
	
}