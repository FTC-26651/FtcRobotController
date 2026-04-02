package org.firstinspires.ftc.teamcode.core.robot.util;

import dev.nextftc.control2.filters.KalmanFilter;
import dev.nextftc.control2.model.LinearModel;
import dev.nextftc.linalg.Matrix;
import dev.nextftc.linalg.N3;
import dev.nextftc.linalg.Vector;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class PoseKalmanFilter {
    private final KalmanFilter<N3, N3, N3> filter;

    private Pose lastOdomPose;

    /**
     * A Kalman filter for position robot position estimation.
     *
     * To use it, once you have constructed the filter, simply call PoseKalmanFilter.update(measured)
     * `measured` is whatever position you have measured. It will automatically take that and compare it
     * with the position from the follower to give a more accurate position
     *
     * You can learn more about the Kalman filter at https://www.ctrlaltftc.com/advanced/the-kalman-filter
     *
     * @param dt The discretization timestep in seconds (default 0.05s / 50ms)
     */
    public PoseKalmanFilter(double dt) {
        lastOdomPose = follower().getPose();

        /*
         * I honestly have no idea what's happening here. Some matrix math, but I don't know matrix math
         */
        Matrix<N3, N3> A = Matrix.identity(N3.INSTANCE);
        Matrix<N3, N3> B = Matrix.identity(N3.INSTANCE);
        Matrix<N3, N3> C = Matrix.identity(N3.INSTANCE);
        Matrix<N3, N3> D = Matrix.zero(N3.INSTANCE, N3.INSTANCE);

        LinearModel<N3, N3, N3> plant = new LinearModel<>(A, B, C, D, dt);

        /*
         * The data is how far we think odometry drifts per update. The first two variables are the
         * x and y coordinates, which are in inches. The last number is the heading change, which is
         * in radians
         */
        Vector<N3> stateStdDevs = Vector.of(N3.INSTANCE, 0.02, 0.02, 0.01);

        /*
         * This is similar to the state standard deviations, but instead of odometry it is for the
         * external measurement. Same variables, it's just how much we think it will deviate per cycle
         */
        Vector<N3> measurementStdDevs = Vector.of(N3.INSTANCE, 0.1, 0.1, 0.05);

        filter = new KalmanFilter<>(plant, stateStdDevs, measurementStdDevs, dt);
    }

    /**
     * Updates the Kalman filter
     *
     * @param measuredPose The position gotten from the other sensor, whatever that may be (typically
     *                     vision though)
     * @return The corrected state estimate
     */
    public Pose update(Pose measuredPose) {
        Pose current = follower().getPose();

        // find the delta (difference) in position between this time and the last time it was called
        double dx = current.getX() - lastOdomPose.getX();
        double dy = current.getY() - lastOdomPose.getY();
        double dTheta = AngleUnit.normalizeRadians(current.getHeading() - lastOdomPose.getHeading());

        lastOdomPose = current;

        // Turn the input deltas into a vector delta
        Vector<N3> u = Vector.of(N3.INSTANCE, dx, dy, dTheta);

        filter.predict(u);

        // Turn the measured pose into a vector
        Vector<N3> z = Vector.of(N3.INSTANCE, measuredPose.getX(), measuredPose.getY(), AngleUnit.normalizeRadians(measuredPose.getHeading()));

        Vector<N3> output = filter.correct(u, z);

        // Return the data we got from the filter as a pose
        return new Pose(
                output.get(0),
                output.get(1),
                AngleUnit.normalizeRadians(output.get(2))
        );
    }

    /**
     * Resets the Kalman filter to its initial state.  We also reset the last odometry pose to avoid carry-over
     */
    public void reset() {
        filter.reset();
        lastOdomPose = follower().getPose();
    }
}