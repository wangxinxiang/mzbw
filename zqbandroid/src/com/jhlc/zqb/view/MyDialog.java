package com.jhlc.zqb.view;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.jhlc.zqb.R;
import com.jhlc.zqb.ui.ZQBApplication;
import com.jhlc.zqb.beans.Constants;
import com.jhlc.zqb.weibo.WeiBoAlert;
import com.jhlc.zqb.weixin.MMAlert;
import com.jhlc.zqb.weixin.Util;
import com.tencent.mm.sdk.openapi.*;

public class MyDialog extends DialogFragment implements View.OnClickListener{
    private ZQBApplication applicaion = ZQBApplication.getInstance();
    private Button btn_share_cancle;
    private ImageButton ibtn_wechat,ibtn_friend_circle,ibtn_sina,ibtn_qq;
    private View view;
    private Bundle savedInstanceState;

    private IWXAPI api;
    //微信分享按钮选项
    private static final int MMAlertSelect1  =  0;
    private static final int MMAlertSelect2  =  1;
    private static final int MMAlertSelect3  =  2;

    //微博分享
    private WeiBoAlert weiBoAlert;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //设置Dialog属性
        Window w = getDialog().getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        getDialog().onWindowAttributesChanged(lp);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        view  = inflater.inflate(R.layout.share_layout,null);
        final int cFullFillWidth = 10000;
        view.setMinimumWidth(cFullFillWidth);
        initView();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(getActivity(), Constants.WEIXIN_APP_ID);
        this.savedInstanceState = savedInstanceState;
    }

    private void initView() {
        btn_share_cancle = (Button) view.findViewById(R.id.btn_share_cancle);
        ibtn_friend_circle = (ImageButton) view.findViewById(R.id.ibtn_friends_circle);
        ibtn_wechat = (ImageButton) view.findViewById(R.id.ibtn_wechat);
        ibtn_sina = (ImageButton) view.findViewById(R.id.ibtn_sina);
        ibtn_qq = (ImageButton) view.findViewById(R.id.ibtn_qq);
        btn_share_cancle.setOnClickListener(this);
        ibtn_wechat.setOnClickListener(this);
        ibtn_sina.setOnClickListener(this);
        ibtn_friend_circle.setOnClickListener(this);
        ibtn_qq.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_share_cancle:
                this.dismiss();
                break;
            case R.id.ibtn_wechat:
                ShareToWeChat("http//www.baidu.com/", false);
                break;
            case R.id.ibtn_friends_circle:
                ShareToWeChat("http//www.baidu.com/",true);
                break;
            case R.id.ibtn_sina:
                weiBoAlert = new WeiBoAlert(getActivity(), savedInstanceState);
                weiBoAlert.showAlert(null, "http//www.baidu.com/");
                break;
            case R.id.ibtn_qq:
                Toast.makeText(getActivity(),"QQ",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void ShareToWeChat(final String url,final Boolean isShareToFriendCircle) {
        MMAlert.showAlert(getActivity(), getString(R.string.send_webpage),
                getActivity().getResources().getStringArray(R.array.send_webpage_item),
                null, new MMAlert.OnAlertSelectId() {

                    @Override
                    public void onClick(int whichButton) {
                        switch (whichButton) {
                            case MMAlertSelect1:
                                WXWebpageObject webpage = new WXWebpageObject();
                                webpage.webpageUrl = url;
                                WXMediaMessage msg = new WXMediaMessage(webpage);
                                msg.title = "该分享来自赚钱宝(测试)";
                                msg.description = "测试测试测试测试测试测试测试测试测试你妹啊";
                                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.send_music_thumb);
                                msg.thumbData = Util.bmpToByteArray(thumb, true);

                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                req.transaction = buildTransaction("webpage");
                                req.message = msg;
                                //是否分享到朋友圈
                                req.scene = isShareToFriendCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                                api.sendReq(req);
                                getDialog().dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                });

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public void onNewIntent(Intent intent){
        if (weiBoAlert != null) {
            weiBoAlert.onNewIntent(intent);
        }
    }
}
