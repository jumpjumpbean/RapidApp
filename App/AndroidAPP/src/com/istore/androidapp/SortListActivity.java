package com.istore.androidapp;
import java.io.InputStream;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.istore.JYZLZJNFHXB20130728142717000.R;
import com.istore.androidapp.MainActivity.img_click_listener;
import com.istore.androidapp.MainActivity.main_menu_click_listener;

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

public class SortListActivity extends Activity {
	
	ImageView[] ivs;
	Bitmap[] bitmaps;
	String url = null;
	JSONArray jsonArray = null;
	
	Handler handler1 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(jsonArray != null)
			{
				addImageViews(jsonArray);
			}
		}
	};
	
	Handler handler2 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			ivs[msg.what].setImageBitmap(bitmaps[msg.what]);
			ivs[msg.what].setOnClickListener(new img_click_listener(ivs[msg.what].getTag().toString()));
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.activity_sort_list);

		Button bn_home = (Button)this.findViewById(R.id.bn_home);
		bn_home.setOnClickListener(new main_menu_click_listener(bn_home.getId()));	
		Button bn_sort = (Button)this.findViewById(R.id.bn_sort);
		bn_sort.setOnClickListener(new main_menu_click_listener(bn_sort.getId()));	
		Button bn_pref = (Button)this.findViewById(R.id.bn_pref);
		bn_pref.setOnClickListener(new main_menu_click_listener(bn_pref.getId()));
		Button bn_more = (Button)this.findViewById(R.id.bn_more);
		bn_more.setOnClickListener(new main_menu_click_listener(bn_more.getId()));

		String category =this.getIntent().getStringExtra("category");

		url = getResources().getText(R.string.baseURI) + "/app/imglist/" 
					 + getResources().getText(R.string.SalerID);
		if(category != null)
		{
			url = url + "/" + category;
		}
		
		getJSONAndAddIVs();		
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
	
	private void getJSONAndAddIVs()
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
	
	private void addImageViews(JSONArray jsonArray)
	{
		try 
		{
			int count = jsonArray.length();
			ivs = new ImageView[count];
			bitmaps = new Bitmap[count];

			WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
			int width = wm.getDefaultDisplay().getWidth();//屏幕宽度

			LinearLayout layout = (LinearLayout)this.findViewById(R.id.LinearLayout2);
			
			for(int i=0; i<count; i++)
			{
				JSONObject jsonObj = jsonArray.optJSONObject(i);
				String imgName = jsonObj.get("imgName").toString();
				int height = width * Integer.parseInt(jsonObj.get("height").toString())
						/ Integer.parseInt(jsonObj.get("width").toString());
				ivs[i] = new ImageView(this);
				LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, height);
				ivs[i].setLayoutParams(param);
				ivs[i].setPadding(8, 6, 8, 6);
				ivs[i].setScaleType(ImageView.ScaleType.FIT_XY);
				ivs[i].setTag(jsonObj.get("id").toString());
				showImages(i, imgName);
				layout.addView(ivs[i]);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showImages(final int index, final String imgName)
	{
		new Thread()
		{
			int index1 = index;
			String imgName1 = imgName;
			public void run()
			{
				try
				{
					URL url = new URL(getResources().getText(R.string.baseURI)
							+"/img/upload/"+getResources().getText(R.string.SalerID)
							+"/"+imgName1);
					InputStream is = url.openStream();
					bitmaps[index1] = BitmapFactory.decodeStream(is);
					is.close();
					handler2.sendEmptyMessage(index1);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	class img_click_listener implements View.OnClickListener
	{
		String imgID = null;
		public img_click_listener(String id)
		{
			imgID = id;
		}
		
		public void onClick(View arg0) 
		{
			Intent intent = new Intent(SortListActivity.this, DetailActivity.class);
			intent.putExtra("imgID", imgID);
			startActivity(intent);
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
					intent = new Intent(SortListActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.bn_sort:
					intent = new Intent(SortListActivity.this, SortActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.bn_pref:
					intent = new Intent(SortListActivity.this, PrefActivity.class);
					startActivity(intent);
					finish();
					break;
				case R.id.bn_more:
					intent = new Intent(SortListActivity.this, MoreActivity.class);
					startActivity(intent);
					finish();
					break;
			}
		}
		
	}
	
}
