package com.cn.zxq.xmlparser.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import com.cn.zxq.bean.HeadLineBean;

public class HeanLinesHandler extends AbstractHandler<HeadLineBean> {

	private List<HeadLineBean> lists;

	public HeanLinesHandler() {
		lists = new ArrayList<HeadLineBean>();
	}

	@Override
	public void generateXml(String localName, Attributes atts) {

		if (localName.equals("news")) {
			String id = atts.getValue(0);
			String storyId = atts.getValue(1);
			String operation = atts.getValue(2);
			HeadLineBean bean = new HeadLineBean();
			bean.setId(id);
			bean.setStoryId(storyId);
			bean.setOperation(operation);
			lists.add(bean);
		}
	}
	@Override
	public  void abstractEndElement(String localName,StringBuilder builder){
		
	}

	public List<HeadLineBean> getResult() {
		return lists;
	}

}
