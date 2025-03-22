package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Commands.Plesio;

@TeleOp(name = "Plesio Teleop")

public class PlesioTeleop extends OpMode {
    private final Plesio plesio = new Plesio();
    Gamepad previousGamepad2;

    @Override
    public void init(){
        telemetry.addLine("Robot Initialized.");
        telemetry.update();

        plesio.init(hardwareMap);
        previousGamepad2 = new Gamepad();
    }

    @Override
    public void loop(){
        Gamepad tempGamepad = new Gamepad();
        tempGamepad.copy(gamepad2);

        double x = gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double rx = gamepad1.right_stick_x;

        plesio.driveRobotCentric(x, -y, rx);

        plesio.intakeSetPos(gamepad2.y);
        plesio.outtakeSetPos(gamepad2.x);

        plesio.wristControl(-gamepad2.left_stick_y, gamepad2.left_stick_button);
        plesio.verticalSlideControl(gamepad2.dpad_up, gamepad2.dpad_down);
        plesio.horizontalSlideControl(gamepad2.dpad_right, gamepad2.dpad_left);

        if(gamepad2.a && !previousGamepad2.a){
            plesio.armControl();
        }

        telemetry.addData("Left Stick readings:", gamepad2.left_stick_y);
        telemetry.addData("Right Stick readings:", gamepad2.right_stick_y);
        telemetry.addData("Arm encoder: ", plesio.armMotor.getCurrentPosition());
        telemetry.addData("Slide: ", plesio.slideMotor.getCurrentPosition());
        telemetry.update();

        previousGamepad2.copy(tempGamepad);
    }
}
