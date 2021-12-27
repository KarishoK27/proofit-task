package com.proofit.busdemo.models;

public class BusTicket {
    private boolean child;
    private int bags;
    private int base;
    private int vat;

    public BusTicket(boolean child, int bags, int base, int vat) {
        this.child = child;
        this.bags = bags;
        this.base = base;
        this.vat = vat;
    }

    public boolean isChild() {
        return child;
    }

    public int getBags() {
        return bags;
    }

    public String getTicketPrice() {
        if (child) {
            return String.format("%.2f", base * 0.5 + (base * 0.5 * vat / 100.0));
        }
        return String.format("%.2f", base + (base * vat / 100.0));
    }

    public String getLuggagePrice() {
        return String.format("%.2f", bags * base * 0.3 + (bags * base * 0.3 * vat / 100.0));
    }
    
}
