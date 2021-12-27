package com.proofit.busdemo.controllers;

import java.util.ArrayList;
import java.util.Map;

import com.proofit.busdemo.models.BusTicket;
import com.proofit.busdemo.models.BusTicketPrice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BusTicketController {
    private static final int BASE_PRICE = 10;
	private static final int VAT = 21;

	@GetMapping("/base_price")
	public String basePrice(@RequestParam(value = "terminal", defaultValue = "Vilnius") String terminal) {
		return String.format("Base price to terminal %1$s: %2$d EUR", terminal, BASE_PRICE);
	}

	@GetMapping("/vat")
	public String vat() {
		return String.format("VAT: %s", VAT);
	}

	@RequestMapping(value = "/price", method = RequestMethod.POST)
	public BusTicketPrice process(@RequestBody Map<String, Object>[] payload) throws Exception {
		ArrayList<BusTicket> busTickets = new ArrayList<>();
		for (int i = 0; i < payload.length; i++) {
			boolean child;
			int bags;

			try {
				child = (boolean) payload[i].get("child");
			} catch (Exception e) {
				child = false;
			}

			try {
				bags = (int) payload[i].get("bags");
			} catch (Exception e) {
				bags = 0;
			}
			busTickets.add(
				new BusTicket(
					child, 
					bags, 
					BASE_PRICE, 
					VAT
				)
			);
		}

		return new BusTicketPrice(busTickets);
	}

}
