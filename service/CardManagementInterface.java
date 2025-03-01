package service;

import model.AccessCard;

public interface CardManagementInterface {
    AccessCard getCard(String cardID);
    void addCard(String cardID, String floor, String name, String room);
    void modifyCard(String cardID, String newFloor, String newRoom);
    void revokeCard(String cardID);
}
