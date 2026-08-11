package org.firstinspires.ftc.teamcode;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class teleops extends OpMode {
    Motoare motoare;
    intakesys intakesys;
    @Override
    public void init() {
        Scheduler.reset();
        motoare = new Motoare(hardwareMap);
        intakesys = new intakesys(hardwareMap);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        if(gamepad1.b)
            Scheduler.schedule(intakesys.update(1));
        if(gamepad1.a)
            Scheduler.schedule(intakesys.update(0));
        if(gamepad1.x)
            Scheduler.schedule(intakesys.update(2));

        Scheduler.execute();
        motoare.run(gamepad1);
    }
}
