package org.next.equmed.uil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.dal.Mst_Equipment_Status;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Documents;
import org.next.equmed.dal.Trn_Equipment_Enroll_Accessories;
import org.next.equmed.dal.Trn_Equipment_Enrollment;
import org.next.equmed.dal.Trn_Images;
import org.next.equmed.dal.Trn_Installation_Enrollment;
import org.next.equmed.nal.NetworkAccessLayer_RESTFUL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

import static org.next.equmed.bal.BusinessAccessLayer.mParetnRoleId;

/**
 * Created by nextmoveo-1 on 14/12/15.
 */
public class ViewMedicalEquipAppCompat extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar_viewDetails;


    ListView listView_Details;
    Spinner spn_View_hosptname;
    String[] hospitalArray = null, hospitalLocation = null;
    HashMap<String, String> mHospitalHashMap = new HashMap<String, String>();

    ArrayList<Bean> equipmentDetails = new ArrayList<>();
    RelativeLayout rL_ListView;
    int hos_selectedPosition = 0;
    String selectedHospitalId = "";

    private String filepath = "Database";
    boolean isAddedNew = true;

    int selectedPostionForEquip = 0;

    FloatingActionButton btn_fab_viewMedEqpmnts_AddNew;

    HashMap<String, String> mHospitalLocationHashMap = new HashMap<String, String>();
    TextView txtVw_no_medicalEqpmntsFound;
    private String mRoleType = "";
    private static Dialog mContactUsDialog, mTechnicalUsDialog;
    ArrayList<Bean> hos_selected_array_list = new ArrayList<Bean>();

    Typeface calibri_typeface,calibri_bold_typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusinessAccessLayer.bug_class_name ="ViewMedicalEquipment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.viewdetalis_activity);

        calibri_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri_Bold.ttf");


        getViewCasting();
        txtVw_no_medicalEqpmntsFound.setVisibility(View.GONE);

        setSupportActionBar(toolbar_viewDetails);
    //    getActiveHospitalInformation();

        this.setTitle("Medical Equipment");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        setUserDetails();

//        getHospitalInformation();

        System.out.println(" BusinessAccessLayer.mParetnRoleId in ViewMedicalEquipAppCompat :"+ BusinessAccessLayer.mParetnRoleId);
        if ( BusinessAccessLayer.mParetnRoleId.equalsIgnoreCase("Y")) {
            // Created as admin
            getActiveHospitalInformation();
        } else {
            // Created as user
            getUserHospital();
        }

        exportDatabseView(BusinessAccessLayer.DATABASE_NAME);

        try {
            if (BusinessAccessLayer.hospitalNameAndLocation.length > 0) {
                spn_View_hosptname.setAdapter(new ArrayAdapter<String>(ViewMedicalEquipAppCompat.this, android.R.layout.simple_spinner_dropdown_item, BusinessAccessLayer.hospitalNameAndLocation));
                //spn_View_hosptList.setAdapter( new ArrayAdapter< String >( ViewMedicalEquipAppCompat.this, android.R.layout.simple_spinner_dropdown_item, hospitalArray ) );

            } else {
                System.out.println("No Hospital Values");
            }
        } catch (Exception e) {
            System.out.println("Exception spn_View_hosptname" + e);
        }


//        for (int i = 0; i < BusinessAccessLayer.hospitalNameAndLocation.length; i++) {
//            System.out.println("BusinessAccessLayer.hospitalNameAndLocation[ hos_selectedPosition ]" + BusinessAccessLayer.hospitalNameAndLocation[hos_selectedPosition]);
//        }

        try {
            if (hos_selectedPosition != 0) {
                selectedHospitalId = BusinessAccessLayer.mHospitalLocationHashMap.get(BusinessAccessLayer.hospitalNameAndLocation[hos_selectedPosition]);
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = selectedHospitalId;

                viewEquipEnroll(selectedHospitalId);
            }
        } catch (Exception e) {
            System.out.println("Exception spn_View_hoame" + e);
        }
