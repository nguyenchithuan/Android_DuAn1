package edu.poly.duan1_fpoly.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.dao.ChiTietSpDao;
import edu.poly.duan1_fpoly.dao.GioHangDao;
import edu.poly.duan1_fpoly.dao.UserDao;
import edu.poly.duan1_fpoly.models.User;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.NetwordUtils;
import edu.poly.duan1_fpoly.utils.Server;

public class ThanhToanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView tvEmai;
    private TextView tvSdt;
    private TextView tvTongTien;
    private TextView tvSoLuongSp;
    private TextView tvNameUser;
    private Button btnDatHang;
    private Button btnCancle;
    private EditText edDiaChi;
    private ProgressBar progressBar;

    private User user;
    private int tongTien = 0;

    private int index = 0; // check nếu nhấn nhiều lần thì chỉ là 1 lần thôi



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        
        if(NetwordUtils.isNetworkConnected(this)) {
            initView();
            actionToolbar();
            setData();
            actionOnClickButtom();
        } else {
            CustomToast.makeText(this, "Không có kết nối Internet");
        }
    }

    private void setData() { // set data có sẵn hiển thị lên activity
        user = UserDao.listUser.get(UserDao.index);
        tongTien = getIntent().getIntExtra("tongtien", -1);
        DecimalFormat format = new DecimalFormat("###,###,###");

        tvNameUser.setText(user.getUsername());
        tvEmai.setText(user.getEmail());
        tvSdt.setText(user.getPhone());
        tvTongTien.setText(format.format(tongTien) + "đ");
        tvSoLuongSp.setText("Tổng tiền (" + GioHangDao.listMuaHang.size() + " sản phẩm): ");
    }

    private void actionOnClickButtom() {
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index == 0) {
                    String strDiaChi = edDiaChi.getText().toString();
                    if(strDiaChi.length() > 0) {
                        index = 1;
                        insertData(strDiaChi); // insert donhang và chitietdonhang
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        index = 0;
                        CustomToast.makeText(ThanhToanActivity.this, "Bạn chưa nhập địa chỉ");
                    }
                }
            }
        });
    }

    private void insertData(String strDiaChi) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.LinkInsertDonHang, new Response.Listener<String>() {
            @Override
            public void onResponse(String idDonHang) { // trả về id đơn hàng thì insert vào chi tiết đơn hàng
                ChiTietSpDao chiTietSpDao = new ChiTietSpDao();
                chiTietSpDao.setData(ThanhToanActivity.this, Server.LinkInsertChiTietDonHang, setHashMap(idDonHang));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThanhToanActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // insert dữ liệu vào bảng đơn hàng trước
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("iduser", user.getId() + "");
                hashMap.put("name", user.getUsername());
                hashMap.put("address", strDiaChi);
                hashMap.put("phone", user.getPhone());
                hashMap.put("email", user.getEmail());
                hashMap.put("soluong", GioHangDao.listMuaHang.size() + "");
                hashMap.put("tongtien", tongTien + "");
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    private HashMap<String, String> setHashMap(String idDonHang) {
        // chuyển dữ liệu về dạng json, để bên code php lấy dữ liệu json rồi insert
        // (dùng trong khi có nhiều dữ liệu cần insert)
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < GioHangDao.listMuaHang.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("iddonhang", Integer.parseInt(idDonHang));
                jsonObject.put("idsanpham", GioHangDao.listMuaHang.get(i).getId());
                jsonObject.put("tensanpham", GioHangDao.listMuaHang.get(i).getTenSp());
                jsonObject.put("soluongsanpham", GioHangDao.listMuaHang.get(i).getSoLuong());
                jsonObject.put("giasanpham", GioHangDao.listMuaHang.get(i).getGia());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonArray.put(jsonObject);
        }


        progressBar.setVisibility(View.GONE);
        xoaListData(); // Xóa list giỏ hàng và mua hàng


        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("json", jsonArray.toString());
        return hashMap;
    }

    private void xoaListData() {

        // vòng lặp for lồng nhau để xóa dữ liệu
        for (int i = 0; i < GioHangDao.listMuaHang.size(); i++) { // duyệt dữ liệu bảng mua hàng
            for (int j = 0; j < GioHangDao.listGioHang.size(); j++) { // duyệt dữ liệu bảng giỏ hảng

                int idMuaHang = GioHangDao.listMuaHang.get(i).getId(); // lấy ra id của tường sp muaHang
                int idGioHang = GioHangDao.listGioHang.get(j).getId(); // lấy ra id của tường sp gioHang

                if(idMuaHang == idGioHang) {
                    GioHangDao.listGioHang.remove(j); // nếu sảm phẩm mua hàng có ở giỏ hàng thì xóa
                }
            }
        }

        GioHangDao.listMuaHang.clear(); // xóa tất cả sản phẩm mua hàng
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

    private void initView() {
        toolbar = findViewById(R.id.id_toolbar);
        tvEmai = findViewById(R.id.tv_email);
        tvSdt = findViewById(R.id.tv_sdt);
        tvTongTien = findViewById(R.id.tv_tong_tien);
        tvSoLuongSp = findViewById(R.id.tv_soluong_sp);
        btnDatHang = findViewById(R.id.btn_dat_hang);
        btnCancle = findViewById(R.id.btn_cancle);
        tvNameUser = findViewById(R.id.tv_user_name);
        edDiaChi = findViewById(R.id.ed_dia_chi);
        progressBar = findViewById(R.id.id_progressBar);
    }
}