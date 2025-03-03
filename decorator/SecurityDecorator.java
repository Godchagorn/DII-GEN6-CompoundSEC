package decorator;

import strategy.AccessStrategy;

import java.time.LocalTime;

public class SecurityDecorator extends AccessControlDecorator {
    public SecurityDecorator(AccessStrategy decoratedAccess) {
        super(decoratedAccess);
    }



    //Card generation or modification will be logged with necessary info
    //ทุกครั้งที่ผู้ใช้พยายามเข้าใช้ห้อง ระบบจะทำการบันทึกข้อมูล
    //หากผู้ใช้ไม่มีสิทธิ์ ระบบจะแสดง "Access Denied" และบันทึกลงใน Audit Log

    @Override
    public boolean hasAccess(String userId, String room) {
        LocalTime currentTime = LocalTime.now();
        if (currentTime.isBefore(LocalTime.of(6, 0)) || currentTime.isAfter(LocalTime.of(22, 0))) {
            System.out.println("Access Denied: Outside of operational hours.");
            return false;
        }
        return super.hasAccess(userId, room);
    }
}
