package edu.poly.duan1_fpoly.activity.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.activity.account.LoginActivity;
import edu.poly.duan1_fpoly.dao.UserDao;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.NetwordUtils;
import edu.poly.duan1_fpoly.utils.Server;

public class SignUpActivity extends AppCompatActivity {
    private TextView tvLogin;
    private EditText edUsername, edEmail, edPass, edRePass, edNumberPhone;
    private Button btnSave;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);

        if(NetwordUtils.isNetworkConnected(this)) {
            initView();
            moveActivity();
        } else {
            CustomToast.makeText(this, "Không có kết nối Internet!");
        }
    }


    private void actionLogin() {
        String username = edUsername.getText().toString().trim();
        String email = edEmail.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        String phone = edNumberPhone.getText().toString().trim();


        if(valueData()) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("username", username);
            hashMap.put("email", email);
            hashMap.put("pass", pass);
            hashMap.put("phone", phone);
            userDao.insertUser(this, Server.LinkInsertUser, hashMap);
            btnSave.setEnabled(false);
        }
    }

    private boolean valueData() {
        String strUsername = edUsername.getText().toString().trim();
        String strEmail = edEmail.getText().toString().trim();
        String strPass = edPass.getText().toString().trim();
        String strPhone = edNumberPhone.getText().toString().trim();
        String strRePass = edRePass.getText().toString().trim();

        if(strUsername.isEmpty() || strEmail.isEmpty() || strPass.isEmpty() || strPhone.isEmpty() || strRePass.isEmpty()) {
            CustomToast.makeText(this, "Hãy nhập đầy đủ thông tin!");
            return false;
        } else if(strPass.equals(strRePass) == false) {
            CustomToast.makeText(this, "Pass and repass không khớp nhau!");
            return false;
        } else if(checkEmail(strEmail) == true) {
            CustomToast.makeText(this, "Đã tồn tại email!");
            return false;
        }

        return true;
    }

    private boolean checkEmail(String email) {
        boolean check = false;
        for (int i = 0; i < userDao.listUser.size(); i++) {
            String emailUser = userDao.listUser.get(i).getEmail();
            if(email.equals(emailUser)) {
                check = true; // có email thì là true
            }
        }
        return check;
    }

    private void initView() {
        tvLogin = findViewById(R.id.tv_login);
        edUsername = findViewById(R.id.ed_username);
        edEmail = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_pass);
        edRePass = findViewById(R.id.ed_rePass);
        edNumberPhone = findViewById(R.id.ed_phoneNumber);
        btnSave = findViewById(R.id.btn_save);

        userDao = new UserDao();
    }

    private void moveActivity() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLogin();
            }
        });
    }
}