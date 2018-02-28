package org.next.equmed.uil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;


import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Equipment_Enrollment;

import java.util.ArrayList;

/*
 * @Subject Master_Hospt_Enroll_Fragment
 * @Created_By Gokul Raj
 * @Created_On 01-12-2015
 * @Updated_By
 * @Updated_On
 * @Description The Hospital details are entered and saved in the database.
 * @Note
 */
public class Master_Hospt_Enroll_Fragment extends UserInterfaceLayer implements View.OnClickListener {

    TextView txtVw_hosptName, txtVw_locationName, txtVw_hosptDescr, txtVw_addrOne, txtVw_addrTwo, txtVw_addrThree, txtVw_state, txtVw_country, txtVw_phnNoOne,
            txtVw_phnNoTwo, txtVw_hosptEmail, txtVw_hosptNotes, txtVw_active, txtVw_activeYesNo, txtVw_noHospitalFound,txtVw_standard_equipments,txtVw_standard_equipmentsYesNo;

    EditText eTxt_hosptName, eTxt_locationName, eTxt_hosptDescr, eTxt_addrOne, eTxt_addrTwo, eTxt_addrThree, eTxt_state, eTxt_country, eTxt_phnNoOne,
            eTxt_phnNoTwo, eTxt_hosptEmail, eTxt_hosptNotes;

    Switch switch_active,switch_standard_equipments;

    String str_eTxt_hosptName, str_eTxt_locationName, str_eTxt_addrOne, str_eTxt_state, str_eTxt_country, str_eTxt_phnNoOne, str_eTxt_hosptDescr, str_eTxt_addrTwo, str_eTxt_addrThree, str_eTxt_phnNoTwo,
            str_eTxt_hosptEmail, str_eTxt_hosptNotes, str_swt_IsActive = "Y",str_swt_StandardEquipments = "N";

//    Button btn_hosptenrollSave;

    FloatingActionButton btn_fab_hosptenrollSave, btn_fab_hosptenrollNew, btn_fab_hosptenrollDelete;
    static String hosptEnrollId = "";
    private Object showToast;
    ScrollView scroll_main_hospital;
    RelativeLayout layout_hospitalView;
    ListView list_HospitalDetails;
    ArrayList<Bean> hospitalDetailsArray = new ArrayList<Bean>();
    int selectedPostion = 0;

    boolean isFromUpdate = false;
    int pageNumber = 0;
    boolean isAddedNew = true;
    String fetchSyncStatus = "";

    Typeface calibri_typeface, calibri_bold_typeface;

    public Master_Hospt_Enroll_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.hosptenroll_activity, container, false);

        BusinessAccessLayer.bug_class_name = "HospitalEnrollFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        getViewCasting(rootView);
        isFromUpdate = false;
        isAddedNew = true;


        getHospitalInformation();

        exportDatabse(BusinessAccessLayer.DATABASE_NAME);


