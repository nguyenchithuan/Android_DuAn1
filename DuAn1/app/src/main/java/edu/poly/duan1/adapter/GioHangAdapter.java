package edu.poly.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1.R;
import edu.poly.duan1.activity.GioHangActivity;
import edu.poly.duan1.activity.MainActivity;
import edu.poly.duan1.model.GioHang;

public class GioHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GioHang> list;

    public GioHangAdapter(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView tvTenGioHang, tvGiaGioHang, tvValues;
        public ImageView imgGioHang;
        public Button btnMinus, btnPlus;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_gio_hang, parent, false);
            viewHolder.tvTenGioHang = view.findViewById(R.id.tvTenGioHang);
            viewHolder.tvGiaGioHang = view.findViewById(R.id.tvGiaGioHang);
            viewHolder.tvValues = view.findViewById(R.id.tvValues);
            viewHolder.imgGioHang = view.findViewById(R.id.imgGioHang);
            viewHolder.btnMinus = view.findViewById(R.id.btnMinus);
            viewHolder.btnPlus = view.findViewById(R.id.btnPlus);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        GioHang gioHang = list.get(position);
        viewHolder.tvTenGioHang.setText(gioHang.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaGioHang.setText(decimalFormat.format(gioHang.getGiaSp()) + "VNĐ");
        Glide.with(context).load(gioHang.getHinhSp()).into(viewHolder.imgGioHang);
        viewHolder.tvValues.setText(gioHang.getSoLuong() + "");

        int soLuong = Integer.parseInt(viewHolder.tvValues.getText().toString());
        if(soLuong >= 10) {
            viewHolder.btnPlus.setVisibility(View.INVISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        } else if(soLuong <= 1) {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.btnPlus.setVisibility(View.VISIBLE);
            viewHolder.btnMinus.setVisibility(View.VISIBLE);
        }

        ViewHolder finalViewHolder = viewHolder;

        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.tvValues.getText().toString()) + 1;
                int slHienTai = MainActivity.listGioHang.get(position).getSoLuong();
                long giaHienTai = MainActivity.listGioHang.get(position).getGiaSp();
                long giaMoiNhat = (giaHienTai / slHienTai) * slMoiNhat;
                MainActivity.listGioHang.get(position).setSoLuong(slMoiNhat);
                MainActivity.listGioHang.get(position).setGiaSp(giaMoiNhat);

                DecimalFormat format = new DecimalFormat("###,###,###");
                finalViewHolder.tvGiaGioHang.setText(format.format(giaMoiNhat) + " VNĐ");
                finalViewHolder.tvValues.setText(String.valueOf(slMoiNhat));

                // sét lại tổng tiền
                GioHangActivity.EvenUltil();

                if(slMoiNhat > 9) {
                    finalViewHolder.btnPlus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slMoiNhat = Integer.parseInt(finalViewHolder.tvValues.getText().toString()) - 1;
                int slHienTai = MainActivity.listGioHang.get(position).getSoLuong();
                long giaHienTai = MainActivity.listGioHang.get(position).getGiaSp();
                long giaMoiNhat = (giaHienTai / slHienTai) * slMoiNhat;
                MainActivity.listGioHang.get(position).setSoLuong(slMoiNhat);
                MainActivity.listGioHang.get(position).setGiaSp(giaMoiNhat);

                DecimalFormat format = new DecimalFormat("###,###,###");
                finalViewHolder.tvGiaGioHang.setText(format.format(giaMoiNhat) + " VNĐ");
                finalViewHolder.tvValues.setText(String.valueOf(slMoiNhat));

                // sét lại tổng tiền
                GioHangActivity.EvenUltil();

                if(slMoiNhat < 2) {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.INVISIBLE);
                } else {
                    finalViewHolder.btnPlus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnMinus.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }
}
