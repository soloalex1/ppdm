package great.ufc.br.awarenessclass.actions;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.VibrationEffect;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


import com.google.android.gms.awareness.fence.FenceState;

import great.ufc.br.awarenessclass.R;

public class NotificationAction extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int state = FenceState.extract(intent).getCurrentState();
        if(state == FenceState.TRUE)
            pushNotification(context);
    }

    public void pushNotification(Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"channelId");
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle("Awareness Tutorial");
        builder.setTicker("Mudanca de contexto detectada");
        builder.setContentText("Você está andando com headphones!");

        Notification not = builder.build();
        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(123,not);
    }
}
