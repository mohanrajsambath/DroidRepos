/**
 * CopyRight (C) Next Techno EnterPrises To Present,
 * All Rights are Reserved,DashBoardActivity.java
 *
 * @author NEXT1
 * @author NEXT1
 */

/**@author NEXT1*/

package org.next.equmed.uil;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.next.equmed.R;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.CustomProgressDialog;
import org.next.equmed.dal.Mst_Equipment_Status;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Consumables;
import org.next.equmed.dal.Trn_Documents;
import org.next.equmed.dal.Trn_Equipment_Enroll_Accessories;
import org.next.equmed.dal.Trn_Equipment_Enrollment;
import org.next.equmed.dal.Trn_Images;
import org.next.equmed.dal.Trn_Installation_Enrollment;
import org.next.equmed.dal.Trn_Service_Details;
import org.next.equmed.dal.Trn_Training_Details;
import org.next.equmed.dal.Trn_User_Registration;
import org.next.equmed.dal.Trn_Voice_Of_Customer;
import org.next.equmed.dal.Trn_Warranty_Details;
import org.next.equmed.nal.HttpRequest;
import org.next.equmed.nal.NetworkAccessLayer_RESTFUL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import static org.next.equmed.bal.BusinessAccessLayer.*;

/**
 * @DevelopedBy Team Moveo,
 * @Date 20-Jul-2015,4:14:46 pm,
 * @Description
 */

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_Master, btn_userCreation, btn_medicalEnroll, btn_back, btn_viewMedical;

    TextView txt_adminTitle;
    private String userName = "";
    private String mRoleId = "";
    private String mUserSyncStatus, mEquip_Enroll_SyncStatus, mEquip_Enroll_AccessoriesSyncStatus, mHospitalSyncStatus,
            mEquipment_Status_SyncStatus, mImage_SyncStatus, mDocument_SyncStatus, mInstallation_EnrollmentSyncStatus, reponseMasterDatStr, mService_SyncStatus,
            mWarranty_SyncStatus, mTraining_SyncStatus, mConsumables_SyncStatus, mVoc_SyncStatus;


    private JSONArray synEquip_Enrollement_AccessoriesArray, syncEquip_EnrollementArray, syncInstallation_EnrollementArray,
            syncUser_RegistrationArray, syncMst_Equip_StatusArray, syncMst_HospitalArray, syncTrn_ImagesArray, syncTrn_DocArray,
            syncService_Array, syncWarranty_Array, syncTraining_Array, syncConsumables_Array, syncVoc_Array;

    JSONObject syncObject = new JSONObject();
    //private JSONObject reponseMasterDatStr;
    Toolbar toolbar_dashboard;
    private String result_MstJson_Str;
    private String mJSON_Str_for_Sync = "";

    private CustomProgressDialog cst_Progress;
    ImageView imageViewmov;
    private String filepath = "Database";
    Typeface calibri_typeface, calibri_bold_typeface;
    String abs_file_path;
    private static Dialog mContactUsDialog, mTechnicalUsDialog;
    private static String mFirstSync_DateTime = "2015/01/01 00:00:00";

    String ServerIP = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.dashboard_new);

        calibri_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri_Bold.ttf");

        BusinessAccessLayer.bug_class_name = "Dashboard";
        getViewCasting();
        setSupportActionBar(toolbar_dashboard);
        this.setTitle("EquMED - Dashboard");
        checkSharedPreference();
        if ( BusinessAccessLayer.mParetnRoleId.equalsIgnoreCase("Y")) {
            // Created as admin
            btn_Master.setVisibility(View.VISIBLE);
            btn_userCreation.setVisibility(View.VISIBLE);

        } else if ( BusinessAccessLayer.mParetnRoleId.equalsIgnoreCase("N")) {
            // Created as user
            btn_Master.setVisibility(View.GONE);
            btn_userCreation.setVisibility(View.GONE);

        } else {
            //temp admin
            btn_Master.setVisibility(View.VISIBLE);
            btn_userCreation.setVisibility(View.VISIBLE);
        }
        setUserDetails();

        exportDatabse(BusinessAccessLayer.DATABASE_NAME);
