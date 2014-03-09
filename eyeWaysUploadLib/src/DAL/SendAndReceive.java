package DAL;

import org.jwebsocket.api.WebSocketClientEvent;
import org.jwebsocket.api.WebSocketClientTokenListener;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.client.token.BaseTokenClient;
import org.jwebsocket.kit.WebSocketException;
import org.jwebsocket.token.Token;

import Interfaces.onMessageArrive;
import Interfaces.onNewLocationArrive;
import Objects.eyeWaysLocation;
import UTIL.Utils;
import UTIL.values;
import android.content.Context;
import android.util.Log;
/*
 * this is singleton class
 */
public class SendAndReceive implements WebSocketClientTokenListener
{
	private static SendAndReceive connInstance = null;

	private static BaseTokenClient conn;
	private String url = "ws://eliwebsocket.cloudapp.net:31285/";
	private int webSocketVersion = 76;
	private Context c;
	private String phoneSN;
	private int numOfImg =1 ;
	private String jpgEnd = ".jpg";
	private onNewLocationArrive LocCallBack = null;
	private onMessageArrive messageCallBack = null;

	private SendAndReceive(Context c)
	{
		this.c = c ;
		//		ReliabilityOptions opt = new ReliabilityOptions(true, 0, 1, 999999999, 99999999);
		conn = new BaseTokenClient();
		//		conn.setReliabilityOptions(opt);
		phoneSN = Utils.getSN(c);
		conn.addListener(this);
		try {
			Thread t = new Thread(
					new Runnable() {
						public void run() {
							conn.open(webSocketVersion, url);
						}
					});
			t.start();
			t.join();
			Log.e("eli", "connected");
		} catch (Exception e) {
			Log.e("eli", "not connected");
		}
	}

	public static SendAndReceive getConnection(Context c){
		if(connInstance == null)
		{
			connInstance = new SendAndReceive(c);
		}
		return connInstance;
	}

	public void closeSocket()
	{
		if (conn.isConnected())
		{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setOnNewLocationArrive(onNewLocationArrive LocCallBack) {
		this.LocCallBack = LocCallBack;
	}

	public void setOnMessageArrive(onMessageArrive messageCallBack) {
		this.messageCallBack = messageCallBack;
	}

	public void send(String data)
	{
		try {
			if (conn.isConnected() && data.compareTo("")!=0)
			{
				conn.sendText(phoneSN, data);
			}
			//			else {
			//				conn.open(webSocketVersion, url);
			//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendImage(final byte[] img)
	{
		Thread t = new Thread(
				new Runnable() {
					public void run() {
						try {
							//							Log.e("eli", "size before commpress: " + img.length);
							//							byte[] imgCompress1 = MyByteArrayCompress.compressByteArray(img);
							//							Log.e("eli", "size after commpress: " + imgCompress1.length);
							//							byte[] imgCompress2 = LZ.compress(imgCompress1, 3);
							//							Log.e("eli", "size after commpress: " + imgCompress2.length);
							conn.sendFile("", img, phoneSN+"-"+numOfImg+jpgEnd, null);
							numOfImg++;
						} catch (WebSocketException e)
						{  e.printStackTrace();  }
					}
				});
		if (conn.isConnected()){
			t.start();
		}
	}

	@Override
	public void processPacket(WebSocketClientEvent arg0, WebSocketPacket arg1) 
	{
		String returned = arg1.getUTF8();
		int code = getCodeFromReturned(returned);
		returned = removeCodeBlock(returned);
		switch (code) {
		case values.AR_CODE:{

			break;
		}
		case values.BROADCAST_CODE:{
			if (messageCallBack != null) {
				messageCallBack.MessageArrive(returned);
			}
			break;
		}
		case values.COMMERCIAL_CODE:{

			break;
		}
		case values.ECHO_PING_CODE:{

			break;
		}
		case values.ECHO_PONG_CODE:{
			if (messageCallBack != null) {
				messageCallBack.MessageArrive(returned);
			}
			break;
		}
		case values.LOCATION_CODE:{
			if (LocCallBack != null)
			{
				String[] values = returned.split(",");
				String floor = values[0].split(":")[1];
				String mx = values[1].split(":")[1];
				String my = values[2].split(":")[1];
				eyeWaysLocation LocObj = new eyeWaysLocation(mx, my, floor);
				LocCallBack.LocationArrive(LocObj);
			}
			break;
		}
		case values.MESSAGE_CODE:{

			break;
		}
		case values.USER_CLOSE_CONNECTION_CODE:{

			break;
		}
		case values.USER_OPEN_CONNECTION_CODE:{

			break;
		}
		}

	}

	private String removeCodeBlock(String returned) 
	{
		StringBuilder ret = new StringBuilder();
		boolean startingpoint = false;
		for (int i = 0; i < returned.length(); i++) 
		{
			if (startingpoint) 
				ret.append(returned.charAt(i));
			if (returned.charAt(i) == '-')
				startingpoint = true;
		}
		return ret.toString();
	}

	private int getCodeFromReturned(String returned) 
	{
		StringBuilder codeStr = new StringBuilder();
		for (int i = 0; returned.charAt(i)!='-'; i++)
		{
			codeStr.append(returned.charAt(i));
		}
		return Integer.parseInt(codeStr.toString());
	}
	//
	//	public static BaseTokenClient getConn() {
	//		return conn;
	//	}
	//
	//	public static void setConn(BaseTokenClient conn) {
	//		SendAndReceive.conn = conn;
	//	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWebSocketVersion() {
		return webSocketVersion;
	}

	public void setWebSocketVersion(int webSocketVersion) {
		this.webSocketVersion = webSocketVersion;
	}

	public Context getC() {
		return c;
	}

	public void setC(Context c) {
		this.c = c;
	}

	public String getPhoneSN() {
		return phoneSN;
	}

	public void setPhoneSN(String phoneSN) {
		this.phoneSN = phoneSN;
	}

	@Override
	public void processClosed(WebSocketClientEvent arg0)
	{
		Log.e("eli", "close");
		Log.e("eli", arg0.getData());
		Log.e("eli", arg0.getName());
	}

	@Override
	public void processOpened(WebSocketClientEvent arg0) 
	{

	}

	@Override
	public void processOpening(WebSocketClientEvent arg0) 
	{

	}

	@Override
	public void processReconnecting(WebSocketClientEvent arg0) 
	{

	}

	@Override
	public void processToken(WebSocketClientEvent arg0, Token arg1) 
	{

	}

}
