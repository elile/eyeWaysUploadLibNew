package SERVICES_SENSORS_UPLOAD;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import DAL.SendAndReceive;
import android.app.IntentService;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ACCELEROMETER_upload_service extends IntentService implements SensorEventListener
{
	static ScheduledExecutorService scheduleTaskExecutor;
	private SensorManager sensorManager;
	private String TextToSend;
	private SendAndReceive sendNow;

	public ACCELEROMETER_upload_service(String name) {
		super(name);
		sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
		TextToSend="";
		//sendNow = new 
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		scheduleTaskExecutor = Executors.newScheduledThreadPool(1);
		scheduleTaskExecutor.scheduleAtFixedRate(
				new Runnable() {
					public void run() {
						
					}
				}, 0/*init delay*/, 1/*period delay*/, TimeUnit.SECONDS);
		stopSelf();		
	}
	
	public static void shutdownNow() {
		scheduleTaskExecutor.shutdownNow();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) 
		{
			float[] values = event.values;
			// Movement
			float x = values[0];
			float y = values[1];
			float z = values[2];
			TextToSend = "ACCELEROMETER:"+x+"|"+y+"|"+z;
		}			
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this);
	}

}
