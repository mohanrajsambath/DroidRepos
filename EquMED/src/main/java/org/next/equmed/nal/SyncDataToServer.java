package org.next.equmed.nal;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.dal.Mst_Equipment_Status;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Equipment_Enroll_Accessories;
import org.next.equmed.dal.Trn_Equipment_Enrollment;
import org.next.equmed.dal.Trn_Images;
import org.next.equmed.dal.Trn_Installation_Enrollment;
import org.next.equmed.dal.Trn_User_Registration;

/**
 * Created by nextmoveo-1 on 8/12/15.
 */
public class  SyncDataToServer extends Activity {

    private JSONArray synEquip_Enrollement_AccessoriesArray, syncEquip_EnrollementArray, syncInstallation_EnrollementArray,
            syncUser_RegistrationArray, syncMst_Equip_StatusArray, syncMst_HospitalArray, syncTrn_ImagesArray, syncTrn_DocArray;
    JSONObject syncObject = new JSONObject();

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

    }

    public void syncDataToServer() throws JSONException {
        /**
         * @CreatedBy R.Sathish KUmar,
         * @CreatedOn 8-Dec-2015,4:05:38 pm,
         * @UpdatedBy
         * @UpdatedOn
         * @Description
         */

        synEquip_Enrollement_AccessoriesArray = new JSONArray();
        syncEquip_EnrollementArray = new JSONArray();
        syncInstallation_EnrollementArray = new JSONArray();
        syncUser_RegistrationArray = new JSONArray();
        syncMst_Equip_StatusArray = new JSONArray();
        syncMst_HospitalArray = new JSONArray();
        syncTrn_ImagesArray = new JSONArray();
        syncTrn_DocArray = new JSONArray();

        try {
            //Sync Trn_Equip_Enroll_Acc Table to Server
            Trn_Equipment_Enroll_Accessories trn_equip_enroll_access = new Trn_Equipment_Enroll_Accessories( this );
            trn_equip_enroll_access.open();
            Cursor mCr_equip_enroll_access = trn_equip_enroll_access.fetchBySyncStatus();

            if ( mCr_equip_enroll_access.getCount() > 0 ) {
                for ( int t_acess = 0; t_acess < mCr_equip_enroll_access.getCount(); t_acess++ ) {
                    mCr_equip_enroll_access.moveToPosition( t_acess );
                    JSONObject syncTrn_Enrollement_Accessories_JsonObj = new JSONObject();

                    String enroll_id = mCr_equip_enroll_access.getString( mCr_equip_enroll_access
                            .getColumnIndex( "" + BusinessAccessLayer.EQ_ENROLL_ID + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.EQ_ENROLL_ID, enroll_id != null ? enroll_id : null );

                    String enroll_ups = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.EQ_ENROLL_UPS + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.EQ_ENROLL_UPS, enroll_ups != null ? enroll_ups : null );

                    String enroll_manual = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.EQ_ENROLL_MANUAL + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.EQ_ENROLL_MANUAL, enroll_manual != null ? enroll_manual : null );

                    String enroll_stabilizer = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.EQ_ENROLL_STABILIZER + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.EQ_ENROLL_STABILIZER, enroll_stabilizer != null ? enroll_stabilizer : null );

                    String enroll_others = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.EQ_ENROLL_OTHERS + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.EQ_ENROLL_OTHERS, enroll_others != null ? enroll_others : null );

                    // flag

                    String enroll_flag = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.FLAG + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.FLAG, enroll_flag != null ? enroll_flag : null );

                    String enroll_isactive = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.ISACTIVE, enroll_isactive != null ? enroll_isactive : null );

                    String enroll_syncstatus = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.SYNC_STATUS + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.SYNC_STATUS, enroll_syncstatus != null ? enroll_syncstatus : null );


                    String enroll_createdby = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.CREATED_BY + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.CREATED_BY, enroll_createdby != null ? enroll_createdby : null );

                    String enroll_createdon = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.CREATED_ON + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.CREATED_ON, enroll_createdon != null ? enroll_createdon : null );

                    String enroll_modifiedby = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_BY + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.MODIFIED_BY, enroll_modifiedby != null ? enroll_modifiedby : null );

                    String enroll_modifiedon = mCr_equip_enroll_access.getString( mCr_equip_enroll_access.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_ON + "" ) );
                    syncTrn_Enrollement_Accessories_JsonObj.put( BusinessAccessLayer.MODIFIED_ON, enroll_modifiedon != null ? enroll_modifiedon : null );

                    synEquip_Enrollement_AccessoriesArray.put( syncTrn_Enrollement_Accessories_JsonObj );

                }
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES, synEquip_Enrollement_AccessoriesArray );

            } else {
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES, synEquip_Enrollement_AccessoriesArray );
            }
            Trn_Equipment_Enroll_Accessories.close();
        } finally {
            Trn_Equipment_Enroll_Accessories.close();
        }/*     Trn_Equipment_Enroll_Accessories.close();     */



        /*
         * @Description  Sync Trn_Equip_Enrollment Table to Server
         */
        try {
            Trn_Equipment_Enrollment trn_enrollment = new Trn_Equipment_Enrollment( SyncDataToServer.this );

            trn_enrollment.open();
            Cursor mCr_enroll = trn_enrollment.fetchBySyncStatus();

            if ( mCr_enroll.getCount() > 0 ) {
                for ( int enroll = 0; enroll > mCr_enroll.getCount(); enroll++ ) {
                    mCr_enroll.moveToPosition( enroll );
                    JSONObject sync_trn_enroll_JsonObj = new JSONObject();

                    String enroll_id = mCr_enroll.getString( mCr_enroll
                            .getColumnIndex( "" + BusinessAccessLayer.EQ_ENROLL_ID + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_ENROLL_ID, enroll_id != null ? enroll_id : null );

                    String eq_id = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_ID + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_ID, eq_id != null ? eq_id : null );

                    String eq_hospital_id = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_HOSPITAL_ID + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_HOSPITAL_ID, eq_hospital_id != null ? eq_hospital_id : null );


                    String eq_location_code = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_LOCATION_CODE + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_LOCATION_CODE, eq_location_code != null ? eq_location_code : null );

                    String eq_serialno = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_SERIALNO + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_SERIALNO, eq_serialno != null ? eq_serialno : null );