//        selectedHospitalId = BusinessAccessLayer.mHospitalLocationHashMap.get(BusinessAccessLayer.hospitalNameAndLocation[hos_selectedPosition]);
//        BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = selectedHospitalId;
//
//        viewEquipEnroll(selectedHospitalId);


    }

    void getUserHospital() {

        // sample hospital string in database = hospitalID-hospitalName-hosptailassigned(true/false)-hospitalLocation , hospitalID2-hospitalName2-hosptailassigned2(true/false)-hospitalLocation2
        System.out.println("BusinessAccessLayer.mAssigendHospitalId ::" + BusinessAccessLayer.mAssigendHospitalId);
        String[] hospitalList = BusinessAccessLayer.mAssigendHospitalId.split("~");

        System.out.println("hospitalList ==> "+hospitalList);

        for (int i = 0; i < hospitalList.length; i++) {


            String[] splitterStr = hospitalList[i].split("-");

            if (splitterStr[2].equalsIgnoreCase("true")) {

                Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(ViewMedicalEquipAppCompat.this);
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


    void getActiveHospitalInformation() {

        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(ViewMedicalEquipAppCompat.this);
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByActive();

            BusinessAccessLayer.hospitalArray = new String[mCr_mst_hospital_enroll.getCount()+1];
            BusinessAccessLayer.hospitalLocation = new String[mCr_mst_hospital_enroll.getCount()+1];
            BusinessAccessLayer.hospitalNameAndLocation = new String[mCr_mst_hospital_enroll.getCount()+1];
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
                    BusinessAccessLayer.mHospitalHashMapByID.put(hospital_id,""+idCount);
                    BusinessAccessLayer.mHospitalLocationHashMap.put(finalString, hospital_id);

                }
                mCr_mst_hospital_enroll.close();
            }

            Mst_Hospital_Enrollment.close();

        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }


    }


    void getHospitalInformation() {

        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(ViewMedicalEquipAppCompat.this);
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetch();

            hospitalArray = new String[mCr_mst_hospital_enroll.getCount()];

            if (mCr_mst_hospital_enroll.getCount() > 0) {
                for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                    mCr_mst_hospital_enroll.moveToPosition(i);


                    String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                    String hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));


                    hospitalArray[i] = hospital_name;

                    mHospitalHashMap.put(mCr_mst_hospital_enroll
                                    .getString(mCr_mst_hospital_enroll
                                            .getColumnIndex(BusinessAccessLayer.HOSPITAL_NAME)),
                            mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll
                                    .getColumnIndex(BusinessAccessLayer.HOSPITAL_ID)));

                }
                mCr_mst_hospital_enroll.close();

            }

            Mst_Hospital_Enrollment.close();

        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }

        if (hospitalArray.length > 0) {

            spn_View_hosptname.setAdapter(new ArrayAdapter<String>(ViewMedicalEquipAppCompat.this, android.R.layout.simple_spinner_dropdown_item, hospitalArray));
            //spn_View_hosptList.setAdapter( new ArrayAdapter< String >( ViewMedicalEquipAppCompat.this, android.R.layout.simple_spinner_dropdown_item, hospitalArray ) );


        } else {
            System.out.println("No Hospital Values");
        }
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

    private void getViewCasting() {
        listView_Details = (ListView) findViewById(R.id.listView_Details);
        toolbar_viewDetails = (Toolbar) findViewById(R.id.toolbar_viewDetails);
        txtVw_no_medicalEqpmntsFound = (TextView) findViewById(R.id.txtVw_no_medicalEqpmntsFound);
        rL_ListView = (RelativeLayout) findViewById(R.id.rL_ListView);
        spn_View_hosptname = (Spinner) findViewById(R.id.spn_View_hosptname);

        txtVw_no_medicalEqpmntsFound.setTypeface(calibri_typeface);

        btn_fab_viewMedEqpmnts_AddNew = (FloatingActionButton) findViewById(R.id.btn_fab_viewMedEqpmnts_AddNew);

        spn_View_hosptname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selectedHospitalId = BusinessAccessLayer.mHospitalLocationHashMap.get(BusinessAccessLayer.hospitalNameAndLocation[position]);

                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = selectedHospitalId;


                hos_selectedPosition = position;
                viewEquipEnroll(selectedHospitalId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TextView txtV_Header_DeviceName = (TextView) findViewById(R.id.txtV_Header_DeviceName);
        TextView txtV_Header_Dept = (TextView) findViewById(R.id.txtV_Header_Dept);
        TextView txtV_Header_SerialNo = (TextView) findViewById(R.id.txtV_Header_SerialNo);
        TextView txtV_Header_Modify = (TextView) findViewById(R.id.txtV_Header_Modify);

        txtV_Header_DeviceName.setTypeface(calibri_typeface);
        txtV_Header_Dept.setTypeface(calibri_typeface);
        txtV_Header_SerialNo.setTypeface(calibri_typeface);
        txtV_Header_Modify.setTypeface(calibri_typeface);


        btn_fab_viewMedEqpmnts_AddNew.setOnClickListener(this);

    }

    /**
     * Let's the user tap the activity icon to go 'home'.
     * Requires setHomeButtonEnabled() in onCreate().
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_others, menu);
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = menuItem.getItemId();
        switch (menuItem.getItemId()) {

            case R.id.contact_Us:
                showTechincalUsDialog(ViewMedicalEquipAppCompat.this);
                return true;

            case R.id.about_Us:
                showContactUsDialog(ViewMedicalEquipAppCompat.this);
                return true;
            case R.id.log_out:
                logoutDialog(ViewMedicalEquipAppCompat.this, "Do you want to logout ? ");
//                gotoBackLogout();
                return true;
            case android.R.id.home:
                startActivityAfterCleanup(DashBoardActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }

//        if (id == android.R.id.home) {
//            // ProjectsActivity is my 'home' activity
//
//            startActivityAfterCleanup(DashBoardActivity.class);
//            return true;
//        }
//
//
//        /*//noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }*/
//
//        return super.onOptionsItemSelected(menuItem);
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
                if (NetworkAccessLayer_RESTFUL.isOnline(ViewMedicalEquipAppCompat.this) == true) {
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
                    UserInterfaceLayer.alert(ViewMedicalEquipAppCompat.this, "Please check your network connection", 1);
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

        if (NetworkAccessLayer_RESTFUL.isOnline(ViewMedicalEquipAppCompat.this) == true) {
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

            }
        });
        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContactUsDialog.show();
    }

    private void gotoBackLogout() {
        logoutDialog(ViewMedicalEquipAppCompat.this, "Do you want to logout ? ");

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

        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();

                Intent intent = new Intent(ViewMedicalEquipAppCompat.this, LoginActivity.class);
                startActivity(intent);
                BusinessAccessLayer.mParetnRoleId = "";
                finish();
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

    private void startActivityAfterCleanup(Class<?> cls) {
//        if (projectsDao != null) projectsDao.close();
        goToBack();
//        Intent intent = new Intent(getApplicationContext(), cls);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
//        BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = "";
//
//        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goToBack();

    }

    private void goToBack() {

        Intent homePageIntent = new Intent(ViewMedicalEquipAppCompat.this,
                DashBoardActivity.class);
        // turnOffGPS();

        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
        BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = "";
        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID = "";
//        BusinessAccessLayer.hospitalNameAndLocation = null;
        startActivity(homePageIntent);
        finish();


    }

    private void setUserDetails() {
        SharedPreferences userDetails = getSharedPreferences("UserDetails",
                MODE_PRIVATE);
        mRoleType = userDetails.getString("roletype", "");
        System.out.println("Entered VME RoleType--=>" + mRoleType);

        Toast.makeText(ViewMedicalEquipAppCompat.this, "IsAdminRoleType" + mRoleType, Toast.LENGTH_LONG).show();
    }


    private void viewEquipEnroll(String hosId) {

        selectedPostionForEquip = 0;
        Trn_Equipment_Enrollment equip_enroll = new Trn_Equipment_Enrollment(
                ViewMedicalEquipAppCompat.this);
        equip_enroll.open();
        Cursor mCr_trn_equipenroll = equip_enroll.fetchByHospital(hosId);
        equipmentDetails.clear();
        if (mCr_trn_equipenroll.getCount() > 0) {
            for (int i = 0; i < mCr_trn_equipenroll.getCount(); i++) {
                mCr_trn_equipenroll.moveToPosition(i);

                Bean bean = new Bean();

                String ernroll_id = mCr_trn_equipenroll.getString(mCr_trn_equipenroll.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_ID + ""));

                bean.seteq_enroll_id(ernroll_id);

                String eq_location_code = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_LOCATION_CODE + ""));

                bean.setEq_location_code(eq_location_code);

                String eq_serialno = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_SERIALNO + ""));
                bean.setEq_serialno(eq_serialno);

                String eq_make = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_MAKE + ""));
                bean.setEq_make(eq_make);

                String eq_model = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_MODEL + ""));

                bean.setEq_model(eq_model);

                String eq_id = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_ID + ""));

                bean.setEq_id(eq_id);

                String eq_installdate = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_DATE + ""));
                bean.setEq_install_date(eq_installdate);

                String eq_servicetagno = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_SERVICE_TAGNO + ""));
                bean.setEq_service_tagno(eq_servicetagno);

                String eq_notes = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_NOTES + ""));
                bean.setEq_notes(eq_notes);

                String eq_install_status = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_STATUS + ""));

                bean.setEq_install_status(eq_install_status);

                String eq_install_notes = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_NOTES + ""));

                bean.setEq_install_notes(eq_install_notes);

                String eq_working_status = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_WORKING_STATUS + ""));

                bean.setEq_working_status(eq_working_status);


                String eq_working_notes = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_WORKING_NOTES + ""));

                bean.setEq_working_notes(eq_working_notes);

                String sync_status = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                bean.setSync_status(sync_status);

                Mst_Equipment_Status mst_equipstatus = new Mst_Equipment_Status(ViewMedicalEquipAppCompat.this);
                mst_equipstatus.open();
                Cursor mCr_mst_equip_status = mst_equipstatus.fetchByEq_Id(eq_id);

                if (mCr_mst_equip_status.getCount() > 0) {
                    mCr_mst_equip_status.moveToPosition(0);
                    String eq_name = mCr_mst_equip_status.getString(mCr_mst_equip_status.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
                    bean.setEq_name(eq_name);
                }


                Mst_Equipment_Status.close();


                Trn_Equipment_Enroll_Accessories trn_equip_enroll_access = new Trn_Equipment_Enroll_Accessories(ViewMedicalEquipAppCompat.this);
                trn_equip_enroll_access.open();
                Cursor mCr_equip_enroll_access = trn_equip_enroll_access.fetchByEq_Enroll_Id(ernroll_id);
                if (mCr_equip_enroll_access.getCount() > 0) {
                    for (int int_enroll_access = 0; int_enroll_access < mCr_equip_enroll_access.getCount(); int_enroll_access++) {
                        mCr_equip_enroll_access.moveToPosition(int_enroll_access);
                        String eq_enroll_ups = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_UPS + ""));

                        bean.setEq_emroll_ups(eq_enroll_ups);

                        String eq_enroll_manual = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_MANUAL + ""));

                        bean.setEq_emroll_ups(eq_enroll_manual);


                        String eq_enroll_stabilizer = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_STABILIZER + ""));
                        bean.setEq_emroll_ups(eq_enroll_stabilizer);


                        String eq_enroll_other = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_OTHERS + ""));
                        bean.setEq_emroll_ups(eq_enroll_other);


                    }
                }

                equipmentDetails.add(bean);

            }


        }

        System.out.println("equipmentDetails:" + equipmentDetails.size());

        try {
            if (equipmentDetails.size() > 0) {
                rL_ListView.setVisibility(View.VISIBLE);
                txtVw_no_medicalEqpmntsFound.setVisibility(View.GONE);
                HospitalListAdapter adapter = new HospitalListAdapter(ViewMedicalEquipAppCompat.this, equipmentDetails);
                listView_Details.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                rL_ListView.setVisibility(View.GONE);
                txtVw_no_medicalEqpmntsFound.setVisibility(View.VISIBLE);
                txtVw_no_medicalEqpmntsFound.setText("No medical equipment details found");

            }
        } catch (Exception e) {
            System.out.println("Exception" + e);
        }


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btn_fab_viewMedEqpmnts_AddNew:

                String selectedHospitalStr = spn_View_hosptname.getSelectedItem().toString();

                System.out.println("spn_View_hosptname:" + selectedHospitalStr);
                if (selectedHospitalStr.equalsIgnoreCase("Select Hospital")) {
                    showValidationDialog(ViewMedicalEquipAppCompat.this, "Please select hospital ");
                } else {
                    BusinessAccessLayer.editPage = true;
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
                    Intent medical = new Intent(ViewMedicalEquipAppCompat.this, MedicalEquipmentActivity.class);
                    BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG = 0;
                    startActivity(medical);
                    finish();
                }

                break;
            default:
                break;
        }


    }
    // Adapter Class

    private class HospitalListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;

        public HospitalListAdapter(Context context, ArrayList<Bean> equipmentdetails) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return equipmentDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = l_InflaterDeviceList.inflate(
                        R.layout.inflate_list_deivceitems, parent, false);

                holder.rL_UpdateDeleteData = (RelativeLayout) convertView.findViewById(R.id.rL_UpdateDeleteData);

                holder.txtV_Equip_SerialNo = (TextView) convertView.findViewById(R.id.txtV_Equip_SerialNo);
                holder.txtV_Equip_SerialNo.setTypeface(calibri_bold_typeface);

                holder.txtV_Equip_name = (TextView) convertView.findViewById(R.id.txtV_Equip_name);
                holder.txtV_Equip_name.setTypeface(calibri_typeface);
