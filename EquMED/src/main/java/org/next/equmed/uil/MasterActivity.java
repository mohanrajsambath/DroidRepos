package org.next.equmed.uil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.next.equmed.R;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.nal.HttpRequest;
import org.next.equmed.nal.NetworkAccessLayer_RESTFUL;

import java.util.ArrayList;
import java.util.List;


public class MasterActivity extends AppCompatActivity {

    private Toolbar toolbar_hospital;
    private TabLayout tabLayout_hospital;
    private ViewPager viewpager_hospital;
    private ListView list_Master_details;
    ViewPagerAdapter adapter;

    private static Dialog mContactUsDialog, mTechnicalUsDialog;
    Typeface calibri_typeface,calibri_bold_typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BusinessAccessLayer.bug_class_name ="MasterActivity";

        calibri_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri_Bold.ttf");

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.hospital_tabs);

        toolbar_hospital = (Toolbar) findViewById(R.id.toolbar_hospital);
        setSupportActionBar(toolbar_hospital);
        this.setTitle("EquMED - Master");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewpager_hospital = (ViewPager) findViewById(R.id.viewpager_hospital);
        setupViewPager(viewpager_hospital);

        list_Master_details = (ListView) findViewById(R.id.list_Master_details);
        tabLayout_hospital = (TabLayout) findViewById(R.id.tabs_hospital);
        tabLayout_hospital.setupWithViewPager(viewpager_hospital);
        viewpager_hospital.setVisibility(View.VISIBLE);
        tabLayout_hospital.setVisibility(View.VISIBLE);
        list_Master_details.setVisibility(View.GONE);
        System.out.println("BusinessAccessLayer.editPage in oncreate " + BusinessAccessLayer.editPage);
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
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        switch (item.getItemId()) {

            case R.id.contact_Us:
                showTechincalUsDialog(MasterActivity.this);
                return true;

            case R.id.about_Us:
                showContactUsDialog(MasterActivity.this);
                return true;
            case R.id.log_out:
                gotoBackLogout();
                return true;
            case android.R.id.home:
                goToBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                if (NetworkAccessLayer_RESTFUL.isOnline(MasterActivity.this) == true) {
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

                    startActivity(Intent.createChooser(emailIntent, "Select an email client:"));
                } else {
                    UserInterfaceLayer.alert(MasterActivity.this, "Please check your network connection", 1);
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

        txtVw_webLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView txtVw_visitUs = (TextView) mContactUsDialog.findViewById(R.id.txtVw_visitUs);
        TextView txtVw_contactUs = (TextView) mContactUsDialog.findViewById(R.id.txtVw_contactUs);

        txtVw_title.setTypeface(calibri_bold_typeface);
        txtVw_companyName.setTypeface(calibri_bold_typeface);
        txtVw_details.setTypeface(calibri_typeface);
        txtVw_phone.setTypeface(calibri_typeface);
        txtVw_webLink.setTypeface(calibri_typeface);
        txtVw_visitUs.setTypeface(calibri_typeface);
        txtVw_contactUs.setTypeface(calibri_typeface);

        if (NetworkAccessLayer_RESTFUL.isOnline(MasterActivity.this) == true) {
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

        mContactUsDialog.show();
    }

//    public boolean onOptionsItemSelected(MenuItem menuItem) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = menuItem.getItemId();
//
//
//        if (id == android.R.id.home) {
//            // ProjectsActivity is my 'home' activity
//
////            startActivityAfterCleanup( DashBoardActivity.class );
//            goToBack();
//            return true;
//        }
//
//
//        /*//noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }*/
//
//        return super.onOptionsItemSelected(menuItem);
//    }

    private void gotoBackLogout() {
        logoutDialog(MasterActivity.this, "Do you want to logout ? ");

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

        txt_dia.setTypeface(calibri_bold_typeface);
        yes.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();
                BusinessAccessLayer.mParetnRoleId = "";
                Intent intent = new Intent(MasterActivity.this, LoginActivity.class);
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
//        Intent intent = new Intent( getApplicationContext(), cls );
//        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        startActivity( intent );
        goToBack();
    }

    @Override
    public void onBackPressed() {
//        if ( BusinessAccessLayer.editPage == true ) {
//            BusinessAccessLayer.editPage = false;
//            Intent refreshIntent = new Intent( this, MasterActivity.class );
//            startActivity( refreshIntent );
//        } else {
//            Intent dashboardIntent = new Intent( this, DashBoardActivity.class );
//            startActivity( dashboardIntent );
//
//        }
        goToBack();

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

    private void goToBack() {
        hideKeyBoard();

        FragmentManager db = getSupportFragmentManager();
        System.out.println("FragmentManager count" + db.getBackStackEntryCount());

        if (BusinessAccessLayer.editPage == true) {
//            System.out.println("BusinessAccessLayer.editPage in if " + BusinessAccessLayer.editPage);
            BusinessAccessLayer.editPage = false;
//            System.out.println("BusinessAccessLayer.UPDATE_GOBACK_FLAG "+BusinessAccessLayer.UPDATE_GOBACK_FLAG);

            showGobackDialog(MasterActivity.this, "Information is not saved, Confirm to exit?");

           /* if(BusinessAccessLayer.UPDATE_GOBACK_FLAG == 1) {
                showGobackDialog(MasterActivity.this, "Are you sure you want to go back?");
            }
            else
            {
                refresh();
            }*/
        } else {
            System.out.println("BusinessAccessLayer.editPage in else " + BusinessAccessLayer.editPage);
            Intent dashboardIntent = new Intent(this, DashBoardActivity.class);
            startActivity(dashboardIntent);
            finish();
        }
//        if ( BusinessAccessLayer.editPage == true ) {
//            BusinessAccessLayer.editPage = false;
//            Intent refreshIntent = new Intent( this, MasterActivity.class );
//            startActivity( refreshIntent );
//        } else {
//            startActivityAfterCleanup( DashBoardActivity.class );
//
//        }
    }

    public void refresh()
    {
        Intent refreshIntent = new Intent(this, MasterActivity.class);
        startActivity(refreshIntent);
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
                refresh();

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


    private void setupViewPager(ViewPager viewPager) {

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Master_Hospt_Enroll_Fragment(), "Hospital ");
        adapter.addFragment(new Master_Equipment_Enroll_Fragment(), "Equipment ");


//        adapter.addFragment(new TwoFragment(), "TWO");
//        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
