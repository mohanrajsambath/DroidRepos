package org.next.equmed.uil;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.FileChooser;
import org.next.equmed.dal.Mst_Equipment_Status;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Documents;
import org.next.equmed.dal.Trn_Images;
import org.next.equmed.dal.Trn_Training_Details;
import org.next.equmed.dal.Trn_Warranty_Details;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * Created by S.Aravindhakumar.
 * Created On on 8/3/16
 */
public class Warranty_Fragment_Activity extends UserInterfaceLayer implements View.OnClickListener {
    private static final String TAG = "WarrantyFragment";
    boolean isAddedNew = true;
    boolean isUpdate = false;
    private int isFlagForDate = 0;
    TextView txtVw_wrnty_update, txtVw_wrnty_type, txtVw_wrnty_from_to_date, txtVw_wrnty_serialNo, txtVw_wrntyDetails_hsptl_name, txtVw_wrntyDetails_label_hsptl_name,
            txtVw_wrntyDetails_eqpmnt_name, txtVw_wrntyDetails_label_eqpmnt_name, txtVw_wrnty_attach_docs, txtVw_noWrrntyDetails_found, txtVw_wrnty_eqpmntName,
            txtVw_wrnty_hsptlName, txtVw_wrnty_wrntyType, txtVw_wrnty_startDate, txtVw_wrnty_endDate, txtVw_wrnty_duration, txtVw_wrnty_description, txtVw_wrnty_createdBy, txtVw_wrnty_createdOn;
    EditText eTxt_wrnty_hsptlName, eTxt_wrnty_eqipName, eTxt_wrnty_startDate, eTxt_wrnty_endDate, eTxt_wrnty_duration, eTxt_wrnty_description, eTxt_wrnty_createdBy, eTxt_wrnty_createdOn;
    Spinner spn_wrnty_wrntyTypeList;
    Button btn_wrnty_attach_document;

    Typeface calibri_typeface, calibri_bold_typeface;

    ListView lv_wrnty_attachDocs, lv_wrrnty_details;
    LinearLayout lLayout_wrrntyDetails, lLayout_wrnty_attachDocs_list, llayout_wrnty_eqmnt_hsptl_name, lLayout_wrnty_createdBy, lLayout_wrnty_createdOn;
    RelativeLayout rlayout_wrntyView;
    FloatingActionButton btn_fab_wrrntyDetailsSave, btn_fab_wrrntyDetailsNew, btn_fab_wrrntyDetailsDelete, btn_fab_WarrantyClear;


    String[] warrantyTypeStrArray = {"Select Warranty Type", "OEM", "Extended", "Service Only"};

    HashMap<String, String> mWarrantyTypenHashMap = new HashMap<String, String>();

    String spn_wrntyTypeStr = "", str_selected_warrantyId = "",str_createdBy = "", str_createdOn = "";

    ArrayList<Bean> warrantyDetailsArray = new ArrayList<>();
    ArrayList<Bean> warrantyDocumentArray = new ArrayList<Bean>();

    String curFileName = "", getFilePath = "", generatedWarrantyDetailsId = "";
    File file;

    String formattedDateStart;
    String fromStr, toStr = "";

//    boolean isPreview = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.warranty_details_activity, container, false);

        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        BusinessAccessLayer.bug_class_name = "WarrantyFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        getViewCasting(rootView);
        hideAllLayouts();

        getWarrantyType();