//        getGPSLocation();

        return rootView;
    }

    public void getHospitalInformation() {

        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(getActivity());
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetch();

            hospitalDetailsArray.clear();


            if (mCr_mst_hospital_enroll.getCount() > 0) {
                for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                    mCr_mst_hospital_enroll.moveToPosition(i);

                    Bean beanObj = new Bean();
                    String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                    String hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));

                    String hospital_location = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));

                    String hospital_desc = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_DESC + ""));

                    String hospital_address1 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ADDRESS1 + ""));

                    String hospital_address2 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ADDRESS2 + ""));

                    String hospital_address3 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ADDRESS3 + ""));

                    String hospital_state = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_STATE + ""));

                    String hospital_country = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_COUNTRY + ""));

                    String activeStr = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));
                    String stand_equip = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.STANDARD_EQUIPMENTS + ""));

                    String hospital_phno1 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_PHNO1 + ""));


                    String hospital_phno2 = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_PHNO2 + ""));


                    String hospital_email = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_EMAIL + ""));

                    String hospital_notes = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NOTES + ""));
                    String syncStatus = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));


                    String created_by = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));

                    String created_on = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));


                    beanObj.setHos_name(hospital_name);
                    beanObj.setHos_Id(hospital_id);
                    beanObj.setHos_location(hospital_location);
                    beanObj.setHospital_desc(hospital_desc);
                    beanObj.setHospital_address1(hospital_address1);
                    beanObj.setHospital_address2(hospital_address2);
                    beanObj.setHospital_address3(hospital_address3);
                    beanObj.setHospital_state(hospital_state);
                    beanObj.setHospital_country(hospital_country);
                    beanObj.setHospital_phno1(hospital_phno1);
                    beanObj.setHospital_phno2(hospital_phno2);
                    beanObj.setHospital_email(hospital_email);
                    beanObj.setHospital_notes(hospital_notes);
                    beanObj.setCreated_by(created_by);
                    beanObj.setIsactive(activeStr);
                    beanObj.setstandard_equipments(stand_equip);
                    beanObj.setCreated_on(created_on);
                    beanObj.setSync_status(syncStatus);


                    hospitalDetailsArray.add(beanObj);

                }
                mCr_mst_hospital_enroll.close();

            }

            Mst_Hospital_Enrollment.close();

        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }
        getActiveHospitalInformation();


        if (hospitalDetailsArray.size() > 0) {
            HospitalListAdapter adapter = new HospitalListAdapter(getActivity());
            list_HospitalDetails.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            layout_hospitalView.setVisibility(View.VISIBLE);
            txtVw_noHospitalFound.setVisibility(View.GONE);
            System.out.println("BusinessAccessLayer.editPage in getHospitalInformation if " + BusinessAccessLayer.editPage);

        } else {

            list_HospitalDetails.setAdapter(null);
            layout_hospitalView.setVisibility(View.GONE);
            txtVw_noHospitalFound.setVisibility(View.VISIBLE);
            txtVw_noHospitalFound.setText("No Hospital Details Found");
            System.out.println("BusinessAccessLayer.editPage in getHospitalInformation else " + BusinessAccessLayer.editPage);
        }


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
    // Adapter Class

    private class HospitalListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public HospitalListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return hospitalDetailsArray.size();
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
                        R.layout.inflate_hospital_details, parent, false);


                holder.txtVw_view_Hospital_serialNo = (TextView) convertView.findViewById(R.id.txtVw_view_Hospital_serialNo);

                holder.lLayout_inflate_hospitallist = (LinearLayout) convertView.findViewById(R.id.lLayout_inflate_hospitallist);

                holder.txtVw_view_Hospital_serialNo.setTypeface(calibri_bold_typeface);

                holder.txtVw_HospitalName = (TextView) convertView.findViewById(R.id.txtVw_HospitalName);

                holder.txtVw_HospitalName.setTypeface(calibri_typeface);

                holder.imgV_UpdateData = (ImageView) convertView.findViewById(R.id.imgV_UpdateData);

                holder.imgV_DeleteData = (ImageView) convertView.findViewById(R.id.imgV_DeleteData);
                holder.txtVw_HospitalStatus = (TextView) convertView.findViewById(R.id.txtVw_HospitalStatus);


                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int sNO = position + 1;
            holder.txtVw_HospitalName.setText(hospitalDetailsArray.get(position).getHos_name() + " / " + hospitalDetailsArray.get(position).getHos_location());
            holder.txtVw_view_Hospital_serialNo.setText("" + sNO);
            String statusstr = hospitalDetailsArray.get(position).getIsactive();

            if (statusstr.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {
                statusstr = BusinessAccessLayer.STATUS_ACTIVE;
            } else {
                statusstr = BusinessAccessLayer.STATUS_INACTIVE;
            }

            fetchSyncStatus = hospitalDetailsArray.get(position).getSync_status();
            if(fetchSyncStatus.equals("0"))
            {
                holder.txtVw_view_Hospital_serialNo.setTextColor(Color.parseColor("#ff0000"));
               // holder.lLayout_inflate_hospitallist.setBackgroundColor(Color.parseColor("#ffffb3"));
            }
            else
            {
                holder.txtVw_view_Hospital_serialNo.setTextColor(Color.parseColor("#009933"));
               // holder.lLayout_inflate_hospitallist.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            holder.txtVw_HospitalStatus.setText(statusstr);
            holder.txtVw_HospitalStatus.setTypeface(calibri_typeface);
            holder.txtVw_HospitalStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFromUpdate = true;
                    BusinessAccessLayer.editPage = true;
                    BusinessAccessLayer.UPDATE_GOBACK_FLAG = 1;
                    //if(BusinessAccessLayer.editPage == true){
//                        BusinessAccessLayer.editPage = false;
//                    }else{
//                        BusinessAccessLayer.editPage = true;
//                    }

                    layout_hospitalView.setVisibility(View.GONE);
                    btn_fab_hosptenrollNew.setVisibility(View.GONE);
                    scroll_main_hospital.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollSave.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollSave.setImageResource(R.drawable.edit);
                    btn_fab_hosptenrollDelete.setVisibility(View.GONE);

                    eTxt_hosptName.setText(hospitalDetailsArray.get(position).getHos_name());
                    eTxt_locationName.setText(hospitalDetailsArray.get(position).getHos_location());
                    eTxt_hosptDescr.setText(hospitalDetailsArray.get(position).getHospital_desc());
                    eTxt_addrOne.setText(hospitalDetailsArray.get(position).getHospital_address1());
                    eTxt_addrTwo.setText(hospitalDetailsArray.get(position).getHospital_address2());
                    eTxt_addrThree.setText(hospitalDetailsArray.get(position).getHospital_address3());
                    eTxt_state.setText(hospitalDetailsArray.get(position).getHospital_state());
                    eTxt_country.setText(hospitalDetailsArray.get(position).getHospital_country());
                    eTxt_phnNoOne.setText(hospitalDetailsArray.get(position).getHospital_phno1());
                    eTxt_phnNoTwo.setText(hospitalDetailsArray.get(position).getHospital_phno2());
                    eTxt_hosptEmail.setText(hospitalDetailsArray.get(position).getHospital_email());
                    eTxt_hosptNotes.setText(hospitalDetailsArray.get(position).getHospital_notes());
                    selectedPostion = position;
                    hosptEnrollId = hospitalDetailsArray.get(position).getHos_Id();
                    str_swt_IsActive = hospitalDetailsArray.get(position).getIsactive();

                    str_swt_StandardEquipments = hospitalDetailsArray.get(position).getStandard_Equipments();

                    fetchSyncStatus = hospitalDetailsArray.get(position).getSync_status();
                    if (str_swt_IsActive.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_active.setChecked(true);
                    } else {
                        switch_active.setChecked(false);
                    }

                    if (str_swt_StandardEquipments.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_standard_equipments.setChecked(true);
                    } else {
                        switch_standard_equipments.setChecked(false);
                    }
                    switch_standard_equipments.setClickable(false);

                }
            });

            holder.imgV_UpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFromUpdate = true;
                    BusinessAccessLayer.UPDATE_GOBACK_FLAG = 1;
                    BusinessAccessLayer.editPage = true;
                    /*if(BusinessAccessLayer.editPage == true){
                        BusinessAccessLayer.editPage = false;
                    }else{
                        BusinessAccessLayer.editPage = true;
                    }*/
                    layout_hospitalView.setVisibility(View.GONE);
                    btn_fab_hosptenrollNew.setVisibility(View.GONE);
                    scroll_main_hospital.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollSave.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollSave.setImageResource(R.drawable.edit);
                    btn_fab_hosptenrollDelete.setVisibility(View.GONE);


                    eTxt_hosptName.setText(hospitalDetailsArray.get(position).getHos_name());
                    eTxt_locationName.setText(hospitalDetailsArray.get(position).getHos_location());
                    eTxt_hosptDescr.setText(hospitalDetailsArray.get(position).getHospital_desc());
                    eTxt_addrOne.setText(hospitalDetailsArray.get(position).getHospital_address1());
                    eTxt_addrTwo.setText(hospitalDetailsArray.get(position).getHospital_address2());
                    eTxt_addrThree.setText(hospitalDetailsArray.get(position).getHospital_address3());
                    eTxt_state.setText(hospitalDetailsArray.get(position).getHospital_state());
                    eTxt_country.setText(hospitalDetailsArray.get(position).getHospital_country());
                    eTxt_phnNoOne.setText(hospitalDetailsArray.get(position).getHospital_phno1());
                    eTxt_phnNoTwo.setText(hospitalDetailsArray.get(position).getHospital_phno2());
                    eTxt_hosptEmail.setText(hospitalDetailsArray.get(position).getHospital_email());
                    eTxt_hosptNotes.setText(hospitalDetailsArray.get(position).getHospital_notes());
                    selectedPostion = position;
                    hosptEnrollId = hospitalDetailsArray.get(position).getHos_Id();
                    str_swt_IsActive = hospitalDetailsArray.get(position).getIsactive();
                    str_swt_StandardEquipments = hospitalDetailsArray.get(position).getStandard_Equipments();
                    System.out.println("str_swt_StandardEquipments"+str_swt_StandardEquipments);

                    if (str_swt_IsActive.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_active.setChecked(true);
                    } else {
                        switch_active.setChecked(false);
                    }

                    if (str_swt_StandardEquipments.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_standard_equipments.setChecked(true);
                    } else {
                        switch_standard_equipments.setChecked(false);
                    }

                    switch_standard_equipments.setClickable(false);

                }


            });


            holder.imgV_DeleteData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hosptEnrollId = hospitalDetailsArray.get(position).getHos_Id();
                    isAddedNew = false;

                    showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                }
            });


            return convertView;
        }


        class ViewHolder {
            RelativeLayout rL_UpdateDeleteData;
            TextView txtVw_view_Hospital_serialNo, txtVw_HospitalName, txtVw_HospitalStatus;
            ImageView imgV_UpdateData, imgV_DeleteData;
            LinearLayout lLayout_inflate_hospitallist;

        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//                String cameback="CameBack";
//                intent = new Intent(getActivity(),HomeActivity.class);
//                intent.putExtra("Comingback", cameback);
//                startActivity(intent);
                if (pageNumber == 1) {
                    getHospitalInformation();
                    scroll_main_hospital.setVisibility(View.GONE);
                    layout_hospitalView.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollSave.setVisibility(View.GONE);
                    btn_fab_hosptenrollNew.setVisibility(View.VISIBLE);
                    txtVw_noHospitalFound.setVisibility(View.GONE);
                    isFromUpdate = false;
                    return true;
                }

        }
        return false;
    }

    public void updateHospitalEnrollment(int position) {


        if (eTxt_hosptName.getText().toString().trim().length() > 0) {
            if (!isNumeric(eTxt_hosptName.getText().toString())) {
                if (eTxt_locationName.getText().toString().trim().length() > 0) {
                    if (!isNumeric(eTxt_locationName.getText().toString())) {
                        if (eTxt_addrOne.getText().toString().trim().length() > 0) {
                            if (eTxt_state.getText().toString().trim().length() > 0) {
                                if (eTxt_country.getText().toString().trim().length() > 0) {
                                    if (eTxt_phnNoOne.getText().toString().trim().length() > 0) {
                                        if (eTxt_hosptEmail.getText().toString().trim().length() > 0) {
                                            if (eTxt_hosptEmail.getText().toString().trim().matches(getString(R.string.emailPattern))) {


                                                String update_eTxt_hosptName = eTxt_hosptName.getText().toString();
                                                String update_eTxt_locationName = eTxt_locationName.getText().toString();
                                                String update_eTxt_hosptDescr = eTxt_hosptDescr.getText().toString();
                                                String update_eTxt_addrOne = eTxt_addrOne.getText().toString();
                                                String update_eTxt_addrTwo = eTxt_addrTwo.getText().toString();
                                                String update_eTxt_addrThree = eTxt_addrThree.getText().toString();
                                                String update_eTxt_state = eTxt_state.getText().toString();
                                                String update_eTxt_country = eTxt_country.getText().toString();
                                                String update_eTxt_phnNoOne = eTxt_phnNoOne.getText().toString();
                                                String update_eTxt_phnNoTwo = eTxt_phnNoTwo.getText().toString();
                                                String update_eTxt_hosptEmail = eTxt_hosptEmail.getText().toString();
                                                String update_eTxt_hosptNotes = eTxt_hosptNotes.getText().toString();
                                                // String update_swt_IsActive = txtVw_activeYesNo.getText().toString();
                                                String update_createdby = hospitalDetailsArray.get(position).getCreated_by();
                                                String update_createdon = hospitalDetailsArray.get(position).getCreated_on();


                                                Mst_Hospital_Enrollment update_hospital_enrollment = new Mst_Hospital_Enrollment(getActivity());
                                                update_hospital_enrollment.open();

                                                Cursor cur = update_hospital_enrollment.fetchbynamesupdate(update_eTxt_hosptName, update_eTxt_locationName, hosptEnrollId);
                                                if (cur.getCount() == 0) {

                                                    String flagStatus = "";
                                                    if (fetchSyncStatus.equalsIgnoreCase("0")) {
                                                        flagStatus = "0";
                                                    } else {
                                                        flagStatus = "1";
                                                    }
                                                    boolean updateHospitalValues = update_hospital_enrollment.update_Hospital_Details(
                                                            hosptEnrollId,
                                                            update_eTxt_hosptName,
                                                            update_eTxt_locationName,
                                                            update_eTxt_hosptDescr,
                                                            update_eTxt_addrOne,
                                                            update_eTxt_addrTwo,
                                                            update_eTxt_addrThree,
                                                            update_eTxt_state,
                                                            update_eTxt_country,
                                                            update_eTxt_phnNoOne,
                                                            update_eTxt_phnNoTwo,
                                                            update_eTxt_hosptEmail,
                                                            update_eTxt_hosptNotes,
                                                            str_swt_IsActive,
                                                            str_swt_StandardEquipments,
                                                            BusinessAccessLayer.SYNC_STATUS_VALUE, flagStatus,
                                                            update_createdby,
                                                            update_createdon,
                                                            BusinessAccessLayer.USER_ID,
                                                            getCurrentDateTime());

                                                    Mst_Hospital_Enrollment.close();


                                                    if (updateHospitalValues == true) {
                                                        isAddedNew = true;

                                                        hideKeyBoard();

                                                        isFromUpdate = false;
                                                        showContactUsDialog(getActivity(), "Hospital updated successfully");

//
                                                    } else {
                                                        showValidationDialog(getActivity(), "Hospital update failed");
                                                    }
                                                } else {
                                                    showValidationDialog(getActivity(), "Hospital and location name already exist");
                                                    eTxt_hosptName.requestFocus();
                                                }

                                            } else {
                                                showValidationDialog(getActivity(), "Invalid email");
                                                eTxt_hosptEmail.requestFocus();
                                                // Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT);
                                            }
                                        } else {
                                            showValidationDialog(getActivity(), "Please enter email");
                                            eTxt_hosptEmail.requestFocus();
                                            // Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT);
                                        }
                                    } else {

                                        showValidationDialog(getActivity(), "Please enter phone no");
                                        eTxt_phnNoOne.requestFocus();
                                        //   Toast.makeText(this, "Please enter the phone No", Toast.LENGTH_SHORT);
                                    }

                                } else {
                                    showValidationDialog(getActivity(), "Please enter country");
                                    eTxt_country.requestFocus();
                                    //Toast.makeText(this, "Please enter the country", Toast.LENGTH_SHORT);
                                }

                            } else {
                                showValidationDialog(getActivity(), "Please enter state");
                                eTxt_state.requestFocus();

                                // Toast.makeText(this, "Please enter the state", Toast.LENGTH_SHORT);
                            }

                        } else {

                            showValidationDialog(getActivity(), "Please enter address");
                            eTxt_addrOne.requestFocus();

                            //  Toast.makeText(this, "Please enter the address", Toast.LENGTH_SHORT);
                        }

                    } else {
                        showValidationDialog(getActivity(), "Please enter valid location name");
                        eTxt_locationName.requestFocus();

                        //  Toast.makeText(this, "Please enter location name", Toast.LENGTH_SHORT);
                    }
                } else {
                    showValidationDialog(getActivity(), "Please enter location name");
                    eTxt_locationName.requestFocus();

                    //  Toast.makeText(this, "Please enter location name", Toast.LENGTH_SHORT);
                }

            } else {
                showValidationDialog(getActivity(), "Please enter valid hospital name");
                eTxt_hosptName.requestFocus();

                // Toast.makeText(this, "Please enter hospital name", Toast.LENGTH_SHORT);
            }
        } else {
            showValidationDialog(getActivity(), "Please enter hospital name");
            eTxt_hosptName.requestFocus();

            // Toast.makeText(this, "Please enter hospital name", Toast.LENGTH_SHORT);
        }
    }

    /* @Name getViewCasting()
* @Type No Argument Method
* @Created_By GokulRaj K.c
* @Created_On 01-12-2015
* @Updated_By
* @Updated_On
* @Description Casting the Fields
*/
    private void getViewCasting(View rootView) {


        TextView txtVw_hosptName = (TextView) rootView.findViewById(R.id.txtVw_hosptName);
        TextView txtVw_locationName = (TextView) rootView.findViewById(R.id.txtVw_locationName);
        TextView txtVw_addrOne = (TextView) rootView.findViewById(R.id.txtVw_addrOne);
        TextView txtVw_state = (TextView) rootView.findViewById(R.id.txtVw_state);
        TextView txtVw_country = (TextView) rootView.findViewById(R.id.txtVw_country);
        TextView txtVw_phnNoOne = (TextView) rootView.findViewById(R.id.txtVw_phnNoOne);
        TextView txtVw_hosptEmail = (TextView) rootView.findViewById(R.id.txtVw_hosptEmail);
        txtVw_noHospitalFound = (TextView) rootView.findViewById(R.id.txtVw_noHospitalFound);


        TextView txtVw_view_eqStatus_serialNo = (TextView) rootView.findViewById(R.id.txtVw_view_eqStatus_serialNo);
        TextView txtVw_DeviceName = (TextView) rootView.findViewById(R.id.txtVw_DeviceName);
        TextView status_hosp = (TextView) rootView.findViewById(R.id.status_hosp);
        TextView update_hosp = (TextView) rootView.findViewById(R.id.update_hosp);

        String hospitalname = "Hospital Name";
        String locationname = "Location Name";
        String address1 = "Address 1";
        String state = "State";
        String country = "Country";
        String phoneno = "Phone No 1";
        String email = "E-Mail";

        String asterisk = "<font color='#EE0000'> *</font>";


        txtVw_hosptName.setText(Html.fromHtml(hospitalname + asterisk));
        txtVw_locationName.setText(Html.fromHtml(locationname + asterisk));
        txtVw_addrOne.setText(Html.fromHtml(address1 + asterisk));
        txtVw_state.setText(Html.fromHtml(state + asterisk));
        txtVw_country.setText(Html.fromHtml(country + asterisk));
        txtVw_phnNoOne.setText(Html.fromHtml(phoneno + asterisk));
        txtVw_hosptEmail.setText(Html.fromHtml(email + asterisk));


        txtVw_hosptDescr = (TextView) rootView.findViewById(R.id.txtVw_hosptDescr);
        txtVw_addrTwo = (TextView) rootView.findViewById(R.id.txtVw_addrTwo);
        txtVw_addrThree = (TextView) rootView.findViewById(R.id.txtVw_addrThree);
        txtVw_phnNoTwo = (TextView) rootView.findViewById(R.id.txtVw_phnNoTwo);
        txtVw_hosptNotes = (TextView) rootView.findViewById(R.id.txtVw_hosptNotes);
        scroll_main_hospital = (ScrollView) rootView.findViewById(R.id.scroll_main_hospital);
        list_HospitalDetails = (ListView) rootView.findViewById(R.id.list_HospitalDetails);
        layout_hospitalView = (RelativeLayout) rootView.findViewById(R.id.layout_hospitalView);

        eTxt_hosptName = (EditText) rootView.findViewById(R.id.eTxt_hosptName);
        eTxt_locationName = (EditText) rootView.findViewById(R.id.eTxt_locationName);
        eTxt_hosptDescr = (EditText) rootView.findViewById(R.id.eTxt_hosptDescr);
        eTxt_addrOne = (EditText) rootView.findViewById(R.id.eTxt_addrOne);
        eTxt_addrTwo = (EditText) rootView.findViewById(R.id.eTxt_addrTwo);
        eTxt_addrThree = (EditText) rootView.findViewById(R.id.eTxt_addrThree);
        eTxt_state = (EditText) rootView.findViewById(R.id.eTxt_state);
        eTxt_country = (EditText) rootView.findViewById(R.id.eTxt_country);

        eTxt_phnNoOne = (EditText) rootView.findViewById(R.id.eTxt_phnNoOne);
        eTxt_phnNoTwo = (EditText) rootView.findViewById(R.id.eTxt_phnNoTwo);
        eTxt_hosptEmail = (EditText) rootView.findViewById(R.id.eTxt_hosptEmail);
        eTxt_hosptNotes = (EditText) rootView.findViewById(R.id.eTxt_hosptNotes);
        txtVw_active = (TextView) rootView.findViewById(R.id.txtVw_active);
        txtVw_activeYesNo = (TextView) rootView.findViewById(R.id.txtVw_activeYesNo);

        txtVw_standard_equipments = (TextView) rootView.findViewById(R.id.txtVw_standard_equipments);
        txtVw_standard_equipmentsYesNo = (TextView) rootView.findViewById(R.id.txtVw_standard_equipmentsYesNo);


        eTxt_hosptName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_locationName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_hosptDescr.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_addrOne.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_addrTwo.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_addrThree.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_state.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_hosptNotes.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);


        switch_active = (Switch) rootView.findViewById(R.id.switch_active);
        switch_standard_equipments = (Switch) rootView.findViewById(R.id.switch_standard_equipments);

        txtVw_view_eqStatus_serialNo.setTypeface(calibri_bold_typeface);
        txtVw_DeviceName.setTypeface(calibri_bold_typeface);
        status_hosp.setTypeface(calibri_bold_typeface);
        update_hosp.setTypeface(calibri_bold_typeface);
        txtVw_hosptName.setTypeface(calibri_typeface);
        txtVw_locationName.setTypeface(calibri_typeface);
        txtVw_addrOne.setTypeface(calibri_typeface);
        txtVw_state.setTypeface(calibri_typeface);
        txtVw_country.setTypeface(calibri_typeface);
        txtVw_phnNoOne.setTypeface(calibri_typeface);
        txtVw_hosptEmail.setTypeface(calibri_typeface);
        txtVw_noHospitalFound.setTypeface(calibri_typeface);
        txtVw_hosptDescr.setTypeface(calibri_typeface);
        txtVw_addrTwo.setTypeface(calibri_typeface);
        txtVw_addrThree.setTypeface(calibri_typeface);
        txtVw_phnNoTwo.setTypeface(calibri_typeface);
        txtVw_phnNoTwo.setTypeface(calibri_typeface);
        txtVw_hosptNotes.setTypeface(calibri_typeface);
        eTxt_hosptName.setTypeface(calibri_typeface);
        eTxt_locationName.setTypeface(calibri_typeface);
        eTxt_hosptDescr.setTypeface(calibri_typeface);
        eTxt_addrOne.setTypeface(calibri_typeface);
        eTxt_addrTwo.setTypeface(calibri_typeface);
        eTxt_addrThree.setTypeface(calibri_typeface);
        eTxt_state.setTypeface(calibri_typeface);
        eTxt_country.setTypeface(calibri_typeface);
        eTxt_phnNoOne.setTypeface(calibri_typeface);
        eTxt_phnNoTwo.setTypeface(calibri_typeface);
        eTxt_hosptEmail.setTypeface(calibri_typeface);
        eTxt_hosptNotes.setTypeface(calibri_typeface);
        txtVw_active.setTypeface(calibri_typeface);
        txtVw_activeYesNo.setTypeface(calibri_typeface);
        txtVw_standard_equipmentsYesNo.setTypeface(calibri_typeface);
        txtVw_standard_equipments.setTypeface(calibri_typeface);
        switch_active.setTypeface(calibri_typeface);
        switch_standard_equipments.setTypeface(calibri_typeface);
        switch_standard_equipments.setClickable(true);

        btn_fab_hosptenrollSave = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_hosptenrollSave);
