
/* Copyright (C) Next Techno Entreprises, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Mohanraj.S <mohanraj.sambath@nexttechnosolutions.com>, December,2015
 */
package org.next.equmed.dal;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.next.equmed.bal.BusinessAccessLayer;

public class DataAccessLayer {
    /**
     * @Type static final String Variable
     * @Created_By Mohanraj.S
     * @Created_On 02-12-2015 at 05:20:10 pm
     * @Updated_By
     * @Updated_On
     * @Description TAG, DatabaseHelper Object,SQLiteDatabase Object,Database
     * name, Database Version no, Context object, Create
     * Query,DropQuery
     */
    protected static final String TAG = "EQUMED_DataAccessLayer";
    /*
     * public static DatabaseHelper mDbHelper; protected static SQLiteDatabase
     * mDb; public static String DATABASE_PATH =
     * "/data/data/org.next.equmed/databases/"; public static final String
     * DATABASE_NAME = "EquMED.db"; public static final int DATABASE_VERSION =
     * 1;
     */
    protected final Context mCtx;
    protected static final String CREATEQUERY = "CREATE TABLE IF NOT EXISTS ";
    protected static final String DROPQUERY = "DROP TABLE IF EXISTS";
    protected static final String UPDATEQUERY = "UPDATE";

    /**
     * @Type static final String Variable
     * @Created_By Mohanraj.S
     * @Created_On 02-12-2015 at 05:48:51 pm
     * @Updated_By
     * @Updated_On
     * @Description TRN_USER_REGISTRATION variable is contains the Table Creating
     * Query for the user Creation in database  Table
     */


    protected static final String TRN_USER_REGISTRATION = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + "("
            + BusinessAccessLayer.USER_ID + " TEXT,"
            + BusinessAccessLayer.USER_EMAIL + " TEXT,"
            + BusinessAccessLayer.USER_FIRST_NAME + " TEXT,"
            + BusinessAccessLayer.USER_LAST_NAME + " TEXT,"
            + BusinessAccessLayer.USER_PHONENO + " TEXT,"
            + BusinessAccessLayer.USER_PASSWORD + " TEXT,"
            + BusinessAccessLayer.USER_IMAGE + " TEXT,"
            + BusinessAccessLayer.USER_ADMIN + " TEXT,"
            + BusinessAccessLayer.ISACTIVE + " TEXT,"
            + BusinessAccessLayer.USER_HOSPITAL + " TEXT,"
            + BusinessAccessLayer.USER_EFFECT_STARTDATE + " TEXT,"
            + BusinessAccessLayer.USER_EFFECT_ENDDATE + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";

    /**
     * @Type static final String Variable
     * @Created_By Mohanraj.S
     * @Created_On 02-12-2015 at 05:54:25 pm
     * @Updated_By
     * @Updated_On
     * @Description MST_EQUIPMENT_STATUS variable is contains the Table Creating Query
     */
    protected static final String MST_EQUIPMENT_STATUS = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + "("
            + BusinessAccessLayer.EQ_ID + " TEXT,"
            + BusinessAccessLayer.EQ_NAME + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.ISACTIVE + " TEXT,"
            + BusinessAccessLayer.IS_STANDARD + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";
    /**
     * @Type static final String Variable
     * @Created_By Mohanraj.S
     * @Created_On 02-12-2015 at 06:04:35 pm
     * @Updated_By
     * @Updated_On
     * @Description MST_HOSPITAL_ENROLL variable is contains the Table
     * Creating Query
     */
    protected static final String MST_HOSPITAL_ENROLL = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + "("
            + BusinessAccessLayer.HOSPITAL_ID + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_NAME + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_LOCATION + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_DESC + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_ADDRESS1 + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_ADDRESS2 + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_ADDRESS3 + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_STATE + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_COUNTRY + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_PHNO1 + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_PHNO2 + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_EMAIL + " TEXT,"
            + BusinessAccessLayer.HOSPITAL_NOTES + " TEXT,"
            + BusinessAccessLayer.STANDARD_EQUIPMENTS + " TEXT,"
            + BusinessAccessLayer.ISACTIVE + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";

