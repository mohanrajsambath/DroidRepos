package org.next.equmed.uil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.next.equmed.R;
import org.next.equmed.bal.BusinessAccessLayer;
import org.next.equmed.dal.Trn_Documents;
import org.next.equmed.dal.Trn_Equipment_Enroll_Accessories;
import org.next.equmed.dal.Trn_Equipment_Enrollment;
import org.next.equmed.dal.Trn_Images;
import org.next.equmed.dal.Trn_Installation_Enrollment;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MedicalEquipmentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Typeface calibri_typeface,calibri_bold_typeface;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
       // BusinessAccessLayer.bug_class_name ="MedicalEquipmentActivity";
      //  Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView( R.layout.medical_equipment_tabs );

        calibri_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getAssets(), "fonts/Calibri_Bold.ttf");


        toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        viewPager = ( ViewPager ) findViewById( R.id.viewpager );
        setupViewPager( viewPager );

        tabLayout = ( TabLayout ) findViewById( R.id.tabs );
        tabLayout.setupWithViewPager( viewPager );
    }

    /**
     * Let's the user tap the activity icon to go 'home'.
     * Requires setHomeButtonEnabled() in onCreate().
     */
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem menuItem ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = menuItem.getItemId();


        if ( id == android.R.id.home ) {
            // ProjectsActivity is my 'home' activity

            goToBack();
//            startActivityAfterCleanup( DashBoardActivity.class );

            return true;
        }


        /*//noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected( menuItem );
    }

    private void startActivityAfterCleanup( Class< ? > cls ) {
//        if (projectsDao != null) projectsDao.close();
        Intent intent = new Intent( getApplicationContext(), cls );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        goToBack();
    }

    private void goToBack() {

        System.out.println("BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG " + BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG);
        if ( BusinessAccessLayer.MEDICAL_EQUIPMENT_FLAG == 0 ) {

            showGobackDialog(MedicalEquipmentActivity.this, "Information is not saved, Confirm to exit?");

        } else {

          /*  System.out.println("BusinessAccessLayer.UPDATE_GOBACK_FLAG "+BusinessAccessLayer.UPDATE_GOBACK_FLAG);

            if(BusinessAccessLayer.UPDATE_GOBACK_FLAG==1) {
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = "";
                Intent homePageIntent = new Intent( MedicalEquipmentActivity.this,ViewMedicalEquipAppCompat.class );
                // turnOffGPS();
                startActivity( homePageIntent );
                finish();
            }
            else {*/
                showContactUsDialog(MedicalEquipmentActivity.this, "Information is not saved, Confirm to exit?");
          //  }
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
                boolean isInstallationExists = checkInstallation( BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID );
                if ( isInstallationExists == true ) {
                    Intent homePageIntent = new Intent( MedicalEquipmentActivity.this,ViewMedicalEquipAppCompat.class );
                    // turnOffGPS();
                    startActivity( homePageIntent );
                    finish();
                } else {
                    BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
                        BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = "";
                        Intent homePageIntent = new Intent( MedicalEquipmentActivity.this,ViewMedicalEquipAppCompat.class );
                        // turnOffGPS();
                        startActivity( homePageIntent );
                        finish();
//                    Trn_Installation_Enrollment trnInstall = new Trn_Installation_Enrollment( MedicalEquipmentActivity.this );
//                    trnInstall.open();
//                    long insVal = trnInstall.insert_installation_enroll(
//                            BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID,
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "",
//                            "0",
//                            BusinessAccessLayer.SYNC_STATUS_VALUE,
//                            BusinessAccessLayer.mUserId,
//                            getCurrentDateWithTime(),
//                            BusinessAccessLayer.mUserId,
//                            getCurrentDateWithTime() );
//                    if ( insVal != 0 ) {
//                        BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
//                        BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = "";
//                        Intent homePageIntent = new Intent( MedicalEquipmentActivity.this,ViewMedicalEquipAppCompat.class );
//                        // turnOffGPS();
//                        startActivity( homePageIntent );
//                        finish();
//                    }
                }

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


    public void showContactUsDialog(final Context ctx, String txt) {


        final Dialog mContactUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mContactUsDialog.setContentView(R.layout.dialog_view);

        mContactUsDialog.setCancelable(true);

        final Button yes = (Button) mContactUsDialog.findViewById(R.id.btn_yes);
        TextView txt_dia = (TextView) mContactUsDialog.findViewById(R.id.txt_dia);
        txt_dia.setText(txt);

        yes.setText("Yes");
        Button no = (Button) mContactUsDialog.findViewById(R.id.btn_no);
        no.setText("No");

        yes.setTypeface(calibri_bold_typeface);
        txt_dia.setTypeface(calibri_bold_typeface);
        no.setTypeface(calibri_bold_typeface);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();
                BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID = "";
                BusinessAccessLayer.VIEW_MEDICAL_HOSPITAL_ID = "";
                Intent homePageIntent = new Intent( MedicalEquipmentActivity.this,ViewMedicalEquipAppCompat.class );
                // turnOffGPS();
                startActivity( homePageIntent );
                finish();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContactUsDialog.dismiss();
            }
        });

        mContactUsDialog.show();

    }

    protected String getCurrentDateWithTime() {
        Date d = new Date();
        // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String formattedDate = df.format( d.getTime() );
        return formattedDate;
    }

    private boolean checkInstallation( String viewMedicalEquipmentEnrollId ) {

        Trn_Installation_Enrollment trnInstall = new Trn_Installation_Enrollment( MedicalEquipmentActivity.this );
        trnInstall.open();
        Cursor insVal = trnInstall.fetchByInst_EquipmentEnroll_Id( BusinessAccessLayer.VIEW_MEDICAL_EQUIPMENT_ENROLL_ID );

        if ( insVal.getCount() > 0 ) {
            return true;
        } else {
            return false;
        }
    }

    private void setupViewPager( ViewPager viewPager ) {
        ViewPagerAdapter adapter = new ViewPagerAdapter( getSupportFragmentManager() );
        adapter.addFragment( new MedicalEquipFragment(), "Equipment" );
        adapter.addFragment( new InstallationDetailsFragment(), "Installation" );
        adapter.addFragment( new ServiceFragment(), "Service" );
        adapter.addFragment( new Warranty_Fragment_Activity(), "Warranty" );
        adapter.addFragment( new TrainingFragment(), "Training" );
        adapter.addFragment( new ConsumablesFragment(), "Consumables" );
        adapter.addFragment( new VoiceOfCustomerFragment(), "Voice of Customer" );

//        adapter.addFragment(new TwoFragment(), "TWO");
//        adapter.addFragment(new ThreeFragment(), "THREE");
        viewPager.setAdapter( adapter );
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List< Fragment > mFragmentList = new ArrayList<>();
        private final List< String > mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter( FragmentManager manager ) {
            super(manager);
        }

        @Override
        public Fragment getItem( int position ) {
            return mFragmentList.get( position );
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment( Fragment fragment, String title ) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add( title );
        }

        @Override
        public CharSequence getPageTitle( int position ) {
            return mFragmentTitleList.get( position );
        }
    }
}
