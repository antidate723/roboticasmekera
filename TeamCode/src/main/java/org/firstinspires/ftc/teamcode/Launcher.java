package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PWMOutput;

@Configurable
public class Launcher {
    DcMotorEx l1, l2;


    public static PIDFCoefficients pid = new PIDFCoefficients(0.01,0,0.00001,0);;

    public static PIDFController pid_ctr;



    public Launcher (HardwareMap hardwaremap){
            l1 = hardwaremap.get(DcMotorEx.class, "LAU");
            l2 = hardwaremap.get(DcMotorEx.class, "LAU2");


            l2.setDirection(DcMotorSimple.Direction.REVERSE);


        pid_ctr = new PIDFController(pid);
        pid_ctr.setTargetPosition(0);
        pid_ctr.updatePosition(0);
    }

    public double getrps(){
            return l1.getVelocity()/28 *(20.0/30)*60;
    }


    public void setTarget(double target){
        pid_ctr.setTargetPosition(target);
    }
    //2600


    public void update(){
        pid_ctr.updatePosition(getrps());

        double power = pid_ctr.run();
        if (power == power){
            l1.setPower(-power);
            l2.setPower(-power);
        }




        if(pid_ctr.getTargetPosition() - getrps() > 300 && pid_ctr.getTargetPosition()>400){
            l1.setPower(-1);
            l2.setPower(-1);
        }



    }}
