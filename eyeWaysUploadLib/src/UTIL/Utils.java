package UTIL;

import java.math.BigInteger;
import java.security.SecureRandom;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Utils 
{
	
	private static SecureRandom random = new SecureRandom();
	
	public static String getSN(Context c)
	{
		TelephonyManager tManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
		String uid = tManager.getDeviceId();
		if (uid == null) 
		{
			return new BigInteger(100, random).toString(32);
		}
		return uid;
	}


}
