package com.example.asus.sensora;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private ListView listView;
    private TextView textView;

    private SensorManager sensorManager;
    private Sensor sensor;

    long lastTime;
    float lastx, lasty, lastz;
    double SHAKE_THRESHOLD = 800;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_item);
        textView = (TextView) findViewById(R.id.text);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ALL); // returns the default sensor from all available sensor

        int a = sensor.getVersion();
        float b = sensor.getPower();
        String c = sensor.getName();

        textView.setText(a + " " + b + " "+ c );

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        SensorListAdapter sensorListAdapter = new SensorListAdapter(this,sensorList);
        listView.setAdapter(sensorListAdapter);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // returns Accelerometer
        sensorManager.registerListener(this, sensor,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER  ) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();


            if ((curTime - lastTime) > 100) {
                long diffTime = (curTime - lastTime);

                lastTime = curTime;
                double speed = Math.abs(x - lastx + y - lasty + z - lastx) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Intent in = new Intent(MainActivity.this, MapsActivity.class);
                    startActivity(in);
                }

                lastx = x;
                lasty = y;
                lastz = z;

            }
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
