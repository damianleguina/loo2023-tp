package com.loo.tp.enums;

public enum PrintStatus {
    PENDING("Pendiente"),
    RECEIVED("Recibido"),
    IN_PROGRESS("En progreso"),
    FINISHED("Finalizado"),
    DELIVERED("Entregado");

    private final String text;

    /**
     * @param text
     */
    PrintStatus(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

}
