package com.sockshop.sockshopcoursework3.model;

public enum ColorSocks {
    BLACK("Черный"), WHITE("Белый"), RED("Красный"), YELLOW("Желтый"), BLU("Синий");
    private final String color;

    ColorSocks(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
