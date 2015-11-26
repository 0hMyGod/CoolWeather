package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil{
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener)
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				try {
					URL url=new URL(address);
					//打开连接
					connection=(HttpURLConnection) url.openConnection();
					//设置连接的一些属性，请求方式为get，连接超时8秒，读取超时8秒
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					//获取输入流
					InputStream in=connection.getInputStream();
			
					BufferedReader reader=new BufferedReader(new InputStreamReader(in));
					StringBuilder response=new StringBuilder();
					String line;
					//每次读取一行且不为空的时候
					while((line=reader.readLine())!=null)
					{
						response.append(line);
					}
					if (listener!=null) {
						//回调onFinish方法
						listener.onFinish(response.toString());
						
					}
					
				} catch (Exception e) {
					//对调onError方法
					listener.onError(e);
				}
				finally
				{
					//在最后关掉连接
					if (connection!=null) {
						connection.disconnect();
						
					}
				}
				
			}
		}).start();
		
	}

}
