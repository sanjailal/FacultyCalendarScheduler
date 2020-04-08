package com.example.facultycalendarscheduler;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Global_TestTest {
    @Test
    public void testUsername() {
        assertEquals(global_test.test_username("soft@gmail.com"), true);
    }

    @Test
    public void testPassword() {
        assertEquals(global_test.test_password("softENGINEhhhh"), true);
    }
}