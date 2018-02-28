package org.next.equmed.uil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.bal.FileChooser;
import org.next.equmed.dal.Trn_Consumables;
import org.next.equmed.dal.Trn_Documents;
import org.next.equmed.dal.Trn_Images;
import org.next.equmed.dal.Trn_Service_Details;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by next on 8/3/16.
 * Created by Muralidharan
 */
public class ConsumablesFragment extends UserInterfaceLayer implements View.OnClickListener {

    Typeface calibri_typeface,calibri_bold_typeface;

    TextView txtVw_consumables_current_stock,txtVw_consumables_uom,txtVw_consumables_name,txtVw_consumables_description,txtVw_consumables_type_of_usage,txtVw_consumables_usage_parameter,txtVw_consumables_quantity,txtVw_consumables_notes,txtVw_noconsumablesFound;
    EditText eTxt_consumables_name,eTxt_consumables_description,eTxt_consumables_quantity,eTxt_consumables_notes,eTxt_consumables_current_stock;
    RadioGroup rBtn_consumables_type_of_usage;
    RadioButton rBtn_time_based,rBtn_usage_based;
    Spinner spn_consumables_usage_parameter,spn_consumables_uom;

    FloatingActionButton btn_fab_consumableMstSave,btn_fab_ConsumDelete,btn_fab_consumableMstAddNew,btn_fab_ConsumablesClear;
    ListView list_consumables;
    LinearLayout layout_listDetails,consumables_add_edit_layout,consumables_eq_hos_name;

    String selectedCons_current_stock,selectedCons_uom,selectedCons_name,selectedCons_description,selectedType_ofUsage,selectedCons_usage_param,selectedCons_quantity,selectedCons_notes,fetchSyncStatus,generatedConsumablesId,eq_enroll_hospital_name,eq_enroll_equipment_name,selectedConsId,selected_conCreatedBy,selected_conCreatedOn;

    String spn_Con_Usage_paramStr="",spn_Con_uom_str="";
    String[] Consumables_usage_Parameter = {"Select Usage Parameter","Daily", "Weekly", "Monthly", "Yearly"};
    String[] Consumables_uom = {"Nos", "Ltrs", "Kgms", "Gms","Cmtr","Mtr"};
    HashMap<String, String> Consumables_usage_ParameterHashMap = new HashMap<String, String>();
    HashMap<String, String> Consumables_uom_HashMap = new HashMap<String, String>();

    private static final String TAG = "ConsumablesFragment";
    ArrayList<Bean> ConsumablesArraylist = new ArrayList<>();

    boolean isAddedNew = true;
    boolean isUpdate = false;
   // boolean isPreview = true;


