package com.labanana.exchange.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class OutputDTO {
	
	private List<TransactionDTO> transactions;
	private List<OrderDTO> orders;

}
