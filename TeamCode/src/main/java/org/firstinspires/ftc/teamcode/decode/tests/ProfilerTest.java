package org.firstinspires.ftc.teamcode.decode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import dev.nullftc.profiler.Profiler;
import dev.nullftc.profiler.entry.BasicProfilerEntryFactory;
import dev.nullftc.profiler.exporter.CSVProfilerExporter;

import java.io.File;

@TeleOp(name = "Profiler Test", group = "Test")
public class ProfilerTest extends LinearOpMode {

    private Profiler profiler;

    @Override
    public void runOpMode() throws InterruptedException {
        File logsFolder = new File(AppUtil.FIRST_FOLDER, "logs");
        if (!logsFolder.exists()) logsFolder.mkdirs();

        long timestamp = System.currentTimeMillis();
        File file = new File(logsFolder, "profiler-" + timestamp + ".csv");

        profiler = Profiler.builder()
                .factory(new BasicProfilerEntryFactory())
                .exporter(new CSVProfilerExporter(file))
                .debugLog(true)
                .build();

        try {
            profiler.start("Init");
            // ... initialization logic ...
            profiler.end("Init");

            telemetry.addData("Status", "Waiting for start");
            telemetry.update();

            waitForStart();

            while (opModeIsActive() && !isStopRequested()) {
                profiler.start("Loop");
                // ... your main loop logic ...
                telemetry.update();
                profiler.end("Loop");
            }
        } finally {
            exportProfiler(file);
        }
    }

    private void exportProfiler(File file) {
        RobotLog.i("Starting async profiler export to: " + file.getAbsolutePath());

        Thread exportThread = new Thread(() -> {
            try {
                profiler.export();
                profiler.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        exportThread.setDaemon(true);
        exportThread.start();
    }
}
