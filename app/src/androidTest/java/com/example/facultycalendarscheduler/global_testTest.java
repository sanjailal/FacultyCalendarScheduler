package com.example.facultycalendarscheduler;

import static org.junit.Assert.*;
import org.junit.Test;

public class global_testTest {
    @Test
    public void test_username() {
        assertEquals(global_test.test_username("soft@gmail.com"),true);
    }

    @Test
    public void test_password() {
        assertEquals(global_test.test_password("softENGINE"),true);
    }

}