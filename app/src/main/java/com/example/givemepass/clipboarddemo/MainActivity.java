package com.example.givemepass.clipboarddemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button copyBtn;
    private EditText editText;
    private Button pasteBtn;
    private Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        copyBtn = (Button) findViewById(R.id.copy_btn);
        pasteBtn = (Button) findViewById(R.id.paste_btn);
        clearBtn = (Button) findViewById(R.id.clear_btn);
        copyBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                copyToClipboard(editText.getText().toString());
                Toast.makeText(MainActivity.this, "複製到剪貼簿", Toast.LENGTH_SHORT).show();
            }

        });
        pasteBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editText.setText(editText.getText().append(copyFromClipboard()));
            }

        });
        clearBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editText.setText("");
            }

        });
    }
    private void copyToClipboard(String str){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(str);
            Log.e("version","1 version");
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("text label",str);
            clipboard.setPrimaryClip(clip);
            Log.e("version","2 version");
        }
    }

    private String copyFromClipboard(){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < clipboard.getPrimaryClip().getItemCount(); i++){
                sb.append(clipboard.getPrimaryClip().getItemAt(i).getText());
            }
            return sb.toString();
        } else {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            return clipboard.getText().toString();
        }

    }
}
