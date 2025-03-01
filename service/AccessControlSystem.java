package service;

import model.*;
import java.util.*;

public class AccessControlSystem implements CardManagementInterface {
    private HashMap<String, AccessCard> cards;
    private List<AuditLog> auditLogs;
    private HashSet<String> selectedRooms;

    public AccessControlSystem() {
        cards = new HashMap<>();
        auditLogs = new ArrayList<>();
        selectedRooms = new HashSet<>();
    }

    public AccessCard getCard(String cardID) {
        return cards.getOrDefault(cardID, null);
    }

    public void addCard(String cardID, String floor, String name, String room) {
        if (cards.containsKey(cardID)) {
            System.out.println("Error: Card ID " + cardID + " already exists!");
            return;
        }

        AccessCard newCard = new AccessCard(cardID, floor, room, name);
        cards.put(cardID, newCard);

        String roomKey = floor + " - " + room;
        selectedRooms.add(roomKey);

        AddCardLog log = new AddCardLog(cardID, name, floor, room);
        auditLogs.add(log);
    }

    public void modifyCard(String cardID, String newFloor, String newRoom) {
        AccessCard card = getCard(cardID);
        if (card == null) {
            System.out.println("Error: Card with ID " + cardID + " does not exist.");
            return;
        }

        String oldFloor = card.getFloor();
        String oldRoom = card.getRoom();
        String oldName = card.getName();

        String oldRoomKey = oldFloor + " - " + oldRoom;
        selectedRooms.remove(oldRoomKey);

        card.setFloor(newFloor);
        card.setRoom(newRoom);

        String newRoomKey = newFloor + " - " + newRoom;
        selectedRooms.add(newRoomKey);

        ModifyCardLog log = new ModifyCardLog(cardID, oldName, oldFloor, oldRoom, newFloor, newRoom);
        auditLogs.add(log);
    }

    public void revokeCard(String cardID) {
        AccessCard card = getCard(cardID);
        if (card == null) {
            System.out.println("Error: Card with ID " + cardID + " does not exist.");
            return;
        }

        if (Objects.nonNull(card.getRoom())) {
            String roomKey = card.getFloor() + " - " + card.getRoom();
            selectedRooms.remove(roomKey);
            cards.remove(cardID);
        }

        RevokeCardLog log = new RevokeCardLog(cardID, card.getName(), card.getFloor(), card.getRoom());
        auditLogs.add(log);
    }


    public boolean checkAccess(String cardID, String requestedRoom) {
        AccessCard card = getCard(cardID);
        if (card != null && card.isActive() && card.canAccessRoom(requestedRoom)) {
            auditLogs.add(new CardModification(cardID, card.getName(), "Checked Access", card.getFloor(), requestedRoom));
            return true;
        }
        return false;
    }

    public String getAccessMessage(String cardID, String room) {
        AccessCard card = getCard(cardID);
        if (card != null && card.isActive()) {
            return checkAccess(cardID, room) ? "Access Granted to " + room : "Access Denied to " + room;
        }
        return "Card not found or inactive.";
    }

    public boolean hasCard(String cardID) {
        return cards.containsKey(cardID);
    }

    public String getCardHistory(String cardID) {
        StringBuilder history = new StringBuilder();
        for (AuditLog log : auditLogs) {
            if (log.getCardID().equals(cardID)) {
                history.append(log.logEvent()).append("\n");
            }
        }
        return history.toString().isEmpty() ? "No history found for Card ID: " + cardID : history.toString();
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

    public HashSet<String> getSelectedRooms() {
        return selectedRooms;
    }
}
