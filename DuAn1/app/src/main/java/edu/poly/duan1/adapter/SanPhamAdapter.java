package edu.poly.duan1.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1.R;
import edu.poly.duan1.activity.ChiTietSanPhamActivity;
import edu.poly.duan1.model.SanPham;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHolder> {
    private Context context;
    private ArrayList<SanPham> list;

    public SanPhamAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<SanPham> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_san_pham_moi_nhat, parent, false);
        return new SanPhamViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        if(sanPham == null) {
            return;
        }
        holder.tvTenSp.setText(sanPham.getTenSanPham());
        // định dạng số
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaSp.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " VNĐ");
        // hiển thị ảnh lấy dữ liệu từ internet
        Glide.with(context).load(sanPham.getHinhAnhSanPham()).into(holder.imgAnhSp);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", list.get(position));
                Toast.makeText(context, list.get(position).getTenSanPham(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgAnhSp;
        private TextView tvTenSp, tvGiaSp;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhSp = itemView.findViewById(R.id.imgAnhSp);
            tvTenSp = itemView.findViewById(R.id.tvTenSp);
            tvGiaSp = itemView.findViewById(R.id.tvGiaSp);
        }
    }
}
