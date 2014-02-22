package SERVICES_SENSORS_UPLOAD;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import DAL.SendAndReceive;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MAGNETIC_FIELD_upload_service implements SensorEventListener
{
	static ScheduledExecutorService scheduleTaskExecutor;
	private static SensorManager sensorManager;
	private String TextToSend;
	private SendAndReceive dalSend;
	private Context context;
	private long startDelay;
	private long periodDelayInMillis;
	private boolean running;
	private int threadPoolParam;

	public MAGNETIC_FIELD_upload_service(SendAndReceive dalSend) 
	{
		threadPoolParam = 2;
		periodDelayInMillis = 200;
		startDelay = 0 ;
		running = false ;
		TextToSend="";
		this.dalSend = dalSend;
		context = dalSend.getC();
		sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
	}

	public void startSend()
	{
		if (!running) 
		{
			running = true ;
			sensorManager.registerListener(this,
					sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
					SensorManager.SENSOR_DELAY_NORMAL);
			scheduleTaskExecutor = Executors.newScheduledThreadPool(threadPoolParam);
			scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
				public void run() {
					dalSend.send(TextToSend);
				}
			}, startDelay/*init delay*/, periodDelayInMillis/*period delay*/,
					TimeUnit.MILLISECONDS);
		}
	}

	public void shutdownNow() 
	{
		if (running) {
			running = false ;
			scheduleTaskExecutor.shutdownNow();
			sensorManager.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) 
		{
			float[] values = event.values;
			// Movement
			float x = values[0];
			float y = values[1];
			float z = values[2];
			TextToSend = "MAGNETIC_FIELD:"+x+"|"+y+"|"+z;
		}			
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public long getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(long startDelay) {
		this.startDelay = startDelay;
	}

	public long getPeriodDelayInMillis() {
		return periodDelayInMillis;
	}

	public void setPeriodDelayInMillis(long periodDelayInMillis) {
		this.periodDelayInMillis = periodDelayInMillis;
	}

}
