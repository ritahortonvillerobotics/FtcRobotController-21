package org.firstinspires.ftc.teamcode;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;


    @Autonomous(name="Drive Imu",group= "Autonomous")

    public class imu extends LinearOpMode
    {
        //private DcMotor DuckSpin;
        //private DcMotor mtrFrontRight;
        //private DcMotor mtrFrontLeft;
        //private DcMotor mtrBackRight;
        //private DcMotor mtrBackLeft;
        //private DcMotor pullArm;
        //public Servo claw;
        BNO055IMU               imu;
        Orientation             lastAngles = new Orientation();
        double                  globalAngle, power = .30, correction;
        boolean                 aButton, bButton, yButton,xButton;


        // called when init button is  pressed.
        @Override
        public void runOpMode() throws InterruptedException {
            //DuckSpin = hardwareMap.dcMotor.get("DuckSpin");
            //mtrFrontRight = hardwareMap.dcMotor.get("mtrFrontRight");
            //mtrFrontLeft = hardwareMap.dcMotor.get("mtrFrontLeft");
            //mtrBackLeft = hardwareMap.dcMotor.get("mtrBackLeft");
            //mtrBackRight = hardwareMap.dcMotor.get("mtrBackRight");
            //pullArm = hardwareMap.dcMotor.get("pullArm");
            //claw = hardwareMap.servo.get("claw");

            //mtrFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //mtrFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //mtrBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //mtrBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //pullArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //mtrFrontRight.setZeroPowerBehavior(BRAKE);
            //mtrFrontLeft.setZeroPowerBehavior(BRAKE);
            //mtrBackLeft.setZeroPowerBehavior(BRAKE);
            //mtrBackRight.setZeroPowerBehavior(BRAKE);
            //pullArm.setZeroPowerBehavior(FLOAT);
            //mtrFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //mtrFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //mtrBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //mtrBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //pullArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


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
            // wait for start button.

            waitForStart();

            telemetry.addData("Mode", "running");
            telemetry.update();

         //   sleep(1000);

            // drive until end of period.
            imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
            while (opModeIsActive()) {
                // Use gyro to drive in a straight line.
                correction = checkDirection();

                telemetry.addData("1 imu heading", lastAngles.firstAngle);
                telemetry.addData("2 global heading", globalAngle);
                telemetry.addData("3 correction", correction);
                telemetry.addData("4 acceleration",imu.getAcceleration());
                telemetry.addData("5 other",getRuntime());
                telemetry.addData("6 imu",imu.getSystemStatus());
                telemetry.update();

                //mtrFrontLeft.setPower(power - correction);
                //mtrBackLeft.setPower(power - correction);
                //mtrFrontRight.setPower(power + correction);
                //mtrBackRight.setPower(power + correction);
                // We record the sensor values because we will test them in more than
                // one place with time passing between those places. See the lesson on
                // Timing Considerations to know why.

                aButton = gamepad1.a;
                bButton = gamepad1.b;
                yButton = gamepad1.y;


                if (yButton) {
                    // backup.
                    //mtrFrontRight.setPower(power);
                    //mtrFrontLeft.setPower(power);
                    //mtrBackLeft.setPower(power);
                    //mtrBackRight.setPower(power);
                    //sleep(500);

                    // stop.
                    //mtrFrontRight.setPower(0);
                    //mtrFrontLeft.setPower(0);
                    //mtrBackLeft.setPower(0);
                    ///mtrBackRight.setPower(0);
                }

                {
                    if (xButton) {
                        // backup.
                       // mtrFrontRight.setPower(-power);
                       // mtrFrontLeft.setPower(-power);
                       // mtrBackLeft.setPower(-power);
                      //  mtrBackRight.setPower(-power);
                      //  sleep(500);

                        // stop.
                        //mtrFrontRight.setPower(0);
                        //mtrFrontLeft.setPower(0);
                        //mtrBackLeft.setPower(0);
                        //mtrBackRight.setPower(0);
                    }

                    {


                        // turn 90 degrees right.
                        if (aButton) rotate(-90, power);

                        // turn 90 degrees left.
                        if (bButton) rotate(90, power);
                    }
                }

                // turn the motors off.
                //mtrFrontRight.setPower(0);
                //mtrFrontLeft.setPower(0);
                //mtrBackLeft.setPower(0);
                //mtrBackRight.setPower(0);
            }
        }
        /**
         * Resets the cumulative angle tracking to zero.
         */
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
            //mtrFrontRight.setPower(power);
            //mtrFrontLeft.setPower(power);
            //mtrBackLeft.setPower(power);
            //mtrBackRight.setPower(power);

            // rotate until turn is completed.
  /*          if (degrees < 0)
            {
                // On right turn we have to get off zero first.
                while (opModeIsActive() && getAngle() == 0) {}

                while (opModeIsActive() && getAngle() > degrees) {}
            }
            else    // left turn.
                while (opModeIsActive() && getAngle() < degrees) {}

            // turn the motors off.
            //mtrFrontRight.setPower(0);
            //mtrFrontLeft.setPower(0);
            //mtrBackLeft.setPower(0);
            //mtrBackRight.setPower(0);

            // wait for rotation to stop.
            //sleep(1000);

            // reset angle tracking on new heading.
            resetAngle();
        }

    }
*/
        }}