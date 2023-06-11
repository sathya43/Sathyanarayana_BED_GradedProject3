package com.ticket.tracker.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ticket.tracker.entity.Attachment;
import com.ticket.tracker.entity.Ticket;
import com.ticket.tracker.service.AttachmentService;
import com.ticket.tracker.service.TicketService;

@Controller
@RequestMapping("/")
public class TicketTrackerController {
	
	private static final long MAX_FILE_SIZE = 1048576L;

	@Autowired
	TicketService ticketService;
	
	@Autowired
	AttachmentService attachmentService;
	
	@GetMapping("/")
	public String getTickets(Model theModel) {
		theModel.addAttribute("allticketslist", ticketService.getAllTickets());
		return "index";
	}
	
	
	@GetMapping("/getTickets")
	public String getAllTickets(Model theModel) {
		theModel.addAttribute("allticketslist", ticketService.getAllTickets());
		return "index";
	}
	
	@PostMapping("/ticket")
	public String saveTicket(Ticket ticket, RedirectAttributes rd) {
		ticketService.saveTicket(ticket);
		
		rd.addFlashAttribute("message","Created");
		
		return "redirect:/ticket/view/" + ticket.getId();
	}
	
   @PostMapping("/ticket/update")
   public String updateTicket(Ticket ticket, RedirectAttributes rd) {
	   Ticket ticket1 = ticketService.getById(ticket.getId());
	   Attachment attachment = ticket1.getAttachment();
	   ticket.setAttachment(attachment);
	   ticketService.saveTicket(ticket);
	   rd.addFlashAttribute("message", "Updated");
	   return "redirect:/ticket/view/" + ticket.getId();
   }
	
	
	@GetMapping("/ticket/{id}")
	public String deleteTicket(@PathVariable(value = "id") Long id) {
		ticketService.deleteByID(id);
		return "redirect:/getTickets";
	}
	
	@GetMapping("/ticket/searchByNameorDescription")
	public String searchByNameorDescription(String keyword, Model theModel) {
        theModel.addAttribute("allticketslist", ticketService.findByKeyword(keyword));
        theModel.addAttribute("key", keyword);
		return "results";
	}
	
	
	@PostMapping("/upload/{id}")
	public String uploadFile(@RequestParam("myfile") MultipartFile multipartFile, @PathVariable Long id, RedirectAttributes rd) throws IOException {
	    try {
	        // Handle file size limit exceeded exception
	        if (multipartFile.getSize() > MAX_FILE_SIZE) {
	            throw new FileSizeLimitExceededException("File size limit exceeded. Maximum allowed file size is " + MAX_FILE_SIZE + " bytes.",multipartFile.getSize(),MAX_FILE_SIZE);
	        }

	        Ticket ticket = ticketService.getById(id);
	        String fileNameString = StringUtils.cleanPath(multipartFile.getOriginalFilename());

	        Attachment attachment = new Attachment();
	        attachment.setAttachementName(fileNameString);
	        attachment.setAttachement(multipartFile.getBytes());
	        attachment.setAttachmentSize(multipartFile.getSize());

	        ticket.setAttachment(attachment);
	        ticketService.saveTicket(ticket);

	        rd.addFlashAttribute("message", "Success");
	        return "redirect:/ticket/view/" + ticket.getId();
	    } catch (FileSizeLimitExceededException e) {
	        rd.addFlashAttribute("message", "Failed");
	        rd.addFlashAttribute("exceptionMessage", e.getMessage());
	        return "redirect:/ticket/view/" + id;
	    }
	}



	@GetMapping("/download/{id}")
	public String downloadFile(@PathVariable("id") Long id,HttpServletResponse response) throws IOException {
		
		Ticket ticket = ticketService.getById(id);
		Attachment attachment = ticket.getAttachment();
	   
		response.setContentType("application/octet-stream");
		String headerKeyString = "Content-Disposition";
		String headerValueString = "attachment; filename=" + attachment.getAttachementName();
		response.setHeader(headerKeyString, headerValueString);
		
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(attachment.getAttachement());
		outputStream.close();
		
		return null;
	}
	

	
	
}
