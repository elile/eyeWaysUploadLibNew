package UTIL;

import java.util.zip.Deflater;


public class MyByteArrayCompress {

	public static byte[] compressByteArray(byte[] bytes)
	{
		//         return bytes;
		//		ByteArrayOutputStream baos = null;
		//		Deflater dfl = new Deflater();
		//		dfl.setLevel(Deflater.BEST_COMPRESSION);
		//		dfl.setInput(bytes);
		//		dfl.finish();
		//		baos = new ByteArrayOutputStream();
		//		byte[] tmp = new byte[4*1024];
		//		try{
		//			while(!dfl.finished()){
		//				int size = dfl.deflate(tmp);
		//				baos.write(tmp, 0, size);
		//			}
		//		} catch (Exception ex){
		//
		//		} finally {
		//			try{
		//				if(baos != null) baos.close();
		//			} catch(Exception ex){}
		//		}
		//
		//		return baos.toByteArray();
		byte[] results = new byte[bytes.length];
		Deflater deflater = new Deflater();
		deflater.setInput(bytes);
		deflater.finish();
		int len = deflater.deflate(results);
		byte[] out = new byte[len];
		for(int i=0; i<len; i++) {
			out[i] = results[i];
		}
		return out ;
	}


}