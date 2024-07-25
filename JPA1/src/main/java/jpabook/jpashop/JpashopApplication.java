package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;


@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

    @Bean
    Hibernate5JakartaModule hibernate5Module() {	
        return new Hibernate5JakartaModule();
    }
    
	/* 다음과 같이 설정하면 강제로 지연 로딩 가능
	 * @Bean 
	 * Hibernate5Module hibernate5Module() { 
	 * 	Hibernate5Module hibernate5Module = new Hibernate5Module(); // 강제 지연 로딩 설정
	 * 	hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, true); 
	 * 	return hibernate5Module; 
	 * }
	 */
}
