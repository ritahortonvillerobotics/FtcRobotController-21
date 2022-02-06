 package org.firstinspires.ftc.teamcode;//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

//import hortonvillerobotics.FinalRobotConfiguration;


@Autonomous(name = "Blue1", group = "Autonomous")
//
public class Blue1 extends LinearOpMode  {
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
        private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
        private static final String[] LABELS = {
                "Ball",
                "Cube",
                "Duck",
                "Marker"
        };


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
                "Ae4Y1tr/////AAABmUOIhh5VUERBieW2UEGVyT2AGvBs+tqZimMoeJTBL57NfKJQjp9v+D/teyPEUYRfVkkTnyZEQGfCewAz0dZlwLkfxcfyWDbEBz33yGrmSEZY7WleEqYVt1P3Eewq1wFWHKxosHyETLU+Vs2XfoKtGXJou46WMNSofNvh4CvLU1bYwwA4Yr9nZ7xbgEySOopKhfXujf1XMqKcmgag7jXEj9WaEUY+7ehRq1A8hKtQjYb2YlrKC5zNZSeiBBTBmYjTbl7Zhn1QxYfOPKWlxZ9tD1/6/OwCSAO/nWwXpVYPSWRL7j6cg4vnTpIuS8lOyz/q18zQleO7H59ckS5mhd/KbM21FZRr/fInq5uwCw8Zehga" +
                        "";

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
    //Restore Me
    int firstDuckPosition = 299;
    int secondDuckPosition = 511;
    double drivePowerScale = 1;
    double theta1 = 0;

    boolean isCollecting = false;
    boolean g1AP = false;
    boolean g2BL = false;

    private static final int STOP_ROBOT_TIME = 1000;
    //TestRobotConfig testRobotConfig = new TestRobotConfig();
    int a = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot r = new Robot(this);
        /*if(label.getRight() && recognition.getBottom <= 300){
            r.arm(1,200);
        }else if (recognition.getRight() && recognition.getBottom >= 400){
            r.arm(2,200);
        }else{
            r.arm(4,200);
        }*/
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
        r.claw(true);
        waitForStart();
        r.claw(false);
        sleep(STOP_ROBOT_TIME);
        r.arm(1,300);
        r.driveEncodersInch(.1,5);
        sleep(STOP_ROBOT_TIME);
        r.turnEncodersDegree(.1,78);
        sleep(STOP_ROBOT_TIME);
        r.driveEncodersInch(.1,-28);
        sleep(STOP_ROBOT_TIME);
        r.mtrBackLeft.setPower(-0.07);
        r.mtrFrontLeft.setPower(-0.07);
        r.duckspinner(false,2150, 0.3);
        r.mtrBackLeft.setPower(0);
        r.mtrFrontLeft.setPower(0);
        sleep(STOP_ROBOT_TIME);
        r.driveEncodersInch(.1,4);
        sleep(STOP_ROBOT_TIME);
        r.turnEncodersDegree(.25,-30);
        sleep(STOP_ROBOT_TIME);
        r.driveEncodersInch(.1,12);
        r.turnEncodersDegree(.25,90);
        sleep(STOP_ROBOT_TIME);
        r.driveEncodersInch(.1, -19);
        sleep(STOP_ROBOT_TIME);
        r.turnEncodersDegree(.25,-28 );
        sleep(STOP_ROBOT_TIME);
        r.driveBackTimed(-.1,30000);




    }
}