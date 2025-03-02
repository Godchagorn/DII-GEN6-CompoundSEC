package GUI;

import model.*;
import service.AccessControlSystem;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;

public class Admin {
    private JFrame frame;
    private JPanel cardPanel;
    private AccessControlSystem accessControlSystem;

    public Admin(JFrame frame, JPanel cardPanel, AccessControlSystem accessControlSystem) {
        this.frame = frame;
        this.cardPanel = cardPanel;
        this.accessControlSystem = accessControlSystem;
    }

    public void showAdminLoginView() {
        JPanel adminLoginPanel = new JPanel();
        adminLoginPanel.setLayout(new BoxLayout(adminLoginPanel, BoxLayout.Y_AXIS));

        JLabel adminLabel = new JLabel("Admin Login", JLabel.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Selection"));

        JButton nextButton = new JButton("Login");
        nextButton.addActionListener(e -> {
            String enteredName = nameField.getText().trim();
            String enteredID = idField.getText().trim();

            if (!"admin".equals(enteredName) || !"12345".equals(enteredID)) {
                JOptionPane.showMessageDialog(frame, "Invalid Admin Name or ID!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            } else {
                showAdminMainView();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        adminLoginPanel.add(adminLabel);
        adminLoginPanel.add(new JLabel("Name:"));
        adminLoginPanel.add(nameField);
        adminLoginPanel.add(new JLabel("ID:"));
        adminLoginPanel.add(idField);
        adminLoginPanel.add(buttonPanel);

        cardPanel.add(adminLoginPanel, "AdminLogin");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "AdminLogin");
    }

    private void showAdminMainView() {
        JPanel adminMainPanel = new JPanel();
        adminMainPanel.setLayout(new BoxLayout(adminMainPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new JLabel("Welcome to Admin", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 10, 10));

        JButton addCardButton = new JButton("Add Card");
        addCardButton.addActionListener(e -> showAddCardPopup());

        JButton modifyCardButton = new JButton("Modify Card");
        modifyCardButton.addActionListener(e -> modifyCard());

        JButton revokeCardButton = new JButton("Revoke Card");
        revokeCardButton.addActionListener(e -> revokeCard());

        JButton checkCardButton = new JButton("Check Card");
        checkCardButton.addActionListener(e -> checkCard());

        JButton viewAuditLogButton = new JButton("View Audit Log");
        viewAuditLogButton.addActionListener(e -> showAuditLog());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> ((CardLayout) cardPanel.getLayout()).show(cardPanel, "Selection"));

        buttonPanel.add(addCardButton);
        buttonPanel.add(modifyCardButton);
        buttonPanel.add(revokeCardButton);
        buttonPanel.add(checkCardButton);
        buttonPanel.add(viewAuditLogButton);
        buttonPanel.add(backButton);

        adminMainPanel.add(welcomeLabel);
        adminMainPanel.add(buttonPanel);

        cardPanel.add(adminMainPanel, "AdminMain");
        ((CardLayout) cardPanel.getLayout()).show(cardPanel, "AdminMain");
    }

    private void showAddCardPopup() {
        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField nameField = new JTextField();
        JTextField userIdField = new JTextField(); // ✅ Admin manually enters the user ID.

        String[] floors = {"Floor 1", "Floor 2", "Floor 3"};
        JComboBox<String> floorComboBox = new JComboBox<>(floors);
        JComboBox<String> roomComboBox = new JComboBox<>();

        JComboBox<Integer> dayComboBox = new JComboBox<>();
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        JComboBox<String> monthComboBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        });

        // ✅ Get the current date
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH); // 0-based index

        // ✅ Allow selection up to 5 years in the future
        for (int i = currentYear; i <= currentYear + 5; i++) {
            yearComboBox.addItem(i);
        }
        yearComboBox.setSelectedItem(currentYear);

        // ✅ Populate day selection
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }
        monthComboBox.setSelectedIndex(currentMonth);

        // ✅ Prevent selection of past dates
        yearComboBox.addActionListener(e -> updateDays(dayComboBox, (Integer) yearComboBox.getSelectedItem(), monthComboBox.getSelectedIndex() + 1));
        monthComboBox.addActionListener(e -> updateDays(dayComboBox, (Integer) yearComboBox.getSelectedItem(), monthComboBox.getSelectedIndex() + 1));

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("User ID:"));  // ✅ Admin manually enters User ID
        panel.add(userIdField);
        panel.add(new JLabel("Floor:"));
        panel.add(floorComboBox);
        panel.add(new JLabel("Room:"));
        panel.add(roomComboBox);
        panel.add(new JLabel("Expiry Date:"));
        panel.add(dayComboBox);
        panel.add(monthComboBox);
        panel.add(yearComboBox);

