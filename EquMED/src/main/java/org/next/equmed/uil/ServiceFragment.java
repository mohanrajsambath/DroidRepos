package org.next.equmed.uil;

import android.app.AlertDialog;
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
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by next on 8/3/16.
 * Created by Muralidharan
 */
public class ServiceFragment extends UserInterfaceLayer implements View.OnClickListener {

    Typeface calibri_typeface, calibri_bold_typeface;

    TextView txtVw_service_datetime, txtVw_service_duration, txtVw_service_type, txtVw_noserviceFound, txtVw_service_img, txtVw_service_notes, txtVw_service_approvedby,
            txtVw_invoice, txtVw_serviceYesNo, txtVw_service_servicedby, txtVw_attDoc, txtVw_service_createdBy, txtVw_service_createdOn;
    EditText eTxt_service_datetime, eTxt_service_duration, eTxt_service_type, eTxt_service_notes, eTxt_service_approvedby, eTxt_service_servicedby, eTxt_attDoc, eTxt_service_createdBy, eTxt_service_createdOn;
    Button btn_service_uploadImage, btn_service_attach_document;
    Switch switch_invoice;
    FloatingActionButton btn_fab_serviceMstAddNew, btn_fab_serviceMstSave, btn_fab_serviDelete,btn_fab_serviClear;
    LinearLayout layout_listDetails, service_add_edit_layout, service_manage_hos_eq_name, lLayout_service_createdOn, lLayout_service_createdBy;
    private static final String TAG = "ServiceFragment";
    ArrayList<Bean> ServiceArraylist = new ArrayList<>();
    ListView list_service;
    boolean isAddedNew = true;
    boolean isUpdate = false;
    //boolean isPreview = true;
    int countVal;
    String fetchSyncStatus,selectedSerId, selectedSerNotes,selectedSerdatetime, selected_serCreatedBy, selected_serCreatedOn, selectedSertype, selectedSerduration, selectedSerapprovedBy, selectedServicedby, selectedSerInvoice;

    String generatedServiceId,eq_enroll_hospital_name,eq_enroll_equipment_name;

    ArrayList<Bean> serviceDocumentArray = new ArrayList<Bean>();
    ArrayList<Bean> serviceImagesArray = new ArrayList<Bean>();
    HorizontalScrollView hori_scroll_view_service;
    ViewGroup selected_photos_container_service;
    String curFileName = "", getFilePath = "";
    ListView list_serviceDocument;
    LinearLayout lLayout_attDocList;


