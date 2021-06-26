package dev.techdozo.grpc.client.service;

import dev.techdozo.product.api.ProductServiceGrpc;
import dev.techdozo.product.api.Resources;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ProductService {

  private final ManagedChannel managedChannel;

  public ProductService(ManagedChannel managedChannel) {
    this.managedChannel = managedChannel;
  }

  public Resources.GetProductResponse getProduct() {
    ProductServiceGrpc.ProductServiceBlockingStub productApiServiceBlockingStub =
        ProductServiceGrpc.newBlockingStub(managedChannel);

    var uuid = UUID.randomUUID();


    log.info("Calling Server, host {},  id {}", managedChannel, uuid);

    var productRequest =
        Resources.GetProductRequest.newBuilder().setProductId(uuid.toString()).build();

    Resources.GetProductResponse product = null;

    try {
      product = productApiServiceBlockingStub.getProduct(productRequest);
    } catch (StatusRuntimeException cause) {
      log.error("{}",cause.getStatus());
    }

    return product;
  }
}