    /**
     * @Type static final String Variable
     * @Created_By Mohanraj.S
     * @Created_On 02-12-2015 at 06:04:35 pm
     * @Updated_By Mohanraj.S
     * @Updated_On 03-12-2015 at 09:06:52 am
     * @Description TRN_EQUIPMENT_ENROLL variable is contains the Table Creating Query
     */
    protected static final String TRN_EQUIPMENT_ENROLL = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + "("
            + BusinessAccessLayer.EQ_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.EQ_ID + " TEXT,"
            + BusinessAccessLayer.EQ_HOSPITAL_ID + " TEXT,"
            + BusinessAccessLayer.EQ_LOCATION_CODE + " TEXT,"
            + BusinessAccessLayer.GPS_COORDINATES + " TEXT,"
            + BusinessAccessLayer.EQ_SERIALNO + " TEXT,"
            + BusinessAccessLayer.EQ_MAKE + " TEXT,"
            + BusinessAccessLayer.EQ_MODEL + " TEXT,"
            + BusinessAccessLayer.EQ_INSTALL_DATE + " TEXT,"
            + BusinessAccessLayer.EQ_SERVICE_TAGNO + " TEXT,"
            + BusinessAccessLayer.EQ_NOTES + " TEXT,"
            + BusinessAccessLayer.EQ_EXTRA_ACCESSORIES + " TEXT,"
            + BusinessAccessLayer.EQ_INSTALL_STATUS + " TEXT,"
            + BusinessAccessLayer.EQ_INSTALL_NOTES + " TEXT,"
            + BusinessAccessLayer.EQ_WORKING_STATUS + " TEXT,"
            + BusinessAccessLayer.EQ_WORKING_NOTES + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


    /**
     * @Type static final String Variable
     * @Created_By Mohanraj.S
     * @Created_On 02-12-2015 at 06:04:35 pm
     * @Updated_By Mohanraj.S
     * @Updated_On 03-12-2015 at 09:06:52 am
     * @Description TRN_EQUIPMENT_ENROLL variable is contains the Table Creating Query
     */
    protected static final String TRN_EQUIPMENT_ENROLL_ACCESSORIES = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + "("
            + BusinessAccessLayer.EQ_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.EQ_ENROLL_UPS + " TEXT,"
            + BusinessAccessLayer.EQ_ENROLL_MANUAL + " TEXT,"
            + BusinessAccessLayer.EQ_ENROLL_STABILIZER + " TEXT,"
            + BusinessAccessLayer.EQ_ENROLL_OTHERS + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.ISACTIVE + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


    /**
     * @Type static final String Variable
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 09:13:03 am
     * @Updated_By
     * @Updated_On
     * @Description TRN_INSTALLATION_ENROLL variable is contains the Table Creating Query
     */

    protected static final String TRN_INSTALLATION_ENROLL = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + "("
            + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.INST_COMPANY_BY + " TEXT,"
            + BusinessAccessLayer.INST_ENGG_NAME + " TEXT,"
            + BusinessAccessLayer.INST_INSTALL_DATE + " TEXT,"
            + BusinessAccessLayer.INST_LOCATION + " TEXT,"
            + BusinessAccessLayer.INST_NEAR_BY_PHONENO + " TEXT,"
            + BusinessAccessLayer.INST_NOTES + " TEXT,"
            + BusinessAccessLayer.INST_AREA + " TEXT,"
            + BusinessAccessLayer.INST_OWNER_NAME + " TEXT,"
            + BusinessAccessLayer.INST_OWNER_PHONENO + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


