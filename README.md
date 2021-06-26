## Building Docker image
To build library - run command `gradlew clean build`

### gRPC client
To build Docker image - run command `docker build . -t techdozo/grpc-lb-client:1.0.0` from grpc-client-svc
To push Docker image - `docker push techdozo/grpc-lb-client:1.0.0`

### gRPC Server
To build Docker image - run command `docker build . -t techdozo/grpc-lb-server:1.0.0` from grpc-server-svc
To push Docker image - `docker push techdozo/grpc-lb-server:1.0.0`
