package ca.umontreal.iro.hurtubin.rebound;

import android.hardware.SensorEvent;

public class Ball {
    public float size = 0;

    public float x = -1;
    public float y = -1;

    public float vx; // vitesse en pixel/ms
    public float vy;

    private float accx; // accélération en 1/ms^2
    private float accy;

    public long last_time;

    public int[] color = {255, 255, 0};
    private int color_pointer = 0;

    public Ball() {
        last_time = System.currentTimeMillis();
    }

    public void accelerometerChanged(SensorEvent sensorEvent) {
        accx = -sensorEvent.values[0] * 200;
        accy = sensorEvent.values[1] * 200;
    }

    public void tic(float w, float h) {

        // Centré au début
        if (x == -1) x = w / 2;
        if (y == -1) y = h / 2;
        if (size == 0) size = Math.max(w / 10, h / 10);


        // Rotation de la couleur
        int next_color = color[(color_pointer + 1) % 3];
        int other_color = color[(color_pointer + 2) % 3];

        if (next_color > 0) {
            color[(color_pointer + 1) % 3]--;
        } else if (next_color == 0 && other_color < 255) {
            color[(color_pointer + 2) % 3]++;
        } else {
            color_pointer = (color_pointer + 2) % 3;
        }

        // L'animation de la balle se fait selon le temps écoulé depuis le dernier tic()
        long time = System.currentTimeMillis();
        long delta_time = time - last_time;

        vx = vx + accx * delta_time / 1000f;
        vy = vy + accy * delta_time / 1000f;

        x = x + vx * delta_time / 1000f;
        y = y + vy * delta_time / 1000f;

        // Ajustements
        x = Math.max(Math.min(x, w - size / 2), size / 2);
        y = Math.max(Math.min(y, h - size / 2), size / 2);

        if (x <= size / 2 || x >= w - size / 2) {
            // Rebond + friction
            vx = -0.8f * vx;
        }

        if (y <= size / 2 || y >= h - size / 2) {
            vy = -0.8f * vy;
        }

        last_time = time;
    }
}
