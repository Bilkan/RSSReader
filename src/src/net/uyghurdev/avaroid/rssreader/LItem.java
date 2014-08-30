package net.uyghurdev.avaroid.rssreader;

public class LItem {

	private int id;
	private String title;
	private int newItem;
	
	public LItem(){}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setNewItem(int newItem){
		this.newItem = newItem;
	}
	
	public int getNewItem(){
		return newItem;
	}
	
}
