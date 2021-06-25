package dev.techdozo.grpc.client;

import dev.techdozo.grpc.client.service.ProductService;
import dev.techdozo.product.api.ProductServiceGrpc;
import dev.techdozo.product.api.Resources;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Component
public class GrpcClientRunner implements ApplicationRunner {

  private final ExecutorService executorService;

  private final int iteration;

  private final ProductService productService;
  private final ManagedChannel managedChannel;

  public GrpcClientRunner(
      @Value("${server_host}") String serverHost,
      @Value("${server_port}") int serverPort,
      @Value("${call_iteration}") int iteration,
      @Value("${parallel_call}") int parallelCall) {
    this.iteration = iteration;
    this.managedChannel =
        ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();
    ProductServiceGrpc.ProductServiceBlockingStub productApiServiceBlockingStub =
        ProductServiceGrpc.newBlockingStub(managedChannel);
    this.productService = new ProductService(productApiServiceBlockingStub);
    this.executorService = Executors.newFixedThreadPool(parallelCall);
  }

  /**
   * Callback used to run the bean.
   *
   * @param args incoming application arguments
   */
  @Override
  public void run(ApplicationArguments args) {
    log.info("Calling Server..");
    long sTime = System.currentTimeMillis();

    parallelCalls();

    log.info(
        "Total Time taken to fetch {} products : {}",
        iteration,
        (System.currentTimeMillis() - sTime));
    log.info("Finished call ");
  }

  @SneakyThrows
  public void parallelCalls() {

    List<Future<Resources.GetProductResponse>> futures = new ArrayList<>();

    for (var i = 0; i < iteration; i++) {
      futures.add(executorService.submit(productService::getProduct));
    }

    for (Future<Resources.GetProductResponse> future : futures) {
      var productResponse = future.get();
      log.info("Received product, description {}", productResponse.getDescription());
    }
  }

  @PreDestroy
  public void destroy() {
    log.info("Destroy called...");
    this.executorService.shutdown();
    this.managedChannel.shutdown();
  }
}
