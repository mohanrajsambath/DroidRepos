package org.next.equmed.uil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.Html;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import org.next.equmed.dal.Trn_Training_Details;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by next on 8/3/16.
 * Created by Muralidharan
 */

public class TrainingFragment extends UserInterfaceLayer implements View.OnClickListener {

    Typeface calibri_typeface, calibri_bold_typeface;

    TextView txtVw_trng_update, txtVw_trng_duration, txtVw_trng_date_label, txtVw_trng_serialNo, txtVw_trngDetails_hsptl_name,
            txtVw_trngDetails_label_hsptl_name, txtVw_trngDetails_eqpmnt_name, txtVw_trngDetails_label_eqpmnt_name, txtVw_trng_attach_docs, txtVw_trng_img, txtVw_trng_prvdBy,
            txtVw_trng_description, txtVw_label_trng_duration, txtVw_trng_date, txtVw_trng_hsptlName, txtVw_trng_eqpmntName, txtVw_noTrainingDetails, txtVw_trng_invoice;
    EditText eTxt_trng_prvdBy, eTxt_trng_description, eTxt_trng_duration, eTxt_trng_date, eTxt_trng_hsptlName, eTxt_trng_eqipName;
    Button btn_trng_attach_docs, btn_trng_uploadImage;

    FloatingActionButton btn_fab_trng_save, btn_fab_trng_new, btn_fab_trng_delete, btn_fab_TrainingClear;
    private int mYear;
    private int mMonth;
    private int mDay;
    int diffInDays;
    private Calendar myCalendar = Calendar.getInstance();
    private String mFormattedDatefrom = "";

    ScrollView sv_detail_trng;
    LinearLayout lLayout_trngDetails, llayout_trng_eqmnt_hsptl_name, lLayout_trng_listView, lLayout_trng_attachDocs_list, lLayout_trng_selected_photos_container;
    RelativeLayout rlayout_trainingView;
    ListView lv_trng_details, lv_trng_attachDocs;
    HorizontalScrollView hori_sv_trng_image;
    Switch switch_trng_invoice;

    String curFileName = "", getFilePath = "";
    File file_upload;
    static File writtenFile;
    static byte[] byteArray = null;
    int countVal;
    private static final String TAG = "TrainingFragment";
    boolean isAddedNew = true;
    boolean isUpdate = false;
    String generatedTrainingDetailsId = "", str_swt_IsInvoice = "Y";
   // boolean isPreview = true;

    ArrayList<Bean> viewTrainingDetailsArray = new ArrayList<>();
    ArrayList<Bean> trainingImagesArray = new ArrayList<Bean>();
    ArrayList<Bean> trainingDocumentArray = new ArrayList<Bean>();


    String str_selected_trainingId = "", str_createdBy = "", str_createdOn = "";

    LinearLayout lLayout_trng_createdBy, lLayout_trng_createdOn;
    TextView txtVw_trng_createdOn, txtVw_trng_createdBy;
    EditText eTxt_trng_createdBy, eTxt_trng_createdOn;

    private Uri picUri;
    public File f;
    public File file;

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
                R.layout.training_details_activity, container, false);

        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        BusinessAccessLayer.bug_class_name = "TrainingFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        getViewCasting(rootView);
        /*Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        BusinessAccessLayer.bug_class_name = "Training Details";*/
        isUpdate = false;
        hideAllLayouts();
        getTrainingDetailsByEquipEnrollId();
        return rootView;
    }

    // Adapter Class

    private class TrainingListAdapter extends BaseAdapter {

        private LayoutInflater l_InflaterDeviceList;

        public TrainingListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return viewTrainingDetailsArray.size();
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
                        R.layout.inflate_list_training_details, parent, false);

                holder.rLayout_inflate_trng_UpdateData = (RelativeLayout) convertView.findViewById(R.id.rLayout_inflate_trng_UpdateData);


//                TextView txtV_inflate_trng_sNo, txtV_inflate_trng_date, txtV_inflate_trng_duration;
//                ImageView imgV_inflate_trng_update;

                holder.txtV_inflate_trng_sNo = (TextView) convertView.findViewById(R.id.txtV_inflate_trng_sNo);
                holder.txtV_inflate_trng_sNo.setTypeface(calibri_bold_typeface);

                holder.txtV_inflate_trng_date = (TextView) convertView.findViewById(R.id.txtV_inflate_trng_date);
                holder.txtV_inflate_trng_date.setTypeface(calibri_typeface);

                holder.txtV_inflate_trng_providedBy = (TextView) convertView.findViewById(R.id.txtV_inflate_trng_providedBy);
                holder.txtV_inflate_trng_providedBy.setTypeface(calibri_typeface);

                holder.imgV_inflate_trng_update = (ImageView) convertView.findViewById(R.id.imgV_inflate_trng_update);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int sNo = position + 1;

            holder.txtV_inflate_trng_sNo.setText("" + sNo);
            String trng_dateFinal = "";
            String trng_date = viewTrainingDetailsArray.get(position).getTrn_training_date();
            if (trng_date.length() > 0) {
                trng_dateFinal = getShowDate(trng_date);
            }
            holder.txtV_inflate_trng_date.setText(trng_dateFinal);
