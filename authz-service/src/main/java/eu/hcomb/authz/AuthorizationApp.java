package eu.hcomb.authz;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.Authorizer;
import io.dropwizard.setup.Environment;

import com.google.inject.Binder;
import com.google.inject.Guice;

import eu.hcomb.authz.resources.RolesResource;
import eu.hcomb.authz.service.ProfileService;
import eu.hcomb.authz.service.impl.ProfileServiceImpl;
import eu.hcomb.common.auth.TokenAuthenticator;
import eu.hcomb.common.auth.UserAuthorizer;
import eu.hcomb.common.healthcheck.DatasourceHealthCheck;
import eu.hcomb.common.resources.WhoAmI;
import eu.hcomb.common.service.TokenService;
import eu.hcomb.common.service.impl.TokenServiceImpl;
import eu.hcomb.common.web.BaseApp;

public class AuthorizationApp extends BaseApp<AuthorizationConfig> {
	
	public static void main(String[] args) throws Exception {
		new AuthorizationApp().run(args);
	}
	
	public void configure(Binder binder) {
		
		binder
			.bind(TokenService.class)
			.to(TokenServiceImpl.class);
		
		binder
			.bind(Authenticator.class)
			.to(TokenAuthenticator.class);

		binder
			.bind(Authorizer.class)
			.to(UserAuthorizer.class);

		binder
			.bind(ProfileService.class)
			.to(ProfileServiceImpl.class);
		
	}	

	@Override
	public void run(AuthorizationConfig configuration, Environment environment) {
		
		defaultConfig(environment, configuration);

		AuthorizationPersistence persistence = new AuthorizationPersistence(configuration);

		injector = Guice.createInjector(this, persistence);

        setupSecurity(environment);
        
		environment.jersey().register(injector.getInstance(WhoAmI.class));
		environment.jersey().register(injector.getInstance(RolesResource.class));
				
		environment.healthChecks().register("mysql", injector.getInstance(DatasourceHealthCheck.class));
	}

}