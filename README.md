# Vehicle Rental System - Microservices and Architecture Project

This project is a microservices-based **Vehicle Rental System** developed using **Spring Boot** and **Spring Cloud OpenFeign**. It consists of six microservices:

## 🏢 Microservices Overview

- **Rental Service**: Handles car rentals for users, checking availability with Inventory Service and processing payments with Payment Service.
- **Payment Service**: Manages rental payments, verifying and processing transactions.
- **Maintenance Service**: Admin service for scheduling maintenance, updating Inventory and Filter services accordingly.
- **Invoice Service**: Stores invoices for completed rental transactions.
- **Inventory Service**: Admin service for managing vehicle stock, models, and brands, reflecting changes in the Filter Service.
- **Filter Service**: User-facing service providing quick access to available vehicles.

## 🛠 Tech Stack

- **Backend**: Java, Spring Boot, Spring Data JPA, Spring Cloud OpenFeign
- **Database**: MySQL
- **Build Tool**: Maven
- **REST Client**: Postman or any other tool

## 🔗 Communication Between Services

- **Rental → Inventory**: The Rental Service requests Inventory Service to check car availability before proceeding with a rental.
- **Rental → Maintenance**: The Rental Service verifies with Maintenance Service whether the requested car is under maintenance.
- **Rental → Payment**: If the car is available, Rental Service requests Payment Service to process the transaction using the given account details.
- **Payment → Invoice**: After a successful payment, Payment Service triggers Invoice Service to generate an invoice for the rental.

## 📚 How It Works

1. A user initiates a car rental request via the Rental Service.
2. The Rental Service contacts the Inventory Service to check car availability.
3. The Rental Service ensures the car is not under maintenance by querying the Maintenance Service.
4. If the car is available, the Rental Service requests the Payment Service to process the payment.
5. Upon successful payment, the Payment Service triggers the Invoice Service to generate an invoice.
6. The rental transaction is completed, and the details are stored.

## 🧪 Testing

- Use **Postman** or any REST client to test API endpoints.
- Validate inter-service communication using logs.
- Check database records to verify data consistency.

## 🛡 Error Handling

- **404 Not Found**: Returned when requested resources (cars, rentals, invoices, etc.) are not found.

## 💡 Future Enhancements

- **Monitoring and Metrics**: Use **Prometheus** and **Grafana** for real-time system health monitoring.
- **Service Discovery & Routing**: Implement **Spring Cloud Gateway** for routing and **Eureka Service Registry** for dynamic service registration.
- **Event-Driven Communication**: Integrate **Apache Kafka** for event-based communication (e.g., rental transactions, invoice generation).
- **Containerization**: Deploy microservices, databases, and Kafka in **Docker** for portability and scalability.

