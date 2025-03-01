package GUI;

import model.*;
import service.AccessControlSystem;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Admin {
    private JFrame frame;
    private JPanel cardPanel;
    private JTextField cardIDField, nameField;
    private final String ADMIN_NAME = "admin";
    private final String ADMIN_ID = "12345";
    private HashSet<String> selectedRooms = new HashSet<>();
    private AccessControlSystem accessControlSystem;

    public Admin(JFrame frame, JPanel cardPanel, AccessControlSystem accessControlSystem) {
        this.frame = frame;
        this.cardPanel = cardPanel;
        this.accessControlSystem = accessControlSystem;
    }

    public void showAdminLoginView() {
        JPanel adminLoginPanel = new JPanel();
        adminLoginPanel.setLayout(new BoxLayout(adminLoginPanel, BoxLayout.Y_AXIS));

        JLabel adminLabel = new JLabel("Admin", JLabel.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 30));
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameField = new JTextField();
        cardIDField = new JTextField();

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");

        backButton.addActionListener(e -> ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Selection"));

        nextButton.addActionListener(e -> {
            String enteredName = nameField.getText().trim();
            String enteredID = cardIDField.getText().trim();

            if (enteredID.isEmpty() || enteredName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in both Name and ID!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!enteredName.equals(ADMIN_NAME) || !enteredID.equals(ADMIN_ID)) {
                JOptionPane.showMessageDialog(frame, "Invalid Admin Name or ID!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            } else {
                showAdminMainView();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        adminLoginPanel.add(Box.createVerticalGlue());
        adminLoginPanel.add(adminLabel);
        adminLoginPanel.add(Box.createVerticalStrut(20));
        adminLoginPanel.add(new JLabel("Name:"));
        adminLoginPanel.add(nameField);
        adminLoginPanel.add(new JLabel("ID:"));
        adminLoginPanel.add(cardIDField);
        adminLoginPanel.add(Box.createVerticalStrut(20));
        adminLoginPanel.add(buttonPanel);
        adminLoginPanel.add(Box.createVerticalGlue());

        cardPanel.add(adminLoginPanel, "AdminLogin");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "AdminLogin");
    }


    private void showAdminMainView() {
        JPanel adminMainPanel = new JPanel();
        adminMainPanel.setLayout(new BoxLayout(adminMainPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to Admin", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton addCardButton = new JButton("Add Card");
        addCardButton.addActionListener(e -> showAddCardPopup());
        buttonPanel.add(addCardButton);

        JButton modifyCardButton = new JButton("Modify Card");
        modifyCardButton.addActionListener(e -> modifyCard());
        buttonPanel.add(modifyCardButton);

        JButton revokeCardButton = new JButton("Revoke Card");
        revokeCardButton.addActionListener(e -> revokeCard());
        buttonPanel.add(revokeCardButton);

        JButton checkCardButton = new JButton("Check Card");
        checkCardButton.addActionListener(e -> checkCard());
        buttonPanel.add(checkCardButton);

        JButton viewAuditLogButton = new JButton("View Audit Log");
        viewAuditLogButton.addActionListener(e -> showAuditLog());
        buttonPanel.add(viewAuditLogButton);


        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Selection"));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);

        adminMainPanel.add(Box.createVerticalGlue());
        adminMainPanel.add(welcomeLabel);
        adminMainPanel.add(Box.createVerticalStrut(20));
        adminMainPanel.add(buttonPanel);
        adminMainPanel.add(bottomPanel);
        adminMainPanel.add(Box.createVerticalGlue());

        cardPanel.add(adminMainPanel, "AdminMain");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "AdminMain");
    }


    private void showAddCardPopup() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Enter Name:"));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Enter ID:"));
        JTextField idField = new JTextField();
        panel.add(idField);

        panel.add(new JLabel("Select Floor:"));
        String[] floors = {"Floor 1", "Floor 2", "Floor 3"};
        JComboBox<String> floorComboBox = new JComboBox<>(floors);
        panel.add(floorComboBox);

        panel.add(new JLabel("Select Room:"));
        JComboBox<String> roomComboBox = new JComboBox<>();
        panel.add(roomComboBox);

        String[][] rooms = {
                {"Meeting 1", "Meeting 2", "Meeting 3", "Event 1", "Event 2"},
                {"Standard 1", "Standard 2", "Standard 3", "Standard 4", "Standard 5"},
                {"VIT 1", "VIT 2", "VIT 3", "VIT 4", "VIT 5"}
        };

        floorComboBox.addActionListener(e -> updateRoomList(roomComboBox, floorComboBox.getSelectedIndex(), rooms));
        floorComboBox.setSelectedIndex(0);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add Card", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String userId = idField.getText().trim();
            String floor = (String) floorComboBox.getSelectedItem();
            String room = (String) roomComboBox.getSelectedItem();
            String roomKey = floor + " - " + room;

            if (name.isEmpty() || !name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Name (only letters)!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userId.isEmpty() || !userId.matches("\\d{5}")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid ID (5 digits)!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedRooms.contains(roomKey)) {
                JOptionPane.showMessageDialog(frame, "This room has already been assigned!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (accessControlSystem.hasCard(userId)) {
                JOptionPane.showMessageDialog(frame, "This ID already has a card!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            selectedRooms.add(roomKey);

            String randomId = String.format("%05d", new Random().nextInt(100000));

            accessControlSystem.addCard(randomId, floor, name, room);

            JOptionPane.showMessageDialog(frame,
                    "Card added for " + name + "\nEntered ID: " + userId + "\nGenerated ID: " + randomId +
                            "\nLocation: " + floor + " - " + room,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            updateRoomList(roomComboBox, floorComboBox.getSelectedIndex(), rooms);
        }
    }


    private void modifyCard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Enter ID:"));
        JTextField idField = new JTextField();
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Modify Card", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String userId = idField.getText().trim();

            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!accessControlSystem.hasCard(userId)) {
                JOptionPane.showMessageDialog(frame, "ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AccessCard card = accessControlSystem.getCard(userId);
            String name = card.getName();
            String floor = card.getFloor();
            String room = "Unknown";

            JPanel modifyPanel = new JPanel();
            modifyPanel.setLayout(new BoxLayout(modifyPanel, BoxLayout.Y_AXIS));

            modifyPanel.add(new JLabel("Name:"));
            JTextField nameField = new JTextField(name);
            modifyPanel.add(nameField);

            modifyPanel.add(new JLabel("Floor:"));
            JComboBox<String> floorComboBox = new JComboBox<>(new String[]{"Floor 1", "Floor 2", "Floor 3"});
            modifyPanel.add(floorComboBox);

            modifyPanel.add(new JLabel("Room:"));
            JComboBox<String> roomComboBox = new JComboBox<>();
            modifyPanel.add(roomComboBox);

            floorComboBox.addActionListener(e -> updateRoomList(roomComboBox, floorComboBox.getSelectedIndex(), room));

            floorComboBox.setSelectedItem(floor);
            updateRoomList(roomComboBox, floorComboBox.getSelectedIndex(), room);

            int modifyResult = JOptionPane.showConfirmDialog(frame, modifyPanel, "Modify Card", JOptionPane.OK_CANCEL_OPTION);

            if (modifyResult == JOptionPane.OK_OPTION) {
                String newName = nameField.getText().trim();
                String newFloor = (String) floorComboBox.getSelectedItem();
                String newRoom = (String) roomComboBox.getSelectedItem();

                if (newName.isEmpty() || !newName.matches("[a-zA-Z\\s]+")) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid Name (only letters)!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (newFloor.isEmpty() || newRoom.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid Floor and Room!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // ✅ ลบห้องเก่าของผู้ใช้จาก selectedRooms ก่อน
                String oldRoomKey = floor + " - " + room;
                accessControlSystem.getSelectedRooms().remove(oldRoomKey);

                // ✅ เพิ่มห้องใหม่เข้า selectedRooms
                String newRoomKey = newFloor + " - " + newRoom;
                accessControlSystem.getSelectedRooms().add(newRoomKey);

                accessControlSystem.modifyCard(userId, newFloor, newRoom);

                JOptionPane.showMessageDialog(frame, "Card details updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }



    private void updateRoomList(JComboBox<String> roomComboBox, int floorIndex, String selectedRoom) {
        String[][] rooms = {
                {"Meeting 1", "Meeting 2", "Meeting 3", "Event 1", "Event 2"},
                {"Standard 1", "Standard 2", "Standard 3", "Standard 4", "Standard 5"},
                {"VIT 1", "VIT 2", "VIT 3", "VIT 4", "VIT 5"}
        };

        roomComboBox.removeAllItems();

        HashSet<String> selectedRooms = accessControlSystem.getSelectedRooms(); // ✅ ดึงข้อมูลห้องที่ถูกเลือกไปแล้ว

        for (String room : rooms[floorIndex]) {
            String roomKey = "Floor " + (floorIndex + 1) + " - " + room;

            if (!selectedRooms.contains(roomKey) || room.equals(selectedRoom)) { // ✅ แสดงเฉพาะห้องที่ยังไม่ได้เลือก หรือเป็นห้องเดิมของผู้ใช้
                roomComboBox.addItem(room);
            }
        }
    }



    private void revokeCard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Enter ID:"));
        JTextField idField = new JTextField();
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Revoke Card", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String userId = idField.getText().trim();

            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!accessControlSystem.hasCard(userId)) {
                JOptionPane.showMessageDialog(frame, "ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            AccessCard card = accessControlSystem.getCard(userId);
            String name = card.getName();
            String floor = card.getFloor();
            String room = card.getRoom();

            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Name: " + name + "\nFloor: " + floor + "\nRoom: " + room +
                            "\n\nDo you want to revoke this card?",
                    "Confirm Revoke", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                accessControlSystem.revokeCard(userId);

                JOptionPane.showMessageDialog(frame, "Card revoked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


    private void updateRoomList(JComboBox<String> roomComboBox, int floorIndex, String[][] rooms) {
        roomComboBox.removeAllItems();

        HashSet<String> selectedRooms = accessControlSystem.getSelectedRooms();

        for (String room : rooms[floorIndex]) {
            String roomKey = "Floor " + (floorIndex + 1) + " - " + room;

            if (!selectedRooms.contains(roomKey)) {
                roomComboBox.addItem(room);
            }
        }
    }




    private void checkCard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Enter ID:"));
        JTextField idField = new JTextField();
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Check Card", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String userId = idField.getText().trim();

            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter an ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!accessControlSystem.hasCard(userId)) {
                JOptionPane.showMessageDialog(frame, "ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ดึงข้อมูลบัตรจาก AccessControlSystem
            AccessCard card = accessControlSystem.getCard(userId);
            String name = card.getName();
            String floor = card.getFloor();
            String room = card.getRoom();

            JOptionPane.showMessageDialog(frame,
                    "ID: " + userId + "\nName: " + name + "\nFloor: " + floor + "\nRoom: " + room,
                    "Card Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void showAuditLog() {
        JPanel auditLogPanel = new JPanel();
        auditLogPanel.setLayout(new BoxLayout(auditLogPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Audit Log", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea auditLogArea = new JTextArea(10, 30);
        auditLogArea.setEditable(false);

        // ดึงข้อมูล Log จาก AccessControlSystem
        String auditLogs = accessControlSystem.getAuditLogs();
        auditLogArea.setText(auditLogs);

        JScrollPane scrollPane = new JScrollPane(auditLogArea);
        auditLogPanel.add(scrollPane);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> ((CardLayout) cardPanel.getLayout()).show(cardPanel, "AdminMain"));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);

        auditLogPanel.add(Box.createVerticalStrut(20));
        auditLogPanel.add(bottomPanel);

        cardPanel.add(auditLogPanel, "AuditLog");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "AuditLog");
    }

}