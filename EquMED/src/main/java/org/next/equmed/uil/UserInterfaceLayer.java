package org.next.equmed.uil;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.CustomProgressDialog;
import org.next.equmed.dal.Trn_User_Registration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class UserInterfaceLayer extends Fragment {

    protected static float screenHeight, screenWidth;
    protected float density;
    protected boolean isPortrait;
    protected boolean isLandScape;
    protected Typeface tfOpenSans_Light, tfOpenSans_Regular,
            tfOpenSans_Semibold, tfOpenSans_Bold;
    public boolean isInternetPresent = false;
    private float ht_px;
    private float wt_px;
    int size;
    protected Typeface tfOpensans;
    protected DisplayMetrics outMetrics;
    public double diagonalInches;
    private CustomProgressDialog cst_Progress;
    private String imeistring;
    private String substringtime;
    protected String todaytime;
    boolean isValuesExists;
    String reponseMasterDatStr;

    private String filepath = "Database";

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        getUnixTimeStamp();
        getDisplayDetails();
        getDisplayDetailsPixel();
        getDeviceDetails();

        if ( getScreenWidth() > getScreenHeight() ) {
            isPortrait = false;
            isLandScape = true;
        } else if ( getScreenHeight() > getScreenWidth() ) {
            isPortrait = true;
            isLandScape = false;
        }
        /**
         * To Copy the Database from the Assert folder into Device*/

        /*DatabaseHelper dbHelper = new DatabaseHelper( this );
        try {
            dbHelper.createDatabase();
        } catch ( IOException e ) {
            e.printStackTrace();
        }*/

        /**
         * To Assign the Custom Font to the TextView and EditText in the Project*/

       /* tfOpenSans_Light = Typeface.createFromAsset( this.getAssets(),
                "fonts/OpenSans-Light.ttf" );
        tfOpenSans_Regular = Typeface.createFromAsset( this.getAssets(),
                "fonts/OpenSans-Regular.ttf" );
        tfOpenSans_Semibold = Typeface.createFromAsset( this.getAssets(),
                "fonts/OpenSans-Semibold.ttf" );
        tfOpenSans_Bold = Typeface.createFromAsset( this.getAssets(),
                "fonts/OpenSans-Bold.ttf" );*/

        TelephonyManager tm = ( TelephonyManager ) getActivity().getSystemService( Context.TELEPHONY_SERVICE );

		/*
         * getDeviceId() function Returns the unique device ID. for example,the
		 * IMEI for GSM and the MEID or ESN for CDMA phones.
		 */

        imeistring = tm.getDeviceId();
        setIMEIno();

        //callAsynchronousTask_Timer();
    }

    /**
     * @CreatedBy
     * @CreatedOn 05-Aug-2015,11:16:48 am,
     * @UpdatedBy
     * @UpdatedOn 05-Aug-2015,11:16:48 am,
     * @Description To get Unix_Time_Stamp for the Server Accessibility
     */
    public String getUnixTimeStamp() {

        try {
            Calendar cal = new GregorianCalendar();
            int unix_timestamp = (int) ((cal.getTimeInMillis() + cal
                    .getTimeZone().getOffset(cal.getTimeInMillis())) / 1000);
            System.out.println("Time from" + unix_timestamp);
            substringtime = Integer.toString(unix_timestamp);
//            todaytime = substringtime.substring(0, 5);
            todaytime = substringtime.substring(Math.max(substringtime.length() - 5, 0));
            System.out.println("Unix_Time ----=>" + todaytime);
        } catch (Exception e) {
            System.out.println(e);
        }
        return todaytime;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To return the IMEI_NO for the global acess
     */

    public String setIMEIno() {
//         String m_IMEINO = imeistring;
        String imeinolast = imeistring.substring(Math.max(imeistring.length() - 5, 0));
        return imeinolast;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To get screenHeight and screenWidth by the getWindowManager
     */

    protected void getDisplayDetails() {

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        screenHeight = outMetrics.heightPixels;
        screenWidth = outMetrics.widthPixels;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To getDisplayDetailsPixel of height and width based on the
     * Display Metrics
     */
    public void getDisplayDetailsPixel() {
        ht_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                screenHeight, getResources().getDisplayMetrics());
        wt_px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                screenWidth, getResources().getDisplayMetrics());
    }

    /**
     * @Type Method
     * @Created_By Gokul raj
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To display the custom alert dialog
     */

    public static void alert(final Context ctx, final String message, int i) {

        CustomAlertDialog cdd = new CustomAlertDialog(ctx, message, i);
        cdd.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        cdd.show();
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To Calculate the Component Size based on the ratio of
     * device pixel calculation
     */
    public void getDeviceDetails() {
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;

        float widthDpi = outMetrics.xdpi;
        float heightDpi = outMetrics.ydpi;

        float widthInches = widthPixels / widthDpi;
        float heightInches = heightPixels / heightDpi;

        diagonalInches = Math.sqrt( ( widthInches * widthInches )
                + ( heightInches * heightInches ) );
        System.out.println("screen resoulution------->" + diagonalInches);

        if ( diagonalInches == 1 ) {
            System.out.println( "1 inch" );
        } else if ( diagonalInches == 2 ) {
            System.out.println( "2 inch" );
        } else if ( diagonalInches == 3 ) {
            System.out.println( "3 inch" );
        } else if ( diagonalInches == 4 ) {
            System.out.println( "4 inch" );
        } else if ( diagonalInches == 5 ) {
            System.out.println( "5 inch" );
        } else if ( diagonalInches > 5.5 && diagonalInches < 6.5 ) {
            System.out.println( "6 inch" );
        } else if ( diagonalInches > 6 && diagonalInches < 7.5 ) {
            System.out.println( "7 inch" );
        } else if ( diagonalInches > 7.5 && diagonalInches < 8.5 ) {
            System.out.println( "8 inch" );
        } else if ( diagonalInches == 9 ) {
            System.out.println( "9 inch" );
        } else if ( diagonalInches == 10 ) {
            System.out.println( "10 inch" );
        }
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To set textSize Based on the returning value from the
     * actual screen Size
     */
    @SuppressWarnings( "static-access" )
    public int setTextPixel( TextView view, int value ) {
        /*
		 * if (getResources().getDisplayMetrics().densityDpi ==
		 * getResources().getDisplayMetrics().Den) { size = value;
		 * view.setTextSize(size); }else
		 */
        if ( getResources().getDisplayMetrics().densityDpi == getResources()
                .getDisplayMetrics().DENSITY_DEFAULT ) {
            // Lava alps-ivorys
            // micro-micromax p280- funbook
            size = value;
            view.setTextSize(size);
        } else if ( getResources().getDisplayMetrics().densityDpi == getResources()
                .getDisplayMetrics().DENSITY_HIGH ) {
            // micromax-a106
            size = value - 4;
            view.setTextSize(size);
        } else if ( getResources().getDisplayMetrics().densityDpi == getResources()
                .getDisplayMetrics().DENSITY_LOW ) {
            size = value;
            view.setTextSize(size);
        } else if ( getResources().getDisplayMetrics().densityDpi == getResources()
                .getDisplayMetrics().DENSITY_MEDIUM ) {
            size = value;
            view.setTextSize(size);
        } else if ( getResources().getDisplayMetrics().densityDpi == getResources()
                .getDisplayMetrics().DENSITY_XHIGH ) {
            // asus-nexus(7 inch),moto-g,samsung note-2
			/*
			 * if (isInternetPresent) { }
			 */
            if ( diagonalInches > 9 ) {
                size = value - 3;
            } else {
                size = value;
            }
            view.setTextSize(size);
        }

        return size;

    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To set Screen Height
     */
    protected float getScreenHeight() {
        return screenHeight;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To set Screen Width
     */
    protected float getScreenWidth() {
        return screenWidth;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To return Screen Height by the Pixel Value
     */
    public double getScreenHeightPixel() {
        return ht_px;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To return Screen Width by the Pixel Value
     */
    public double getScreenWidthPixel() {
        return wt_px;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To get Screen Width by the Percentage Value
     */
    protected double getScreenWidthPercentage( double d ) {

        int result = ( int ) ( ( d * screenWidth ) / 100 );
        return result;

    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_Byte
     * @Updated_On
     * @Description: To get Screen Height by the Percentage Value
     */
    protected double getScreenHeightPercentage( double d ) {
        int result = ( int ) ( ( d * screenHeight ) / 100 );
        return result;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To get device Width by the Percentage Value
     */
    public int getWidthByPercentagePixel( double k ) {
        int result = ( int ) ( ( k * wt_px ) / 100 );
        return result;

    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To get device Height by the Pixel Value
     */
    public int getHeightByPercentagePixel( double d ) {

        int result = ( int ) ( ( d * ht_px ) / 100 );
        return result;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To get device Width by the Percentage Value
     */
    public int getWidthByPercentage( double i ) {

        int result = ( int ) ( ( i * screenWidth ) / 100 );
        return result;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To get device Height by the Percentage Value
     */
    public static int getHeightByPercentage( double d ) {

        int result = ( int ) ( ( d * screenHeight ) / 100 );
        return result;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To decode the Image
     */
    @SuppressWarnings( "unused" )
    protected Bitmap downSample( int value ) {
        BitmapFactory.Options bitmap = new BitmapFactory.Options();
        bitmap.inSampleSize = 2;
        return BitmapFactory.decodeResource( getResources(), value, bitmap );
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To show the TIME to the user based on the am and pm
     */
    @SuppressWarnings( "unused" )
    protected void updateTime( int hours, int mins ) {

        String timeSet = "";
        if ( hours > 12 ) {
            hours -= 12;
            timeSet = "PM";
        } else if ( hours == 0 ) {
            hours += 12;
            timeSet = "AM";
        } else if ( hours == 12 )
            timeSet = "PM";
        else
            timeSet = "AM";
        String minutes = "";
        if ( mins < 10 )
            minutes = "0" + mins;
        else
            minutes = String.valueOf( mins );

        // Append in a StringBuilder

        String aTime = new StringBuilder().append( hours ).append( ':' )
                .append( minutes ).append( " " ).append( timeSet ).toString();

        // sp_time.setText(aTime);
    }

    /**
     * @Subject getShowDate method
     * @Description This method Show current date
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     */

    protected String getShowDate(String dateStr) {
        String mytime = dateStr;
        // create a date "formatter" (the date format we want)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // create a date "formatter" (the date format we want)
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yyyy",
                Locale.getDefault());
        // create a new String using the date format we want
        String finalDate = timeFormat.format(myDate);

        System.out.println(finalDate);
        return finalDate;

    }

    /**
     * @Subject getShowDate method
     * @Description This method Show current date
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     */

    protected String getShowDateWithTime(String dateTimeStr) {
        String mytime = dateTimeStr;
        // create a date "formatter" (the date format we want)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // create a date "formatter" (the date format we want)
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",
                Locale.getDefault());
        // create a new String using the date format we want
        String finalDate = timeFormat.format(myDate);

        System.out.println(finalDate);
        return finalDate;

    }

    /**
     * @Subject getShowDate method
     * @Description This method Show current date
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     */

    protected String getDefaultDate(String dateStr) {
        String mytime = dateStr;
        // create a date "formatter" (the date format we want)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy",
                Locale.getDefault());
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // create a date "formatter" (the date format we want)
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        // create a new String using the date format we want
        String finalDate = timeFormat.format(myDate);

        String finalDateWithTimeZero = finalDate + " 00:00:00";
        System.out.println("finalDateWithTimeZero:;" + finalDateWithTimeZero);
        return finalDateWithTimeZero;

    }

    protected String getDefaultDateWithTime(String dateTimeStr) {
        String mytime = dateTimeStr;
        // create a date "formatter" (the date format we want)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",
                Locale.getDefault());
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // create a date "formatter" (the date format we want)
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        // create a new String using the date format we want
        String finalDate = timeFormat.format(myDate);

        String finalDateWithTimeZero = finalDate + " 00:00:00";
        System.out.println("finalDateWithTimeZero:;" + finalDateWithTimeZero);
        return finalDateWithTimeZero;

    }

    protected String getDefaultDateTime(String dateTimeStr) {
        String mytime = dateTimeStr;
        // create a date "formatter" (the date format we want)
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",
                Locale.getDefault());
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // create a date "formatter" (the date format we want)
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        // create a new String using the date format we want
        String finalDate = timeFormat.format(myDate);

        String finalDateWithTimeZero = finalDate ;
        System.out.println("finalDateWithTimeZero:;" + finalDateWithTimeZero);
        return finalDateWithTimeZero;

    }
    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To show the custom Progress Dialog
     */
    public void showProgressDialog(Context context, String title, String msg) {
		/*
		 * cst_Progress = CustomProgressDialog.show(context, "", true, false,
		 * null);
		 */
        cst_Progress = CustomProgressDialog.show(context, title, msg, true,
                false, null);
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To dismiss the custom progress dialog
     */
    public void dismissDialog() {
        if (cst_Progress != null)
            if (cst_Progress.isShowing()) {
                cst_Progress.dismiss();
                cst_Progress = null;
            }
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To Show the Toast msg to enitre application
     */
    protected void showToast(Context context, String strMsg) {
        Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
    }// End of showToast

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To Create the database
     */

    public class DatabaseHelper extends SQLiteOpenHelper {

        private SQLiteDatabase myDataBase;
        private final Context myContext;

        public DatabaseHelper(Context context) {
            super(context, BusinessAccessLayer.DATABASE_NAME, null,
                    BusinessAccessLayer.DATABASE_VERSION);
            this.myContext = context;
        }

        public void createDatabase() throws IOException {
            boolean vtVarMi = isDatabaseExist();

            if ( !vtVarMi ) {
                this.getReadableDatabase();

                try {
                    copyDataBase();
                    //Test for Export the Database  on 07-08-2015
                    exportDatabase_A( BusinessAccessLayer.DATABASE_NAME );

                } catch ( IOException e ) {
                    System.out.println( "e.getMessage:" + e.getMessage() );
                    throw new Error( "Error copying database" );
                }
            }
        }

        /**
         * @Type Method
         * @Created_By Mohanraj.S
         * @Created_On 15-07-2015
         * @Updated_By
         * @Updated_On
         * @Description: To copy Db from assets folder
         */
        private void copyDataBase() throws IOException {

            // Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(
                    BusinessAccessLayer.DATABASE_NAME );

            // Path to the just created empty db
            String outFileName = BusinessAccessLayer.DATABASE_PATH
                    + BusinessAccessLayer.DATABASE_NAME;

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream( outFileName );

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[ 1024 ];
            int length;
            while ( ( length = myInput.read( buffer ) ) > 0 ) {
                myOutput.write( buffer, 0, length );
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }

        /**
         * @Type Method
         * @Created_By Mohanraj.S
         * @Created_On 15-07-2015
         * @Updated_By
         * @Updated_On
         * @Description: To Validate the Database isExists in the Device or Not
         */

        boolean isDatabaseExist() {

            File dbFile = new File( BusinessAccessLayer.DATABASE_PATH
                    + BusinessAccessLayer.DATABASE_NAME );
            return dbFile.exists();

        }

        /**
         * @Type Method
         * @Created_By Mohanraj.S
         * @Created_On 15-07-2015
         * @Updated_By
         * @Updated_On
         * @Description: To Validate the Database isExists in the Device or Not
         */
        public void openDataBase() throws SQLException {

            // Open the database
            String myPath = BusinessAccessLayer.DATABASE_PATH
                    + BusinessAccessLayer.DATABASE_NAME;
            myDataBase = SQLiteDatabase.openDatabase( myPath, null,
                    SQLiteDatabase.OPEN_READWRITE );

        }

        public Cursor Sample_use_of_helper() {

            return myDataBase.query( "TABLE_NAME", null, null, null, null, null,
                    null );
        }

        /**
         * @Type Method
         * @Created_By Mohanraj.S
         * @Created_On 15-07-2015
         * @Updated_By
         * @Updated_On
         * @Description: To close the Database whether the database is Opened
         */
        @Override
        public synchronized void close() {
            if ( myDataBase != null )
                myDataBase.close();
            super.close();
        }

        @Override
        public void onCreate( SQLiteDatabase db ) {
        }

        @Override
        public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        }
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-05-2015
     * @Updated_On 20-05-2015
     * @Description To Get the Current Date and Time of the Current Device
     */

    protected String getCurrentDate() {
        Date d = new Date();
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(d.getTime());
        return formattedDate;
    }

    protected String getCurrentDateTime() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(d.getTime());

        return formattedDate;
    }
    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-05-2015
     * @Updated_On 20-05-2015
     * @Description To Get the Current Date and Time of the Current Device
     */

    protected String getCurrentDateWithTime() {
        Date d = new Date();
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(d.getTime());
        return formattedDate;
    }


    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description to export database to the user needed location
     */

    protected void exportDatabase_A( String databaseName ) {
        try {

            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if ( sd.canWrite() ) {
                String currentDBPath = "//data//" + getActivity().getPackageName()
                        + "//databases//" + databaseName + "";

                String backupDBPath = BusinessAccessLayer.DATABASE_NAME;

                File currentDB = new File( data, currentDBPath );

                // create a File object for the parent directory
                File dbDirectory = new File( Environment.getExternalStorageDirectory() + "/" + BusinessAccessLayer.EXPORT_DB_PATH + "/" );
                // have the object build the directory structure, if needed.
                dbDirectory.mkdirs();
                // create a File object for the output file
                File outputFile = new File( dbDirectory, backupDBPath );
                // now attach the OutputStream to the file object, instead of a String representation
                FileOutputStream fos = new FileOutputStream( outputFile );

                if ( currentDB.exists() ) {
                    FileChannel src = new FileInputStream( currentDB )
                            .getChannel();

                    FileChannel dst = new FileOutputStream( outputFile )
                            .getChannel();
                    dst.transferFrom( src, 0, src.size() );
                    src.close();
                    dst.close();
                }
            }
        } catch ( Exception e ) {

        }
    }


    public void exportDatabse(String databaseName) {
        try {

            BusinessAccessLayer.myExternalFile = new File(getActivity().getExternalFilesDir(filepath), BusinessAccessLayer.DATABASE_NAME);
            System.out.println( "directory myExternalFile   " + BusinessAccessLayer.myExternalFile );
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getActivity().getPackageName()
                        + "//databases//" + databaseName + "";

                String backupDBPath = BusinessAccessLayer.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB)
                            .getChannel();
                    FileChannel dst = new FileOutputStream( BusinessAccessLayer.myExternalFile)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }

    public void exportFile(String databaseName) {
        try {

            BusinessAccessLayer.myExternalFile = new File(getActivity().getExternalFilesDir(filepath), BusinessAccessLayer.DATABASE_NAME);
            System.out.println( "directory myExternalFile   " + BusinessAccessLayer.myExternalFile );
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getActivity().getPackageName()
                        + "//databases//" + databaseName + "";

                String backupDBPath = BusinessAccessLayer.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB)
                            .getChannel();
                    FileChannel dst = new FileOutputStream( BusinessAccessLayer.myExternalFile)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
    }

    protected void generateNoteOnSD( String sFileName, String sBody ) {
        try {
            File root = new File( Environment.getExternalStorageDirectory() + "/" + BusinessAccessLayer.EXPORT_DB_PATH + "/", "Notes" );
            if ( !root.exists() ) {
                root.mkdirs();
            }
            File gpxfile = new File( root, sFileName );
            FileWriter writer = new FileWriter( gpxfile );
            writer.append( sBody );
            writer.flush();
            writer.close();
            //  showToast(this, "Notes Saved");
        } catch ( IOException e ) {
            e.printStackTrace();

        }
    }


//    /**
//     * @Type ClickEvent
//     * @Created_By Mohanraj.S
//     * @Created_On 13-07-2015
//     * @Updated_By
//     * @Updated_On
//     * @Description To Hide the KeyBoard to the Entire Project
//     */
//    public void hideKeyBoard() {
//        InputMethodManager imm = ( InputMethodManager ) getSystemService( Context.INPUT_METHOD_SERVICE );
//        imm.hideSoftInputFromWindow(
//                getWindow().getDecorView().getWindowToken(), 0 );
//    }
//
//    public boolean onTouchEvent( MotionEvent event ) {
//        View view = getActivity().getCurrentFocus();
//        if ( view != null ) {
//            InputMethodManager inputManager = ( InputMethodManager ) getActivity()
//                    .getSystemService( Context.INPUT_METHOD_SERVICE );
//            inputManager.hideSoftInputFromWindow( view.getWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS );
//        }
//        return true;
//    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 14-07-2015
     * @Updated_By
     * @Updated_On
     * @Description To Encode The Bitmap Image into String Format to Store into
     * Database
     */
    public static String encodeTobase64( Bitmap image ) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress( Bitmap.CompressFormat.JPEG, 100, baos );
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e( "LOOK", imageEncoded );
        return imageEncoded;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 14-07-2015
     * @Updated_By
     * @Updated_On
     * @Description To Decode The String into Bitmap Image Format to retrieve
     * from Database
     */
    public static Bitmap decodeBase64( String input ) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray( decodedByte, 0, decodedByte.length );
    }

    /**
     * Checking device has camera hardware or not
     */
    protected boolean isDeviceSupportCamera() {
        if ( getActivity().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA) ) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To Validate the Email-Id entered by the user
     */

    public boolean isValidEmail( String email ) {
        // Email validation Pattern String
        final Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile( "[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+" );

        if ( EMAIL_ADDRESS_PATTERN.matcher(email.trim()).matches() ) {
            return false;
        }
        return true;
    }




    public String getUserNameByUserId(String userId) {

        String user_name = "";
        Trn_User_Registration trn_userAdapt = new Trn_User_Registration(getActivity());
        trn_userAdapt.open();
        Cursor mCr_mst_hospital_enroll = trn_userAdapt.fetchByUser_Id(userId);

        if (mCr_mst_hospital_enroll.getCount() > 0) {
            for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                mCr_mst_hospital_enroll.moveToPosition(i);


                String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.USER_ID + ""));

               String  user_first_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.USER_FIRST_NAME + ""));

                String  user_last_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.USER_LAST_NAME + ""));

                user_name=user_first_name+" "+user_last_name;
            }
            mCr_mst_hospital_enroll.close();

        }

        trn_userAdapt.close();
        Trn_User_Registration.close();

        return user_name;


    }
    protected void CropImage(Uri picUri) {
      /*  try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(picUri, "image*//*");

            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("aspectX", 5);
            intent.putExtra("aspectY", 3);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, BusinessAccessLayer.CROP_IMAGE);

        } catch (ActivityNotFoundException e) {
            //  Common.showToast(this, "Your device doesn't support the crop action!");
        }*/

        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(picUri, "image/*");

            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("aspectX", 5);
            intent.putExtra("aspectY", 3);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", true);

            List list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
            int size = list.size();
            File outPutFile = new File(Environment.getExternalStorageDirectory(), "temp_equmed.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {

                Intent i = new Intent(intent);
                ResolveInfo res = (ResolveInfo) list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, BusinessAccessLayer.CROP_IMAGE);
            } else {

                startActivityForResult(intent, BusinessAccessLayer.CROP_IMAGE);
            }

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
// Common.showToast(this, "Your device doesn't support the crop action!");
        }
    }


    protected String getShowDateWithOutTime(String dateTimeStr) {
        String mytime = dateTimeStr;
// create a date "formatter" (the date format we want)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        Date myDate = null;
        try {
            myDate = dateFormat.parse(mytime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
// create a date "formatter" (the date format we want)
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MMM-yyyy",
                Locale.getDefault());
// create a new String using the date format we want
        String finalDate = timeFormat.format(myDate);

        System.out.println(finalDate);
        return finalDate;

    }

}
