package com.example.phuong201200281_taxi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class Adapter extends BaseAdapter {
    private ArrayList<Taxi> data;
    private Activity context;
    private LayoutInflater inflater;
    private ArrayList<Taxi> databackup;

    public Adapter(){}
     public Adapter(ArrayList<Taxi> data, Activity activity) {
        this.data = data;
        this.context = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
       return data.size();
    }

    @Override
    public Object getItem(int position) {
       return data.get(position);
    }

    @Override
    public long getItemId(int position) {
         return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = inflater.inflate(R.layout.itemlistview, null);
            // Gán giá trị cho TextView
            TextView ten = v.findViewById(R.id.tvTen);
            TextView phong = v.findViewById(R.id.tvPhong);
            TextView gia = v.findViewById(R.id.tvGia);
            ten.setText(data.get(position).getSoXe());
            phong.setText(String.valueOf(data.get(position).getQuangDuong())+"km");

            double gia1 = -(data.get(position).getDonGia() * data.get(position).getQuangDuong() * (100 - data.get(position).getKhuyenMai())) / 100;

            // Định dạng giá trị gia1 thành số tiền
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            decimalFormat.setCurrency(Currency.getInstance("VND"));
            String giaFormatted = decimalFormat.format(gia1);

            // Hiển thị giá trị đã định dạng lên TextView
            gia.setText(giaFormatted);
        }
        return v;
    }
    //tìm kiếm 
      public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                //Backup dữ liệu: lưu tạm data vào databackup
                if(databackup==null)
                    databackup = new ArrayList<>(data);
                //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                if(charSequence==null || charSequence.length()==0)
                {
                    fr.count = databackup.size();
                    fr.values = databackup;
                }
                //Còn nếu không rỗng thì thực hiện filter
                else{

                    ArrayList<Taxi> newdata = new ArrayList<>();
                    for(Taxi u:databackup)
                        if(-(u.getDonGia() * u.getQuangDuong()*(100-u.getKhuyenMai())/100)<
                                Integer.parseInt(charSequence.toString()))
                            newdata.add(u);
                    fr.count=newdata.size();
                    fr.values=newdata;

                }
                return fr;
            }
            @Override
            protected void publishResults(CharSequence charSequence,
                                          FilterResults filterResults) {
                data = new ArrayList<Taxi>();
                ArrayList<Taxi> tmp =(ArrayList<Taxi>)filterResults.values;
                for(Taxi u: tmp)
                    data.add(u);
                notifyDataSetChanged();
            }
        };
        return f;
    }
}

