package com.loo.tp.controllers;

import org.javatuples.Pair;

public abstract class BaseController {
    protected <T> Pair<Boolean, T> Ok(T t) {
        return new Pair<Boolean, T>(true, t);
    }

    protected <T> Pair<Boolean, T> Error(T t) {
        return new Pair<Boolean, T>(false, t);
    }
}
