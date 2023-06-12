package edu.poly.duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.poly.duan1.R;
import edu.poly.duan1.adapter.LoaiSpAdapter;
import edu.poly.duan1.adapter.SanPhamAdapter;
import edu.poly.duan1.model.GioHang;
import edu.poly.duan1.model.LoaiSp;
import edu.poly.duan1.model.SanPham;
import edu.poly.duan1.util.NetworkUtils;
import edu.poly.duan1.util.Server;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navTrangChinh;
    private ViewFlipper viewFlipper;
    private TextView tvBadgeCount;


    // dữ liệu navLv
    private ListView lvManChinh;
    private ArrayList<LoaiSp> listLoaiSp;
    private LoaiSpAdapter loaiSpAdapter;

    // dữ liệu mới nhất
    private RecyclerView rcvTrangChinh;
    private ArrayList<SanPham> listSpMoiNhat;
    private SanPhamAdapter sanPhamAdapter;

    // mảng dữ liệu giỏ hàng có thể gọi bất kỳ đâu
    public static ArrayList<GioHang> listGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        // kiểm tra kết nối internet
        if(NetworkUtils.isNetworkConnected(this)) {
            // set các thuộc tính cho actionBar
            ActionBar();
            // chạy viewFlipper
            ActionViewFlipper();
            // đọc dữ liệu
            GetDuLieuLoaiSp();
            GetDuLieuSpMoiNhat();

            // bắt sự kiện listview
            lvManChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(MainActivity.this, DienThoaiActivity.class);
                            intent.putExtra("idloaisanpham", listLoaiSp.get(position).getId());
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(MainActivity.this, LapTopActivity.class);
                            intent.putExtra("idloaisanpham", listLoaiSp.get(position).getId());
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(MainActivity.this, LienHeActivity.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(MainActivity.this, ThongTinActivity.class);
                            startActivity(intent);
                            break;
                    }
                    // nav lại
                    drawerLayout.closeDrawer(navTrangChinh);
                }
            });
        } else {
            Toast.makeText(this, "Không có kết nối Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void GetDuLieuSpMoiNhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.LinkSanPhamMoiNhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            // id cùng với trong json của php
                            int id = jsonObject.getInt("id");
                            String tenSp = jsonObject.getString("tensanpham");
                            int giaSp = jsonObject.getInt("giasanpham");
                            String anhSp = jsonObject.getString("hinhanhsanpham");
                            String moTaSp = jsonObject.getString("motasanpham");
                            int idSp = jsonObject.getInt("idsanpham");
                            listSpMoiNhat.add(new SanPham(id, tenSp, giaSp, anhSp, moTaSp, idSp));
                            sanPhamAdapter.setData(listSpMoiNhat);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    private void GetDuLieuLoaiSp() {
        // đọc nội dung từ internet về dừng asystask hoặc volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // nhảy vào json và đọc trực tiếp dữ liệu mảng
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.LinkLoaiSp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            // lấy từng dữ liệu trong jsonArray
                            JSONObject jsonObject = response.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            String tenLoaiSp = jsonObject.getString("tenloaisanpham");
                            String hinhLoaiSp = jsonObject.getString("hinhloaisanpham");

                            // đưa dữ liệu vào list
                            listLoaiSp.add(new LoaiSp(id, tenLoaiSp, hinhLoaiSp));
                            loaiSpAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listLoaiSp.add(new LoaiSp(3, "Liên hệ", "https://tse1.mm.bing.net/th?id=OIP.OAa5FQEXFg2-p2DJL5e7XQHaHa&pid=Api&P=0"));
                    listLoaiSp.add(new LoaiSp(4, "Thông tin", "https://tse3.mm.bing.net/th?id=OIP.i990EA1wkyLHdtteUnTSQQHaHa&pid=Api&P=0"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // yêu cầu dữ liệu cho server
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        // drawer là int nên tạo mảng int
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.drawable.anhbanner1);
        list.add(R.drawable.anhbanner2);
        list.add(R.drawable.anhbien1);
        list.add(R.drawable.anhbien2);
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(list.get(i));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            // load ảnh vào viewFlipper
            viewFlipper.addView(imageView);
        }
        // chạy trong 5s
        viewFlipper.setFlipInterval(5000);
        // tự động chạy
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                drawerLayout.isDrawerOpen(navTrangChinh); // kiểm tra xem có đang mở hay không
                drawerLayout.openDrawer(GravityCompat.START); // set cho cái nav mở
//                drawerLayout.closeDrawer(navTrangChinh); // đóng nav lại
            }
        });
    }

    private void initView() {
        drawerLayout = findViewById(R.id.id_drawerLayout);
        toolbar = findViewById(R.id.id_toolbar);
        navTrangChinh = findViewById(R.id.id_nav);
        viewFlipper = findViewById(R.id.id_viewFlipper);

        lvManChinh = findViewById(R.id.lv_trangChinh);

        // -------------------------------------------
        listLoaiSp = new ArrayList<>();
        listLoaiSp.add(new LoaiSp(0, "Trang chính", "https://tse4.mm.bing.net/th?id=OIP.NSlKGZ5lB61nmNw99CGwlwHaHa&pid=Api&P=0"));
        loaiSpAdapter = new LoaiSpAdapter(this, listLoaiSp);
        lvManChinh.setAdapter(loaiSpAdapter);

        // -------------------------------------------
        rcvTrangChinh = findViewById(R.id.rcv_trangChinh);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvTrangChinh.setLayoutManager(layoutManager);
        listSpMoiNhat = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(this);
        rcvTrangChinh.setAdapter(sanPhamAdapter);

        // --------------Nếu chưa có dữ liệu thì tạo có rồi thì thôi ------------
        if(listGioHang == null) {
            listGioHang = new ArrayList<>();
        }
        Log.d("test", new Gson().toJson(listGioHang));
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(navTrangChinh)) {
            drawerLayout.closeDrawer(navTrangChinh);
        } else {
            super.onBackPressed();
        }
    }

    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // lấy item giỏ hàng
        MenuItem menuItem = menu.findItem(R.id.ic_gioHang);
        // set layout co item giỏ hàng
        menuItem.setActionView(R.layout.layout_notification_badge);
        // lấy ra view
        View view = menuItem.getActionView();
        // thao tác với tv của layout
        tvBadgeCount = view.findViewById(R.id.tv_badge_count);
        tvBadgeCount.setText(listGioHang.size() + "");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_gioHang:
                Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tvBadgeCount != null) {
            tvBadgeCount.setText(listGioHang.size() + "");
        }
    }


}
