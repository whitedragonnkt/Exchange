package com.labanana.exchange.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.labanana.exchange.dto.InputDTO;
import com.labanana.exchange.dto.OrderDTO;
import com.labanana.exchange.dto.OutputDTO;
import com.labanana.exchange.dto.TransactionDTO;
import com.labanana.exchange.dto.converter.OrderDTOConverter;
import com.labanana.exchange.entity.Order;
import com.labanana.exchange.service.OrderServicio;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {
	
	private final OrderServicio orderServicio;
	
	@PostMapping("/orders")
	public ResponseEntity<?> input(@RequestBody InputDTO inputDTO) {
		
		List<Order> ordenes = OrderDTOConverter.convertOrderDtoToOrder(inputDTO.getOrders());
		
		Boolean existeOrden = orderServicio.verificarOrdenes(ordenes);
		if (existeOrden) {
			return ResponseEntity.ok("Alguna orden ya existe");
		}
		
		orderServicio.ejecutarOrdenes(ordenes);
		
		List<Long> ids = OrderDTOConverter.getIDs(ordenes);
		
		List<Order> ordenesGrabadas = orderServicio.obtenerOrdenesPorID(ids);
		
		List<OrderDTO> ordenesIncompletas = ordenesGrabadas.stream()
								.filter(o -> o.getRemaining()>0f)
								.map(OrderDTOConverter::convertOrderToOrderDto)
								.collect(Collectors.toList());
		
		List<TransactionDTO> transaccionesDTO = ordenesGrabadas.stream()
										.map(o -> o.getTransactions())
										.flatMap(txs -> txs.stream())
										.map(OrderDTOConverter::getOrderIDByTransaction)
										.map(OrderDTOConverter::convertToTransactionDTO)
										.collect(Collectors.toList());				
		
		OutputDTO outputDTO = new OutputDTO();
		outputDTO.setTransactions(transaccionesDTO);
		outputDTO.setOrders(ordenesIncompletas);
		
		return ResponseEntity.ok(outputDTO);	
	}

}
