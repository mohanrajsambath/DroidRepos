package org.next.equmed.bal;

import org.next.equmed.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomProgressDialog extends Dialog {

    CustomProgressDialog dialog;

    public CustomProgressDialog( Context context ) {
        super( context );
    }

    public CustomProgressDialog( Context context, int theme ) {
        super( context, theme );
    }

    public void onWindowFocusChanged( boolean hasFocus ) {
        ImageView imageView = ( ImageView ) findViewById( R.id.spinnerImageView );
        AnimationDrawable spinner = ( AnimationDrawable ) imageView.getBackground();
        spinner.start();
    }

    public static CustomProgressDialog show( Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable,
                                             OnCancelListener cancelListener ) {
        CustomProgressDialog dialog = new CustomProgressDialog( context, R.style.ProgressHUD );
        //dialog.setTitle("");
        dialog.setContentView( R.layout.custom_progress_dialog );

        if ( title == null || title.length() == 0 ) {
            dialog.findViewById( R.id.title ).setVisibility( View.GONE );
        } else {
            TextView txt = ( TextView ) dialog.findViewById( R.id.title );
            txt.setText( title );
            txt.setTextColor( Color.parseColor( "#35ADEE" ) );
        }
        if ( message == null || message.length() == 0 ) {

            dialog.findViewById( R.id.layout_message ).setVisibility( View.GONE );
            dialog.findViewById( R.id.divline ).setVisibility( View.GONE );
            dialog.findViewById( R.id.message ).setVisibility( View.GONE );

        } else {
            dialog.findViewById( R.id.layout_message ).setVisibility( View.VISIBLE );
            dialog.findViewById( R.id.divline ).setVisibility( View.VISIBLE );
            TextView txt = ( TextView ) dialog.findViewById( R.id.message );
            txt.setText( message );
        }
        dialog.setCancelable( cancelable );
        dialog.setOnCancelListener( cancelListener );
        /*dialog.getWindow().getAttributes().gravity = Gravity.LEFT | Gravity.TOP;*/
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
		/*dialog.getWindow().getAttributes().verticalMargin = 0.1F;
		dialog.getWindow().getAttributes().horizontalMargin = -0.1F;*/
        dialog.getWindow().setAttributes( lp );
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        dialog.show();
        return dialog;
    }
}
