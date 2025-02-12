package model;

public class MediumFloorAccess extends AccessLevel {
    public MediumFloorAccess() {
        super("Medium Floor");
    }

    @Override
    public boolean canAccess(String area) {
        return area.equalsIgnoreCase("Low Floor") || area.equalsIgnoreCase("Medium Floor");
    }
}
