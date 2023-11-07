package com.loo.tp.enums;

public enum PrintQuality {
    BLACK_AND_WHITE("Blanco y Negro"),
    COLOR("Color");

    private final String text;

    PrintQuality(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
