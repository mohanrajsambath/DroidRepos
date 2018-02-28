package org.next.equmed.uil;

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
import android.text.InputType;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.dal.Mst_Equipment_Status;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_Equipment_Enrollment;
import org.next.equmed.dal.Trn_Images;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NTE_ELE_HW on 12/10/2015.
 */
public class Master_Equipment_Enroll_Fragment extends UserInterfaceLayer implements View.OnClickListener {


    private EditText eTxt_eq_Name;
    private String str_eTxt_eq_Name;
    private String str_EquTag = "NTE_";
    private ListView list_Mst_Equipment;

    String selectedEquId = "", selected_eqName = "", selected_eqCreatedBy = "", selected_eqCreatedOn = "", str_swt_IsActive = "Y", str_swt_IsStandard = "N";

    ArrayList<Bean> masterEquipmentArraylist = new ArrayList<>();

    FloatingActionButton btn_fab_equipMstSave, btn_fab_equipMstAddNew, btn_fab_equipMstDelete;
    private LinearLayout lLayout_eq_Name, layout_listDetails, lLayout_active, lLayout_isstandard, lLayout_insImage;

    boolean isUpdate = false;
    boolean isAddedNew = true;
    Switch switch_equ_active, switch_equ_isstandard;

    TextView txtVw_noEqpmntFound;
    String fetchSyncStatus = "";
    Typeface calibri_typeface, calibri_bold_typeface;
    ImageView imgVw_EqImg;
    String EqImage_EncodedStr = "", Eq_ImgId = "";
    String str_createdBy = "", str_createdOn = "";

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.master_equipment_enrollment, container, false);


        calibri_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Calibri_Bold.ttf");

        getViewCasting(rootView);
        isUpdate = false;
        layoutHidden();
        getMstEquipmentEnrollmentStatus();
        BusinessAccessLayer.bug_class_name = "EquipmentEnrollFragment";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getActivity()));
        // layout_listDetails.setVisibility( View.VISIBLE );
        return rootView;
    }

    private void layoutHidden() {
        lLayout_eq_Name.setVisibility(View.GONE);
        lLayout_isstandard.setVisibility(View.GONE);
        lLayout_insImage.setVisibility(View.GONE);
        layout_listDetails.setVisibility(View.GONE);
        lLayout_active.setVisibility(View.GONE);
        txtVw_noEqpmntFound.setVisibility(View.GONE);
    }

    /* @Name getViewCasting()
* @Type No Argument Method
* @Created_By Mohanraj.S
* @Created_On 03-12-2015 at 11:25:56 am
* @Updated_By
* @Updated_On
* @Description Casting the Fields
*/
    private void getViewCasting(View rootView) {

        TextView txtVw_eq_Name = (TextView) rootView.findViewById(R.id.txtVw_eq_Name);

        TextView txtVw_view_eqStatus_serialNo1 = (TextView) rootView.findViewById(R.id.txtVw_view_eqStatus_serialNo1);
        TextView txtVw_DeviceName1 = (TextView) rootView.findViewById(R.id.txtVw_DeviceName1);
        TextView txtVw_eqStatus1 = (TextView) rootView.findViewById(R.id.txtVw_eqStatus1);
        TextView update_equip1 = (TextView) rootView.findViewById(R.id.update_equip1);

        String equipmentname = "Equipment Name";

        String asterisk = "<font color='#EE0000'> *</font>";
        txtVw_eq_Name.setText(Html.fromHtml(equipmentname + asterisk));

        eTxt_eq_Name = (EditText) rootView.findViewById(R.id.eTxt_eq_Name);
        btn_fab_equipMstSave = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_equipMstSave);
        btn_fab_equipMstAddNew = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_equipMstAddNew);
        lLayout_eq_Name = (LinearLayout) rootView.findViewById(R.id.lLayout_eq_Name);
        lLayout_active = (LinearLayout) rootView.findViewById(R.id.lLayout_active);
        lLayout_isstandard = (LinearLayout) rootView.findViewById(R.id.lLayout_isstandard);
        lLayout_insImage = (LinearLayout) rootView.findViewById(R.id.lLayout_insImage);
        layout_listDetails = (LinearLayout) rootView.findViewById(R.id.layout_listDetails);
        list_Mst_Equipment = (ListView) rootView.findViewById(R.id.list_Mst_Equipment);
        btn_fab_equipMstDelete = (FloatingActionButton) rootView.findViewById(R.id.btn_fab_equipMstDelete);
        eTxt_eq_Name.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        switch_equ_active = (Switch) rootView.findViewById(R.id.switch_equ_active);
        switch_equ_isstandard = (Switch) rootView.findViewById(R.id.switch_equ_isstandard);
        txtVw_noEqpmntFound = (TextView) rootView.findViewById(R.id.txtVw_noEqpmntFound);
        TextView txtVw_isstandard = (TextView) rootView.findViewById(R.id.txtVw_isstandard);
        TextView txtVw_active = (TextView) rootView.findViewById(R.id.txtVw_active);

        TextView txtVw_EqImg = (TextView) rootView.findViewById(R.id.txtVw_EqImg);
        imgVw_EqImg = (ImageView) rootView.findViewById(R.id.imgVw_EqImg);

        txtVw_eq_Name.setTypeface(calibri_typeface);
        txtVw_EqImg.setTypeface(calibri_typeface);
        txtVw_isstandard.setTypeface(calibri_typeface);
        txtVw_active.setTypeface(calibri_typeface);
        eTxt_eq_Name.setTypeface(calibri_typeface);
        switch_equ_active.setTypeface(calibri_typeface);
        switch_equ_isstandard.setTypeface(calibri_typeface);
        txtVw_noEqpmntFound.setTypeface(calibri_bold_typeface);
        txtVw_view_eqStatus_serialNo1.setTypeface(calibri_bold_typeface);
        txtVw_DeviceName1.setTypeface(calibri_bold_typeface);
        txtVw_eqStatus1.setTypeface(calibri_bold_typeface);
        update_equip1.setTypeface(calibri_bold_typeface);


        switch_equ_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    str_swt_IsActive = BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS;

                } else {
                    str_swt_IsActive = BusinessAccessLayer.IS_ACTIVE_STATUS_FAIL;
                }
            }
        });

        switch_equ_isstandard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    str_swt_IsStandard = BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS;

                } else {
                    str_swt_IsStandard = BusinessAccessLayer.IS_ACTIVE_STATUS_FAIL;
                }
            }
        });
        getViewClickEvents();

    }

    private void getViewClickEvents() {
        btn_fab_equipMstSave.setOnClickListener(this);
        btn_fab_equipMstAddNew.setOnClickListener(this);
        btn_fab_equipMstDelete.setOnClickListener(this);
        imgVw_EqImg.setOnClickListener(this);


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

    /**
     * @param – no params
     * @return –no return values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:24:27 am
     * @Updated_By
     * @Updated_On
     * @Description Onclick for the Components
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_fab_equipMstSave:
                if (isUpdate == true) {
                    updateEquipmentDetails_into_DB();
                } else {
                    insertEquipmentDetails_into_DB();

                }

                break;
            case R.id.btn_fab_equipMstAddNew:
                BusinessAccessLayer.editPage = true;
                BusinessAccessLayer.UPDATE_GOBACK_FLAG = 0;
                addNewEquipmentDetails();
                break;
            case R.id.btn_fab_equipMstDelete:
                isAddedNew = false;
                showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                break;
            case R.id.imgVw_EqImg:
                hideKeyBoard();
                uploadImage();
                break;

            default:
                break;
        }

    }

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
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error accessing file: " + e.getMessage());
        }
        return finalPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {

            if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA) {

                CropImage(picUri);

                f = new File(Environment.getExternalStorageDirectory().toString());

                for (File temp : f.listFiles()) {

                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;

                        break;

                    }

                }

//                try {
//
//                    Bitmap bitmap;
//
//                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                            bitmapOptions);
//                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                    Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//
//                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                    String[] extension = fileName.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String tempImg = storeImage(scale, new_file_name);
//
//                    EqImage_EncodedStr = new_file_name;
//
//                    //        insDetails_InstallermageEncodedStr = encodeTobase64(scale);
////                        uploadImageStr = encodeTobase64(scale);
//
//                    imgVw_EqImg.setImageURI(Uri.parse(tempImg));
//
//
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//
//                }


            } else if (requestCode == BusinessAccessLayer.PICK_FROM_GALLERY) {

                // capture image from gallery  for installer  image

//                Uri selectedImage = data.getData();

                picUri = data.getData();

                CropImage(picUri);
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getActivity().getContentResolver().query(picUri, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                String picturePath = c.getString(columnIndex);

                c.close();

//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
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
//                System.out.println("picturePath:::" + picturePath);
////                Log.w("path of image from gallery......******************.........", picturePath + "");
//
//                EqImage_EncodedStr = new_file_name;
//
////                    uploadImageStr = encodeTobase64(scaled);
//                imgVw_EqImg.setImageURI(Uri.parse(fileDirectoryPath));
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
//                    Bitmap bitmap;
//
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

                        EqImage_EncodedStr = new_file_name;

                        //        insDetails_InstallermageEncodedStr = encodeTobase64(scale);
//                        uploadImageStr = encodeTobase64(scale);

                        imgVw_EqImg.setImageURI(Uri.parse(tempImg));


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

//                Log.w("path of image from gallery......******************.........", picturePath + "");

                    EqImage_EncodedStr = new_file_name;

//                    uploadImageStr = encodeTobase64(scaled);
                    imgVw_EqImg.setImageURI(Uri.parse(fileDirectoryPath));
                    if (file != null) {
                        // To delete original image taken by camera
                        if (file.delete()) {

                            // Toast.makeText(TakePictureDemo.class,"original image deleted...",Toast.LENGTH_SHORT).show();
                        }
                        // Common.showToast(TakePictureDemo.this,"original image deleted...");
                    }
                }
            }
        }

    }


    private void addNewEquipmentDetails() {
        lLayout_eq_Name.setVisibility(View.VISIBLE);
        lLayout_active.setVisibility(View.VISIBLE);
        lLayout_isstandard.setVisibility(View.VISIBLE);
        lLayout_insImage.setVisibility(View.VISIBLE);
        btn_fab_equipMstSave.setVisibility(View.VISIBLE);
        btn_fab_equipMstAddNew.setVisibility(View.GONE);
        layout_listDetails.setVisibility(View.GONE);
        btn_fab_equipMstDelete.setVisibility(View.GONE);
        txtVw_noEqpmntFound.setVisibility(View.GONE);

    }

    /**
     * @param – no params
     * @return –no return values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:24:27 am
     * @Updated_By
     * @Updated_On
     * @Description Onclick for the Components
     */
    private void insertEquipmentDetails_into_DB() {
        if (eTxt_eq_Name.getText().toString().trim().length() > 0) {
            if (!Master_Hospt_Enroll_Fragment.isNumeric(eTxt_eq_Name.getText().toString())) {
                str_eTxt_eq_Name = eTxt_eq_Name.getText().toString();
                Mst_Equipment_Status mEquipmentEnroll = new Mst_Equipment_Status(getActivity());
                mEquipmentEnroll.open();
                Cursor cur = mEquipmentEnroll.fetchByEq_name(str_eTxt_eq_Name);
                if (cur.getCount() == 0) {

                    Cursor equipmentCount = mEquipmentEnroll.fetch();

                    String eq_id = getEquipmentId(equipmentCount.getCount());
//            int eq_Id = equipmentCount.getCount() + 1;

                    long insert_EquipmentEnrollValue = mEquipmentEnroll.insert_Equipment_Status_Enroll(
                            eq_id,
                            str_eTxt_eq_Name,
                            "0", str_swt_IsActive, str_swt_IsStandard,
                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                            BusinessAccessLayer.mUserId,
                            getCurrentDate(),
                            BusinessAccessLayer.mUserId,
                            getCurrentDate()
                    );


                    if (insert_EquipmentEnrollValue > 0) {

                        if (EqImage_EncodedStr.length() > 0) {
                            storeEquipmentImage(eq_id);
                        }

                        isAddedNew = true;
                        hideKeyBoard();
                        showContactUsDialog(getActivity(), "Equipment added successfully");
                    } else {
                        showValidationDialog(getActivity(), "Equipment added failed");
                    }
                } else {
                    showValidationDialog(getActivity(), "Equipment name already exist");
                }

            } else {
                showValidationDialog(getActivity(), "Please enter valid equipment name");

            }
        } else {
            showValidationDialog(getActivity(), "Please enter equipment name");

        }
    }

    private void storeEquipmentImage(String eq_id) {
        Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
        trn_imagesAdapt.open();
        Cursor trn_images_cursor = trn_imagesAdapt.fetch();

        int id_trn_images = trn_images_cursor.getCount() + 1;


        long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images,
                eq_id,
                BusinessAccessLayer.EQUIPMENT_IMAGE,
                EqImage_EncodedStr,
                BusinessAccessLayer.FLAG_VALUE,
                BusinessAccessLayer.SYNC_STATUS_VALUE,
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime(),
                BusinessAccessLayer.mUserId,
                getCurrentDateWithTime());
        trn_imagesAdapt.close();
    }

    /**
     * @param – no params
     * @return –no return values
     * @throws – no exception throws
     * @Type cursor return type Method
     * @Created_By Mohanraj.S
     * @Created_On 03-12-2015 at 11:24:27 am
     * @Updated_By
     * @Updated_On
     * @Description Onclick for the Components
     */
    private void updateEquipmentDetails_into_DB() {

        if (eTxt_eq_Name.getText().toString().trim().length() > 0) {
            if (!Master_Hospt_Enroll_Fragment.isNumeric(eTxt_eq_Name.getText().toString())) {
                str_eTxt_eq_Name = eTxt_eq_Name.getText().toString();
                Mst_Equipment_Status mEquipmentEnroll = new Mst_Equipment_Status(getActivity());
                mEquipmentEnroll.open();
                Cursor cur = mEquipmentEnroll.fetchByEq_nameupdate(str_eTxt_eq_Name, selectedEquId);
                if (cur.getCount() == 0) {

                    Cursor equipmentCount = mEquipmentEnroll.fetch();
//            int eq_Id = equipmentCount.getCount() + 1;


                    String flagStatusEqpmnt = "";
                    if (fetchSyncStatus.equalsIgnoreCase("0")) {
                        flagStatusEqpmnt = "0";
                    } else {
                        flagStatusEqpmnt = "1";
                    }
                    boolean update_EquipmentEnrollValue = mEquipmentEnroll.update_Equipment_Status_Enroll(
                            selectedEquId,
                            str_eTxt_eq_Name,
                            flagStatusEqpmnt, str_swt_IsActive, str_swt_IsStandard,
                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                            selected_eqCreatedBy,
                            selected_eqCreatedOn,
                            BusinessAccessLayer.mUserId,
                            getCurrentDate());


                    if (update_EquipmentEnrollValue == true) {

                        if (EqImage_EncodedStr.length() > 0) {
                            updateEquipmentImage(selectedEquId);
                        }

                        isAddedNew = true;
                        hideKeyBoard();

                        isUpdate = false;
                        showContactUsDialog(getActivity(), "Equipment updated successfully");


                    } else {
                        showValidationDialog(getActivity(), "Equipment updated failed");
                    }
                } else {
                    showValidationDialog(getActivity(), "Equipment name already exist");
                }

            } else {
                showValidationDialog(getActivity(), "Please enter valid equipment name");

            }
        } else {
            showValidationDialog(getActivity(), "Please enter equipment name");

        }

    }

    private void updateEquipmentImage(String eq_id) {
        Trn_Images trn_imagesAdapt = new Trn_Images(getActivity());
        trn_imagesAdapt.open();

        if (Eq_ImgId.equalsIgnoreCase("")) {

            Cursor trn_images_cursor = trn_imagesAdapt.fetch();

            int id_trn_images = trn_images_cursor.getCount() + 1;


            long insert_trn_images = trn_imagesAdapt.insert_image("" + id_trn_images,
                    eq_id,
                    BusinessAccessLayer.EQUIPMENT_IMAGE,
                    EqImage_EncodedStr,
                    BusinessAccessLayer.FLAG_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime(),
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());


            trn_imagesAdapt.close();
        } else {


            Cursor cur = trn_imagesAdapt.fetchByImgImg_ID(Eq_ImgId, eq_id, BusinessAccessLayer.EQUIPMENT_IMAGE);
            System.out.println("Currr count : " + cur.getCount());
            String pr_image = cur.getString(cur.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
            if (!pr_image.equals("")) {
                File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + pr_image);
                File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + pr_image);

                if (tempImg.exists() == true) {
                    tempImg.delete();
                } else {
                    tempImg1.delete();
                }
            }

            boolean insert_trn_images = trn_imagesAdapt.update_image(Eq_ImgId,
                    eq_id,
                    BusinessAccessLayer.EQUIPMENT_IMAGE,
                    EqImage_EncodedStr,
                    "1",
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    str_createdBy,
                    str_createdOn,
                    BusinessAccessLayer.mUserId,
                    getCurrentDateWithTime());


            trn_imagesAdapt.close();
        }


    }

    private String getEquipmentId(int count) {
        String imeino = setIMEIno();
        String timeStamp = getUnixTimeStamp();
        System.out.println("timeStamp" + timeStamp);
        String finalId = "EQU_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }


    void getMstEquipmentEnrollmentStatus() {
        Mst_Equipment_Status mst_equip_status = new Mst_Equipment_Status(getActivity());
        mst_equip_status.open();
        Cursor mCr_Equip_status = mst_equip_status.fetch();
        System.out.println("status count:" + mCr_Equip_status.getCount());
        if (mCr_Equip_status.getCount() > 0) {
            masterEquipmentArraylist.clear();
            for (int eqip_status = 0; eqip_status < mCr_Equip_status.getCount(); eqip_status++) {
                mCr_Equip_status.moveToPosition(eqip_status);
                Bean mBean = new Bean();


                String equip_id = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.EQ_ID + ""));
                mBean.setEq_id(equip_id);

                String eq_name = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.EQ_NAME + ""));
                mBean.setEq_name(eq_name);

                String flag = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                mBean.setFlag(flag);

                String isactive = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));
                mBean.setIsactive(isactive);

                String isstandard = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.IS_STANDARD + ""));
                mBean.setIsStandard(isstandard);

                String sync_status = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));
                mBean.setSync_status(sync_status);

                String created_by = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));
                mBean.setCreated_by(created_by);

                String created_on = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));
                mBean.setCreated_on(created_on);

                String modified_by = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.MODIFIED_BY + ""));
                mBean.setModified_by(modified_by);

                String modified_on = mCr_Equip_status.getString(mCr_Equip_status.getColumnIndex("" + BusinessAccessLayer.MODIFIED_ON + ""));
                mBean.setModified_on(modified_on);

                masterEquipmentArraylist.add(mBean);

                str_createdBy = mCr_Equip_status.getString(mCr_Equip_status
                        .getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));


                str_createdOn = mCr_Equip_status.getString(mCr_Equip_status
                        .getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));


               /* Trn_Images trn_image = new Trn_Images(getActivity());
                trn_image.open();

                Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(equip_id, BusinessAccessLayer.EQUIPMENT_IMAGE);
                if (mCr_trn_image.getCount() > 0) {
                    for (int int_trn_image = 0; int_trn_image < mCr_trn_image.getCount(); int_trn_image++) {
                        mCr_trn_image.moveToPosition(int_trn_image);


                        String str_trn_image = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                        EqImage_EncodedStr = str_trn_image;

                        String str_img_id = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));
                        System.out.println("ins_insImgId:" + Eq_ImgId);
                        Eq_ImgId = str_img_id;

                        String img_path;
                        File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + str_trn_image);
                        File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + str_trn_image);

                        if (tempImg.exists() == true) {
                            img_path = tempImg.getAbsolutePath();
                        } else {
                            img_path = tempImg1.getAbsolutePath();
                        }
                        // Bitmap medicalEqpmntImage = decodeBase64(str_trn_image);
                        imgVw_EqImg.setImageURI(Uri.parse(img_path));
                    }

                }

                Trn_Images.close();*/

            }
            Mst_Equipment_Status.close();
            System.out.println("masterEquipmentArraylist Size" + masterEquipmentArraylist.size());

        }

        if (masterEquipmentArraylist.size() > 0) {
            DeviceListAdapter adapter = new DeviceListAdapter(getActivity(), masterEquipmentArraylist);
            list_Mst_Equipment.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            layout_listDetails.setVisibility(View.VISIBLE);
            txtVw_noEqpmntFound.setVisibility(View.GONE);
        } else {
            layout_listDetails.setVisibility(View.GONE);
            list_Mst_Equipment.setAdapter(null);
            txtVw_noEqpmntFound.setVisibility(View.VISIBLE);

        }
    }


    /**
     * @Subject Displaying EqMedKitDetails in ViewDetails Activity
     * @Created_By Mohanraj.S
     * @Created_On 13-07-2015
     * @Updated_By Mohanraj.S
     * @Updated_On 16-07-2015
     */

    private class DeviceListAdapter extends BaseAdapter {
        ArrayList<Bean> masterEquipmentArraylist;
        private LayoutInflater l_InflaterDeviceList;

        public DeviceListAdapter(Context context, ArrayList<Bean> masterEquipDetails) {

            masterEquipmentArraylist = masterEquipDetails;
            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return masterEquipmentArraylist.size();
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
                convertView = l_InflaterDeviceList.inflate(
                        R.layout.inflate_list_masterdeivceitems, parent, false);

                holder.rL_UpdateDeleteData = (RelativeLayout) convertView.findViewById(R.id.rL_UpdateDeleteData);
                holder.txtVw_DeviceName = (TextView) convertView.findViewById(R.id.txtVw_DeviceName);
                holder.txtVw_DeviceName.setTypeface(calibri_typeface);

                holder.txtVw_view_eqStatus_serialNo = (TextView) convertView.findViewById(R.id.txtVw_view_eqStatus_serialNo);
                holder.txtVw_view_eqStatus_serialNo.setTypeface(calibri_typeface);
                holder.lLayout_inflate_masterdevice = (LinearLayout) convertView.findViewById(R.id.lLayout_inflate_masterdevice);
                holder.imgV_UpdateData = (ImageView) convertView.findViewById(R.id.imgV_UpdateData);
                holder.imgV_DeleteData = (ImageView) convertView.findViewById(R.id.imgV_DeleteData);
                holder.txtVw_Status = (TextView) convertView.findViewById(R.id.txtVw_Status);
                holder.txtVw_Status.setTypeface(calibri_typeface);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int sNo = position + 1;
            holder.txtVw_view_eqStatus_serialNo.setText("" + sNo);
            holder.txtVw_DeviceName.setText(masterEquipmentArraylist.get(position).getEq_name());
            String statusstr = masterEquipmentArraylist.get(position).getIsactive();

            if (statusstr.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {
                statusstr = BusinessAccessLayer.STATUS_ACTIVE;
            } else {
                statusstr = BusinessAccessLayer.STATUS_INACTIVE;
            }
            holder.txtVw_Status.setText(statusstr);

            fetchSyncStatus = masterEquipmentArraylist.get(position).getSync_status();
            holder.txtVw_view_eqStatus_serialNo.setTypeface(calibri_bold_typeface);

            if (fetchSyncStatus.equals("0")) {
                // holder.lLayout_inflate_masterdevice.setBackgroundColor(Color.parseColor("#ffcccc"));
                holder.txtVw_view_eqStatus_serialNo.setTextColor(Color.parseColor("#ff0000"));

            } else {
                // holder.lLayout_inflate_masterdevice.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.txtVw_view_eqStatus_serialNo.setTextColor(Color.parseColor("#009933"));
            }

            holder.txtVw_Status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusinessAccessLayer.editPage = true;
                    BusinessAccessLayer.UPDATE_GOBACK_FLAG = 1;
                    isUpdate = true;
                    selectedEquId = masterEquipmentArraylist.get(position).getEq_id();
                    selected_eqName = masterEquipmentArraylist.get(position).getEq_name();
                    selected_eqCreatedBy = masterEquipmentArraylist.get(position).getCreated_by();
                    selected_eqCreatedOn = masterEquipmentArraylist.get(position).getCreated_on();
                    eTxt_eq_Name.setText(selected_eqName);
                    str_swt_IsActive = masterEquipmentArraylist.get(position).getIsactive();
                    str_swt_IsStandard = masterEquipmentArraylist.get(position).getIsStandard();
                    fetchSyncStatus = masterEquipmentArraylist.get(position).getSync_status();
                    if (str_swt_IsActive.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_equ_active.setChecked(true);
                    } else {
                        switch_equ_active.setChecked(false);
                    }

                    if (str_swt_IsStandard.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_equ_isstandard.setChecked(true);
                    } else {
                        switch_equ_isstandard.setChecked(false);
                    }
                    imgVw_EqImg.setImageResource(R.drawable.medical_equiment);
                    Trn_Images trn_image = new Trn_Images(getActivity());
                    trn_image.open();

                    Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(selectedEquId, BusinessAccessLayer.EQUIPMENT_IMAGE);
                    if (mCr_trn_image.getCount() > 0) {
                        for (int int_trn_image = 0; int_trn_image < mCr_trn_image.getCount(); int_trn_image++) {
                            mCr_trn_image.moveToPosition(int_trn_image);


                            String str_trn_image = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                            EqImage_EncodedStr = str_trn_image;

                            String str_img_id = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));
                            System.out.println("ins_insImgId:" + Eq_ImgId);
                            Eq_ImgId = str_img_id;

                            String img_path;
                            File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + str_trn_image);
                            File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + str_trn_image);

                            if (tempImg.exists() == true) {
                                img_path = tempImg.getAbsolutePath();
                            } else {
                                img_path = tempImg1.getAbsolutePath();
                            }
                            // Bitmap medicalEqpmntImage = decodeBase64(str_trn_image);
                            imgVw_EqImg.setImageURI(Uri.parse(img_path));
                        }

                    }

                    Trn_Images.close();


                    btn_fab_equipMstAddNew.setVisibility(View.GONE);
                    btn_fab_equipMstSave.setVisibility(View.VISIBLE);
                    btn_fab_equipMstSave.setImageResource(R.drawable.edit);
                    layoutHidden();
                    lLayout_eq_Name.setVisibility(View.VISIBLE);
                    lLayout_active.setVisibility(View.VISIBLE);
                    lLayout_isstandard.setVisibility(View.VISIBLE);
                    lLayout_insImage.setVisibility(View.VISIBLE);
                    btn_fab_equipMstDelete.setVisibility(View.GONE);
                }
            });

            holder.imgV_UpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BusinessAccessLayer.editPage = true;
                    BusinessAccessLayer.UPDATE_GOBACK_FLAG = 1;
                    isUpdate = true;
                    selectedEquId = masterEquipmentArraylist.get(position).getEq_id();
                    selected_eqName = masterEquipmentArraylist.get(position).getEq_name();
                    selected_eqCreatedBy = masterEquipmentArraylist.get(position).getCreated_by();
                    selected_eqCreatedOn = masterEquipmentArraylist.get(position).getCreated_on();
                    eTxt_eq_Name.setText(selected_eqName);
                    str_swt_IsActive = masterEquipmentArraylist.get(position).getIsactive();
                    str_swt_IsStandard = masterEquipmentArraylist.get(position).getIsStandard();

                    if (str_swt_IsActive.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_equ_active.setChecked(true);
                    } else {
                        switch_equ_active.setChecked(false);
                    }

                    if (str_swt_IsStandard.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {

                        switch_equ_isstandard.setChecked(true);
                    } else {
                        switch_equ_isstandard.setChecked(false);
                    }

                    imgVw_EqImg.setImageResource(R.drawable.medical_equiment);

                    Trn_Images trn_image = new Trn_Images(getActivity());
                    trn_image.open();

                    Cursor mCr_trn_image = trn_image.fetchByEq_Enroll_Id_Type(selectedEquId, BusinessAccessLayer.EQUIPMENT_IMAGE);
                    if (mCr_trn_image.getCount() > 0) {
                        for (int int_trn_image = 0; int_trn_image < mCr_trn_image.getCount(); int_trn_image++) {
                            mCr_trn_image.moveToPosition(int_trn_image);


                            String str_trn_image = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_ENCRYPTED_DATA + ""));
                            EqImage_EncodedStr = str_trn_image;

                            String str_img_id = mCr_trn_image.getString(mCr_trn_image.getColumnIndex("" + BusinessAccessLayer.IMG_IMG_ID + ""));
                            System.out.println("ins_insImgId:" + Eq_ImgId);
                            Eq_ImgId = str_img_id;

                            String img_path;
                            File tempImg = new File(getActivity().getFilesDir() + "/temp_images/" + str_trn_image);
                            File tempImg1 = new File(getActivity().getFilesDir() + "/images/" + str_trn_image);

                            if (tempImg.exists() == true) {
                                img_path = tempImg.getAbsolutePath();
                            } else {
                                img_path = tempImg1.getAbsolutePath();
                            }
                            // Bitmap medicalEqpmntImage = decodeBase64(str_trn_image);
                            imgVw_EqImg.setImageURI(Uri.parse(img_path));
                        }
                    }

                    Trn_Images.close();

                    btn_fab_equipMstAddNew.setVisibility(View.GONE);
                    btn_fab_equipMstSave.setVisibility(View.VISIBLE);
                    btn_fab_equipMstSave.setImageResource(R.drawable.edit);
                    layoutHidden();
                    lLayout_eq_Name.setVisibility(View.VISIBLE);
                    lLayout_active.setVisibility(View.VISIBLE);
                    lLayout_isstandard.setVisibility(View.VISIBLE);
                    lLayout_insImage.setVisibility(View.VISIBLE);
                    btn_fab_equipMstDelete.setVisibility(View.GONE);
                }
            });

            holder.imgV_DeleteData.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    selectedEquId = masterEquipmentArraylist.get(position).getEq_id();

                    isAddedNew = false;
                    showContactUsDialog(getActivity(), BusinessAccessLayer.DELETE_MESSAGE);
                }
            });

            //  holder.txtVw_Dept.setText(getDepartmentName(deviceAdapterArrayList
           /*         .get(position).getTrn_deviceInfo_dprtmnt_id()));
            holder.imgV_ViewData.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                 //   viewInventoryAnDeviceInformation(position);
                }


            });*/


       /*     holder.imgV_UpdateData.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    _sharedprefernce = getSharedPreferences("isUpdateEnabled",
                            MODE_PRIVATE);
                    isUpdate_Pref_Editor = _sharedprefernce.edit();
                    isUpdate_Pref_Editor.clear();
                    isUpdate_Pref_Editor.putString("isUpdateEnabled", "1");
                    isUpdate_Pref_Editor.commit();
                    viewInventoryInformation(position);

                }
            });*/
            return convertView;
        }

        class ViewHolder {
            RelativeLayout rL_UpdateDeleteData;
            TextView txtVw_DeviceName, txtVw_view_eqStatus_serialNo, txtVw_Status;
            ImageView imgV_ViewData, imgV_UpdateData, imgV_DeleteData;
            LinearLayout lLayout_inflate_masterdevice;

        }
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
                    isUpdate = false;

                    Mst_Equipment_Status mst_eqStatus = new Mst_Equipment_Status(ctx);
                    mst_eqStatus.open();
                    mst_eqStatus.deleteFlagActiveStatus(selectedEquId);
                    getMstEquipmentEnrollmentStatus();

                    eTxt_eq_Name.setText("");
                    imgVw_EqImg.setImageResource(R.drawable.medical_equiment);
                    lLayout_eq_Name.setVisibility(View.GONE);
                    lLayout_active.setVisibility(View.GONE);
                    btn_fab_equipMstSave.setVisibility(View.GONE);
                    btn_fab_equipMstSave.setImageResource(R.drawable.save);
                    btn_fab_equipMstAddNew.setVisibility(View.VISIBLE);
                    btn_fab_equipMstDelete.setVisibility(View.GONE);

                    getMstEquipmentEnrollmentStatus();
                    selectedEquId = "";

                } else {
                    isUpdate = false;

                    eTxt_eq_Name.setText("");
                    imgVw_EqImg.setImageResource(R.drawable.medical_equiment);
                    lLayout_eq_Name.setVisibility(View.GONE);
                    lLayout_active.setVisibility(View.GONE);
                    lLayout_isstandard.setVisibility(View.GONE);
                    lLayout_insImage.setVisibility(View.GONE);
                    btn_fab_equipMstSave.setVisibility(View.GONE);
                    btn_fab_equipMstSave.setImageResource(R.drawable.save);
                    btn_fab_equipMstAddNew.setVisibility(View.VISIBLE);
                    btn_fab_equipMstDelete.setVisibility(View.GONE);
                    BusinessAccessLayer.editPage = false;
                    getMstEquipmentEnrollmentStatus();
                    selectedEquId = "";
                }

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();
                isUpdate = false;

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

        mContactUsDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mContactUsDialog.show();

    }


}
