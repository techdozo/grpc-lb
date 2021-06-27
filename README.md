# Overview
gRPC load balancing code example. You can read full article at [gRPC load balancing on Kubernetes (using Headless Service)][1]

## Building Docker image
To build library - run command `gradlew clean build`

### gRPC client
To build Docker image - run command `docker build . -t techdozo/grpc-lb-client:1.0.0` from grpc-client-svc
To push Docker image - `docker push techdozo/grpc-lb-client:1.0.0`

### gRPC Server
To build Docker image - run command `docker build . -t techdozo/grpc-lb-server:1.0.0` from grpc-server-svc
To push Docker image - `docker push techdozo/grpc-lb-server:1.0.0`

### Kubernetes
The folder `/kubernetes` has YAML file for creating Kubernetes deployments and service as described in the article.
- no-lb : has example for creating Kubernetes object which does not load balances requests
- round-robin: example of client side load balancing

[1]: https://techdozo.dev/2021/grpc-load-balancing-on-kubernetes-using-headless-service/