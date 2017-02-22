package com.istore.androidapp;
import java.io.InputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import org.json.*;

import com.istore.JYZLZJNFHXB20130728142717000.R;
import com.istore.androidapp.SortActivity.main_menu_click_listener;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.*;

public class AppStartActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		final View view = View.inflate(this, R.layout.activity_app_start, null);
		setContentView(view);
		
		final Intent intent = new Intent();
		intent.setAction("com.istore.androidapp.Lauch_Service");
		stopService(intent);
		startService(intent);
		
        ConnectivityManager cwjManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); 
        NetworkInfo info = cwjManager.getActiveNetworkInfo(); 
        if (info == null || !info.isAvailable()){ 
        	try
        	{
	        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        	builder.setTitle(getResources().getText(R.string.SalerName)).toString();
	        	builder.setMessage("无法连接到网络！");
	        	
	        	builder.setPositiveButton("退出", new DialogInterface.OnClickListener()
	        	{
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
	        	});
	        	builder.show();
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
        else
        {
			//渐变展示启动屏
			AlphaAnimation aa = new AlphaAnimation(1.0f,1.0f);
			aa.setDuration(800);
			view.startAnimation(aa);
			aa.setAnimationListener(new AnimationListener()
			{
				@Override
				public void onAnimationEnd(Animation arg0) {
					redirectTo();
				}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationStart(Animation animation) {}
				
			});
        }
		
    }
    
    private void redirectTo(){        
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    
}