    LinearLayout lLayout_consumables_createdBy, lLayout_consumables_createdOn;
    TextView txtVw_consumables_createdBy, txtVw_consumables_createdOn;
    EditText eTxt_consumables_createdBy, eTxt_consumables_createdOn;
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
                R.layout.consumables_enrollment, container, false);

        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        BusinessAccessLayer.bug_class_name = "ConsumablesFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        getViewCasting(rootView);

        return rootView;
    }

    private void getViewCasting(View rootView_consumables) {

        String consumables_name = "Name";
        String usage_paramenter = "Usage Parameter";

        String asterisk = "<font color='#EE0000'> *</font>";

        list_consumables = (ListView) rootView_consumables.findViewById(R.id.list_consumables);
        layout_listDetails = (LinearLayout) rootView_consumables.findViewById(R.id.layout_listDetails);
        consumables_add_edit_layout = (LinearLayout) rootView_consumables.findViewById(R.id.consumables_add_edit_layout);
        consumables_eq_hos_name = (LinearLayout) rootView_consumables.findViewById(R.id.consumables_eq_hos_name);

        txtVw_consumables_name = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_name);
        eTxt_consumables_name = (EditText) rootView_consumables.findViewById(R.id.eTxt_consumables_name);
        txtVw_consumables_description = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_description);
        eTxt_consumables_description = (EditText) rootView_consumables.findViewById(R.id.eTxt_consumables_description);
        rBtn_consumables_type_of_usage = (RadioGroup) rootView_consumables.findViewById(R.id.rBtn_consumables_type_of_usage);
        rBtn_time_based=(RadioButton) rootView_consumables.findViewById(R.id.rBtn_time_based);
        rBtn_usage_based=(RadioButton) rootView_consumables.findViewById(R.id.rBtn_usage_based);
        txtVw_consumables_type_of_usage = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_type_of_usage);
        txtVw_consumables_usage_parameter = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_usage_parameter);
        spn_consumables_usage_parameter = (Spinner) rootView_consumables.findViewById(R.id.spn_consumables_usage_parameter);
        spn_consumables_uom = (Spinner) rootView_consumables.findViewById(R.id.spn_consumables_uom);
        txtVw_consumables_quantity = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_quantity);
        eTxt_consumables_quantity = (EditText) rootView_consumables.findViewById(R.id.eTxt_consumables_quantity);
        txtVw_consumables_notes = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_notes);
        eTxt_consumables_notes = (EditText) rootView_consumables.findViewById(R.id.eTxt_consumables_notes);

        txtVw_consumables_current_stock = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_current_stock);
        txtVw_consumables_uom = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_uom);
        eTxt_consumables_current_stock = (EditText) rootView_consumables.findViewById(R.id.eTxt_consumables_current_stock);

        txtVw_consumables_name.setText(Html.fromHtml(consumables_name + asterisk));
        txtVw_consumables_usage_parameter.setText(Html.fromHtml(usage_paramenter + asterisk));

        //      aravinth

        lLayout_consumables_createdBy = (LinearLayout) rootView_consumables.findViewById(R.id.lLayout_consumables_createdBy);
        lLayout_consumables_createdOn = (LinearLayout) rootView_consumables.findViewById(R.id.lLayout_consumables_createdOn);
        txtVw_consumables_createdBy = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_createdBy);
        txtVw_consumables_createdOn = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_createdOn);
        eTxt_consumables_createdBy = (EditText) rootView_consumables.findViewById(R.id.eTxt_consumables_createdBy);
        eTxt_consumables_createdOn = (EditText) rootView_consumables.findViewById(R.id.eTxt_consumables_createdOn);
        txtVw_consumables_createdBy.setTypeface(calibri_typeface);
        txtVw_consumables_createdOn.setTypeface(calibri_typeface);
        eTxt_consumables_createdBy.setTypeface(calibri_typeface);
        eTxt_consumables_createdOn.setTypeface(calibri_typeface);

        txtVw_consumables_name.setTypeface(calibri_typeface);
        eTxt_consumables_name.setTypeface(calibri_typeface);
        txtVw_consumables_description.setTypeface(calibri_typeface);
        eTxt_consumables_description.setTypeface(calibri_typeface);
        rBtn_time_based.setTypeface(calibri_typeface);
        rBtn_usage_based.setTypeface(calibri_typeface);
        txtVw_consumables_type_of_usage.setTypeface(calibri_typeface);
        txtVw_consumables_usage_parameter.setTypeface(calibri_typeface);
        txtVw_consumables_quantity.setTypeface(calibri_typeface);
        eTxt_consumables_quantity.setTypeface(calibri_typeface);
        txtVw_consumables_notes.setTypeface(calibri_typeface);
        eTxt_consumables_notes.setTypeface(calibri_typeface);
        txtVw_consumables_current_stock.setTypeface(calibri_typeface);
        txtVw_consumables_uom.setTypeface(calibri_typeface);
        eTxt_consumables_current_stock.setTypeface(calibri_typeface);

        btn_fab_consumableMstSave = (FloatingActionButton) rootView_consumables.findViewById(R.id.btn_fab_consumableMstSave);
        btn_fab_ConsumDelete = (FloatingActionButton) rootView_consumables.findViewById(R.id.btn_fab_ConsumDelete);
        btn_fab_consumableMstAddNew = (FloatingActionButton) rootView_consumables.findViewById(R.id.btn_fab_consumableMstAddNew);
        btn_fab_ConsumablesClear = (FloatingActionButton) rootView_consumables.findViewById(R.id.btn_fab_ConsumablesClear);

        btn_fab_consumableMstAddNew.setOnClickListener(this);
        btn_fab_consumableMstSave.setOnClickListener(this);
        btn_fab_ConsumDelete.setOnClickListener(this);
        btn_fab_ConsumablesClear.setOnClickListener(this);

        TextView txtVw_hosp_name = (TextView) rootView_consumables.findViewById(R.id.txtVw_hosp_name);
        TextView txtVw_hosp_name_val = (TextView) rootView_consumables.findViewById(R.id.txtVw_hosp_name_val);
        TextView txtVw_Equip_name = (TextView) rootView_consumables.findViewById(R.id.txtVw_Equip_name);
        TextView txtVw_Equip_name_val = (TextView) rootView_consumables.findViewById(R.id.txtVw_Equip_name_val);
        TextView txtVw_hosp_name1 = (TextView) rootView_consumables.findViewById(R.id.txtVw_hosp_name1);
        EditText txtVw_hosp_name_val1 = (EditText) rootView_consumables.findViewById(R.id.txtVw_hosp_name_val1);
        TextView txtVw_Equip_name1 = (TextView) rootView_consumables.findViewById(R.id.txtVw_Equip_name1);
        EditText txtVw_Equip_name_val1 = (EditText) rootView_consumables.findViewById(R.id.txtVw_Equip_name_val1);
        TextView txtVw_consumables_serialNo1 = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_serialNo1);
        TextView txtVw_Consumables_name = (TextView) rootView_consumables.findViewById(R.id.txtVw_Consumables_name);
        TextView txtVw_consumables_typeOfUsage = (TextView) rootView_consumables.findViewById(R.id.txtVw_consumables_typeOfUsage);
        TextView update_consumable = (TextView) rootView_consumables.findViewById(R.id.update_consumable);
        txtVw_noconsumablesFound = (TextView) rootView_consumables.findViewById(R.id.txtVw_noconsumablesFound);

        txtVw_hosp_name.setTypeface(calibri_typeface);
        txtVw_hosp_name_val.setTypeface(calibri_typeface);
        txtVw_Equip_name.setTypeface(calibri_typeface);
        txtVw_Equip_name_val.setTypeface(calibri_typeface);
        txtVw_hosp_name1.setTypeface(calibri_typeface);
        txtVw_hosp_name_val1.setTypeface(calibri_typeface);
        txtVw_Equip_name1.setTypeface(calibri_typeface);
        txtVw_Equip_name_val1.setTypeface(calibri_typeface);
        txtVw_consumables_serialNo1.setTypeface(calibri_bold_typeface);
        txtVw_Consumables_name.setTypeface(calibri_bold_typeface);
        txtVw_consumables_typeOfUsage.setTypeface(calibri_bold_typeface);
        update_consumable.setTypeface(calibri_bold_typeface);

        FilterAdapter adapterCons_UsageParam = new FilterAdapter(getActivity(), Consumables_usage_Parameter);
        spn_consumables_usage_parameter.setAdapter(adapterCons_UsageParam);
        spn_consumables_usage_parameter.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spn_Con_Usage_paramStr = "";
                } else {
//                    spn_insCondStr = ""+position;
                    spn_Con_Usage_paramStr = Consumables_usage_Parameter[position];

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        FilterAdapter adapterCons_Uom = new FilterAdapter(getActivity(), Consumables_uom);
        spn_consumables_uom.setAdapter(adapterCons_Uom);
        spn_consumables_uom.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    spn_Con_uom_str = "";
                } else {
//                    spn_insCondStr = ""+position;
                    spn_Con_uom_str = Consumables_uom[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID.equals("")) {
            txtVw_noconsumablesFound.setVisibility(View.VISIBLE);
            layout_listDetails.setVisibility(View.GONE);
            consumables_eq_hos_name.setVisibility(View.GONE);
        } else {
          //  consumables_eq_hos_name.setVisibility(View.VISIBLE);
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
            getConsumablesDetails();
        }
    }

    void getConsumablesDetails() {
        Trn_Consumables trn_consumables = new Trn_Consumables(getActivity());
        trn_consumables.open();
        Cursor mCr_consumable_details = trn_consumables.fetchByConsEq_Enroll_Id(BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
        System.out.println("status count:" + mCr_consumable_details.getCount());
        ConsumablesArraylist.clear();
        if (mCr_consumable_details.getCount() > 0) {

            for (int eqip_status = 0; eqip_status < mCr_consumable_details.getCount(); eqip_status++) {
                mCr_consumable_details.moveToPosition(eqip_status);
                Bean mBean = new Bean();

                String consumables_id = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_ID + ""));
                mBean.setConsumables_id(consumables_id);

                String consumables_name = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.NAME + ""));
                mBean.setConsumables_name(consumables_name);

                String consumables_description = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.DESCRIPTION + ""));
                mBean.setConsumables_description(consumables_description);

                String consumables_type_of_usage = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.TYPE_OF_USAGE + ""));
                mBean.setConsumables_type_of_usage(consumables_type_of_usage);

                String consumables_usage_parameter = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.USAGE_PARAMETER + ""));
                mBean.setConsumables_usage_parameter(consumables_usage_parameter);

                String consumables_quantity = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.QUANTITY + ""));
                mBean.setConsumables_quantity(consumables_quantity);

                String consumables_uom = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_UOM + ""));
                mBean.setConsumables_uom(consumables_uom);

                String consumables_current_stock = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.CONSUMABLES_CURRENT_STOCK + ""));
                mBean.setConsumables_current_stock(consumables_current_stock);

                String consumables_notes = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.CONSUMABLE_NOTES + ""));
                mBean.setConsumables_notes(consumables_notes);

                String flag = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                mBean.setFlag(flag);

                String sync_status = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                mBean.setSync_status(sync_status);

                String created_by = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                mBean.setCreated_by(created_by);

                String created_on = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                mBean.setCreated_on(created_on);

                String modified_by = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                mBean.setModified_by(modified_by);

                String modified_on = mCr_consumable_details.getString(mCr_consumable_details.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                mBean.setModified_on(modified_on);

                ConsumablesArraylist.add(mBean);
            }
            trn_consumables.close();
            System.out.println("ConsumablesArraylist Size" + ConsumablesArraylist.size());
        }

        if (ConsumablesArraylist.size() > 0) {
            ConsumablesListAdapter adapter = new ConsumablesListAdapter(getActivity(), ConsumablesArraylist);
            list_consumables.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            layout_listDetails.setVisibility(View.VISIBLE);
            txtVw_noconsumablesFound.setVisibility(View.GONE);
            consumables_eq_hos_name.setVisibility(View.VISIBLE);
        } else {
            layout_listDetails.setVisibility(View.GONE);
            list_consumables.setAdapter(null);
            txtVw_noconsumablesFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fab_consumableMstAddNew:
                hideKeyBoard();
                System.out.println("BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID"+BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID);
                if (BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID != "") {
                    System.out.println("ConsumablesArraylist.size()==" + ConsumablesArraylist.size());
                    if (ConsumablesArraylist.size() >= 10) {
                        showContactUsDialog(getActivity(), "You can't add more than 10 consumables per equipment");
                    } else {

                        btn_fab_consumableMstSave.setImageResource(R.drawable.save);
                        txtVw_noconsumablesFound.setVisibility(View.GONE);
                        layout_listDetails.setVisibility(View.GONE);
                        consumables_add_edit_layout.setVisibility(View.VISIBLE);
                        btn_fab_consumableMstSave.setVisibility(View.VISIBLE);
                        btn_fab_consumableMstAddNew.setVisibility(View.GONE);
                        consumables_eq_hos_name.setVisibility(View.GONE);
                        btn_fab_ConsumDelete.setVisibility(View.GONE);
                        getUsageParameter();
                        getuom();
                    }
                } else {
                    showContactUsDialog(getActivity(), "Please add equipment details before adding consumables");
                }
                break;

            case R.id.btn_fab_consumableMstSave:

                if (eTxt_consumables_name.length() == 0) {
                    showValidationDialog(getActivity(), "Enter consumables name");
                } else if (spn_Con_Usage_paramStr.length() == 0) {
                    showValidationDialog(getActivity(), "Choose usage parameter");
                } else {
                    System.out.println("isUpdate == "+isUpdate);
                    if(isUpdate==true) {
                        update_consumables_details();
                    }
                    else
                    {
                       /* if(isPreview==true)
                        {
                            showValidationDialog(getActivity(), "Please verify all data before you submit");
                            btn_fab_ConsumablesClear.setVisibility(View.VISIBLE);
                            isPreview=false;
                        }
                        else {*/
                            insert_consumables_detail();
                         //   btn_fab_ConsumablesClear.setVisibility(View.GONE);
                       // }
                    }
                }
                break;
            case R.id.btn_fab_ConsumDelete:
                hideKeyBoard();
                isAddedNew = false;
                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                break;
            case R.id.btn_fab_ConsumablesClear:
                hideKeyBoard();
                clearFields();
                break;
            default:
                break;
        }
    }

    private void getUsageParameter() {

        for (int i = 0; i < Consumables_usage_Parameter.length; i++) {
            if (i == 0) {
                Consumables_usage_ParameterHashMap.put("Select", "" + i);

            } else {
                Consumables_usage_ParameterHashMap.put(Consumables_usage_Parameter[i], "" + i);

            }

        }
    }

    private void getuom() {

        for (int i = 0; i < Consumables_uom.length; i++) {

                Consumables_uom_HashMap.put(Consumables_uom[i], "" + i);

        }
    }

    private String getConsumablesId(int count) {
        String imeino = setIMEIno().substring(0, 5);
        String timeStamp = getUnixTimeStamp();
        String finalId = "CONS_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }
    private void insert_consumables_detail() {
        String etx_cons_name = eTxt_consumables_name.getText().toString().trim();
        String etx_cons_des = eTxt_consumables_description.getText().toString().trim();
        String etx_cons_qty = eTxt_consumables_quantity.getText().toString().trim();
        String etx_cons_notes = eTxt_consumables_notes.getText().toString().trim();
        String etx_cons_current_stock = eTxt_consumables_current_stock.getText().toString().trim();

        String cons_type_of_usage="";
        if (rBtn_time_based.isChecked()) {
            cons_type_of_usage = "Time Based";
        }
        if(rBtn_usage_based.isChecked())
        {
            cons_type_of_usage = "Usage Based";
        }

        Trn_Consumables trn_consumables = new Trn_Consumables(getActivity());
        trn_consumables.open();
        Cursor cursor = trn_consumables.fetch();
        generatedConsumablesId = getConsumablesId(cursor.getCount());

        System.out.println("BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID === " + BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID);

        long insert_consumables_detail = trn_consumables.insert_consumables(
                generatedConsumablesId,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                etx_cons_name,
                etx_cons_des,
                cons_type_of_usage,
                spn_Con_Usage_paramStr,
                etx_cons_qty,
                spn_Con_uom_str,
                etx_cons_current_stock,
                etx_cons_notes,
                BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime(),
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (insert_consumables_detail != 0) {
            isAddedNew = true;
            clearFields();
            showContactUsDialog(getActivity(), "Consumables added successfully");
        } else {
            showValidationDialog(getActivity(), "Consumables add failed");
        }
        trn_consumables.close();
    }

    private void update_consumables_details() {
        String etx_cons_name = eTxt_consumables_name.getText().toString().trim();
        String etx_cons_des = eTxt_consumables_description.getText().toString().trim();
        String etx_cons_qty = eTxt_consumables_quantity.getText().toString().trim();
        String etx_cons_notes = eTxt_consumables_notes.getText().toString().trim();
        String etx_cons_current_stock = eTxt_consumables_current_stock.getText().toString().trim();

        String cons_type_of_usage="";
        if (rBtn_time_based.isChecked()) {
            cons_type_of_usage = "Time Based";
        }
        if(rBtn_usage_based.isChecked())
        {
            cons_type_of_usage = "Usage Based";
        }

        Trn_Consumables trn_consumables = new Trn_Consumables(getActivity());
        trn_consumables.open();

        String flagStatusEqpmnt = "";
        if (fetchSyncStatus.equalsIgnoreCase("0")) {
            flagStatusEqpmnt = "0";
        } else {
            flagStatusEqpmnt = "1";
        }

        boolean update_service_detail = trn_consumables.update_consumables_detail(
                selectedConsId,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID,
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ID,
                etx_cons_name,
                etx_cons_des,
                cons_type_of_usage,
                spn_Con_Usage_paramStr,
                etx_cons_qty,
                spn_Con_uom_str,
                etx_cons_current_stock,
                etx_cons_notes,
                "1",
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                selected_conCreatedBy,
                selected_conCreatedOn,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());

        if (update_service_detail == true) {
            clearFields();
            isUpdate = false;
            showContactUsDialog(getActivity(), "Consumables updated successfully");
        } else {
            showValidationDialog(getActivity(), "Consumables update failed ");
        }
        trn_consumables.close();
    }

    private class ConsumablesListAdapter extends BaseAdapter {
        ArrayList<Bean> consumablesArraylist;
        private LayoutInflater l_InflaterConsumablesList;

        public ConsumablesListAdapter(Context context, ArrayList<Bean> consumableDetails) {

            consumablesArraylist = consumableDetails;
            l_InflaterConsumablesList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return consumablesArraylist.size();
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
                convertView = l_InflaterConsumablesList.inflate(
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
            holder.txtV_service_date_time.setText(consumablesArraylist.get(position).getConsumables_type_of_usage());
            holder.txtV_service_type_inflate.setText(consumablesArraylist.get(position).getConsumables_name());
            fetchSyncStatus = consumablesArraylist.get(position).getSync_status();

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
                    isUpdate = true;
                    selectedConsId = consumablesArraylist.get(position).getConsumables_id();
                    selectedCons_name = consumablesArraylist.get(position).getConsumables_name();
                    selectedCons_description = consumablesArraylist.get(position).getConsumables_description();
                    selectedType_ofUsage = consumablesArraylist.get(position).getConsumables_type_of_usage();
                    selectedCons_usage_param = consumablesArraylist.get(position).getConsumables_usage_parameter();
                    selectedCons_uom = consumablesArraylist.get(position).getConsumables_uom();
                    selectedCons_quantity = consumablesArraylist.get(position).getConsumables_quantity();
                    selectedCons_notes = consumablesArraylist.get(position).getConsumables_notes();
                    selectedCons_current_stock = consumablesArraylist.get(position).getConsumables_current_stock();

                    getUsageParameter();
                    getuom();
                    System.out.println("selectedCons_usage_param" + selectedCons_usage_param);

                    eTxt_consumables_name.setText(selectedCons_name);
                    eTxt_consumables_description.setText(selectedCons_description);
                    eTxt_consumables_quantity.setText(selectedCons_quantity);
                    eTxt_consumables_notes.setText(selectedCons_notes);
                    eTxt_consumables_current_stock.setText(selectedCons_current_stock);

                    if (selectedType_ofUsage.equalsIgnoreCase("Time Based")) {
                        rBtn_time_based.setChecked(true);
                    } else {
                        rBtn_usage_based.setChecked(true);
                    }
                    System.out.println("Consumables_usage_ParameterHashMap "+Consumables_usage_ParameterHashMap);
                    if (selectedCons_usage_param.length() > 0) {
                        spn_consumables_usage_parameter.setSelection(Integer.parseInt(Consumables_usage_ParameterHashMap.get(selectedCons_usage_param)));

                    } else {
                        spn_Con_Usage_paramStr = Consumables_usage_Parameter[0];
                        spn_consumables_usage_parameter.setSelection(Integer.parseInt(Consumables_usage_ParameterHashMap.get(selectedCons_usage_param)));
                    }


                    if (selectedCons_uom.length() > 0) {
                        spn_consumables_uom.setSelection(Integer.parseInt(Consumables_uom_HashMap.get(selectedCons_uom)));

                    } else {
                        spn_Con_uom_str = Consumables_uom[0];
                        spn_consumables_uom.setSelection(0);
                    }


                    selected_conCreatedBy = consumablesArraylist.get(position).getCreated_by();
                    selected_conCreatedOn = consumablesArraylist.get(position).getCreated_on();

                    btn_fab_consumableMstSave.setImageResource(R.drawable.edit);
                    consumables_add_edit_layout.setVisibility(View.VISIBLE);
                    consumables_eq_hos_name.setVisibility(View.VISIBLE);
                    btn_fab_consumableMstSave.setVisibility(View.VISIBLE);
                    btn_fab_consumableMstAddNew.setVisibility(View.GONE);
                    txtVw_noconsumablesFound.setVisibility(View.GONE);
                    layout_listDetails.setVisibility(View.GONE);
                    btn_fab_ConsumDelete.setVisibility(View.VISIBLE);
                    consumables_eq_hos_name.setVisibility(View.GONE);


                    lLayout_consumables_createdBy.setVisibility(View.VISIBLE);
                    lLayout_consumables_createdOn.setVisibility(View.VISIBLE);
                    if (selected_conCreatedBy.equalsIgnoreCase("1")) {
                        eTxt_consumables_createdBy.setText("Admin");
                    } else {
                        eTxt_consumables_createdBy.setText(""+getUserNameByUserId(selected_conCreatedBy));
                    }
                    if (selected_conCreatedOn.length() > 0) {
                        eTxt_consumables_createdOn.setText(getShowDate(selected_conCreatedOn));
                    }

                    eTxt_consumables_createdOn.setText(selected_conCreatedOn);
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
                    deleteConsumablesDetails();
                    clearFields();
                    getConsumablesDetails();
                    btn_fab_ConsumDelete.setVisibility(View.GONE);
                    consumables_add_edit_layout.setVisibility(View.GONE);
                    lLayout_consumables_createdBy.setVisibility(View.GONE);
                    lLayout_consumables_createdOn.setVisibility(View.GONE);
                    btn_fab_consumableMstSave.setVisibility(View.GONE);
                    btn_fab_consumableMstAddNew.setVisibility(View.VISIBLE);
                } else {
                    isUpdate = false;
                    getConsumablesDetails();
                    consumables_add_edit_layout.setVisibility(View.GONE);
                    lLayout_consumables_createdBy.setVisibility(View.GONE);
                    lLayout_consumables_createdOn.setVisibility(View.GONE);
                    btn_fab_consumableMstSave.setVisibility(View.GONE);
                    btn_fab_consumableMstAddNew.setVisibility(View.VISIBLE);
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

    private void deleteConsumablesDetails()
    {
        System.out.println("selectedConsId"+selectedConsId);
        Trn_Consumables consumables = new Trn_Consumables(getActivity());
        consumables.open();
        Cursor del_cursor = consumables.update_flag_By_consumables_Id(selectedConsId);
        consumables.close();
    }

    private void clearFields() {
        eTxt_consumables_name.setText("");
        eTxt_consumables_description.setText("");
        eTxt_consumables_notes.setText("");
        eTxt_consumables_quantity.setText("");
        eTxt_consumables_current_stock.setText("");
        rBtn_time_based.setChecked(true);
        spn_consumables_usage_parameter.setSelection(0);
        spn_consumables_uom.setSelection(0);

        btn_fab_ConsumDelete.setVisibility(View.GONE);
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(
                getActivity().getWindow().getDecorView().getWindowToken(), 0);
    }

    private class FilterAdapter extends ArrayAdapter<String> {

        public FilterAdapter(Context context, String[] CateogoryDetails) {
            super(context, android.R.layout.simple_spinner_item, CateogoryDetails);

//            categoryList=CateogoryDetails;
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }

    }
}
