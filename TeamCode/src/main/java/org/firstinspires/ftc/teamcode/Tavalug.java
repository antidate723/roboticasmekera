package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Tavalug {
    Servo s1;
    Servo s2;

    Mode mode = Mode.COLLECT;
    public Tavalug (HardwareMap hardwaremap){
        s1 = hardwaremap.get(Servo.class, "TV1");
        s2 = hardwaremap.get(Servo.class, "TV2");
        setposition();
    }


    public enum Mode{
        COLLECT (0.336),
        UP(0.3),
        DOWN(0.4);
        double position;

        Mode (double position){
            this.position = position;
        }
    }


    //doar asta rulezeaza;
    public void setposition(){
        s1.setPosition(mode.position);
        s2.setPosition(mode.position);
    }

    public void setmode(Mode nume){
            this.mode = nume;
            setposition();
    }

    public Command csetmode (Mode primeste){
        return instant(() -> setmode(primeste));
    }

}
