package eu.hcomb.authz;

import java.util.Properties;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import eu.hcomb.authz.service.mapper.ProfileMapper;

public class AuthorizationPersistence extends MyBatisModule {
	
	protected AuthorizationConfig configuration;
	
	public AuthorizationPersistence(AuthorizationConfig configuration) {
		this.configuration = configuration;
	}
	
	@Override
	protected void initialize() {
		install(JdbcHelper.MySQL);
		
		bindDataSourceProviderType(PooledDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        addMapperClass(ProfileMapper.class);

        Names.bindProperties(binder(), createProperties());

	}

	private Properties createProperties() {
        Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "test");
        
        myBatisProperties.setProperty("JDBC.username", configuration.getDatabase().getUsername());
        myBatisProperties.setProperty("JDBC.password", configuration.getDatabase().getPassword());
        myBatisProperties.setProperty("JDBC.autoCommit", configuration.getDatabase().getAutoCommit());
        myBatisProperties.setProperty("JDBC.host", configuration.getDatabase().getHost());
        myBatisProperties.setProperty("JDBC.port", configuration.getDatabase().getPort());
        myBatisProperties.setProperty("JDBC.schema", configuration.getDatabase().getSchema());
        return myBatisProperties;
	}
	
	@Provides
	@Named("healthcheck.query")
	public String getHealthCheckQuery(){
		return configuration.getDatabase().getHealthCheckQuery();
	}
}
