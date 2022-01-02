package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {
    public DcMotor mtrFrontRight;
    public DcMotor mtrFrontLeft;
    public DcMotor mtrBackLeft;
    public DcMotor mtrBackRight;
    public DcMotor DuckSpin;
    public DcMotor pullArm;
    public Servo claw;
    public LinearOpMode opMode;
    boolean isFourWheel = false;

    public void driveForward (double power, long timems) {

        if (isFourWheel == true) {
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setPower(power);
            mtrBackRight.setPower(power);
            mtrFrontLeft.setPower(-power);
            mtrBackLeft.setPower(-power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);


        }
       if((isFourWheel==false))
           mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
           mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
           mtrFrontRight.setPower(0.0);
           mtrFrontLeft.setPower(0.0);

           mtrBackRight.setPower(power);
           mtrBackLeft.setPower(-power);

           opMode.sleep(timems);

           mtrBackRight.setPower(0.0);
           mtrBackLeft.setPower(0.0);
       }


    public void driveBack (double power, long timems){
        if (isFourWheel == true) {
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setPower(-power);
            mtrBackRight.setPower(-power);
            mtrFrontLeft.setPower(power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);


        }
        if((isFourWheel==false)){
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackRight.setPower(-power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrBackRight.setPower(0.0);
            mtrBackLeft.setPower(0.0);
        }
    }
    public void turnRight (double power, long timems){
        if(isFourWheel == true) {
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setPower(-power);
            mtrBackRight.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);
        }
        if (isFourWheel == false){
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setPower(-power);
            mtrBackRight.setPower(power);

            opMode.sleep((timems));

            mtrFrontLeft.setPower(0.0);
            mtrFrontRight.setPower(0.0);
            mtrBackLeft.setPower(0.0);
            mtrBackRight.setPower(0.0);
        }
    }
    public void turnLeft (double power, long timems){
        if(isFourWheel == true) {
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setPower(-power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);
        }
        if (isFourWheel == false){
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontRight.setPower(-power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrBackLeft.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
        }
    }
    public void arm (int level,long timems){
        //Alternate Arm Code
        if (level == 1) {
            pullArm.setTargetPosition(425);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (level == 2) {
            pullArm.setTargetPosition(960);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (level == 3) {
            pullArm.setTargetPosition(1445);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (level == 4) {
            pullArm.setTargetPosition(1940);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (level == 5) {
            pullArm.setTargetPosition(2700);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (level == 0) {
            pullArm.setTargetPosition(105);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (level == -1) {
            pullArm.setPower(-.1);

            opMode.sleep(timems);

            pullArm.setPower(0.00);
        }
    }
    public void claw(boolean open){

        if (open == true) {
            claw.setPosition(0.00);
        }

        if (open == false) {
            claw.setPosition(0.3);
        }


    }
    public void duckspinner (boolean clockwise,long timems){

        if (clockwise == true) {
            DuckSpin.setPower(0.45);
        }
        if (clockwise==false) {
            DuckSpin.setPower(-0.45);
        }
        opMode.sleep(timems);
        DuckSpin.setPower(0.0);
    }
    public void setDrive (boolean value){
        isFourWheel=value;
    }
    public void allBrake() {
        mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontRight.setPower(0.0);
        mtrFrontLeft.setPower(0.0);
        mtrBackLeft.setPower(0.0);
        mtrBackRight.setPower(0.0);
    }
    public void standardBehavior(){
        mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mtrFrontRight.setPower(0.0);
        mtrFrontLeft.setPower(0.0);
        mtrBackLeft.setPower(0.0);
        mtrBackRight.setPower(0.0);
    }
}
