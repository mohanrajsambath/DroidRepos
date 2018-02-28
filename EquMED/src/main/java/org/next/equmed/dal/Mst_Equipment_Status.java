
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
public class Mst_Equipment_Status extends DataAccessLayer {


    /**
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @author – Mohanraj.S
     * @version – 0.1
     * @Updated_By Mohanraj.S
     * @Updated_On 03-12-2015 at 10:10:31 am
     * @description The following constructor have to recieve the values from which the activity requested to access this table values.
     * @see – no link
     * @since – 03-12-2015 at 10:10:31 am
     * @deprecated - no deprecation
     */

    public Mst_Equipment_Status( Context mCtx ) {
        super( mCtx );

    }

    /**
     * @param – str_eq_id,str_eq_name,str_isactive,str_sync_status,str_created_by,str_created_on,str_modified_by,str_modified_on
     * @return – return values of insertion status
     * @throws – no exception throws
     * @Type long return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 10:48:31 am
     * @Updated_By
     * @Updated_On
     * @Description insert_Equipment_Status_Enroll have to insert the Status of the Equipment
     */

    public long insert_Equipment_Status_Enroll( String str_eq_id,
                                                String str_eq_name,
                                                String str_flag,
                                                String str_isactive,
                                                String str_isstandard,
                                                String str_sync_status,
                                                String str_created_by,
                                                String str_created_on,
                                                String str_modified_by,
                                                String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.EQ_ID, str_eq_id );
        mCVArgs.put( BusinessAccessLayer.EQ_NAME, str_eq_name );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.ISACTIVE, str_isactive );
        mCVArgs.put( BusinessAccessLayer.IS_STANDARD, str_isstandard );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);


        return BusinessAccessLayer.mDb.insert( BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS, null,
                mCVArgs );

    }

    /**
     * @param – str_eq_id,str_eq_name,str_isactive,str_sync_status,str_created_by,str_created_on,str_modified_by,str_modified_on
     * @return – return values of insertion status
     * @throws – no exception throws
     * @Type long return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 10:48:31 am
     * @Updated_By
     * @Updated_On
     * @Description insert_Equipment_Status_Enroll have to insert the Status of the Equipment
     */

    public boolean update_Equipment_Status_Enroll( String str_eq_id,
                                                   String str_eq_name, String str_flag,
                                                   String str_isactive,
                                                   String str_isstandard,
                                                   String str_sync_status,
                                                   String str_created_by,
                                                   String str_created_on,
                                                   String str_modified_by,
                                                   String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.EQ_NAME, str_eq_name );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.ISACTIVE, str_isactive );
        mCVArgs.put( BusinessAccessLayer.IS_STANDARD, str_isstandard );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_ON, str_modified_on );

        String updateQuery = "" + BusinessAccessLayer.EQ_ID
                + " = '" + str_eq_id + "'";

        return BusinessAccessLayer.mDb.update( BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS,
                mCVArgs, updateQuery, null ) > 0;

    }

    /**
     * @param – str_Eq_Id
     * @return – Equipment Status
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 10:55:51 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByEq_Id( String str_Eq_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where "
                + BusinessAccessLayer.EQ_ID + " = '" + str_Eq_Id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByEq_name( String eq_name ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where "
                + BusinessAccessLayer.EQ_NAME + " = '" + eq_name + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByEq_nameupdate( String eq_name, String eq_id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where "
                + BusinessAccessLayer.EQ_NAME + " = '" + eq_name + "' and "+BusinessAccessLayer.EQ_ID+"!='"+eq_id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_Eq_Id
     * @return – Equipment Status
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 10:55:51 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByEq_IdStatus( String str_Eq_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where "
                + BusinessAccessLayer.EQ_ID + " = '" + str_Eq_Id + "' and  " + BusinessAccessLayer.ISACTIVE + " = 'Y';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
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
     * @Created_On 03-12-2015 at 10:58:53 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */

    public Cursor fetchByActive() throws SQLException {
        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where " + BusinessAccessLayer.ISACTIVE + " = 'Y';";


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
     * @Created_On 03-12-2015 at 10:58:53 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */

    public Cursor fetch() throws SQLException {
        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + ";";
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
     * @Created_On 03-12-2015 at 11:03:08 am
     * @Updated_By
     * @Updated_On
     * @Description return status of deleted values
     */
    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = BusinessAccessLayer.mDb.rawQuery( "Delete FROM "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS, null );
        if ( mCursorDeleteAll != null ) {
            mCursorDeleteAll.moveToFirst();
        }
        return mCursorDeleteAll;
    }

    /**
     * @param – str_Eq_Id
     * @return – the Deleted Value Status in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:03:08 am
     * @Updated_By
     * @Updated_On
     * @Description return deleted equipment  based on the eqid
     */
    public Cursor deleteBy_Eq_Id( String str_Eq_Id ) throws SQLException {

        String deleteByEq_Id = "DELETE FROM "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " WHERE "
                + BusinessAccessLayer.EQ_ID + " = '" + str_Eq_Id + "';";
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery( deleteByEq_Id, null );
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
     * @Created_On 03-12-2015 at 11:11:46 am
     * @Updated_By
     * @Updated_On
     * @Description The Values have return based on the sync status
     */

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_Eq_Id
     * @return – update database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:14:26 am
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void updateSyncStatus( String str_Eq_Id ) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.EQ_ID + " = '" + str_Eq_Id
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
    public void deleteFlagActiveStatus( String str_hosId ) throws SQLException {


        String updateQuery = "Update " + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " set " + BusinessAccessLayer.FLAG + " = '2' and " + BusinessAccessLayer.ISACTIVE + " = 'N' where id = '" + BusinessAccessLayer.EQ_ID + "';";

        System.out.println( "updateQuery:" + updateQuery );
        Cursor mCursor = BusinessAccessLayer.mDb.rawQuery( updateQuery, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

}
