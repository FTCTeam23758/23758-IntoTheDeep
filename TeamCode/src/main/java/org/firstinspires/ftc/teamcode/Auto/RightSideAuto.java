package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands.Plesio;

@Autonomous(name = "Auto Specimen Nacional")
public class RightSideAuto extends LinearOpMode {
    private final Plesio plesio = new Plesio();

    @Override
    public void runOpMode() {
        plesio.init(hardwareMap);
        //plesio.otos.calibrateImu();
        //plesio.otos.resetTracking();

        telemetry.addData("Status", "Initialized");
        telemetry.addLine("AUTONOMO SPECIMENS:)");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            scoreFirstSpecimen();

            //pickSecondSpecimen();

            leaveSample();
            //scoreSecondSpecimen();

            //park();

            //pickThirdSpecimen();

            //scoreThirdSpecimen();
        }
    }

    private void scoreFirstSpecimen(){
        /*plesio.outtakeClose(); //close outtake
        strafe(18, 0.6) ; //left
        //armAutoTime(200, 0.8); //upwards arm
        //plesio.resetMechEncoders(); //set new 0
        //sleep(300);
        armAuto(plesio.arm_posToSpecimen, 0.85); //set position
        //sleep(3000);
        verticalSlideAuto(plesio.verticalSlide_posToSpecimen, 1); //upwards slide
        straight(-74, 0.5); //fwd till wall
        //straight(3, 0.5);
        verticalSlideAuto(plesio.verticalSlide_posToScoreSpecimen, 1); //downwards slide
        //armAuto(plesio.arm_posToScore, 0.8);
        plesio.armMotor.setPower(-0.7);
        sleep(400);
        plesio.armMotor.setPower(0);
        verticalSlideAuto(600, 1);
        //armAuto(plesio.arm_posToScore - 10, 0.8);
        sleep(100);
        plesio.outtakeOpen();
        verticalSlideAuto(plesio.verticalSlide_posToRetract, 1); //downwards slide
        armAuto(plesio.arm_posTo90deg, 0.85);

         */
        plesio.outtakeClose(); //close outtake
        strafe(20, 0.6) ; //left
        //armAutoTime(200, 0.8); //upwards arm
        armAuto(90, 0.85); //set position
        verticalSlideAuto(1530, 1); //upwards slide
        straight(-72, 0.5); //fwd till wall
        sleep(300);
        straight(-10, 0.3);
        straight(2, 0.3);
        armAuto(54, 1);
        //straight(-5, 0.5);
        //plesio.outtakeOpen();
        verticalSlideAuto(700, 1); //downwards slide
        sleep(300);
        plesio.outtakeOpen();
        verticalSlideAuto(plesio.verticalSlide_posToRetract, 1); //downwards slide
        //armAuto(plesio.arm_posTo90deg, 0.85);

    }

    private void pickSecondSpecimen(){
        diagonal(60, 0.6, -1); //left diagonal
        strafe(-20, 0.6);
        //turnToAngleIMU(179, 0.6);
        turn(90, 0.6);
        straight(-10, 0.45);
        plesio.outtakeClose();
        verticalSlideAuto(plesio.verticalSlide_posToGetSpecimen, 1);
    }

    private void scoreSecondSpecimen(){
        strafe(-30, 0.6);
        diagonal(90, 0.7, -1);
        verticalSlideAuto(plesio.verticalSlide_posToSpecimen, 1);
        //turnToAngleIMU(0, 0.6);
        turn(40, 0.6);
        armAuto(plesio.arm_posToSpecimen, 0.85); //set position
        //sleep(3000);
        verticalSlideAuto(plesio.verticalSlide_posToSpecimen, 1); //upwards slide
        straight(-40, 0.5); //fwd till wall
        //straight(3, 0.5);
        verticalSlideAuto(plesio.verticalSlide_posToScoreSpecimen, 1); //downwards slide
        //armAuto(plesio.arm_posToScore, 0.8);
        plesio.armMotor.setPower(-0.7);
        sleep(400);
        plesio.armMotor.setPower(0);
        verticalSlideAuto(800, 0.9);
        //armAuto(plesio.arm_posToScore - 10, 0.8);
        sleep(100);
        plesio.outtakeOpen();
        verticalSlideAuto(plesio.verticalSlide_posToRetract, 1); //downwards slide
        armAuto(plesio.arm_posTo90deg, 0.85);
    }

    private void park(){
        diagonal(60, 0.6, -1); //left diagonal
        strafe(-20, 0.6);
    }

    private void leaveSample(){
        diagonal(80, 0.7, -1); //left diagonal
        strafe(-70, 0.6);
        straight(-80, 0.6);
        strafe(-20, 0.6);
        straight(100, 0.5);
        straight(-15, 0.6);
        //turnToAngleIMU(179, 0.6);
        turn(5, 0.5);
        //straight(-25, 0.4);
        //plesio.outtakeClose();
        //verticalSlideAuto(plesio.verticalSlide_posToGetSpecimen, 1);
    }

    private void pickThirdSpecimen(){
        diagonal(90, 0.6, -1); //left diagonal
        strafe(-30, 0.6);
        //turnToAngleIMU(179, 0.6);
        turn(40, 0.6);
        straight(-25, 0.45);
        plesio.outtakeClose();
        verticalSlideAuto(plesio.verticalSlide_posToGetSpecimen, 1);
    }

    private void scoreThirdSpecimen(){
        strafe(40, 0.6);
        diagonal(80, 0.6, -1);
        //turnToAngleIMU(0, 0.6);
        turn(40, 0.6);
        verticalSlideAuto(plesio.verticalSlide_posToSpecimen, 1);
        straight(-40, 0.4);
        verticalSlideAuto(plesio.verticalSlide_posToScoreSpecimen, 1);
        plesio.outtakeOpen();
    }

    //-----------------------------

    public void straight (double cm, double maxPower) {
        plesio.resetChassisEncoders();
        int targetPos = (int) (cm/plesio.cmByTick);

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        double kP_dist = 0.02; //Valor constante ya tuneado
        //double kP_angle = 0.0; //Valor constante ya tuneado

        //SparkFunOTOS.Pose2D pos = plesio.otos.getPosition();
        //double targetAngle = pos.h;

        while (opModeIsActive()){
            //double currentAngle = plesio.otos.getPosition().h;

            int currentPos = plesio.encoderAverage();
            double errorDist = targetPos - currentPos;

            double power = (kP_dist * errorDist);
            power = Math.max(-maxPower, Math.min(power, maxPower));

            //double errorAngle = targetAngle - currentAngle;
            //double correction = kP_angle * errorAngle;

            //double leftPow = power + correction;
            //double rightPow = power - correction;

            plesio.motorsSetPower(power, power, power, power);

            if(Math.abs(errorDist) <=1) break;

            if (timer.seconds() > 2) {
                telemetry.addData("Status", "Time limit exceeded!");
                break;
            }

            telemetry.addData("Encoders", "delanteDe: %d , delanteIz: %d , atrasDe: %d , atrasIz: %d",
                    plesio.frontLeft.getCurrentPosition(), plesio.frontRight.getCurrentPosition(),
                    plesio.backRight.getCurrentPosition(), plesio.backLeft.getCurrentPosition());
            telemetry.addData("Average encoder value: ", plesio.encoderAverage());
            telemetry.addData("Target position: ", targetPos);
            //telemetry.addData("Error angulo: ", errorAngle);
            telemetry.addData("Error distancia: ", errorDist);
            telemetry.addData("Power: ", power);
            telemetry.update();
        }

        plesio.stopMotors();
        sleep(80);
    }

    public void strafe (double cm, double maxPower) {
        plesio.resetChassisEncoders();
        int targetPos = (int) (cm/plesio.cmByTick);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        double kP_dist = 0.02; //Valor constante ya tuneado
        //double kP_angle = 0.01;

        //SparkFunOTOS.Pose2D pos = plesio.otos.getPosition();
        //double targetAngle = pos.h;

        while (opModeIsActive()){
            //double currentAngle = plesio.otos.getPosition().h;

            int currentPos = plesio.encoderAverageDiagonalFlBr();
            double error = targetPos - currentPos;

            double power = (kP_dist * error);
            power = Math.max(-maxPower, Math.min(power, maxPower));

            //double errorAngle = targetAngle - currentAngle;
            //double correction = kP_angle * errorAngle;

            //double leftPow = power + correction;
            //double rightPow = power - correction;

            plesio.motorsSetPower(power, -power, -power, power);

            //plesio.motorsSetPower(power, -power, -power, power);

            if(Math.abs(error) <=2) break;
            if (timer.seconds() > 3) {
                telemetry.addData("Status", "Time limit exceeded!");
                telemetry.update();
                break;
            }

            telemetry.addData("Encoders", "delanteDe: %d , delanteIz: %d , atrasDe: %d , atrasIz: %d",
                    plesio.frontRight.getCurrentPosition(), plesio.frontLeft.getCurrentPosition(),
                    plesio.backRight.getCurrentPosition(), plesio.backLeft.getCurrentPosition());
            telemetry.addData("Average encoder value: ", plesio.encoderAverage());
            telemetry.addData("Target position: ", targetPos);
            telemetry.addData("Error: ", error);
            telemetry.addData("Power: ", power);
            telemetry.update();
        }
        plesio.stopMotors();
        sleep(80);
    }

    public void diagonal(double cm, double maxPower, int side){
        plesio.resetChassisEncoders();
        int targetPos = (int) (cm/plesio.cmByTick);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        double kP_dist = 0.02; //Valor constante ya tuneado
        double kP_angle = 0.01;

        //SparkFunOTOS.Pose2D pos = plesio.otos.getPosition();
        //double targetAngle = pos.h;

        int currentPos = 0;

        while (opModeIsActive()){
            //double currentAngle = plesio.otos.getPosition().h;

            if(side == 1){
                currentPos = plesio.encoderAverageDiagonalFlBr();
            } else if(side == -1){
                currentPos = plesio.encoderAverageDiagonalBlFr();
            }
            //int currentPos = plesio.encoderAverageDiagonalFlBr();
            double error = targetPos - currentPos;

            double power = (kP_dist * error);
            power = Math.max(-maxPower, Math.min(power, maxPower));

            //double errorAngle = targetAngle - currentAngle;
            //double correction = kP_angle * errorAngle;

            //double frontPow = power + correction;
            //double backPow = power - correction;

            if(side == 1){
                plesio.motorsSetPower(power, 0, 0, power);
            } else if(side == -1){
                plesio.motorsSetPower(0, power, power, 0);
            }

            if(Math.abs(error) <=2) break;
            if (timer.seconds() > 3) {
                telemetry.addData("Status", "Time limit exceeded!");
                telemetry.update();
                break;
            }

            telemetry.addData("Encoders", "delanteDe: %d , delanteIz: %d , atrasDe: %d , atrasIz: %d",
                    plesio.frontRight.getCurrentPosition(), plesio.frontLeft.getCurrentPosition(),
                    plesio.backRight.getCurrentPosition(), plesio.backLeft.getCurrentPosition());
            telemetry.addData("Average encoder value: ", plesio.encoderAverage());
            telemetry.addData("Target position: ", targetPos);
            telemetry.addData("Error: ", error);
            telemetry.addData("Power: ", power);
            telemetry.update();
        }
        plesio.stopMotors();
        sleep(80);
    }

    public void turn (double cm, double maxPower) {
        plesio.resetChassisEncoders();
        int targetPos = (int) (cm/plesio.cmByTick);

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        double kP_dist = 0.02; //Valor constante ya tuneado
        //double kP_angle = 0.0; //Valor constante ya tuneado

        //SparkFunOTOS.Pose2D pos = plesio.otos.getPosition();
        //double targetAngle = pos.h;

        while (opModeIsActive()){
            //double currentAngle = plesio.otos.getPosition().h;

            int currentPos = plesio.encoderAverageOneSide();
            double errorDist = targetPos - currentPos;

            double power = (kP_dist * errorDist);
            power = Math.max(-maxPower, Math.min(power, maxPower));

            //double errorAngle = targetAngle - currentAngle;
            //double correction = kP_angle * errorAngle;

            //double leftPow = power + correction;
            //double rightPow = power - correction;

            plesio.motorsSetPower(power, -power, power, -power);

            if(Math.abs(errorDist) <=1) break;

            if (timer.seconds() > 2) {
                telemetry.addData("Status", "Time limit exceeded!");
                break;
            }

            telemetry.addData("Encoders", "delanteDe: %d , delanteIz: %d , atrasDe: %d , atrasIz: %d",
                    plesio.frontLeft.getCurrentPosition(), plesio.frontRight.getCurrentPosition(),
                    plesio.backRight.getCurrentPosition(), plesio.backLeft.getCurrentPosition());
            telemetry.addData("Average encoder value: ", plesio.encoderAverage());
            telemetry.addData("Target position: ", targetPos);
            //telemetry.addData("Error angulo: ", errorAngle);
            telemetry.addData("Error distancia: ", errorDist);
            telemetry.addData("Power: ", power);
            telemetry.update();
        }

        plesio.stopMotors();
        sleep(80);
    }

    /*public void turnToAngleIMU (double targetAngle, double maxPower) {
        double kP = 0.1;

        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while (opModeIsActive()) {
            SparkFunOTOS.Pose2D pos = plesio.otos.getPosition();
            double currentAngle = pos.h;
            double error = targetAngle - currentAngle;
            double power = (kP * error);
            power = Math.max(-maxPower, Math.min(power, maxPower));

            plesio.motorsSetPower(-power, power, -power, power);

            if(Math.abs(error) <= 0.5) break;

            if (timer.seconds() > 2) {
                telemetry.addData("Status", "Time limit exceeded!");
                break;
            }

            telemetry.addData("Angle: ", pos.h);
            telemetry.addData("Error: ", error);
            telemetry.addData("Power: ", power);
            telemetry.update();
        }
        plesio.stopMotors();
    }

     */

    public void verticalSlideAuto(int pos, double power) {
        plesio.verticalSlide1Motor.setTargetPosition(pos);
        plesio.verticalSlide2Motor.setTargetPosition(pos);

        plesio.verticalSlide1Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        plesio.verticalSlide2Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        plesio.verticalSlide1Motor.setPower(power);
        plesio.verticalSlide2Motor.setPower(power);

        while (opModeIsActive() && plesio.verticalSlide1Motor.isBusy()) {
            telemetry.addData("Elevador", "Posición: %d", plesio.verticalSlide1Motor.getCurrentPosition());
            telemetry.update();
        }

        plesio.verticalSlide1Motor.setPower(0);
        plesio.verticalSlide2Motor.setPower(0);
    }

    public void armAuto(int pos, double power) {
        plesio.armMotor.setTargetPosition(pos);

        plesio.armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        plesio.armMotor.setPower(power);

        while (opModeIsActive() && plesio.armMotor.isBusy()) {
            telemetry.addData("Brazo", "Posición: %d", plesio.armMotor.getCurrentPosition());
            telemetry.update();
        }

        plesio.armMotor.setPower(0);
    }

    public void armAutoTime(long time, double power) {
        plesio.armMotor.setPower(power);
        sleep(time);
        plesio.armMotor.setPower(0);
    }
}
