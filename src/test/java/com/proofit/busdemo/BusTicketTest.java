package com.proofit.busdemo;

import com.proofit.busdemo.models.BusTicket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BusTicketTest {
    
    @Test
    public void testGetTicketPrice() {
        BusTicket busTicket1 = new BusTicket(false, 0, 10, 21);
        Assertions.assertEquals(busTicket1.getTicketPrice(), "12.10"); 

        BusTicket busTicket2 = new BusTicket(false, 1, 10, 21);
        Assertions.assertEquals(busTicket2.getTicketPrice(), "12.10"); 

        BusTicket busTicket3 = new BusTicket(true, 0, 10, 21);
        Assertions.assertEquals(busTicket3.getTicketPrice(), "6.05"); 

        BusTicket busTicket4 = new BusTicket(true, 1, 10, 21);
        Assertions.assertEquals(busTicket4.getTicketPrice(), "6.05"); 

        BusTicket busTicket5 = new BusTicket(false, 0, 15, 20);
        Assertions.assertEquals(busTicket5.getTicketPrice(), "18.00");
    }

    @Test
    public void testLuggagePrice() {
        BusTicket busTicket1 = new BusTicket(false, 0, 10, 21);
        Assertions.assertEquals(busTicket1.getLuggagePrice(), "0.00"); 

        BusTicket busTicket2 = new BusTicket(false, 1, 10, 21);
        Assertions.assertEquals(busTicket2.getLuggagePrice(), "3.63"); 

        BusTicket busTicket3 = new BusTicket(false, 2, 10, 21);
        Assertions.assertEquals(busTicket3.getLuggagePrice(), "7.26"); 

        BusTicket busTicket4 = new BusTicket(true, 0, 10, 21);
        Assertions.assertEquals(busTicket4.getLuggagePrice(), "0.00"); 

        BusTicket busTicket5 = new BusTicket(true, 1, 10, 21);
        Assertions.assertEquals(busTicket5.getLuggagePrice(), "3.63");

        BusTicket busTicket6 = new BusTicket(true, 2, 10, 21);
        Assertions.assertEquals(busTicket6.getLuggagePrice(), "7.26"); 

        BusTicket busTicket7 = new BusTicket(false, 1, 15, 20);
        Assertions.assertEquals(busTicket7.getLuggagePrice(), "5.40"); 
    }
}
