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
public class Mst_Hospital_Enrollment extends DataAccessLayer {


    /**
     * @param ctx
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:24:26 am
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public Mst_Hospital_Enrollment( Context ctx ) {
        super( ctx );
    }


    /**
     * @param – str_hospital_id,  str_hospital_name,
     *          str_hospital_location,
     *          str_hospital_desc,  str_hospital_address1,
     *          str_hospital_address2,
     *          str_hospital_address3,
     *          str_hospital_state,
     *          str_hospital_country,
     *          str_hospital_phno1,
     *          str_hospital_phno2 ,
     *          str_hospital_email,
     *          str_hospital_notes,
     *          str_isactive,
     *          str_sync_status,
     *          str_created_by,
     *          str_created_on,
     *          str_modified_by,
     *          str_modified_on
     * @return – return values of insertion status
     * @throws – no exception throws
     * @Type long return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:47:06 am
     * @Updated_By
     * @Updated_On
     * @Description insert_MST_HOSPITAL_ENROLL have to insert the Status of the Equipment
     */

    public long insert_Hospital_Enroll( String str_hospital_id, String str_hospital_name,
                                        String str_hospital_location,
                                        String str_hospital_desc, String str_hospital_address1,
                                        String str_hospital_address2,
                                        String str_hospital_address3,
                                        String str_hospital_state,
                                        String str_hospital_country,
                                        String str_hospital_phno1,
                                        String str_hospital_phno2,
                                        String str_hospital_email,
                                        String str_hospital_notes,
                                        String str_isactive,
                                        String str_standard_equipments,
                                        String str_sync_status,
                                        String str_flag,
                                        String str_created_by,
                                        String str_created_on,
                                        String str_modified_by,
                                        String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_ID, str_hospital_id );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_NAME, str_hospital_name );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_LOCATION, str_hospital_location );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_DESC, str_hospital_desc );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_ADDRESS1, str_hospital_address1 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_ADDRESS2, str_hospital_address2 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_ADDRESS3, str_hospital_address3 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_STATE, str_hospital_state );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_COUNTRY, str_hospital_country );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_PHNO1, str_hospital_phno1 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_PHNO2, str_hospital_phno2 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_EMAIL, str_hospital_email );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_NOTES, str_hospital_notes );
        mCVArgs.put( BusinessAccessLayer.ISACTIVE, str_isactive );
        mCVArgs.put( BusinessAccessLayer.STANDARD_EQUIPMENTS, str_standard_equipments );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_ON, str_modified_on );

        return BusinessAccessLayer.mDb.insert( BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL, null,
                mCVArgs );

    }

    /**
     * @Type Multiple Argument Method
     * @Created_By Sathishkumar.R
     * @Created_On 15-12-2015
     * @Updated_By
     * @Updated_On
     * @Description update_Hospital_Details Method contains the Data Parameters for
     * update values to the table
     */
    public boolean update_Hospital_Details( String str_hospital_id, String str_hospital_name,
                                            String str_hospital_location,
                                            String str_hospital_desc, String str_hospital_address1,
                                            String str_hospital_address2,
                                            String str_hospital_address3,
                                            String str_hospital_state,
                                            String str_hospital_country,
                                            String str_hospital_phno1,
                                            String str_hospital_phno2,
                                            String str_hospital_email,
                                            String str_hospital_notes,
                                            String str_isactive,
                                            String str_standard_equipments,
                                            String str_sync_status,
                                            String str_flag,
                                            String str_created_by,
                                            String str_created_on,
                                            String str_modified_by,
                                            String str_modified_on ) {


        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_NAME, str_hospital_name );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_LOCATION, str_hospital_location );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_DESC, str_hospital_desc );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_ADDRESS1, str_hospital_address1 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_ADDRESS2, str_hospital_address2 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_ADDRESS3, str_hospital_address3 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_STATE, str_hospital_state );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_COUNTRY, str_hospital_country );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_PHNO1, str_hospital_phno1 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_PHNO2, str_hospital_phno2 );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_EMAIL, str_hospital_email );
        mCVArgs.put( BusinessAccessLayer.HOSPITAL_NOTES, str_hospital_notes );
        mCVArgs.put( BusinessAccessLayer.ISACTIVE, str_isactive );
        mCVArgs.put( BusinessAccessLayer.STANDARD_EQUIPMENTS, str_standard_equipments );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_ON, str_modified_on );


        String updateQuery = "" + BusinessAccessLayer.HOSPITAL_ID
                + " = '" + str_hospital_id + "'";

        return BusinessAccessLayer.mDb.update( BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL,
                mCVArgs, updateQuery, null ) > 0;
    }

    /**
     * @param – str_hospital_id
     * @return – Hospital Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:49:51 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByHospital_Id( String str_hospital_id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " where "
                + BusinessAccessLayer.HOSPITAL_ID + " = '" + str_hospital_id + "';";

        System.out.println( "Query: " + query );
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
     * @Created_On 03-12-2015 at 11:52:10 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetch() throws SQLException {
        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + ";";
        System.out.println( "Query: " + fetchQuery );


        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( fetchQuery, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchbynames(String hos_name, String loc_name) throws SQLException {
        String fetchQuery = "Select "+BusinessAccessLayer.HOSPITAL_ID+" from " + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " where "+BusinessAccessLayer.HOSPITAL_NAME+"='"+hos_name+"' and "+BusinessAccessLayer.HOSPITAL_LOCATION+"='"+loc_name+"';";
        System.out.println( "fetchbynames Query: " + fetchQuery );


        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( fetchQuery, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchbynamesupdate(String hos_name, String loc_name,String hos_id) throws SQLException {
        String fetchQuery = "Select "+BusinessAccessLayer.HOSPITAL_ID+" from " + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " where "+BusinessAccessLayer.HOSPITAL_NAME+"='"+hos_name+"' and "+BusinessAccessLayer.HOSPITAL_LOCATION+"='"+loc_name+"' and "+BusinessAccessLayer.HOSPITAL_ID+"!='"+hos_id+"';";
        System.out.println( "fetchbynamesupdate Query: " + fetchQuery );


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
     * @Created_On 03-12-2015 at 11:52:10 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetchByActive() throws SQLException {
        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " where " + BusinessAccessLayer.ISACTIVE + " = 'Y';";
        System.out.println( "Query: " + fetchQuery );


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
     * @Created_On 03-12-2015 at 11:52:10 am
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetchByHospital_ID_Active( String hosId ) throws SQLException {
        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " where " + BusinessAccessLayer.ISACTIVE + " = 'Y' and " + BusinessAccessLayer.HOSPITAL_ID + " = '" + hosId + "';";


        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( fetchQuery, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchStandard_Equipment() throws SQLException {
        String fetchQuery = "Select * from " + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where " + BusinessAccessLayer.ISACTIVE + " = 'Y' and " + BusinessAccessLayer.IS_STANDARD + " = 'Y';";

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
     * @Created_On 03-12-2015 at 11:53:41 am
     * @Updated_By
     * @Updated_On
     * @Description return status of deleted values
     */
    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = BusinessAccessLayer.mDb.rawQuery( "Delete FROM "
                + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL, null );
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
     * @Created_On 03-12-2015 at 11:55:48 am
     * @Updated_By
     * @Updated_On
     * @Description return deleted Hospital  based on the hospital_id
     */
    public Cursor deleteBy_Hospital_Id( String str_hospital_id ) throws SQLException {


        String deleteByHospital_Id = "DELETE FROM "
                + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " WHERE "
                + BusinessAccessLayer.HOSPITAL_ID + " = '" + str_hospital_id + "';";

        System.out.println( "delete query:" + deleteByHospital_Id );
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery( deleteByHospital_Id, null );
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
     * @Created_On 03-12-2015 at 11:56:59 am
     * @Updated_By
     * @Updated_On
     * @Description The Values have return based on the sync status
     */

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_hospital_id
     * @return – update database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:58:20 am
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */


    public void updateSyncStatus( String str_hospital_id ) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.HOSPITAL_ID + " = '" + str_hospital_id
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


        String updateQuery = "Update " + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " set " + BusinessAccessLayer.FLAG + " = '2'," + BusinessAccessLayer.ISACTIVE + " = 'N' where " + BusinessAccessLayer.HOSPITAL_ID + " = '" + str_hosId + "';";


        System.out.println( "updateQuery:" + updateQuery );
        Cursor mCursor = BusinessAccessLayer.mDb.rawQuery( updateQuery, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }
}
