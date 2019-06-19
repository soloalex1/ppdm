package great.ufc.br.awarenessclass.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.google.android.gms.awareness.fence.FenceState;

public class VibrateAction extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //No ToastAction, tem uma cola pra descobrir se a fence é TRUE, FALSE ou UNKNOWN
        int state = FenceState.extract(intent).getCurrentState();

        if(state == FenceState.TRUE){
            this.vibrate(context);
        }
    }

    public void vibrate(Context context) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}
