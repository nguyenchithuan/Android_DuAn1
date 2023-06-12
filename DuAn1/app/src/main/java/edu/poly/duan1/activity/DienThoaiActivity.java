package edu.poly.duan1.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.poly.duan1.R;
import edu.poly.duan1.adapter.DienThoaiAdapter;
import edu.poly.duan1.model.SanPham;
import edu.poly.duan1.util.NetworkUtils;
import edu.poly.duan1.util.Server;

public class DienThoaiActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lvDienThoai;
    private DienThoaiAdapter adapter;
    private ArrayList<SanPham> listDt;
    private int idDt;
    private int page = 1; // trang
    private View footerView; // view progressbar
    // hàm scroll chạy liên tục nên tạo biến này
    // để dữ liệu load xong thì mới chạy tiếp tránh gây lỗi
    private boolean isLoading = false;
    private boolean limitdata = false; // kiểm tra đã hết dữ liệu chưa
    private mHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        AnhXaView();
        if(NetworkUtils.isNetworkConnected(this)) {
            GetIDLoaiSp();
            ActionToolbar();
            GetData(page);
            LoadMoreData();
        } else {
            Toast.makeText(this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void LoadMoreData() {
        lvDienThoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DienThoaiActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", listDt.get(position));
                startActivity(intent);
            }
        });

        lvDienThoai.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // vuốt đến vị trí nhất định
                Log.d("TAG", "scrollState: " + scrollState);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // firstVisibleItem : dòng đầu trong listView
                // visibleItemCount : các giá trị item có thể nhìn thấy trong view này
                // totalItemCount : tổng số lượng item trong lv
//                Log.d("TAG", "firstVisibleItem: " + firstVisibleItem);
//                Log.d("TAG", "visibleItemCount: " + firstVisibleItem);
//                Log.d("TAG", "totalItemCount: " + firstVisibleItem);
                if(visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitdata == false) {
                    // đọc dữ liệu về xong mới cập nhật dữ liệu nó chạy lây
                    // ta dùng thread để chạy đã luồng
                    // handler là dùng để cấp công việc cho thread
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            // hàm này dùng để quản lý dữ liệu khi thread gửi lên
            switch (msg.what) { // msg.what là dữ liệu gửi lên
                case 0:
                    lvDienThoai.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading = false;
                    break;
            }
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            super.run();
            mHandler.sendEmptyMessage(0); // gửi dữ liệu
            try {
                Thread.sleep(3000); // ngủ 3 giây
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // obtainMessage là phương thức liên kết thread với handler
            // nếu các bạn muốn tiếp tục liên kêt thread với handler phải gọi qua obtainMessage
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
        }
    }

    private void GetData(int page) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String link = Server.LinkSanPham + String.valueOf(page);

        // có thêm phương thức đẩy lên cho server
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // vì trong dữ liệu luôn có [] lên leng nhỏ nhất là 2
                if(response != null &&  response.length() != 2) {
                    lvDienThoai.removeFooterView(footerView); // nếu có dữ liệu thì tắt
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String tenSp = jsonObject.getString("tensanpham");
                            int giaSp = jsonObject.getInt("giasanpham");
                            String anhSp = jsonObject.getString("hinhanhsanpham");
                            String moTaSp = jsonObject.getString("motasanpham");
                            int idSp = jsonObject.getInt("idsanpham");
                            listDt.add(new SanPham(id, tenSp, giaSp, anhSp, moTaSp, idSp));
                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    limitdata = true;
                    lvDienThoai.removeFooterView(footerView); // nếu có dữ liệu thì tắt
                    Toast.makeText(DienThoaiActivity.this, "Đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DienThoaiActivity.this, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) { // phương thức đẩy dữ liệu lên cho server
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("idsanpham", String.valueOf(idDt)); // cùng với key trong php
                return param;
            }
        };

        requestQueue.add(stringRequest);
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

    private void GetIDLoaiSp() {
        idDt = getIntent().getIntExtra("idloaisanpham", -1);
    }

    private void AnhXaView() {
        toolbar = findViewById(R.id.id_toolbar);
        lvDienThoai = findViewById(R.id.lvDienThoai);
        listDt = new ArrayList<>();
        adapter = new DienThoaiAdapter(this, listDt);
        lvDienThoai.setAdapter(adapter);
        footerView = LayoutInflater.from(this).inflate(R.layout.layout_progress_bar, null);
        mHandler = new mHandler();
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
                Intent intent = new Intent(DienThoaiActivity.this, GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}