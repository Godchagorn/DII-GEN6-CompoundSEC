package model;

public class MediumFloorAccess implements AccessBehavior {
    @Override
    public boolean canAccess(String area) {
        return area.equalsIgnoreCase("Low Floor") || area.equalsIgnoreCase("Medium Floor");
    }

    @Override
    public String grantAccess() {
        return "Access Granted: Medium Floor - Employees only.";
    }
}
