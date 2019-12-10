package com.cryptodev.cryptopedia.PriceBackEnd;

public class Price {
    private String name;
    private String Symbol;
    private double latest_price;

    public Price(String name, String symbol, double latest_price) {
        this.name = name;
        Symbol = symbol;
        this.latest_price = latest_price;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return Symbol;
    }

    public double getLatest_price() {
        return latest_price;
    }
}
