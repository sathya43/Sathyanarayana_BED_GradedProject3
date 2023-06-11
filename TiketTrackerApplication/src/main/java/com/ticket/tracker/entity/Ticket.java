package com.ticket.tracker.entity;


import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ticketName;
	
	private String shortDescription;
	
    @CreatedDate
	private LocalDate createDate;
	
    @Lob
	private String content;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment")
    private Attachment attachment;
	
    
	@PrePersist
	private void timestamp() {
		this.setCreateDate(java.time.LocalDate.now());
	}

	
}
