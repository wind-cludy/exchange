package com.cn.axq.dlg;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cn.zxq.R;

public class AboutDialog {

	private static final String TAG = "AboutDialog";	
	
	public static Dialog create(final Activity activity) {
		
		Log.d(TAG, "AboutDialog");
		
		LayoutInflater factory = LayoutInflater.from(activity);
		final View dialog_contnt = factory.inflate(R.layout.about, null);		
		
		WebView webView=(WebView)dialog_contnt.findViewById(R.id.webview);   
		webView.setBackgroundColor(0);
		webView.loadUrl("file:///android_asset/about_us.html");
		
		return new AlertDialog.Builder(activity).setIcon(R.drawable.about)
		.setView(dialog_contnt)
		.setTitle("Currencies")
		.setPositiveButton("Rating",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					activity.startActivity( new Intent( Intent.ACTION_VIEW,
					           Uri.parse("market://details?id=com.ideatec.itexplorer") ) );
				}
		})
		
//		.setNegativeButton(activity.getString(R.string.done), 
//				new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//            	dialog.dismiss();
//            }
//        })
		
		.setNeutralButton("More Apps",
		new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				activity.startActivity( new Intent( Intent.ACTION_VIEW,
				           Uri.parse("market://search?q=pub:\"Ideatec Team\"")));

			}
		}).create();

	}
	
    private class InsideWebViewClient extends WebViewClient {  
        @Override  
        public boolean shouldOverrideUrlLoading(WebView view, String url) {  
            view.loadUrl(url);  
            return true;  
        }  
    } 
}
