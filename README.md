# 🛒 Supermarket Management System

<div align="center">

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/your-username/Supermarket-Management/actions)
[![Java](https://img.shields.io/badge/Java-8%2B-blue.svg)](https://www.oracle.com/java/)
[![Python](https://img.shields.io/badge/Python-3.8%2B-yellow.svg)](https://www.python.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](./LICENSE)

*A comprehensive desktop application for supermarket management with AI-powered product recommendations*

[Features](#-features) • [Installation](#-installation--setup) • [Usage](#-usage) • [Contributing](#-contributing)

</div>

---

## Overview

The Supermarket Management System is a feature-rich desktop application designed to streamline all aspects of supermarket operations. Built with Java Swing for the frontend and powered by a Python-based recommendation engine, this system provides comprehensive management capabilities for employees, inventory, suppliers, customers, and sales transactions.

### Key Highlights

- **Role-based access control** with multiple user types
- **Intelligent product recommendations** using Apriori algorithm
- **Real-time inventory tracking** with low-stock alerts
- **Comprehensive reporting** and analytics
- **Supplier relationship management** with purchase order tracking
- **Customer transaction history** and loyalty management

---

## Features

### Authentication & Authorization
- Multi-role login system (Manager, Cashier, Warehouse, Employee)
- Secure session management
- User activity logging and audit trails

### Employee Management
- Complete CRUD operations for staff management
- Employee performance tracking
- Work schedule management
- Salary and payroll integration

### Inventory & Product Management
- Product categorization with hierarchical structure
- Real-time stock level monitoring
- Automated reorder point notifications
- Product image management
- Batch and expiry date tracking

### Supplier & Procurement
- Comprehensive supplier database
- Purchase order creation and management
- Supplier performance analytics
- Automated purchase suggestions

### Sales & Customer Management
- Point-of-sale (POS) interface
- Customer profile management
- Invoice generation and printing
- Sales analytics and reporting

### AI-Powered Recommendations
- **Apriori algorithm** implementation for market basket analysis
- Product bundle suggestions based on historical sales data
- Customer behavior pattern analysis
- Seasonal trend identification

---

## System Architecture

```
┌─────────────────────┐    ┌─────────────────────┐    ┌─────────────────────┐
│   Java Swing GUI   │◄───┤   DAO & Services    │◄───┤   SQL Server DB    │
│   (Presentation)    │    │  (Business Logic)   │    │     (Storage)       │
└─────────────────────┘    └─────────────────────┘    └─────────────────────┘
           │                          │
           │                          │
           ▼                          ▼
┌─────────────────────┐    ┌─────────────────────┐
│  Python/Flask API   │    │  Apriori Engine     │
│  (Microservice)     │    │  (Recommendations)  │
└─────────────────────┘    └─────────────────────┘
```

### Technology Stack

| Layer | Technology | Purpose |
|-------|------------|---------|
| **Frontend** | Java Swing | Desktop GUI application |
| **Backend** | Java SE 8+ | Business logic and data access |
| **Database** | SQL Server | Data persistence |
| **AI Service** | Python Flask | Recommendation engine |
| **Build System** | Apache Ant | Project compilation and packaging |

---

## Tech Stack

### Backend Technologies
- **Java SE 8+** - Core application framework
- **JDBC** - Database connectivity
- **Apache Ant** - Build automation
- **Apache POI** - Excel file processing

### AI & Analytics
- **Python 3.8+** - Recommendation engine
- **Flask** - REST API framework
- **Pandas** - Data manipulation
- **mlxtend** - Apriori algorithm implementation
- **openpyxl** - Excel file handling

### Database
- **Microsoft SQL Server** - Primary database
- **JDBC Driver** - Database connectivity

### Libraries & Dependencies
- Commons Collections, Commons Codec
- Microsoft JDBC Driver
- Various Apache Commons utilities

---

## Prerequisites

Before installing the Supermarket Management System, ensure you have the following installed:

### System Requirements
- **Operating System**: Windows 10+ / macOS 10.14+ / Linux Ubuntu 18.04+
- **RAM**: Minimum 4GB, Recommended 8GB+
- **Storage**: 500MB available space

### Software Dependencies
- **Java Development Kit (JDK)**: Version 8 or later
- **Python**: Version 3.8 or later
- **SQL Server**: Express or full edition
- **Git**: For version control (optional)

### Development Tools (Optional)
- **NetBeans IDE**: For development and debugging
- **SQL Server Management Studio**: For database management

---

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/Supermarket-Management.git
cd Supermarket-Management
```

### 2. Database Setup
1. Start SQL Server service
2. Execute the database initialization script:
   ```sql
   -- Run the script located at:
   src/db/database.sql
   ```
3. Ensure SQL Server is running on `localhost:1433`

### 3. Python Dependencies
```bash
# Navigate to the Python service directory
cd recommendation-engine

# Install required packages
pip install -r requirements.txt

# Or install individually:
pip install flask pandas mlxtend openpyxl
```

### 4. Java Application Setup
```bash
# Build the project using Ant
ant clean build

# Or run the pre-built JAR file
java -jar dist/supermarket-management.jar
```

### 5. Start Services
```bash
# Start the Python recommendation service
cd recommendation-engine
python app.py

# Start the Java application
java -jar dist/supermarket-management.jar
```

---

## Default Credentials

| Role | Username | Password | Permissions |
|------|----------|----------|-------------|
| **Manager** | `admin` | `admin123` | Full system access |
| **Cashier** | `cashier` | `cash123` | POS, Customer management |
| **Warehouse** | `warehouse` | `ware123` | Inventory, Suppliers |
| **Employee** | `employee` | `emp123` | Limited access |

> **Important**: Change default passwords immediately after first login for security.

---

## Usage

### Getting Started
1. Launch the application using `java -jar dist/supermarket-management.jar`
2. Login with appropriate credentials based on your role
3. Navigate through the intuitive menu system

### Key Workflows

#### Daily Operations
- **Morning Setup**: Check inventory levels, review pending orders
- **Sales Processing**: Use POS interface for customer transactions
- **Inventory Updates**: Record stock movements and adjustments
- **End-of-Day**: Generate daily sales reports

#### Inventory Management
- Add new products with categories and pricing
- Set reorder points for automatic alerts
- Track expiry dates and batch numbers
- Generate inventory reports

#### Using Recommendations
- Access the recommendations panel in the sales module
- View suggested product bundles based on customer purchase patterns
- Apply recommendations to increase sales and customer satisfaction

---

## Screenshots

<details>
<summary>🔐 Login Interface</summary>

![Login Screen](src/resources/login/login_img.png)

*Clean, professional login screen with role-based authentication*
</details>

<details>
<summary>Admin Dashboard</summary>

![Admin Dashboard](src/resources/employee/setting.png)

*Comprehensive dashboard with real-time metrics and quick access to all modules*
</details>

<details>
<summary>🛍 Point of Sale</summary>

*User-friendly POS interface for efficient transaction processing*
</details>

---

## Project Structure

```
Supermarket-Management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── dao/          # Data Access Objects
│   │   │   ├── model/        # Entity classes
│   │   │   ├── service/      # Business logic
│   │   │   └── ui/           # Swing UI components
│   │   └── resources/        # Images, config files
│   └── db/
│       └── database.sql      # Database schema
├── dist/
│   ├── lib/                  # JAR dependencies
│   └── supermarket-management.jar
├── recommendation-engine/
│   ├── app.py               # Flask API
│   ├── apriori_engine.py    # Recommendation logic
│   └── requirements.txt     # Python dependencies
├── build.xml                # Ant build script
└── README.md
```

---

## Testing

### Unit Tests
```bash
# Run Java unit tests
ant test

# Run Python tests
cd recommendation-engine
python -m pytest tests/
```

### Integration Tests
```bash
# Test database connectivity
ant test-db

# Test API endpoints
python test_api.py
```

---

## Contributing

We welcome contributions to the Supermarket Management System! Here's how you can help:

### Development Workflow
1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Code Standards
- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Include unit tests for new features

### Bug Reports
Please use the [GitHub Issues](https://github.com/your-username/Supermarket-Management/issues) to report bugs with:
- Clear description of the issue
- Steps to reproduce
- Expected vs actual behavior
- System information

---

<div align="center">

**Made with ❤️ for efficient supermarket management**

⭐ If you find this project useful, please consider giving it a star!

</div>
