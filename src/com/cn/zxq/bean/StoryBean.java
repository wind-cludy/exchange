package com.cn.zxq.bean;

public class StoryBean implements IHeadlerBean{
	private String storyId;
	private String title;
	private String category;
	private String  market;
	private String currency;
	private String date;
	private String time;
	private String fulltext ;
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFulltext() {
		return fulltext;
	}
	public void setFulltext(String fulltext) {
		this.fulltext = fulltext;
	}
}
