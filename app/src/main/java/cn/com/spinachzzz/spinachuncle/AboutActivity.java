package cn.com.spinachzzz.spinachuncle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.about);

	TextView t = (TextView) this.findViewById(R.id.about_text);

	String msg = "Welcome to use me!\n\nThis application aims to help you improve your camera skills by providing you tons of amazing pictures from internet.\n\nThe author is pool but this application will never go comercial, no worry about ADs.\n\nIf any issue please don't contact author because he is busy and his skills is still under developing.\n\nThank you again for using this application!\n\n\n---J";
	t.setText(msg);
    }

}