//
//                    String eq_name = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_NAME + "" ) );
//                    sync_trn_enroll_JsonObj.put( "EQ_NAME", eq_name != null ? eq_name : null );


                    String eq_make = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_MAKE + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_MAKE, eq_make != null ? eq_make : null );


                    String eq_model = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_MODEL + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_MODEL, eq_model != null ? eq_model : null );


                    String eq_installdate = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_INSTALL_DATE + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_INSTALL_DATE, eq_installdate != null ? eq_installdate : null );

                    String eq_servicetag = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_SERVICE_TAGNO + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_SERVICE_TAGNO, eq_servicetag != null ? eq_servicetag : null );


                    String eq_notes = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_NOTES + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_NOTES, eq_notes != null ? eq_notes : null );


                    String eq_installstatus = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_INSTALL_STATUS + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_INSTALL_STATUS, eq_installstatus != null ? eq_installstatus : null );


                    String eq_installnotes = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_INSTALL_NOTES + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_INSTALL_NOTES, eq_installnotes != null ? eq_installnotes : null );

//                    String eq_extra_accessories = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_EXTRA_ACCESSORIES + "" ) );
//                    sync_trn_enroll_JsonObj.put( "EQ_EXTRA_ACCESSORIES", eq_extra_accessories != null ? eq_extra_accessories : null );

                    String eq_workingstatus = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_WORKING_STATUS + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_WORKING_STATUS, eq_workingstatus != null ? eq_workingstatus : null );

                    String eq_workingnotes = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.EQ_WORKING_NOTES + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.EQ_WORKING_NOTES, eq_workingnotes != null ? eq_workingnotes : null );


