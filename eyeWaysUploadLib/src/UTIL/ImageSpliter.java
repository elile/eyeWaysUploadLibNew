package UTIL;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class ImageSpliter 
{
	// for best smallimage_Numbers = 4,9,16,25,36
	public static ArrayList<Bitmap> splitImage(Bitmap bitmap, int smallimage_Numbers)
	{      
		//For the number of rows and columns of the grid to be displayed
		int rows;
		int cols;
		//For height and width of the small image smallimage_s
		int smallimage_Height; 
		int smallimage_Width;
		//To store all the small image smallimage_s in bitmap format in this list
		ArrayList<Bitmap> smallimages = new ArrayList<Bitmap>(smallimage_Numbers);
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
		rows = cols = (int) Math.sqrt(smallimage_Numbers);
		smallimage_Height = bitmap.getHeight()/rows;
		smallimage_Width = bitmap.getWidth()/cols;
		//xCo and yCo are the pixel positions of the image smallimage_s
		int yCo = 0;
		for(int x=0; x<rows; x++)
		{
			int xCo = 0;
			for(int y=0; y<cols; y++)
			{
				smallimages.add(Bitmap.createBitmap(scaledBitmap, xCo, yCo, smallimage_Width, smallimage_Height));
				xCo += smallimage_Width;
			}
			yCo+= smallimage_Height;
		}
		return smallimages;
	}
}
