package com.ticket.tracker.service;

import java.util.List;


import com.ticket.tracker.entity.Ticket;

public interface TicketService {

	List<Ticket> getAllTickets();
    Ticket saveTicket(Ticket employee);
    Ticket  getById(Long id);
    boolean deleteByID(Long id);
    List<Ticket> findByKeyword(String keyword);
}