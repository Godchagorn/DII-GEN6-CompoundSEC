package service;

import model.AccessCard;

import java.time.LocalDate;

public interface CardManagementInterface {
    AccessCard getCard(String userId);

    void addCard(String cardID, String userId, String floor, String name, String room, LocalDate expiryDate);
    void modifyCard(String userId, String newFloor, String newRoom, LocalDate newExpiryDate);
    void revokeCard(String userId);
}
