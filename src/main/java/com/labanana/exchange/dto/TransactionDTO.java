package com.labanana.exchange.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
public class TransactionDTO {
	
	private Set<Long> orders;

}
