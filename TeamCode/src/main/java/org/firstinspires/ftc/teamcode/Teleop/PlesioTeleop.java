package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.Plesio;

@TeleOp(name = "Plesio Teleop")

public class PlesioTeleop extends OpMode {
    private final Plesio plesio = new Plesio();

    @Override
    public void init(){
        telemetry.addLine("Robot Initialized.");
        telemetry.update();

        plesio.init(hardwareMap);
        //plesio.otos.calibrateImu();
        //plesio.otos.resetTracking();
    }

    @Override
    public void loop(){
        //SparkFunOTOS.Pose2D pos = plesio.otos.getPosition();
        //double robotHeading = pos.h;

        //plesio.mecanumDriveFC(gamepad1.left_stick_x, gamepad1.left_stick_y,
                //gamepad1.right_stick_x, robotHeading);

        //plesio.mecanumDriveRC(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

        //if (gamepad1.y) {
            //plesio.otos.resetTracking();
        //}
    }
}
