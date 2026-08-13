package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.instant;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Gate {
    Servo s1;


    Mode mode = Mode.CLOSE;
    public Gate (HardwareMap hardwaremap){
        s1 = hardwaremap.get(Servo.class, "GATE");
        setposition();
    }


    public enum Mode{
        OPEN (0),
        CLOSE(0.05);
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
