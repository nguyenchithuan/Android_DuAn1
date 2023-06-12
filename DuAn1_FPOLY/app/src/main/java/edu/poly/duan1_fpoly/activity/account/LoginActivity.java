package edu.poly.duan1_fpoly.activity.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.activity.MainActivity;
import edu.poly.duan1_fpoly.dao.UserDao;
import edu.poly.duan1_fpoly.utils.CustomToast;
import edu.poly.duan1_fpoly.utils.NetwordUtils;
import edu.poly.duan1_fpoly.utils.Server;

public class LoginActivity extends AppCompatActivity {
    private TextView tvSignUp, tvSignUp_2;
    private EditText edEmail, edPass;
    private CheckBox chkSaveData;
    private Button btnLogin;
    private SharedPreferences prefer;
    private UserDao userDao;

    private RelativeLayout relativeManHinhLoadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(NetwordUtils.isNetworkConnected(this)) {
            initView();
            moveActivitySignUp();
            loginActivity();
            readPrefer();
        } else {
            CustomToast.makeText(this, "Không có kết nối Internet!");
        }
    }

    private void loginActivity() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionLogin();
            }
        });

    }

    private void actionLogin() {
        String email = edEmail.getText().toString().trim();
        String pass = edPass.getText().toString().trim();
        Boolean status = chkSaveData.isChecked();

        if(validate()) {
            writePrefer(email, pass, status);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validate() {
        String email = edEmail.getText().toString().trim();
        String pass = edPass.getText().toString().trim();

        if(email.isEmpty()) {
            CustomToast.makeText(this, "Mời nhập email!");
            return false;
        } else if(pass.isEmpty()) {
            CustomToast.makeText(this, "Mời nhập password!");
            return false;
        } else if(checkEmail(email) == false) {
            CustomToast.makeText(this, "Email không tồn tại!");
            return false;
        } else if(checkLogin(email, pass) == false) {
            CustomToast.makeText(this, "Mật khẩu của bạn không đúng!");
            return false;
        }

        return true;
    }

    private boolean checkEmail(String email) {
        boolean check = false;
        for (int i = 0; i < userDao.getListUser().size(); i++) {
            String emailUser = userDao.getListUser().get(i).getEmail();
            if(email.equals(emailUser)) {
                check = true; // có email thì là true
                UserDao.index = i;
            }
        }
        return check;
    }

    private boolean checkLogin(String email, String pass) {
        boolean check = false;
        for (int i = 0; i < userDao.getListUser().size(); i++) {
            String emailUser = userDao.getListUser().get(i).getEmail();
            String passUser = userDao.getListUser().get(i).getPass();
            if(email.equals(emailUser) && pass.equals(passUser)) {
                check = true; // có tài khoản thì là true
                UserDao.index = i;
            }
        }
        return check;
    }

    private void initView() {
        tvSignUp = findViewById(R.id.tv_signUp);
        tvSignUp_2 = findViewById(R.id.tv_signUp_2);
        edEmail = findViewById(R.id.ed_email);
        edPass = findViewById(R.id.ed_pass);
        chkSaveData = findViewById(R.id.chk_saveData);
        btnLogin = findViewById(R.id.btn_login);

        relativeManHinhLoadData = findViewById(R.id.id_man_hinh_load_data);

        prefer = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        userDao = new UserDao();
        userDao.newData(this, Server.LinkAllUser, btnLogin, relativeManHinhLoadData); // tạo dữ liệu rồi thao tác
    }

    private void moveActivitySignUp() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tvSignUp_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void writePrefer(String email, String pass, Boolean status) {
        SharedPreferences.Editor editor = prefer.edit();

        editor.putString("EMAIL", email);
        editor.putString("PASS", pass);
        editor.putBoolean("STATUS", status);

        editor.commit();
    }

    private void readPrefer() {
        String email = prefer.getString("EMAIL", null);
        String pass = prefer.getString("PASS", null);
        Boolean status = prefer.getBoolean("STATUS", false);

        chkSaveData.setChecked(status);
        if(email == null || pass == null) {
            return;
        }

        if(chkSaveData.isChecked()) {
            edEmail.setText(email);
            edPass.setText(pass);
        } else {
            edEmail.setText(email);
        }
    }
}