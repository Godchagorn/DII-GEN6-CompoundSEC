import service.AccessControlSystem;
import javax.swing.*;
import java.awt.*;

public class Main {
    private AccessControlSystem system;
    private JFrame frame;
    private JPanel cardPanel;
    private JTextField cardIDField, accessLevelField;
    private JTextArea logArea;
    private JTextArea auditLogArea;

    public Main() {
        system = new AccessControlSystem();
        frame = new JFrame("Access Control System");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel();
        cardPanel.setLayout(new CardLayout());

        JPanel selectionPanel = new JPanel();
        JButton adminButton = new JButton("Admin");
        JButton customerButton = new JButton("Customer");

        adminButton.addActionListener(e -> showAdminView());
        customerButton.addActionListener(e -> showCustomerView());

        selectionPanel.add(adminButton);
        selectionPanel.add(customerButton);

        cardPanel.add(selectionPanel, "Selection");
        frame.add(cardPanel);

        frame.setVisible(true);
    }

    private void showAdminView() {
        cardPanel.removeAll();

        JPanel adminPanel = new JPanel(new GridLayout(7, 2));

        adminPanel.add(new JLabel("Card ID:"));
        cardIDField = new JTextField();
        adminPanel.add(cardIDField);

        adminPanel.add(new JLabel("Access Level:"));
        accessLevelField = new JTextField();
        adminPanel.add(accessLevelField);

        JButton addButton = new JButton("Add Card");
        JButton modifyButton = new JButton("Modify Card");
        JButton revokeButton = new JButton("Revoke Card");
        JButton checkButton = new JButton("Check Access");
        JButton viewLogButton = new JButton("View Audit Log");

        logArea = new JTextArea(5, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        adminPanel.add(addButton);
        adminPanel.add(modifyButton);
        adminPanel.add(revokeButton);
        adminPanel.add(checkButton);
        adminPanel.add(viewLogButton);
        adminPanel.add(scrollPane);

        addButton.addActionListener(e -> {
            String cardID = cardIDField.getText();
            String accessLevel = accessLevelField.getText();
            if (system.containsCard(cardID)) {
                logArea.append("Error: Card " + cardID + " already exists!\n");
            } else {
                system.addCard(cardID, accessLevel);
                logArea.append("Card " + cardID + " added with access: " + accessLevel + "\n");
            }
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
            String requestedArea = accessLevelField.getText();
            boolean access = system.checkAccess(cardID, requestedArea);
            String accessMessage = system.getAccessMessage(cardID);
            logArea.append("Card " + cardID + " access to " + requestedArea + ": " + (access ? "Granted" : "Denied") + "\n");
            logArea.append(accessMessage + "\n");
        });

        viewLogButton.addActionListener(e -> showAuditLogPopup());

        cardPanel.add(adminPanel, "Admin");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Admin");

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    private void showAuditLogPopup() {
        JFrame logFrame = new JFrame("Audit Log");
        logFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String logs = system.getAuditLogs();
        if (logs.isEmpty()) {
            logs = "No audit logs available.";
        }

        JTextArea logTextArea = new JTextArea(logs);
        logTextArea.setEditable(false);
        logTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        logTextArea.setSize(logTextArea.getPreferredSize());

        JScrollPane scrollPane = new JScrollPane(logTextArea);

        logFrame.add(scrollPane);
        logFrame.pack(); //
        logFrame.setLocationRelativeTo(frame);
        logFrame.setVisible(true);
    }

    private void showCustomerView() {
        cardPanel.removeAll();

        JPanel customerPanel = new JPanel(new BorderLayout());
        JButton customerCheckButton = new JButton("Check Access");

        customerCheckButton.addActionListener(e -> {
            String cardID = JOptionPane.showInputDialog("Enter your Card ID:");
            String requestedArea = JOptionPane.showInputDialog("Enter requested Access Level:");
            boolean access = system.checkAccess(cardID, requestedArea);
            String accessMessage = system.getAccessMessage(cardID);
            JOptionPane.showMessageDialog(null, "Access " + (access ? "Granted" : "Denied") + "\n" + accessMessage);
        });

        customerPanel.add(customerCheckButton, BorderLayout.CENTER);

        cardPanel.add(customerPanel, "Customer");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Customer");

        cardPanel.revalidate();
        cardPanel.repaint();
    }

    public static void main(String[] args) {
        new Main();
    }
}
