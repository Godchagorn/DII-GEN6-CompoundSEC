package service;

public interface CardManagementInterface {
    void addCard(String cardID, String accessLevel);
    void modifyCard(String cardID, String newAccessLevel);
    void revokeCard(String cardID);
}