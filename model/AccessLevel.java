package model;

public abstract class AccessLevel {
    protected String levelName;

    public AccessLevel(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelName() {
        return levelName;
    }

    public abstract boolean canAccess(String area);
}
