package net.yisus.mifutbolitovtjj;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private final float[] rotationSensorReading = new float[3];
    ImageView ImgPelota;
    ImageView ImgArco1;
    ImageView ImgArco2;
    TextView goles1;
    TextView goles2;
    ConstraintLayout cancha;
    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cancha = findViewById(R.id.cancha);
        ImgPelota = findViewById(R.id.ImgPelota);
        ImgArco1 = findViewById(R.id.ImgArco1);
        ImgArco2 = findViewById(R.id.ImgArco2);
        goles1 = findViewById(R.id.txtGoles1);
        goles2 = findViewById(R.id.txtGoles2);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Sensor acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometer != null) {
            sensorManager.registerListener(this, acelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, rotationSensorReading,
                    0, rotationSensorReading.length);
        }
        Log.d("MySensor",rotationSensorReading[1] + "");
        width = cancha.getWidth();
        height = cancha.getHeight();

        if(ImgPelota.getX() < 0){
            ImgPelota.setX(0);
        }else if((ImgPelota.getX()+ ImgPelota.getHeight()) > width && width != 0){
            ImgPelota.setX(width- ImgPelota.getHeight());
        }else {
            ImgPelota.setX(ImgPelota.getX()-(rotationSensorReading[0]*10));
        }


        Log.d("Position",(ImgPelota.getY()+ ImgPelota.getWidth())+"");
        if (ImgPelota.getY() < 0){
            ImgPelota.setY(0);
        }else if((ImgPelota.getY()+ ImgPelota.getWidth()) > height && height != 0){
            ImgPelota.setY(height- ImgPelota.getWidth());
        }else {
            ImgPelota.setY(ImgPelota.getY()+(rotationSensorReading[1]*10));
        }

        if (ImgPelota.getX() + ImgPelota.getWidth() -15 >= ImgArco1.getX() && ImgPelota.getX() - 15 <= ImgArco1.getX() + ImgArco1.getWidth()){
            if (ImgPelota.getY() + 15 >= ImgArco1.getY() && ImgPelota.getY() + 15 <= ImgArco1.getY() + ImgArco1.getHeight()) {
                goles2.setText(((Integer.parseInt(goles2.getText().toString()))+1)+"");
                ImgPelota.setX(width/2);
                ImgPelota.setY(height/2);

                Log.d("Position","Gooooool en la portería #1");
            }
        }
        if (ImgPelota.getX() + ImgPelota.getWidth() - 15 >= ImgArco2.getX() && ImgPelota.getX() - 15 <= ImgArco2.getX() + ImgArco2.getWidth()){
            if (ImgPelota.getY() + ImgPelota.getHeight() - 15 >= ImgArco2.getY() && ImgPelota.getY() - 15 <= ImgArco2.getY() + ImgArco2.getHeight()) {
                goles1.setText(((Integer.parseInt(goles1.getText().toString()))+1)+"");
                ImgPelota.setX(width/2);
                ImgPelota.setY(height/2);

                Log.d("Position","Goooool en la portería #2");
            }
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}