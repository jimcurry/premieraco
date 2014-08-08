package com.lpasystems.premieraco;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class PremierAcoConfiguration extends Configuration {

	@JsonProperty
	private DataSourceFactory dataSourceFactory = new DataSourceFactory();

	public DataSourceFactory getDataSourceFactory() {
		return dataSourceFactory;
	}

}
