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
public class Trn_Consumables extends DataAccessLayer {

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

    public Trn_Consumables( Context mCtx ) {
        super( mCtx );

    }

    public long insert_consumables( String str_consumables_id,
                                         String str_consumables_equipment_enroll_id,
                                         String str_consumables_hospital_id,
                                         String str_consumables_equipment_id,
                                         String str_name,
                                         String str_description,
                                         String str_type_of_usage,
                                         String str_usage_parameter,
                                         String str_quantity,
                                         String str_uom,
                                         String str_current_stock,
                                         String str_consumable_notes,
                                         String str_flag,
                                         String str_sync_status,
                                         String str_created_by,
                                         String str_created_on,
                                         String str_modified_by,
                                         String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_ID, str_consumables_id );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ENROLL_ID, str_consumables_equipment_enroll_id );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_HOSPITAL_ID, str_consumables_hospital_id );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ID, str_consumables_equipment_id );
        mCVArgs.put( BusinessAccessLayer.NAME, str_name );
        mCVArgs.put( BusinessAccessLayer.DESCRIPTION, str_description );
        mCVArgs.put( BusinessAccessLayer.TYPE_OF_USAGE, str_type_of_usage );
        mCVArgs.put( BusinessAccessLayer.USAGE_PARAMETER, str_usage_parameter );
        mCVArgs.put( BusinessAccessLayer.QUANTITY, str_quantity );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_UOM, str_uom );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_CURRENT_STOCK, str_current_stock );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLE_NOTES, str_consumable_notes );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        return BusinessAccessLayer.mDb.insert( BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES, null,
                mCVArgs );

    }

    public boolean update_consumables_detail( String str_consumables_id,
                                              String str_consumables_equipment_enroll_id,
                                              String str_consumables_hospital_id,
                                              String str_consumables_equipment_id,
                                              String str_name,
                                              String str_description,
                                              String str_type_of_usage,
                                              String str_usage_parameter,
                                              String str_quantity,
                                              String str_uom,
                                              String str_current_stock,
                                              String str_consumable_notes,
                                              String str_flag,
                                              String str_sync_status,
                                              String str_created_by,
                                              String str_created_on,
                                              String str_modified_by,
                                              String str_modified_on ) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ENROLL_ID, str_consumables_equipment_enroll_id );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_HOSPITAL_ID, str_consumables_hospital_id );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ID, str_consumables_equipment_id );
        mCVArgs.put( BusinessAccessLayer.NAME, str_name );
        mCVArgs.put( BusinessAccessLayer.DESCRIPTION, str_description );
        mCVArgs.put( BusinessAccessLayer.TYPE_OF_USAGE, str_type_of_usage );
        mCVArgs.put( BusinessAccessLayer.USAGE_PARAMETER, str_usage_parameter );
        mCVArgs.put( BusinessAccessLayer.QUANTITY, str_quantity );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_UOM, str_uom );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLES_CURRENT_STOCK, str_current_stock );
        mCVArgs.put( BusinessAccessLayer.CONSUMABLE_NOTES, str_consumable_notes );
        mCVArgs.put( BusinessAccessLayer.FLAG, str_flag );
        mCVArgs.put( BusinessAccessLayer.SYNC_STATUS, str_sync_status );
        mCVArgs.put( BusinessAccessLayer.CREATED_BY, str_created_by );
        mCVArgs.put( BusinessAccessLayer.CREATED_ON, str_created_on );
        mCVArgs.put( BusinessAccessLayer.MODIFIED_BY, str_modified_by );
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.CONSUMABLES_ID
                + " = '" + str_consumables_id + "'";

        return BusinessAccessLayer.mDb.update( BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES,
                mCVArgs, updateQuery, null ) > 0;
    }

    public Cursor fetch( ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + ";";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByEq_Id_and_HoS_ID( String str_Eq_Id, String str_Hosp_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + " where "
                + BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ID + " = '" + str_Eq_Id + "' and "+ BusinessAccessLayer.CONSUMABLES_HOSPITAL_ID + " = '"+str_Hosp_Id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByConsEq_Enroll_Id( String str_Cons_Eq_Enr_Id ) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + " where "
                + BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ENROLL_ID + " = '" + str_Cons_Eq_Enr_Id + "' and "+ BusinessAccessLayer.FLAG + " != '2';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }


    public Cursor fetchByconsumablesId(String str_consumables_id) throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + " where "
                + BusinessAccessLayer.CONSUMABLES_ID + " = '"+str_consumables_id+"';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery( query, null );
        if ( mCursorFetch != null ) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public void updateSyncStatus( String str_consumables_id ) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.CONSUMABLES_ID + " = '" + str_consumables_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery( syncStatus_query, null );
        if ( mCursor != null ) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

    public Cursor update_flag_By_consumables_Id(String str_consumables_id) throws SQLException {

        String deleteByconsumables_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.CONSUMABLES_ID + " = '" + str_consumables_id  + "';";
        System.out.println("Update flag:"+deleteByconsumables_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByconsumables_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;
    }

}
