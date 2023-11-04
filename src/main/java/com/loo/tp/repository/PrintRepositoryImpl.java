package com.loo.tp.repository;

import com.loo.tp.entities.Print;
import com.loo.tp.repository.interfaces.PrintRepository;

public class PrintRepositoryImpl extends BaseRepository<Print> implements PrintRepository {
    public PrintRepositoryImpl() {
        super(new Print[20]);
    }    
}
