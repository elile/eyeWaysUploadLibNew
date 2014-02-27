package DAL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;

public class getListOfStoreFromCloud extends AsyncTask<String, Integer, String>
{
	@Override
	protected String doInBackground(String... params)
	{
		String body = "";
		try {
			Document RemoteDocument=Jsoup.connect(params[0]).get();
			Elements elements = RemoteDocument.select("#Label1");
			body = elements.get(0).text();
		}catch(Exception e){}
		return body;
	}
	
}
