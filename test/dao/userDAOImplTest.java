/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package dao;

import java.util.List;
import model.AdminUserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author srsro
 */
public class userDAOImplTest {
    
    public userDAOImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createUser method, of class userDAOImpl.
     */
    @Test
    public void testCreateUser() {
        System.out.println("createUser");
        AdminUserData user = null;
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.createUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserById method, of class userDAOImpl.
     */
    @Test
    public void testGetUserById() {
        System.out.println("getUserById");
        int userId = 0;
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        AdminUserData expResult = null;
        AdminUserData result = instance.getUserById(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserByEmail method, of class userDAOImpl.
     */
    @Test
    public void testGetUserByEmail() {
        System.out.println("getUserByEmail");
        String email = "";
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        AdminUserData expResult = null;
        AdminUserData result = instance.getUserByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUser method, of class userDAOImpl.
     */
    @Test
    public void testUpdateUser() {
        System.out.println("updateUser");
        AdminUserData user = null;
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.updateUser(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteUser method, of class userDAOImpl.
     */
    @Test
    public void testDeleteUser() {
        System.out.println("deleteUser");
        int userId = 0;
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.deleteUser(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of authenticateUser method, of class userDAOImpl.
     */
    @Test
    public void testAuthenticateUser() {
        System.out.println("authenticateUser");
        String email = "";
        String password = "";
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.authenticateUser(email, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of userExists method, of class userDAOImpl.
     */
    @Test
    public void testUserExists() {
        System.out.println("userExists");
        String email = "";
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.userExists(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllUsers method, of class userDAOImpl.
     */
    @Test
    public void testGetAllUsers() {
        System.out.println("getAllUsers");
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        List<AdminUserData> expResult = null;
        List<AdminUserData> result = instance.getAllUsers();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePassword method, of class userDAOImpl.
     */
    @Test
    public void testUpdatePassword() {
        System.out.println("updatePassword");
        int userId = 0;
        String newPasswordHash = "";
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.updatePassword(userId, newPasswordHash);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProfile method, of class userDAOImpl.
     */
    @Test
    public void testUpdateProfile() {
        System.out.println("updateProfile");
        AdminUserData user = null;
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.updateProfile(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deactivateUser method, of class userDAOImpl.
     */
    @Test
    public void testDeactivateUser() {
        System.out.println("deactivateUser");
        int userId = 0;
        AdminUserDAOImpl instance = new AdminUserDAOImpl();
        boolean expResult = false;
        boolean result = instance.deactivateUser(userId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
