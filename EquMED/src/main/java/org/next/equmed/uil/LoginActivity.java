package org.next.equmed.uil;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.CustomProgressDialog;
import org.next.equmed.dal.Mst_Equipment_Status;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Consumables;
import org.next.equmed.dal.Trn_Equipment_Enrollment;
import org.next.equmed.dal.Trn_Installation_Enrollment;
import org.next.equmed.dal.Trn_Service_Details;
import org.next.equmed.dal.Trn_Training_Details;
import org.next.equmed.dal.Trn_User_Registration;
import org.next.equmed.dal.Trn_Voice_Of_Customer;
import org.next.equmed.dal.Trn_Warranty_Details;
import org.next.equmed.nal.HttpRequest;
import org.next.equmed.nal.NetworkAccessLayer_RESTFUL;
import org.w3c.dom.Text;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by next-03 on 1/12/15.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText eTxt_loginUsername, eTxt_loginUserpassword;
    Button btn_Login;
    private String finalUserName = "", selectedUserEmail = "";
    private String user_Id = "";
    private String mRoleType = "", reponseMasterDatStr = "", result_MstJson_Str = "", result_MstJson_Admin_Login_Str = "", response_MstJson_Admin_Login_Str = "";
    private String mFirstSync_DateTime = "2015/01/01 00:00:00";
    Toolbar toolbar_login;
    ArrayList<Bean> hos_selected_array_list = new ArrayList<Bean>();
  //  CheckBox chckbox_admin;
    String mStatus = "";
    Typeface calibri_typeface, calibri_bold_typeface;

    String ServerIP = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_new);

        BusinessAccessLayer.bug_class_name = "Login";

        calibri_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri_Bold.ttf");

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getViewCasting();
        setSupportActionBar(toolbar_login);
        this.setTitle("EquMED - Login");
        if (checkSharedPreference() == false) {
            System.out.println("checkSharedPreference in if::" + checkSharedPreference());
            createCustomDialog();
        } else if (NetworkAccessLayer_RESTFUL.isOnline(LoginActivity.this) == true) {
            System.out.println("checkSharedPreference in else::" + checkSharedPreference());
            //    downLoadData();
//            new Http_Reterive_MasterSync_EqMED().execute();
        }

        checkSharedPreference();

