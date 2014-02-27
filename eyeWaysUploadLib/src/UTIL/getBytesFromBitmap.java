package UTIL;

import java.nio.ByteBuffer;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;

public class getBytesFromBitmap 
{
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	public static byte[] getBytes(Bitmap b) 
	{
		//calculate how many bytes our image consists of.
		int bytes = b.getByteCount();
		//or we can calculate bytes this way. Use a different value than 4 if you don't use 32bit images.
		//int bytes = b.getWidth()*b.getHeight()*4; 
		ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
		b.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
		byte[] array = buffer.array(); //Get the underlying array containing the data.
		return array;
	}
}
