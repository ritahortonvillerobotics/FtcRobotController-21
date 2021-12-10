import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import android.os.Environment;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;


@Autonomous(name = "Autonomous1", group = "Testing")
public class Auto1 extends OpMode {
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";;
    private VuforiaLocalizer vuforia;
    final String[] LABELS = {"Ball",
            "Cube",
            "Duck",
            "Marker"};
    private DcMotor mtrFrontRight;
    private DcMotor DuckSpin;
    private DcMotor mtrFrontLeft;
    private DcMotor mtrBackRight;
    private DcMotor mtrBackLeft;
    private DcMotor pullArm;
    Servo claw;
    final String VUFORIA_KEY = "Ae4Y1tr/////AAABmUOIhh5VUERBieW2UEGVyT2AGvBs+tqZimMoeJTBL57NfKJQjp9v+D/teyPEUYRfVkkTnyZEQGfCewAz0dZlwLkfxcfyWDbEBz33yGrmSEZY7WleEqYVt1P3Eewq1wFWHKxosHyETLU+Vs2XfoKtGXJou46WMNSofNvh4CvLU1bYwwA4Yr9nZ7xbgEySOopKhfXujf1XMqKcmgag7jXEj9WaEUY+7ehRq1A8hKtQjYb2YlrKC5zNZSeiBBTBmYjTbl7Zhn1QxYfOPKWlxZ9tD1/6/OwCSAO/nWwXpVYPSWRL7j6cg4vnTpIuS8lOyz/q18zQle O7H59ckS5mhd/KbM21FZRr/fInq5uwCw8Zehga ";





    //final String LABEL_THIRD_ELEMENT = "Duck";
        //final String LABEL_FOURTH_ELEMENT = "Marker";


        /**
         * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
         * Detection engine.
         */
        TFObjectDetector tfod;

        double drivePowerScale = 1;
        double theta1 = 0;

        boolean isCollecting = false;
        boolean g1AP = false;
        boolean g2BL = false;
        int a = 0;




    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "Ae4Y1tr/////AAABmUOIhh5VUERBieW2UEGVyT2AGvBs+tqZimMoeJTBL57NfKJQjp9v+D/teyPEUYRfVkkTnyZEQGfCewAz0dZlwLkfxcfyWDbEBz33yGrmSEZY7WleEqYVt1P3Eewq1wFWHKxosHyETLU+Vs2XfoKtGXJou46WMNSofNvh4CvLU1bYwwA4Yr9nZ7xbgEySOopKhfXujf1XMqKcmgag7jXEj9WaEUY+7ehRq1A8hKtQjYb2YlrKC5zNZSeiBBTBmYjTbl7Zhn1QxYfOPKWlxZ9tD1/6/OwCSAO/nWwXpVYPSWRL7j6cg4vnTpIuS8lOyz/q18zQle O7H59ckS5mhd/KbM21FZRr/fInq5uwCw8Zehga ";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        TFObjectDetector tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
    @Override
    public void init() {
        DcMotor mtrFrontRight = hardwareMap.get(DcMotor.class, "mtrFrontRight");
        DcMotor  mtrFrontLeft = hardwareMap.get(DcMotor.class, "mtrFrontLeft");
        DcMotor mtrBackRight = hardwareMap.get(DcMotor.class, "mtrBackRight");
        DcMotor mtrBackLeft = hardwareMap.get(DcMotor.class, "mtrBackLeft");
        DcMotor DuckSpin = hardwareMap.get(DcMotor.class, "DuckSpin");
        DcMotor pullArm = hardwareMap.get(DcMotor.class, "pullArm");
        Servo claw = hardwareMap.servo.get("claw");
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
        initVuforia();
        //initTfod();
    }

    @Override
    public void loop() {
        //Alt Trainmtr
        mtrFrontRight.setPower(Math.abs(gamepad1.right_stick_y) * .4 <= .07 ? 0 : gamepad1.right_stick_y);
        mtrFrontLeft.setPower(Math.abs(gamepad1.left_stick_y) * .4 <= .07 ? 0 : -gamepad1.left_stick_y);
        mtrBackLeft.setPower(Math.abs(gamepad1.left_stick_y) * .4 <= .07 ? 0 : -gamepad1.left_stick_y);
        mtrBackRight.setPower(Math.abs(gamepad1.right_stick_y) * .4 <= .07 ? 0 : gamepad1.right_stick_y);
        mtrFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        //Current 2022 Duck Spinner
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
        //Servo is 270 degree 0.00 reprsents full open
        //Servo gamepad x represents closed
        //New Servo gamepade x represents open
        if (gamepad1.a == true) {
            claw.setPosition(0.00);
        }

        if (gamepad1.x == true) {
            claw.setPosition(0.3);
        }
/*
        //Developer Code Area
        //claw.setPosition(0.00);
        mtrFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrFrontRight.setTargetPosition(540);
        mtrFrontRight.setPower(.4);
        mtrFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mtrFrontLeft.setTargetPosition(540);
        mtrFrontLeft.setPower(.4);
        mtrFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mtrBackLeft.setTargetPosition(540);
        mtrBackLeft.setPower(.4);
        mtrBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mtrBackRight.setTargetPosition(540);
        mtrBackRight.setPower(.4);
        mtrBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        // telemetry2022

        telemetry.addData("Lift Value: ", pullArm.getCurrentPosition());

        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                // step through the list of recognitions and display boundary info.
                int i = 0;
                for (Recognition recognition : updatedRecognitions) {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                }
                telemetry.update();
            }
        }
  */  }
}
