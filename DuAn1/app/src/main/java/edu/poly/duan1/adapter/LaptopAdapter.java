package edu.poly.duan1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1.R;
import edu.poly.duan1.model.SanPham;

public class LaptopAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> list;

    public LaptopAdapter(Context context, ArrayList<SanPham> list) {
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
        private TextView tvTenLaptop, tvGiaLaptop, tvMoTaLaptop;
        private ImageView imgLaptop;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_san_pham, parent, false);
            viewHolder.tvTenLaptop = view.findViewById(R.id.tvTenSanPham);
            viewHolder.tvGiaLaptop = view.findViewById(R.id.tvGiaSanPham);
            viewHolder.tvMoTaLaptop = view.findViewById(R.id.tvMoTaSanPham);
            viewHolder.imgLaptop = view.findViewById(R.id.imgSanPham);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = list.get(position);

        viewHolder.tvTenLaptop.setText(sanPham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGiaLaptop.setText("Giá: " + decimalFormat.format(sanPham.getGiaSanPham()) + " VNĐ");
        viewHolder.tvMoTaLaptop.setMaxLines(2); // hiển thị 2 dòng
        viewHolder.tvMoTaLaptop.setEllipsize(TextUtils.TruncateAt.END); // dài quá hiển thị dấu ...
        viewHolder.tvMoTaLaptop.setText(sanPham.getMoTaSanPham());
        Glide.with(context).load(sanPham.getHinhAnhSanPham())
                .into(viewHolder.imgLaptop);

        return view;
    }
}
