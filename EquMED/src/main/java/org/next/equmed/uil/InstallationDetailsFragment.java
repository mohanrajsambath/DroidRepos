package org.next.equmed.uil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.FileChooser;
import org.next.equmed.dal.Mst_Equipment_Status;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Documents;
import org.next.equmed.dal.Trn_Equipment_Enroll_Accessories;
import org.next.equmed.dal.Trn_Equipment_Enrollment;
import org.next.equmed.dal.Trn_Images;
import org.next.equmed.dal.Trn_Installation_Enrollment;
import org.next.equmed.dal.Trn_Service_Details;

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
import java.util.Date;
import java.util.Locale;

/**
 * Created by next-03 on 4/12/15.
 */

public class InstallationDetailsFragment extends UserInterfaceLayer implements View.OnClickListener {


    TextView txtVw_compInsName, txtVw_engName, txtVw_insDate, txtVw_gpsLoc, txtVw_nrbPhoNo, txtVw_insNotes, txtVw_insImg, txtVw_insAttDoc, txtVw_insArea,
            txtVw_supName, txtVw_supPhnNo, txtVw_areaSupImg, txtVw_supAttDoc, txtVw_active, txtVw_activeYesNo, txtVw_id_createdBy, txtVw_id_createdOn;

    EditText eTxt_compInsName, eTxt_engName, eTxt_insDate, eTxt_gpsLoc, eTxt_nrbPhnNo, eTxt_insNotes, eTxt_insAttDoc, eTxt_insArea, eTxt_supName, eTxt_supPhnNo, eTxt_supAttDoc, eTxt_id_createdBy,
            eTxt_id_createdOn;

    ImageView imgVw_insImg, imgVw_areaSupImg;

    //    Button btn_insDetailSave;
    FloatingActionButton btn_fab_insDetailSave, btn_fab_insDetailDelete, btn_fab_MedInstClear;

    Switch switch_active;


    int insDetailId = 1;
    String insEquipEnrollId = "";

    String str_eTxt_compInsName = "", str_eTxt_engName = "", str_eTxt_insDate = "", str_eTxt_gpsLoc = "", str_eTxt_nrbPhnNo = "", str_eTxt_insNotes = "", str_eTxt_insAttDoc = "", str_eTxt_insArea = "",
            str_eTxt_supName = "", str_eTxt_supPhnNo = "", str_eTxt_supAttDoc = "", str_swt_IsActive = "";

//
//    private static final int PICK_FROM_CAMERA = 1;
//    private static final int PICK_FROM_CAMERA1 = 2;

    static File writtenFile;
    static byte[] byteArray = null;

    int isCaptureImg = 0;
    int isCaptureDocument = 0;

    String curFileName = "", getFilePath = "";

    byte[] installerDocByte = null, supllierDocByte = null;

    File file_upload;

    private Calendar myCalendar = Calendar.getInstance();
    private int mYear;
    private int mMonth;
    private int mDay;
    int diffInDays;

    private String mFormattedDatefrom = "";

    LinearLayout lLayout_insDetails_eqpmnt_enroll_id, lLayout_id_createdOn, lLayout_id_createdBy;
    TextView txtVw_insDetails_eqpmnt_enroll_id;

    String insDetails_InstallermageEncodedStr = "", insDetails_InstallerDocumentEncodedStr = "", insDetails_OwnermageEncodedStr = "", insDetails_OwnerDocumentEncodedStr = "";

    String ins_insImgId = "", ins_ownerImgId = "";
    String str_createdBy = "", str_createdOn = "";
    boolean isAddedNew = true;
//    boolean isPreview = true;

    String insEnroll_syncstatus = "";

    //updated 23 dec 2015 by aravinth
    Button btn_attach_document_installer, btn_attach_document_owner;

    ArrayList<Bean> installerDocumentArray = new ArrayList<Bean>();
    ArrayList<Bean> ownerDocumentArray = new ArrayList<Bean>();

    ListView lv_installerDocument, lv_ownerDocument;

    LinearLayout lLayout_instlDtls_attDocList_installer, lLayout_instlDtls_attDocList_owner;

    Typeface calibri_typeface, calibri_bold_typeface;

    private Uri picUri;
    public File f;
    public File file;

    public InstallationDetailsFragment() {
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
                R.layout.medical_insdetail_activity, container, false);
        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        getViewCasting(rootView);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        BusinessAccessLayer.bug_class_name = "InstallationDetails";
//        getGPSLocation();

        System.out.println("BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == " + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);

        LinearLayout lLayout_hosp_loc_name = (LinearLayout) rootView.findViewById(R.id.lLayout_hosp_loc_name);
        LinearLayout lLayout_eqName = (LinearLayout) rootView.findViewById(R.id.lLayout_eqName);

        installerDocumentArray.clear();
        ownerDocumentArray.clear();

        if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
            btn_fab_insDetailSave.setImageResource(R.drawable.save);
            btn_fab_insDetailDelete.setVisibility(View.GONE);
            lLayout_id_createdBy.setVisibility(View.GONE);
            lLayout_id_createdOn.setVisibility(View.GONE);
            lLayout_insDetails_eqpmnt_enroll_id.setVisibility(View.GONE);
            lLayout_hosp_loc_name.setVisibility(View.GONE);
            lLayout_eqName.setVisibility(View.GONE);

        } else {

            btn_fab_insDetailSave.setImageResource(R.drawable.edit);
            btn_fab_insDetailDelete.setVisibility(View.VISIBLE);
            lLayout_id_createdBy.setVisibility(View.VISIBLE);
            lLayout_id_createdOn.setVisibility(View.VISIBLE);
            lLayout_insDetails_eqpmnt_enroll_id.setVisibility(View.GONE);

            String eq_enroll_hospital_name, eq_enroll_equipment_name;
            TextView txtVw_hosp_loc_name = (TextView) rootView.findViewById(R.id.txtVw_hosp_loc_name);
            TextView txtVw_eqName = (TextView) rootView.findViewById(R.id.txtVw_eqName);
            EditText eTxt_hosp_loc_name = (EditText) rootView.findViewById(R.id.eTxt_hosp_loc_name);
            EditText eTxt_eqName = (EditText) rootView.findViewById(R.id.eTxt_eqName);

            txtVw_hosp_loc_name.setTypeface(calibri_typeface);
            txtVw_eqName.setTypeface(calibri_typeface);
            eTxt_hosp_loc_name.setTypeface(calibri_typeface);
            eTxt_eqName.setTypeface(calibri_typeface);

            Trn_Service_Details trn_service_details = new Trn_Service_Details(getActivity());
            trn_service_details.open();
            Cursor hos_details = trn_service_details.fetchHosName(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);
            Cursor eq_details = trn_service_details.fetchEquName(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);
            eq_enroll_hospital_name = hos_details.getString(hos_details.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + "")) + " / " + hos_details.getString(hos_details.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));
            eq_enroll_equipment_name = eq_details.getString(eq_details.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
            eTxt_hosp_loc_name.setText(eq_enroll_hospital_name);
            eTxt_eqName.setText(eq_enroll_equipment_name);
            lLayout_hosp_loc_name.setVisibility(View.VISIBLE);
            lLayout_eqName.setVisibility(View.VISIBLE);
            System.out.println("BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID:::" + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

            viewInstallDetails(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        }

        return rootView;
    }

    private void viewInstallDetails(String enrollId) {

        Trn_Installation_Enrollment inst_enrollAdapt = new Trn_Installation_Enrollment(getActivity());
        inst_enrollAdapt.open();
        Cursor mCr_trn_inst_enroll = inst_enrollAdapt.fetchByInst_EquipmentEnroll_Id(enrollId);
        System.out.println("mCr_trn_inst_enroll :;" + mCr_trn_inst_enroll.getCount());
        if (mCr_trn_inst_enroll.getCount() > 0) {
            for (int i = 0; i < mCr_trn_inst_enroll.getCount(); i++) {

                mCr_trn_inst_enroll.moveToPosition(i);

                String insEnroll_eqpmntEnrollId = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_EQUIPMENT_ENROLL_ID + ""));
                insEquipEnrollId = insEnroll_eqpmntEnrollId;
                String insEnroll_cmpnyName = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_COMPANY_BY + ""));
                System.out.println("insEnroll_cmpnyName:::::" + insEnroll_cmpnyName);
                eTxt_compInsName.setText(insEnroll_cmpnyName);

                String insEnroll_engnrName = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_ENGG_NAME + ""));
                eTxt_engName.setText(insEnroll_engnrName);

                String insEnroll_insDate = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_INSTALL_DATE + ""));
                if (insEnroll_insDate.length() > 0) {
                    String showinsEnroll_insDate = getShowDate(insEnroll_insDate);
                    eTxt_insDate.setText(showinsEnroll_insDate);
                } else {
                    eTxt_insDate.setHint("Date");
                }

                String insEnroll_locatCode = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_LOCATION + ""));
                eTxt_gpsLoc.setText(insEnroll_locatCode);

                String insEnroll_nrbPhnNoe = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_NEAR_BY_PHONENO + ""));
                eTxt_nrbPhnNo.setText(insEnroll_nrbPhnNoe);

                String insEnroll_insNotes = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_NOTES + ""));
                eTxt_insNotes.setText(insEnroll_insNotes);

                String insEnroll_insArea = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_AREA + ""));
                eTxt_insArea.setText(insEnroll_insArea);

                String insEnroll_insSupName = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_OWNER_NAME + ""));
                eTxt_supName.setText(insEnroll_insSupName);

                String insEnroll_insSupPhnNo = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.INST_OWNER_PHONENO + ""));
                eTxt_supPhnNo.setText(insEnroll_insSupPhnNo);

                insEnroll_syncstatus = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));


                str_createdBy = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll
                        .getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));

