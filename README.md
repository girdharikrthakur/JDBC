
---

# Simple JDBC Program

This is a simple Employee Management System using Java, MySQL, and basic JDBC operations. The application connects to a MySQL database, checks if an `employee` table exists, creates it if not, and allows the insertion of employee data (name, phone, email, password) along with auto-generation of a unique ID for each employee.

## Features

* Connects to a MySQL database using JDBC.
* Checks if the `employee` table exists and creates it if it doesn't.
* Allows users to input employee details such as name, phone, email, and password.
* Ensures unique IDs for each employee by checking for existing IDs in the database and incrementing until a new unique ID is found.
* Displays the list of employees from the database after an insertion.

## Prerequisites

Before running the application, make sure you have the following:

* Java Development Kit (JDK) 8 or higher installed.
* MySQL Database Server running on `localhost:3306`.
* A MySQL database named `hibernate` (or modify the connection string as needed).
* MySQL JDBC Driver (`com.mysql.cj.jdbc.Driver`) added to your project.

## Setup

1. **Clone the Repository** :

```bash
   git clone https://github.com/girdharikrthakur/JDBC.git
```

1. **Create the Database in MySQL** :
   In MySQL, create the `hibernate` database (if it doesn't exist already):

```sql
   CREATE DATABASE hibernate;
```

1. **Update Database Connection** :
   In the `App.java` file, ensure the connection string is correct. The default connection in the example is:

```java
   connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernate", "root", "admin");
```

   Update the username (`root`) and password (`admin`) to match your MySQL credentials.

1. **Run the Program** :
   Compile and run the `App.java` file:

```bash
   javac App.java
   java App
```

1. **User Input** :
   The program will prompt you to enter employee details (name, phone, email, and password). After entering the information, the program will insert it into the `employee` table with a unique ID and display the list of employees.

## Example

Here’s an example of how the program works:

```
Enter your name:
John Doe
Enter your phone:
1234567890
Enter your email:
john.doe@example.com
Enter your password:
password123
Inserted employee with ID: 1

1   John Doe   1234567890
2   Jane Doe   0987654321
```

## Code Explanation

1. **Database Connectivity** :
   The application establishes a connection to the MySQL database using the `DriverManager` class and `getConnection()` method.
2. **Employee Table Creation** :
   If the `employee` table doesn't exist, it is created with fields for `id`, `name`, `phone`, `email`, and `password`.
3. **Insert Employee Data** :
   The program takes user input using `Scanner` and inserts the employee data into the `employee` table. It ensures the ID is unique by checking the database for existing IDs and incrementing until it finds a free one.
4. **Display Employee Data** :
   After insertion, it fetches and displays all employees from the table using a simple `SELECT` query.

## Technologies Used

* Java 8 or higher
* MySQL 5.7 or higher
* JDBC (Java Database Connectivity)

## Troubleshooting

* If you encounter a `ClassNotFoundException`, ensure that the MySQL JDBC driver (`mysql-connector-java`) is included in your project dependencies.
* If you're facing connection issues, verify that the MySQL server is running and accessible on the provided host (`localhost:3306`).
