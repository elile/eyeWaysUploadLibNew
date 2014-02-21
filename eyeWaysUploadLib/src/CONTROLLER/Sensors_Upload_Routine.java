package CONTROLLER;

import DAL.SendAndReceive;
import Interfaces.InternalOnNewLocationArrive;
import Interfaces.onNewLocationArrive;
import Objects.eyeWaysLocation;
import android.content.Context;

public class Sensors_Upload_Routine implements InternalOnNewLocationArrive
{
	private Context c;
	private SendAndReceive send;
	private onNewLocationArrive nNewLocationArrive;
	
	public Sensors_Upload_Routine(Context c) 
	{
		this.c = c;
		send = new SendAndReceive(c);
	}
	
	public void setOnNewLocationArrive(onNewLocationArrive nNewLocationArrive) 
	{
		this.nNewLocationArrive = nNewLocationArrive;
	}
	
	public void start()
	{
		
	}
	
	public void stop()
	{
		
	}

	@Override
	public void LocationArrive(eyeWaysLocation location)
	{
		nNewLocationArrive.LocationArrive(location);		
	}
	
	
	
}
