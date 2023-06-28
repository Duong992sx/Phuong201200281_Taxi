package com.example.phuong201200281_taxi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
 private ArrayList<Taxi> ContacList;
    private EditText etSreach;
    private  Adapter ListAdapter;
    private ListView lstContact;
    FloatingActionButton btthem;
    int selectedid = -1;
    private Sqlite_MaDe db;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.idup:
                Intent intent = new Intent(MainActivity.this,
                        Add.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", ContacList.get(selectedid).getId());
                bundle.putString("Ten", ContacList.get(selectedid).getSoXe());
                bundle.putDouble("Phong", ContacList.get(selectedid).getQuangDuong());
                bundle.putDouble("Dongia", ContacList.get(selectedid).getDonGia());
                bundle.putDouble("Songay", ContacList.get(selectedid).getKhuyenMai());
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.iddel:
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Phuong wants to delete?");
                dlgAlert.setTitle("Confirm");
                dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
//                                ContacList.remove(selectedid);
                        db.deleteContact(ContacList.get(selectedid).getId());
                        resetData();
                    }
                });
                dlgAlert.setNegativeButton("Cancel",null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String ten = bundle.getString("Ten");
        double phong = bundle.getDouble("Phong");
        double gia = bundle.getDouble("Dongia");
        double ngay = bundle.getDouble("Songay");
        if(requestCode==100 && resultCode==200 )
        {
            //đặt vào listData
            db.addContact(new Taxi(ten,phong,gia,ngay));
            //ContacList.add(new Contact(id,"img1",name, phone, "p1","s1"));

        }
        else if(requestCode==200 && resultCode==201)
            db.UpdateContact(id,new Taxi(id,ten,phong,gia,ngay));
        //ContacList.set(selectedid,new Contact(id,"img1",name, phone, "p1","s1"));
        //cập nhật adapter
        ListAdapter.notifyDataSetChanged();
        resetData();
    }
    private void resetData(){
        db = new Sqlite_MaDe(MainActivity.this, "ContactDBb1",null,1);
        ContacList  = db.GetAllContact();
        ListAdapter = new Adapter(ContacList, MainActivity.this);
        lstContact.setAdapter(ListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContacList = new ArrayList<Taxi>();
        db = new Sqlite_MaDe(this, "ContactDBb1",null,1);
//        db.addContact(new HoaDon_08("Lê Thị Phương",810,2600000,7));
//        db.addContact(new HoaDon_08("Trịnh Tô Uyên ",811,2500000,4));
  //     db.addContact(new HoaDon_08("Lưu Bảo Long ",910,2400000,3));
////        db.addContact(new HoaDon_08("Trần Mai Anh ",610,2300000,8));
////        db.addContact(new HoaDon_08("Nguyễn Bích Ngọc ",710,2200000,9));
//        db.addContact(new Taxi("29D2-283.34",14.3,20000,5));
//        db.addContact(new Taxi("30K2-129.84",15.6,20000,8));
//        db.addContact(new Taxi("29T2-298.56",19,20000,7));

        ContacList = db.GetAllContact();
        lstContact = findViewById(R.id.listview);
        ListAdapter = new Adapter(ContacList, this);
        etSreach = findViewById(R.id.etSearch);
        btthem = findViewById(R.id.btnAdd);



           Collections.sort(ContacList, new Comparator<Taxi>() {
            @Override
            public int compare(Taxi o1, Taxi o2) {
                return (int) (o2.getQuangDuong()-o1.getQuangDuong());
            }
        });

        lstContact.setAdapter(ListAdapter);
        btthem.setOnClickListener(v -> {
            Intent intent = new Intent(this, Add.class);
            startActivityForResult(intent,100);

        });
        registerForContextMenu(lstContact);
       lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        selectedid = position;
        int dem = 0;
        double selectedPrice = (ContacList.get(selectedid).getDonGia()*ContacList.get(selectedid).getQuangDuong()*(100-ContacList.get(selectedid).getKhuyenMai()))/100;

        for (Taxi nhahang : ContacList) {
            double price = (nhahang.getDonGia()*nhahang.getQuangDuong()*(100-nhahang.getKhuyenMai()))/100;
            if (price < selectedPrice) {
                dem++;
            }
        }

        String result = "Có " + dem + " hóa đơn  có tổng tiền nhỏ hơn  phần tử được chọn";
        Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();

        return false;
    }
});

        etSreach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                ListAdapter.getFilter().filter(s.toString());
                ListAdapter.notifyDataSetChanged();
                lstContact.setAdapter(ListAdapter);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}