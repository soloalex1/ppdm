package great.ufc.br.awarenessclass.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.awareness.fence.FenceState;

public class ToastAction extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int state = FenceState.extract(intent).getCurrentState();
        switch (state){
            case FenceState.TRUE:
                Toast.makeText(context, "Plugado", Toast.LENGTH_SHORT).show();
                break;
            case FenceState.FALSE:
                Toast.makeText(context, "Removido", Toast.LENGTH_SHORT).show();
                break;
            case FenceState.UNKNOWN:
                Toast.makeText(context, "?????", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
