package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
//package org.firstinspires.ftc.teamcode


//import hortonvillerobotics.FinalRobotConfiguration;


@Autonomous(name = "Red2", group = "Autonomous")
//
public class Red2 extends LinearOpMode {
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
    private static final int STOP_ROBOT_TIME = 1000;
    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(this);
        r.DuckSpin = hardwareMap.dcMotor.get("DuckSpin");
        r.mtrFrontRight = hardwareMap.dcMotor.get("mtrFrontRight");
        r.mtrFrontLeft = hardwareMap.dcMotor.get("mtrFrontLeft");
        r.mtrBackLeft = hardwareMap.dcMotor.get("mtrBackLeft");
        r.mtrBackRight = hardwareMap.dcMotor.get("mtrBackRight");
        r.pullArm = hardwareMap.dcMotor.get("pullArm");
        r.claw = hardwareMap.servo.get("claw");

        r.mtrFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.mtrFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.mtrBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.mtrBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        r.mtrFrontRight.setZeroPowerBehavior(BRAKE);
        r.mtrFrontLeft.setZeroPowerBehavior(BRAKE);
        r.mtrBackLeft.setZeroPowerBehavior(BRAKE);
        r.mtrBackRight.setZeroPowerBehavior(BRAKE);
        r.pullArm.setZeroPowerBehavior(FLOAT);
        r.mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        r.pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        r.claw(false);
        waitForStart();
        r.claw(true);
        sleep(STOP_ROBOT_TIME);
        r.arm(4, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.1,59);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(4, 1000);
        sleep(STOP_ROBOT_TIME);
        r.claw(false);
        r.driveEncodersInch(.1, -1);
        sleep(1000);
        r.turnEncodersDegree(.1, -30);
        r.arm(3,500);
        r.arm(2,500);
        r.arm(1, 500);
        r.driveEncodersInch(.1, -15);
        r.turnEncodersDegree(.1, -80);
        r.driveEncodersInch(.3,36);
        r.arm(0,500);

    }
}