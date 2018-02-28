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
public class Trn_Voice_Of_Customer extends DataAccessLayer {


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

    public Trn_Voice_Of_Customer( Context mCtx ) {
        super( mCtx );

    }

    public long insert_voc_details( String str_voc_id,
                                         String str_voc_equipment_enroll_id,
                                         String str_voc_hospital_id,
                                         String str_voc_equipment_id,
                                         String str_type,
                                         String str_in_brief,
                                         String str_flag,
                                         String str_sync_status,
                                         String str_created_by,
                                         String str_created_on,
                                         String str_modified_by,
                                         String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.VOC_ID, str_voc_id );
        mCVArgs.put( BusinessAccessLayer.VOC_EQUIPMENT_ENROLL_ID, str_voc_equipment_enroll_id );
        mCVArgs.put( BusinessAccessLayer.VOC_HOSPITAL_ID, str_voc_hospital_id );
        mCVArgs.put( BusinessAccessLayer.VOC_EQUIPMENT_ID, str_voc_equipment_id );
        mCVArgs.put( BusinessAccessLayer.TYPE, str_type );
        mCVArgs.put( BusinessAccessLayer.IN_BRIEF, str_in_brief );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        return BusinessAccessLayer.mDb.insert( BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER, null,
                mCVArgs );

    }

    public boolean update_voc_detail( String str_voc_id,
                                      String str_voc_equipment_enroll_id,
                                      String str_voc_hospital_id,
                                      String str_voc_equipment_id,
                                      String str_type,
                                      String str_in_brief,
                                      String str_flag,
                                      String str_sync_status,
                                      String str_created_by,
                                      String str_created_on,
                                      String str_modified_by,
                                      String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.VOC_EQUIPMENT_ENROLL_ID, str_voc_equipment_enroll_id );
        mCVArgs.put( BusinessAccessLayer.VOC_HOSPITAL_ID, str_voc_hospital_id );
        mCVArgs.put( BusinessAccessLayer.VOC_EQUIPMENT_ID, str_voc_equipment_id );
        mCVArgs.put( BusinessAccessLayer.TYPE, str_type );
        mCVArgs.put( BusinessAccessLayer.IN_BRIEF, str_in_brief );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.VOC_ID
                + " = '" + str_voc_id + "'";

        return BusinessAccessLayer.mDb.update( BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER,
                mCVArgs, updateQuery, null ) > 0;
    }

    public Cursor fetchBy_Voc_Eq_Enroll_Id( String str_Voc_Eq_Enr_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + " where "
                + BusinessAccessLayer.VOC_EQUIPMENT_ENROLL_ID + " = '" + str_Voc_Eq_Enr_Id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByEq_Id_and_HoS_ID( String str_Eq_Id, String str_Hosp_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + " where "
                + BusinessAccessLayer.VOC_EQUIPMENT_ID + " = '" + str_Eq_Id + "' and "+ BusinessAccessLayer.VOC_HOSPITAL_ID + " = '"+str_Hosp_Id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByvocId(String str_voc_id) throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + " where "
                + BusinessAccessLayer.VOC_ID + " = '"+str_voc_id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetch() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + ";";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }


    public void updateSyncStatus( String str_voc_id ) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.VOC_ID + " = '" + str_voc_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery( syncStatus_query, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

    public Cursor update_flag_By_voc_Id(String str_voc_id) throws SQLException {

        String deleteByvoc_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.VOC_ID + " = '" + str_voc_id  + "';";
        System.out.println("Update flag:"+deleteByvoc_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByvoc_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;
    }

}
