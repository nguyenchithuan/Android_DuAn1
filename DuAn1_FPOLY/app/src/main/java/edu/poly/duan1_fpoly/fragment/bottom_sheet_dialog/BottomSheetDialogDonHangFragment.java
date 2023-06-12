package edu.poly.duan1_fpoly.fragment.bottom_sheet_dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.activity.GioHangActivity;
import edu.poly.duan1_fpoly.activity.MainActivity;
import edu.poly.duan1_fpoly.dao.GioHangDao;
import edu.poly.duan1_fpoly.models.GioHang;
import edu.poly.duan1_fpoly.models.SanPham;

public class BottomSheetDialogDonHangFragment extends BottomSheetDialogFragment {
    private Context context;
    private SanPham sanPham;

    private TextView tvName;
    private TextView tvGia;
    private TextView tvSoLuongSp;
    private ImageView imgSp;
    private Button btnGioHang;
    private Button btnMinus;
    private Button btnPlus;

    private int soLuong = 1; // biến số lượng

    public BottomSheetDialogDonHangFragment(Context context, SanPham sanPham) {
        this.context = context;
        this.sanPham = sanPham;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // tạo ra bottomSheetDialog
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        // tạo layout cho bottom sheet
        View view = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_don_hang, null);
        bottomSheetDialog.setContentView(view);

        initView(view);
        setData();
        
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GioHangActivity.class);
                context.startActivity(intent);
                bottomSheetDialog.dismiss();
                addListGioHang();
            }
        });

        showBtnMinusAndPlus();
        actionOnclickBtnSoLuong();

        // trả về bottomsheet để còn hiển thị
        return bottomSheetDialog;
    }

    private void addListGioHang() {
        Boolean check = false;

        // kiểm tra xem có dữ liệu chưa đó trong mảng chưa
        for (int i = 0; i < GioHangDao.listGioHang.size(); i++) {
            if(sanPham.getId() == GioHangDao.listGioHang.get(i).getId()) {
                soLuong += GioHangDao.listGioHang.get(i).getSoLuong();
                if(soLuong > 10) {
                    soLuong = 10;
                }
                // Gắn lại số lượng
                GioHangDao.listGioHang.get(i).setSoLuong(soLuong);
                GioHangDao.listGioHang.get(i).setGia(soLuong * sanPham.getGiaSp());
                check = true;
            }
        }

        if(check == false) {
            GioHangDao.listGioHang.add(new GioHang(sanPham.getId(), sanPham.getTenSp(), soLuong * sanPham.getGiaSp(), sanPham.getHinhAnhSp(), soLuong));
        }
    }

    private void setData() {
        tvName.setText(sanPham.getTenSp());
        DecimalFormat decimal = new DecimalFormat("###,###,###");
        tvGia.setText(decimal.format(sanPham.getGiaSp()) + "đ");

        Glide.with(context).load(sanPham.getHinhAnhSp()).into(imgSp);
    }


    private void actionOnclickBtnSoLuong() {

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soLuong < 10) {
                    soLuong += 1;
                    tvSoLuongSp.setText(soLuong + "");
                    showBtnMinusAndPlus();
                }
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soLuong > 1) {
                    soLuong -= 1;
                    tvSoLuongSp.setText(soLuong + "");
                    showBtnMinusAndPlus();
                }
            }
        });

    }

    private void showBtnMinusAndPlus() {
        // vào thì kiểm tra hiện thị mấy nút
        if(soLuong <= 1) {
            btnMinus.setVisibility(View.INVISIBLE);
            btnPlus.setVisibility(View.VISIBLE);
        } else if(soLuong >= 10) {
            btnMinus.setVisibility(View.VISIBLE);
            btnPlus.setVisibility(View.INVISIBLE);
        } else {
            btnMinus.setVisibility(View.VISIBLE);
            btnPlus.setVisibility(View.VISIBLE);
        }
    }


    private void initView(View view) {
        tvName = view.findViewById(R.id.tv_name_sp);
        tvGia = view.findViewById(R.id.tv_gia_sp);
        imgSp = view.findViewById(R.id.img_sp);
        btnGioHang = view.findViewById(R.id.btn_add_gio_hang);
        btnMinus = view.findViewById(R.id.btnMinus);
        btnPlus = view.findViewById(R.id.btnPlus);
        tvSoLuongSp = view.findViewById(R.id.tv_soluong_sv);
    }
}
