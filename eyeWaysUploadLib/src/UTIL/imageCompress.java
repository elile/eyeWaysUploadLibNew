package UTIL;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import android.util.Log;

public class imageCompress 
{
	public static byte[] GZIPCompress(byte[] data) {
	    try {
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
	        gZIPOutputStream.write(data);
	        gZIPOutputStream.close();
	        return byteArrayOutputStream.toByteArray();
	    } catch(IOException e) {
	        Log.i("output", "GZIPCompress Error: " + e.getMessage());
	        return null;
	    }
	}
}
