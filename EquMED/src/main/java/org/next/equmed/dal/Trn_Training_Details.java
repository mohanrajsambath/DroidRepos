package org.next.equmed.dal;

/**
 * Created by next on 9/3/16.
 */

/* Copyright (C) Next Techno Entreprises, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Muralidharan M (muralidharan.murugan@nexttechnosolutions.com)
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.next.equmed.bal.BusinessAccessLayer;


/**
 * Created by nextmoveo-1 on 9/3/16.
 */
public class Trn_Training_Details extends DataAccessLayer {


    /**
     * @param – no parameters
     * @return – no return values
     * @throws – no exception throws
     * @author – Muralidharan M
     * @version – 2.0
     * @Updated_By Muralidharan M
     * @Updated_On 09-03-2016 at 10:48:31 am
     * @description The following constructor have to recieve the values from which the activity requested to access this table values.
     * @see – no link
     * @since – 09-03-2016 at 10:48:31 am
     * @deprecated - no deprecation
     */

    public Trn_Training_Details( Context mCtx ) {
        super( mCtx );

    }

    public long insert_training_details( String str_training_id,
                                        String str_training_equipment_enroll_id,
                                        String str_training_hospital_id,
                                        String str_training_equipment_id,
                                        String str_training_date,
                                        String str_training_duration,
                                        String str_training_description,
                                        String str_training_provided_by,
                                        String str_training_invoice,
                                        String str_flag,
                                        String str_sync_status,
                                        String str_created_by,
                                        String str_created_on,
                                        String str_modified_by,
                                        String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.TRAINING_ID, str_training_id );
        mCVArgs.put( BusinessAccessLayer.TRAINING_EQUIPMENT_ENROLL_ID, str_training_equipment_enroll_id );
        mCVArgs.put( BusinessAccessLayer.TRAINING_HOSPITAL_ID, str_training_hospital_id );
        mCVArgs.put( BusinessAccessLayer.TRAINING_EQUIPMENT_ID, str_training_equipment_id );
        mCVArgs.put( BusinessAccessLayer.TRAINING_DATE, str_training_date );
        mCVArgs.put( BusinessAccessLayer.TRAINING_DURATION, str_training_duration );
        mCVArgs.put( BusinessAccessLayer.TRAINING_DESCRIPTION, str_training_description );
        mCVArgs.put( BusinessAccessLayer.TRAINING_PROVIDED_BY, str_training_provided_by );
        mCVArgs.put( BusinessAccessLayer.TRAINING_INVOICE, str_training_invoice );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        return BusinessAccessLayer.mDb.insert( BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS, null,
                mCVArgs );

    }

    public boolean update_training_detail( String str_training_id,
                                           String str_training_equipment_enroll_id,
                                           String str_training_hospital_id,
                                           String str_training_equipment_id,
                                           String str_training_date,
                                           String str_training_duration,
                                           String str_training_description,
                                           String str_training_provided_by,
                                           String str_training_invoice,
                                           String str_flag,
                                           String str_sync_status,
                                           String str_created_by,
                                           String str_created_on,
                                           String str_modified_by,
                                           String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.TRAINING_HOSPITAL_ID, str_training_hospital_id );
        mCVArgs.put( BusinessAccessLayer.TRAINING_EQUIPMENT_ENROLL_ID, str_training_equipment_enroll_id);
        mCVArgs.put( BusinessAccessLayer.TRAINING_EQUIPMENT_ID, str_training_equipment_id );
        mCVArgs.put( BusinessAccessLayer.TRAINING_DATE, str_training_date );
        mCVArgs.put( BusinessAccessLayer.TRAINING_DURATION, str_training_duration );
        mCVArgs.put( BusinessAccessLayer.TRAINING_DESCRIPTION, str_training_description );
        mCVArgs.put( BusinessAccessLayer.TRAINING_PROVIDED_BY, str_training_provided_by );
        mCVArgs.put( BusinessAccessLayer.TRAINING_INVOICE, str_training_invoice );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.TRAINING_ID
                + " = '" + str_training_id + "'";

        return BusinessAccessLayer.mDb.update( BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS,
                mCVArgs, updateQuery, null ) > 0;
    }


    /**
     * @param – no params
     * @return – All the Values in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Aravindhakumar.S
     * @Created_On 10-03-2016
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetch() throws SQLException {
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery("SELECT * FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }


//    public Cursor fetchBy_Tra_Eq_Enroll_Id( String str_Trai_Eq_Enr_Id ) throws SQLException {
//        String query = "Select * from "
//                + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + " where "
//                + BusinessAccessLayer.TRAINING_EQUIPMENT_ENROLL_ID + " = '" + str_Trai_Eq_Enr_Id + "';";
//        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
//        if ( mCursorFetch != null ) {
//            mCursorFetch.moveToFirst();
//        }
//        return mCursorFetch;
//    }

    public Cursor fetchBy_Tra_Eq_Enroll_Id( String str_Trai_Eq_Enr_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + " where "
                + BusinessAccessLayer.TRAINING_EQUIPMENT_ENROLL_ID + " = '" + str_Trai_Eq_Enr_Id + "' and " + BusinessAccessLayer.FLAG + "!='2';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }
    public Cursor fetchByEq_Id_and_HoS_ID( String str_Eq_Id, String str_Hosp_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + " where "
                + BusinessAccessLayer.TRAINING_EQUIPMENT_ID + " = '" + str_Eq_Id + "' and "+ BusinessAccessLayer.TRAINING_HOSPITAL_ID + " = '"+str_Hosp_Id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBytrainingId(String str_training_id) throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + " where "
                + BusinessAccessLayer.TRAINING_ID + " = '"+str_training_id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public void updateSyncStatus( String str_training_id ) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.TRAINING_ID + " = '" + str_training_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery( syncStatus_query, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

    public Cursor update_flag_By_training_Id(String str_training_id) throws SQLException {

        String deleteBytraining_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.TRAINING_ID + " = '" + str_training_id  + "';";
        System.out.println("Update flag:"+deleteBytraining_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteBytraining_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;
    }

}
