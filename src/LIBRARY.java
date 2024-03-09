import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LIBRARY {

    // Method to add users to the database
    public boolean addUsers(String name, String contact, int id) {
        boolean success = false;
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USERNAME = "root";
        final String PASSWORD = "sq@nosho789";
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "INSERT INTO user_info (id, name, contact) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, contact);
            int resultSet = preparedStatement.executeUpdate();
            // Checking if the insertion was successful
            success = resultSet > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Method to add books to the database
    public boolean addBooks(int id, String bookName, String author, String genre) {
        boolean success = false;
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USERNAME = "root";
        final String PASSWORD = "sq@nosho789";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO books (id, title, author, genre, availability_status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, bookName);
                preparedStatement.setString(3, author);
                preparedStatement.setString(4, genre);
                preparedStatement.setBoolean(5, true);

                int resultSet = preparedStatement.executeUpdate();
                // Checking if the insertion was successful
                success = resultSet > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Method to display available books in the library
    public void displayBooks(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USERNAME = "root";
        final String PASSWORD = "sq@nosho789";
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM books WHERE availability_status = true";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        // Retrieving book details from the database
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String author = resultSet.getString("author");
                        String genre = resultSet.getString("genre");
                        boolean availability = resultSet.getBoolean("availability_status");

                        // Adding book details to the table model
                        model.addRow(new Object[]{id, title, author, genre, availability});
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to display borrowed books in the library
    public void displayBorrowedBooks(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USERNAME = "root";
        final String PASSWORD = "sq@nosho789";
        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM books WHERE availability_status = false";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        // Retrieving borrowed book details from the database
                        int id = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String author = resultSet.getString("author");
                        String genre = resultSet.getString("genre");
                        boolean availability = resultSet.getBoolean("availability_status");

                        // Adding borrowed book details to the table model
                        model.addRow(new Object[]{id, title, author, genre, availability});
                    }
                }
            }
            System.out.println("Rows are " + model.getRowCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check out books from the library
    public boolean checkoutBooks(int id) {
        boolean success = false;
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USERNAME = "root";
        final String PASSWORD = "sq@nosho789";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE books SET availability_status = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, false);
                preparedStatement.setInt(2, id);

                int resultSet = preparedStatement.executeUpdate();
                // Checking if the update was successful
                success = resultSet > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Method to search for books in the library
    public boolean searchBooks(int id, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows
        boolean success = false;
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USERNAME = "root";
        final String PASSWORD = "sq@nosho789";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "SELECT * FROM books WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Retrieving book details from the database
                        int bookId = resultSet.getInt("id");
                        String title = resultSet.getString("title");
                        String authorName = resultSet.getString("author");
                        String genreType = resultSet.getString("genre");
                        boolean availability = resultSet.getBoolean("availability_status");

                        // Adding book details to the table model
                        model.addRow(new Object[]{bookId, title, authorName, genreType, availability});
                        success = true;
                    }
                }
            }
            System.out.println("Rows are " + model.getRowCount());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Method to return books to the library
    public boolean returnBooks(int id) {
        boolean success = false;
        final String DB_URL = "jdbc:mysql://localhost:3306/library";
        final String USERNAME = "root";
        final String PASSWORD = "sq@nosho789";

        try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String sql = "UPDATE books SET availability_status = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, true);
                preparedStatement.setInt(2, id);

                int resultSet = preparedStatement.executeUpdate();
                // Checking if the update was successful
                success = resultSet > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }
}
