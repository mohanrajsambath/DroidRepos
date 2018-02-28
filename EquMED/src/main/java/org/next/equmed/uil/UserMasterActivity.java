package org.next.equmed.uil;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.next.equmed.R;
import org.next.equmed.bal.Bean;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.dal.Mst_Hospital_Enrollment;
import org.next.equmed.dal.Trn_User_Registration;
import org.next.equmed.nal.NetworkAccessLayer_RESTFUL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;


import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


public class UserMasterActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Bean> userDetailsArray = new ArrayList<Bean>();

    private Toolbar toolbar_userMaster;
    private TabLayout tabs_userMaster;
    private ViewPager viewpager_userMaster;
    private ScrollView scr_adduser;
    private RelativeLayout rLayout_userView;
    private ArrayList<CheckBox> ctv = new ArrayList<CheckBox>();

    Typeface custom_font;

    TextView txtVw_adminYesNo, txtVw_active, txtVw_activeYesNo;

    EditText eTxt_userEmail, eTxt_firstName, eTxt_lastName, eTxt_userPhoneNo, eTxt_userPassword, eTxt_userConfirmPassword;

    Switch switch_admin, switch_active;

    CheckBox chkbox_ShowPassword, chkbox_select_hos;

    ImageView imgVw_userPhoto;

    //    Button btn_userSave;
    FloatingActionButton btn_fab_userSave, btn_fab_userAdd;
    LinearLayout lLayout_changePassword;

    EditText eTxt_uPassUpdate, eTxt_userNewPassword, eTxt_UserNewConfirmPassword;

    String userPassword = "", str_eTxt_update_UserNewPassword = "", str_eTxt_update_UserNewConfirmPassword = "";

    ScrollView scr_usrCreation;

    private String filepath = "Database";
    private String imeistring;
    private String substringtime;
    protected String todaytime;

    HospitalListAdapter assignAdapter;

    private final Pattern hasUppercase = Pattern.compile("[A-Z]");
    private final Pattern hasLowercase = Pattern.compile("[a-z]");
    private final Pattern hasNumber = Pattern.compile("\\d");
    private final Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");

