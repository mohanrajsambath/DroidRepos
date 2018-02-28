package org.next.equmed.uil;

import org.next.equmed.R;
import org.next.equmed.bal.BusinessAccessLayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashActivity extends Activity {


    String pathAssets = "data/data/org.next.equmed/databases/";

    String dbName = "EquMED.db";

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

//        DatabaseHelper dbHelper = new DatabaseHelper( this );
//        try {
//            dbHelper.createDatabase();
//        } catch ( IOException e ) {
//            e.printStackTrace();
//        }

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent( SplashActivity.this,
                        LoginActivity.class );
                startActivity( intent );
                finish();

            }

        }, 3000 );

    }

    /**
     * @author Aravindhakumar
     * @created On 13-06-2015
     * @description : To copy Db from assets folder
     **/

    public class DatabaseHelper extends SQLiteOpenHelper {

        private SQLiteDatabase myDataBase;
        private final Context myContext;

        public DatabaseHelper( Context context ) {
            super( context, dbName, null, 1 );
            this.myContext = context;
        }

        public void createDatabase() throws IOException {
            boolean vtVarMi = isDatabaseExist();

            if ( !vtVarMi ) {
                this.getReadableDatabase();

                try {
                    copyDataBase();
                } catch ( IOException e ) {
                    System.out.println( "e message:" + e.getMessage() );
                    throw new Error( "Error copying database" );
                }
            }
        }

        private void copyDataBase() throws IOException {

            // Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open( dbName );

            // Path to the just created empty db
            String outFileName = pathAssets + dbName;

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

        boolean isDatabaseExist() {

            File dbFile = new File( pathAssets + dbName );
            return dbFile.exists();

        }

        public void openDataBase() throws SQLException {

            // Open the database
            String myPath = pathAssets + dbName;
            myDataBase = SQLiteDatabase.openDatabase( myPath, null,
                    SQLiteDatabase.OPEN_READWRITE );

        }

        public Cursor Sample_use_of_helper() {

            return myDataBase.query( "TABLE_NAME", null, null, null, null, null,
                    null );
        }

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
//    /**
//     * @Type Method
//     * @Created_By Mohanraj.S
//     * @Created_On 15-07-2015
//     * @Updated_By
//     * @Updated_On
//     * @Description: To Create the database
//     */
//
//    public class DatabaseHelper extends SQLiteOpenHelper {
//
//        private SQLiteDatabase myDataBase;
//        private final Context myContext;
//
//        public DatabaseHelper( Context context ) {
//            super( context, BusinessAccessLayer.DATABASE_NAME, null,
//                    BusinessAccessLayer.DATABASE_VERSION );
//            this.myContext = context;
//        }
//
//        public void createDatabase() throws IOException {
//            boolean vtVarMi = isDatabaseExist();
//
//            if ( !vtVarMi ) {
//                this.getReadableDatabase();
//
//                try {
//                    copyDataBase();
//                    //Test for Export the Database  on 07-08-2015
//
//                } catch ( IOException e ) {
//                    System.out.println( "e.getMessage:" + e.getMessage() );
//                    throw new Error( "Error copying database" );
//                }
//            }
//        }
//
//        /**
//         * @Type Method
//         * @Created_By Mohanraj.S
//         * @Created_On 15-07-2015
//         * @Updated_By
//         * @Updated_On
//         * @Description: To copy Db from assets folder
//         */
//        private void copyDataBase() throws IOException {
//
//            // Open your local db as the input stream
//            InputStream myInput = myContext.getAssets().open(
//                    BusinessAccessLayer.DATABASE_NAME );
//
//            // Path to the just created empty db
//            String outFileName = BusinessAccessLayer.DATABASE_PATH
//                    + BusinessAccessLayer.DATABASE_NAME;
//
//            System.out.println("outFileName"+outFileName);
//
//            // Open the empty db as the output stream
//            OutputStream myOutput = new FileOutputStream( outFileName );
//
//            // transfer bytes from the inputfile to the outputfile
//            byte[] buffer = new byte[ 1024 ];
//            int length;
//            while ( ( length = myInput.read( buffer ) ) > 0 ) {
//                myOutput.write( buffer, 0, length );
//            }
//
//            // Close the streams
//            myOutput.flush();
//            myOutput.close();
//            myInput.close();
//        }
//
//        /**
//         * @Type Method
//         * @Created_By Mohanraj.S
//         * @Created_On 15-07-2015
//         * @Updated_By
//         * @Updated_On
//         * @Description: To Validate the Database isExists in the Device or Not
//         */
//
//        boolean isDatabaseExist() {
//
//            File dbFile = new File( BusinessAccessLayer.DATABASE_PATH
//                    + BusinessAccessLayer.DATABASE_NAME );
//            return dbFile.exists();
//
//        }
//
//        /**
//         * @Type Method
//         * @Created_By Mohanraj.S
//         * @Created_On 15-07-2015
//         * @Updated_By
//         * @Updated_On
//         * @Description: To Validate the Database isExists in the Device or Not
//         */
//        public void openDataBase() throws SQLException {
//
//            // Open the database
//            String myPath = BusinessAccessLayer.DATABASE_PATH
//                    + BusinessAccessLayer.DATABASE_NAME;
//            myDataBase = SQLiteDatabase.openDatabase( myPath, null,
//                    SQLiteDatabase.OPEN_READWRITE );
//
//        }
//
//        public Cursor Sample_use_of_helper() {
//
//            return myDataBase.query( "TABLE_NAME", null, null, null, null, null,
//                    null );
//        }
//
//        /**
//         * @Type Method
//         * @Created_By Mohanraj.S
//         * @Created_On 15-07-2015
//         * @Updated_By
//         * @Updated_On
//         * @Description: To close the Database whether the database is Opened
//         */
//        @Override
//        public synchronized void close() {
//            if ( myDataBase != null )
//                myDataBase.close();
//            super.close();
//        }
//
//        @Override
//        public void onCreate( SQLiteDatabase db ) {
//        }
//
//        @Override
//        public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
//        }
//    }


}