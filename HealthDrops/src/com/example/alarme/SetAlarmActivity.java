package com.example.alarme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

	    String nameStr = null, intervalStr = null,horaIntervalStr = null, minIntervalStr = null, numDosisStr = null,
	    		numPillsBoxStr = null, numPillsDoseStr = null, DoseInicialStr = null, horaDoseInicialStr = null,
	    		minDoseInicialStr = null;
	    
	    try{
		    nameStr = nameEdit.getText().toString();
		    intervalStr = intervalEdit.getText().toString();
		    numDosisStr = numDosisEdit.getText().toString();
		    numPillsBoxStr = numPillsBoxEdit.getText().toString();
		    numPillsDoseStr = numPillsDoseEdit.getText().toString();       //passando para strings e parseando
		    DoseInicialStr = DoseInicialEdit.getText().toString();
	    }catch(NullPointerException npe){
	    	Toast.makeText(this, "Por favor,preencha todos os campos antes de prosseguir", Toast.LENGTH_SHORT).show();
	    	return;
	    }	
	    try{	
	    	horaIntervalStr = intervalStr.substring(0,intervalStr.indexOf(":"));
	     	minIntervalStr = intervalStr.substring(intervalStr.indexOf(":") + 1);
	    	horaDoseInicialStr = DoseInicialStr.substring(0,DoseInicialStr.indexOf(":"));
	    	minDoseInicialStr = DoseInicialStr.substring(DoseInicialStr.indexOf(":") + 1);
	    }catch(StringIndexOutOfBoundsException obe){
	    	Toast.makeText(this, "Por favor,preencha todos os campos no formato adequado antes de prosseguir", Toast.LENGTH_SHORT).show();
	    	return;
	    }
	    
	    Log.e(LOG_TAG,"criando medicine");
	    Medicine medicine = new Medicine(this,nameStr,Integer.decode(horaIntervalStr),Integer.decode(minIntervalStr),Integer.decode(numDosisStr),Integer.decode(numPillsDoseStr),Integer.decode(horaDoseInicialStr),Integer.decode(minDoseInicialStr), Integer.decode(numPillsBoxStr));
	    Log.e(LOG_TAG,"criando receiver");
	    AlarmReceiver almReceiver = new AlarmReceiver(this, medicine);
	    Log.e(LOG_TAG,"colocando medicine em receiver");
	    //registerReceiver(almReceiver);
	    
	    medicine.setAlarme();
	    Log.e(LOG_TAG,"fim de setalarm");
    }
    
}