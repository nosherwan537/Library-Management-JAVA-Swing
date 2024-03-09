import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Class representing the dialog to display borrowed books
public class BORROWED_BOOKS extends JDialog {
    private JPanel panel1;
    private JTable table1;
    private JButton RETURNBOOKSButton;
    private JTextField returntf;
    private JLabel ReturnID;

    // Constructor for BORROWED_BOOKS dialog
    public BORROWED_BOOKS(JFrame parent) {
        super(parent);
        setTitle("Borrowed Books");
        setContentPane(panel1);
        setMinimumSize(new Dimension(450, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(parent);
        initializeTable(); // Initialize the table to display borrowed books

        // ActionListener for RETURNBOOKSButton
        RETURNBOOKSButton.addActionListener(e -> {
            if(returntf.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter the book ID");
                return;
            }
            //checking if the book id is valid
            try {
                // Retrieve the book ID from the text field
                int bookId = Integer.parseInt(returntf.getText());
                // Create a library instance to handle book return
                LIBRARY library = new LIBRARY();
                boolean success = library.returnBooks(bookId);
                // Display appropriate message based on return success
                if (success) {
                    JOptionPane.showMessageDialog(null, "Book returned successfully");
                    // Clear the table and refresh with updated borrowed books
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();
                    model.setRowCount(0);
                    library.displayBorrowedBooks(table1);
                } else {
                    JOptionPane.showMessageDialog(null, "Book not returned");
                }
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Please enter a valid book ID");
            }
        });
    }

    // Method to initialize the table to display borrowed books
    private void initializeTable() {
        // Create a table model with appropriate column headers
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Genre");
        model.addColumn("Availability");

        // Set the table model
        table1.setModel(model);

        // Populate the table with borrowed books data
        LIBRARY library = new LIBRARY();
        library.displayBorrowedBooks(table1);
    }
}
