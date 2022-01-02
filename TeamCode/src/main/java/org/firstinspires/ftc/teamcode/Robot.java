package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class Robot<aButton, bButton, yButton, xButton> {
    // Hardware Setup for Robot Object
    public DcMotor mtrFrontRight;
    public DcMotor mtrFrontLeft;
    public DcMotor mtrBackLeft;
    public DcMotor mtrBackRight;
    public DcMotor DuckSpin;
    public DcMotor pullArm;
    public Servo claw;
    public BNO055IMU imu;

    Orientation lastAngles = new Orientation();
    double                  globalAngle, imupower = .30,   correction;
    boolean                 imuForward, imuBack, imuRight, imuLeft;





    public LinearOpMode opMode;
    boolean isFourWheel = false;


    public void imuRight(){
        rotate(-90, imupower);
        mtrFrontRight.setPower(0);
        mtrFrontLeft.setPower(0);
        mtrBackLeft.setPower(0);
        mtrBackRight.setPower(0);
        resetAngle();
    }
    public void imuRight(int angle){
        rotate (-angle, imupower);
        mtrBackRight.setPower(0);
        mtrBackLeft.setPower(0);
        mtrFrontRight.setPower(0);
        mtrFrontLeft.setPower(0);
        resetAngle();
    }
    public void imuLeft(){
        rotate(90, imupower);
        mtrFrontRight.setPower(0);
        mtrFrontLeft.setPower(0);
        mtrBackLeft.setPower(0);
        mtrBackRight.setPower(0);
        resetAngle();
    }
    public void imuLeft(int angle){
        rotate(angle,imupower);
        mtrFrontLeft.setPower(0);
        mtrFrontRight.setPower(0);
        mtrBackRight.setPower(0);
        mtrBackLeft.setPower(0);
        resetAngle();
    }














    public  void imuSetup() {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

    /**
     * Resets the cumulative angle tracking to zero.
     */
    public void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }
    /**
     * Get current cumulative angle rotation from last reset.
     * @return Angle in degrees. + = left, - = right.
     */
    public double getAngle()
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
    public double checkDirection()
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
    /*
     * Rotate left or right the number of degrees. Does not support turning more than 180 degrees.
     * @param degrees Degrees to turn, + is left - is right
     */

    public void rotate (int degrees, double imupower) {
        double leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0) {   // turn right.
            leftPower = imupower;
            rightPower = -imupower;
        } else if (degrees > 0) {   // turn left.
            leftPower = -imupower;
            rightPower = imupower;
        } else return;

        // set power to rotate.
        mtrFrontRight.setPower(imupower);
        mtrFrontLeft.setPower(imupower);
        mtrBackLeft.setPower(imupower);
        mtrBackRight.setPower(imupower);

        // rotate until turn is completed.
         if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            while (opMode.opModeIsActive() && getAngle() == 0) {}

            while (opMode.opModeIsActive() && getAngle() > degrees) {}
        }
        else    // left turn.
            while (opMode.opModeIsActive() && getAngle() < degrees) {}

        // turn the motors off.
        mtrFrontRight.setPower(0);
        mtrFrontLeft.setPower(0);
        mtrBackLeft.setPower(0);
        mtrBackRight.setPower(0);

        // wait for rotation to stop.
        opMode.sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }


    public void driveForward (double power, long timems) {

        if (isFourWheel == true) {
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setPower(power);
            mtrBackRight.setPower(power);
            mtrFrontLeft.setPower(-power);
            mtrBackLeft.setPower(-power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);


        }
       if((isFourWheel==false))
           mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
           mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
           mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
           mtrFrontRight.setPower(0.0);
           mtrFrontLeft.setPower(0.0);

           mtrBackRight.setPower(power);
           mtrBackLeft.setPower(-power);

           opMode.sleep(timems);

           mtrBackRight.setPower(0.0);
           mtrBackLeft.setPower(0.0);
       }


    public void driveBack (double power, long timems){
        if (isFourWheel == true) {
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setPower(-power);
            mtrBackRight.setPower(-power);
            mtrFrontLeft.setPower(power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);


        }
        if((isFourWheel==false)){
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackRight.setPower(-power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrBackRight.setPower(0.0);
            mtrBackLeft.setPower(0.0);
        }
    }
    public void turnRight (double power, long timems){
        if(isFourWheel == true) {
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setPower(-power);
            mtrBackRight.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);
        }
        if (isFourWheel == false){
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setPower(-power);
            mtrBackRight.setPower(power);

            opMode.sleep((timems));

            mtrFrontLeft.setPower(0.0);
            mtrFrontRight.setPower(0.0);
            mtrBackLeft.setPower(0.0);
            mtrBackRight.setPower(0.0);
        }
    }
    public void turnLeft (double power, long timems){
        if(isFourWheel == true) {
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontRight.setPower(-power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
            mtrBackLeft.setPower(0.0);
        }
        if (isFourWheel == false){
            mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            mtrFrontRight.setPower(-power);
            mtrBackLeft.setPower(power);

            opMode.sleep(timems);

            mtrFrontRight.setPower(0.0);
            mtrBackRight.setPower(0.0);
            mtrBackLeft.setPower(0.0);
            mtrFrontLeft.setPower(0.0);
        }
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

            opMode.sleep(timems);

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
        opMode.sleep(timems);
        DuckSpin.setPower(0.0);
    }
    public void setDrive (boolean value){
        isFourWheel=value;
    }
    public void allBrake() {
        mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontRight.setPower(0.0);
        mtrFrontLeft.setPower(0.0);
        mtrBackLeft.setPower(0.0);
        mtrBackRight.setPower(0.0);
    }
    public void standardBehavior(){
        mtrBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtrFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mtrFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        mtrFrontRight.setPower(0.0);
        mtrFrontLeft.setPower(0.0);
        mtrBackLeft.setPower(0.0);
        mtrBackRight.setPower(0.0);
    }
}
