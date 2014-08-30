package net.uyghurdev.avaroid.rssreader.data;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

public class DataHelper_ {



	

	OpenHelper openHelper;


	public DataHelper_(Context context) {
//		this.context = context;
////		if (!checkdatabase()) {
////			try {
////				copydatabase();
////			} catch (IOException e) {
////				// TODO Auto-generated catch block
////				Log.d("Copy Data", e.toString());
////				e.printStackTrace();
////			}
////		}
//		openHelper = new OpenHelper(this.context);
		
	}
}
	

//	private boolean checkdatabase() {
//		// SQLiteDatabase checkdb = null;
//		boolean checkdb = false;
//		try {
//			String myPath = DB_PATH + DATABASE_NAME;
//			File dbfile = new File(myPath);
//			// checkdb =
//			// SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
//			checkdb = dbfile.exists();
//
//		} catch (SQLiteException e) {
//			System.out.println("Database doesn't exist");
//		}
//
//		return checkdb;
//	}
//
//	private void copydatabase() throws IOException {
//
//		// Open your local db as the input stream
//		InputStream myinput = context.getAssets().open(DATABASE_NAME);
//
//		// Path to the just created empty db
//		String outfilename = DB_PATH + DATABASE_NAME;
//		File data = new File(DB_PATH);
//		data.mkdirs();
//		// Open the empty db as the output stream
//		OutputStream myoutput = new FileOutputStream(outfilename);
//
//		// transfer byte to inputfile to outputfile
//		byte[] buffer = new byte[1024];
//		int length;
//		while ((length = myinput.read(buffer)) > 0) {
//			myoutput.write(buffer, 0, length);
//		}
//
//		// Close the streams
//		myoutput.flush();
//		myoutput.close();
//		myinput.close();
//
//	}






	
