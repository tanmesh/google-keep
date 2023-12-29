package org.keep.utils;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.keep.configuration.MongoDBConfig;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.ArrayList;
import java.util.List;

public class MongoUtils {
    public static Datastore createDatastore(MongoDBConfig dbConfig) {
        ServerAddress addr = new ServerAddress(dbConfig.getHost(), dbConfig.getPort());

        List<MongoCredential> credentialsList = new ArrayList<>();
        MongoClient client = new MongoClient(addr, credentialsList);
        Datastore datastore = new Morphia().mapPackage("com.tanmesh.splatter.entity").createDatastore(client, dbConfig.getDbName());
        datastore.ensureIndexes(true);
        return datastore;
    }
}
