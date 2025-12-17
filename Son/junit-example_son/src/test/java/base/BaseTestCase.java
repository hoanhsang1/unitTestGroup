package base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

abstract public class BaseTestCase {
    @BeforeEach
    protected void beforeEach() {
        System.out.println("Before each test");
    }

    @AfterEach
    protected void afterEach() {
        System.out.println("After each test");
    }
}
