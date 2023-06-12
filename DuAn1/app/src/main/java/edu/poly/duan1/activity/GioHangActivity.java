package edu.poly.duan1.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import edu.poly.duan1.R;
import edu.poly.duan1.adapter.GioHangAdapter;
import edu.poly.duan1.model.GioHang;
import edu.poly.duan1.util.NetworkUtils;

public class GioHangActivity extends AppCompatActivity {
    private ListView lvGioHang;
    private TextView tvThongBao;
    private static TextView tvTongTien;
    private Button btnThanhToan, btnTiepTucMua;
    private Toolbar toolbar;
    private GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        
        if(NetworkUtils.isNetworkConnected(this)) {
            AnhXaView();
            ActionToolBar();
            CheckData();
            EvenUltil();
            CatchOnItemListView();
            EventButton();
        } else {
            Toast.makeText(this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void EventButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listGioHang.size() > 0) {
                    Intent intent = new Intent(GioHangActivity.this, ThongTinKhachHangActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng của bạn chưa có sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.listGioHang.size() <= 0) {
                            tvThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.listGioHang.remove(position);
                            adapter.notifyDataSetChanged();
                            // hiển thị lại tổng tiền
                            EvenUltil();
                            if(MainActivity.listGioHang.size() <= 0) {
                                tvThongBao.setVisibility(View.VISIBLE);
                            } else {
                                tvThongBao.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();

                return false;
            }
        });
    }

    public static void EvenUltil() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
            tongTien += MainActivity.listGioHang.get(i).getGiaSp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(tongTien) + " VNĐ");
    }

    private void CheckData() {
        if(MainActivity.listGioHang.size() <= 0) {
            tvThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        } else {
            tvThongBao.setVisibility(View.GONE);
            lvGioHang.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    private void ActionToolBar() {
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
        lvGioHang = findViewById(R.id.lvGioHang);
        tvThongBao = findViewById(R.id.tvThongBao);
        tvTongTien = findViewById(R.id.tvTongTienGioHang);
        btnThanhToan = findViewById(R.id.btnThanhToanHang);
        btnTiepTucMua = findViewById(R.id.btnTiepTucMuaHang);
        toolbar = findViewById(R.id.id_toolbar);
        adapter = new GioHangAdapter(this, MainActivity.listGioHang);
        lvGioHang.setAdapter(adapter);
    }
}