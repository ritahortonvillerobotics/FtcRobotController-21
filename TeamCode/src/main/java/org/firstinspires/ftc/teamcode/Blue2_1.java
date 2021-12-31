package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
 //package org.firstinspires.ftc.teamcode


//import hortonvillerobotics.FinalRobotConfiguration;


    @Autonomous(name = "org.firstinspires.ftc.teamcode.Blue2_1", group = "Autonomous")
//
    public class Blue2_1 extends LinearOpMode {
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
        public void runOpMode() throws InterruptedException {
            Robot r = new Robot();
            r.DuckSpin = hardwareMap.dcMotor.get("DuckSpin");
            r.mtrFrontRight = hardwareMap.dcMotor.get("mtrFrontRight");
            r.mtrFrontLeft = hardwareMap.dcMotor.get("mtrFrontLeft");
            r.mtrBackLeft = hardwareMap.dcMotor.get("mtrBackLeft");
            r.mtrBackRight = hardwareMap.dcMotor.get("mtrBackRight");
            r.pullArm = hardwareMap.dcMotor.get("pullArm");
            r.claw = hardwareMap.servo.get("claw");

            r.mtrFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            r. mtrFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
            r.claw(true);
            waitForStart();
            r.claw(false);
            sleep(2000);
            r.arm(1,300);
            r.driveForward(.1,850);
            sleep(2000);
            r.turnRight(.55,900);
            sleep(2000);
            r.driveBack(.087,1200);
            sleep(2000);
            r.duckspinner(true,2000);
            sleep(2000);
            r.driveForward(.1,780);
            sleep(300);
            //driveForward(.1,880);
            //turnRight(.35,460);
        /*sleep(2000);
        driveBack(.1,800);
        sleep(2000);
        turnLeft(.1,525);
        sleep(2000);
        driveForward(.1,670);
        sleep(2000); */
            //driveBack(.1,670);
            //driveForward(.1, 670);
            // Method Test
            //driveForward(0.4,500);
       /* driveBack(0.4,500);

        turnLeft(.3,300);
        driveForward(.3,300);
        turnRight(.3, 500);
        driveBack(0.3,300);
        claw(true);
        claw(false);
        arm(0,5000);
        turnRight(.3, 500);
        arm(1,5000);
        turnRight(.3, 500);
        arm(2,5000);
        turnRight(.3, 500);
        arm(3,5000);
        turnRight(.3, 500);
        turnRight(.3, 500);
        arm(4,5000);
        turnRight(.3, 500);
        arm(5,5000);
        turnRight(.3, 500);
        duckspinner(true,500);
        duckspinner(false,500);
        //mtrFrontRight.setPower(-0.4);
        //mtrBackRight.setPower(-0.4);
        //mtrFrontLeft.setPower(0.4);
        //mtrBackLeft.setPower(0.4);

        sleep(300);


        mtrFrontRight.setPower(0.0);
        mtrBackRight.setPower(0.0);
        mtrFrontLeft.setPower(0.0);
        mtrBackLeft.setPower(0.0);




        //opModeIsActive(); * use for looping conditions *
        */
        }





    }

