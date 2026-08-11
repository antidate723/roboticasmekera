package org.firstinspires.ftc.teamcode;

import static com.pedropathing.ivy.commands.Commands.branch;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.groups.Groups.sequential;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.LinkedHashMap;
import java.util.function.BooleanSupplier;

public class intakesys {
    Powerramp p;

    Intake i;

    Tavalug t ;
    int intakestate = 0;
    private Command intake;




    public intakesys(HardwareMap hardwaremap){
        p = new Powerramp(hardwaremap);
        i = new Intake(hardwaremap);
        t = new Tavalug(hardwaremap);

        LinkedHashMap<BooleanSupplier, Command>cases = new LinkedHashMap<>();
        cases.put(() -> intakestate == 0,instant(()->{
            t.setmode(Tavalug.Mode.COLLECT);
            i.power0();
            p.setmode(Powerramp.Mode.HOLD);
        })) ;
        cases.put(() -> intakestate == 1,instant(()->{
            t.setmode(Tavalug.Mode.COLLECT);
            i.cpower1();
            p.setmode(Powerramp.Mode.COLLECT);
        })) ;
        cases.put(() -> intakestate == 2,instant(()->{
            t.setmode(Tavalug.Mode.COLLECT);
            i.powercurtom(-0.7);
            p.setmode(Powerramp.Mode.COLLECT);
        })) ;
        intake = branch(cases);
    }
    public Command update(int state)
    {
        return sequential(
                instant(()->intakestate=state),
                intake
        );
    }







}
