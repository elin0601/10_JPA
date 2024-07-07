package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders") // 테이블 이름을 orders로 설정
@Getter
@Setter
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "order_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id") // 매핑을 어떤 컬럼으로 할건지 지정하는 어노테이션(외래키 이름이 member_id가 됐다고 생각)
									// 연관 관계 주인
	private Member member; // 다대일 관계
	
	// JPQL select o From irder o; -> SQL select * from order; 로 해석됨
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // cascade = CascadeType.ALL : orderItems 에다가 데이터를 저장하면
	private List<OrderItem> orderItems = new ArrayList<>();
	
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //delivery 값만 (개체만) 세팅해 놓으면 order를 저장할 때 delivery도 persist 해줌
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	// order_date
	private LocalDateTime orderDate; // 주문 시간 (Hibernate가 알아서 자동 지원해줌)
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //주문 상태 [ORDER, CANCEL]
	
	// ==== 연관 관계 편의 메서드(양방향 연관관계 세팅) =====
	public void Member(Member member) {
		this.member = member;
		member.getOrders().add(this); 
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	// === 생성 메서드 === //
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		Order order = new Order();
		order.setMember(member);
		order.setDelivery(delivery);
		
		for(OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		order.setStatus(OrderStatus.ORDER);
		order.setOrderDate(LocalDateTime.now());
		return order;
	}
	
	
	// === 비즈니스 로직 === //
	
	/**
	 * 주문 취소
	 */
	public void cancel() {
		if (delivery.getStatus() == DeliveryStatus.COMP) {
			throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
		}
		
		this.setStatus(OrderStatus.CANCEL);
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}
	
	
	// === 조회 로직 === //
	
	/**
	 * 전체 주문 가격 조회
	 */
	public int getTotalPrice() {
		
		int totalPrice = 0;
		
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		return totalPrice;
		
//		return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
	}
	
	
	
	
}