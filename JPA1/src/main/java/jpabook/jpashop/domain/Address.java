package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable // 어딘가에 내장되어 있다	
@Getter
@Setter
public class Address {
	
	private String city;
	private String street;
	private String zipcode;

}
