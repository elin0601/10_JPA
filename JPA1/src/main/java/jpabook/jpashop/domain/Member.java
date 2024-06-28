package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue // 시퀀스 값 사용
	@Column(name = "member_id") // PK 컬럼 지정
	private Long id;
	private String name;
	
	@Embedded // 인베디드 내장 타입을 포함 했다는걸 알려주는 어노테이션
	private Address address;
	
	@OneToMany(mappedBy = "member") // order 테이블에 있는 member 테이블에 의해 매핑 되었다는 걸 알 수 있음(그냥 매핑된 거울 모드.. 읽기 전용)
	private List<Order> orders = new ArrayList<>(); // 1대다 관계
	
}
