package edu.poly.duan1_fpoly.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.models.DonHang;
import edu.poly.duan1_fpoly.models.SanPham;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder> {
    private Context context;
    private ArrayList<DonHang> list;
    private ChiTietDonHangAdapter chiTietDonHangAdapter;

    public DonHangAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<DonHang> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_don_hang, parent, false);
        return new DonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        DonHang donHang = list.get(position);

        if(donHang == null) {
            return;
        }

        holder.tvIdDonHang.setText("Đơn hàng: " + donHang.getId());
        holder.tvSoLuong.setText(donHang.getSoluong() + " loại sản phẩm");
        DecimalFormat decimal = new DecimalFormat("###,###,###");
        holder.tvTongTien.setText(decimal.format(donHang.getTongTien()) + "đ");

        chiTietDonHangAdapter.setData(donHang.getList());
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class DonHangViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdDonHang;
        private TextView tvSoLuong;
        private TextView tvTongTien;
        private RecyclerView rcvChiTietDonHang;

        public DonHangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdDonHang = itemView.findViewById(R.id.tv_id_don_hang);
            tvSoLuong = itemView.findViewById(R.id.tv_sl_loai_sp);
            tvTongTien = itemView.findViewById(R.id.tv_tong_tien);
            rcvChiTietDonHang = itemView.findViewById(R.id.rcv_chi_tiet_gio_hang);


            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rcvChiTietDonHang.setLayoutManager(linearLayoutManager);
            chiTietDonHangAdapter = new ChiTietDonHangAdapter(context);
            rcvChiTietDonHang.setAdapter(chiTietDonHangAdapter);
        }
    }
}
