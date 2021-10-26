package br.com.letscode.java.atividaderestcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AtividadeRestControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtividadeRestControllerApplication.class, args);
    }

}
