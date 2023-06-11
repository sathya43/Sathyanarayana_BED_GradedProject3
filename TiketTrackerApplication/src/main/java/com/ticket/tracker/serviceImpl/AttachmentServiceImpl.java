package com.ticket.tracker.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ticket.tracker.entity.Attachment;
import com.ticket.tracker.entity.Ticket;
import com.ticket.tracker.repository.AttachmentRepository;
import com.ticket.tracker.service.AttachmentService;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Override
	public Attachment getById(Long id) {
		Optional<Attachment> optional =  attachmentRepository.findById(id);
		Attachment attachment = null;
		if(optional.isPresent()) {
			attachment = optional.get();
		}else {
			throw new RuntimeException("Ticket is not found with id: " + id);
		}
		return attachment;
	}
	
	

}