    /**
     * @Type static final String Variable
     * @Created_By Aravindhakumar.S
     * @Created_On 08-12-2015 at 04:18:23 pm
     * @Updated_By
     * @Updated_On
     * @Description TRN_IMAGES variable is contains the Table Creating Query
     */
    protected static final String TRN_IMAGES = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + "("
            + BusinessAccessLayer.IMG_IMG_ID + " TEXT,"
            + BusinessAccessLayer.IMG_ID + " TEXT,"
            + BusinessAccessLayer.IMG_NAME + " TEXT,"
            + BusinessAccessLayer.IMG_ENCRYPTED_DATA + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


//    protected static final String TRN_IMAGES = CREATEQUERY
//            + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + "("
//            + BusinessAccessLayer.IMG_ID + " TEXT,"
//            + BusinessAccessLayer.IMG_NAME + " TEXT,"
//            + BusinessAccessLayer.IMG_ENCRYPTED_DATA + " TEXT,"
//            + BusinessAccessLayer.FLAG + " TEXT,"
//            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
//            + BusinessAccessLayer.CREATED_BY + " TEXT,"
//            + BusinessAccessLayer.CREATED_ON + " TEXT,"
//            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
//            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


    /**
     * @Type static final String Variable
     * @Created_By Aravindhakumar.S
     * @Created_On 08-12-2015 at 04:18:23 pm
     * @Updated_By
     * @Updated_On
     * @Description TRN_IMAGES variable is contains the Table Creating Query
     */

    protected static final String TRN_DOCUMENTS = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + "("
            + BusinessAccessLayer.DOC_DOC_ID + " TEXT,"
            + BusinessAccessLayer.DOC_ID + " TEXT,"
            + BusinessAccessLayer.DOC_NAME + " TEXT,"
            + BusinessAccessLayer.DOC_ENCRYPTED_DATA + " TEXT,"
            + BusinessAccessLayer.DOC_TYPE + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


    /**
     * @Type static final String Variable
     * @Created_By Muralidharan M
     * @Created_On 09-03-2016 at 09:20:15 am
     * @Updated_By
     * @Updated_On
     * @Description TRN_SERVICE_DETAILS variable is contains the Table Creating Query
     */

    protected static final String TRN_SERVICE_DETAILS = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + "("
            + BusinessAccessLayer.SERVICE_ID + " TEXT,"
            + BusinessAccessLayer.SERVICE_EQUIPMENT_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.SERVICE_HOSPITAL_ID + " TEXT,"
            + BusinessAccessLayer.SERVICE_EQUIPMENT_ID + " TEXT,"
            + BusinessAccessLayer.SERVICE_DATE_TIME + " TEXT,"
            + BusinessAccessLayer.SERVICE_DURATION + " TEXT,"
            + BusinessAccessLayer.SERVICE_TYPE + " TEXT,"
            + BusinessAccessLayer.SERVICE_NOTES + " TEXT,"
            + BusinessAccessLayer.SERVICE_APPROVED_BY + " TEXT,"
            + BusinessAccessLayer.SERVICE_INVOICE + " TEXT,"
            + BusinessAccessLayer.SERVICED_BY + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";

    /**
     * @Type static final String Variable
     * @Created_By Muralidharan M
     * @Created_On 09-03-2016 at 09:25:15 am
     * @Updated_By
     * @Updated_On
     * @Description TRN_WARRANTY_DETAILS variable is contains the Table Creating Query
     */

    protected static final String TRN_WARRANTY_DETAILS = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + "("
            + BusinessAccessLayer.WARRANTY_ID + " TEXT,"
            + BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.WARRANTY_HOSPITAL_ID + " TEXT,"
            + BusinessAccessLayer.WARRANTY_EQUIPMENT_ID + " TEXT,"
            + BusinessAccessLayer.WARRANTY_START_DATE + " TEXT,"
            + BusinessAccessLayer.WARRANTY_END_DATE + " TEXT,"
            + BusinessAccessLayer.WARRANTY_DURATION + " TEXT,"
            + BusinessAccessLayer.WARRANTY_DESCRIPTION + " TEXT,"
            + BusinessAccessLayer.WARRANTY_TYPE + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


    /**
     * @Type static final String Variable
     * @Created_By Muralidharan M
     * @Created_On 09-03-2016 at 09:27:15 am
     * @Updated_By
     * @Updated_On
     * @Description TRN_TRAINING_DETAILS variable is contains the Table Creating Query
     */

