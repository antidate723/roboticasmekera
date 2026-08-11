package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class Intake {
    DcMotor intake;

    public Intake (HardwareMap hardwaremap){
        intake = hardwaremap.get(DcMotor.class, "INT");


        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void power1(){
        intake.setPower(1);
    }
    public void power0(){
        intake.setPower(0);
    }
    public void powerreverse(){
        intake.setPower(-1);
    }

    public void powercurtom(double valoare){
        intake.setPower(valoare);
    }

    public Command cpower1 (){
        return instant(this::power1);
    }

    public Command cpowerreverse (){
        return instant(this::powerreverse);
    }
    public Command cpower0 (){
        return instant(this::power0);
    }






}
