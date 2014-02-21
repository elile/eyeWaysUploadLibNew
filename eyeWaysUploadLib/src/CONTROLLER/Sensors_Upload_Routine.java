package CONTROLLER;

import DAL.SendAndReceive;
import Interfaces.InternalOnNewLocationArrive;
import Interfaces.onNewLocationArrive;
import Objects.eyeWaysLocation;
import SERVICES_SENSORS_UPLOAD.ACCELEROMETER_upload_service;
import android.content.Context;

public class Sensors_Upload_Routine implements InternalOnNewLocationArrive
{
	private Context c;
	private SendAndReceive send;
	private onNewLocationArrive nNewLocationArrive;
	private ACCELEROMETER_upload_service accelometer;

	public Sensors_Upload_Routine(final Context c) 
	{
		this.c = c;
		Thread t = new Thread(
				new Runnable() {
					public void run() {
						send = new SendAndReceive(c);
						send.setOnInternalNewLocationArrive(Sensors_Upload_Routine.this);
						// ---- all servicess init here ------
						accelometer = new ACCELEROMETER_upload_service(send);
					}
				});
		t.start();
		
	}

	public void setOnNewLocationArrive(onNewLocationArrive nNewLocationArrive) 
	{
		this.nNewLocationArrive = nNewLocationArrive;
	}

	public void start()
	{
		accelometer.startSend();
	}

	public void stop()
	{
		accelometer.shutdownNow();
	}

	@Override
	public void InternalLocationArrive(eyeWaysLocation location) 
	{
		nNewLocationArrive.LocationArrive(location);				
	}



}
