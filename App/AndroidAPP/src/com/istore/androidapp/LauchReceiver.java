package com.istore.androidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LauchReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		Intent tIntent = new Intent(context, LauchService.class);
		context.startService(tIntent);
	}
	
}
