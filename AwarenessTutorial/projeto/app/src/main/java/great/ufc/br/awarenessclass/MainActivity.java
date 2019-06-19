package great.ufc.br.awarenessclass;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.SnapshotClient;
import com.google.android.gms.awareness.fence.AwarenessFence;
import com.google.android.gms.awareness.snapshot.PlacesResponse;
import com.google.android.gms.awareness.snapshot.WeatherResponse;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSnapshot;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("AwarenessClass");
        Button btn = findViewById(R.id.btn_fence);
        btnSnapshot = findViewById(R.id.btn_snapshot);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FenceActivity.class));
            }
        });
        btnSnapshot.setOnClickListener(this);


    }

    //Para saber se as permissoes foram concedidas.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults[0] == PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissoes de GPS concedidas.", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Aceite as permissoes.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MissingPermission")
    private void startSnapshot(){
        //pegar o cliente de snapshot da api
        SnapshotClient snapshotClient = Awareness.getSnapshotClient(this);
        snapshotClient.getWeather().addOnSuccessListener(new OnSuccessListener<WeatherResponse>() {
            //promise da requisicao getWeather
            @Override
            public void onSuccess(WeatherResponse weatherResponse) {
                //mostrar a previsao do tempo no celular
                String resp = weatherResponse.getWeather().toString();
                Toast.makeText(MainActivity.this, resp, Toast.LENGTH_SHORT).show();
                textView.setText(resp);
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startSnapshotPlaces(){
        //criem um snapshot de Places!
        //olhem a colinha acima :)

        SnapshotClient snapshotClient = Awareness.getSnapshotClient(this);
        snapshotClient.getPlaces().addOnSuccessListener(new OnSuccessListener<PlacesResponse>() {
            @Override
            public void onSuccess(PlacesResponse placesResponse) {
                List<PlaceLikelihood> places = placesResponse.getPlaceLikelihoods();
                String currentPlace = places.get(0).getPlace().getName().toString();
                Toast.makeText(MainActivity.this, currentPlace ,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_snapshot:
                //Se o app não tiver acesso ao gps,
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    String[] myPermissions = {Manifest.permission.ACCESS_FINE_LOCATION}; //pedir acesso ao gps
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //Permissão para acessar a localização. (caixa de dialogo)
                        requestPermissions(myPermissions,123);
                    }
                }
                else{
                    //Inicie o snapshot
                    startSnapshotPlaces();
                }
        }
    }
}
