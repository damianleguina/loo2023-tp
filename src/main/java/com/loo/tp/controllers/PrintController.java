package com.loo.tp.controllers;

import org.javatuples.Triplet;

import com.loo.tp.entities.Print;
import com.loo.tp.enums.PrintStatus;
import com.loo.tp.repository.interfaces.PrintRepository;
import com.loo.tp.repository.interfaces.UserRepository;
import com.loo.tp.session.SessionManager;
import com.loo.tp.utils.ArrayUtils;

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
        if (!this.isAdmin() && this.getCurrentUserId() != userId) {
            return Error("Usuario no es administrador");
        }
        var prints = printRepository.getByUserId(userId);

        for (int i = 0; i < prints.length; i++) {
            prints[i].setUser(userRepository.getById(prints[i].getUserId()));
        }
        return Ok(prints);
    }

    public Triplet<Boolean, Print, String> getById(long printId) {
        var print = printRepository.getById(printId);
        if (print == null) {
            return Error("Trabajo no encontrado.");
        }
        if (print.getUserId() != this.getCurrentUserId() && !this.isAdmin()) {
            return Error("Usuario no es administrador");
        }
        print.setUser(userRepository.getById(print.getUserId()));
        return Ok(print);
    }

    public Triplet<Boolean, Print, String> addPrint(Print newPrint) {
        var user = userRepository.getById(newPrint.getUserId());
        if (user == null) {
            return Error("Usuario inválido");
        }

        var print = printRepository.add(newPrint);
        return print != null
                ? Ok(print)
                : Error("Error al agregar trabajo.");
    }

    public Triplet<Boolean, Long, String> deletePrints(long[] printIds) {
        long result = 0;
        
        var prints = this.printRepository.getByUserId(this.getCurrentUserId());

        // Do not delete if one of the selected prints has started (Status different from pending).
        for (Print print : prints) {
            if (ArrayUtils.contains(printIds, print.getId()) && print.getStatus() != PrintStatus.PENDING) {
                return Error("Uno de los trabajos ya ha comenzado el proceso de impresión.");
            }
        }
        for (Print print : prints) {
            if (ArrayUtils.contains(printIds, print.getId())) {
                printRepository.delete(print.getId());
                result++;
            }
        }
        return Ok(result);
    }

}
