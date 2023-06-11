package com.ticket.tracker.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.tracker.entity.Ticket;
import com.ticket.tracker.repository.TicketRepository;
import com.ticket.tracker.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Override
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	@Override
	public Ticket saveTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketRepository.save(ticket);
	}

	@Override
	public Ticket getById(Long id) {
		// TODO Auto-generated method stub
		Optional<Ticket> optional =  ticketRepository.findById(id);
		Ticket ticket = null;
		if(optional.isPresent()) {
			ticket = optional.get();
		}else {
			throw new RuntimeException("Ticket is not found with id: " + id);
		}
		return ticket;
	}

	@Override
	public boolean deleteByID(Long id) {
		Optional<Ticket> optional =  ticketRepository.findById(id);
		if(optional.isPresent()) {
			ticketRepository.deleteById(id);
			return true;
		}else {
			throw new RuntimeException("Ticket is not found with id: " + id);
		}
	}

	@Override
	public List<Ticket> findByKeyword(String keyword) {
	   return ticketRepository.findByKeyword(keyword);
	}


	
	

}
