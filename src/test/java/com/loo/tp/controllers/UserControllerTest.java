package com.loo.tp.controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.loo.tp.entities.User;
import com.loo.tp.repository.interfaces.UserRepository;

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepositoryMock;

    @Before
    public void setup() {
        userRepositoryMock = mock(UserRepository.class);
        userController = new UserController(userRepositoryMock);
    }

    @Test
    public void getByCredentialsReturnsUser() {
        // Arrange
        String name = "Name";
        String password = "Password";
        User user = new User(1, name, password, false);
        when(userRepositoryMock.getByCredentials(anyString(), anyString())).thenReturn(user);

        // Act
        var result = userController.getUser(name, password);

        // Assert
        assertTrue(result.getValue0());
        assertNotNull(result.getValue1());

    }

    @Test
    public void getByCredentialsReturns() {
        // Arrange
        String name = "Name";
        String password = "Password";
        when(userRepositoryMock.getByCredentials(anyString(), anyString())).thenReturn(null);

        // Act
        var result = userController.getUser(name, password);

        // Assert
        assertFalse(result.getValue0());
        assertNull(result.getValue1());
    }
}
