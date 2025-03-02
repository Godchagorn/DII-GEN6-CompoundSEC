package model;

import java.time.LocalDate;

public class RevokeCardLog extends AuditLog {
    private String userName;
    private String floor;
    private String room;
    private LocalDate expiryDate;

    public RevokeCardLog(String cardID, String userName, String floor, String room, LocalDate expiryDate) {
        super(cardID);
        this.userName = userName;
        this.floor = floor;
        this.room = room;
        this.expiryDate = expiryDate;
    }

    @Override
    public String logEvent() {
        return "Timestamp: " + timestamp + " │ Revoked: " + " │ CardID: " + cardID +
                " │ Name: " + userName + " │ Floor: " + floor + " │ Room: " + room +
                " │ Expiry Date: " + expiryDate;
    }
}