//                eTxt_id_createdBy.setText(str_createdBy);

                if (str_createdBy.equalsIgnoreCase("1")) {
                    eTxt_id_createdBy.setText("Admin");
                } else {
                    eTxt_id_createdBy.setText("" + getUserNameByUserId(str_createdBy));
                }

                str_createdOn = mCr_trn_inst_enroll.getString(mCr_trn_inst_enroll
                        .getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                if (str_createdOn.length() > 0) {
                    String show_str_createdOn = getShowDateWithTime(str_createdOn);
                    eTxt_id_createdOn.setText(show_str_createdOn);
                }

                Trn_Images trn_image = new Trn_Images(getActivity());
                trn_image.open();

                Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(enrollId, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_IMG);
                if (mCr_trn_image.getCount() > 0) {
                    for (int int_trn_image = 0; int_trn_image < mCr_trn_image.getCount(); int_trn_image++) {
                        mCr_trn_image.moveToPosition(int_trn_image);


                        String str_trn_image = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                        insDetails_InstallermageEncodedStr = str_trn_image;

                        String str_img_id = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));
                        System.out.println("ins_insImgId:" + ins_insImgId);
                        ins_insImgId = str_img_id;

                        String img_path;
                        File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + str_trn_image);
                        File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + str_trn_image);

                        if (tempImg.exists() == true) {
                            img_path = tempImg.getAbsolutePath();
                        } else {
                            img_path = tempImg1.getAbsolutePath();
                        }
                        System.out.println("img_path ==> " + img_path);
                        // Bitmap medicalEqpmntImage = decodeBase64(str_trn_image);
                        imgVw_insImg.setImageURI(Uri.parse(img_path));
                    }

                }

                Trn_Images.close();

                Trn_Images trn_image_supplr = new Trn_Images(getActivity());
                trn_image_supplr.open();

                Cursor mCr_trn_image_supplr = trn_image_supplr.fetchByEq_Enroll_Id_Type(enrollId, BusinessAccessLayer.INSTALLDETAILS_OWNER_IMAGE);
                if (mCr_trn_image_supplr.getCount() > 0) {
                    for (int int_trn_image_supplr = 0; int_trn_image_supplr < mCr_trn_image_supplr.getCount(); int_trn_image_supplr++) {
                        mCr_trn_image_supplr.moveToPosition(int_trn_image_supplr);


                        String str_trn_image_supplr = mCr_trn_image_supplr.getString(mCr_trn_image_supplr.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                        insDetails_OwnermageEncodedStr = str_trn_image_supplr;
                        String str_img_id_owner = mCr_trn_image_supplr.getString(mCr_trn_image_supplr.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));

                        ins_ownerImgId = str_img_id_owner;

                        String img_path_owner;
                        File tempImg_owner = new File(getActivity().getFilesDir() + "/temp_images/" + str_trn_image_supplr);
                        File tempImg1_owner = new File(getActivity().getFilesDir() + "/images/" + str_trn_image_supplr);

                        System.out.println("tempImg_owner.exists() " + tempImg_owner.exists());
                        if (tempImg_owner.exists() == true) {
                            img_path_owner = tempImg_owner.getAbsolutePath();
                        } else {
                            img_path_owner = tempImg1_owner.getAbsolutePath();
                        }
                        System.out.println("img_path ==> " + img_path_owner);
                        //Bitmap medicalEqpmntImageSupplr = decodeBase64(str_trn_image_supplr);
                        imgVw_areaSupImg.setImageURI(Uri.parse(img_path_owner));
                    }
                }

                trn_image_supplr.close();


//                Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
//                trn_docAdapt.open();
//                Cursor mCr_trn_doc = trn_docAdapt.fetchByDoc_Id_Type(enrollId, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);
//                if (mCr_trn_doc.getCount() > 0) {
//                    for (int int_trn_image = 0; int_trn_image < mCr_trn_doc.getCount(); int_trn_image++) {
//                        mCr_trn_doc.moveToPosition(int_trn_image);
//
//
//                        String str_trn_docStr = mCr_trn_doc.getString(mCr_trn_doc.getColumnIndex("" + BusinessAccessLayer.DOC_ENCRYPTED_DATA + ""));
//
//                        insDetails_InstallerDocumentEncodedStr = str_trn_docStr;
//                    }
//
//                }
//                trn_docAdapt.close();


                Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
                trn_docAdapt.open();
                Cursor mCr_trn_doc = trn_docAdapt.fetchByDoc_Id_Type(enrollId, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);
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
                        medicalDocument.setTrn_doc_filepath_filname("");
                        installerDocumentArray.add(medicalDocument);

//                        documentEncodedStr = str_trn_docStr;


