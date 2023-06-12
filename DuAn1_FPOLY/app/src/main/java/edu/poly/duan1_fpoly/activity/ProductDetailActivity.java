package edu.poly.duan1_fpoly.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.dao.GioHangDao;
import edu.poly.duan1_fpoly.fragment.bottom_sheet_dialog.BottomSheetDialogDonHangFragment;
import edu.poly.duan1_fpoly.models.SanPham;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.NetwordUtils;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView imgSp;
    private TextView tvName;
    private TextView tvGia;
    private TextView tvChiTiet;
    private TextView tvMua;
    private SanPham sanPham;

    private Toolbar toolbar;
    private TextView numberShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        
        if(NetwordUtils.isNetworkConnected(this)) {
            initView();
            onclickMua();
            actionToolbar();
        } else {
            CustomToast.makeText(this, "Không có kết nối Internet");
        }
    }

    private void onclickMua() {
        tvMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheetDialog();
            }
        });
    }

    private void openBottomSheetDialog() {
        BottomSheetDialogDonHangFragment donHangFragment = new BottomSheetDialogDonHangFragment(this, sanPham);
        donHangFragment.show(getSupportFragmentManager(), donHangFragment.getTag()); // để hiển thị được btnSheetDialog
    }

    private void initView() {
        imgSp = findViewById(R.id.img_sp);
        tvName = findViewById(R.id.tv_name_sp);
        tvGia = findViewById(R.id.tv_gia_sp);
        tvChiTiet = findViewById(R.id.tv_chitiet_sp);
        tvMua = findViewById(R.id.tv_mua);
        toolbar = findViewById(R.id.id_toolbar);
        sanPham = new SanPham();

        // set dữ liệu cho chi tiết sản phẩm
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            sanPham = (SanPham) bundle.getSerializable("sanpham");
            Glide.with(this).load(sanPham.getHinhAnhSp()).into(imgSp);
            tvName.setText(sanPham.getTenSp());
            DecimalFormat decimal = new DecimalFormat("###,###,###");
            tvGia.setText(decimal.format(sanPham.getGiaSp()) + "đ");
            tvChiTiet.setText(sanPham.getMoTaSp());
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop, menu);

        // lấy item giỏ hàng
        MenuItem menuItem = menu.findItem(R.id.ic_shopping);

        // set layout cho item giỏ hàng
        menuItem.setActionView(R.layout.layout_notification_shopping);

        // lấy ra view
        View view = menuItem.getActionView();

        // thao tác với textview number
        numberShopping = view.findViewById(R.id.tv_number_shopping);
        numberShopping.setText(GioHangDao.listGioHang.size() + "");

        // bắt sự kiện onOptionsItemSelected không được lên bắt sự kiện view
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Toast.makeText(this, "Giỏ hàng", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.ic_shopping:
                // bắt sự kiện khi custom không được
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(numberShopping != null) {
            numberShopping.setText(GioHangDao.listGioHang.size() + "");
        }
    }
}