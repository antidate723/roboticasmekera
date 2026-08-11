package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Powerramp {
    Servo s1;


    Mode mode = Mode.COLLECT;
    public Powerramp (HardwareMap hardwaremap){
        s1 = hardwaremap.get(Servo.class, "PR");
        setposition();
    }


    public enum Mode{
        COLLECT (0.77),
        HOLD(0.75),
        PUSH(0.25);
        double position;

        Mode (double position){
            this.position = position;
        }
    }


    //doar asta rulezeaza;
    public void setposition(){
        s1.setPosition(mode.position);
    }

    public void setmode(Mode nume){
        this.mode = nume;
        setposition();
    }

    public Command csetmode (Mode primeste){
        return instant(() -> setmode(primeste));
    }

}
