//File: SEARCH.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * A dialog window for searching books.
 */
public class SEARCH extends JDialog {
    private JTextField textField1;
    private JTable table1;
    private JLabel ENTERBOOKTITLELabel;
    private JButton SEARCHButton;
    private JPanel panel1;
    private JTextField textField2;
    private JLabel ENTERBOOKAUTHORLabel;

    /**
     * Constructs a new SEARCH dialog.
     *
     * @param parent The parent JFrame to which this dialog belongs.
     */
    public SEARCH(JFrame parent) {
        super(parent);
        setTitle("Search Books");
        setContentPane(panel1);
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(450, 474));
        setLocationRelativeTo(parent);
        initializeTable();
        // ActionListener for SEARCHButton
        SEARCHButton.addActionListener(e -> {
            if (textField1.getText().isEmpty() && textField2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the book title or author");
                return;
            }
            // Creating a library instance to search for books
            LIBRARY library = new LIBRARY();
            String title = textField1.getText();
            String author = textField2.getText();
            // Getting the list of books based on the search criteria
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0); // Clear the table
            library.searchBooks(title, author, table1);
        });

    }

    /**
     * Initializes the table with column names.
     */
    private void initializeTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Genre");
        model.addColumn("Availability");

        table1.setModel(model); // Set the table model
    }

}
