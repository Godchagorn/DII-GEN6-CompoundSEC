import GUI.Admin;
import GUI.Customer;
import service.AccessControlSystem;
import javax.swing.*;
import java.awt.*;

public class Main {
    private JFrame frame;
    private JPanel cardPanel;
    private AccessControlSystem accessControlSystem;

    public Main() {
        frame = new JFrame("Access Control System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel(new CardLayout());
        frame.add(cardPanel);

        accessControlSystem = new AccessControlSystem();

        showSelectionView();
        frame.setVisible(true);
    }

    public void showSelectionView() {
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));

        JLabel systemLabel = new JLabel("SYSTEM", JLabel.CENTER);
        systemLabel.setFont(new Font("Arial", Font.BOLD, 50));
        systemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton adminButton = new JButton("Admin");
        JButton customerButton = new JButton("Customer");

        adminButton.addActionListener(e -> new Admin(frame, cardPanel, accessControlSystem).showAdminLoginView());
        customerButton.addActionListener(e -> new Customer(frame, cardPanel, accessControlSystem).showCustomerView());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(adminButton);
        buttonPanel.add(customerButton);

        selectionPanel.add(Box.createVerticalGlue());
        selectionPanel.add(systemLabel);
        selectionPanel.add(Box.createVerticalStrut(20));
        selectionPanel.add(buttonPanel);
        selectionPanel.add(Box.createVerticalGlue());

        cardPanel.add(selectionPanel, "Selection");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Selection");
    }

    public static void main(String[] args) {
        new Main();
    }
}
