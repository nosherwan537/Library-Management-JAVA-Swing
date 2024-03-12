//file: LIBRARY.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
/**
 * A class representing library operations such as adding users, adding books, displaying available and borrowed books,
 * searching for books, checking out books, and returning books.
 */
public class LIBRARY {

    /**
     * Adds a user to the library database.
     *
     * @param name    The name of the user.
     * @param contact The contact information of the user.
     * @param id      The ID of the user.
     * @return true if the user is added successfully, false otherwise.
     */
    public boolean addUsers(String name, String contact, int id) {
        boolean success = false;
        try {
            // Establishing connection to the database
            Connection connection = DriverManager.getConnection(DBProperties.getURL(), DBProperties.getUser(), DBProperties.getPassword());
            String sql = "INSERT INTO user_info (id, name, contact) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, contact);
            int resultSet = preparedStatement.executeUpdate();
            // Checking if the insertion was successful
            success = resultSet > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to add the user.");
        }
        return success;
    }

    /**
     * Adds a book to the library database.
     *
     * @param id       The ID of the book.
     * @param bookName The title of the book.
     * @param author   The author of the book.
     * @param genre    The genre of the book.
     * @return true if the book is added successfully, false otherwise.
     */
    public boolean addBooks(int id, String bookName, String author, String genre) {
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(DBProperties.getURL(), DBProperties.getUser(), DBProperties.getPassword())) {
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
            JOptionPane.showMessageDialog(null, "Failed to add the book.");
        }
        return success;
    }

    /**
     * Displays available books in the library.
     *
     * @param table The table to display the available books.
     */
    public void displayBooks(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows
        try (Connection connection = DriverManager.getConnection(DBProperties.getURL(), DBProperties.getUser(), DBProperties.getPassword())) {
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
                        model.addRow(new Object[]{id, title, author, genre, availability? "Available" : "Not Available"});
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to display books.");
        }
    }

    /**
     * Displays borrowed books in the library for a specific user.
     *
     * @param userId The ID of the user whose borrowed books are to be displayed.
     * @param table  The table to display the borrowed books.
     */
    public void displayBorrowedBooks(int userId, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows
        try (Connection connection = DriverManager.getConnection(DBProperties.getURL(), DBProperties.getUser(), DBProperties.getPassword())) {
            String sql = "SELECT * FROM borrowed_books WHERE user_id = ?"; // Select data from borrowed_books table

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId); // Set the value of the user_id parameter
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Retrieving borrowed book details from the database
                        int id = resultSet.getInt("id");
                        int bookId = resultSet.getInt("book_id");
                        // Retrieve book details from books table based on book_id
                        String bookTitle = getBookTitleById(bookId, connection);
                        String bookAuthor = getBookAuthorById(bookId, connection);
                        String bookGenre = getBookGenreById(bookId, connection);
                        boolean availability = false; // Since it's borrowed, it's not available

                        // Adding borrowed book details to the table model
                        model.addRow(new Object[]{id, bookId, bookTitle, bookAuthor, bookGenre, availability? "Available" : "Not Available", userId});
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to display borrowed books.");
        }
    }

    // Helper method to get book title by book ID
    private String getBookTitleById(int bookId, Connection connection) throws SQLException {
        String sql = "SELECT title FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("title");
                }
            }
        }
        return null;
    }

    // Helper method to get book author by book ID
    private String getBookAuthorById(int bookId, Connection connection) throws SQLException {
        String sql = "SELECT author FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("author");
                }
            }
        }
        return null;
    }

    // Helper method to get book genre by book ID
    private String getBookGenreById(int bookId, Connection connection) throws SQLException {
        String sql = "SELECT genre FROM books WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("genre");
                }
            }
        }
        return null;
    }


    /**
     * Checks out a book from the library.
     *
     * @param bookId The ID of the book to check out.
     * @param userId The ID of the user checking out the book.
     * @return true if the book is checked out successfully, false otherwise.
     */
    public boolean checkoutBooks(int bookId, int userId){
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(DBProperties.getURL(), DBProperties.getUser(), DBProperties.getPassword())) {
            // Begin transaction
            connection.setAutoCommit(false);

            // First SQL statement: Update book's availability status to false
            String updateBookSql = "UPDATE books SET availability_status = false WHERE id = ?";
            try (PreparedStatement updateBookStatement = connection.prepareStatement(updateBookSql)) {
                updateBookStatement.setInt(1, bookId);
                int bookUpdateResult = updateBookStatement.executeUpdate();

                // Second SQL statement: Insert record into borrowed_books table
                String insertBorrowedBookSql = "INSERT INTO borrowed_books (book_id, user_id) VALUES (?, ?)";
                try (PreparedStatement insertBorrowedBookStatement = connection.prepareStatement(insertBorrowedBookSql)) {
                    insertBorrowedBookStatement.setInt(1, bookId);
                    insertBorrowedBookStatement.setInt(2, userId);
                    int borrowedBookInsertResult = insertBorrowedBookStatement.executeUpdate();

                    // Commit transaction if both statements executed successfully
                    if (bookUpdateResult > 0 && borrowedBookInsertResult > 0) {
                        connection.commit();
                        success = true;
                    } else {
                        // Rollback transaction if any statement failed
                        connection.rollback();
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to check out the book.");

        }
        return success;
    }


    /**
     * Searches for books in the library based on title or author.
     *
     * @param title  The title of the book to search for.
     * @param author The author of the book to search for.
     * @param table  The table to display the search results.
     * @return true if books are found matching the search criteria, false otherwise.
     */
    public boolean searchBooks(String title, String author, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(DBProperties.getURL(), DBProperties.getUser(), DBProperties.getPassword())) {
            String sql = "SELECT * FROM books WHERE title = ? OR author= ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, author);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Retrieving book details from the database
                        int bookId = resultSet.getInt("id");
                        String titleName = resultSet.getString("title");
                        String authorName = resultSet.getString("author");
                        String genreType = resultSet.getString("genre");
                        boolean availability = resultSet.getBoolean("availability_status");
                        // Adding book details to the table model
                        model.addRow(new Object[]{bookId, titleName, authorName, genreType, availability? "Available" : "Not Available"});
                        success = true;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to search for books.");
        }
        return success;
    }

    /**
     * Returns a book to the library.
     *
     * @param bookId The ID of the book to return.
     * @param userId The ID of the user returning the book.
     * @return true if the book is returned successfully, false otherwise.
     */
    public boolean returnBooks(int bookId, int userId) {
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(DBProperties.getURL(), DBProperties.getUser(), DBProperties.getPassword())) {
            // Delete the entry from the borrowed_books table
            String deleteSql = "DELETE FROM borrowed_books WHERE book_id = ? AND user_id = ?";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, bookId);
                deleteStatement.setInt(2, userId);

                int rowsDeleted = deleteStatement.executeUpdate();
                // Checking if the delete was successful
                if (rowsDeleted > 0) {
                    // Update the availability_status in the books table
                    String updateSql = "UPDATE books SET availability_status = ? WHERE id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                        updateStatement.setBoolean(1, true);
                        updateStatement.setInt(2, bookId);

                        int rowsUpdated = updateStatement.executeUpdate();
                        // Checking if the update was successful
                        success = rowsUpdated > 0;
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed to return the book.");
        }
        return success;
    }
}