//        //  int t = Integer.parseInt("dsfd");
//        if (checkDB() == false) {
//            new Http_Reterive_MasterSync_EqMED().execute();
//        }
        getHospitalInformation();


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


    private void createCustomDialog() {
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.serverconfigurationdialog);
        dialog.setTitle("Server Configuration");
        dialog.setCancelable(false);

        final EditText serverIP = (EditText) dialog.findViewById(R.id.eTxt_IP);

        dialog.show();

        FloatingActionButton saveBtn = (FloatingActionButton) dialog.findViewById(R.id.btn_Save);


        // if button is clicked, close the custom dialog
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalIP = serverIP.getText().toString().trim();
                if (finalIP.length() > 0) {
//                    hideSoftKeyboard();

//                    if (Patterns.WEB_URL.matcher(finalIP).matches()) {
//                        System.out.println("Valid url ;;;;" + finalIP);
                    String serverIPValue = serverIP.getText().toString();
//                    SharedPreference dd = new SharedPreference();
//                    dd.saveFavorites(Login.this, serverIPValue);

                    SharedPreferences ServerDetails = getSharedPreferences(
                            "ServerDetails", MODE_PRIVATE);
                    SharedPreferences.Editor ServerDetailsEditor = ServerDetails
                            .edit();
                    ServerDetailsEditor.putString("ServerIP", serverIPValue);
                    ServerDetailsEditor.commit();


                    //  downLoadData();
//                    new Http_Reterive_MasterSync_EqMED().execute();

//                    try {
//                        FileOutputStream outputStream = getApplicationContext().openFileOutput("server_ip", Context.MODE_PRIVATE);
//
//                        outputStream.write(serverIPValue.getBytes());
//                        outputStream.close();
//                        System.out.println("sever ip file input ::"+outputStream);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//
//                        System.out.println("sever ip file input exce ::"+e);
//
//                    }
                    System.out.println("server IP in finalIP.toString().trim()" + finalIP.toString().trim());
                    BusinessAccessLayer.BASE_IP = finalIP.toString().trim();

                    dialog.dismiss();

                    checkSharedPreference();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Invalid Url ", Toast.LENGTH_SHORT).show();
//                        System.out.println("In Valid url ;;;;" + finalIP);
//                    }

                }

            }
        });

        FloatingActionButton resetBtn = (FloatingActionButton) dialog.findViewById(R.id.btn_Reset);
        // if button is clicked, close the custom dialog
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverIP.setText("http://");
            }
        });

    }

    @Override
    public void onBackPressed() {

        gotoBack();
    }

    private void gotoBack() {
        logoutDialog(LoginActivity.this, "Do you want to exit " +
                "? ");
    }

    public void logoutDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(true);

        Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("Yes");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("No");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();

                //finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                BusinessAccessLayer.mParetnRoleId = "";

            }

        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();

            }
        });

        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContactUsDialog.show();

    }







    /**
     * @Type ClickEvent
     * @Created_By Mohanraj.S
     * @Created_On 13-07-2015
     * @Updated_By
     * @Updated_On
     * @Description To Hide the KeyBoard to the Entire Project
     */
    private void hideKeyBoard() {
        /*InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);*/

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                getWindow().getDecorView().getWindowToken(), 0);
    }
    /* @Name getViewCasting()
  * @Type No Argument Method
  * @Created_By GokulRaj K.c
  * @Created_On 01-12-2015
  * @Updated_By
  * @Updated_On
  * @Description Casting the Fields
  */

    private void getViewCasting() {
        toolbar_login = (Toolbar) findViewById(R.id.toolbar_login);
        eTxt_loginUsername = (EditText) findViewById(R.id.eTxt_loginUsername);
        eTxt_loginUserpassword = (EditText) findViewById(R.id.eTxt_loginUserpassword);
        btn_Login = (Button) findViewById(R.id.btn_Login);
      //  chckbox_admin = (CheckBox) findViewById(R.id.chckbox_admin);

        btn_Login.setTypeface(calibri_bold_typeface);
        eTxt_loginUsername.setTypeface(calibri_typeface);
        eTxt_loginUserpassword.setTypeface(calibri_typeface);
     //   chckbox_admin.setTypeface(calibri_typeface);
        getViewClickEvents();

    }

    /**
     * @Name getViewClickEvents()
     * @Type No Argument Method
     * @Created_By GokulRaj K.c
     * @Created_On 01-12-2015
     * @Updated_By
     * @Updated_On
     * @Description Declared onclick events in User Activity
     **/

    private void getViewClickEvents() {
        btn_Login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        hideKeyBoard();
        switch (v.getId()) {
            case R.id.btn_Login:
                hideKeyBoard();

               /* if ( eTxt_loginUsername.getText().toString().equalsIgnoreCase( "admin" ) && eTxt_loginUserpassword.getText().toString().equalsIgnoreCase( "admin" ) ) {


                    Intent login = new Intent( LoginActivity.this, DashBoardActivity.class );
                    startActivity( login );
                    finish();
                } else {

                    Intent login = new Intent( LoginActivity.this, DashBoardActivity.class );
                    startActivity( login );
                    finish();

                }*/

                getUserValidation();

                /*Intent login = new Intent( LoginActivity.this, DashBoardActivity.class );
                startActivity( login );
                finish();
                break;*/


            default:
                break;
        }
    }


    private void getUserValidation() {

        if (eTxt_loginUsername.getText().toString().equalsIgnoreCase("")
                && eTxt_loginUserpassword.getText().toString().equalsIgnoreCase("")) {
            showValidationDialog(LoginActivity.this, "Please enter credential");

        } else if (eTxt_loginUsername.getText().toString().trim().equalsIgnoreCase("")) {
            showValidationDialog(LoginActivity.this, "Please enter user name");

        } else if (eTxt_loginUserpassword.getText().toString().trim().equalsIgnoreCase("")) {

            showValidationDialog(LoginActivity.this, "Please enter password");

        } else {
           if( LoginUser(eTxt_loginUsername.getText().toString().trim(),
                    eTxt_loginUserpassword.getText().toString().trim()) == true)
            {
                getUserHospital();
                setUserCredentials(finalUserName, BusinessAccessLayer.mParetnRoleId, BusinessAccessLayer.mUserId);
                gotoLogin();
            }
            else
            {
                if (NetworkAccessLayer_RESTFUL.isOnline(LoginActivity.this)) {
                    new Http_Reterive_Login_Admin().execute();
                } else {
                    showValidationDialog(LoginActivity.this, "Invalid user! Please enable internet connection to login as admin");
                }
            }
        }

        /*else if (chckbox_admin.isChecked() == true) {
            if (NetworkAccessLayer_RESTFUL.isOnline(LoginActivity.this)) {
                new Http_Reterive_Login_Admin().execute();
//
            } else {
                showValidationDialog(LoginActivity.this, "Please check your network connection");

            }

        } else if (LoginUser(eTxt_loginUsername.getText().toString().trim(),
                eTxt_loginUserpassword.getText().toString().trim()) == true) {
            getUserHospital();
            setUserCredentials(finalUserName, BusinessAccessLayer.mParetnRoleId, BusinessAccessLayer.mUserId);
            gotoLogin();

        } else {
            showValidationDialog(LoginActivity.this, "Invalid user name or password");
        }*/

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private class Http_Reterive_Login_Admin extends AsyncTask<String, Void, String> {

        private ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("Signing in...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCanceledOnTouchOutside(false);
            progress.setProgress(0);
            //   progress.setCancelable( true );
            progress.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            List<NameValuePair> args = new ArrayList<NameValuePair>();
            args.add(new BasicNameValuePair("login", eTxt_loginUsername.getText().toString().trim()));
            args.add(new BasicNameValuePair("password", md5(eTxt_loginUserpassword.getText().toString().trim())));
//            args.add(new BasicNameValuePair("password", eTxt_loginUserpassword.getText().toString().trim()));
            System.out.println("JSON Result Admin_Login ARGS ====>" + args);

            String URLforConnectionLoginAdmin = ServerIP + "" + BusinessAccessLayer.ADMIN_LOGIN_URL;
            System.out.println("JSON Result Admin_Login URLforConnection ====>" + URLforConnectionLoginAdmin);
//            System.out.println("JSON Result Admin_Login URL ====>" + BusinessAccessLayer.ADMIN_LOGIN_URL);

            HttpRequest.sharedInstance().doHttpRequest(URLforConnectionLoginAdmin, args);
            result_MstJson_Admin_Login_Str = HttpRequest.sharedInstance().responseString;

            System.out.println("JSON Result Admin_Login  ====>" + result_MstJson_Admin_Login_Str);

            return result_MstJson_Admin_Login_Str;
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
//            messageHanler.sendEmptyMessage(0);
            String responseMessage = "", responseStatus = "";

            String json = "Assuming that here is your JSON response";
            try {
                JSONObject parentObject = new JSONObject(result);
                JSONObject userDetails = parentObject.getJSONObject("response");

                //And then read attributes like
                responseMessage = userDetails.getString("message");
//                String phone = userDetails.getString("user_phone");
//                String id = userDetails.getString("re‌​f_id");

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            progress.dismiss();
            if (responseMessage.equalsIgnoreCase("Success")) {
                BusinessAccessLayer.mParetnRoleId = "Y";
                getHospitalInformation();
                setUserCredentials("Administrator", BusinessAccessLayer.mParetnRoleId, BusinessAccessLayer.mUserId);
                gotoLogin();
            } else {
                showValidationDialog(LoginActivity.this, "Invalid user name or password");

            }
        }
    }

    void getUserHospital() {


        // sample hospital string in database = hospitalID-hospitalName-hosptailassigned(true/false)-hospitalLocation , hospitalID2-hospitalName2-hosptailassigned2(true/false)-hospitalLocation2
        System.out.println("BusinessAccessLayer.mAssigendHospitalId ::" + BusinessAccessLayer.mAssigendHospitalId);
        String[] hospitalList = BusinessAccessLayer.mAssigendHospitalId.split("~");

        for (int i = 0; i < hospitalList.length; i++) {


            String[] splitterStr = hospitalList[i].split("-");

            if (splitterStr[2].equalsIgnoreCase("true")) {

                Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(LoginActivity.this);
                mst_hospital_enroll.open();
                Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByHospital_ID_Active(splitterStr[0]);
                if (mCr_mst_hospital_enroll.getCount() > 0) {

                    Bean beanObj = new Bean();
                    beanObj.setHos_Id(splitterStr[0]);
                    beanObj.setHos_name(splitterStr[1]);
                    beanObj.setAssignedHospital(true);
                    beanObj.setHos_location(splitterStr[3]);

                    hos_selected_array_list.add(beanObj);
                }
            }


        }
        BusinessAccessLayer.hospitalArray = new String[hos_selected_array_list.size() + 1];
        BusinessAccessLayer.hospitalLocation = new String[hos_selected_array_list.size() + 1];
        BusinessAccessLayer.hospitalNameAndLocation = new String[hos_selected_array_list.size() + 1];
        BusinessAccessLayer.mHospitalHashMap.clear();
        BusinessAccessLayer.mHospitalHashMapByID.clear();
        BusinessAccessLayer.mHospitalLocationHashMap.clear();

        System.out.println("hos arr count:" + hos_selected_array_list.size());


        BusinessAccessLayer.hospitalArray[0] = "Select Hospital";
        BusinessAccessLayer.hospitalLocation[0] = "";
        BusinessAccessLayer.hospitalNameAndLocation[0] = "Select Hospital";
        BusinessAccessLayer.mHospitalHashMap.put("Select Hospital", "0");
        BusinessAccessLayer.mHospitalHashMapByID.put("0", "0");
        BusinessAccessLayer.mHospitalLocationHashMap.put("0", "0");

        for (int i = 0; i < hos_selected_array_list.size(); i++) {


            String finalString = hos_selected_array_list.get(i).getHos_name() + " / " + hos_selected_array_list.get(i).getHos_location();

            // 0 - Hos Id
            // 1 Hos Name
            // 2 - Hospital assinged (true/false)
            //3 - Hos location
            int idCount = i + 1;

            BusinessAccessLayer.hospitalArray[idCount] = hos_selected_array_list.get(i).getHos_name();
            BusinessAccessLayer.hospitalLocation[idCount] = hos_selected_array_list.get(i).getHos_location();
            BusinessAccessLayer.hospitalNameAndLocation[idCount] = finalString;
            BusinessAccessLayer.mHospitalHashMap.put(hos_selected_array_list.get(i).getHos_name(), hos_selected_array_list.get(i).getHos_Id());
            BusinessAccessLayer.mHospitalHashMapByID.put(hos_selected_array_list.get(i).getHos_Id(), "" + idCount);
            BusinessAccessLayer.mHospitalLocationHashMap.put(finalString, hos_selected_array_list.get(i).getHos_Id());


        }


    }

    void getHospitalInformation() {

        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(LoginActivity.this);
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByActive();

            BusinessAccessLayer.hospitalArray = new String[mCr_mst_hospital_enroll.getCount() + 1];
            BusinessAccessLayer.hospitalLocation = new String[mCr_mst_hospital_enroll.getCount() + 1];
            BusinessAccessLayer.hospitalNameAndLocation = new String[mCr_mst_hospital_enroll.getCount() + 1];
            BusinessAccessLayer.mHospitalHashMap.clear();
            BusinessAccessLayer.mHospitalHashMapByID.clear();
            BusinessAccessLayer.mHospitalLocationHashMap.clear();

            BusinessAccessLayer.hospitalArray[0] = "Select Hospital";
            BusinessAccessLayer.hospitalLocation[0] = "";
            BusinessAccessLayer.hospitalNameAndLocation[0] = "Select Hospital";
            BusinessAccessLayer.mHospitalHashMap.put("Select Hospital", "0");

            BusinessAccessLayer.mHospitalHashMapByID.put("0", "0");

            BusinessAccessLayer.mHospitalLocationHashMap.put("0", "0");


            if (mCr_mst_hospital_enroll.getCount() > 0) {
                for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                    mCr_mst_hospital_enroll.moveToPosition(i);

                    int idCount = i + 1;
                    String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                    String hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));

                    String hospital_location = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));
                    String finalString = hospital_name + " / " + hospital_location;

                    BusinessAccessLayer.hospitalArray[idCount] = hospital_name;
                    BusinessAccessLayer.hospitalLocation[idCount] = hospital_location;
                    BusinessAccessLayer.hospitalNameAndLocation[idCount] = finalString;
                    BusinessAccessLayer.mHospitalHashMap.put(hospital_name, hospital_id);
                    BusinessAccessLayer.mHospitalHashMapByID.put(hospital_id, "" + idCount);
                    BusinessAccessLayer.mHospitalLocationHashMap.put(finalString, hospital_id);

                }
                mCr_mst_hospital_enroll.close();
            }

            Mst_Hospital_Enrollment.close();

        } catch (Exception e) {
            System.out.println("Exception getHospitalInformation :" + e);
        }

        if (BusinessAccessLayer.hospitalArray.length > 0) {

            System.out.println("BusinessAccessLayer" + BusinessAccessLayer.hospitalArray.length);


        } else {
            System.out.println("No Hospital Values");
        }


    }

    public void showValidationDialog(final Context ctx, String txt) {
        hideKeyBoard();

        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(true);

        Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("Ok");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("Cancel");

        txt_dia.setTypeface(calibri_bold_typeface);
        yes.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

        no.setVisibility(View.GONE);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();


            }
        });


        mContactUsDialog.show();

    }

    private void gotoLogin() {

        eTxt_loginUsername.setText("");
        eTxt_loginUserpassword.setText("");
        Intent loginIntent = new Intent(LoginActivity.this,
                DashBoardActivity.class);
        startActivity(loginIntent);
        finish();
    }

    /**
     * @Subject User Login method
     * @Created_By Aracindhakumar.S
     * @Created_On 20-07-2015
     * @Updated_By
     * @Updated_On
     */

    private boolean LoginUser(String userName, String password) {

        Trn_User_Registration mst_user = new Trn_User_Registration(
                LoginActivity.this);
        mst_user.open();

        Cursor logincursor = mst_user.fetchByUserNamePassword(userName,
                md5(password));
        System.out.println("logincursor::" + logincursor.getCount());
        if (logincursor.getCount() > 0) {
            for (int i = 0; i < logincursor.getCount(); i++) {
                logincursor.moveToPosition(i);

                String fName = logincursor.getString(logincursor
                        .getColumnIndex("" + BusinessAccessLayer.USER_FIRST_NAME
                                + ""));
                String lName = logincursor.getString(logincursor
                        .getColumnIndex("" + BusinessAccessLayer.USER_LAST_NAME
                                + ""));
                String userEmail = logincursor.getString(logincursor
                        .getColumnIndex("" + BusinessAccessLayer.USER_EMAIL
                                + ""));

                finalUserName = fName + " " + lName;
                selectedUserEmail = userEmail;

                BusinessAccessLayer.mUserId = logincursor.getString(logincursor.getColumnIndex(""
                        + BusinessAccessLayer.USER_ID + ""));

                BusinessAccessLayer.mParetnRoleId = logincursor.getString(logincursor
                        .getColumnIndex("" + BusinessAccessLayer.USER_ADMIN + ""));
                BusinessAccessLayer.mAssigendHospitalId = logincursor.getString(logincursor
                        .getColumnIndex("" + BusinessAccessLayer.USER_HOSPITAL + ""));
            }
            // logincursor.close();
            Trn_User_Registration.close();

            return true;

        } else {
//            Toast.makeText( getApplicationContext(),
//                    "Invalid User name or Password", Toast.LENGTH_SHORT ).show();
            return false;

        }
    }

    private void setUserCredentials(String fname, String roletype,
                                    String role_Id) {
        SharedPreferences userDetails;
        SharedPreferences.Editor userDetailsEditor;
        userDetails = getSharedPreferences("UserDetails", MODE_PRIVATE);
        userDetailsEditor = userDetails.edit();
        userDetailsEditor.clear();
        userDetailsEditor.putString("firstname", fname);
        userDetailsEditor.putString("roletype", roletype);
        userDetailsEditor.putString("userId", role_Id);
        userDetailsEditor.commit();
    }

    /*protected void hidekeyboard() {
        // TODO Auto-generated method stub
        InputMethodManager inputManager = (InputMethodManager ) getSystemService( Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(rL_parent_login.getWindowToken(), 0);
    }*/




    /*--------Class End Here-------*/
}