//        try {
//            loadAccess();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    private boolean checkSharedPreference() {
        File f = new File(
                "/data/data/org.next.equmed/shared_prefs/ServerDetails.xml");
        if (f.exists()) {
            SharedPreferences pref = getSharedPreferences("ServerDetails", 0);
            ServerIP = pref.getString("ServerIP", null);
            System.out.println("server IP in checkSharedPreference" + ServerIP);
            BusinessAccessLayer.BASE_IP = ServerIP;
            return true;

        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
//        return true;

        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        switch (item.getItemId()) {
//            case R.id.sync_server:
//                if (HttpRequest.isOnline(DashBoardActivity.this) == true) {
//
//                  //  Toast.makeText(getApplicationContext(),"Server not yet configured",Toast.LENGTH_SHORT).show();
//                    try {
//                        syncDataToServer();
//                        if (mJSON_Str_for_Sync.length() > 0 && !mJSON_Str_for_Sync.equals("{\"trn_equipment_enroll_accessories\":[],\"trn_equipment_enroll\":[],\"trn_installation_enroll\":[],\"trn_user_registration\":[],\"mst_equipment_status\":[],\"mst_hospital_enroll\":[],\"trn_images\":[],\"trn_documents\":[]}")
//                                &&!mJSON_Str_for_Sync.equalsIgnoreCase("{\"trn_equipment_enroll_accessories\":[],\"trn_documents\":[],\"trn_images\":[],\"mst_hospital_enroll\":[],\"mst_equipment_status\":[],\"trn_installation_enroll\":[],\"trn_user_registration\":[],\"trn_equipment_enroll\":[]}")) {
//
//                            System.out.println("Json String is in if----=>" + mJSON_Str_for_Sync);
//
//                            new Http_Upload_Sync_EqMED().execute();
//                        } else {
//                            System.out.println("Json String is Empty----=>" + mJSON_Str_for_Sync);
//                            Toast.makeText(DashBoardActivity.this, "No Data To Upload", Toast.LENGTH_LONG).show();
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                } else {
//                    Toast.makeText(DashBoardActivity.this, "Internet Connection Failed", Toast.LENGTH_SHORT).show();
//
//                }
//                return true;

            case R.id.retrieve_from_server:
                if (HttpRequest.isOnline(DashBoardActivity.this) == true) {
                    downLoadData();
                } else {
                    Toast.makeText(DashBoardActivity.this, "Internet connection failed", Toast.LENGTH_SHORT).show();

                }
                return true;

            case R.id.sync_to_odoo_server:
                if (HttpRequest.isOnline(DashBoardActivity.this) == true) {

                    //  Toast.makeText(getApplicationContext(),"Server not yet configured",Toast.LENGTH_SHORT).show();
                    try {
                        syncDataToServer();
                        if (mJSON_Str_for_Sync.length() > 0 && !mJSON_Str_for_Sync.equals("{\"trn_equipment_enroll_accessories\":[],\"trn_equipment_enroll\":[],\"trn_installation_enroll\":[],\"trn_user_registration\":[],\"mst_equipment_status\":[],\"mst_hospital_enroll\":[],\"trn_images\":[],\"trn_documents\":[],\"trn_service_details\":[],\"trn_warranty_details\":[],\"trn_training_details\":[],\"trn_consumables\":[],\"trn_voice_of_customer\":[]}")) {
                            System.out.println("Json String is in if----=>" + mJSON_Str_for_Sync);

                            new Http_Upload_Sync_EqMED_Odoo().execute();
                        } else {
                            System.out.println("Json String is Empty----=>" + mJSON_Str_for_Sync);
                            Toast.makeText(DashBoardActivity.this, "No data to upload", Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Toast.makeText(DashBoardActivity.this, "Internet connection failed", Toast.LENGTH_SHORT).show();

                }
                return true;
            case R.id.contact_Us:
                showTechincalUsDialog(DashBoardActivity.this);
//                CustomDialogClass.showTechincalUsDialog(DashBoardActivity.this);
               /* Intent contactus = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://www.nexttechnosolutions.com/" ) );
                startActivity( contactus );*/
                return true;

            case R.id.about_Us:
                showContactUsDialog(DashBoardActivity.this);
//                CustomDialogClass.showContactUsDialog(DashBoardActivity.this);
                /*Intent aboutus = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://www.nexttechnosolutions.com/" ) );
                startActivity( aboutus );*/
                return true;
            case R.id.log_out:
                gotoBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void downLoadData() {
//        isDBExists = checkDB();
//        System.out.println("isDBExists: " + isDBExists);
        if (checkSharedPreference() == true) {
            if (NetworkAccessLayer_RESTFUL.isOnline(DashBoardActivity.this) == true) {
                new Http_Reterive_MasterSync_EqMED().execute();
            } else {
                UserInterfaceLayer.alert(DashBoardActivity.this, "Please check your network connection", 4);
            }
        }
    }

    private boolean checkDB() {

        File database = getApplicationContext().getDatabasePath("EquMED.db");

        if (database.exists()) {

            return true;

        } else {
            return false;
        }
    }

    private class Http_Reterive_MasterSync_EqMED extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(DashBoardActivity.this);
            progress.setMessage("Fetching Data..");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            /*
            progress = new ProgressDialog(DashBoardActivity.this);
            progress.setMessage("Fetching Data...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setProgress(0);
            //   progress.setCancelable( true );
            progress.show();*/

            List<NameValuePair> args = new ArrayList<NameValuePair>();
            String last_s_date;

            SharedPreferences lastSyncDetails = getSharedPreferences("LastSyncInfo",
                    MODE_PRIVATE);
            if (lastSyncDetails.contains("lastSyncDate")) {
                last_s_date = lastSyncDetails.getString("lastSyncDate", null);
            } else {
                last_s_date = "2015/01/01 00:00:00";
            }

//            SharedPreferences sharedPreferences_syncdate = getPreferences(2);
//            String last_s_date = sharedPreferences_syncdate.getString("lastSyncDate",null);

            System.out.println("last_s_date " + last_s_date);
            args.add(new BasicNameValuePair(BusinessAccessLayer.LAST_SYNC_DATE, last_s_date));

            String URLforConnection = ServerIP + "" + BusinessAccessLayer.URL_RETRIEVE;
            System.out.println("JSON Result Admin_Login URLforConnection ====>" + URLforConnection);
            HttpRequest.sharedInstance().doHttpRequest(URLforConnection, args);
//            HttpRequest.sharedInstance().doHttpRequest(BusinessAccessLayer.URL_RETRIEVE, args);

            System.out.println("JSON Result URL_RETRIEVE  args ====>" + args);

            System.out.println("JSON Result URL_RETRIEVE  ====>" + BusinessAccessLayer.URL_RETRIEVE);

            result_MstJson_Str = HttpRequest.sharedInstance().responseString;

            return result_MstJson_Str;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            System.out.println("JSON Result String ====>" + result);
            progress.dismiss();

            try {
                JSONObject masterJsonObject = new JSONObject(result);
                String response_string = masterJsonObject.getString("response");

                JSONObject response_string_obj = new JSONObject(response_string);

              /*  if(response_string.equals("{\"trn_installation_enroll\": [], \"trn_user_registration\": [], \"trn_training_details\": [], \"mst_equipment_status\": [], \"mst_hospital_enroll\": [], \"trn_images\": [], \"trn_documents\": [], \"trn_warranty_details\": [], \"trn_voice_of_customer\": [], \"trn_equipment_enroll_accessories\": [], \"trn_service_details\": [], \"trn_consumables\": [], \"trn_equipment_enroll\": [], \"response\": {\"status\": \"NTE_01\", \"message\": \"success\", \"last_sync_date\": \"2016-03-24 06:58:39\"}}"))
                {
                    Toast.makeText( getBaseContext(), "Data's are up-to-date", Toast.LENGTH_SHORT ).show();
                }
                else
                {
                    Toast.makeText( getBaseContext(), "Data retrieved", Toast.LENGTH_SHORT ).show();
                }*/
                SharedPreferences lastSyncDate = getSharedPreferences("LastSyncInfo",
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = lastSyncDate.edit();
                editor.putString("lastSyncDate", response_string_obj.getString("last_sync_date"));
                editor.commit();

                //  mFirstSync_DateTime = response_string_obj.getString("last_sync_date");
              /*  System.out.println("response_string_obj.getString(\"last_sync_date\")"+response_string_obj.getString("last_sync_date"));
                System.out.println("last_retrieve_date "+mFirstSync_DateTime);*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  progress.dismiss();
            reponseMasterDatStr = result;
            getDataParaMeterFromJSONString();
        }
    }

    private void getDataParaMeterFromJSONString() {

        if (NetworkAccessLayer_RESTFUL.isOnline(DashBoardActivity.this)) {

            try {

                JSONObject masterJsonObject = new JSONObject(
                        reponseMasterDatStr);
                JSONArray mst_equipment_status = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS);
                JSONArray mst_hospital_enroll = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL);
                JSONArray trn_user_registration = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION);
                JSONArray trn_equipment_enroll = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL);
                JSONArray trn_installation_enroll = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL);

                JSONArray trn_service_details = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS);
                JSONArray trn_training_details = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS);
                JSONArray trn_warranty_details = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS);
                JSONArray trn_consumables = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES);
                JSONArray trn_voice_of_customer = masterJsonObject
                        .getJSONArray(BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER);


                if (mst_equipment_status.length() == 0 && mst_hospital_enroll.length() == 0 && trn_user_registration.length() == 0 && trn_installation_enroll.length() == 0 &&
                        trn_service_details.length() == 0 && trn_training_details.length() == 0 && trn_warranty_details.length() == 0 && trn_consumables.length() == 0 && trn_voice_of_customer.length() == 0) {
                    Toast.makeText(this, "Data's are up-to-date", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Data retrieved", Toast.LENGTH_SHORT).show();

                    if (mst_equipment_status.length() > 0) {
                        getMst_Equipment_Status(mst_equipment_status);
                    }
                    if (mst_hospital_enroll.length() > 0) {
                        getMst_Hospital_Enroll(mst_hospital_enroll);
                    }
                    if (trn_user_registration.length() > 0) {
                        getTrn_User_Registration(trn_user_registration);
                    }
                    if (trn_equipment_enroll.length() > 0) {
                        getTrn_Equipment_Enroll(trn_equipment_enroll);
                    }
                    if (trn_installation_enroll.length() > 0) {
                        getTrn_Installation_Enroll(trn_installation_enroll);
                    }
                    if (trn_service_details.length() > 0) {
                        getTrn_Service_Details(trn_service_details);
                    }
                    if (trn_training_details.length() > 0) {
                        getTrn_Training_Details(trn_training_details);
                    }
                    if (trn_warranty_details.length() > 0) {
                        getTrn_Warranty_Details(trn_warranty_details);
                    }
                    if (trn_consumables.length() > 0) {
                        getTrn_consumables(trn_consumables);
                    }
                    if (trn_voice_of_customer.length() > 0) {
                        getTrn_Voice_of_Customer(trn_voice_of_customer);
                    }
                }
            } catch (Exception e) {
                System.out.println("getDataParaMeterFromJSONString exception ::" + e);
            }

        }

    }


    private void getMst_Equipment_Status(JSONArray mst_equipment_status) {
        Cursor mCr_mst_equipstatus = null;

        try {
            for (int i = 0; i < mst_equipment_status.length(); i++) {
                JSONObject mst_equip_statusinput_json = mst_equipment_status.getJSONObject(i);

                String eq_id = mst_equip_statusinput_json.getString("eq_id");
                String eq_name = mst_equip_statusinput_json.getString("eq_name");
                String mflag = mst_equip_statusinput_json.getString("flag");
                String misactive = mst_equip_statusinput_json.getString("isactive");
                String misstandard = mst_equip_statusinput_json.getString("is_standard");
                String msync_status = mst_equip_statusinput_json.getString("sync_status");
                String mcreated_by = mst_equip_statusinput_json.getString("created_by");
                String mcreated_on = mst_equip_statusinput_json.getString("created_on");
                String mmodified_by = mst_equip_statusinput_json.getString("modified_by");
                String mmodified_on = mst_equip_statusinput_json.getString("modified_on");

                Mst_Equipment_Status mst_Equipmentstatus = new Mst_Equipment_Status(DashBoardActivity.this);
                mst_Equipmentstatus.open();
                mCr_mst_equipstatus = mst_Equipmentstatus.fetchByEq_Id(eq_id);
                if (mCr_mst_equipstatus.getCount() > 0) {
                    mst_Equipmentstatus.deleteBy_Eq_Id(eq_id);
                }
                mst_Equipmentstatus.insert_Equipment_Status_Enroll(eq_id, eq_name, mflag, misactive, misstandard, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);

                Mst_Equipment_Status.close();
            }
        } catch (Exception e) {
            System.out.println("Exception in Mst_Equipment_Status" + e);
        } finally {
            Mst_Equipment_Status.close();
        }
    }

    private void getMst_Hospital_Enroll(JSONArray mst_hospital_enroll) {

        Cursor cursorMst_Hospital_Enroll = null;
        String mCreated_On = "", mModified_On = "", mCreated_By = "", mModified_By = "";

        try {

            for (int i = 0; i < mst_hospital_enroll.length(); i++) {
                JSONObject mst_hospital_input_json = mst_hospital_enroll.getJSONObject(i);

                String mhospital_id = mst_hospital_input_json.getString("hospital_id");
                String mhospital_name = mst_hospital_input_json.getString("hospital_name");
                String mhospital_location = mst_hospital_input_json.getString("hospital_location");
                String mhospital_desc = mst_hospital_input_json.getString("hospital_desc");
                String mhospital_address1 = mst_hospital_input_json.getString("hospital_address1");
                String mhospital_address2 = mst_hospital_input_json.getString("hospital_address2");
                String mhospital_address3 = mst_hospital_input_json.getString("hospital_address3");
                String mhospital_state = mst_hospital_input_json.getString("hospital_state");
                String mhospital_country = mst_hospital_input_json.getString("hospital_country");
                String mhospital_phno1 = mst_hospital_input_json.getString("hospital_phno1");
                String mhospital_phno2 = mst_hospital_input_json.getString("hospital_phno2");
                String mhospital_email = mst_hospital_input_json.getString("hospital_email");
                String mhospital_notes = mst_hospital_input_json.getString("hospital_notes");
                String misactive = mst_hospital_input_json.getString("isactive");
                String mstandardEquipments = mst_hospital_input_json.getString("standard_equipments");
                String mflag = mst_hospital_input_json.getString("flag");
                String msync_status = mst_hospital_input_json.getString("sync_status");
                String mcreated_by = mst_hospital_input_json.getString("created_by");
                String mcreated_on = mst_hospital_input_json.getString("created_on");
                String mmodified_by = mst_hospital_input_json.getString("modified_by");
                String mmodified_on = mst_hospital_input_json.getString("modified_on");

                Mst_Hospital_Enrollment mstHospitalEnrollment = new Mst_Hospital_Enrollment(DashBoardActivity.this);
                mstHospitalEnrollment.open();
                cursorMst_Hospital_Enroll = mstHospitalEnrollment.fetchByHospital_Id(mhospital_id);

                if (cursorMst_Hospital_Enroll.getCount() > 0) {
                    mstHospitalEnrollment.deleteBy_Hospital_Id(mhospital_id);
                }
                mstHospitalEnrollment.insert_Hospital_Enroll(mhospital_id, mhospital_name, mhospital_location, mhospital_desc, mhospital_address1, mhospital_address2, mhospital_address3,
                        mhospital_state, mhospital_country, mhospital_phno1, mhospital_phno2, mhospital_email, mhospital_notes, misactive, mstandardEquipments, "1", mflag, mcreated_by, mcreated_on,
                        mmodified_by, mmodified_on);
                Mst_Hospital_Enrollment.close();


            }

        } catch (Exception e) {
            System.out.println("Exception in Mst_Hospital_Enrollment--=> " + e);
        } finally {
            Mst_Hospital_Enrollment.close();
        }


    }

    private void getTrn_Equipment_Enroll(JSONArray trn_equipment_enroll) {

        Cursor cursorTrn_Equipment_Enroll = null;
        String mCreated_On = "", mModified_On = "", mCreated_By = "", mModified_By = "";

        try {

            for (int i = 0; i < trn_equipment_enroll.length(); i++) {
                JSONObject trn_equipment_enroll_input_json = trn_equipment_enroll.getJSONObject(i);

                String meq_enroll_id = trn_equipment_enroll_input_json.getString("eq_enroll_id");
                String meq_id = trn_equipment_enroll_input_json.getString("eq_id");
                String meq_hospital_id = trn_equipment_enroll_input_json.getString("eq_hospital_id");
                String meq_location_code = trn_equipment_enroll_input_json.getString("eq_location_code");
                String meq_gps_coordinates = trn_equipment_enroll_input_json.getString("gps_coordinates");
                String meq_serialno = trn_equipment_enroll_input_json.getString("eq_serialno");
                String meq_make = trn_equipment_enroll_input_json.getString("eq_make");
                String meq_model = trn_equipment_enroll_input_json.getString("eq_model");
                String meq_install_date = trn_equipment_enroll_input_json.getString("eq_install_date");
                String meq_service_tagno = trn_equipment_enroll_input_json.getString("eq_service_tagno");
                String meq_notes = trn_equipment_enroll_input_json.getString("eq_notes");
                String meq_extra_accessories = trn_equipment_enroll_input_json.getString("eq_extra_accessories");
                String meq_install_status = trn_equipment_enroll_input_json.getString("eq_install_status");
                String meq_install_notes = trn_equipment_enroll_input_json.getString("eq_install_notes");
                String meq_working_condition = trn_equipment_enroll_input_json.getString("eq_working_condition");
                String meq_working_notes = trn_equipment_enroll_input_json.getString("eq_working_notes");
                String msync_status = trn_equipment_enroll_input_json.getString("sync_status");
                String mflag = trn_equipment_enroll_input_json.getString("flag");
                String mcreated_by = trn_equipment_enroll_input_json.getString("created_by");
                String mcreated_on = trn_equipment_enroll_input_json.getString("created_on");
                String mmodified_by = trn_equipment_enroll_input_json.getString("modified_by");
                String mmodified_on = trn_equipment_enroll_input_json.getString("modified_on");

                Trn_Equipment_Enrollment trnEqEnrollment = new Trn_Equipment_Enrollment(DashBoardActivity.this);
                trnEqEnrollment.open();
                cursorTrn_Equipment_Enroll = trnEqEnrollment.fetchByEq_Enroll_Id(meq_enroll_id);

                if (cursorTrn_Equipment_Enroll.getCount() > 0) {
                    trnEqEnrollment.deleteBy_Eq_Enroll_Id(meq_enroll_id);
                }
                trnEqEnrollment.insert_equipment_enroll(meq_enroll_id, meq_id, meq_hospital_id, meq_location_code, meq_gps_coordinates,
                        meq_serialno, meq_make, meq_model, meq_install_date, meq_service_tagno,
                        meq_notes, meq_extra_accessories, meq_install_status, meq_install_notes, meq_working_condition, meq_working_notes,
                        "1", mflag, mcreated_by, mcreated_on, mmodified_by, mmodified_on);


                Trn_Equipment_Enrollment.close();


            }
        } catch (Exception e) {
            System.out.println("Exception in Trn_Equipment_Enrollment--=> " + e);
        } finally {
            Trn_Equipment_Enrollment.close();
        }
    }

    private void getTrn_User_Registration(JSONArray trn_user_registration) {

        Cursor cursorTrn_User_Registration = null;
        String mCreated_On = "", mModified_On = "", mCreated_By = "", mModified_By = "";

        try {

            for (int i = 0; i < trn_user_registration.length(); i++) {
                JSONObject trn_user_registration_input_json = trn_user_registration.getJSONObject(i);

                String muser_id = trn_user_registration_input_json.getString("user_id");
                String muser_email = trn_user_registration_input_json.getString("user_email");
                String muser_first_name = trn_user_registration_input_json.getString("user_first_name");
                String muser_last_name = trn_user_registration_input_json.getString("user_last_name");
                String muser_phoneno = trn_user_registration_input_json.getString("user_phoneno");
                String muser_password = trn_user_registration_input_json.getString("user_password");
                String muser_image = trn_user_registration_input_json.getString("user_image");
                String muser_admin = trn_user_registration_input_json.getString("user_admin");
                String misactive = trn_user_registration_input_json.getString("isactive");
                String muser_hospital = trn_user_registration_input_json.getString("user_hospital");
                String muser_effect_startdate = trn_user_registration_input_json.getString("user_effect_startdate");
                String muser_effect_enddate = trn_user_registration_input_json.getString("user_effect_enddate");
                String mflag = trn_user_registration_input_json.getString("flag");
                String msync_status = trn_user_registration_input_json.getString("sync_status");
                String mcreated_by = trn_user_registration_input_json.getString("created_by");
                String mcreated_on = trn_user_registration_input_json.getString("created_on");
                String mmodified_by = trn_user_registration_input_json.getString("modified_by");
                String mmodified_on = trn_user_registration_input_json.getString("modified_on");


                Trn_User_Registration trnUserRegistration = new Trn_User_Registration(DashBoardActivity.this);
                trnUserRegistration.open();
                cursorTrn_User_Registration = trnUserRegistration.fetchByUser_Id(muser_id);

                if (cursorTrn_User_Registration.getCount() > 0) {
                    trnUserRegistration.deleteBy_User_Id(muser_id, muser_email);
                }
                trnUserRegistration.insert_User_Registration(muser_id, muser_email, muser_first_name, muser_last_name, muser_phoneno, muser_password, muser_image,
                        muser_admin, misactive, muser_hospital, muser_effect_startdate, muser_effect_enddate, misactive, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);

                Trn_User_Registration.close();


            }

        } catch (Exception e) {
            System.out.println("Exception in Trn_User_Registration--=> " + e);
        } finally {
            Trn_User_Registration.close();
        }


    }

    private void getTrn_Installation_Enroll(JSONArray trn_installation_enroll) {

        Cursor cursorTrn_Installation_Enroll = null;
        String mCreated_On = "", mModified_On = "", mCreated_By = "", mModified_By = "";

        try {

            for (int i = 0; i < trn_installation_enroll.length(); i++) {
                JSONObject trn_installation_input_json = trn_installation_enroll.getJSONObject(i);

                String minst_date = trn_installation_input_json.getString("inst_install_date");
                String minst_equipment_enroll_id = trn_installation_input_json.getString("inst_equipment_enroll_id");
                String minst_company_by = trn_installation_input_json.getString("inst_company_by");
                String minst_engg_name = trn_installation_input_json.getString("inst_engg_name");
                String minst_location = trn_installation_input_json.getString("inst_location");
                String minst_near_by_phoneno = trn_installation_input_json.getString("inst_near_by_phoneno");
                String minst_notes = trn_installation_input_json.getString("inst_notes");
                String minst_area = trn_installation_input_json.getString("inst_area");
                String minst_owner_name = trn_installation_input_json.getString("inst_owner_name");
                String minst_owner_phoneno = trn_installation_input_json.getString("inst_owner_phoneno");
                String mflag = trn_installation_input_json.getString("flag");
                String msync_status = trn_installation_input_json.getString("sync_status");
                String mcreated_by = trn_installation_input_json.getString("created_by");
                String mcreated_on = trn_installation_input_json.getString("created_on");
                String mmodified_by = trn_installation_input_json.getString("modified_by");
                String mmodified_on = trn_installation_input_json.getString("modified_on");

                Trn_Installation_Enrollment trnInstallationEnrollment = new Trn_Installation_Enrollment(DashBoardActivity.this);
                trnInstallationEnrollment.open();

                trnInstallationEnrollment.insert_installation_enroll(minst_equipment_enroll_id, minst_company_by, minst_engg_name, minst_date, minst_location, minst_near_by_phoneno, minst_notes,
                        minst_area, minst_owner_name, minst_owner_phoneno, mflag, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);
                Trn_Installation_Enrollment.close();
            }
        } catch (Exception e) {
            System.out.println("Exception in Trn_Installation_Enrollment--=> " + e);
        } finally {
            Trn_Installation_Enrollment.close();
        }
    }

    private void getTrn_Service_Details(JSONArray trn_service_detail) {
        try {
            for (int i = 0; i < trn_service_detail.length(); i++) {
                JSONObject trn_service_input_json = trn_service_detail.getJSONObject(i);
                String service_id = trn_service_input_json.getString("service_id");
                String service_equipment_enroll_id = trn_service_input_json.getString("service_equipment_enroll_id");
                String service_hospital_id = trn_service_input_json.getString("service_hospital_id");
                String service_equipment_id = trn_service_input_json.getString("service_equipment_id");
                String service_date_time = trn_service_input_json.getString("service_date_time");
                String service_duration = trn_service_input_json.getString("service_duration");
                String service_type = trn_service_input_json.getString("service_type");
                String service_notes = trn_service_input_json.getString("service_notes");
                String service_approved_by = trn_service_input_json.getString("service_approved_by");
                String service_invoice = trn_service_input_json.getString("service_invoice");
                String serviced_by = trn_service_input_json.getString("serviced_by");
                String mflag = trn_service_input_json.getString("flag");
                String msync_status = trn_service_input_json.getString("sync_status");
                String mcreated_by = trn_service_input_json.getString("created_by");
                String mcreated_on = trn_service_input_json.getString("created_on");
                String mmodified_by = trn_service_input_json.getString("modified_by");
                String mmodified_on = trn_service_input_json.getString("modified_on");

                Trn_Service_Details trn_service_details_Enroll = new Trn_Service_Details(DashBoardActivity.this);
                trn_service_details_Enroll.open();

                trn_service_details_Enroll.insert_service_details(service_id, service_equipment_enroll_id, service_hospital_id, service_equipment_id, service_date_time, service_duration, service_type,
                        service_notes, service_approved_by, service_invoice, serviced_by, mflag, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);

                trn_service_details_Enroll.close();
            }

        } catch (Exception e) {
            System.out.println("Exception in Trn_Service_Details--=> " + e);
        } finally {
            Trn_Service_Details.close();
        }
    }

    private void getTrn_Training_Details(JSONArray trn_training_details) {
        try {
            for (int i = 0; i < trn_training_details.length(); i++) {
                JSONObject trn_training_input_json = trn_training_details.getJSONObject(i);

                String training_id = trn_training_input_json.getString("training_id");
                String training_equipment_enroll_id = trn_training_input_json.getString("training_equipment_enroll_id");
                String training_hospital_id = trn_training_input_json.getString("training_hospital_id");
                String training_equipment_id = trn_training_input_json.getString("training_equipment_id");
                String training_date = trn_training_input_json.getString("training_date");
                String training_duration = trn_training_input_json.getString("training_duration");
                String training_description = trn_training_input_json.getString("training_description");
                String training_provided_by = trn_training_input_json.getString("training_provided_by");
                String training_invoice = trn_training_input_json.getString("training_invoice");

                String mflag = trn_training_input_json.getString("flag");
                String msync_status = trn_training_input_json.getString("sync_status");
                String mcreated_by = trn_training_input_json.getString("created_by");
                String mcreated_on = trn_training_input_json.getString("created_on");
                String mmodified_by = trn_training_input_json.getString("modified_by");
                String mmodified_on = trn_training_input_json.getString("modified_on");

                Trn_Training_Details trn_training_details_Enroll = new Trn_Training_Details(DashBoardActivity.this);
                trn_training_details_Enroll.open();
                trn_training_details_Enroll.insert_training_details(training_id, training_equipment_enroll_id, training_hospital_id, training_equipment_id, training_date, training_duration, training_description,
                        training_provided_by, training_invoice, mflag, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);
                trn_training_details_Enroll.close();
            }

        } catch (Exception e) {
            System.out.println("Exception in Trn_Training_Details--=> " + e);
        } finally {
            Trn_Training_Details.close();
        }
    }

    private void getTrn_Warranty_Details(JSONArray trn_warranty_details) {
        try {
            for (int i = 0; i < trn_warranty_details.length(); i++) {
                JSONObject trn_warranty_input_json = trn_warranty_details.getJSONObject(i);

                String warranty_id = trn_warranty_input_json.getString("warranty_id");
                String warranty_equipment_enroll_id = trn_warranty_input_json.getString("warranty_equipment_enroll_id");
                String warranty_hospital_id = trn_warranty_input_json.getString("warranty_hospital_id");
                String warranty_equipment_id = trn_warranty_input_json.getString("warranty_equipment_id");
                String warranty_start_date = trn_warranty_input_json.getString("warranty_start_date");
                String warranty_end_date = trn_warranty_input_json.getString("warranty_end_date");
                String warranty_description = trn_warranty_input_json.getString("warranty_description");
                String warranty_duration = trn_warranty_input_json.getString("warranty_duration");
                String warranty_type = trn_warranty_input_json.getString("warranty_type");

                String mflag = trn_warranty_input_json.getString("flag");
                String msync_status = trn_warranty_input_json.getString("sync_status");
                String mcreated_by = trn_warranty_input_json.getString("created_by");
                String mcreated_on = trn_warranty_input_json.getString("created_on");
                String mmodified_by = trn_warranty_input_json.getString("modified_by");
                String mmodified_on = trn_warranty_input_json.getString("modified_on");

                Trn_Warranty_Details trn_warranty_details_Enroll = new Trn_Warranty_Details(DashBoardActivity.this);
                trn_warranty_details_Enroll.open();
                trn_warranty_details_Enroll.insert_warranty_details(warranty_id, warranty_equipment_enroll_id, warranty_hospital_id, warranty_equipment_id, warranty_start_date, warranty_end_date, warranty_description,
                        warranty_duration, warranty_type, mflag, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);
                trn_warranty_details_Enroll.close();
            }

        } catch (Exception e) {
            System.out.println("Exception in Trn_Warranty_Details--=> " + e);
        } finally {
            Trn_Warranty_Details.close();
        }
    }

    private void getTrn_consumables(JSONArray trn_consumables) {
        try {
            for (int i = 0; i < trn_consumables.length(); i++) {
                JSONObject trn_consumables_input_json = trn_consumables.getJSONObject(i);

                String consumables_id = trn_consumables_input_json.getString("consumables_id");
                String consumables_equipment_enroll_id = trn_consumables_input_json.getString("consumables_equipment_enroll_id");
                String consumables_hospital_id = trn_consumables_input_json.getString("consumables_hospital_id");
                String consumables_equipment_id = trn_consumables_input_json.getString("consumables_equipment_id");
                String name = trn_consumables_input_json.getString("name");
                String description = trn_consumables_input_json.getString("description");
                String type_of_usage = trn_consumables_input_json.getString("type_of_usage");
                String usage_parameter = trn_consumables_input_json.getString("usage_parameter");
                String quantity = trn_consumables_input_json.getString("quantity");
                String consumables_uom = trn_consumables_input_json.getString("consumables_uom");
                String consumables_current_stock = trn_consumables_input_json.getString("consumables_current_stock");
                String consumable_notes = trn_consumables_input_json.getString("consumable_notes");

                String mflag = trn_consumables_input_json.getString("flag");
                String msync_status = trn_consumables_input_json.getString("sync_status");
                String mcreated_by = trn_consumables_input_json.getString("created_by");
                String mcreated_on = trn_consumables_input_json.getString("created_on");
                String mmodified_by = trn_consumables_input_json.getString("modified_by");
                String mmodified_on = trn_consumables_input_json.getString("modified_on");

                Trn_Consumables trn_consumables_details_Enroll = new Trn_Consumables(DashBoardActivity.this);
                trn_consumables_details_Enroll.open();
                trn_consumables_details_Enroll.insert_consumables(consumables_id, consumables_equipment_enroll_id, consumables_hospital_id, consumables_equipment_id, name, description, type_of_usage,
                        usage_parameter, quantity, consumables_uom, consumables_current_stock, consumable_notes, mflag, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);
                trn_consumables_details_Enroll.close();
            }

        } catch (Exception e) {
            System.out.println("Exception in Trn_Consumables--=> " + e);
        } finally {
            Trn_Consumables.close();
        }
    }

    private void getTrn_Voice_of_Customer(JSONArray trn_voice_of_customer) {
        try {
            for (int i = 0; i < trn_voice_of_customer.length(); i++) {
                JSONObject trn_voc_input_json = trn_voice_of_customer.getJSONObject(i);

                String voc_id = trn_voc_input_json.getString("voc_id");
                String voc_equipment_enroll_id = trn_voc_input_json.getString("voc_equipment_enroll_id");
                String voc_hospital_id = trn_voc_input_json.getString("voc_hospital_id");
                String voc_equipment_id = trn_voc_input_json.getString("voc_equipment_id");
                String type = trn_voc_input_json.getString("type");
                String in_brief = trn_voc_input_json.getString("in_brief");

                String mflag = trn_voc_input_json.getString("flag");
                String msync_status = trn_voc_input_json.getString("sync_status");
                String mcreated_by = trn_voc_input_json.getString("created_by");
                String mcreated_on = trn_voc_input_json.getString("created_on");
                String mmodified_by = trn_voc_input_json.getString("modified_by");
                String mmodified_on = trn_voc_input_json.getString("modified_on");

                Trn_Voice_Of_Customer trn_voc_details_Enroll = new Trn_Voice_Of_Customer(DashBoardActivity.this);
                trn_voc_details_Enroll.open();
                trn_voc_details_Enroll.insert_voc_details(voc_id, voc_equipment_enroll_id, voc_hospital_id, voc_equipment_id, type, in_brief, mflag, "1", mcreated_by, mcreated_on, mmodified_by, mmodified_on);
                trn_voc_details_Enroll.close();
            }
        } catch (Exception e) {
            System.out.println("Exception in Trn_Voice_Of_Customer--=> " + e);
        } finally {
            Trn_Voice_Of_Customer.close();
        }
    }


    public void showTechincalUsDialog(final Context ctx) {
        mTechnicalUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mTechnicalUsDialog.setContentView(R.layout.dialog_tech_us);
//        dialog.setTitle("Contact Us");
        mTechnicalUsDialog.setCancelable(true);

        TextView txtVw_title = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_title);

        TextView txtVw_addDetail = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_addDetail);

        TextView txtVw_phone = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_phone);

        final TextView txtVw_nextEmail = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_nextEmail);

        ImageView imgVw_technicalcall = (ImageView) mTechnicalUsDialog.findViewById(R.id.imgVw_technicalcall);

        ImageView imgVw_Email = (ImageView) mTechnicalUsDialog.findViewById(R.id.imgVw_Email);
        TextView tech_phone = (TextView) mTechnicalUsDialog.findViewById(R.id.tech_phone);
        TextView tech_email = (TextView) mTechnicalUsDialog.findViewById(R.id.tech_email);
        TextView txtVw_nxtAddress = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_nxtAddress);

        txtVw_title.setTypeface(calibri_bold_typeface);
        txtVw_addDetail.setTypeface(calibri_typeface);
        txtVw_phone.setTypeface(calibri_typeface);
        txtVw_nextEmail.setTypeface(calibri_typeface);
        tech_phone.setTypeface(calibri_typeface);
        tech_email.setTypeface(calibri_typeface);
        txtVw_nxtAddress.setTypeface(calibri_typeface);

        imgVw_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkAccessLayer_RESTFUL.isOnline(DashBoardActivity.this) == true) {
                    String emailTo = txtVw_nextEmail.getText().toString();

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
//                emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
//                /// use below 2 commented lines if need to use BCC an CC feature in email
//                //emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ to});
//                //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{to});
//                ////use below 3 commented lines if need to send attachment
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Welcome to Next Inc");

                    //need this to prompts email client only
                    emailIntent.setType("message/rfc822");

                    startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));
                } else {
                    UserInterfaceLayer.alert(DashBoardActivity.this, "Please check your network connection", 1);
                }


            }
        });

        imgVw_technicalcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + BusinessAccessLayer.mPhoneNo));
                startActivity(callIntent);

            }
        });
        mTechnicalUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mTechnicalUsDialog.show();
    }


    public void showContactUsDialog(final Context ctx) {
        mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_contact_us);
//        dialog.setTitle("Contact Us");
        mContactUsDialog.setCancelable(true);
        TextView txtVw_title = (TextView) mContactUsDialog.findViewById(R.id.txtVw_title);

        TextView txtVw_companyName = (TextView) mContactUsDialog.findViewById(R.id.txtVw_companyName);

        TextView txtVw_details = (TextView) mContactUsDialog.findViewById(R.id.txtVw_details);

        TextView txtVw_webLink = (TextView) mContactUsDialog.findViewById(R.id.txtVw_webLink);

        TextView txtVw_phone = (TextView) mContactUsDialog.findViewById(R.id.txtVw_phone);

        ImageView imgVw_call = (ImageView) mContactUsDialog.findViewById(R.id.imgVw_call);

        TextView txtVw_visitUs = (TextView) mContactUsDialog.findViewById(R.id.txtVw_visitUs);
        TextView txtVw_contactUs = (TextView) mContactUsDialog.findViewById(R.id.txtVw_contactUs);

        txtVw_title.setTypeface(calibri_bold_typeface);
        txtVw_companyName.setTypeface(calibri_bold_typeface);
        txtVw_details.setTypeface(calibri_typeface);
        txtVw_phone.setTypeface(calibri_typeface);
        txtVw_webLink.setTypeface(calibri_typeface);
        txtVw_visitUs.setTypeface(calibri_typeface);
        txtVw_contactUs.setTypeface(calibri_typeface);

        txtVw_webLink.setMovementMethod(LinkMovementMethod.getInstance());

        if (NetworkAccessLayer_RESTFUL.isOnline(DashBoardActivity.this) == true) {
            txtVw_webLink.setTextColor(Color.BLUE);
            txtVw_webLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent aboutusss = new Intent(Intent.ACTION_VIEW, Uri.parse("http://greentelemed.com.pg/"));
                    startActivity(aboutusss);

                }
            });
        } else {
            txtVw_webLink.setClickable(false);
            txtVw_webLink.setTextColor(Color.BLACK);
        }


        imgVw_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  String   mPhoneNo = txtVw_phone.getText().toString();

                //  String phoneno = 04222971111;
//                Intent in=new Intent(Intent.ACTION_CALL,Uri.parse(mPhoneNo));
//                ctx.startActivity(in);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + BusinessAccessLayer.mAboutusPhoneNo));
                startActivity(callIntent);
//                Intent callIntent = new Intent(Intent.ACTION_CALL);
//                callIntent.setData(Uri.parse("tel:" + R.string.phoneNo));
//                startActivity(callIntent);

            }
        });
        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mContactUsDialog.show();
    }

    private void getViewCasting() {

        btn_Master = (Button) findViewById(R.id.btn_Master);
        btn_userCreation = (Button) findViewById(R.id.btn_userCreation);
        btn_medicalEnroll = (Button) findViewById(R.id.btn_medicalEnroll);
        btn_viewMedical = (Button) findViewById(R.id.btn_viewMedical);
        txt_adminTitle = (TextView) findViewById(R.id.txt_adminTitle);
        toolbar_dashboard = (Toolbar) findViewById(R.id.toolbar_dashboard);
        imageViewmov = (ImageView) findViewById(R.id.imageViewmov);

        btn_Master.setTypeface(calibri_typeface);
        btn_userCreation.setTypeface(calibri_typeface);
        btn_medicalEnroll.setTypeface(calibri_typeface);
        btn_viewMedical.setTypeface(calibri_typeface);
        txt_adminTitle.setTypeface(calibri_typeface);


        getViewClickListener();
    }


    private void getViewClickListener() {

        btn_Master.setOnClickListener(this);
        btn_userCreation.setOnClickListener(this);
        btn_medicalEnroll.setOnClickListener(this);
        btn_viewMedical.setOnClickListener(this);
        imageViewmov.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_Master:
                Intent hospital = new Intent(DashBoardActivity.this, MasterActivity.class);
                startActivity(hospital);
                finish();
                break;
            case R.id.imageViewmov:
                CustomDialogClass.showAppalertDialog(DashBoardActivity.this);

                break;
            case R.id.btn_userCreation:
                Intent user = new Intent(DashBoardActivity.this, UserMasterActivity.class);
                startActivity(user);
                finish();
                break;

            case R.id.btn_medicalEnroll:

                Intent medical = new Intent(DashBoardActivity.this, MedicalEquipmentActivity.class);
                MEDICAL_EQUIPMENT_FLAG = 0;
                startActivity(medical);
                finish();
                break;
            case R.id.btn_viewMedical:

                if (BusinessAccessLayer.hospitalArray == null || BusinessAccessLayer.hospitalArray.length == 0) {

                    showMasterAlertDialog(DashBoardActivity.this, "No assigned hospitals are active");
                } else {
                    Intent viewDetails = new Intent(DashBoardActivity.this, ViewMedicalEquipAppCompat.class);
                    startActivity(viewDetails);
                    finish();
                }


                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
//        UserInterfaceLayer.alert( DashBoardActivity.this, BusinessAccessLayer.LOGOUT_MESSAGE, 4 );
        gotoBack();
    }

    private void gotoBack() {
        logoutDialog(DashBoardActivity.this, "Do you want to logout ? ");

    }

    /**
     *
     */
    private void setUserDetails() {
        SharedPreferences userDetails = getSharedPreferences("UserDetails",
                MODE_PRIVATE);
        userName = userDetails.getString("firstname", "");
        mRoleId = userDetails.getString("userId", "");


//        txt_adminTitle.setText("Welcome " + userName + ", ");
        txt_adminTitle.setText("Welcome " + userName);

        System.out.println("Entered Role ID--=>" + mRoleId);
    }

    /**
     * @CreatedBy MOhanraj.S
     * @CreatedOn 11-Dec-2015,11:30:19 am,
     * @UpdatedBy MOhanraj.S
     * @UpdatedOn 11-Dec-2015,11:43:38 am,
     * @Description
     */
    public void updateSyncStatusData() {


        Trn_User_Registration trn_User = new Trn_User_Registration(DashBoardActivity.this);
        trn_User.open();
        Cursor mCursor_User = trn_User.fetchBySyncStatus();
        if (mCursor_User.getCount() > 0) {
            for (int i = 0; i < mCursor_User.getCount(); i++) {
                mCursor_User.moveToPosition(i);
                mUserSyncStatus = mCursor_User.getString(mCursor_User
                        .getColumnIndex("" + BusinessAccessLayer.USER_ID
                                + ""));
                trn_User.updateSyncStatus(mUserSyncStatus);

            }
        }
        Trn_User_Registration.close();

        Trn_Equipment_Enrollment trn_Equipment_enroll = new Trn_Equipment_Enrollment(
                DashBoardActivity.this);
        trn_Equipment_enroll.open();
        Cursor mCursor_EquipInfo = trn_Equipment_enroll.fetchBySyncStatus();
        if (mCursor_EquipInfo.getCount() > 0) {
            for (int i = 0; i < mCursor_EquipInfo.getCount(); i++) {
                mCursor_EquipInfo.moveToPosition(i);
                mEquip_Enroll_SyncStatus = mCursor_EquipInfo
                        .getString(mCursor_EquipInfo.getColumnIndex(""
                                + BusinessAccessLayer.EQ_ENROLL_ID + ""));
                trn_Equipment_enroll.updateSyncStatus(mEquip_Enroll_SyncStatus);
            }
        }
        Trn_Equipment_Enrollment.close();

        Trn_Equipment_Enroll_Accessories trn_Equipment_enroll_accessories = new Trn_Equipment_Enroll_Accessories(DashBoardActivity.this);
        trn_Equipment_enroll_accessories.open();
        Cursor mCursor_Equip_Accessories = trn_Equipment_enroll_accessories.fetchBySyncStatus();
        if (mCursor_Equip_Accessories.getCount() > 0) {
            for (int i = 0; i < mCursor_Equip_Accessories.getCount(); i++) {
                mCursor_Equip_Accessories.moveToPosition(i);
                mEquip_Enroll_AccessoriesSyncStatus = mCursor_Equip_Accessories.getString(mCursor_Equip_Accessories
                        .getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_ID
                                + ""));
                trn_Equipment_enroll_accessories.updateSyncStatus(mEquip_Enroll_AccessoriesSyncStatus);
            }
        }
        Trn_Equipment_Enroll_Accessories.close();

        Trn_Installation_Enrollment trn_Installation_Enrollment = new Trn_Installation_Enrollment(DashBoardActivity.this);
        trn_Installation_Enrollment.open();
        Cursor mCursor_Installation = trn_Installation_Enrollment.fetchBySyncStatus();
        if (mCursor_Installation.getCount() > 0) {
            for (int i = 0; i < mCursor_Installation.getCount(); i++) {
                mCursor_Installation.moveToPosition(i);
                mInstallation_EnrollmentSyncStatus = mCursor_Installation.getString(mCursor_Installation
                        .getColumnIndex("" + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID
                                + ""));
                trn_Installation_Enrollment.updateSyncStatus(mInstallation_EnrollmentSyncStatus);
            }
        }
        Trn_Installation_Enrollment.close();


        Mst_Hospital_Enrollment mst_Hospital = new Mst_Hospital_Enrollment(DashBoardActivity.this);
        mst_Hospital.open();
        Cursor mCursor_Hospital = mst_Hospital.fetchBySyncStatus();
        if (mCursor_Hospital.getCount() > 0) {
            for (int i = 0; i < mCursor_Hospital.getCount(); i++) {
                mCursor_Hospital.moveToPosition(i);
                mHospitalSyncStatus = mCursor_Hospital.getString(mCursor_Hospital
                        .getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID
                                + ""));
                mst_Hospital.updateSyncStatus(mHospitalSyncStatus);
            }
        }
        Mst_Hospital_Enrollment.close();

        Mst_Equipment_Status mst_Equipment_Status = new Mst_Equipment_Status(
                DashBoardActivity.this);
        mst_Equipment_Status.open();
        Cursor mCursor_Department = mst_Equipment_Status.fetchBySyncStatus();
        if (mCursor_Department.getCount() > 0) {
            for (int i = 0; i < mCursor_Department.getCount(); i++) {
                mCursor_Department.moveToPosition(i);
                mEquipment_Status_SyncStatus = mCursor_Department
                        .getString(mCursor_Department.getColumnIndex(""
                                + BusinessAccessLayer.EQ_ID + ""));
                mst_Equipment_Status.updateSyncStatus(mEquipment_Status_SyncStatus);
            }
        }
        Mst_Equipment_Status.close();

        Trn_Images trn_Images = new Trn_Images(DashBoardActivity.this);
        trn_Images.open();
        Cursor mCursor_Images = trn_Images.fetchBySyncStatus();
        System.out.println("Total" + mCursor_Images.getCount());
        if (mCursor_Images.getCount() > 0) {
            for (int i = 0; i < mCursor_Images.getCount(); i++) {
                mCursor_Images.moveToPosition(i);
                mImage_SyncStatus = mCursor_Images.getString(mCursor_Images
                        .getColumnIndex("" + BusinessAccessLayer.IMG_ID
                                + ""));
                String imgImgId = mCursor_Images.getString(mCursor_Images
                        .getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID
                                + ""));

                System.out.println("imgImgId" + imgImgId);
                String imgName = mCursor_Images.getString(mCursor_Images
                        .getColumnIndex("" + BusinessAccessLayer.IMG_NAME
                                + ""));
                System.out.println("mImage_SyncStatus:" + mImage_SyncStatus);


                Trn_Images trn_ImagesUpdate = new Trn_Images(DashBoardActivity.this);
                trn_ImagesUpdate.open();
                trn_ImagesUpdate.updateSyncStatus(mImage_SyncStatus, imgImgId, imgName);
                Trn_Images.close();
            }
        }
        Trn_Images.close();

        Trn_Documents trn_Documents = new Trn_Documents(DashBoardActivity.this);
        trn_Documents.open();
        Cursor mCursor_Supplier = trn_Documents.fetchBySyncStatus();
        if (mCursor_Supplier.getCount() > 0) {
            for (int i = 0; i < mCursor_Supplier.getCount(); i++) {
                mCursor_Supplier.moveToPosition(i);
                mDocument_SyncStatus = mCursor_Supplier.getString(mCursor_Supplier
                        .getColumnIndex("" + BusinessAccessLayer.DOC_ID
                                + ""));
                trn_Documents.updateSyncStatus(mDocument_SyncStatus);
            }
        }
        Trn_Documents.close();


        Trn_Service_Details trn_Service_enroll = new Trn_Service_Details(
                DashBoardActivity.this);
        trn_Service_enroll.open();
        Cursor mCursor_ServiceInfo = trn_Service_enroll.fetchBySyncStatus();
        if (mCursor_ServiceInfo.getCount() > 0) {
            for (int i = 0; i < mCursor_ServiceInfo.getCount(); i++) {
                mCursor_ServiceInfo.moveToPosition(i);
                mService_SyncStatus = mCursor_ServiceInfo
                        .getString(mCursor_ServiceInfo.getColumnIndex(""
                                + BusinessAccessLayer.SERVICE_ID + ""));

                trn_Service_enroll.updateSyncStatus(mService_SyncStatus);
            }
        }
        Trn_Service_Details.close();


        Trn_Warranty_Details trn_Warranty_enroll = new Trn_Warranty_Details(
                DashBoardActivity.this);
        trn_Warranty_enroll.open();
        Cursor mCursor_WarrantyInfo = trn_Warranty_enroll.fetchBySyncStatus();
        if (mCursor_WarrantyInfo.getCount() > 0) {
            for (int i = 0; i < mCursor_WarrantyInfo.getCount(); i++) {
                mCursor_WarrantyInfo.moveToPosition(i);
                mWarranty_SyncStatus = mCursor_WarrantyInfo
                        .getString(mCursor_WarrantyInfo.getColumnIndex(""
                                + BusinessAccessLayer.WARRANTY_ID + ""));
                trn_Warranty_enroll.updateSyncStatus(mWarranty_SyncStatus);
            }
        }
        Trn_Warranty_Details.close();


        Trn_Training_Details trn_Training_enroll = new Trn_Training_Details(
                DashBoardActivity.this);
        trn_Training_enroll.open();
        Cursor mCursor_TrainingInfo = trn_Training_enroll.fetchBySyncStatus();
        if (mCursor_TrainingInfo.getCount() > 0) {
            for (int i = 0; i < mCursor_TrainingInfo.getCount(); i++) {
                mCursor_TrainingInfo.moveToPosition(i);
                mTraining_SyncStatus = mCursor_TrainingInfo
                        .getString(mCursor_TrainingInfo.getColumnIndex(""
                                + BusinessAccessLayer.TRAINING_ID + ""));
                trn_Training_enroll.updateSyncStatus(mTraining_SyncStatus);
            }
        }
        Trn_Training_Details.close();


        Trn_Consumables trn_Consumables_enroll = new Trn_Consumables(
                DashBoardActivity.this);
        trn_Consumables_enroll.open();
        Cursor mCursor_ConsumablesInfo = trn_Consumables_enroll.fetchBySyncStatus();
        if (mCursor_ConsumablesInfo.getCount() > 0) {
            for (int i = 0; i < mCursor_ConsumablesInfo.getCount(); i++) {
                mCursor_ConsumablesInfo.moveToPosition(i);
                mConsumables_SyncStatus = mCursor_ConsumablesInfo
                        .getString(mCursor_ConsumablesInfo.getColumnIndex(""
                                + BusinessAccessLayer.CONSUMABLES_ID + ""));
                trn_Consumables_enroll.updateSyncStatus(mConsumables_SyncStatus);
            }
        }
        Trn_Consumables.close();


        Trn_Voice_Of_Customer trn_Voc_enroll = new Trn_Voice_Of_Customer(
                DashBoardActivity.this);
        trn_Voc_enroll.open();
        Cursor mCursor_VocInfo = trn_Voc_enroll.fetchBySyncStatus();
        if (mCursor_VocInfo.getCount() > 0) {
            for (int i = 0; i < mCursor_VocInfo.getCount(); i++) {
                mCursor_VocInfo.moveToPosition(i);
                mVoc_SyncStatus = mCursor_VocInfo
                        .getString(mCursor_VocInfo.getColumnIndex(""
                                + BusinessAccessLayer.VOC_ID + ""));
                trn_Voc_enroll.updateSyncStatus(mVoc_SyncStatus);
            }
        }
        Trn_Voice_Of_Customer.close();


        exportDatabse(BusinessAccessLayer.DATABASE_NAME);

//        Trn_Images trn_Images = new Trn_Images(DashBoardActivity.this);
//        trn_Images.open();
//        Cursor mCursor_Images = trn_Images.fetchBySyncStatus();
//        if (mCursor_Images.getCount() > 0) {
//            for (int i = 0; i < mCursor_Images.getCount(); i++) {
//                mCursor_Images.moveToPosition(i);
//                mImage_SyncStatus = mCursor_Images.getString(mCursor_Images
//                        .getColumnIndex("" + BusinessAccessLayer.IMG_ID
//                                + ""));
//                System.out.println("mImage_SyncStatus:" + mImage_SyncStatus);
//
//
//                Trn_Images trn_ImagesUpdate = new Trn_Images(DashBoardActivity.this);
//                trn_ImagesUpdate.open();
//                trn_ImagesUpdate.updateSyncStatus(mImage_SyncStatus);
//                Trn_Images.close();
//            }
//        }
//        Trn_Images.close();
    }

    public void exportDatabse(String databaseName) {
        try {

            BusinessAccessLayer.myExternalFile = new File(DashBoardActivity.this.getExternalFilesDir(filepath), BusinessAccessLayer.DATABASE_NAME);
            System.out.println("directory myExternalFile   " + BusinessAccessLayer.myExternalFile);
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + DashBoardActivity.this.getPackageName()
                        + "//databases//" + databaseName + "";

                String backupDBPath = BusinessAccessLayer.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB)
                            .getChannel();
                    FileChannel dst = new FileOutputStream(BusinessAccessLayer.myExternalFile)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
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

        syncService_Array = new JSONArray();
        syncWarranty_Array = new JSONArray();
        syncTraining_Array = new JSONArray();
        syncConsumables_Array = new JSONArray();
        syncVoc_Array = new JSONArray();

        try {
            //Sync Trn_Equip_Enroll_Acc Table to Server
            Trn_Equipment_Enroll_Accessories trn_equip_enroll_access = new Trn_Equipment_Enroll_Accessories(this);
            trn_equip_enroll_access.open();
            Cursor mCr_equip_enroll_access = trn_equip_enroll_access.fetchBySyncStatus();

            if (mCr_equip_enroll_access.getCount() > 0) {
                for (int t_acess = 0; t_acess < mCr_equip_enroll_access.getCount(); t_acess++) {
                    mCr_equip_enroll_access.moveToPosition(t_acess);
                    JSONObject syncTrn_Enrollement_Accessories_JsonObj = new JSONObject();

                    String enroll_id = mCr_equip_enroll_access.getString(mCr_equip_enroll_access
                            .getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_ID + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.EQ_ENROLL_ID, enroll_id != null ? enroll_id : null);

                    String enroll_ups = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_UPS + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.EQ_ENROLL_UPS, enroll_ups != null ? enroll_ups : null);

                    String enroll_manual = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_MANUAL + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.EQ_ENROLL_MANUAL, enroll_manual != null ? enroll_manual : null);

                    String enroll_stabilizer = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_STABILIZER + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.EQ_ENROLL_STABILIZER, enroll_stabilizer != null ? enroll_stabilizer : null);

                    String enroll_others = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_OTHERS + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.EQ_ENROLL_OTHERS, enroll_others != null ? enroll_others : null);

                    // flag

                    String enroll_flag = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.FLAG, enroll_flag != null ? enroll_flag : null);

                    String enroll_isactive = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.ISACTIVE, enroll_isactive != null ? enroll_isactive : null);

                    String enroll_syncstatus = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, enroll_syncstatus != null ? enroll_syncstatus : null);


                    String enroll_createdby = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.CREATED_BY, enroll_createdby != null ? enroll_createdby : null);

                    String enroll_createdon = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.CREATED_ON, enroll_createdon != null ? enroll_createdon : null);

                    String enroll_modifiedby = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, enroll_modifiedby != null ? enroll_modifiedby : null);

                    String enroll_modifiedon = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    syncTrn_Enrollement_Accessories_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, enroll_modifiedon != null ? enroll_modifiedon : null);

                    synEquip_Enrollement_AccessoriesArray.put(syncTrn_Enrollement_Accessories_JsonObj);

                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES, synEquip_Enrollement_AccessoriesArray);

            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL_ACCESSORIES, synEquip_Enrollement_AccessoriesArray);
            }
            Trn_Equipment_Enroll_Accessories.close();
        } finally {
            Trn_Equipment_Enroll_Accessories.close();
        }/*     Trn_Equipment_Enroll_Accessories.close();     */



        /*
         * @Description  Sync Trn_Equip_Enrollment Table to Server
         */
        try {
            Trn_Equipment_Enrollment trn_enrollment = new Trn_Equipment_Enrollment(DashBoardActivity.this);

            trn_enrollment.open();
            Cursor mCr_enroll = trn_enrollment.fetchBySyncStatus();

            if (mCr_enroll.getCount() > 0) {
                for (int enroll = 0; enroll < mCr_enroll.getCount(); enroll++) {
                    mCr_enroll.moveToPosition(enroll);
                    JSONObject sync_trn_enroll_JsonObj = new JSONObject();

                    String enroll_id = mCr_enroll.getString(mCr_enroll
                            .getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_ID + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_ENROLL_ID, enroll_id != null ? enroll_id : null);

                    String eq_id = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_ID + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_ID, eq_id != null ? eq_id : null);

                    String eq_hospital_id = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_HOSPITAL_ID + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_HOSPITAL_ID, eq_hospital_id != null ? eq_hospital_id : null);


                    String eq_location_code = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_LOCATION_CODE + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_LOCATION_CODE, eq_location_code != null ? eq_location_code : null);

                    String eq_gps_coordinates = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.GPS_COORDINATES + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.GPS_COORDINATES, eq_gps_coordinates != null ? eq_gps_coordinates : null);

                    String eq_serialno = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_SERIALNO + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_SERIALNO, eq_serialno != null ? eq_serialno : null);

