[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/your-username/Supermarket-Management/actions)
[![Java](https://img.shields.io/badge/Java-8%2B-blue.svg)](https://www.oracle.com/java/)
[![Python](https://img.shields.io/badge/Python-3.8%2B-yellow.svg)](https://www.python.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](./LICENSE)

# 🛒 Supermarket Management System

A full-featured desktop application for managing all aspects of a supermarket—employees, inventory, suppliers, customers, orders—augmented by an Apriori-based recommendation engine to suggest product bundles based on sales history.

---

## 📋 Table of Contents

1. [Features](#-features)  
2. [Architecture](#-architecture)  
3. [Screenshots](#-screenshots)  
4. [Tech Stack](#-tech-stack)  
5. [Prerequisites](#-prerequisites)  
6. [Installation & Setup](#-installation--setup)  
7. [Default Credentials](#-default-credentials)  
8. [Usage](#-usage)  
9. [Database Initialization](#-database-initialization)  
10. [Contributing](#-contributing)  
11. [License](#-license)  

---

## ✨ Features

- **User Authentication**  
  Role-based login (Quản lý, Thu ngân, Kho hàng, Nhân viên).  
- **Employee Management**  
  CRUD operations on staff, with activity logs.  
- **Product & Inventory**  
  Categories, products, stock levels, images.  
- **Supplier & Purchase Orders**  
  Maintain supplier records; create and manage incoming stock.  
- **Sales & Invoicing**  
  Create invoices, track customer purchases.  
- **Apriori Recommendation Engine**  
  Python/Flask service analyzes Victorian-style transaction data (`GiaoDich_SanPham_TiengViet.xlsx`) to suggest product bundles.  

---

## 🏗 Architecture
│ Java Swing GUI │◀─────│ DAO & Services │◀─────│ SQL Server DB │
└──────────────────┘ └────────────────────┘ └─────────────────────┘
│ ▲
│ │
▼ │
┌──────────────────┐ │
│ Python/Flask │──────────Apriori API──────────▶────────┘
└──────────────────┘

- **Presentation**: Java Swing (NetBeans, Ant)  
- **Data Access**: DAO pattern with **JDBC** (Microsoft SQL Server)  
- **Business Logic**: Java services + Python microservice  
- **Recommendation**: `mlxtend` Apriori & association rules  
- **Storage**: SQL Server; seed data via `src/db/database.sql`  
- **Build**: Ant (`build.xml`), or run `dist/supermarket-management.jar`  

---

## 🖼 Screenshots

<details>
<summary>Login Screen</summary>

![Login Screen](src/resources/login/login_img.png)

</details>

<details>
<summary>Admin Dashboard</summary>

![Admin Dashboard](src/resources/employee/setting.png)  
*Manage products, suppliers, staff…*
</details>

---

## ⚙️ Tech Stack

- **Java SE 8+** → Swing, JDBC, Ant  
- **Python 3.8+** → Flask, Pandas, mlxtend, openpyxl  
- **SQL Server** → Relational database  
- **Libraries**  
  - Apache POI (in `dist/lib`)  
  - Microsoft JDBC Driver  
  - Commons-Collections, Commons-Codec, etc.  

---

## 📦 Prerequisites

1. **Java** JDK 8 or later  
2. **Python** 3.8 or later  
3. **SQL Server** (Express or full) running on `localhost:1433`  
4. **pip** for Python dependencies  
5. (Optional) **NetBeans** IDE for development  

---
