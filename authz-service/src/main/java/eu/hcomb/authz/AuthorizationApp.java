package eu.hcomb.authz;

import io.dropwizard.setup.Environment;

import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.Binder;
import com.google.inject.Guice;

import eu.hcomb.authz.resources.LoginResource;
import eu.hcomb.authz.resources.UserResource;
import eu.hcomb.authz.service.UserService;
import eu.hcomb.authz.service.impl.UserServiceImpl;
import eu.hcomb.authz.service.mapper.RoleMapper;
import eu.hcomb.authz.service.mapper.UserMapper;
import eu.hcomb.common.healthcheck.DatasourceHealthCheck;
import eu.hcomb.common.jdbc.DefaultPersistenceModule;
import eu.hcomb.common.resources.WhoAmI;
import eu.hcomb.common.web.BaseApp;

public class AuthorizationApp extends BaseApp<AuthorizationConfig> {
	
	public static void main(String[] args) throws Exception {
		new AuthorizationApp().run(args);
	}
	
	public void configure(Binder binder) {
		configureSecurity(binder);
		
		binder
			.bind(UserService.class)
			.to(UserServiceImpl.class);
	}	

	@Override
	public void run(AuthorizationConfig configuration, Environment environment) {
		
		DefaultPersistenceModule persistence = new DefaultPersistenceModule(configuration) {
			@Override
			protected void initialize() {
				install(JdbcHelper.MySQL);
				setup();
		        addMapperClass(RoleMapper.class);				
		        addMapperClass(UserMapper.class);				
			}
		};
		
		injector = Guice.createInjector(this, persistence);

		defaultConfig(environment, configuration);
        
		environment.jersey().register(injector.getInstance(WhoAmI.class));
		environment.jersey().register(injector.getInstance(LoginResource.class));
		environment.jersey().register(injector.getInstance(UserResource.class));
				
		environment.healthChecks().register("mysql", injector.getInstance(DatasourceHealthCheck.class));
		
	}

}