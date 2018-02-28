package org.next.equmed.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.next.equmed.bal.BusinessAccessLayer;

/**
 * Created by next-4 on 8/12/15.
 */
public class Trn_Images extends DataAccessLayer {
    /**
     * @param ctx
     * @Type Single Argument Constructor
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 04:01:42 pm
     * @Updated_By
     * @Updated_On
     * @Description Constructor to receive the which Context will be passed
     */
    public Trn_Images(Context ctx) {
        super(ctx);
    }


    /**
     * @param – str_doc_id,
     *          str_doc_name,
     *          str_doc_encrypted_data,
     *          str_flag,
     *          str_sync_status,
     *          str_created_by,
     *          str_created_on,
     *          str_modified_by,
     *          str_modified_on
     * @return – return values of insertion status
     * @throws – no exception throws
     * @Type long return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 04:01:38 pm
     * @Updated_By
     * @Updated_On
     * @Description DB_TABLE_TRN_IMAGES have to insert the Status of the Equipment
     */

    public long insert_image(String str_img_img_id, String str_img_id,
                             String str_img_name,
                             String str_img_encrypted_data,
                             String str_flag,
                             String str_sync_status,
                             String str_created_by,
                             String str_created_on,
                             String str_modified_by,
                             String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        mCVArgs.put(BusinessAccessLayer.IMG_IMG_ID, str_img_img_id);
        mCVArgs.put(BusinessAccessLayer.IMG_ID, str_img_id);
        mCVArgs.put(BusinessAccessLayer.IMG_NAME, str_img_name);
        mCVArgs.put(BusinessAccessLayer.IMG_ENCRYPTED_DATA, str_img_encrypted_data);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);