//            holder.txtV_Equip_InstallDate.setText( equipmentDetails.get( position ).getEq_install_date() );
            holder.txtV_inflate_trng_providedBy.setText(viewTrainingDetailsArray.get(position).getTrn_training_provideBy());
            String syncStatus = viewTrainingDetailsArray.get(position).getSync_status();
            if (syncStatus.equals("0")) {
                holder.txtV_inflate_trng_sNo.setTextColor(Color.parseColor("#ff0000"));
            } else {
                holder.txtV_inflate_trng_sNo.setTextColor(Color.parseColor("#009933"));
            }


            holder.imgV_inflate_trng_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusinessAccessLayer.editPage = true;
                    isUpdate = true;
                    hideAllLayouts();
                    sv_detail_trng.setVisibility(View.VISIBLE);
                    lLayout_trngDetails.setVisibility(View.VISIBLE);
                    lLayout_trng_attachDocs_list.setVisibility(View.VISIBLE);
                    hideAllFabButtons();
                    btn_fab_trng_save.setVisibility(View.VISIBLE);
                    btn_fab_trng_save.setImageResource(R.drawable.edit);
                    btn_fab_trng_delete.setVisibility(View.VISIBLE);

                    str_selected_trainingId = viewTrainingDetailsArray.get(position).getTrn_training_id();
                    String str_trng_eqpmnt_name = getEquipmentInfoByEquipmentId(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);
                    String str_trng_hsptl_name = getHospitalInformationByHospitalId(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);

                    String str_selected_trng_date = viewTrainingDetailsArray.get(position).getTrn_training_date();
                    String str_selected_trng_duration = viewTrainingDetailsArray.get(position).getTrn_training_duration();
                    String str_selected_trng_desc = viewTrainingDetailsArray.get(position).getTrn_training_description();
                    String str_selected_trng_provdBy = viewTrainingDetailsArray.get(position).getTrn_training_provideBy();
                    String str_selected_trng_incoice = viewTrainingDetailsArray.get(position).getTrn_training_inVoice();

                    eTxt_trng_eqipName.setText(str_trng_eqpmnt_name);
                    eTxt_trng_hsptlName.setText(str_trng_hsptl_name);
                    if (str_selected_trng_date.length() > 0) {
                        eTxt_trng_date.setText(getShowDate(str_selected_trng_date));
                    }

                    eTxt_trng_duration.setText(str_selected_trng_duration);
                    eTxt_trng_description.setText(str_selected_trng_desc);
                    eTxt_trng_prvdBy.setText(str_selected_trng_provdBy);

                    if (str_selected_trng_incoice.equals("Y")) {
                        switch_trng_invoice.setChecked(true);
                    } else {
                        switch_trng_invoice.setChecked(false);
                    }

                    lLayout_trng_createdBy.setVisibility(View.VISIBLE);
                    lLayout_trng_createdOn.setVisibility(View.VISIBLE);

                    if (str_createdBy.equalsIgnoreCase("1")) {
                        eTxt_trng_createdBy.setText("Admin");
                    } else {
                        eTxt_trng_createdBy.setText("" + getUserNameByUserId(str_createdBy));
                    }

                    if (str_createdOn.length() > 0) {
                        eTxt_trng_createdOn.setText(getShowDate(str_createdOn));
                    }
//        EditText eTxt_trng_createdBy,eTxt_trng_createdOn;
                    loadAddedImageAndDocument(str_selected_trainingId);

                }
            });

//            holder.imgV_DeleteData.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    isAddedNew = false;
//                    selectedPostionForEquip = position;
//                    showContactUsDialog(ViewMedicalEquipAppCompat.this, BusinessAccessLayer.DELETE_MESSAGE);
//
//
//                }
//            });
            return convertView;
        }


        class ViewHolder {
            RelativeLayout rLayout_inflate_trng_UpdateData;
            TextView txtV_inflate_trng_sNo, txtV_inflate_trng_date, txtV_inflate_trng_providedBy;
            ImageView imgV_inflate_trng_update;
        }
    }

    private void loadAddedImageAndDocument(String training_id) {
        trainingImagesArray.clear();
        trainingDocumentArray.clear();
        Trn_Images trn_image = new Trn_Images(getActivity());
        trn_image.open();
        Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(training_id, BusinessAccessLayer.TRAININGDETAILS_IMAGE);

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

                trainingImagesArray.add(imgBean);
            }
        }
        Trn_Images.close();
        if (trainingImagesArray.size() > 0) {
            hori_sv_trng_image.setVisibility(View.VISIBLE);
            lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);
            loadImageArray();
        }
//                else{
//                    serviceImagesArray.clear();
//                }

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();

        Cursor mCr_trn_doc = trn_docAdapt.fetchByDoc_Id_Type(training_id, BusinessAccessLayer.TRAININGDETAILS_DOCUMENT);
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

                trainingDocumentArray.add(medicalDocument);

//                        documentEncodedStr = str_trn_docStr;


