package com.cn.zxq.xmlparser.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.cn.zxq.bean.StoryBean;

public class StoryHandler extends AbstractHandler<StoryBean> {

	private List<StoryBean> lists;

	
	public StoryHandler(){
		lists = new ArrayList<StoryBean>();
	}
	@Override
	public void generateXml(String localName, Attributes atts) {
		
			//bStory = true;
			String storyId = atts.getValue(0);
			StoryBean storyBean = new StoryBean();
			storyBean.setStoryId(storyId);
			lists.add(storyBean);
	
	}

	public List<StoryBean> getResult() {

		return lists;
	}
	@Override
	public void abstractEndElement(String localName, StringBuilder builder) {
		StoryBean storyBean = lists.get(0);
		if (localName.equals("title")) {
			storyBean.setTitle(builder.toString());
		} else if (localName.equals("category")) {
			storyBean.setCategory(builder.toString());
		} else if (localName.equals("market")) {
			storyBean.setMarket(builder.toString());
		} else if (localName.equals("currency")) {
			storyBean.setCurrency(builder.toString());
		} else if (localName.equals("date")) {
			storyBean.setDate(builder.toString());
		} else if (localName.equals("time")) {
			storyBean.setTime(builder.toString());
		} else if (localName.equals("fulltext")) {
			storyBean.setFulltext(builder.toString());
		}
	}

}
