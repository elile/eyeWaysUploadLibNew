#include <iostream>
#include <time.h> 
#include <string>
#include <sstream>
#include <iomanip>
#include <opencv2/highgui/highgui.hpp>

using namespace cv;
using namespace::std;

string ToStrInt(int i)
{
	ostringstream buffer;
	buffer << setprecision(10);
	buffer << i;    
	return buffer.str();
}

string ToStrFloat(double f)
{
	ostringstream buffer;
	buffer << setprecision(10);
	buffer << f;
	return buffer.str();
}

int main(int argc,char *argv[])
{
	Mat image = imread(argv[1]/*"C:\\Users\\eli\\Desktop\\1.jpg"*/, CV_LOAD_IMAGE_COLOR);

	string backToServer = "";
	// greg coordinate
	double mx = 632.1101021;
	double my = 387.8347646;
	int floor = 2;

	srand ( time(NULL) );
	int x = rand() % 60 + 1;
	my += x;

	backToServer = "floor:"+ToStrInt(floor)+",mx:"+ToStrFloat(mx)+",my:"+ToStrFloat(my);
	cout << backToServer;
	return 0;
	//getchar();
}