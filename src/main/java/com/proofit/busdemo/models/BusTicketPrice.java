package com.proofit.busdemo.models;

import java.util.ArrayList;

public class BusTicketPrice {
    private ArrayList<BusTicket> busTickets;

    public BusTicketPrice(ArrayList<BusTicket> busTickets) {
        this.busTickets = busTickets;
    }

    public String getTotalPrice() {
        double totalPrice = 0;
        for (int i = 0; i < busTickets.size(); i++) {
            totalPrice += Double.parseDouble(busTickets.get(i).getTicketPrice()) + Double.parseDouble(busTickets.get(i).getLuggagePrice());
        }
        return String.format("%.2f", totalPrice);
    }

    public ArrayList<BusTicket> getTickets() {
        return busTickets;
    }
}
