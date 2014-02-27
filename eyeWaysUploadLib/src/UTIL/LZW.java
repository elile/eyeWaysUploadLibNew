package UTIL;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

public class LZW {
	/** Compress a string to a list of output symbols. */
	public static byte[] compress(byte[] img) 
	{
		char[] uncompressed = (new String(img)).toCharArray();

		// Build the dictionary.
		int dictSize = 256;
		Map<String,Integer> dictionary = new HashMap<String,Integer>();
		for (int i = 0; i < 256; i++)
			dictionary.put("" + (char)i, i);

		String w = "";
		List<Integer> result = new ArrayList<Integer>();
		for (char c : uncompressed)
		{
			String wc = w + c;
			if (dictionary.containsKey(wc))
				w = wc;
			else 
			{
				result.add(dictionary.get(w));
				// Add wc to the dictionary.
				dictionary.put(wc, dictSize++);
				w = "" + c;
			}
		}
		// Output the code for w.
		if (!w.equals(""))
			result.add(dictionary.get(w));
		int k = 0 ;
		byte[] bytes = null;
		byte[] commpressArr = new byte[result.size()*4];
		for (int i = 0; i < commpressArr.length; i+=4) 
		{
			if (k < result.size())
			{
				Log.e("eli", result.get(k) + "");
				bytes = toBytes(result.get(k));
			}
			for (int j = 0; j < bytes.length; j++) 
				commpressArr[i+j] = bytes[j];
			k++;
		}
		return commpressArr;
	}

	/** Decompress a list of output ks to a string. */
	public static String decompress(List<Integer> compressed) 
	{
		// Build the dictionary.
		int dictSize = 256;
		Map<Integer,String> dictionary = new HashMap<Integer,String>();
		for (int i = 0; i < 256; i++)
			dictionary.put(i, "" + (char)i);

		String w = "" + (char)(int)compressed.remove(0);
		StringBuffer result = new StringBuffer(w);
		for (int k : compressed) 
		{
			String entry;
			if (dictionary.containsKey(k))
				entry = dictionary.get(k);
			else if (k == dictSize)
				entry = w + w.charAt(0);
			else
				throw new IllegalArgumentException("Bad compressed k: " + k);

			result.append(entry);

			// Add w+entry[0] to the dictionary.
			dictionary.put(dictSize++, w + entry.charAt(0));

			w = entry;
		}
		return result.toString();
	}

	public static byte[] toBytes(int i)
	{
		byte[] result = new byte[4];

		result[0] = (byte) (i >> 24);
		result[1] = (byte) (i >> 16);
		result[2] = (byte) (i >> 8);
		result[3] = (byte) (i /*>> 0*/);

		return result;
	}
}