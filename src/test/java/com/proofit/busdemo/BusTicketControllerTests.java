package com.proofit.busdemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class BusTicketControllerTests extends BusDemoApplicationTests {
    
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testVat() throws Exception {
        mockMvc.perform(get("/vat")).andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("VAT: 21"));
    }


    @Test
    public void testBasePrice_default() throws Exception {
        mockMvc.perform(get("/base_price")).andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Base price to terminal Vilnius: 10 EUR"));
    }

    @Test
    public void testBasePrice_terminal() throws Exception {
        mockMvc.perform(get("/base_price").param("terminal", "Tallinn"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("text/plain;charset=UTF-8"))
        .andExpect(content().string("Base price to terminal Tallinn: 10 EUR"));
    }

    @Test
    public void testBusTicket_empty() throws Exception {
        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("0.00"));
    }

    @Test
    public void testBusTicket_oneAdultZeroBag() throws Exception {
        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"child\": false, \"bags\": 0}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("12.10"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());

        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"child\": false}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("12.10"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());

        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"bags\": 0}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("12.10"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());

        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("12.10"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());
    }

    @Test
    public void testBusTicket_oneAdultOneBag() throws Exception {
        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"child\": false, \"bags\": 1}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("15.73"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());

        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"bags\": 1}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("15.73"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());
    }

    @Test
    public void testBusTicket_oneAdultTwoBags_oneChildOneBag() throws Exception {
        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"child\": false, \"bags\": 2}, {\"child\": true, \"bags\": 1}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("29.04"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());

        mockMvc.perform(post("/price")
            .contentType(MediaType.APPLICATION_JSON)
            .content("[{\"bags\": 2}, {\"child\": true, \"bags\": 1}]"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.totalPrice").value("29.04"))
        .andExpect(jsonPath("$.tickets").exists())
        .andExpect(jsonPath("$.tickets").isArray());
    }


}
