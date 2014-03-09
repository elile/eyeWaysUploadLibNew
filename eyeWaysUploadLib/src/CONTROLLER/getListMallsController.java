package CONTROLLER;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import DAL.getListOfStoreFromCloud;
import Objects.Mall;
import UTIL.values;

public class getListMallsController
{
	public static LinkedList<Mall> getList()
	{
		LinkedList<Mall> ret = new LinkedList<Mall>();
		String sFromCloud = "";
		try 
		{
			sFromCloud = new getListOfStoreFromCloud().execute(values.URL_GET_LIST_MALL).get();
			String[] splitByEMark = sFromCloud.split("!");
			for (String line : splitByEMark)
			{
				String[] splitByPound = line.split("#", -1);
				Mall s = new Mall(
						splitByPound[0], 
						splitByPound[1], 
						splitByPound[2] 
								);
				ret.add(s);
			}
		} 
		catch (InterruptedException e) {	} 
		catch (ExecutionException e) {	}

		return ret;
	}

}
