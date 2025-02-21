package com.mindex.challenge.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import java.net.InetSocketAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.lang.NonNull;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    @NonNull
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    @NonNull
    public MongoClient mongoClient() {
        MongoServer server = new MongoServer(new MemoryBackend());
        InetSocketAddress serverAddress = server.bind();
        String mongoConnectionString =
                String.format(
                        "mongodb://%s:%d", serverAddress.getHostName(), serverAddress.getPort());
        MongoClientSettings settings =
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(mongoConnectionString))
                        .build();
        return MongoClients.create(settings);
    }
}
