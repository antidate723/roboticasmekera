package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motoare {
        DcMotor df;
        DcMotor ds;
        DcMotor sf;
        DcMotor ss;



        public Motoare(HardwareMap hardwaremap){
            df = hardwaremap.get(DcMotor.class, "FR");
            ds = hardwaremap.get(DcMotor.class, "BR");
            sf = hardwaremap.get(DcMotor.class, "FL");
            ss = hardwaremap.get(DcMotor.class, "BL");

            df.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            ds.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            sf.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            ss.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            df.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ds.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            sf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            ss.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


            ss.setDirection(DcMotorSimple.Direction.REVERSE);
            df.setDirection(DcMotorSimple.Direction.REVERSE);


        }



        public void run(Gamepad gamepad1){

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            sf.setPower(frontLeftPower);
            ss.setPower(backLeftPower);
            df.setPower(frontRightPower);
            ds.setPower(backRightPower);

        }






}
