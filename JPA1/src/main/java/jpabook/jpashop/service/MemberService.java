package jpabook.jpashop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true) // readOnly = true : DB한테 단순 읽기 전용이니 리소스를 너무 많이 쓰지 않게 해주는 역할
								// 그냥 @Transactional 이것만 사용해도 상관 없음
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// Setter Injection : 스프링이 바로 주입하는 것이 아니라 setter를 거쳐 주입해줌
	// 장점 : 테스트 코드 작성할 때 몫 같은걸 직접 주입할 수 있음
	// 단점 : 런타임 시점에 누군가가 변경하게 되면 치명적인 단점이 됨
//	public void setMembeRepository(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
	// 생성자 Injection 이 딱 한 개만 존재하는 경우 @Autowired를 작성하지 않아도 스프링이 알아서 Injection 해줌
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
	/**
	 * 회원 가입
	 */
	@Transactional
	public Long join(Member member) {
		
		// 중복 회원 검사
		validateDuplicateMember(member);
		memberRepository.save(member);
		return member.getId();
	}
	
	
	private void validateDuplicateMember(Member member) {
		
		// 예외처리
		
		List<Member> findMembers = memberRepository.findByName(member.getName());
		
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("아미 존재하는 회원입니다.");
		}
	}

	// 회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	// 회원 단일 조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
}
