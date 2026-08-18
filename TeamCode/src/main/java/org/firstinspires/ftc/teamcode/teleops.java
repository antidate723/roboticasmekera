package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.groups.Groups.sequential;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static com.pedropathing.ivy.commands.Commands.*;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Drawing;
import org.firstinspires.ftc.teamcode.pedroPathing.Tuning;

@Configurable
public class teleops extends OpMode {
    Pose goalRed = new Pose(140, 140);
    Pose goalBlue = new Pose(0, 140);
    public static double targetx = 140;
    public static double targety = 140;

    public teleops(double alliance){
        if(alliance==0)
        {
            targetx=goalRed.getX();
            targety=goalRed.getY();
        }
        else
        {
            targetx=goalBlue.getX();
            targety=goalBlue.getY();
        }
    };
    Motoare motoare;
    intakesys intakesys;

    Follower follower;

    Tureta tureta;
    TelemetryManager telemetryM;

    Launcher launcher;
    double targetFieldAngle;
    Gate gate;
    public static double angle = 40;

    Pivot pivot;
    public static double target = 0;
    public static double targetrps = 0;

    LookupTable shootingTable = new LookupTable();

    public static double color=0;
    Servo bec;
    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        Scheduler.reset();
        bec = hardwareMap.get(Servo.class,"BEC");
        intakesys = new intakesys(hardwareMap);
        tureta = new Tureta(hardwareMap);
        launcher = new Launcher(hardwareMap);
        gate = new Gate(hardwareMap);

        pivot = new Pivot(hardwareMap);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(7, 7, 0));
        motoare = new Motoare(hardwareMap);
        shootingTable.add(102, 44, 2550);
        shootingTable.add(90, 44, 2400);
        shootingTable.add(71, 46, 2280);
        shootingTable.add(112, 49, 2650);
        shootingTable.add(128, 54, 3000);
        shootingTable.add(140, 47, 3200);
        shootingTable.add(152, 47, 3300);
        shootingTable.add(143, 50, 3200);
        shootingTable.add(161, 54, 3700);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        gate.setmode(Gate.Mode.CLOSE);
    }


    @Override
    public void loop() {
        bec.setPosition(color);
        if (gamepad1.rightBumperWasPressed())
            if (intakesys.getIntakestate() == 0) {
                Scheduler.schedule(intakesys.update(1));
            } else {
                Scheduler.schedule(intakesys.update(0));
            }


        if (gamepad1.leftBumperWasPressed())
            Scheduler.schedule(intakesys.update(2));

        if (gamepad1.right_trigger_pressed)
            Scheduler.schedule(launch());

        Scheduler.execute();
        update();

        double x1 = follower.getPose().getX();
        double x2 = targetx;
        double y1 = follower.getPose().getY();
        double y2 = targety;
        double dist = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        LookupTable.Result result = shootingTable.get(dist);
        if (dist > 71 && dist < 161) {
            angle = result.y1;
            targetrps = result.y2;
        }
        motoare.run(gamepad1);

        target = getTurretTargetAngle(targetx, targety);
        launcher.setTarget(targetrps);
        pivot.gotoangle(angle);
        tureta.setTarget(target);

        Drawing.drawRobot(follower.getPose());
        telemetry.addData("POSE", follower.getPose());
        telemetry.addData("POSE", follower.getPose().getHeading());
        telemetry.addData("POSE", Math.toDegrees(targetFieldAngle));

        telemetry.addData("Distanta", dist);
    }

    public void update() {
        tureta.update();
        launcher.update();
        telemetry.update();
        follower.update();
        Drawing.sendPacket();
    }

    public Command launch() {
        return sequential(
                gate.csetmode(Gate.Mode.OPEN),
                intakesys.update(3),
                waitMs(500),
                gate.csetmode(Gate.Mode.CLOSE),
                intakesys.update(0)
        );
    }

    public double getTurretTargetAngle(double tx, double ty) {
        Pose currentPose = follower.getPose();

        double dx = tx - currentPose.getX();
        double dy = ty - currentPose.getY();

        targetFieldAngle = Math.atan2(dy, dx);

        double relativeAngleRad = targetFieldAngle - currentPose.getHeading();

        double shortestAngleRad = Math.atan2(Math.sin(relativeAngleRad), Math.cos(relativeAngleRad));

        if (shortestAngleRad > Math.toRadians(180))
            shortestAngleRad = Math.toRadians(180);

        if (shortestAngleRad < Math.toRadians(-130))
            shortestAngleRad = Math.toRadians(-130);


        return -Math.toDegrees(shortestAngleRad);
    }
}
