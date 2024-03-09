import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SEARCH extends JDialog {
    private JTextField textField1;
    private JTable table1;
    private JLabel ENTERIDLabel;
    private JButton SEARCHButton;
    private JPanel panel1;
    public SEARCH (JFrame parent) {
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
            if(textField1.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter the book ID");
                return;
            }
            //checking if the book id is valid
            try{
            int bookId = Integer.parseInt(textField1.getText());
            LIBRARY library = new LIBRARY();
            boolean success = library.searchBooks(bookId, table1);
            if (success) {
                DefaultTableModel model = (DefaultTableModel) table1.getModel();
                model.setRowCount(0);
                library.searchBooks(bookId, table1);
            } else {
                JOptionPane.showMessageDialog(null, "NOT AVAILABLE");
            }
            }
            catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Please enter a valid book ID");
            }

        });

    }
    // Initialize the table with column names
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
