package com.example.modelclasses;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;

public class SmsSender {
	
	public static final int PANICO_MESSAGE = 0;
	public static final int GPS_MESSAGE = 1;
	Context context;
	
	public SmsSender(Context inContext){
		context = inContext;
	}
	
	public boolean SendEmergencySms(int caller){
		
		Log.e("LOGTAG","em SendEmergencySms");
		boolean b;
		try{
			ArchiveManager archiveManager = new ArchiveManager(context);
			String[] out = archiveManager.ler("contact_info").split("DIV");           //acessando o arquivo do contato de emergência
			
				
			SmsManager smsManager = SmsManager.getDefault();
			String message = null;
			switch(caller){
			case PANICO_MESSAGE:
				message = out[0] + ",preciso de sua ajuda!";
				break;
			case GPS_MESSAGE:
				message = out[0] + "seu dependente saiu da area predeterminada";
				break;
			}
			String phoneNo = out[1];
			Log.e("LOGTAG",message);
			smsManager.sendTextMessage(phoneNo, null, message, null, null);   //enviando sms
			b =  true;
		}catch(Exception e){
			Log.e("LOGTAG","exception");
			b = false;
		}
		return b;
	}

}
