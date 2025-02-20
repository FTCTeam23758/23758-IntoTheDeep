package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Commands.Plesio;

@TeleOp(name = "Plesio Teleop")

public class PlesioTeleop extends OpMode {
    private final Plesio plesio = new Plesio();

    @Override
    public void init(){
        telemetry.addLine("Robot Initialized.");
        telemetry.update();

        plesio.init(hardwareMap);
        plesio.otos.calibrateImu();
        plesio.otos.resetTracking();
    }

    @Override
    public void loop(){
        SparkFunOTOS.Pose2D pos = plesio.otos.getPosition();
        double robotHeading = pos.h;
        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;

        plesio.driveRobotCentric(x, -y, rx);

        //plesio.driveFieldCentric(x, -y, rx, robotHeading);

        if (gamepad1.y) {
            plesio.otos.resetTracking();
        }

        plesio.intakeSetPos(gamepad2.y);
        plesio.outtakeSetPos(gamepad2.x);

        plesio.wristControl(-gamepad2.left_stick_y);
        plesio.armControl(-gamepad2.right_stick_y);
        plesio.verticalSlideControl(gamepad2.dpad_up, gamepad2.dpad_down);
        plesio.horizontalSlideControl(gamepad2.dpad_right, gamepad2.dpad_left);

        /*if(Math.abs(gamepad2.left_stick_y) > 0.3){
            plesio.wristControl(-gamepad2.left_stick_y);
        }

        if(Math.abs(gamepad2.right_stick_y) > 0.3){
            plesio.armControl(-gamepad2.right_stick_y);
        }

        if(gamepad2.dpad_up || gamepad2.dpad_down){
            plesio.verticalSlideControl(gamepad2.dpad_up, gamepad2.dpad_down);
        }

        if(gamepad2.dpad_right || gamepad2.dpad_left){
            plesio.horizontalSlideControl(gamepad2.dpad_right, gamepad2.dpad_left);
        }
         */

        telemetry.addData("Robot Heading:", robotHeading);
        telemetry.addData("Left Stick readings:", gamepad2.left_stick_y);
        telemetry.addData("Right Stick readings:", gamepad2.right_stick_y);
        telemetry.update();
    }
}
