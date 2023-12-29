package org.keep;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.keep.configuration.MongoDBConfig;

public class Configurations extends Configuration {
    @JsonProperty
    private MongoDBConfig dbConfig;

    public MongoDBConfig getDBConfig() {
        return dbConfig;
    }
}
