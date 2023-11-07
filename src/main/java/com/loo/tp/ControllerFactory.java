package com.loo.tp;

import com.loo.tp.controllers.PrintController;
import com.loo.tp.controllers.SessionController;
import com.loo.tp.controllers.UserController;
import com.loo.tp.repository.PrintRepositoryImpl;
import com.loo.tp.repository.UserRepositoryImpl;
import com.loo.tp.repository.interfaces.PrintRepository;
import com.loo.tp.repository.interfaces.UserRepository;
import com.loo.tp.session.SessionManager;
import com.loo.tp.session.SessionManagerImpl;

public final class ControllerFactory {
    private static SessionManager sessionManager;
    private static UserRepository userRepository;
    private static PrintRepository printRepository;

    public static UserController getUserController() {
        return new UserController(userRepository, sessionManager);
    }

    public static SessionController getSessionController() {
        return new SessionController(sessionManager);
    }

    public static PrintController getPrintController() {
        return new PrintController(sessionManager, printRepository, userRepository);
    }

    public static void init() {
        if (sessionManager == null) {
            sessionManager = new SessionManagerImpl();
        }
        if (userRepository == null) {
            userRepository = new UserRepositoryImpl();
        }
        if (printRepository == null) {
            printRepository = new PrintRepositoryImpl();
        }
    }
}
