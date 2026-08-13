package org.firstinspires.ftc.teamcode;

import com.bylazar.configurables.annotations.Configurable;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


@Configurable
public class Tureta {
    DcMotor tureta;
    public static PIDFCoefficients pid = new PIDFCoefficients(0.02,0,0.0019,0);
    public static PIDFController pid_ctr;

    public static PIDFCoefficients pid_cls = new PIDFCoefficients(0.1,0,0.002,0);
    public static PIDFController pid_ctr_cls;
    private static final double  tics  = 103.8 * 4.75;

    public static  double k = 0;
    public Tureta(HardwareMap hardwaremap){
        tureta = hardwaremap.get(DcMotor.class, "TUR");


        pid_ctr = new PIDFController(pid);
        pid_ctr.setTargetPosition(0);
        pid_ctr.updatePosition(0);



        pid_ctr_cls = new PIDFController(pid_cls);
        pid_ctr_cls.setTargetPosition(0);
        pid_ctr_cls.updatePosition(0);
    }



    public double getangle() {
        return tureta.getCurrentPosition()/tics * 360;
    }


    public void update(){
        pid_ctr.updatePosition(getangle());
        pid_ctr_cls.updatePosition(getangle());
        double power = pid_ctr.run();

        if(Math.abs(pid_ctr.getTargetPosition() - getangle()) < 5)
            power= pid_ctr_cls.run();


        if(power==power)
            tureta.setPower(power);

        if (Math.abs(pid_ctr.getTargetPosition() - getangle()) < 0.5)
            tureta.setPower(0);



    }


    public void setTarget(double target){
        pid_ctr.setTargetPosition(target);

        pid_ctr_cls.setTargetPosition(target);
    }


}
