package org.firstinspires.ftc.teamcode.Commands;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Plesio {
    public DcMotorEx frontLeft, backLeft, frontRight, backRight;
    public DcMotorEx slideMotor;
    public DcMotorEx verticalSlide1Motor, verticalSlide2Motor;
    public DcMotorEx armMotor;
    public Servo intake;
    public Servo outtake;
    public Servo wrist;
    public SparkFunOTOS otos;

    final double intake_open = 0.12;
    final double intake_close = 0;

    final double outtake_open = 0.15;
    final double outtake_close = 0.35;

    final double wrist_in = 0.2;
    final double wrist_out = 1;
    final double wrist_mid = 0.6;

    public boolean intakeMode = false;
    public boolean intakeButtonState = false;
    public boolean outtakeMode = false;
    public boolean outtakeButtonState = false;
    public boolean wristButtonState = false;

    public int wristState = 0;

    public void init(HardwareMap hardwareMap) {
        frontLeft = hardwareMap.get(DcMotorEx.class, "fl");
        backLeft = hardwareMap.get(DcMotorEx.class, "bl");
        frontRight = hardwareMap.get(DcMotorEx.class, "fr");
        backRight = hardwareMap.get(DcMotorEx.class, "br");

        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        slideMotor = hardwareMap.get(DcMotorEx.class, "s");
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slideMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        verticalSlide1Motor = hardwareMap.get(DcMotorEx.class, "vS1");
        verticalSlide1Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide1Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //verticalSlide2Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalSlide1Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        verticalSlide2Motor = hardwareMap.get(DcMotorEx.class, "vS2");
        verticalSlide2Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide2Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //verticalSlide2Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalSlide2Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake = hardwareMap.get(Servo.class, "in");
        intake.setPosition(intake_close);

        outtake = hardwareMap.get(Servo.class, "out");
        outtake.setDirection(Servo.Direction.REVERSE);
        outtake.setPosition(outtake_close);

        wrist = hardwareMap.get(Servo.class, "wrist");
        outtake.setDirection(Servo.Direction.REVERSE);
        wrist.setPosition(wrist_in);

        otos = hardwareMap.get(SparkFunOTOS.class, "otos");
    }

    public void intakeOpen(){
        intake.setPosition(intake_open);
    }

    public void intakeClose(){
        intake.setPosition(intake_close);
    }

    public void outtakeOpen(){
        outtake.setPosition(outtake_open);
    }

    public void outtakeClose(){
        outtake.setPosition(outtake_close);
    }

    public void driveRobotCentric(double x, double y, double rx){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

    public void driveFieldCentric(double x, double y, double rx, double heading){
        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;

        frontLeft.setPower(frontLeftPower);
        backLeft.setPower(backLeftPower);
        frontRight.setPower(frontRightPower);
        backRight.setPower(backRightPower);
    }

    public void intakeSetPos(boolean button) {
        if (button && !intakeButtonState) {
            intakeMode = !intakeMode;
        }

        intakeButtonState = button;

        intake.setPosition(intakeMode ? intake_open : intake_close);
    }

    public void outtakeSetPos(boolean button) {
        if (button && !outtakeButtonState) {
            outtakeMode = !outtakeMode;
        }

        outtakeButtonState = button;

        outtake.setPosition(outtakeMode ? outtake_open : outtake_close);
    }

    public void wristControl(double y){
        if(y > 0.5){
            wrist.setPosition(wrist_out);
        } else if(y < 0.5){
            wrist.setPosition(wrist_mid);
        }
    }

    public void armControl(double y){
        if(y > 0.5){
            armMotor.setPower(1);
        } else if(y < -0.5){
            armMotor.setPower(-1);
        } else{
            armMotor.setPower(0);
        }
    }

    public void verticalSlideControl(boolean up, boolean down){
        if(up){
            verticalSlide1Motor.setPower(1);
            verticalSlide2Motor.setPower(1);
        } else if(down){
            verticalSlide1Motor.setPower(-1);
            verticalSlide2Motor.setPower(-1);
        } else{
            verticalSlide2Motor.setPower(0);
            verticalSlide2Motor.setPower(0);
        }
    }

    public void horizontalSlideControl(boolean up, boolean down){
        if(up){
            slideMotor.setPower(1);
        } else if(down){
            slideMotor.setPower(-1);
        } else {
            slideMotor.setPower(0);
        }
    }


    public Action intakeOpenAction(){
        return new ServoAction(intake, intake_open);
    }

    public Action intakeCloseAction(){
        return new ServoAction(intake, intake_close);
    }

    public Action outtakeOpenAction(){
        return new ServoAction(outtake, outtake_open);
    }

    public Action outtakeCloseAction(){
        return new ServoAction(outtake, outtake_close);
    }

    public Action wristInAction(){
        return new ServoAction(wrist, wrist_in);
    }

    public Action wristOutAction(){
        return new ServoAction(wrist, wrist_out);
    }

}