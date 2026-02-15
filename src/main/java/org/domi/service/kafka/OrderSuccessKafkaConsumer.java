package org.domi.service.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.domi.model.OrderSuccessEvent;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderSuccessKafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Consumes order success events from Kafka topic "order-success"
     * Processes the order for payment, notifications, fulfillment, etc.
     * @param orderEventJson the order event as JSON string from Kafka
     */
    @Incoming("order-success")
    public void consumeOrderSuccessEvent(String orderEventJson) {
        try {
            OrderSuccessEvent orderEvent = objectMapper.readValue(orderEventJson, OrderSuccessEvent.class);
            Log.info("Received order success event: " + orderEvent);

            // Process the order
            processOrder(orderEvent);

            Log.info("Successfully processed order for orderId: " + orderEvent.getOrderId());
        } catch (Exception e) {
            Log.error("Error consuming order success event: ", e);
        }
    }

    /**
     * Main order processing logic
     * This can be extended to include:
     * - Payment processing
     * - Inventory management
     * - Email notifications
     * - Shipment tracking
     * - etc.
     */
    private void processOrder(OrderSuccessEvent orderEvent) {
        Log.info("=== Starting Order Processing ===");
        Log.info("Order ID: " + orderEvent.getOrderId());
        Log.info("Customer ID: " + orderEvent.getCustomerID());
        Log.info("Product: " + orderEvent.getProduct());
        Log.info("Price: $" + orderEvent.getPrice());
        Log.info("Database ID: " + orderEvent.getOrderDatabaseId());

        try {
            // Step 1: Process payment
            processPayment(orderEvent);

            // Step 2: Update inventory
            updateInventory(orderEvent);

            // Step 3: Send confirmation email
            sendConfirmationEmail(orderEvent);

            // Step 4: Create shipment
            createShipment(orderEvent);

            Log.info("=== Order Processing Completed Successfully ===");
        } catch (Exception e) {
            Log.error("Error during order processing for orderId: " + orderEvent.getOrderId(), e);
            // TODO: Send failure notification, retry logic, etc.
        }
    }

    private void processPayment(OrderSuccessEvent orderEvent) {
        Log.info("Processing payment for orderId: " + orderEvent.getOrderId());
        // TODO: Call payment gateway
        // TODO: Handle payment success/failure
        Log.info("Payment processed successfully");
    }

    private void updateInventory(OrderSuccessEvent orderEvent) {
        Log.info("Updating inventory for product: " + orderEvent.getProduct());
        // TODO: Update inventory in inventory service
        // TODO: Handle out of stock scenarios
        Log.info("Inventory updated");
    }

    private void sendConfirmationEmail(OrderSuccessEvent orderEvent) {
        Log.info("Sending confirmation email to customer: " + orderEvent.getCustomerID());
        // TODO: Send email via email service
        // TODO: Include order details, tracking number, etc.
        Log.info("Confirmation email sent");
    }

    private void createShipment(OrderSuccessEvent orderEvent) {
        Log.info("Creating shipment for orderId: " + orderEvent.getOrderId());
        // TODO: Create shipment in logistics system
        // TODO: Generate tracking number
        Log.info("Shipment created");
    }
}

