package edu.poly.duan1_fpoly.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.adapter.SanPhamAdapter;
import edu.poly.duan1_fpoly.dao.GioHangDao;
import edu.poly.duan1_fpoly.dao.SanPhamDao;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.NetwordUtils;
import edu.poly.duan1_fpoly.utils.Server;

public class SearchProductActivity extends AppCompatActivity {
    private RecyclerView rcvSearch;
    private Toolbar toolbar;
    private Button btnSearch;
    private String value;
    private EditText edSearch;
    private SanPhamAdapter adapter;
    private SanPhamDao sanPhamDao;

    private TextView numberShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        if(NetwordUtils.isNetworkConnected(this)) {
            initView();
            actionToolbar();
            actionSearch();
        } else {
            CustomToast.makeText(this, "Không có kết nối Internet");
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

    private void actionSearch() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivitySearch();
            }
        });

        // mỗi lần thay đổi dữ liệu ở edittext thì nó chạy hàm này
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence chuoi, int start, int before, int count) {
                startActivitySearch(chuoi.toString()); // chuoi.toString : giá trị trong edText
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                    startActivitySearch();
                }
                return true;
            }
        });
    }

    private void startActivitySearch() { // của nut buttom
        value = edSearch.getText().toString().trim(); // value để chuyển vào hashmap
        // set lại dữ liệu khi search
        sanPhamDao.getSanPham(this, Server.LinkSearch, getHastMap(), adapter);
    }

    private void startActivitySearch(String search) { // của hàm onTextChanged
        value = search; // value để chuyển vào hashmap
        // set lại dữ liệu khi search
        sanPhamDao.getSanPham(this, Server.LinkSearch, getHastMap(), adapter);
    }

    private void initView() {
        toolbar = findViewById(R.id.id_toolbar);
        btnSearch = findViewById(R.id.btn_search);
        edSearch = findViewById(R.id.ed_search);

        rcvSearch = findViewById(R.id.rcv_search_product);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvSearch.setLayoutManager(layoutManager);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            value = bundle.getString("value");
            edSearch.setText(value);
        }

        adapter = new SanPhamAdapter(this);
        sanPhamDao = new SanPhamDao();
        // set dữ liệu khi search
        sanPhamDao.getSanPham(this, Server.LinkSearch, getHastMap(), adapter);
        rcvSearch.setAdapter(adapter);
    }

    private HashMap<String, String> getHastMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("name", value);
        return hashMap;
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
        switch (item.getItemId()) {
            case R.id.ic_shopping:
                Intent intent = new Intent(this, GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(numberShopping != null) {
            numberShopping.setText(GioHangDao.listGioHang.size() + "");
        }
    }
}