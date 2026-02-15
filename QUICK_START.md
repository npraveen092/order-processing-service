# Quick Start Guide - Order Processing Microservice

## Prerequisites

Ensure you have the following installed:
- Java 21 or higher
- Maven 3.8+
- Apache Kafka running on `localhost:9092`
- PostgreSQL running on `localhost:5432`
- The Sales Service running on `localhost:8080`

## Setup Steps

### 1. Verify Kafka is Running

```bash
# Check Kafka broker status
kafka-topics.sh --list --bootstrap-server localhost:9092

# You should see the "order-success" topic (created by Sales Service)
```

### 2. Build the Order Processing Service

```bash
cd /Users/pravinn/Projects/order-processing-service
mvn clean compile
```

### 3. Run in Development Mode

```bash
mvn quarkus:dev
```

You should see output similar to:
```
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2026-01-31 12:30:00,000 INFO  [io.qua.dep.QuarkusAugmentor] (main) Quarkus augmentation completed
```

### 4. Verify Service is Running

```bash
# Health check
curl http://localhost:8081/orderProcessing/health

# Expected response:
# {"status": "UP", "service": "order-processing-service"}
```

## Testing the Flow

### 1. Place an Order (Sales Service)

```bash
curl -X POST http://localhost:8080/salesOrder/placeOrder \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": "TEST123",
    "product": "MacBook Pro",
    "price": 1999,
    "customerID": "CUST001"
  }'
```

### 2. Monitor Order Processing (Order Processing Service)

Watch the console output in the Order Processing Service terminal. You should see:
```
INFO  [org.domi.service.kafka.OrderSuccessKafkaConsumer] Received order success event: OrderSuccessEvent{...}
INFO  [org.domi.service.kafka.OrderSuccessKafkaConsumer] === Starting Order Processing ===
INFO  [org.domi.service.kafka.OrderSuccessKafkaConsumer] Processing payment for orderId: TEST123
INFO  [org.domi.service.kafka.OrderSuccessKafkaConsumer] Updating inventory for product: MacBook Pro
INFO  [org.domi.service.kafka.OrderSuccessKafkaConsumer] Sending confirmation email to customer: CUST001
INFO  [org.domi.service.kafka.OrderSuccessKafkaConsumer] Creating shipment for orderId: TEST123
INFO  [org.domi.service.kafka.OrderSuccessKafkaConsumer] === Order Processing Completed Successfully ===
```

### 3. Check Order Processing Status

```bash
curl http://localhost:8081/orderProcessing/status/TEST123
```

## API Documentation

### Swagger UI
Access the interactive API documentation at:
```
http://localhost:8081/order-processing-api
```

## Troubleshooting

### Issue: "Cannot connect to Kafka broker"
- Verify Kafka is running: `kafka-broker-api-versions.sh --bootstrap-server localhost:9092`
- Check `application.properties` has correct `kafka.bootstrap.servers`

### Issue: "No events being consumed"
- Verify `order-success` topic exists: `kafka-topics.sh --list --bootstrap-server localhost:9092`
- Check consumer group: `kafka-consumer-groups.sh --list --bootstrap-server localhost:9092`

### Issue: Service won't start
- Check Java version: `java -version` (should be 21+)
- Check Maven: `mvn --version`
- Clean build: `mvn clean install`

## Next Steps

1. **Add Database Integration**
   - Add Hibernate ORM and PostgreSQL driver
   - Create entities to track order processing status

2. **Implement External Integrations**
   - Payment Gateway API integration
   - Email service integration
   - Inventory management service
   - Shipping/Logistics API

3. **Add Error Handling**
   - Implement retry logic with backoff
   - Dead letter queue for failed events
   - Error notifications

4. **Add Monitoring**
   - Prometheus metrics
   - Distributed tracing with Jaeger
   - Health checks and readiness probes

5. **Scale to Production**
   - Docker containerization
   - Kubernetes deployment
   - CI/CD pipeline integration

## Documentation

- **README.md** - Service overview and configuration
- **ARCHITECTURE.md** - System design and event flow
- **QUICK_START.md** - This file

For more information on Quarkus, visit: https://quarkus.io

