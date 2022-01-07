package org.firstinspires.ftc.teamcode;  //package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import java.util.ArrayList;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

@TeleOp(name = "tELEOP_IMUcRAWL", group = "TeleOp")
@Disabled
public class TeleOP_IMU_CRAWL extends OpMode {
    private DcMotor DuckSpin;
    private DcMotor mtrFrontRight;
    private DcMotor mtrFrontLeft;
    private DcMotor mtrBackRight;
    private DcMotor mtrBackLeft;
    private DcMotor pullArm;
    public Servo claw;
    private static final String VUFORIA_KEY = "Ae4Y1tr/////AAABmUOIhh5VUERBieW2UEGVyT2AGvBs+tqZimMoeJTBL57NfKJQjp9v+D/teyPEUYRfVkkTnyZEQGfCewAz0dZlwLkfxcfyWDbEBz33yGrmSEZY7WleEqYVt1P3Eewq1wFWHKxosHyETLU+Vs2XfoKtGXJou46WMNSofNvh4CvLU1bYwwA4Yr9nZ7xbgEySOopKhfXujf1XMqKcmgag7jXEj9WaEUY+7ehRq1A8hKtQjYb2YlrKC5zNZSeiBBTBmYjTbl7Zhn1QxYfOPKWlxZ9tD1/6/OwCSAO/nWwXpVYPSWRL7j6cg4vnTpIuS8lOyz/q18zQle O7H59ckS5mhd/KbM21FZRr/fInq5uwCw8Zehga ";
    BNO055IMU               imu;
    Orientation             lastAngles = new Orientation();
    double                  globalAngle, power = .30, correction;
    boolean                 aButton, bButton, yButton,xButton;
    @Override
    public void init() {

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
        pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);
/*
        //Need to be in CONFIG mode to write to registers
        imu.write8(BNO055IMU.Register.OPR_MODE, BNO055IMU.SensorMode.CONFIG.bVal & 0x0F);

        sleep(100); //Changing modes requires a delay before doing anything else

//Write to the AXIS_MAP_CONFIG register
        imu.write8(BNO055IMU.Register.AXIS_MAP_CONFIG, 0x06);

//Write to the AXIS_MAP_SIGN register
        imu.write8(BNO055IMU.Register.AXIS_MAP_SIGN, 0x0F);

//Need to change back into the IMU mode to use the gyro
        imu.write8(BNO055IMU.Register.OPR_MODE, 0x0F);

        sleep(100); //Changing modes again requires a delay

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        // make sure the imu gyro is calibrated before continuing.
        /* while (!isStopRequested() && !imu.isGyroCalibrated()) {
            sleep(50);
            idle();
        }
*/
        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", imu.getCalibrationStatus().toString());
        telemetry.update();
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

    public void loop() {

        //CURRENT DRIVE TRAIN
        mtrFrontRight.setPower(Math.abs(gamepad1.right_stick_y * .4) <= .07 ? 0 : gamepad1.right_stick_y);
        mtrFrontLeft.setPower(Math.abs(gamepad1.left_stick_y * .4) <= .07 ? 0 : -gamepad1.left_stick_y);
        //mtrBackLeft.setPower(Math.abs(gamepad1.left_stick_y * .4) <= .07 ? 0 : -gamepad1.left_stick_y);
        //mtrBackRight.setPower(Math.abs(gamepad1.right_stick_y * .4) <= .07 ? 0 : gamepad1.right_stick_y);
        mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        correction = checkDirection();
        telemetry.addData("1 imu heading", lastAngles.firstAngle);
        telemetry.addData("2 global heading", globalAngle);
        telemetry.addData("3 correction", correction);
        telemetry.addData("4 acceleration", imu.getAcceleration());
        telemetry.addData("5 other", getRuntime());
        telemetry.addData("6 imu", imu.getSystemStatus());
        telemetry.update();

        aButton = gamepad1.a;
        bButton = gamepad1.b;
        yButton = gamepad1.y;
        xButton = gamepad1.x;

        if (yButton) {
            // forward
            mtrFrontLeft.setPower(power - correction);
            mtrBackLeft.setPower(power - correction);
            mtrFrontRight.setPower(power + correction);
            mtrBackRight.setPower(power + correction);//mtrFrontRight.setPower(power);


            // stop.
            mtrFrontRight.setPower(0);
            mtrFrontLeft.setPower(0);
            mtrBackLeft.setPower(0);
            mtrBackRight.setPower(0);
        }

        {
            if (xButton) {
                // backup.
                mtrFrontLeft.setPower(-(power - correction));
                mtrBackLeft.setPower(-(power - correction));
                mtrFrontRight.setPower(-(power + correction));
                mtrBackRight.setPower(-(power + correction));


                // stop.
                mtrFrontRight.setPower(0);
                mtrFrontLeft.setPower(0);
                mtrBackLeft.setPower(0);
                mtrBackRight.setPower(0);
            }

            {


                // turn 90 degrees right.
                if (aButton) rotate(-90, power);

                // turn 90 degrees left.
                if (bButton) rotate(90, power);
            }
        }

        // turn the motors off.
        mtrFrontRight.setPower(0);
        mtrFrontLeft.setPower(0);
        mtrBackLeft.setPower(0);
        mtrBackRight.setPower(0);

        // Current 2022 Duck Spinner need new keys
        /*if(gamepad1.b== true && gamepad1.y==true){
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
*/
        //Lift Reset
        if (gamepad2.right_bumper == true) {
            pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
        } else if (gamepad2.right_bumper == true) {
            pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            pullArm.setPower(0);
        }

        //2022 Claw Code
        //Servo is 270 degree 0.00 reprsents full open
        //Servo gamepad x represents closed
        //New Servo gamepade x represents open
        // Need new keys
/*

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
     * Resets the cumulative angle tracking to zero.
     */
    }
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

    /**
     * See if we are moving in a straight line and if not return a power correction value.
     * @return Power adjustment, + is adjust left - is adjust right.
     */
    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }

    /**
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */
    private void rotate(int degrees, double power) {
        double leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0) {   // turn right.
            leftPower = power;
            rightPower = -power;
        } else if (degrees > 0) {   // turn left.
            leftPower = -power;
            rightPower = power;
        } else return;

        // set power to rotate.
       mtrFrontRight.setPower(rightPower);
       mtrFrontLeft.setPower(leftPower);
       mtrBackLeft.setPower(leftPower);
       mtrBackRight.setPower(rightPower);

        // rotate until turn is completed.
            if (degrees < 0)
            {
                // On right turn we have to get off zero first.
                while (getAngle() == 0) {}

                while (getAngle() > degrees) {}
            }
            else    // left turn.
                while ( getAngle() < degrees) {}

            // turn the motors off.
            mtrFrontRight.setPower(0);
            mtrFrontLeft.setPower(0);
            mtrBackLeft.setPower(0);
            mtrBackRight.setPower(0);


            // reset angle tracking on new heading.
            resetAngle();
        }

    }









