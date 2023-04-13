package com.example.diplomawork;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class DiplomaWorkApplicationTests {

    private static final int PORT_BD = 3306;
    private static final int PORT_SERVER = 5050;

    private static final String DATABASE_NAME = "diploma_work_cloud_storage";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "412112Alex1994";

    private static final Network CLOUD_NETWORK = Network.newNetwork();

    @Container
    public static MySQLContainer<?> databaseContainer = new MySQLContainer<>("mysql")
            .withNetwork(CLOUD_NETWORK)
            .withExposedPorts(PORT_BD)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USERNAME)
            .withPassword(DATABASE_PASSWORD);

    @Container
    public static GenericContainer<?> serverContainer = new GenericContainer<>("backend")
            .withNetwork(CLOUD_NETWORK)
            .withExposedPorts(PORT_SERVER)
            .withEnv(Map.of("SPRING_DATASOURSE_URL", "jdbc:mysql://database:" + PORT_BD + "/" + DATABASE_NAME))
            .dependsOn(databaseContainer);

    @Test
    void contextDatabase() {
        assertTrue(databaseContainer.isRunning());
    }

    @Test
    void contextServer() {
        assertFalse(serverContainer.isRunning());
    }
}
