package DAL;

import org.jwebsocket.api.WebSocketClientEvent;
import org.jwebsocket.api.WebSocketClientTokenListener;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.client.token.BaseTokenClient;
import org.jwebsocket.kit.WebSocketException;
import org.jwebsocket.token.Token;

import Interfaces.onNewLocationArrive;
import Objects.eyeWaysLocation;
import UTIL.Utils;
import android.content.Context;
import android.util.Log;

public class SendAndReceive implements WebSocketClientTokenListener
{
	private static BaseTokenClient conn;
	private String url = "ws://192.168.1.103:31285/"; //192.168.1.103, 172.20.10.3 , 10.0.2.2, elivm.cloudapp.net , eliwebsocket.cloudapp.net   
	private int webSocketVersion = 76;
	private Context c;
	private String phoneSN;
	private onNewLocationArrive LocCallBack;
	private int numOfImg =1 ;
	private String jpgEnd = ".jpg";




	public SendAndReceive(Context c)
	{
		this.c = c ;
		conn = new BaseTokenClient();
		phoneSN = Utils.getSN(c);
		conn.addListener(this);
		try {
			new Thread(
					new Runnable() {
						public void run() {
							conn.open(webSocketVersion, url);
						}
					}).start();
			Log.e("eli", "connected");
		} catch (Exception e) {
			Log.e("eli", "not connected");
		}
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
		eyeWaysLocation LocObj = new eyeWaysLocation();
		LocObj.setFloor("floor : " + arg1.getUTF8());
		LocObj.setMx("mx : " + arg1.getUTF8());
		LocObj.setMy("my : " + arg1.getUTF8());
		LocCallBack.LocationArrive(LocObj);
	}

	public static BaseTokenClient getConn() {
		return conn;
	}

	public static void setConn(BaseTokenClient conn) {
		SendAndReceive.conn = conn;
	}

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
