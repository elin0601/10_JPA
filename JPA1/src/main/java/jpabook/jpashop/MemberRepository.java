package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;

@Repository
public class MemberRepository {
	
	// 스프링이 알아서 EntityManager을 주입해주고 관리해주는 어노테이션
	@PersistenceContext
	private EntityManager em;
	
	// 회원 저장
	public Long save(Member member) {
		em.persist(member);
		return member.getId();
	}
	
	// id 조회
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
}
