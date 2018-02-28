package org.next.equmed.uil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dm.zbar.android.scanner.ZBarConstants;
import com.dm.zbar.android.scanner.ZBarScannerActivity;
import com.rey.material.widget.RelativeLayout;

import org.apache.commons.io.FileUtils;
import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.FileChooser;
import org.next.equmed.bal.Item;
import org.next.equmed.bal.MyLocation;
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
import org.next.equmed.dal.Trn_Voice_Of_Customer;
import org.next.equmed.dal.Trn_Warranty_Details;
import org.next.equmed.dal.Trn_User_Registration;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static org.next.equmed.bal.BusinessAccessLayer.mParetnRoleId;

/**
 * Created by next-03 on 2/12/15.
 */
public class MedicalEquipFragment extends UserInterfaceLayer implements View.OnClickListener, LocationListener {
    LinearLayout lLayout_attDocList, lLayout_me_createdBy, lLayout_me_createdOn;

    TextView txtVw_hosptName, txtVw_locationCode, txtVw_serialNo, txtVw_equip, txtVw_make, txtVw_model, txtVw_equipId, txtVw_img, txtVw_attDoc, txtVw_insDate, txtVw_serTagNo,
            txtVw_medEquipNotes, txtVw_itemEquip, txtVw_ups, txtVw_manual, txtVw_stabilizer, txtVw_accList, txtVw_insCond, txtVw_insNotes, txtVw_worCond, txtVw_worCondNotes, txtVw_me_createdBy, txtVw_me_createdOn;

    EditText eTxt_locationCode, eTxt_serialNo, eTxt_make, eTxt_model, eTxt_equipId, eTxt_attDoc, eTxt_insDate, eTxt_serTagNo,
            eTxt_medEquipNotes, eTxt_accList, eTxt_insNotes, eTxt_condNotes, eTxt_eq_createdBy, eTxt_eq_createdOn;

    //    Spinner  , spn_insCond, spn_worCond;

    Spinner spn_hosptList, spn_equipList, spn_insCond, spn_worCond;
    RadioButton rBtn_upsYes, rBtn_upsNo, rBtn_manualYes, rBtn_manualNo, rBtn_stabYes, rBtn_stabNo;
    int countVal;
//    ImageView imgVw_equipImage;

    //    Button btn_equipSave;
    FloatingActionButton btn_fab_equipSave, btn_fab_equipDelete, btn_fab_MedEqClear;

    ScrollView scr_medEquip;
    String upsStatus = "", manualStatus = "", stablizerStatus = "";


    private RadioGroup rBtn_upsGrp, rBtn_manualGrp, rBtn_stabGrp;


    ArrayList<Bean> hos_selected_array_list = new ArrayList<Bean>();

    String curFileName = "", getFilePath = "";
    private String uploadImageStr = "";
    static File writtenFile;
    static byte[] byteArray = null;
//    String[] hsptlStrArray = { "SELECT HOSPITAL", "KG", "CHILD JESUS", "GEM",
//            "GH" };
//
//    String[] eqpmntStrArray = { "X-RAY MACHINE", "BLOOD PRESSURE MONITORS", "ECG MACHINES",
//            "GH" };

    String[] instlCndtnStrArray = {"Select Installation Condition","Completed", "Open to Go", "Good to Go", "Still to Go",
            "Not to Go"};

    String[] wrkngCndtnStrArray = {"Select Working Condition", "Working", "Retired", "Repaired"};

    String str_spn_hosptList = " ", spn_equipListStr = "", spn_insCondStr = "", spn_worCondStr = "";

    private Calendar myCalendar = Calendar.getInstance();
    private int mYear;
    private int mMonth;
    private int mDay;
    int diffInDays;

    private String mFormattedDatefrom = "";

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    String[] hospitalArray = null, hosLocationName = null;
    ArrayList equipmentNameArray = new ArrayList();
    HashMap<String, String> mHospitalHashMap = new HashMap<String, String>();
    HashMap<String, String> mHospitalHashMapByID = new HashMap<String, String>();

    HashMap<String, String> mEquipmentHashMap = new HashMap<String, String>();
    HashMap<String, String> mEquipmentHashMapByID = new HashMap<String, String>();

    HashMap<String, String> mInstalltionConditionHashMap = new HashMap<String, String>();
    HashMap<String, String> mWorkingConditionHashMap = new HashMap<String, String>();


    String selectedMedicalEquipId = "", selectedHospitalId = "";
    String generatedMedicalEquipId = "";

    String medEquImageName = "", medEquImageEncodedStr = "", medEquDocumentName = "", medEquDocumentEncodedStr = "";
    String medOwnerImageName = "", medOwnerEncodedStr = "";

    ArrayList<Bean> viewEquipmentDetailsArray = new ArrayList<>();

    LinearLayout lLayout_eqpmnt_id;
    TextView txtVw_eqpmnt_id;
    String documentEncodedStr = "";

    String str_eTxt_serialNo, str_eTxt_locationCode, str_eTxt_make, str_eTxt_model, str_eTxt_attDoc, str_eTxt_insDate, str_eTxt_serTagNo, str_eTxt_medEquipNotes, str_upsStatus, str_manualStatus, str_stablizerStatus, str_eTxt_accList, str_spn_insCond, str_eTxt_insNotes, str_spn_worCond, str_eTxt_condNotes;
    String str_createdBy = "", str_createdOn = "";
    boolean isAddedNew = true;
//    boolean isPreview = true;


    // updated

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private static final int INTENT_REQUEST_GET_N_IMAGES = 14;
    private static final String TAG = "MedicalEquipFragment";
    HashSet<Uri> mMedia = new HashSet<Uri>();
    ViewGroup selected_photos_container_eqpmnt;

    HorizontalScrollView hori_scroll_view_eqpmnt;

    HashSet<Bitmap> imageRetriveHashSet = new HashSet<Bitmap>();
    Button btn_eqpmnt_uploadImage, btn_eqpmnt_attach_document;

    ArrayList<Bean> medicalDocumentArray = new ArrayList<Bean>();
    ArrayList<Bean> medicalImagesArray = new ArrayList<Bean>();

    ListView list_medicalDocument;


    String fetchSyncStatus = "";


    Typeface calibri_typeface, calibri_bold_typeface;

    ImageButton btn_scan_barcode, btn_getLocation;
    MyLocation myLocation;
    TextView txtVw_gpsCoordnts;
    EditText eTxt_gpsCoordnts;

    private Uri picUri;
    public File f;
    public File file, file_upload;

    public MedicalEquipFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.medical_equip_activity, container, false);

        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        BusinessAccessLayer.bug_class_name = "MedicalEquipmentFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        getViewCasting(rootView);
        getGPSLocation();
       // getActiveHospitalInformation();

        if (mParetnRoleId.equalsIgnoreCase("Y")) {
            // Created as admin
            getActiveHospitalInformation();
        } else {
            // Created as user
            getUserHospital();
        }

        try {
            if (BusinessAccessLayer.hospitalArray.length > 0) {


                spn_hosptList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, BusinessAccessLayer.hospitalArray));
                System.out.println(" BusinessAccessLayer.hospitalArray  in if :" + BusinessAccessLayer.hospitalArray.toString());

            } else {
                System.out.println(" BusinessAccessLayer.hospitalArray  in else :" + BusinessAccessLayer.hospitalArray);
            }

        } catch (Exception e) {
            System.out.println("Exception spn_hosptList in oncreate" + e);
        }

//        getHospitalInformation();
        getEquipmentInformation();
        getInstallationCondition();
        getWorkingCondition();
        medicalDocumentArray.clear();

        System.out.println("BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG ==> " + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);

        try {
            if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
//                isPreview = true;
                btn_fab_equipSave.setImageResource(R.drawable.save);
                btn_fab_equipDelete.setVisibility(View.GONE);
                lLayout_eqpmnt_id.setVisibility(View.GONE);
                lLayout_me_createdBy.setVisibility(View.GONE);
                lLayout_me_createdOn.setVisibility(View.GONE);
                System.out.println("me oncreate BusinessAccessLayer.mHospitalHashMapByID ::" + BusinessAccessLayer.mHospitalHashMapByID);
                System.out.println("me oncreate BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID ::" + BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);
                System.out.println("selection Id:" + BusinessAccessLayer.mHospitalHashMapByID.get(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID));

                spn_hosptList.setSelection(Integer.parseInt(BusinessAccessLayer.mHospitalHashMapByID.get(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID)));
                eTxt_locationCode.setText(BusinessAccessLayer.hospitalLocation[Integer.parseInt(BusinessAccessLayer.mHospitalHashMapByID.get(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID))]);
            } else {
//                isPreview = false;
                lLayout_me_createdBy.setVisibility(View.VISIBLE);
                lLayout_me_createdOn.setVisibility(View.VISIBLE);
                btn_fab_equipSave.setImageResource(R.drawable.edit);
                btn_fab_equipDelete.setVisibility(View.VISIBLE);
                lLayout_eqpmnt_id.setVisibility(View.GONE);


                viewEquipEnroll(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
                System.out.println("selection flag:" + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);


            }

        } catch (Exception e) {
            System.out.println("Exception in oncreate" + e);
        }