//
//                    String eq_name = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
//                    sync_trn_enroll_JsonObj.put("EQ_NAME", eq_name != null ? eq_name : null);


                    String eq_make = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_MAKE + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_MAKE, eq_make != null ? eq_make : null);


                    String eq_model = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_MODEL + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_MODEL, eq_model != null ? eq_model : null);


                    String eq_installdate = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_DATE + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_INSTALL_DATE, eq_installdate != null ? eq_installdate : null);

                    String eq_servicetag = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_SERVICE_TAGNO + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_SERVICE_TAGNO, eq_servicetag != null ? eq_servicetag : null);


                    String eq_notes = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_NOTES + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_NOTES, eq_notes != null ? eq_notes : null);

                    String eq_extra_accessories = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_EXTRA_ACCESSORIES + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_EXTRA_ACCESSORIES, eq_extra_accessories != null ? eq_extra_accessories : null);

                    String eq_installstatus = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_STATUS + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_INSTALL_STATUS, eq_installstatus != null ? eq_installstatus : null);


                    String eq_installnotes = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_NOTES + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_INSTALL_NOTES, eq_installnotes != null ? eq_installnotes : null);


                    String eq_workingstatus = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_WORKING_STATUS + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_WORKING_STATUS, eq_workingstatus != null ? eq_workingstatus : null);

                    String eq_workingnotes = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.EQ_WORKING_NOTES + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.EQ_WORKING_NOTES, eq_workingnotes != null ? eq_workingnotes : null);


