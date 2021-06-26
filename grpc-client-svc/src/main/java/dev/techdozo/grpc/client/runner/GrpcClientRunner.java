package dev.techdozo.grpc.client.runner;

import dev.techdozo.grpc.client.service.ProductService;
import dev.techdozo.product.api.Resources;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Slf4j
@Component
public class GrpcClientRunner implements ApplicationRunner {

  @Autowired private ExecutorService executorService;

  @Autowired private ProductService productService;

  @Value("${call_iteration}")
  private int iteration;

  @SneakyThrows
  @Override
  public void run(ApplicationArguments args) {
    long startTime = System.currentTimeMillis();
    log.info("Calling Server..");

    List<Future<Resources.GetProductResponse>> futures = new ArrayList<>();

    for (var i = 0; i < iteration; i++) {
      futures.add(executorService.submit(productService::getProduct));
    }

    for (Future<Resources.GetProductResponse> future : futures) {
      var productResponse = future.get();
      log.info("Received product, description {}", productResponse.getDescription());
    }

    log.info("Finished calling server, total time {} ", System.currentTimeMillis() - startTime);
  }
}