    protected static final String TRN_TRAINING_DETAILS = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + "("
            + BusinessAccessLayer.TRAINING_ID + " TEXT,"
            + BusinessAccessLayer.TRAINING_EQUIPMENT_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.TRAINING_HOSPITAL_ID + " TEXT,"
            + BusinessAccessLayer.TRAINING_EQUIPMENT_ID + " TEXT,"
            + BusinessAccessLayer.TRAINING_DATE + " TEXT,"
            + BusinessAccessLayer.TRAINING_DURATION + " TEXT,"
            + BusinessAccessLayer.TRAINING_DESCRIPTION + " TEXT,"
            + BusinessAccessLayer.TRAINING_PROVIDED_BY + " TEXT,"
            + BusinessAccessLayer.TRAINING_INVOICE + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";


    /**
     * @Type static final String Variable
     * @Created_By Muralidharan M
     * @Created_On 09-03-2016 at 09:29:15 am
     * @Updated_By
     * @Updated_On
     * @Description TRN_CONSUMABLES variable is contains the Table Creating Query
     */

    protected static final String TRN_CONSUMABLES = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + "("
            + BusinessAccessLayer.CONSUMABLES_ID + " TEXT,"
            + BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.CONSUMABLES_HOSPITAL_ID + " TEXT,"
            + BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ID + " TEXT,"
            + BusinessAccessLayer.NAME + " TEXT,"
            + BusinessAccessLayer.DESCRIPTION + " TEXT,"
            + BusinessAccessLayer.TYPE_OF_USAGE + " TEXT,"
            + BusinessAccessLayer.USAGE_PARAMETER + " TEXT,"
            + BusinessAccessLayer.QUANTITY + " TEXT,"
            + BusinessAccessLayer.CONSUMABLES_UOM + " TEXT,"
            + BusinessAccessLayer.CONSUMABLES_CURRENT_STOCK + " TEXT,"
            + BusinessAccessLayer.CONSUMABLE_NOTES + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";

    /**
     * @Type static final String Variable
     * @Created_By Muralidharan M
     * @Created_On 09-03-2016 at 09:29:15 am
     * @Updated_By
     * @Updated_On
     * @Description TRN_VOICE_OF_CUSTOMER variable is contains the Table Creating Query
     */

    protected static final String TRN_VOICE_OF_CUSTOMER = CREATEQUERY
            + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + "("
            + BusinessAccessLayer.VOC_ID + " TEXT,"
            + BusinessAccessLayer.VOC_EQUIPMENT_ENROLL_ID + " TEXT,"
            + BusinessAccessLayer.VOC_HOSPITAL_ID + " TEXT,"
            + BusinessAccessLayer.VOC_EQUIPMENT_ID + " TEXT,"
            + BusinessAccessLayer.TYPE + " TEXT,"
            + BusinessAccessLayer.IN_BRIEF + " TEXT,"
            + BusinessAccessLayer.FLAG + " TEXT,"
            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
            + BusinessAccessLayer.CREATED_BY + " TEXT,"
            + BusinessAccessLayer.CREATED_ON + " TEXT,"
            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";

//    protected static final String TRN_DOCUMENTS = CREATEQUERY
//            + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + "("
//            + BusinessAccessLayer.DOC_ID + " TEXT,"
//            + BusinessAccessLayer.DOC_NAME + " TEXT,"
//            + BusinessAccessLayer.DOC_ENCRYPTED_DATA + " TEXT,"
//            + BusinessAccessLayer.DOC_TYPE + " TEXT,"
//            + BusinessAccessLayer.FLAG + " TEXT,"
//            + BusinessAccessLayer.SYNC_STATUS + " TEXT,"
//            + BusinessAccessLayer.CREATED_BY + " TEXT,"
//            + BusinessAccessLayer.CREATED_ON + " TEXT,"
//            + BusinessAccessLayer.MODIFIED_BY + " TEXT,"
//            + BusinessAccessLayer.MODIFIED_ON + " TEXT);";

