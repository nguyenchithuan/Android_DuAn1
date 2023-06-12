package edu.poly.duan1_fpoly.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.dao.GioHangDao;
import edu.poly.duan1_fpoly.models.GioHang;
import edu.poly.duan1_fpoly.models.SanPham;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.UtilsInterface;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHolder> {
    private Context context;
    private ArrayList<GioHang> list;
    private UtilsInterface utilsInterface;

    public GioHangAdapter(Context context, UtilsInterface utilsInterface) {
        this.context = context;
        this.utilsInterface = utilsInterface;
    }

    public void setData(ArrayList<GioHang> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GioHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_gio_hang, parent, false);
        return new GioHangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangViewHolder holder, int position) {
        int index = position;

        GioHang gioHang = list.get(position);
        if(gioHang == null) {
            return;
        }
        holder.tvName.setText(gioHang.getTenSp());
        holder.tvName.setMaxLines(1);

        DecimalFormat decimal = new DecimalFormat("###,###,###");
        holder.tvGia.setText(decimal.format(gioHang.getGia()) + "đ");

        Glide.with(context).load(gioHang.getHinhSp()).into(holder.imgSp);

        holder.tvSoLuong.setText(gioHang.getSoLuong() + "");

        // hiển thị btnMinus và btnPlus
        showBtnMinusAndPlus(gioHang.getSoLuong(), holder);

        // định dùng interface mà làm nó khó hơn
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                if(soLuong < 10) {
                    soLuong += 1;
                    GioHangDao.listGioHang.get(index).setSoLuong(soLuong); // gắn lại giá trị vào lv
                    long giaMoi = gioHang.getGia() / (soLuong - 1) * soLuong; // tính giá mới, trừ 1 là do vì vừa tăng thêm 1
                    GioHangDao.listGioHang.get(index).setGia(giaMoi);

                    utilsInterface.setTongGia(); // để ra lớp gọi nó thì phải thực hiện lại tính tổng

                    notifyDataSetChanged(); // chạy lại adapter
                }
            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuong = gioHang.getSoLuong();
                if(soLuong > 1) {
                    soLuong -= 1;
                    GioHangDao.listGioHang.get(index).setSoLuong(soLuong); // gắn lại giá trị vào lv
                    long giaMoi = gioHang.getGia() / (soLuong + 1) * soLuong; // tính giá mới, công 1 là vì vừa giảm đi 1
                    GioHangDao.listGioHang.get(index).setGia(giaMoi);

                    utilsInterface.setTongGia(); // để ra lớp gọi nó thì phải thực hiện lại tính tổng

                    notifyDataSetChanged(); // chạy lại adapter
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn xóa không!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GioHangDao.listGioHang.remove(index);
                        utilsInterface.setTongGia();
                        notifyDataSetChanged();
                        CustomToast.makeText((Activity) context, "Xóa thành công!");
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });

        holder.chkMuaHang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    GioHangDao.listMuaHang.add(gioHang); // nếu check thì thêm vào giỏ hàng
                    utilsInterface.setTongGia();
                } else {
//                    for (int i = 0; i < GioHangDao.listMuaHang.size(); i++) {
//                        if(GioHangDao.listMuaHang.get(i).getId() == gioHang.getId()) {
//                            GioHangDao.listMuaHang.remove(i); // remove nếu không check
//                        }
//                    }
                    GioHangDao.listMuaHang.remove(gioHang);
                    utilsInterface.setTongGia();
                }
                // kiểm tra dữ liệu trong Giỏ hàng và Mua hàng
                Log.d("hihi", "GioHang: " + new Gson().toJson(GioHangDao.listGioHang));
                Log.d("hihi", "MuaHang: " + new Gson().toJson(GioHangDao.listMuaHang));
            }
        });
    }


    private void showBtnMinusAndPlus(int soLuong, GioHangViewHolder holder) {
        if(soLuong <= 1) {
            holder.btnMinus.setVisibility(View.INVISIBLE);
            holder.btnPlus.setVisibility(View.VISIBLE);
        } else if(soLuong >= 10) {
            holder.btnMinus.setVisibility(View.VISIBLE);
            holder.btnPlus.setVisibility(View.INVISIBLE);
        } else {
            holder.btnMinus.setVisibility(View.VISIBLE);
            holder.btnPlus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if(list != null) {
            return list.size();
        }
        return 0;
    }

    public class GioHangViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvGia;
        private TextView tvSoLuong;
        private ImageView imgSp;
        private Button btnMinus;
        private Button btnPlus;
        private CheckBox chkMuaHang;

        public GioHangViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tv_name_sp);
            tvGia = itemView.findViewById(R.id.tv_gia_sp);
            tvSoLuong = itemView.findViewById(R.id.tv_soluong_sp);
            imgSp = itemView.findViewById(R.id.img_sp);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            chkMuaHang = itemView.findViewById(R.id.chk_mua_hang);
        }
    }
}
