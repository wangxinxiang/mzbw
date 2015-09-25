package com.jhlc.zqb.weibo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import com.jhlc.zqb.R;
import com.jhlc.zqb.view.WBShareItemView;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.*;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.utils.Utility;

/**
 * Created by Administrator on 2015/9/8.
 */
public class WeiBoAlert implements IWeiboHandler.Response{

    private static Activity context;
    /** 微博微博分享接口实例 */
    private static IWeiboShareAPI mWeiboShareAPI = null;
    /**
     * 分享网页控件
     */
    private static WBShareItemView mShareWebPageView;

    public WeiBoAlert(Activity context, Bundle savedInstanceState){
        WeiBoAlert.context = context;
        initView();
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(context, Constants.APP_KEY);

        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();

        // 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(context.getIntent(), this);
        }
    }

    private void initView() {

    }

    public Dialog showAlert(DialogInterface.OnCancelListener cancelListener, String url) {
        final Dialog dlg = new Dialog(context, R.style.MMTheme_DataSheet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.sina_share, null);
        mShareWebPageView = (WBShareItemView)layout.findViewById(R.id.share_webpage_view);
        mShareWebPageView.initWithData("分享网页", R.drawable.ic_sina_logo, "分享网页", "网页描述：赚钱宝下载", url);

        layout.findViewById(R.id.share_to_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
                    int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
                    if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                        sendMultiMessage();
                    } else {
                        sendSingleMessage();
                    }
                } else {
                    Toast.makeText(context, "微博客户端不支持 SDK 分享或微博客户端未安装或微博客户端是非官方版本。", Toast.LENGTH_LONG).show();
                }
            }
        });

        // set a large value put it in bottom
        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(true);
        if (cancelListener != null) {
            dlg.setOnCancelListener(cancelListener);
        }
        dlg.setContentView(layout);
        dlg.show();
        return dlg;
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendSingleMessage() {

        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();

        weiboMessage.mediaObject = getWebpageObj();

        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(context, request);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     */
    private void sendMultiMessage() {

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();

            weiboMessage.mediaObject = getWebpageObj();

        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;

        // 3. 发送请求消息到微博，唤起微博分享界面
            mWeiboShareAPI.sendRequest(context, request);

    }


    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = mShareWebPageView.getTitle();
        mediaObject.description = mShareWebPageView.getShareDesc();

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo);
        // 设置 Bitmap 类型的图片到视频对象里         设置缩略图。 注意：最终压缩过的缩略图大小不得超过 32kb。
        mediaObject.setThumbImage(bitmap);
        mediaObject.actionUrl = mShareWebPageView.getShareUrl();
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }


    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     *
     * @param baseResp 微博请求数据对象
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
            case WBConstants.ErrorCode.ERR_OK:
                Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_CANCEL:
                Toast.makeText(context, "取消", Toast.LENGTH_LONG).show();
                break;
            case WBConstants.ErrorCode.ERR_FAIL:
                Toast.makeText(context,
                        "分享失败" + "Error Message: " + baseResp.errMsg,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * 将返回结果在返回Activity时调用
     * @param intent
     */
    public void onNewIntent(Intent intent) {
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }
}