//        btn_hosptenrollCancel = ( Button ) rootView.findViewById( R.id.btn_hosptenrollCancel );
        btn_fab_hosptenrollNew = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_hosptenrollNew);
        btn_fab_hosptenrollDelete = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_hosptenrollDelete);


        layoutHidden();
//        layout_hospitalView.setVisibility( View.GONE );
        getViewClickEvents();
    }

    private void layoutHidden() {
        layout_hospitalView.setVisibility(View.GONE);
        scroll_main_hospital.setVisibility(View.GONE);
        txtVw_noHospitalFound.setVisibility(View.GONE);
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


        switch_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    txtVw_activeYesNo.setText("Yes");
                    str_swt_IsActive = "Y";

                } else {
                    txtVw_activeYesNo.setText("No");
                    str_swt_IsActive = "N";
                }
            }
        });

        switch_standard_equipments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    txtVw_standard_equipmentsYesNo.setText("Yes");
                    str_swt_StandardEquipments = "Y";

                } else {
                    txtVw_standard_equipmentsYesNo.setText("No");
                    str_swt_StandardEquipments = "N";
                }
            }
        });

        btn_fab_hosptenrollSave.setOnClickListener(this);
        btn_fab_hosptenrollNew.setOnClickListener(this);
        btn_fab_hosptenrollDelete.setOnClickListener(this);