//                    String eq_workingnotes_isactive = mCr_enroll.getString( mCr_enroll.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
//                    sync_trn_enroll_JsonObj.put( "ISACTIVE", eq_workingnotes_isactive != null ? eq_workingnotes_isactive : null );

                    String eq_syncstatus = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, eq_syncstatus != null ? eq_syncstatus : null);
//flag
                    String eq_flag = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.FLAG, eq_flag != null ? eq_flag : null);


                    String eq_createdby = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.CREATED_BY, eq_createdby != null ? eq_createdby : null);

                    String eq_createdon = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.CREATED_ON, eq_createdon != null ? eq_createdon : null);

                    String eq_modifiedby = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, eq_modifiedby != null ? eq_modifiedby : null);

                    String eq_modifiedon = mCr_enroll.getString(mCr_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, eq_modifiedon != null ? eq_modifiedon : null);

                    syncEquip_EnrollementArray.put(sync_trn_enroll_JsonObj);

                }

                System.out.println("syncEquip_EnrollementArray:" + syncEquip_EnrollementArray.toString());
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL, syncEquip_EnrollementArray);
            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_EQUIPMENT_ENROLL, syncEquip_EnrollementArray);
            }
            Trn_Equipment_Enrollment.close();
        } finally {
            Trn_Equipment_Enrollment.close();

        } //Trn_Equipment_Enrollment Table End


 /*
         * @Description  Sync Trn_Installation_Enrollment Table to Server
         */
        try {
            Trn_Installation_Enrollment trn_install_enroll = new Trn_Installation_Enrollment(DashBoardActivity.this);

            trn_install_enroll.open();
            Cursor mCr_install_enroll = trn_install_enroll.fetchBySyncStatus();
            if (mCr_install_enroll.getCount() > 0) {
                for (int install_enroll = 0; install_enroll < mCr_install_enroll.getCount(); install_enroll++) {
                    mCr_install_enroll.moveToPosition(install_enroll);
                    JSONObject sync_trn_install_enroll_JsonObj = new JSONObject();


                    String inst_id = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_INSTALL_DATE + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_INSTALL_DATE, inst_id != null ? inst_id : null);

                    String inst_equipenroll = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID, inst_equipenroll != null ? inst_equipenroll : null);

                    String inst_company = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_COMPANY_BY + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_COMPANY_BY, inst_company != null ? inst_company : null);

                    String inst_engg_name = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_ENGG_NAME + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_ENGG_NAME, inst_engg_name != null ? inst_engg_name : null);

                    String inst_location = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_LOCATION + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_LOCATION, inst_location != null ? inst_location : null);

                    String inst_nearby_location = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_NEAR_BY_PHONENO + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_NEAR_BY_PHONENO, inst_nearby_location != null ? inst_nearby_location : null);

                    String inst_notes = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_NOTES + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_NOTES, inst_notes != null ? inst_notes : null);

                    String inst_area = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_AREA + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_AREA, inst_area != null ? inst_area : null);


                    String inst_ownername = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_OWNER_NAME + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_OWNER_NAME, inst_ownername != null ? inst_ownername : null);

                    String inst_ownerphonename = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.INST_OWNER_PHONENO + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.INST_OWNER_PHONENO, inst_ownerphonename != null ? inst_ownerphonename : null);


