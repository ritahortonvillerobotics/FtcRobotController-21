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
        mtrFrontRight = hardwareMap.get(DcMotor.class, "mtrFrontRight");
        mtrFrontLeft = hardwareMap.get(DcMotor.class, "mtrFrontLeft");
        mtrBackRight = hardwareMap.get(DcMotor.class, "mtrBackRight");
        mtrBackLeft = hardwareMap.get(DcMotor.class, "mtrBackLeft");
        DuckSpin = hardwareMap.get(DcMotor.class, "DuckSpin");
        pullArm = hardwareMap.get(DcMotor.class, "pullArm");
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
        }

    public void loop() {

          /*if (tfod != null) {

              tfod.activate() = true;
              tfodParameters.inputSize = 320;
              tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
              tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
          }

          tfodParameters.useObjectTracker = false;

          tfodParameters.minResultConfidence = 0.8f;

          final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
          final String[] LABELS = {
                  "Ball",
                  "Cube",
                  "Duck",
                  "Marker"

          };
          tfod.setZoom(2.5, 16.0 / 9.0);

          if (tfod != null) {

              tfod.activate();
          }
      }}*/



        //Alt Trainmtr
        mtrFrontRight.setPower(Math.abs(gamepad1.right_stick_y * .4) <= .07 ? 0 : gamepad1.right_stick_y);
        mtrFrontLeft.setPower(Math.abs(gamepad1.left_stick_y * .4) <= .07 ? 0 : -gamepad1.left_stick_y);
        mtrBackLeft.setPower(Math.abs(gamepad1.left_stick_y * .4) <= .07 ? 0 : -gamepad1.left_stick_y);
        mtrBackRight.setPower(Math.abs(gamepad1.right_stick_y * .4) <= .07 ? 0 : gamepad1.right_stick_y);
        mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Alt Train
         //mtrFrontRight.setPower(gamepad1.right_stick_y);
        //mtrFrontLeft.setPower(-gamepad1.left_stick_y);
        //mtrBackLeft.setPower(-gamepad1.left_stick_y);
        //mtrBackRight.setPower(gamepad1.right_stick_y);


        // Current 2021 Drive Train
        //Restore
         /*
         r.setPower("mtrFrontLeft", - gamepad1.left_stick_y);
        r.setPower("mtrBackLeft", gamepad1.left_stick_y);
        r.setPower("mtrFrontRight",gamepad1.right_stick_y);
        r.setPower("mtrBackRight", gamepad1.right_stick_y);

*/
        // Current 2021 Collector Motors
        // r.setPower("mtrCollect1", gamepad2.left_bumper ? 1 : gamepad2.left_trigger > 0.5 ? -1 : 0);
        //r.setPower("mtrCollect2", gamepad1.left_bumper ? 1 : gamepad1.left_trigger > 0.5 ? -1 : 0);

        //Current 2022 Duck Spinner
        if(gamepad1.b== true && gamepad1.y==true){
            DuckSpin.setPower(0.00);
        }

        if (gamepad1.b) {
            DuckSpin.setPower(0.50);
        } else if (!gamepad1.b) {
            DuckSpin.setPower(0.0);
        }
        if (gamepad1.y) {
            DuckSpin.setPower(-0.50);
        } else if (!gamepad1.y) {
            DuckSpin.setPower(0.0);
        }

        /*
       // Arm motor: This controls the arm using a motor to wind/unwind a string.
         pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

          //int encoderCount=pullArm.getCurrentPosition();
          //int max=encoderCount+0;
          // if(encoderCount<max)
          if(gamepad2.a == true){
              pullArm.setPower(1.0);
          }
          else if(!gamepad2.a){
              pullArm.setPower(0.0);
         }
         if (gamepad2.x ==true){
             pullArm.setPower(-0.1);
          }

         else if (!gamepad2.x){
             pullArm.setPower(0.0);
         }
        */

  /*      //Lift Reset
        //if (gamepad2.right_bumper == true){
        //  pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
*/
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
        }else if (gamepad2.right_bumper == true){
            pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            pullArm.setPower(0);
        }

        //2022 Claw Code
        //Servo is 270 degree 0.00 reprsents full open
        //Servo gamepad x represents closed
        //New Servo gamepade x represents open
        if (gamepad1.a == true && !gamepad1.x == true) {
            claw.setPosition(0.00);
        }

        if (gamepad1.x == true && !gamepad1.a== true) {
            claw.setPosition(0.3);
        }
        // telemetry2022
        telemetry.addData("Lift Value: ", pullArm.getCurrentPosition());

        telemetry.update();

    }
    // Vision 2021
      /*

                          List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                              telemetry.addData("# Object Detected", updatedRecognitions.size());

                              int i = 0;
                              for  (Recognition recognition : updatedRecognitions)

      {

          {
              telemetry.addData(String.format("label (%d)", i),
                      recognition.getLabel());
              telemetry.addData(String.format(" left,top (%d)", i), "%.03f , % .03f",
                      recognition.getLeft(), recognition.getTop());
              telemetry.addData(String.format(" right,bottom (%d)", i), "%.03f, % .03f ",
                      recognition.getRight(), recognition.getBottom());
              i++;
              telemetry.update();
          }*/





}


//2021 CLAW CODE - RZ & SB 
//if (gamepad1.a == true)
//{ r.setServoPosition("srvClaw", 0.0);}
//if (gamepad1.x == true) {r.setServoPosition("srvClaw", .82);}

        /* 2021 COLLECTOR 3 CODE - RZ & SB--Removed in rebuild
        if(gamepad2.y == true)
        {r.setServoPower("srvCollect3", -1.0);}
        */
