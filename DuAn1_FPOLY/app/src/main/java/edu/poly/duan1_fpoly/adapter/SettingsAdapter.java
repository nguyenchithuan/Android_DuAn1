package edu.poly.duan1_fpoly.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.activity.package_settings.ChangePassActivity;
import edu.poly.duan1_fpoly.activity.package_settings.GroupActivity;
import edu.poly.duan1_fpoly.activity.package_settings.InfoActivity;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {
    private Context context;
    private ArrayList<Integer> listImg; // list chưa icon ảnh
    private ArrayList<String> listName; // list chưa name

    public SettingsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_setting, parent, false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        setData();
        int index = position;

        // gắn dữ liệu
        holder.imgSetting.setImageResource(listImg.get(position));
        holder.tvSetting.setText(listName.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0) {
                    Intent intent = new Intent(context, InfoActivity.class);
                    context.startActivity(intent);
                } else if(index == 1) {
                    Intent intent = new Intent(context, GroupActivity.class);
                    context.startActivity(intent);
                } else if(index == 2) {
                    Intent intent = new Intent(context, ChangePassActivity.class);
                    context.startActivity(intent);
                }
            }
        });
    }

    private void setData() {
        // list ảnh
        listImg.add(R.drawable.ic_account);
        listImg.add(R.drawable.ic_groups);
        listImg.add(R.drawable.ic_key);

        // list name
        listName.add("Thông tin");
        listName.add("Nhóm");
        listName.add("Đổi mật khẩu");
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class SettingsViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgSetting;
        private TextView tvSetting;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSetting = itemView.findViewById(R.id.img_settings);
            tvSetting = itemView.findViewById(R.id.tv_settings);

            listImg = new ArrayList<>();
            listName = new ArrayList<>();
        }
    }
}
