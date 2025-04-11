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

## API Endpoints

### 1. Rental Service (http://localhost:8080)
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | /api/rentals/create | Create a new rental entry. |
| GET | /api/rentals/get/{id} | Retrieve rental details by rental ID. |
| GET | /api/rentals/get-all | Fetch a list of all rentals. |
| PUT | /api/rentals/update/{id} | Update rental details by rental ID. |
| DELETE | /api/rentals/delete/{id} | Delete a rental entry by rental ID. |

### 2. Payment Service (http://localhost:8082)
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | /api/payments/create | Create a new payment transaction. |
| GET | /api/payments/get/{id} | Retrieve payment details by payment ID. |
| GET | /api/payments/get-all | Fetch a list of all payments. |
| DELETE | /api/payments/delete/{id} | Delete a payment entry by payment ID. |

### 3. Inventory Service (http://localhost:8081)
#### Brand Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | /api/brands/get-all | Fetch a list of all brands. |
| GET | /api/brands/get/{id} | Retrieve brand details by ID. |
| POST | /api/brands/create | Create a new brand entry. |
| DELETE | /api/brands/delete/{id} | Delete a brand by ID. |

#### Car Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | /api/cars/get-all | Fetch a list of all cars. |
| GET | /api/cars/get/{id} | Retrieve car details by ID. |
| POST | /api/cars/create | Create a new car entry. |
| DELETE | /api/cars/delete/{id} | Delete a car by ID. |

#### Model Endpoints
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | /api/models/get-all | Fetch a list of all models. |
| GET | /api/models/get/{id} | Retrieve model details by ID. |
| POST | /api/models/create | Create a new model entry. |
| DELETE | /api/models/delete/{id} | Delete a model by ID. |

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

## 💡 Scalability Features

- **Monitoring and Metrics**: Use **Prometheus** and **Grafana** for system health monitoring.
- **Service Discovery & Routing**: Implement **Spring Cloud Gateway** for routing and **Eureka Service Registry** for dynamic service registration.
- **Event-Driven Communication**: Integrate **Apache Kafka** for event-based communication (e.g., rental transactions, invoice generation).
- **Containerization**: Deploy microservices, databases, and Kafka in **Docker** for portability and scalability.
