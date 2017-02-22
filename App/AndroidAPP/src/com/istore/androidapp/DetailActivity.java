package com.istore.androidapp;
import java.io.InputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.istore.JYZLZJNFHXB20130728142717000.R;
import com.istore.androidapp.MainActivity.img_click_listener;
import com.istore.androidapp.SortListActivity.main_menu_click_listener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

public class DetailActivity extends Activity {
	
	ImageView img;
	Bitmap bitmap;
	String url = null;
	JSONArray jsonArray = null;

	Handler handler1 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(jsonArray != null)
			{
				setContents(jsonArray);
			}
		}
	};
	
	Handler handler2 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(msg.what == 0x7777)
			{
				img.setImageBitmap(bitmap);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.activity_detail);
		
		Button bn_home = (Button)this.findViewById(R.id.bn_home);
		bn_home.setOnClickListener(new main_menu_click_listener(bn_home.getId()));	
		Button bn_sort = (Button)this.findViewById(R.id.bn_sort);
		bn_sort.setOnClickListener(new main_menu_click_listener(bn_sort.getId()));	
		Button bn_pref = (Button)this.findViewById(R.id.bn_pref);
		bn_pref.setOnClickListener(new main_menu_click_listener(bn_pref.getId()));
		Button bn_more = (Button)this.findViewById(R.id.bn_more);
		bn_more.setOnClickListener(new main_menu_click_listener(bn_more.getId()));


		String imgID =this.getIntent().getStringExtra("imgID");

		url = getResources().getText(R.string.baseURI) + "/app/imgdetail/" 
					 + getResources().getText(R.string.SalerID);
		if(imgID != null)
		{
			url = url + "/" + imgID;
		}
		
		getJSONAndSetContents();		
	}
	
	protected void onDestroy()
	{
//		if(bitmap != null && !bitmap.isRecycled())
//		{
//			// 回收并且置为null
//			bitmap.recycle();
//			bitmap = null;
//		}
//		img.destroyDrawingCache();
//		img.setImageBitmap(null);
		System.gc();
		super.onDestroy();
	}
	
	private void getJSONAndSetContents()
	{
		new Thread()
		{
			public void run()
			{
				try
				{
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
	
	private void setContents(JSONArray jsonArray)
	{
		try 
		{
			WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();//屏幕宽度

			JSONObject jsonObj = jsonArray.optJSONObject(0);
			
			String imgName = jsonObj.get("imgName").toString();
			int height = width * Integer.parseInt(jsonObj.get("height").toString())
					/ Integer.parseInt(jsonObj.get("width").toString());
			
			img = new ImageView(this);
			LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, height);
			img.setLayoutParams(param);
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			
			LinearLayout layout = (LinearLayout)this.findViewById(R.id.LinearLayout3);
			layout.addView(img);
			
			TextView detail_name = (TextView)this.findViewById(R.id.detail_name);
			detail_name.setText(jsonObj.getString("name"));
			TextView detail_desc = (TextView)this.findViewById(R.id.detail_desc);
			detail_desc.setText(jsonObj.getString("descriptions"));
			
			showImages(imgName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showImages(final String imgName)
	{
		new Thread()
		{
			String imgName1 = imgName;
			public void run()
			{
				try
				{
					URL url = new URL(getResources().getText(R.string.baseURI)
							+"/img/upload/"+getResources().getText(R.string.SalerID)
							+"/"+imgName1);
					InputStream is = url.openStream();
					bitmap = BitmapFactory.decodeStream(is);
					is.close();
					handler2.sendEmptyMessage(0x7777);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
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
					intent = new Intent(DetailActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.bn_sort:
					intent = new Intent(DetailActivity.this, SortActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.bn_pref:
					intent = new Intent(DetailActivity.this, PrefActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.bn_more:
					intent = new Intent(DetailActivity.this, MoreActivity.class);
					startActivity(intent);
					finish();
					break;
			}
		}
		
	}
	
}
