/*
package jpabook.jpashop;

import static org.junit.Assert.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MemberRepositoryTest {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Test
	@Transactional
	public void testMember() throws Exception {
		
		// given
		Member member = new Member();
		member.setUsername("memberA");
		
		// when
		Long saveId = memberRepository.save(member);
		Member findMember =  memberRepository.find(saveId);
		
		// then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member); // true 단순 비교 
	
	}

}
*/
