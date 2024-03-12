//file: BOOK.java
import javax.swing.*;
import java.awt.*;

/**
 * A dialog window for adding a book to the library.
 */
public class BOOK extends JDialog {
    // Instance variables to store book information
    private long bookId;
    private String bookName;
    private String author;
    private String genre;
    private boolean isAvailable;

    // Components for the user interface
    private JPanel panel1;
    private JTextField titlef; // Text field for book title
    private JTextField authorf; // Text field for author name
    private JTextField genref; // Text field for genre
    private JTextField idf; // Text field for book ID
    private JButton ADDButton; // Button to add the book
    private JLabel genret; // Label for genre
    private JLabel idt; // Label for book ID
    private JLabel authort; // Label for author

    /**
     * Constructs a new BOOK dialog.
     *
     * @param parent The parent JFrame to which this dialog belongs.
     */
    public BOOK(JFrame parent) {
        super(parent);
        setTitle("Add Book");
        setContentPane(panel1);
        setMinimumSize(new Dimension(450, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(parent);

        // ActionListener for the ADDButton
        ADDButton.addActionListener(e -> {
            // Retrieving book information from the text fields
            String bookIdText = idf.getText();
            bookName = titlef.getText();
            author = authorf.getText();
            genre = genref.getText();

            // Checking if any of the fields are empty
            if (bookIdText.isEmpty() || bookName.isEmpty() || author.isEmpty() || genre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all fields");
                return; // Exit the method early if any field is empty
            }

            //checking if the book id is valid
            try {
                // Parsing the book ID from the text field
                bookId = Long.parseLong(bookIdText);

                // Creating a library instance to add the book
                LIBRARY library = new LIBRARY();
                boolean success = library.addBooks((int) bookId, bookName, author, genre);

                // Displaying appropriate message based on the success of adding the book
                if (success) {
                    JOptionPane.showMessageDialog(null, "Book added successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not added");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid book ID");
            }
        });

    }

}
