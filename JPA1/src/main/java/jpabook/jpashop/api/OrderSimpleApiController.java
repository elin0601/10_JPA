package jpabook.jpashop.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.Data;
import lombok.RequiredArgsConstructor;


/*
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	
	/**
	 * V1. 엔티티 직접 노출
	 * - Hibernate5Module 모듈 등록, LAZY=null 처리
	 * - 양방향 관계 문제 발생 -> @JsonIgnore
	 */
	@GetMapping("/api/v1/simple-orders")
	public List<Order> orderV1(){
		List<Order> all = orderRepository.findAll(new OrderSearch());
		
		for (Order order : all) {
			 order.getMember().getName(); //Lazy 강제 초기화
			 order.getDelivery().getAddress(); //Lazy 강제 초기환
			 }
		
		return all;
	}
	
	/**
	 * V2. 엔티티를 조회해서 DTO로 변환(fetch join 사용X)
	 * - 단점: 지연로딩으로 쿼리 N번 호출
	 */ 
	@GetMapping("/api/v2/simple-orders")
	public List<SimpleOrderDto> orderV2(){
		
		// ORDER 2개
		// N + 1 -> 1 + 회원 N +  N
		List<Order> orders = orderRepository.findAll(new OrderSearch());
		 
		List<SimpleOrderDto> result = orders.stream()
		 		.map(o -> new SimpleOrderDto(o))
		 		.collect(Collectors.toList());
		 
		return result;
	}
	
	
	/**
	 * V3. 엔티티를 조회해서 DTO로 변환(fetch join 사용O)
	 * - fetch join으로 쿼리 1번 호출
	 * 참고: fetch join에 대한 자세한 내용은 JPA 기본편 참고(정말 중요함)
	 */
	@GetMapping("/api/v3/simple-orders")
	public List<SimpleOrderDto> orderV3(){
	
		List<Order> orders = orderRepository.findOrderWithMemberDelivery();
		
		List<SimpleOrderDto> result = orders.stream()
				.map(o -> new SimpleOrderDto(o))
				.collect(Collectors.toList());
		
		return result;
	}
	
	
	@Data
	static class SimpleOrderDto{
		private Long orderId;
		private String name;
		private LocalDateTime orderDate;
		private OrderStatus orderStatus;
		private Address address;
		
		public SimpleOrderDto(Order order) {
			orderId = order.getId();
			name = order.getMember().getName();
			orderDate = order.getOrderDate();
			orderStatus = order.getStatus();
			address = order.getDelivery().getAddress();
		}
	}

}
