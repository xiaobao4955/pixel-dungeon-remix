package com.nyrds.pixeldungeon.ml;

import android.support.multidex.MultiDexApplication;

//@ReportsCrashes(mailTo = "nyrdsofficial@gmail.com", mode = ReportingInteractionMode.TOAST, resToastText = R.string.RemixedPixelDungeonApp_sendCrash)
public class RemixedPixelDungeonApp extends MultiDexApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		if(!BuildConfig.DEBUG) {
			//ACRA.init(this);
		}

		try {
			Class.forName("android.os.AsyncTask");
		} catch (Throwable ignore) {
		}
	}
}
