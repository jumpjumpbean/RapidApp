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

public class PrefActivity extends Activity {

	JSONArray jsonArray = null;

	Handler handler1 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(jsonArray != null)
			{
				addContents(jsonArray);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 填充标题栏
		setContentView(R.layout.activity_pref);
		
		Button bn_home = (Button)this.findViewById(R.id.bn_home);
		bn_home.setOnClickListener(new main_menu_click_listener(bn_home.getId()));	
		Button bn_sort = (Button)this.findViewById(R.id.bn_sort);
		bn_sort.setOnClickListener(new main_menu_click_listener(bn_sort.getId()));	
		Button bn_pref = (Button)this.findViewById(R.id.bn_pref);
		bn_pref.setOnClickListener(new main_menu_click_listener(bn_pref.getId()));
		Button bn_more = (Button)this.findViewById(R.id.bn_more);
		bn_more.setOnClickListener(new main_menu_click_listener(bn_more.getId()));

		getJSONAndAddContents();
	}
	
	private void getJSONAndAddContents()
	{
		new Thread()
		{
			public void run()
			{
				try
				{

					jsonArray = new JSONArray(HttpUtil.getRequest(
						 getResources().getText(R.string.baseURI) + "/app/promotionlist/" 
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
	
	private void addContents(JSONArray jsonArray)
	{
		try {
			int count = jsonArray.length();

			LinearLayout layout = (LinearLayout)this.findViewById(R.id.LinearLayout2);
			
			for(int i=0; i<count; i++)
			{
				JSONObject jsonObj = jsonArray.optJSONObject(i);

				TextView period = new TextView(this, null, R.style.detail_text);
				LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				period.setLayoutParams(param);
				period.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
				period.setPadding(6, 0, 0, 0);
				period.setBackgroundColor(getResources().getColor(R.color.white));
				period.setTextColor(getResources().getColor(R.color.black));
				period.setTextSize(18);
				period.setText(getResources().getText(R.string.prefText1) + jsonObj.getString("beginDate")
							+ getResources().getText(R.string.wavyLine) + jsonObj.getString("endDate"));
				layout.addView(period);

				TextView content = new TextView(this, null, R.style.detail_text);
				content.setLayoutParams(param);
				content.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
				content.setPadding(6, 0, 0, 0);
				content.setBackgroundColor(getResources().getColor(R.color.white));
				content.setTextColor(getResources().getColor(R.color.black));
				content.setTextSize(15);
				content.setText(jsonObj.getString("content"));
				layout.addView(content);

				TextView tv1 = new TextView(this);
				tv1.setBackgroundColor(getResources().getColor(R.color.white));
				LayoutParams param1 = new LayoutParams(LayoutParams.MATCH_PARENT, 10);
				tv1.setLayoutParams(param1);
				layout.addView(tv1);
				TextView tv2 = new TextView(this);
				tv2.setBackgroundColor(getResources().getColor(R.color.gray));
				LayoutParams param2 = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
				tv2.setLayoutParams(param2);
				layout.addView(tv2);
			}
					
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
					intent = new Intent(PrefActivity.this, MainActivity.class);
					startActivity(intent);
					break;
				case R.id.bn_sort:
					intent = new Intent(PrefActivity.this, SortActivity.class);
					startActivity(intent);
					break;
				case R.id.bn_pref:
					break;
				case R.id.bn_more:
					intent = new Intent(PrefActivity.this, MoreActivity.class);
					startActivity(intent);
					break;
			}
		}
		
	}
	
}
