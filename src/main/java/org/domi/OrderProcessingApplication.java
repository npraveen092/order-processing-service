package org.domi;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
    info = @Info(
        title="Order Processing Service API",
        version = "1.0.0",
        description = "Microservice for processing orders from the order-success Kafka topic"
    )
)
public class OrderProcessingApplication extends Application {

}

