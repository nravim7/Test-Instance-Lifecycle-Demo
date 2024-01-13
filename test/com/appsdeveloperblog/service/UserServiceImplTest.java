package com.appsdeveloperblog.service;

import com.appsdeveloperblog.io.UsersDatabase;
import com.appsdeveloperblog.io.UsersDatabaseMapImpl;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId = "";

    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();
        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {

        //Arrange
        Map<String, String> user = new HashMap<>();
        user.put("FirstName", "Ravi");
        user.put("lastName", "Nagaraju");

        //Act
        createdUserId = userService.createUser(user);

        //Assert
        assertNotNull("User id should nor be null", createdUserId);

    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {

        //Arrange
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName","John");
        newUserDetails.put("lastname", "martin");

        //Act
        Map updatedUserDetails = userService.updateUser(createdUserId, newUserDetails);

        //Assert
        Assert.assertEquals("failed", newUserDetails.get("firstName"), updatedUserDetails.get("firstName"));
        Assert.assertEquals("failed", newUserDetails.get("lastName"), updatedUserDetails.get("lastName"));

    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {

        //Act
        Map userdet = userService.getUserDetails(createdUserId);

        //Assert
        assertNotNull("ugugdugd", userdet);
        assertEquals("Not the same id", createdUserId, userdet.get("userId"));

    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {

        //Act
        userService.deleteUser(createdUserId);

        //Assert
        assertNull(userService.getUserDetails(createdUserId), "User not found");

    }

}
