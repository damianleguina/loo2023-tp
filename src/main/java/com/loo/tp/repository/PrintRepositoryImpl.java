package com.loo.tp.repository;

import java.time.Instant;

import com.loo.tp.entities.Print;
import com.loo.tp.enums.PrintQuality;
import com.loo.tp.enums.PrintStatus;
import com.loo.tp.repository.interfaces.PrintRepository;

public class PrintRepositoryImpl extends BaseRepository<Print> implements PrintRepository {
    public PrintRepositoryImpl() {
        super(populate());
    }

    @Override
    public Print[] get() {
        int aux = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                aux++;
            }
        }
        Print[] result = new Print[aux];
        aux = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                result[aux] = data[i];
                aux++;
            }
        }
        return result;
    }

    public Print[] getByUserId(long userId) {
        int aux = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null && data[i].getUserId() == userId) {
                aux++;
            }
        }
        Print[] result = new Print[aux];
        aux = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null && data[i].getUserId() == userId) {
                result[aux] = data[i];
                aux++;
            }
        }
        return result;
    }


    private static Print[] populate() {
        var data = new Print[20];
        data[0] = new Print(0, 2, PrintQuality.COLOR, 2, PrintStatus.IN_PROGRESS, Instant.now(), null, null);
        data[2] = new Print(0, 3, PrintQuality.BLACK_AND_WHITE, 1, PrintStatus.RECEIVED, Instant.now(), null, null);
        data[3] = new Print(0, 2, PrintQuality.COLOR, 2, PrintStatus.PENDING, null, null, null);
        return data;
    }
}