//                    String inst_isactive = mCr_install_enroll.getString( mCr_install_enroll.getColumnIndex( "" + BusinessAccessLayer.ISACTIVE + "" ) );
//                    sync_trn_install_enroll_JsonObj.put( "ISACTIVE", inst_isactive != null ? inst_isactive : null );

                    // flag
                    String inst_flag = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.FLAG, inst_flag != null ? inst_flag : null);


                    String inst_syncstatus = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, inst_syncstatus != null ? inst_syncstatus : null);


                    String inst_createdby = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.CREATED_BY, inst_createdby != null ? inst_createdby : null);

                    String inst_createdon = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.CREATED_ON, inst_createdon != null ? inst_createdon : null);

                    String inst_modifiedby = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, inst_modifiedby != null ? inst_modifiedby : null);

                    String inst_modifiedon = mCr_install_enroll.getString(mCr_install_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_install_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, inst_modifiedon != null ? inst_modifiedon : null);


                    syncInstallation_EnrollementArray.put(sync_trn_install_enroll_JsonObj);


                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL, syncInstallation_EnrollementArray);


            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_INSTALLATION_ENROLL, syncInstallation_EnrollementArray);
            }
            Trn_Installation_Enrollment.close();

        } finally {
            Trn_Installation_Enrollment.close();
        }//Trn_Installation_Enrollment Table End