//                    String eq_workingnotes_isactive = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
//                    sync_trn_enroll_JsonObj.put( "ISACTIVE", eq_workingnotes_isactive != null ? eq_workingnotes_isactive : null );

//                    String eq_syncstatus = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.SYNC_STATUS + "" ) );
//                    sync_trn_enroll_JsonObj.put( "SYNC_STATUS", eq_syncstatus != null ? eq_syncstatus : null );
//flag
                    String eq_flag = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.FLAG + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.FLAG, eq_flag != null ? eq_flag : null );


                    String eq_createdby = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.CREATED_BY + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.CREATED_BY, eq_createdby != null ? eq_createdby : null );

                    String eq_createdon = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.CREATED_ON + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.CREATED_ON, eq_createdon != null ? eq_createdon : null );

                    String eq_modifiedby = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_BY + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.MODIFIED_BY, eq_modifiedby != null ? eq_modifiedby : null );

                    String eq_modifiedon = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_ON + "" ) );
                    sync_trn_enroll_JsonObj.put( BusinessAccessLayer.MODIFIED_ON, eq_modifiedon != null ? eq_modifiedon : null );

                    syncEquip_EnrollementArray.put( sync_trn_enroll_JsonObj );

                }
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL, syncEquip_EnrollementArray );
            } else {
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL, syncEquip_EnrollementArray );
            }
            Trn_Equipment_Enrollment.close();
        } finally {
            Trn_Equipment_Enrollment.close();

        } //Trn_Equipment_Enrollment Table End


 /*
         * @Description  Sync Trn_Installation_Enrollment Table to Server
         */
        try {
            Trn_Installation_Enrollment trn_install_enroll = new Trn_Installation_Enrollment( SyncDataToServer.this );

            trn_install_enroll.open();
            Cursor mCr_install_enroll = trn_install_enroll.fetchBySyncStatus();
            if ( mCr_install_enroll.getCount() > 0 ) {
                for ( int install_enroll = 0; install_enroll > mCr_install_enroll.getCount(); install_enroll++ ) {
                    mCr_install_enroll.moveToPosition( install_enroll );
                    JSONObject sync_trn_install_enroll_JsonObj = new JSONObject();


                    String inst_id = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_INSTALL_DATE + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_INSTALL_DATE, inst_id != null ? inst_id : null );

                    String inst_equipenroll = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID, inst_equipenroll != null ? inst_equipenroll : null );

                    String inst_company = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_COMPANY_BY + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_COMPANY_BY, inst_company != null ? inst_company : null );

                    String inst_engg_name = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_ENGG_NAME + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_ENGG_NAME, inst_engg_name != null ? inst_engg_name : null );

                    String inst_location = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_LOCATION + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_LOCATION, inst_location != null ? inst_location : null );

                    String inst_nearby_location = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_NEAR_BY_PHONENO + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_NEAR_BY_PHONENO, inst_nearby_location != null ? inst_nearby_location : null );

                    String inst_notes = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_NOTES + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_NOTES, inst_notes != null ? inst_notes : null );

                    String inst_area = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_AREA + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_AREA, inst_area != null ? inst_area : null );


                    String inst_ownername = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_OWNER_NAME + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_OWNER_NAME, inst_ownername != null ? inst_ownername : null );

                    String inst_ownerphonename = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.INST_OWNER_PHONENO + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.INST_OWNER_PHONENO, inst_ownerphonename != null ? inst_ownerphonename : null );


