package com.eventerzgz.view.share;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import com.eventerzgz.view.R;

import java.util.ArrayList;
import java.util.List;

public class SocialShare {

	/**
	 * Muestra un di�logo para compartir contenido en WhatsApp, Twitter o Facebook
	 * @param context
	 * @param url String que contiene una URL por ejemplo https://google.com, si no es una URL en Facebook no funcionar�
	 */
	public static void share (Context context, String url){
	    List<Intent> targetShareIntents=new ArrayList<Intent>();
	    Intent shareIntent=new Intent();
	    shareIntent.setAction(Intent.ACTION_SEND);
	    shareIntent.setType("text/plain");
	    List<ResolveInfo> resInfos=context.getPackageManager().queryIntentActivities(shareIntent, 0);
	    if(!resInfos.isEmpty()){
	        System.out.println("Have package");
	        for(ResolveInfo resInfo : resInfos){
	            String packageName=resInfo.activityInfo.packageName;
	            Log.i("Package Name", packageName);
//	            if(packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("whatsapp")){
	                Intent intent=new Intent();
	                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
	                intent.setAction(Intent.ACTION_SEND);
	                intent.setType("text/plain");
	                intent.putExtra(Intent.EXTRA_TEXT, url);
	                intent.setPackage(packageName);
	                targetShareIntents.add(intent);
//	            }
	        }
	        if(!targetShareIntents.isEmpty()){
	            System.out.println("Have Intent");
	            Intent chooserIntent=Intent.createChooser(targetShareIntents.remove(0), context.getString(R.string.compartircon));
	            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
	            context.startActivity(chooserIntent);
	        }else{
//	            System.out.println("Do not Have Intent");
	        }
	    }
	}
	/**
	 * Compartir contenido en Facebook
	 * @param context
	 * @param url String que contiene una URL por ejemplo https://google.com, si no es una URL no funcionar�
	 */
	public static void shareFacebook (Context context, String url){
		//String urlToShare = "http://stackoverflow.com/questions/7545254";
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		// intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
		intent.putExtra(Intent.EXTRA_TEXT, url);

		// See if official Facebook app is found
		boolean facebookAppFound = false;
		List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
		for (ResolveInfo info : matches) {
			if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
				intent.setPackage(info.activityInfo.packageName);
				facebookAppFound = true;
				break;
			}
		}

		// As fallback, launch sharer.php in a browser
		if (!facebookAppFound) {
			String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + url;
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
		}
		try{
			context.startActivity(intent);	
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(context, "Facebook no instalado", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * Compartir texto en WhatsApp
	 * @param context
	 * @param text Cualquier cadena de texto
	 */
	public static void shareWhatsapp (Context context, String text){
		try{
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, text);
			sendIntent.setType("text/plain");
			sendIntent.setPackage("com.whatsapp");
			context.startActivity(sendIntent);
		}
		catch (Exception e){
			e.printStackTrace();
			Toast.makeText(context, "WhatsApp no instalado", Toast.LENGTH_SHORT).show();
		}

	}
	/**
	 * Compartir texto en Twitter
	 * @param context
	 * @param text Cualquier String
	 */
	public static void shareTwitter (Context context, String text){
		try
		{
			List<Intent> targetedShareIntents = new ArrayList<Intent>();
			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("text/plain");
			List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(share, 0);
			if (!resInfo.isEmpty()){
				for (ResolveInfo info : resInfo) {
					Intent targetedShare = new Intent(Intent.ACTION_SEND);
					targetedShare.setType("text/plain"); // put here your mime type
					if (info.activityInfo.packageName.toLowerCase().contains("twitter") || info.activityInfo.name.toLowerCase().contains("twitter")) {
						targetedShare.putExtra(Intent.EXTRA_SUBJECT, "Twitter");
						targetedShare.putExtra(Intent.EXTRA_TEXT,text);
						targetedShare.setPackage(info.activityInfo.packageName);
						targetedShareIntents.add(targetedShare);
					}
				}
				Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
				chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
				context.startActivity(chooserIntent);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(context, "Twitter no instalado", Toast.LENGTH_SHORT).show();
		}
	}
}
