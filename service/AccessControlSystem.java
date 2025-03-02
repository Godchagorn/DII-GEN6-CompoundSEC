package service;

import model.*;
import java.util.*;
import java.time.LocalDate;

public abstract class AccessControlSystem implements CardManagementInterface {
    protected HashMap<String, AccessCard> cards;
    protected List<AuditLog> auditLogs;
    protected HashSet<String> selectedRooms;

    public AccessControlSystem() {
        cards = new HashMap<>();
        auditLogs = new ArrayList<>();
        selectedRooms = new HashSet<>();
    }

    @Override
    public AccessCard getCard(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            System.out.println("❌ Error: User ID is null or empty!");
            return null;
        }

        if (cards == null) {
            System.out.println("❌ Error: cards HashMap is not initialized!");
            return null;
        }

        if (!cards.containsKey(userId)) {
            System.out.println("❌ Error: User ID " + userId + " not found! Available IDs: " + cards.keySet());
            return null;
        }

        return cards.get(userId);
    }



    public HashSet<String> getSelectedRooms() {
        return selectedRooms;
    }

    public boolean hasCard(String userId) {
        return cards.containsKey(userId);
    }

    @Override
    public void addCard(String cardID, String userId, String floor, String name, String room, LocalDate expiryDate) {
        if (hasCard(userId)) {
            System.out.println("Error: User ID " + userId + " already has a card!");
            return;
        }

        AccessCard newCard = new AccessCard(cardID, userId, floor, room, name, expiryDate);
        cards.put(userId, newCard);

        selectedRooms.add(floor + " - " + room);
        auditLogs.add(new AddCardLog(cardID, name, floor, room, expiryDate));

        System.out.println("✅ Card added: " + newCard);
    }


    @Override
    public void modifyCard(String userId, String newFloor, String newRoom, LocalDate newExpiryDate) {
        if (!hasCard(userId)) {
            System.out.println("Error: User ID " + userId + " not found!");
            return;
        }

        AccessCard card = getCard(userId);
        String oldFloor = card.getFloor();
        String oldRoom = card.getRoom();

        card.setFloor(newFloor);
        card.setRoom(newRoom);
        card.setExpiryDate(newExpiryDate);

        selectedRooms.remove(oldFloor + " - " + oldRoom);
        selectedRooms.add(newFloor + " - " + newRoom);

        auditLogs.add(new ModifyCardLog(card.getCardID(), card.getName(), oldFloor, oldRoom, newFloor, newRoom, newExpiryDate));

        System.out.println("✅ Card modified: " + card);
    }

    @Override
    public void revokeCard(String userId) {
        AccessCard card = getCard(userId);
        if (card == null) {
            System.out.println("Error: Card with User ID " + userId + " does not exist.");
            return;
        }

        selectedRooms.remove(card.getFloor() + " - " + card.getRoom());
        cards.remove(userId);
        auditLogs.add(new RevokeCardLog(card.getCardID(), card.getName(), card.getFloor(), card.getRoom(), card.getExpiryDate()));

        System.out.println("✅ Card revoked: " + userId);
    }

    public String getAuditLogs() {
        if (auditLogs.isEmpty()) {
            return "No logs available.";
        }

        StringBuilder logs = new StringBuilder();
        for (AuditLog log : auditLogs) {
            logs.append(log.logEvent()).append("\n");
        }
        return logs.toString();
    }
}
