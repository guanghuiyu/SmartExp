package com.fangwolf.smartexp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class GenerateQRActivity extends AppCompatActivity {

    EditText Sname;
    EditText Sphone;
    EditText SDadress;
    EditText Rname;
    EditText Rphone;
    EditText RDadress;
    Button confirm;
    Button cancel;
    ImageView QR;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        Sname = (EditText) findViewById(R.id.Sname);
        Sphone = (EditText) findViewById(R.id.Sphone);
        SDadress = (EditText) findViewById(R.id.SDadress);
        Rname = (EditText) findViewById(R.id.Rname);
        Rphone = (EditText) findViewById(R.id.Rphone);
        RDadress = (EditText) findViewById(R.id.RDadress);

        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        QR = (ImageView) findViewById(R.id.QR);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //输入框不能为空
                    if (TextUtils.isEmpty(Sname.getText()) /*|| TextUtils.isEmpty(Sphone.getText()) || TextUtils.isEmpty(SDadress.getText()) ||
                            TextUtils.isEmpty(Rname.getText()) || TextUtils.isEmpty(Rphone.getText()) || TextUtils.isEmpty(RDadress.getText())*/) {
                        Toast.makeText(GenerateQRActivity.this,"null",Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("GenerateQRActivity","name="+Sname.getText().toString()+" phone="+Sphone.getText().toString()+" adress="+SDadress.getText().toString());
                        createCode(v);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //优化内存
        bitmap.recycle();
        bitmap=null;
    }

    //输入的内容生成二维码
    public void createCode(View view) throws UnsupportedEncodingException {

        BitMatrix matrix;
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(Sname.getText()).append(Sphone.getText()).append(SDadress.getText())
                .append(Rname.getText()).append(Rphone.getText()).append(RDadress.getText());
        try {
            matrix = new MultiFormatWriter().encode(new String(stringBuilder.toString().getBytes("UTF-8"),
                            "ISO-8859-1"),BarcodeFormat.QR_CODE, 1000, 1000);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
            QR.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
