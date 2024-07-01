package jpabook.jpashop.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;

@Repository //spring bean 등록
public class MemberRepository {
	
	@PersistenceContext // spring이 생성한 EntityManager를 주입해 주는 어노테이션
	private EntityManager em; // spring이 엔티티 메니지를 만들어서 주입해줌
	
	public void save(Member member) {
		em.persist(member);
	}
	
	public Member findOne(Long id) {
		return em.find(Member.class, id);
	}
	
	public List<Member> findAll() {
		return em.createQuery("select m from Member m", Member.class).getResultList(); // SQL과의 차이 : sql은 테이블을 대상을 쿼리를 진행하고 jpql은 엔티티 객체를 이용해 쿼리는 진행함
	}
	
	public List<Member> findByName(String name){
		return em.createQuery("select m from Member m where m.name = :name", Member.class)
				.setParameter("name", name)
				.getResultList();
	}

}