        getWarrantyDetailsByEquipEnrollId();
        return rootView;
    }

    private void getViewCasting(View rootview_wrnty) {
        lLayout_wrrntyDetails = (LinearLayout) rootview_wrnty.findViewById(R.id.lLayout_wrrntyDetails);
        lLayout_wrnty_attachDocs_list = (LinearLayout) rootview_wrnty.findViewById(R.id.lLayout_wrnty_attachDocs_list);
        llayout_wrnty_eqmnt_hsptl_name = (LinearLayout) rootview_wrnty.findViewById(R.id.llayout_wrnty_eqmnt_hsptl_name);
        rlayout_wrntyView = (RelativeLayout) rootview_wrnty.findViewById(R.id.rlayout_wrntyView);
        lLayout_wrnty_createdBy = (LinearLayout) rootview_wrnty.findViewById(R.id.lLayout_wrnty_createdBy);
        lLayout_wrnty_createdOn = (LinearLayout) rootview_wrnty.findViewById(R.id.lLayout_wrnty_createdOn);
        String asterisk = "<font color='#EE0000'> *</font>";



        txtVw_noWrrntyDetails_found = (TextView) rootview_wrnty.findViewById(R.id.txtVw_noWrrntyDetails_found);
        txtVw_wrnty_eqpmntName = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_eqpmntName);
        eTxt_wrnty_eqipName = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_eqipName);
        eTxt_wrnty_hsptlName = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_hsptlName);
        eTxt_wrnty_startDate = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_startDate);
        eTxt_wrnty_endDate = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_endDate);
        eTxt_wrnty_duration = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_duration);
        eTxt_wrnty_description = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_description);

        txtVw_wrnty_hsptlName = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_hsptlName);
        txtVw_wrnty_wrntyType = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_wrntyType);

        txtVw_wrnty_wrntyType.setText(Html.fromHtml(txtVw_wrnty_wrntyType.getText().toString() + asterisk));

        txtVw_wrnty_startDate = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_startDate);
        txtVw_wrnty_startDate.setText(Html.fromHtml(txtVw_wrnty_startDate.getText().toString() + asterisk));

        txtVw_wrnty_endDate = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_endDate);
     //   txtVw_wrnty_endDate.setText(Html.fromHtml(txtVw_wrnty_endDate.getText().toString() + asterisk));

        txtVw_wrnty_duration = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_duration);
        txtVw_wrnty_description = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_description);
        txtVw_wrnty_attach_docs = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_attach_docs);
        txtVw_wrntyDetails_label_eqpmnt_name = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrntyDetails_label_eqpmnt_name);
        txtVw_wrntyDetails_eqpmnt_name = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrntyDetails_eqpmnt_name);
        txtVw_wrntyDetails_label_hsptl_name = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrntyDetails_label_hsptl_name);
        txtVw_wrntyDetails_hsptl_name = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrntyDetails_hsptl_name);
        txtVw_wrnty_serialNo = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_serialNo);
        txtVw_wrnty_type = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_type);
        txtVw_wrnty_from_to_date = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_from_to_date);
        txtVw_wrnty_update = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_update);

        spn_wrnty_wrntyTypeList = (Spinner) rootview_wrnty.findViewById(R.id.spn_wrnty_wrntyTypeList);
        btn_wrnty_attach_document = (Button) rootview_wrnty.findViewById(R.id.btn_wrnty_attach_document);


        btn_fab_wrrntyDetailsSave = (FloatingActionButton) rootview_wrnty.findViewById(R.id.btn_fab_wrrntyDetailsSave);
        btn_fab_wrrntyDetailsNew = (FloatingActionButton) rootview_wrnty.findViewById(R.id.btn_fab_wrrntyDetailsNew);
        btn_fab_wrrntyDetailsDelete = (FloatingActionButton) rootview_wrnty.findViewById(R.id.btn_fab_wrrntyDetailsDelete);
        btn_fab_WarrantyClear = (FloatingActionButton) rootview_wrnty.findViewById(R.id.btn_fab_WarrantyClear);
        lv_wrnty_attachDocs = (ListView) rootview_wrnty.findViewById(R.id.lv_wrnty_attachDocs);
        lv_wrrnty_details = (ListView) rootview_wrnty.findViewById(R.id.lv_wrrnty_details);


        txtVw_wrnty_createdBy = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_createdBy);
        txtVw_wrnty_createdOn = (TextView) rootview_wrnty.findViewById(R.id.txtVw_wrnty_createdOn);

        eTxt_wrnty_createdBy = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_createdBy);
        eTxt_wrnty_createdOn = (EditText) rootview_wrnty.findViewById(R.id.eTxt_wrnty_createdOn);

        txtVw_wrnty_createdBy.setTypeface(calibri_typeface);
        txtVw_wrnty_createdOn.setTypeface(calibri_typeface);
        eTxt_wrnty_createdBy.setTypeface(calibri_typeface);
        eTxt_wrnty_createdOn.setTypeface(calibri_typeface);


        txtVw_noWrrntyDetails_found.setTypeface(calibri_bold_typeface);
        txtVw_wrnty_eqpmntName.setTypeface(calibri_typeface);
        eTxt_wrnty_eqipName.setTypeface(calibri_typeface);
        eTxt_wrnty_hsptlName.setTypeface(calibri_typeface);
        eTxt_wrnty_startDate.setTypeface(calibri_typeface);
        eTxt_wrnty_endDate.setTypeface(calibri_typeface);
        eTxt_wrnty_duration.setTypeface(calibri_typeface);
        eTxt_wrnty_description.setTypeface(calibri_typeface);
        txtVw_wrnty_hsptlName.setTypeface(calibri_typeface);
        txtVw_wrnty_wrntyType.setTypeface(calibri_typeface);
        txtVw_wrnty_startDate.setTypeface(calibri_typeface);
        txtVw_wrnty_endDate.setTypeface(calibri_typeface);
        txtVw_wrnty_duration.setTypeface(calibri_typeface);
        txtVw_wrnty_description.setTypeface(calibri_typeface);
        txtVw_wrnty_attach_docs.setTypeface(calibri_typeface);
        txtVw_wrntyDetails_label_eqpmnt_name.setTypeface(calibri_typeface);
        txtVw_wrntyDetails_eqpmnt_name.setTypeface(calibri_typeface);
        txtVw_wrntyDetails_label_hsptl_name.setTypeface(calibri_typeface);
        txtVw_wrntyDetails_hsptl_name.setTypeface(calibri_typeface);
        txtVw_wrnty_serialNo.setTypeface(calibri_bold_typeface);
        txtVw_wrnty_type.setTypeface(calibri_bold_typeface);
        txtVw_wrnty_from_to_date.setTypeface(calibri_bold_typeface);
        txtVw_wrnty_update.setTypeface(calibri_bold_typeface);
        btn_wrnty_attach_document.setTypeface(calibri_bold_typeface);

        btn_fab_wrrntyDetailsDelete.setOnClickListener(this);
        btn_fab_wrrntyDetailsNew.setOnClickListener(this);
        btn_fab_wrrntyDetailsSave.setOnClickListener(this);
        btn_wrnty_attach_document.setOnClickListener(this);
        eTxt_wrnty_startDate.setOnClickListener(this);
        eTxt_wrnty_endDate.setOnClickListener(this);
        btn_fab_WarrantyClear.setOnClickListener(this);

        FilterAdapter adapterInstlCndtn = new FilterAdapter(getActivity(), warrantyTypeStrArray);
        spn_wrnty_wrntyTypeList.setAdapter(adapterInstlCndtn);
        spn_wrnty_wrntyTypeList.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spn_wrntyTypeStr = "";
                    System.out.println("spn_wrntyTypeStr;:" + spn_wrntyTypeStr);
                } else {
//                    spn_insCondStr = ""+position;
                    spn_wrntyTypeStr = warrantyTypeStrArray[position];
                    System.out.println("spn_wrntyTypeStr;:" + spn_wrntyTypeStr);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getWarrantyDetailsByEquipEnrollId() {

        Trn_Warranty_Details trn_warrantyAdapt = new Trn_Warranty_Details(getActivity());
        trn_warrantyAdapt.open();

        Cursor mCr_trn_warranty = trn_warrantyAdapt.fetchBy_War_Eq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);

        warrantyDetailsArray.clear();
        warrantyDocumentArray.clear();

        if (mCr_trn_warranty.getCount() > 0) {
            for (int i = 0; i < mCr_trn_warranty.getCount(); i++) {
                mCr_trn_warranty.moveToPosition(i);

                Bean warrantyBean = new Bean();

                String str_wrntyId = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_ID + ""));
                str_selected_warrantyId = str_wrntyId;
                warrantyBean.setTrn_wrnty_wrntyID(str_wrntyId);

                String str_wrnty_startDate = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_START_DATE + ""));
                warrantyBean.setTrn_wrnty_startDate(str_wrnty_startDate);

                String str_wrnty_endDate = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_END_DATE + ""));
                warrantyBean.setTrn_wrnty_endDate(str_wrnty_endDate);


                String str_wrnty_desc = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_DESCRIPTION + ""));
                warrantyBean.setTrn_wrnty_desc(str_wrnty_desc);


                String str_wrnty_duration = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_DURATION + ""));
                warrantyBean.setTrn_wrnty_duration(str_wrnty_duration);


                String str_wrnty_wrntyType = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_TYPE + ""));
                warrantyBean.setTrn_wrnty_type(str_wrnty_wrntyType);

                String str_wrnty_flag = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                warrantyBean.setFlag(str_wrnty_flag);

                String str_wrnty_syncStatus = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                warrantyBean.setSync_status(str_wrnty_syncStatus);

                String str_wrnty_createdOn = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                warrantyBean.setCreated_on(str_wrnty_createdOn);
                str_createdOn=str_wrnty_createdOn;
                String str_wrnty_createdBy = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                warrantyBean.setCreated_by(str_wrnty_createdBy);
                str_createdBy = str_wrnty_createdBy;
                String str_wrnty_modifiedBy = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                warrantyBean.setModified_by(str_wrnty_modifiedBy);

                String str_wrnty_modifiedOn = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                warrantyBean.setModified_on(str_wrnty_modifiedOn);


                warrantyDetailsArray.add(warrantyBean);

            }
        }

        if (warrantyDetailsArray.size() > 0) {

            WarrantyListAdapter adapter = new WarrantyListAdapter(getActivity());
            lv_wrrnty_details.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            rlayout_wrntyView.setVisibility(View.VISIBLE);
            llayout_wrnty_eqmnt_hsptl_name.setVisibility(View.VISIBLE);
            txtVw_wrntyDetails_eqpmnt_name.setText(getEquipmentInfoByEquipmentId(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID));
            txtVw_wrntyDetails_hsptl_name.setText(getHospitalInformationByHospitalId(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID));
        } else {
            lv_wrrnty_details.setAdapter(null);
//            lLayout_w.setVisibility(View.GONE);
            txtVw_noWrrntyDetails_found.setVisibility(View.VISIBLE);
            txtVw_noWrrntyDetails_found.setText("No warranty details found");
        }
    }
    // Adapter Class

    private class WarrantyListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;

        public WarrantyListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return warrantyDetailsArray.size();
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
                        R.layout.inflate_list_warranty_details, parent, false);

