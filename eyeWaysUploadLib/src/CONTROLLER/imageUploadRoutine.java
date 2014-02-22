package CONTROLLER;

import java.io.ByteArrayOutputStream;

import DAL.SendAndReceive;
import Interfaces.InternalOnNewLocationArrive;
import Interfaces.onNewLocationArrive;
import Objects.eyeWaysLocation;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class imageUploadRoutine implements InternalOnNewLocationArrive
{
	private Context c;
	private onNewLocationArrive nNewLocationArrive;
	private SendAndReceive send;

	public imageUploadRoutine(final Context c) 
	{
		this.c = c;
		Thread t = new Thread(
				new Runnable() {
					public void run() 
					{
						send = new SendAndReceive(c);
						send.setOnInternalNewLocationArrive(imageUploadRoutine.this);
					}
				});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void uploadBitmapForLocation(Bitmap img)
	{
		ByteArrayOutputStream bos_output = new ByteArrayOutputStream();
		img.compress(CompressFormat.JPEG , 60, bos_output);
		byte[] bos =  bos_output.toByteArray();
		uploadByteArrayForLocation(bos);
	}
	
	public void uploadByteArrayForLocation(byte[] img)
	{
		send.sendImage(img);
	}

	public void setOnNewLocationArrive(onNewLocationArrive nNewLocationArrive) 
	{
		this.nNewLocationArrive = nNewLocationArrive;
	}

	@Override
	public void InternalLocationArrive(eyeWaysLocation location) 
	{
		nNewLocationArrive.LocationArrive(location);				

	}

}
