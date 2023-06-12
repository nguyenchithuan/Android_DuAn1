package edu.poly.duan1_fpoly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.adapter.GioHangAdapter;
import edu.poly.duan1_fpoly.dao.GioHangDao;
import edu.poly.duan1_fpoly.models.GioHang;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.NetwordUtils;
import edu.poly.duan1_fpoly.utils.UtilsInterface;

public class GioHangActivity extends AppCompatActivity implements UtilsInterface {
    private Toolbar toolbar;
    private RecyclerView rcvGioHang;
    private GioHangAdapter adapter;
    private TextView tvThongBao;
    private TextView tvTongTien;
    private Button btnGioHang;

    private int tongTien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        
        if(NetwordUtils.isNetworkConnected(this)) {
            initView();
            actionToolbar();
            kiemTraTrong();
            tongTien();
            actionMua();
        } else {
            CustomToast.makeText(this, "Không có kết nối Internet!");
        }
    }

    private void tongTien() {
        tongTien = 0;
        for (int i = 0; i < GioHangDao.listMuaHang.size(); i++) {
            tongTien += GioHangDao.listMuaHang.get(i).getGia();
        }

        DecimalFormat decimal = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimal.format(tongTien) + "đ");
    }

    private void kiemTraTrong() {
        if(GioHangDao.listGioHang.size() == 0) {
            tvThongBao.setVisibility(View.VISIBLE);
        } else {
            tvThongBao.setVisibility(View.GONE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                GioHangDao.listMuaHang.clear(); // mỗi khi quay lại bàn mình trước thì clear mua hàng
            }
        });
    }

    private void initView() {
        tvThongBao = findViewById(R.id.tv_thong_bao_trong);
        tvTongTien = findViewById(R.id.tv_tong_tien);
        btnGioHang = findViewById(R.id.btn_gio_hang);
        btnGioHang.setText("Mua hàng (" + GioHangDao.listMuaHang.size() + ")");

        toolbar = findViewById(R.id.id_toolbar);
        rcvGioHang = findViewById(R.id.rcv_gio_hang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvGioHang.setLayoutManager(layoutManager);

        adapter = new GioHangAdapter(this, this::setTongGia);
        adapter.setData(GioHangDao.listGioHang);
        rcvGioHang.setAdapter(adapter);
    }

    private void actionMua() {
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GioHangDao.listMuaHang.size() > 0) {
                    Intent intent = new Intent(getBaseContext(), ThanhToanActivity.class);
                    intent.putExtra("tongtien", tongTien);
                    startActivity(intent);
                } else {
                    CustomToast.makeText(GioHangActivity.this, "Giỏ hàng không có dữ liệu!");
                }
            }
        });
    }

    @Override
    public void setTongGia() {
        // reset lại dữ liệu hiển thị
        tongTien(); // thực hiện lại tính tổng thông qua interface
        kiemTraTrong(); // kiểm tra trống
        btnGioHang.setText("Mua hàng (" + GioHangDao.listMuaHang.size() + ")");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GioHangDao.listMuaHang.clear(); // mỗi khi quay lại bàn mình trước thì clear mua hàng
        // tránh gây lỗi phát sinh
    }
}