/*
         * @Description  Sync Trn_User_Registration Table to Server
         */

        try {

            Trn_User_Registration trn_user_registration = new Trn_User_Registration(DashBoardActivity.this);
            trn_user_registration.open();
            Cursor mCr_userregistration = trn_user_registration.fetchBySyncStatus();
            if (mCr_userregistration.getCount() > 0) {
                for (int user_registration = 0; user_registration < mCr_userregistration.getCount(); user_registration++) {
                    mCr_userregistration.moveToPosition(user_registration);
                    JSONObject sync_trn_user_registration = new JSONObject();

                    String user_id = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_ID + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_ID, user_id != null ? user_id : null);

                    String user_email = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_EMAIL + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_EMAIL, user_email != null ? user_email : null);

                    String user_firstname = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_FIRST_NAME + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_FIRST_NAME, user_firstname != null ? user_firstname : null);

                    String user_lastname = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_LAST_NAME + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_LAST_NAME, user_lastname != null ? user_lastname : null);

                    String user_phoneno = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_PHONENO + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_PHONENO, user_phoneno != null ? user_phoneno : null);

                    String user_password = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_PASSWORD + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_PASSWORD, user_password != null ? user_password : null);

                    String user_image = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_IMAGE + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_IMAGE, user_image != null ? user_image : null);

                    String user_admin = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_ADMIN + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_ADMIN, user_admin != null ? user_admin : null);

                    String user_isactive = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.ISACTIVE, user_isactive != null ? user_isactive : null);

                    String user_hospital = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_HOSPITAL + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_HOSPITAL, user_hospital != null ? user_hospital : null);

                    String user_startdate = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_EFFECT_STARTDATE + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_EFFECT_STARTDATE, user_startdate != null ? user_startdate : null);

                    String user_enddate = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.USER_EFFECT_ENDDATE + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.USER_EFFECT_ENDDATE, user_enddate != null ? user_enddate : null);

                    //flag
                    String user_flag = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.FLAG, user_flag != null ? user_flag : null);


                    String user_syncstatus = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.SYNC_STATUS, user_syncstatus != null ? user_syncstatus : null);


                    String user_createdby = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.CREATED_BY, user_createdby != null ? user_createdby : null);

                    String user_createdon = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.CREATED_ON, user_createdon != null ? user_createdon : null);

                    String user_modifiedby = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.MODIFIED_BY, user_modifiedby != null ? user_modifiedby : null);

                    String user_modifiedon = mCr_userregistration.getString(mCr_userregistration.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_user_registration.put(BusinessAccessLayer.MODIFIED_ON, user_modifiedon != null ? user_modifiedon : null);

                    syncUser_RegistrationArray.put(sync_trn_user_registration);
                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION, syncUser_RegistrationArray);

            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_USER_REGISTRATION, syncUser_RegistrationArray);
            }
            Trn_User_Registration.close();
        } finally {
            Trn_User_Registration.close();
        }//Trn_User_Registration End

       /*
         * @Description  Sync Mst_Equipment_Status Table to Server
         */


        try {
            Mst_Equipment_Status mst_eqip_status = new Mst_Equipment_Status(DashBoardActivity.this);
            mst_eqip_status.open();
            Cursor mCt_mst_equipment_status = mst_eqip_status.fetchBySyncStatus();
            if (mCt_mst_equipment_status.getCount() > 0) {
                for (int mst_equipstatus = 0; mst_equipstatus < mCt_mst_equipment_status.getCount(); mst_equipstatus++) {
                    mCt_mst_equipment_status.moveToPosition(mst_equipstatus);
                    JSONObject sync_mst_equip_status = new JSONObject();

                    String eq_id = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.EQ_ID + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.EQ_ID, eq_id != null ? eq_id : null);


                    String eq_name = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.EQ_NAME, eq_name != null ? eq_name : null);
//flag

                    String eq_flag = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.FLAG, eq_flag != null ? eq_flag : null);

                    String equipstatus_isactive = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.ISACTIVE, equipstatus_isactive != null ? equipstatus_isactive : null);

                    String equip_isstandard = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.IS_STANDARD + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.IS_STANDARD, equip_isstandard != null ? equip_isstandard : null);

                    String equipstatus_syncstatus = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.SYNC_STATUS, equipstatus_syncstatus != null ? equipstatus_syncstatus : null);


                    String equipstatus_createdby = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.CREATED_BY, equipstatus_createdby != null ? equipstatus_createdby : null);

                    String equipstatus_createdon = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.CREATED_ON, equipstatus_createdon != null ? equipstatus_createdon : null);

                    String equipstatus_modifiedby = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.MODIFIED_BY, equipstatus_modifiedby != null ? equipstatus_modifiedby : null);

                    String equipstatus_modifiedon = mCt_mst_equipment_status.getString(mCt_mst_equipment_status.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_mst_equip_status.put(BusinessAccessLayer.MODIFIED_ON, equipstatus_modifiedon != null ? equipstatus_modifiedon : null);


                    syncMst_Equip_StatusArray.put(sync_mst_equip_status);
                }

                syncObject.put(BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS, syncMst_Equip_StatusArray);

            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_MST_EQUIPMENT_STATUS, syncMst_Equip_StatusArray);
            }
            Mst_Equipment_Status.close();

        } finally {
            Mst_Equipment_Status.close();
        }// Mst_Equipment_Status Table End

 /*
         * @Description  Sync Mst_Hospital_Enrollment Table to Server
         */

        try {
            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(DashBoardActivity.this);
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchBySyncStatus();
            if (mCr_mst_hospital_enroll.getCount() > 0) {
                for (int mst_hospitalenroll = 0; mst_hospitalenroll < mCr_mst_hospital_enroll.getCount(); mst_hospitalenroll++) {
                    mCr_mst_hospital_enroll.moveToPosition(mst_hospitalenroll);
                    JSONObject sync_mst_hospital_enroll = new JSONObject();

                    String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_ID, hospital_id != null ? hospital_id : null);


                    String hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_NAME, hospital_name != null ? hospital_name : null);


                    String hospital_location = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_LOCATION, hospital_location != null ? hospital_location : null);

                    String hospital_desc = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_DESC + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_DESC, hospital_desc != null ? hospital_desc : null);


                    String hospital_address1 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ADDRESS1 + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_ADDRESS1, hospital_address1 != null ? hospital_address1 : null);

                    String hospital_address2 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ADDRESS2 + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_ADDRESS2, hospital_address2 != null ? hospital_address2 : null);

                    String hospital_address3 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ADDRESS3 + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_ADDRESS3, hospital_address3 != null ? hospital_address3 : null);

                    String hospital_state = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_STATE + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_STATE, hospital_state != null ? hospital_state : null);


                    String hospital_country = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_COUNTRY + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_COUNTRY, hospital_country != null ? hospital_country : null);

                    String hospital_phno1 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_PHNO1 + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_PHNO1, hospital_phno1 != null ? hospital_phno1 : null);


                    String hospital_phno2 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_PHNO2 + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_PHNO2, hospital_phno2 != null ? hospital_phno2 : null);


                    String hospital_email = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_EMAIL + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_EMAIL, hospital_email != null ? hospital_email : null);


                    String hospital_notes = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NOTES + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.HOSPITAL_NOTES, hospital_notes != null ? hospital_notes : null);


                    String hospital_isactive = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.ISACTIVE, hospital_isactive != null ? hospital_isactive : null);

                    String standard_equipments = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.STANDARD_EQUIPMENTS + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.STANDARD_EQUIPMENTS, standard_equipments != null ? standard_equipments : null);

                    String hospital_syncstatus = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.SYNC_STATUS, hospital_syncstatus != null ? hospital_syncstatus : null);

                    //flag

                    String hospital_flag = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.FLAG, hospital_flag != null ? hospital_flag : null);

                    String hospital_createdby = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.CREATED_BY, hospital_createdby != null ? hospital_createdby : null);

                    String hospital_createdon = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.CREATED_ON, hospital_createdon != null ? hospital_createdon : null);

                    String hospital_modifiedby = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.MODIFIED_BY, hospital_modifiedby != null ? hospital_modifiedby : null);

                    String hospital_modifiedon = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_mst_hospital_enroll.put(BusinessAccessLayer.MODIFIED_ON, hospital_modifiedon != null ? hospital_modifiedon : null);


                    syncMst_HospitalArray.put(sync_mst_hospital_enroll);
                }

                syncObject.put(BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL, syncMst_HospitalArray);

            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_MST_HOSPITAL_ENROLL, syncMst_HospitalArray);
            }
            Mst_Hospital_Enrollment.close();

        } finally {
            Mst_Hospital_Enrollment.close();
        }// Mst_Hospital_Enrollment Table End


        try {
            Trn_Images trn_images = new Trn_Images(DashBoardActivity.this);

            trn_images.open();
            Cursor mCr_images = trn_images.fetchBySyncStatusbeforesync();
            if (mCr_images.getCount() > 0) {
                for (int images = 0; images < mCr_images.getCount(); images++) {
                    mCr_images.moveToPosition(images);
                    JSONObject sync_trn_img_JsonObj = new JSONObject();

                    String img_img_id = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.IMG_IMG_ID, img_img_id != null ? img_img_id : null);

                    String img_id = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.IMG_ID + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.IMG_ID, img_id != null ? img_id : null);

                    String img_name = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.IMG_NAME + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.IMG_NAME, img_name != null ? img_name : null);

                    String img_encrypt_data = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.IMG_ENCRYPTED_DATA, img_encrypt_data != null ? img_encrypt_data : null);


                    String img_flag = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.FLAG, img_flag != null ? img_flag : null);

//                    String img_syncstatus = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
//                    sync_trn_img_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, img_syncstatus != null ? img_syncstatus : null);


                    String img_createdby = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.CREATED_BY, img_createdby != null ? img_createdby : null);

                    String img_createdon = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.CREATED_ON, img_createdon != null ? img_createdon : null);

                    String img_modifiedby = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, img_modifiedby != null ? img_modifiedby : null);

                    String img_modifiedOn = mCr_images.getString(mCr_images.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_img_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, img_modifiedOn != null ? img_modifiedOn : null);

                    syncTrn_ImagesArray.put(sync_trn_img_JsonObj);

                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_IMAGES, syncTrn_ImagesArray);
            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_IMAGES, syncTrn_ImagesArray);
            }
            Trn_Images.close();

        } finally {
            Trn_Images.close();
        }// Trn Image Table Sync End


        try {
            Trn_Documents trn_documents = new Trn_Documents(DashBoardActivity.this);

            trn_documents.open();
            Cursor mCr_documents = trn_documents.fetchBySyncStatus();

            System.out.println("mCr_documents:" + mCr_documents.getCount());
            if (mCr_documents.getCount() > 0) {
                for (int documents = 0; documents < mCr_documents.getCount(); documents++) {
                    mCr_documents.moveToPosition(documents);
                    JSONObject sync_trn_documents_JsonObj = new JSONObject();

                    String doc_doc_id = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.DOC_DOC_ID + ""));

                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.DOC_DOC_ID, doc_doc_id != null ? doc_doc_id : null);

                    String doc_id = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.DOC_ID + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.DOC_ID, doc_id != null ? doc_id : null);

                    String doc_name = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.DOC_NAME + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.DOC_NAME, doc_name != null ? doc_name : null);

                    String doc_encrypt_data = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.DOC_ENCRYPTED_DATA + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.DOC_ENCRYPTED_DATA, doc_encrypt_data != null ? doc_encrypt_data : null);

                    String doc_type = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.DOC_TYPE + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.DOC_TYPE, doc_type != null ? doc_type : null);

                    String doc_flag = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.FLAG, doc_flag != null ? doc_flag : null);

