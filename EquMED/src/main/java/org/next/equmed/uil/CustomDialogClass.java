package org.next.equmed.uil;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.next.equmed.R;
import org.next.equmed.bal.BusinessAccessLayer;
import org.w3c.dom.Text;

public class CustomDialogClass extends Dialog implements
        View.OnClickListener {

    //String phoneno = 04222971111;
    private static String mPhoneNo = "+91-422-2971111";
    public Activity activity;
    public Dialog dialog;
    public Button yes, no, btnSendMail;
    public EditText eTxtVwEailTo, eTxtVwEmailSubject, eTxtVwEmailContent;
    private TextView txt_dia;
    private String message = "";
    private static Dialog mContactUsDialog, mTechnicalUsDialog, mAppalertDialog;
    //private static boolean mDialogDismiss = true;
    public LinearLayout lLayoutMailLayout, lLayoutDialog;

    // private Button btnSendMail;

    public static boolean IS_INTERNETAVAILABLE_DIALOG;
    static Typeface calibri_typeface,calibri_bold_typeface;

    public CustomDialogClass(Activity a, String str) {
        super(a);
        message = str;
        // TODO Auto-generated constructor stub
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(true);
        setContentView(R.layout.dialog_view);
        txt_dia = (TextView) findViewById(R.id.txt_dia);
        txt_dia.setText(message);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        lLayoutMailLayout = (LinearLayout) findViewById(R.id.lLayoutMailLayout);

        calibri_typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Calibri.ttf");
        calibri_bold_typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Calibri_Bold.ttf");

        lLayoutDialog = (LinearLayout) findViewById(R.id.lLayoutDialog);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        eTxtVwEailTo = (EditText) findViewById(R.id.eTxtVwEmailTo);
        eTxtVwEmailSubject = (EditText) findViewById(R.id.editTextEmailSubject);
        eTxtVwEmailContent = (EditText) findViewById(R.id.eTxtVwEmailContent);

        btnSendMail = (Button) findViewById(R.id.btnSendMail);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        btnSendMail.setOnClickListener(this);
        messageType();
        isOnlineCustomDialog();
    }

//    private void isOnline() {
//    }

    public void isOnlineCustomDialog() {

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        NetworkInfo typemo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo tywi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
        NetworkInfo tywifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (netInfo != null && netInfo.isConnectedOrConnecting()
                || typemo != null && typemo.isConnectedOrConnecting()
                || tywi != null && tywi.isConnectedOrConnecting()
                || tywifi != null && tywifi.isConnectedOrConnecting()) {
            IS_INTERNETAVAILABLE_DIALOG = true;
//            return BusinessAccessLayer.IS_INTERNETAVAILABLE;
        } else {
            IS_INTERNETAVAILABLE_DIALOG = false;
//            return BusinessAccessLayer.IS_INTERNETAVAILABLE;
        }

    }

    private void messageType() {

        if (message.equalsIgnoreCase("Send mail")) {
            lLayoutDialog.setVisibility(View.GONE);
            Display display = activity.getWindowManager().getDefaultDisplay();

//            int width = display.getWidth()/2;
//            int height = display.getHeight();
//            lLayoutMailLayout.setLayoutParams(new
//                    LinearLayout.LayoutParams(width,
//                    LinearLayout.LayoutParams.MATCH_PARENT));

            lLayoutMailLayout.setVisibility(View.VISIBLE);

        } else {

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();


                break;
            case R.id.btn_no:
                dismiss();
                break;

           /* case R.id.imgVw_failure:
                dismiss();
                break;*/
            default:
                break;
            case R.id.btnSendMail:

                //get to, subject and content from the user input and store as string.
                String emailTo = eTxtVwEailTo.getText().toString();
                String emailSubject = eTxtVwEmailSubject.getText().toString();
                String emailContent = eTxtVwEmailContent.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
                /// use below 2 commented lines if need to use BCC an CC feature in email
                //emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ to});
                //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{to});
                ////use below 3 commented lines if need to send attachment
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Welcome to Next Inc");

                //need this to prompts email client only
                emailIntent.setType("message/rfc822");

                activity.startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));
                break;
        }
        dismiss();
    }


    public static void showAppalertDialog(final Activity ctx) {
        mAppalertDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mAppalertDialog.setContentView(R.layout.dialog_version);
//        dialog.setTitle("Contact Us");
        mAppalertDialog.setCancelable(true);

        TextView txtVw_versionNo = (TextView) mAppalertDialog.findViewById(R.id.txtVw_versionNo);

        TextView version_information = (TextView) mAppalertDialog.findViewById(R.id.version_information);

        TextView txtVw_appRelease = (TextView) mAppalertDialog.findViewById(R.id.txtVw_appRelease);

        TextView txtVw_released = (TextView) mAppalertDialog.findViewById(R.id.txtVw_released);
        TextView txtVw_version = (TextView) mAppalertDialog.findViewById(R.id.txtVw_version);

        TextView txtVw_BuildNo = (TextView) mAppalertDialog.findViewById(R.id.txtVw_BuildNo);
        TextView txtVw_BuildNoVal = (TextView) mAppalertDialog.findViewById(R.id.txtVw_BuildNoVal);
        TextView txtVw_LicenseType = (TextView) mAppalertDialog.findViewById(R.id.txtVw_LicenseType);
        TextView txtVw_LicenseTypeVal = (TextView) mAppalertDialog.findViewById(R.id.txtVw_LicenseTypeVal);
        TextView txtVw_ExpiredOn = (TextView) mAppalertDialog.findViewById(R.id.txtVw_ExpiredOn);
        TextView txtVw_appExpiredOn = (TextView) mAppalertDialog.findViewById(R.id.txtVw_appExpiredOn);


        txtVw_BuildNo.setTypeface(calibri_typeface);
        txtVw_BuildNoVal.setTypeface(calibri_typeface);
        txtVw_LicenseType.setTypeface(calibri_typeface);
        txtVw_LicenseTypeVal.setTypeface(calibri_typeface);
        txtVw_ExpiredOn.setTypeface(calibri_typeface);
        txtVw_appExpiredOn.setTypeface(calibri_typeface);

        txtVw_BuildNoVal.setText(BusinessAccessLayer.versionBuildNumber);
        txtVw_appExpiredOn.setText(BusinessAccessLayer.versionExpiryDate);

        txtVw_version.setTypeface(calibri_typeface);
        txtVw_released.setTypeface(calibri_typeface);
        version_information.setTypeface(calibri_typeface);

        txtVw_versionNo.setText(BusinessAccessLayer.versionCode);
        txtVw_versionNo.setTypeface(calibri_bold_typeface);

        txtVw_appRelease.setText(BusinessAccessLayer.versionDate);
        txtVw_appRelease.setTypeface(calibri_typeface);

        mAppalertDialog.show();
    }


    public static void showTechincalUsDialog(final Activity ctx) {
        mTechnicalUsDialog = new Dialog(ctx, R.style.FullHeightDialog);
        mTechnicalUsDialog.setContentView(R.layout.dialog_tech_us);
//        dialog.setTitle("Contact Us");
        mTechnicalUsDialog.setCancelable(true);

        TextView txtVw_title = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_title);

        TextView txtVw_addDetail = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_addDetail);

        TextView txtVw_phone = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_phone);

        TextView txtVw_nextEmail = (TextView) mTechnicalUsDialog.findViewById(R.id.txtVw_nextEmail);

        ImageView imgVw_technicalcall = (ImageView) mTechnicalUsDialog.findViewById(R.id.imgVw_technicalcall);

        ImageView imgVw_Email = (ImageView) mTechnicalUsDialog.findViewById(R.id.imgVw_Email);

        imgVw_Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomDialogClass cdd = new CustomDialogClass(ctx, "Send mail");
                cdd.show();
            }
        });

        imgVw_technicalcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mPhoneNo));
                ctx.startActivity(callIntent);

            }
        });
        mTechnicalUsDialog.show();
    }


    public static void showContactUsDialog(final Activity ctx) {
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
        System.out.println("IS_INTERNETAVAILABLE_DIALOG::"+IS_INTERNETAVAILABLE_DIALOG);
//        if (IS_INTERNETAVAILABLE_DIALOG == true) {
//            txtVw_webLink.setTextColor(Color.BLUE);
//            txtVw_webLink.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intfood = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(R.string.link_text_auto)));
//                    ctx.startActivity(intfood);
//                }
//            });
//        } else {
//            txtVw_webLink.setClickable(false);
//            txtVw_webLink.setTextColor(Color.BLACK);
//        }


        imgVw_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  String   mPhoneNo = txtVw_phone.getText().toString();

                //  String phoneno = 04222971111;
//                Intent in=new Intent(Intent.ACTION_CALL,Uri.parse(mPhoneNo));
//                ctx.startActivity(in);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mPhoneNo));
                ctx.startActivity(callIntent);

            }
        });

        mContactUsDialog.show();
    }
}
