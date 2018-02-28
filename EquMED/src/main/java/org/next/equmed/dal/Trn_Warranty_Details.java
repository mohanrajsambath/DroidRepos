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
public class Trn_Warranty_Details extends DataAccessLayer {


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

    public Trn_Warranty_Details(Context mCtx) {
        super(mCtx);

    }

    public long insert_warranty_details(String str_warranty_id,
                                        String str_warranty_equipment_enroll_id,
                                        String str_warranty_hospital_id,
                                        String str_warranty_equipment_id,
                                        String str_warranty_start_date,
                                        String str_warranty_end_date,
                                        String str_warranty_description,
                                        String str_warranty_duration,
                                        String str_warranty_type,
                                        String str_flag,
                                        String str_sync_status,
                                        String str_created_by,
                                        String str_created_on,
                                        String str_modified_by,
                                        String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.WARRANTY_ID, str_warranty_id);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID, str_warranty_equipment_enroll_id);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_HOSPITAL_ID, str_warranty_hospital_id);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_EQUIPMENT_ID, str_warranty_equipment_id);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_START_DATE, str_warranty_start_date);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_END_DATE, str_warranty_end_date);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_DESCRIPTION, str_warranty_description);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_DURATION, str_warranty_duration);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_TYPE, str_warranty_type);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        return BusinessAccessLayer.mDb.insert(BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS, null,
                mCVArgs);

    }

    public boolean update_warranty_detail(String str_warranty_id,
                                          String str_warranty_equipment_enroll_id,
                                          String str_warranty_hospital_id,
                                          String str_warranty_equipment_id,
                                          String str_warranty_start_date,
                                          String str_warranty_end_date,
                                          String str_warranty_description,
                                          String str_warranty_duration,
                                          String str_warranty_type,
                                          String str_flag,
                                          String str_sync_status,
                                          String str_created_by,
                                          String str_created_on,
                                          String str_modified_by,
                                          String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.WARRANTY_HOSPITAL_ID, str_warranty_hospital_id);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID, str_warranty_equipment_enroll_id);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_EQUIPMENT_ID, str_warranty_equipment_id);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_START_DATE, str_warranty_start_date);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_END_DATE, str_warranty_end_date);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_DESCRIPTION, str_warranty_description);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_DURATION, str_warranty_duration);
        mCVArgs.put(BusinessAccessLayer.WARRANTY_TYPE, str_warranty_type);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.WARRANTY_ID
                + " = '" + str_warranty_id + "'";

        return BusinessAccessLayer.mDb.update(BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS,
                mCVArgs, updateQuery, null) > 0;
    }

//    public Cursor fetchBy_War_Eq_Enroll_Id( String str_War_Eq_Enr_Id ) throws SQLException {
//        String query = "Select * from "
//                + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + " where "
//                + BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID + " = '" + str_War_Eq_Enr_Id + "';";
//        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
//        if ( mCursorFetch != null ) {
//            mCursorFetch.moveToFirst();
//        }
//        return mCursorFetch;
//    }

    public Cursor fetch() throws SQLException {
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery("SELECT * FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBy_War_Eq_Enroll_Id(String str_War_Eq_Enr_Id) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + " where "
                + BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID + " = '" + str_War_Eq_Enr_Id + "' and " + BusinessAccessLayer.FLAG + "!='2';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByEq_Id_and_HoS_ID(String str_Eq_Id, String str_Hosp_Id) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + " where "
                + BusinessAccessLayer.WARRANTY_EQUIPMENT_ID + " = '" + str_Eq_Id + "' and " + BusinessAccessLayer.WARRANTY_HOSPITAL_ID + " = '" + str_Hosp_Id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBywarrantyId(String str_warranty_id) throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + " where "
                + BusinessAccessLayer.WARRANTY_ID + " = '" + str_warranty_id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public void updateSyncStatus(String str_warranty_id) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.WARRANTY_ID + " = '" + str_warranty_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery(syncStatus_query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

    public Cursor update_flag_By_warranty_Id(String str_warranty_id) throws SQLException {

        String deleteBywarranty_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.WARRANTY_ID + " = '" + str_warranty_id + "';";
        System.out.println("Update flag:" + deleteBywarranty_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteBywarranty_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;
    }

    public Cursor fetchByEq_Id_startEndDate(String str_selectedDate, String eq_enroll_id) throws SQLException {

        String Query = "SELECT * FROM "+ BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS +
                " WHERE '" + str_selectedDate +" 00:00:00' BETWEEN " + BusinessAccessLayer.WARRANTY_START_DATE + " AND " + BusinessAccessLayer.WARRANTY_END_DATE + "";

        System.out.println("Query ==> "+Query);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery("SELECT * FROM "+ BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS +
                " WHERE '" + str_selectedDate+" 00:00:00' BETWEEN " + BusinessAccessLayer.WARRANTY_START_DATE + " AND " + BusinessAccessLayer.WARRANTY_END_DATE + " AND " + BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID + " = '"+eq_enroll_id+"' limit 0,1 ", null);

        System.out.println("mCursorFetch:;"+mCursorFetch);
// Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

}
