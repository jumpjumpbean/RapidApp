package com.istore.androidapp;
import java.io.InputStream;
import java.net.URL;

import org.json.*;

import com.istore.JYZLZJNFHXB20130728142717000.R;
import com.istore.androidapp.SortActivity.main_menu_click_listener;

import android.os.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

public class MoreActivity extends Activity {

	JSONArray jsonArray = null;

	Handler handler1 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(jsonArray != null)
			{
				setContent(jsonArray);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.activity_more);
		
//		final Intent intent = new Intent();
//		intent.setAction("com.istore.androidapp.Lauch_Service");
//		stopService(intent);
//		startService(intent);
				
		Button bn_home = (Button)this.findViewById(R.id.bn_home);
		bn_home.setOnClickListener(new main_menu_click_listener(bn_home.getId()));	
		Button bn_sort = (Button)this.findViewById(R.id.bn_sort);
		bn_sort.setOnClickListener(new main_menu_click_listener(bn_sort.getId()));	
		Button bn_pref = (Button)this.findViewById(R.id.bn_pref);
		bn_pref.setOnClickListener(new main_menu_click_listener(bn_pref.getId()));
		Button bn_more = (Button)this.findViewById(R.id.bn_more);
		bn_more.setOnClickListener(new main_menu_click_listener(bn_more.getId()));

		getJSONAndSetContent();		
	}
	
	protected void onDestroy()
	{
//		for(Bitmap bitmap : bitmaps)
//		{
//			if(bitmap != null && !bitmap.isRecycled())
//			{
//				// 回收并且置为null
//				bitmap.recycle();
//				bitmap = null;
//			}
//		}
//		for(ImageView iv : ivs)
//		{
//			iv.destroyDrawingCache();
//			iv.setImageBitmap(null);
//		}
		System.gc();
		super.onDestroy();
	}
	
	private void getJSONAndSetContent()
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					String url = getResources().getText(R.string.baseURI) + "/app/storedetail/" 
								 + getResources().getText(R.string.SalerID);

					jsonArray = new JSONArray(HttpUtil.getRequest(url));
					handler1.sendEmptyMessage(0x999);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void setContent(JSONArray jsonArray)
	{
		try 
		{		
			JSONObject jsonObj = jsonArray.optJSONObject(0);
			String storeName = jsonObj.get("name").toString();
			String storeDesc = jsonObj.get("descriptions").toString();
			String storeAddress = getResources().getText(R.string.address).toString() + jsonObj.get("address").toString();
			String storeMail = getResources().getText(R.string.mailBox).toString() + jsonObj.get("email").toString();
			String storePhone = getResources().getText(R.string.phone).toString() + jsonObj.get("phone").toString();
			
			TextView tv = (TextView)this.findViewById(R.id.store_name);
			tv.setText(storeName);
			tv = (TextView)this.findViewById(R.id.store_desc);
			tv.setText(storeDesc);
			tv = (TextView)this.findViewById(R.id.store_address);
			tv.setText(storeAddress);
			tv = (TextView)this.findViewById(R.id.store_mail);
			tv.setText(storeMail);
			tv = (TextView)this.findViewById(R.id.store_phone);
			tv.setText(storePhone);
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class main_menu_click_listener implements View.OnClickListener
	{
		int Button_id = 0;
		Intent intent = null;
		
		public main_menu_click_listener(int id)
		{
			Button_id = id;
		}
		
		public void onClick(View v)
		{
			switch(Button_id)
			{
				case R.id.bn_home:
					intent = new Intent(MoreActivity.this, MainActivity.class);
					startActivity(intent);
					break;
				case R.id.bn_sort:
					intent = new Intent(MoreActivity.this, SortActivity.class);
					startActivity(intent);
					break;
				case R.id.bn_pref:
					intent = new Intent(MoreActivity.this, PrefActivity.class);
					startActivity(intent);
					break;
				case R.id.bn_more:
					break;
			}
		}
		
	}
	
}
