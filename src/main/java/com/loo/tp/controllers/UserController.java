package com.loo.tp.controllers;

import org.javatuples.Triplet;

import com.loo.tp.entities.User;
import com.loo.tp.repository.interfaces.UserRepository;
import com.loo.tp.session.SessionManager;

public class UserController extends BaseController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository, SessionManager sessionManager) {
        super(sessionManager);
        this.userRepository = userRepository;
    }

    public Triplet<Boolean, User, String> getUser(String username, String password) {
        User user = userRepository.getByCredentials(username, password);
        if (user != null) {
            sessionManager.setUser(user);
            return Ok(user);
        }
        return Error("Usuario no encontrado.");
    }

    public Triplet<Boolean, User[], String> getUsers() {
        if (!this.isAdmin()) {
            return Error(USER_IS_NOT_ADMIN_ERROR_MESSAGE);
        }
        return Ok(userRepository.get());
    }

    public Triplet<Boolean, User, String> getById(long userId) {
        if (!this.isAdmin()) {
            return Error(USER_IS_NOT_ADMIN_ERROR_MESSAGE);
        }
        var user = userRepository.getById(userId);
        return user != null
                ? Ok(user)
                : Error(null);
    }

    public Triplet<Boolean, User, String> addUser(User newUser) {
        if (!this.isAdmin()) {
            return Error(USER_IS_NOT_ADMIN_ERROR_MESSAGE);
        }
        if (newUser.getName() == null || newUser.getName().equals("")) {
            return Error("El campo 'Nombre' tiene un valor inválido.");
        }
        if (newUser.getPassword() == null || newUser.getPassword().equals("")) {
            return Error("El campo 'Contraseña' tiene un valor inválido.");
        }

        var users = userRepository.get();
        for (User user : users) {
            if (user.getName().equals(newUser.getName())) {
                return Error("Ya existe un usuario con ese nombre.");
            }
        }

        var user = userRepository.add(newUser);
        return user != null
                ? Ok(user)
                : Error("Error al guardar el usuario.");
    }

    public Triplet<Boolean, User, String> changeStatus(long userId, boolean status) {
        if (!this.isAdmin()) {
            return Error(USER_IS_NOT_ADMIN_ERROR_MESSAGE);
        }
        if (sessionManager.getUser().getId() == userId) {
            return Error("Un usuario no puede modificar su propio estado.");
        }
        var user = userRepository.changeStatus(userId, status);
        return user != null
                ? Ok(user)
                : Error("Error al modificar el estado del usuario.");
    }

    public Triplet<Boolean, Long, String> changeStatus(long userIds[], boolean status) {
        if (!this.isAdmin()) {
            return Error(USER_IS_NOT_ADMIN_ERROR_MESSAGE);
        }
        long currentUserId = sessionManager.getUser().getId();
        for (long userId : userIds) {
            if (userId == currentUserId) {
            return Error("Un usuario no puede modificar su propio estado.");
            }
        }
        var modified = userRepository.changeStatus(userIds, status);
        return modified > 0
                ? Ok(modified)
                : Error("No se modificó ningún usuario.");
    }
}
