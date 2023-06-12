package edu.poly.duan1_fpoly.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.dao.GioHangDao;
import edu.poly.duan1_fpoly.fragment.HomeFragment;
import edu.poly.duan1_fpoly.fragment.LaptopFragment;
import edu.poly.duan1_fpoly.fragment.PhoneFragment;
import edu.poly.duan1_fpoly.fragment.DonHangFragment;
import edu.poly.duan1_fpoly.fragment.SettingsFragment;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.NetwordUtils;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private Toolbar toolbar;
    private Button btnSearch;
    private EditText edSearch;
    private LinearLayout linearLayout; // để ẩn thanh search
    private TextView numberShopping; // số lượng sản phẩm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(NetwordUtils.isNetworkConnected(this)) {
            initView();
            actionViewPagerAndBottomNav();
            actionToolbar();
            actionSearch();
            replaceFragment(HomeFragment.newInstance());
        } else {
            CustomToast.makeText(this, "Không có kết nối Internet");
        }
    }

    private void actionSearch() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivitySearch();
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

    private void startActivitySearch() {
        Intent intent = new Intent(getBaseContext(), SearchProductActivity.class);
        String value = edSearch.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("value", value);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
    }

    private void actionViewPagerAndBottomNav() {
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        replaceFragment(HomeFragment.newInstance());
                        linearLayout.setVisibility(View.VISIBLE); // thanh search
                        break;
                    case R.id.ic_phone:
                        replaceFragment(PhoneFragment.newInstance());
                        linearLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.ic_laptop:
                        replaceFragment(LaptopFragment.newInstance());
                        linearLayout.setVisibility(View.VISIBLE);
                        break;
                    case R.id.ic_donhang:
                        replaceFragment(DonHangFragment.newInstance());
                        linearLayout.setVisibility(View.GONE);
                        toolbar.setTitle("Đơn hàng");
                        break;
                    case R.id.ic_setting:
                        replaceFragment(SettingsFragment.newInstance());
                        linearLayout.setVisibility(View.GONE);
                        toolbar.setTitle("Settings");
                        break;
                }
                return true;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.id_frameLayout, fragment);
        transaction.commit();
    }

    private void initView() {
        bottomNav = findViewById(R.id.bottom_nav);
        toolbar = findViewById(R.id.id_toolbar);
        btnSearch = findViewById(R.id.btn_search);
        edSearch = findViewById(R.id.ed_search);
        linearLayout = findViewById(R.id.id_linnearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_shop, menu);

        // lấy item giỏ hàng
        MenuItem menuItem = menu.findItem(R.id.ic_shopping);

        // set layout cho item giỏ hàng
        menuItem.setActionView(R.layout.layout_notification_shopping);

        // lấy ra view(layout)
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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_shopping:
                Intent intent = new Intent(this, GioHangActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(numberShopping != null) { // khác null thì mới set nó giá trị
            numberShopping.setText(GioHangDao.listGioHang.size() + "");
        }
    }

    @Override
    public void onBackPressed() {
    }
}