//                RelativeLayout rLayout_inflate_wrnty_UpdateData;
//                TextView txtV_inflate_wrnty_sNo, txtV_inflate_wrnty_type, txtV_inflate_wrnty_fromToDate;
//                ImageView imgV_inflate_wrnty_update;

                holder.rLayout_inflate_wrnty_UpdateData = (RelativeLayout) convertView.findViewById(R.id.rLayout_inflate_wrnty_UpdateData);
                holder.txtV_inflate_wrnty_sNo = (TextView) convertView.findViewById(R.id.txtV_inflate_wrnty_sNo);
                holder.txtV_inflate_wrnty_sNo.setTypeface(calibri_bold_typeface);
                holder.txtV_inflate_wrnty_type = (TextView) convertView.findViewById(R.id.txtV_inflate_wrnty_type);
                holder.txtV_inflate_wrnty_type.setTypeface(calibri_typeface);

                holder.txtV_inflate_wrnty_fromToDate = (TextView) convertView.findViewById(R.id.txtV_inflate_wrnty_fromToDate);
                holder.txtV_inflate_wrnty_fromToDate.setTypeface(calibri_typeface);

                holder.imgV_inflate_wrnty_update = (ImageView) convertView.findViewById(R.id.imgV_inflate_wrnty_update);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            int sNo = position + 1;

            holder.txtV_inflate_wrnty_sNo.setText("" + sNo);
            holder.txtV_inflate_wrnty_type.setText(warrantyDetailsArray.get(position).getTrn_wrnty_type());
            String startDateBeforeFinal = "", endDateBeforeFinal = "";
            String startDateBefore = warrantyDetailsArray.get(position).getTrn_wrnty_startDate();
            if (startDateBefore.length() > 0) {
                startDateBeforeFinal = getShowDate(startDateBefore);
            }
            String endDateBefore = warrantyDetailsArray.get(position).getTrn_wrnty_endDate();
            if (endDateBefore.length() > 0) {
                endDateBeforeFinal = getShowDate(endDateBefore);
            }
//            String from_to_date = warrantyDetailsArray.get(position).getTrn_wrnty_startDate() + "  to  " + warrantyDetailsArray.get(position).getTrn_wrnty_endDate();
         //   String from_to_date = startDateBeforeFinal + "  to  " + endDateBeforeFinal;
            String from_to_date = startDateBeforeFinal + "  to..  ";
            holder.txtV_inflate_wrnty_fromToDate.setText(from_to_date);

            String syncStatus = warrantyDetailsArray.get(position).getSync_status();
            if(syncStatus.equals("0"))
            {
                holder.txtV_inflate_wrnty_sNo.setTextColor(Color.parseColor("#ff0000"));
            }
            else
            {
                holder.txtV_inflate_wrnty_sNo.setTextColor(Color.parseColor("#009933"));
            }

            holder.imgV_inflate_wrnty_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusinessAccessLayer.editPage = true;
                    isUpdate = true;
                    hideAllLayouts();
                    lLayout_wrrntyDetails.setVisibility(View.VISIBLE);
                    lLayout_wrnty_attachDocs_list.setVisibility(View.VISIBLE);
                    hideAllFabButtons();
                    getWarrantyType();
                    btn_fab_wrrntyDetailsSave.setVisibility(View.VISIBLE);
                    btn_fab_wrrntyDetailsSave.setImageResource(R.drawable.edit);
                    btn_fab_wrrntyDetailsDelete.setVisibility(View.VISIBLE);
//
                    str_selected_warrantyId = warrantyDetailsArray.get(position).getTrn_wrnty_wrntyID();
                    String str_wrnty_eqpmnt_name = getEquipmentInfoByEquipmentId(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);
                    String str_wrnty_hsptl_name = getHospitalInformationByHospitalId(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);
