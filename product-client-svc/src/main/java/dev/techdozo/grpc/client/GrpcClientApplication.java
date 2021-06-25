package dev.techdozo.grpc.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class GrpcClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(GrpcClientApplication.class, args);
  }
}
