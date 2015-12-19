package eu.hcomb.authz;

import io.dropwizard.setup.Environment;

import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;

import eu.hcomb.authz.resources.LoginResource;
import eu.hcomb.authz.resources.UserResource;
import eu.hcomb.authz.service.UserService;
import eu.hcomb.authz.service.impl.UserServiceImpl;
import eu.hcomb.authz.service.mapper.RoleMapper;
import eu.hcomb.authz.service.mapper.UserMapper;
import eu.hcomb.common.jdbc.DatasourceHealthCheck;
import eu.hcomb.common.jdbc.PersistenceModule;
import eu.hcomb.common.redis.JedisModule;
import eu.hcomb.common.resources.WhoAmI;
import eu.hcomb.common.service.EventEmitter;
import eu.hcomb.common.service.impl.RedisEventEmitter;
import eu.hcomb.common.web.BaseApp;

public class AuthorizationApp extends BaseApp<AuthorizationConfig> {
	
	public static void main(String[] args) throws Exception {
		new AuthorizationApp().run(args);
	}
	
	@Override
	public String getName() {
        return "authz-service";
    }
	
	public void configure(Binder binder) {
		configureSecurity(binder);
		
		binder
			.bind(UserService.class)
			.to(UserServiceImpl.class);
		
		binder
			.bind(EventEmitter.class)
			.to(RedisEventEmitter.class);
	}	

	@Override
	public void run(AuthorizationConfig configuration, Environment environment) {
		
		init(environment, configuration);
		
		Module persistence = new PersistenceModule(configuration, environment) {
			@Override
			protected void initialize() {
				install(JdbcHelper.MySQL);
				setup();
		        addMapperClass(RoleMapper.class);
		        addMapperClass(UserMapper.class);
			}
		};
		
		
		
		Module jedis = new JedisModule(configuration, environment);
		
		injector = Guice.createInjector(this, persistence, jedis);
		
		defaultConfig(environment, configuration);
        
		environment.jersey().register(injector.getInstance(WhoAmI.class));
		environment.jersey().register(injector.getInstance(LoginResource.class));
		environment.jersey().register(injector.getInstance(UserResource.class));
				
		environment.healthChecks().register("mysql", injector.getInstance(DatasourceHealthCheck.class));
		
		setUpSwagger(configuration, environment);
	}

}