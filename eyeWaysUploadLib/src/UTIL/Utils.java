package UTIL;

import android.content.Context;
import android.telephony.TelephonyManager;

public class Utils 
{
	public static String getSN(Context c)
	{
		TelephonyManager tManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
		String uid = tManager.getDeviceId();
		return uid;
	}


}
