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

    final double intake_open = 0.12;
    final double intake_close = 0;

    final double outtake_open = 0.15;
    final double outtake_close = 0.35;

    final double wrist_in = 1;
    final double wrist_out = 0;
    final double wrist_mid = 0.4;
    final double wrist_transfer = 0.95;

    public boolean intakeMode = false;
    public boolean intakeButtonState = false;
    public boolean outtakeMode = false;
    public boolean outtakeButtonState = false;

    public int transferState = 0;

    public int armState = 0;

    public int armTargetPos = 0;
    public int armCurrentPos = 0;

    public double cmByTick = 0.053855874; //constante que define cuantos cm hay en cada tick de los motores el chasis

    public int verticalSlide_posToSpecimen = 1300;
    public int verticalSlide_posToScoreSpecimen = 1050;
    public int verticalSlide_posToRetract = -50;
    public int verticalSlide_posToGetSpecimen = 100;

    public int verticalSlide_posToSample = 2700;

    public int arm_posTo90deg = 80;
    public int arm_posToSpecimen = 87;
    public int arm_posToScore = 60;

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
        //verticalSlide1Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalSlide1Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        verticalSlide2Motor = hardwareMap.get(DcMotorEx.class, "vS2");
        verticalSlide2Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide2Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //verticalSlide2Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        verticalSlide2Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake = hardwareMap.get(Servo.class, "in");
        intake.setPosition(intake_close);

        outtake = hardwareMap.get(Servo.class, "out");
        outtake.setDirection(Servo.Direction.REVERSE);
        outtake.setPosition(outtake_close);

        wrist = hardwareMap.get(Servo.class, "wrist");
        wrist.setDirection(Servo.Direction.REVERSE);
        wrist.setPosition(wrist_in);

        //otos = hardwareMap.get(SparkFunOTOS.class, "otos");
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

    public void wristControl(double y, boolean button){
        if(y > 0.5){
            wrist.setPosition(wrist_out);
        } else if(y < -0.5){
            wrist.setPosition(wrist_mid);
        } else if(button){
            wrist.setPosition(wrist_transfer);
        }
    }

    public void armControlManual(double y){
        armMotor.setPower(y);
    }

    public void armControl(){
        switch(armState){
            case 0:
                armTargetPos = armMotor.getCurrentPosition() - armCurrentPos;
                armMotor.setTargetPosition(armTargetPos);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.8);
                armCurrentPos = armTargetPos;
                armState = 1;
                break;
            case 1:
                armTargetPos = 100 + armMotor.getCurrentPosition();
                armMotor.setTargetPosition(armTargetPos);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.8);
                armCurrentPos = armTargetPos;
                armState = 2;
                break;
            case 2:
                armTargetPos = 40 + armMotor.getCurrentPosition();
                armMotor.setTargetPosition(armTargetPos);
                armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                armMotor.setPower(0.8);
                armCurrentPos = armTargetPos;
                armState = 0;
                break;
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
            verticalSlide1Motor.setPower(0);
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

    public void transfer (boolean button){
        if(transferState == 0){
            wrist.setPosition(wrist_mid);

            transferState = 1;
        } else if(transferState == 1){
            wrist.setPosition(wrist_transfer);

            outtake.setPosition(outtake_open);

            transferState = 2;
        } else if(transferState == 2){
            outtake.setPosition(outtake_close);

            intake.setPosition(intake_open);

            transferState = 0;
        }
    }

    public void stopMotors() {
        motorsSetPower(0, 0, 0, 0);
    }

    public void motorsSetPower (double powDeIz, double powDeDe, double powAtIz, double powAtDe) {
        frontLeft.setPower(powDeIz);
        frontRight.setPower(powDeDe);
        backLeft.setPower(powAtIz);
        backRight.setPower(powAtDe);
    }

    //se configuran los encoders para poder acceder a las lecturas y configurar limites
    public void resetChassisEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void resetMechEncoders() {
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide1Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        verticalSlide2Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlide1Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        verticalSlide2Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public int encoderAverage () {
        return (frontLeft.getCurrentPosition() + frontRight.getCurrentPosition() +
                backLeft.getCurrentPosition() + backRight.getCurrentPosition()) / 4;
    }

    public int encoderAverageOneSide () {
        return (frontLeft.getCurrentPosition() + frontRight.getCurrentPosition()) / 2;
    }

    public int encoderAverageDiagonalFlBr () {
        return (frontLeft.getCurrentPosition() + backRight.getCurrentPosition()) / 2;
    }

    public int encoderAverageDiagonalBlFr () {
        return (backLeft.getCurrentPosition() + frontRight.getCurrentPosition()) / 2;
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