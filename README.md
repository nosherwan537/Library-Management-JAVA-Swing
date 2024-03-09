Java Library Management System

Brief Description:
This project is a Java-based Library Management System designed to manage books, users, and borrowing operations in a library setting. It provides functionalities for adding users, adding books, displaying available and borrowed books, checking out and returning books.

Setting up and Running the Project Locally

Prerequisites:
To run this project locally, you need to have the following installed on your system:

1) Java Development Kit (JDK) - version 8 or higher

2) MySQL database server

3) MySQL JDBC driver
4) IntelliJ IDEA (IDE)

Instructions
Clone the repository to your local machine:

Copy code

`https://github.com/nosherwantahir/CS212-Assignment-01.git`

Set up the database:

1) Create a MySQL database named library.

2) Run the SQL script library_file.sql provided in the DATABASE folder to create the necessary tables.

3) Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).

Update the database connection details in the LIBRARY.java file:

Copy code

`final String DB_URL = "jdbc:mysql://localhost:3306/library";
final String USERNAME = "root";
final String PASSWORD = "sq@nosho789";`

Build and run the project.

Key Features and Functionalities
1) User Management: 
Add users to the library system with their name, contact information, and unique ID.
2) Book Management: 
Add books to the library system with details such as title, author, genre, and availability status.
3) Display available books and borrowed books separately.
4) Search for books by their ID.
5) Book Borrowing
6) Checkout books by entering the book ID.
7) Return borrowed books by entering the book ID.

Graphical User Interface

The project provides a user-friendly graphical interface built using Java Swing for easy navigation and interaction with the library system.

Developed by:
Nosherwan Tahir