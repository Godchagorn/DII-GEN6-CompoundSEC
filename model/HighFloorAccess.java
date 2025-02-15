package model;

public class HighFloorAccess extends AccessLevel {
    public HighFloorAccess() {
        super("High Floor");
    }

    @Override
    public boolean canAccess(String area) {
        return true;
    }

    @Override
    public String grantAccess() {
        return "Access Granted: High Floor - VIP or special permission holders only.";
    }
}
