package com.loo.tp.enums;

public enum PrintStatus {
    PENDING("Pendiente"),
    RECEIVED("Recibido"),
    IN_PROGRESS("En progreso"),
    FINISHED("Finalizado"),
    DELIVERED("Entregado");

    private final String text;

    PrintStatus(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static PrintStatus findByValue(String value) {
        for (PrintStatus printStatus : values()) {
            if (printStatus.toString().equals(value)) {
                return printStatus;
            }
        }
        return null;
    }
}