        return BusinessAccessLayer.mDb.insert(BusinessAccessLayer.DB_TABLE_TRN_IMAGES, null,
                mCVArgs);

    }

    public boolean update_image(String str_img_img_id, String str_img_id,
                                String str_img_name,
                                String str_img_encrypted_data,
                                String str_flag,
                                String str_sync_status,
                                String str_created_by,
                                String str_created_on,
                                String str_modified_by,
                                String str_modified_on) {

        ContentValues mCVArgs = new ContentValues();
        //  mCVArgs.put(BusinessAccessLayer.IMG_NAME, str_img_name);
        mCVArgs.put(BusinessAccessLayer.IMG_ENCRYPTED_DATA, str_img_encrypted_data);
        mCVArgs.put(BusinessAccessLayer.FLAG, str_flag);
        mCVArgs.put(BusinessAccessLayer.SYNC_STATUS, str_sync_status);
        mCVArgs.put(BusinessAccessLayer.CREATED_BY, str_created_by);
        mCVArgs.put(BusinessAccessLayer.CREATED_ON, str_created_on);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_BY, str_modified_by);
        mCVArgs.put(BusinessAccessLayer.MODIFIED_ON, str_modified_on);

        String updateQuery = "" + BusinessAccessLayer.IMG_ID
                + " = '" + str_img_id + "' and " + BusinessAccessLayer.IMG_IMG_ID + " = '" + str_img_img_id + "' and " + BusinessAccessLayer.IMG_NAME + " = '" + str_img_name + "'";

        System.out.println("update query:>>" + updateQuery);

        return BusinessAccessLayer.mDb.update(BusinessAccessLayer.DB_TABLE_TRN_IMAGES,
                mCVArgs, updateQuery, null) > 0;

    }

    /**
     * @param – str_img_id
     * @return – IMAGES Data
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 04:07:47 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search based on the eqid
     */
    public Cursor fetchByEq_Enroll_Id(String str_img_id) throws SQLException {
        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
                + BusinessAccessLayer.IMG_ID + " = '" + str_img_id + "';";
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByImgImg_ID(String imageId, String imh_imgId, String imgName) {

//        String Sign = "Select * from " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
//                + BusinessAccessLayer.IMG_ID + " = '" + imageId + "' AND  " + BusinessAccessLayer.IMG_NAME + " = '"
//                + imgName + "';";

        String Sign = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
                + BusinessAccessLayer.IMG_IMG_ID + " = '" + imageId + "' and " + BusinessAccessLayer.IMG_ID + " = '" + imh_imgId + "' and " + BusinessAccessLayer.IMG_NAME + " = '"
                + imgName + "';";
        System.out.println("Sign: "+Sign);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(Sign, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchByEq_Enroll_Id_Type(String imageId, String imgType) {

        String Sign = "Select * from " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
                + BusinessAccessLayer.IMG_ID + " = '" + imageId + "' AND  " + BusinessAccessLayer.IMG_NAME + " = '"
                + imgType + "' AND " + BusinessAccessLayer.FLAG + " in ('0','1');";
        System.out.println("Sign  :::" + Sign);
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
     * @Created_On 08-12-2015 at 04:08:40 pm
     * @Updated_By
     * @Updated_On
     * @Description return values of search
     */

    public Cursor fetch() throws SQLException {
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery("SELECT * FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_IMAGES, null);
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
     * @Created_On 08-12-2015 at 04:09:41 pm
     * @Updated_By
     * @Updated_On
     * @Description return status of deleted values
     */
    public Cursor deleteAll() throws SQLException {
        Cursor mCursorDeleteAll = BusinessAccessLayer.mDb.rawQuery("Delete FROM "
                + BusinessAccessLayer.DB_TABLE_TRN_IMAGES, null);
        if (mCursorDeleteAll != null) {
            mCursorDeleteAll.moveToFirst();
        }
        return mCursorDeleteAll;
    }


    /**
     * @param – str_img_id
     * @return – the Deleted Value Status in the Database table
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 04:10:15 pm
     * @Updated_By
     * @Updated_On
     * @Description return deleted Hospital  based on the str_img_id
     */
    public Cursor updateBy_Img_Id(String str_img_id) throws SQLException {

//        String deleteByUser_Id = "DELETE FROM "
//                + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " WHERE "
//                + BusinessAccessLayer.IMG_ID + " = '" + str_img_id + "';";


        String deleteByUser_Id = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " SET "
                + BusinessAccessLayer.FLAG + " = '2' , " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.IMG_ID + " = '" + str_img_id + "';";

        Cursor mCursorById = BusinessAccessLayer.mDb.rawQuery(deleteByUser_Id, null);
        if (mCursorById != null) {
            mCursorById.moveToFirst();
        }
        return mCursorById;

    }


    public Cursor updateBy_Img_Id_Type(String imageId, String imgType) {

//        String Sign = "DELETE FROM " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
//                + BusinessAccessLayer.IMG_ID + " = '" + imageId + "' AND  " + BusinessAccessLayer.IMG_NAME + " = '"
//                + imgType + "';";

        String delteQuery = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " SET "
                + BusinessAccessLayer.FLAG + " = '2', " + BusinessAccessLayer.SYNC_STATUS + "='0' where " + BusinessAccessLayer.IMG_ID + " = '" + imageId + "' AND  " + BusinessAccessLayer.IMG_NAME + " = '"
                + imgType + "';";
        System.out.println("Update flag:" + delteQuery);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(delteQuery, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor deleteByImg_Id_Type(String imageId,String eq_id, String imgType) {

        String delteQuery = "DELETE FROM " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
                + BusinessAccessLayer.IMG_IMG_ID + " = '" + imageId + "' AND  " + BusinessAccessLayer.IMG_NAME + " = '"
                + imgType + "' and "+BusinessAccessLayer.IMG_ID+"='"+eq_id+"';";
/*
        String delteQuery = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " SET "
                + BusinessAccessLayer.FLAG + " = '2' where " + BusinessAccessLayer.IMG_ID + " = '" + imageId + "' AND  " + BusinessAccessLayer.IMG_NAME + " = '"
                + imgType + "';";*/
        System.out.println("Update flag:" + delteQuery);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(delteQuery, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
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
     * @param – no params
     * @return – the database table values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 08-12-2015 at 04:11:25 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return based on the sync status
     */

    public Cursor fetchBySyncStatus() throws SQLException {

        String query = "Select " + BusinessAccessLayer.IMG_ID + "," + BusinessAccessLayer.IMG_IMG_ID + "," + BusinessAccessLayer.IMG_NAME + " from "
                + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
                + BusinessAccessLayer.SYNC_STATUS + " = '0';";

        System.out.println("fetchBySyncStatus "+query);
        Cursor mCursorFetch = BusinessAccessLayer.mDb.rawQuery(query, null);
        if (mCursorFetch != null) {
            mCursorFetch.moveToFirst();
        }
        return mCursorFetch;
    }

    public Cursor fetchBySyncStatusbeforesync() throws SQLException {

        String query = "Select * from "
                + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " where "
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
     * @Created_On 08-12-2015 at 04:11:25 pm
     * @Updated_By
     * @Updated_On
     * @Description The Values have return updated table values based on the sync status
     */
    public void updateSyncStatus(String str_img_id, String str_img_img_id, String str_imgName) throws SQLException {

        Cursor mCursor;
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1' where " + BusinessAccessLayer.IMG_ID + " = '" + str_img_id
                + "' and " + BusinessAccessLayer.IMG_IMG_ID + " = '" + str_img_img_id + "' and " + BusinessAccessLayer.IMG_NAME + " = '" + str_imgName + "';";
        System.out.println("syncStatus_query:" + syncStatus_query);
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
        String syncStatus_query = "UPDATE " + BusinessAccessLayer.DB_TABLE_TRN_IMAGES + " SET "
                + BusinessAccessLayer.SYNC_STATUS + " = '1', " + BusinessAccessLayer.FLAG + " = '" + str_flagStatus + "' where " + BusinessAccessLayer.IMG_ID + " = '" + str_doc_id
                + "';";

        mCursor = BusinessAccessLayer.mDb.rawQuery(syncStatus_query, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        mCursor.close();

    }

}
