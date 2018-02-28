package org.next.equmed.uil;

/**
 * Created by next on 8/3/16.
 * Created by Muralidharan
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rey.material.widget.Spinner;

import org.next.equmed.R;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.FileChooser;
import org.next.equmed.dal.Trn_Consumables;
import org.next.equmed.dal.Trn_Service_Details;
import org.next.equmed.dal.Trn_Voice_Of_Customer;

import java.util.HashMap;

public class VoiceOfCustomerFragment extends UserInterfaceLayer implements View.OnClickListener {

    Typeface calibri_typeface, calibri_bold_typeface;

    TextView txtVw_voice_of_customer_type, txtVw_voice_of_customer_brief;
    EditText eTxt_voice_of_customer_brief;
    Spinner spn_voice_of_customer_type;
    LinearLayout voc_hos_eq_name;
    FloatingActionButton btn_fab_vocMstSave,btn_fab_VocClear;
    String generatedVocId, eq_enroll_hospital_name, eq_enroll_equipment_name;

    String spn_voc_type_Str = "";
    String[] voc_type_list = {"Select", "Order", "Lead", "Complaints", "General Enquiry", "Others"};
    HashMap<String, String> voc_typeHashMap = new HashMap<String, String>();
//    boolean isPreview = true;

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
                R.layout.voice_of_customer, container, false);

        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        BusinessAccessLayer.bug_class_name = "VoiceOfCustomerFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        getViewCasting(rootView);
        return rootView;
    }

    private void getViewCasting(View rootView_voc) {

        String voc_type = "Type";
        String asterisk = "<font color='#EE0000'> *</font>";

        voc_hos_eq_name = (LinearLayout) rootView_voc.findViewById(R.id.voc_hos_eq_name);

        txtVw_voice_of_customer_type = (TextView) rootView_voc.findViewById(R.id.txtVw_voice_of_customer_type);
        txtVw_voice_of_customer_type.setText(Html.fromHtml(voc_type + asterisk));
        spn_voice_of_customer_type = (Spinner) rootView_voc.findViewById(R.id.spn_voice_of_customer_type);
        txtVw_voice_of_customer_brief = (TextView) rootView_voc.findViewById(R.id.txtVw_voice_of_customer_brief);
        eTxt_voice_of_customer_brief = (EditText) rootView_voc.findViewById(R.id.eTxt_voice_of_customer_brief);

        TextView txtVw_hosp_name = (TextView) rootView_voc.findViewById(R.id.txtVw_hosp_name);
        TextView txtVw_hosp_name_val = (TextView) rootView_voc.findViewById(R.id.txtVw_hosp_name_val);
        TextView txtVw_Equip_name = (TextView) rootView_voc.findViewById(R.id.txtVw_Equip_name);
        TextView txtVw_Equip_name_val = (TextView) rootView_voc.findViewById(R.id.txtVw_Equip_name_val);

        btn_fab_vocMstSave = (FloatingActionButton) rootView_voc.findViewById(R.id.btn_fab_vocMstSave);
        btn_fab_VocClear = (FloatingActionButton) rootView_voc.findViewById(R.id.btn_fab_VocClear);

        eTxt_voice_of_customer_brief.requestFocus();

        txtVw_hosp_name.setTypeface(calibri_typeface);
        txtVw_hosp_name_val.setTypeface(calibri_typeface);
        txtVw_Equip_name.setTypeface(calibri_typeface);
        txtVw_Equip_name_val.setTypeface(calibri_typeface);
        txtVw_voice_of_customer_type.setTypeface(calibri_typeface);
        txtVw_voice_of_customer_brief.setTypeface(calibri_typeface);
        eTxt_voice_of_customer_brief.setTypeface(calibri_typeface);

        btn_fab_vocMstSave.setOnClickListener(this);
        btn_fab_VocClear.setOnClickListener(this);

        FilterAdapter adapterCons_UsageParam = new FilterAdapter(getActivity(), voc_type_list);
        spn_voice_of_customer_type.setAdapter(adapterCons_UsageParam);
        spn_voice_of_customer_type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                if (position == 0) {
                    spn_voc_type_Str = "";
                } else {
//                    spn_insCondStr = ""+position;
                    spn_voc_type_Str = voc_type_list[position];

                }
            }
        });

        getVocType();

        if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID.equals("")) {
            voc_hos_eq_name.setVisibility(View.GONE);
        } else {
            voc_hos_eq_name.setVisibility(View.VISIBLE);
            Trn_Service_Details trn_service_details = new Trn_Service_Details(getActivity());
            trn_service_details.open();
            Cursor hos_details = trn_service_details.fetchHosName(BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID);
            Cursor eq_details = trn_service_details.fetchEquName(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);
            eq_enroll_hospital_name = hos_details.getString(hos_details.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + "")) + " / " + hos_details.getString(hos_details.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));
            eq_enroll_equipment_name = eq_details.getString(eq_details.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
            txtVw_hosp_name_val.setText(eq_enroll_hospital_name);
            txtVw_Equip_name_val.setText(eq_enroll_equipment_name);
            trn_service_details.close();

            Trn_Voice_Of_Customer trn_voice_of_customer = new Trn_Voice_Of_Customer(getActivity());
            trn_voice_of_customer.open();
            Cursor check_exist = trn_voice_of_customer.fetchBy_Voc_Eq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
            if (check_exist.getCount() > 0) {
                String voc_str_type = check_exist.getString(check_exist.getColumnIndex("" + BusinessAccessLayer.TYPE + ""));
                String voc_in_brief = check_exist.getString(check_exist.getColumnIndex("" + BusinessAccessLayer.IN_BRIEF + ""));
                eTxt_voice_of_customer_brief.setText(voc_in_brief);
                System.out.println("voc_str_type -- " + voc_str_type);
                if (voc_str_type.length() > 0) {
                    spn_voice_of_customer_type.setSelection(Integer.parseInt(voc_typeHashMap.get(voc_str_type)));
                } else {
                    spn_voc_type_Str = voc_type_list[0];
                    spn_voice_of_customer_type.setSelection(Integer.parseInt(voc_typeHashMap.get(voc_str_type)));
                }
                btn_fab_vocMstSave.setImageResource(R.drawable.edit);
            } else {
                btn_fab_vocMstSave.setImageResource(R.drawable.save);
            }
            trn_voice_of_customer.close();
        }
    }

    private void getVocType() {

        for (int i = 0; i < voc_type_list.length; i++) {
            if (i == 0) {
                voc_typeHashMap.put("Select", "" + i);

            } else {
                voc_typeHashMap.put(voc_type_list[i], "" + i);

            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fab_vocMstSave:
                if (spn_voc_type_Str.length() == 0) {
                    showValidationDialog(getActivity(), "Choose type");
                } else {
                    if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID.equals("")) {
                        showValidationDialog(getActivity(), "Please add equipment before adding voice of customer");
                    } else {
                        update_voc_details();
                    }
                }
                break;
            case R.id.btn_fab_VocClear:
                clearFields();
                break;
            default:
                break;
        }
    }

    private void update_voc_details() {
        String str_in_brief = eTxt_voice_of_customer_brief.getText().toString().trim();

        Trn_Voice_Of_Customer trn_voice_of_customer = new Trn_Voice_Of_Customer(getActivity());
        trn_voice_of_customer.open();
        Cursor cursor = trn_voice_of_customer.fetch();
        generatedVocId = getVocId(cursor.getCount());

        Cursor check_exist = trn_voice_of_customer.fetchBy_Voc_Eq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        if (check_exist.getCount() > 0) {
            String update_voc_id = check_exist.getString(check_exist.getColumnIndex("" + BusinessAccessLayer.VOC_ID + ""));
            String voc_created_on = check_exist.getString(check_exist.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
            String voc_created_by = check_exist.getString(check_exist.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
            String fetchSyncStatus = check_exist.getString(check_exist.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
            String flagStatusVoc = "";
            if (fetchSyncStatus.equalsIgnoreCase("0")) {
                flagStatusVoc = "0";
            } else {
                flagStatusVoc = "1";
            }

            boolean update_voc_details = trn_voice_of_customer.update_voc_detail(
                    update_voc_id,
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                    BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                    spn_voc_type_Str,
                    str_in_brief,
                    "1",
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    voc_created_by,
                    voc_created_on,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());

            if (update_voc_details == true) {
                showValidationDialog(getActivity(), "Voice of Customer updated successfully");
            } else {
                showValidationDialog(getActivity(), "Voice of Customer update failed ");
            }
            trn_voice_of_customer.close();
        } else {
            /*if(isPreview==true) {
                showValidationDialog(getActivity(), "Please verify all data before you submit");
                isPreview=false;
            }
            else {*/
                long insert_voc_details = trn_voice_of_customer.insert_voc_details(
                        generatedVocId,
                        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                        BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                        spn_voc_type_Str,
                        str_in_brief,
                        BusinessAccessLayer.FLAG_VALUE,
                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                        BusinessAccessLayer.mUserId,
                        getCurrentDateWithTime(),
                        BusinessAccessLayer.mUserId,
                        getCurrentDateWithTime());

                if (insert_voc_details != 0) {
                    showValidationDialog(getActivity(), "Voice of customer added successfully");
                    btn_fab_vocMstSave.setImageResource(R.drawable.edit);
                } else {
                    showValidationDialog(getActivity(), "Voice of customer add failed");
                }
                trn_voice_of_customer.close();
            //    isPreview=true;
           // }
        }
    }

    private void clearFields() {
        eTxt_voice_of_customer_brief.setText("");
        spn_voice_of_customer_type.setSelection(0);
    }

    private String getVocId(int count) {
        String imeino = setIMEIno();
        String timeStamp = getUnixTimeStamp();
        String finalId = "VOC_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
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

    private class FilterAdapter extends ArrayAdapter<String> {

        public FilterAdapter(Context context, String[] CateogoryDetails) {
            super(context, android.R.layout.simple_spinner_item, CateogoryDetails);

//            categoryList=CateogoryDetails;
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

    }
}
