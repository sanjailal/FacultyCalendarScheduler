package com.example.facultycalendarscheduler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class global_testTest {
    @Test
    public void test_username() {
        assertEquals(global_test.test_username("soft@gmail.com"), true);
    }

    @Test
    public void test_password() {
        assertEquals(global_test.test_password("softENGINEhhhh"), true);
    }

}