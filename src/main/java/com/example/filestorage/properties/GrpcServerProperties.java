package com.example.filestorage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("grpc.server")
public class GrpcServerProperties {

    private Integer port;
    private Integer maxInboundMessageSize;

}
