package com.lpasystems.premieraco;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lpasystems.premieraco.resources.DateResource;
import com.lpasystems.premieraco.resources.ProviderResource;
import com.lpasystems.premieraco.resources.ReportingPeriodResource;

public class App extends Application<PremierAcoConfiguration> {
	private final static Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}

	@Override
	public void initialize(Bootstrap<PremierAcoConfiguration> bootstrap) {
		bootstrap.addBundle(new AssetsBundle());
	}

	@Override
	public void run(PremierAcoConfiguration configuration, Environment environment) throws Exception {
		LOGGER.info("Started...");

		// Create a DBI factory and build a JDBI instance
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "sqlServer");
		
		// Configure for CORS
		 Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
	   filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
	   filter.setInitParameter("allowedOrigins", "*");
	   filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
	   filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS");
	    //filter.setInitParameter("preflightMaxAge", "5184000"); // 2 months
	    //filter.setInitParameter("allowCredentials", "true");

		// Add the resources to the environment
		environment.jersey().register(new DateResource(jdbi));
		environment.jersey().register(new ProviderResource(jdbi));
		environment.jersey().register(new ReportingPeriodResource(jdbi));
	}
}
