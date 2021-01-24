package radiotaxi114.radiotaxi114v6;

/**
 * Created by Jonathan on 15/12/2016.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

public class GpsTrackerBootReceiver extends BroadcastReceiver {
    private static final String TAG = "GpsTrackerBootReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent gpsTrackerIntent = new Intent(context, GpsTrackerAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, gpsTrackerIntent, 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences("radiotaxi114.radiotaxi114v6", Context.MODE_PRIVATE);
        int AplicacionMonitoreoCoordenadaPermanenteContador = sharedPreferences.getInt("AplicacionMonitoreoCoordenadaPermanenteContador", 30000);
        Boolean currentlyTracking = sharedPreferences.getBoolean("currentlyTracking", false);

        Log.e("GpsTrackerBootReceiver",currentlyTracking.toString());

        if (currentlyTracking) {
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    AplicacionMonitoreoCoordenadaPermanenteContador, // 60000 = 1 minute,
                    pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }
    }
}