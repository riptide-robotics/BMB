package org.firstinspires.ftc.teamcode.Sequencer;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.ArrayList;
import java.util.List;

public enum Zones {
    //TODO get actual zones. This will have to be redone every new game.


    //Test locations, do not change
    A(new Zone("A", 5.0,10.0,1.0,8.0,2.0,5.0)),
    B(new Zone("B", 1.0,1.0,1.0,5.0,5.0,5.0,5.0,1.0)),

    C(new Zone("C", 3.0,3.0,3.0,7.0,7.0,7.0,7.0,3.0)),

    ;
    public final Zone zone;

    Zones(Zone zone) {
        this.zone = zone;
    }

    public static List<Zones> getRobotZones(Robot robot) {
        List<Zones> returnedzones = new ArrayList<>();

        Zones[] zones = Zones.values();

        for (int i = 0; i < zones.length; i++)
            if (zones[i].zone.botInZone(robot)) returnedzones.add(zones[i]);

        return returnedzones;
    }
}
