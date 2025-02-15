package model;

import java.time.LocalDateTime;

public abstract class AuditLog {
    protected String cardID;
    protected LocalDateTime timestamp;

    public AuditLog(String cardID) {
        this.cardID = cardID;
        this.timestamp = LocalDateTime.now();
    }

    public abstract String logEvent();
}
