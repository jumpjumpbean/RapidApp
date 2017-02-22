package com.istore.androidapp;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.istore.JYZLZJNFHXB20130728142717000.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.widget.*;

public class HttpUtil {

	public static HttpClient httpClient = new DefaultHttpClient();
	
	public static String getRequest(final String url) throws Exception
	{
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception
					{
						HttpGet get = new HttpGet(url);
						HttpResponse httpResponse = httpClient.execute(get);
						if (httpResponse.getStatusLine().getStatusCode() == 200)
						{
							String result = EntityUtils.toString(httpResponse.getEntity());
							return result;
						}
						return null;
					}
				});
				new Thread(task).start();
				return task.get();
	}
}
