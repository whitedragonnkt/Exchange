package com.labanana.exchange.entity;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.labanana.exchange.util.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(toBuilder = true)
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "ORDEN")
public class Order {
	
	@Id 
//	@GeneratedValue
	@Column(name="ORDEN_ID")
	private Long id;
	
	@Column(name="USUARIO")
	private String user;
	@Column(name="TIPO")
	private String type;
	@Column(name="CANTIDAD")
	private Float size;
	@Column(name="PRECIO")
	private Float value;
	@Column(name="RESTO")
	private Float remaining;
	@Column(name="ESTADO")
	private String status = Status.PENDIENTE.toString();
		
	@JsonManagedReference
	@EqualsAndHashCode.Exclude 
	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Transaction> transactions = new HashSet<>();
	
}