//                    String inst_isactive = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
//                    sync_trn_install_enroll_JsonObj.put( "ISACTIVE", inst_isactive != null ? inst_isactive : null );

                    // flag
                    String inst_flag = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.FLAG + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.FLAG, inst_flag != null ? inst_flag : null );


                    String inst_syncstatus = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.SYNC_STATUS + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.SYNC_STATUS, inst_syncstatus != null ? inst_syncstatus : null );


                    String inst_createdby = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.CREATED_BY + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.CREATED_BY, inst_createdby != null ? inst_createdby : null );

                    String inst_createdon = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.CREATED_ON + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.CREATED_ON, inst_createdon != null ? inst_createdon : null );

                    String inst_modifiedby = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_BY + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.MODIFIED_BY, inst_modifiedby != null ? inst_modifiedby : null );

                    String inst_modifiedon = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_ON + "" ) );
                    sync_trn_install_enroll_JsonObj.put( BusinessAccessLayer.MODIFIED_ON, inst_modifiedon != null ? inst_modifiedon : null );


                    syncInstallation_EnrollementArray.put( sync_trn_install_enroll_JsonObj );


                }
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL, syncInstallation_EnrollementArray );


            } else {
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL, syncInstallation_EnrollementArray );
            }
            Trn_Installation_Enrollment.close();

        } finally {
            Trn_Installation_Enrollment.close();
        }//Trn_Installation_Enrollment Table End

