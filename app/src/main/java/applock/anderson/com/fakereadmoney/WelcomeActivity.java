package applock.anderson.com.fakereadmoney;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mEnsure;
    TextView mCancle;
    TextView mJiqiCode;
    TextView mDianjifuzhi;
    EditText mEdit;

    String mDeviceId;
    int mIdcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mEnsure = (TextView) findViewById(R.id.ensure_button);
        mCancle = (TextView) findViewById(R.id.cancel_Button);
        mJiqiCode = (TextView) findViewById(R.id.jiqi_code);
        mDianjifuzhi = (TextView) findViewById(R.id.textView3);
        mEdit = (EditText) findViewById(R.id.author_code);
        mEnsure.setClickable(true);
        mJiqiCode.setClickable(true);
        mEnsure.setOnClickListener(this);
        mCancle.setOnClickListener(this);
        mDianjifuzhi.setOnClickListener(this);
        init();
        if (check()) {
            startActivity(new Intent(this,SettingsActivity.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ensure_button:
                if (check()) {
                    startActivity(new Intent(this,SettingsActivity.class));
                    Toast.makeText(WelcomeActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(WelcomeActivity.this, "验证不通过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel_Button:
                finish();
                break;
            case R.id.textView3:
                ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                String text = "" + mIdcode;
                myClip = ClipData.newPlainText("text", text);
                copy.setPrimaryClip(myClip);
                Toast.makeText(WelcomeActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private boolean check() {
        SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("hascode", false)) {
            return true;
        }
        if(TextUtils.isEmpty(mEdit.getText()) ){
            return false;
        }
        int textid = Integer.parseInt(mEdit.getText().toString());
        Log.i("iii", "textid" + (((mIdcode - 3) * 3 / 2 - 9) * 2));
        if (textid == (((mIdcode - 3) * 3 / 2 - 9) * 2)) {
            Log.i("iii", "textid" + textid + " "+ (((mIdcode - 3) * 3 / 2 - 9) * 2));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("hascode", true);
            editor.commit();
            return true;
        }
        return false;
    }

    private void init() {
        mDeviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //mDeviceId = "" + tm.getDeviceId();
        mIdcode = Math.abs(mDeviceId.hashCode() % 1000000);
        mJiqiCode.setText("" + mIdcode);
        Log.i("iii", "mDeviceId" + mDeviceId);
        Log.i("iii", "mDeviceId hash " + mDeviceId.hashCode());
        Log.i("iii", "mIdcode" + Math.abs(mDeviceId.hashCode() % 1000000));
    }


}