//                        Bitmap medicalEqpmntImage = decodeBase64(str_trn_docStr);
//                        eTxt_attDoc.setImageBitmap(medicalEqpmntImage);
//                        bean.setImg_encrypted_data(str_trn_image);
                    }

                }

                trn_docAdapt.close();
                if (installerDocumentArray.size() > 0) {
                    loadDocumentFileInstaller();
                }


                Trn_Documents trn_docOwnerAdapt = new Trn_Documents(getActivity());
                trn_docAdapt.open();
                ownerDocumentArray.clear();
                Cursor mCr_trn_doc_owner = trn_docOwnerAdapt.fetchByDoc_Id_Type(enrollId, BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT);

                System.out.println("mCr_trn_doc_owner" + mCr_trn_doc_owner.getCount());

                if (mCr_trn_doc_owner.getCount() > 0) {
                    for (int int_trn_image = 0; int_trn_image < mCr_trn_doc_owner.getCount(); int_trn_image++) {
                        mCr_trn_doc_owner.moveToPosition(int_trn_image);
                        Bean medicalDocument = new Bean();

                        String str_trn_docId = mCr_trn_doc_owner.getString(mCr_trn_doc_owner.getColumnIndex("" + BusinessAccessLayer.DOC_DOC_ID + ""));
                        System.out.println("str_trn_docId:" + str_trn_docId);
                        medicalDocument.setTrn_doc_doc_id(str_trn_docId);
                        String str_trn_docEncrypedData = mCr_trn_doc_owner.getString(mCr_trn_doc_owner.getColumnIndex("" + BusinessAccessLayer.DOC_ENCRYPTED_DATA + ""));
                        medicalDocument.setTrn_doc_encrypted_data(str_trn_docEncrypedData);

                        String str_trn_docType = mCr_trn_doc_owner.getString(mCr_trn_doc_owner.getColumnIndex("" + BusinessAccessLayer.DOC_TYPE + ""));
                        medicalDocument.setTrn_doc_type(str_trn_docType);

                        String str_trn_docCreatedBy = mCr_trn_doc_owner.getString(mCr_trn_doc_owner.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                        medicalDocument.setTrn_created_by(str_trn_docCreatedBy);

                        String str_trn_docCreatedOn = mCr_trn_doc_owner.getString(mCr_trn_doc_owner.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                        medicalDocument.setTrn_created_on(str_trn_docCreatedOn);
                        medicalDocument.setTrn_doc_filepath_filname("");
                        ownerDocumentArray.add(medicalDocument);

//                        documentEncodedStr = str_trn_docStr;


//                        Bitmap medicalEqpmntImage = decodeBase64(str_trn_docStr);
//                        eTxt_attDoc.setImageBitmap(medicalEqpmntImage);
//                        bean.setImg_encrypted_data(str_trn_image);
                    }

                }

                trn_docOwnerAdapt.close();
                if (ownerDocumentArray.size() > 0) {
                    loadDocumentFileOwner();
                }


            }
            inst_enrollAdapt.close();
        } else {
//           UserInterfaceLayer.alert( getActivity(),"No installation details ",1 );
            System.out.println("BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID  else:::" + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
            btn_fab_insDetailSave.setImageResource(R.drawable.save);
            btn_fab_insDetailDelete.setVisibility(View.GONE);
            lLayout_id_createdBy.setVisibility(View.GONE);
            lLayout_id_createdOn.setVisibility(View.GONE);
            // BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG = 0;
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


    private void getViewCasting(View rootView) {
        lLayout_id_createdBy = (LinearLayout) rootView.findViewById(R.id.lLayout_id_createdBy);
        lLayout_id_createdOn = (LinearLayout) rootView.findViewById(R.id.lLayout_id_createdOn);
        lLayout_instlDtls_attDocList_installer = (LinearLayout) rootView.findViewById(R.id.lLayout_instlDtls_attDocList_installer);
        lLayout_instlDtls_attDocList_owner = (LinearLayout) rootView.findViewById(R.id.lLayout_instlDtls_attDocList_owner);
        txtVw_compInsName = (TextView) rootView.findViewById(R.id.txtVw_compInsName);
        txtVw_engName = (TextView) rootView.findViewById(R.id.txtVw_engName);
        txtVw_insDate = (TextView) rootView.findViewById(R.id.txtVw_insDate);
        txtVw_gpsLoc = (TextView) rootView.findViewById(R.id.txtVw_gpsLoc);
        txtVw_nrbPhoNo = (TextView) rootView.findViewById(R.id.txtVw_nrbPhoNo);
        txtVw_insNotes = (TextView) rootView.findViewById(R.id.txtVw_insNotes);
        txtVw_insImg = (TextView) rootView.findViewById(R.id.txtVw_insImg);
        txtVw_insAttDoc = (TextView) rootView.findViewById(R.id.txtVw_insAttDoc);
        txtVw_insArea = (TextView) rootView.findViewById(R.id.txtVw_insArea);
        txtVw_supName = (TextView) rootView.findViewById(R.id.txtVw_supName);
        txtVw_supPhnNo = (TextView) rootView.findViewById(R.id.txtVw_supPhnNo);
        txtVw_areaSupImg = (TextView) rootView.findViewById(R.id.txtVw_areaSupImg);
        txtVw_supAttDoc = (TextView) rootView.findViewById(R.id.txtVw_supAttDoc);
        txtVw_id_createdBy = (TextView) rootView.findViewById(R.id.txtVw_id_createdBy);
        txtVw_id_createdOn = (TextView) rootView.findViewById(R.id.txtVw_id_createdOn);

        eTxt_compInsName = (EditText) rootView.findViewById(R.id.eTxt_compInsName);
        eTxt_engName = (EditText) rootView.findViewById(R.id.eTxt_engName);
        eTxt_insDate = (EditText) rootView.findViewById(R.id.eTxt_insDate);
        eTxt_gpsLoc = (EditText) rootView.findViewById(R.id.eTxt_gpsLoc);
        eTxt_nrbPhnNo = (EditText) rootView.findViewById(R.id.eTxt_nrbPhnNo);
        eTxt_insNotes = (EditText) rootView.findViewById(R.id.eTxt_insNotes);
        eTxt_insAttDoc = (EditText) rootView.findViewById(R.id.eTxt_insAttDoc);
        eTxt_insArea = (EditText) rootView.findViewById(R.id.eTxt_insArea);
        eTxt_supName = (EditText) rootView.findViewById(R.id.eTxt_supName);
        eTxt_supPhnNo = (EditText) rootView.findViewById(R.id.eTxt_supPhnNo);
        eTxt_supAttDoc = (EditText) rootView.findViewById(R.id.eTxt_supAttDoc);
        eTxt_id_createdBy = (EditText) rootView.findViewById(R.id.eTxt_id_createdBy);
        eTxt_id_createdOn = (EditText) rootView.findViewById(R.id.eTxt_id_createdOn);

        txtVw_compInsName.setTypeface(calibri_typeface);
        txtVw_engName.setTypeface(calibri_typeface);
        txtVw_insDate.setTypeface(calibri_typeface);
        txtVw_gpsLoc.setTypeface(calibri_typeface);
        txtVw_nrbPhoNo.setTypeface(calibri_typeface);
        txtVw_insNotes.setTypeface(calibri_typeface);
        txtVw_insImg.setTypeface(calibri_typeface);
        txtVw_insAttDoc.setTypeface(calibri_typeface);
        txtVw_insArea.setTypeface(calibri_typeface);
        txtVw_supName.setTypeface(calibri_typeface);
        txtVw_supPhnNo.setTypeface(calibri_typeface);
        txtVw_areaSupImg.setTypeface(calibri_typeface);
        txtVw_supAttDoc.setTypeface(calibri_typeface);
        txtVw_id_createdBy.setTypeface(calibri_typeface);
        txtVw_id_createdOn.setTypeface(calibri_typeface);

        eTxt_compInsName.setTypeface(calibri_typeface);
        eTxt_engName.setTypeface(calibri_typeface);
        eTxt_insDate.setTypeface(calibri_typeface);
        eTxt_gpsLoc.setTypeface(calibri_typeface);
        eTxt_nrbPhnNo.setTypeface(calibri_typeface);
        eTxt_insNotes.setTypeface(calibri_typeface);
        eTxt_insAttDoc.setTypeface(calibri_typeface);
        eTxt_insArea.setTypeface(calibri_typeface);
        eTxt_supName.setTypeface(calibri_typeface);
        eTxt_supPhnNo.setTypeface(calibri_typeface);
        eTxt_supAttDoc.setTypeface(calibri_typeface);
        eTxt_id_createdBy.setTypeface(calibri_typeface);
        eTxt_id_createdOn.setTypeface(calibri_typeface);


        String comName = "Company Name";
        String locationCode = "Location Code";

        String asterisk = "<font color='#EE0000'> *</font>";


        txtVw_compInsName.setText(Html.fromHtml(comName + asterisk));
        txtVw_gpsLoc.setText(Html.fromHtml(locationCode + asterisk));


        eTxt_compInsName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_engName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_gpsLoc.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_insNotes.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_insArea.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_supName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


        txtVw_active = (TextView) rootView.findViewById(R.id.txtVw_active);
        txtVw_activeYesNo = (TextView) rootView.findViewById(R.id.txtVw_activeYesNo);

        switch_active = (Switch) rootView.findViewById(R.id.switch_active);

        txtVw_active.setTypeface(calibri_typeface);
        txtVw_activeYesNo.setTypeface(calibri_typeface);
        switch_active.setTypeface(calibri_typeface);

        imgVw_insImg = (ImageView) rootView.findViewById(R.id.imgVw_insImg);
        imgVw_areaSupImg = (ImageView) rootView.findViewById(R.id.imgVw_areaSupImg);

//        btn_insDetailCancel = (Button) rootView.findViewById(R.id.btn_insDetailCancel);
//        btn_insDetailSave = ( Button ) rootView.findViewById( R.id.btn_insDetailSave );
        lLayout_insDetails_eqpmnt_enroll_id = (LinearLayout) rootView.findViewById(R.id.lLayout_insDetails_eqpmnt_enroll_id);
        txtVw_insDetails_eqpmnt_enroll_id = (TextView) rootView.findViewById(R.id.txtVw_insDetails_eqpmnt_enroll_id);
        btn_fab_insDetailSave = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_insDetailSave);
        btn_fab_insDetailDelete = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_insDetailDelete);
        btn_fab_MedInstClear = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_MedInstClear);

        // updated
        btn_attach_document_installer = (Button) rootView.findViewById(R.id.btn_attach_document_installer);
        btn_attach_document_owner = (Button) rootView.findViewById(R.id.btn_attach_document_owner);

        txtVw_insDetails_eqpmnt_enroll_id.setTypeface(calibri_typeface);
        btn_attach_document_installer.setTypeface(calibri_bold_typeface);
        btn_attach_document_owner.setTypeface(calibri_bold_typeface);

        lv_installerDocument = (ListView) rootView.findViewById(R.id.lv_installerDocument);
        lv_ownerDocument = (ListView) rootView.findViewById(R.id.lv_ownerDocument);

        getViewClickEvents();
    }

    private void getViewClickEvents() {

        btn_attach_document_installer.setOnClickListener(this);
        btn_attach_document_owner.setOnClickListener(this);
        btn_fab_MedInstClear.setOnClickListener(this);

        switch_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {

                    str_swt_IsActive = "Y";
                    txtVw_activeYesNo.setText("Yes");

                } else {
                    str_swt_IsActive = "N";
                    txtVw_activeYesNo.setText("No");

                }
            }
        });

        eTxt_compInsName.setOnClickListener(this);
        eTxt_engName.setOnClickListener(this);
        eTxt_insDate.setOnClickListener(this);
        eTxt_gpsLoc.setOnClickListener(this);
        eTxt_nrbPhnNo.setOnClickListener(this);
        eTxt_insNotes.setOnClickListener(this);
        eTxt_insAttDoc.setOnClickListener(this);
        eTxt_supName.setOnClickListener(this);
        eTxt_supPhnNo.setOnClickListener(this);
        eTxt_supAttDoc.setOnClickListener(this);
        eTxt_insArea.setOnClickListener(this);

        imgVw_insImg.setOnClickListener(this);
        imgVw_areaSupImg.setOnClickListener(this);