/*
        // 2021 LIFT CODE - RZ & SB
        if(gamepad1.y == true) {r.setPower("mtrAngle", -1.0);}
        else if(gamepad1.y == false) {r.setPower("mtrAngle", 0.0);}

        if(gamepad1.b == true) {r.setPower("mtrAngle", 1.0);}
        else if(gamepad1.b == false) {r.setPower("mtrAngle", 0.0);}

        //2021 Arm Code

        if (gamepad2.a) {
            r.setServoPower("srvArm",-1.00);}
         else if (gamepad2.a == false) {
            r.setServoPower("srvArm", 0);
    }

         if (gamepad2.b) {
        r.setServoPower("srvArm",1.00);}
        else if (gamepad2.b == false) {
        r.setServoPower("srvArm", 0);}



        // UnUsed Code as of March 2021

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


//        WobbleGoalGetter wobbleGoalGetter = new WobbleGoalGetter(r);
//        telemetry.addData("Color Reading Red", wobbleGoalGetter.getRed(false));
//        telemetry.addData("Doing it: ", 69);
//        if (Math.abs(gamepad1.right_stick_x) < 0.075) {
//            double x = gamepad1.left_stick_x;
//            double y = -gamepad1.left_stick_y;
////theta1 is diagonal motion not rotation
//            theta1 = ((Math.atan(y / x)));
//            //This series of if statements prevents us from dividing by 0
//            //Because we divide by X, X != 0
//            if (x == 0 && y > 0) {
//                theta1 = Math.PI / 2;
//            } else if (x == 0 && y < 0) {
//                theta1 = 3 * Math.PI / 2;
//            } else if (x < 0) {
//                theta1 = Math.atan(y / x) + Math.PI;
//            }
//            double theta2 = Math.PI / 4 - theta1 + Math.PI;
//            double hyp = Math.sqrt(x * x + y * y);
//            boolean motorBand = Math.abs(x) > .05 || Math.abs(y) > .05;
//            telemetry.addData("MotorBand", motorBand);
//            telemetry.addData("x", x);
//            telemetry.addData("y", y);
//            double speedControl = gamepad1.right_bumper ? 1 : gamepad1.right_trigger > .4 ? .25 : .5;
//            r.setPower(Robot.wheelSet1[0], motorBand ? hyp * Math.cos(theta2) * speedControl : 0);
//            r.setPower(Robot.wheelSet2[0], motorBand ? -hyp * Math.sin(theta2) * speedControl : 0);
//            r.setPower(Robot.wheelSet1[1], motorBand ? hyp * Math.cos(theta2) * speedControl : 0);
//            r.setPower(Robot.wheelSet2[1], motorBand ? -hyp * Math.sin(theta2) * speedControl : 0);
//
//        } else {
//            r.setPower(Robot.wheelSetL[0], gamepad1.right_stick_x / 2);
//            r.setPower(Robot.wheelSetL[1], gamepad1.right_stick_x / 2);
//            r.setPower(Robot.wheelSetR[0], -gamepad1.right_stick_x / 2);
//            r.setPower(Robot.wheelSetR[1], -gamepad1.right_stick_x / 2);
//        }
//
//
//        drivePowerScale = gamepad1.left_trigger >= .5 ? 0.25 : gamepad1.left_bumper ? 0.7 : 0.4;
//        telemetry.addData("drivePowerScale: ", drivePowerScale);
//        r.setDrivePower(Math.abs(gamepad1.left_stick_y) > 0.05 && !gamepad1.left_stick_button ? drivePowerScale * gamepad1.left_stick_y : 0, Math.abs(gamepad1.right_stick_y) > 0.05 && !gamepad1.right_stick_button ? drivePowerScale * gamepad1.right_stick_y : 0);

//        telemetry.addData("isCollecting", isCollecting);
//        if (!g1AP && gamepad1.a) {
//            g1AP = true;
//            isCollecting = !isCollecting;
//        } else if (!gamepad1.a) g1AP = false;


//Cade Claw Code Replaced
        /*boolean arm = false;
        boolean claw = false;
        if (gamepad2.a && arm == false) {
            r.setServoPower("srvArm", .75);
            arm = true;
        } else if (gamepad2.a && arm == true) {
            r.setServoPower("srvArm", -.75);
            arm = false;
        }
        if (gamepad2.b && claw == false) {
            r.setServoPower("srvClaw", .75);
            claw = true;
        } else if (gamepad2.b && claw == true) {
            r.setServoPower("srvClaw", -.75);
            claw = false;
        }*/


//
//          ***UNUSED DUE TO CURRENT BUILD****
/*
        if (gamepad2.right_stick_y <= 0.05&&gamepad2.right_stick_y>=-0.05) {
            r.setServoPower("srvAngle", 0.0);
        } else {
            r.setServoPower("srvAngle",gamepad2.right_stick_y);
        }
*/

//TODO: CHECK FOR VALIDITY

//        if (!g2BL && gamepad2.left_bumper)   {
//            g2BL = true;
//            isCollecting = isCollecting;
//        } else if (!gamepad2.left_bumper) g2BL = false;
//
//        if (gamepad2.left_bumper && count == 0 && !isCollecting) {
//
//            r.setServoPosition("srvFlip", 1.0);//Set to open
//        count ++;
//        }    else if(gamepad2.left_bumper && count == 1 && !isCollecting) {
//            r.setServoPosition("srvFlip", 0.0); //Set to close
//            count -- ;
//        }


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

//  Mr B Test Area
//String myString="Hello";


