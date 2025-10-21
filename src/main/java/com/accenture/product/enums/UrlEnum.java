package com.accenture.product.enums;

public enum UrlEnum {

    PRODUCTS_URL("http://localhost:8080/product"),
    PRODUCT_FORMAT("%s?code=%s");
    private String url;

    UrlEnum(String url) {
        this.url = url;
    }

    public String value() {
        return url;
    }
}