//                        Bitmap medicalEqpmntImage = decodeBase64(str_trn_docStr);
//                        eTxt_attDoc.setImageBitmap(medicalEqpmntImage);
//                        bean.setImg_encrypted_data(str_trn_image);
            }

        }

        trn_docAdapt.close();
        if (trainingDocumentArray.size() > 0) {
            loadDocumentFile();
        }
    }

    private void getTrainingDetailsByEquipEnrollId() {

        Trn_Training_Details trn_traingAdapt = new Trn_Training_Details(getActivity());
        trn_traingAdapt.open();

        Cursor mCr_trn_training = trn_traingAdapt.fetchBy_Tra_Eq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

        viewTrainingDetailsArray.clear();
        trainingImagesArray.clear();
        trainingDocumentArray.clear();

        if (mCr_trn_training.getCount() > 0) {
            for (int i = 0; i < mCr_trn_training.getCount(); i++) {
                mCr_trn_training.moveToPosition(i);

                Bean trainingBean = new Bean();

                String str_trng_trngId = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.TRAINING_ID + ""));
                str_selected_trainingId = str_trng_trngId;
                trainingBean.setTrn_training_id(str_trng_trngId);

                String str_trng_date = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.TRAINING_DATE + ""));
                trainingBean.setTrn_training_date(str_trng_date);

                String str_trng_duration = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.TRAINING_DURATION + ""));
                trainingBean.setTrn_training_duration(str_trng_duration);

                String str_trng_desc = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.TRAINING_DESCRIPTION + ""));
                trainingBean.setTrn_training_description(str_trng_desc);

                String str_trng_provdBy = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.TRAINING_PROVIDED_BY + ""));
                trainingBean.setTrn_training_provideBy(str_trng_provdBy);

                String str_trng_invoice = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.TRAINING_INVOICE + ""));
                trainingBean.setTrn_training_inVoice(str_trng_invoice);

                String str_trng_flag = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                trainingBean.setFlag(str_trng_flag);

                String str_trng_syncStatus = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                trainingBean.setSync_status(str_trng_syncStatus);

                String str_trng_createdOn = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                trainingBean.setCreated_on(str_trng_createdOn);
                str_createdOn = str_trng_createdOn;
                String str_trng_createdBy = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                trainingBean.setCreated_by(str_trng_createdBy);
                str_createdBy = str_trng_createdBy;
                String str_trng_modifiedBy = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                trainingBean.setModified_by(str_trng_modifiedBy);

                String str_trng_modifiedOn = mCr_trn_training.getString(mCr_trn_training.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                trainingBean.setModified_on(str_trng_modifiedOn);

             /*   Trn_Images trn_image = new Trn_Images(getActivity());
                trn_image.open();
                Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.TRAININGDETAILS_IMAGE);

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

                        trainingImagesArray.add(imgBean);
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
//
//                if (medicalImagesArray.size() > 0) {
//                    hori_scroll_view_eqpmnt.setVisibility(View.VISIBLE);
//                    selected_photos_container_eqpmnt.setVisibility(View.VISIBLE);
//                    loadImageArray();
//                }
//                else{
//                    medicalImagesArray.clear();
//                }

                Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
                trn_docAdapt.open();
                Cursor mCr_trn_doc = trn_docAdapt.fetchByDoc_Id_Type(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID, BusinessAccessLayer.TRAININGDETAILS_DOCUMENT);
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

                        trainingDocumentArray.add(medicalDocument);

//                        documentEncodedStr = str_trn_docStr;


//                        Bitmap medicalEqpmntImage = decodeBase64(str_trn_docStr);
//                        eTxt_attDoc.setImageBitmap(medicalEqpmntImage);
//                        bean.setImg_encrypted_data(str_trn_image);
                    }

                }

                trn_docAdapt.close();

//                if (medicalDocumentArray.size() > 0) {
//                    loadDocumentFile();
//                }*/


                viewTrainingDetailsArray.add(trainingBean);

            }
        }

        if (viewTrainingDetailsArray.size() > 0) {
            TrainingListAdapter adapter = new TrainingListAdapter(getActivity());
            lv_trng_details.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            rlayout_trainingView.setVisibility(View.VISIBLE);
            lLayout_trng_listView.setVisibility(View.VISIBLE);
            txtVw_trngDetails_eqpmnt_name.setText(getEquipmentInfoByEquipmentId(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID));
            txtVw_trngDetails_hsptl_name.setText(getHospitalInformationByHospitalId(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID));
            System.out.println("BusinessAccessLayer.editPage in getHospitalInformation if " + BusinessAccessLayer.editPage);
        } else {
            lv_trng_details.setAdapter(null);
            lLayout_trng_listView.setVisibility(View.GONE);
            txtVw_noTrainingDetails.setVisibility(View.VISIBLE);
            txtVw_noTrainingDetails.setText("No training details found");
            System.out.println("BusinessAccessLayer.editPage in getHospitalInformation else " + BusinessAccessLayer.editPage);
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

    private void getViewCasting(View rootView_training) {
        String asterisk = "<font color='#EE0000'> *</font>";
        sv_detail_trng = (ScrollView) rootView_training.findViewById(R.id.sv_detail_trng);
        lLayout_trngDetails = (LinearLayout) rootView_training.findViewById(R.id.lLayout_trngDetails);
        llayout_trng_eqmnt_hsptl_name = (LinearLayout) rootView_training.findViewById(R.id.llayout_trng_eqmnt_hsptl_name);
        lLayout_trng_listView = (LinearLayout) rootView_training.findViewById(R.id.lLayout_trng_listView);
        rlayout_trainingView = (RelativeLayout) rootView_training.findViewById(R.id.rlayout_trainingView);
        lLayout_trng_attachDocs_list = (LinearLayout) rootView_training.findViewById(R.id.lLayout_trng_attachDocs_list);
        lLayout_trng_selected_photos_container = (LinearLayout) rootView_training.findViewById(R.id.lLayout_trng_selected_photos_container);

        lv_trng_details = (ListView) rootView_training.findViewById(R.id.lv_trng_details);
        lv_trng_attachDocs = (ListView) rootView_training.findViewById(R.id.lv_trng_attachDocs);

        switch_trng_invoice = (Switch) rootView_training.findViewById(R.id.switch_trng_invoice);
        txtVw_noTrainingDetails = (TextView) rootView_training.findViewById(R.id.txtVw_noTrainingDetails);
        txtVw_trng_eqpmntName = (TextView) rootView_training.findViewById(R.id.txtVw_trng_eqpmntName);
        txtVw_trng_hsptlName = (TextView) rootView_training.findViewById(R.id.txtVw_trng_hsptlName);
        txtVw_trng_date = (TextView) rootView_training.findViewById(R.id.txtVw_trng_date);
        txtVw_trng_date.setText(Html.fromHtml(txtVw_trng_date.getText().toString() + asterisk));

        txtVw_label_trng_duration = (TextView) rootView_training.findViewById(R.id.txtVw_label_trng_duration);
        txtVw_trng_description = (TextView) rootView_training.findViewById(R.id.txtVw_trng_description);
        txtVw_trng_prvdBy = (TextView) rootView_training.findViewById(R.id.txtVw_trng_prvdBy);
        txtVw_trng_prvdBy.setText(Html.fromHtml(txtVw_trng_prvdBy.getText().toString() + asterisk));

        txtVw_trng_img = (TextView) rootView_training.findViewById(R.id.txtVw_trng_img);
        txtVw_trng_invoice = (TextView) rootView_training.findViewById(R.id.txtVw_trng_invoice);
        txtVw_trng_attach_docs = (TextView) rootView_training.findViewById(R.id.txtVw_trng_attach_docs);
        txtVw_trngDetails_label_eqpmnt_name = (TextView) rootView_training.findViewById(R.id.txtVw_trngDetails_label_eqpmnt_name);
        txtVw_trngDetails_eqpmnt_name = (TextView) rootView_training.findViewById(R.id.txtVw_trngDetails_eqpmnt_name);
        txtVw_trngDetails_label_hsptl_name = (TextView) rootView_training.findViewById(R.id.txtVw_trngDetails_label_hsptl_name);
        txtVw_trngDetails_hsptl_name = (TextView) rootView_training.findViewById(R.id.txtVw_trngDetails_hsptl_name);
        txtVw_trng_serialNo = (TextView) rootView_training.findViewById(R.id.txtVw_trng_serialNo);
        txtVw_trng_date_label = (TextView) rootView_training.findViewById(R.id.txtVw_trng_date_label);
        txtVw_trng_duration = (TextView) rootView_training.findViewById(R.id.txtVw_trng_duration);
        txtVw_trng_update = (TextView) rootView_training.findViewById(R.id.txtVw_trng_update);

        eTxt_trng_eqipName = (EditText) rootView_training.findViewById(R.id.eTxt_trng_eqipName);
        eTxt_trng_hsptlName = (EditText) rootView_training.findViewById(R.id.eTxt_trng_hsptlName);
        eTxt_trng_date = (EditText) rootView_training.findViewById(R.id.eTxt_trng_date);
        eTxt_trng_duration = (EditText) rootView_training.findViewById(R.id.eTxt_trng_duration);
        eTxt_trng_description = (EditText) rootView_training.findViewById(R.id.eTxt_trng_description);
        eTxt_trng_prvdBy = (EditText) rootView_training.findViewById(R.id.eTxt_trng_prvdBy);

        btn_trng_uploadImage = (Button) rootView_training.findViewById(R.id.btn_trng_uploadImage);
        btn_trng_attach_docs = (Button) rootView_training.findViewById(R.id.btn_trng_attach_docs);
        hori_sv_trng_image = (HorizontalScrollView) rootView_training.findViewById(R.id.hori_sv_trng_image);


//      aravinth

        lLayout_trng_createdBy = (LinearLayout) rootView_training.findViewById(R.id.lLayout_trng_createdBy);
        lLayout_trng_createdOn = (LinearLayout) rootView_training.findViewById(R.id.lLayout_trng_createdOn);
        txtVw_trng_createdBy = (TextView) rootView_training.findViewById(R.id.txtVw_trng_createdBy);
        txtVw_trng_createdOn = (TextView) rootView_training.findViewById(R.id.txtVw_trng_createdOn);
        eTxt_trng_createdBy = (EditText) rootView_training.findViewById(R.id.eTxt_trng_createdBy);
        eTxt_trng_createdOn = (EditText) rootView_training.findViewById(R.id.eTxt_trng_createdOn);
        txtVw_trng_createdBy.setTypeface(calibri_typeface);
        txtVw_trng_createdOn.setTypeface(calibri_typeface);
        eTxt_trng_createdBy.setTypeface(calibri_typeface);
        eTxt_trng_createdOn.setTypeface(calibri_typeface);
        txtVw_trng_invoice.setTypeface(calibri_typeface);


        switch_trng_invoice.setTypeface(calibri_typeface);
        txtVw_noTrainingDetails.setTypeface(calibri_bold_typeface);
        txtVw_trng_eqpmntName.setTypeface(calibri_typeface);
        txtVw_trng_hsptlName.setTypeface(calibri_typeface);
        txtVw_trng_date.setTypeface(calibri_typeface);
        txtVw_label_trng_duration.setTypeface(calibri_typeface);
        txtVw_trng_description.setTypeface(calibri_typeface);
        txtVw_trng_prvdBy.setTypeface(calibri_typeface);
        txtVw_trng_img.setTypeface(calibri_typeface);
        txtVw_trng_attach_docs.setTypeface(calibri_typeface);
        txtVw_trngDetails_label_eqpmnt_name.setTypeface(calibri_typeface);
        txtVw_trngDetails_eqpmnt_name.setTypeface(calibri_typeface);
        txtVw_trngDetails_label_hsptl_name.setTypeface(calibri_typeface);
        txtVw_trngDetails_hsptl_name.setTypeface(calibri_typeface);
        eTxt_trng_eqipName.setTypeface(calibri_typeface);
        eTxt_trng_hsptlName.setTypeface(calibri_typeface);
        eTxt_trng_date.setTypeface(calibri_typeface);
        eTxt_trng_duration.setTypeface(calibri_typeface);
        eTxt_trng_description.setTypeface(calibri_typeface);
        eTxt_trng_prvdBy.setTypeface(calibri_typeface);

        txtVw_trng_serialNo.setTypeface(calibri_bold_typeface);
        txtVw_trng_date_label.setTypeface(calibri_bold_typeface);
        txtVw_trng_duration.setTypeface(calibri_bold_typeface);
        txtVw_trng_update.setTypeface(calibri_bold_typeface);
        btn_trng_uploadImage.setTypeface(calibri_bold_typeface);
        btn_trng_attach_docs.setTypeface(calibri_bold_typeface);

//        btn_fab_trng_save,btn_fab_trng_new,btn_fab_trng_delete;
        btn_fab_trng_new = (FloatingActionButton) rootView_training.findViewById(R.id.btn_fab_trng_new);
        btn_fab_trng_save = (FloatingActionButton) rootView_training.findViewById(R.id.btn_fab_trng_save);
        btn_fab_trng_delete = (FloatingActionButton) rootView_training.findViewById(R.id.btn_fab_trng_delete);
        btn_fab_TrainingClear = (FloatingActionButton) rootView_training.findViewById(R.id.btn_fab_TrainingClear);

        btn_fab_trng_new.setOnClickListener(this);
        btn_fab_trng_save.setOnClickListener(this);
        btn_fab_trng_delete.setOnClickListener(this);
        eTxt_trng_date.setOnClickListener(this);
        btn_trng_uploadImage.setOnClickListener(this);
        btn_trng_attach_docs.setOnClickListener(this);
        btn_fab_TrainingClear.setOnClickListener(this);

        switch_trng_invoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
//                    txtVw_activeYesNo.setText("Yes");
                    str_swt_IsInvoice = "Y";

                } else {
//                    txtVw_activeYesNo.setText("No");
                    str_swt_IsInvoice = "N";
                }
            }
        });

    }


    private void hideAllLayouts() {
//        ScrollView sv_detail_trng;
//        LinearLayout lLayout_trngDetails,llayout_trng_eqmnt_hsptl_name,lLayout_trng_listView;
//        RelativeLayout rlayout_trainingView;
        sv_detail_trng.setVisibility(View.GONE);
        lLayout_trngDetails.setVisibility(View.GONE);
        lLayout_trng_createdOn.setVisibility(View.GONE);
        lLayout_trng_createdBy.setVisibility(View.GONE);
//        llayout_trng_eqmnt_hsptl_name.setVisibility(View.GONE);
        lLayout_trng_listView.setVisibility(View.GONE);
        rlayout_trainingView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fab_trng_new:
                hideKeyBoard();
                if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID.length() > 0) {
                    if (viewTrainingDetailsArray.size() >= 5) {
                        showContactUsDialog(getActivity(), "You can't add more than 5  training  per equipment");
                    } else {
                        hideAllLayouts();
                        hideAllFabButtons();
                        sv_detail_trng.setVisibility(View.VISIBLE);
                        lLayout_trngDetails.setVisibility(View.VISIBLE);
                        btn_fab_trng_save.setImageResource(R.drawable.save);
                        btn_fab_trng_save.setVisibility(View.VISIBLE);
                        txtVw_noTrainingDetails.setVisibility(View.GONE);
                        String selectedEqpmntName = getEquipmentInfoByEquipmentId(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);
                        eTxt_trng_eqipName.setText(selectedEqpmntName);
                        String hsptlName = getHospitalInformationByHospitalId(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);
                        eTxt_trng_hsptlName.setText(hsptlName);
                    }


                } else {
                    showContactUsDialog(getActivity(), "Please add equipment details before adding training details");
                }

                break;
            case R.id.btn_fab_trng_save:
                isAddedNew = true;