//
                    String str_selected_wrnty_startDate = warrantyDetailsArray.get(position).getTrn_wrnty_startDate();
                    String str_selected_wrnty_endDate = warrantyDetailsArray.get(position).getTrn_wrnty_endDate();
                    String str_selected_wrnty_desc = warrantyDetailsArray.get(position).getTrn_wrnty_desc();
                    String str_selected_wrnty_duration = warrantyDetailsArray.get(position).getTrn_wrnty_duration();
                    String str_selected_wrnty_type = warrantyDetailsArray.get(position).getTrn_wrnty_type();
                    spn_wrntyTypeStr = str_selected_wrnty_type;
//
                    eTxt_wrnty_eqipName.setText(str_wrnty_eqpmnt_name);
                    eTxt_wrnty_hsptlName.setText(str_wrnty_hsptl_name);
                    if (str_selected_wrnty_startDate.length() > 0) {
                        eTxt_wrnty_startDate.setText(getShowDate(str_selected_wrnty_startDate));
                    }
                    if (str_selected_wrnty_endDate.length() > 0) {
                        eTxt_wrnty_endDate.setText(getShowDate(str_selected_wrnty_endDate));
                    }

                    eTxt_wrnty_duration.setText(str_selected_wrnty_desc);
                    eTxt_wrnty_description.setText(str_selected_wrnty_duration);

                    if (str_selected_wrnty_type.length() > 0) {
                        spn_wrnty_wrntyTypeList.setSelection(Integer.parseInt(mWarrantyTypenHashMap
                                .get(spn_wrntyTypeStr)));

                    } else {
                        spn_wrntyTypeStr = warrantyTypeStrArray[0];
                        spn_wrnty_wrntyTypeList.setSelection(Integer.parseInt(mWarrantyTypenHashMap.get(spn_wrntyTypeStr)));

                    }

                    lLayout_wrnty_createdBy.setVisibility(View.VISIBLE);
                    lLayout_wrnty_createdOn.setVisibility(View.VISIBLE);

                    if (str_createdBy.equalsIgnoreCase("1")) {
                        eTxt_wrnty_createdBy.setText("Admin");
                    } else {
                        eTxt_wrnty_createdBy.setText("" + getUserNameByUserId(str_createdBy));
                    }
                    if (str_createdOn.length()>0){
                        eTxt_wrnty_createdOn.setText(getShowDateWithTime(str_createdOn));
                    }


                    loadAddedImageAndDocument(str_selected_warrantyId);

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
            RelativeLayout rLayout_inflate_wrnty_UpdateData;
            TextView txtV_inflate_wrnty_sNo, txtV_inflate_wrnty_type, txtV_inflate_wrnty_fromToDate;
            ImageView imgV_inflate_wrnty_update;

        }

    }

    private void loadAddedImageAndDocument(String warnty_id) {
        warrantyDocumentArray.clear();
//        trainingImagesArray.clear();
//        trainingDocumentArray.clear();
//        Trn_Images trn_image = new Trn_Images(getActivity());
//        trn_image.open();
//        Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(training_id, BusinessAccessLayer.TRAININGDETAILS_IMAGE);
//
//        if (mCr_trn_image.getCount() > 0) {
//            for (int int_trn_image = 0; int_trn_image < mCr_trn_image.getCount(); int_trn_image++) {
//
//                mCr_trn_image.moveToPosition(int_trn_image);
//                Bean imgBean = new Bean();
//
//                String str_trnImg_Id = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));
//                imgBean.setTrn_img_img_id(str_trnImg_Id);
//
//                String str_trn_image = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
//                imgBean.setTrn_img_encrypted_data(str_trn_image);
//
//                String str_trn_image_name = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
//                imgBean.setTrn_img_encrypted_name(str_trn_image_name);
//
//                String str_trn_created = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
//                imgBean.setCreated_by(str_trn_created);
//                String str_trn_createdOn = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
//                imgBean.setCreated_on(str_trn_createdOn);
//                imgBean.setNew_Img_upload("0");
//
//                trainingImagesArray.add(imgBean);
//            }
//
//        }
//        Trn_Images.close();
//        if (trainingImagesArray.size() > 0) {
//            hori_sv_trng_image.setVisibility(View.VISIBLE);
//            lLayout_trng_selected_photos_container.setVisibility(View.VISIBLE);
//            loadImageArray();
//        }
////                else{
////                    serviceImagesArray.clear();
////                }

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();

        Cursor mCr_trn_doc = trn_docAdapt.fetchByDoc_Id_Type(warnty_id, BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT);
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

                warrantyDocumentArray.add(medicalDocument);

//                        documentEncodedStr = str_trn_docStr;
//                        Bitmap medicalEqpmntImage = decodeBase64(str_trn_docStr);
//                        eTxt_attDoc.setImageBitmap(medicalEqpmntImage);
//                        bean.setImg_encrypted_data(str_trn_image);
            }

        }

        trn_docAdapt.close();
        if (warrantyDocumentArray.size() > 0) {
            loadDocumentFile();
        }
    }

    private void getWarrantyType() {

        for (int i = 0; i < warrantyTypeStrArray.length; i++) {
            if (i == 0) {
                mWarrantyTypenHashMap.put("Select", "" + i);

            } else {
                mWarrantyTypenHashMap.put(warrantyTypeStrArray[i], "" + i);

            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fab_wrrntyDetailsNew:
                hideKeyBoard();
                if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID.length() > 0) {
                    if (warrantyDetailsArray.size() >= 5) {
                        showContactUsDialog(getActivity(), "You can't add more than 5  training  per equipment");
                    } else {
                    hideAllLayouts();
                    hideAllFabButtons();
                    lLayout_wrrntyDetails.setVisibility(View.VISIBLE);
                    btn_fab_wrrntyDetailsSave.setImageResource(R.drawable.save);
                    btn_fab_wrrntyDetailsSave.setVisibility(View.VISIBLE);
                    txtVw_noWrrntyDetails_found.setVisibility(View.GONE);
                    String selectedEqpmntName = getEquipmentInfoByEquipmentId(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);
                    eTxt_wrnty_eqipName.setText(selectedEqpmntName);
                    String hsptlName = getHospitalInformationByHospitalId(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);
                    eTxt_wrnty_hsptlName.setText(hsptlName);
                    }
                } else {
                    showContactUsDialog(getActivity(), "Please add equipment details before adding warranty details");
                }
                break;
            case R.id.btn_fab_wrrntyDetailsSave:
                isAddedNew = true;
                if (spn_wrntyTypeStr.length() == 0) {
                    showValidationDialog(getActivity(), "Select warranty type");
                } else if (eTxt_wrnty_startDate.getText().toString().trim().length() == 0) {
                    showValidationDialog(getActivity(), "Select warranty start date");

                } /*else if (eTxt_wrnty_endDate.getText().toString().trim().length() == 0) {
                    showValidationDialog(getActivity(), "Select warranty end date");

                }*/ else {

                    if (isUpdate == true) {
//update
                        update_warranty_details();
                    } else {

                        /*if(isPreview==true)
                        {
                            showValidationDialog(getActivity(), "Please verify all data before you submit");
                            btn_fab_WarrantyClear.setVisibility(View.VISIBLE);
                            isPreview=false;
                        }
                        else {*/
                            insert_warrranty_Details();
                         /*   isPreview=true;
                            btn_fab_WarrantyClear.setVisibility(View.GONE);
                        }*/
                    }


                }
                break;

            case R.id.btn_fab_wrrntyDetailsDelete:
                hideKeyBoard();
                isAddedNew = false;
                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                break;
            case R.id.btn_wrnty_attach_document:
                hideKeyBoard();
                Intent intentAttach = new Intent(getActivity(), FileChooser.class);
                startActivityForResult(intentAttach, BusinessAccessLayer.FILE_ATTACHMENT);
                break;
            case R.id.eTxt_wrnty_startDate:
                hideKeyBoard();
                startSelectDateTime(v);
//                datepicker();
//                isFlagForDate = 1;
                break;
            case R.id.eTxt_wrnty_endDate:
//                if (eTxt_wrnty_startDate.getText().toString().trim().length() == 0) {
//                    showContactUsDialog(getActivity(), "Please select start date first");
//                } else {
//                    datepicker();
//                    isFlagForDate = 2;
//                }
                hideKeyBoard();
                endSelectDateTime(v);
                break;
            case R.id.btn_fab_WarrantyClear:
                hideKeyBoard();
                clearAllFields();
                break;
            default:
                break;
        }
    }

    private void datepicker() {
        new DatePickerDialog(getActivity(), dateD,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener dateD = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            try {
                updateLabel();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    };


    private void updateLabel() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(myCalendar.getTime());
        formattedDateStart = df.format(myCalendar.getTime());
        if (isFlagForDate == 1) {
            fromStr = formattedDateStart;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date date1 = sdf.parse(getShowDate(getCurrentDate()));
            Date date2 = sdf.parse(getShowDate(formattedDateStart));

//            if (date1.before(date2)) {
//
//                showValidationDialog(getActivity(),"Given date is greater than today's date  : "
//                        + getShowDate(getCurrentDate()));
//
//            } else {

            eTxt_wrnty_startDate.setText(getShowDate(formattedDateStart));

//            }

        } else if (isFlagForDate == 2) {
            toStr = formattedDate;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            Date date1 = sdf.parse(getShowDate(getCurrentDate()));
            Date date2 = sdf.parse(getShowDate(formattedDate));

            Date date3 = sdf.parse(getShowDate(fromStr));
            System.out.println("Dateformat date1 ::" + date1);
            System.out.println("Dateformat date2 ::" + date2);
            System.out.println("Dateformat date3 ::" + date3);
//            if (date2.before(date3)) {
//                showValidationDialog(getActivity(), "End date is lesser than start date ");
//                eTxt_wrnty_endDate.setText("");
//            } else if (date1.equals(date3)) {
//                showValidationDialog(getActivity(), "Start date and end date are equal");
//                eTxt_wrnty_endDate.setText("");
//
////                eTxt_wrnty_endDate.setText(getShowDate(formattedDate));
//            }else {
            eTxt_wrnty_endDate.setText(getShowDate(formattedDate));
//            }

//            if (date1.before(date2)) {
//
//                showValidationDialog(getActivity(),"Given date is greater than today's date  :" + ""
//                        + getShowDate(getCurrentDate()));
//
//            } else            if (date2.before(date3)) {
//                showValidationDialog(getActivity(),"End date is lesser than start date ");
//                eTxt_wrnty_endDate.setText("");
//            } else if (date1.equals(date3)) {
//                showValidationDialog(getActivity(),"Start date and end date are equal");
//                eTxt_wrnty_endDate.setText("");
//
////                eTxt_wrnty_endDate.setText(getShowDate(formattedDate));
//
//            } else {
//                eTxt_wrnty_endDate.setText(getShowDate(formattedDate));
//
//            }

        }
    }

    private String getWarrantyDetailsId(int count) {
        String imeino = setIMEIno();
        String timeStamp = getUnixTimeStamp();
        String finalId = "WAR_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }

    private void insert_warrranty_Details() {
        String str_eTxt_wrnty_startDateFinal = "", str_eTxt_wrnty_endDateFinal = "";
        String str_eTxt_wrnty_startDate = eTxt_wrnty_startDate.getText().toString().trim();
        System.out.println("str_eTxt_wrnty_startDate::"+str_eTxt_wrnty_startDate.length());
        if (str_eTxt_wrnty_startDate.length() > 0) {
            str_eTxt_wrnty_startDateFinal = getDefaultDate(str_eTxt_wrnty_startDate);

        } else {
            str_eTxt_wrnty_startDateFinal = "";
        }


        String str_eTxt_wrnty_endDate = eTxt_wrnty_endDate.getText().toString().trim();
        System.out.println("str_eTxt_wrnty_endDate::"+str_eTxt_wrnty_endDate.length());
        if (str_eTxt_wrnty_endDate.length() > 0) {
            str_eTxt_wrnty_endDateFinal = getDefaultDate(str_eTxt_wrnty_endDate);
        }

        String str_eTxt_wrnty_description = eTxt_wrnty_description.getText().toString().trim();
        String str_eTxt_wrnty_duration = eTxt_wrnty_duration.getText().toString().trim();

        Trn_Warranty_Details trn_wrntyAdapt = new Trn_Warranty_Details(getActivity());
        trn_wrntyAdapt.open();
        Cursor cursor_trng = trn_wrntyAdapt.fetch();
        generatedWarrantyDetailsId = getWarrantyDetailsId(cursor_trng.getCount());

        long insertTrainingValue = trn_wrntyAdapt.insert_warranty_details(generatedWarrantyDetailsId, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID, str_eTxt_wrnty_startDateFinal, str_eTxt_wrnty_endDateFinal,
                str_eTxt_wrnty_description, str_eTxt_wrnty_duration, spn_wrntyTypeStr, BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE, BusinessAccessLayer.mUserId, getCurrentDateWithTime(), BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (insertTrainingValue != 0) {


            for (int i = 0; i < warrantyDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();
                Bean docBean = warrantyDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;
//                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedMedicalEquipId, BusinessAccessLayer.MEDICALEQUIPMENT_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), BusinessAccessLayer.FLAG_VALUE,
//                        BusinessAccessLayer.SYNC_STATUS_VALUE,
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());

                String file_name = docBean.getTrn_doc_type();
                String[] extension = file_name.split("\\.");
                String n_file_name = extension[0] + "_" + getCurrentDateTime();
                String new_file_type = n_file_name + "." + extension[1];


                long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, generatedWarrantyDetailsId, BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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
            showContactUsDialog(getActivity(), "Warranty details added successfully");

            exportDatabse(BusinessAccessLayer.DATABASE_NAME);

            clearAllFields();
        } else {
            showValidationDialog(getActivity(), "Warranty details  added failed");
        }
    }

    private void update_warranty_details() {
        String str_eTxt_wrnty_startDateFinalUpdate = "", str_eTxt_wrnty_endDateFinalUpdate = "";
        String str_eTxt_wrnty_startDate = eTxt_wrnty_startDate.getText().toString().trim();
        if (str_eTxt_wrnty_startDate.length() > 0) {
            str_eTxt_wrnty_startDateFinalUpdate = getDefaultDate(str_eTxt_wrnty_startDate);
        }
        String str_eTxt_wrnty_endDate = eTxt_wrnty_endDate.getText().toString().trim();
        if (str_eTxt_wrnty_endDate.length() > 0) {
            str_eTxt_wrnty_endDateFinalUpdate = getDefaultDate(str_eTxt_wrnty_endDate);
        }
        String str_eTxt_wrnty_description = eTxt_wrnty_description.getText().toString().trim();
        String str_eTxt_wrnty_duration = eTxt_wrnty_duration.getText().toString().trim();



        Trn_Warranty_Details trn_wrntyAdaptUpdate = new Trn_Warranty_Details(getActivity());
        trn_wrntyAdaptUpdate.open();

//        Cursor cursor_trng = trn_trainingAdapt.fetch();
//        generatedTrainingDetailsId = getTrainingDetailsId(cursor_trng.getCount());


        boolean update_training_details = trn_wrntyAdaptUpdate.update_warranty_detail(
                str_selected_warrantyId,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                str_eTxt_wrnty_startDateFinalUpdate,
                str_eTxt_wrnty_endDateFinalUpdate,
                str_eTxt_wrnty_description,
                str_eTxt_wrnty_duration,
                spn_wrntyTypeStr,
                "1",
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                str_createdBy,
                str_createdOn,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (update_training_details == true) {

//            for (int i = 0; i < trainingImagesArray.size(); i++) {
//
//                System.out.println("serviceImagesArray insert:" + trainingImagesArray.size());
//
//
//                Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
//                trn_imagesAdapt.open();
//                Cursor imGCursor = trn_imagesAdapt.fetch();
//
//                Bean medImageBean = trainingImagesArray.get(i);
//                int id_trn_images = imGCursor.getCount() + 1;
//                System.out.println("medImageBean.getNew_Img_upload():" + medImageBean.getNew_Img_upload());
//                if (medImageBean.getNew_Img_upload().equalsIgnoreCase("1")) {
//
//                    System.out.println("Id 1 : " + str_selected_trainingId);
//                    System.out.println("Id 3 : " + medImageBean.getTrn_img_img_id());
//                    //   Cursor cursorImage = trn_imagesAdapt.fetchByImgImg_ID(generatedServiceId, medImageBean.getTrn_img_img_id(), BusinessAccessLayer.TRAININGDETAILS_IMAGE);
//
////                    if (cursorImage.getCount() == 0) {
//                    long update_training = trn_imagesAdapt.insert_image("" + id_trn_images, str_selected_trainingId, BusinessAccessLayer.TRAININGDETAILS_IMAGE,
//                            medImageBean.getTrn_img_encrypted_name(), BusinessAccessLayer.FLAG_VALUE,
//                            BusinessAccessLayer.SYNC_STATUS_VALUE,
//                            BusinessAccessLayer.mUserId, getCurrentDateWithTime(),
//                            BusinessAccessLayer.mUserId, getCurrentDateWithTime());
////                    }
//
//                    trn_imagesAdapt.close();
//                }
//            }
//

            for (int i = 0; i < warrantyDocumentArray.size(); i++) {
                Trn_Documents trn_documentAdapt = new Trn_Documents(getActivity());
                trn_documentAdapt.open();
                Cursor cursorCount = trn_documentAdapt.fetch();

                Bean docBean = warrantyDocumentArray.get(i);
                int intCount = cursorCount.getCount() + 1;

                if (docBean.getTrn_doc_filepath_filname() != null) {
                    System.out.println("Document Bean array " + docBean.getTrn_doc_doc_id());

                    Cursor cursorDoc = trn_documentAdapt.fetchByDoc_IdAndDocDocID(str_selected_warrantyId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT);
                    String file_name = docBean.getTrn_doc_type();
                    String[] extension = file_name.split("\\.");

                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
                    String new_file_type = n_file_name + "." + extension[1];
                    long insert_trn_document = trn_documentAdapt.insert_document("" + intCount, str_selected_warrantyId, BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT, "", new_file_type, BusinessAccessLayer.FLAG_VALUE,
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
            showContactUsDialog(getActivity(), "Warranty details updated successfully");
        } else {
            showValidationDialog(getActivity(), "Warranty details update failed");
        }
    }

    private void clearAllFields() {
        eTxt_wrnty_duration.setText("");
        eTxt_wrnty_startDate.setText("");
        eTxt_wrnty_endDate.setText("");
        eTxt_wrnty_description.setText("");
//        eTxt_wrnty_eqipName.setText("");
//        eTxt_wrnty_hsptlName.setText("");
        warrantyDocumentArray.clear();
        spn_wrnty_wrntyTypeList.setSelection(0);
        lLayout_wrnty_attachDocs_list.setVisibility(View.GONE);
        lv_wrnty_attachDocs.setVisibility(View.GONE);
    }

    public void startSelectDateTime(View v) {
        final View dialogView = View.inflate(getActivity(), R.layout.activity_date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        dialogView.findViewById(R.id.datetimeset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datepicker);
//                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timepicker);
                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                SimpleDateFormat mSDF = new SimpleDateFormat("HH:mm:ss");
//                mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
                String time = mSDF.format(calendar.getTime());

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
//                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String formatedDate = sdf.format(new Date(year - 1900, month, day));

                getWarrantyDetailsByDate(formatedDate);

                eTxt_wrnty_startDate.setText(getShowDate(formatedDate));
//                eTxt_FromDate.setText(formatedDate + ' ' + "07:00:00");

                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }

    private void getWarrantyDetailsByDate(String selectdDate) {
        Trn_Warranty_Details trn_warrantyAdapt = new Trn_Warranty_Details(getActivity());
        trn_warrantyAdapt.open();

        Cursor mCr_trn_warranty = trn_warrantyAdapt.fetchByEq_Id_startEndDate(selectdDate, BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        System.out.println("mCursorFetch mCr_trn_warranty: ;" + mCr_trn_warranty.getCount());

        if (mCr_trn_warranty.getCount() > 0) {
            for (int i = 0; i < mCr_trn_warranty.getCount(); i++) {
                mCr_trn_warranty.moveToPosition(i);

                Bean warrantyBean = new Bean();

                String str_wrntyId = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_ID + ""));
                warrantyBean.setTrn_wrnty_wrntyID(str_wrntyId);

                String str_wrnty_startDate = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_START_DATE + ""));
                warrantyBean.setTrn_wrnty_startDate(str_wrnty_startDate);
                String str_wrnty_startDateformated = getShowDateWithOutTime(str_wrnty_startDate);

                String str_wrnty_endDate = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_END_DATE + ""));
                warrantyBean.setTrn_wrnty_endDate(str_wrnty_endDate);
                String str_wrnty_endDateformated = getShowDateWithOutTime(str_wrnty_endDate);

                String startEndDate = str_wrnty_startDateformated + " to " + str_wrnty_endDateformated;
                String str_wrnty_desc = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_DESCRIPTION + ""));
                warrantyBean.setTrn_wrnty_desc(str_wrnty_desc);


                String str_wrnty_duration = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_DURATION + ""));
                warrantyBean.setTrn_wrnty_duration(str_wrnty_duration);


                String str_wrnty_wrntyType = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.WARRANTY_TYPE + ""));
                warrantyBean.setTrn_wrnty_type(str_wrnty_wrntyType);

                String str_wrnty_flag = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                warrantyBean.setFlag(str_wrnty_flag);

                String str_wrnty_syncStatus = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                warrantyBean.setSync_status(str_wrnty_syncStatus);

                String str_wrnty_createdOn = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                warrantyBean.setCreated_on(str_wrnty_createdOn);
                str_createdOn = str_wrnty_createdOn;
                String str_wrnty_createdBy = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                warrantyBean.setCreated_by(str_wrnty_createdBy);
                str_createdBy = str_wrnty_createdBy;
                String str_wrnty_modifiedBy = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                warrantyBean.setModified_by(str_wrnty_modifiedBy);

                String str_wrnty_modifiedOn = mCr_trn_warranty.getString(mCr_trn_warranty.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                warrantyBean.setModified_on(str_wrnty_modifiedOn);

                showWarrantyDialog(getActivity(), "Warranty Overlap", str_wrnty_wrntyType, startEndDate);

            }
        }
