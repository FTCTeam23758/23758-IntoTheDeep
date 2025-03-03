package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands.Plesio;

@Autonomous(name = "Auto Samples Nacional")
public class LeftSideAuto extends LinearOpMode {
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
            diagonal(65, 0.6, -1);
            turn(20, 0.6);
            verticalSlideAuto(plesio.verticalSlide_posToSample, 1);
            straight(-15, 0.5);
            plesio.outtakeOpen();
            straight(40, 0.6);
            verticalSlideAuto(plesio.verticalSlide_posToRetract, 1);
            turn(-20, 0.6);
            straight(35, 0.6);
            strafe(-20, 0.6);
            straight(-50, 0.6);
            straight(50, 0.6);
            turn(-40, 0.6);
            straight(40, 0.4);
        }
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

            int currentPos = plesio.encoderAverage();
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

        while (opModeIsActive() && (plesio.verticalSlide1Motor.isBusy() || plesio.verticalSlide2Motor.isBusy())) {
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
