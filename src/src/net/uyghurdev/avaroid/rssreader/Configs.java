package net.uyghurdev.avaroid.rssreader;


public class Configs {
	
	public static final String SugFeedServer = "http://localhost/"; // add your server address here;
	
	public static final String ShareServer = "http://localhost/"; // add your server address here;

	public static boolean StartService = false;

	public static boolean ServiceStarted = false;

	public static int Minute;

	public static int Hour;

	public static int UpdateRatio;
	
	public static int ShowCount = 20;
	public static String FeedUrl = null;
	public static int Network = 0;
	protected static int IdIndex;
	public static int ToAddFeedNumOfFeed=0;
	public static int ToItemListFeedId=0;
	public static String ToItemListFeedTitle="";
	public static String ToItemListFeedUrl="";
	public static int ToItemListFeedItemCount=0;
	public static int FeedCount=0;
	public static int FeedId;
	public static boolean NewsMobile2G;
	public static boolean NewsMobile3G;
	public static boolean NewsWIFI;
	public static boolean PictureMobile2G;
	public static boolean PictureMobile3G;
	public static boolean PictureWIFI;
	public static String Model;
	public static String Version;
	public static String Language;
	public static String FeedTitle;
	public static int ItemId;
	public static int[] ItemIds;
	public static String AppName;

	public static Item SingleItem;

	public static int FontSize;

	protected static int ItemListScroll = 0;
	protected static int FeedListScroll = 0;
	
	public static Integer tryParse(String text) {
		  try {
		    return new Integer(text);
		  } catch (NumberFormatException e) {
		    return 10;
		  }
		}

	public static boolean ShowPicture() {
		// TODO Auto-generated method stub
		boolean b = false;
		switch (Network) {

		case 0:
			break;
		case 1:
			if (PictureWIFI)
				b = true;
			break;
		case 2:
			if (PictureMobile3G)
				b = true;
			break;
		case 3:
			if (PictureMobile2G)
				b = true;
			break;

		}
		return b;
	}
}
