package org.domi.controller;

import io.quarkus.logging.Log;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/orderProcessing")
@Tag(name = "Order Processing Controller", description = "APIs for monitoring order processing")
public class OrderProcessingController {

    @GET
    @Path("/status/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get order processing status", description = "Retrieve the processing status of an order")
    @APIResponse(responseCode = "200", description = "Processing status retrieved successfully")
    public Response getOrderProcessingStatus(@PathParam("orderId") String orderId) {
        Log.info("Fetching processing status for orderId: " + orderId);

        // TODO: Implement status tracking by fetching from database or cache
        // For now, return a placeholder response
        return Response.ok()
                .entity("{\"orderId\": \"" + orderId + "\", \"status\": \"PROCESSING\"}")
                .build();
    }

    @GET
    @Path("/health")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Health check", description = "Check if the order processing service is running")
    @APIResponse(responseCode = "200", description = "Service is healthy")
    public Response healthCheck() {
        return Response.ok()
                .entity("{\"status\": \"UP\", \"service\": \"order-processing-service\"}")
                .build();
    }
}

