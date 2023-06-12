package edu.poly.duan1_fpoly.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.poly.duan1_fpoly.R;
import edu.poly.duan1_fpoly.activity.account.LoginActivity;
import edu.poly.duan1_fpoly.adapter.SettingsAdapter;
import edu.poly.duan1_fpoly.dao.UserDao;

public class SettingsFragment extends Fragment {
    private Button btnDangXuat;
    private TextView tvUserName;
    private RecyclerView rcvSetting;
    private SettingsAdapter adapter;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initView(view);
        actionOnClick();
        setData();
    }

    private void setData() {
        tvUserName.setText(UserDao.listUser.get(UserDao.index).getUsername());
    }

    private void actionOnClick() {
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn đăng xuất tài khoản không");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finishAffinity(); // thoát toàn bộ activity nhưng vẫn được thao tác

//                        getActivity().finish(); // thoát khỏi activity đang thao tác
//                        System.exit(0); // thoát khỏi ứng dụng và không cho thao tác
                    }
                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
            }
        });
    }

    private void initView(View view) {
        btnDangXuat = view.findViewById(R.id.btn_dang_xuat);
        tvUserName = view.findViewById(R.id.tv_user_name);

        rcvSetting = view.findViewById(R.id.rcv_settings);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rcvSetting.setLayoutManager(layoutManager);
        adapter = new SettingsAdapter(getContext());
        rcvSetting.setAdapter(adapter);
    }

}