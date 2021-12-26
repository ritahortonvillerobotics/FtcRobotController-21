package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
 //package org.firstinspires.ftc.teamcode


//import hortonvillerobotics.FinalRobotConfiguration;


    @Autonomous(name = "org.firstinspires.ftc.teamcode.Autonomous3", group = "Autonomous")
//
    public class Autonomous3 extends LinearOpMode {
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
            DuckSpin = hardwareMap.dcMotor.get("DuckSpin");
            mtrFrontRight = hardwareMap.dcMotor.get("mtrFrontRight");
            mtrFrontLeft = hardwareMap.dcMotor.get("mtrFrontLeft");
            mtrBackLeft = hardwareMap.dcMotor.get("mtrBackLeft");
            mtrBackRight = hardwareMap.dcMotor.get("mtrBackRight");
            pullArm = hardwareMap.dcMotor.get("pullArm");
            claw = hardwareMap.servo.get("claw");

            mtrFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            mtrFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            mtrBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            mtrBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            mtrFrontRight.setZeroPowerBehavior(BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(BRAKE);
            mtrBackLeft.setZeroPowerBehavior(BRAKE);
            mtrBackRight.setZeroPowerBehavior(BRAKE);
            pullArm.setZeroPowerBehavior(FLOAT);
            mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            claw(true);
            waitForStart();
            claw(false);
            sleep(2000);
            arm(1,300);
            driveForward(.1,850);
            sleep(2000);
            turnRight(.55,900);
            sleep(2000);
            driveBack(.087,1200);
            sleep(2000);
            duckspinner(true,2000);
            sleep(2000);
            driveForward(.1,780);
            sleep(300);
            turnLeft(.55,900);
            sleep(1000);
            driveForward(.1,880);
            turnRight(.35,460);
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


        public void driveForward (double power, long timems){

            mtrFrontRight.setPower(-power);
            mtrBackRight.setPower(-power);
            mtrFrontLeft.setPower(power);
            mtrBackLeft.setPower(power);

            sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);


        }
        public void driveBack (double power, long timems){

            mtrFrontRight.setPower(power);
            mtrBackRight.setPower(power);
            mtrFrontLeft.setPower(-power);
            mtrBackLeft.setPower(-power);

            sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);


        }
        public void turnRight (double power, long timems){
            mtrFrontLeft.setPower(power);
            mtrBackRight.setPower(power);
            sleep(timems);
            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);

        }

        public void turnLeft (double power, long timems){
            mtrFrontRight.setPower(-power);
            mtrBackLeft.setPower(-power);
            sleep(timems);
            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);
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

                sleep(timems);

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
            sleep(timems);
            DuckSpin.setPower(0.0);
        }




    }

