package com.example.sinais;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.healthdrops.R;
import com.example.modelclasses.ArchiveManager;
  
public class SinaisActivity extends ListActivity {  
	String[] out;
	ArrayList<String> output;
  	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Show the Up button in the action bar.
		setupActionBar();
	    // Pegar entradas antigas do arquivo
	    ArchiveManager manager = new ArchiveManager(this);
	       
	    out =  manager.ler("sensor").split("DIV");
  	    output = new ArrayList<String>();
  	    
  	    for(int i =0; i < out.length; i++){
  	    	if(out[i].length()>25){
  	    		output.add(out[i].substring(0, 25));
  	    	}
  	    	else output.add(out[i]);
  	    }
	    
	    // colocar na listView
	    setListAdapter(new ArrayAdapter<String>(this,R.layout.activity_sinais, output)); 
	    ListView listView = getListView();
	    listView.setTextFilterEnabled(true);
 
	    
	    //onClick (funciona eeeeeeeeeeeeeee)(falta o popup, massssss) (agora tem eeeeeeee)
	    listView.setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    		LayoutInflater layoutInflater 
	    	     = (LayoutInflater)getBaseContext()
	    	      .getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	    View popupView = layoutInflater.inflate(R.layout.popup, null);  
	    	             final PopupWindow popupWindow = new PopupWindow(
	    	               popupView, 
	    	               LayoutParams.WRAP_CONTENT,  
	    	                     LayoutParams.WRAP_CONTENT);  
	    	             
	    	             
	    	             
	    	             Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
	    	             btnDismiss.setOnClickListener(new Button.OnClickListener(){

	    	     @Override
	    	     public void onClick(View v) {
	    	      // TODO Auto-generated method stub
	    	      popupWindow.dismiss();
	    	     }});
	    	               
	    	             popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
	    	             
	    	             TextView recording_text = (TextView) popupWindow.getContentView().
	    	                     findViewById(R.id.popup_text);
	    	             String s = ((TextView) view).getText().toString();
	    	             String f = "";

	    	            	f = out[output.indexOf(s)];
	    	             
	    	             Log.e("LOG_TAG",f); //ok, as paradas chegam na string
	    	             recording_text.setText(f);
	    	         
	    	   };
	    		//Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
	    	
	    });
	            
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ler_diario, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
