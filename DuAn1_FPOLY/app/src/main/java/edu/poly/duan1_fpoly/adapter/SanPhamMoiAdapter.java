package edu.poly.duan1_fpoly.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import edu.poly.duan1_fpoly.activity.ProductDetailActivity;
import edu.poly.duan1_fpoly.models.SanPham;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.SanPhamMoiViewHolder> {
    private Context context;
    private ArrayList<SanPham> list;

    public SanPhamMoiAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<SanPham> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SanPhamMoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_san_pham_moi, parent, false);
        return new SanPhamMoiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamMoiViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        if(sanPham == null) {
            return;
        }

        holder.tvTenSp.setText(sanPham.getTenSp());
        holder.tvTenSp.setMaxLines(1);
        holder.tvTenSp.setEllipsize(TextUtils.TruncateAt.END);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvGiaSp.setText(decimalFormat.format(sanPham.getGiaSp()) + " đ");

        Glide.with(context).load(sanPham.getHinhAnhSp()).into(holder.imgSp);

        setDataDaBan(sanPham, holder);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sanpham", sanPham);
                intent.putExtras(bundle);
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

    public class SanPhamMoiViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSp;
        private TextView tvTenSp;
        private TextView tvGiaSp;
        private TextView tvDaBan;

        public SanPhamMoiViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSp = itemView.findViewById(R.id.img_sp);
            tvTenSp = itemView.findViewById(R.id.tv_name_sp);
            tvGiaSp = itemView.findViewById(R.id.tv_gia_sp);
            tvDaBan = itemView.findViewById(R.id.tv_da_ban);
        }
    }

    private void setDataDaBan(SanPham sanPham, SanPhamMoiViewHolder holder) {
        if(sanPham.getGiaSp() < 3000000) {
            holder.tvDaBan.setText("Đã bán 57,1k");
        } else if(sanPham.getGiaSp() < 10000000) {
            holder.tvDaBan.setText("Đã bán 20,5k");
        } else if(sanPham.getGiaSp() < 20000000) {
            holder.tvDaBan.setText("Đã bán 32,3k");
        } else {
            holder.tvDaBan.setText("Đã bán 1,2k");
        }
    }
}
