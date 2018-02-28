package org.next.equmed.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.next.equmed.bal.BusinessAccessLayer;

/**
 * Created by nextmoveo-1 on 7/12/15.
 */
public class Trn_Equipment_Enroll_Accessories extends DataAccessLayer {
    /**
     * @param ctx
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 09:18:16 am
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public Trn_Equipment_Enroll_Accessories(Context ctx) {
        super(ctx);
    }

    /**
     * @param – str_eq_enroll_id,
     *          str_eq_enroll_id,
     *          str_eq_eq_enroll_ups ,
     *          str_eq_enroll_manual ,
     *          str_eq_enroll_stabilizer ,
     *          str_eq_enroll_others ,
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
     * @Description DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES have to insert the Status of the Equipment
     */

    public long insert_equipment_enroll_accessories(String str_eq_enroll_id,
                                                    String str_eq_eq_enroll_ups,
                                                    String str_eq_enroll_manual,
                                                    String str_eq_enroll_stabilizer,
                                                    String str_eq_enroll_others, String str_flag,
                                                    String str_isactive,
                                                    String str_sync_status,
                                                    String str_created_by,
                                                    String str_created_on,
                                                    String str_modified_by,
                                                    String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_ID, str_eq_enroll_id);
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_UPS, str_eq_eq_enroll_ups);
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_MANUAL, str_eq_enroll_manual);
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_STABILIZER, str_eq_enroll_stabilizer);
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_OTHERS, str_eq_enroll_others);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.ISACTIVE, str_isactive);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);


        return BusinessAccessLayer.mDb.insert(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES, null,
                mCVArgs);

    }

    public boolean update_equipment_enroll_accessories(String str_eq_enroll_id,
                                                       String str_eq_eq_enroll_ups,
                                                       String str_eq_enroll_manual,
                                                       String str_eq_enroll_stabilizer,
                                                       String str_eq_enroll_others, String str_flag,
                                                       String str_isactive,
                                                       String str_sync_status,
                                                       String str_created_by,
                                                       String str_created_on,
                                                       String str_modified_by,
                                                       String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_UPS, str_eq_eq_enroll_ups);
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_MANUAL, str_eq_enroll_manual);
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_STABILIZER, str_eq_enroll_stabilizer);
        mCVArgs.put(BusinessAccessLayer.EQ_ENROLL_OTHERS, str_eq_enroll_others);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.ISACTIVE, str_isactive);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.EQ_ENROLL_ID
                + " = '" + str_eq_enroll_id + "'";

        return BusinessAccessLayer.mDb.update(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES,
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
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + " where "
                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";
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
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES, null);
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
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES, null);
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
    public Cursor deleteBy_Eq_Enroll_Id(String str_Eq_Enroll_Id) throws SQLException {

        String deleteByUser_Id = "DELETE FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + " WHERE "
                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";
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
    public Cursor update_flag_By_Eq_Enroll_Id(String str_Eq_Enroll_Id) throws SQLException {

//        String deleteByUser_Id = "DELETE FROM "
//                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + " WHERE "
//                + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";

        String deleteByUser_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_Eq_Enroll_Id  + "';";

        System.out.println("Delete eqenroll"+deleteByUser_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByUser_Id, null);
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
                + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + " where "
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
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + " SET "
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
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1', " + BusinessAccessLayer.FLAG + " = '" + str_flagStatus + "' where " + BusinessAccessLayer.EQ_ENROLL_ID + " = '" + str_doc_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery(syncStatus_query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

}