//        else {
//            eTxt_wrnty_startDate.setText(getShowDate(selectdDate));
//        }


    }

    public void endSelectDateTime(View v) {
//        07:00:00   19:00:00

        final View dialogView = View.inflate(getActivity(), R.layout.activity_date_picker, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        dialogView.findViewById(R.id.datetimeset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.datepicker);
//                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timepicker);
                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth());
                SimpleDateFormat mSDF = new SimpleDateFormat("HH:mm:ss");
//                mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
                String time = mSDF.format(calendar.getTime());
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formatedDate = sdf.format(new Date(year - 1900, month, day));
                eTxt_wrnty_endDate.setText(getShowDate(formatedDate));
//                eTxt_ToDate.setText(formatedDate + ' ' + "19:00:00");

                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }
    public String getHospitalInformationByHospitalId(String hsptlId) {

        String hospital_name = "",location_name="";
        Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(getActivity());
        mst_hospital_enroll.open();
        Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByHospital_Id(hsptlId);

        if (mCr_mst_hospital_enroll.getCount() > 0) {
            for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                mCr_mst_hospital_enroll.moveToPosition(i);


                String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));
                location_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));

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

        return hospital_name+" / "+location_name;


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

    private void hideAllLayouts() {
//        LinearLayout lLayout_wrrntyDetails, lLayout_wrnty_attachDocs_list, llayout_wrnty_eqmnt_hsptl_name;
//        RelativeLayout rlayout_wrntyView
        lLayout_wrrntyDetails.setVisibility(View.GONE);
        lLayout_wrnty_createdOn.setVisibility(View.GONE);
        lLayout_wrnty_createdBy.setVisibility(View.GONE);
//        lLayout_wrnty_attachDocs_list.setVisibility(View.GONE);
        llayout_wrnty_eqmnt_hsptl_name.setVisibility(View.GONE);
        rlayout_wrntyView.setVisibility(View.GONE);


    }

    private void hideAllFabButtons() {
        btn_fab_wrrntyDetailsNew.setVisibility(View.GONE);
        btn_fab_wrrntyDetailsSave.setVisibility(View.GONE);
        btn_fab_wrrntyDetailsDelete.setVisibility(View.GONE);

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
     * @Created_By Aravindhakumar.S
     * @Created_On 11-03-2016
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

    public void showWarrantyDialog(final Context ctx, String txt, String wrntyType, String wrntyDateRange) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_warranty);

        mContactUsDialog.setCancelable(false);

        Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes_wrnty);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_wrnty_dia);
        TextView txt_wrnty_type = (TextView) mContactUsDialog.findViewById(R.id.txt_wrnty_type);
        TextView txt_wrnty_range = (TextView) mContactUsDialog.findViewById(R.id.txt_wrnty_range);
        txt_dia.setText(txt);

        yes.setText("Ok");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no_wrnty);
        no.setText("Cancel");

        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
        txt_wrnty_type.setTypeface(calibri_bold_typeface);
        txt_wrnty_range.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

        txt_wrnty_type.setText("Warranty type :" + "  " + wrntyType);
        txt_wrnty_range.setText("Date range       :" + "  " + wrntyDateRange);
        no.setVisibility(View.GONE);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                eTxt_wrnty_startDate.setHint("Warranty Start Date");