//        spn_worCondStr = wrkngCndtnStrArray[0];
//        spn_insCondStr = instlCndtnStrArray[0];


        return rootView;
    }


    void getActiveHospitalInformation() {

        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(getActivity());
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

    void getUserHospital() {

        // sample hospital string in database = hospitalID-hospitalName-hosptailassigned(true/false)-hospitalLocation , hospitalID2-hospitalName2-hosptailassigned2(true/false)-hospitalLocation2
        System.out.println("BusinessAccessLayer.mAssigendHospitalId ::" + BusinessAccessLayer.mAssigendHospitalId);
        String[] hospitalList = BusinessAccessLayer.mAssigendHospitalId.split("~");

        for (int i = 0; i < hospitalList.length; i++) {


            String[] splitterStr = hospitalList[i].split("-");

            if (splitterStr[2].equalsIgnoreCase("true")) {

                Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(getActivity());
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

    private void getWorkingCondition() {

        for (int i = 0; i < wrkngCndtnStrArray.length; i++) {

            if (i == 0) {
                mWorkingConditionHashMap.put("Select Working Condition", "" + i);

            } else {
                mWorkingConditionHashMap.put(wrkngCndtnStrArray[i], "" + i);

            }
        }
    }

    private void getInstallationCondition() {

        for (int i = 0; i < instlCndtnStrArray.length; i++) {
            if (i == 0) {
                mInstalltionConditionHashMap.put("Select Installation Condition", "" + i);

            } else {
                mInstalltionConditionHashMap.put(instlCndtnStrArray[i], "" + i);

            }

        }
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.medequipenroll_mainactivity);
//        getViewCasting();
//        getGPSLocation();
//
//
//        // ATTENTION: This was auto-generated to implement the App Indexing API.
//        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//    }


    private void viewEquipEnroll(String enrollId) {

        Trn_Equipment_Enrollment equip_enroll = new Trn_Equipment_Enrollment(getActivity());
        equip_enroll.open();
        Cursor mCr_trn_equipenroll = equip_enroll.fetchByEq_Enroll_Id(enrollId);
        viewEquipmentDetailsArray.clear();
        medicalDocumentArray.clear();
        medicalImagesArray.clear();


        if (mCr_trn_equipenroll.getCount() > 0) {
            for (int i = 0; i < mCr_trn_equipenroll.getCount(); i++) {
                mCr_trn_equipenroll.moveToPosition(i);

                Bean bean = new Bean();

                String ernroll_id = mCr_trn_equipenroll.getString(mCr_trn_equipenroll.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_ID + ""));
                generatedMedicalEquipId = ernroll_id;

                txtVw_eqpmnt_id.setText(ernroll_id);
                bean.seteq_enroll_id(ernroll_id);

                String ernroll_hosptl_id = mCr_trn_equipenroll.getString(mCr_trn_equipenroll.getColumnIndex("" + BusinessAccessLayer.EQ_HOSPITAL_ID + ""));
                selectedHospitalId = ernroll_hosptl_id;

                System.out.println("selectedHospitalId in viewEquipEnroll" + selectedHospitalId);

                try {
                    spn_hosptList.setSelection(Integer.parseInt(BusinessAccessLayer.mHospitalHashMapByID.get(selectedHospitalId)));

                } catch (Exception e) {
                    System.out.println("Exception spn_hosptList in viewEquipEnroll" + e);
                }
//                spn_hosptList.setSelection(Integer.parseInt(BusinessAccessLayer.mHospitalHashMapByID.get(selectedHospitalId)));

                String ernroll_hosptl_name = getHospitalInformationByHospitalId(ernroll_hosptl_id);
//                spn_hosptList.setSelection(Integer.parseInt(ernroll_hosptl_id));

                String eq_location_code = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_LOCATION_CODE + ""));

                bean.setEq_location_code(eq_location_code);
                eTxt_locationCode.setText(eq_location_code);

                String eq_gps_coordinates = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.GPS_COORDINATES + ""));

                bean.setEq_gps_coordinates(eq_gps_coordinates);
                eTxt_gpsCoordnts.setText(eq_gps_coordinates);

                String eq_serialno = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_SERIALNO + ""));
                bean.setEq_serialno(eq_serialno);
                eTxt_serialNo.setText(eq_serialno);

                String eq_make = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_MAKE + ""));
                bean.setEq_make(eq_make);
                eTxt_make.setText(eq_make);

                String eq_model = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_MODEL + ""));

                bean.setEq_model(eq_model);
                eTxt_model.setText(eq_model);

                String eq_id = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_ID + ""));

                selectedMedicalEquipId = eq_id;

                System.out.println("selectedMedicalEquipId  ::" + selectedMedicalEquipId);
                System.out.println("selectedMedicalEquipId  mEquipmentHashMapByID ::" + mEquipmentHashMapByID);

                System.out.println("selectedMedicalEquipId  mEquipmentHashMapByID hash mpa::" + mEquipmentHashMapByID.get(selectedMedicalEquipId));


                spn_equipList.setSelection(Integer.parseInt(mEquipmentHashMapByID.get(selectedMedicalEquipId)));

                String eq_installdate = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_DATE + ""));
                bean.setEq_install_date(eq_installdate);
                if (eq_installdate.length() > 0) {
                    String showInstalDate = getShowDate(eq_installdate);
                    eTxt_insDate.setText(showInstalDate);
                }


                String eq_servicetagno = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_SERVICE_TAGNO + ""));
                bean.setEq_serialno(eq_servicetagno);
                eTxt_serTagNo.setText(eq_servicetagno);

                String eq_notes = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_NOTES + ""));
                bean.setEq_notes(eq_notes);
                eTxt_medEquipNotes.setText(eq_notes);

                String eq_install_status = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_STATUS + ""));
                spn_insCondStr = eq_install_status;
                System.out.println("spn_insCondStr" + spn_insCondStr);
                if (eq_install_status.length() > 0) {
                    spn_insCond.setSelection(Integer.parseInt(mInstalltionConditionHashMap.get(spn_insCondStr)));

                } else {
                    spn_insCondStr = instlCndtnStrArray[0];
                    spn_insCond.setSelection(Integer.parseInt(mInstalltionConditionHashMap.get(spn_insCondStr)));

                }
                bean.setEq_install_status(eq_install_status);

                String eq_install_notes = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_INSTALL_NOTES + ""));

                bean.setEq_install_notes(eq_install_notes);
                eTxt_insNotes.setText(eq_install_notes);

                String eq_working_status = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_WORKING_STATUS + ""));

                bean.setEq_working_status(eq_working_status);

                spn_worCondStr = eq_working_status;
                System.out.println("spn_worCondStr" + spn_worCondStr);

                if (eq_working_status.length() > 0) {
                    spn_worCond.setSelection(Integer.parseInt(mWorkingConditionHashMap.get(spn_worCondStr)));


                } else {
                    spn_worCondStr = wrkngCndtnStrArray[0];
                    spn_worCond.setSelection(Integer.parseInt(mWorkingConditionHashMap.get(spn_worCondStr)));


                }

                String eq_working_notes = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.EQ_WORKING_NOTES + ""));

                bean.setEq_working_notes(eq_working_notes);

                eTxt_condNotes.setText(eq_install_notes);


                str_createdBy = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));


                System.out.println("str_createdBy in me edit :;" + str_createdBy);
                if (str_createdBy.equalsIgnoreCase("1")) {
                    eTxt_eq_createdBy.setText("Admin");
                } else {
                    eTxt_eq_createdBy.setText("" + getUserNameByUserId(str_createdBy));
                }


                str_createdOn = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));

                if (str_createdOn.length() > 0) {
                    String show_str_createdOn = getShowDateWithTime(str_createdOn);
                    eTxt_eq_createdOn.setText(show_str_createdOn);
                }


                fetchSyncStatus = mCr_trn_equipenroll.getString(mCr_trn_equipenroll
                        .getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                Mst_Equipment_Status mst_equipstatus = new Mst_Equipment_Status(getActivity());
                mst_equipstatus.open();
                Cursor mCr_mst_equip_status = mst_equipstatus.fetchByEq_Id(eq_id);

                if (mCr_mst_equip_status.getCount() > 0) {
                    mCr_mst_equip_status.moveToPosition(0);
                    String eq_name = mCr_mst_equip_status.getString(mCr_mst_equip_status.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
                    bean.setEq_name(eq_name);
                }


//                if ( mCr_mst_equip_status.getCount() > 0 ) {
//                    for ( int int_mst_equip_status = 0; i < mCr_mst_equip_status.getCount(); i++ ) {
//                        mCr_mst_equip_status.moveToPosition( int_mst_equip_status );
//
//                        equipmentNameArray = new String[ mCr_mst_equip_status.getCount() ];
//
//                        String eq_name = mCr_mst_equip_status.getString( mCr_mst_equip_status.getColumnIndex( "" + BusinessAccessLayer.EQ_NAME + "" ) );
//                        equipmentNameArray[ int_mst_equip_status ] = eq_name;
//                        // bean.setEq_name(eq_name);
//                    }
//
//
//                }
                Mst_Equipment_Status.close();


                Trn_Images trn_image = new Trn_Images(getActivity());
                trn_image.open();
                Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(ernroll_id, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE);

                if (mCr_trn_image.getCount() > 0) {
                    for (int int_trn_image = 0; int_trn_image < mCr_trn_image.getCount(); int_trn_image++) {

                        mCr_trn_image.moveToPosition(int_trn_image);
                        Bean imgBean = new Bean();

                        String str_trnImg_Id = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));
                        imgBean.setTrn_img_img_id(str_trnImg_Id);

                        String str_trn_image = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                        imgBean.setTrn_img_encrypted_data(str_trn_image);

                        String str_trn_image_name = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                        imgBean.setTrn_img_encrypted_name(str_trn_image_name);

                        String str_trn_created = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                        imgBean.setCreated_by(str_trn_created);
                        String str_trn_createdOn = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                        imgBean.setCreated_on(str_trn_createdOn);
                        imgBean.setNew_Img_upload("0");

                        medicalImagesArray.add(imgBean);
                        imgBean = null;

//                        Bitmap medicalEqpmntImage = decodeBase64(str_trn_image);
//
//                        if (medicalEqpmntImage != null) {
////                            for (Bitmap bitmapImage : medicalEqpmntImage) {
////                                Log.i(TAG, " uri: " + bitmapImage);
////                                System.out.println("mMedia  uri:::" + bitmapImage);
//
//                            imageRetriveHashSet.add(medicalEqpmntImage);
//                            System.out.println("mMedia  medicalEqpmntImage :::" + medicalEqpmntImage);
////                            }
//
////                            showRetriveImages(imageRetriveHashSet);
//                        }

//                        imgVw_equipImage.setImageBitmap(medicalEqpmntImage);
//                        bean.setImg_encrypted_data(str_trn_image);
                    }

                }
                Trn_Images.close();
                if (medicalImagesArray.size() > 0) {
                    hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
                    selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
                    loadImageArray();
                }
//                else{
//                    medicalImagesArray.clear();
//                }

                Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
                trn_docAdapt.open();
                Cursor mCr_trn_doc = trn_docAdapt.fetchByDoc_Id_Type(ernroll_id, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT);
                if (mCr_trn_doc.getCount() > 0) {
                    for (int int_trn_image = 0; int_trn_image < mCr_trn_doc.getCount(); int_trn_image++) {
                        mCr_trn_doc.moveToPosition(int_trn_image);
                        Bean medicalDocument = new Bean();
                        String str_trn_docId = mCr_trn_doc.getString(mCr_trn_doc.getColumnIndex("" + BusinessAccessLayer.DOC_DOC_ID + ""));
                        medicalDocument.setTrn_doc_doc_id(str_trn_docId);
                        String str_trn_docEncrypedData = mCr_trn_doc.getString(mCr_trn_doc.getColumnIndex("" + BusinessAccessLayer.DOC_ENCRYPTED_DATA + ""));
                        medicalDocument.setTrn_doc_encrypted_data(str_trn_docEncrypedData);

                        String str_trn_docType = mCr_trn_doc.getString(mCr_trn_doc.getColumnIndex("" + BusinessAccessLayer.DOC_TYPE + ""));
                        medicalDocument.setTrn_doc_type(str_trn_docType);

                        String str_trn_docCreatedBy = mCr_trn_doc.getString(mCr_trn_doc.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                        medicalDocument.setTrn_created_by(str_trn_docCreatedBy);

                        String str_trn_docCreatedOn = mCr_trn_doc.getString(mCr_trn_doc.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                        medicalDocument.setTrn_created_on(str_trn_docCreatedOn);

                        medicalDocumentArray.add(medicalDocument);

//                        documentEncodedStr = str_trn_docStr;


//                        Bitmap medicalEqpmntImage = decodeBase64(str_trn_docStr);
//                        eTxt_attDoc.setImageBitmap(medicalEqpmntImage);
//                        bean.setImg_encrypted_data(str_trn_image);
                    }

                }

                trn_docAdapt.close();
                if (medicalDocumentArray.size() > 0) {
                    loadDocumentFile();
                }

                Trn_Equipment_Enroll_Accessories trn_equip_enroll_access = new Trn_Equipment_Enroll_Accessories(getActivity());
                trn_equip_enroll_access.open();
                Cursor mCr_equip_enroll_access = trn_equip_enroll_access.fetchByEq_Enroll_Id(ernroll_id);
                if (mCr_equip_enroll_access.getCount() > 0) {
                    for (int int_enroll_access = 0; int_enroll_access < mCr_equip_enroll_access.getCount(); int_enroll_access++) {
                        mCr_equip_enroll_access.moveToPosition(int_enroll_access);

                        String eq_enroll_ups = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_UPS + ""));
                        upsStatus = eq_enroll_ups;
                        if (eq_enroll_ups.equalsIgnoreCase("yes")) {
                            rBtn_upsYes.setChecked(true);
                            rBtn_upsNo.setChecked(false);

                        } else {
                            rBtn_upsNo.setChecked(true);
                            rBtn_upsYes.setChecked(false);


                        }
                        bean.setEq_emroll_ups(eq_enroll_ups);

                        String eq_enroll_manual = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_MANUAL + ""));
                        manualStatus = eq_enroll_manual;
                        if (eq_enroll_manual.equalsIgnoreCase("yes")) {
                            rBtn_manualYes.setChecked(true);
                            rBtn_manualNo.setChecked(false);

                        } else {
                            rBtn_manualNo.setChecked(true);
                            rBtn_manualYes.setChecked(false);


                        }
                        bean.setEq_emroll_ups(eq_enroll_manual);


                        String eq_enroll_stabilizer = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_STABILIZER + ""));
                        str_stablizerStatus = eq_enroll_stabilizer;
                        if (eq_enroll_stabilizer.equalsIgnoreCase("yes")) {
                            rBtn_stabYes.setChecked(true);
                            rBtn_stabNo.setChecked(false);

                        } else {
                            rBtn_stabNo.setChecked(true);
                            rBtn_stabYes.setChecked(false);


                        }
                        bean.setEq_emroll_ups(eq_enroll_stabilizer);


                        String eq_enroll_other = mCr_equip_enroll_access.getString(mCr_equip_enroll_access.getColumnIndex("" + BusinessAccessLayer.EQ_ENROLL_OTHERS + ""));
                        eTxt_accList.setText(eq_enroll_other);
                        bean.setEq_emroll_ups(eq_enroll_other);


                    }
                }

                viewEquipmentDetailsArray.add(bean);

            }


        }


//        if (equipmentDetails.size() > 0) {
//            rL_ListView.setVisibility(View.VISIBLE);
//
//            HospitalListAdapter adapter = new HospitalListAdapter(ViewMedicalEquipAppCompat.this, equipmentDetails);
//            listView_Details.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//        } else {
//            rL_ListView.setVisibility(View.GONE);
//        }


    }

    private void loadImageArray() {

        selected_photos_container_eqpmnt.removeAllViews();

        for (countVal = 0; countVal < medicalImagesArray.size(); countVal++) {

            Bean beanImgObj = medicalImagesArray.get(countVal);

            String img_path;
            File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + beanImgObj.getTrn_img_encrypted_name());
            File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + beanImgObj.getTrn_img_encrypted_name());

            if (tempImg.exists() == true) {
                img_path = tempImg.getAbsolutePath();
            } else {
                img_path = tempImg1.getAbsolutePath();
            }

