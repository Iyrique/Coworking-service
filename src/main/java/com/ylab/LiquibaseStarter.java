package com.ylab;

import com.ylab.connector.ConnectorDB;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * A class for performing migrations
 */
public class LiquibaseStarter {

    public static void main(String[] args) {
        Map<String, String> config = loadConfig("liquibase.yml");

        String changeLogFile = config.get("changeLogFile");
        String defaultSchemaName = config.get("defaultSchemaName");
        String liquibaseSchemaName = config.get("liquibaseSchemaName");

        try {
            ConnectorDB connectorDB = ConnectorDB.getInstance();
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connectorDB.getConnection()));
            Liquibase liquibase =
                    new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
            database.setDefaultSchemaName(defaultSchemaName);
            database.setLiquibaseSchemaName(liquibaseSchemaName);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        } catch (LiquibaseException e) {
            System.out.println("SQL Exception in migration:" + e.getMessage());
        }
    }

    private static Map<String, String> loadConfig(String fileName) {
        Yaml yaml = new Yaml();
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (in != null) {
                return yaml.load(in);
            } else {
                throw new RuntimeException("Failed to load configuration file: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while loading configuration file: " + fileName, e);
        }
    }

}