//    int counterId = 1;

    String str_eTxt_useremail = "", str_eTxt_userfname = "", str_eTxt_userlname = "", str_eTxt_phoneno = "", str_eTxt_userpassword = "", str_swt_IsAdmin = "", str_swt_IsActive = "";

    LinearLayout lLayout_userProfileImage, lLayout_userEmail, lLayout_firstName, lLayout_LastName, lLayout_userPhoneNo,
            lLayout_userPassword, lLayout_userConfirmPassword, lLayout_ShowPassword, lLayout_admin, lLayout_active;
    CheckBox chkbox_ShowPassword_update;
    Button btn_ChangePassword;
    FloatingActionButton btn_fab_user_delete, btn_fab_user_update;


    Bitmap userBitmap;
    String selectedHosIDs = "";
    String login_userId = "";
    String str_currentDate = "";
    ListView list_HospitalAssign;


    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;

    //  ArrayList< Bean > userDetails = new ArrayList< Bean >();

    private String uploadUserImageStr = "";
    boolean isAddedNew = true;
    private ListView list_view_userDetails;
    private int selectedPostion;
    //    private String userId,userEmail;
    String selectedUserId = "", selectedUserEmail = "", selectedUserStartDate = "", selectedUserEndDate = "";
    ;
    private AutoCompleteTextView autoTxt_uNameUpdate;

    boolean isVisible;
    private boolean isUpdateuser;
    private String str_CreatedBy = "", str_CreatedOn = "";
    private String userName = "";

    int screen = 0;
    public ArrayList<Bean> hospitalDetailsArray = new ArrayList<Bean>();
    String assignedHospitalDetails = "";
    LinearLayout layout_add_password, layout_update_password;
    private TextView txtVw_nouserfound;

    private static Dialog mContactUsDialog, mTechnicalUsDialog;
    boolean isSelectAll = false;

    Spinner mSpinner;
    protected CountryAdapter mAdapter;
    protected String mLastEnteredPhone;
    protected PhoneNumberUtil mPhoneNumberUtil = PhoneNumberUtil.getInstance();
    protected SparseArray<ArrayList<Country>> mCountriesMap = new SparseArray<ArrayList<Country>>();
    protected static final TreeSet<String> CANADA_CODES = new TreeSet<String>();
    static {
        CANADA_CODES.add("204");
        CANADA_CODES.add("236");
        CANADA_CODES.add("249");
        CANADA_CODES.add("250");
        CANADA_CODES.add("289");
        CANADA_CODES.add("306");
        CANADA_CODES.add("343");
        CANADA_CODES.add("365");
        CANADA_CODES.add("387");
        CANADA_CODES.add("403");
        CANADA_CODES.add("416");
        CANADA_CODES.add("418");
        CANADA_CODES.add("431");
        CANADA_CODES.add("437");
        CANADA_CODES.add("438");
        CANADA_CODES.add("450");
        CANADA_CODES.add("506");
        CANADA_CODES.add("514");
        CANADA_CODES.add("519");
        CANADA_CODES.add("548");
        CANADA_CODES.add("579");
        CANADA_CODES.add("581");
        CANADA_CODES.add("587");
        CANADA_CODES.add("604");
        CANADA_CODES.add("613");
        CANADA_CODES.add("639");
        CANADA_CODES.add("647");
        CANADA_CODES.add("672");
        CANADA_CODES.add("705");
        CANADA_CODES.add("709");
        CANADA_CODES.add("742");
        CANADA_CODES.add("778");
        CANADA_CODES.add("780");
        CANADA_CODES.add("782");
        CANADA_CODES.add("807");
        CANADA_CODES.add("819");
        CANADA_CODES.add("825");
        CANADA_CODES.add("867");
        CANADA_CODES.add("873");
        CANADA_CODES.add("902");
        CANADA_CODES.add("905");
    }

    Typeface calibri_typeface,calibri_bold_typeface;

    private Uri picUri;
    public File f;
    public File file;

    protected AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Country c = (Country) mSpinner.getItemAtPosition(position);
            if (mLastEnteredPhone != null && mLastEnteredPhone.startsWith(c.getCountryCodeStr())) {
                return;
            }
            eTxt_userPhoneNo.getText().clear();
            eTxt_userPhoneNo.getText().insert(eTxt_userPhoneNo.getText().length() > 0 ? 1 : 0, String.valueOf(c.getCountryCode()));
            eTxt_userPhoneNo.setSelection(eTxt_userPhoneNo.length());
            mLastEnteredPhone = null;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusinessAccessLayer.bug_class_name = "UserMasterActivity";
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.user_add);

        calibri_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri_Bold.ttf");

        getViewCasting();
        showPassword();
        hideLayouts();
        hideButtons();
        initCodes(this);
        chkbox_select_hos.setChecked(false);

        screen = 0;
        rLayout_userView.setVisibility(View.VISIBLE);
        btn_fab_userAdd.setVisibility(View.VISIBLE);

        getUserInformation();
        setUserDetails();
        exportDatabse(BusinessAccessLayer.DATABASE_NAME);

        str_swt_IsAdmin = BusinessAccessLayer.IS_ACTIVE_STATUS_FAIL;
        str_swt_IsActive = BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS;

        switch_admin.setChecked(false);
        switch_active.setChecked(true);

        isVisible = false;

        eTxt_firstName.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }
        });

        eTxt_lastName.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if (cs.equals("")) { // for backspace
                            return cs;
                        }
                        if (cs.toString().matches("[a-zA-Z ]+")) {
                            return cs;
                        }
                        return "";
                    }
                }
        });


    }



    protected OnPhoneChangedListener mOnPhoneChangedListener = new OnPhoneChangedListener() {
        @Override
        public void onPhoneChanged(String phone) {
            try {
                mLastEnteredPhone = phone;
                Phonenumber.PhoneNumber p = mPhoneNumberUtil.parse(phone, null);
                ArrayList<Country> list = mCountriesMap.get(p.getCountryCode());
                Country country = null;
                if (list != null) {
                    if (p.getCountryCode() == 1) {
                        String num = String.valueOf(p.getNationalNumber());
                        if (num.length() >= 3) {
                            String code = num.substring(0, 3);
                            if (CANADA_CODES.contains(code)) {
                                for (Country c : list) {
                                    // Canada has priority 1, US has priority 0
                                    if (c.getPriority() == 1) {
                                        country = c;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (country == null) {
                        for (Country c : list) {
                            if (c.getPriority() == 0) {
                                country = c;
                                break;
                            }
                        }
                    }
                }
                if (country != null) {
                    final int position = country.getNum();
                    mSpinner.post(new Runnable() {
                        @Override
                        public void run() {
                            mSpinner.setSelection(position);
                        }
                    });
                }
            } catch (NumberParseException ignore) {
            }

        }
    };

    private void getViewCasting() {
        toolbar_userMaster = (Toolbar) findViewById(R.id.toolbar_userMaster);
        setSupportActionBar(toolbar_userMaster);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.setTitle("EquMED - User");

        rLayout_userView = (RelativeLayout) findViewById(R.id.rLayout_userView);
        scr_adduser = (ScrollView) findViewById(R.id.scr_adduser);
//        autoTxt_uNameUpdate = (AutoCompleteTextView) findViewById(R.id.eTxt_uNameUpdate);
        layout_update_password = (LinearLayout) findViewById(R.id.layout_update_password);
        layout_add_password = (LinearLayout) findViewById(R.id.layout_add_password);

        TextView txtVw_userPhoto = (TextView) findViewById(R.id.txtVw_userPhoto);
        TextView txtVw_userEmail = (TextView) findViewById(R.id.txtVw_userEmail);
        TextView txtVw_firstName = (TextView) findViewById(R.id.txtVw_firstName);
        TextView txtVw_lastName = (TextView) findViewById(R.id.txtVw_lastName);
        TextView txtVw_userPhoneNo = (TextView) findViewById(R.id.txtVw_userPhoneNo);
        TextView txtVw_userPassword = (TextView) findViewById(R.id.txtVw_userPassword);
        TextView txtVw_userConfirmPassword = (TextView) findViewById(R.id.txtVw_userConfirmPassword);
        txtVw_nouserfound = (TextView) findViewById(R.id.txtVw_nouserfound);


        mSpinner = (Spinner) findViewById(R.id.country_spinner);
        mSpinner.setOnItemSelectedListener(mOnItemSelectedListener);

        mAdapter = new CountryAdapter(this);

        mSpinner.setAdapter(mAdapter);

        //String photo = "Profile Photo";
        String email = "User Name";
        String firstname = "First Name";
        String lastname = "Last Name";
        String phoneno = "Phone No";
        String password = "Password";
        String confirmpassword = "Confirm Password";
        String oldpassword = "Old Password";
        String newconfirmpassword = "New Password";


        String asterisk = "<font color='#EE0000'> *</font>";

        //txtVw_userPhoto.setText( Html.fromHtml( photo + asterisk ) );
        txtVw_userEmail.setText(Html.fromHtml(email + asterisk));
        txtVw_firstName.setText(Html.fromHtml(firstname + asterisk));
        txtVw_lastName.setText(Html.fromHtml(lastname + asterisk));
        txtVw_userPhoneNo.setText(Html.fromHtml(phoneno + asterisk));
        txtVw_userPassword.setText(Html.fromHtml(password + asterisk));
        txtVw_userConfirmPassword.setText(Html.fromHtml(confirmpassword + asterisk));


        txtVw_adminYesNo = (TextView) findViewById(R.id.txtVw_adminYesNo);
        txtVw_active = (TextView) findViewById(R.id.txtVw_active);
        txtVw_activeYesNo = (TextView) findViewById(R.id.txtVw_activeYesNo);

        eTxt_userEmail = (EditText) findViewById(R.id.eTxt_userEmail);
        eTxt_firstName = (EditText) findViewById(R.id.eTxt_firstName);

//        eTxt_firstName = new EditText(this);
//        eTxt_firstName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        eTxt_lastName = (EditText) findViewById(R.id.eTxt_lastName);
        eTxt_userPhoneNo = (EditText) findViewById(R.id.eTxt_userPhoneNo);
        eTxt_userPassword = (EditText) findViewById(R.id.eTxt_userPassword);
        eTxt_userConfirmPassword = (EditText) findViewById(R.id.eTxt_userConfirmPassword);

        eTxt_userPhoneNo.addTextChangedListener(new CustomPhoneNumberFormattingTextWatcher(mOnPhoneChangedListener));
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (dstart > 0 && !Character.isDigit(c)) {
                        return "";
                    }
                }
                return null;
            }
        };

        eTxt_userPhoneNo.setFilters(new InputFilter[]{filter});

        //eTxt_userEmail.setInputType( InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
        eTxt_firstName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        eTxt_lastName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        // eTxt_userPhoneNo.setInputType( InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
        //  eTxt_userPassword.setInputType( InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
        // eTxt_userConfirmPassword.setInputType( InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );


        switch_admin = (Switch) findViewById(R.id.switch_admin);
        switch_active = (Switch) findViewById(R.id.switch_active);

        chkbox_ShowPassword = (CheckBox) findViewById(R.id.chkbox_ShowPassword);
        chkbox_select_hos = (CheckBox) findViewById(R.id.chkbox_select_hos);

        imgVw_userPhoto = (ImageView) findViewById(R.id.imgVw_userPhoto);

//        btn_userBack = ( Button ) findViewById( R.id.btn_userBack );
        btn_fab_userSave = (FloatingActionButton) findViewById(R.id.btn_fab_userSave);
        btn_fab_userAdd = (FloatingActionButton) findViewById(R.id.btn_fab_userAdd);

        list_view_userDetails = (ListView) findViewById(R.id.list_view_userDetails);
        rLayout_userView = (RelativeLayout) findViewById(R.id.rLayout_userView);


        lLayout_changePassword = (LinearLayout) findViewById(R.id.lLayout_changePassword);

        TextView txtVw_uPassUpdate = (TextView) findViewById(R.id.txtVw_uPassUpdate);
        TextView txtV_userNewPassword = (TextView) findViewById(R.id.txtV_userNewPassword);
        TextView txtV_UserNewConfirmPassword = (TextView) findViewById(R.id.txtV_UserNewConfirmPassword);
        eTxt_uPassUpdate = (EditText) findViewById(R.id.eTxt_uPassUpdate);
        eTxt_userNewPassword = (EditText) findViewById(R.id.eTxt_userNewPassword);
        eTxt_UserNewConfirmPassword = (EditText) findViewById(R.id.eTxt_UserNewConfirmPassword);
        txtVw_uPassUpdate.setText(Html.fromHtml(oldpassword + asterisk));
        txtV_userNewPassword.setText(Html.fromHtml(newconfirmpassword + asterisk));
        txtV_UserNewConfirmPassword.setText(Html.fromHtml(confirmpassword + asterisk));

        chkbox_ShowPassword_update = (CheckBox) findViewById(R.id.chkbox_ShowPassword_update);
        chkbox_ShowPassword = (CheckBox) findViewById(R.id.chkbox_ShowPassword);

        TextView txtVw_view_user_serialNo = (TextView) findViewById(R.id.txtVw_view_user_serialNo);
        TextView txtVw_view_UserFirstName = (TextView) findViewById(R.id.txtVw_view_UserFirstName);
        TextView txtVw_view_userEmail = (TextView) findViewById(R.id.txtVw_view_userEmail);
        TextView txtVw_view_user_updatedelete = (TextView) findViewById(R.id.txtVw_view_user_updatedelete);
        TextView txtVw_userHospitalAssignment = (TextView) findViewById(R.id.txtVw_userHospitalAssignment);

        TextView txtVw_DeviceName = (TextView) findViewById(R.id.txtVw_DeviceName);
        TextView assign = (TextView) findViewById(R.id.assign);
        TextView txtVw_view_eqStatus_serialNo = (TextView) findViewById(R.id.txtVw_view_eqStatus_serialNo);

        txtVw_userPhoto.setTypeface(calibri_typeface);
        txtVw_userEmail.setTypeface(calibri_typeface);
        txtVw_firstName.setTypeface(calibri_typeface);
        txtVw_lastName.setTypeface(calibri_typeface);
        txtVw_userPhoneNo.setTypeface(calibri_typeface);
        txtVw_userPassword.setTypeface(calibri_typeface);
        txtVw_userConfirmPassword.setTypeface(calibri_typeface);
        txtVw_nouserfound.setTypeface(calibri_typeface);
        txtVw_adminYesNo.setTypeface(calibri_typeface);
        txtVw_active.setTypeface(calibri_typeface);
        txtVw_activeYesNo.setTypeface(calibri_typeface);
        eTxt_userEmail.setTypeface(calibri_typeface);
        eTxt_firstName.setTypeface(calibri_typeface);
        eTxt_lastName.setTypeface(calibri_typeface);
        eTxt_userPhoneNo.setTypeface(calibri_typeface);
        eTxt_userPassword.setTypeface(calibri_typeface);
        eTxt_userConfirmPassword.setTypeface(calibri_typeface);
        switch_admin.setTypeface(calibri_typeface);
        switch_active.setTypeface(calibri_typeface);
        txtVw_uPassUpdate.setTypeface(calibri_typeface);
        txtV_userNewPassword.setTypeface(calibri_typeface);
        txtV_UserNewConfirmPassword.setTypeface(calibri_typeface);
        eTxt_uPassUpdate.setTypeface(calibri_typeface);
        eTxt_userNewPassword.setTypeface(calibri_typeface);
        eTxt_UserNewConfirmPassword.setTypeface(calibri_typeface);
        chkbox_ShowPassword_update.setTypeface(calibri_typeface);
        txtVw_view_user_serialNo.setTypeface(calibri_typeface);
        txtVw_view_UserFirstName.setTypeface(calibri_typeface);
        txtVw_view_userEmail.setTypeface(calibri_typeface);
        txtVw_view_user_updatedelete.setTypeface(calibri_typeface);
        txtVw_userHospitalAssignment.setTypeface(calibri_bold_typeface);
        txtVw_DeviceName.setTypeface(calibri_typeface);
        assign.setTypeface(calibri_typeface);
        txtVw_view_eqStatus_serialNo.setTypeface(calibri_typeface);
        chkbox_ShowPassword.setTypeface(calibri_typeface);



        btn_fab_user_delete = (FloatingActionButton) findViewById(R.id.btn_fab_user_delete);
        btn_fab_user_update = (FloatingActionButton) findViewById(R.id.btn_fab_user_update);
        btn_ChangePassword = (Button) findViewById(R.id.btn_ChangePassword);

        list_HospitalAssign = (ListView) findViewById(R.id.list_HospitalAssign);

        getViewClickEvents();


        TelephonyManager tm = (TelephonyManager) UserMasterActivity.this.getSystemService(Context.TELEPHONY_SERVICE);

		/*
         * getDeviceId() function Returns the unique device ID. for example,the
		 * IMEI for GSM and the MEID or ESN for CDMA phones.
		 */

        imeistring = tm.getDeviceId();
        setIMEIno();


        chkbox_select_hos.setOnClickListener(this);


    }


    protected void initCodes(Context context) {
        new AsyncPhoneInitTask(context).execute();
        eTxt_userPhoneNo.setText("675");
    }

    protected class AsyncPhoneInitTask extends AsyncTask<Void, Void, ArrayList<Country>> {

        private int mSpinnerPosition = -1;
        private Context mContext;

        public AsyncPhoneInitTask(Context context) {
            mContext = context;
        }

        @Override
        protected ArrayList<Country> doInBackground(Void... params) {
            ArrayList<Country> data = new ArrayList<Country>(233);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(mContext.getApplicationContext().getAssets().open("countries.dat"), "UTF-8"));

                // do reading, usually loop until end of file reading
                System.out.println("reader==>"+reader);
                String line;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    //process line
                    Country c = new Country(mContext, line, i);
                    data.add(c);
                    ArrayList<Country> list = mCountriesMap.get(c.getCountryCode());
                    if (list == null) {
                        list = new ArrayList<Country>();
                        mCountriesMap.put(c.getCountryCode(), list);
                    }
                    list.add(c);
                    i++;
                }
            } catch (IOException e) {
                //log the exception
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }

            System.out.println("data=="+data);
            if (!TextUtils.isEmpty(eTxt_userPhoneNo.getText())) {
                return data;
            }
            String countryRegion = PhoneUtils.getCountryRegionFromPhone(mContext);
            int code = mPhoneNumberUtil.getCountryCodeForRegion(countryRegion);
            ArrayList<Country> list = mCountriesMap.get(code);
            if (list != null) {
                for (Country c : list) {
                    if (c.getPriority() == 0) {
                        mSpinnerPosition = c.getNum();
                        break;
                    }
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<Country> data) {
            mAdapter.addAll(data);
            System.out.println("mSpinnerPosition "+mSpinnerPosition);

            if (mSpinnerPosition > 0) {
                mSpinner.setSelection(mSpinnerPosition);
            }
            else
            {
                mSpinner.setSelection(159);
            }
        }
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

        imgVw_userPhoto.setOnClickListener(this);
//        btn_userBack.setOnClickListener( this );
        btn_fab_userSave.setOnClickListener(this);
        btn_fab_userAdd.setOnClickListener(this);
        btn_fab_user_delete.setOnClickListener(this);
        btn_fab_user_update.setOnClickListener(this);
        btn_ChangePassword.setOnClickListener(this);
        switch_admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    // txtVw_adminYesNo.setText( "Yes" );
                    str_swt_IsAdmin = BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS;

                } else {
                    // txtVw_adminYesNo.setText( "No" );
                    str_swt_IsAdmin = BusinessAccessLayer.IS_ACTIVE_STATUS_FAIL;

                }
            }
        });
        switch_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    str_swt_IsActive = BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS;
                } else {
                    str_swt_IsActive = BusinessAccessLayer.IS_ACTIVE_STATUS_FAIL;


                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chkbox_select_hos:

                if (chkbox_select_hos.isChecked() == true) {


                    System.out.println("chkbox_select_hos:" + chkbox_select_hos.isChecked());
                    for (int i = 0; i < hospitalDetailsArray.size(); i++) {

                        Bean access = hospitalDetailsArray.get(i);
                        access.setAssignedHospital(true);
                        assignAdapter.notifyDataSetChanged();

                    }
                } else {
                    for (int i = 0; i < hospitalDetailsArray.size(); i++) {

                        Bean access = hospitalDetailsArray.get(i);
                        access.setAssignedHospital(false);
                        assignAdapter.notifyDataSetChanged();

                    }
                }
                break;

            case R.id.btn_fab_userSave:

                boolean isSelected = false;

                for (int i = 0; i < hospitalDetailsArray.size(); i++) {

                    if (hospitalDetailsArray.get(i).isAssignedHospital() == true) {

                        isSelected = true;
                    }


                    break;

                }

                if (isSelected == true) {
                    for (int i = 0; i < hospitalDetailsArray.size(); i++) {

                        String hospitalDetails = hospitalDetailsArray.get(i).getHos_Id() + "-" + hospitalDetailsArray.get(i).getHos_name() + "-" + hospitalDetailsArray.get(i).isAssignedHospital() + "-" + hospitalDetailsArray.get(i).getHos_location();

                        if (i > 0) {
                            assignedHospitalDetails = assignedHospitalDetails + "~" + hospitalDetails;
                        } else {
                            assignedHospitalDetails = hospitalDetails;
                        }
                    }
                    insertUserDetails();
                } else {

                    for (int i = 0; i < hospitalDetailsArray.size(); i++) {

                        String hospitalDetails = hospitalDetailsArray.get(i).getHos_Id() + "-" + hospitalDetailsArray.get(i).getHos_name() + "-" + hospitalDetailsArray.get(i).isAssignedHospital() + "-" + hospitalDetailsArray.get(i).getHos_location();

                        if (i > 0) {
                            assignedHospitalDetails = assignedHospitalDetails + "~" + hospitalDetails;
                        } else {
                            assignedHospitalDetails = hospitalDetails;
                        }
                    }
                    insertUserDetails();


                }


                break;

            case R.id.btn_fab_userAdd:
                getHospitalInformation();
                BusinessAccessLayer.UPDATE_GOBACK_FLAG=0;
                screen = 1;

                addNewUser();

                break;

            case R.id.imgVw_userPhoto:
                uploadImage();


                break;
            case R.id.btn_ChangePassword:
                if (isVisible == false) {


                    eTxt_uPassUpdate.setText("");
                    eTxt_userNewPassword.setText("");
                    eTxt_UserNewConfirmPassword.setText("");
                    lLayout_changePassword.setVisibility(View.VISIBLE);
                    isVisible = true;
                } else {
                    lLayout_changePassword.setVisibility(View.GONE);
                    isVisible = false;
                }


                break;

            case R.id.btn_fab_user_delete:

                deleteUser();

                break;
            case R.id.btn_fab_user_update:
                updateUser();
                break;
            default:
                break;
        }
    }

    private void deleteUser() {
        isAddedNew = false;
        showContactUsDialog(UserMasterActivity.this, BusinessAccessLayer.DELETE_MESSAGE);

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void updateUser() {


        str_eTxt_userfname = eTxt_firstName.getText().toString().trim();
        str_eTxt_userlname = eTxt_lastName.getText().toString().trim();
        str_eTxt_phoneno = eTxt_userPhoneNo.getText().toString().trim();
        str_eTxt_update_UserNewPassword = eTxt_userNewPassword.getText().toString().trim();
        str_eTxt_update_UserNewConfirmPassword = eTxt_UserNewConfirmPassword.getText()
                .toString().trim();
        for (int i = 0; i < hospitalDetailsArray.size(); i++) {

            String hospitalDetails = hospitalDetailsArray.get(i).getHos_Id() + "-" + hospitalDetailsArray.get(i).getHos_name() + "-" + hospitalDetailsArray.get(i).isAssignedHospital() + "-" + hospitalDetailsArray.get(i).getHos_location();
            if (i > 0) {
                assignedHospitalDetails = assignedHospitalDetails + "~" + hospitalDetails;
            } else {
                assignedHospitalDetails = hospitalDetails;
            }
        }

        if (str_eTxt_userfname.length() == 0) {
            showValidationDialog(UserMasterActivity.this, "Please enter first name");

        } else if (str_eTxt_userlname.length() == 0) {
            showValidationDialog(UserMasterActivity.this, "Please enter last name");

        } else if (eTxt_userPhoneNo.length() <= 8) {
            showValidationDialog(UserMasterActivity.this, "Phone number should be minimum 8 numbers");

        } else {

            if (isVisible == true) {
                if (eTxt_uPassUpdate.getText().toString().trim().length() == 0) {
                    showValidationDialog(UserMasterActivity.this, "Please enter old password");
                } else if (!eTxt_uPassUpdate.getText().toString().trim().equals(userPassword)) {
                    showValidationDialog(UserMasterActivity.this, "Old password incorrect");
                } else if (eTxt_userNewPassword.getText().toString().trim().length() == 0) {
                    showValidationDialog(UserMasterActivity.this, "Please enter new password");
                } else if (!hasUppercase.matcher(eTxt_userNewPassword.getText().toString().trim()).find() || !hasLowercase.matcher(eTxt_userNewPassword.getText().toString().trim()).find() || !hasNumber.matcher(eTxt_userNewPassword.getText().toString().trim()).find() || !hasSpecialChar.matcher(eTxt_userNewPassword.getText().toString().trim()).find()) {
                    showValidationDialog(UserMasterActivity.this, "Password should have atleast one Lowercase, Uppercase, Number and Special character");
                } else if (eTxt_UserNewConfirmPassword.getText().toString().trim().length() == 0) {
                    showValidationDialog(UserMasterActivity.this, "Please enter confirm password");
                } else if (!eTxt_userNewPassword.getText().toString().trim().equalsIgnoreCase(eTxt_UserNewConfirmPassword.getText().toString().trim())) {
                    showValidationDialog(UserMasterActivity.this, "New password and confirm password mismatch");
                } else {
                    Trn_User_Registration mst_user = new Trn_User_Registration(
                            UserMasterActivity.this);
                    mst_user.open();
                    Cursor cur = mst_user.fetchimage(selectedUserId);
                    System.out.println("Cur count: " + cur.getCount());
                    String pr_image = cur.getString(cur.getColumnIndex("" + BusinessAccessLayer.USER_IMAGE + ""));
                    System.out.println("Cur: " + pr_image);
                    if (!pr_image.equals("") && !pr_image.equals(uploadUserImageStr)) {
                        File tempImg = new File(this.getFilesDir() + "/temp_images/" + pr_image);
                        File tempImg1 = new File(this.getFilesDir() + "/images/" + pr_image);

                        if (tempImg.exists() == true) {
                            tempImg.delete();
                        } else {
                            tempImg1.delete();
                        }
                    }

                    /*if ( cur.getCount() > 0 ) {


                        String pr_image = cur.getString(cur.getColumnIndex("" + BusinessAccessLayer.USER_IMAGE + ""));
                        System.out.println("Cur: " + pr_image);
                        if (!pr_image.equals("")) {
                            File tempImg = new File(this.getFilesDir() + "/temp_images/" + pr_image);
                            File tempImg1 = new File(this.getFilesDir() + "/images/" + pr_image);

                            if (tempImg.exists() == true) {
                                tempImg.delete();
                            } else {
                                tempImg1.delete();
                            }
                        }
                    }*/

                    if(str_swt_IsActive.equals(BusinessAccessLayer.IS_ACTIVE_STATUS_FAIL))
                    {
                        selectedUserEndDate = getCurrentDate();
                    }

                    isUpdateuser = mst_user.update_user_credential(selectedUserId,
                            selectedUserEmail,
                            str_eTxt_userfname,
                            str_eTxt_userlname,
                            str_eTxt_phoneno,
                            md5(str_eTxt_update_UserNewPassword), uploadUserImageStr, str_swt_IsAdmin,
                            str_swt_IsActive, assignedHospitalDetails, selectedUserStartDate, selectedUserEndDate, "1",
                            BusinessAccessLayer.SYNC_STATUS_VALUE,
                            str_CreatedBy,
                            str_CreatedOn,
                            BusinessAccessLayer.mUserId,
                            getCurrentDate());

                    Trn_User_Registration.close();
                    if (isUpdateuser == true) {
                        isAddedNew = true;
                        lLayout_changePassword.setVisibility(View.GONE);
                        isVisible = false;
                        eTxt_userEmail.setEnabled(true);
                        showContactUsDialog(UserMasterActivity.this, "User updated successfully");
                    } else {
                        showContactUsDialog(UserMasterActivity.this, "User updated failed");

                    }
                }
            } else {
                Trn_User_Registration mst_user = new Trn_User_Registration(
                        UserMasterActivity.this);
                mst_user.open();

                System.out.println("User ID" + selectedUserId);

                Cursor cur = mst_user.fetchimage(selectedUserId);

                /*if ( cur.getCount() > 0 ) {


                    String pr_image = cur.getString(cur.getColumnIndex("" + BusinessAccessLayer.USER_IMAGE + ""));
                    System.out.println("Cur: " + pr_image);
                    if (!pr_image.equals("")) {
                        File tempImg = new File(this.getFilesDir() + "/temp_images/" + pr_image);
                        File tempImg1 = new File(this.getFilesDir() + "/images/" + pr_image);

                        if (tempImg.exists() == true) {
                            tempImg.delete();
                        } else {
                            tempImg1.delete();
                        }
                    }
                }*/

                System.out.println("Cur count: " + cur.getCount());
                String pr_image = cur.getString(cur.getColumnIndex("" + BusinessAccessLayer.USER_IMAGE + ""));
                System.out.println("Cur: " + pr_image);
                if (!pr_image.equals("") && !pr_image.equals(uploadUserImageStr)) {
                    File tempImg = new File(this.getFilesDir() + "/temp_images/" + pr_image);
                    File tempImg1 = new File(this.getFilesDir() + "/images/" + pr_image);

                    if (tempImg.exists() == true) {
                        tempImg.delete();
                    } else {
                        tempImg1.delete();
                    }
                }

                if(str_swt_IsActive.equals(BusinessAccessLayer.IS_ACTIVE_STATUS_FAIL))
                {
                    selectedUserEndDate = getCurrentDate();
                }

                isUpdateuser = mst_user.update_user_credential(selectedUserId,
                        selectedUserEmail,
                        str_eTxt_userfname,
                        str_eTxt_userlname,
                        str_eTxt_phoneno,
                        userPassword, uploadUserImageStr, "N",
                        str_swt_IsActive, assignedHospitalDetails, selectedUserStartDate, selectedUserEndDate, "1",
                        BusinessAccessLayer.SYNC_STATUS_VALUE,
                        str_CreatedBy,
                        str_CreatedOn,
                        BusinessAccessLayer.mUserId,
                        getCurrentDate());
                Trn_User_Registration.close();
                if (isUpdateuser == true) {
                    isAddedNew = true;
                    lLayout_changePassword.setVisibility(View.GONE);
                    isVisible = false;
                    showContactUsDialog(UserMasterActivity.this, "User updated successfully");
                } else {
                    showContactUsDialog(UserMasterActivity.this, "User updated failed");
                }
            }
        }
    }

    private void setUserDetails() {
        SharedPreferences userDetails = getApplicationContext().getSharedPreferences("UserDetails",
                getApplicationContext().MODE_PRIVATE);
        userName = userDetails.getString("firstname", "");
        // txtV_logged_username.setText( "Welcome " + userName + ", " );
    }

    private void addNewUser() {
        hideLayouts();
        hideButtons();
        clearAllFields();
        eTxt_userPhoneNo.setText("675");
        mSpinner.setSelection(159);
        eTxt_userEmail.setEnabled(true);
        mSpinner.setVisibility(View.VISIBLE);
        scr_adduser.setVisibility(View.VISIBLE);
        btn_fab_userSave.setVisibility(View.VISIBLE);
        layout_add_password.setVisibility(View.VISIBLE);
    }

    /* * @Name showPassword()
* @Type No Argument Method
* @Created_By GokulRaj K.c
* @Created_On 01-12-2015
* @Updated_By
* @Updated_On
* @Description The user can click the show password checkbox to visible the entered password
*
**/
    private void showPassword() {

        chkbox_ShowPassword
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // TODO Auto-generated method stub

                        if (!isChecked) {
                            eTxt_userPassword
                                    .setTransformationMethod(PasswordTransformationMethod
                                            .getInstance());
                            eTxt_userConfirmPassword
                                    .setTransformationMethod(PasswordTransformationMethod
                                            .getInstance());
                        } else {
                            eTxt_userPassword
                                    .setTransformationMethod(HideReturnsTransformationMethod
                                            .getInstance());
                            eTxt_userConfirmPassword
                                    .setTransformationMethod(HideReturnsTransformationMethod
                                            .getInstance());
                        }

                    }
                });


        chkbox_ShowPassword_update
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // TODO Auto-generated method stub

                        if (!isChecked) {
                            eTxt_userNewPassword
                                    .setTransformationMethod(PasswordTransformationMethod
                                            .getInstance());
                            eTxt_uPassUpdate
                                    .setTransformationMethod(PasswordTransformationMethod
                                            .getInstance());
                            eTxt_UserNewConfirmPassword
                                    .setTransformationMethod(PasswordTransformationMethod
                                            .getInstance());
                        } else {
                            eTxt_userNewPassword
                                    .setTransformationMethod(HideReturnsTransformationMethod
                                            .getInstance());
                            eTxt_uPassUpdate
                                    .setTransformationMethod(HideReturnsTransformationMethod
                                            .getInstance());
                            eTxt_UserNewConfirmPassword
                                    .setTransformationMethod(HideReturnsTransformationMethod
                                            .getInstance());

                        }

                    }
                });


    }


    private void clearAllFields() {
        eTxt_userEmail.setText("");
        eTxt_firstName.setText("");
        eTxt_lastName.setText("");
        eTxt_userPhoneNo.setText("");
        eTxt_userPassword.setText("");
        eTxt_userConfirmPassword.setText("");
        switch_admin.setChecked(false);
        switch_active.setChecked(true);
        chkbox_ShowPassword.setChecked(false);
        imgVw_userPhoto.setImageResource(R.drawable.userimage);


    }

    public void getUserInformation() {

        try {

            Trn_User_Registration trnUserRegistration = new Trn_User_Registration(UserMasterActivity.this);
            trnUserRegistration.open();
            Cursor mCr_trn_userRegistration = trnUserRegistration.fetch();

            userDetailsArray.clear();
            if (mCr_trn_userRegistration.getCount() > 0) {
                for (int i = 0; i < mCr_trn_userRegistration.getCount(); i++) {
                    mCr_trn_userRegistration.moveToPosition(i);

                    Bean beanObj = new Bean();
                    String user_id = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_ID + ""));

                    String user_email = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_EMAIL + ""));

                    String user_first_name = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_FIRST_NAME + ""));

                    String user_last_name = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_LAST_NAME + ""));

                    String user_phoneno = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_PHONENO + ""));

                    String user_password = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_PASSWORD + ""));

                    String user_image = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_IMAGE + ""));

                    String user_admin = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_ADMIN + ""));

                    String isactive = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.ISACTIVE + ""));


                    String user_hospital = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_HOSPITAL + ""));


                    String user_effect_startdate = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_EFFECT_STARTDATE + ""));


                    String user_effect_enddate = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.USER_EFFECT_ENDDATE + ""));

                    String flag = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.FLAG + ""));
                    String sync_status = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.SYNC_STATUS + ""));


                    String created_by = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.CREATED_BY + ""));

                    String created_on = mCr_trn_userRegistration.getString(mCr_trn_userRegistration.getColumnIndex("" + BusinessAccessLayer.CREATED_ON + ""));


                    beanObj.setUser_id(user_id);
                    beanObj.setUser_email(user_email);
                    beanObj.setUser_first_name(user_first_name);
                    beanObj.setUser_last_name(user_last_name);
                    beanObj.setUser_phoneno(user_phoneno);
                    beanObj.setUser_password(user_password);
                    beanObj.setUser_image(user_image);
                    beanObj.setUser_admin(user_admin);
                    beanObj.setIsactive(isactive);
                    beanObj.setUser_hospital(user_hospital);
                    beanObj.setUser_effect_startdate(user_effect_startdate);
                    beanObj.setUser_effect_enddate(user_effect_enddate);
                    beanObj.setFlag(flag);
                    beanObj.setCreated_by(created_by);
                    beanObj.setCreated_on(created_on);
                    beanObj.setSync_status(sync_status);


                    userDetailsArray.add(beanObj);

                }
                mCr_trn_userRegistration.close();

            }

            Trn_User_Registration.close();

        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }

        if (userDetailsArray.size() > 0) {
            UserListAdapter adapter = new UserListAdapter(UserMasterActivity.this);
            list_view_userDetails.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            hideLayouts();
            hideButtons();
            rLayout_userView.setVisibility(View.VISIBLE);
            btn_fab_userAdd.setVisibility(View.VISIBLE);

        } else {
            System.out.println("No User Values");
            hideLayouts();
            hideButtons();
            btn_fab_userAdd.setVisibility(View.VISIBLE);
            txtVw_nouserfound.setVisibility(View.VISIBLE);
        }


    }


    public void getHospitalInformation() {

        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(UserMasterActivity.this);
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByActive();

            hospitalDetailsArray.clear();
            if (mCr_mst_hospital_enroll.getCount() > 0) {
                for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                    mCr_mst_hospital_enroll.moveToPosition(i);

                    Bean beanObj = new Bean();
                    String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                    String hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));
                    String hospital_location = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));


                    beanObj.setHos_name(hospital_name);
                    beanObj.setHos_Id(hospital_id);
                    beanObj.setAssignedHospital(false);
                    beanObj.setHos_location(hospital_location);

                    hospitalDetailsArray.add(beanObj);

                }
                mCr_mst_hospital_enroll.close();

            }

            Mst_Hospital_Enrollment.close();

        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }

        if (hospitalDetailsArray.size() > 0) {

//            list_HospitalAssign.setMinimumHeight(hospitalDetailsArray.size() * 62);

            list_HospitalAssign.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, hospitalDetailsArray.size() * 170));

            assignAdapter = new HospitalListAdapter(UserMasterActivity.this);
            list_HospitalAssign.setAdapter(assignAdapter);
            assignAdapter.notifyDataSetChanged();


        } else {
            System.out.println("No Hospital Values");
        }


    }

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
                        R.layout.inflate_assign_hospital_details, parent, false);


                holder.txtVw_assign_Hospital_serialNo = (TextView) convertView.findViewById(R.id.txtVw_assign_Hospital_serialNo);
                holder.txtVw_assign_Hospital_serialNo.setTypeface(calibri_typeface);

                holder.txtVw_assign_HospitalName = (TextView) convertView.findViewById(R.id.txtVw_assign_HospitalName);
                holder.txtVw_assign_HospitalName.setTypeface(calibri_typeface);

                holder.check_selection = (CheckBox) convertView.findViewById(R.id.check_selection);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Bean beanObj = hospitalDetailsArray.get(position);
            int sNO = position + 1;
            holder.txtVw_assign_HospitalName.setText(beanObj.getHos_name());
            holder.txtVw_assign_Hospital_serialNo.setText("" + sNO);

            holder.check_selection.setChecked(beanObj.isAssignedHospital());
            holder.check_selection.setTag(beanObj);
            holder.check_selection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox check = (CheckBox) v;
                    Bean access = (Bean) v.getTag();
                    System.out.println("check.isChecked()"+check.isChecked());
                    access.setAssignedHospital(check.isChecked());
                    assignAdapter.notifyDataSetChanged();

                    for (int i = 0; i < hospitalDetailsArray.size(); i++) {
                        Bean tempAcc = hospitalDetailsArray.get(i);
                        if (tempAcc.isAssignedHospital() == false) {
                            isSelectAll = false;
                            break;
                        } else {
                            isSelectAll = true;
                        }
                    }
                    if (isSelectAll == true) {
                        chkbox_select_hos.setChecked(true);

                    } else {
                        chkbox_select_hos.setChecked(false);

                    }

                }
            });

            return convertView;
        }


        class ViewHolder {
            TextView txtVw_assign_Hospital_serialNo, txtVw_assign_HospitalName;
            CheckBox check_selection;

        }

    }

    private class UserListAdapter extends BaseAdapter {


        private LayoutInflater l_InflaterDeviceList;


        public UserListAdapter(Context context) {

            l_InflaterDeviceList = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            return userDetailsArray.size();
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
                        R.layout.inflate_list_useritems, parent, false);


                holder.txtV_User_SerialNo = (TextView) convertView.findViewById(R.id.txtV_User_SerialNo);
                holder.txtV_User_name = (TextView) convertView.findViewById(R.id.txtV_User_name);
                holder.txtV_User_status = (TextView) convertView.findViewById(R.id.txtV_User_status);
                holder.imgV_UpdateData = (ImageView) convertView.findViewById(R.id.imgV_UpdateData);
                holder.imgV_DeleteData = (ImageView) convertView.findViewById(R.id.imgV_DeleteData);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.txtV_User_SerialNo.setTypeface(calibri_bold_typeface);
            holder.txtV_User_name.setTypeface(calibri_typeface);
            holder.txtV_User_status.setTypeface(calibri_typeface);

            String firstName = userDetailsArray.get(position).getUser_first_name();
            String lastName = userDetailsArray.get(position).getUser_last_name();

            String status = userDetailsArray.get(position).getIsactive();
            String Sync_status = userDetailsArray.get(position).getSync_status();

            if(Sync_status.equals("0"))
            {
                holder.txtV_User_SerialNo.setTextColor(Color.parseColor("#ff0000"));
                // holder.lLayout_inflate_hospitallist.setBackgroundColor(Color.parseColor("#ffffb3"));
            }
            else
            {
                holder.txtV_User_SerialNo.setTextColor(Color.parseColor("#009933"));
                // holder.lLayout_inflate_hospitallist.setBackgroundColor(Color.parseColor("#ffffff"));
            }

            if (status.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {
                status = BusinessAccessLayer.STATUS_ACTIVE;
            } else {
                status = BusinessAccessLayer.STATUS_INACTIVE;
            }
//            String name = firstName + " " + lastName + "\n" + status;

            int sNO = position + 1;
            holder.txtV_User_name.setText(userDetailsArray.get(position).getUser_email());

            holder.txtV_User_status.setText(status);
            holder.txtV_User_SerialNo.setText("" + sNO);

            holder.txtV_User_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bean beanObj = userDetailsArray.get(position);
                    showUserInformation(beanObj);
                }
            });
            holder.imgV_UpdateData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BusinessAccessLayer.UPDATE_GOBACK_FLAG=1;
                    Bean beanObj = userDetailsArray.get(position);
                    showUserInformation(beanObj);

                }


            });


            holder.imgV_DeleteData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedUserId = userDetailsArray.get(position).getUser_id();
                    selectedUserEmail = userDetailsArray.get(position).getUser_email();
                    isAddedNew = false;

                    showContactUsDialog(UserMasterActivity.this, BusinessAccessLayer.DELETE_MESSAGE);
                }
            });


            return convertView;
        }


        class ViewHolder {
            RelativeLayout rL_UpdateDeleteData;
            TextView txtV_User_SerialNo, txtV_User_name, txtV_User_status;
            ImageView imgV_UpdateData, imgV_DeleteData;

        }

    }

    private void showUserInformation(Bean beanObj) {


        hideLayouts();
        hideButtons();
        screen = 1;
        mSpinner.setVisibility(View.GONE);
        scr_adduser.setVisibility(View.VISIBLE);
        btn_fab_user_update.setVisibility(View.VISIBLE);
        btn_fab_user_delete.setVisibility(View.GONE);
        eTxt_userEmail.setEnabled(false);
        layout_update_password.setVisibility(View.VISIBLE);
        selectedUserId = beanObj.getUser_id();
        selectedUserEmail = beanObj.getUser_email();

        eTxt_userEmail.setText(selectedUserEmail);
        eTxt_firstName.setText(beanObj.getUser_first_name());
        eTxt_lastName.setText(beanObj.getUser_last_name());
        eTxt_userPhoneNo.setText(beanObj.getUser_phoneno());
        userPassword = beanObj.getUser_password();
        assignedHospitalDetails = beanObj.getUser_hospital();
        String isAdminStr = beanObj.getUser_admin();
        String isActiveStr = beanObj.getIsactive();
        uploadUserImageStr = beanObj.getUser_image();
        str_CreatedBy = beanObj.getCreated_by();
        str_CreatedOn = beanObj.getCreated_on();

        if (uploadUserImageStr.length() == 0) {
            imgVw_userPhoto.setImageResource(R.drawable.userimage);
        } else {

            String img_path;
            File tempImg = new File(this.getFilesDir() + "/temp_images/" + uploadUserImageStr);
            File tempImg1 = new File(this.getFilesDir() + "/images/" + uploadUserImageStr);

            if (tempImg.exists() == true) {
                img_path = tempImg.getAbsolutePath();
            } else {
                img_path = tempImg1.getAbsolutePath();
            }

            //    Bitmap useImage = decodeBase64( uploadUserImageStr );
            imgVw_userPhoto.setImageURI(Uri.parse(img_path));
        }

        selectedUserStartDate = beanObj.getUser_effect_startdate();
        selectedUserEndDate = beanObj.getUser_effect_enddate();


        if (isActiveStr.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {
            switch_active.setChecked(true);
        } else {
            switch_active.setChecked(false);
        }

        if (isAdminStr.equalsIgnoreCase(BusinessAccessLayer.IS_ACTIVE_STATUS_SUCCESS)) {
            switch_admin.setChecked(true);
        } else {
            switch_admin.setChecked(false);
        }

        // getHospitalInformation();

        System.out.println("assignedHospitalDetails  "+assignedHospitalDetails);
        ArrayList hos_id_list = new ArrayList();
        if(assignedHospitalDetails.length()>0) {

            String[] hospitalList = assignedHospitalDetails.split("~");
            System.out.println("hospitalList + " + hospitalList.toString());
            if (hospitalList.length > 0) {
                for (int i = 0; i < hospitalList.length; i++) {
                    String[] splitterStr = hospitalList[i].split("-");
                    if (splitterStr[2].equalsIgnoreCase("True")) {
                        hos_id_list.add(splitterStr[0]);
                    }
                }
            }
        }

        System.out.println("hos_id_list =" + hos_id_list);


        try {

            Mst_Hospital_Enrollment mst_hospital_enroll = new Mst_Hospital_Enrollment(UserMasterActivity.this);
            mst_hospital_enroll.open();
            Cursor mCr_mst_hospital_enroll = mst_hospital_enroll.fetchByActive();

            hospitalDetailsArray.clear();
            if (mCr_mst_hospital_enroll.getCount() > 0) {
                for (int i = 0; i < mCr_mst_hospital_enroll.getCount(); i++) {
                    mCr_mst_hospital_enroll.moveToPosition(i);

                    Bean beanOb = new Bean();
                    String hospital_id = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_ID + ""));

                    String hospital_name = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_NAME + ""));
                    String hospital_location = mCr_mst_hospital_enroll.getString(mCr_mst_hospital_enroll.getColumnIndex("" + BusinessAccessLayer.HOSPITAL_LOCATION + ""));


                    beanOb.setHos_name(hospital_name);
                    beanOb.setHos_Id(hospital_id);
                    if (hos_id_list.contains(hospital_id)) {
                        beanOb.setAssignedHospital(true);
                    } else {
                        beanOb.setAssignedHospital(false);
                    }
                    beanOb.setHos_location(hospital_location);

                    hospitalDetailsArray.add(beanOb);

                }
                mCr_mst_hospital_enroll.close();

            }

            Mst_Hospital_Enrollment.close();

        } catch (Exception e) {
            System.out.println("Exception e" + e);
        }

        if (hospitalDetailsArray.size() > 0) {

//            list_HospitalAssign.setMinimumHeight(hospitalDetailsArray.size() * 62);

            list_HospitalAssign.setLayoutParams(new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, hospitalDetailsArray.size() * 170));

            assignAdapter = new HospitalListAdapter(UserMasterActivity.this);
            list_HospitalAssign.setAdapter(assignAdapter);
            assignAdapter.notifyDataSetChanged();


        } else {
            System.out.println("No Hospital Values");
        }



        /*String[] hospitalList = assignedHospitalDetails.split( "," );
        hospitalDetailsArray.clear();
        for ( int i = 0; i < hospitalList.length; i++ ) {
            String[] splitterStr = hospitalList[ i ].split( "-" );


            Bean assignBean = new Bean();
            assignBean.setHos_Id( splitterStr[ 0 ] );
            assignBean.setHos_name( splitterStr[ 1 ] );
            if ( splitterStr[ 2 ].equalsIgnoreCase( "True" ) ) {
                assignBean.setAssignedHospital( true );
            } else {
                assignBean.setAssignedHospital( false );
            }
            assignBean.setHos_location( splitterStr[ 3 ] );
            hospitalDetailsArray.add( assignBean );
        }
        if ( hospitalDetailsArray.size() > 0 ) {
            list_HospitalAssign.setLayoutParams( new LinearLayout.LayoutParams( AbsListView.LayoutParams.MATCH_PARENT, hospitalDetailsArray.size() * 170 ) );
            assignAdapter = new HospitalListAdapter( UserMasterActivity.this );
            list_HospitalAssign.setAdapter( assignAdapter );
            assignAdapter.notifyDataSetChanged();
        }*/
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private void insertUserDetails() {


        if (eTxt_userEmail.getText().toString().trim().length() == 0) {
            showValidationDialog(UserMasterActivity.this, "Please enter email");
        } else if (!eTxt_userEmail.getText().toString().trim().matches(getString(R.string.emailPattern))) {
            showValidationDialog(UserMasterActivity.this, "User name should be Email address");
        } else if (eTxt_firstName.getText().toString().trim().length() == 0) {
            showValidationDialog(UserMasterActivity.this, "Please enter first name");
        } else if (eTxt_lastName.getText().toString().trim().length() == 0) {
            showValidationDialog(UserMasterActivity.this, "Please enter last name");
        } else if (eTxt_userPhoneNo.getText().toString().trim().length() <= 8) {
            showValidationDialog(UserMasterActivity.this, "Phone number should be minimum 8 numbers");
        } else if (eTxt_userPassword.getText().toString().trim().length() == 0) {
            showValidationDialog(UserMasterActivity.this, "Please enter password");
        } else if (!hasUppercase.matcher(eTxt_userPassword.getText().toString().trim()).find() || !hasLowercase.matcher(eTxt_userPassword.getText().toString().trim()).find() || !hasNumber.matcher(eTxt_userPassword.getText().toString().trim()).find() || !hasSpecialChar.matcher(eTxt_userPassword.getText().toString().trim()).find()) {
            showValidationDialog(UserMasterActivity.this, "Password should have atleast one Lowercase, Uppercase, Number and Special character");
        } else if (eTxt_userConfirmPassword.getText().toString().trim().length() == 0) {
            showValidationDialog(UserMasterActivity.this, "Password enter confirm password");
        } else if (!eTxt_userConfirmPassword.getText().toString().trim().equals(eTxt_userPassword.getText().toString().trim())) {
            showValidationDialog(UserMasterActivity.this, "Password mismatch");
        } else {
            str_eTxt_useremail = eTxt_userEmail.getText().toString();
            str_eTxt_userfname = eTxt_firstName.getText().toString();
            str_eTxt_userlname = eTxt_lastName.getText().toString();
            str_eTxt_phoneno = eTxt_userPhoneNo.getText().toString();
            str_eTxt_userpassword = eTxt_userPassword.getText().toString();
            System.out.println(str_eTxt_useremail + "\n" + str_eTxt_userfname);

            Trn_User_Registration trnUserAdapt = new Trn_User_Registration(
                    UserMasterActivity.this);
            trnUserAdapt.open();

            Cursor userCount = trnUserAdapt.fetch();
            String userIdStr = getUserId(userCount.getCount());
            long insert_status = trnUserAdapt.insert_User_Registration(
                    userIdStr,
                    str_eTxt_useremail,
                    str_eTxt_userfname,
                    str_eTxt_userlname,
                    str_eTxt_phoneno,
                    md5(str_eTxt_userpassword),
                    uploadUserImageStr,
                    "N", str_swt_IsActive,
                    assignedHospitalDetails,
                    getCurrentDate(),
                    getCurrentDate(),
                    BusinessAccessLayer.FLAG_VALUE,
                    BusinessAccessLayer.SYNC_STATUS_VALUE,
                    login_userId,
                    getCurrentDateTime(),
                    login_userId,
                    getCurrentDateTime());
            Trn_User_Registration.close();

            //clearallfields();
            if (insert_status > 0) {
                showContactUsDialog(UserMasterActivity.this, "User added successfully");
                exportDatabse(BusinessAccessLayer.DATABASE_NAME);
                clearAllFields();

            }
        }
    }

    private void hideLayouts() {

        scr_adduser.setVisibility(View.GONE);
        rLayout_userView.setVisibility(View.GONE);
        layout_update_password.setVisibility(View.GONE);
        layout_add_password.setVisibility(View.GONE);

    }

    private void hideButtons() {
        btn_fab_userAdd.setVisibility(View.GONE);
        btn_fab_userSave.setVisibility(View.GONE);
        btn_fab_user_delete.setVisibility(View.GONE);
        btn_fab_user_update.setVisibility(View.GONE);
        txtVw_nouserfound.setVisibility(View.GONE);
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

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(UserMasterActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take from camera")) {

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

        File tempImg = new File(this.getFilesDir(), "/temp_images/");

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

    protected void CropImage() {
       /* try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(picUri, "image*//*");



            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("aspectX", 5);
            intent.putExtra("aspectY", 3);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, BusinessAccessLayer.CROP_IMAGE);

        } catch (ActivityNotFoundException e) {
            //  Common.showToast(this, "Your device doesn't support the crop action!");
        }*/

        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(picUri, "image/*");


            intent.putExtra("crop", "true");
            intent.putExtra("outputX", 300);
            intent.putExtra("outputY", 300);
            intent.putExtra("aspectX", 5);
            intent.putExtra("aspectY", 3);
            intent.putExtra("scaleUpIfNeeded", true);
            intent.putExtra("return-data", true);

            List list = getPackageManager().queryIntentActivities(intent, 0);
            int size = list.size();
            File outPutFile = new File(Environment.getExternalStorageDirectory(), "temp_equmed.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {

                Intent i = new Intent(intent);
                ResolveInfo res = (ResolveInfo) list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, BusinessAccessLayer.CROP_IMAGE);
            } else {

                startActivityForResult(intent, BusinessAccessLayer.CROP_IMAGE);
            }

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
// Common.showToast(this, "Your device doesn't support the crop action!");
        }


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == UserMasterActivity.this.RESULT_OK ) {

        if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA && resultCode == RESULT_OK) {


            CropImage();



            f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp_equmed.jpg")) {

                    f = temp;

                    break;

                }

            }


