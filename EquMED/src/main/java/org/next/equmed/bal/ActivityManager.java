package org.next.equmed.bal;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class ActivityManager extends FragmentActivity {

	protected float screenWidth;
	protected float screenHeight;
	protected float density;
	protected boolean isPortrait;
	protected boolean isLandScape;
	protected Typeface tfMonoType,tfHelvitica,tfLora,tfLoraBold;
	public boolean isInternetPresent = false;
	private float ht_px;
	private float wt_px;
	int size;
	protected Typeface tfOpensans;

	protected DisplayMetrics outMetrics;
	public double diagonalInches; 
	String companyName;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		getDisplayDetails();
		getDisplayDetailsPixel();
		getDeviceDetails();


		isInternetPresent = getconnection().isConnectingToInternet();
		/*tfMonoType = Typeface.createFromAsset(this.getAssets(), "fonts/MonotypeCorsiva.ttf");
		tfOpensans = Typeface.createFromAsset(this.getAssets(), "fonts/OpenSans-Regular.ttf");
		tfHelvitica=Typeface.createFromAsset(this.getAssets(), "fonts/Helvetica_Reg.ttf");
		tfLora = Typeface.createFromAsset(this.getAssets(), "fonts/Lora-Regular.ttf");
		tfLoraBold = Typeface.createFromAsset(this.getAssets(), "fonts/Lora-Bold.ttf");*/

		
		
		companyName = android.os.Build.BRAND;
		
		if (getScreenWidth()>getScreenHeight()) {
			isPortrait = false;
			isLandScape = true;
		}else if (getScreenHeight()>getScreenWidth()) {
			isPortrait = true;
			isLandScape = false;
		}

		
		
	} 

	protected void getDisplayDetails() { 

		Display display = getWindowManager().getDefaultDisplay();
		outMetrics = new DisplayMetrics ();
		display.getMetrics(outMetrics);
		screenHeight= outMetrics.heightPixels;
		screenWidth = outMetrics.widthPixels;
	}
	@SuppressWarnings("unused")
	protected void updateTime(int hours, int mins) {

		String timeSet = "";
		if (hours > 12) {
			hours -= 12;
			timeSet = "PM";
		} else if (hours == 0) {
			hours += 12;
			timeSet = "AM";
		} else if (hours == 12)
			timeSet = "PM";
		else
			timeSet = "AM";
		String minutes = "";
		if (mins < 10)
			minutes = "0" + mins;
		else
			minutes = String.valueOf(mins);

		// Append in a StringBuilder
		
		String aTime = new StringBuilder().append(hours).append(':')
				.append(minutes).append(" ").append(timeSet).toString();

		//		 sp_time.setText(aTime);
	}
	public void getDisplayDetailsPixel(){ 
		ht_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenHeight, getResources().getDisplayMetrics());
		wt_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenWidth, getResources().getDisplayMetrics());
	}

	public void getDeviceDetails(){
		int widthPixels = outMetrics.widthPixels;
		int heightPixels = outMetrics.heightPixels;

		float widthDpi = outMetrics.xdpi;
		float heightDpi = outMetrics.ydpi;

		float widthInches = widthPixels / widthDpi;
		float heightInches = heightPixels / heightDpi;

		diagonalInches = Math.sqrt(
				(widthInches * widthInches) 
				+ (heightInches * heightInches));
		System.out.println("screen resoulution------->"+diagonalInches);

		if (diagonalInches==1) {
			System.out.println("1 inch");
		}else if (diagonalInches==2) {
			System.out.println("2 inch");
		}else if (diagonalInches==3) {
			System.out.println("3 inch");
		}else if (diagonalInches==4) {
			System.out.println("4 inch");
		}else if (diagonalInches==5) {
			System.out.println("5 inch");
		}else if (diagonalInches >5.5 && diagonalInches<6.5) {
			System.out.println("6 inch");
		}else if (diagonalInches > 6 && diagonalInches <7.5 ) {
			System.out.println("7 inch");
		}else if (diagonalInches > 7.5 && diagonalInches < 8.5) {
			System.out.println("8 inch");
		}else if (diagonalInches ==9) {
			System.out.println("9 inch");
		}else if (diagonalInches==10) {
			System.out.println("10 inch");
		}
	}
	
	@SuppressWarnings("static-access")
	public int setTextPixel(TextView view,int value){
		/*if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().Den) {
		size = value;
		view.setTextSize(size);
	}else*/ if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_DEFAULT) {
		//Lava alps-ivorys
		//micro-micromax p280- funbook
		size = value;
		view.setTextSize(size);
	}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_HIGH) {
		//micromax-a106
		size = value-4;
		view.setTextSize(size);
	}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_LOW) {
		size = value;
		view.setTextSize(size);
	}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_MEDIUM) {
		size = value;
		view.setTextSize(size);
	}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_TV) {
		size = value;
		view.setTextSize(size);
	}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XHIGH) {
		//asus-nexus(7 inch),moto-g,samsung note-2
		/*if (isInternetPresent) {
		}*/
		if (diagonalInches > 9) {
			size = value-3;
		}else {
			size = value;
		}
		view.setTextSize(size);
	}
	else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
		//samsung galaxy s4.
		size = value-2;
		view.setTextSize(size);
	}/*else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
		size = value-3;
		view.setTextSize(size);
	}*/
	return size;

}
	protected float getScreenWidth(){
		return screenWidth;
	}

	public double getScreenHeightPixel(){
		return ht_px;
	}

	protected float getScreenHeight(){
		return screenHeight;
	}

	public double getScreenWidthPixel(){
		return wt_px;
	}

	protected double getScreenWidthPercentage(double d){

		int result = (int) ((d*screenWidth)/100); 
		return result;

	}

	public int getWidthByPercentagePixel(double k){
		int result = (int) ((k*wt_px)/ 100);
		return result;

	}

	protected double getScreenHeightPercentage(double d){   
		int result = (int) ((d*screenHeight)/100); 
		return result;
	}

	public int getHeightByPercentagePixel(double d) {

		int result = (int) ((d*ht_px)/ 100);
		return result;
	}

	protected Bitmap downSample(int value){
		BitmapFactory.Options bitmap = new BitmapFactory.Options();
		bitmap.inSampleSize = 2;
		return BitmapFactory.decodeResource(getResources(), value, bitmap); 
	}

	public int getWidthByPercentage(double i) {

		int result = (int) ((i*screenWidth)/ 100);
		return result;
	}

	public int getHeightByPercentage(double d) {

		int result = (int) ((d*screenHeight)/ 100);
		return result;
	}



	public ConnectionDetector getconnection()
	{
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		return cd;
	}

	public class ConnectionDetector {

		private Context _context;

		public ConnectionDetector(Context context){
			this._context = context;
		}

		public boolean isConnectingToInternet(){
			ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) 
			{
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null) 
					for (int i = 0; i < info.length; i++) 
						if (info[i].getState() == NetworkInfo.State.CONNECTED)
						{
							return true;
						}

			}
			return false;
		}
	}

	public void showMessage(String msg, String title) { 
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setPositiveButton("OK", null)
		.setTitle(title)
		.setCancelable(false).show();
		return;
	}



	@SuppressWarnings("static-access")
	public int setLeftPadding(TextView view,int value){
		if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_DEFAULT) {
			//Lava alps-ivorys
			//micro-micromax p280- funbook
			size = value;
			view.setPadding(size, 0, 0, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_HIGH) {
			//micromax-a106
			size = value;
			view.setPadding(size, 0, 0, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_LOW) {
			size = value;
			view.setPadding(size, 0, 0, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_MEDIUM) {
			size = value;
			view.setPadding(size, 0, 0, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_TV) {
			size = value;
			view.setPadding(size, 0, 0, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XHIGH) {
			//nexus(7 inch),moto-g,samsung note-2
			size = value;
			view.setPadding(size, 0, 0, 0);
		}
		else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			//samsung galaxy s4.
			size = value;
			view.setPadding(size, 0, 0, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			size = value;
			view.setPadding(size, 0, 0, 0);
		}
		return size;

	}

	@SuppressWarnings("static-access")
	public int setRightPadding(TextView view,int value){
		/*if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().Den) {
			size = value;
			view.setTextSize(size);
		}else*/ if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_DEFAULT) {
			//Lava alps-ivorys
			//micro-micromax p280- funbook
			size = value;
			view.setPadding(0, 0, size, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_HIGH) {
			//micromax-a106
			size = value;
			view.setPadding(0, 0, size, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_LOW) {
			size = value;
			view.setPadding(0, 0, size, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_MEDIUM) {
			size = value;
			view.setPadding(0, 0, size, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_TV) {
			size = value;
			view.setPadding(0, 0, size, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XHIGH) {
			//nexus(7 inch),moto-g,samsung note-2
			size = value;
			view.setPadding(0, 0, size, 0);
		}
		else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			//samsung galaxy s4.
			size = value;
			view.setPadding(0, 0, size, 0);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			size = value;
			view.setPadding(0, 0, size, 0);
		}
		return size;

	}

	@SuppressWarnings("static-access")
	public int setTopPadding(TextView view,int value){
		/*if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().Den) {
			size = value;
			view.setTextSize(size);
		}else*/ if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_DEFAULT) {
			//Lava alps-ivorys
			//micro-micromax p280- funbook
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_HIGH) {
			//micromax-a106
			size = value-4;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_LOW) {
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_MEDIUM) {
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_TV) {
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XHIGH) {
			//nexus(7 inch),moto-g,samsung note-2
			size = value;
			view.setTextSize(size);
		}
		else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			//samsung galaxy s4.
			size = value-2;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			size = value-3;
			view.setTextSize(size);
		}
		return size;

	}

	@SuppressWarnings("static-access")
	public int setBottomPadding(TextView view,int value){
		/*if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().Den) {
			size = value;
			view.setTextSize(size);
		}else*/ if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_DEFAULT) {
			//Lava alps-ivorys
			//micro-micromax p280- funbook
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_HIGH) {
			//micromax-a106
			size = value-4;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_LOW) {
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_MEDIUM) {
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_TV) {
			size = value;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XHIGH) {
			//nexus(7 inch),moto-g,samsung note-2
			size = value;
			view.setTextSize(size);
		}
		else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			//samsung galaxy s4.
//			size = value-2;
			view.setTextSize(size);
		}else if (getResources().getDisplayMetrics().densityDpi == getResources().getDisplayMetrics().DENSITY_XXHIGH) {
			size = value-3;
			view.setTextSize(size);
		}
		return size;

	}  
	

	

	


	

	


}
