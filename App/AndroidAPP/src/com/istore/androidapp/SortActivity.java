package com.istore.androidapp;
import org.json.JSONArray;
import org.json.JSONObject;

import com.istore.JYZLZJNFHXB20130728142717000.R;
import com.istore.androidapp.MainActivity.main_menu_click_listener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;

public class SortActivity extends Activity {

	JSONArray jsonArray = null;

	Handler handler1 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(jsonArray != null)
			{
				addMenus(jsonArray);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.activity_sort);
		
		Button bn_home = (Button)this.findViewById(R.id.bn_home);
		bn_home.setOnClickListener(new main_menu_click_listener(bn_home.getId()));	
		Button bn_sort = (Button)this.findViewById(R.id.bn_sort);
		bn_sort.setOnClickListener(new main_menu_click_listener(bn_sort.getId()));	
		Button bn_pref = (Button)this.findViewById(R.id.bn_pref);
		bn_pref.setOnClickListener(new main_menu_click_listener(bn_pref.getId()));
		Button bn_more = (Button)this.findViewById(R.id.bn_more);
		bn_more.setOnClickListener(new main_menu_click_listener(bn_more.getId()));

		getJSONAndAddMenus();
	}
	
	private void getJSONAndAddMenus()
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					jsonArray = new JSONArray(HttpUtil.getRequest(
							 getResources().getText(R.string.baseURI) + "/app/catlist/" 
									 + getResources().getText(R.string.SalerID)));
					handler1.sendEmptyMessage(0x999);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	private void addMenus(JSONArray jsonArray)
	{
		try {			
			int count = jsonArray.length();

			LinearLayout layout = (LinearLayout)this.findViewById(R.id.LinearLayout2);
			
			for(int i=0; i<count; i++)
			{
				JSONObject jsonObj = jsonArray.optJSONObject(i);

				Button btn = new Button(this, null, R.style.main_sort_menu);

				WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
				int width = wm.getDefaultDisplay().getHeight();//屏幕高度

				LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, width/9);
				btn.setLayoutParams(param);
				btn.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
				btn.setPadding(6, 0, 0, 0);
				btn.setBackgroundColor(getResources().getColor(R.color.white));
				btn.setTextColor(getResources().getColor(R.color.black));
				btn.setTextSize(18);
				btn.setTextAppearance(this, R.style.main_sort_menu);
				
				btn.setText(jsonObj.getString("name"));
				btn.setOnClickListener(new menulist_click_listener(jsonObj.getString("id")));
				
				layout.addView(btn);
				
				TextView tv = new TextView(this);
				tv.setBackgroundColor(getResources().getColor(R.color.gray));
				LayoutParams param1 = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
				tv.setLayoutParams(param1);
				layout.addView(tv);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	class menulist_click_listener implements View.OnClickListener
	{
		String category_id = null;
		
		public menulist_click_listener(String id)
		{
			category_id = id;
		}
		
		public void onClick(View v)
		{
			Intent intent = new Intent(SortActivity.this, SortListActivity.class);
			intent.putExtra("category", category_id);
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
					intent = new Intent(SortActivity.this, MainActivity.class);
					startActivity(intent);
					break;
				case R.id.bn_sort:
					break;
				case R.id.bn_pref:
					intent = new Intent(SortActivity.this, PrefActivity.class);
					startActivity(intent);
					break;
				case R.id.bn_more:
					intent = new Intent(SortActivity.this, MoreActivity.class);
					startActivity(intent);
					break;
			}
		}
		
	}
	
}
