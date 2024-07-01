package jpabook.jpashop.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Delivery {
	
	@Id
	@GeneratedValue
	@Column(name = "delivery_id")
	private Long id;
	
	@OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
	private Order order;
	
	@Embedded
	private Address address;
	
	@Enumerated(EnumType.STRING) // ORDINAL (default로 설정 되어 있음) : 컬럼이 1, 2, 3, 4 숫자로 들어감 (중간에 READY / COMP 말고 다른 상태가 들어가면 순서에 의해 큰일남)
								  // STRING
	private DeliveryStatus status; // READY / COMP

}
