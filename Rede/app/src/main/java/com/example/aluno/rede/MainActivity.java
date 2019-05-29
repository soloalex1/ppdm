package com.example.aluno.rede;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.M)
@RequiresApi(api = Build.VERSION_CODES.M)

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkActiveConnection();
    }

    public void checkActiveConnection() {
        ConnectivityManager connManager;
        connManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connManager.getActiveNetwork();
        NetworkCapabilities networkInfo;
        networkInfo = connManager.getNetworkCapabilities(activeNetwork);
        if (!networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)) {
            Log.v("tipo de conexão", "Pagamento de dados extra");
        }
        if (networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            Log.v("tipo de conexão", "Tem acesso a Internet");
        }
        if (networkInfo.hasCapability(NetworkCapabilities.NET_CAPABILITY_MMS)) {
            Log.v("tipo de conexão", "Envia MMS");
        }
        if (networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Log.v("tipo de conexão", "Tem acesso a WIFI");
        }
        if (networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Log.v("tipo de conexão", "Tem acesso a rede ceular");
        }
        if (networkInfo.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            Log.v("tipo de conexão", "Tem acesso a rede ethernet");
        }
    }

}
