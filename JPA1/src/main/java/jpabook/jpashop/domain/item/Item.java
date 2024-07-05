package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

// 상속
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // JOINED (가장 정교화된 방법)
												// SINGLE_TABLE (하나의 테이블에 합쳐 놓은 방법)
												// TABLE_PER_CLASS (같은 패키지 내에 있는 클래스만 나오는 전략)

/* 상속 계층 구조의 상위 클래스에 사용되며, 판별 컬럼(discriminator column)을 정의 */
@DiscriminatorColumn(name = "dtype") // JPA(Java Persistence API)에서 상속 관계를 가진 여러 클래스를 하나의 데이터베이스 테이블에 매핑할 때 사용
									 // 데이터베이스 테이블에 저장될 판별 값(discriminator value)을 나타내는 컬럼의 이름을 지정
@Entity
@Getter
@Setter
// 추상 클래스
public abstract class Item {
	
	@Id
	@GeneratedValue
	@Column(name = "item_id")
	private Long id;
	
	private String name;
	private int price;
	private int stockQuantity; // 재고 수량
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<>();
	
	
	// ==== 비즈니스 로직 ==== //
	
	/** 재고 수량 증가
	 * stock 즡가
	 * @param quatity
	 */
	public void addStock(int quatity) {
		this.stockQuantity += quatity; 
	}
	
	/** stock 감소
	 * @param quatity
	 */
	public void removeStock(int quatity) {
		int restStock = this.stockQuantity - quatity;
		if(restStock < 0) {
			throw new NotEnoughStockException("need more stock");
		}
		this.stockQuantity = restStock;
	}

}
