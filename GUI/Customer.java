package GUI;

import service.AccessControlSystem;
import javax.swing.*;
import java.awt.*;
import model.AccessCard;

public class Customer {
    private JFrame frame;
    private JPanel cardPanel;
    private AccessControlSystem accessControlSystem;

    public Customer(JFrame frame, JPanel cardPanel, AccessControlSystem accessControlSystem) {
        this.frame = frame;
        this.cardPanel = cardPanel;
        this.accessControlSystem = accessControlSystem;
    }

    public void showCustomerView() {
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BoxLayout(customerPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome, User", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        inputPanel.add(new JLabel("Enter ID:"));
        JTextField idField = new JTextField(20);
        inputPanel.add(idField);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Selection"));

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            String userId = idField.getText().trim();

            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AccessCard userCard = accessControlSystem.getCard(userId);

            if (userCard == null) {
                JOptionPane.showMessageDialog(frame, "ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame,
                    "ID: " + userCard.getUserId() +
                            "\nName: " + userCard.getName() +
                            "\nFloor: " + userCard.getFloor() +
                            "\nRoom: " + userCard.getRoom() +
                            "\nExpiry Date: " + userCard.getExpiryDate(),
                    "User Information", JOptionPane.INFORMATION_MESSAGE);
        });


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        customerPanel.add(welcomeLabel);
        customerPanel.add(inputPanel);
        customerPanel.add(buttonPanel);

        cardPanel.add(customerPanel, "Customer");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Customer");
    }
}
