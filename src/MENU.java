import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// Class representing the menu of the library application
public class MENU extends JDialog {
    // Components for the user interface
    private JButton BOOKSAVAILABLEButton; // Button to display available books
    private JButton SEARCHBOOKSButton; // Button to search for books
    private JButton RETURNBORROWEDBOOKSButton; // Button to return borrowed books
    private JButton CHECKOUTBOOKSButton; // Button to checkout books
    private JButton ADDBOOKSButton; // Button to add books
    private JPanel MenuBox; // Panel to contain the menu buttons
    private JButton ADDUSERButton; // Button to add users
    private JFrame frame; // Reference to the parent frame

    // Constructor for the MENU dialog
    public MENU(JFrame parent) {
        super(parent);
        setTitle("Library Menu");
        setContentPane(MenuBox);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        // ActionListener for the ADDUSERButton
        ADDUSERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opening UserInfo dialog when the button is clicked
                UserInfo userInfo = new UserInfo(null);
                userInfo.setVisible(true);
            }
        });

        // ActionListener for the ADDBOOKSButton
        ADDBOOKSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opening BOOK dialog when the button is clicked
                BOOK addBooks = new BOOK(null);
                addBooks.setVisible(true);
            }
        });

        // ActionListener for the BOOKSAVAILABLEButton
        BOOKSAVAILABLEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opening DISPLAY dialog to show available books
                DISPLAY dispBook = new DISPLAY(null);
                dispBook.setVisible(true);
            }
        });

        // ActionListener for the RETURNBORROWEDBOOKSButton
        RETURNBORROWEDBOOKSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opening BORROWED_BOOKS dialog to return borrowed books
                BORROWED_BOOKS borrowedBooks = new BORROWED_BOOKS(null);
                borrowedBooks.setVisible(true);
            }
        });

        // ActionListener for the CHECKOUTBOOKSButton
        CHECKOUTBOOKSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opening CHECKOUT dialog to check out books
                CHECKOUT checkout = new CHECKOUT(null);
                checkout.setVisible(true);
            }
        });

        // ActionListener for the SEARCHBOOKSButton
        SEARCHBOOKSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Opening SEARCH dialog to search for books
                SEARCH search = new SEARCH(null);
                search.setVisible(true);
            }
        });

        // Making the dialog visible
        setVisible(true);
    }
}