//                eTxt_wrnty_endDate.setHint("Warranty End Date");

                mContactUsDialog.dismiss();


            }
        });

        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContactUsDialog.show();

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
                    deleteWarrantyDetails();
                    hideAllLayouts();
                    hideAllFabButtons();
                    getWarrantyDetailsByEquipEnrollId();
                    clearAllFields();
                    btn_fab_wrrntyDetailsNew.setVisibility(View.VISIBLE);

                } else {
                    isUpdate = false;

                    hideAllLayouts();
                    hideAllFabButtons();
                    getWarrantyDetailsByEquipEnrollId();
                    btn_fab_wrrntyDetailsNew.setVisibility(View.VISIBLE);
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

    private void deleteWarrantyDetails() {
        System.out.println("str_selected_warrantyId ==>>>" + str_selected_warrantyId);
        Trn_Warranty_Details wrnty_deleteAdapt = new Trn_Warranty_Details(getActivity());
        wrnty_deleteAdapt.open();
        Cursor del_cursor = wrnty_deleteAdapt.update_flag_By_warranty_Id(str_selected_warrantyId);

        wrnty_deleteAdapt.close();

        Trn_Images trn_image = new Trn_Images(getActivity());
        trn_image.open();
        Cursor img_cursor = trn_image.updateBy_Img_Id(str_selected_warrantyId);
        trn_image.close();

        Trn_Documents trn_docAdapt = new Trn_Documents(getActivity());
        trn_docAdapt.open();
        Cursor doc_cursor = trn_docAdapt.updateBy_Doc_Id(str_selected_warrantyId);
        trn_docAdapt.close();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {


            if (requestCode == BusinessAccessLayer.FILE_ATTACHMENT) {

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
                        file = new File(pathPlusName);

                        long fileSize = file.length() / 1024;

                        if (fileSize > 2048) {

                            Toast.makeText(getActivity(), "Please attach file less than 2 MB", Toast.LENGTH_SHORT).show();
                        } else {
                            System.out.println("fize size:" + fileSize);

                            byte[] docBytes;


                            //       String docString = Base64.encodeToString(docBytes, Base64.DEFAULT);
                            String docString = "";
                            System.out.println("Inside file attach : " + pathPlusName);

                            Bean beanObjDocument = new Bean();
                            beanObjDocument.setTrn_doc_name(BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT);
                            beanObjDocument.setTrn_doc_type(curFileName);
                            beanObjDocument.setTrn_doc_encrypted_data(docString);
                            beanObjDocument.setTrn_doc_filepath_filname(pathPlusName);

                            warrantyDocumentArray.add(beanObjDocument);


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

    private void loadDocumentFile() {

        if (warrantyDocumentArray.size() > 0) {
            lLayout_wrnty_attachDocs_list.setVisibility(View.VISIBLE);
            lv_wrnty_attachDocs.setVisibility(View.VISIBLE);
            lv_wrnty_attachDocs.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, warrantyDocumentArray.size() * 100));

            DocumentListAdapter adapter = new DocumentListAdapter(getActivity());
            lv_wrnty_attachDocs.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        } else {
            lv_wrnty_attachDocs.setVisibility(View.GONE);
        }


    }

    private class DocumentListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public DocumentListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return warrantyDocumentArray.size();
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

            Bean beanObj = warrantyDocumentArray.get(position);
            int sNo = position + 1;
            holder.txtVw_documentNo.setText("Attachment " + sNo);
            holder.eTxt_medical_attDoc.setText(beanObj.getTrn_doc_type());

            holder.imgV_MedicalDocDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Flag  sadsa" + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);
                    if (BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0) {
                        warrantyDocumentArray.remove(position);
                        if (warrantyDocumentArray.size() == 0) {
                            lv_wrnty_attachDocs.setVisibility(View.GONE);
                        }
                    } else {

                        Trn_Documents trn_document = new Trn_Documents(getActivity());
                        trn_document.open();
                        Bean docBean = warrantyDocumentArray.get(position);

                        Cursor cursorDoc = trn_document.fetchByDoc_IdAndDocDocID(str_selected_warrantyId, docBean.getTrn_doc_doc_id(), BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT);
                        if (cursorDoc.getCount() > 0) {
                            String doc_sync_status = cursorDoc.getString(cursorDoc.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));

                            if (doc_sync_status.equals("0")) {
                                //   System.out.println("File PAth : "+docBean.getTrn_doc_type());
                                File tempDir = new File(getActivity().getFilesDir(), "/temp_documents/" + docBean.getTrn_doc_type());
                                tempDir.delete();

                                Cursor delete_trn_document = trn_document.deleteByDoc_Id_Type(docBean.getTrn_doc_doc_id(), str_selected_warrantyId, BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT);
                            } else {
                                File dir = new File(getActivity().getFilesDir(), "/documents/" + docBean.getTrn_doc_type());
                                dir.delete();

                                boolean update_trn_document = trn_document.update_document(docBean.getTrn_doc_doc_id(), str_selected_warrantyId, BusinessAccessLayer.WARRANTYDETAILS_DOCUMENT, docBean.getTrn_doc_encrypted_data(), docBean.getTrn_doc_type(), "2",
                                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                                        docBean.getTrn_created_by(), docBean.getTrn_created_on(),
                                        BusinessAccessLayer.mUserId, getCurrentDateWithTime());
                            }
                        }

                        warrantyDocumentArray.remove(position);
                        if (warrantyDocumentArray.size() == 0) {
                            lv_wrnty_attachDocs.setVisibility(View.GONE);
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


}

