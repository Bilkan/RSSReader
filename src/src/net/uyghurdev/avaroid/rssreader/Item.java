package net.uyghurdev.avaroid.rssreader;

public class Item {
	
	private String title;
	private String link;
	private String description;
	private String author;
	private String imgurl;
	private String pubDate;
	
	public Item(){}

	public void setTitle(String title){
		this.title = title;
		if (this.title == null)
				this.title = "";
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setLink(String url){
		this.link = url;
		if(this.link == null)
			this.link = "";
	}
	
	public String getLink(){
		return link;
	}
	
	public void setDescription(String description){
		this.description = description;
		if(this.description == null)
			this.description = "";
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setAuthor(String author){
		this.author = author; 
		if(this.author == null)
			this.author = "";
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setImageUrl(String imgurl){
		this.imgurl = imgurl;
		if(this.imgurl == null)
			this.imgurl = "";
	}
	
	public String getImageUrl(){
		return imgurl;
	}
	
	public void setPubDate(String pubDate){
		this.pubDate = pubDate;
		if(this.pubDate == null)
			this.pubDate = "";
	}
	
	public String getPubDate(){
		return pubDate;
	}
	
	
}