//            try {
//
//                Bitmap bitmap;
//
//                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//
//
//                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//
//                        bitmapOptions);
//                int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
//                Bitmap scale = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
//                    Matrix matrix = new Matrix();
//                    matrix.postRotate(90);
//                    Bitmap scale = Bitmap.createBitmap(bitmap, 0, 0,
//                            512, nh, matrix,
//                            true);

//                String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                String[] extension = fileName.split("\\.");
//                String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                String new_file_name = n_file_name + "." + extension[1];
//
//                String tempImg = storeImage(scale, new_file_name);
//
//
//                uploadUserImageStr = new_file_name;
//
//
//                // imgVw_userPhoto.setImageURI(Uri.parse(tempImg));
//
//                imgVw_userPhoto.setImageURI(Uri.parse(tempImg));
//            } catch (Exception e) {
//
//                e.printStackTrace();
//
//            }

        } else if (requestCode == BusinessAccessLayer.PICK_FROM_GALLERY && resultCode == RESULT_OK) {


//            Uri selectedImage = data.getData();
            picUri = data.getData();

            CropImage();

            String[] filePath = {MediaStore.Images.Media.DATA};

            Cursor c = UserMasterActivity.this.getContentResolver().query(picUri, filePath, null, null, null);

            c.moveToFirst();

            int columnIndex = c.getColumnIndex(filePath[0]);

            String picturePath = c.getString(columnIndex);

            c.close();

//            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//            System.out.println("thumbnail.getHeight()" + thumbnail.getHeight());
//            System.out.println("thumbnail.getWidth()" + thumbnail.getWidth());
//
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
//            System.out.println("picturePath:::" + picturePath);
////                Log.w("path of image from gallery......******************.........", picturePath + "");
//            uploadUserImageStr = new_file_name;
//            imgVw_userPhoto.setImageURI(Uri.parse(fileDirectoryPath));


//            }
        } else if (requestCode == BusinessAccessLayer.CROP_IMAGE) {
            if (data != null) {
                Bitmap photo = null;
                if (data.getData() == null) {
                    photo = (Bitmap) data.getExtras().get("data");
                    System.out.println("====if========"+photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                } else {
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("====else========"+photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                }
//                // get the returned data
//                Bundle extras = data.getExtras();
//
//                // get the cropped bitmap
//                Bitmap photo = extras.getParcelable("data");


                String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                String[] extension = fileName.split("\\.");
                String n_file_name = extension[0] + "_" + getCurrentDateTime();
                String new_file_name = n_file_name + "." + extension[1];

//                int nh = (int) (photo.getHeight() * (512.0 / photo.getWidth()));
//                Bitmap scaled = Bitmap.createScaledBitmap(photo, 512, nh, true);
                String tempImg = storeImage(photo, new_file_name);


                uploadUserImageStr = new_file_name;


                // imgVw_userPhoto.setImageURI(Uri.parse(tempImg));

                imgVw_userPhoto.setImageURI(Uri.parse(tempImg));


                //   imgVw_userPhoto.setImageBitmap(photo);

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
                    System.out.println("====if========"+photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                } else {
                    try {
                        photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("====else========"+photo);
//                    imgVw_userPhoto.setImageBitmap(photo);
                }

//                // get the returned data
//                Bundle extras = data.getExtras();
//
//                // get the cropped bitmap
//                Bitmap photo = extras.getParcelable("data");


                String file_name = file.getName().toString();
                String[] extension = file_name.split("\\.");
                String n_file_name = extension[0] + "_" + getCurrentDateTime();
                String new_file_name = n_file_name + "." + extension[1];

                String fileDirectoryPath = storeImage(photo, new_file_name);

//                Log.w("path of image from gallery......******************.........", picturePath + "");
                uploadUserImageStr = new_file_name;
                imgVw_userPhoto.setImageURI(Uri.parse(fileDirectoryPath));


                //   imgVw_userPhoto.setImageBitmap(photo);

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
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == UserMasterActivity.this.RESULT_OK) {
//
//            if (requestCode == BusinessAccessLayer.PICK_FROM_CAMERA) {
//
//                File f = new File(Environment.getExternalStorageDirectory().toString());
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
////                    Matrix matrix = new Matrix();
////                    matrix.postRotate(90);
////                    Bitmap scale = Bitmap.createBitmap(bitmap, 0, 0,
////                            512, nh, matrix,
////                            true);
//
//                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
//                    String[] extension = fileName.split("\\.");
//                    String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                    String new_file_name = n_file_name + "." + extension[1];
//
//                    String tempImg = storeImage(scale, new_file_name);
//
//                    uploadUserImageStr = new_file_name;
//
//
//                    imgVw_userPhoto.setImageURI(Uri.parse(tempImg));
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
//                Cursor c = UserMasterActivity.this.getContentResolver().query(selectedImage, filePath, null, null, null);
//
//                c.moveToFirst();
//
//                int columnIndex = c.getColumnIndex(filePath[0]);
//
//                String picturePath = c.getString(columnIndex);
//
//                c.close();
//
//                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//
//                System.out.println("thumbnail.getHeight()" + thumbnail.getHeight());
//                System.out.println("thumbnail.getWidth()" + thumbnail.getWidth());
//
//
//                int nh = (int) (thumbnail.getHeight() * (512.0 / thumbnail.getWidth()));
//                Bitmap scaled = Bitmap.createScaledBitmap(thumbnail, 512, nh, true);
//
//
//                File file = new File(picturePath);
//                String file_name = file.getName().toString();
//                String[] extension = file_name.split("\\.");
//                String n_file_name = extension[0] + "_" + getCurrentDateTime();
//                String new_file_name = n_file_name + "." + extension[1];
//
//                String fileDirectoryPath = storeImage(scaled, new_file_name);
//
//                System.out.println("picturePath:::" + picturePath);
////                Log.w("path of image from gallery......******************.........", picturePath + "");
//                uploadUserImageStr = new_file_name;
//                imgVw_userPhoto.setImageURI(Uri.parse(fileDirectoryPath));
//
//
//            }
//        }
//
//    }

    private String getUserId(int count) {
        String imeino = setIMEIno().substring(0, 5);
        String timeStamp = getUnixTimeStamp();
        System.out.println("timeStamp" + timeStamp);
        String finalId = "USER_" + imeino + "_" + timeStamp + "_" + count;

        return finalId;
    }


    /**
     * @CreatedBy
     * @CreatedOn 05-Aug-2015,11:16:48 am,
     * @UpdatedBy
     * @UpdatedOn 05-Aug-2015,11:16:48 am,
     * @Description To get Unix_Time_Stamp for the Server Accessibility
     */
    public String getUnixTimeStamp() {

        try {
            Calendar cal = new GregorianCalendar();
            int unix_timestamp = (int) ((cal.getTimeInMillis() + cal
                    .getTimeZone().getOffset(cal.getTimeInMillis())) / 1000);
            System.out.println("Time from" + unix_timestamp);
            substringtime = Integer.toString(unix_timestamp);
            todaytime = substringtime.substring(0, 5);
            System.out.println("Unix_Time ----=>" + todaytime);
        } catch (Exception e) {
            System.out.println(e);
        }
        return todaytime;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-07-2015
     * @Updated_By
     * @Updated_On
     * @Description: To return the IMEI_NO for the global acess
     */

    public String setIMEIno() {
        // String m_IMEINO = imeistring;
        return imeistring;
    }

    /**
     * @Type Method
     * @Created_By Mohanraj.S
     * @Created_On 15-05-2015
     * @Updated_On 20-05-2015
     * @Description To Get the Current Date and Time of the Current Device
     */

    protected String getCurrentDate() {
        Date d = new Date();
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(d.getTime());
        return formattedDate;
    }

    protected String getCurrentDateTime() {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(d.getTime());

        return formattedDate;
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
                    Trn_User_Registration mst_user = new Trn_User_Registration(UserMasterActivity.this);
                    mst_user.open();

                    mst_user.deleteFlagActiveStatus(selectedUserId);

                    Trn_User_Registration.close();
                    clearAllFields();
                    hideLayouts();
                    hideButtons();
                    rLayout_userView.setVisibility(View.VISIBLE);
                    btn_fab_userAdd.setVisibility(View.VISIBLE);
                    getUserInformation();

                } else {
                    clearAllFields();
                    hideLayouts();
                    hideButtons();
                    isVisible = false;
                    rLayout_userView.setVisibility(View.VISIBLE);
                    btn_fab_userAdd.setVisibility(View.VISIBLE);
                    getUserInformation();


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


    public void showUpdateDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(true);

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
                    Trn_User_Registration mst_user = new Trn_User_Registration(UserMasterActivity.this);
                    mst_user.open();

                    mst_user.deleteFlagActiveStatus(selectedUserId);

                    Trn_User_Registration.close();
                    clearAllFields();
                    getUserInformation();

                } else {
                    clearAllFields();
                    hideLayouts();
                    hideButtons();
                    rLayout_userView.setVisibility(View.VISIBLE);
                    btn_fab_userAdd.setVisibility(View.VISIBLE);
                    System.out.println("isAddedNew " + isAddedNew);
                    getUserInformation();

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


    public void exportDatabse(String databaseName) {
        try {

            BusinessAccessLayer.myExternalFile = new File(UserMasterActivity.this.getExternalFilesDir(filepath), BusinessAccessLayer.DATABASE_NAME);
            System.out.println("directory myExternalFile   " + BusinessAccessLayer.myExternalFile);
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + UserMasterActivity.this.getPackageName()
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


    /**
     * Let's the user tap the activity icon to go 'home'.
     * Requires setHomeButtonEnabled() in onCreate().
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
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
                showTechincalUsDialog(UserMasterActivity.this);
               /* Intent contactus = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://www.nexttechnosolutions.com/" ) );
                startActivity( contactus );*/
                return true;

            case R.id.about_Us:
                showContactUsDialog(UserMasterActivity.this);
                /*Intent aboutus = new Intent( Intent.ACTION_VIEW, Uri.parse( "http://www.nexttechnosolutions.com/" ) );
                startActivity( aboutus );*/
                return true;
            case R.id.log_out:
                gotoBackLogout();
                return true;
            case android.R.id.home:
                startActivityAfterCleanup(DashBoardActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
//
//        if ( id == android.R.id.home ) {
//            // ProjectsActivity is my 'home' activity
//
//            startActivityAfterCleanup( DashBoardActivity.class );
//
//            return true;
//        }
//

        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

//        return super.onOptionsItemSelected( menuItem );
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
                if (NetworkAccessLayer_RESTFUL.isOnline(UserMasterActivity.this) == true) {
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
                    UserInterfaceLayer.alert(UserMasterActivity.this, "Please check your network connection", 1);
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

        if (NetworkAccessLayer_RESTFUL.isOnline(UserMasterActivity.this) == true) {
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
        logoutDialog(UserMasterActivity.this, "Do you want to logout ? ");

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
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();
                BusinessAccessLayer.mParetnRoleId = "";
                Intent intent = new Intent(UserMasterActivity.this, LoginActivity.class);
                startActivity(intent);
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
//        Intent intent = new Intent(getApplicationContext(), cls);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        goToBack();
    }

    @Override
    public void onBackPressed() {
        goToBack();

    }

    private void goToBack() {

        if (screen == 1) {

            showGobackDialog(UserMasterActivity.this, "Information is not saved, Confirm to exit?");

          /*  if( BusinessAccessLayer.UPDATE_GOBACK_FLAG==1)
            {
                showGobackDialog(UserMasterActivity.this, "Are you sure you want to go back?");
            }
            else {
                hideLayouts();
                hideButtons();
                hideKeyBoard();
                getUserInformation();
                lLayout_changePassword.setVisibility(View.GONE);
                isVisible = false;
                btn_fab_userAdd.setVisibility(View.VISIBLE);
                screen = 0;
            }*/


        } else {
            hideKeyBoard();
            Intent homePageIntent = new Intent(UserMasterActivity.this,
                    DashBoardActivity.class);
            // turnOffGPS();
            startActivity(homePageIntent);
            finish();

        }


    }


    public void showGobackDialog(final Context ctx, String txt) {


        final Dialog mGobackDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mGobackDialog.setContentView(R.layout.dialog_view);

        mGobackDialog.setCancelable(true);

        final Button yes = (Button) mGobackDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mGobackDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("Yes");
        Button no = (Button) mGobackDialog.findViewById(R.id.btn_no);
        no.setText("No");

        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGobackDialog.dismiss();
                hideLayouts();
                hideButtons();
                hideKeyBoard();
                getUserInformation();
                lLayout_changePassword.setVisibility(View.GONE);
                isVisible = false;
                btn_fab_userAdd.setVisibility(View.VISIBLE);
                screen = 0;
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGobackDialog.dismiss();
            }
        });

        mGobackDialog.show();

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

}
