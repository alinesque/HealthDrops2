package com.example.alarme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.example.healthdrops.R;
import com.example.modelclasses.AlarmReceiver;
import com.example.modelclasses.Medicine;

public class SetAlarmActivity extends Activity {
public static final String LOG_TAG = "LogTag";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);                
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_alarm, menu);
        return true;
    }
    
    public void salvarAlarmeButton(View view) {
	
    	EditText nameEdit = (EditText) findViewById(R.id.medicine_name_alarm_edittext);
	    EditText intervalEdit = (EditText) findViewById(R.id.interval_dosis_alarm_edittext);
	    EditText numDosisEdit = (EditText) findViewById(R.id.num_dosis_edittext);                      //pegando a entrada do usuario
	    EditText numPillsBoxEdit = (EditText) findViewById(R.id.num_pills_box_alarm_edittext);
	    EditText numPillsDoseEdit = (EditText) findViewById(R.id.num_pills_dose_alarme_edittext);
	    EditText DoseInicialEdit = (EditText) findViewById(R.id.first_dosis_time_alarm_edittext);
	    
	    String nameStr = nameEdit.getText().toString();
	    String intervalStr = intervalEdit.getText().toString();
	    	String horaIntervalStr = intervalStr.substring(0,intervalStr.indexOf(":"));
	    	String minIntervalStr = intervalStr.substring(intervalStr.indexOf(":") + 1);
	    String numDosisStr = numDosisEdit.getText().toString();
	    String numPillsBoxStr = numPillsBoxEdit.getText().toString();
	    String numPillsDoseStr = numPillsDoseEdit.getText().toString();                                //passando para strings e parseando
	    String DoseInicialStr = DoseInicialEdit.getText().toString();
			String horaDoseInicialStr = DoseInicialStr.substring(0,DoseInicialStr.indexOf(":"));
	    	String minDoseInicialStr = DoseInicialStr.substring(DoseInicialStr.indexOf(":") + 1);
		
	    	
	    Log.e(LOG_TAG,"criando medicine");
	    Medicine medicine = new Medicine(this,nameStr,Integer.decode(horaIntervalStr),Integer.decode(minIntervalStr),Integer.decode(numDosisStr),Integer.decode(numPillsDoseStr),Integer.decode(horaDoseInicialStr),Integer.decode(minDoseInicialStr));
	    Log.e(LOG_TAG,"criando receiver");
	    AlarmReceiver almReceiver = new AlarmReceiver(this, medicine);
	    Log.e(LOG_TAG,"colocando medicine em receiver");
	    medicine.setAlarme();
	    Log.e(LOG_TAG,"fim de setalarm");
    }
    
}