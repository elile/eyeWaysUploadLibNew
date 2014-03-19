package CONTROLLER;

import java.io.ByteArrayOutputStream;

import DAL.SendAndReceive;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class imageUploadRoutine
{
	private SendAndReceive send;

	public imageUploadRoutine(SendAndReceive sendReceive) 
	{
		this.send = sendReceive;
	}
	
	public void uploadBitmapForLocation(Bitmap img)
	{
		ByteArrayOutputStream bos_output = new ByteArrayOutputStream();
		img.compress(CompressFormat.JPEG , 60, bos_output);
		byte[] bos =  bos_output.toByteArray();
		uploadByteArrayForLocation(bos);
//		byte[] imgByte = getBytesFromBitmap.getBytes(img);
//		uploadByteArrayForLocation(imgByte);
	}
	
	public void uploadByteArrayForLocation(byte[] img)
	{
		send.sendImage(img);
	}


}
