package model;

public class AccessAttempt extends AuditLog {
    private String requestedArea;
    private boolean isGranted;

    public AccessAttempt(String cardID, String requestedArea, boolean isGranted) {
        super(cardID);
        this.requestedArea = requestedArea;
        this.isGranted = isGranted;
    }

    @Override
    public String logEvent() {
        return timestamp + " - Card ID: " + cardID +
                " attempted to access " + requestedArea +
                " - Access " + (isGranted ? "Granted" : "Denied");
    }
}
