# Microservices Architecture

## System Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                     CLIENT APPLICATION                          │
└────────────────────────┬────────────────────────────────────────┘
                         │
                         │ HTTP Request (Place Order)
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                    SALES SERVICE                                 │
│  Port: 8080                                                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Order Controller                                         │   │
│  │ - placeOrder(SalesEntity)                              │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                        │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ ISalesOrderService                                       │   │
│  │ - Validate order                                         │   │
│  │ - Save to PostgreSQL                                     │   │
│  │ - Publish to Kafka                                       │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                        │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ OrderSuccessKafkaProducer                                │   │
│  │ - Serialize OrderSuccessEvent to JSON                    │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                        │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ PostgreSQL Database                                      │   │
│  │ - Store order data                                       │   │
│  └────────────────────────────────────────────────────────┘   │
└─────────────────────────┬──────────────────────────────────────┘
                         │
                         │ OrderSuccessEvent (JSON)
                         │ Topic: order-success
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│                   KAFKA BROKER                                   │
│              Partition: order-success-events                    │
└─────────────────────────┬──────────────────────────────────────┘
                         │
                         │ Consume Event
                         ▼
┌─────────────────────────────────────────────────────────────────┐
│              ORDER PROCESSING SERVICE                            │
│  Port: 8081                                                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ OrderSuccessKafkaConsumer                               │   │
│  │ - Listen to order-success topic                         │   │
│  │ - Deserialize JSON to OrderSuccessEvent                 │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                        │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ Order Processing Logic                                   │   │
│  │ ┌─────────────────┐ ┌─────────────────┐                │   │
│  │ │ Payment         │ │ Inventory       │                │   │
│  │ │ Processing      │ │ Management      │                │   │
│  │ └─────────────────┘ └─────────────────┘                │   │
│  │ ┌─────────────────┐ ┌─────────────────┐                │   │
│  │ │ Notifications   │ │ Shipment        │                │   │
│  │ │ (Email)         │ │ Creation        │                │   │
│  │ └─────────────────┘ └─────────────────┘                │   │
│  └──────────────────────┬──────────────────────────────────┘   │
│                         │                                        │
│  ┌──────────────────────▼──────────────────────────────────┐   │
│  │ OrderProcessingController                               │   │
│  │ - GET /orderProcessing/status/{orderId}                │   │
│  │ - GET /orderProcessing/health                           │   │
│  └────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## Event Flow

```
1. Client → Sales Service (HTTP)
   Request: {orderId, product, price, customerID}
   
2. Sales Service (Sync Processing)
   - Validate order
   - Save to PostgreSQL
   
3. Sales Service → Kafka (Async)
   Event: OrderSuccessEvent (JSON)
   Topic: order-success
   
4. Order Processing Service ← Kafka (Async)
   Consumer reads from order-success topic
   
5. Order Processing Service (Async Processing)
   - Process payment
   - Update inventory
   - Send notifications
   - Create shipment
```

## Communication Patterns

### Synchronous (HTTP)
- Client ↔ Sales Service

### Asynchronous (Kafka)
- Sales Service → Order Processing Service
- Allows decoupling of services
- Supports scaling independently
- Event-driven architecture

## Key Benefits

1. **Decoupling**: Services don't need to know about each other
2. **Scalability**: Each service can be scaled independently
3. **Resilience**: If one service is down, others continue processing
4. **Asynchronous Processing**: Long-running tasks don't block the API response
5. **Event Sourcing**: Complete audit trail of all order events

