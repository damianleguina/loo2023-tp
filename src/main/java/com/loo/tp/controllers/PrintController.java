package com.loo.tp.controllers;

import org.javatuples.Pair;
import org.javatuples.Triplet;

import com.loo.tp.entities.Print;
import com.loo.tp.repository.interfaces.PrintRepository;
import com.loo.tp.repository.interfaces.UserRepository;
import com.loo.tp.session.SessionManager;

public class PrintController extends BaseController {
    private PrintRepository printRepository;
    private UserRepository userRepository;

    public PrintController(
            SessionManager sessionManager,
            PrintRepository printRepository,
            UserRepository userRepository) {
        super(sessionManager);
        this.printRepository = printRepository;
        this.userRepository = userRepository;
    }

    public Triplet<Boolean, Print[], String> get() {
        if (!this.isAdmin()) {
            return Error("Usuario no es administrador");
        }
        var prints = printRepository.get();
        for (int i = 0; i < prints.length; i++) {
            prints[i].setUser(userRepository.getById(prints[i].getUserId()));
        }
        return Ok(prints);
    }

    public Triplet<Boolean, Print[], String> get(long userId) {
        if (!this.isAdmin()) {
            return Error("Usuario no es administrador");
        }
        var prints = printRepository.getByUserId(userId);

        // for (int i = 0; i < prints.length; i++) {
        //     prints[i].setUser(userRepository.getById(prints[i].getUserId()));
        // }
        return Ok(prints);
    }

    public Triplet<Boolean, Print, String> getById(long printId) {
        if (!this.isAdmin()) {
            return Error("Usuario no es administrador");
        }
        var print = printRepository.getById(printId);
        print.setUser(userRepository.getById(print.getUserId()));
        return print != null
            ? Ok(print)
            : Error(null);
    }

}
