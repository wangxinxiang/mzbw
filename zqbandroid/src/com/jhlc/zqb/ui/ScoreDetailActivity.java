package com.jhlc.zqb.ui;

import android.os.Bundle;
import com.jhlc.zqb.R;

/**
 * Created by licheng on 28/8/15.
 */
public class ScoreDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_one);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("积分明细");
    }
}
