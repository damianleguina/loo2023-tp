package com.loo.tp.controllers;

import org.javatuples.Pair;

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

    public Pair<Boolean, Print[]> get() {
        if (!this.isAdmin()) {
            return Error(null);
        }
        var prints = printRepository.get();
        for (int i = 0; i < prints.length; i++) {
            prints[i].setUser(userRepository.getById(prints[i].getUserId()));
        }
        return Ok(prints);
    }

    public Pair<Boolean, Print[]> get(long userId) {
        if (!this.isAdmin()) {
            return Error(null);
        }
        var prints = printRepository.getByUserId(userId);

        // for (int i = 0; i < prints.length; i++) {
        //     prints[i].setUser(userRepository.getById(prints[i].getUserId()));
        // }
        return Ok(prints);
    }

    public Pair<Boolean, Print> getById(long printId) {
        if (!this.isAdmin()) {
            return Error(null);
        }
        var print = printRepository.getById(printId);
        print.setUser(userRepository.getById(print.getUserId()));
        return print != null
            ? Ok(print)
            : Error(null);
    }

}