/*
         * @Description  Sync Trn_User_Registration Table to Server
         */

        try {

            Trn_User_Registration trn_user_registration = new Trn_User_Registration( SyncDataToServer.this );
            trn_user_registration.open();
            Cursor mCr_userregistration = trn_user_registration.fetchBySyncStatus();
            if ( mCr_userregistration.getCount() > 0 ) {
                for ( int user_registration = 0; user_registration > mCr_userregistration.getCount(); user_registration++ ) {
                    mCr_userregistration.moveToPosition( user_registration );
                    JSONObject sync_trn_user_registration = new JSONObject();

                    String user_id = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_ID + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_ID, user_id != null ? user_id : null );

                    String user_email = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_EMAIL + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_EMAIL, user_email != null ? user_email : null );

                    String user_firstname = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_FIRST_NAME + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_FIRST_NAME, user_firstname != null ? user_firstname : null );

                    String user_lastname = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_LAST_NAME + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_LAST_NAME, user_lastname != null ? user_lastname : null );

                    String user_phoneno = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_PHONENO + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_PHONENO, user_phoneno != null ? user_phoneno : null );

                    String user_password = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_PASSWORD + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_PASSWORD, user_password != null ? user_password : null );

                    String user_image = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_IMAGE + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_IMAGE, user_image != null ? user_image : null );

                    String user_admin = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_ADMIN + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_ADMIN, user_admin != null ? user_admin : null );

                    String user_isactive = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.ISACTIVE, user_isactive != null ? user_isactive : null );

                    String user_hospital = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_HOSPITAL + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_HOSPITAL, user_hospital != null ? user_hospital : null );

                    String user_startdate = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_EFFECT_STARTDATE + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_EFFECT_STARTDATE, user_startdate != null ? user_startdate : null );

                    String user_enddate = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.USER_EFFECT_ENDDATE + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.USER_EFFECT_ENDDATE, user_enddate != null ? user_enddate : null );

                    //flag
                    String user_flag = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.FLAG + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.FLAG, user_flag != null ? user_flag : null );


                    String user_syncstatus = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.SYNC_STATUS + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.SYNC_STATUS, user_syncstatus != null ? user_syncstatus : null );


                    String user_createdby = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.CREATED_BY + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.CREATED_BY, user_createdby != null ? user_createdby : null );

                    String user_createdon = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.CREATED_ON + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.CREATED_ON, user_createdon != null ? user_createdon : null );

                    String user_modifiedby = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_BY + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.MODIFIED_BY, user_modifiedby != null ? user_modifiedby : null );

                    String user_modifiedon = mCr_userregistration.getString( mCr_userregistration.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_ON + "" ) );
                    sync_trn_user_registration.put( BusinessAccessLayer.MODIFIED_ON, user_modifiedon != null ? user_modifiedon : null );

                    syncUser_RegistrationArray.put( sync_trn_user_registration );
                }
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION, syncUser_RegistrationArray );

            } else {
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION, syncUser_RegistrationArray );
            }
            Trn_User_Registration.close();
        } finally {
            Trn_User_Registration.close();
        }//Trn_User_Registration End

       /*
         * @Description  Sync Mst_Equipment_Status Table to Server
         */


        try {
            Mst_Equipment_Status mst_eqip_status = new Mst_Equipment_Status( SyncDataToServer.this );
            mst_eqip_status.open();
            Cursor mCt_mst_equipment_status = mst_eqip_status.fetchBySyncStatus();
            if ( mCt_mst_equipment_status.getCount() > 0 ) {
                for ( int mst_equipstatus = 0; mst_equipstatus > mCt_mst_equipment_status.getCount(); mst_equipstatus++ ) {
                    mCt_mst_equipment_status.moveToPosition( mst_equipstatus );
                    JSONObject sync_mst_equip_status = new JSONObject();

                    String eq_id = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.EQ_ID + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.EQ_ID, eq_id != null ? eq_id : null );


                    String eq_name = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.EQ_NAME + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.EQ_NAME, eq_name != null ? eq_name : null );
//flag

                    String eq_flag = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.FLAG + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.FLAG, eq_flag != null ? eq_flag : null );

                    String equipstatus_isactive = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.ISACTIVE, equipstatus_isactive != null ? equipstatus_isactive : null );

                    String equipstatus_syncstatus = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.SYNC_STATUS + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.SYNC_STATUS, equipstatus_syncstatus != null ? equipstatus_syncstatus : null );


                    String equipstatus_createdby = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.CREATED_BY + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.CREATED_BY, equipstatus_createdby != null ? equipstatus_createdby : null );

                    String equipstatus_createdon = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.CREATED_ON + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.CREATED_ON, equipstatus_createdon != null ? equipstatus_createdon : null );

                    String equipstatus_modifiedby = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_BY + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.MODIFIED_BY, equipstatus_modifiedby != null ? equipstatus_modifiedby : null );

                    String equipstatus_modifiedon = mCt_mst_equipment_status.getString( mCt_mst_equipment_status.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_ON + "" ) );
                    sync_mst_equip_status.put( BusinessAccessLayer.MODIFIED_ON, equipstatus_modifiedon != null ? equipstatus_modifiedon : null );


                    syncMst_Equip_StatusArray.put( sync_mst_equip_status );
                }

                syncObject.put( BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS, syncMst_Equip_StatusArray );

            } else {
                syncObject.put( BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS, syncMst_Equip_StatusArray );
            }
            Mst_Equipment_Status.close();

        } finally {
            Mst_Equipment_Status.close();
        }// Mst_Equipment_Status Table End

 /*
         * @Description  Sync Mst_Hospital_Enrollment Table to Server
         */

        try {
            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment( SyncDataToServer.this );
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchBySyncStatus();
            if ( mCr_mst_hospital_enroll.getCount() > 0 ) {
                for ( int mst_hospitalenroll = 0; mst_hospitalenroll > mCr_mst_hospital_enroll.getCount(); mst_hospitalenroll++ ) {
                    mCr_mst_hospital_enroll.moveToPosition( mst_hospitalenroll );
                    JSONObject sync_mst_hospital_enroll = new JSONObject();

                    String hospital_id = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_ID + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_ID, hospital_id != null ? hospital_id : null );


                    String hospital_name = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_NAME + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_NAME, hospital_name != null ? hospital_name : null );


                    String hospital_location = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_LOCATION + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_LOCATION, hospital_location != null ? hospital_location : null );

                    String hospital_desc = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_DESC + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_DESC, hospital_desc != null ? hospital_desc : null );


                    String hospital_address1 = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_ADDRESS1 + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_ADDRESS1, hospital_address1 != null ? hospital_address1 : null );

                    String hospital_address2 = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_ADDRESS2 + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_ADDRESS2, hospital_address2 != null ? hospital_address2 : null );

                    String hospital_address3 = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_ADDRESS3 + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_ADDRESS3, hospital_address3 != null ? hospital_address3 : null );

                    String hospital_state = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_STATE + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_STATE, hospital_state != null ? hospital_state : null );


                    String hospital_country = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_COUNTRY + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_COUNTRY, hospital_country != null ? hospital_country : null );

                    String hospital_phno1 = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_PHNO1 + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_PHNO1, hospital_phno1 != null ? hospital_phno1 : null );


                    String hospital_phno2 = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_PHNO2 + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_PHNO2, hospital_phno2 != null ? hospital_phno2 : null );


                    String hospital_email = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_EMAIL + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_EMAIL, hospital_email != null ? hospital_email : null );


                    String hospital_notes = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.HOSPITAL_NOTES + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.HOSPITAL_NOTES, hospital_notes != null ? hospital_notes : null );


                    String hospital_isactive = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.ISACTIVE, hospital_isactive != null ? hospital_isactive : null );

                    String hospital_syncstatus = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.SYNC_STATUS + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.SYNC_STATUS, hospital_syncstatus != null ? hospital_syncstatus : null );

                    //flag

                    String hospital_flag = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.FLAG + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.FLAG, hospital_flag != null ? hospital_flag : null );

                    String hospital_createdby = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.CREATED_BY + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.CREATED_BY, hospital_createdby != null ? hospital_createdby : null );

                    String hospital_createdon = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.CREATED_ON + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.CREATED_ON, hospital_createdon != null ? hospital_createdon : null );

                    String hospital_modifiedby = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_BY + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.MODIFIED_BY, hospital_modifiedby != null ? hospital_modifiedby : null );

                    String hospital_modifiedon = mCr_mst_hospital_enroll.getString( mCr_mst_hospital_enroll.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_ON + "" ) );
                    sync_mst_hospital_enroll.put( BusinessAccessLayer.MODIFIED_ON, hospital_modifiedon != null ? hospital_modifiedon : null );


                    syncMst_HospitalArray.put( sync_mst_hospital_enroll );
                }

                syncObject.put( BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL, syncMst_HospitalArray );

            } else {
                syncObject.put( BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL, syncMst_HospitalArray );
            }
            Mst_Hospital_Enrollment.close();

        } finally {
            Mst_Hospital_Enrollment.close();
        }// Mst_Hospital_Enrollment Table End


        try {
            Trn_Images trn_images = new Trn_Images( SyncDataToServer.this );

            trn_images.open();
            Cursor mCr_images = trn_images.fetchBySyncStatus();
            if ( mCr_images.getCount() > 0 ) {
                for ( int images = 0; images > mCr_images.getCount(); images++ ) {
                    mCr_images.moveToPosition( images );
                    JSONObject sync_trn_img_JsonObj = new JSONObject();


                    String img_id = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.IMG_ID + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.IMG_ID, img_id != null ? img_id : null );

                    String img_name = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.IMG_NAME + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.IMG_NAME, img_name != null ? img_name : null );

                    String img_encrypt_data = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.IMG_ENCRYPTED_DATA, img_encrypt_data != null ? img_encrypt_data : null );


                    String img_flag = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.FLAG + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.FLAG, img_flag != null ? img_flag : null );

                    String img_syncstatus = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.SYNC_STATUS + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.SYNC_STATUS, img_syncstatus != null ? img_syncstatus : null );


                    String img_createdby = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.CREATED_BY + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.CREATED_BY, img_createdby != null ? img_createdby : null );

                    String img_createdon = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.CREATED_ON + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.CREATED_ON, img_createdon != null ? img_createdon : null );

                    String img_modifiedby = mCr_images.getString( mCr_images.getColumnIndex( "" + BusinessAccessLayer.MODIFIED_BY + "" ) );
                    sync_trn_img_JsonObj.put( BusinessAccessLayer.MODIFIED_BY, img_modifiedby != null ? img_modifiedby : null );

                    syncTrn_ImagesArray.put( sync_trn_img_JsonObj );

                }
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_IMAGES, syncTrn_ImagesArray );
            }else
            {
                syncObject.put( BusinessAccessLayer.DB_TABLE_TRN_IMAGES, syncTrn_ImagesArray );
            }Trn_Images.close();

        }finally {
            Trn_Images.close();
        }// Trn Image Table Sync End





    }
}
