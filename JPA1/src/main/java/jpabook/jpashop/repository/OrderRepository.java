package jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	
	// order 단건 조회
	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}
	
	public List<Order> findAll(OrderSearch orderSearch) {
	    return em.createQuery("select o from Order o join o.member m " + 
	                          "where o.status = :status " + 
	                          "and m.name like :name", Order.class)
	            .setParameter("status", orderSearch.getOrderStatus())
	            .setParameter("name", "%" + orderSearch.getMemberName() + "%")
	            .setMaxResults(1000) // 최대 1000건
	            .getResultList();
	}
	
	
	public List<Order> findOrderWithMemberDelivery(){
		return em.createQuery("select o from Order o" +
				 				" join fetch o.member m" +
				 				" join fetch o.delivery d", Order.class)
				.getResultList();
	}
	

	public List<Order> findAllWithMemberDelivery(int offset, int limit) {
		return em.createQuery(
				"select o from Order o" + 
				" join fetch o.member m" + 
				" join fetch o.delivery d", Order.class)
			.setFirstResult(offset)
			.setMaxResults(limit)
			.getResultList();
	}

	
	public List<Order> findAllWithItem() {
		return em.createQuery(
				"select distinct o from Order o" + 
				" join fetch o.member m" + 
				" join fetch o.delivery d" +
				" join fecth o.orderItems oi" + 
				" join fetch oi.item", Order.class)
			.getResultList();
	}

	
	
}