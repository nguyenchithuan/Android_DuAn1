package edu.poly.duan1_fpoly.activity.package_settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.dao.UserDao;
import edu.poly.duan1_fpoly.models.User;

public class InfoActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvSdt;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        
        initView();
        setData();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setData() {
        User user = UserDao.listUser.get(UserDao.index);
        tvName.setText(user.getUsername());
        tvEmail.setText(user.getEmail());
        tvSdt.setText(user.getPhone() + "");
    }

    private void initView() {
        tvName = findViewById(R.id.tv_user_name);
        tvEmail = findViewById(R.id.tv_email);
        tvSdt = findViewById(R.id.tv_sdt);

        toolbar = findViewById(R.id.id_toolbar);
    }
}