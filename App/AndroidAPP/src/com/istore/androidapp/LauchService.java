package com.istore.androidapp;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.istore.JYZLZJNFHXB20130728142717000.R;
import com.istore.androidapp.DetailActivity.main_menu_click_listener;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class LauchService extends Service
{

	static final int NOTIFICATION_ID = 0x123;
    private WakeLock wakeLock;
    private TestTask task;
    private Timer timer;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
    
    @Override    
	public void onCreate() {
		
        PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wakeLock=powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getCanonicalName());
        wakeLock.acquire();
        super.onCreate();

//		final Handler handler = new Handler()
//		{
//			public void handleMessage(Message msg)
//			{
//				if(msg.what == 0x9999)
//				{
//					showNotification(R.drawable.notification,"图标边的文字","标题","内容");
//				}
//			}
//		};
//		
//		new Timer().schedule(new TimerTask()
//		{
//			public void run()
//			{
//				try {
//					JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(
//							 getResources().getText(R.string.baseURI) + "/app/pushlist/" 
//									 + getResources().getText(R.string.SalerID) + "/0"));
//					handler.sendEmptyMessage(0x9999);
//				} catch (NotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}, 2000);
		
	}
	
    @Override
    public void onDestroy() {
//            if(task!=null){
//               timer.cancel();
//               task.cancel();
//               timer=null;
//               task=null;
//            }
//            if (wakeLock != null && wakeLock.isHeld()) {
//                    wakeLock.release();
//                    wakeLock = null;
//            }
//            super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
            if(task==null){
                    task=new TestTask();
                    timer=new Timer();    
                    timer.schedule(task,0,1000*60*Integer.parseInt(getResources().getText(R.string.Interval).toString()));  
            }
            return START_STICKY;
    }
	
    public void showNotification(int icon,			//图标
    						int notification_id,	//ID
    						String tickertext,		//图标旁的文字
    						String title,			//标题
    						String content){		//内容
    	//设置一个唯一的ID，随便设置
    	NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);;
    	//Notification管理器
    	Notification notification = new Notification(icon,tickertext,System.currentTimeMillis());
    	//后面的参数分别是显示在顶部通知栏的小图标，小图标旁的文字（短暂显示，自动消失）系统当前时间（不明白这个有什么用）
    	notification.defaults = Notification.DEFAULT_ALL; 
    	//这是设置通知是否同时播放声音或振动，声音为Notification.DEFAULT_SOUND
    	//振动为Notification.DEFAULT_VIBRATE;
    	//Light为Notification.DEFAULT_LIGHTS，在我的Milestone上好像没什么反应
    	//全部为Notification.DEFAULT_ALL
    	//如果是振动或者全部，必须在AndroidManifest.xml加入振动权限
    	PendingIntent pt = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
    	//点击通知后的动作，这里是转回main 这个Acticity
    	notification.setLatestEventInfo(this, title, content, pt);
    	nm.notify(notification_id, notification);
 
    }
    
	public class TestTask extends TimerTask {
	
	    @Override
	    public void run() {
	    	try
	    	{
				Context ctx = LauchService.this;
				SharedPreferences sp = ctx.getSharedPreferences("SP", MODE_PRIVATE);
				String lastID = sp.getString(getResources().getText(R.string.NotifyKey).toString(), null);
				
				if(lastID == null)
				{
					JSONArray preJsonArray = new JSONArray(HttpUtil.getRequest(
							 getResources().getText(R.string.baseURI) + "/app/pushlist/" 
									 + getResources().getText(R.string.SalerID)));
					lastID = "0";
					if(preJsonArray.length() > 0)
					{
						JSONObject preJsonObj = preJsonArray.optJSONObject(0);
						lastID = preJsonObj.get("id").toString();
					}
				}
				JSONArray jsonArray = new JSONArray(HttpUtil.getRequest(
				 getResources().getText(R.string.baseURI) + "/app/pushlist/" 
						 + getResources().getText(R.string.SalerID) + "/" + lastID));
		    	
				String NotifyID = lastID;
				
				int count = jsonArray.length();
				if(count > 0)
				{
					for(int i=0; i<count; i++)
					{
						JSONObject jsonObj = jsonArray.optJSONObject(i);
						NotifyID = jsonObj.get("id").toString();
						String content = jsonObj.get("name").toString();
						
				    	showNotification(R.drawable.notification, Integer.parseInt(NotifyID),
				    			content, getResources().getText(R.string.SalerName).toString(), content);
					}
					NotifyID = jsonArray.optJSONObject(count-1).get("id").toString();
				}
				//存入数据
				Editor editor = sp.edit();
				editor.putString(getResources().getText(R.string.NotifyKey).toString(), NotifyID);
//				editor.putInt(getResources().getText(R.string.NotifyKey).toString(), NotifyID);
//				editor.putBoolean("BOOLEAN_KEY", true);
				editor.commit();
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	
	    }
	    
	}
}
