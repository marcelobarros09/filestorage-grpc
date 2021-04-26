package com.example.filestorage;

import com.example.filestorage.grpc.endpoint.FileStorageEndpoint;
import com.example.filestorage.properties.GrpcServerProperties;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@AllArgsConstructor
@Log4j2
@SpringBootApplication
public class Application implements CommandLineRunner {

	private final GrpcServerProperties grpcServerProperties;
	private final FileStorageEndpoint fileStorageEndpoint;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private void startGrpcServer() throws IOException, InterruptedException {
		log.info(String.format("gRPC Server Starting Port: %s...", grpcServerProperties.getPort()));

		Server grpcServer = ServerBuilder
				.forPort(grpcServerProperties.getPort())
				.addService(fileStorageEndpoint)
				.maxInboundMessageSize(grpcServerProperties.getMaxInboundMessageSize())
				.build();

		grpcServer.start();

		log.info("gRPC Server Started.");

		grpcServer.awaitTermination();
	}

	@Override
	public void run(String... args) throws Exception {
		this.startGrpcServer();
	}
}
