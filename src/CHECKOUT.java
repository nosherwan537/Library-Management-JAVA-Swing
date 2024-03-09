import javax.swing.*;
import java.awt.*;

// Class representing the dialog for checking out books
public class CHECKOUT extends JDialog {
    // Components for the user interface
    private JButton CHECKOUTButton; // Button to initiate book checkout
    private JTextField bookidtf; // Text field for entering book ID
    private JLabel CHECKOUTBOOKSLabel; // Label for the checkout books title
    private JPanel panelc; // Main panel
    private JPanel panel1; // Additional panel (not used)
    private JPanel panel2; // Additional panel (not used)


    // Constructor for CHECKOUT dialog
    public CHECKOUT(JFrame parent) {
        super(parent);
        setTitle("Checkout Books");
        setContentPane(panelc); // Set the main content pane
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        // ActionListener for the CHECKOUTButton
        CHECKOUTButton.addActionListener(e -> {
            if (bookidtf.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the book ID");
                return;
            }
            //checking if the book id is valid
            try {
                // Retrieve the book ID from the text field
                int bookId = Integer.parseInt(bookidtf.getText());
                // Create a library instance to handle book checkout
                LIBRARY library = new LIBRARY();
                boolean success = library.checkoutBooks(bookId);
                // Display appropriate message based on checkout success
                if (success) {
                    JOptionPane.showMessageDialog(null, "Book checked out successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Book not checked out");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid book ID");
            }
        });
    }
}
