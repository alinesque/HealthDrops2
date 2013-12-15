package com.example.sinais;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthdrops.R;
import com.example.modelclasses.ArchiveManager;

public class BluetoothActivity extends Activity {

    TextView Label2;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		
        Label2 = (TextView) findViewById(R.id.statusLabel);
        
        try 
        {
            boolean b = findBT();
            if(b) openBT();
            else{
            	TextView t = (TextView) findViewById(R.id.statusLabel);
            	t.setText("Sem contato");
            	Calendar c = Calendar.getInstance();
            	Date d = c.getTime();            	
            	//escrever("180" + "" + d.toString());//TESTESTESTESTESTE
            }
        }
        catch (IOException ex) { }

	}
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bluetooth, menu);
		return true;
	}
	
    boolean findBT()
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            Toast.makeText(this,"Bluetooth indisponível",Toast.LENGTH_SHORT).show();
            return false;
            //finish();
        }
        
        if(!mBluetoothAdapter.isEnabled())
        {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }
        
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals("HC-05")) 
                {
                    mmDevice = device;
                    Label2.setText("Sensor indisponível");
                    Calendar c = Calendar.getInstance();
                	Date d = c.getTime();            	
                	//escrever("180" + " " + d.toString());//TESTESTESTESTESTE
                    return true;//diferente
                    //break;
                }
            }
        }
        return false;//diferente
    }
    
    void openBT() throws IOException
    {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);        
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
        
        beginListenForData();
        
        Label2.setText("Bluetooth Opened");
    }
    
    void beginListenForData()
    {
        final Handler handler = new Handler(); 
        final byte delimiter = 10; //This is the ASCII code for a newline character
        
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable()
        {
            public void run()
            {                
               while(!Thread.currentThread().isInterrupted() && !stopWorker)
               {
                    try 
                    {
                        int bytesAvailable = mmInputStream.available();                        
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    
                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                        	//data.subSequence(1, data.length()-1);//ver se funciona
                                        	data.substring(1, data.length()-1);
                                            Label2.setText(data);
                                            Calendar c = Calendar.getInstance();
                                            Date d = c.getTime();
                                            escrever(data + " " + d.toString());
                                            if(Integer.parseInt(data)<70)   Label2.setText(data + "Abaixo do normal");
                                            if(Integer.parseInt(data)>110)   Label2.setText(data + "Acima do normal");
                                            else Label2.setText("Normal");
                                            try{
                                            	Thread.sleep(3000);
                                            }catch(Exception e){}
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } 
                    catch (IOException ex) 
                    {
                        stopWorker = true;
                    }
               }
            }
        });

        workerThread.start();
    }
    
    void sendData() throws IOException
    {
    	String msg = "msg";
        msg += "\n";
        mmOutputStream.write(msg.getBytes());
        Label2.setText("Data Sent");
    }
    
    void closeBT() throws IOException
    {
        stopWorker = true;
        if(mmOutputStream != null) mmOutputStream.close();
        if(mmInputStream != null) mmInputStream.close();
        if(mmSocket != null) mmSocket.close();
        Label2.setText("Bluetooth Closed");
    }
    
	public void onClick(View view) {
	   	Intent intent = new Intent(this, SinaisActivity.class);
		startActivity(intent);
	}
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try 
        {
            closeBT();
        }
        catch (IOException ex) { }
    }
    
    public void escrever(String leitura){
    	String filename = "sensor";
		
		ArchiveManager manager = new ArchiveManager(this);
		
		boolean escreveu = manager.escrever(filename, leitura);					//escrever entrada no arquivo "logs.txt"
		
		if(escreveu) {
			Toast.makeText(this, "Seu status foi salvo", Toast.LENGTH_SHORT).show();//sucesso!
		}
		else {
			Toast.makeText(this, "Seu status não foi salvo", Toast.LENGTH_SHORT).show();//fracasso
		}
    }
}
