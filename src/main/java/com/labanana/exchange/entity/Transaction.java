package com.labanana.exchange.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "TRANSACCION")
public class Transaction {
	
	@Id 
	@GeneratedValue
	@Column(name="TRANSACCION_ID")
	private Long id;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="ORDEN_ID_EJECUTADO")
	private Order order;	
	
	@ElementCollection
	@CollectionTable(name = "TRANSACCION_ORDEN", joinColumns = @JoinColumn(name="TRANSACCION_ID"))
	@Column(name="ORDEN_ID")
	private List<Long> orderID = new ArrayList();
	
}
