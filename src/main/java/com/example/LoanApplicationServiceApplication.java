//package com.example;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class LoanApplicationServiceApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(LoanApplicationServiceApplication.class, args);
//	}
//
//}


//package com.example;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.client.RestTemplate;
//
//@SpringBootApplication
//public class LoanApplicationServiceApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(LoanApplicationServiceApplication.class, args);
//    }
//
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//}

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LoanApplicationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanApplicationServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}



