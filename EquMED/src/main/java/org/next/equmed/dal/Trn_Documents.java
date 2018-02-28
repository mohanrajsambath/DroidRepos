package org.next.equmed.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.next.equmed.bal.BusinessAccessLayer;

/**
 * Created by next-4 on 8/12/15.
 */
public class Trn_Documents extends DataAccessLayer {

    /**
     * @param ctx
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 03:44:42 pm
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public Trn_Documents(Context ctx) {

        super(ctx);
    }

    /**
     * @param – str_doc_id,
     *          str_doc_name,
     *          str_doc_encrypted_data,
     *          str_flag,
     *          str_doc_type,
     *          str_sync_status,
     *          str_created_by,
     *          str_created_on,
     *          str_modified_by,
     *          str_modified_on
     * @return – return values of insertion status
     * @throws – no exception throws
     * @Type long return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 03:50:38 pm
     * @Updated_By
     * @Updated_On
     * @Description DB_TABLE_TRN_DOCUMENTS have to insert the Status of the Equipment
     */

    public long insert_document(String str_doc_doc_id, String str_doc_id,
                                String str_doc_name,
                                String str_doc_encrypted_data,
                                String str_doc_type,
                                String str_flag,
                                String str_sync_status,
                                String str_created_by,
                                String str_created_on,
                                String str_modified_by,
                                String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.DOC_DOC_ID, str_doc_doc_id);
        mCVArgs.put(BusinessAccessLayer.DOC_ID, str_doc_id);
        mCVArgs.put(BusinessAccessLayer.DOC_NAME, str_doc_name);
        mCVArgs.put(BusinessAccessLayer.DOC_ENCRYPTED_DATA, str_doc_encrypted_data);
        mCVArgs.put(BusinessAccessLayer.DOC_TYPE, str_doc_type);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);


        return BusinessAccessLayer.mDb.insert(BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS, null,
                mCVArgs);

    }

    public boolean update_document(String str_doc_doc_id, String str_doc_id,
                                   String str_doc_name,
                                   String str_doc_encrypted_data,
                                   String str_doc_type,
                                   String str_flag,
                                   String str_sync_status,
                                   String str_created_by,
                                   String str_created_on,
                                   String str_modified_by,
                                   String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.DOC_NAME, str_doc_name);
        mCVArgs.put(BusinessAccessLayer.DOC_ENCRYPTED_DATA, str_doc_encrypted_data);
        mCVArgs.put(BusinessAccessLayer.DOC_TYPE, str_doc_type);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.DOC_ID
                + " = '" + str_doc_id + "' and " + BusinessAccessLayer.DOC_DOC_ID + " = '" + str_doc_doc_id + "'";
        System.out.println("updateQuery:" + updateQuery);

//        String updateQuery = "" + BusinessAccessLayer.DOC_ID
//                + " = '" + str_doc_id + "' and " + BusinessAccessLayer.DOC_NAME + " = '" + str_doc_name + "'";

        return BusinessAccessLayer.mDb.update(BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS,
                mCVArgs, updateQuery, null) > 0;

    }

    /**
     * @param – str_Eq_Enroll_Id
     * @return – EQUIPMENT_ENROLLMENT Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 03:50:38 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByDoc_Id(String str_doc_id) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
                + BusinessAccessLayer.DOC_ID + " = '" + str_doc_id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_Eq_Enroll_Id
     * @return – EQUIPMENT_ENROLLMENT Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 03:50:38 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByDoc_IdAndDocDocID(String str_doc_id, String doc_doc_id, String imgName) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
                + BusinessAccessLayer.DOC_ID + " = '" + str_doc_id + "' and " + BusinessAccessLayer.DOC_DOC_ID + " = '" + doc_doc_id + "' and " + BusinessAccessLayer.DOC_NAME + " = '" + imgName + "';";

        System.out.println("fetch update:" + query);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByDoc_Id_Type(String docID, String docName) {

        String Sign = "Select * from " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
                + BusinessAccessLayer.DOC_ID + " = '" + docID + "' AND  " + BusinessAccessLayer.DOC_NAME + " = '"
                + docName + "' AND " + BusinessAccessLayer.FLAG + " in ('0','1');";

        System.out.println("Sign query:" + Sign);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(Sign, null);
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
     * @Created_On 08-12-2015 at 03:54:38 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetch() throws SQLException {
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery("SELECT * FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS, null);
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
     * @Created_On 08-12-2015 at 03:54:56 pm
     * @Updated_By
     * @Updated_On
     * @Description return status of deleted values
     */
    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = BusinessAccessLayer.mDb.rawQuery("Delete FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS, null);
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
     * @Created_On 08-12-2015 at 03:55:36 pm
     * @Updated_By
     * @Updated_On
     * @Description return deleted Hospital  based on the hospital_id
     */
    public Cursor updateBy_Doc_Id(String str_doc_id) throws SQLException {

//        String deleteByUser_Id = "DELETE FROM "
//                + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " WHERE "
//                + BusinessAccessLayer.DOC_ID + " = '" + str_doc_id + "';";

        String deleteByUser_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.DOC_ID + " = '" + str_doc_id + "';";

        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByUser_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;

    }


    public Cursor updateByDoc_Id_Type(String docID, String docName) {


        String delteQuery = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " SET "
                + BusinessAccessLayer.FLAG + " = '2' , " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.DOC_ID + " = '" + docID + "' AND  " + BusinessAccessLayer.DOC_NAME + " = '"
                + docName + "';";


//        String delteQuery = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
//                + BusinessAccessLayer.DOC_ID + " = '" + docID + "' AND  " + BusinessAccessLayer.DOC_NAME + " = '"
//                + docName + "';";

//        String delteQuery = "DELETE FROM " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
//                + BusinessAccessLayer.DOC_ID + " = '" + docID + "' AND  " + BusinessAccessLayer.DOC_NAME + " = '"
//                + docName + "';";

        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(delteQuery, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor deleteByDoc_Id_Type(String docID, String docName, String doc_doc_id) {


//        String delteQuery = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " SET "
//                + BusinessAccessLayer.FLAG + " = '2' where " + BusinessAccessLayer.DOC_ID + " = '" + docID + "' AND  " + BusinessAccessLayer.DOC_NAME + " = '"
//                + docName + "';";


//        String delteQuery = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
//                + BusinessAccessLayer.DOC_ID + " = '" + docID + "' AND  " + BusinessAccessLayer.DOC_NAME + " = '"
//                + docName + "';";

        String delteQuery = "DELETE FROM " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
                + BusinessAccessLayer.DOC_DOC_ID + " = '" + docID + "' AND  " + BusinessAccessLayer.DOC_ID + " = '"
                + docName + "' AND  " + BusinessAccessLayer.DOC_NAME + " = '" + doc_doc_id + "';";
        System.out.println("Delete document:" + delteQuery);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(delteQuery, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – no params
     * @return – the database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 03:56:23 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return based on the sync status
     */

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";


        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    /**
     * @param – str_doc_id
     * @return – update database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 04:00:42 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void updateSyncStatus(String str_doc_id) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.DOC_ID + " = '" + str_doc_id
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
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1', " + BusinessAccessLayer.FLAG + " = '" + str_flagStatus + "' where " + BusinessAccessLayer.DOC_ID + " = '" + str_doc_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery(syncStatus_query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }


}