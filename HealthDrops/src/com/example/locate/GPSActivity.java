package com.example.locate;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthdrops.R;
import com.example.modelclasses.ArchiveManager;
import com.example.modelclasses.SmsSender;

public class GPSActivity extends Activity implements LocationListener
{
	private LocationManager locationManager;
	private TextView textView;
	Location nowLocation = null, homeLocation = null;
	boolean warning = true, message = true;
	double distance = 100;
	final String filename = "gps";
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		try{
			textView = (TextView) findViewById(R.id.label1);
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
			nowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			homeLocation = nowLocation;//inicializando com a instancia de nowLocation
		}catch(Exception e){
			Log.e("LOGTAG", e.toString());
		}
		if(nowLocation == null || homeLocation == null){
			Toast.makeText(this, "GPS não está disponível", Toast.LENGTH_SHORT).show();
			finish();
		}
		try{
			Log.d("tag", "em oncreate");
			ArchiveManager archiveManager = new ArchiveManager(this);
			Log.d("tag", "archive manager criado");
			String[] out = archiveManager.ler(filename).split("DIV");           //acessando o arquivo
			Log.d("tag", "arquivo lido");
			
			
			String latitude = out[0];
			String longitude = out[1];
			String dist = out[2];
			Log.d("tag", "latitude" + latitude);
			Log.d("tag", "longitude" + longitude);
			Log.d("tag", "dist" + dist);
			homeLocation.setLatitude(Double.parseDouble(latitude));//tentando setar a parada null, AQUI ESTA O PROBLEMA
			homeLocation.setLongitude(Double.parseDouble(longitude));//E AQUI
			distance = Double.parseDouble(dist);
			
		}catch(Exception e){
			Log.e("LOGTAG",e.toString());
		}
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

	}

	public void GPSClick(View v){
		homeLocation = nowLocation;
		
		EditText edit = (EditText)findViewById (R.id.metersEdit);
		distance = Float.parseFloat(edit.getText().toString());
		
		File dir = getFilesDir();
		File file = new File(dir, filename);
		boolean deleted = file.delete();
				
		ArchiveManager manager = new ArchiveManager(this);
		
		double latitude = homeLocation.getLatitude();
		double longitude = homeLocation.getLongitude();
		
		manager.escrever(filename, Double.toString(latitude));
		manager.escrever(filename, Double.toString(longitude));
		manager.escrever(filename, Double.toString(distance));
		
		Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
	}
	
    @Override
    public void onLocationChanged(Location inLocation)
    {
        textView = (TextView) findViewById(R.id.label1);
        String latitude = String.valueOf(inLocation.getLatitude());
        String longitude = String.valueOf(inLocation.getLongitude());

        Log.e("GPS", "location changed: lat="+latitude+", lon="+longitude);

        textView.setText("lat="+latitude+", lon="+longitude);
        
        nowLocation = inLocation;

        if(nowLocation.distanceTo(homeLocation) >= (0.8 * distance) && nowLocation.distanceTo(homeLocation) < distance && warning){
        	warning = false;
        	Toast.makeText(this, "Ei! Onde você vai? Volte para seu limite determinado", Toast.LENGTH_SHORT).show();
        }
        
        if(nowLocation.distanceTo(homeLocation) >= distance && message){
        	warning = true;
        	message = false;
        	Toast.makeText(this, "Você saiu do limite determinado", Toast.LENGTH_SHORT).show();
        	SmsSender sender = new SmsSender(this);
        	sender.SendEmergencySms(SmsSender.GPS_MESSAGE);
        }
    }

    public void onProviderDisabled(String providerName)
    {
        Log.e("GPS", "provider disabled " + providerName);
    }

    public void onProviderEnabled(String providerName)
    {
        Log.e("GPS", "provider enabled " + providerName);
    }

    public void onStatusChanged(String providerName, int status, Bundle extras)
    {
        Log.e("GPS", "status changed to " + providerName + " [" + status + "]");
    }

}

