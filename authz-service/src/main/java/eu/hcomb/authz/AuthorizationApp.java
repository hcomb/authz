package eu.hcomb.authz;

import io.dropwizard.setup.Environment;

import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.Binder;
import com.google.inject.Guice;

import eu.hcomb.authz.resources.RolesResource;
import eu.hcomb.authz.service.ProfileService;
import eu.hcomb.authz.service.impl.ProfileServiceImpl;
import eu.hcomb.authz.service.mapper.ProfileMapper;
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
			.bind(ProfileService.class)
			.to(ProfileServiceImpl.class);
		
	}	

	@Override
	public void run(AuthorizationConfig configuration, Environment environment) {
		
		DefaultPersistenceModule persistence = new DefaultPersistenceModule(configuration) {
			@Override
			protected void initialize() {
				install(JdbcHelper.MySQL);
				setup();
		        addMapperClass(ProfileMapper.class);				
			}
		};
		
		injector = Guice.createInjector(this, persistence);

		defaultConfig(environment, configuration);
        
		environment.jersey().register(injector.getInstance(WhoAmI.class));
		environment.jersey().register(injector.getInstance(RolesResource.class));
				
		environment.healthChecks().register("mysql", injector.getInstance(DatasourceHealthCheck.class));
		
	}

}