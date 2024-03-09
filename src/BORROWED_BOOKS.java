import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * A dialog window for displaying borrowed books.
 */
public class BORROWED_BOOKS extends JDialog {
    private JPanel panel1;
    private JTable table1;
    private JButton RETURNBOOKSButton;
    private JTextField useridtf;
    private JLabel ReturnID;
    private JButton SEARCHBOOKButton;
    private JTextField bookidtf;

    /**
     * Constructs a new BORROWED_BOOKS dialog.
     *
     * @param parent The parent JFrame to which this dialog belongs.
     */
    public BORROWED_BOOKS(JFrame parent) {
        super(parent);
        setTitle("Borrowed Books");
        setContentPane(panel1);
        setMinimumSize(new Dimension(550, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(parent);

        SEARCHBOOKButton.addActionListener(e -> {
            if (useridtf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the user ID");
                return;
            }
            // Creating a library instance to search for borrowed books
            LIBRARY library = new LIBRARY();
            int userId = Integer.parseInt(useridtf.getText());
            // Getting the list of borrowed books based on the user ID
            initializeTable(userId);
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0); // Clear the table
            library.displayBorrowedBooks(userId, table1);
        });

        // ActionListener for RETURNBOOKSButton
        RETURNBOOKSButton.addActionListener(e -> {
            if (bookidtf.getText().isEmpty() || useridtf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the book ID and user ID");
                return;
            }
            //checking if the book id and user id is valid
            try {
                // Retrieve the book ID from the text field
                int bookId = Integer.parseInt(bookidtf.getText());
                // Retrieve the user ID from the text field
                int userId = Integer.parseInt(useridtf.getText());
                // Create a library instance to handle book return
                LIBRARY library = new LIBRARY();
                boolean success = library.returnBooks(bookId, userId);
                // Display appropriate message based on return success
                if (success) {
                    JOptionPane.showMessageDialog(null, "Book returned successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not returned");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid book ID and user ID");
            }
        });

    }

    /**
     * Initializes the table to display borrowed books for the given user ID.
     *
     * @param userId The ID of the user whose borrowed books are to be displayed.
     */
    private void initializeTable(int userId) {
        // Create a table model with appropriate column headers
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Book ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Genre");
        model.addColumn("Availability");
        model.addColumn("User ID");

        // Set the table model
        table1.setModel(model);

        // Populate the table with borrowed books data
        LIBRARY library = new LIBRARY();
        library.displayBorrowedBooks(userId, table1);
    }

}
