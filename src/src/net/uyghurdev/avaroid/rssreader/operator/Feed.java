package net.uyghurdev.avaroid.rssreader.operator;

public class Feed {

	
	int id;
	public String title;
	String url;
	int newItemCount;
	int page;
	int itemnumber;
	
	public int getItemnumber() {
		return itemnumber;
	}

	public void setItemnumber(int itemnumber) {
		this.itemnumber = itemnumber;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Feed(){}
	
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
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setNewItemCout(int newCount){
		this.newItemCount = newCount;
	}
	
	public int getNewItemCount(){
		return newItemCount;
	}
	
}
