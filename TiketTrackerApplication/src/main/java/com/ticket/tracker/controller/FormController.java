package com.ticket.tracker.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ticket.tracker.entity.Ticket;

import com.ticket.tracker.service.TicketService;

@Controller
@RequestMapping
public class FormController {

	@Autowired
	private TicketService ticketService;
	
	@GetMapping("/ticket/register")
	public String registerTicket(Model theModel) {
		Ticket ticket = new Ticket();
        theModel.addAttribute("ticket", ticket);
		return "newticket";
	}
	
	@GetMapping("/ticket/update/{id}")
	public String updateTicket(@PathVariable(value = "id") Long id, Model theModel) {
	    Ticket ticket = ticketService.getById(id);
	    System.out.println(ticket.toString());
	    theModel.addAttribute("ticket", ticket);
		return "updateticket";
	}
	
	@GetMapping("/ticket/view/{id}")
	public String getTicket(@PathVariable(value = "id") Long id, Model theModel ) {
	    Ticket ticket = ticketService.getById(id);
	    theModel.addAttribute("ticket", ticket);
		return "viewticket";
	}
}
