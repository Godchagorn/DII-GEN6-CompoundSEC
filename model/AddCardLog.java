package model;

public class AddCardLog extends AuditLog {
    private String userName;
    private String floor;
    private String room;

    public AddCardLog(String cardID, String userName, String floor, String room) {
        super(cardID);
        this.userName = userName;
        this.floor = floor;
        this.room = room;
    }

    @Override
    public String logEvent() {
        return "Timestamp: " + timestamp + " │ Added: " + " │ CardID: " + cardID + " │ Name: " + userName + " │ Floor: " + floor + " │ Room: " + room  ;
    }
}
