package org.next.equmed.uil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.next.equmed.R;
import org.next.equmed.bal.BusinessAccessLayer;

/**
 * Created by next on 16/10/15.
 */
public class CustomAlertDialog extends Dialog implements View.OnClickListener {

    private Context ctx;
    private String message = "";
    int i = 0;
    Button yes, no;

    private TextView txt_dia;
    String btnType = "";

    public CustomAlertDialog( Context a, String message, int val ) {
        super( a );
        // TODO Auto-generated constructor stub
        this.ctx = a;
        this.message = message;
        this.i = val;
    }

  /*  public CustomAlertDialog(Context context) {
        super(context);
        this.c = context;
    }*/

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setBackgroundDrawable( new ColorDrawable( android.graphics.Color.TRANSPARENT ) );
        setContentView( R.layout.dialog_view );

        yes = ( Button ) findViewById( R.id.btn_yes );
        txt_dia = ( TextView ) findViewById( R.id.txt_dia );
        txt_dia.setText( message );
        no = ( Button ) findViewById( R.id.btn_no );

        if ( i == 1 ) {
            btnType = "OK";
            no.setVisibility( View.VISIBLE );
            no.setText( "OK" );
            yes.setVisibility( View.GONE );
        } else if ( i == 3 ) {
            btnType = "Settings";
            no.setVisibility( View.VISIBLE );
            no.setText( "Cancel" );
            //    alertDialog.setTitle("GPS is settings");
        } else if ( i == 2 ) {
            btnType = "Cancel";
            no.setVisibility( View.VISIBLE );
            no.setText( "OK" );
//            alertDialog.setNegativeButton("Cancel",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
        } else if ( i == 4 ) {
            btnType = "Yes";
            no.setVisibility( View.VISIBLE );
            no.setText( "No" );
//            alertDialog.setNegativeButton("No",
//                    new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    });
        }
        yes.setText( btnType );
        yes.setOnClickListener( this );
        no.setOnClickListener( this );
    }

    @Override
    public void onClick( View v ) {
        switch ( v.getId() ) {
            case R.id.btn_yes:

                /*if (message.equalsIgnoreCase("You have been logged-in succesfully")) {
                    Intent intent = new Intent(ctx, HomeActivity.class);
                    ctx.startActivity(intent);
                }*/


                if ( "GPS is not enabled. Do you want to go to settings menu?".equalsIgnoreCase( message ) ) {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                    ctx.startActivity( intent );
                } else if ( message.equalsIgnoreCase( BusinessAccessLayer.LOGOUT_MESSAGE ) ) {
//                    BusinessAccessLayer.mParetnRoleId = "";
                    Intent intent = new Intent( ctx, LoginActivity.class );
                    ctx.startActivity( intent );
//                    finish();
//                    ctx.finish();


                } else if ( message.equalsIgnoreCase( BusinessAccessLayer.EXIT_MESSAGE ) ) {
                    BusinessAccessLayer.mParetnRoleId = "";
                    //finish();
//                    ctx.finish();
//                    System.exit( 0 );


                }
//                else
//                {
//
//                }

//                if ( message. )

                dismiss();
                break;

            case R.id.btn_no:
                if ( message.equalsIgnoreCase( BusinessAccessLayer.LOGIN_MESSAGE ) ) {
                    Intent intent = new Intent( ctx, DashBoardActivity.class );
                    ctx.startActivity( intent );
                }

              /*  if ("Registered successfully".equalsIgnoreCase(message) ||"Password Updated Succesfully".equalsIgnoreCase(message)) {
                    Intent NavigateToLogin = new Intent(ctx,
                            Login.class);
                    ctx.startActivity(NavigateToLogin);
                }*/
                dismiss();
                break;
            default:
                break;
        }
    }
}