//            System.out.println("Img path : " + img_path);
//
//            System.out.println("Img ID val:" + beanImgObj.getTrn_img_img_id());
            RelativeLayout.LayoutParams rLayoutImage = new RelativeLayout.LayoutParams(140,
                    140);
            rLayoutImage.setMargins(5, 5, 5, 5);
            RelativeLayout backgroundLayout = new RelativeLayout(getActivity());
            backgroundLayout.setLayoutParams(rLayoutImage);
            backgroundLayout.setBackgroundResource(R.drawable.rect_edittext);

            //  Bitmap medicalEqpmntImage = decodeBase64(encoded_string);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(130,
                    130);
            ImageView iv = new ImageView(getActivity());
            iv.setId(countVal);
            iv.setImageURI(Uri.parse(img_path));
            // iv.setImageBitmap(medicalEqpmntImage);
            iv.setPadding(5, 5, 5, 5);
            iv.setLayoutParams(lp);
            backgroundLayout.addView(iv);

            selected_photos_container_eqpmnt.addView(backgroundLayout);


            RelativeLayout.LayoutParams btnParam = new RelativeLayout.LayoutParams(40,
                    40);
            btnParam.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            btnParam.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            btnParam.setMargins(0, -10, -10, 0);


            ImageView btn_update = new ImageView(getActivity());
            btn_update.setImageResource(R.drawable.close);
            btn_update.setLayoutParams(btnParam);
            btn_update.setId(countVal);
            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Trn_Images trn_document = new Trn_Images(getActivity());
                    trn_document.open();
                    Bean docBean = medicalImagesArray.get(v.getId());

                    Cursor cursorDoc = trn_document.fetchByImgImg_ID(docBean.getTrn_img_img_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE);
                    System.out.println("dfds: " + cursorDoc.getCount());

                    if (cursorDoc.getCount() > 0) {


                        String img_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                        if (img_sync_status.equals("0")) {
                            //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                            File tempDir = new File(getActivity().getFilesDir(), "/temp_images/" + docBean.getImg_encrypted_data());
                            tempDir.delete();

                            System.out.println("docBean.getTrn_img_img_id()" + docBean.getTrn_img_img_id());
                            System.out.println("generatedMedicalEquipId" + generatedMedicalEquipId);

                            Cursor delete_trn_document = trn_document.deleteByImg_Id_Type(docBean.getTrn_img_img_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE);
                        } else {
                            File dir = new File(getActivity().getFilesDir(), "/images/" + docBean.getImg_encrypted_data());
                            dir.delete();

                            boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
                                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                                    docBean.getCreated_by(), docBean.getCreated_on(),
                                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                        }

                    }


                    medicalImagesArray.remove(v.getId());
                    System.out.println("medicalImagesArray update:" + medicalImagesArray.size());
                    if (medicalImagesArray.size() > 0) {
                        loadImageArray();
                    } else {
                        hori_scroll_view_eqpmnt.setVisibility(View.GONE);
                        selected_photos_container_eqpmnt.setVisibility(View.GONE);
                        medicalImagesArray.clear();
                    }
                }
            });


            backgroundLayout.addView(btn_update);


            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Trn_Images trn_document = new Trn_Images(getActivity());
                    trn_document.open();
                    Bean docBean = medicalImagesArray.get(v.getId());

                    Cursor cursorDoc = trn_document.fetchByImgImg_ID(docBean.getTrn_img_img_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE);
                    System.out.println("cursorDoc.getCount() " + cursorDoc.getCount());
                    if (cursorDoc.getCount() > 0) {


                        String img_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                        System.out.println("img_sync_status : " + img_sync_status);

                        if (img_sync_status.equals("0")) {
                            //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                            File tempDir = new File(getActivity().getFilesDir(), "/temp_images/" + docBean.getImg_encrypted_data());
                            tempDir.delete();

                            System.out.println("docBean.getTrn_img_img_id()" + docBean.getTrn_img_img_id());
                            System.out.println("generatedMedicalEquipId" + generatedMedicalEquipId);

                            Cursor delete_trn_document = trn_document.deleteByImg_Id_Type(docBean.getTrn_img_img_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE);
                        } else {
                            System.out.println("asdf : " + getActivity().getFilesDir() + "/images/" + docBean.getTrn_img_encrypted_data());
                            File dir = new File(getActivity().getFilesDir(), "/images/" + docBean.getTrn_img_encrypted_data());
                            dir.delete();

                            boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
                                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                                    docBean.getCreated_by(), docBean.getCreated_on(),
                                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                        }

                    }

                    /*if (cursorDoc.getCount() > 0) {
                        boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
                                BusinessAccessLayer.SYNC_STATUS_VALUE,
                                docBean.getCreated_by(), docBean.getCreated_on(),
                                BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                    }*/

                    medicalImagesArray.remove(v.getId());

                    if (medicalImagesArray.size() > 0) {
                        loadImageArray();
                    } else {
                        medicalImagesArray.clear();

                        hori_scroll_view_eqpmnt.setVisibility(View.GONE);
                        selected_photos_container_eqpmnt.setVisibility(View.GONE);
                    }
                }
            });
        }
    }


    public String getHospitalInformationByHospitalId(String hsptlId) {

        String hospital_name = "";
        Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(getActivity());
        mst_hospital_enroll.open();
        Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByHospital_Id(hsptlId);

        if (mCr_mst_hospital_enroll.getCount() > 0) {
            for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                mCr_mst_hospital_enroll.moveToPosition(i);


                String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));

//
//                    hospitalArray[i] = hospital_name;
//
//                    mHospitalHashMap.put(mCr_mst_hospital_enroll
//                                    .getString(mCr_mst_hospital_enroll
//                                            .getColumnIndex(BusinessAccessLayer.HOSPITAL_NAME)),
//                            mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll
//                                    .getColumnIndex(BusinessAccessLayer.HOSPITAL_ID)));

            }
            mCr_mst_hospital_enroll.close();

        }

        Mst_Hospital_Enrollment.close();

        return hospital_name;


    }

    private void getGPSLocation() {

    }


    private void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void getViewCasting(View rootView) {

        lLayout_attDocList = (LinearLayout) rootView.findViewById(R.id.lLayout_attDocList);
        lLayout_me_createdBy = (LinearLayout) rootView.findViewById(R.id.lLayout_me_createdBy);
        lLayout_me_createdOn = (LinearLayout) rootView.findViewById(R.id.lLayout_me_createdOn);

        TextView txtVw_hosptName = (TextView) rootView.findViewById(R.id.txtVw_hosptName);
        TextView txtVw_serialNo = (TextView) rootView.findViewById(R.id.txtVw_serialNo);
        TextView txtVw_equip = (TextView) rootView.findViewById(R.id.txtVw_equip);
        txtVw_me_createdBy = (TextView) rootView.findViewById(R.id.txtVw_me_createdBy);
        txtVw_me_createdOn = (TextView) rootView.findViewById(R.id.txtVw_me_createdOn);
        selected_photos_container_eqpmnt = (ViewGroup) rootView.findViewById(R.id.selected_photos_container_eqpmnt);
        hori_scroll_view_eqpmnt = (HorizontalScrollView) rootView.findViewById(R.id.hori_scroll_view_eqpmnt);

        btn_scan_barcode = (ImageButton) rootView.findViewById(R.id.btn_scan_barcode);
        btn_getLocation = (ImageButton) rootView.findViewById(R.id.btn_getLocation);

        String hospitalname = "Hospital Name";
        String serialno = "Serial No";
        String equipment = "Equipment";

        String asterisk = "<font color='#EE0000'> *</font>";

        txtVw_hosptName.setText(Html.fromHtml(hospitalname + asterisk));
        txtVw_serialNo.setText(Html.fromHtml(serialno + asterisk));
        txtVw_equip.setText(Html.fromHtml(equipment + asterisk));


        txtVw_locationCode = (TextView) rootView.findViewById(R.id.txtVw_locationCode);

        txtVw_make = (TextView) rootView.findViewById(R.id.txtVw_make);
        txtVw_model = (TextView) rootView.findViewById(R.id.txtVw_model);
//        txtVw_equipId = (TextView) findViewById(R.id.txtVw_equipId);
        txtVw_img = (TextView) rootView.findViewById(R.id.txtVw_img);
        txtVw_attDoc = (TextView) rootView.findViewById(R.id.txtVw_attDoc);
        txtVw_insDate = (TextView) rootView.findViewById(R.id.txtVw_insDate);
        txtVw_serTagNo = (TextView) rootView.findViewById(R.id.txtVw_serTagNo);
        txtVw_medEquipNotes = (TextView) rootView.findViewById(R.id.txtVw_medEquipNotes);
        txtVw_itemEquip = (TextView) rootView.findViewById(R.id.txtVw_itemEquip);
        txtVw_ups = (TextView) rootView.findViewById(R.id.txtVw_ups);
        txtVw_manual = (TextView) rootView.findViewById(R.id.txtVw_manual);
        txtVw_stabilizer = (TextView) rootView.findViewById(R.id.txtVw_stabilizer);
        txtVw_accList = (TextView) rootView.findViewById(R.id.txtVw_accList);
        txtVw_insCond = (TextView) rootView.findViewById(R.id.txtVw_insCond);
        txtVw_insNotes = (TextView) rootView.findViewById(R.id.txtVw_insNotes);
        txtVw_worCond = (TextView) rootView.findViewById(R.id.txtVw_worCond);
        txtVw_worCondNotes = (TextView) rootView.findViewById(R.id.txtVw_worCondNotes);

        txtVw_worCondNotes.setTypeface(calibri_typeface);
        txtVw_worCond.setTypeface(calibri_typeface);
        txtVw_insNotes.setTypeface(calibri_typeface);
        txtVw_insCond.setTypeface(calibri_typeface);
        txtVw_accList.setTypeface(calibri_typeface);
        txtVw_stabilizer.setTypeface(calibri_typeface);
        txtVw_manual.setTypeface(calibri_typeface);
        txtVw_ups.setTypeface(calibri_typeface);
        txtVw_itemEquip.setTypeface(calibri_typeface);
        txtVw_medEquipNotes.setTypeface(calibri_typeface);
        txtVw_serTagNo.setTypeface(calibri_typeface);
        txtVw_insDate.setTypeface(calibri_typeface);
        txtVw_attDoc.setTypeface(calibri_typeface);
        txtVw_img.setTypeface(calibri_typeface);
        txtVw_hosptName.setTypeface(calibri_typeface);
        txtVw_serialNo.setTypeface(calibri_typeface);
        txtVw_equip.setTypeface(calibri_typeface);
        txtVw_locationCode.setTypeface(calibri_typeface);
        txtVw_make.setTypeface(calibri_typeface);
        txtVw_model.setTypeface(calibri_typeface);
        txtVw_me_createdBy.setTypeface(calibri_typeface);
        txtVw_me_createdOn.setTypeface(calibri_typeface);


        eTxt_locationCode = (EditText) rootView.findViewById(R.id.eTxt_locationCode);
        eTxt_serialNo = (EditText) rootView.findViewById(R.id.eTxt_serialNo);
        eTxt_make = (EditText) rootView.findViewById(R.id.eTxt_make);
        eTxt_model = (EditText) rootView.findViewById(R.id.eTxt_model);
        //eTxt_equipId = (EditText) findViewById(R.id.eTxt_equipId);
        eTxt_attDoc = (EditText) rootView.findViewById(R.id.eTxt_attDoc);
        eTxt_insDate = (EditText) rootView.findViewById(R.id.eTxt_insDate);
        eTxt_serTagNo = (EditText) rootView.findViewById(R.id.eTxt_serTagNo);
        eTxt_medEquipNotes = (EditText) rootView.findViewById(R.id.eTxt_medEquipNotes);
        eTxt_accList = (EditText) rootView.findViewById(R.id.eTxt_accList);
        eTxt_insNotes = (EditText) rootView.findViewById(R.id.eTxt_insNotes);
        eTxt_condNotes = (EditText) rootView.findViewById(R.id.eTxt_condNotes);
        eTxt_eq_createdBy = (EditText) rootView.findViewById(R.id.eTxt_eq_createdBy);
        eTxt_eq_createdOn = (EditText) rootView.findViewById(R.id.eTxt_eq_createdOn);

        eTxt_serialNo.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_make.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_model.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_serTagNo.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_medEquipNotes.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_accList.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_insNotes.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_condNotes.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


        txtVw_gpsCoordnts = (TextView) rootView.findViewById(R.id.txtVw_gpsCoordnts);
        eTxt_gpsCoordnts = (EditText) rootView.findViewById(R.id.eTxt_gpsCoordnts);
        txtVw_gpsCoordnts.setTypeface(calibri_typeface);
        eTxt_gpsCoordnts.setTypeface(calibri_typeface);


        eTxt_serialNo.setTypeface(calibri_typeface);
        eTxt_locationCode.setTypeface(calibri_typeface);
        eTxt_make.setTypeface(calibri_typeface);
        eTxt_model.setTypeface(calibri_typeface);
        eTxt_attDoc.setTypeface(calibri_typeface);
        eTxt_insDate.setTypeface(calibri_typeface);
        eTxt_serTagNo.setTypeface(calibri_typeface);
        eTxt_medEquipNotes.setTypeface(calibri_typeface);
        eTxt_accList.setTypeface(calibri_typeface);
        eTxt_insNotes.setTypeface(calibri_typeface);
        eTxt_condNotes.setTypeface(calibri_typeface);
        eTxt_eq_createdBy.setTypeface(calibri_typeface);
        eTxt_eq_createdOn.setTypeface(calibri_typeface);
        eTxt_eq_createdOn.setClickable(false);
        eTxt_eq_createdOn.setFocusableInTouchMode(false);


        spn_hosptList = (Spinner) rootView.findViewById(R.id.spn_hosptList);
        spn_equipList = (Spinner) rootView.findViewById(R.id.spn_equipList);
        spn_insCond = (Spinner) rootView.findViewById(R.id.spn_insCond);
        spn_worCond = (Spinner) rootView.findViewById(R.id.spn_worCond);

        lLayout_eqpmnt_id = (LinearLayout) rootView.findViewById(R.id.lLayout_eqpmnt_id);
        txtVw_eqpmnt_id = (TextView) rootView.findViewById(R.id.txtVw_eqpmnt_id);

        rBtn_upsGrp = (RadioGroup) rootView.findViewById(R.id.rBtn_upsGrp);
        rBtn_manualGrp = (RadioGroup) rootView.findViewById(R.id.rBtn_manualGrp);
        rBtn_stabGrp = (RadioGroup) rootView.findViewById(R.id.rBtn_stabGrp);

        rBtn_upsYes = (RadioButton) rootView.findViewById(R.id.rBtn_upsYes);
        rBtn_upsNo = (RadioButton) rootView.findViewById(R.id.rBtn_upsNo);
        rBtn_manualYes = (RadioButton) rootView.findViewById(R.id.rBtn_manualYes);
        rBtn_manualNo = (RadioButton) rootView.findViewById(R.id.rBtn_manualNo);
        rBtn_stabYes = (RadioButton) rootView.findViewById(R.id.rBtn_stabYes);
        rBtn_stabNo = (RadioButton) rootView.findViewById(R.id.rBtn_stabNo);

        txtVw_eqpmnt_id.setTypeface(calibri_typeface);
        rBtn_upsYes.setTypeface(calibri_typeface);
        rBtn_upsNo.setTypeface(calibri_typeface);
        rBtn_manualYes.setTypeface(calibri_typeface);
        rBtn_manualNo.setTypeface(calibri_typeface);
        rBtn_stabYes.setTypeface(calibri_typeface);
        rBtn_stabNo.setTypeface(calibri_typeface);

//        btn_equipCancel = (Button) rootView.findViewById(R.id.btn_equipCancel);
//        btn_equipSave = ( Button ) rootView.findViewById( R.id.btn_equipSave );
        btn_fab_equipSave = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_equipSave);
        btn_fab_equipDelete = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_equipDelete);
        btn_fab_MedEqClear = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_MedEqClear);