//                holder.txtV_Equip_InstallDate = ( TextView ) convertView.findViewById( R.id.txtV_Equip_InstallDate );
                holder.txtV_EquipIns_serialNo = (TextView) convertView.findViewById(R.id.txtV_EquipIns_serialNo);
                holder.txtV_EquipIns_serialNo.setTypeface(calibri_typeface);

                holder.imgV_UpdateData = (ImageView) convertView.findViewById(R.id.imgV_UpdateData);

                holder.imgV_DeleteData = (ImageView) convertView.findViewById(R.id.imgV_DeleteData);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int sNo = position + 1;
            holder.txtV_Equip_SerialNo.setText("" + sNo);
            holder.txtV_Equip_name.setText(equipmentDetails.get(position).getEq_name());
//            holder.txtV_Equip_InstallDate.setText( equipmentDetails.get( position ).getEq_install_date() );
            String serial_number = equipmentDetails.get(position).getEq_serialno();
            if(serial_number.length()>7)
            {
                serial_number = serial_number.substring(0,7)+"..";
            }
            holder.txtV_EquipIns_serialNo.setText(serial_number);

            String syncStatus = equipmentDetails.get(position).getSync_status();
            if(syncStatus.equals("0"))
            {
                holder.txtV_Equip_SerialNo.setTextColor(Color.parseColor("#ff0000"));
            }
            else
            {
                holder.txtV_Equip_SerialNo.setTextColor(Color.parseColor("#009933"));
            }

            holder.imgV_UpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Mst_Equipment_Status mst_equipstatus = new Mst_Equipment_Status(ViewMedicalEquipAppCompat.this);
                    mst_equipstatus.open();
                    Cursor mCr_mst_equip_status = mst_equipstatus.fetchByEq_Id(equipmentDetails.get(position).getEq_id());
                    System.out.println("Cursor count:" + mCr_mst_equip_status.getCount());
                    mCr_mst_equip_status.moveToPosition(0);
                    String eq_name = mCr_mst_equip_status.getString(mCr_mst_equip_status.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
                    String eq_status = mCr_mst_equip_status.getString(mCr_mst_equip_status.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));

                    if (eq_status.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG = 1;
                        BusinessAccessLayer.editPage = true;
                        Intent updateIntent = new Intent(ViewMedicalEquipAppCompat.this, MedicalEquipmentActivity.class);
                        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = equipmentDetails.get(position).geteq_enroll_id();
                        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID = equipmentDetails.get(position).getEq_id();
                        startActivity(updateIntent);

                    } else {

                        showValidationDialog(ViewMedicalEquipAppCompat.this, "You can't update equipment because " + eq_name + " was In-Active");
                    }

                    Mst_Equipment_Status.close();

