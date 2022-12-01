package ru.ncs.DemoShop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class AbstractUnitTest {

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
        init();
    }

    protected abstract void init();

    @AfterEach
    void teardDown() throws Exception {
        closeable.close();
    }
}
