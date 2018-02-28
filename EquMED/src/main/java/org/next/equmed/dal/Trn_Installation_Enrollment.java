package org.next.equmed.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.next.equmed.bal.BusinessAccessLayer;

/**
 * Created by nextmoveo-1 on 3/12/15.
 */
public class Trn_Installation_Enrollment extends DataAccessLayer {
    /**
     * @param ctx
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 02:02:26 pm
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public Trn_Installation_Enrollment(Context ctx) {
        super(ctx);
    }


    /**
     * @param – str_inst_id,
     *          str_inst_equipment_enroll_id,
     *          str_inst_company_by,
     *          str_inst_engg_name,
     *          str_inst_location,
     *          str_inst_near_by_phoneno,
     *          str_inst_notes,
     *          str_inst_area,
     *          str_inst_owner_name,
     *          str_inst_owner_phoneno,
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
     * @Created_On 03-12-2015 at 02:03:22 pm
     * @Updated_By
     * @Updated_On
     * @Description DB_TABLE_TRN_INSTALLATION_ENROLL have to insert the Status of the Equipment
     */

    public long insert_installation_enroll(String str_inst_equipment_enroll_id,
                                           String str_inst_company_by,
                                           String str_inst_engg_name,
                                           String str_inst_install_date,
                                           String str_inst_location,
                                           String str_inst_near_by_phoneno,
                                           String str_inst_notes,
                                           String str_inst_area,
                                           String str_inst_owner_name,
                                           String str_inst_owner_phoneno,
                                           String str_flag,
                                           String str_sync_status,
                                           String str_created_by,
                                           String str_created_on,
                                           String str_modified_by,
                                           String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID, str_inst_equipment_enroll_id);
        mCVArgs.put(BusinessAccessLayer.INST_COMPANY_BY, str_inst_company_by);
        mCVArgs.put(BusinessAccessLayer.INST_ENGG_NAME, str_inst_engg_name);
        mCVArgs.put(BusinessAccessLayer.INST_INSTALL_DATE, str_inst_install_date);
        mCVArgs.put(BusinessAccessLayer.INST_LOCATION, str_inst_location);
        mCVArgs.put(BusinessAccessLayer.INST_NEAR_BY_PHONENO, str_inst_near_by_phoneno);
        mCVArgs.put(BusinessAccessLayer.INST_NOTES, str_inst_notes);
        mCVArgs.put(BusinessAccessLayer.INST_AREA, str_inst_area);
        mCVArgs.put(BusinessAccessLayer.INST_OWNER_NAME, str_inst_owner_name);
        mCVArgs.put(BusinessAccessLayer.INST_OWNER_PHONENO, str_inst_owner_phoneno);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);


        return BusinessAccessLayer.mDb.insert(BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL, null,
                mCVArgs);

    }

    public boolean update_installation_enroll(String str_inst_equipment_enroll_id,
                                String str_inst_company_by,
                                String str_inst_engg_name, String str_inst_install_date,
                                String str_inst_location,
                                String str_inst_near_by_phoneno,
                                String str_inst_notes,
                                String str_inst_area,
                                String str_inst_owner_name,
                                String str_inst_owner_phoneno,
                                String str_flag,
                                String str_sync_status,
                                String str_created_by,
                                String str_created_on,
                                String str_modified_by,
                                String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.INST_COMPANY_BY, str_inst_company_by);
        mCVArgs.put(BusinessAccessLayer.INST_ENGG_NAME, str_inst_engg_name);
        mCVArgs.put(BusinessAccessLayer.INST_INSTALL_DATE, str_inst_install_date);
        mCVArgs.put(BusinessAccessLayer.INST_LOCATION, str_inst_location);
        mCVArgs.put(BusinessAccessLayer.INST_NEAR_BY_PHONENO, str_inst_near_by_phoneno);
        mCVArgs.put(BusinessAccessLayer.INST_NOTES, str_inst_notes);
        mCVArgs.put(BusinessAccessLayer.INST_AREA, str_inst_area);
        mCVArgs.put(BusinessAccessLayer.INST_OWNER_NAME, str_inst_owner_name);
        mCVArgs.put(BusinessAccessLayer.INST_OWNER_PHONENO, str_inst_owner_phoneno);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);


        String updateQuery = "" + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID
                + " = '" + str_inst_equipment_enroll_id + "'";

        return BusinessAccessLayer.mDb.update(BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL,
                mCVArgs, updateQuery, null) > 0;

    }

    /**
     * @param – str_Inst_Id
     * @return – EQUIPMENT_ENROLLMENT Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 02:11:44 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByInst_EquipmentEnroll_Id(String str_Inst_Id) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " where "
                + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " = '" + str_Inst_Id + "';";
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
     * @Created_On 03-12-2015 at 02:13:15 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetch() throws SQLException {
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery("SELECT * FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL, null);
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
     * @Created_On 03-12-2015 at 02:13:40 pm
     * @Updated_By
     * @Updated_On
     * @Description return status of deleted values
     */
    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = BusinessAccessLayer.mDb.rawQuery("Delete FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL, null);
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
                + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " WHERE "
                + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";
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
//                + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " WHERE "
//                + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " = '" + str_Eq_Enroll_Id + "';";

        String deleteByUser_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " SET "
                + BusinessAccessLayer.FLAG + " = '2' , " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " = '" + str_Eq_Enroll_Id  + "';";
        System.out.println("Update flag:"+deleteByUser_Id);
        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByUser_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;

    }
    /**
     * @param – str_Inst_Id
     * @return – the Deleted Value Status in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 02:15:10 pm
     * @Updated_By
     * @Updated_On
     * @Description return deleted Hospital  based on the hospital_id
     */
    public Cursor deleteBy_Eq_Inst_Id(String str_Inst_Id) throws SQLException {

        String deleteByUser_Id = "DELETE FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " WHERE "
                + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " = '" + str_Inst_Id + "';";
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
     * @Created_On 03-12-2015 at 02:15:32 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return based on the sync status
     */

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_Inst_Id
     * @return – update database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 02:17:19 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void updateSyncStatus(String str_Inst_Id) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " = '" + str_Inst_Id
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
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1', " + BusinessAccessLayer.FLAG + " = '" + str_flagStatus + "' where " + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + " = '" + str_doc_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery(syncStatus_query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }
}
