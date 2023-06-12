package edu.poly.duan1_fpoly.dao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.poly.duan1_fpoly.activity.account.LoginActivity;
import edu.poly.duan1_fpoly.models.User;
import edu.poly.duan1_fpoly.utils.CustomToast;

public class UserDao {
    public static int index; // tạo biến index, để khi activity khác dùng đến còn đang ở index thứ mấy
    public static ArrayList<User> listUser = new ArrayList<>(); // tạo static để có thể gọi ở bất kì đâu, và chỉ khai báo 1 lần

    // lấy toàn bộ dữ liệu
    public void newData(Context context, String link, Button button, RelativeLayout relativeLayout) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listUser.clear();

                if(response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String username = jsonObject.getString("username");
                            String email = jsonObject.getString("email");
                            String pass = jsonObject.getString("pass");
                            String phone = jsonObject.getString("phone");
                            listUser.add(new User(id, username, email, pass, phone));

                            button.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    public void insertUser(Context context, String link, HashMap<String, String> hashMap) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomToast.makeText((Activity) context, "Tạo tài khoản thành công!");
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void updateUser(Context context, String link, HashMap<String, String> hashMap, ProgressBar progressBar) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
                public void onResponse(String response) {
                int check = Integer.parseInt(response);
                if(check == 1) {
                    CustomToast.makeText((Activity) context, "Đổi mật khẩu thành công!");
                } else {
                    CustomToast.makeText((Activity) context, "Đổi mật khẩu thất bại!");
                }
                progressBar.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    public static ArrayList<User> getListUser() {
        return listUser;
    }

    public static void setListUser(ArrayList<User> listUser) {
        UserDao.listUser = listUser;
    }
}
