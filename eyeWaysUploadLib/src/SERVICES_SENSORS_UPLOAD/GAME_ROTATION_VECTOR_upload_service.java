package SERVICES_SENSORS_UPLOAD;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.IntentService;
import android.content.Intent;

public class GAME_ROTATION_VECTOR_upload_service extends IntentService {
	static ScheduledExecutorService scheduleTaskExecutor;

	public GAME_ROTATION_VECTOR_upload_service(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		scheduleTaskExecutor = Executors.newScheduledThreadPool(1);
		scheduleTaskExecutor.scheduleAtFixedRate(
				new Runnable() {
					public void run() {
					}
				}, 0/*init delay*/, 1/*period delay*/, TimeUnit.SECONDS);
		stopSelf();		
	}
	public static void sendInterupt() {
		scheduleTaskExecutor.shutdownNow();
	}

}
