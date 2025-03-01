package model;

public class RevokeCardLog extends AuditLog {
    private String userName;
    private String floor;
    private String room;

    public RevokeCardLog(String cardID, String userName, String floor, String room) {
        super(cardID);
        this.userName = userName;
        this.floor = floor;
        this.room = room;
    }

    @Override
    public String logEvent() {
        return "Timestamp: " + timestamp + " │ Revoked: " + " │ Name: " + userName + " │ CardID: " + cardID + " │ Floor: " + floor + " │ Room: " + room;
    }
}
