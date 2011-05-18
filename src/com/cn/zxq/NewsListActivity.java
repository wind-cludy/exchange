package com.cn.zxq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cn.zxq.bean.HeadLineBean;
import com.cn.zxq.bean.StoryBean;
import com.cn.zxq.xmlparser.XmlPaser;
import com.cn.zxq.xmlparser.handler.HeanLinesHandler;
import com.cn.zxq.xmlparser.handler.StoryHandler;

public class NewsListActivity extends Activity {

	LinearLayout ll_news_head;
	ListView lv_news_list;
	private static final String URL_STRING = "http://124.40.40.146/_xml/marketnews/headlines.xml";
	private static final String URL_XML_STRING = "http://124.40.40.146/_xml/marketnews/fxnews_XXXXXX.xml";
	private Button backButton;
	//private Pattern aTagfind = Pattern.compile("<\\s*a\\s+([^>]*)\\s*>");
	//private Pattern tableTagfind = Pattern.compile("<table.*?>(.*?)</table>");
	private Pattern aTagfind = Pattern.compile("<a.*?href=\"(.*?)\".*?>.*?</a>");
	private static final String URL_XML_ALL = "http://124.40.40.146/_xml/marketnews/";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.news_list);
		backButton = (Button) findViewById(R.id.news_back);
		//endButton();
		ll_news_head = (LinearLayout) findViewById(R.id.news_head_ll);
		ll_news_head.setBackgroundResource(R.drawable.channelgallery_bg);
		lv_news_list = (ListView) findViewById(R.id.news_list_ll);
//		List<String> lists = getData();
//		if(lists!=null &&lists.size()>0 ) {
//			lists.remove(0);
//			Collections.sort(lists);
//			
//		}
		loadListData();
	}
	
	private void loadListData() {
		List<StoryBean> storyBeans = new ArrayList<StoryBean>();
		try {
			//String data = htmlParse(url);
			
			XmlPaser<HeadLineBean> xmlPaser = new XmlPaser<HeadLineBean>();
			HeanLinesHandler heanLinesHandler = new HeanLinesHandler();
			List<HeadLineBean> lists = xmlPaser.getNewsData(URL_STRING,heanLinesHandler);
			XmlPaser<StoryBean> storyXmlPaser = new XmlPaser<StoryBean>();
			for (HeadLineBean bean : lists) {
				if (bean.getOperation().equals("delete"))
					continue;
				String temp = URL_XML_STRING.replace("XXXXXX", bean.getStoryId());
				StoryHandler storyHandler = new StoryHandler();
				List<StoryBean> listStory =storyXmlPaser.getNewsData(temp, storyHandler);
				storyBeans.add(listStory.get(0));
			}

		} catch (Exception e) {
			e.getStackTrace();

		}
		
		ListAdapter adapter = new ArrayAdapter<StoryBean>(this,R.layout.news_title_item,storyBeans){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = convertView;
				if(view == null) {
					LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					view = inflater.inflate(R.layout.news_title_item, null);
				}
				StoryBean myBean = getItem(position);
				TextView textView = (TextView)view.findViewById(R.id.news_title_item);
				String title = myBean.getTitle();
				textView.setText(title);
				return view;
			}
		};
		lv_news_list.setAdapter(adapter);
	}
	
	public List<String> getData() {
		List<String> keys = new ArrayList<String>();
		try {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL_XML_ALL);
		HttpResponse response = httpclient.execute(httppost);
		String data = new BasicResponseHandler().handleResponse(response);

//		while (i < len) {
		//Matcher tableMatcher = tableTagfind.matcher(data);
		//if(tableMatcher.find()) {
		//	String table = tableMatcher.group(1).replaceAll("\\s", ""); 
		
		Matcher matcher = aTagfind.matcher(data);
		while (matcher.find()) {
//		if (matcher != null)
//			j = matcher.start();
//		else
//			j = len;
//		
//		if (i < j){
//			Log.i("iii",data.substring(i, j));
//			i = j;
//		}
			 String href = matcher.group(1).replaceAll("\\s", "");        // (2)
			 keys.add(href);
			// String text = matcher.group(2).replaceAll("\\s", "");
//		boolean result1 = matcher.find();
//
//		while (result1) {
//
//		matcher.appendReplacement(sb, "");
//
//		result1 = matcher.find();
//
//		}
//
//		matcher.appendTail(sb);
			// Log.i(href,"");
		}
		//}
		}catch(Exception e){
			Log.i("eee",e.toString());	
			return null;
		}
		
		return keys;
	}
}