    /**
     * @Type Static Class
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 09:14:10 am
     * @Updated_By
     * @Updated_On
     * @Description DatabaseHelper class is inHerited from the SQLiteOpenHelper
     * to Create and Upgrade the Database
     */
    public static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * @Type Single Argument Constructor
         * @Created_By Mohanraj.S
         * @Created_On 03-12-2015 at 09:14:42 am
         * @Updated_By
         * @Updated_On
         * @Description Constructor to receive the which Context will be passed
         */
        DatabaseHelper(Context context) {
            super(context, BusinessAccessLayer.DATABASE_NAME, null, BusinessAccessLayer.DATABASE_VERSION);
        }

        /**
         * @Type Override Method
         * @Created_By Mohanraj.S
         * @Created_On 03-12-2015 at 09:15:35 am
         * @Updated_By
         * @Updated_On
         * @Description The onCreate() method is defined for create the database
         */
        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(TRN_USER_REGISTRATION);
            db.execSQL(MST_EQUIPMENT_STATUS);
            db.execSQL(MST_HOSPITAL_ENROLL);
            db.execSQL(TRN_EQUIPMENT_ENROLL);
            db.execSQL(TRN_EQUIPMENT_ENROLL_ACCESSORIES);
            db.execSQL(TRN_INSTALLATION_ENROLL);
            db.execSQL(TRN_IMAGES);
            db.execSQL(TRN_DOCUMENTS);
            db.execSQL(TRN_SERVICE_DETAILS);
            db.execSQL(TRN_WARRANTY_DETAILS);
            db.execSQL(TRN_CONSUMABLES);
            db.execSQL(TRN_TRAINING_DETAILS);
            db.execSQL(TRN_VOICE_OF_CUSTOMER);
        }

        /**
         * @Type Override Method
         * @Created_By Mohanraj.S
         * @Created_On 03-12-2015 at 09:17:59 am
         * @Updated_By
         * @Updated_On
         * @Description The onUpgrade() method is defined for upgrade the
         * database by the version name of Database
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(DROPQUERY + " " + TRN_USER_REGISTRATION);
            db.execSQL(DROPQUERY + " " + MST_EQUIPMENT_STATUS);
            db.execSQL(DROPQUERY + " " + MST_HOSPITAL_ENROLL);
            db.execSQL(DROPQUERY + " " + TRN_EQUIPMENT_ENROLL);
            db.execSQL(DROPQUERY + " " + TRN_EQUIPMENT_ENROLL_ACCESSORIES);
            db.execSQL(DROPQUERY + " " + TRN_INSTALLATION_ENROLL);
            db.execSQL(DROPQUERY + " " + TRN_IMAGES);
            db.execSQL(DROPQUERY + " " + TRN_DOCUMENTS);
            db.execSQL(DROPQUERY + " " + TRN_SERVICE_DETAILS);
            db.execSQL(DROPQUERY + " " + TRN_WARRANTY_DETAILS);
            db.execSQL(DROPQUERY + " " + TRN_CONSUMABLES);
            db.execSQL(DROPQUERY + " " + TRN_TRAINING_DETAILS);
            db.execSQL(DROPQUERY + " " + TRN_VOICE_OF_CUSTOMER);


        }

    }

    /**
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 09:18:16 am
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public DataAccessLayer(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 09:19:20 am
     * @Updated_By
     * @Updated_On
     * @Description The Open() method is defined with Database access in
     * writable mode in global access permission
     */

    public DataAccessLayer open() throws SQLException {
        BusinessAccessLayer.mDbHelper = new DatabaseHelper(mCtx);
        BusinessAccessLayer.mDb = BusinessAccessLayer.mDbHelper
                .getWritableDatabase();
        return this;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 09:19:52 am
     * @Updated_By
     * @Updated_On
     * @Description The Close() method is defined for close the database in
     * global access permission by using static
     */
    public static void close() {
        if (BusinessAccessLayer.mDb != null) {
            // mDbHelper.close();
            BusinessAccessLayer.mDb.close();
        }
    }
}
