package CONTROLLER;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;

import DAL.getListOfStoreFromCloud;
import Objects.store;
import UTIL.values;

public class getListStoresController 
{
	
	public static LinkedList<store> getList(String mallID)
	{
		LinkedList<store> ret = new LinkedList<store>();
		String sFromCloud = "";
		try 
		{
			sFromCloud = new getListOfStoreFromCloud().execute(values.URL_GET_LIST_STORE+mallID).get();
			String[] splitByEMark = sFromCloud.split("!");
			for (String line : splitByEMark)
			{
				String[] splitByPound = line.split("#", -1);
				store s = new store(
						splitByPound[0], 
						splitByPound[1], 
						splitByPound[2], 
						splitByPound[3], 
						splitByPound[4]
								);
				ret.add(s);
			}
		} 
		catch (InterruptedException e) {	} 
		catch (ExecutionException e) {	}

		return ret;
	}

}
