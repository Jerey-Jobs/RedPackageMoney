package applock.anderson.com.register;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button1);
        textView = (TextView) findViewById(R.id.result);
        editText = (EditText) findViewById(R.id.edit_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText()) ){
                    Toast.makeText(MainActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editText.getText().toString().length() >= 8) {
                    Toast.makeText(MainActivity.this,"输入过长，最长8位",Toast.LENGTH_SHORT).show();
                    return;
                }
                int textid = Integer.parseInt(editText.getText().toString());
                Log.i("iii", "textid" + (((textid - 3) * 3 / 2 - 9) * 2));
                int result = (((textid - 3) * 3 / 2 - 9) * 2);
                textView.setText("授权码为：" + result);

                ClipboardManager copy = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData myClip;
                String text = "" + result;
                myClip = ClipData.newPlainText("text", text);
                copy.setPrimaryClip(myClip);
                Toast.makeText(MainActivity.this, "已经复制到剪切板", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
