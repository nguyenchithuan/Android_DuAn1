package edu.poly.duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import edu.poly.duan1.R;
import edu.poly.duan1.model.LoaiSp;

public class LoaiSpAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LoaiSp> list;

    public LoaiSpAdapter(Context context, ArrayList<LoaiSp> list) {
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

    private class ViewHolder {
        private ImageView imgLoaiSp;
        private TextView tvLoaiSp;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.layout_listview_loai_sp, parent, false);
            viewHolder.imgLoaiSp = view.findViewById(R.id.imgLoaiSp);
            viewHolder.tvLoaiSp = view.findViewById(R.id.tvLoaiSp);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        LoaiSp loaiSp = list.get(position);
        viewHolder.tvLoaiSp.setText(loaiSp.getTenLoaiSp());
        Glide.with(context).load(loaiSp.getHinhLoaiSp()).into(viewHolder.imgLoaiSp);

        return view;
    }
}
