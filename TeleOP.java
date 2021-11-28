package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.ArrayList;

import hortonvillerobotics.FinalRobotConfiguration;

@TeleOp(name = "TeleOPTest2020", group = "TeleOp")
public class TeleOP extends OpMode {
    Robot r;

    double drivePowerScale = 1;
    double theta1 = 0;

    double rightSideCompensation = .31;
    double leftSideCompensation = 0.0;

    boolean isCollecting = false;
    boolean g1AP = false;

    //revert
    TestRobotConfig testRobotConfig = new TestRobotConfig();
    int a = 0;
    @Override
    public void init() {
        r = Robot.getInstance(this, testRobotConfig);
        r.initialize(this, new TestRobotConfig());
        r.setDriveRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ((DcMotor) r.motors.get("mtrShoot")).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        //telemetry.addData("Init Success", 69);
    }

    @Override
    public void loop() {
        //r.getAngle();
        //CONTROLLER 1
        //testxcvzv
            /*
            Tank Drive Controls
            Configured for Left-Handed Drivers


            Left Stick      -       Left Power
            Right Stick     -       Right Power
            L. Stick Button -       Cut Left Power
            R. Stick Button -       Cut Right Power
            Left Trigger    -       25% Drive Power
            Left Bumper     -       50% Drive Power
             */
        WobbleGoalGetter wobbleGoalGetter = new WobbleGoalGetter(r);
        telemetry.addData("Color Reading Red", wobbleGoalGetter.getRed(false));
        //telemetry.addData("Doing it: ", 69);
        if (Math.abs(gamepad1.left_stick_y) > .075){
            double speedControl = gamepad1.right_bumper ? 1 : gamepad1.right_trigger > .4 ? .25 : .5;
            double rightStick = gamepad1.right_stick_y;
            double leftStick = gamepad1.left_stick_y;

            boolean motorBandL = Math.abs(leftStick) > .05;

            r.setPower(Robot.wheelSetL[0], motorBandL ? leftStick * speedControl + ((leftStick<0) ? -rightSideCompensation : rightSideCompensation) : 0);
            r.setPower(Robot.wheelSetL[1], motorBandL ? leftStick * speedControl : 0);
        }else{
            r.setPower(Robot.wheelSetL[0],0);
            r.setPower(Robot.wheelSetL[1], 0);
        }
        if(Math.abs(gamepad1.right_stick_y) > .075){
            double speedControl = gamepad1.right_bumper ? 1 : gamepad1.right_trigger > .4 ? .25 : .5;
            double rightStick = gamepad1.right_stick_y;
            boolean motorBandR = Math.abs(rightStick) > .05;
            r.setPower(Robot.wheelSetR[0], motorBandR ? rightStick * speedControl : 0);
            r.setPower(Robot.wheelSetR[1], motorBandR ? rightStick * speedControl : 0);
        } else {
            r.setPower(Robot.wheelSetR[0], 0);
            r.setPower(Robot.wheelSetR[1], 0);
        }
        if(gamepad1.dpad_down){
            rightSideCompensation -= .01;
        }
        if(gamepad1.dpad_up){
            rightSideCompensation += .01;
        }
        telemetry.addData("Right: ", rightSideCompensation);

//        r.setDrivePower();
        /*if (Math.abs(gamepad1.right_stick_x) > 0.075) {
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
//theta1 is diagonal motion not rotation
            theta1 = ((Math.atan(y / x)));
            //This series of if statements prevents us from dividing by 0
            //Because we divide by X, X != 0
            if (x == 0 && y > 0) {
                theta1 = Math.PI / 2;
            } else if (x == 0 && y < 0) {
                theta1 = 3 * Math.PI / 2;
            } else if (x < 0) {
                theta1 = Math.atan(y / x) + Math.PI;
            }
            double theta2 = Math.PI / 4 - theta1 + Math.PI;
            double hyp = Math.sqrt(x * x + y * y);
            boolean motorBand = Math.abs(x) > .05 || Math.abs(y) > .05;
            telemetry.addData("MotorBand", motorBand);
            telemetry.addData("x", x);
            telemetry.addData("y", y);
            double speedControl = gamepad1.right_bumper ? 1 : gamepad1.right_trigger > .4 ? .25 : .5;
            r.setPower(Robot.wheelSet1[0], motorBand ? hyp * Math.cos(theta2) * speedControl : 0);
            r.setPower(Robot.wheelSet2[0], motorBand ? -hyp * Math.sin(theta2) * speedControl : 0);
            r.setPower(Robot.wheelSet1[1], motorBand ? hyp * Math.cos(theta2) * speedControl : 0);
            r.setPower(Robot.wheelSet2[1], motorBand ? -hyp * Math.sin(theta2) * speedControl : 0);

        } else {
            r.setPower(Robot.wheelSetL[0], gamepad1.right_stick_x / 2);
            r.setPower(Robot.wheelSetL[1], gamepad1.right_stick_x / 2);
            r.setPower(Robot.wheelSetR[0], -gamepad1.right_stick_x / 2);
            r.setPower(Robot.wheelSetR[1], -gamepad1.right_stick_x / 2);
        }

        drivePowerScale = gamepad1.left_trigger >= .5 ? 0.25 : gamepad1.left_bumper ? 0.7 : 0.4;
        telemetry.addData("drivePowerScale: ", drivePowerScale);
        r.setDrivePower(Math.abs(gamepad1.left_stick_y) > 0.05 && !gamepad1.left_stick_button ? drivePowerScale * gamepad1.left_stick_y : 0, Math.abs(gamepad1.right_stick_y) > 0.05 && !gamepad1.right_stick_button ? drivePowerScale * gamepad1.right_stick_y : 0);
        telemetry.addData("isCollecting", isCollecting);*/
//        if (!g1AP && gamepad1.a) {
//            g1AP = true;
//            isCollecting = !isCollecting;
//        } else if (!gamepad1.a) g1AP = false;
//
//        if (!isCollecting) {
//            r.setPower("mtrCollect1.-", .5);
//            r.setPower("mtrCollect2", .5);
//
//        } else {
//            r.setPower("mtrCollect1", 0);
//            r.setPower("mtrCollect2", 0);
//
//        }
        if(gamepad1.right_bumper){
            r.setPower("mtrCollect2",1);
        }else if(!gamepad1.right_bumper){
            r.setPower("mtrCollect2",0);
        }
        if(gamepad1.left_bumper){
            r.setPower("mtrCollect1",1);
        }else if(!gamepad1.left_bumper){
            r.setPower("mtrCollect1",0);
        }
        if (gamepad2.right_bumper) {
            r.setPower("mtrShoot", 1.0);
        } else if (!gamepad2.right_bumper) {
            r.setPower("mtrShoot", 0.0);
        }
        if (gamepad2.right_stick_y <= 0.05&&gamepad2.right_stick_y>=-0.05) {
            r.setPower("mtrAngle", 0.0);
        } else {
            r.setPower("mtrAngle",gamepad2.right_stick_y);
        }


    }

    /*public void updateMotorEncoders(){
        for(int i = 0; i<4;i++){
            prevMotorEncvoderValues.set(i, currMotorEncoderValues.get(i));
            currMotorEncoderValues.set(i, r.getEncoderCounts(testRobotConfig.motors[i][0]));
        }
    }*/

   /* public int[] calculateDistance(){
        double distanceTravel = 0.0d;
        for(int i = 0; i < 4;i++){
            distanceTravel+=prevMotorEncvoderValues.get(i)-currMotorEncoderValues.get(i);
        }
        distanceTravel/=4;
        return new int[]{-1,-1};
    }*/
}
