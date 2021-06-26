package dev.techdozo.grpc.client.config;

import dev.techdozo.grpc.client.service.ProductService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfig {

  @Value("${server_host}")
  private String serverHost;

  @Value("${num_thread}")
  private int numThread;

  @Bean
  public ManagedChannel managedChannel() {

    return ManagedChannelBuilder.forTarget(serverHost)
        .defaultLoadBalancingPolicy("round_robin")
        .usePlaintext()
        .build();
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(numThread);
  }

  @Bean
  public ProductService productService() {
    return new ProductService(managedChannel());
  }
}
