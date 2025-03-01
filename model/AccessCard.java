package model;

public class AccessCard {
    private String cardID;
    private String floor;
    private String room;
    private String name;
    private boolean active;

    public AccessCard(String cardID, String floor, String room, String name) {
        this.cardID = cardID;
        this.floor = floor;
        this.room = room;
        this.name = name;
        this.active = true;
    }

    public String getCardID() {
        return cardID;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String newFloor) {
        this.floor = newFloor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String newRoom) {
        this.room = newRoom;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean canAccessRoom(String room) {
        return room.startsWith(floor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccessCard that = (AccessCard) obj;
        return cardID.equals(that.cardID);
    }

    @Override
    public int hashCode() {
        return cardID.hashCode();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Card ID: " + cardID + ", Name: " + name + ", Floor: " + floor + ", Active: " + active;
    }
}