package com.sockshop.sockshopcoursework3.model;

public enum SizeSocks {
    S("23-25"), M("25-27"), L("27-29"), XL("29-31");
    private final String size;

    SizeSocks(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
