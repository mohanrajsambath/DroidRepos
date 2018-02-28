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
public class Trn_Equipment_Enrollment extends DataAccessLayer {
    /**
     * @param ctx
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 01:44:34 pm
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public Trn_Equipment_Enrollment(Context ctx) {
        super(ctx);
    }


    /**
     * @param – str_eq_enroll_id,
     *          str_eq_id,
     *          str_eq_hospital_id,
     *          str_eq_location_code,
     *          str_eq_serialno,
     *          str_eq_name,
     *          str_eq_make,
     *          str_eq_model,
     *          str_eq_install_date,
     *          str_eq_service_tagno,
     *          str_eq_notes ,
     *          str_eq_extra_accessories ,
     *          str_eq_install_status ,
     *          str_eq_install_notes ,
     *          str_eq_working_condition ,
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
     * @Created_On 03-12-2015 at 01:53:38 pm
     * @Updated_By
     * @Updated_On
     * @Description DB_TABLE_TRN_EQUIPMENT_ENROLL have to insert the Status of the Equipment
     */

    public long insert_equipment_enroll(String str_eq_enroll_id,
                                        String str_eq_id,
                                        String str_eq_hospital_id,
                                        String str_eq_location_code,
                                        String str_gps_coordinates,
                                        String str_eq_serialno,
                                        String str_eq_make,
                                        String str_eq_model,
                                        String str_eq_install_date,
                                        String str_eq_service_tagno,
                                        String str_eq_notes,
                                        String str_eq_extra_access,
                                        String str_eq_install_status,
                                        String str_eq_install_notes,
                                        String str_eq_working_status,
                                        String str_eq_working_notes,
                                        String str_sync_status,
                                        String str_flag,
                                        String str_created_by,
                                        String str_created_on,
                                        String str_modified_by,
                                        String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_ID, str_eq_enroll_id);
        mCVArgs.put(BusinessAccessLayer.EQ_ID, str_eq_id);
        mCVArgs.put(BusinessAccessLayer.EQ_HOSPITAL_ID, str_eq_hospital_id);
        mCVArgs.put(BusinessAccessLayer.EQ_LOCATION_CODE, str_eq_location_code);
        mCVArgs.put(BusinessAccessLayer.GPS_COORDINATES, str_gps_coordinates);
        mCVArgs.put(BusinessAccessLayer.EQ_SERIALNO, str_eq_serialno);
        mCVArgs.put(BusinessAccessLayer.EQ_MAKE, str_eq_make);
        mCVArgs.put(BusinessAccessLayer.EQ_MODEL, str_eq_model);
        mCVArgs.put(BusinessAccessLayer.EQ_INSTALL_DATE, str_eq_install_date);
        mCVArgs.put(BusinessAccessLayer.EQ_SERVICE_TAGNO, str_eq_service_tagno);
        mCVArgs.put(BusinessAccessLayer.EQ_NOTES, str_eq_notes);
        mCVArgs.put(BusinessAccessLayer.EQ_EXTRA_ACCESSORIES, str_eq_extra_access);
        mCVArgs.put(BusinessAccessLayer.EQ_INSTALL_STATUS, str_eq_install_status);
        mCVArgs.put(BusinessAccessLayer.EQ_INSTALL_NOTES, str_eq_install_notes);
        mCVArgs.put(BusinessAccessLayer.EQ_WORKING_STATUS, str_eq_working_status);
        mCVArgs.put(BusinessAccessLayer.EQ_WORKING_NOTES, str_eq_working_notes);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);


        return BusinessAccessLayer.mDb.insert(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL, null,
                mCVArgs);

    }

    public boolean update_equipment_enroll(String str_eq_enroll_id,
                                           String str_eq_id,
                                           String str_eq_hospital_id,
                                           String str_eq_location_code,
                                           String str_gps_coordinates,
                                           String str_eq_serialno,
                                           String str_eq_make,
                                           String str_eq_model,
                                           String str_eq_install_date,
                                           String str_eq_service_tagno,
                                           String str_eq_notes,
                                           String str_eq_install_status,
                                           String str_eq_install_notes,
                                           String str_eq_working_status,
                                           String str_eq_working_notes,
                                           String str_sync_status,
                                           String str_flag,
                                           String str_created_by,
                                           String str_created_on,
                                           String str_modified_by,
                                           String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.EQ_ID, str_eq_id);
        mCVArgs.put(BusinessAccessLayer.EQ_HOSPITAL_ID, str_eq_hospital_id);
        mCVArgs.put(BusinessAccessLayer.EQ_LOCATION_CODE, str_eq_location_code);
        mCVArgs.put(BusinessAccessLayer.GPS_COORDINATES, str_gps_coordinates);
        mCVArgs.put(BusinessAccessLayer.EQ_SERIALNO, str_eq_serialno);
        mCVArgs.put(BusinessAccessLayer.EQ_MAKE, str_eq_make);
        mCVArgs.put(BusinessAccessLayer.EQ_MODEL, str_eq_model);
        mCVArgs.put(BusinessAccessLayer.EQ_INSTALL_DATE, str_eq_install_date);
        mCVArgs.put(BusinessAccessLayer.EQ_SERVICE_TAGNO, str_eq_service_tagno);
        mCVArgs.put(BusinessAccessLayer.EQ_NOTES, str_eq_notes);
        mCVArgs.put(BusinessAccessLayer.EQ_INSTALL_STATUS, str_eq_install_status);
        mCVArgs.put(BusinessAccessLayer.EQ_INSTALL_NOTES, str_eq_install_notes);
        mCVArgs.put(BusinessAccessLayer.EQ_WORKING_STATUS, str_eq_working_status);
        mCVArgs.put(BusinessAccessLayer.EQ_WORKING_NOTES, str_eq_working_notes);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.EQ_ENROLL_ID
                + " = '" + str_eq_enroll_id + "'";

        return BusinessAccessLayer.mDb.update(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL,
                mCVArgs, updateQuery, null) > 0;

    }

    /**
     * @param – str_Eq_Enroll_Id
     * @return – EQUIPMENT_ENROLLMENT Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 01:55:27 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByEq_Enroll_Id(String str_Eq_Enroll_Id) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " where "
                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";

        System.out.println("query:" + query);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
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
     * @Created_On 03-12-2015 at 01:56:50 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetch() throws SQLException {
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery("SELECT * FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL, null);
        if (mCursorFetch != null) {
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
     * @Created_On 03-12-2015 at 01:55:45 pm
     * @Updated_By
     * @Updated_On
     * @Description return status of deleted values
     */
    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = BusinessAccessLayer.mDb.rawQuery("Delete FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL, null);
        if (mCursorDeleteAll != null) {
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
     * @Created_On 03-12-2015 at 01:59:16 pm
     * @Updated_By
     * @Updated_On
     * @Description return deleted Hospital  based on the hospital_id
     */
    public Cursor updateBy_flag_Eq_Enroll_Id(String str_Eq_Enroll_Id) throws SQLException {

//        String deleteByUser_Id = "DELETE FROM "
//                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " WHERE "
//                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";

        String deleteByUser_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";


        System.out.println("deleteByUser_Id:::" + deleteByUser_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByUser_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;

    }

    /**
     * @param – str_hospital_id
     * @return – the Deleted Value Status in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 01:59:16 pm
     * @Updated_By
     * @Updated_On
     * @Description return deleted Hospital  based on the hospital_id
     */
    public Cursor deleteBy_Eq_Enroll_Id(String str_Eq_Enroll_Id) throws SQLException {

        String deleteByUser_Id = "DELETE FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " WHERE "
                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";

        System.out.println("deleteByUser_Id:::" + deleteByUser_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByUser_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;

    }


    /**
     * @param – str_Eq_Enroll_Id
     * @return – EQUIPMENT_ENROLLMENT Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 01:55:27 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByHospital(String str_Eq_Enroll_Id) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " where "
                + BusinessAccessLayer.EQ_HOSPITAL_ID + " = '" + str_Eq_Enroll_Id + "' AND " + BusinessAccessLayer.FLAG + " in ('0','1');";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }


    /**
     * @param – str_hospital_id,str_Eq_Enroll_Id
     * @return – the Deleted Value Status in the Database table
     * @throws – no exception throws
     * @Description return deleted Hospital and enrollment based on the hospital_id
     */

    public Cursor deleteBy_Eq_Enroll_Id(String str_Eq_Enroll_Id, String str_hosId) throws SQLException {

        String test = "Delete from " + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " where " + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "' and " + BusinessAccessLayer.EQ_HOSPITAL_ID + " = '" + str_hosId + "'";

//        String deleteByUser_Id = "DELETE FROM "
//                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " WHERE "
//                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "' and " +
//                "                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id  + "'
//        ";
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(test, null);
        if (mCursorById != null) {
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
     * @Created_On 03-12-2015 at 01:59:40 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return based on the sync status
     */

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
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
     * @Created_On 03-12-2015 at 02:01:05 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void updateSyncStatus(String str_Eq_Enroll_Id) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery(syncStatus_query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

    /**
     * @param – str_doc_id,str_flagId
     * @return – update database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Venkatakrishnan.k
     * @Created_On 09-12-2015 at 10:30:42 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void updateFlagStatus(String str_doc_id, String str_flagStatus) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1', " + BusinessAccessLayer.FLAG + " = '" + str_flagStatus + "' where " + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_doc_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery(syncStatus_query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

}
