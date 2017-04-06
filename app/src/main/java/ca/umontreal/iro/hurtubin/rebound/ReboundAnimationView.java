package ca.umontreal.iro.hurtubin.rebound;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ReboundAnimationView extends AnimationView {

    private Ball ball;

    public ReboundAnimationView(Context context, Ball ball) {
        super(context);
        this.ball = ball;
    }

    public ReboundAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
    
    public void draw(Canvas canvas) {
        // Repaint le canvas
        canvas.drawColor(Color.WHITE);
        float h = canvas.getHeight(), w = canvas.getWidth();

        ball.tic(w, h);

        // Couleur de la balle
        paint.setColor(Color.rgb(ball.color[0], ball.color[1], ball.color[2]));

        canvas.drawCircle(ball.x, ball.y, ball.size, paint);
    }
}