//                if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                if (eTxt_trng_date.getText().toString().trim().length() == 0) {
                    showValidationDialog(getActivity(), "Select training date");

                } else if (eTxt_trng_prvdBy.getText().toString().trim().length() == 0) {
                    showValidationDialog(getActivity(), "Enter who provide training");

                } else {

                    if (isUpdate == true) {
//update
                        update_training_details();
                    } else {
                       /* if (isPreview == true) {
                            showValidationDialog(getActivity(), "Please verify all data before you submit");
                            btn_fab_TrainingClear.setVisibility(View.VISIBLE);
                            isPreview = false;
                        } else {*/
                            insert_Training_Details();
                         /*   isPreview = true;
                            btn_fab_TrainingClear.setVisibility(View.GONE);
                        }*/
                    }
                }
                break;
            case R.id.btn_fab_trng_delete:
                hideKeyBoard();
                isAddedNew = false;
                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                break;
            case R.id.eTxt_trng_date:
                hideKeyBoard();
                datepicker();
                break;
            case R.id.btn_trng_uploadImage:
                hideKeyBoard();
                uploadImage();
                break;
            case R.id.btn_trng_attach_docs:
                hideKeyBoard();
                Intent intentAttach = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intentAttach, BusinessAccessLayer.FILE_ATTACHMENT);
                break;
            case R.id.btn_fab_TrainingClear:
                hideKeyBoard();
                clearAllFields();
                break;
            default:
                break;
        }

    }


    private void update_training_details() {
        String str_eTxt_trng_dateUpdate = "";
        String str_eTxt_upTrng_date = eTxt_trng_date.getText().toString().trim();
        if (str_eTxt_upTrng_date.length() > 0) {
            str_eTxt_trng_dateUpdate = getDefaultDate(str_eTxt_upTrng_date);
        }
        String str_eTxt_upTrng_duration = eTxt_trng_duration.getText().toString().trim();
        String str_eTxt_upTrng_description = eTxt_trng_description.getText().toString().trim();
        String str_eTxt_upTrng_prvdBy = eTxt_trng_prvdBy.getText().toString().trim();


        String sw_trng_invoice;
        if (switch_trng_invoice.isChecked()) {
            sw_trng_invoice = "Y";
        } else {
            sw_trng_invoice = "N";
        }

        Trn_Training_Details trn_trainingAdapt = new Trn_Training_Details(getActivity());
        trn_trainingAdapt.open();

//        Cursor cursor_trng = trn_trainingAdapt.fetch();
//        generatedTrainingDetailsId = getTrainingDetailsId(cursor_trng.getCount());

        boolean update_training_details = trn_trainingAdapt.update_training_detail(
                str_selected_trainingId,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                str_eTxt_trng_dateUpdate,
                str_eTxt_upTrng_duration,
                str_eTxt_upTrng_description,
                str_eTxt_upTrng_prvdBy,
                sw_trng_invoice,
                "1",
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                str_createdBy,
                str_createdOn,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (update_training_details == true) {

            for (int i = 0; i < trainingImagesArray.size(); i++) {

                System.out.println("serviceImagesArray insert:" + trainingImagesArray.size());


                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                trn_imagesAdapt.open();
                Cursor imGCursor = trn_imagesAdapt.fetch();

                Bean medImageBean = trainingImagesArray.get(i);
                int id_trn_images = imGCursor.getCount() + 1;
                System.out.println("medImageBean.getNew_Img_upload():" + medImageBean.getNew_Img_upload());
                if (medImageBean.getNew_Img_upload().equalsIgnoreCase("1")) {

                    System.out.println("Id 1 : " + str_selected_trainingId);
                    System.out.println("Id 3 : " + medImageBean.getTrn_img_img_id());
                    //   Cursor cursorImage = trn_imagesAdapt.fetchByImgImg_ID(generatedServiceId, medImageBean.getTrn_img_img_id(), BusinessAccessLayer.TRAININGDETAILS_IMAGE);

//                    if (cursorImage.getCount() == 0) {
                    long update_training = trn_imagesAdapt.insert_image("" + id_trn_images, str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE,
                            medImageBean.getTrn_img_encrypted_name(), BusinessAccessLayer.FLAG_VALUE,
                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//                    }

                    trn_imagesAdapt.close();
                }
            }


            for (int i = 0; i < trainingDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();

                Bean docBean = trainingDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;

                if (docBean.getTrn_doc_filepath_filname() != null) {
                    System.out.println("Document Bean array " + docBean.getTrn_doc_doc_id());

                    Cursor cursorDoc = trn_documentAdapt.fetchByDoc_IdAndDocDocID(str_selected_trainingId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.TRAININGDETAILS_DOCUMENT);
                    String file_name = docBean.getTrn_doc_type();
                    String[] extension = file_name.split("\\.");

                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_type = n_file_name + "." + extension[1];
                    long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            clearAllFields();
            isUpdate = false;
            showContactUsDialog(getActivity(), "Training updated successfully");
        } else {
            showValidationDialog(getActivity(), "Training update failed");
        }
    }

    private void hideAllFabButtons() {
        btn_fab_trng_new.setVisibility(View.GONE);
        btn_fab_trng_delete.setVisibility(View.GONE);
        btn_fab_trng_save.setVisibility(View.GONE);

    }

    private String getTrainingDetailsId(int count) {
        String imeino = setIMEIno();
        String timeStamp = getUnixTimeStamp();
        String finalId = "TRA_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }


    public String getHospitalInformationByHospitalId(String hsptlId) {

        String hospital_name = "", location_name = "";
        Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(getActivity());
        mst_hospital_enroll.open();
        Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByHospital_Id(hsptlId);

        if (mCr_mst_hospital_enroll.getCount() > 0) {
            for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                mCr_mst_hospital_enroll.moveToPosition(i);


                String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));
                location_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));

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

        return hospital_name + " / " + location_name;


    }

    public String getEquipmentInfoByEquipmentId(String eqpmntId) {

        String equipment_name = "";

        Mst_Equipment_Status mst_eqpmnt = new Mst_Equipment_Status(getActivity());
        mst_eqpmnt.open();
        Cursor mCr_mst_eqpmnt = mst_eqpmnt.fetchByEq_Id(eqpmntId);
        if (mCr_mst_eqpmnt.getCount() > 0) {
            for (int i = 0; i < mCr_mst_eqpmnt.getCount(); i++) {
                mCr_mst_eqpmnt.moveToPosition(i);


                String hospital_id = mCr_mst_eqpmnt.getString(mCr_mst_eqpmnt.getColumnIndex("" + BusinessAccessLayer.EQ_ID + ""));

                equipment_name = mCr_mst_eqpmnt.getString(mCr_mst_eqpmnt.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));


            }
            mCr_mst_eqpmnt.close();

        }

        Mst_Equipment_Status.close();

        return equipment_name;


    }

    /**
     * @param – no parameter
     * @return – no return values
     * @throws – no exception throws
     * @Type void Method
     * @Created_By Aravindhakumar.S
     * @Created_On 09-03-2016
     * @Updated_By
     * @Updated_On
     * @Description To Insert the values of Training Details
     */
    private void insert_Training_Details() {
        //To get String values from the Component
        //String str_spn_hosptList= spn_hosptList.getSelectedItem().toString();
        String str_eTxt_trng_dateInsert = "";
        String str_eTxt_trng_date = eTxt_trng_date.getText().toString().trim();
        if (str_eTxt_trng_date.length() > 0) {
            str_eTxt_trng_dateInsert = getDefaultDate(str_eTxt_trng_date);
        }
        String str_eTxt_trng_duration = eTxt_trng_duration.getText().toString().trim();
        String str_eTxt_trng_description = eTxt_trng_description.getText().toString().trim();
        String str_eTxt_trng_prvdBy = eTxt_trng_prvdBy.getText().toString().trim();

        Trn_Training_Details trn_trainingAdapt = new Trn_Training_Details(getActivity());
        trn_trainingAdapt.open();
        Cursor cursor_trng = trn_trainingAdapt.fetch();
        generatedTrainingDetailsId = getTrainingDetailsId(cursor_trng.getCount());

        long insertTrainingValue = trn_trainingAdapt.insert_training_details(generatedTrainingDetailsId, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID, str_eTxt_trng_dateInsert, str_eTxt_trng_duration,
                str_eTxt_trng_description, str_eTxt_trng_prvdBy, str_swt_IsInvoice, BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE, BusinessAccessLayer.mUserId, getCurrentDateWithTime(), BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (insertTrainingValue != 0) {


            System.out.println("Image count: " + trainingImagesArray.size());

            for (int imgI = 0; imgI < trainingImagesArray.size(); imgI++) {
                Bean bean = trainingImagesArray.get(imgI);

                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                trn_imagesAdapt.open();
                Cursor trn_images_cursor = trn_imagesAdapt.fetch();
                int imgCount = trn_images_cursor.getCount() + 1;

                long insert_trn_images = trn_imagesAdapt.insert_image("" + imgCount, generatedTrainingDetailsId, BusinessAccessLayer.TRAININGDETAILS_IMAGE, bean.getTrn_img_encrypted_name(), BusinessAccessLayer.FLAG_VALUE,
                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                trn_imagesAdapt.close();
            }

            for (int i = 0; i < trainingDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();
                Bean docBean = trainingDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;
//                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), BusinessAccessLayer.FLAG_VALUE,
//                        BusinessAccessLayer.SYNC_STATUS_VALUE,
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                String file_name = docBean.getTrn_doc_type();
                String[] extension = file_name.split("\\.");
                String n_file_name = extension[0] + "_" + getCurrentDateTime();
                String new_file_type = n_file_name + "." + extension[1];


                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedTrainingDetailsId, BusinessAccessLayer.TRAININGDETAILS_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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


            isAddedNew = true;
            showContactUsDialog(getActivity(), "Training details added successfully");

            exportDatabse(BusinessAccessLayer.DATABASE_NAME);

            clearAllFields();
        } else {
            showValidationDialog(getActivity(), "Training details added failed");
        }


    }

    private void clearAllFields() {

//        eTxt_trng_eqipName.setText("");
//        eTxt_trng_hsptlName.setText("");
        eTxt_trng_date.setText("");
        eTxt_trng_duration.setText("");
        eTxt_trng_description.setText("");
        eTxt_trng_prvdBy.setText("");

        lLayout_trng_selected_photos_container.removeAllViews();
        hori_sv_trng_image.setVisibility(View.GONE);
        lLayout_trng_selected_photos_container.setVisibility(View.GONE);
        trainingDocumentArray.clear();
        lLayout_trng_attachDocs_list.setVisibility(View.GONE);
        lv_trng_attachDocs.setVisibility(View.GONE);
        switch_trng_invoice.setChecked(false);
//        viewTrainingDetailsArray.clear();
        trainingImagesArray.clear();
        trainingDocumentArray.clear();

//        imgVw_equipImage.setImageResource(R.drawable.userimage);


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
//                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
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
//                    trainingImagesArray.add(beanObj);
//
//
//                    if (trainingImagesArray.size() > 0) {
//                        hori_sv_trng_image.setVisibility(View.VISIBLE);
//                        lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);
//
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
//                trainingImagesArray.add(beanObj);
//
//                if (trainingImagesArray.size() > 0) {
//
//                    hori_sv_trng_image.setVisibility(View.VISIBLE);
//                    lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);
//                    loadImageArray();
//
//                }
//
//
//            } else if (requestCode == BusinessAccessLayer.FILE_ATTACHMENT) {
//
//                curFileName = data.getStringExtra("GetFileName");
//
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
//                        trainingDocumentArray.add(beanObjDocument);
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
//        }
//
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {


            if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA) {

                CropImage(picUri);
                f = new File(Environment.getExternalStorageDirectory().toString());
                System.out.println("Absolute Path : " + f.getAbsolutePath());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        break;

                    }

                }

//                try {
//
//                    Bitmap bitmap = null;
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
//                    trainingImagesArray.add(beanObj);
//
//
//                    if (trainingImagesArray.size() > 0) {
//                        hori_sv_trng_image.setVisibility(View.VISIBLE);
//                        lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);
//
//                        loadImageArray();
//                    }
//
//
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//
//                }

            } else if (requestCode == BusinessAccessLayer.PICK_FROM_GALLERY) {


//                Uri selectedImage = data.getData();
                picUri = data.getData();
                CropImage(picUri);

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getActivity().getContentResolver().query(picUri, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);
                c.close();

//                Bitmap thumbnail = null;
//                thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//                Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);

                file = new File(picturePath);

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
//                trainingImagesArray.add(beanObj);
//
//                if (trainingImagesArray.size() > 0) {
//
//                    hori_sv_trng_image.setVisibility(View.VISIBLE);
//                    lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);
//                    loadImageArray();
//
//                }


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
                        trainingImagesArray.add(beanObj);


                        if (trainingImagesArray.size() > 0) {
                            hori_sv_trng_image.setVisibility(View.VISIBLE);
                            lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);

                            loadImageArray();
                        }


                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                    if (f != null) {
                        // To delete original image taken by camera
                        if (f.delete()) {

                            // Toast.makeText(TakePictureDemo.class,"original image deleted...",Toast.LENGTH_SHORT).show();
                        }
                        // Common.showToast(TakePictureDemo.this,"original image deleted...");
                    }
                }
            } else if (requestCode == BusinessAccessLayer.CROP_IMAGE) {
                if (data != null) {

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

                    trainingImagesArray.add(beanObj);

                    if (trainingImagesArray.size() > 0) {

                        hori_sv_trng_image.setVisibility(View.VISIBLE);
                        lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);
                        loadImageArray();

                    }

                    if (file != null) {
                        // To delete original image taken by camera
                        if (file.delete()) {

                            // Toast.makeText(TakePictureDemo.class,"original image deleted...",Toast.LENGTH_SHORT).show();
                        }
                        // Common.showToast(TakePictureDemo.this,"original image deleted...");
                    }
                }
            } else if (requestCode == BusinessAccessLayer.FILE_ATTACHMENT) {

                curFileName = data.getStringExtra("GetFileName");


                if(curFileName.length()>0) {
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

                            trainingDocumentArray.add(beanObjDocument);


                            beanObjDocument = null;

                            loadDocumentFile();
                        }

                    } else {
                        Toast.makeText(getContext(), "File format is not valid", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {

                }

            }
        }

    }

    private void loadImageArray() {

        lLayout_trng_selected_photos_container.removeAllViews();

        for (countVal = 0; countVal < trainingImagesArray.size(); countVal++) {

            Bean beanImgObj = trainingImagesArray.get(countVal);

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
            com.rey.material.widget.RelativeLayout backgroundLayout = new com.rey.material.widget.RelativeLayout(getActivity());
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

            lLayout_trng_selected_photos_container.addView(backgroundLayout);


            RelativeLayout.LayoutParams btnParam = new RelativeLayout.LayoutParams(40,
                    40);
            btnParam.addRule(com.rey.material.widget.RelativeLayout.ALIGN_PARENT_RIGHT, com.rey.material.widget.RelativeLayout.TRUE);
            btnParam.addRule(com.rey.material.widget.RelativeLayout.ALIGN_PARENT_TOP, com.rey.material.widget.RelativeLayout.TRUE);
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
                    Bean docBean = trainingImagesArray.get(v.getId());

                    Cursor cursorDoc = trn_document.fetchByImgImg_ID(docBean.getTrn_img_img_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE);
                    System.out.println("dfds: " + cursorDoc.getCount());

                    if (cursorDoc.getCount() > 0) {


                        String img_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                        if (img_sync_status.equals("0")) {
                            //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                            File tempDir = new File(getActivity().getFilesDir(), "/temp_images/" + docBean.getImg_encrypted_data());
                            tempDir.delete();

                            System.out.println("docBean.getTrn_img_img_id()" + docBean.getTrn_img_img_id());
                            System.out.println("generatedMedicalEquipId" + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

                            Cursor delete_trn_document = trn_document.deleteByImg_Id_Type(docBean.getTrn_img_img_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE);
                        } else {
                            File dir = new File(getActivity().getFilesDir(), "/images/" + docBean.getImg_encrypted_data());
                            dir.delete();

                            boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
                                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                                    docBean.getCreated_by(), docBean.getCreated_on(),
                                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                        }

                    }


                    trainingImagesArray.remove(v.getId());
                    System.out.println("medicalImagesArray update:" + trainingImagesArray.size());
                    if (trainingImagesArray.size() > 0) {
                        loadImageArray();
                    } else {
                        hori_sv_trng_image.setVisibility(View.GONE);
                        lLayout_trng_selected_photos_container.setVisibility(View.GONE);
                        trainingImagesArray.clear();
                    }
                }
            });


            backgroundLayout.addView(btn_update);


            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Trn_Images trn_document = new Trn_Images(getActivity());
                    trn_document.open();
                    Bean docBean = trainingImagesArray.get(v.getId());

                    Cursor cursorDoc = trn_document.fetchByImgImg_ID(docBean.getTrn_img_img_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE);
                    System.out.println("cursorDoc.getCount() " + cursorDoc.getCount());
                    if (cursorDoc.getCount() > 0) {


                        String img_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                        System.out.println("img_sync_status : " + img_sync_status);

                        if (img_sync_status.equals("0")) {
                            //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                            File tempDir = new File(getActivity().getFilesDir(), "/temp_images/" + docBean.getImg_encrypted_data());
                            tempDir.delete();

                            System.out.println("docBean.getTrn_img_img_id()" + docBean.getTrn_img_img_id());
//                            System.out.println("generatedMedicalEquipId" + generatedMedicalEquipId);

                            Cursor delete_trn_document = trn_document.deleteByImg_Id_Type(docBean.getTrn_img_img_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE);
                        } else {
                            System.out.println("asdf : " + getActivity().getFilesDir() + "/images/" + docBean.getTrn_img_encrypted_data());
                            File dir = new File(getActivity().getFilesDir(), "/images/" + docBean.getTrn_img_encrypted_data());
                            dir.delete();

                            boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
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

                    trainingImagesArray.remove(v.getId());

                    if (trainingImagesArray.size() > 0) {
                        loadImageArray();
                    } else {
                        trainingImagesArray.clear();

                        hori_sv_trng_image.setVisibility(View.GONE);
                        lLayout_trng_selected_photos_container.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void loadDocumentFile() {

        if (trainingDocumentArray.size() > 0) {
            lv_trng_attachDocs.setVisibility(View.VISIBLE);
            lv_trng_attachDocs.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, trainingDocumentArray.size() * 100));

            DocumentListAdapter adapter = new DocumentListAdapter(getActivity());
            lv_trng_attachDocs.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            lv_trng_attachDocs.setVisibility(View.GONE);
        }


    }

    private class DocumentListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public DocumentListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return trainingDocumentArray.size();
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

            Bean beanObj = trainingDocumentArray.get(position);
            int sNo = position + 1;
            holder.txtVw_documentNo.setText("Attachment " + sNo);
            holder.eTxt_medical_attDoc.setText(beanObj.getTrn_doc_type());

            holder.imgV_MedicalDocDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Flag  sadsa" + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);
                    if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                        trainingDocumentArray.remove(position);
                        if (trainingDocumentArray.size() == 0) {
                            lv_trng_attachDocs.setVisibility(View.GONE);
                        }
                    } else {

                        Trn_Documents trn_document = new Trn_Documents(getActivity());
                        trn_document.open();
                        Bean docBean = trainingDocumentArray.get(position);

                        Cursor cursorDoc = trn_document.fetchByDoc_IdAndDocDocID(str_selected_trainingId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.TRAININGDETAILS_DOCUMENT);
                        if (cursorDoc.getCount() > 0) {
                            String doc_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                            if (doc_sync_status.equals("0")) {
                                //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                                File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/" + docBean.getTrn_doc_type());
                                tempDir.delete();

                                Cursor delete_trn_document = trn_document.deleteByDoc_Id_Type(docBean.getTrn_doc_doc_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_DOCUMENT);
                            } else {
                                File dir = new File(getActivity().getFilesDir(), "/documents/" + docBean.getTrn_doc_type());
                                dir.delete();

                                boolean update_trn_document = trn_document.update_document(docBean.getTrn_doc_doc_id(), str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), "2",
                                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                                        docBean.getTrn_created_by(), docBean.getTrn_created_on(),
                                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                            }
                        }

                        trainingDocumentArray.remove(position);
                        if (trainingDocumentArray.size() == 0) {
                            lv_trng_attachDocs.setVisibility(View.GONE);
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


    /**
     * @Type ClickEvent
     * @Created_By Aravindhakumar.S
     * @Created_On 08-03-2016
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

//        diffBtwDays();
//        if (date1.before(date2)) {
//
//            alert("Given date is greater than today's date  : "
//                    + getShowDate(getCurrentDate()));
//
//        }
////        else if (diffInDays <= 1828) {
////            alert("Please select a date less than 5 years from the current date");
////        }
//        else {

        eTxt_trng_date.setText(getShowDate(mFormattedDatefrom));
//        }

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
                    isUpdate = false;
                    deleteTrainingDetails();
                    hideAllLayouts();
                    hideAllFabButtons();
                    getTrainingDetailsByEquipEnrollId();
                    clearAllFields();
                    btn_fab_trng_new.setVisibility(View.VISIBLE);

                } else {
                    isUpdate = false;

//                    getServiceDetails();
                    hideAllLayouts();
                    hideAllFabButtons();
                    getTrainingDetailsByEquipEnrollId();
                    btn_fab_trng_new.setVisibility(View.VISIBLE);
//                    service_add_edit_layout.setVisibility(View.GONE);
//                    btn_fab_serviceMstSave.setVisibility(View.GONE);
//                    btn_fab_serviceMstAddNew.setVisibility(View.VISIBLE);
                }

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpdate = false;
                mContactUsDialog.dismiss();
            }
        });
        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContactUsDialog.show();

    }

    private void deleteTrainingDetails() {
        System.out.println("str_selected_trainingId ==>>>" + str_selected_trainingId);
        Trn_Training_Details trng_enroll = new Trn_Training_Details(getActivity());
        trng_enroll.open();
        Cursor del_cursor = trng_enroll.update_flag_By_training_Id(str_selected_trainingId);

        trng_enroll.close();

        Trn_Images trn_image = new Trn_Images(getActivity());
        trn_image.open();
        Cursor img_cursor = trn_image.updateBy_Img_Id(str_selected_trainingId);
        trn_image.close();

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();
        Cursor doc_cursor = trn_docAdapt.updateBy_Doc_Id(str_selected_trainingId);
        trn_docAdapt.close();
    }
//    public void showContactUsDialog(final Context ctx, String txt) {
//
//
//        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
//        mContactUsDialog.setContentView(R.layout.dialog_view);
//
//        mContactUsDialog.setCancelable(false);
//
//        final Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
//        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
//        txt_dia.setText(txt);
//
//        yes.setText("Yes");
//        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
//        no.setText("No");
//
//        yes.setTypeface(calibri_bold_typeface);
//        txt_dia.setTypeface(calibri_bold_typeface);
//        no.setTypeface(calibri_bold_typeface);
//
//        if (isAddedNew == true) {
//            no.setVisibility(View.GONE);
//            yes.setText("OK");
//
//        } else {
//            no.setVisibility(View.VISIBLE);
//        }
//
//        yes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContactUsDialog.dismiss();
//
//                if (isAddedNew == false) {
//
////                    delete_medical_ewuipment_installation();
//
//
//                    exportDatabse(BusinessAccessLayer.DATABASE_NAME);
//
////                    clearAllFields();
//                    Intent intObj = new Intent(getActivity(), ViewMedicalEquipAppCompat.class);
//                    startActivity(intObj);
//                } else {
//
//                    exportDatabse(BusinessAccessLayer.DATABASE_NAME);
////                    clearAllFields();
//                    Intent intObj = new Intent(getActivity(), ViewMedicalEquipAppCompat.class);
//                    startActivity(intObj);
//
//                }
//
//            }
//        });
//
//        no.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mContactUsDialog.dismiss();
//            }
//        });
//        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        mContactUsDialog.show();
//
//    }
}
