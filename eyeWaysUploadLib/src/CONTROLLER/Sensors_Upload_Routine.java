package CONTROLLER;

import DAL.SendAndReceive;
import Interfaces.InternalOnNewLocationArrive;
import Interfaces.onNewLocationArrive;
import Objects.eyeWaysLocation;
import SERVICES_SENSORS_UPLOAD.ACCELEROMETER_upload_service;
import SERVICES_SENSORS_UPLOAD.GAME_ROTATION_VECTOR_upload_service;
import SERVICES_SENSORS_UPLOAD.GEOMETRIC_ROTATION_VECTOR_upload_service;
import SERVICES_SENSORS_UPLOAD.GRAVITY_upload_service;
import SERVICES_SENSORS_UPLOAD.GYROSCOPE_upload_service;
import SERVICES_SENSORS_UPLOAD.LINEAR_ACCELERATION_upload_service;
import SERVICES_SENSORS_UPLOAD.MAGNETIC_FIELD_upload_service;
import SERVICES_SENSORS_UPLOAD.ORIENTATION_upload_service;
import SERVICES_SENSORS_UPLOAD.ROTATION_VECTOR_upload_service;
import SERVICES_SENSORS_UPLOAD.SIGNIFICANT_MOTION_upload_service;
import UTIL.Utils;
import android.content.Context;

public class Sensors_Upload_Routine implements InternalOnNewLocationArrive
{
	private Context c;
	private SendAndReceive send;
	private onNewLocationArrive nNewLocationArrive;

	private ACCELEROMETER_upload_service accelometer;
	private GAME_ROTATION_VECTOR_upload_service gameRotVect;
	private GEOMETRIC_ROTATION_VECTOR_upload_service geoRotVect;
	private GRAVITY_upload_service gravity;
	private GYROSCOPE_upload_service gyro;
	private LINEAR_ACCELERATION_upload_service linAccelaretion;
	private MAGNETIC_FIELD_upload_service magField;
	private ORIENTATION_upload_service orientation;
	private ROTATION_VECTOR_upload_service rotVect;
	private SIGNIFICANT_MOTION_upload_service sigMotion;


	public Sensors_Upload_Routine(final Context c) 
	{
		this.c = c;
		Thread t = new Thread(
				new Runnable() {
					public void run() {
						send = new SendAndReceive(c);
						send.setOnInternalNewLocationArrive(Sensors_Upload_Routine.this);
						// ---- all servicess init here ------
						accelometer = new ACCELEROMETER_upload_service(send);
						gameRotVect = new GAME_ROTATION_VECTOR_upload_service(send);
						geoRotVect = new GEOMETRIC_ROTATION_VECTOR_upload_service(send);
						gravity = new GRAVITY_upload_service(send);
						gyro = new GYROSCOPE_upload_service(send);
						linAccelaretion = new LINEAR_ACCELERATION_upload_service(send);
						magField = new MAGNETIC_FIELD_upload_service(send);
						orientation = new ORIENTATION_upload_service(send);
						rotVect = new ROTATION_VECTOR_upload_service(send);
						sigMotion = new SIGNIFICANT_MOTION_upload_service(send);
					}
				});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setOnNewLocationArrive(onNewLocationArrive nNewLocationArrive) 
	{
		this.nNewLocationArrive = nNewLocationArrive;
	}

	public void start()
	{
		start_accelometer();
		start_gameRotVect();
		start_geoRotVect();
		start_gravity();
		start_gyro();
		start_linAccelaretion();
		start_magField();
		start_orientation();
		start_rotVect();
		start_sigMotion();
	}
	public void stop()
	{
		stop_accelometer();
		stop_gameRotVect();
		stop_geoRotVect();
		stop_gravity();
		stop_gyro();
		stop_linAccelaretion();
		stop_magField();
		stop_orientation();
		stop_rotVect();
		stop_sigMotion();
	}

	private void stop_sigMotion() {
		if (Utils.isVersionHigherThen(18)) 
			sigMotion.shutdownNow();
	}

	private void stop_rotVect() {
		if (Utils.isVersionHigherThen(9)) 
			rotVect.shutdownNow();
	}

	private void stop_orientation() {
		if (Utils.isVersionHigherThen(1)) 
			orientation.shutdownNow();
	}

	private void stop_magField() {
		if (Utils.isVersionHigherThen(3)) 
			magField.shutdownNow();
	}

	private void stop_linAccelaretion() {
		if (Utils.isVersionHigherThen(9)) 
			linAccelaretion.shutdownNow();
	}

	private void stop_gyro() {
		if (Utils.isVersionHigherThen(3)) 
			gyro.shutdownNow();
	}

	private void stop_gravity() {
		if (Utils.isVersionHigherThen(9)) 
			gravity.shutdownNow();
	}

	private void stop_geoRotVect() {
		if (Utils.isVersionHigherThen(19)) 
			geoRotVect.shutdownNow();
	}

	private void stop_gameRotVect() {
		if (Utils.isVersionHigherThen(18)) 
			gameRotVect.shutdownNow();
	}

	private void stop_accelometer() {
		if (Utils.isVersionHigherThen(3)) 
			accelometer.shutdownNow();
	}

	private void start_sigMotion() {
		if (Utils.isVersionHigherThen(18)) 
			sigMotion.startSend();
	}

	private void start_rotVect() {
		if (Utils.isVersionHigherThen(9)) 
			rotVect.startSend();
	}

	private void start_orientation() {
		if (Utils.isVersionHigherThen(1)) 
			orientation.startSend();
	}

	private void start_magField() {
		if (Utils.isVersionHigherThen(3)) 
			magField.startSend();
	}

	private void start_linAccelaretion() {
		if (Utils.isVersionHigherThen(9)) 
			linAccelaretion.startSend();
	}

	private void start_gyro() {
		if (Utils.isVersionHigherThen(3)) 
			gyro.startSend();
	}

	private void start_gravity() {
		if (Utils.isVersionHigherThen(9)) 
			gravity.startSend();
	}

	private void start_geoRotVect() {
		if (Utils.isVersionHigherThen(19)) 
			geoRotVect.startSend();
	}

	private void start_gameRotVect() {
		if (Utils.isVersionHigherThen(18)) 
			gameRotVect.startSend();
	}

	private void start_accelometer() {
		if (Utils.isVersionHigherThen(3)) 
			accelometer.startSend();
	}

	

	@Override
	public void InternalLocationArrive(eyeWaysLocation location) 
	{
		nNewLocationArrive.LocationArrive(location);				
	}



}
