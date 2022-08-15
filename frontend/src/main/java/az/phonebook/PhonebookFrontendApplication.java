package az.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PhonebookFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhonebookFrontendApplication.class, args);
    }

}
