# 🎓 Student Management System  

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)  
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)  
![JDBC](https://img.shields.io/badge/JDBC-4DB33D?style=for-the-badge&logo=java&logoColor=white)  
![Swing](https://img.shields.io/badge/Swing-FF6F00?style=for-the-badge&logo=java&logoColor=white)   

A **Java + MySQL** based Student Management System designed to manage student records efficiently.  
The project demonstrates **CRUD operations (Create, Read, Update, Delete)** with both **Console** and **Swing GUI** interfaces using **JDBC integration**.  

---

## 🚀 Features  
- ➕ **Add Student** – Insert new student details into the database.  
- ✏️ **Update Student** – Modify existing student information.  
- ❌ **Delete Student** – Remove student records.  
- 🔍 **Search Student** – Retrieve student details by ID or Name.  
- 🖥️ **Swing GUI** – User-friendly interface in addition to console support.  
- 💾 **MySQL Integration** – Persistent data storage.  
- ⚡ **Exception Handling** – Handles database & input errors gracefully.  
- 🧩 **OOP Concepts** – Demonstrates encapsulation, abstraction, inheritance, and polymorphism.  
- 📂 **Collections Framework** – Used for handling dynamic data storage before persistence.  

---

## 🛠️ Tech Stack  
- **Language:** Java (Core + OOPs)  
- **Database:** MySQL  
- **Connector:** JDBC  
- **Interface:** Console + Swing GUI  
- **IDE:** IntelliJ IDEA / Eclipse / NetBeans  
- **Server:** XAMPP (MySQL Database Management via phpMyAdmin)  

---

## 📂 Project Structure  
├── src/
│ ├── db/ # Database connection setup
│ ├── model/ # Student class (POJO)
│ ├── dao/ # Data Access Object for CRUD
│ ├── service/ # Business logic
│ ├── ui/ # Swing GUI
│ └── Main.java # Entry point
├── resources/
│ └── sql/ # Database schema (sms_db.sql)
└── README.md

---

## 🗄️ Database Schema  
**Database:** `sms_db`  
**Table:** `students`  

| Column Name | Type         | Constraints        |
|-------------|--------------|--------------------|
| id          | INT          | PRIMARY KEY, AUTO_INCREMENT |
| name        | VARCHAR(100) | NOT NULL           |
| age         | INT          | NOT NULL           |
| course      | VARCHAR(50)  | NOT NULL           |
| address     | VARCHAR(255) |                    |

---

## ⚙️ Installation & Setup  

1. **Clone Repository**  
```bash
git clone https://github.com/your-username/student-management-system.git
cd student-management-system

Setup Database

Start XAMPP (Apache + MySQL).

Open phpMyAdmin → Create database sms_db.

Import provided sms_db.sql file.

Configure JDBC

Download MySQL Connector (JAR).

Add it to your project lib/ folder and include in classpath.

Update database URL in db/DBConnection.java:
private static final String URL = "jdbc:mysql://localhost:3306/sms_db?serverTimezone=UTC";
private static final String USER = "root";
private static final String PASSWORD = "";

Run the Project

Run Main.java for console mode.

Run StudentUI.java for Swing GUI mode.

🎯 Learning Outcomes

Hands-on experience with Java + JDBC + MySQL.

Understanding CRUD operations in a real-world project.

Applied OOP principles & Collections Framework.

Error handling & exception management.

GUI building using Swing.
