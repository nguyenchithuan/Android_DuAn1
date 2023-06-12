package edu.poly.duan1_fpoly.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.models.Item;
import edu.poly.duan1_fpoly.models.SanPham;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ChiTietDonHangViewHolder> {
    private Context context;
    private ArrayList<Item> list;

    public ChiTietDonHangAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChiTietDonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_chi_tiet_don_hang, parent, false);
        return new ChiTietDonHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietDonHangViewHolder holder, int position) {
        Item item = list.get(position);
        if(item == null) {
            return;
        }

        holder.tvName.setText(item.getTensp() + "");
        DecimalFormat decimal = new DecimalFormat("###,###,###");
        holder.tvSoLuong.setText(decimal.format(item.getGiaSp()) + "đ" + " x " + item.getSoLuong());

        Glide.with(context).load(item.getHinhanh()).into(holder.imgSp);

    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class ChiTietDonHangViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSp;
        private TextView tvName, tvSoLuong;


        public ChiTietDonHangViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSp = itemView.findViewById(R.id.img_sp);
            tvName = itemView.findViewById(R.id.tv_name_sp);
            tvSoLuong = itemView.findViewById(R.id.tv_soluong_sp);
        }
    }
}
