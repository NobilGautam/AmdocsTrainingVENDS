package com.amdocs.vends.service;

import com.amdocs.vends.bean.User;
import com.amdocs.vends.dao.JDBC;
import com.amdocs.vends.utils.PasswordUtil;
import com.amdocs.vends.utils.enums.Role;
import com.amdocs.vends.utils.exceptions.DuplicateUsernameException;
import com.amdocs.vends.utils.singleton.LoggedInUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserImplTest {

    private UserImpl userService;
    private Connection mockConnection;
    private Statement mockStatement;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() {
        userService = new UserImpl();
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);
    }

    @After
    public void tearDown() {
        LoggedInUser.logout();
    }

    @Test
    public void testAddUserSuccess() throws Exception {
        // Arrange - Use a unique username to avoid conflicts
        String uniqueUsername = "testuser_" + System.currentTimeMillis();
        User user = new User(Role.ADMIN.getValue(), "Test User", uniqueUsername,
                           PasswordUtil.hashPassword("password123"), true, "1234567890");
        
        // Act
        Integer result = userService.addUser(user);
        
        // Assert - Just check that a valid user ID is returned (greater than 0)
        assertNotNull(result);
        assertTrue("User ID should be greater than 0", result > 0);
    }

    @Test
    public void testAddUserDuplicateUsername() throws Exception {
        // Arrange - First create a user
        String uniqueUsername = "testuser_dup_" + System.currentTimeMillis();
        User user1 = new User(Role.ADMIN.getValue(), "Test User 1", uniqueUsername,
                            PasswordUtil.hashPassword("password123"), true, "1234567890");
        userService.addUser(user1);
        
        // Now try to create another user with the same username
        User user2 = new User(Role.ADMIN.getValue(), "Test User 2", uniqueUsername,
                            PasswordUtil.hashPassword("password456"), true, "9876543210");
        
        // Act
        Integer result = userService.addUser(user2);
        
        // Assert - The method catches the exception and returns -1
        assertEquals(Integer.valueOf(-1), result);
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