package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TestMotor", group = "Test")
//@Disabled // Comment out if you want to see the op mode in drivers station
public class MotorTest extends OpMode {

    DcMotor testMotor;
    boolean cruiseControl = false;
    boolean cruiseControlOS = true; //OS = if current press can change corresponding value
    double cruiseControlSpeed = 0.0;
    boolean targetPositionOS = true;
    int targetPosition = 0;
    final int TARGET_POSITION_DELTA = 100; //ANGRY_SNAKE_CASE

    @Override
    public void init() {
        testMotor = hardwareMap.dcMotor.get("testMotor");
        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        //set motor power according to cruise control
        if (!cruiseControl) {
            testMotor.setPower(gamepad1.left_stick_y);
            cruiseControlSpeed = gamepad1.left_stick_y;
        }else{
            testMotor.setPower(cruiseControlSpeed);
        }

        //run mode options
        if (gamepad1.a){
            testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }else if(gamepad1.b){
            testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }else if(gamepad1.x){
            testMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }else if(gamepad1.y){
            testMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        //cruise control functionality
        if (gamepad1.guide && cruiseControlOS){
            cruiseControl = !cruiseControl;
            cruiseControlOS = false;
        }else if(!gamepad1.guide){
            cruiseControlOS = true;
        }

        //target position control
        if(gamepad1.dpad_up && targetPositionOS){
            targetPosition += TARGET_POSITION_DELTA;
            targetPositionOS = false;
        }else if(gamepad1.dpad_down && targetPositionOS){
            targetPosition -= TARGET_POSITION_DELTA;
            targetPositionOS = false;
        }else if(!gamepad1.dpad_up && !gamepad1.dpad_down){
            targetPositionOS = true;
        }
        testMotor.setTargetPosition(targetPosition);

        //telemetry output
        telemetry.addLine("Power Data");
        telemetry.addData("Cruise Control?", cruiseControl);
        telemetry.addData("Power", testMotor.getPower());
        telemetry.addData("Left Stick Value", gamepad1.left_stick_y);
        telemetry.addLine("Encoders");
        telemetry.addData("Encoder Value", testMotor.getCurrentPosition());
        telemetry.addData("Target Position", testMotor.getTargetPosition());
        telemetry.addLine("Run Mode/PID");
        telemetry.addData("RunMode", testMotor.getMode());
        telemetry.addData("PID Mode?", testMotor.getMode().isPIDMode());
        telemetry.addData("P Value", testMotor.getMotorType().getHubVelocityParams().p);
        telemetry.addData("I Value", testMotor.getMotorType().getHubVelocityParams().i);
        telemetry.addData("D Value", testMotor.getMotorType().getHubVelocityParams().d);
    }
}
