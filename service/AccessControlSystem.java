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

    private AccessBehavior getAccessBehavior(String accessLevel) {
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
        AccessCard newCard = new AccessCard(cardID, getAccessBehavior(accessLevel));

        if (cards.containsKey(cardID)) {
            System.out.println("Error: Card ID " + cardID + " already exists!");
            return;
        }

        cards.put(cardID, newCard);
        auditLogs.add(new CardModification(cardID, "Added", accessLevel));
    }

    @Override
    public void modifyCard(String cardID, String newAccessLevel) {
        if (!cards.containsKey(cardID)) {
            System.out.println("Error: Card with ID " + cardID + " does not exist.");
            return;
        }
        cards.get(cardID).setAccessBehavior(getAccessBehavior(newAccessLevel), "Admin123");
        auditLogs.add(new CardModification(cardID, "Modified", newAccessLevel));
    }

    @Override
    public void revokeCard(String cardID) {
        if (!cards.containsKey(cardID)) {
            System.out.println("Error: Card with ID " + cardID + " does not exist.");
            return;
        }
        cards.get(cardID).deactivate();
        auditLogs.add(new CardModification(cardID, "Revoked", null));
    }

    public boolean checkAccess(String cardID, String requestedArea) {
        boolean accessGranted = false;

        if (cards.containsKey(cardID)) {
            AccessCard card = cards.get(cardID);

            if (card.isActive() && card.canAccess(requestedArea)) {
                accessGranted = true;
            }
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

    public String getAuditLogs() {
        StringBuilder logs = new StringBuilder();
        for (AuditLog log : auditLogs) {
            logs.append(log.logEvent()).append("\n");
        }
        return logs.toString();
    }

    public boolean hasCard(String cardID) {
        return cards.containsKey(cardID);
    }
}
