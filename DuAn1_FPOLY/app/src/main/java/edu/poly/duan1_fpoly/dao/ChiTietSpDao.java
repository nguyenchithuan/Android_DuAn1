package edu.poly.duan1_fpoly.dao;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import edu.poly.duan1_fpoly.activity.MainActivity;
import edu.poly.duan1_fpoly.utils.CustomToast;

public class ChiTietSpDao { // chi tiết đơn hàng

    public void setData(Context context, String link, HashMap<String, String> hashMap) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int check = Integer.parseInt(response);
                if(check == 1) { // nếu trả về giá trị là 1 insert thành công chi tiết đơn hàng
                    CustomToast.makeText((Activity) context, "Mua hàng thành công!");
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                    ((Activity) context).finishAffinity(); // xóa tất cả các acvitivy đang chạy
                } else {
                    CustomToast.makeText((Activity) context, "Mua hàng không thành công");
                }
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
}
