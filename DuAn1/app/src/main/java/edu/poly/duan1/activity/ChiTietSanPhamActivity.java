package edu.poly.duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1.R;
import edu.poly.duan1.model.GioHang;
import edu.poly.duan1.model.SanPham;
import edu.poly.duan1.util.NetworkUtils;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imgChiTiet;
    private TextView tvTen, tvGia, tvMoTa;
    private Spinner spnSoLuong;
    private Button btnDatMua;

    private int id;
    private String tenChiTiet;
    private int giaChiTiet;
    private String hinhAnhChiTiet;
    private String moTaChiTiet;
    private int idSanPham;
    private int soLuong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        
        AnhXaView();
        if(NetworkUtils.isNetworkConnected(this)) {
            ActionToolbar();
            GetInFormation();
            CatchEventSpinner();
            EventButton();
        } else {
            Toast.makeText(this, "Không có Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void EventButton() {
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listGioHang.size() > 0) {
                    soLuong = (int) spnSoLuong.getSelectedItem();
                    boolean check = false;
                    for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                        // kiểm tra xem đã thêm dữ liệu này chưa, nều rồi thì cập nhật lại số lượng
                        if(MainActivity.listGioHang.get(i).getId() == id) {
                            // nếu đã có thì số lượng cộng thêm với số lượng ban đầu
                            soLuong += MainActivity.listGioHang.get(i).getSoLuong();
                            MainActivity.listGioHang.get(i).setSoLuong(soLuong);
                            // nếu số lượng lớn hơn 10 thì set dữ liệu lại là 10
                            if(MainActivity.listGioHang.get(i).getSoLuong() > 10) {
                                MainActivity.listGioHang.get(i).setSoLuong(10);
                            }
                            // cập nhật lại giá
                            MainActivity.listGioHang.get(i).setGiaSp(giaChiTiet * MainActivity.listGioHang.get(i).getSoLuong());
                            check = true;
                        }
                    }

                    if(check == false) {
                        soLuong = (int) spnSoLuong.getSelectedItem();
                        long giaMoi = soLuong * giaChiTiet; // giá mới
                        MainActivity.listGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                    }
                } else { // chưa có dữ liệu trong mang thì thêm thẳng vào
                    soLuong = (int) spnSoLuong.getSelectedItem();
                    long giaMoi = soLuong * giaChiTiet; // giá mới
                    MainActivity.listGioHang.add(new GioHang(id, tenChiTiet, giaMoi, hinhAnhChiTiet, soLuong));
                }

                Intent intent = new Intent(getBaseContext(), GioHangActivity.class);

                startActivity(intent);
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soLuong = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soLuong);
        spnSoLuong.setAdapter(adapter);
    }

    private void GetInFormation() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getId();
        tenChiTiet = sanPham.getTenSanPham();
        giaChiTiet = sanPham.getGiaSanPham();
        hinhAnhChiTiet = sanPham.getHinhAnhSanPham();
        moTaChiTiet = sanPham.getMoTaSanPham();
        idSanPham = sanPham.getIdSanPham();

        tvTen.setText(tenChiTiet);
        DecimalFormat format = new DecimalFormat("###,###,###");
        tvGia.setText("Giá: " + format.format(giaChiTiet) + " VNĐ");
        tvMoTa.setText(moTaChiTiet);
        Glide.with(this).load(hinhAnhChiTiet).into(imgChiTiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXaView() {
        toolbar = findViewById(R.id.id_toolbar);
        imgChiTiet = findViewById(R.id.imgChiTietSp);
        tvTen = findViewById(R.id.tvTenChiTietSp);
        tvGia = findViewById(R.id.tvGiaChiTietSp);
        tvMoTa = findViewById(R.id.tvMoToChiTietSp);
        spnSoLuong = findViewById(R.id.id_spinner);
        btnDatMua = findViewById(R.id.btnDatMua);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_gioHang:
                Intent intent = new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}