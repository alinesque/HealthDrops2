package com.example.modelclasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
	public static final String LOG_TAG = "Log Tag";
	
	public static final String ALARM_ALERT_ACTION = "com.android.deskclock.ALARM_ALERT";
	public static final String ALARM_SNOOZE_ACTION = "com.android.deskclock.ALARM_SNOOZE";
	public static final String ALARM_DISMISS_ACTION = "com.android.deskclock.ALARM_DISMISS";
	public static final String ALARM_DONE_ACTION = "com.android.deskclock.ALARM_DONE";

	public Medicine medicine;
	Context context;
	
	public AlarmReceiver(Context inContext, Medicine inMedicine) {
		context = inContext;
		medicine = inMedicine;
  	  	Log.e(LOG_TAG,"no construtor de AlarmReceiver");
	}
	
    public void onReceive(Context context, Intent intent) {
    	
    	  Log.e(LOG_TAG,"em onReceive");
    	  
          String action = intent.getAction();

          if (action.equals(ALARM_ALERT_ACTION) || action.equals(ALARM_DISMISS_ACTION) || action.equals(ALARM_SNOOZE_ACTION) || action.equals(ALARM_DONE_ACTION)) {
              // chamar o alarme
        	  
        	  Log.e(LOG_TAG,"verificando doses");
        	  Log.e(LOG_TAG,"doses = " + medicine.getNumDosisRestante());

        	  if(medicine.getNumDosisRestante() != 0) {
        		  Log.e(LOG_TAG,"chamando o alarme");
        		  medicine.setAlarme();
        	  }
          }
    }

	public void onCreate(Bundle savedInstanceState) {
		Log.e(LOG_TAG, "criando");
	    IntentFilter filter = new IntentFilter(ALARM_ALERT_ACTION);
	    filter.addAction(ALARM_DISMISS_ACTION);
	    filter.addAction(ALARM_SNOOZE_ACTION);
	    filter.addAction(ALARM_DONE_ACTION);
	    context.registerReceiver(this, filter);   //isso tem que funcionar
	}
}
