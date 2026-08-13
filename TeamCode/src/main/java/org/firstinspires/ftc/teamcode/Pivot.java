package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pivot {
    Servo s1;


    public Pivot(HardwareMap hardwaremap){
        s1 = hardwaremap.get(Servo.class, "PIV");
    }


    //34 57
    public void gotoangle(double angle){
        if(angle > 57)
            angle= 57;

        if (angle < 34)
            angle = 34;

        double position = (angle-60)/-50.3921;

        if(position == position)
            s1.setPosition(position);

    }

    public void setposition(double position){
        s1.setPosition(position);
    }

}
