package dev.techdozo.grpc.client.service;

import dev.techdozo.product.api.ProductServiceGrpc;
import dev.techdozo.product.api.Resources;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ProductService {

  private final ProductServiceGrpc.ProductServiceBlockingStub productApiServiceBlockingStub;

  public ProductService(
      ProductServiceGrpc.ProductServiceBlockingStub productApiServiceBlockingStub) {
    this.productApiServiceBlockingStub = productApiServiceBlockingStub;
  }

  public Resources.GetProductResponse getProduct() {
    var uuid = UUID.randomUUID();

    log.info("Calling Product Service, id {}", uuid);

    var productRequest =
        Resources.GetProductRequest.newBuilder().setProductId(uuid.toString()).build();

    return productApiServiceBlockingStub.getProduct(productRequest);
  }
}
