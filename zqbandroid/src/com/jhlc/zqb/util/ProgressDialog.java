package com.jhlc.zqb.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;
import com.jhlc.zqb.R;


/**
 * Created by 104520 on 2015/4/1.
 */
public class ProgressDialog extends Dialog {
    private Context context = null;
    private static ProgressDialog ProgressDialog = null;

    //方法重写一
    public ProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    //方法重写二
    public ProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    //创建一个自定义的progressDialog
    public static ProgressDialog createDialog(Context context) {
        ProgressDialog = new ProgressDialog(context, R.style.progress_dialog);
        ProgressDialog.setCancelable(true);
        ProgressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ProgressDialog.show();
        //setContent一定要在show()方法后
        ProgressDialog.setContentView(R.layout.dialog);
        return ProgressDialog;
    }

    //设置显示的文字
    public static ProgressDialog setMessage(String message) {
        TextView msg = (TextView) ProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        if (msg != null) {
            msg.setText(message);
        }
        return ProgressDialog;
    }

    public static ProgressDialog Dismiss() {
        ProgressDialog.dismiss();
        return ProgressDialog;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }
}
