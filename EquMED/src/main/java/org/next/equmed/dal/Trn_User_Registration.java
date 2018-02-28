/* Copyright (C) Next Techno Entreprises, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Mohanraj.S <mohanraj.sambath@nexttechnosolutions.com>, December,2015
 */
package org.next.equmed.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.next.equmed.bal.BusinessAccessLayer;

/**
 * Created by nextmoveo-1 on 3/12/15.
 */
public class Trn_User_Registration extends DataAccessLayer {
    /**
     * @param ctx
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:00:03 pm
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public Trn_User_Registration( Context ctx ) {
        super( ctx );
    }

    /**
     * @param – str_user_id,str_user_email,str_user_first_name,
     *          str_user_last_name,str_user_phoneno,
     *          str_user_password,str_user_image,str_user_admin,
     *          str_user_hospital,str_user_effect_startdate,
     *          str_user_effect_enddate,str_isactive,str_sync_status,
     *          str_created_by,str_created_on,str_modified_by,str_modified_on
     * @return – return values of insertion status
     * @throws – no exception throws
     * @Type long return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:08:27 pm
     * @Updated_By
     * @Updated_On
     * @Description DB_TABLE_TRN_USER_REGISTRATION have to insert the Status of the Equipment
     */


    public long insert_User_Registration( String str_user_id,
                                          String str_user_email,
                                          String str_user_first_name,
                                          String str_user_last_name,
                                          String str_user_phoneno,
                                          String str_user_password,
                                          String str_user_image,
                                          String str_user_admin,
                                          String str_isactive,

                                          String str_user_hospital,
                                          String str_user_effect_startdate,
                                          String str_user_effect_enddate,
                                          String str_flag,

                                          String str_sync_status,
                                          String str_created_by,
                                          String str_created_on,
                                          String str_modified_by,
                                          String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.USER_ID, str_user_id );
        mCVArgs.put( BusinessAccessLayer.USER_EMAIL, str_user_email );
        mCVArgs.put( BusinessAccessLayer.USER_FIRST_NAME, str_user_first_name );
        mCVArgs.put( BusinessAccessLayer.USER_LAST_NAME, str_user_last_name );
        mCVArgs.put( BusinessAccessLayer.USER_PHONENO, str_user_phoneno );
        mCVArgs.put( BusinessAccessLayer.USER_PASSWORD, str_user_password );
        mCVArgs.put( BusinessAccessLayer.USER_IMAGE, str_user_image );
        mCVArgs.put( BusinessAccessLayer.USER_ADMIN, str_user_admin );
        mCVArgs.put( BusinessAccessLayer.ISACTIVE, str_isactive );
        mCVArgs.put( BusinessAccessLayer.USER_HOSPITAL, str_user_hospital );
        mCVArgs.put( BusinessAccessLayer.USER_EFFECT_STARTDATE, str_user_effect_startdate );
        mCVArgs.put( BusinessAccessLayer.USER_EFFECT_ENDDATE, str_user_effect_enddate );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_ON, str_modified_on );


        return BusinessAccessLayer.mDb.insert( BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION, null,
                mCVArgs );

    }


    /**
     * @Type Multiple Argument Method
     * @Created_By Aravindhakumar.S
     * @Created_On 20-07-2015  4pm
     * @Updated_By
     * @Updated_On
     * @Description update_user_credential  Method contains the Data Parameters for
     * update  values to the table
     */

    public boolean update_user_credential( String str_user_id,
                                           String str_user_email,
                                           String str_user_first_name,
                                           String str_user_last_name,
                                           String str_user_phoneno,
                                           String str_user_password,
                                           String str_user_image,
                                           String str_user_admin,
                                           String str_isactive,

                                           String str_user_hospital,
                                           String str_user_effect_startdate,
                                           String str_user_effect_enddate,
                                           String str_flag,

                                           String str_sync_status,
                                           String str_created_by,
                                           String str_created_on,
                                           String str_modified_by,
                                           String str_modified_on ) {
        ContentValues mCVArgs = new ContentValues();


        mCVArgs.put( BusinessAccessLayer.USER_FIRST_NAME, str_user_first_name );
        mCVArgs.put( BusinessAccessLayer.USER_LAST_NAME, str_user_last_name );
        mCVArgs.put( BusinessAccessLayer.USER_PHONENO, str_user_phoneno );
        mCVArgs.put( BusinessAccessLayer.USER_PASSWORD, str_user_password );
        mCVArgs.put( BusinessAccessLayer.USER_IMAGE, str_user_image );
        mCVArgs.put( BusinessAccessLayer.USER_ADMIN, str_user_admin );
        mCVArgs.put( BusinessAccessLayer.ISACTIVE, str_isactive );
        mCVArgs.put( BusinessAccessLayer.USER_HOSPITAL, str_user_hospital );
        mCVArgs.put( BusinessAccessLayer.USER_EFFECT_STARTDATE, str_user_effect_startdate );
        mCVArgs.put( BusinessAccessLayer.USER_EFFECT_ENDDATE, str_user_effect_enddate );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_ON, str_modified_on );


        String UpdateUser = "" + BusinessAccessLayer.USER_ID + " = '" + str_user_id
                + "' and " + BusinessAccessLayer.USER_EMAIL + " = '" + str_user_email + "'";

        return BusinessAccessLayer.mDb.update( BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION, mCVArgs,
                UpdateUser, null ) > 0;

    }

    /**
     * @param – str_user_id
     * @return – Hospital Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:12:07 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByUser_Id( String str_user_id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " where "
                + BusinessAccessLayer.USER_ID + " = '" + str_user_id + "' and  " + BusinessAccessLayer.ISACTIVE + " = 'Y';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_user_id
     * @return – Hospital Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:12:07 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByUser_Name( String str_user_name ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " where "
                + BusinessAccessLayer.USER_EMAIL + " = '" + str_user_name + "' and  " + BusinessAccessLayer.ISACTIVE + " = 'Y';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @Type Single Argument Method
     * @Created_By Aarvindhakumar.S
     * @Created_On 20-07-2015
     * @Updated_By
     * @Updated_On
     * @Description fetch Method contains DataReterive from the Table by user
     * name
     */
    public Cursor fetchByUserNamePassword( String userNAME, String userPASSWORD )
            throws SQLException {

        String fetchByOffenceCode = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " where "
                + BusinessAccessLayer.USER_EMAIL + " = '" + userNAME + "' and "
                + BusinessAccessLayer.USER_PASSWORD + " = '" + userPASSWORD + "' and  " + BusinessAccessLayer.ISACTIVE + " = 'Y';";
        System.out.println("fetchByOffenceCode" +fetchByOffenceCode );
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery( fetchByOffenceCode, null );
        if ( mCursorById != null ) {
            mCursorById.moveToFirst();
        }
        return mCursorById;
    }

    /**
     * @param – no params
     * @return – All the Values in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:14:09 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetch() throws SQLException {

        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " ;";

        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( fetchQuery, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchimage(String str_user_id) throws SQLException {

        String fetchQuery = "Select "+BusinessAccessLayer.USER_IMAGE+" from " + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " where "+BusinessAccessLayer.USER_ID + " = '" + str_user_id + "' ;";
        System.out.println("fffff:"+fetchQuery);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( fetchQuery, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – no params
     * @return – All the Values in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:14:09 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetchByActive() throws SQLException {

        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " where " + BusinessAccessLayer.ISACTIVE + " = 'Y';";

        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( fetchQuery, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – no params
     * @return – All the Deleted Value Status in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:14:42 pm
     * @Updated_By
     * @Updated_On
     * @Description return status of deleted values
     */
    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = BusinessAccessLayer.mDb.rawQuery( "Delete FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION, null );
        if ( mCursorDeleteAll != null ) {
            mCursorDeleteAll.moveToFirst();
        }
        return mCursorDeleteAll;
    }

    /**
     * @param – str_hospital_id
     * @return – the Deleted Value Status in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:16:07 pm
     * @Updated_By
     * @Updated_On
     * @Description return deleted Hospital  based on the hospital_id
     */
    public Cursor deleteBy_User_Id( String str_user_id, String str_user_email ) throws SQLException {

        String deleteByUser_Id = "DELETE FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " WHERE "
                + BusinessAccessLayer.USER_ID + " = '" + str_user_id
                + "' and " + BusinessAccessLayer.USER_EMAIL + " = '" + str_user_email + "';";
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery( deleteByUser_Id, null );
        if ( mCursorById != null ) {
            mCursorById.moveToFirst();
        }
        return mCursorById;

    }

    /**
     * @param – no params
     * @return – the database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:16:32 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return based on the sync status
     */

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_user_id
     * @return – update database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 12:18:36 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void updateSyncStatus( String str_user_id ) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.USER_ID + " = '" + str_user_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery( syncStatus_query, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }


    /**
     * @param – str_flag
     * @return – update database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Venkatakrishnan.K
     * @Created_On 09-12-2015 at 10:00:20 am
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void deleteFlagActiveStatus( String str_UserId ) throws SQLException {


        String updateQuery = "Update " + BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION + " set " + BusinessAccessLayer.FLAG + " = '2'," + BusinessAccessLayer.ISACTIVE + " = 'N' where " + BusinessAccessLayer.USER_ID + " = '" + str_UserId + "';";


        System.out.println( "updateQuery:" + updateQuery );
        Cursor mCursor = BusinessAccessLayer.mDb.rawQuery( updateQuery, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

}
