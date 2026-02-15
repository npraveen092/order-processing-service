# Order Processing Service

A microservice that consumes order success events from Kafka and processes them for payment, notifications, fulfillment, and other downstream operations.

## Architecture

This service listens to the `order-success` Kafka topic published by the Sales Service and processes each order through various fulfillment steps:

1. **Payment Processing** - Process payment through payment gateway
2. **Inventory Management** - Update inventory levels
3. **Customer Notifications** - Send confirmation emails
4. **Shipment Creation** - Create shipment records for logistics

## Technologies

- **Quarkus 3.30.1** - Java framework for microservices
- **Apache Kafka** - Event streaming platform
- **SmallRye Reactive Messaging** - Reactive messaging with Kafka

## Configuration

### Kafka Connection
- **Bootstrap Servers**: `localhost:9092`
- **Topic**: `order-success`
- **Consumer Group**: `order-processing-service-group`
- **Auto Offset Reset**: `earliest`

### Application Properties
- **Port**: `8081`
- **API Path**: `/order-processing-api`

## API Endpoints

### Health Check
```
GET /orderProcessing/health
```
Check if the service is running and healthy.

### Order Processing Status
```
GET /orderProcessing/status/{orderId}
```
Retrieve the processing status of an order.

## Running the Service

### Prerequisites
- Java 21+
- Apache Kafka running on localhost:9092
- Maven 3.8+

### Build and Run

```bash
cd order-processing-service
mvn clean quarkus:dev
```

The service will start on `http://localhost:8081`

### API Documentation
Access Swagger UI at: `http://localhost:8081/order-processing-api`

## Event Flow

1. **Sales Service** creates an order and publishes `OrderSuccessEvent` to `order-success` topic
2. **Order Processing Service** consumes the event
3. Service processes the order through multiple steps (payment, inventory, notifications, shipment)
4. Each step can be extended to integrate with external systems

## Example Event Structure

```json
{
  "orderId": "12345ORD",
  "product": "MacBook Pro M3",
  "price": 1999,
  "customerID": "CUST001",
  "orderDatabaseId": 1
}
```

## Future Enhancements

- Add database for order processing history
- Implement status tracking and persistence
- Add retry logic and dead letter queue handling
- Integrate with external payment gateways
- Add email notification service
- Implement order tracking system
- Add metrics and monitoring

