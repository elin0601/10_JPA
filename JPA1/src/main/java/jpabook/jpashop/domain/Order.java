package jpabook.jpashop.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
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
	
	@ManyToOne
	@JoinColumn(name = "member_id") // 매핑을 어떤 컬럼으로 할건지 지정하는 어노테이션(외래키 이름이 member_id가 됐다고 생각)
									// 연관 관계 주인
	private Member member; // 다대일 관계
	
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems = new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
	
	private LocalDateTime orderDate; // 주문 시간 (Hibernate가 알아서 자동 지원해줌)
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status; //주문 상태 [ORDER, CANCEL]

}