//        imgVw_equipImage = (ImageView) rootView.findViewById(R.id.imgVw_equipImage);

        // updated
        btn_eqpmnt_uploadImage = (Button) rootView.findViewById(R.id.btn_eqpmnt_uploadImage);
        btn_eqpmnt_attach_document = (Button) rootView.findViewById(R.id.btn_eqpmnt_attach_document);

        btn_eqpmnt_uploadImage.setTypeface(calibri_bold_typeface);
        btn_eqpmnt_attach_document.setTypeface(calibri_bold_typeface);


        list_medicalDocument = (ListView) rootView.findViewById(R.id.list_medicalDocument);
        getViewClickEvents();

    }

    void getHospitalInformation() {

        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(getActivity());
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByActive();

            hospitalArray = new String[mCr_mst_hospital_enroll.getCount()];
            hosLocationName = new String[mCr_mst_hospital_enroll.getCount()];
            mHospitalHashMap.clear();
            mHospitalHashMapByID.clear();
            if (mCr_mst_hospital_enroll.getCount() > 0) {
                for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                    mCr_mst_hospital_enroll.moveToPosition(i);


                    String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                    String hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));

                    String hospital_location = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));

                    hospitalArray[i] = hospital_name;
                    hosLocationName[i] = hospital_location;

                    mHospitalHashMap.put(hospital_name,
                            mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll
                                    .getColumnIndex(BusinessAccessLayer.HOSPITAL_ID)));

                    mHospitalHashMapByID.put(hospital_id, "" + i);

                }
                mCr_mst_hospital_enroll.close();

            }

            Mst_Hospital_Enrollment.close();

        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }

        if (hospitalArray.length > 0) {


            spn_hosptList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, hospitalArray));

            eTxt_locationCode.setText(hosLocationName[0]);


        } else {
            System.out.println("No Hospital Values");
        }
    }

    void getEquipmentInformation() {


        Mst_Equipment_Status mst_equipstatus = new Mst_Equipment_Status(getActivity());
        mst_equipstatus.open();
        Cursor mCr_mst_equip_status = mst_equipstatus.fetchByActive();
        // equipmentNameArray = new String[mCr_mst_equip_status.getCount()+1];
        mEquipmentHashMap.clear();
        equipmentNameArray.clear();
        equipmentNameArray.add("Select Equipment");
        mEquipmentHashMap.put("Select Equipment", "-1");
        mEquipmentHashMapByID.put("-1", "" + "-1");
        if (mCr_mst_equip_status.getCount() > 0) {
            for (int eq = 0; eq < mCr_mst_equip_status.getCount(); eq++) {
                mCr_mst_equip_status.moveToPosition(eq);
                String eqName = mCr_mst_equip_status.getString(mCr_mst_equip_status.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
                String eqId = mCr_mst_equip_status.getString(mCr_mst_equip_status.getColumnIndex("" + BusinessAccessLayer.EQ_ID + ""));

//                if (eq == 0) {
//                    eqName = "Select";
//                    equipmentNameArray[eq] = eqName;
//
//                } else {
//                    equipmentNameArray[eq] = eqName;
//                }

                equipmentNameArray.add(eqName);
                mEquipmentHashMap.put(eqName, eqId);
                mEquipmentHashMapByID.put(eqId, "" + (eq + 1));
            }
        }
        Mst_Equipment_Status.close();


        if (equipmentNameArray.size() > 0) {

            spn_equipList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, equipmentNameArray));


        } else {
            System.out.println("No Hospital Values");
        }
    }

    private void getViewClickEvents() {

        eTxt_locationCode.setOnClickListener(this);
        eTxt_serialNo.setOnClickListener(this);
        eTxt_make.setOnClickListener(this);
        eTxt_model.setOnClickListener(this);
        // eTxt_equipId.setOnClickListener(this);
        eTxt_attDoc.setOnClickListener(this);
        eTxt_insDate.setOnClickListener(this);
        eTxt_serTagNo.setOnClickListener(this);
        eTxt_medEquipNotes.setOnClickListener(this);
        eTxt_accList.setOnClickListener(this);
        eTxt_insNotes.setOnClickListener(this);
        eTxt_condNotes.setOnClickListener(this);

        rBtn_upsYes.setOnClickListener(this);
        rBtn_upsNo.setOnClickListener(this);
        rBtn_manualYes.setOnClickListener(this);
        rBtn_manualNo.setOnClickListener(this);
        rBtn_stabYes.setOnClickListener(this);
        rBtn_stabNo.setOnClickListener(this);

        btn_fab_MedEqClear.setOnClickListener(this);

        /*spn_hosptList.setOnClickListener(this);
        spn_equipList.setOnClickListener(this);
        spn_insCond.setOnClickListener(this);
        spn_worCond.setOnClickListener(this);*/

//        btn_equipCancel.setOnClickListener(this);
        btn_fab_equipSave.setOnClickListener(this);
        btn_fab_equipDelete.setOnClickListener(this);

//        imgVw_equipImage.setOnClickListener(this);
//update by aravinth
        btn_eqpmnt_uploadImage.setOnClickListener(this);
        btn_eqpmnt_attach_document.setOnClickListener(this);
        btn_scan_barcode.setOnClickListener(this);
        btn_getLocation.setOnClickListener(this);

        rBtn_upsGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected


                if (checkedId == R.id.rBtn_upsYes) {
                    upsStatus = "yes";

                } else if (checkedId == R.id.rBtn_upsNo) {
                    upsStatus = "no";
                }
            }

        });

        rBtn_manualGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected


                if (checkedId == R.id.rBtn_manualYes) {
                    manualStatus = "yes";

                } else if (checkedId == R.id.rBtn_manualNo) {
                    manualStatus = "no";
                }
            }

        });

        rBtn_stabGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected


                if (checkedId == R.id.rBtn_stabYes) {
                    stablizerStatus = "yes";

                } else if (checkedId == R.id.rBtn_stabNo) {
                    stablizerStatus = "no";
                }
            }

        });

        if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
            spn_hosptList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedHospitalId = BusinessAccessLayer.mHospitalHashMap.get(BusinessAccessLayer.hospitalArray[position]);

                    eTxt_locationCode.setText(BusinessAccessLayer.hospitalLocation[position]);
                    System.out.println("selectedHospitalId:" + selectedHospitalId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }

            });
        } else {
          //  spn_hosptList.setEnabled(false);
            spn_hosptList.setEnabled(false);
        }


        if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
            spn_equipList
                    .setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            if (position == 0) {
                                selectedMedicalEquipId = "";
                            } else {
                                selectedMedicalEquipId = mEquipmentHashMap.get(equipmentNameArray.get(position));

                                System.out.println("selectedMedicalEquipId:" + selectedMedicalEquipId);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
        } else {
            spn_equipList.setEnabled(false);
        }


        FilterAdapter adapterInstlCndtn = new FilterAdapter(getActivity(), instlCndtnStrArray);
        spn_insCond.setAdapter(adapterInstlCndtn);
        spn_insCond.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spn_insCondStr = "";
                } else {
//                    spn_insCondStr = ""+position;
                    spn_insCondStr = instlCndtnStrArray[position];

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        FilterAdapter adapterWrkngCndtn = new FilterAdapter(getActivity(), wrkngCndtnStrArray);
        spn_worCond.setAdapter(adapterWrkngCndtn);
        spn_worCond.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spn_worCondStr = "";
                } else {
//                    spn_worCondStr = ""+position;
                    spn_worCondStr = wrkngCndtnStrArray[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private class FilterAdapter extends ArrayAdapter<String> {

        public FilterAdapter(Context context, String[] CateogoryDetails) {
            super(context, android.R.layout.simple_spinner_item, CateogoryDetails);

//            categoryList=CateogoryDetails;
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.btn_eqpmnt_uploadImage:
//                getImages();
                uploadImage();
//                captureCameraImages();
                break;

//            case R.id.btn_equipCancel:
//                Intent back = new Intent(getActivity(), DashBoardActivity.class);
//                startActivity(back);
////                finish();
//                break;

            case R.id.btn_fab_equipSave:

                System.out.println("flag_equipment == > " + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);
                if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                    if (selectedHospitalId.length() == 0) {
                        showValidationDialog(getActivity(), "Select hospital");
                    } else if (eTxt_serialNo.getText().toString().trim().length() == 0) {
                        showValidationDialog(getActivity(), "Enter serial number");

                    } else if (selectedMedicalEquipId.length() == 0) {
                        showValidationDialog(getActivity(), "Select equipment");

                    } else {

                        /*** Please verify all data before you submit 29-March-2016 ***/

                        /*if (isPreview == true) {
                            showValidationDialog(getActivity(), "Please verify all data before you submit");
                            btn_fab_MedEqClear.setVisibility(View.VISIBLE);
                            isPreview = false;
                        } else {*/
                            insert_Medical_Equipment_Details();
                           // btn_fab_MedEqClear.setVisibility(View.GONE);
//                            isPreview = true;
                        //}
                    }
                } else {
                    if (eTxt_serialNo.getText().toString().trim().length() == 0) {
                        showValidationDialog(getActivity(), "Enter serial number");

                    } else {
                        update_Medical_Equipment_Details();

                    }
                }
                break;

            case R.id.btn_fab_equipDelete:
                isAddedNew = false;
                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                break;

            case R.id.eTxt_locationCode:

//                hideSoftKeyboard(getActivity());
                break;

            case R.id.eTxt_serialNo:

                break;
            case R.id.eTxt_make:

                break;

            case R.id.eTxt_model:

                break;
//            case R.id.eTxt_equipId:
//
//                break;
            case R.id.btn_eqpmnt_attach_document:
                Intent intentAttach = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intentAttach, BusinessAccessLayer.FILE_ATTACHMENT);
                break;

            case R.id.eTxt_attDoc:
                hideSoftKeyboard(getActivity());


                Intent intent1 = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent1, BusinessAccessLayer.FILE_ATTACHMENT);

                break;
            case R.id.btn_scan_barcode:
                Intent intent = new Intent(getActivity(), ZBarScannerActivity.class);
                //  intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE, Symbol.ISBN10, Symbol.ISBN13});
                startActivityForResult(intent, BusinessAccessLayer.BAR_CODE_SCAN);
                break;
            case R.id.btn_getLocation:
                hideSoftKeyboard(getActivity());
                try {
                    location();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            case R.id.eTxt_insDate:
                hideSoftKeyboard(getActivity());
                datepicker();
                break;

            case R.id.eTxt_serTagNo:

                break;
            case R.id.eTxt_medEquipNotes:

                break;
            case R.id.eTxt_accList:

                break;
            case R.id.eTxt_insNotes:

                break;

            case R.id.eTxt_condNotes:

                break;
            case R.id.btn_fab_MedEqClear:
                clearAllFields();
                break;
            default:
                break;
        }


    }

    private void getCurrentLatLong() {

        myLocation = new MyLocation(getActivity());

        // check if GPS enabled
        if (myLocation.isLocationAvailable()) {

            String latValue = String.valueOf(myLocation.getLatitude());
            String longValue = String.valueOf(myLocation.getLongitude());
            String conCatLatLong = "Lat - " + latValue + ", " + "Long - " + longValue;
            eTxt_gpsCoordnts.setText(conCatLatLong);
        } else {
            myLocation.showSettingsAlert();
//            showValidationDialog(getActivity(),"No location");
        }

    }

    private void location() {

        myLocation = new MyLocation(getActivity());

        // check if GPS enabled
        if (myLocation.isLocationAvailable()) {
            String latValue = String.valueOf(myLocation.getLatitude());
            String longValue = String.valueOf(myLocation.getLongitude());
            String conCatLatLong = "";
            if (latValue.equalsIgnoreCase("0.0") || longValue.equalsIgnoreCase("0.0")
                    || latValue == null) {
                conCatLatLong="No gps fix";
                eTxt_gpsCoordnts.setText(conCatLatLong);
            } else {
                conCatLatLong = "Lat - " + latValue + ", " + "Long - " + longValue;
                eTxt_gpsCoordnts.setText(conCatLatLong);
            }
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            myLocation.showSettingsAlert();
        }
    }


    private void delete_medical_ewuipment_installation() {

        Trn_Equipment_Enrollment equip_enroll = new Trn_Equipment_Enrollment(getActivity());
        equip_enroll.open();
        Cursor del_cursor = equip_enroll.updateBy_flag_Eq_Enroll_Id(generatedMedicalEquipId);
        equip_enroll.close();

        Trn_Images trn_image = new Trn_Images(getActivity());
        trn_image.open();
        Cursor img_cursor = trn_image.updateBy_Img_Id(generatedMedicalEquipId);
        trn_image.close();

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();
        Cursor doc_cursor = trn_docAdapt.updateBy_Doc_Id(generatedMedicalEquipId);
        trn_docAdapt.close();

        Trn_Equipment_Enroll_Accessories trn_equip_enroll_access = new Trn_Equipment_Enroll_Accessories(getActivity());
        trn_equip_enroll_access.open();
        Cursor acc_cursor = trn_equip_enroll_access.update_flag_By_Eq_Enroll_Id(generatedMedicalEquipId);
        trn_equip_enroll_access.close();

        Trn_Installation_Enrollment install_enroll = new Trn_Installation_Enrollment(getActivity());
        install_enroll.open();
        Cursor del_cursor_inst = install_enroll.update_flag_By_Eq_Enroll_Id(generatedMedicalEquipId);
        install_enroll.close();

        Trn_Service_Details service_enroll = new Trn_Service_Details(getActivity());
        service_enroll.open();

        Trn_Documents trn_ser_doc = new Trn_Documents(getActivity());
        trn_ser_doc.open();

        Trn_Images trn_ser_image = new Trn_Images(getActivity());
        trn_ser_image.open();


        Cursor service_list_cursor = service_enroll.fetchBySerEq_Enroll_Id(generatedMedicalEquipId);
        if (service_list_cursor.getCount() > 0) {
            for (int i = 0; i < service_list_cursor.getCount(); i++) {
                service_list_cursor.moveToPosition(i);
                String service_id = service_list_cursor.getString(service_list_cursor.getColumnIndex("" + BusinessAccessLayer.SERVICE_ID + ""));
                Cursor del_cursor_service = service_enroll.update_flag_By_service_Id(service_id);
                Cursor trn_ser_image_cursor = trn_ser_image.updateBy_Img_Id(service_id);
                Cursor trn_ser_doc_cursor = trn_ser_doc.updateBy_Doc_Id(service_id);
            }
        }

        trn_ser_doc.close();
        trn_image.close();
        service_enroll.close();

        Trn_Warranty_Details warranty_enroll = new Trn_Warranty_Details(getActivity());
        warranty_enroll.open();

        Trn_Documents trn_war_doc = new Trn_Documents(getActivity());
        trn_war_doc.open();

        Cursor warranty_list_cursor = warranty_enroll.fetchBy_War_Eq_Enroll_Id(generatedMedicalEquipId);
        if (warranty_list_cursor.getCount() > 0) {
            for (int i = 0; i < warranty_list_cursor.getCount(); i++) {
                warranty_list_cursor.moveToPosition(i);
                String warranty_id = warranty_list_cursor.getString(warranty_list_cursor.getColumnIndex("" + BusinessAccessLayer.WARRANTY_ID + ""));
                Cursor del_cursor_warranty = warranty_enroll.update_flag_By_warranty_Id(warranty_id);
                Cursor trn_war_doc_cursor = trn_war_doc.updateBy_Doc_Id(warranty_id);
            }
        }

        trn_war_doc.close();
        warranty_enroll.close();

        Trn_Training_Details training_enroll = new Trn_Training_Details(getActivity());
        training_enroll.open();

        Trn_Images trn_tra_image = new Trn_Images(getActivity());
        trn_tra_image.open();

        Trn_Documents trn_tra_doc = new Trn_Documents(getActivity());
        trn_tra_doc.open();

        Cursor training_list_cursor = training_enroll.fetchBy_Tra_Eq_Enroll_Id(generatedMedicalEquipId);
        if (training_list_cursor.getCount() > 0) {
            for (int i = 0; i < training_list_cursor.getCount(); i++) {
                training_list_cursor.moveToPosition(i);
                String training_id = training_list_cursor.getString(training_list_cursor.getColumnIndex("" + BusinessAccessLayer.TRAINING_ID + ""));
                Cursor del_cursor_training = training_enroll.update_flag_By_training_Id(training_id);
                Cursor trn_tra_image_cursor = trn_tra_image.updateBy_Img_Id(training_id);
                Cursor trn_ser_doc_cursor = trn_tra_doc.updateBy_Doc_Id(training_id);
            }
        }
        trn_tra_doc.close();
        trn_image.close();
        training_enroll.close();

        Trn_Consumables consumables_enroll = new Trn_Consumables(getActivity());
        consumables_enroll.open();
        Cursor consumables_list_cursor = consumables_enroll.fetchByConsEq_Enroll_Id(generatedMedicalEquipId);
        if (consumables_list_cursor.getCount() > 0) {
            for (int i = 0; i < consumables_list_cursor.getCount(); i++) {
                consumables_list_cursor.moveToPosition(i);
                String consumables_id = consumables_list_cursor.getString(consumables_list_cursor.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_ID + ""));
                Cursor del_cursor_consumables = consumables_enroll.update_flag_By_consumables_Id(consumables_id);
            }
        }
        consumables_enroll.close();

        Trn_Voice_Of_Customer voc_enroll = new Trn_Voice_Of_Customer(getActivity());
        voc_enroll.open();
        Cursor voc_enroll_cursor = voc_enroll.fetchBy_Voc_Eq_Enroll_Id(generatedMedicalEquipId);
        if (voc_enroll_cursor.getCount() > 0) {
            for (int i = 0; i < voc_enroll_cursor.getCount(); i++) {
                voc_enroll_cursor.moveToPosition(i);
                String voc_id = voc_enroll_cursor.getString(voc_enroll_cursor.getColumnIndex("" + BusinessAccessLayer.VOC_ID + ""));
                Cursor del_cursor_voc = voc_enroll.update_flag_By_voc_Id(voc_id);
            }
        }
        voc_enroll.close();

    }

    private void datepicker() {
//		if (mYear != 0 || mMonth != 0 || mDay != 0) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
//		}

        new DatePickerDialog(getActivity(), dateD,
                mYear, mMonth, mDay).show();

    }

    /**
     * @Subject date picker method
     * @Description open datepicker dialog. set date picker for current date add
     * pickerListener listner to date picker
     * @Created_By Nandhini.M
     * @Created_On
     * @Updated_By Sathish
     * @Updated_On 20/10/15
     */
    DatePickerDialog.OnDateSetListener dateD = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            try {
                updateLabel();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * @Subject updateLabel method
     * @Description This method to show date picker dialog
     * @Created_By
     * @Created_On
     * @Updated_By
     * @Updated_On
     */
    protected void updateLabel() throws ParseException {
        // create a date "formatter" (the date format we want)

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        // create a new String using the date format we want
        mFormattedDatefrom = df.format(myCalendar.getTime());
        // create a date "formatter" (the date format we want)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        Date date1 = sdf.parse(getCurrentDateTime());
        Date date2 = sdf.parse(mFormattedDatefrom);
        diffBtwDays();
        if (date1.before(date2)) {

            alert("Given date is greater than today's date  : "
                    + getShowDate(getCurrentDate()));

        }
//        else if (diffInDays <= 1828) {
//            alert("Please select a date less than 5 years from the current date");
//        }
        else {

            eTxt_insDate.setText(getShowDate(mFormattedDatefrom));
        }

    }

    public void alert(String message) {
        AlertDialog al_dia = new AlertDialog.Builder(getActivity()).setMessage(message)
                .setTitle("EquMED")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

        TextView t1 = (TextView) al_dia.findViewById(android.R.id.message);
        t1.setTypeface(calibri_bold_typeface);
    }

    private void diffBtwDays() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // String fromDate = "20150101";
        // String toDate = "20150107";
        long diff = 0;

        try {

            // Convert to Date
            Date startDate = df.parse(mFormattedDatefrom);
            Calendar c1 = Calendar.getInstance();

            // Change to Calendar Date
            c1.setTime(startDate);

            // Convert to Date
            // Date endDate = df.parse(toDate);
            Calendar c2 = Calendar.getInstance();
            Date endDate = c2.getTime();

            // Change to Calendar Date
            c2.setTime(endDate);

            // get Time in milli seconds
            long ms1 = c1.getTimeInMillis();
            long ms2 = c2.getTimeInMillis();
            // get difference in milli seconds

            diff = ms2 - ms1;

        } catch (ParseException e) {
            e.printStackTrace();
            String bugClassName = "", errorMessage = "";
//            bugClassName = BusinessAccessLayer.bugClassName;
//            errorMessage = convertErrorToString(e);
//            ExceptionHandler ParseExceptiondiffBtwDays = new ExceptionHandler(
//                    Registration.this);
//            ParseExceptiondiffBtwDays.catchException(bugClassName,
//                    "diffBtwDays_ParseException", errorMessage);

        }
// Find number of days by dividing the mili seconds
        diffInDays = (int) (diff / (24 * 60 * 60 * 1000));
    }


    /**
     * @param – no parameter
     * @return – no return values
     * @throws – no exception throws
     * @Type void Method
     * @Created_By Aravindhakumar.S
     * @Created_On 08-12-2015 at 02:28:46 pm
     * @Updated_By
     * @Updated_On
     * @Description To upload image from gallery or camera
     */

    private void uploadImage() {


        final CharSequence[] options = {"Take from camera", "Choose from gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take from camera"))

                {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File f = new File(Environment.getExternalStorageDirectory(), "temp_equmed.jpg");
//
                    picUri = Uri.fromFile(f);
//
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
//
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//
                    intent.putExtra("return-data", true);

                    startActivityForResult(intent, BusinessAccessLayer.PICK_FROM_CAMERA);

//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    File f = new File(Environment.getExternalStorageDirectory(), "temp_equmed.jpg");
//
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//
//                    startActivityForResult(intent, BusinessAccessLayer.PICK_FROM_CAMERA);


                } else if (options[item].equals("Choose from gallery"))

                {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, BusinessAccessLayer.PICK_FROM_GALLERY);


                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    private String storeImage(Bitmap scale, String fileName) {

        File tempImg = new File(getActivity().getFilesDir(), "/temp_images/");

        if (!tempImg.isDirectory()) {
            tempImg.mkdirs();
        }
        String finalPath = "";
        try {
            finalPath = tempImg.getPath() + "/" + fileName;
            FileOutputStream fos = new FileOutputStream(finalPath);
            scale.compress(Bitmap.CompressFormat.JPEG, 45, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }

        return finalPath;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == getActivity().RESULT_OK) {
//
//
//            if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA) {
//
//
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                System.out.println("Absolute Path : " + f.getAbsolutePath());
//
//                for (File temp : f.listFiles()) {
//
//                    if (temp.getName().equals("temp.jpg")) {
//
//                        f = temp;
//
//                        break;
//
//                    }
//
//                }
//
//                try {
//
//                    Bitmap bitmap = null;
//                    medEquImageEncodedStr = "";
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                            bitmapOptions);
//                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                    Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//
//
//                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                    String[] extension = fileName.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String tempImg = storeImage(scale, new_file_name);
//
//                    Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//                    trn_imagesAdapt.open();
//                    Cursor trn_images_cursor = trn_imagesAdapt.fetch();
//                    int id_trn_images = trn_images_cursor.getCount() + 1;
//
//                    Bean beanObj = new Bean();
//                    beanObj.setTrn_img_img_id("" + id_trn_images);
//                    beanObj.setTrn_img_encrypted_data("");
//                    beanObj.setTrn_img_encrypted_name(new_file_name);
//                    System.out.println("Medical Image path:" + tempImg.toString());
//                    beanObj.setTrn_img_path(tempImg);
//                    beanObj.setNew_Img_upload("1");
//                    medicalImagesArray.add(beanObj);
//
//
//                    if (medicalImagesArray.size() > 0) {
//                        hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
//                        selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
//                        loadImageArray();
//                    }
//
//
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//
//                }
//
//            } else if (requestCode == BusinessAccessLayer.PICK_FROM_GALLERY) {
//
//                medEquImageEncodedStr = "";
//
//                Uri selectedImage = data.getData();
//
//                String[] filePath = {MediaStore.Images.Media.DATA};
//
//                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
//
//                c.moveToFirst();
//
//                int columnIndex = c.getColumnIndex(filePath[0]);
//
//                String picturePath = c.getString(columnIndex);
//                c.close();
//                Bitmap thumbnail = null;
//                thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//                Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
//
//                File file = new File(picturePath);
//                String file_name = file.getName().toString();
//                String[] extension = file_name.split("\\.");
//                String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                String new_file_name = n_file_name + "." + extension[1];
//
//                String fileDirectoryPath = storeImage(scaled, new_file_name);
//
//
//                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//                trn_imagesAdapt.open();
//                Cursor trn_images_cursor = trn_imagesAdapt.fetch();
//
//                int id_trn_images = trn_images_cursor.getCount() + 1;
//
//                Bean beanObj = new Bean();
//                beanObj.setTrn_img_img_id("" + id_trn_images);
//                beanObj.setNew_Img_upload("1");
//                beanObj.setTrn_img_encrypted_data("");
//                beanObj.setTrn_img_encrypted_name(new_file_name);
//                beanObj.setTrn_img_path(fileDirectoryPath);
//
//                medicalImagesArray.add(beanObj);
//
//                if (medicalImagesArray.size() > 0) {
//
//                    hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
//                    selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
//                    loadImageArray();
//
//                }
//
//
//            } else if (requestCode == BusinessAccessLayer.FILE_ATTACHMENT) {
//
//                curFileName = data.getStringExtra("GetFileName");
//
////                String n_file_name = extension[0] + "_" + getCurrentDateTime();
////                    String new_file_type;
////                if (extension.length > 1) {
////                    new_file_type = n_file_name + "." + extension[1];
////                } else {
////
////                    new_file_type = n_file_name;
////                }
//
//                System.out.println("CurFileName:" + curFileName);
//
//                String file_name = curFileName;
//                String[] extension = file_name.split("\\.");
//                //    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                //   String new_file_type = n_file_name + "." + extension[1];
//                if (extension.length > 1) {
//
//                    getFilePath = data.getStringExtra("GetPath");
//                    String pathPlusName = getFilePath + "/" + curFileName;
//                    file_upload = new File(pathPlusName);
//
//                    long fileSize = file_upload.length() / 1024;
//
//                    if (fileSize > 2048) {
//
//                        Toast.makeText(getActivity(), "Please attach file less than 2 MB", Toast.LENGTH_SHORT).show();
//                    } else {
//                        System.out.println("fize size:" + fileSize);
//
//                        byte[] docBytes;
//
//
//                        //       String docString = Base64.encodeToString(docBytes, Base64.DEFAULT);
//                        String docString = "";
//                        System.out.println("Inside file attach : " + pathPlusName);
//
//                        Bean beanObjDocument = new Bean();
//                        beanObjDocument.setTrn_doc_name(BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT);
//                        beanObjDocument.setTrn_doc_type(curFileName);
//                        beanObjDocument.setTrn_doc_encrypted_data(docString);
//                        beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);
//
//                        medicalDocumentArray.add(beanObjDocument);
//
//
//                        beanObjDocument = null;
//
//                        loadDocumentFile();
//                    }
//
//                } else {
//                    Toast.makeText(getContext(), "File format is not valid", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//            else if (requestCode == 4)
//            {
//                String scan_result = data.getStringExtra(ZBarConstants.SCAN_RESULT);
//                int scan_result_type = data.getIntExtra(ZBarConstants.SCAN_RESULT_TYPE,0);
//                eTxt_serialNo.setText(scan_result);
//            }
//        }
//
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == getActivity().RESULT_OK) {


        if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA && resultCode == getActivity().RESULT_OK) {

            CropImage(picUri);

            f = new File(Environment.getExternalStorageDirectory().toString());
            System.out.println("Absolute Path : " + f.getAbsolutePath());

            for (File temp : f.listFiles()) {

                if (temp.getName().equals("temp_equmed.jpg")) {

                    f = temp;

                    break;

                }

            }

//                try {
//
//                    Bitmap bitmap = null;
//                    medEquImageEncodedStr = "";
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                            bitmapOptions);
//                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                    Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//
//
//                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                    String[] extension = fileName.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String tempImg = storeImage(scale, new_file_name);
//
//                    Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//                    trn_imagesAdapt.open();
//                    Cursor trn_images_cursor = trn_imagesAdapt.fetch();
//                    int id_trn_images = trn_images_cursor.getCount() + 1;
//
//                    Bean beanObj = new Bean();
//                    beanObj.setTrn_img_img_id("" + id_trn_images);
//                    beanObj.setTrn_img_encrypted_data("");
//                    beanObj.setTrn_img_encrypted_name(new_file_name);
//                    System.out.println("Medical Image path:" + tempImg.toString());
//                    beanObj.setTrn_img_path(tempImg);
//                    beanObj.setNew_Img_upload("1");
//                    medicalImagesArray.add(beanObj);
//
//
//                    if (medicalImagesArray.size() > 0) {
//                        hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
//                        selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
//                        loadImageArray();
//                    }
//
//
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//
//                }

        } else if (requestCode == BusinessAccessLayer.PICK_FROM_GALLERY && resultCode == getActivity().RESULT_OK) {

            medEquImageEncodedStr = "";

//                Uri selectedImage = data.getData();
            picUri = data.getData();

            CropImage(picUri);

            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor c = getActivity().getContentResolver().query(picUri, filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            String picturePath = c.getString(columnIndex);
            c.close();


//            Bitmap thumbnail = null;
//            thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//            int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//            Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);

            file = new File(picturePath);

//            String file_name = file.getName().toString();
//            String[] extension = file_name.split("\\.");
//            String n_file_name = extension[0] + "_" + getCurrentDateTime();
//            String new_file_name = n_file_name + "." + extension[1];
//
//            String fileDirectoryPath = storeImage(scaled, new_file_name);
//
//
//            Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//            trn_imagesAdapt.open();
//            Cursor trn_images_cursor = trn_imagesAdapt.fetch();
//
//            int id_trn_images = trn_images_cursor.getCount() + 1;
//
//            Bean beanObj = new Bean();
//            beanObj.setTrn_img_img_id("" + id_trn_images);
//            beanObj.setNew_Img_upload("1");
//            beanObj.setTrn_img_encrypted_data("");
//            beanObj.setTrn_img_encrypted_name(new_file_name);
//            beanObj.setTrn_img_path(fileDirectoryPath);
//
//            medicalImagesArray.add(beanObj);
//
//            if (medicalImagesArray.size() > 0) {
//
//                hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
//                selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
//                loadImageArray();
//
//            }


        } else if (requestCode == BusinessAccessLayer.CROP_IMAGE) {
            if (data != null) {
                Bitmap photo = null;
                if (data.getData() == null) {
                    photo = (Bitmap) data.getExtras().get("data");
                    System.out.println("====if========" + photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                } else {
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("====else========" + photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                }
                try {

//                    Bitmap bitmap = null;
//                    medEquImageEncodedStr = "";
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                            bitmapOptions);
//                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                    Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);


                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                    String[] extension = fileName.split("\\.");
                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_name = n_file_name + "." + extension[1];

                    String tempImg = storeImage(photo, new_file_name);

                    Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                    trn_imagesAdapt.open();
                    Cursor trn_images_cursor = trn_imagesAdapt.fetch();
                    int id_trn_images = trn_images_cursor.getCount() + 1;

                    Bean beanObj = new Bean();
                    beanObj.setTrn_img_img_id("" + id_trn_images);
                    beanObj.setTrn_img_encrypted_data("");
                    beanObj.setTrn_img_encrypted_name(new_file_name);
                    System.out.println("Medical Image path:" + tempImg.toString());
                    beanObj.setTrn_img_path(tempImg);
                    beanObj.setNew_Img_upload("1");
                    medicalImagesArray.add(beanObj);


                    if (medicalImagesArray.size() > 0) {
                        hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
                        selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
                        loadImageArray();
                    }


                } catch (Exception e) {

                    e.printStackTrace();

//                }
                    if (f != null) {
                        // To delete original image taken by camera
                        if (f.delete()) {

                            // Toast.makeText(TakePictureDemo.class,"original image deleted...",Toast.LENGTH_SHORT).show();
                        }
                        // Common.showToast(TakePictureDemo.this,"original image deleted...");
                    }
                }
            }
        } else if (requestCode == BusinessAccessLayer.CROP_IMAGE) {
            if (data != null) {

                Bitmap photo = null;
                if (data.getData() == null) {
                    photo = (Bitmap) data.getExtras().get("data");
                    System.out.println("====if========" + photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                } else {
                    try {
                        photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("====else========" + photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                }

                String file_name = file.getName().toString();
                String[] extension = file_name.split("\\.");
                String n_file_name = extension[0] + "_" + getCurrentDateTime();
                String new_file_name = n_file_name + "." + extension[1];

                String fileDirectoryPath = storeImage(photo, new_file_name);


                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                trn_imagesAdapt.open();
                Cursor trn_images_cursor = trn_imagesAdapt.fetch();

                int id_trn_images = trn_images_cursor.getCount() + 1;

                Bean beanObj = new Bean();
                beanObj.setTrn_img_img_id("" + id_trn_images);
                beanObj.setNew_Img_upload("1");
                beanObj.setTrn_img_encrypted_data("");
                beanObj.setTrn_img_encrypted_name(new_file_name);
                beanObj.setTrn_img_path(fileDirectoryPath);

                medicalImagesArray.add(beanObj);

                if (medicalImagesArray.size() > 0) {

                    hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
                    selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
                    loadImageArray();

//            }
                    if (file != null) {
                        // To delete original image taken by camera
                        if (file.delete()) {

                            // Toast.makeText(TakePictureDemo.class,"original image deleted...",Toast.LENGTH_SHORT).show();
                        }
                        // Common.showToast(TakePictureDemo.this,"original image deleted...");
                    }
                }
            }
        } else if (requestCode == BusinessAccessLayer.FILE_ATTACHMENT) {

            curFileName = data.getStringExtra("GetFileName");

            if(curFileName.length()>0) {

//                String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_type;
//                if (extension.length > 1) {
//                    new_file_type = n_file_name + "." + extension[1];
//                } else {
//
//                    new_file_type = n_file_name;
//                }

                System.out.println("CurFileName:" + curFileName);

                String file_name = curFileName;
                String[] extension = file_name.split("\\.");
                //    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                //   String new_file_type = n_file_name + "." + extension[1];
                if (extension.length > 1) {

                    getFilePath = data.getStringExtra("GetPath");
                    String pathPlusName = getFilePath + "/" + curFileName;
                    file_upload = new File(pathPlusName);

                    long fileSize = file_upload.length() / 1024;

                    if (fileSize > 2048) {

                        Toast.makeText(getActivity(), "Please attach file less than 2 MB", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("fize size:" + fileSize);

                        byte[] docBytes;


                        //       String docString = Base64.encodeToString(docBytes, Base64.DEFAULT);
                        String docString = "";
                        System.out.println("Inside file attach : " + pathPlusName);

                        Bean beanObjDocument = new Bean();
                        beanObjDocument.setTrn_doc_name(BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT);
                        beanObjDocument.setTrn_doc_type(curFileName);
                        beanObjDocument.setTrn_doc_encrypted_data(docString);
                        beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);

                        medicalDocumentArray.add(beanObjDocument);


                        beanObjDocument = null;

                        loadDocumentFile();
                    }

                } else {
                   // Toast.makeText(getContext(), "File format is not valid", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == BusinessAccessLayer.BAR_CODE_SCAN) {
            String scan_result = data.getStringExtra(ZBarConstants.SCAN_RESULT);
            int scan_result_type = data.getIntExtra(ZBarConstants.SCAN_RESULT_TYPE, 0);
            eTxt_serialNo.setText(scan_result);
        }
//        }

    }


    private void loadDocumentFile() {

        if (medicalDocumentArray.size() > 0) {
            list_medicalDocument.setVisibility(View.VISIBLE);
            list_medicalDocument.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, medicalDocumentArray.size() * 100));

            DocumentListAdapter adapter = new DocumentListAdapter(getActivity());
            list_medicalDocument.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            list_medicalDocument.setVisibility(View.GONE);
        }


    }

    private class DocumentListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public DocumentListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return medicalDocumentArray.size();
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
                        R.layout.inflate_document_attachment, parent, false);


                holder.eTxt_medical_attDoc = (TextView) convertView.findViewById(R.id.eTxt_medical_attDoc);
                holder.eTxt_medical_attDoc.setTypeface(calibri_typeface);
                holder.txtVw_documentNo = (TextView) convertView.findViewById(R.id.txtVw_documentNo);
                holder.txtVw_documentNo.setTypeface(calibri_typeface);

                holder.imgV_MedicalDocDelete = (ImageView) convertView.findViewById(R.id.imgV_MedicalDocDelete);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Bean beanObj = medicalDocumentArray.get(position);
            int sNo = position + 1;
            holder.txtVw_documentNo.setText("Attachment " + sNo);
            holder.eTxt_medical_attDoc.setText(beanObj.getTrn_doc_type());

            holder.imgV_MedicalDocDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Flag  sadsa" + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);
                    if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                        medicalDocumentArray.remove(position);
                        if (medicalDocumentArray.size() == 0) {
                            list_medicalDocument.setVisibility(View.GONE);
                        }
                    } else {

                        Trn_Documents trn_document = new Trn_Documents(getActivity());
                        trn_document.open();
                        Bean docBean = medicalDocumentArray.get(position);

                        Cursor cursorDoc = trn_document.fetchByDoc_IdAndDocDocID(generatedMedicalEquipId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT);
                        if (cursorDoc.getCount() > 0) {
                            String doc_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                            if (doc_sync_status.equals("0")) {
                                //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                                File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/" + docBean.getTrn_doc_type());
                                tempDir.delete();

                                Cursor delete_trn_document = trn_document.deleteByDoc_Id_Type(docBean.getTrn_doc_doc_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT);
                            } else {
                                File dir = new File(getActivity().getFilesDir(), "/documents/" + docBean.getTrn_doc_type());
                                dir.delete();

                                boolean update_trn_document = trn_document.update_document(docBean.getTrn_doc_doc_id(), generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), "2",
                                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                                        docBean.getTrn_created_by(), docBean.getTrn_created_on(),
                                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                            }
                        }

                        medicalDocumentArray.remove(position);
                        if (medicalDocumentArray.size() == 0) {
                            list_medicalDocument.setVisibility(View.GONE);
                        }

                        trn_document.close();
                    }
                    notifyDataSetChanged();
                }
            });

            return convertView;
        }


        class ViewHolder {
            TextView eTxt_medical_attDoc, txtVw_documentNo;
            ImageView imgV_MedicalDocDelete;

        }

    }


    public static byte[] convertFileToByteArray(File f) {

        byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("byteArray::" + byteArray);

        return byteArray;
    }

    public static File writeByteArrayToFile(byte[] bytearray_file) {

        String strFilePath = "/storage/sdcard0/saveTest3.db";
        try {
            FileOutputStream fos = new FileOutputStream(strFilePath);
            String strContent = "Write File using Java ";

            fos.write(bytearray_file);
            fos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException : " + ex);
        } catch (IOException ioe) {
            System.out.println("IOException : " + ioe);
        }
        return writtenFile;
    }



        /* * @Name captureCameraImages()
 * @Type No Argument Method
 * @Created_By GokulRaj K.c
 * @Created_On 01-12-2015
 * @Updated_By
 * @Updated_On
 * @Description The user can capture the image from camera and sets the image in the profile photo.
 *
 **/


    private void captureCameraImages() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, BusinessAccessLayer.PICK_FROM_CAMERA);

    }


    private String getMedicalEquipmentlId(int count) {
        String imeino = setIMEIno();
//        String imeino = imeinoFull.substring(Math.max(imeinoFull.length() - 5, 0));
        String timeStamp = getUnixTimeStamp();
        String finalId = "MED_EQU_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }

    /**
     * @param – no parameter
     * @return – no return values
     * @throws – no exception throws
     * @Type void Method
     * @Created_By Mohanraj.S
     * @Created_On 04-12-2015 at 02:46:46 pm
     * @Updated_By
     * @Updated_On
     * @Description To Insert the values of Medical Equipment Details
     */
    private void update_Medical_Equipment_Details() {
        String str_eTxt_insDateDef = "";
        str_eTxt_serialNo = eTxt_serialNo.getText().toString().trim();
        str_eTxt_locationCode = eTxt_locationCode.getText().toString().trim();
        // String str_spn_equipList= spn_equipList.getSelectedItem().toString().trim();
        str_eTxt_make = eTxt_make.getText().toString().trim();
        str_eTxt_model = eTxt_model.getText().toString().trim();
        str_eTxt_attDoc = eTxt_attDoc.getText().toString().trim();
        str_eTxt_insDate = eTxt_insDate.getText().toString().trim();
        if (str_eTxt_insDate.length() > 0) {
            str_eTxt_insDateDef = getDefaultDate(str_eTxt_insDate);
        }

        str_eTxt_serTagNo = eTxt_serTagNo.getText().toString().trim();
        str_eTxt_medEquipNotes = eTxt_medEquipNotes.getText().toString().trim();
        str_upsStatus = upsStatus.toString().trim();
        str_manualStatus = manualStatus.toString().trim();
        str_stablizerStatus = stablizerStatus.toString().trim();
        str_eTxt_accList = eTxt_accList.getText().toString().trim();
        str_eTxt_insNotes = eTxt_insNotes.getText().toString().trim();
        str_eTxt_condNotes = eTxt_condNotes.getText().toString().trim();
        String str_eTxt_gps_coordinates = eTxt_gpsCoordnts.getText().toString().trim();


        Trn_Equipment_Enrollment trn_equipment_enrollment = new Trn_Equipment_Enrollment(getActivity());
        trn_equipment_enrollment.open();
        String flagStatus = "";
        if (fetchSyncStatus.equalsIgnoreCase("0")) {
            flagStatus = "0";
        } else {
            flagStatus = "1";
        }
        boolean insert_equipment_enroll = trn_equipment_enrollment.update_equipment_enroll(
                generatedMedicalEquipId,
                selectedMedicalEquipId,
                selectedHospitalId,
                str_eTxt_locationCode,
                str_eTxt_gps_coordinates,
                str_eTxt_serialNo,
                str_eTxt_make,
                str_eTxt_model,
                str_eTxt_insDateDef,
                str_eTxt_serTagNo,
                str_eTxt_medEquipNotes,
                spn_insCondStr,
                str_eTxt_insNotes,
                spn_worCondStr,
                str_eTxt_condNotes,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                "1",
                str_createdBy,
                str_createdOn,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (insert_equipment_enroll == true) {

            Trn_Equipment_Enroll_Accessories trn_equipment_enroll_accessories = new Trn_Equipment_Enroll_Accessories(getActivity());
            trn_equipment_enroll_accessories.open();
            boolean insert_equipment_enroll_accessories = trn_equipment_enroll_accessories.update_equipment_enroll_accessories(
                    generatedMedicalEquipId, str_upsStatus,
                    str_manualStatus, str_stablizerStatus,
                    str_eTxt_accList,"1",
                    BusinessAccessLayer.IS_ACTIVE_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    str_createdBy, str_createdOn,
                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());

//
//            Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//            trn_imagesAdapt.open();
//            Cursor imGCursor = trn_imagesAdapt.fetch();
//            int id_trn_images = imGCursor.getCount() + 1;
//
//
//            boolean insert_trn_images = trn_imagesAdapt.update_image("" + id_trn_images, generatedMedicalEquipId, "M", medEquImageEncodedStr, "1",
//                    BusinessAccessLayer.SYNC_STATUS_VALUE,
//                    str_createdBy, str_createdOn,
//                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//
//            trn_imagesAdapt.close();

            for (int i = 0; i < medicalImagesArray.size(); i++) {

                System.out.println("medicalImagesArray insert:" + medicalImagesArray.size());

                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                trn_imagesAdapt.open();
                Cursor imGCursor = trn_imagesAdapt.fetch();

                Bean medImageBean = medicalImagesArray.get(i);
                int id_trn_images = imGCursor.getCount() + 1;
                if (medImageBean.getNew_Img_upload().equalsIgnoreCase("1")) {

                    System.out.println("Id 1 : " + generatedMedicalEquipId);
                    System.out.println("Id 3 : " + medImageBean.getTrn_img_img_id());
                    //   Cursor cursorImage = trn_imagesAdapt.fetchByImgImg_ID(generatedMedicalEquipId, medImageBean.getTrn_img_img_id(), BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE);

//                    if (cursorImage.getCount() == 0) {
                    long insert_trn_document = trn_imagesAdapt.insert_image("" + id_trn_images, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE,
                            medImageBean.getTrn_img_encrypted_name(), BusinessAccessLayer.FLAG_VALUE,
                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//                    }

                    trn_imagesAdapt.close();
                }


            }


            for (int i = 0; i < medicalDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();

                Bean docBean = medicalDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;

                if (docBean.getTrn_doc_filepath_filname() != null) {
                    System.out.println("Document Bean array " + docBean.getTrn_doc_doc_id());

                    Cursor cursorDoc = trn_documentAdapt.fetchByDoc_IdAndDocDocID(generatedMedicalEquipId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT);

//                if (cursorDoc.getCount() == 0) {
//                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), BusinessAccessLayer.FLAG_VALUE,
//                        BusinessAccessLayer.SYNC_STATUS_VALUE,
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//                }

                    //  System.out.println("Document file path : " + docBean.getTrn_doc_filepath_filname());

                    String file_name = docBean.getTrn_doc_type();
                    String[] extension = file_name.split("\\.");

                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_type = n_file_name + "." + extension[1];
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_type;
//                    if(extension.length>1) {
//                        new_file_type = n_file_name + "." + extension[1];
//                    }
//                    else
//                    {
//
//                        new_file_type = n_file_name;
//                    }

                    long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                    trn_documentAdapt.close();

                    if (insert_trn_document != 0) {
                        File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/");

                        if (!tempDir.isDirectory()) {
                            tempDir.mkdirs();
                        }


                        File source = new File(docBean.getTrn_doc_filepath_filname());
                        System.out.println("getTrn_doc_filepath_filname:" + docBean.getTrn_doc_filepath_filname());
                        try {
                            System.out.println("getTrn_doc_filepath_filname  try source  :" + source);

                            System.out.println("getTrn_doc_filepath_filname  try :" + docBean.getTrn_doc_filepath_filname());
                            System.out.println("getTrn_doc_filepath_filname  try tempDir  :" + tempDir.getPath());
                            String tempDirectory = tempDir.getAbsolutePath();


                            FileInputStream in = new FileInputStream(source);
                            FileOutputStream out = new FileOutputStream(tempDir + "/" + new_file_type);

                            byte[] buffer = new byte[1024];
                            int read;
                            while ((read = in.read(buffer)) != -1) {
                                out.write(buffer, 0, read);
                            }
                            in.close();
                            in = null;

                            // write the output file (You have now copied the file)
                            out.flush();
                            out.close();
                            out = null;

                            //      FileUtils.copyFile(source,tempDirectory);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                // trn_documentAdapt.close();
            }

            isAddedNew = true;
            showContactUsDialog(getActivity(), "Medical equipment updated successfully");

        } else {
            showValidationDialog(getActivity(), "Medical equipment update failed");
        }
    }

    /**
     * @param – no parameter
     * @return – no return values
     * @throws – no exception throws
     * @Type void Method
     * @Created_By Mohanraj.S
     * @Created_On 04-12-2015 at 02:46:46 pm
     * @Updated_By
     * @Updated_On
     * @Description To Insert the values of Medical Equipment Details
     */
    private void insert_Medical_Equipment_Details() {
        //To get String values from the Component
        //String str_spn_hosptList= spn_hosptList.getSelectedItem().toString();
        String str_eTxt_insDateDef = "";
        String str_eTxt_serialNo = eTxt_serialNo.getText().toString().trim();
        String str_eTxt_locationCode = eTxt_locationCode.getText().toString().trim();
        // String str_spn_equipList= spn_equipList.getSelectedItem().toString().trim();
        String str_eTxt_make = eTxt_make.getText().toString().trim();
        String str_eTxt_model = eTxt_model.getText().toString().trim();
        //String str_eTxt_equipId=eTxt_equipId.getText().toString().trim();
        String str_eTxt_attDoc = eTxt_attDoc.getText().toString().trim();
        String str_eTxt_insDate = eTxt_insDate.getText().toString().trim();
        String str_gps_coordinates = eTxt_gpsCoordnts.getText().toString().trim();

        if (str_eTxt_insDate.length() > 0) {
            str_eTxt_insDateDef = getDefaultDate(str_eTxt_insDate);
        }

        String str_eTxt_serTagNo = eTxt_serTagNo.getText().toString().trim();
        String str_eTxt_medEquipNotes = eTxt_medEquipNotes.getText().toString().trim();
        String str_upsStatus = upsStatus.toString().trim();
        String str_manualStatus = manualStatus.toString().trim();
        String str_stablizerStatus = stablizerStatus.toString().trim();
        String str_eTxt_accList = eTxt_accList.getText().toString().trim();
        //String str_spn_insCond= spn_insCond.getSelectedItem().toString().trim();
        String str_spn_insCond = "no";
        String str_eTxt_insNotes = eTxt_insNotes.getText().toString().trim();
        //String str_spn_worCond= spn_worCond.getSelectedItem().toString().trim();
        String str_spn_worCond = "yes";
        String str_eTxt_condNotes = eTxt_condNotes.getText().toString().trim();


        Trn_Equipment_Enrollment trn_equipment_enrollment = new Trn_Equipment_Enrollment(getActivity());
        trn_equipment_enrollment.open();
        Cursor cursor = trn_equipment_enrollment.fetch();
        generatedMedicalEquipId = getMedicalEquipmentlId(cursor.getCount());

        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = generatedMedicalEquipId;
        BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = selectedHospitalId;
        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID = selectedMedicalEquipId;

        long insert_equipment_enroll = trn_equipment_enrollment.insert_equipment_enroll(
                generatedMedicalEquipId,
                selectedMedicalEquipId,
                selectedHospitalId,
                str_eTxt_locationCode,
                str_gps_coordinates,
                str_eTxt_serialNo,
                str_eTxt_make,
                str_eTxt_model,
                str_eTxt_insDateDef,
                str_eTxt_serTagNo,
                str_eTxt_medEquipNotes,
                "",
                spn_insCondStr,
                str_eTxt_insNotes,
                spn_worCondStr,
                str_eTxt_condNotes,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime(),
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (insert_equipment_enroll != 0) {

            Trn_Equipment_Enroll_Accessories trn_equipment_enroll_accessories = new Trn_Equipment_Enroll_Accessories(getActivity());
            trn_equipment_enroll_accessories.open();
            long insert_equipment_enroll_accessories = trn_equipment_enroll_accessories.insert_equipment_enroll_accessories(
                    generatedMedicalEquipId, str_upsStatus,
                    str_manualStatus, str_stablizerStatus,
                    str_eTxt_accList,
                    BusinessAccessLayer.FLAG_VALUE,
                    BusinessAccessLayer.IS_ACTIVE_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());


            Iterator<Uri> iterator = mMedia.iterator();

//            while (iterator.hasNext()) {
//                Uri uriSaveImage = iterator.next();
//
//                // showImage(uri);
//                Log.i(TAG, " uriSaveImage: " + uriSaveImage);
//                if (mMedia.size() >= 1) {
//                    selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
//                }
//
//                if (!uriSaveImage.toString().contains("content://")) {
//                    // probably a relative uri
//                    uriSaveImage = Uri.fromFile(new File(uriSaveImage.toString()));
//                }
//
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriSaveImage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//                trn_imagesAdapt.open();
//                Cursor trn_images_cursor = trn_imagesAdapt.fetch();
//
//                int id_trn_images = trn_images_cursor.getCount() + 1;
//                medEquImageEncodedStr = encodeTobase64(bitmap);
//                long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE, medEquImageEncodedStr, BusinessAccessLayer.FLAG_VALUE,
//                        BusinessAccessLayer.SYNC_STATUS_VALUE,
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//
//                trn_imagesAdapt.close();
//            }

//            while (iterator.hasNext()) {
//                Uri uriSaveImage = iterator.next();
//
//                // showImage(uri);
//                Log.i(TAG, " uriSaveImage: " + uriSaveImage);
//                if (mMedia.size() >= 1) {
//                    selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
//                }
//
//                if (!uriSaveImage.toString().contains("content://")) {
//                    // probably a relative uri
//                    uriSaveImage = Uri.fromFile(new File(uriSaveImage.toString()));
//                }
//
//                Bitmap bitmap = null;
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uriSaveImage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//
//                int nh = (int) (bitmap.getHeight() * (150.0 / bitmap.getWidth()));
//                Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 150, nh, true);
//
////                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
//                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//                trn_imagesAdapt.open();
//                Cursor trn_images_cursor = trn_imagesAdapt.fetch();
//
//                int id_trn_images = trn_images_cursor.getCount() + 1;
//                medEquImageEncodedStr = encodeTobase64(scaled);
//                long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE, medEquImageEncodedStr, BusinessAccessLayer.FLAG_VALUE,
//                        BusinessAccessLayer.SYNC_STATUS_VALUE,
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//
//                trn_imagesAdapt.close();
//            }
            System.out.println("Image count: " + medicalImagesArray.size());

            for (int imgI = 0; imgI < medicalImagesArray.size(); imgI++) {
                Bean bean = medicalImagesArray.get(imgI);

                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                trn_imagesAdapt.open();
                Cursor trn_images_cursor = trn_imagesAdapt.fetch();
                int imgCount = trn_images_cursor.getCount() + 1;

                long insert_trn_images = trn_imagesAdapt.insert_image("" + imgCount, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE, bean.getTrn_img_encrypted_name(), BusinessAccessLayer.FLAG_VALUE,
                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                trn_imagesAdapt.close();
            }

            for (int i = 0; i < medicalDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();
                Bean docBean = medicalDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;
//                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), BusinessAccessLayer.FLAG_VALUE,
//                        BusinessAccessLayer.SYNC_STATUS_VALUE,
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                String file_name = docBean.getTrn_doc_type();
                String[] extension = file_name.split("\\.");
                String n_file_name = extension[0] + "_" + getCurrentDateTime();
                String new_file_type = n_file_name + "." + extension[1];


                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                trn_documentAdapt.close();

                if (insert_trn_document != 0) {
                    File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/");

                    if (!tempDir.isDirectory()) {
                        tempDir.mkdirs();
                    }


                    File source = new File(docBean.getTrn_doc_filepath_filname());
                    System.out.println("getTrn_doc_filepath_filname:" + docBean.getTrn_doc_filepath_filname());
                    try {
                        System.out.println("getTrn_doc_filepath_filname  try source  :" + source);

                        System.out.println("getTrn_doc_filepath_filname  try :" + docBean.getTrn_doc_filepath_filname());
                        System.out.println("getTrn_doc_filepath_filname  try tempDir  :" + tempDir.getPath());
                        String tempDirectory = tempDir.getAbsolutePath();


                        FileInputStream in = new FileInputStream(source);
                        FileOutputStream out = new FileOutputStream(tempDir + "/" + new_file_type);

                        byte[] buffer = new byte[1024];
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                        in.close();
                        in = null;

                        // write the output file (You have now copied the file)
                        out.flush();
                        out.close();
                        out = null;

                        //      FileUtils.copyFile(source,tempDirectory);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }

//            Cursor trn_documentCursor = trn_documentAdapt.fetch();
//            int id_trn_document = trn_documentCursor.getCount() + 1;
//            if (byteArray != null) {
//                documentEncodedStr = "";
//
//            } else {
//                try {
//                    documentEncodedStr = Base64.encodeToString( byteArray, Base64.DEFAULT );
//
//                } catch ( Exception e ) {
//                    documentEncodedStr = "";
//                }
//
//            }


//            long insert_trn_document = trn_documentAdapt.insert_document(
//                    generatedMedicalEquipId,
//                    BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT,
//                    documentEncodedStr, "",
//                    BusinessAccessLayer.FLAG_VALUE,
//                    BusinessAccessLayer.SYNC_STATUS_VALUE,
//                    BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                    BusinessAccessLayer.mUserId, getCurrentDateWithTime() );
//
//
//            trn_documentAdapt.close();

            isAddedNew = true;
            showValidationDialog(getActivity(), "Medical equipment added successfully");

            exportDatabse(BusinessAccessLayer.DATABASE_NAME);

            clearAllFields();
        } else {
            showValidationDialog(getActivity(), "Medical equipment added failed");
        }


    }

    private void clearAllFields() {

        eTxt_locationCode.setText("");
        eTxt_serialNo.setText("");
        eTxt_make.setText("");
        eTxt_model.setText("");
        eTxt_attDoc.setText("");
        eTxt_insDate.setText("");
        eTxt_serTagNo.setText("");
        eTxt_medEquipNotes.setText("");
        eTxt_accList.setText("");
        eTxt_insNotes.setText("");
        eTxt_condNotes.setText("");
        eTxt_gpsCoordnts.setText("");

        rBtn_upsGrp.clearCheck();
        rBtn_manualGrp.clearCheck();
        rBtn_stabGrp.clearCheck();


        spn_equipList.setSelection(0);

        spn_insCond.setSelection(0);
        spn_worCond.setSelection(0);

        selected_photos_container_eqpmnt.removeAllViews();
        hori_scroll_view_eqpmnt.setVisibility(View.GONE);
        selected_photos_container_eqpmnt.setVisibility(View.GONE);
        medicalDocumentArray.clear();
        lLayout_attDocList.setVisibility(View.GONE);
        list_medicalDocument.setVisibility(View.GONE);
//        imgVw_equipImage.setImageResource(R.drawable.userimage);


    }


    /**
     * Called when the location has changed.
     * <p/>
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    public void showValidationDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(false);

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

        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContactUsDialog.show();

    }

    public void showContactUsDialog(final Context ctx, String txt) {

        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(false);

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

                if (isAddedNew == false) {

                    delete_medical_ewuipment_installation();


                    exportDatabse(BusinessAccessLayer.DATABASE_NAME);

                    clearAllFields();
                    Intent intObj = new Intent(getActivity(), ViewMedicalEquipAppCompat.class);
                    startActivity(intObj);
                } else {

                    exportDatabse(BusinessAccessLayer.DATABASE_NAME);
                   /* clearAllFields();
                    Intent intObj = new Intent(getActivity(), ViewMedicalEquipAppCompat.class);
                    startActivity(intObj);*/

                }

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
}
