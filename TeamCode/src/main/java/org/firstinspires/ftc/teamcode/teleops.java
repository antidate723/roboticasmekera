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

import static com.pedropathing.ivy.commands.Commands.*;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.pedroPathing.Drawing;
import org.firstinspires.ftc.teamcode.pedroPathing.Tuning;

@TeleOp
@Configurable
public class teleops extends OpMode {
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


    public static double targetx = 144;
    public static double targety = 144;
    Pose goalRed = new Pose(144, 144);

    @Override
    public void init() {
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        Scheduler.reset();

        intakesys = new intakesys(hardwareMap);
        tureta = new Tureta(hardwareMap);
        launcher = new Launcher(hardwareMap);
        gate = new Gate(hardwareMap);

        pivot = new Pivot(hardwareMap);

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(7, 7, 0));
        motoare = new Motoare(hardwareMap);
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
        if (gamepad1.bWasPressed())
            Scheduler.schedule(intakesys.update(1));
        if (gamepad1.aWasPressed())
            Scheduler.schedule(intakesys.update(0));
        if (gamepad1.xWasPressed())
            Scheduler.schedule(intakesys.update(2));

        if (gamepad1.yWasPressed())
            Scheduler.schedule(launch());

        Scheduler.execute();
        update();
        motoare.run(gamepad1);

        target = getTurretTargetAngle(targetx, targety);
        launcher.setTarget(targetrps);
        tureta.setTarget(target);

        Drawing.drawRobot(follower.getPose());
        telemetry.addData("POSE",follower.getPose());
        telemetry.addData("POSE",follower.getPose().getHeading());
        telemetry.addData("POSE",Math.toDegrees(targetFieldAngle));
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

        targetFieldAngle  = Math.atan2(dy, dx);

        double relativeAngleRad = targetFieldAngle - currentPose.getHeading();

        double shortestAngleRad = Math.atan2(Math.sin(relativeAngleRad), Math.cos(relativeAngleRad));

        if(shortestAngleRad > Math.toRadians(180))
            shortestAngleRad = Math.toRadians(180);

        if(shortestAngleRad < Math.toRadians(-130))
            shortestAngleRad = Math.toRadians(-130);



        return -Math.toDegrees(shortestAngleRad);
    }
}
