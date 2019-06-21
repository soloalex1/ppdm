package great.ufc.br.awarenessclass;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.FenceClient;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.fence.DetectedActivityFence;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.awareness.fence.HeadphoneFence;
import com.google.android.gms.awareness.state.HeadphoneState;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import great.ufc.br.awarenessclass.actions.NotificationAction;
import great.ufc.br.awarenessclass.actions.ToastAction;
import great.ufc.br.awarenessclass.actions.VibrateAction;

public class FenceActivity extends AppCompatActivity implements OnSuccessListener<Void>, OnFailureListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fence);

        //Criar as AwarenessFences
        AwarenessFence headphone = HeadphoneFence.during(HeadphoneState.PLUGGED_IN);
        AwarenessFence walking = DetectedActivityFence.during(DetectedActivityFence.WALKING);
        AwarenessFence headphoneWalking = AwarenessFence.and(headphone, walking);

        //Filtros de Intent
//      IntentFilter hp = new IntentFilter("headphone");
        IntentFilter walk = new IntentFilter("walking");
        IntentFilter hpWalk = new IntentFilter("headphoneWalking");

        // Registrar Receivers (actions) na pilha do Android
//      registerReceiver(new VibrateAction(), hp);
        registerReceiver(new VibrateAction(), walk);
        registerReceiver(new NotificationAction(), hpWalk);

        // Registrar PendingIntents com os filtros criados
//      PendingIntent pi = PendingIntent.getBroadcast(this,123, new Intent("headphone"),PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pi2 = PendingIntent.getBroadcast(this,123, new Intent("walking"),PendingIntent.FLAG_CANCEL_CURRENT);
        PendingIntent pendingCombination = PendingIntent.getBroadcast(this, 123, new Intent("headphoneWalking"), PendingIntent.FLAG_CANCEL_CURRENT);

        // Registro de Fences no Google Awareness API
        FenceClient fc = Awareness.getFenceClient(this);
//      fc.updateFences(new FenceUpdateRequest.Builder().addFence("Headphone",headphone,pi).build()).addOnSuccessListener(this).addOnFailureListener(this);
        fc.updateFences(new FenceUpdateRequest.Builder().addFence("Walking", walking, pi2).build()).addOnSuccessListener(this).addOnFailureListener(this);
        fc.updateFences(new FenceUpdateRequest.Builder().addFence("Headphone Walking", headphoneWalking, pendingCombination).build())
                .addOnSuccessListener(this).addOnFailureListener(this);
    }

    @Override
    public void onSuccess(Void aVoid) {
        Toast.makeText(this, "Fence registrada com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, "Houve um erro ao registrar fence", Toast.LENGTH_SHORT).show();
        Log.e("FenceActivity", "onFailure: Houve um erro ao registrar fence", e);
    }
}
