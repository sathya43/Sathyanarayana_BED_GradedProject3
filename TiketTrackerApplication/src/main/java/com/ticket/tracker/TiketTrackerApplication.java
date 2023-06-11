package com.ticket.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ticket.tracker.entity.Attachment;
import com.ticket.tracker.entity.Ticket;
import com.ticket.tracker.repository.AttachmentRepository;
import com.ticket.tracker.service.TicketService;

@SpringBootApplication
public class TiketTrackerApplication implements CommandLineRunner{
	
	@Autowired
	TicketService ticketService;
	
	@Autowired
	AttachmentRepository attachmentRepository;

	public static void main(String[] args)  {
		SpringApplication.run(TiketTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	  Ticket ticket = new Ticket();
	  ticket.setShortDescription("TestTicket");
	//  ticket.setContent("My ticket to test create Date");
	  ticket.setTicketName("SathyaTicket");
	  ticketService.saveTicket(ticket);
	  System.out.println("The ticket object" + ticket.toString());
	  
	  Attachment attachment = new Attachment();
	  
	  attachment.setAttachementName("file1");
	  attachment.setAttachmentSize(1219L);
	  
	  ticket.setAttachment(attachment);
	  
	  ticketService.saveTicket(ticket);
	  
		
	}

}
