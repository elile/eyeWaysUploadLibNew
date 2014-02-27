package UTIL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIP 
{
	public static byte[] compress(byte[] bytes) 
	{
		ByteArrayOutputStream baos = null;
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			baos = new ByteArrayOutputStream();
			GZIPOutputStream gzos = new GZIPOutputStream(baos);
			byte[] buffer = new byte[1024];
			int len;
			while((len = bais.read(buffer)) >= 0) {
				gzos.write(buffer, 0, len);
			}
			gzos.close();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return(baos.toByteArray());
	}

	public static byte[] deCompress(byte[] gbytes) 
	{
		ByteArrayOutputStream baos = null;
		ByteArrayInputStream bais = new ByteArrayInputStream(gbytes);
		try {
			baos = new ByteArrayOutputStream();
			GZIPInputStream gzis = new GZIPInputStream(bais);
			byte[] bytes = new byte[1024];
			int len;
			while((len = gzis.read(bytes)) > 0) {
				baos.write(bytes, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return(baos.toByteArray());
	}
}
