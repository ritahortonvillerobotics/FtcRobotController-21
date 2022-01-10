package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;

@Autonomous (name = "Square")
public class square extends LinearOpMode {

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

        r.opMode = this;

        waitForStart();

        for(int i=0;i<4 && opModeIsActive();i++){
            r.turnEncodersDegree(.25, -360);
            sleep(5000);
            r.turnEncodersDegree(.25, -360);
            sleep(5000);
        }
    }
}