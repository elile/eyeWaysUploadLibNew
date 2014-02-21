package DAL;

import org.jwebsocket.api.WebSocketClientEvent;
import org.jwebsocket.api.WebSocketClientTokenListener;
import org.jwebsocket.api.WebSocketPacket;
import org.jwebsocket.client.token.BaseTokenClient;
import org.jwebsocket.token.Token;

import Interfaces.InternalOnNewLocationArrive;
import Interfaces.onNewLocationArrive;
import Objects.eyeWaysLocation;
import UTIL.Utils;
import android.content.Context;

public class SendAndReceive implements WebSocketClientTokenListener
{
	private static BaseTokenClient conn;
	private String url = "ws://10.0.2.2:8181/";
	private int webSocketVersion = 76;
	private Context c;
	private String phoneSN;
	private InternalOnNewLocationArrive LocCallBack;
	

	public SendAndReceive(Context c)
	{
		this.c = c ;
		phoneSN = Utils.getSN(c);
		conn = new BaseTokenClient();
		conn.addListener(this);
		try {
			conn.open(webSocketVersion, url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setOnNewLocationArrive(InternalOnNewLocationArrive LocCallBack) {
		this.LocCallBack = LocCallBack;
	}
	
	public void send(String data)
	{
		try {
			conn.sendText(phoneSN, data);
		} catch (Exception e) {
			e.printStackTrace();
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