//        btn_hosptenrollCancel.setOnClickListener( this );

        eTxt_hosptName.setOnClickListener(this);
        eTxt_locationName.setOnClickListener(this);
        eTxt_hosptDescr.setOnClickListener(this);
        eTxt_addrOne.setOnClickListener(this);
        eTxt_addrTwo.setOnClickListener(this);
        eTxt_addrThree.setOnClickListener(this);
        eTxt_state.setOnClickListener(this);
        eTxt_country.setOnClickListener(this);
        eTxt_phnNoOne.setOnClickListener(this);
        eTxt_phnNoTwo.setOnClickListener(this);
        eTxt_hosptEmail.setOnClickListener(this);
        eTxt_hosptNotes.setOnClickListener(this);

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
            case R.id.btn_fab_hosptenrollDelete:
                isAddedNew = false;

                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                break;
            case R.id.btn_fab_hosptenrollSave:
                isAddedNew = true;

                if (isFromUpdate == true) {
                    updateHospitalEnrollment(selectedPostion);

                } else {
                    insertHospitalDetails();

                }
               /* Intent i = new Intent(Master_Hospt_Enroll_Fragment.this, MedicalEquipFragment.class);
                startActivity(i);*/


                break;

            case R.id.btn_fab_hosptenrollNew:
                BusinessAccessLayer.editPage = true;
                isFromUpdate = false;
                BusinessAccessLayer.UPDATE_GOBACK_FLAG = 0;
                layout_hospitalView.setVisibility(View.GONE);
                btn_fab_hosptenrollNew.setVisibility(View.GONE);
                scroll_main_hospital.setVisibility(View.VISIBLE);
                btn_fab_hosptenrollSave.setVisibility(View.VISIBLE);
                btn_fab_hosptenrollDelete.setVisibility(View.GONE);
                txtVw_noHospitalFound.setVisibility(View.GONE);


                break;
