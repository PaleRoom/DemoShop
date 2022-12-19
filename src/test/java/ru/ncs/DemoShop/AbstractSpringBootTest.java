package ru.ncs.DemoShop;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("local")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AbstractSpringBootTest {
    @LocalServerPort
    protected int port;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        init();
    }

    protected abstract void init();

}