//                    String doc_syncstatus = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
//                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, doc_syncstatus != null ? doc_syncstatus : null);

                    String doc_createdby = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.CREATED_BY, doc_createdby != null ? doc_createdby : null);

                    String doc_createdon = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.CREATED_ON, doc_createdon != null ? doc_createdon : null);

                    String doc_modifiedby = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, doc_modifiedby != null ? doc_modifiedby : null);

                    String doc_modifiedon = mCr_documents.getString(mCr_documents.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_documents_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, doc_modifiedon != null ? doc_modifiedon : null);

                    syncTrn_DocArray.put(sync_trn_documents_JsonObj);


                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS, syncTrn_DocArray);


            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_DOCUMENTS, syncTrn_DocArray);
            }
            Trn_Documents.close();

        } finally {
            Trn_Documents.close();
        }


        /*
         * @Description  Sync Trn_Service_Detail Table to Server
         * @Author Muralidharan M
         */
        try {
            Trn_Service_Details trn_service_enroll = new Trn_Service_Details(DashBoardActivity.this);

            trn_service_enroll.open();
            Cursor mCr_service_enroll = trn_service_enroll.fetchBySyncStatus();
            if (mCr_service_enroll.getCount() > 0) {
                for (int service_enroll = 0; service_enroll < mCr_service_enroll.getCount(); service_enroll++) {
                    mCr_service_enroll.moveToPosition(service_enroll);
                    JSONObject sync_trn_service_enroll_JsonObj = new JSONObject();

                    String service_id = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_ID + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_ID, service_id != null ? service_id : null);

                    String service_equipment_enroll_id = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_EQUIPMENT_ENROLL_ID + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_EQUIPMENT_ENROLL_ID, service_equipment_enroll_id != null ? service_equipment_enroll_id : null);

                    String service_hospital_id = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_HOSPITAL_ID + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_HOSPITAL_ID, service_hospital_id != null ? service_hospital_id : null);

                    String service_equipment_id = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_EQUIPMENT_ID + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_EQUIPMENT_ID, service_equipment_id != null ? service_equipment_id : null);

                    String service_date_time = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_DATE_TIME + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_DATE_TIME, service_date_time != null ? service_date_time : null);

                    String service_duration = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_DURATION + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_DURATION, service_duration != null ? service_duration : null);

                    String service_type = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_TYPE + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_TYPE, service_type != null ? service_type : null);

                    String service_notes = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_NOTES + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_NOTES, service_notes != null ? service_notes : null);

                    String service_approved_by = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_APPROVED_BY + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_APPROVED_BY, service_approved_by != null ? service_approved_by : null);

                    String service_invoice = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICE_INVOICE + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICE_INVOICE, service_invoice != null ? service_invoice : null);

                    String serviced_by = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SERVICED_BY + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SERVICED_BY, serviced_by != null ? serviced_by : null);


                    String inst_flag = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.FLAG, inst_flag != null ? inst_flag : null);

                    String inst_syncstatus = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, inst_syncstatus != null ? inst_syncstatus : null);

                    String inst_createdby = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.CREATED_BY, inst_createdby != null ? inst_createdby : null);

                    String inst_createdon = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.CREATED_ON, inst_createdon != null ? inst_createdon : null);

                    String inst_modifiedby = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, inst_modifiedby != null ? inst_modifiedby : null);

                    String inst_modifiedon = mCr_service_enroll.getString(mCr_service_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_service_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, inst_modifiedon != null ? inst_modifiedon : null);

                    syncService_Array.put(sync_trn_service_enroll_JsonObj);
                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS, syncService_Array);


            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_SERVICE_DETAILS, syncService_Array);
            }
            Trn_Service_Details.close();

        } finally {
            Trn_Service_Details.close();
        }//Trn_Service_Details Table End


        /*
         * @Description  Sync Trn_Warranty_Detail Table to Server
         * @Author Muralidharan M
         */
        try {
            Trn_Warranty_Details trn_warranty_enroll = new Trn_Warranty_Details(DashBoardActivity.this);
            trn_warranty_enroll.open();
            Cursor mCr_warranty_enroll = trn_warranty_enroll.fetchBySyncStatus();
            if (mCr_warranty_enroll.getCount() > 0) {
                for (int warranty_enroll = 0; warranty_enroll < mCr_warranty_enroll.getCount(); warranty_enroll++) {
                    mCr_warranty_enroll.moveToPosition(warranty_enroll);
                    JSONObject sync_trn_warranty_enroll_JsonObj = new JSONObject();

                    String warranty_id = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_ID + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_ID, warranty_id != null ? warranty_id : null);

                    String warranty_equipment_enroll_id = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_EQUIPMENT_ENROLL_ID, warranty_equipment_enroll_id != null ? warranty_equipment_enroll_id : null);

                    String warranty_hospital_id = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_HOSPITAL_ID + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_HOSPITAL_ID, warranty_hospital_id != null ? warranty_hospital_id : null);

                    String warranty_equipment_id = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_EQUIPMENT_ID + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_EQUIPMENT_ID, warranty_equipment_id != null ? warranty_equipment_id : null);

                    String warranty_start_date = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_START_DATE + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_START_DATE, warranty_start_date != null ? warranty_start_date : null);

                    String warranty_end_date = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_END_DATE + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_END_DATE, warranty_end_date != null ? warranty_end_date : null);

                    String warranty_duration = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_DURATION + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_DURATION, warranty_duration != null ? warranty_duration : null);

                    String warranty_description = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_DESCRIPTION + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_DESCRIPTION, warranty_description != null ? warranty_description : null);

                    String warranty_type = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.WARRANTY_TYPE + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.WARRANTY_TYPE, warranty_type != null ? warranty_type : null);


                    String inst_flag = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.FLAG, inst_flag != null ? inst_flag : null);

                    String inst_syncstatus = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, inst_syncstatus != null ? inst_syncstatus : null);

                    String inst_createdby = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.CREATED_BY, inst_createdby != null ? inst_createdby : null);

                    String inst_createdon = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.CREATED_ON, inst_createdon != null ? inst_createdon : null);

                    String inst_modifiedby = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, inst_modifiedby != null ? inst_modifiedby : null);

                    String inst_modifiedon = mCr_warranty_enroll.getString(mCr_warranty_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_warranty_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, inst_modifiedon != null ? inst_modifiedon : null);

                    syncWarranty_Array.put(sync_trn_warranty_enroll_JsonObj);
                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS, syncWarranty_Array);


            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_WARRANTY_DETAILS, syncWarranty_Array);
            }
            Trn_Warranty_Details.close();

        } finally {
            Trn_Warranty_Details.close();
        }//Trn_Warranty_Details Table End


        /*
         * @Description  Sync Trn_Training_Detail Table to Server
         * @Author Muralidharan M
         */
        try {
            Trn_Training_Details trn_training_enroll = new Trn_Training_Details(DashBoardActivity.this);
            trn_training_enroll.open();
            Cursor mCr_training_enroll = trn_training_enroll.fetchBySyncStatus();
            if (mCr_training_enroll.getCount() > 0) {
                for (int training_enroll = 0; training_enroll < mCr_training_enroll.getCount(); training_enroll++) {
                    mCr_training_enroll.moveToPosition(training_enroll);
                    JSONObject sync_trn_training_enroll_JsonObj = new JSONObject();

                    String training_id = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_ID + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_ID, training_id != null ? training_id : null);

                    String training_equipment_enroll_id = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_EQUIPMENT_ENROLL_ID + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_EQUIPMENT_ENROLL_ID, training_equipment_enroll_id != null ? training_equipment_enroll_id : null);

                    String training_hospital_id = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_HOSPITAL_ID + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_HOSPITAL_ID, training_hospital_id != null ? training_hospital_id : null);

                    String training_equipment_id = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_EQUIPMENT_ID + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_EQUIPMENT_ID, training_equipment_id != null ? training_equipment_id : null);

                    String training_date = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_DATE + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_DATE, training_date != null ? training_date : null);

                    String training_duration = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_DURATION + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_DURATION, training_duration != null ? training_duration : null);

                    String training_description = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_DESCRIPTION + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_DESCRIPTION, training_description != null ? training_description : null);

                    String training_provided_by = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_PROVIDED_BY + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_PROVIDED_BY, training_provided_by != null ? training_provided_by : null);

                    String training_invoice = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.TRAINING_INVOICE + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.TRAINING_INVOICE, training_invoice != null ? training_invoice : null);


                    String inst_flag = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.FLAG, inst_flag != null ? inst_flag : null);

                    String inst_syncstatus = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, inst_syncstatus != null ? inst_syncstatus : null);

                    String inst_createdby = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.CREATED_BY, inst_createdby != null ? inst_createdby : null);

                    String inst_createdon = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.CREATED_ON, inst_createdon != null ? inst_createdon : null);

                    String inst_modifiedby = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, inst_modifiedby != null ? inst_modifiedby : null);

                    String inst_modifiedon = mCr_training_enroll.getString(mCr_training_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_training_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, inst_modifiedon != null ? inst_modifiedon : null);

                    syncTraining_Array.put(sync_trn_training_enroll_JsonObj);
                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS, syncTraining_Array);


            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_TRAINING_DETAILS, syncTraining_Array);
            }
            Trn_Training_Details.close();

        } finally {
            Trn_Training_Details.close();
        }//Trn_Training_Details Table End



        /*
         * @Description  Sync Trn_Consumables Table to Server
         * @Author Muralidharan M
         */
        try {
            Trn_Consumables trn_consumables_enroll = new Trn_Consumables(DashBoardActivity.this);
            trn_consumables_enroll.open();
            Cursor mCr_consumables_enroll = trn_consumables_enroll.fetchBySyncStatus();
            if (mCr_consumables_enroll.getCount() > 0) {
                for (int consumables_enroll = 0; consumables_enroll < mCr_consumables_enroll.getCount(); consumables_enroll++) {
                    mCr_consumables_enroll.moveToPosition(consumables_enroll);
                    JSONObject sync_trn_consumables_enroll_JsonObj = new JSONObject();

                    String consumables_id = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_ID + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CONSUMABLES_ID, consumables_id != null ? consumables_id : null);

                    String consumables_equipment_enroll_id = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ENROLL_ID + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ENROLL_ID, consumables_equipment_enroll_id != null ? consumables_equipment_enroll_id : null);

                    String consumables_hospital_id = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_HOSPITAL_ID + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CONSUMABLES_HOSPITAL_ID, consumables_hospital_id != null ? consumables_hospital_id : null);

                    String consumables_equipment_id = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ID + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CONSUMABLES_EQUIPMENT_ID, consumables_equipment_id != null ? consumables_equipment_id : null);

                    String name = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.NAME + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.NAME, name != null ? name : null);

                    String description = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.DESCRIPTION + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.DESCRIPTION, description != null ? description : null);

                    String type_of_usage = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.TYPE_OF_USAGE + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.TYPE_OF_USAGE, type_of_usage != null ? type_of_usage : null);

                    String usage_parameter = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.USAGE_PARAMETER + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.USAGE_PARAMETER, usage_parameter != null ? usage_parameter : null);

                    String quantity = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.QUANTITY + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.QUANTITY, quantity != null ? quantity : null);

                    String consumables_uom = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_UOM + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CONSUMABLES_UOM, consumables_uom != null ? consumables_uom : null);

                    String consumables_current_stock = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_CURRENT_STOCK + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CONSUMABLES_CURRENT_STOCK, consumables_current_stock != null ? consumables_current_stock : null);

                    String consumable_notes = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CONSUMABLE_NOTES + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CONSUMABLE_NOTES, consumable_notes != null ? consumable_notes : null);


                    String inst_flag = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.FLAG, inst_flag != null ? inst_flag : null);

                    String inst_syncstatus = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, inst_syncstatus != null ? inst_syncstatus : null);

                    String inst_createdby = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CREATED_BY, inst_createdby != null ? inst_createdby : null);

                    String inst_createdon = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.CREATED_ON, inst_createdon != null ? inst_createdon : null);

                    String inst_modifiedby = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, inst_modifiedby != null ? inst_modifiedby : null);

                    String inst_modifiedon = mCr_consumables_enroll.getString(mCr_consumables_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_consumables_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, inst_modifiedon != null ? inst_modifiedon : null);

                    syncConsumables_Array.put(sync_trn_consumables_enroll_JsonObj);
                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES, syncConsumables_Array);


            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_CONSUMABLES, syncConsumables_Array);
            }
            Trn_Consumables.close();

        } finally {
            Trn_Consumables.close();
        }//Trn_Consumables Table End


        /*
         * @Description  Sync Trn_Voice_Of_Customer Table to Server
         * @Author Muralidharan M
         */
        try {
            Trn_Voice_Of_Customer trn_voc_enroll = new Trn_Voice_Of_Customer(DashBoardActivity.this);
            trn_voc_enroll.open();
            Cursor mCr_voc_enroll = trn_voc_enroll.fetchBySyncStatus();
            if (mCr_voc_enroll.getCount() > 0) {
                for (int voc_enroll = 0; voc_enroll < mCr_voc_enroll.getCount(); voc_enroll++) {
                    mCr_voc_enroll.moveToPosition(voc_enroll);
                    JSONObject sync_trn_voc_enroll_JsonObj = new JSONObject();

                    String voc_id = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.VOC_ID + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.VOC_ID, voc_id != null ? voc_id : null);

                    String voc_equipment_enroll_id = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.VOC_EQUIPMENT_ENROLL_ID + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.VOC_EQUIPMENT_ENROLL_ID, voc_equipment_enroll_id != null ? voc_equipment_enroll_id : null);

                    String type = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.TYPE + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.TYPE, type != null ? type : null);

                    String in_brief = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.IN_BRIEF + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.IN_BRIEF, in_brief != null ? in_brief : null);

                    String voc_hospital_id = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.VOC_HOSPITAL_ID + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.VOC_HOSPITAL_ID, voc_hospital_id != null ? voc_hospital_id : null);

                    String voc_equipment_id = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.VOC_EQUIPMENT_ID + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.VOC_EQUIPMENT_ID, voc_equipment_id != null ? voc_equipment_id : null);


                    String inst_flag = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.FLAG, inst_flag != null ? inst_flag : null);

                    String inst_syncstatus = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.SYNC_STATUS, inst_syncstatus != null ? inst_syncstatus : null);

                    String inst_createdby = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.CREATED_BY, inst_createdby != null ? inst_createdby : null);

                    String inst_createdon = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.CREATED_ON, inst_createdon != null ? inst_createdon : null);

                    String inst_modifiedby = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_BY, inst_modifiedby != null ? inst_modifiedby : null);

                    String inst_modifiedon = mCr_voc_enroll.getString(mCr_voc_enroll.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                    sync_trn_voc_enroll_JsonObj.put(BusinessAccessLayer.MODIFIED_ON, inst_modifiedon != null ? inst_modifiedon : null);

                    syncVoc_Array.put(sync_trn_voc_enroll_JsonObj);
                }
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER, syncVoc_Array);


            } else {
                syncObject.put(BusinessAccessLayer.DB_TABLE_TRN_VOICE_OF_CUSTOMER, syncVoc_Array);
            }
            Trn_Voice_Of_Customer.close();

        } finally {
            Trn_Voice_Of_Customer.close();
        }//Trn_Voice_Of_Customer Table End


        mJSON_Str_for_Sync = syncObject.toString();
        Log.i("SyncStatus", mJSON_Str_for_Sync);

        System.out.println("SyncStatus " + mJSON_Str_for_Sync);


    }

    private class Http_Upload_Sync_EqMED extends AsyncTask<Void, Void, Integer> {

        //        StringCustomProgressDialog mCusDialog;
        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mCusDialog = new CustomProgressDialog( DashBoardActivity.this );
//            mCusDialog.show( DashBoardActivity.this, "Loading", "", true,
//                    false, null );

            progress = new ProgressDialog(DashBoardActivity.this);
            progress.setMessage("Loading...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);
            progress.setProgress(0);
            //   progress.setCancelable( true );
            progress.show();
        }

        @Override
        protected Integer doInBackground(Void... urls) {


            List<NameValuePair> args = new ArrayList<NameValuePair>();
            args.add(new BasicNameValuePair(DATA, syncObject.toString()));
//            HttpRequest.sharedInstance().doHttpRequest(URL_MASTER_SYNC, args);
            result_MstJson_Str = HttpRequest.sharedInstance().responseString;

            System.out.println("result_MstJson_Str:" + result_MstJson_Str);
            return 0;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Integer result) {
//            Toast.makeText( getBaseContext(), "Received!", Toast.LENGTH_LONG ).show();
//            if ( progress != null ) {
//                progress.dismiss();
//                progress = null;
//            }
//            mCusDialog.dismiss();
            super.onPostExecute(result);

            progress.dismiss();
            messageHanlerFirstSync.sendEmptyMessage(0);

//                {"message":"Success","status":"NTE_01","last_sync_date":"2015\/12\/16 11:58:11"}
        }


        // }

    }

    Handler messageHanlerFirstSync = new Handler() {

        public void handleMessage(android.os.Message msg) {
            try {
                JSONObject jsonObjStr = new JSONObject(
                        result_MstJson_Str);

                String responseStr = jsonObjStr.getString("response");
                JSONObject res_obj = new JSONObject(responseStr);
                String res_string = res_obj.getString("status");
                System.out.println("responseStr " + res_string);
                if (res_string.equalsIgnoreCase(RESPONSE_SUCCESS)) {
//                    showContactUsDialog( getApplicationContext(), "Data Uploaded Successfully" );

                    updateSyncStatusData();

                    //    Toast.makeText(DashBoardActivity.this, "Data Uploaded Successfully", Toast.LENGTH_LONG).show();


                    new Thread(new Runnable() {
                        public void run() {
                            File documents = new File(getFilesDir(), "/documents");
                            if (!documents.isDirectory()) {
                                documents.mkdirs();
                            }
                            String folder_path = getFilesDir() + "/temp_documents";
                            File parentDir = new File(folder_path);

                            File[] files = parentDir.listFiles();
                            System.out.println("File dire : " + files);
                            if (files != null) {
                                for (File file : files) {
                                    if (file.isDirectory()) {
                                    } else {
                                        System.out.println("Absolute Path: " + file.getAbsolutePath());
                                        abs_file_path = file.getAbsolutePath();
                                        new HttpAsyncTask().execute(BusinessAccessLayer.FILE_UPLOAD_URL);
                                        try {
                                            Thread.sleep(15000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }


                            File images = new File(getFilesDir(), "/images");
                            if (!images.isDirectory()) {
                                images.mkdirs();
                            }
                            String folder_patht = getFilesDir() + "/temp_images";
                            File parentDirt = new File(folder_patht);

                            File[] filest = parentDirt.listFiles();
                            System.out.println("File dire : " + filest);
                            if (filest != null) {
                                for (File img : filest) {
                                    if (img.isDirectory()) {
                                    } else {
                                        System.out.println("Absolute Path: " + img.getAbsolutePath());
                                        abs_file_path = img.getAbsolutePath();
                                        new HttpAsyncTaskImage().execute(BusinessAccessLayer.IMAGE_UPLOAD_URL);
                                        try {
                                            Thread.sleep(15000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }


                        }
                    }).start();


                   /* new Thread(new Runnable() {
                        public void run() {

                        }
                    }).start();*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    };


    /// odoo

    private class Http_Upload_Sync_EqMED_Odoo extends AsyncTask<Void, Void, Integer> {

        //        StringCustomProgressDialog mCusDialog;
        private ProgressDialog progress;
        Runnable progressRunnable;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mCusDialog = new CustomProgressDialog( DashBoardActivity.this );
//            mCusDialog.show( DashBoardActivity.this, "Loading", "", true,
//                    false, null );

            progress = new ProgressDialog(DashBoardActivity.this);
            progress.setMessage("Fetching Data..");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);
            progress.setProgress(0);
            //   progress.setCancelable( true );
            progress.show();

            progressRunnable = new Runnable() {

                @Override
                public void run() {
                    progress.cancel();
                }
            };

            progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progress.cancel();
                }
            });

        }

        @Override
        protected Integer doInBackground(Void... urls) {


            progress.setMessage("Fetching Data..\nTransferring Data..");

            System.out.println("syncObject.toString() " + syncObject.toString());
            List<NameValuePair> args = new ArrayList<NameValuePair>();
            args.add(new BasicNameValuePair(DATA, syncObject.toString()));
            String URL_MASTER_SYNC_ODOO_DATA = ServerIP + "" + BusinessAccessLayer.URL_MASTER_SYNC_ODOO;
            HttpRequest.sharedInstance().doHttpRequest(URL_MASTER_SYNC_ODOO_DATA, args);
            result_MstJson_Str = HttpRequest.sharedInstance().responseString;

            System.out.println("result_MstJson_Str:" + result_MstJson_Str);
            return 0;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Integer result) {
//            Toast.makeText( getBaseContext(), "Received!", Toast.LENGTH_LONG ).show();
//            if ( progress != null ) {
//                progress.dismiss();
//                progress = null;
//            }
//            mCusDialog.dismiss();

            progress.setMessage("Fetching Data\nTransferring Data\nData Transferred, Updating sync status..");
            super.onPostExecute(result);
            progress.setMessage("Fetching Data\nTransferring Data\nTransferred, Updating sync status\nFinished, Images and Documents will be sent to server in background..");
            messageHanlerFirstSyncOdoo.sendEmptyMessage(0);

            Handler pdCanceller = new Handler();
            pdCanceller.postDelayed(progressRunnable, 6000);

//            Toast.makeText(DashBoardActivity.this, "Document moved successfully", Toast.LENGTH_SHORT).show();

//                {"message":"Success","status":"NTE_01","last_sync_date":"2015\/12\/16 11:58:11"}
        }
        // }
    }

    Handler messageHanlerFirstSyncOdoo = new Handler() {

        public void handleMessage(android.os.Message msg) {
            try {
                JSONObject jsonObjStr = new JSONObject(
                        result_MstJson_Str);

                String responseStr = jsonObjStr.getString("status");
                System.out.println("responseStr in handle::" + responseStr);
                if (responseStr.equalsIgnoreCase(RESPONSE_SUCCESS)) {
//                    showContactUsDialog( getApplicationContext(), "Data Uploaded Successfully" );

                    updateSyncStatusData();

                    //   Toast.makeText(DashBoardActivity.this, "Data Uploaded Successfully", Toast.LENGTH_LONG).show();


                    new Thread(new Runnable() {
                        public void run() {
                            File documents = new File(getFilesDir(), "/documents");
                            if (!documents.isDirectory()) {
                                documents.mkdirs();
                            }
                            String folder_path = getFilesDir() + "/temp_documents";
                            File parentDir = new File(folder_path);

                            File[] files = parentDir.listFiles();
                            System.out.println("File dire : " + files);
                            if (files != null) {
                                for (File file : files) {
                                    if (file.isDirectory()) {
                                    } else {
                                        System.out.println("Absolute Path: " + file.getAbsolutePath());
                                        abs_file_path = file.getAbsolutePath();

                                        String URL_MASTER_SYNC_ODOO_FILE_UPLOAD = ServerIP + "" + BusinessAccessLayer.FILE_UPLOAD_URL;

                                        new HttpAsyncTask().execute(URL_MASTER_SYNC_ODOO_FILE_UPLOAD);
                                        try {
                                            Thread.sleep(15000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
//                                Toast.makeText(DashBoardActivity.this, "Image moved successfully", Toast.LENGTH_SHORT).show();
                            }

                            File images = new File(getFilesDir(), "/images");
                            if (!images.isDirectory()) {
                                images.mkdirs();
                            }
                            String folder_patht = getFilesDir() + "/temp_images";
                            File parentDirt = new File(folder_patht);

                            File[] filest = parentDirt.listFiles();
                            System.out.println("File dire : " + filest);
                            if (filest != null) {
                                for (File img : filest) {
                                    if (img.isDirectory()) {
                                    } else {
                                        System.out.println("Absolute Path: " + img.getAbsolutePath());
                                        abs_file_path = img.getAbsolutePath();
                                        String URL_MASTER_SYNC_ODOO_IMAGE_UPLOAD = ServerIP + "" + BusinessAccessLayer.IMAGE_UPLOAD_URL;
                                        new HttpAsyncTaskImage().execute(URL_MASTER_SYNC_ODOO_IMAGE_UPLOAD);
                                        try {
                                            Thread.sleep(15000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
//                                Toast.makeText(DashBoardActivity.this, "Document moved successfully", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }).start();


                   /* new Thread(new Runnable() {
                        public void run() {

                        }
                    }).start();*/
                }
            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("responseStr in handle exception ::" + e);
            }
        }


    };

    public String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(url);

            FileBody fileBody = new FileBody(new File(abs_file_path)); //image should be a String
            System.out.println("filebody : " + fileBody.toString());
            MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("file", fileBody);
            //    reqEntity.addPart("random", new StringBody(encameo1.random));
            //     reqEntity.addPart("fingerPrint", new StringBody(encameo1.fingerprint));
            httppost.setEntity(reqEntity);
            //     MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            //    reqEntity.addPart("file", fileBody);
            //    httppost.setEntity(reqEntity);
            //    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(httppost);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();


            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("result doc : " + result);
                JSONObject json = new JSONObject(result);
                String str = "";
                System.out.println("Result: " + json.getString("message"));

                File source = new File(abs_file_path);

                if (source.renameTo(new File(getFilesDir(), "/documents/" + source.getName()))) {
                    System.out.println("File is moved successful!");
                } else {
                    System.out.println("File is failed to move!");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class HttpAsyncTaskImage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("Result string: " + result);
                JSONObject json = new JSONObject(result);
                String str = "";
                System.out.println("Result: " + json.getString("message"));

                File source = new File(abs_file_path);

                if (source.renameTo(new File(getFilesDir(), "/images/" + source.getName()))) {
                    System.out.println("Image is moved successful!");
                } else {
                    System.out.println("Image is failed to move!");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void showMasterAlertDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(true);

        Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("OK");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("No");
        no.setVisibility(View.GONE);
        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();


            }
        });


        mContactUsDialog.show();

    }

    public void logoutDialog(final Context ctx, String txt) {

        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);
        mContactUsDialog.setCancelable(true);


        Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setTypeface(calibri_bold_typeface);
        txt_dia.setText(txt);

        yes.setText("Yes");
        yes.setTypeface(calibri_bold_typeface);
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("No");
        no.setTypeface(calibri_bold_typeface);
        yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();

                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                startActivity(intent);
                BusinessAccessLayer.mParetnRoleId = "";
                finish();
            }

        });

        no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();

            }
        });

        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContactUsDialog.show();

    }


}





