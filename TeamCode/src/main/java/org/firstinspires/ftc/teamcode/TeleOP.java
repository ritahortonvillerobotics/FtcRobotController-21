package org.firstinspires.ftc.teamcode;  //package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;









import java.util.ArrayList;

//import hortonvillerobotics.FinalRobotConfiguration;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;


@TeleOp(name = "TeleOPTest2022", group = "TeleOp")
//
public class TeleOP extends OpMode {
    private DcMotor DuckSpin;
    private DcMotor mtrFrontRight;
    private DcMotor mtrFrontLeft;
    private DcMotor mtrBackRight;
    private DcMotor mtrBackLeft;
    private DcMotor pullArm;
    public Servo claw;
    private static final String VUFORIA_KEY = "Ae4Y1tr/////AAABmUOIhh5VUERBieW2UEGVyT2AGvBs+tqZimMoeJTBL57NfKJQjp9v+D/teyPEUYRfVkkTnyZEQGfCewAz0dZlwLkfxcfyWDbEBz33yGrmSEZY7WleEqYVt1P3Eewq1wFWHKxosHyETLU+Vs2XfoKtGXJou46WMNSofNvh4CvLU1bYwwA4Yr9nZ7xbgEySOopKhfXujf1XMqKcmgag7jXEj9WaEUY+7ehRq1A8hKtQjYb2YlrKC5zNZSeiBBTBmYjTbl7Zhn1QxYfOPKWlxZ9tD1/6/OwCSAO/nWwXpVYPSWRL7j6cg4vnTpIuS8lOyz/q18zQle O7H59ckS5mhd/KbM21FZRr/fInq5uwCw8Zehga ";
    //Restore Me
    /*Robot r;
    int count = 0;*/


    double drivePowerScale = 1;
    double theta1 = 0;

    boolean isCollecting = false;
    boolean g1AP = false;
    boolean g2BL = false;

    //TestRobotConfig testRobotConfig = new TestRobotConfig();
    int a = 0;

    @Override
    public void init() {
        //Restore me
        /*
        r = Robot.getInstance(this, testRobotConfig);
        r.initialize(this, new TestRobotConfig());
        r.setDriveRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pullArm.setDriveRunMode(DcMotor.RunMode.RUN_WITH_ENCODER);
         //((DcMotor) r.motors.get("DuckSpin")).setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        */
        mtrFrontRight = hardwareMap.dcMotor.get( "mtrFrontRight");
        mtrFrontLeft = hardwareMap.dcMotor.get( "mtrFrontLeft");
        mtrBackRight = hardwareMap.dcMotor.get( "mtrBackRight");
        mtrBackLeft = hardwareMap.dcMotor.get("mtrBackLeft");
        DuckSpin = hardwareMap.dcMotor.get( "DuckSpin");
        pullArm = hardwareMap.dcMotor.get("pullArm");
        claw = hardwareMap.servo.get("claw");
        mtrFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrFrontRight.setZeroPowerBehavior(BRAKE);
        mtrFrontLeft.setZeroPowerBehavior(BRAKE);
        mtrBackLeft.setZeroPowerBehavior(BRAKE);
        mtrBackRight.setZeroPowerBehavior(BRAKE);
        //pullArm.setZeroPowerBehavior(FLOAT);
        mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    public void loop() {

        //Alt Trainmtr
        mtrFrontRight.setPower(Math.abs(gamepad1.right_stick_y) <= .07 ? 0 : 0.4*gamepad1.right_stick_y);
        mtrFrontLeft.setPower(Math.abs(gamepad1.left_stick_y) <= .07 ? 0 : 0.4*-gamepad1.left_stick_y);
        mtrBackLeft.setPower(Math.abs(gamepad1.left_stick_y) <= .07 ? 0 : 0.4*-gamepad1.left_stick_y);
        mtrBackRight.setPower(Math.abs(gamepad1.right_stick_y) <= .07 ? 0 : 0.4*gamepad1.right_stick_y);


        //Current 2022 Duck Spinner
        if (gamepad1.b) {
            DuckSpin.setPower(0.48);
        } else if (!gamepad1.b) {
            DuckSpin.setPower(0.0);
        }
        if (gamepad1.y) {
            DuckSpin.setPower(-0.48);
        } else if (!gamepad1.y) {
            DuckSpin.setPower(0.0);
        }

        //Alternate Arm Code
        if (gamepad2.a == true) {
            pullArm.setTargetPosition(425);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad2.b == true) {
            pullArm.setTargetPosition(960);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad2.y == true) {
            pullArm.setTargetPosition(1445);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad2.dpad_up == true) {
            pullArm.setTargetPosition(1940);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad2.dpad_down == true) {
            pullArm.setTargetPosition(2700);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad2.x == true) {
            pullArm.setTargetPosition(105);
            pullArm.setPower(1);
            pullArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (gamepad2.dpad_right == true) {
            pullArm.setPower(-.1);
        } else {
            pullArm.setPower(0);
        }
        //2022 Claw Code
        //Servo is 270 degree 0.00 represents full open
        //Servo gamepad x represents closed
        //New Servo gamepad a represents open
        if (gamepad1.a == true) {
            claw.setPosition(0.00);
        }

        if (gamepad1.x == true) {
            claw.setPosition(0.3);
        }
        // telemetry2022

        telemetry.addData("Lift Value: ", pullArm.getCurrentPosition());
        telemetry.addData("Front Left", mtrFrontLeft.getCurrentPosition());
        telemetry.addData("Front Right", mtrFrontRight.getCurrentPosition());
        telemetry.addData("Back Left", mtrBackLeft.getCurrentPosition());
        telemetry.addData("Back Right", mtrBackRight.getCurrentPosition());
    }
}

