package eu.hcomb.authz;

import eu.hcomb.common.cors.CorsConfig;
import eu.hcomb.common.cors.CorsConfigurable;
import eu.hcomb.common.jdbc.JdbcConfig;
import eu.hcomb.common.jdbc.JdbcConfigurable;
import eu.hcomb.common.redis.JedisConfig;
import eu.hcomb.common.redis.JedisConfigurable;
import eu.hcomb.common.swagger.SwaggerConfig;
import eu.hcomb.common.swagger.SwaggerConfigurable;
import eu.hcomb.common.web.BaseConfig;

public class AuthorizationConfig extends BaseConfig implements CorsConfigurable, JdbcConfigurable, SwaggerConfigurable, JedisConfigurable {

	protected JdbcConfig jdbcConfig = new JdbcConfig();
	
	protected CorsConfig corsConfig = new CorsConfig();

	protected SwaggerConfig swaggerConfig = new SwaggerConfig();

    protected JedisConfig redis = new JedisConfig();

	public JedisConfig getRedis() {
		return redis;
	}


	public SwaggerConfig getSwaggerConfig() {
		return swaggerConfig;
	}


	public JdbcConfig getJdbcConfig() {
		return jdbcConfig;
	}
	

	public CorsConfig getCorsConfig() {
		return corsConfig;
	}
	
}