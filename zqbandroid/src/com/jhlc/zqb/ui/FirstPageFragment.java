package com.jhlc.zqb.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jhlc.zqb.R;

import net.youmi.android.offers.OffersManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class FirstPageFragment extends BaseFragment {
    private View view;
    private LinearLayout ll_fistpage;
    private static Integer[] img_icon = {R.drawable.daily_sign_in, R.drawable.novice_task, R.drawable.youmi,
            R.drawable.midi, R.drawable.qumi, R.drawable.mopan, R.drawable.juzhang, R.drawable.zhimeng};
    private static String[] des = {"每日签到", "新手任务", "有米任务", "米迪任务", "趣米任务", "磨盘任务",
            "巨掌任务", "指盟任务"};
    private static String[] money = {"1000+", "80万+", "99万+", "99万+", "99万+", "99万+", "99万+", "99万+"};

    //viewpager
    private ViewPager adViewPager;
    private LinearLayout pagerLayout;
    private List<View> pageViews;
    private ImageView[] imageViews;
    private ImageView imageView;
    private AdPageAdapter adapter;
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private boolean isContinue = true;

    //有米积分统计
    private int myPointBalance = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.firstpage_layout, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initViewPager();
    }


    private void initView() {
        ll_fistpage = (LinearLayout) view.findViewById(R.id.ll_firstpage);

        for (int i = 0; i < img_icon.length; i++) {
            FillViewGroup(ll_fistpage, img_icon[i], null, des[i], null, money[i]);
            if ("每日签到".equals(tv_item_center.getText())) {
                exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                        startActivity(intent);
                    }
                });
            } else if ("新手任务".equals(tv_item_center.getText())) {
                exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(),NewAccountTaskActivity.class);
//                        startActivity(intent);
                        showToast("暂未开放....");
                    }
                });
            } else if ("有米任务".equals(tv_item_center.getText())) {
                exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 自 Youmi Android OfferWall SDK v5.0.0 起, 支持全屏积分墙退出监听回调
                        OffersManager.getInstance(getActivity()).showOffersWall(new net.youmi.android.listener.Interface_ActivityListener() {
                            @Override
                            public void onActivityDestroy(Context context) {

                            }
                        });
                    }
                });
            } else {
                exchange_icon_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("暂未开放....");
                    }
                });
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        OffersManager.getInstance(getActivity()).onAppExit();
    }

    //viewpager
    private void initViewPager() {

        //从布局文件中获取ViewPager父容器
        pagerLayout = (LinearLayout) view.findViewById(R.id.view_pager_content);
        //创建ViewPager
        adViewPager = new ViewPager(getActivity());

        //获取屏幕像素相关信息
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);


        //根据屏幕信息设置ViewPager广告容器的宽高
        adViewPager.setLayoutParams(new ViewGroup.LayoutParams(dm.widthPixels, dm.heightPixels * 2 / 5));

        //将ViewPager容器设置到布局文件父容器中
        pagerLayout.addView(adViewPager);

        initPageAdapter();

        initCirclePoint();

        adViewPager.setAdapter(adapter);
        adViewPager.setOnPageChangeListener(new AdPageChangeListener());

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(atomicInteger.get());
                        atomicOption();
                    }
                }
            }
        }).start();

    }


    private void atomicOption() {
        atomicInteger.incrementAndGet();
        if (atomicInteger.get() > imageViews.length - 1) {
            atomicInteger.getAndAdd(-5);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
    }

    /*
     * 每隔固定时间切换广告栏图片
     */
    private final Handler viewHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            adViewPager.setCurrentItem(msg.what);
            super.handleMessage(msg);
        }

    };

    private void initPageAdapter() {
        pageViews = new ArrayList<View>();

        ImageView img1 = new ImageView(getActivity());
        img1.setBackgroundResource(R.drawable.view_add_1);
        pageViews.add(img1);

        ImageView img2 = new ImageView(getActivity());
        img2.setBackgroundResource(R.drawable.view_add_2);
        pageViews.add(img2);

        ImageView img3 = new ImageView(getActivity());
        img3.setBackgroundResource(R.drawable.view_add_3);
        pageViews.add(img3);

        ImageView img4 = new ImageView(getActivity());
        img4.setBackgroundResource(R.drawable.view_add_4);
        pageViews.add(img4);

        ImageView img5 = new ImageView(getActivity());
        img5.setBackgroundResource(R.drawable.view_add_5);
        pageViews.add(img5);

        ImageView img6 = new ImageView(getActivity());
        img6.setBackgroundResource(R.drawable.view_add_6);
        pageViews.add(img6);

        adapter = new AdPageAdapter(pageViews);
    }

    private void initCirclePoint() {
        ViewGroup group = (ViewGroup) view.findViewById(R.id.viewGroup);
        imageViews = new ImageView[pageViews.size()];
        //广告栏的小圆点图标
        for (int i = 0; i < pageViews.size(); i++) {
            //创建一个ImageView, 并设置宽高. 将该对象放入到数组中
            imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(lp);
            imageViews[i] = imageView;

            //初始值, 默认第0个选中
            if (i == 0) {
                imageViews[i]
                        .setBackgroundResource(R.drawable.point_focused);
            } else {
                imageViews[i]
                        .setBackgroundResource(R.drawable.point_unfocused);
            }
            //将小圆点放入到布局中
            group.addView(imageViews[i]);
        }
    }


    /**
     * ViewPager 页面改变监听器
     */
    private final class AdPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 页面滚动状态发生改变的时候触发
         */
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        /**
         * 页面滚动的时候触发
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /**
         * 页面选中的时候触发
         */
        @Override
        public void onPageSelected(int arg0) {
            //获取当前显示的页面是哪个页面
            atomicInteger.getAndSet(arg0);
            //重新设置原点布局集合
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.drawable.point_focused);
                if (arg0 != i) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.point_unfocused);
                }
            }

        }
    }


    private final class AdPageAdapter extends PagerAdapter {
        private List<View> views = null;

        /**
         * 初始化数据源, 即View数组
         */
        public AdPageAdapter(List<View> views) {
            this.views = views;
        }

        /**
         * 从ViewPager中删除集合中对应索引的View对象
         */
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView(views.get(position));
        }

        /**
         * 获取ViewPager的个数
         */
        @Override
        public int getCount() {
            return views.size();
        }

        /**
         * 从View集合中获取对应索引的元素, 并添加到ViewPager中
         */
        @Override
        public Object instantiateItem(View container, final int position) {
            View view = views.get(position);
            ((ViewPager) container).addView(view, 0);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse("http://www.94.com/mzbw/index.htm");
                    intent.setData(content_url);
                    startActivity(intent);
//                    showToast("点击了第" + (position + 1) + "广告");
                }
            });
            return view;
        }

        /**
         * 是否将显示的ViewPager页面与instantiateItem返回的对象进行关联
         * 这个方法是必须实现的
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
