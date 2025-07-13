package com.amdocs.vends.service;

import com.amdocs.vends.utils.PasswordUtil;
import com.amdocs.vends.utils.enums.Role;
import com.amdocs.vends.utils.singleton.LoggedInUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserImplTest {

    private UserImpl userService;

    @Before
    public void setUp() {
        userService = new UserImpl();
    }

    @After
    public void tearDown() {
        LoggedInUser.logout();
    }

    @Test
    public void testPasswordHashing() {
        // Arrange
        String password = "testpassword123";
        
        // Act
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        // Assert
        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
        assertTrue(hashedPassword.length() > 0);
    }

    @Test
    public void testPasswordVerification() {
        // Arrange
        String password = "testpassword123";
        String hashedPassword = PasswordUtil.hashPassword(password);
        
        // Act
        boolean isValid = PasswordUtil.verifyPassword(password, hashedPassword);
        boolean isInvalid = PasswordUtil.verifyPassword("wrongpassword", hashedPassword);
        
        // Assert
        assertTrue(isValid);
        assertFalse(isInvalid);
    }

    @Test
    public void testLoggedInUserFunctionality() {
        // Arrange
        LoggedInUser.setName("Test User");
        LoggedInUser.setUserId(123);
        LoggedInUser.setRole("ADMIN");
        
        // Assert initial state
        assertEquals("Test User", LoggedInUser.getName());
        assertEquals(Integer.valueOf(123), LoggedInUser.getUserId());
        assertEquals(Role.ADMIN, LoggedInUser.getRole());
        
        // Act
        LoggedInUser.logout();
        
        // Assert after logout
        assertNull(LoggedInUser.getName());
        assertNull(LoggedInUser.getUserId());
        assertNull(LoggedInUser.getRole());
    }

    @Test
    public void testRoleEnum() {
        // Test Role enum functionality
        assertEquals("admin", Role.ADMIN.getValue());
        assertEquals("tenant", Role.TENANT.getValue());
        assertEquals(Role.ADMIN, Role.valueOf("ADMIN"));
        assertEquals(Role.TENANT, Role.valueOf("TENANT"));
    }
} 