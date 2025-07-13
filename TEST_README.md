# Unit Tests for VendsSmartRentingSystem Authentication

This document describes the comprehensive unit tests created for the login, signup, and logout functionality of the VendsSmartRentingSystem.

## Overview

The test suite covers all authentication-related functionality including:
- User login (successful and failed scenarios)
- User signup (successful and failed scenarios)
- User logout
- Password hashing and verification
- User session management
- Exception handling

## Test Structure

### 1. UserAuthenticationTest
**Location**: `src/test/java/com/amdocs/vends/service/UserAuthenticationTest.java`

**Purpose**: Tests the core authentication functionality including login, signup, and logout operations.

**Key Test Categories**:
- **Login Tests**:
  - Successful login with valid credentials (admin and tenant)
  - Failed login with invalid username/password
  - Login with empty/null credentials
  - Login with password change for first-time tenants
  - Login without password change for admins

- **Signup Tests**:
  - Successful signup with valid data
  - Failed signup with duplicate username
  - Failed signup with empty/null data
  - User creation with valid data
  - User creation with duplicate username (exception testing)
  - User creation with database errors

- **Logout Tests**:
  - Successful logout
  - Logout when not logged in
  - Multiple logout operations

- **Integration Tests**:
  - Complete authentication flow (signup → login → logout → login again)
  - Password hashing consistency

### 2. PasswordUtilTest
**Location**: `src/test/java/com/amdocs/vends/utils/PasswordUtilTest.java`

**Purpose**: Tests the password hashing and verification utility functions.

**Key Test Categories**:
- Password hashing with various input types
- Password verification (correct and incorrect passwords)
- Hash consistency and uniqueness
- Performance testing
- Edge cases (null, empty, special characters, unicode)

### 3. LoggedInUserTest
**Location**: `src/test/java/com/amdocs/vends/utils/singleton/LoggedInUserTest.java`

**Purpose**: Tests the singleton class that manages logged-in user session data.

**Key Test Categories**:
- User session data management (set/get operations)
- Role management (ADMIN, TENANT, invalid roles)
- Logout functionality
- Singleton behavior
- Concurrent access simulation

### 4. DuplicateUsernameExceptionTest
**Location**: `src/test/java/com/amdocs/vends/utils/exceptions/DuplicateUsernameExceptionTest.java`

**Purpose**: Tests the custom exception class for duplicate username scenarios.

**Key Test Categories**:
- Exception creation with various message types
- Exception inheritance and behavior
- Message handling (null, empty, special characters, unicode)

## Test Infrastructure

### Test Database Setup
- **TestDatabaseSetup**: Creates an in-memory H2 database for testing
- **MockJDBC**: Provides test database connections instead of real MySQL connections
- **TestableUserImpl**: A testable version of UserImpl that doesn't require user input

### Dependencies Added
- **JUnit 4.13.2**: Core testing framework
- **Mockito 4.5.1**: Mocking framework (for future use)
- **H2 Database 2.1.214**: In-memory database for testing

## Running the Tests

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher

### Running All Tests
```bash
mvn test
```

### Running Specific Test Classes
```bash
# Run only authentication tests
mvn test -Dtest=UserAuthenticationTest

# Run only password utility tests
mvn test -Dtest=PasswordUtilTest

# Run only session management tests
mvn test -Dtest=LoggedInUserTest

# Run only exception tests
mvn test -Dtest=DuplicateUsernameExceptionTest
```

### Running the Test Suite
```bash
mvn test -Dtest=AllAuthenticationTests
```

### Running Individual Test Methods
```bash
# Run specific test method
mvn test -Dtest=UserAuthenticationTest#testSuccessfulLoginWithValidCredentials

# Run multiple specific test methods
mvn test -Dtest=UserAuthenticationTest#testSuccessfulLoginWithValidCredentials,UserAuthenticationTest#testFailedLoginWithInvalidPassword
```

## Test Coverage

The test suite provides comprehensive coverage for:

### Login Functionality
- ✅ Valid credentials (admin and tenant)
- ✅ Invalid credentials
- ✅ Empty/null credentials
- ✅ First-time tenant password change
- ✅ Admin login without password change
- ✅ Database connection errors

### Signup Functionality
- ✅ Valid user registration
- ✅ Duplicate username handling
- ✅ Invalid data handling
- ✅ Database error scenarios
- ✅ User creation with proper data validation

### Logout Functionality
- ✅ Successful logout
- ✅ Logout when not logged in
- ✅ Multiple logout operations
- ✅ Session data cleanup

### Password Security
- ✅ SHA-256 hashing
- ✅ Password verification
- ✅ Hash consistency
- ✅ Performance validation
- ✅ Edge case handling

### Session Management
- ✅ User data storage and retrieval
- ✅ Role management
- ✅ Session cleanup
- ✅ Singleton behavior

### Exception Handling
- ✅ Custom exception creation
- ✅ Exception message handling
- ✅ Exception inheritance
- ✅ Edge case scenarios

## Test Data

The test database is pre-populated with:
- **Admin user**: username="admin", password="admin123"
- **Tenant user**: username="tenant", password="tenant123" (first_login=true)

## Best Practices Implemented

1. **Isolation**: Each test is independent and doesn't affect others
2. **Cleanup**: Proper teardown after each test
3. **Mocking**: Uses in-memory database instead of real database
4. **Comprehensive Coverage**: Tests both success and failure scenarios
5. **Edge Cases**: Tests null, empty, and invalid inputs
6. **Performance**: Includes performance validation for critical operations
7. **Documentation**: Clear test names and descriptions

## Future Enhancements

1. **Integration Tests**: Add tests that use the real MySQL database
2. **Performance Tests**: Add load testing for authentication operations
3. **Security Tests**: Add tests for SQL injection prevention
4. **UI Tests**: Add tests for the console-based user interface
5. **Mockito Usage**: Implement mocking for better test isolation

## Troubleshooting

### Common Issues

1. **Database Connection Errors**: Ensure H2 database dependency is properly configured
2. **Test Failures**: Check that all dependencies are resolved correctly
3. **Memory Issues**: Tests use in-memory database, so memory should not be an issue

### Debug Mode
To run tests with detailed output:
```bash
mvn test -X
```

### Test Reports
After running tests, check the `target/surefire-reports` directory for detailed test reports. 