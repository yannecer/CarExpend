package com.necer.carexpend;


import android.util.Log;

public class MyLog {

	private static boolean debug = true;

	public static void v( String msg) {
		if (debug) {
			Log.v("NECER", msg);
		}
	}

	public static void d(String msg) {
		if (debug) {
			Log.d("NECER", msg);
		}
	}

	public static void i(String msg) {
		if (debug) {
			Log.i("NECER", msg);
		}
	}

	public static void w( String msg) {
		if (debug) {
			Log.w("NECER", msg);
		}

	}

	public static void e( String msg) {
		if (debug) {
			Log.e("NECER", msg);
		}
	}
}
