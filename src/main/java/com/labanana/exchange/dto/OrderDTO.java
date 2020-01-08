package com.labanana.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString 
@NoArgsConstructor 
@AllArgsConstructor
public class OrderDTO {

	private Long id;
	private String user;
	private String type;
	private Float size;
	private Float value;
	private Float remaining;
	
}
