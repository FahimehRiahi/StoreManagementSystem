# Store Management System

A Java-based console application for managing products and inventory.

## Overview

Store Management System is a practice project built with Java to demonstrate object-oriented programming, service-layer design, exception handling, and unit testing.

## Features

- Add and update products
- Delete products
- Search for products by ID
- Prevent duplicate product IDs
- Validate product data
- Sort products by price and name
- Filter products with low stock
- Increase and decrease inventory
- Handle insufficient stock and missing products with custom exceptions
- Unit tests for core business logic

## Technologies

- Java
- Apache Ant
- NetBeans IDE
- JUnit 4

## Project Structure

```text
StoreManagementSystem/
├── src/
│   └── storemanagementsystem/
│       ├── model/
│       ├── repository/
│       ├── service/
│       ├── exception/
│       └── Main.java
├── test/
│   └── storemanagementsystem/
│       └── service/
│           └── ProductServiceTest.java
├── build.xml
├── .gitignore
└── README.md
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK)
- NetBeans IDE with Ant project support

### Run the Application

1. Clone or download this repository.
2. Open the project in NetBeans.
3. Build the project.
4. Run `storemanagementsystem.Main`.

### Run Tests

In NetBeans, right-click `ProductServiceTest.java` and select **Test File**.

## Testing

The project includes unit tests for product management, validation, inventory operations, sorting, and low-stock filtering.

## Project Status

This project is being developed as a portfolio project to practice Java application design, testing, and maintainable code.

## Author

Fahimeh Riahi