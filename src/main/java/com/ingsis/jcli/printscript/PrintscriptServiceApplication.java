package com.ingsis.jcli.printscript;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PrintscriptServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(PrintscriptServiceApplication.class, args);
  }
}