        floorComboBox.addActionListener(e -> updateRoomList(roomComboBox, floorComboBox.getSelectedIndex()));

        // ✅ Set default floor and populate room list
        floorComboBox.setSelectedIndex(0);
        updateRoomList(roomComboBox, 0);
        updateDays(dayComboBox, currentYear, currentMonth + 1);

        int result = JOptionPane.showConfirmDialog(frame, panel, "Add Card", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String userId = userIdField.getText().trim(); // ✅ User ID manually entered by Admin

            // ✅ Validate name input (only letters and spaces)
            if (!name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Name (only letters and spaces)!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Validate User ID (must be 5-digit number)
            if (!userId.matches("\\d{5}")) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid User ID (exactly 5 digits)!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String floor = (String) floorComboBox.getSelectedItem();
            String room = (String) roomComboBox.getSelectedItem();

            if (room == null) {
                JOptionPane.showMessageDialog(frame, "Please select a valid Room!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int day = (Integer) dayComboBox.getSelectedItem();
            int month = monthComboBox.getSelectedIndex() + 1;
            int year = (Integer) yearComboBox.getSelectedItem();

            LocalDate expiryDate = LocalDate.of(year, month, day);

            // ✅ Prevent expired dates from being selected
            if (expiryDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(frame, "Expiry date cannot be in the past!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ✅ Use the Admin-entered User ID instead of generating one
            accessControlSystem.addCard(userId, userId, floor, name, room, expiryDate);
            JOptionPane.showMessageDialog(frame,
                    "Card Added Successfully!\nUser ID: " + userId,
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            updateRoomList(roomComboBox, floorComboBox.getSelectedIndex());
        }
    }


    private void updateDays(JComboBox<Integer> dayComboBox, int selectedYear, int selectedMonth) {
        dayComboBox.removeAllItems();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, selectedYear);
        cal.set(Calendar.MONTH, selectedMonth - 1);
        int maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= maxDays; i++) {
            dayComboBox.addItem(i);
        }

        // ✅ ลบวันที่ย้อนหลังออกจากตัวเลือก
        if (selectedYear == LocalDate.now().getYear() && selectedMonth == LocalDate.now().getMonthValue()) {
            int currentDay = LocalDate.now().getDayOfMonth();
            for (int i = 1; i < currentDay; i++) {
                dayComboBox.removeItem(i);
            }
        }
        dayComboBox.setSelectedIndex(0);
    }



    private void updateRoomList(JComboBox<String> roomComboBox, int floorIndex) {
        String[][] rooms = {
                {"Meeting 1", "Meeting 2", "Meeting 3", "Even 1", "Even 2"},
                {"Standard 1", "Standard 2", "Standard 3", "Standard 4", "Standard 5"},
                {"VIP 1", "VIP 2", "VIP 3", "VIP 4", "VIP 5"}
        };

        roomComboBox.removeAllItems();

        if (floorIndex < 0 || floorIndex >= rooms.length) {
            JOptionPane.showMessageDialog(frame, "Invalid floor selection!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        HashSet<String> occupiedRooms = accessControlSystem.getSelectedRooms(); // ✅ ดึงรายชื่อห้องที่ถูกเลือกไปแล้ว
        for (String room : rooms[floorIndex]) {
            String roomKey = "Floor " + (floorIndex + 1) + " - " + room;

            if (!occupiedRooms.contains(roomKey)) { // ✅ แสดงเฉพาะห้องที่ยังไม่ถูกเลือก
                roomComboBox.addItem(room);
            }
        }

        if (roomComboBox.getItemCount() > 0) {
            roomComboBox.setSelectedIndex(0);
        }
    }


    private void showAuditLog() {
        JTextArea auditLogArea = new JTextArea(15, 40);
        auditLogArea.setEditable(false);

        String auditLogsContent = accessControlSystem.getAuditLogs();
        if (auditLogsContent.trim().isEmpty() || auditLogsContent.equals("No logs available.")) {
            auditLogsContent = "No logs recorded yet.";
        }

        auditLogArea.setText(auditLogsContent);

        JOptionPane.showMessageDialog(frame, new JScrollPane(auditLogArea), "Audit Log", JOptionPane.INFORMATION_MESSAGE);
    }



    private void modifyCard() {
        String userId = JOptionPane.showInputDialog(frame, "Enter User ID to Modify:");
        if (userId == null || userId.trim().isEmpty()) return;

        if (!accessControlSystem.hasCard(userId)) {
            JOptionPane.showMessageDialog(frame, "ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AccessCard card = accessControlSystem.getCard(userId);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JTextField nameField = new JTextField(card.getName());

        String[] floors = {"Floor 1", "Floor 2", "Floor 3"};
        JComboBox<String> floorComboBox = new JComboBox<>(floors);
        floorComboBox.setSelectedItem(card.getFloor());

        JComboBox<String> roomComboBox = new JComboBox<>();
        updateRoomList(roomComboBox, floorComboBox.getSelectedIndex());
        roomComboBox.setSelectedItem(card.getRoom());

        JComboBox<Integer> dayComboBox = new JComboBox<>();
        JComboBox<Integer> yearComboBox = new JComboBox<>();
        JComboBox<String> monthComboBox = new JComboBox<>(new String[]{
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        });

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        for (int i = currentYear; i <= currentYear + 10; i++) {
            yearComboBox.addItem(i);
        }
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }

        LocalDate expiryDate = card.getExpiryDate();
        dayComboBox.setSelectedItem(expiryDate.getDayOfMonth());
        monthComboBox.setSelectedIndex(expiryDate.getMonthValue() - 1);
        yearComboBox.setSelectedItem(expiryDate.getYear());

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Floor:"));
        panel.add(floorComboBox);
        panel.add(new JLabel("Room:"));
        panel.add(roomComboBox);
        panel.add(new JLabel("Expiry Date:"));
        panel.add(dayComboBox);
        panel.add(monthComboBox);
        panel.add(yearComboBox);

        // ✅ Update room list when a floor is selected
        floorComboBox.addActionListener(e -> updateRoomList(roomComboBox, floorComboBox.getSelectedIndex()));

        int result = JOptionPane.showConfirmDialog(frame, panel, "Modify Card", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String newName = nameField.getText().trim();
            String newFloor = (String) floorComboBox.getSelectedItem();
            String newRoom = (String) roomComboBox.getSelectedItem();

            if (newRoom == null) {
                JOptionPane.showMessageDialog(frame, "Please select a valid Room!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int day = (Integer) dayComboBox.getSelectedItem();
            int month = monthComboBox.getSelectedIndex() + 1;
            int year = (Integer) yearComboBox.getSelectedItem();

            LocalDate newExpiryDate = LocalDate.of(year, month, day);

            accessControlSystem.modifyCard(userId, newFloor, newRoom, newExpiryDate);
            JOptionPane.showMessageDialog(frame, "Card Modified Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void revokeCard() {
        String userId = JOptionPane.showInputDialog(frame, "Enter User ID to Revoke:");
        if (userId == null || userId.trim().isEmpty()) return;

        if (!accessControlSystem.hasCard(userId)) {
            JOptionPane.showMessageDialog(frame, "ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ ดึงข้อมูลบัตร
        AccessCard card = accessControlSystem.getCard(userId);
        String name = card.getName();
        String floor = card.getFloor();
        String room = card.getRoom();
        LocalDate expiryDate = card.getExpiryDate();

        // ✅ สร้าง Panel ที่มีข้อมูลการ์ด + ปุ่ม Revoke
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("User ID: " + userId));
        panel.add(new JLabel("Name: " + name));
        panel.add(new JLabel("Floor: " + floor));
        panel.add(new JLabel("Room: " + room));
        panel.add(new JLabel("Expiry Date: " + expiryDate));

        // ✅ แสดง Pop-up ยืนยันการลบ
        int confirm = JOptionPane.showConfirmDialog(frame, panel,
                "Confirm Revoke", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            accessControlSystem.revokeCard(userId);
            JOptionPane.showMessageDialog(frame, "Card Revoked Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private void checkCard() {
        String userId = JOptionPane.showInputDialog(frame, "Enter User ID to Check:");

        if (userId == null || userId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        AccessCard card = accessControlSystem.getCard(userId);

        // ✅ If no card is found, notify the admin
        if (card == null) {
            JOptionPane.showMessageDialog(frame, "No card found for ID: " + userId, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ✅ If the card exists, display its details
        JOptionPane.showMessageDialog(frame,
                "ID: " + userId +
                        "\nName: " + card.getName() +
                        "\nFloor: " + card.getFloor() +
                        "\nRoom: " + card.getRoom() +
                        "\nExpiry Date: " + card.getExpiryDate(),
                "Card Information", JOptionPane.INFORMATION_MESSAGE);
    }


}
