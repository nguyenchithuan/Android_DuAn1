package edu.poly.duan1_fpoly.activity.package_settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.dao.UserDao;
import edu.poly.duan1_fpoly.models.User;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.Server;

public class ChangePassActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edPass, edNewPass, edNewRePass;
    private Button btnUpdate, btnCancle;
    private ProgressBar progressBar;
    private SharedPreferences prefer;

    private String strPass, strNewPass, strNewRePass;
    private User user;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionOnclick();
    }

    private void actionOnclick() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    private void updateUser() {
        strPass = edPass.getText().toString().trim();
        strNewPass = edNewPass.getText().toString().trim();
        strNewRePass = edNewRePass.getText().toString().trim();
        if(validate()) {
            progressBar.setVisibility(View.VISIBLE);
            userDao.updateUser(this, Server.LinkUpdateUser, getHashMap(), progressBar);
            writePrefer();
        }
    }

    private boolean validate() {
        if(strPass.isEmpty() == true || strNewPass.isEmpty() == true || strNewRePass.isEmpty() == true) {
            CustomToast.makeText(this, "Mời nhập đầy đủ dữ liệu");
            return false;
        } else if(strPass.equals(user.getPass()) == false) {
            CustomToast.makeText(this, "Mật khẩu cũ không đúng");
            return false;
        } else if(strNewPass.equals(strNewRePass) == false) {
            CustomToast.makeText(this, "Mật khậu mới không khớp nhau");
            return false;
        }
        return true;
    }

    private HashMap<String, String> getHashMap() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", user.getId() + "");
        hashMap.put("pass", strNewPass);
        return hashMap;
    }

    public void writePrefer() {
        // nếu thay đổi mật khẩu thì reset lại sharedPreferences
        SharedPreferences.Editor editor = prefer.edit();
        editor.putString("PASS", "");
        editor.putBoolean("STATUS", false);

        editor.commit();
    }


    private void initView() {
        toolbar = findViewById(R.id.id_toolbar);
        edPass = findViewById(R.id.ed_pass);
        edNewPass = findViewById(R.id.ed_new_pass);
        edNewRePass = findViewById(R.id.ed_new_re_pass);
        btnUpdate = findViewById(R.id.btn_update);
        btnCancle = findViewById(R.id.btn_cancle);
        progressBar = findViewById(R.id.id_progressBar);

        prefer = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        user = UserDao.listUser.get(UserDao.index);

        userDao = new UserDao();
    }
}