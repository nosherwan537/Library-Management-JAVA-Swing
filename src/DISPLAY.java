//File: DISPLAY.java
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;


/**
 * A dialog window for displaying available books.
 */
public class DISPLAY extends JDialog {
    private JPanel panel1;
    private JTable table1;


    /**
     * Constructs a new DISPLAY dialog.
     *
     * @param parent The parent JFrame to which this dialog belongs.
     */
    public DISPLAY(JFrame parent) {
        super(parent);
        setTitle("Available Books");
        setContentPane(panel1);
        setMinimumSize(new Dimension(450, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(parent);
        initializeTable(); // Initialize the table to display available books
    }

    /**
     * Initializes the table to display available books.
     */
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

        // Populate the table with available books data
        LIBRARY library = new LIBRARY();
        library.displayBooks(table1);
    }

}