import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserInfo extends JDialog {
    // Variables to store user information
    int userId;
    String name;
    String contact;
    BOOK borrowed; // Assuming BOOK class is defined elsewhere

    // Components for the user interface
    private JTextField tfName;
    private JTextField textField2; // Assuming this should represent the user ID
    private JTextField textField3; // Assuming this should represent the contact info
    private JButton btnRegister;
    private JLabel tfID; // Assuming this should display the user ID
    private JLabel tfContact; // Assuming this should display the contact info
    private JPanel register;
    private JButton cancelButton;

    // Constructor for UserInfo dialog
    public UserInfo(JFrame parent) {
        super(parent);
        setTitle("Become member of library ");
        setContentPane(register);
        setMinimumSize(new Dimension(450, 474));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(parent);

        // ActionListener for the register button


        btnRegister.addActionListener(e -> {
            name = tfName.getText();
            String userIdText = textField2.getText();
            contact = textField3.getText();

            if (name.isEmpty() || userIdText.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill out all fields");
                return; // Exit the method early if any field is empty
            }
            //checking if the user id is valid
            try {
                userId = Integer.parseInt(userIdText);

                // Creating a library instance to add user
                LIBRARY library = new LIBRARY();
                boolean success = library.addUsers(name, contact, userId);

                // Displaying appropriate message based on the success of adding user
                if (success) {
                    JOptionPane.showMessageDialog(null, "User added successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "User not added");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid user ID");
            }
        });

        // ActionListener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Closing the dialog
                dispose();
            }
        });
    }
}