//        btn_insDetailCancel.setOnClickListener(this);
        btn_fab_insDetailSave.setOnClickListener(this);
        btn_fab_insDetailDelete.setOnClickListener(this);
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

    private boolean checkInstallation(String viewMedicalEquipmentEnrollId) {

        Trn_Installation_Enrollment trnInstall = new Trn_Installation_Enrollment(getActivity());
        trnInstall.open();
        Cursor insVal = trnInstall.fetchByInst_EquipmentEnroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

        if (insVal.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eTxt_insDate:
                hideKeyBoard();
                datepicker();

                break;
            case R.id.btn_fab_insDetailSave:
                isAddedNew = true;
                if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {

                    if (eTxt_compInsName.getText().toString().trim().length() == 0) {
                        showValidationDialog(getActivity(), "Enter company name");

                    } else if (eTxt_gpsLoc.getText().toString().trim().length() == 0) {
                        showValidationDialog(getActivity(), "Enter location code");

                    } else {
                        /*if (isPreview == true) {
                            showValidationDialog(getActivity(), "Please verify all data before you submit");
                            btn_fab_MedInstClear.setVisibility(View.VISIBLE);
                            isPreview = false;
                        } else {*/
                        insertInstallationDetails();
                          /*  btn_fab_MedInstClear.setVisibility(View.GONE);
                            isPreview = true;
                        }*/
                    }
                } else {
                    //update fun
                    if (eTxt_compInsName.getText().toString().trim().length() == 0) {
                        showValidationDialog(getActivity(), "Enter company name");

                    } else if (eTxt_gpsLoc.getText().toString().trim().length() == 0) {
                        showValidationDialog(getActivity(), "Enter location code");

                    } else {
                        boolean isInstallationExists = checkInstallation(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
                        if (isInstallationExists == true) {
                            updateInstallationDetails();
                        } else {
                            insertInstallationDetails();
                        }


                    }
                }

                break;
            case R.id.btn_fab_insDetailDelete:
                isAddedNew = false;
                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
//                delete_installation_details();
                break;

//            case R.id.btn_insDetailCancel:
//                Intent i = new Intent(getActivity(), DashBoardActivity.class);
//                startActivity(i);
////                finish();
//                break;

            case R.id.imgVw_insImg:
                isCaptureImg = 0;
                hideKeyBoard();
                uploadImage();

                break;

            case R.id.imgVw_areaSupImg:
                isCaptureImg = 1;
                hideKeyBoard();
                uploadImage();
                break;
            case R.id.eTxt_insAttDoc:
                isCaptureDocument = 0;
                hideKeyBoard();
                Intent intent_instlrAttachDoc = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent_instlrAttachDoc, BusinessAccessLayer.FILE_ATTACHMENT);

                break;
            case R.id.eTxt_supAttDoc:
                isCaptureDocument = 1;
                hideKeyBoard();
                Intent intent_supplrAttachDoc = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent_supplrAttachDoc, BusinessAccessLayer.FILE_ATTACHMENT);
                break;

            case R.id.btn_attach_document_installer:
                isCaptureDocument = 0;
                hideKeyBoard();
                Intent intent_instllrAttachDoc = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent_instllrAttachDoc, BusinessAccessLayer.FILE_ATTACHMENT);


                break;
            case R.id.btn_attach_document_owner:
                isCaptureDocument = 1;
                hideKeyBoard();

                Intent intent_suppllrAttachDoc = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intent_suppllrAttachDoc, BusinessAccessLayer.FILE_ATTACHMENT);
                break;

            case R.id.btn_fab_MedInstClear:
                hideKeyBoard();
                clearAllFields();
                break;

            default:
                break;
        }

    }


    private void delete_installation_details() {

        Trn_Installation_Enrollment install_enroll = new Trn_Installation_Enrollment(getActivity());
        install_enroll.open();
        Cursor del_cursor_inst = install_enroll.update_flag_By_Eq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        install_enroll.close();

        Trn_Images trn_image = new Trn_Images(getActivity());
        trn_image.open();
        Cursor img_cursor = trn_image.updateBy_Img_Id_Type(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_IMG);
        trn_image.close();

        Trn_Images trn_image_owner = new Trn_Images(getActivity());
        trn_image_owner.open();
        Cursor img_cursor_owner = trn_image_owner.updateBy_Img_Id_Type(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_IMAGE);
        trn_image_owner.close();

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();
        Cursor doc_cursor = trn_docAdapt.updateByDoc_Id_Type(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);
        trn_docAdapt.close();

        Trn_Equipment_Enroll_Accessories trn_equip_enroll_access = new Trn_Equipment_Enroll_Accessories(getActivity());
        trn_equip_enroll_access.open();
        Cursor acc_cursor = trn_equip_enroll_access.update_flag_By_Eq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        trn_equip_enroll_access.close();

        Trn_Equipment_Enrollment equip_enroll = new Trn_Equipment_Enrollment(getActivity());
        equip_enroll.open();
        Cursor del_cursor = equip_enroll.updateBy_flag_Eq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        equip_enroll.close();


        Trn_Documents trn_docAdaptOwn = new Trn_Documents(getActivity());
        trn_docAdaptOwn.open();
        Cursor doc_cursorOwn = trn_docAdaptOwn.updateByDoc_Id_Type(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT);
        trn_docAdaptOwn.close();


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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        // create a new String using the date format we want
        mFormattedDatefrom = df.format(myCalendar.getTime());
        // create a date "formatter" (the date format we want)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        Date date1 = sdf.parse(getCurrentDate());
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
     * @Created_On 10-12-2015 at 03:41:46 pm
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

                    picUri = Uri.fromFile(f);

//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                    intent.putExtra("return-data", true);

                    startActivityForResult(intent, BusinessAccessLayer.PICK_FROM_CAMERA);
//
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

/*

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {

        if ( requestCode == 1 ) {
            Bundle extras = data.getExtras();
            if ( extras != null ) {
                Bitmap photo = extras.getParcelable( "data" );
                imgVw_insImg.setImageBitmap( photo );

            }
        }

        if ( requestCode == 2 ) {
            Bundle extras1 = data.getExtras();
            if ( extras1 != null ) {
                Bitmap photo1 = extras1.getParcelable( "data" );
                imgVw_areaSupImg.setImageBitmap( photo1 );

            }
        }
    }

*/

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
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error accessing file: " + e.getMessage());
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
//            if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA) {
//
//
//                // capture image from camera for installer image
//                if (isCaptureImg == 0) {
//                    File f = new File(Environment.getExternalStorageDirectory().toString());
//
//                    for (File temp : f.listFiles()) {
//
//                        if (temp.getName().equals("temp.jpg")) {
//
//                            f = temp;
//
//                            break;
//
//                        }
//
//                    }
//
//                    try {
//
//                        Bitmap bitmap;
//
//                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                                bitmapOptions);
//                        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                        Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//
//                        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                        String[] extension = fileName.split("\\.");
//                        String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                        String new_file_name = n_file_name + "." + extension[1];
//
//                        String tempImg = storeImage(scale, new_file_name);
//
//                        insDetails_InstallermageEncodedStr = new_file_name;
//
//                        //        insDetails_InstallermageEncodedStr = encodeTobase64(scale);
////                        uploadImageStr = encodeTobase64(scale);
//
//                        imgVw_insImg.setImageURI(Uri.parse(tempImg));
//
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//
//                    }
//                } else {
//                    // capture image from camera for owner or supervisior  image
//
//
//                    File f = new File(Environment.getExternalStorageDirectory().toString());
//
//                    for (File temp : f.listFiles()) {
//
//                        if (temp.getName().equals("temp.jpg")) {
//
//                            f = temp;
//
//                            break;
//
//                        }
//
//                    }
//
//                    try {
//
//                        Bitmap bitmap;
//
//                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                                bitmapOptions);
//                        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                        Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//
//                        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                        String[] extension = fileName.split("\\.");
//                        String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                        String new_file_name = n_file_name + "." + extension[1];
//
//                        String tempImg = storeImage(scale, new_file_name);
//                        insDetails_OwnermageEncodedStr = new_file_name;
//                        //     insDetails_OwnermageEncodedStr = encodeTobase64(scale);
//
////                        uploadImageStr = encodeTobase64(scale);
//
//                        imgVw_areaSupImg.setImageURI(Uri.parse(tempImg));
//
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//
//                    }
//                }
//
//
//            } else if (requestCode == BusinessAccessLayer.PICK_FROM_GALLERY) {
//
//                if (isCaptureImg == 0) {
//                    // capture image from gallery  for installer  image
//
//                    Uri selectedImage = data.getData();
//
//                    String[] filePath = {MediaStore.Images.Media.DATA};
//
//                    Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
//
//                    c.moveToFirst();
//
//                    int columnIndex = c.getColumnIndex(filePath[0]);
//
//                    String picturePath = c.getString(columnIndex);
//
//                    c.close();
//
//                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                    int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//                    Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
//
//
//                    File file = new File(picturePath);
//                    String file_name = file.getName().toString();
//                    String[] extension = file_name.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String fileDirectoryPath = storeImage(scaled, new_file_name);
//
//                    System.out.println("picturePath:::" + picturePath);
////                Log.w("path of image from gallery......******************.........", picturePath + "");
//
//                    insDetails_InstallermageEncodedStr = new_file_name;
//
////                    uploadImageStr = encodeTobase64(scaled);
//                    imgVw_insImg.setImageURI(Uri.parse(fileDirectoryPath));
//                } else {
//
//                    // capture image from gallery for owner or supervisior  image
//
//                    Uri selectedImage = data.getData();
//
//                    String[] filePath = {MediaStore.Images.Media.DATA};
//
//                    Cursor c = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
//
//                    c.moveToFirst();
//
//                    int columnIndex = c.getColumnIndex(filePath[0]);
//
//                    String picturePath = c.getString(columnIndex);
//
//                    c.close();
//
//                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                    int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//                    Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
//                    System.out.println("picturePath:::" + picturePath);
//
//
//                    File file = new File(picturePath);
//                    String file_name = file.getName().toString();
//                    String[] extension = file_name.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String fileDirectoryPath = storeImage(scaled, new_file_name);
//
//
//                    System.out.println("picturePath:::" + picturePath);
//
//
////                Log.w("path of image from gallery......******************.........", picturePath + "");
//
//
////                    uploadImageStr = encodeTobase64(scaled);
//                    insDetails_OwnermageEncodedStr = new_file_name;
//                    imgVw_areaSupImg.setImageURI(Uri.parse(fileDirectoryPath));
//
//                }
//            } else if (requestCode == BusinessAccessLayer.FILE_ATTACHMENT) {
//
//                if (isCaptureDocument == 0) {
//                    curFileName = data.getStringExtra("GetFileName");
//
//                    String file_name = curFileName;
//                    String[] extension = file_name.split("\\.");
//                    if (extension.length > 1) {
//                        getFilePath = data.getStringExtra("GetPath");
//                        String pathPlusName = getFilePath + "/" + curFileName;
//                        file_upload = new File(pathPlusName);
//                        long fileSize = file_upload.length() / 1024;
//
//                        if (fileSize > 2048) {
//
//                            Toast.makeText(getActivity(), "Please attach file less than 2 MB", Toast.LENGTH_SHORT).show();
//                        } else {
//                            System.out.println("path   :pathPlusName" + pathPlusName);
//                            eTxt_insAttDoc.setText(pathPlusName);
//
//
//                            byte[] docBytes;
//
//                            //   String docString = Base64.encodeToString(docBytes, Base64.DEFAULT);
//                            String docString = "";
//
//                            Bean beanObjDocument = new Bean();
//                            beanObjDocument.setTrn_doc_name(BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);
//                            beanObjDocument.setTrn_doc_type(curFileName);
//                            beanObjDocument.setTrn_doc_encrypted_data(docString);
//                            beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);
//
//                            installerDocumentArray.add(beanObjDocument);
//                            System.out.println("pathPlusName:" + pathPlusName);
//
//                            System.out.println("installerDocumentArray:" + installerDocumentArray.size());
//                            beanObjDocument = null;
//
//                            loadDocumentFileInstaller();
//                        }
//
////                    installerDocByte = convertFileToByteArray( file );
////
////                    String documentEncodedInstaller = Base64.encodeToString( installerDocByte, Base64.DEFAULT );
////                    if ( documentEncodedInstaller.length() > 0 ) {
////                        insDetails_InstallerDocumentEncodedStr = documentEncodedInstaller;
////                    } else {
////                        insDetails_InstallerDocumentEncodedStr = "";
////                    }
//                    } else {
//                        Toast.makeText(getContext(), "File format is not valid", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//                    curFileName = data.getStringExtra("GetFileName");
//
//                    String file_name = curFileName;
//                    String[] extension = file_name.split("\\.");
//                    if (extension.length > 1) {
//                        getFilePath = data.getStringExtra("GetPath");
//                        String pathPlusName = getFilePath + "/" + curFileName;
//                        file_upload = new File(pathPlusName);
////                    eTxt_supAttDoc.setText( pathPlusName );
//                        long fileSize = file_upload.length() / 1024;
//
//                        if (fileSize > 2048) {
//
//                            Toast.makeText(getActivity(), "Please attach file less than 2 MB", Toast.LENGTH_SHORT).show();
//                        } else {
//
//                            byte[] docBytes;
//                            String docString = "";
//                            try {
//                                docString = "";
//                                // docString = Base64.encodeToString(docBytes, Base64.DEFAULT);
//                            } catch (Exception ex) {
//                                System.out.println("" + ex.getMessage());
//                            }
//
//
//                            Bean beanObjDocument = new Bean();
//                            beanObjDocument.setTrn_doc_name(BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT);
//                            beanObjDocument.setTrn_doc_type(curFileName);
//                            beanObjDocument.setTrn_doc_encrypted_data(docString);
//                            beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);
//
//                            ownerDocumentArray.add(beanObjDocument);
//
//
//                            beanObjDocument = null;
//
//                            loadDocumentFileOwner();
//                        }
//
////                    supllierDocByte = convertFileToByteArray( file );
////
////                    String documentEncodedSupplier = Base64.encodeToString( supllierDocByte, Base64.DEFAULT );
////                    if ( documentEncodedSupplier.length() > 0 ) {
////                        insDetails_OwnerDocumentEncodedStr = documentEncodedSupplier;
////                    } else {
////                        insDetails_OwnerDocumentEncodedStr = "";
////                    }
//                    } else {
//                        Toast.makeText(getContext(), "File format is not valid", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//
//
//            }
//        }
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == getActivity().RESULT_OK) {

        if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA && resultCode == getActivity().RESULT_OK) {
            System.out.println("isCaptureImg in camera :" + isCaptureImg);

            // capture image from camera for installer image
            if (isCaptureImg == 0) {

                CropImage(picUri);

                f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp_equmed.jpg")) {

                        f = temp;

                        break;

                    }

                }

