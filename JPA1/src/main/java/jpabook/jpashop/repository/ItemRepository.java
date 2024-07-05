package jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

	private final EntityManager em;
	
	public void save(Item item) {
		if(item.getId()==null) {
			em.persist(item);
			
		}else {
			em.merge(item);
		}
	}
	
	// item 단일 조회
	public Item findOne(Long id) {
		return em.find(Item.class, id);
	}
	
	public List<Item> findAll(){
		return em.createQuery("select i from Item i", Item.class)
				.getResultList();
	}
}

/* item은 JPA에 저장하기 전까지 id 값이 없음(완전히 새로 호출 한다는 의미)
 * 여기서 save는 업데이트와 비슷한 기능을 함
 * */
