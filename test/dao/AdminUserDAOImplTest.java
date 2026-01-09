package dao;

import java.util.List;
import model.AdminUserData;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AdminUserDAOImplTest {
    
    private static AdminUserDAOImpl dao;
    private static AdminUserData testUser;
    private static int testUserId = -1;
    
    @BeforeClass
    public static void setUpClass() {
        dao = new AdminUserDAOImpl();
        System.out.println("Starting AdminUserDAOImpl tests...");
    }
    
    @AfterClass
    public static void tearDownClass() {
        // Clean up test user if it exists
        if (testUserId > 0) {
            dao.deleteUser(testUserId);
        }
        System.out.println("Finished AdminUserDAOImpl tests.");
    }
    
    @Before
    public void setUp() {
        // Create a fresh test user before each test
        testUser = new AdminUserData();
        testUser.setEmail("test" + System.currentTimeMillis() + "@example.com");
        testUser.setPassword("testPassword123");
        testUser.setUsername("testuser" + System.currentTimeMillis());
        testUser.setFullName("Test User");
        testUser.setAddress("123 Test Street");
        testUser.setPhone("9876543210");
    }
    
    @After
    public void tearDown() {
        // Clean up after each test
        if (testUserId > 0) {
            dao.deleteUser(testUserId);
            testUserId = -1;
        }
    }

    @Test
    public void testCreateUser() {
        System.out.println("Testing createUser...");
        
        boolean result = dao.createUser(testUser);
        assertTrue("User should be created successfully", result);
        
        // Verify user was created by checking if it exists
        boolean exists = dao.userExists(testUser.getEmail());
        assertTrue("Created user should exist in database", exists);
        
        // Get the user to store ID for cleanup
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        assertNotNull("Created user should be retrievable", createdUser);
        testUserId = createdUser.getid();
    }

    @Test
    public void testGetUserById() {
        System.out.println("Testing getUserById...");
        
        // First create a user
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Now retrieve by ID
        AdminUserData result = dao.getUserById(testUserId);
        
        assertNotNull("User should be found by ID", result);
        assertEquals("Email should match", testUser.getEmail(), result.getEmail());
        assertEquals("Username should match", testUser.getUsername(), result.getUsername());
        assertEquals("Full name should match", testUser.getFullName(), result.getFullName());
    }

    @Test
    public void testGetUserByEmail() {
        System.out.println("Testing getUserByEmail...");
        
        // First create a user
        dao.createUser(testUser);
        
        // Now retrieve by email
        AdminUserData result = dao.getUserByEmail(testUser.getEmail());
        
        assertNotNull("User should be found by email", result);
        assertEquals("Email should match", testUser.getEmail(), result.getEmail());
        assertEquals("Username should match", testUser.getUsername(), result.getUsername());
        
        testUserId = result.getid();
    }

    @Test
    public void testUpdateUser() {
        System.out.println("Testing updateUser...");
        
        // Create a user first
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Update the user
        createdUser.setFullName("Updated Name");
        createdUser.setAddress("456 New Street");
        createdUser.setPhone("1234567890");
        
        boolean result = dao.updateUser(createdUser);
        assertTrue("User should be updated successfully", result);
        
        // Verify the update
        AdminUserData updatedUser = dao.getUserById(testUserId);
        assertEquals("Full name should be updated", "Updated Name", updatedUser.getFullName());
        assertEquals("Address should be updated", "456 New Street", updatedUser.getAddress());
        assertEquals("Phone should be updated", "1234567890", updatedUser.getPhone());
    }

    @Test
    public void testDeleteUser() {
        System.out.println("Testing deleteUser...");
        
        // Create a user first
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        int userId = createdUser.getid();
        
        // Delete the user
        boolean result = dao.deleteUser(userId);
        assertTrue("User should be deleted successfully", result);
        
        // Verify deletion
        AdminUserData deletedUser = dao.getUserById(userId);
        assertNull("Deleted user should not be found", deletedUser);
        
        testUserId = -1; // Set to -1 so cleanup doesn't try to delete again
    }

    @Test
    public void testAuthenticateUser() {
        System.out.println("Testing authenticateUser...");
        
        // Create a user first
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Test successful authentication
        boolean result = dao.authenticateUser(testUser.getEmail(), testUser.getPassword());
        assertTrue("Authentication should succeed with correct password", result);
        
        // Test failed authentication
        boolean failResult = dao.authenticateUser(testUser.getEmail(), "wrongPassword");
        assertFalse("Authentication should fail with incorrect password", failResult);
    }

    @Test
    public void testUserExists() {
        System.out.println("Testing userExists...");
        
        // Test with non-existent user
        boolean existsBefore = dao.userExists(testUser.getEmail());
        assertFalse("User should not exist before creation", existsBefore);
        
        // Create user
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Test with existing user
        boolean existsAfter = dao.userExists(testUser.getEmail());
        assertTrue("User should exist after creation", existsAfter);
    }

    @Test
    public void testGetAllUsers() {
        System.out.println("Testing getAllUsers...");
        
        // Get initial count
        List<AdminUserData> usersBefore = dao.getAllUsers();
        int countBefore = usersBefore != null ? usersBefore.size() : 0;
        
        // Create a user
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Get all users again
        List<AdminUserData> usersAfter = dao.getAllUsers();
        
        assertNotNull("User list should not be null", usersAfter);
        assertEquals("User count should increase by 1", countBefore + 1, usersAfter.size());
    }

    @Test
    public void testUpdatePassword() {
        System.out.println("Testing updatePassword...");
        
        // Create a user first
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Update password
        String newPassword = "newPassword456";
        boolean result = dao.updatePassword(testUserId, newPassword);
        assertTrue("Password should be updated successfully", result);
        
        // Verify new password works for authentication
        boolean authResult = dao.authenticateUser(testUser.getEmail(), newPassword);
        assertTrue("Authentication should succeed with new password", authResult);
        
        // Verify old password doesn't work
        boolean oldAuthResult = dao.authenticateUser(testUser.getEmail(), testUser.getPassword());
        assertFalse("Authentication should fail with old password", oldAuthResult);
    }

    @Test
    public void testUpdateProfile() {
        System.out.println("Testing updateProfile...");
        
        // Create a user first
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Update profile
        createdUser.setFullName("Profile Updated");
        createdUser.setAddress("789 Profile Street");
        createdUser.setPhone("5555555555");
        
        boolean result = dao.updateProfile(createdUser);
        assertTrue("Profile should be updated successfully", result);
        
        // Verify the update
        AdminUserData updatedUser = dao.getUserById(testUserId);
        assertEquals("Full name should be updated", "Profile Updated", updatedUser.getFullName());
        assertEquals("Address should be updated", "789 Profile Street", updatedUser.getAddress());
        assertEquals("Phone should be updated", "5555555555", updatedUser.getPhone());
    }

    @Test
    public void testDeactivateUser() {
        System.out.println("Testing deactivateUser...");
        
        // Create a user first
        dao.createUser(testUser);
        AdminUserData createdUser = dao.getUserByEmail(testUser.getEmail());
        testUserId = createdUser.getid();
        
        // Verify user is active initially
        assertTrue("User should be active initially", createdUser.isActive());
        
        // Deactivate user
        boolean result = dao.deactivateUser(testUserId);
        assertTrue("User should be deactivated successfully", result);
        
        // Verify deactivation
        AdminUserData deactivatedUser = dao.getUserById(testUserId);
        assertFalse("User should be inactive after deactivation", deactivatedUser.isActive());
        
        // Verify authentication fails for deactivated user
        boolean authResult = dao.authenticateUser(testUser.getEmail(), testUser.getPassword());
        assertFalse("Authentication should fail for deactivated user", authResult);
    }
}