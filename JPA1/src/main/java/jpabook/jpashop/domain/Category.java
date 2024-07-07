package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
	
	@Id
	@GeneratedValue
	@Column(name = "category_id")
	private Long id;
	
	private String name;
	
	@ManyToMany // 다대다 관계
	
	@JoinTable(name = "catagory_item",// (== 조인테이블명)
			joinColumns = @JoinColumn(name = "category_id"), // 중간 테이블에 있는 category_id (== 외래키)
			inverseJoinColumns = @JoinColumn(name = "item_id")) // "catagory_item" 테이블의 item 으로 저장되는 번호 (== 반대 엔티티의 외래키)
	// 중간 테이블로 매핑을 해줘야 함 => 객체는 각각 컬렉션이 있어서 다대다 관계가 가능함
    // 							근데 관계형 DB는 컬렉션 관계를 양쪽이 가질 수 있는게 아니기
    // 							때문에 다대다 관계로 풀어내는 중간 테이블이 있어야 가능함
	private List<Item> items = new ArrayList<>();
	
	
	// 같은 엔티티 내부에서 연관 관계를 맺음 (양방향 관계)
	// 이름만 같지 다른 엔티티와 연관 관계라고 생각하면 됨
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> child = new ArrayList<>();	
	
	// ==== 연관관계 편의 메서드 ====
	public void addChildCategory(Category child) {
		this.child.add(child);
		child.setParent(this);
	}
	
}