//            case R.id.btn_hosptenrollCancel:
//                Intent i = new Intent( getActivity(), DashBoardActivity.class );
//                startActivity( i );
////                finish();
//
//                break;
            case R.id.eTxt_hosptName:

                break;
            case R.id.eTxt_locationName:

                break;
            case R.id.eTxt_state:

                break;
            case R.id.eTxt_country:

                break;

            default:
                break;
        }

    }

    private void insertHospitalDetails() {

        if (eTxt_hosptName.getText().toString().trim().length() > 0) {
            if (!isNumeric(eTxt_hosptName.getText().toString())) {
                if (eTxt_locationName.getText().toString().trim().length() > 0) {
                    if (!isNumeric(eTxt_locationName.getText().toString())) {
                        if (eTxt_addrOne.getText().toString().trim().length() > 0) {
                            if (eTxt_state.getText().toString().trim().length() > 0) {
                                if (eTxt_country.getText().toString().trim().length() > 0) {
                                    if (eTxt_phnNoOne.getText().toString().trim().length() > 0) {
                                        if (eTxt_hosptEmail.getText().toString().trim().length() > 0) {
                                            if (eTxt_hosptEmail.getText().toString().trim().matches(getString(R.string.emailPattern))) {

                                                str_eTxt_hosptName = eTxt_hosptName.getText().toString();
                                                str_eTxt_locationName = eTxt_locationName.getText().toString();
                                                str_eTxt_hosptDescr = eTxt_hosptDescr.getText().toString();
                                                str_eTxt_addrOne = eTxt_addrOne.getText().toString();
                                                str_eTxt_addrTwo = eTxt_addrTwo.getText().toString();
                                                str_eTxt_addrThree = eTxt_addrThree.getText().toString();
                                                str_eTxt_state = eTxt_state.getText().toString();
                                                str_eTxt_country = eTxt_country.getText().toString();
                                                str_eTxt_phnNoOne = eTxt_phnNoOne.getText().toString();
                                                str_eTxt_phnNoTwo = eTxt_phnNoTwo.getText().toString();
                                                str_eTxt_hosptEmail = eTxt_hosptEmail.getText().toString();
                                                str_eTxt_hosptNotes = eTxt_hosptNotes.getText().toString();


                                                Mst_Hospital_Enrollment mHospital = new Mst_Hospital_Enrollment(getActivity());
                                                mHospital.open();
                                                Cursor cur = mHospital.fetchbynames(str_eTxt_hosptName, str_eTxt_locationName);
                                                if (cur.getCount() == 0) {
                                                    Cursor cursor = mHospital.fetch();
                                                    hosptEnrollId = getHospitalId(cursor.getCount());

                                                    long insert_hosptvalue = mHospital.insert_Hospital_Enroll(
                                                            "" + hosptEnrollId,
                                                            str_eTxt_hosptName,
                                                            str_eTxt_locationName,
                                                            str_eTxt_hosptDescr,
                                                            str_eTxt_addrOne,
                                                            str_eTxt_addrTwo,
                                                            str_eTxt_addrThree,
                                                            str_eTxt_state,
                                                            str_eTxt_country,
                                                            str_eTxt_phnNoOne,
                                                            str_eTxt_phnNoTwo,
                                                            str_eTxt_hosptEmail,
                                                            str_eTxt_hosptNotes,
                                                            str_swt_IsActive,
                                                            str_swt_StandardEquipments,
                                                            BusinessAccessLayer.SYNC_STATUS_VALUE, "0",
                                                            BusinessAccessLayer.mUserId,
                                                            getCurrentDate(),
                                                            BusinessAccessLayer.mUserId,
                                                            getCurrentDate());


                                                    if (insert_hosptvalue > 0) {
                                                        hideKeyBoard();
                                                        if(switch_standard_equipments.isChecked())
                                                        {
                                                            Cursor sta_equi =  mHospital.fetchStandard_Equipment();
                                                            if (sta_equi.getCount() > 0) {
                                                                Trn_Equipment_Enrollment trn_equipment_enrollment = new Trn_Equipment_Enrollment(getActivity());
                                                                trn_equipment_enrollment.open();
                                                                for(int i=0;i<sta_equi.getCount();i++)
                                                                {
                                                                    Cursor eq_cur = trn_equipment_enrollment.fetch();
                                                                    String generatedMedicalEquipId = getMedicalEquipmentlId(eq_cur.getCount());

                                                                    sta_equi.moveToPosition(i);
                                                                    String equipment_id = sta_equi.getString(sta_equi.getColumnIndex(""+BusinessAccessLayer.EQ_ID+""));

                                                                    long insert_equipment_enroll = trn_equipment_enrollment.insert_equipment_enroll(
                                                                            generatedMedicalEquipId,
                                                                            equipment_id,
                                                                            hosptEnrollId,
                                                                            str_eTxt_locationName,
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            "",
                                                                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                                                                            BusinessAccessLayer.FLAG_VALUE,
                                                                            BusinessAccessLayer.mUserId,
                                                                            getCurrentDateWithTime(),
                                                                            BusinessAccessLayer.mUserId,
                                                                            getCurrentDateWithTime());
                                                                }
                                                                Trn_Equipment_Enrollment.close();
                                                            }

                                                        }
//                                            showToast( getActivity(), "Hospital Details Saved Successfully" );
//                                            clearAllFields();
                                                        showContactUsDialog(getActivity(), "Hospital added Successfully");

                                            /*Intent i = new Intent( getActivity(), MedicalEquipFragment.class );
                                            startActivity( i );*/
                                                    } else {
//                                            showToast( getActivity(), "Hospital Details Failed" );
                                                        showValidationDialog(getActivity(), "Hospital Added Failed");

                                                    }

                                                    Mst_Hospital_Enrollment.close();

                                                } else {
                                                    showValidationDialog(getActivity(), "Hospital and location name already exist");
                                                    eTxt_hosptName.requestFocus();
                                                }

                                            } else {
                                                showValidationDialog(getActivity(), "Invalid email");
                                                eTxt_hosptEmail.requestFocus();

//                                        showToast( getActivity(), "Invalid email" );
                                                // Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT);
                                            }
                                        } else {
                                            showValidationDialog(getActivity(), "Please enter email");
                                            eTxt_hosptEmail.requestFocus();

//                                    showToast( getActivity(), "Please enter the email" );
                                            // Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT);
                                        }
                                    } else {
                                        showValidationDialog(getActivity(), "Please enter phone no");
                                        eTxt_phnNoOne.requestFocus();

//                                showToast( getActivity(), "Please enter the phone No" );
                                        //   Toast.makeText(this, "Please enter the phone No", Toast.LENGTH_SHORT);
                                    }

                                } else {
                                    showValidationDialog(getActivity(), "Please enter country");
                                    eTxt_country.requestFocus();

//                            showToast( getActivity(), "Please enter the country" );
                                    //Toast.makeText(this, "Please enter the country", Toast.LENGTH_SHORT);
                                }

                            } else {
                                showValidationDialog(getActivity(), "Please enter state");
                                eTxt_state.requestFocus();

//                        showToast( getActivity(), "Please enter the state" );

                                // Toast.makeText(this, "Please enter the state", Toast.LENGTH_SHORT);
                            }

                        } else {
                            showValidationDialog(getActivity(), "Please enter address");
                            eTxt_addrOne.requestFocus();

//                    showToast( getActivity(), "Please enter the address" );

                            //  Toast.makeText(this, "Please enter the address", Toast.LENGTH_SHORT);
                        }

                    } else {
                        showValidationDialog(getActivity(), "Please enter valid location name");
                        eTxt_locationName.requestFocus();
                    }
                } else {
                    showValidationDialog(getActivity(), "Please enter location name");
                    eTxt_locationName.requestFocus();

//                showToast( getActivity(), "Please enter location name" );

                    //  Toast.makeText(this, "Please enter location name", Toast.LENGTH_SHORT);
                }

            } else {
                showValidationDialog(getActivity(), "Please enter valid hospital name");
                eTxt_hosptName.requestFocus();
            }
        } else {
            showValidationDialog(getActivity(), "Please enter hospital name");
            eTxt_hosptName.requestFocus();

//            showToast( getActivity(), "Please enter hospital name" );

            // Toast.makeText(this, "Please enter hospital name", Toast.LENGTH_SHORT);
        }

    }

    private String getMedicalEquipmentlId(int count) {
        String imeino = setIMEIno();
        String timeStamp = getUnixTimeStamp();
        String finalId = "MED_EQU_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }

    private String getHospitalId(int count) {
        String imeino = setIMEIno();
        String timeStamp = getUnixTimeStamp();
        System.out.println("timeStamp" + timeStamp);
        String finalId = "HOS_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }

    private void clearAllFields() {
        eTxt_hosptName.setText("");
        eTxt_locationName.setText("");
        eTxt_hosptDescr.setText("");
        eTxt_addrOne.setText("");
        eTxt_addrTwo.setText("");
        eTxt_addrThree.setText("");
        eTxt_state.setText("");
        eTxt_phnNoOne.setText("");
        eTxt_phnNoTwo.setText("");
        eTxt_hosptEmail.setText("");
        eTxt_hosptNotes.setText("");
    }

    public void showContactUsDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(false);
//        mContactUsDialog.setCanceledOnTouchOutside(true);
        final Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("Yes");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("No");
        txt_dia.setTypeface(calibri_bold_typeface);
        yes.setTypeface(calibri_bold_typeface);
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

                    isFromUpdate = false;

                    Mst_Hospital_Enrollment mstHospitalEnrollment = new Mst_Hospital_Enrollment(ctx);
                    mstHospitalEnrollment.open();

                    mstHospitalEnrollment.deleteFlagActiveStatus(hosptEnrollId);

                    Mst_Hospital_Enrollment.close();

                    scroll_main_hospital.setVisibility(View.GONE);
                    layout_hospitalView.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollSave.setVisibility(View.GONE);
                    btn_fab_hosptenrollNew.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollDelete.setVisibility(View.GONE);
                    txtVw_noHospitalFound.setVisibility(View.GONE);
                    System.out.println("BusinessAccessLayer.editPage in showContactUsDialog if " + BusinessAccessLayer.editPage);
                    clearAllFields();
                    getHospitalInformation();

                    exportDatabse(BusinessAccessLayer.DATABASE_NAME);

                } else {

                    scroll_main_hospital.setVisibility(View.GONE);
                    layout_hospitalView.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollSave.setVisibility(View.GONE);
                    btn_fab_hosptenrollNew.setVisibility(View.VISIBLE);
                    btn_fab_hosptenrollDelete.setVisibility(View.GONE);
                    txtVw_noHospitalFound.setVisibility(View.GONE);
                    BusinessAccessLayer.editPage = false;
                    System.out.println("BusinessAccessLayer.editPage in showContactUsDialog else " + BusinessAccessLayer.editPage);
                    clearAllFields();
                    getHospitalInformation();

                    exportDatabse(BusinessAccessLayer.DATABASE_NAME);

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

    public void showValidationDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(false);

        Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);


        yes.setText("OK");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("Cancel");
        no.setVisibility(View.GONE);
        txt_dia.setTypeface(calibri_bold_typeface);
        yes.setTypeface(calibri_bold_typeface);
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

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}
