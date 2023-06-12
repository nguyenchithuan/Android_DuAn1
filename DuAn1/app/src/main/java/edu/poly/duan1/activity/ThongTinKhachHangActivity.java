package edu.poly.duan1.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import edu.poly.duan1.R;
import edu.poly.duan1.util.NetworkUtils;
import edu.poly.duan1.util.Server;

public class ThongTinKhachHangActivity extends AppCompatActivity {
    private EditText edTenKhachHang, edEmail, edSdt;
    private Button btnXacNhan, btnTroVe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);
        
        AnhXaView();
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(NetworkUtils.isNetworkConnected(this)) {
            EventButton();
        } else {
            Toast.makeText(this, "Không có kết nói Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void EventButton() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edTenKhachHang.getText().toString().trim();
                String sdt = edSdt.getText().toString().trim();
                String email = edEmail.getText().toString().trim();

                if(ten.length() > 0 && sdt.length() > 0 && email.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.LinkDonHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String maDonHang) {



                            if(Integer.parseInt(maDonHang) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.LinkChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.equals("1")) {
                                            MainActivity.listGioHang.clear();
                                            Toast.makeText(ThongTinKhachHangActivity.this, "Bạn đã thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ThongTinKhachHangActivity.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(ThongTinKhachHangActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.listGioHang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("iddonhang", Integer.parseInt(maDonHang));
                                                jsonObject.put("idsanpham", MainActivity.listGioHang.get(i).getId());
                                                jsonObject.put("tensanpham", MainActivity.listGioHang.get(i).getTenSp());
                                                jsonObject.put("giasanpham", MainActivity.listGioHang.get(i).getGiaSp());
                                                jsonObject.put("soluongsanpham", MainActivity.listGioHang.get(i).getSoLuong());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("json", jsonArray.toString());
                                        Log.d("test1", new Gson().toJson(jsonArray.toString()));
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ThongTinKhachHangActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("tenkhachhang", ten);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("email", email);
                            return hashMap;
                        }
                    };

                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(ThongTinKhachHangActivity.this, "Hãy kiểm tra lại dữ liệu", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void AnhXaView() {
        edTenKhachHang = findViewById(R.id.edTenKhachHang);
        edSdt = findViewById(R.id.edSdtKhachHang);
        edEmail = findViewById(R.id.edEmailKhachHang);
        btnXacNhan = findViewById(R.id.btnXacNhan);
        btnTroVe = findViewById(R.id.btnTroVe);
    }
}