//                    try {
//
//                        Bitmap bitmap;
//
//                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                                bitmapOptions);
//                        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                        Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//
//                        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                        String[] extension = fileName.split("\\.");
//                        String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                        String new_file_name = n_file_name + "." + extension[1];
//
//                        String tempImg = storeImage(scale, new_file_name);
//
//                        insDetails_InstallermageEncodedStr = new_file_name;
//
//                //        insDetails_InstallermageEncodedStr = encodeTobase64(scale);
////                        uploadImageStr = encodeTobase64(scale);
//
//                       imgVw_insImg.setImageURI(Uri.parse(tempImg));
//
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//
//                    }

            } else {
                // capture image from camera for owner or supervisior  image


                f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp_equmed.jpg")) {

                        f = temp;

                        break;

                    }

                }
                CropImage(picUri);
//                    try {
//
//                        Bitmap bitmap;
//
//                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                                bitmapOptions);
//                        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                        Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//
//                        String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                        String[] extension = fileName.split("\\.");
//                        String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                        String new_file_name = n_file_name + "." + extension[1];
//
//                        String tempImg = storeImage(scale, new_file_name);
//                        insDetails_OwnermageEncodedStr = new_file_name;
//                   //     insDetails_OwnermageEncodedStr = encodeTobase64(scale);
//
////                        uploadImageStr = encodeTobase64(scale);
//
//                        imgVw_areaSupImg.setImageURI(Uri.parse(tempImg));
//
//
//                    } catch (Exception e) {
//
//                        e.printStackTrace();
//
//                    }
            }


        } else if (requestCode == BusinessAccessLayer.PICK_FROM_GALLERY && resultCode == getActivity().RESULT_OK) {
            System.out.println("isCaptureImg in PICK_FROM_GALLERY :" + isCaptureImg);
            if (isCaptureImg == 0) {
                // capture image from gallery  for installer  image

//                    Uri selectedImage = data.getData();
                picUri = data.getData();

                CropImage(picUri);

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getActivity().getContentResolver().query(picUri, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

//                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                    int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//                    Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);


                file = new File(picturePath);

//                    String file_name = file.getName().toString();
//                    String[] extension = file_name.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String fileDirectoryPath = storeImage(scaled, new_file_name);
//
//                    System.out.println("picturePath:::" + picturePath);
////                Log.w("path of image from gallery......******************.........", picturePath + "");
//
//                    insDetails_InstallermageEncodedStr = new_file_name;
//
////                    uploadImageStr = encodeTobase64(scaled);
//                    imgVw_insImg.setImageURI(Uri.parse(fileDirectoryPath));
            } else {

                // capture image from gallery for owner or supervisior  image

//                    Uri selectedImage = data.getData();
                picUri = data.getData();
                CropImage(picUri);
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getActivity().getContentResolver().query(picUri, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

//                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                    int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//                    Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
//                    System.out.println("picturePath:::" + picturePath);


                file = new File(picturePath);


//                    String file_name = file.getName().toString();
//                    String[] extension = file_name.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String fileDirectoryPath = storeImage(scaled, new_file_name);
//
//
//                    System.out.println("picturePath:::" + picturePath);
//
//
////                Log.w("path of image from gallery......******************.........", picturePath + "");
//
//
////                    uploadImageStr = encodeTobase64(scaled);
//                    insDetails_OwnermageEncodedStr = new_file_name;
//                    imgVw_areaSupImg.setImageURI(Uri.parse(fileDirectoryPath));

            }
        } else if (requestCode == BusinessAccessLayer.CROP_IMAGE) {
            if (data != null) {

                if (isCaptureImg == 0) {
                    // get the returned data


                    Bitmap photo = null;
                    if (data.getData() == null) {
                        photo = (Bitmap) data.getExtras().get("data");
                    } else {
                        try {
                            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

//                        Bundle extras = data.getExtras();
//
//                        // get the cropped bitmap
//                        Bitmap photo = extras.getParcelable("data");


                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                    String[] extension = fileName.split("\\.");
                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_name = n_file_name + "." + extension[1];

                    String tempImg = storeImage(photo, new_file_name);

                    insDetails_InstallermageEncodedStr = new_file_name;

                    //        insDetails_InstallermageEncodedStr = encodeTobase64(scale);
//                        uploadImageStr = encodeTobase64(scale);

                    imgVw_insImg.setImageURI(Uri.parse(tempImg));
                    //   imgVw_userPhoto.setImageBitmap(photo);

                    if (f != null) {
                        // To delete original image taken by camera
                        if (f.delete()) {

                            // Toast.makeText(TakePictureDemo.class,"original image deleted...",Toast.LENGTH_SHORT).show();
                        }
                        // Common.showToast(TakePictureDemo.this,"original image deleted...");
                    }
                } else {

                    // get the returned data
                    Bitmap photo = null;
                    if (data.getData() == null) {
                        photo = (Bitmap) data.getExtras().get("data");
                    } else {
                        try {
                            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//                        Bundle extras = data.getExtras();
//
//                        // get the cropped bitmap
//                        Bitmap photo = extras.getParcelable("data");

                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                    String[] extension = fileName.split("\\.");
                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_name = n_file_name + "." + extension[1];

                    String tempImg = storeImage(photo, new_file_name);
                    insDetails_OwnermageEncodedStr = new_file_name;
                    //     insDetails_OwnermageEncodedStr = encodeTobase64(scale);

//                        uploadImageStr = encodeTobase64(scale);

                    imgVw_areaSupImg.setImageURI(Uri.parse(tempImg));

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

                if (isCaptureImg == 0) {
                    // get the returned data
                    Bitmap photo = null;
                    if (data.getData() == null) {
                        photo = (Bitmap) data.getExtras().get("data");
                    } else {
                        try {
                            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//                        Bundle extras = data.getExtras();
//
//                        // get the cropped bitmap
//                        Bitmap photo = extras.getParcelable("data");


                    String file_name = file.getName().toString();
                    String[] extension = file_name.split("\\.");
                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_name = n_file_name + "." + extension[1];

                    String fileDirectoryPath = storeImage(photo, new_file_name);

                    System.out.println("fileDirectoryPath:::" + fileDirectoryPath);
//                Log.w("path of image from gallery......******************.........", picturePath + "");

                    insDetails_InstallermageEncodedStr = new_file_name;

//                    uploadImageStr = encodeTobase64(scaled);
                    imgVw_insImg.setImageURI(Uri.parse(fileDirectoryPath));
                    if (file != null) {
                        // To delete original image taken by camera
                        if (file.delete()) {

                            // Toast.makeText(TakePictureDemo.class,"original image deleted...",Toast.LENGTH_SHORT).show();
                        }
                        // Common.showToast(TakePictureDemo.this,"original image deleted...");
                    }
                } else {
                    // get the returned data
                    Bitmap photo = null;
                    if (data.getData() == null) {
                        photo = (Bitmap) data.getExtras().get("data");
                    } else {
                        try {
                            photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
//                        Bundle extras = data.getExtras();
//                        // get the cropped bitmap
//                        Bitmap photoGallery = extras.getParcelable("data");


                    String file_name = file.getName().toString();
                    String[] extension = file_name.split("\\.");
                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_name = n_file_name + "." + extension[1];

                    String fileDirectoryPath = storeImage(photo, new_file_name);


//                Log.w("path of image from gallery......******************.........", picturePath + "");


//                    uploadImageStr = encodeTobase64(scaled);
                    insDetails_OwnermageEncodedStr = new_file_name;
                    imgVw_areaSupImg.setImageURI(Uri.parse(fileDirectoryPath));


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

            if (isCaptureDocument == 0) {
                curFileName = data.getStringExtra("GetFileName");

                if (curFileName.length() > 0) {
                    String file_name = curFileName;
                    String[] extension = file_name.split("\\.");
                    if (extension.length > 1) {
                        getFilePath = data.getStringExtra("GetPath");
                        String pathPlusName = getFilePath + "/" + curFileName;
                        file = new File(pathPlusName);
                        long fileSize = file.length() / 1024;

                        if (fileSize > 2048) {

                            Toast.makeText(getActivity(), "Please attach file less than 2 MB", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("path   :pathPlusName" + pathPlusName);
                            eTxt_insAttDoc.setText(pathPlusName);


                            byte[] docBytes;

                            //   String docString = Base64.encodeToString(docBytes, Base64.DEFAULT);
                            String docString = "";

                            Bean beanObjDocument = new Bean();
                            beanObjDocument.setTrn_doc_name(BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);
                            beanObjDocument.setTrn_doc_type(curFileName);
                            beanObjDocument.setTrn_doc_encrypted_data(docString);
                            beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);

                            installerDocumentArray.add(beanObjDocument);
                            System.out.println("pathPlusName:" + pathPlusName);

                            System.out.println("installerDocumentArray:" + installerDocumentArray.size());
                            beanObjDocument = null;

                            loadDocumentFileInstaller();
                        }

//                    installerDocByte = convertFileToByteArray( file );
//
//                    String documentEncodedInstaller = Base64.encodeToString( installerDocByte, Base64.DEFAULT );
//                    if ( documentEncodedInstaller.length() > 0 ) {
//                        insDetails_InstallerDocumentEncodedStr = documentEncodedInstaller;
//                    } else {
//                        insDetails_InstallerDocumentEncodedStr = "";
//                    }
                    } else {
                        Toast.makeText(getContext(), "File format is not valid", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }

            } else {
                curFileName = data.getStringExtra("GetFileName");

                if (curFileName.length() > 0) {
                    String file_name = curFileName;
                    String[] extension = file_name.split("\\.");
                    if (extension.length > 1) {
                        getFilePath = data.getStringExtra("GetPath");
                        String pathPlusName = getFilePath + "/" + curFileName;
                        file = new File(pathPlusName);
//                    eTxt_supAttDoc.setText( pathPlusName );
                        long fileSize = file.length() / 1024;

                        if (fileSize > 2048) {

                            Toast.makeText(getActivity(), "Please attach file less than 2 MB", Toast.LENGTH_SHORT).show();
                        } else {

                            byte[] docBytes;
                            String docString = "";
                            try {
                                docString = "";
                                // docString = Base64.encodeToString(docBytes, Base64.DEFAULT);
                            } catch (Exception ex) {
                                System.out.println("" + ex.getMessage());
                            }


                            Bean beanObjDocument = new Bean();
                            beanObjDocument.setTrn_doc_name(BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT);
                            beanObjDocument.setTrn_doc_type(curFileName);
                            beanObjDocument.setTrn_doc_encrypted_data(docString);
                            beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);

                            ownerDocumentArray.add(beanObjDocument);


                            beanObjDocument = null;

                            loadDocumentFileOwner();
                        }

//                    supllierDocByte = convertFileToByteArray( file );
//
//                    String documentEncodedSupplier = Base64.encodeToString( supllierDocByte, Base64.DEFAULT );
//                    if ( documentEncodedSupplier.length() > 0 ) {
//                        insDetails_OwnerDocumentEncodedStr = documentEncodedSupplier;
//                    } else {
//                        insDetails_OwnerDocumentEncodedStr = "";
//                    }
                    } else {
                        Toast.makeText(getContext(), "File format is not valid", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }

            }


        }
//        }

    }

    private void loadDocumentFileInstaller() {

        if (installerDocumentArray.size() > 0) {
            lv_installerDocument.setVisibility(View.VISIBLE);
            lv_installerDocument.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, installerDocumentArray.size() * 100));

            DocumentListAdapterInstaller adapter = new DocumentListAdapterInstaller(getActivity());
            lv_installerDocument.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            lv_installerDocument.setVisibility(View.GONE);
        }


    }

    private class DocumentListAdapterInstaller extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public DocumentListAdapterInstaller(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return installerDocumentArray.size();
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

            Bean beanObj = installerDocumentArray.get(position);
            int sNo = position + 1;
            holder.txtVw_documentNo.setText("Attachment " + sNo);
            holder.eTxt_medical_attDoc.setText(beanObj.getTrn_doc_type());

            holder.imgV_MedicalDocDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                        installerDocumentArray.remove(position);
                        if (installerDocumentArray.size() == 0) {
                            lv_installerDocument.setVisibility(View.GONE);
                        }
                    } else {

                        Trn_Documents trn_document = new Trn_Documents(getActivity());
                        trn_document.open();
                        Bean docBean = installerDocumentArray.get(position);

                        Cursor cursorDoc = trn_document.fetchByDoc_IdAndDocDocID(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);
                        if (cursorDoc.getCount() > 0) {

                            String doc_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                            if (doc_sync_status.equals("0")) {
                                //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                                File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/" + docBean.getTrn_doc_type());
                                tempDir.delete();

                                Cursor delete_trn_document = trn_document.deleteByDoc_Id_Type(docBean.getTrn_doc_doc_id(), BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);
                            } else {
                                File dir = new File(getActivity().getFilesDir(), "/documents/" + docBean.getTrn_doc_type());
                                dir.delete();

                                boolean update_trn_document = trn_document.update_document(docBean.getTrn_doc_doc_id(), BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), "2",
                                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                                        docBean.getTrn_created_by(), docBean.getTrn_created_on(),
                                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                            }


                        }
                        installerDocumentArray.remove(position);
                        if (installerDocumentArray.size() == 0) {
                            lv_installerDocument.setVisibility(View.GONE);
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


    private void loadDocumentFileOwner() {

        if (ownerDocumentArray.size() > 0) {
            lv_ownerDocument.setVisibility(View.VISIBLE);
            lv_ownerDocument.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, ownerDocumentArray.size() * 100));

            DocumentListAdapterOwner adapter = new DocumentListAdapterOwner(getActivity());
            lv_ownerDocument.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            lv_ownerDocument.setVisibility(View.GONE);
        }

    }

    private class DocumentListAdapterOwner extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public DocumentListAdapterOwner(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return ownerDocumentArray.size();
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

            Bean beanObj = ownerDocumentArray.get(position);
            int sNo = position + 1;
            holder.txtVw_documentNo.setText("Attachment " + sNo);
            holder.eTxt_medical_attDoc.setText(beanObj.getTrn_doc_type());

            holder.imgV_MedicalDocDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                        ownerDocumentArray.remove(position);
                        if (ownerDocumentArray.size() == 0) {
                            lv_installerDocument.setVisibility(View.GONE);
                        }
                    } else {

                        Trn_Documents trn_document = new Trn_Documents(getActivity());
                        trn_document.open();
                        Bean docBean = ownerDocumentArray.get(position);

                        Cursor cursorDoc = trn_document.fetchByDoc_IdAndDocDocID(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT);
                        if (cursorDoc.getCount() > 0) {

                            String doc_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                            if (doc_sync_status.equals("0")) {
                                //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                                File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/" + docBean.getTrn_doc_type());
                                tempDir.delete();

                                Cursor delete_trn_document = trn_document.deleteByDoc_Id_Type(docBean.getTrn_doc_doc_id(), BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT);
                            } else {
                                File dir = new File(getActivity().getFilesDir(), "/documents/" + docBean.getTrn_doc_type());
                                dir.delete();

                                boolean update_trn_document = trn_document.update_document(docBean.getTrn_doc_doc_id(), BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), "2",
                                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                                        docBean.getTrn_created_by(), docBean.getTrn_created_on(),
                                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                            }


//                            boolean update_trn_document = trn_document.update_document(docBean.getTrn_doc_doc_id(), BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), "2",
//                                    BusinessAccessLayer.SYNC_STATUS_VALUE,
//                                    docBean.getTrn_created_by(), docBean.getTrn_created_on(),
//                                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());


                        }
                        ownerDocumentArray.remove(position);
                        if (ownerDocumentArray.size() == 0) {
                            lv_ownerDocument.setVisibility(View.GONE);
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

    private void insertInstallationDetails() {


        System.out.println("insertinto ");
        System.out.println("VIEW_MEDICAL_EQUIPMENT_ENROLL_ID Test:::" + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

        String str_eTxt_insDateDef = "";
        str_eTxt_compInsName = eTxt_compInsName.getText().toString();
        str_eTxt_engName = eTxt_engName.getText().toString();
        str_eTxt_insDate = eTxt_insDate.getText().toString();
        if (str_eTxt_insDate.length() > 0) {
            str_eTxt_insDateDef = getDefaultDate(str_eTxt_insDate);
        }

        str_eTxt_gpsLoc = eTxt_gpsLoc.getText().toString();
        str_eTxt_nrbPhnNo = eTxt_nrbPhnNo.getText().toString();
        str_eTxt_insNotes = eTxt_insNotes.getText().toString();
        str_eTxt_insAttDoc = eTxt_insAttDoc.getText().toString();
        str_eTxt_insArea = eTxt_insArea.getText().toString();
        str_eTxt_supName = eTxt_supName.getText().toString();
        str_eTxt_supPhnNo = eTxt_supPhnNo.getText().toString();
        str_eTxt_supAttDoc = eTxt_supAttDoc.getText().toString();
//        str_swt_IsActive = txtVw_activeYesNo.getText().toString();

        Trn_Installation_Enrollment trnInstall = new Trn_Installation_Enrollment(getActivity());
        trnInstall.open();

        long insVal = trnInstall.insert_installation_enroll(
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                str_eTxt_compInsName,
                str_eTxt_engName,
                str_eTxt_insDateDef,
                str_eTxt_gpsLoc,
                str_eTxt_nrbPhnNo,
                str_eTxt_insNotes,
                str_eTxt_insArea,
                str_eTxt_supName,
                str_eTxt_supPhnNo,
                BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime(),
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());
        if (insVal != 0) {

            if (insDetails_InstallermageEncodedStr.length() > 0) {
                storeInstallerImage(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
            }

            if (insDetails_OwnermageEncodedStr.length() > 0) {
                storeSupplierImage(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

            }

            if (installerDocumentArray.size() > 0) {
                storeInstallerDoc();
            }

            if (ownerDocumentArray.size() > 0) {
                storeSupplierDoc();
            }

            showValidationDialog(getActivity(), "Installation details added successfully");
            exportDatabse(BusinessAccessLayer.DATABASE_NAME);

            clearAllFields();
        } else {
            showValidationDialog(getActivity(), "Installation details added failed");

        }

        Trn_Installation_Enrollment.close();


    }


    private void updateInstallationDetails() {
        String str_eTxt_insDateDef = "";
        System.out.println("VIEW_MEDICAL_EQUIPMENT_ENROLL_ID Test:::" + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        str_eTxt_compInsName = eTxt_compInsName.getText().toString();
        str_eTxt_engName = eTxt_engName.getText().toString();
        str_eTxt_insDate = eTxt_insDate.getText().toString();
        if (str_eTxt_insDate.length() > 0) {
            str_eTxt_insDateDef = getDefaultDate(str_eTxt_insDate);
        } else {
            System.out.println("str_eTxt_insDate.length:" + str_eTxt_insDate.length());
            str_eTxt_insDateDef = "";
        }

        str_eTxt_gpsLoc = eTxt_gpsLoc.getText().toString();
        str_eTxt_nrbPhnNo = eTxt_nrbPhnNo.getText().toString();
        str_eTxt_insNotes = eTxt_insNotes.getText().toString();
        str_eTxt_insAttDoc = eTxt_insAttDoc.getText().toString();
        str_eTxt_insArea = eTxt_insArea.getText().toString();
        str_eTxt_supName = eTxt_supName.getText().toString();
        str_eTxt_supPhnNo = eTxt_supPhnNo.getText().toString();
        str_eTxt_supAttDoc = eTxt_supAttDoc.getText().toString();
        str_swt_IsActive = txtVw_activeYesNo.getText().toString();
        String flag;
        if (insEnroll_syncstatus.equals("0")) {
            flag = "0";
        } else {
            flag = "1";
        }

        System.out.println("insDetails_InstallermageEncodedStr ==> " + insDetails_InstallermageEncodedStr);
        System.out.println("insDetails_OwnermageEncodedStr ==> " + insDetails_OwnermageEncodedStr);

        Trn_Installation_Enrollment trnInstall = new Trn_Installation_Enrollment(getActivity());
        trnInstall.open();
        boolean insVal = trnInstall.update_installation_enroll(
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                str_eTxt_compInsName,
                str_eTxt_engName,
                str_eTxt_insDateDef,
                str_eTxt_gpsLoc,
                str_eTxt_nrbPhnNo,
                str_eTxt_insNotes,
                str_eTxt_insArea,
                str_eTxt_supName,
                str_eTxt_supPhnNo,
                "1",
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                str_createdBy,
                str_createdOn,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());
        if (insVal == true) {

            if (insDetails_InstallermageEncodedStr.length() > 0) {
                updateInstallerImage(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
            }

            if (insDetails_OwnermageEncodedStr.length() > 0) {
                updateSupplierImage(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

            }
            System.out.println("update installerDocumentArray:" + installerDocumentArray.size());

            for (int i = 0; i < installerDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();

                Bean docBean = installerDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;
                System.out.println("docBean.getTrn_doc_filepath_filname():" + docBean.getTrn_doc_filepath_filname().toString());
                if (!docBean.getTrn_doc_filepath_filname().equalsIgnoreCase("")) {

                    Cursor cursorDoc = trn_documentAdapt.fetchByDoc_IdAndDocDocID(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT);

                    String file_name = docBean.getTrn_doc_type();
                    String[] extension = file_name.split("\\.");
                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_type = n_file_name + "." + extension[1];


                    long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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
                   /* if (cursorDoc.getCount() == 0) {
                        long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT, "", docBean.getTrn_doc_type(), BusinessAccessLayer.FLAG_VALUE,
                                BusinessAccessLayer.SYNC_STATUS_VALUE,
                                BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                                BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                    }*/
                }

                //  trn_documentAdapt.close();
            }


            for (int i = 0; i < ownerDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();

                Bean docBean = ownerDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;
                System.out.println("docBean.getTrn_doc_filepath_filname()" + docBean.getTrn_doc_filepath_filname());

                if (!docBean.getTrn_doc_filepath_filname().equalsIgnoreCase("")) {
                    Cursor cursorDoc = trn_documentAdapt.fetchByDoc_IdAndDocDocID(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT);

//                if (cursorDoc.getCount() == 0) {
//                    long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), BusinessAccessLayer.FLAG_VALUE,
//                            BusinessAccessLayer.SYNC_STATUS_VALUE,
//                            BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                            BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//                }

                    String file_name = docBean.getTrn_doc_type();
                    String[] extension = file_name.split("\\.");
                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_type = n_file_name + "." + extension[1];

                    long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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

                //trn_documentAdapt.close();
            }
//            if (insDetails_InstallerDocumentEncodedStr.length() > 0) {
//                storeInstallerDoc(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
//
//            }
//
//            if (insDetails_OwnerDocumentEncodedStr.length() > 0) {
//                storeSupplierDoc(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
//
//            }


            isAddedNew = true;
            showContactUsDialog(getActivity(), "Installation details updated successfully");

        } else {
            showValidationDialog(getActivity(), "Installation details updated failed");

        }

        Trn_Installation_Enrollment.close();


    }

    private void updateInstallerImage(String eqpmntEnrollId) {
        Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
        trn_imagesAdapt.open();

        System.out.println("ins_insImgId ==> " + ins_insImgId);

        if (ins_insImgId.equalsIgnoreCase("")) {

            Cursor trn_images_cursor = trn_imagesAdapt.fetch();

            int id_trn_images = trn_images_cursor.getCount() + 1;


            long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images,
                    eqpmntEnrollId,
                    BusinessAccessLayer.INSTALLDETAILS_INSTALLER_IMG,
                    insDetails_InstallermageEncodedStr,
                    BusinessAccessLayer.FLAG_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime(),
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());


            trn_imagesAdapt.close();
        } else {


            Cursor cur = trn_imagesAdapt.fetchByImgImg_ID(ins_insImgId, eqpmntEnrollId, BusinessAccessLayer.INSTALLDETAILS_INSTALLER_IMG);
            System.out.println("Currr count : " + cur.getCount());
            String pr_image = cur.getString(cur.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
            if (!pr_image.equals("") && !pr_image.equals(insDetails_InstallermageEncodedStr)) {
                File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + pr_image);
                File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + pr_image);

                if (tempImg.exists() == true) {
                    tempImg.delete();
                } else {
                    tempImg1.delete();
                }
            }

            boolean insert_trn_images = trn_imagesAdapt.update_image(ins_insImgId,
                    eqpmntEnrollId,
                    BusinessAccessLayer.INSTALLDETAILS_INSTALLER_IMG,
                    insDetails_InstallermageEncodedStr,
                    "1",
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    str_createdBy,
                    str_createdOn,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());


            trn_imagesAdapt.close();
        }


    }

    private void updateSupplierImage(String ticket_no_new) {
        Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
        trn_imagesAdapt.open();
        Cursor trn_images_cursor = trn_imagesAdapt.fetch();
        System.out.println("ins_ownerImgId ==> " + ins_ownerImgId);

        if (ins_ownerImgId.equalsIgnoreCase("")) {

            int id_trn_images = trn_images_cursor.getCount() + 1;
            long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images,
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                    BusinessAccessLayer.INSTALLDETAILS_OWNER_IMAGE,
                    insDetails_OwnermageEncodedStr,
                    BusinessAccessLayer.FLAG_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime(),
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());

            trn_imagesAdapt.close();
        } else {
            System.out.println("ins_ownerImgId: " + ins_ownerImgId);
            System.out.println("BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID: " + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

            Cursor cur = trn_imagesAdapt.fetchByImgImg_ID(ins_ownerImgId, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.INSTALLDETAILS_OWNER_IMAGE);
            System.out.println("Currr count : " + cur.getCount());
            String pr_image = cur.getString(cur.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
            if (!pr_image.equals("") && !pr_image.equals(insDetails_OwnermageEncodedStr)) {
                File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + pr_image);
                File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + pr_image);

                if (tempImg.exists() == true) {
                    tempImg.delete();
                } else {
                    tempImg1.delete();
                }
            }

            boolean insert_trn_images = trn_imagesAdapt.update_image(ins_ownerImgId,
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                    BusinessAccessLayer.INSTALLDETAILS_OWNER_IMAGE,
                    insDetails_OwnermageEncodedStr,
                    "1",
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    str_createdBy,
                    str_createdOn,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());
        }


        trn_imagesAdapt.close();


    }

    private void storeInstallerImage(String eqpmntEnrollId) {
        Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
        trn_imagesAdapt.open();
        Cursor trn_images_cursor = trn_imagesAdapt.fetch();

        int id_trn_images = trn_images_cursor.getCount() + 1;


        long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images,
                eqpmntEnrollId,
                BusinessAccessLayer.INSTALLDETAILS_INSTALLER_IMG,
                insDetails_InstallermageEncodedStr,
                BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime(),
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());


        trn_imagesAdapt.close();


    }


    private void storeSupplierImage(String ticket_no_new) {
        Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
        trn_imagesAdapt.open();
        Cursor trn_images_cursor = trn_imagesAdapt.fetch();

        int id_trn_images = trn_images_cursor.getCount() + 1;
        long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.INSTALLDETAILS_OWNER_IMAGE,
                insDetails_OwnermageEncodedStr,
                BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime(),
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        trn_imagesAdapt.close();


    }


    private void storeInstallerDoc() {
        for (int i = 0; i < installerDocumentArray.size(); i++) {

            Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
            trn_documentAdapt.open();
            Cursor trn_documentCursor = trn_documentAdapt.fetch();
            int id_trn_document = trn_documentCursor.getCount() + 1;

            Bean docBean = installerDocumentArray.get(i);

            String file_name = docBean.getTrn_doc_type();
            String[] extension = file_name.split("\\.");
            String n_file_name = extension[0] + "_" + getCurrentDateTime();
            String new_file_type = n_file_name + "." + extension[1];

            long insert_trn_document = trn_documentAdapt.insert_document("" + id_trn_document,
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                    BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT,
                    "",
                    new_file_type,
                    BusinessAccessLayer.FLAG_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime(),
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());


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

//            long insert_trn_document = trn_documentAdapt.insert_document("" + id_trn_document,
//                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
//                    BusinessAccessLayer.INSTALLDETAILS_INSTALLER_DOCUMENT,
//                    docBean.getTrn_doc_encrypted_data(),
//                    docBean.getTrn_doc_type(),
//                    BusinessAccessLayer.FLAG_VALUE,
//                    BusinessAccessLayer.SYNC_STATUS_VALUE,
//                    BusinessAccessLayer.mUserId,
//                    getCurrentDateWithTime(),
//                    BusinessAccessLayer.mUserId,
//                    getCurrentDateWithTime());


            trn_documentAdapt.close();
        }

    }

    private void storeSupplierDoc() {


        for (int i = 0; i < ownerDocumentArray.size(); i++) {

            Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
            trn_documentAdapt.open();
            Cursor trn_documentCursor = trn_documentAdapt.fetch();
            int id_trn_documentSupplr = trn_documentCursor.getCount() + 1;

            Bean docBean = ownerDocumentArray.get(i);


            String file_name = docBean.getTrn_doc_type();
            String[] extension = file_name.split("\\.");
            String n_file_name = extension[0] + "_" + getCurrentDateTime();
            String new_file_type = n_file_name + "." + extension[1];

            long insert_trn_document = trn_documentAdapt.insert_document("" + id_trn_documentSupplr,
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                    BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT,
                    "",
                    new_file_type,
                    BusinessAccessLayer.FLAG_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime(),
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());

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
            trn_documentAdapt.close();


//            long insert_trn_document = trn_documentAdapt.insert_document("" + id_trn_documentSupplr,
//                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
//                    BusinessAccessLayer.INSTALLDETAILS_OWNER_DOCUMENT,
//                    docBean.getTrn_doc_encrypted_data(),
//                    docBean.getTrn_doc_type(),
//                    BusinessAccessLayer.FLAG_VALUE,
//                    BusinessAccessLayer.SYNC_STATUS_VALUE,
//                    BusinessAccessLayer.mUserId,
//                    getCurrentDateWithTime(),
//                    BusinessAccessLayer.mUserId,
//                    getCurrentDateWithTime());
//
//
//            trn_documentAdapt.close();

        }
    }

    private void clearAllFields() {


        eTxt_compInsName.setText("");
        eTxt_engName.setText("");
        eTxt_insDate.setText("");
        eTxt_gpsLoc.setText("");
        eTxt_nrbPhnNo.setText("");
        eTxt_insNotes.setText("");
        eTxt_insAttDoc.setText("");
        eTxt_supName.setText("");
        eTxt_supPhnNo.setText("");
        eTxt_supAttDoc.setText("");
        eTxt_insArea.setText("");
        imgVw_insImg.setImageResource(R.drawable.userimage);
        imgVw_areaSupImg.setImageResource(R.drawable.userimage);
        installerDocumentArray.clear();
        ownerDocumentArray.clear();

        lLayout_instlDtls_attDocList_installer.setVisibility(View.GONE);
        lLayout_instlDtls_attDocList_owner.setVisibility(View.GONE);

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
        no.setVisibility(View.GONE);

        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

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

        if (isAddedNew == true) {
            no.setVisibility(View.GONE);
            yes.setText("OK");

        } else {
            no.setVisibility(View.VISIBLE);
        }
        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();

                if (isAddedNew == false) {

                    delete_installation_details();
                    clearAllFields();
                    Intent ibtObj = new Intent(ctx, ViewMedicalEquipAppCompat.class);
                    startActivity(ibtObj);

                } else {
                    exportDatabse(BusinessAccessLayer.DATABASE_NAME);
                   /* clearAllFields();
                    Intent ibtObj = new Intent(ctx, ViewMedicalEquipAppCompat.class);
                    startActivity(ibtObj);*/

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
/*    *//* * @Name captureCameraImages()
* @Type No Argument Method
* @Created_By GokulRaj K.c
* @Created_On 04-12-2015
* @Updated_By
* @Updated_On
* @Description The user can capture the image from camera and sets the image in the profile photo.
*
**//*
    private void captureCameraImagesIns() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, PICK_FROM_CAMERA);
        // ******** code for crop image
       *//* intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }*//*
    }

    private void captureCameraImagesSup() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
        startActivityForResult(intent, PICK_FROM_CAMERA1);
        // ******** code for crop image
       *//* intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PICK_FROM_CAMERA);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }*//*
    }*/


}
