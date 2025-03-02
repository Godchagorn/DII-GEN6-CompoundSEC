package service;

public class ConcreteAccessControlSystem extends AccessControlSystem {
    private static ConcreteAccessControlSystem instance;

    private ConcreteAccessControlSystem() {
        super();
    }

    public static ConcreteAccessControlSystem getInstance() {
        if (instance == null) {
            instance = new ConcreteAccessControlSystem();
        }
        return instance;
    }
}
