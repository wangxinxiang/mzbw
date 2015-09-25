package com.jhlc.zqb.view;

import android.os.Bundle;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.jhlc.zqb.R;


public class ExchangeCallDialog extends DialogFragment {
    private View view;
    private TextView tv_exchangecall_title,tv_exchange_content;
    private Button btn_exchangecall;
    private String title,content;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exchangedialog,null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();
        btn_exchangecall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("exchangecall_title");
        content = getArguments().getString("exchangecall_content");
    }

    private void initView() {
        tv_exchange_content = (TextView) view.findViewById(R.id.tv_exchange_call_content);
        tv_exchangecall_title = (TextView) view.findViewById(R.id.tv_exchange_call_title);
        btn_exchangecall = (Button) view.findViewById(R.id.btn_exchange_call);
        tv_exchangecall_title.setText(title);
        tv_exchange_content.setText(content);
    }
}
