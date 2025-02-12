import service.AccessControlSystem;
import javax.swing.*;
import java.awt.*;

public class Main {
    private AccessControlSystem system;
    private JFrame frame;
    private JTextField cardIDField, accessLevelField;
    private JTextArea logArea;

    public Main() {
        system = new AccessControlSystem();
        frame = new JFrame("Access Control System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(6, 2));

        frame.add(new JLabel("Card ID:"));
        cardIDField = new JTextField();
        frame.add(cardIDField);

        frame.add(new JLabel("Access Level:"));
        accessLevelField = new JTextField();
        frame.add(accessLevelField);

        JButton addButton = new JButton("Add Card");
        JButton modifyButton = new JButton("Modify Card");
        JButton revokeButton = new JButton("Revoke Card");
        JButton checkButton = new JButton("Check Access");
        JButton customerButton = new JButton("Customer Access");

        logArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(logArea);

        frame.add(addButton);
        frame.add(modifyButton);
        frame.add(revokeButton);
        frame.add(checkButton);
        frame.add(customerButton);
        frame.add(scrollPane);

        addButton.addActionListener(e -> {
            String cardID = cardIDField.getText();
            String accessLevel = accessLevelField.getText();
            system.modifyCard(cardID, accessLevel);
            logArea.append("Card " + cardID + " Masked: ****" + cardID.substring(cardID.length() - 4) + ")\n");
        });

        addButton.addActionListener(e -> {
            String cardID = cardIDField.getText();
            String accessLevel = accessLevelField.getText();
            system.addCard(cardID, accessLevel);
            logArea.append("Card " + cardID + " added with access: " + accessLevel + "\n");
        });

        modifyButton.addActionListener(e -> {
            String cardID = cardIDField.getText();
            String accessLevel = accessLevelField.getText();
            system.modifyCard(cardID, accessLevel);
            logArea.append("Card " + cardID + " modified to access: " + accessLevel + "\n");
        });

        revokeButton.addActionListener(e -> {
            String cardID = cardIDField.getText();
            system.revokeCard(cardID);
            logArea.append("Card " + cardID + " revoked.\n");
        });

        checkButton.addActionListener(e -> {
            String cardID = cardIDField.getText();
            String accessLevel = accessLevelField.getText();
            boolean access = system.checkAccess(cardID, accessLevel);
            logArea.append("Card " + cardID + " access to " + accessLevel + ": " + (access ? "Granted" : "Denied") + "\n");
        });

        customerButton.addActionListener(e -> {
            String cardID = JOptionPane.showInputDialog("Enter your Card ID:");
            String accessLevel = JOptionPane.showInputDialog("Enter requested Access Level:");
            boolean access = system.checkAccess(cardID, accessLevel);
            JOptionPane.showMessageDialog(null, "Access " + (access ? "Granted" : "Denied"));
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}