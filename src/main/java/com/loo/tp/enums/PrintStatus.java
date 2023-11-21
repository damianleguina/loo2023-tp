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

    public static PrintStatus getNexStatus(PrintStatus status) {
        switch (status) {
            case PENDING:
                return PrintStatus.RECEIVED;
            case RECEIVED:
                return PrintStatus.IN_PROGRESS;
            case IN_PROGRESS:
                return PrintStatus.FINISHED;
            case FINISHED:
                return PrintStatus.DELIVERED;
            default:
                return null;
        }
    }
}
