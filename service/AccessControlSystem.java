package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccessControlSystem implements CardManagementInterface {
    private HashMap<String, AccessCard> cards;
    private List<AuditLog> auditLogs;

    public AccessControlSystem() {
        cards = new HashMap<>();
        auditLogs = new ArrayList<>();
    }

    private AccessLevel getAccessLevel(String accessLevel) {
        switch (accessLevel.toLowerCase()) {
            case "medium floor":
                return new MediumFloorAccess();
            case "high floor":
                return new HighFloorAccess();
            default:
                return new LowFloorAccess();
        }
    }

    @Override
    public void addCard(String cardID, String accessLevel) {
        if (cards.containsKey(cardID)) {
            System.out.println("Error: Card with ID " + cardID + " already exists!");
            return;
        }
        AccessCard newCard = new AccessCard(cardID, getAccessLevel(accessLevel));
        cards.put(cardID, newCard);
        auditLogs.add(new CardModification(cardID, "Added", accessLevel));
    }

    @Override
    public void modifyCard(String cardID, String newAccessLevel) {
        if (cards.containsKey(cardID)) {
            cards.get(cardID).setAccessLevel(getAccessLevel(newAccessLevel), "Admin123");
            auditLogs.add(new CardModification(cardID, "Modified", newAccessLevel));
        } else {
            System.out.println("Error: Card with ID " + cardID + " not found!");
        }
    }

    @Override
    public void revokeCard(String cardID) {
        if (cards.containsKey(cardID)) {
            cards.get(cardID).deactivate();
            auditLogs.add(new CardModification(cardID, "Revoked", null));
        }else {
            System.out.println("Error: Card with ID " + cardID + " not found!");
        }
    }

    public boolean checkAccess(String cardID, String requestedArea) {
        boolean accessGranted = false;
        if (cards.containsKey(cardID) && cards.get(cardID).isActive()) {
            accessGranted = cards.get(cardID).canAccess(requestedArea);
        }
        auditLogs.add(new AccessAttempt(cardID, requestedArea, accessGranted));
        return accessGranted;
    }

    public String getAccessMessage(String cardID) {
        if (cards.containsKey(cardID) && cards.get(cardID).isActive()) {
            return cards.get(cardID).getAccessLevel();
        }
        return "Card not found or inactive.";
    }

    public void printAuditLogs() {
        for (AuditLog log : auditLogs) {
            System.out.println(log.logEvent());
        }
    }
        public String getAuditLogs() {
            StringBuilder logs = new StringBuilder();
            for (AuditLog log : auditLogs) {
                logs.append(log.logEvent()).append("\n");
            }
            return logs.toString();
    }

    public boolean containsCard(String cardID) {
        return cards.containsKey(cardID);
    }

}