package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class Autonomous_Vision {
    public static class ConceptTensorFlowObjectDetection_test extends LinearOpMode {
        /* Note: This sample uses the all-objects Tensor Flow model (FreightFrenzy_BCDM.tflite), which contains
         * the following 4 detectable objects
         *  0: Ball,
         *  1: Cube,
         *  2: Duck,
         *  3: Marker (duck location tape marker)
         *
         *  Two additional model assets are available which only contain a subset of the objects:
         *  FreightFrenzy_BC.tflite  0: Ball,  1: Cube
         *  FreightFrenzy_DM.tflite  0: Duck,  1: Marker
         */
        private static final String TFOD_MODEL_ASSET = "model_20220226_192255.tflite";
        private static final String[] LABELS = {
                "Duck",
                "Icon",
                "Block"
        };

        //private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
        /*private static final String[] LABELS = {
                "Ball",
                "Cube",
                "Duck",
                "Marker"
        };*/


        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code on the next line, between the double quotes.
         */
        private static final String VUFORIA_KEY =
                "Ae4Y1tr/////AAABmUOIhh5VUERBieW2UEGVyT2AGvBs+tqZimMoeJTBL57NfKJQjp9v+D/teyPEUYRfVkkTnyZEQGfCewAz0dZlwLkfxcfyWDbEBz33yGrmSEZY7WleEqYVt1P3Eewq1wFWHKxosHyETLU+Vs2XfoKtGXJou46WMNSofNvh4CvLU1bYwwA4Yr9nZ7xbgEySOopKhfXujf1XMqKcmgag7jXEj9WaEUY+7ehRq1A8hKtQjYb2YlrKC5zNZSeiBBTBmYjTbl7Zhn1QxYfOPKWlxZ9tD1/6/OwCSAO/nWwXpVYPSWRL7j6cg4vnTpIuS8lOyz/q18zQleO7H59ckS5mhd/KbM21FZRr/fInq5uwCw8Zehga";

        /**
         * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
         * localization engine.
         */
        private VuforiaLocalizer vuforia;

        /**
         * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
         * Detection engine.
         */
        private TFObjectDetector tfod;

        @Override
        public void runOpMode() {
            // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
            // first.
            initVuforia();
            initTfod();

            /**
             * Activate TensorFlow Object Detection before we wait for the start command.
             * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
             **/
            if (tfod != null) {
                tfod.activate();

                // The TensorFlow software will scale the input images from the camera to a lower resolution.
                // This can result in lower detection accuracy at longer distances (> 55cm or 22").
                // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
                // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
                // should be set to the value of the images used to create the TensorFlow Object Detection model
                // (typically 16/9).
                tfod.setZoom(1, 16.0/9.0);
            }

            /** Wait for the game to begin */
            telemetry.addData(">", "Press Play to start op mode");
            telemetry.update();
            waitForStart();

            if (opModeIsActive()) {
                while (opModeIsActive()) {
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
                                i++;
                            }
                            telemetry.update();
                        }
                    }
                }
            }
        }

        /**
         * Initialize the Vuforia localization engine.
         */
        private void initVuforia() {
            /*
             * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
             */
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

            parameters.vuforiaLicenseKey = VUFORIA_KEY;
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

            //  Instantiate the Vuforia engine
            vuforia = ClassFactory.getInstance().createVuforia(parameters);

            // Loading trackables is not necessary for the TensorFlow Object Detection engine.
        }

        /**
         * Initialize the TensorFlow Object Detection engine.
         */
        private void initTfod() {
            int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                    "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
            tfodParameters.minResultConfidence = 0.5f;
            tfodParameters.isModelTensorFlow2 = true;
            tfodParameters.inputSize = 320;
            tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
//        tfod.loadModelFromFile("/storage/emulated/0/FreightFrenzy_BCDM.tflite") ;
            tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
        }
    }

    private DcMotor DuckSpin;
    private DcMotor mtrFrontRight;
    private DcMotor mtrFrontLeft;
    private DcMotor mtrBackRight;
    private DcMotor mtrBackLeft;
    private DcMotor pullArm;
    public Servo claw;
    private static final String VUFORIA_KEY = "Ae4Y1tr/////AAABmUOIhh5VUERBieW2UEGVyT2AGvBs+tqZimMoeJTBL57NfKJQjp9v+D/teyPEUYRfVkkTnyZEQGfCewAz0dZlwLkfxcfyWDbEBz33yGrmSEZY7WleEqYVt1P3Eewq1wFWHKxosHyETLU+Vs2XfoKtGXJou46WMNSofNvh4CvLU1bYwwA4Yr9nZ7xbgEySOopKhfXujf1XMqKcmgag7jXEj9WaEUY+7ehRq1A8hKtQjYb2YlrKC5zNZSeiBBTBmYjTbl7Zhn1QxYfOPKWlxZ9tD1/6/OwCSAO/nWwXpVYPSWRL7j6cg4vnTpIuS8lOyz/q18zQle O7H59ckS5mhd/KbM21FZRr/fInq5uwCw8Zehga ";
    private static final int STOP_ROBOT_TIME = 1000;

    public void Blue1_Position1(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(  1, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,30);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(1, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Blue1_Position2(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(  2, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,30);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(2, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Blue1_Position3(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(  4, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,30);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(4, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Blue2_Position1(Robot r){
        r.claw(false);
        r.opMode.sleep (STOP_ROBOT_TIME);
        r.arm(1, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,-30);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(1, 1000);
        r.opMode.sleep (STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Blue2_Position2(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(2, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,-30);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(2, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Blue2_Position3(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(4, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,-30);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(4, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);

    }
    public void Red1_Position1(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(  1, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,-59);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(1, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Red1_Position2(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(  2, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,-59);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(2, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Red1_Position3(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(  4, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.5,-59);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(4, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Red2_Position1(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(1, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.2,59);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(1, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Red2_Position2(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(2, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.2,59);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(2, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
    public void Red2_Position3(Robot r){
        r.claw(false);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.arm(4, 1000);
        r.driveEncodersInch(.1,21);
        //sleep(2000);
        r.turnEncodersDegree(.2,59);
        //sleep(2000);
        r.driveEncodersInch(.1, 8);
        //sleep(2000);
        r.arm(4, 1000);
        r.opMode.sleep(STOP_ROBOT_TIME);
        r.claw(true);
    }
}