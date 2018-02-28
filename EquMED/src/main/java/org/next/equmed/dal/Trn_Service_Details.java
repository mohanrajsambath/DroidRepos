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
public class Trn_Service_Details extends DataAccessLayer {


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

    public Trn_Service_Details( Context mCtx ) {
        super( mCtx );

    }

    public long insert_service_details( String str_service_id,
                                        String str_service_equipment_enroll_id,
                                        String str_service_hospital_id,
                                        String str_service_equipment_id,
                                        String str_service_date_time,
                                        String str_service_duration,
                                        String str_service_type,
                                        String str_service_notes,
                                        String str_service_approved_by,
                                        String str_service_invoice,
                                        String str_serviced_by,
                                        String str_flag,
                                        String str_sync_status,
                                        String str_created_by,
                                        String str_created_on,
                                        String str_modified_by,
                                        String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.SERVICE_ID, str_service_id );
        mCVArgs.put( BusinessAccessLayer.SERVICE_EQUIPMENT_ENROLL_ID, str_service_equipment_enroll_id );
        mCVArgs.put( BusinessAccessLayer.SERVICE_HOSPITAL_ID, str_service_hospital_id );
        mCVArgs.put( BusinessAccessLayer.SERVICE_EQUIPMENT_ID, str_service_equipment_id );
        mCVArgs.put( BusinessAccessLayer.SERVICE_DATE_TIME, str_service_date_time );
        mCVArgs.put( BusinessAccessLayer.SERVICE_DURATION, str_service_duration );
        mCVArgs.put( BusinessAccessLayer.SERVICE_TYPE, str_service_type );
        mCVArgs.put( BusinessAccessLayer.SERVICE_NOTES, str_service_notes );
        mCVArgs.put( BusinessAccessLayer.SERVICE_APPROVED_BY, str_service_approved_by );
        mCVArgs.put( BusinessAccessLayer.SERVICE_INVOICE, str_service_invoice );
        mCVArgs.put( BusinessAccessLayer.SERVICED_BY, str_serviced_by );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        return BusinessAccessLayer.mDb.insert( BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS, null,
                mCVArgs );

    }

    public boolean update_service_detail( String str_service_id,
                                          String str_service_equipment_enroll_id,
                                          String str_service_hospital_id,
                                          String str_service_equipment_id,
                                          String str_service_date_time,
                                          String str_service_duration,
                                          String str_service_type,
                                          String str_service_notes,
                                          String str_service_approved_by,
                                          String str_service_invoice,
                                          String str_serviced_by,
                                          String str_flag,
                                          String str_sync_status,
                                          String str_created_by,
                                          String str_created_on,
                                          String str_modified_by,
                                          String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.SERVICE_EQUIPMENT_ENROLL_ID, str_service_equipment_enroll_id );
        mCVArgs.put( BusinessAccessLayer.SERVICE_HOSPITAL_ID, str_service_hospital_id );
        mCVArgs.put( BusinessAccessLayer.SERVICE_EQUIPMENT_ID, str_service_equipment_id );
        mCVArgs.put( BusinessAccessLayer.SERVICE_DATE_TIME, str_service_date_time );
        mCVArgs.put( BusinessAccessLayer.SERVICE_DURATION, str_service_duration );
        mCVArgs.put( BusinessAccessLayer.SERVICE_TYPE, str_service_type );
        mCVArgs.put( BusinessAccessLayer.SERVICE_NOTES, str_service_notes );
        mCVArgs.put( BusinessAccessLayer.SERVICE_APPROVED_BY, str_service_approved_by );
        mCVArgs.put( BusinessAccessLayer.SERVICE_INVOICE, str_service_invoice );
        mCVArgs.put( BusinessAccessLayer.SERVICED_BY, str_serviced_by );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.SERVICE_ID
                + " = '" + str_service_id + "'";

        System.out.println("updateQuery" + updateQuery);

        return BusinessAccessLayer.mDb.update( BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS,
                mCVArgs, updateQuery, null ) > 0;
    }

    public Cursor fetch( ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + ";";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBySerEq_Enroll_Id( String str_Ser_Eq_Enr_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + " where "
                + BusinessAccessLayer.SERVICE_EQUIPMENT_ENROLL_ID + " = '" + str_Ser_Eq_Enr_Id + "' and " + BusinessAccessLayer.FLAG + "!='2';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByEq_Id_and_HoS_ID( String str_Eq_Id, String str_Hosp_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + " where "
                + BusinessAccessLayer.SERVICE_EQUIPMENT_ID + " = '" + str_Eq_Id + "' and "+ BusinessAccessLayer.SERVICE_HOSPITAL_ID + " = '"+str_Hosp_Id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchHosName( String str_Hosp_Id ) throws SQLException {
        String query = "Select "+BusinessAccessLayer.HOSPITAL_NAME+","+BusinessAccessLayer.HOSPITAL_LOCATION+" from "
                + BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL + " where "
                + BusinessAccessLayer.HOSPITAL_ID + " = '" + str_Hosp_Id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchEquName( String str_Equip_Id ) throws SQLException {
        String query = "Select "+BusinessAccessLayer.EQ_NAME+" from "
                + BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS + " where "
                + BusinessAccessLayer.EQ_ID + " = '" + str_Equip_Id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByServiceId(String str_service_id) throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + " where "
                + BusinessAccessLayer.SERVICE_ID + " = '"+str_service_id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public void updateSyncStatus( String str_service_id ) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.SERVICE_ID + " = '" + str_service_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery( syncStatus_query, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

    public Cursor update_flag_By_service_Id(String str_service_id) throws SQLException {

        String deleteByService_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS + " SET "
                + BusinessAccessLayer.FLAG + " = '2' , " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.SERVICE_ID + " = '" + str_service_id  + "';";
        System.out.println("Update flag:"+deleteByService_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByService_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;
    }

}
