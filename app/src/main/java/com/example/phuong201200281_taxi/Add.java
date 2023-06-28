package com.example.phuong201200281_taxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends AppCompatActivity {

  EditText editText_name, editText_sophong, editText_dongia, editText_songayluutru;
    Button button_add,btn_back;
     private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        button_add=findViewById(R.id.btnthem);
        btn_back=findViewById(R.id.btnquayve);
        editText_name =findViewById(R.id.editten);
        editText_sophong=findViewById(R.id.editphong);
        editText_dongia=findViewById(R.id.editgia);
        editText_songayluutru=findViewById(R.id.editngay);

         Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
           id = bundle.getInt("Id");
            String ten = bundle.getString("Ten");
            double phong = bundle.getDouble("Phong");
            double gia = bundle.getDouble("Dongia");
            double ngay = bundle.getDouble("Songay");

           // etId.setText(String.valueOf(id));
            editText_name.setText(ten);
            editText_sophong.setText(String.valueOf(phong));
             editText_dongia.setText(String.valueOf(ngay));
             editText_songayluutru.setText(String.valueOf(gia));

            button_add.setText("Edit");
        }
        btn_back.setOnClickListener(view -> {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivityForResult(intent1,0);
        });
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){

                    //Tạo intent để trở về MainActivity
                    Intent intent = new Intent();
                    //Tạo bundle là đối tượng để chứa dữ liệu
                    Bundle bundle = new Bundle();
                    //bundle hoạt động như một Java Map các phần tử phân biệt theo key
                    //bundle có các hàm put... trong đó ... là kiểu dữ liệu tương ứng
                   bundle.putInt("Id",id);
                    bundle.putString("Ten", editText_name.getText().toString());
                    bundle.putDouble("Phong", Double.parseDouble(editText_sophong.getText().toString()));
                    bundle.putDouble("Dongia", Double.parseDouble(editText_dongia.getText().toString()));
                    bundle.putDouble("Songay", Double.parseDouble(editText_songayluutru.getText().toString()));

                    //có thể đặt cả đối tượng lên bundle bằng hàm putSerilizable
                    //đặt bundle lên intent

                    intent.putExtras(bundle);


                    //trả về bằng hàm setResult
                    //tham số thứ nhất là resultCode để quản lý phiên
                    //tham số thứ hai  là intent chứa dữ liệu gửi về
                    setResult(200, intent);
                    if(button_add.getText()=="Edit")
                        setResult(201, intent);
                    //Kết thúc: đóng activity hiện thời.
                    finish();
                }

            }
        });
    }
    boolean check(){
        if(editText_name.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập ten!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(editText_sophong.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập phong!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(editText_dongia.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập gia!", Toast.LENGTH_SHORT).show();
            return false;
        }
          if(editText_songayluutru.getText().toString().isEmpty()){
            Toast.makeText(this, "Vui lòng nhập so ngay !", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}