// update query

                }
            });

            holder.imgV_DeleteData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    isAddedNew = false;
                    selectedPostionForEquip = position;
                    showContactUsDialog(ViewMedicalEquipAppCompat.this, BusinessAccessLayer.DELETE_MESSAGE);


                }
            });


            return convertView;
        }


        class ViewHolder {
            RelativeLayout rL_UpdateDeleteData;
            TextView txtV_Equip_SerialNo, txtV_Equip_name, txtV_EquipIns_serialNo;
            ImageView imgV_UpdateData, imgV_DeleteData;

        }

    }

    public void exportDatabseView(String databaseName) {
        try {

            BusinessAccessLayer.myExternalFile = new File(ViewMedicalEquipAppCompat.this.getExternalFilesDir(filepath), BusinessAccessLayer.DATABASE_NAME);
            System.out.println("directory myExternalFile   " + BusinessAccessLayer.myExternalFile);
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + ViewMedicalEquipAppCompat.this.getPackageName()
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

    public void showContactUsDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(true);

        final Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("Yes");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("No");

        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

        if (isAddedNew == true) {
            no.setVisibility(View.GONE);
            yes.setText("OK");

        } else {
            no.setVisibility(View.VISIBLE);
        }

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();


                Trn_Equipment_Enrollment equip_enroll = new Trn_Equipment_Enrollment(ViewMedicalEquipAppCompat.this);
                equip_enroll.open();
                Cursor del_cursor = equip_enroll.updateBy_flag_Eq_Enroll_Id(equipmentDetails.get(selectedPostionForEquip).geteq_enroll_id());

                Trn_Images trn_image = new Trn_Images(ViewMedicalEquipAppCompat.this);
                trn_image.open();
                Cursor img_cursor = trn_image.updateBy_Img_Id(equipmentDetails.get(selectedPostionForEquip).geteq_enroll_id());

                Trn_Documents trn_docAdapt = new Trn_Documents(ViewMedicalEquipAppCompat.this);
                trn_docAdapt.open();
                Cursor doc_cursor = trn_docAdapt.updateBy_Doc_Id(equipmentDetails.get(selectedPostionForEquip).geteq_enroll_id());

                Trn_Equipment_Enroll_Accessories trn_equip_enroll_access = new Trn_Equipment_Enroll_Accessories(ViewMedicalEquipAppCompat.this);
                trn_equip_enroll_access.open();
                Cursor acc_cursor = trn_equip_enroll_access.update_flag_By_Eq_Enroll_Id(equipmentDetails.get(selectedPostionForEquip).geteq_enroll_id());


                Trn_Installation_Enrollment install_enroll = new Trn_Installation_Enrollment(ViewMedicalEquipAppCompat.this);
                install_enroll.open();
                Cursor del_cursor_inst = install_enroll.update_flag_By_Eq_Enroll_Id(equipmentDetails.get(selectedPostionForEquip).geteq_enroll_id());


                viewEquipEnroll(selectedHospitalId);

                exportDatabseView(BusinessAccessLayer.DATABASE_NAME);


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();
            }
        });

        mContactUsDialog.show();

    }

    public void showValidationDialog(final Context ctx, String txt) {

        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(true);

        Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("Ok");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("Cancel");

        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
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
}
