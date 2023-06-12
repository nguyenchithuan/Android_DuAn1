package edu.poly.duan1_fpoly.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import edu.poly.duan1_fpoly.R;

public class CustomToast {

    public static void makeText(Activity activity, String value) {
        Toast toast = new Toast(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_custom_toast, activity.findViewById(R.id.layout_custom_toast));
        toast.setView(view);
        TextView tvMessage = view.findViewById(R.id.tv_custom_toast);
        tvMessage.setText(value);
        toast.show();
    }
}