    private Uri picUri;
    public File f;
    public File file,file_upload;
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
                R.layout.service_enrollment, container, false);

        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        isUpdate = false;
        serviceDocumentArray.clear();

        BusinessAccessLayer.bug_class_name = "ServiceFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        getViewCasting(rootView);

        return rootView;
    }

    private void getViewCasting(View rootView_service) {

        String service_date_time = "Service Date and Time";
        String service_type = "Service Type";

        String asterisk = "<font color='#EE0000'> *</font>";

        list_serviceDocument = (ListView) rootView_service.findViewById(R.id.list_serviceDocument);
        selected_photos_container_service = (ViewGroup) rootView_service.findViewById(R.id.selected_photos_container_service);
        hori_scroll_view_service = (HorizontalScrollView) rootView_service.findViewById(R.id.hori_scroll_view_ser);

        list_service = (ListView) rootView_service.findViewById(R.id.list_Service);
        layout_listDetails = (LinearLayout) rootView_service.findViewById(R.id.layout_listDetails);
        service_add_edit_layout = (LinearLayout) rootView_service.findViewById(R.id.service_add_edit_layout);
        service_manage_hos_eq_name = (LinearLayout) rootView_service.findViewById(R.id.service_manage_hos_eq_name);
        lLayout_attDocList = (LinearLayout) rootView_service.findViewById(R.id.lLayout_attDocList);
        lLayout_service_createdBy = (LinearLayout) rootView_service.findViewById(R.id.lLayout_service_createdBy);
        lLayout_service_createdOn = (LinearLayout) rootView_service.findViewById(R.id.lLayout_service_createdOn);

        txtVw_service_datetime = (TextView) rootView_service.findViewById(R.id.txtVw_service_datetime);
        eTxt_service_datetime = (EditText) rootView_service.findViewById(R.id.eTxt_service_datetime);

        txtVw_service_duration = (TextView) rootView_service.findViewById(R.id.txtVw_service_duration);
        eTxt_service_duration = (EditText) rootView_service.findViewById(R.id.eTxt_service_duration);

        txtVw_service_type = (TextView) rootView_service.findViewById(R.id.txtVw_service_type);
        eTxt_service_type = (EditText) rootView_service.findViewById(R.id.eTxt_service_type);

        txtVw_service_datetime.setText(Html.fromHtml(service_date_time + asterisk));
        txtVw_service_type.setText(Html.fromHtml(service_type + asterisk));

        txtVw_service_img = (TextView) rootView_service.findViewById(R.id.txtVw_service_img);
        btn_service_uploadImage = (Button) rootView_service.findViewById(R.id.btn_service_uploadImage);

        txtVw_service_notes = (TextView) rootView_service.findViewById(R.id.txtVw_service_notes);
        eTxt_service_notes = (EditText) rootView_service.findViewById(R.id.eTxt_service_notes);

        txtVw_service_approvedby = (TextView) rootView_service.findViewById(R.id.txtVw_service_approvedby);
        eTxt_service_approvedby = (EditText) rootView_service.findViewById(R.id.eTxt_service_approvedby);

        txtVw_invoice = (TextView) rootView_service.findViewById(R.id.txtVw_invoice);
        switch_invoice = (Switch) rootView_service.findViewById(R.id.switch_invoice);
        txtVw_serviceYesNo = (TextView) rootView_service.findViewById(R.id.txtVw_serviceYesNo);

        txtVw_service_servicedby = (TextView) rootView_service.findViewById(R.id.txtVw_service_servicedby);
        eTxt_service_servicedby = (EditText) rootView_service.findViewById(R.id.eTxt_service_servicedby);

        txtVw_attDoc = (TextView) rootView_service.findViewById(R.id.txtVw_attDoc);
        btn_service_attach_document = (Button) rootView_service.findViewById(R.id.btn_service_attach_document);
        eTxt_attDoc = (EditText) rootView_service.findViewById(R.id.eTxt_attDoc);

        TextView txtVw_hosp_name = (TextView) rootView_service.findViewById(R.id.txtVw_hosp_name);
        TextView txtVw_hosp_name_val = (TextView) rootView_service.findViewById(R.id.txtVw_hosp_name_val);
        TextView txtVw_Equip_name = (TextView) rootView_service.findViewById(R.id.txtVw_Equip_name);
        TextView txtVw_Equip_name_val = (TextView) rootView_service.findViewById(R.id.txtVw_Equip_name_val);

        TextView txtVw_hosp_name1 = (TextView) rootView_service.findViewById(R.id.txtVw_hosp_name1);
        EditText txtVw_hosp_name_val1 = (EditText) rootView_service.findViewById(R.id.txtVw_hosp_name_val1);
        TextView txtVw_Equip_name1 = (TextView) rootView_service.findViewById(R.id.txtVw_Equip_name1);
        EditText txtVw_Equip_name_val1 = (EditText) rootView_service.findViewById(R.id.txtVw_Equip_name_val1);

        TextView txtVw_view_service_serialNo1 = (TextView) rootView_service.findViewById(R.id.txtVw_view_service_serialNo1);
        TextView txtVw_ServiceType = (TextView) rootView_service.findViewById(R.id.txtVw_ServiceType);
        TextView txtVw_service_DateTime = (TextView) rootView_service.findViewById(R.id.txtVw_service_DateTime);
        TextView update_service = (TextView) rootView_service.findViewById(R.id.update_service);
        txtVw_noserviceFound = (TextView) rootView_service.findViewById(R.id.txtVw_noserviceFound);

        btn_fab_serviceMstAddNew = (FloatingActionButton) rootView_service.findViewById(R.id.btn_fab_serviceMstAddNew);
        btn_fab_serviceMstSave = (FloatingActionButton) rootView_service.findViewById(R.id.btn_fab_serviceMstSave);
        btn_fab_serviDelete = (FloatingActionButton) rootView_service.findViewById(R.id.btn_fab_serviDelete);
        btn_fab_serviClear = (FloatingActionButton) rootView_service.findViewById(R.id.btn_fab_serviClear);

        txtVw_service_createdBy = (TextView) rootView_service.findViewById(R.id.txtVw_service_createdBy);
        eTxt_service_createdBy = (EditText) rootView_service.findViewById(R.id.eTxt_service_createdBy);

        txtVw_service_createdOn = (TextView) rootView_service.findViewById(R.id.txtVw_service_createdOn);
        eTxt_service_createdOn = (EditText) rootView_service.findViewById(R.id.eTxt_service_createdOn);

        txtVw_hosp_name.setTypeface(calibri_typeface);
        txtVw_hosp_name_val.setTypeface(calibri_typeface);
        txtVw_Equip_name.setTypeface(calibri_typeface);
        txtVw_Equip_name_val.setTypeface(calibri_typeface);
        txtVw_hosp_name1.setTypeface(calibri_typeface);
        txtVw_hosp_name_val1.setTypeface(calibri_typeface);
        txtVw_Equip_name1.setTypeface(calibri_typeface);
        txtVw_Equip_name_val1.setTypeface(calibri_typeface);
        txtVw_view_service_serialNo1.setTypeface(calibri_bold_typeface);
        txtVw_ServiceType.setTypeface(calibri_bold_typeface);
        txtVw_service_DateTime.setTypeface(calibri_bold_typeface);
        update_service.setTypeface(calibri_bold_typeface);
        txtVw_noserviceFound.setTypeface(calibri_bold_typeface);
        txtVw_service_createdBy.setTypeface(calibri_typeface);
        eTxt_service_createdBy.setTypeface(calibri_typeface);
        txtVw_service_createdOn.setTypeface(calibri_typeface);
        eTxt_service_createdOn.setTypeface(calibri_typeface);


        txtVw_service_datetime.setTypeface(calibri_typeface);
        eTxt_service_datetime.setTypeface(calibri_typeface);
        txtVw_service_duration.setTypeface(calibri_typeface);
        eTxt_service_duration.setTypeface(calibri_typeface);

        txtVw_service_type.setTypeface(calibri_typeface);
        eTxt_service_type.setTypeface(calibri_typeface);
        txtVw_service_img.setTypeface(calibri_typeface);
        btn_service_uploadImage.setTypeface(calibri_bold_typeface);

        txtVw_service_notes.setTypeface(calibri_typeface);
        eTxt_service_notes.setTypeface(calibri_typeface);
        txtVw_service_approvedby.setTypeface(calibri_typeface);
        eTxt_service_approvedby.setTypeface(calibri_typeface);
        txtVw_invoice.setTypeface(calibri_typeface);
        switch_invoice.setTypeface(calibri_typeface);

        txtVw_serviceYesNo.setTypeface(calibri_typeface);
        txtVw_service_servicedby.setTypeface(calibri_typeface);
        eTxt_service_servicedby.setTypeface(calibri_typeface);
        txtVw_attDoc.setTypeface(calibri_typeface);
        btn_service_attach_document.setTypeface(calibri_bold_typeface);
        eTxt_attDoc.setTypeface(calibri_typeface);

        btn_fab_serviceMstAddNew.setOnClickListener(this);
        eTxt_service_datetime.setOnClickListener(this);
        btn_fab_serviceMstSave.setOnClickListener(this);
        btn_service_uploadImage.setOnClickListener(this);
        btn_service_attach_document.setOnClickListener(this);
        btn_fab_serviDelete.setOnClickListener(this);
        btn_fab_serviClear.setOnClickListener(this);

        if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID.equals("")) {
            txtVw_noserviceFound.setVisibility(View.VISIBLE);
            layout_listDetails.setVisibility(View.GONE);
            service_manage_hos_eq_name.setVisibility(View.GONE);
        } else {
          //  service_manage_hos_eq_name.setVisibility(View.VISIBLE);
            Trn_Service_Details trn_service_details = new Trn_Service_Details(getActivity());
            trn_service_details.open();
            Cursor hos_details = trn_service_details.fetchHosName(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);
            Cursor eq_details = trn_service_details.fetchEquName(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);
            eq_enroll_hospital_name = hos_details.getString(hos_details.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""))+" / "+hos_details.getString(hos_details.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));
            eq_enroll_equipment_name = eq_details.getString(eq_details.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
            txtVw_hosp_name_val.setText(eq_enroll_hospital_name);
            txtVw_Equip_name_val.setText(eq_enroll_equipment_name);
            txtVw_hosp_name_val1.setText(eq_enroll_hospital_name);
            txtVw_Equip_name_val1.setText(eq_enroll_equipment_name);
            getServiceDetails();
        }
    }

    void getServiceDetails() {
    //    BusinessAccessLayer.UPDATE_GOBACK_FLAG = 1;
        Trn_Service_Details trn_service_details = new Trn_Service_Details(getActivity());
        trn_service_details.open();
        Cursor mCr_service_details = trn_service_details.fetchBySerEq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        System.out.println("status count:" + mCr_service_details.getCount());
        ServiceArraylist.clear();
        if (mCr_service_details.getCount() > 0) {

            for (int eqip_status = 0; eqip_status < mCr_service_details.getCount(); eqip_status++) {
                mCr_service_details.moveToPosition(eqip_status);
                Bean mBean = new Bean();

                String service_id = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICE_ID + ""));
                mBean.setService_id(service_id);

                String service_date_time = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICE_DATE_TIME + ""));
                mBean.setService_datetime(service_date_time);

                String service_duration = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICE_DURATION + ""));
                mBean.setService_duration(service_duration);

                String service_type = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICE_TYPE + ""));
                mBean.setService_type(service_type);

                String service_notes = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICE_NOTES + ""));
                mBean.setService_notes(service_notes);

                String service_approved_by = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICE_APPROVED_BY + ""));
                mBean.setService_approved_by(service_approved_by);

                String serviced_by = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICED_BY + ""));
                mBean.setServiced_by(serviced_by);

                String service_invoice = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SERVICE_INVOICE + ""));
                mBean.setService_invoice(service_invoice);

                String flag = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                mBean.setFlag(flag);

                String sync_status = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                mBean.setSync_status(sync_status);

                String created_by = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                mBean.setCreated_by(created_by);

                String created_on = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                mBean.setCreated_on(created_on);

                String modified_by = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                mBean.setModified_by(modified_by);

                String modified_on = mCr_service_details.getString(mCr_service_details.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                mBean.setModified_on(modified_on);

                ServiceArraylist.add(mBean);
            }
            trn_service_details.close();
            System.out.println("ServiceArraylist Size" + ServiceArraylist.size());
        }

        if (ServiceArraylist.size() > 0) {
            ServiceListAdapter adapter = new ServiceListAdapter(getActivity(), ServiceArraylist);
            list_service.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            layout_listDetails.setVisibility(View.VISIBLE);
            txtVw_noserviceFound.setVisibility(View.GONE);
            service_manage_hos_eq_name.setVisibility(View.VISIBLE);
        } else {
            layout_listDetails.setVisibility(View.GONE);
            list_service.setAdapter(null);
            txtVw_noserviceFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fab_serviceMstAddNew:
                hideKeyBoard();
                if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID != "") {
                    System.out.println("ServiceArraylist.size()==" + ServiceArraylist.size());
                    if (ServiceArraylist.size() >= 5) {
                        showContactUsDialog(getActivity(), "You can't add more than 5 service per equipment");
                    } else {
                        btn_fab_serviceMstSave.setImageResource(R.drawable.save);
                        txtVw_noserviceFound.setVisibility(View.GONE);
                        layout_listDetails.setVisibility(View.GONE);
                        service_add_edit_layout.setVisibility(View.VISIBLE);
                        btn_fab_serviceMstSave.setVisibility(View.VISIBLE);
                        btn_fab_serviceMstAddNew.setVisibility(View.GONE);
                        service_manage_hos_eq_name.setVisibility(View.GONE);
                        btn_fab_serviDelete.setVisibility(View.GONE);
                    }
                } else {
                    showContactUsDialog(getActivity(), "Please add equipment details before adding service");
                }
                break;

            case R.id.eTxt_service_datetime:
                hideKeyBoard();
                eTxt_service_datetime.setInputType(InputType.TYPE_NULL);
                SelectDateTime(v);
                break;

            case R.id.btn_fab_serviceMstSave:

                if (eTxt_service_datetime.length() == 0) {
                    showValidationDialog(getActivity(), "Pick service date and time");
                } else if (eTxt_service_type.length() == 0) {
                    showValidationDialog(getActivity(), "Enter service type");
                } else {
                    System.out.println("isUpdate == "+isUpdate);
                    if(isUpdate==true) {
                            update_service_details();
                    }
                    else
                    {
                        /*if(isPreview==true)
                        {
                            showValidationDialog(getActivity(), "Please verify all data before you submit");
                            btn_fab_serviClear.setVisibility(View.VISIBLE);
                            eTxt_service_type.requestFocus();
                            isPreview=false;
                        }
                        else {*/
                            insert_service_details();
                        /*    btn_fab_serviClear.setVisibility(View.GONE);
                        }*/
                    }
                }
                break;

            case R.id.btn_service_uploadImage:
                hideKeyBoard();
                uploadImage();
                break;
            case R.id.btn_service_attach_document:
                hideKeyBoard();
                Intent intentAttach = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intentAttach, BusinessAccessLayer.FILE_ATTACHMENT);
                break;
            case R.id.btn_fab_serviDelete:
                hideKeyBoard();
                isAddedNew = false;
                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                break;
            case R.id.btn_fab_serviClear:
                hideKeyBoard();
                clearFields();
                break;
            default:
                break;
        }
    }

    private void uploadImage() {
        final CharSequence[] options = {"Take from camera", "Choose from gallery", "Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
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
//                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
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
//        if (resultCode == getActivity().RESULT_OK) {
//            if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA) {
//                File f = new File(Environment.getExternalStorageDirectory().toString());
//                System.out.println("Absolute Path : " + f.getAbsolutePath());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
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
//                    serviceImagesArray.add(beanObj);
//
//
//                    if (serviceImagesArray.size() > 0) {
//                        hori_scroll_view_service.setVisibility(View.VISIBLE);
//                        selected_photos_container_service.setVisibility(View.VISIBLE);
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
//                serviceImagesArray.add(beanObj);
//
//                System.out.println("serviceImagesArray ==> "+serviceImagesArray);
//
//                if (serviceImagesArray.size() > 0) {
//                    hori_scroll_view_service.setVisibility(View.VISIBLE);
//                    selected_photos_container_service.setVisibility(View.VISIBLE);
//                    loadImageArray();
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
//                        beanObjDocument.setTrn_doc_name(BusinessAccessLayer.SERVICEDETAIL_DOCUMENT);
//                        beanObjDocument.setTrn_doc_type(curFileName);
//                        beanObjDocument.setTrn_doc_encrypted_data(docString);
//                        beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);
//
//                        serviceDocumentArray.add(beanObjDocument);
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
                    if (temp.getName().equals("temp_equmed.jpg")) {
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
//                    serviceImagesArray.add(beanObj);
//
//
//                    if (serviceImagesArray.size() > 0) {
//                        hori_scroll_view_service.setVisibility(View.VISIBLE);
//                        selected_photos_container_service.setVisibility(View.VISIBLE);
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
//                serviceImagesArray.add(beanObj);
//
//                System.out.println("serviceImagesArray ==> " + serviceImagesArray);
//
//                if (serviceImagesArray.size() > 0) {
//                    hori_scroll_view_service.setVisibility(View.VISIBLE);
//                    selected_photos_container_service.setVisibility(View.VISIBLE);
//                    loadImageArray();
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
                        serviceImagesArray.add(beanObj);


                        if (serviceImagesArray.size() > 0) {
                            hori_scroll_view_service.setVisibility(View.VISIBLE);
                            selected_photos_container_service.setVisibility(View.VISIBLE);
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

                    serviceImagesArray.add(beanObj);

                    System.out.println("serviceImagesArray ==> " + serviceImagesArray);

                    if (serviceImagesArray.size() > 0) {
                        hori_scroll_view_service.setVisibility(View.VISIBLE);
                        selected_photos_container_service.setVisibility(View.VISIBLE);
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

//                String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_type;
//                if (extension.length > 1) {
//                    new_file_type = n_file_name + "." + extension[1];
//                } else {
//
//                    new_file_type = n_file_name;
//                }

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
                            beanObjDocument.setTrn_doc_name(BusinessAccessLayer.SERVICEDETAIL_DOCUMENT);
                            beanObjDocument.setTrn_doc_type(curFileName);
                            beanObjDocument.setTrn_doc_encrypted_data(docString);
                            beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);

                            serviceDocumentArray.add(beanObjDocument);


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

        selected_photos_container_service.removeAllViews();

        for (countVal = 0; countVal < serviceImagesArray.size(); countVal++) {

            Bean beanImgObj = serviceImagesArray.get(countVal);

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
            com.rey.material.widget.RelativeLayout.LayoutParams rLayoutImage = new com.rey.material.widget.RelativeLayout.LayoutParams(140,
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

            selected_photos_container_service.addView(backgroundLayout);


            com.rey.material.widget.RelativeLayout.LayoutParams btnParam = new com.rey.material.widget.RelativeLayout.LayoutParams(40,
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
                    Bean docBean = serviceImagesArray.get(v.getId());
                    System.out.println("generatedServiceId "+generatedServiceId);
                    System.out.println("generatedServiceId docBean.getTrn_img_img_id()"+docBean.getTrn_img_img_id());

                    Cursor cursorDoc = trn_document.fetchByImgImg_ID(docBean.getTrn_img_img_id(),generatedServiceId,  BusinessAccessLayer.SERVICEDETAIL_IMAGE);
                    System.out.println("dfds: "+cursorDoc.getCount());

                    if (cursorDoc.getCount() > 0) {


                        String img_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                        System.out.println("img_sync_status "+img_sync_status);
                        if (img_sync_status.equals("0")) {
                            //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                            File tempDir = new File(getActivity().getFilesDir(), "/temp_images/" + docBean.getImg_encrypted_data());
                            tempDir.delete();

                            System.out.println("docBean.getTrn_img_img_id()" + docBean.getTrn_img_img_id());
                            System.out.println("generatedServiceId" + generatedServiceId);

                            Cursor delete_trn_document = trn_document.deleteByImg_Id_Type(docBean.getTrn_img_img_id(), generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_IMAGE);
                        } else {
                            File dir = new File(getActivity().getFilesDir(), "/images/" + docBean.getImg_encrypted_data());
                            dir.delete();

                            boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
                                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                                    docBean.getCreated_by(), docBean.getCreated_on(),
                                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                        }

                    }


                    serviceImagesArray.remove(v.getId());
                    System.out.println("serviceImagesArray update:" + serviceImagesArray.size());
                    if (serviceImagesArray.size() > 0) {
                        loadImageArray();
                    } else {
                        hori_scroll_view_service.setVisibility(View.GONE);
                        selected_photos_container_service.setVisibility(View.GONE);
                        serviceImagesArray.clear();
                    }
                }
            });


            backgroundLayout.addView(btn_update);


            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Trn_Images trn_document = new Trn_Images(getActivity());
                    trn_document.open();
                    Bean docBean = serviceImagesArray.get(v.getId());

                    Cursor cursorDoc = trn_document.fetchByImgImg_ID(docBean.getTrn_img_img_id(),generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_IMAGE);
                    System.out.println("cursorDoc.getCount() "+cursorDoc.getCount());
                    if (cursorDoc.getCount() > 0) {


                        String img_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                        System.out.println("img_sync_status : "+img_sync_status);

                        if (img_sync_status.equals("0")) {
                            //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                            File tempDir = new File(getActivity().getFilesDir(), "/temp_images/" + docBean.getImg_encrypted_data());
                            tempDir.delete();

                            System.out.println("docBean.getTrn_img_img_id()" + docBean.getTrn_img_img_id());
                            System.out.println("generatedServiceId" + generatedServiceId);

                            Cursor delete_trn_document = trn_document.deleteByImg_Id_Type(docBean.getTrn_img_img_id(), generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_IMAGE);
                        } else {
                            System.out.println("asdf : "+getActivity().getFilesDir()+"/images/" + docBean.getTrn_img_encrypted_data());
                            File dir = new File(getActivity().getFilesDir(), "/images/" + docBean.getTrn_img_encrypted_data());
                            dir.delete();

                            boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
                                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                                    docBean.getCreated_by(), docBean.getCreated_on(),
                                    BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                        }

                    }

                    /*if (cursorDoc.getCount() > 0) {
                        boolean update_trn_document = trn_document.update_image(docBean.getTrn_img_img_id(), generatedServiceId, BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE, docBean.getTrn_img_encrypted_data(), "2",
                                BusinessAccessLayer.SYNC_STATUS_VALUE,
                                docBean.getCreated_by(), docBean.getCreated_on(),
                                BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                    }*/

                    serviceImagesArray.remove(v.getId());

                    if (serviceImagesArray.size() > 0) {
                        loadImageArray();
                    } else {
                        serviceImagesArray.clear();

                        hori_scroll_view_service.setVisibility(View.GONE);
                        selected_photos_container_service.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void loadDocumentFile() {

        if (serviceDocumentArray.size() > 0) {
            lLayout_attDocList.setVisibility(View.VISIBLE);
            list_serviceDocument.setVisibility(View.VISIBLE);
            list_serviceDocument.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, serviceDocumentArray.size() * 100));

            DocumentListAdapter adapter = new DocumentListAdapter(getActivity());
            list_serviceDocument.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            list_serviceDocument.setVisibility(View.GONE);
        }
    }


    private class DocumentListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public DocumentListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return serviceDocumentArray.size();
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

            Bean beanObj = serviceDocumentArray.get(position);
            int sNo = position + 1;
            holder.txtVw_documentNo.setText("Attachment " + sNo);
            holder.eTxt_medical_attDoc.setText(beanObj.getTrn_doc_type());

            holder.imgV_MedicalDocDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Flag  sadsa" + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);
                    if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                        serviceDocumentArray.remove(position);
                        if (serviceDocumentArray.size() == 0) {
                            list_serviceDocument.setVisibility(View.GONE);
                        }
                    } else {

                        Trn_Documents trn_document = new Trn_Documents(getActivity());
                        trn_document.open();
                        Bean docBean = serviceDocumentArray.get(position);

                        Cursor cursorDoc = trn_document.fetchByDoc_IdAndDocDocID(generatedServiceId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.SERVICEDETAIL_DOCUMENT);
                        if (cursorDoc.getCount() > 0) {
                            String doc_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                            if (doc_sync_status.equals("0")) {
                                //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                                File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/" + docBean.getTrn_doc_type());
                                tempDir.delete();

                                Cursor delete_trn_document = trn_document.deleteByDoc_Id_Type(docBean.getTrn_doc_doc_id(), generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_DOCUMENT);
                            } else {
                                File dir = new File(getActivity().getFilesDir(), "/documents/" + docBean.getTrn_doc_type());
                                dir.delete();

                                boolean update_trn_document = trn_document.update_document(docBean.getTrn_doc_doc_id(), generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), "2",
                                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                                        docBean.getTrn_created_by(), docBean.getTrn_created_on(),
                                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                            }
                        }

                        serviceDocumentArray.remove(position);
                        if (serviceDocumentArray.size() == 0) {
                            list_serviceDocument.setVisibility(View.GONE);
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

    private String getServiceId(int count) {
        String imeino = setIMEIno();
        String timeStamp = getUnixTimeStamp();
        String finalId = "SER_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }

    private void insert_service_details() {
        String etx_ser_date_timeDefault = "";
        String etx_ser_date_time = eTxt_service_datetime.getText().toString().trim();
        if (etx_ser_date_time.length() > 0) {
            etx_ser_date_timeDefault = getDefaultDateTime(etx_ser_date_time);
        }
        String etx_ser_duration = eTxt_service_duration.getText().toString().trim();
        String etx_ser_type = eTxt_service_type.getText().toString().trim();
        String etx_ser_notes = eTxt_service_notes.getText().toString().trim();
        String etx_ser_approved_by = eTxt_service_approvedby.getText().toString().trim();
        String etx_serviced_by = eTxt_service_servicedby.getText().toString().trim();
        String sw_ser_invoice;
        if (switch_invoice.isChecked()) {
            sw_ser_invoice = "Y";
        } else {
            sw_ser_invoice = "N";
        }

        Trn_Service_Details trn_service_details = new Trn_Service_Details(getActivity());
        trn_service_details.open();
        Cursor cursor = trn_service_details.fetch();
        generatedServiceId = getServiceId(cursor.getCount());

        System.out.println("BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID === " + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);

        long insert_service_detail = trn_service_details.insert_service_details(
                generatedServiceId,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                etx_ser_date_timeDefault,
                etx_ser_duration,
                etx_ser_type,
                etx_ser_notes,
                etx_ser_approved_by,
                sw_ser_invoice,
                etx_serviced_by,
                BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime(),
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (insert_service_detail != 0) {


            System.out.println("Image count: " + serviceImagesArray.size());

            for (int imgI = 0; imgI < serviceImagesArray.size(); imgI++) {
                Bean bean = serviceImagesArray.get(imgI);

                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                trn_imagesAdapt.open();
                Cursor trn_images_cursor = trn_imagesAdapt.fetch();
                int imgCount = trn_images_cursor.getCount() + 1;

                long insert_trn_images = trn_imagesAdapt.insert_image("" + imgCount, generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_IMAGE, bean.getTrn_img_encrypted_name(), BusinessAccessLayer.FLAG_VALUE,
                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                trn_imagesAdapt.close();
            }

            System.out.println("serviceDocumentArray.size()"+serviceDocumentArray.size());

            for (int i = 0; i < serviceDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();
                Bean docBean = serviceDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;

                String file_name = docBean.getTrn_doc_type();
                String[] extension = file_name.split("\\.");
                String n_file_name = extension[0] + "_" + getCurrentDateTime();
                String new_file_type = n_file_name + "." + extension[1];


                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedServiceId, BusinessAccessLayer.SERVICEDETAIL_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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
                        out.flush();
                        out.close();
                        out = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            isAddedNew = true;
            clearFields();
            showContactUsDialog(getActivity(), "Service added successfully");
//            isPreview=true;
        } else {
            showValidationDialog(getActivity(), "Service add failed");
//            isPreview=true;
        }
    }

    private void update_service_details() {
        String etx_ser_date_timeUpdate = "";
        String etx_ser_date_time = eTxt_service_datetime.getText().toString().trim();
        if (etx_ser_date_time.length() > 0) {
            etx_ser_date_timeUpdate = getDefaultDateTime(etx_ser_date_time);
        }
        String etx_ser_duration = eTxt_service_duration.getText().toString().trim();
        String etx_ser_type = eTxt_service_type.getText().toString().trim();
        String etx_ser_notes = eTxt_service_notes.getText().toString().trim();
        String etx_ser_approved_by = eTxt_service_approvedby.getText().toString().trim();
        String etx_serviced_by = eTxt_service_servicedby.getText().toString().trim();
        String sw_ser_invoice;
        if (switch_invoice.isChecked()) {
            sw_ser_invoice = "Y";
        } else {
            sw_ser_invoice = "N";
        }

        Trn_Service_Details trn_service_details = new Trn_Service_Details(getActivity());
        trn_service_details.open();

        String flagStatusEqpmnt = "";
        if (fetchSyncStatus.equalsIgnoreCase("0")) {
            flagStatusEqpmnt = "0";
        } else {
            flagStatusEqpmnt = "1";
        }

        boolean update_service_detail = trn_service_details.update_service_detail(
                selectedSerId,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                etx_ser_date_timeUpdate,
                etx_ser_duration,
                etx_ser_type,
                etx_ser_notes,
                etx_ser_approved_by,
                sw_ser_invoice,
                etx_serviced_by,
                "1",
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                selected_serCreatedBy,
                selected_serCreatedOn,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (update_service_detail == true) {

            for (int i = 0; i < serviceImagesArray.size(); i++) {

                System.out.println("serviceImagesArray insert:" + serviceImagesArray.size());


                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
                trn_imagesAdapt.open();
                Cursor imGCursor = trn_imagesAdapt.fetch();

                Bean medImageBean = serviceImagesArray.get(i);
                int id_trn_images = imGCursor.getCount() + 1;
                System.out.println("medImageBean.getNew_Img_upload():" + medImageBean.getNew_Img_upload());
                if (medImageBean.getNew_Img_upload().equalsIgnoreCase("1")) {

                    System.out.println("Id 1 : " + selectedSerId);
                    System.out.println("Id 3 : " + medImageBean.getTrn_img_img_id());
                    //   Cursor cursorImage = trn_imagesAdapt.fetchByImgImg_ID(generatedServiceId, medImageBean.getTrn_img_img_id(), BusinessAccessLayer.MEDICALEQUIPMENT_IMAGE);

//                    if (cursorImage.getCount() == 0) {
                    long insert_trn_document = trn_imagesAdapt.insert_image("" + id_trn_images, selectedSerId, BusinessAccessLayer.SERVICEDETAIL_IMAGE,
                            medImageBean.getTrn_img_encrypted_name(), BusinessAccessLayer.FLAG_VALUE,
                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
                            BusinessAccessLayer.mUserId, getCurrentDateWithTime());
//                    }

                    trn_imagesAdapt.close();
                }
            }


            for (int i = 0; i < serviceDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();

                Bean docBean = serviceDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;

                if (docBean.getTrn_doc_filepath_filname() != null) {
                    System.out.println("Document Bean array " + docBean.getTrn_doc_doc_id());

                    Cursor cursorDoc = trn_documentAdapt.fetchByDoc_IdAndDocDocID(selectedSerId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.SERVICEDETAIL_DOCUMENT);
                    String file_name = docBean.getTrn_doc_type();
                    String[] extension = file_name.split("\\.");

                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_type = n_file_name + "." + extension[1];
                    long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, selectedSerId, BusinessAccessLayer.SERVICEDETAIL_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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
            clearFields();
            isUpdate = false;
            showContactUsDialog(getActivity(), "Service updated successfully");
        } else {
            showValidationDialog(getActivity(), "Service update failed");
        }
    }

    private void clearFields() {
        eTxt_service_datetime.setText("");
        eTxt_service_duration.setText("");
        eTxt_service_type.setText("");
        eTxt_service_notes.setText("");
        eTxt_service_approvedby.setText("");
        eTxt_service_servicedby.setText("");
        switch_invoice.setChecked(false);

        btn_fab_serviDelete.setVisibility(View.GONE);
        selected_photos_container_service.removeAllViews();
        hori_scroll_view_service.setVisibility(View.GONE);
        selected_photos_container_service.setVisibility(View.GONE);
        serviceDocumentArray.clear();
        serviceImagesArray.clear();
        lLayout_attDocList.setVisibility(View.GONE);
        list_serviceDocument.setVisibility(View.GONE);
    }

    private class ServiceListAdapter extends BaseAdapter {
        ArrayList<Bean> serviceArraylist;
        private LayoutInflater l_InflaterServiceList;

        public ServiceListAdapter(Context context, ArrayList<Bean> serviceDetails) {

            serviceArraylist = serviceDetails;
            l_InflaterServiceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return serviceArraylist.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = l_InflaterServiceList.inflate(
                        R.layout.inflate_list_service, parent, false);

                holder.rL_UpdateDeleteData = (RelativeLayout) convertView.findViewById(R.id.rL_UpdateDeleteData);
                holder.txtV_service_type_inflate = (TextView) convertView.findViewById(R.id.txtV_service_type_inflate);
                holder.txtV_service_type_inflate.setTypeface(calibri_typeface);

                holder.txtV_service_date_time = (TextView) convertView.findViewById(R.id.txtV_service_date_time);
                holder.txtV_service_date_time.setTypeface(calibri_typeface);

                holder.imgV_UpdateData = (ImageView) convertView.findViewById(R.id.imgV_UpdateData);

                holder.txtV_service_SerialNo_inflate = (TextView) convertView.findViewById(R.id.txtV_service_SerialNo_inflate);
                holder.txtV_service_SerialNo_inflate.setTypeface(calibri_bold_typeface);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int sNo = position + 1;
            holder.txtV_service_SerialNo_inflate.setText("" + sNo);
            String service_datetime = serviceArraylist.get(position).getService_datetime();
            String service_datetimeFinal = "";
            if (service_datetime.length() > 0) {
                service_datetimeFinal = getShowDateWithTime(service_datetime);
            }
            holder.txtV_service_date_time.setText(service_datetimeFinal);
            holder.txtV_service_type_inflate.setText(serviceArraylist.get(position).getService_type());
            fetchSyncStatus = serviceArraylist.get(position).getSync_status();
            if(fetchSyncStatus.equals("0"))
            {
                holder.txtV_service_SerialNo_inflate.setTextColor(Color.parseColor("#ff0000"));
            }
            else
            {
                holder.txtV_service_SerialNo_inflate.setTextColor(Color.parseColor("#009933"));
            }

            holder.imgV_UpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusinessAccessLayer.editPage = true;
                   // BusinessAccessLayer.UPDATE_GOBACK_FLAG = 1;
                    isUpdate = true;
                    String service_datetimeFinalUpdate = "";
                    generatedServiceId = serviceArraylist.get(position).getService_id();
                    selectedSerId = serviceArraylist.get(position).getService_id();
                    selectedSerdatetime = serviceArraylist.get(position).getService_datetime();
                    selectedSertype = serviceArraylist.get(position).getService_type();
                    selectedSerduration = serviceArraylist.get(position).getService_duration();
                    selectedSerNotes = serviceArraylist.get(position).getService_notes();
                    selectedSerapprovedBy = serviceArraylist.get(position).getService_approved_by();
                    selectedServicedby = serviceArraylist.get(position).getServiced_by();
                    selectedSerInvoice = serviceArraylist.get(position).getService_invoice();
                    fetchSyncStatus = serviceArraylist.get(position).getSync_status();
                    if (selectedSerdatetime.length() > 0) {
                        service_datetimeFinalUpdate = getShowDateWithTime(selectedSerdatetime);
                    }
                    eTxt_service_datetime.setText(service_datetimeFinalUpdate);
                    eTxt_service_duration.setText(selectedSerduration);
                    eTxt_service_type.setText(selectedSertype);
                    eTxt_service_notes.setText(selectedSerNotes);
                    eTxt_service_approvedby.setText(selectedSerapprovedBy);
                    eTxt_service_servicedby.setText(selectedServicedby);

                    if (selectedSerInvoice.equals("Y")) {
                        switch_invoice.setChecked(true);
                    } else {
                        switch_invoice.setChecked(false);
                    }
                    selected_serCreatedBy = serviceArraylist.get(position).getCreated_by();
                    selected_serCreatedOn = serviceArraylist.get(position).getCreated_on();

                    loadAddedImageAndDocument(selectedSerId);
                    btn_fab_serviceMstSave.setImageResource(R.drawable.edit);
                    service_add_edit_layout.setVisibility(View.VISIBLE);
                    service_manage_hos_eq_name.setVisibility(View.VISIBLE);
                    btn_fab_serviceMstSave.setVisibility(View.VISIBLE);
                    btn_fab_serviceMstAddNew.setVisibility(View.GONE);
                    txtVw_noserviceFound.setVisibility(View.GONE);
                    layout_listDetails.setVisibility(View.GONE);
                    lLayout_attDocList.setVisibility(View.VISIBLE);
                    btn_fab_serviDelete.setVisibility(View.VISIBLE);
                    lLayout_service_createdOn.setVisibility(View.VISIBLE);
                    lLayout_service_createdBy.setVisibility(View.VISIBLE);


                    if (selected_serCreatedBy.equalsIgnoreCase("1")) {
                        eTxt_service_createdBy.setText("Admin");
                    } else {
                        eTxt_service_createdBy.setText("" + getUserNameByUserId(selected_serCreatedBy));
                    }
                    if (selected_serCreatedOn.length() > 0) {
                        eTxt_service_createdOn.setText(getShowDateWithTime(selected_serCreatedOn));
                    }

                }
            });

            return convertView;
        }

        class ViewHolder {
            RelativeLayout rL_UpdateDeleteData;
            TextView txtV_service_SerialNo_inflate, txtV_service_type_inflate, txtV_service_date_time;
            ImageView imgV_UpdateData;

        }
    }

    private void hideAllLayouts() {
        service_add_edit_layout.setVisibility(View.GONE);
//        lLayout_wrnty_attachDocs_list.setVisibility(View.GONE);
    }

    private void loadAddedImageAndDocument(String service_id) {
        serviceImagesArray.clear();
        serviceDocumentArray.clear();
        System.out.println("service_id ==> "+service_id);
        Trn_Images trn_image = new Trn_Images(getActivity());
        trn_image.open();
        Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(service_id, BusinessAccessLayer.SERVICEDETAIL_IMAGE);

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

                serviceImagesArray.add(imgBean);
            }

        }
        Trn_Images.close();
        if (serviceImagesArray.size() > 0) {
            hori_scroll_view_service.setVisibility(View.VISIBLE);
            selected_photos_container_service.setVisibility(View.VISIBLE);
            loadImageArray();
        }
//                else{
//                    serviceImagesArray.clear();
//                }

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();
        Cursor mCr_trn_doc = trn_docAdapt.fetchByDoc_Id_Type(service_id, BusinessAccessLayer.SERVICEDETAIL_DOCUMENT);
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

                serviceDocumentArray.add(medicalDocument);
            }

        }

        trn_docAdapt.close();
        if (serviceDocumentArray.size() > 0) {
            loadDocumentFile();
        }
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }

    public void SelectDateTime(View v) {
        final View dialogView = View.inflate(getActivity(), R.layout.activity_date_timer, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        dialogView.findViewById(R.id.datetimeset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datepicker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timepicker);
                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                SimpleDateFormat mSDF = new SimpleDateFormat("HH:mm:ss");
// mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
                String time = mSDF.format(calendar.getTime());
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
// SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = sdf.format(new Date(year - 1900, month, day));
                eTxt_service_datetime.setText(getShowDateWithTime(formatedDate + ' ' + time));
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
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
                    deleteServiceDetails();
                    getServiceDetails();
                    clearFields();
                    btn_fab_serviDelete.setVisibility(View.GONE);
                    lLayout_service_createdOn.setVisibility(View.GONE);
                    lLayout_service_createdBy.setVisibility(View.GONE);
                    service_add_edit_layout.setVisibility(View.GONE);
                    btn_fab_serviceMstSave.setVisibility(View.GONE);
                    btn_fab_serviceMstAddNew.setVisibility(View.VISIBLE);
                } else {
                    isUpdate = false;
                    getServiceDetails();
                    service_add_edit_layout.setVisibility(View.GONE);
                    lLayout_service_createdOn.setVisibility(View.GONE);
                    lLayout_service_createdBy.setVisibility(View.GONE);
                    btn_fab_serviceMstSave.setVisibility(View.GONE);
                    btn_fab_serviceMstAddNew.setVisibility(View.VISIBLE);
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

    private void deleteServiceDetails()
    {
        System.out.println("selectedSerId ==>>>"+selectedSerId);
        Trn_Service_Details service_enroll = new Trn_Service_Details(getActivity());
        service_enroll.open();
        Cursor del_cursor = service_enroll.update_flag_By_service_Id(selectedSerId);
        service_enroll.close();

        Trn_Images trn_image = new Trn_Images(getActivity());
        trn_image.open();
        Cursor img_cursor = trn_image.updateBy_Img_Id(selectedSerId);
        trn_image.close();

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();
        Cursor doc_cursor = trn_docAdapt.updateBy_Doc_Id(selectedSerId);
        trn_docAdapt.close();
    }
}
