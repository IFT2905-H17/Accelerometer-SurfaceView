package ca.umontreal.iro.hurtubin.rebound;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener eventListener;
    private Ball ball;

    ReboundAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ball = new Ball();

        animationView = new ReboundAnimationView(this, ball);

        setContentView(animationView);

        animationView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                ball.x = motionEvent.getX();
                ball.y = motionEvent.getY();

                // Reset la vitesse de la balle
                ball.vx = ball.vy = 0;

                return true;
            }
        });


        // Setup les senseurs
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Crée un Listener pour l'accéléromètre
        eventListener = new AccelerometerListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Évite de donner un très long delta_time si on quitte l'app et on revient plus tard
        ball.last_time = System.currentTimeMillis();

        // Attache les événements de l'accéléromètre au Listener
        sensorManager.registerListener(eventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Détache les événements de l'accéléromètre au Listener
        // (inutile d'updater les infos si l'application est en pause)
        sensorManager.unregisterListener(eventListener);
    }

    public class AccelerometerListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                // Met à jour les informations d'accélération
                ball.accelerometerChanged(sensorEvent);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    }
}
