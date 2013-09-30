package com.example.healthdrops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.alarme.SetAlarmActivity;
import com.example.diario.DiarioActivity;
import com.example.locate.GPSActivity;
import com.example.panico.ContactActivity;
import com.example.panico.SendSmsActivity;
import com.example.sinais.BluetoothActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void diarioButton(View view) {  //inicia DiarioActivity
		   	Intent intent = new Intent(this, DiarioActivity.class);
			startActivity(intent);
	}
	
	public void remButton(View view){    //inicia SetAlarmActivity
		Intent intent = new Intent(this, SetAlarmActivity.class);
		startActivity(intent);
	}

	public void smsButton(View view){  //inicia SendSmsActivity
		Intent intent = new Intent(this, SendSmsActivity.class);
		startActivity(intent);
	}
	
	public void contactButton(View view){  //inicia ContactActivity
		Intent intent = new Intent(this, ContactActivity.class);
		startActivity(intent);
	}

	public void sinaisButton(View view){  //inicia SinaisActivity
		Intent intent = new Intent(this, BluetoothActivity.class);
		startActivity(intent);
	}
	
	public void gpsButton(View view){  //inicia SinaisActivity
		Intent intent = new Intent(this, GPSActivity.class);
		startActivity(intent);
	}
}
