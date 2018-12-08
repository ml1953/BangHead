package com.ly.banghead.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.banghead.R;
import com.ly.banghead.bangtools.BangScreenTools;
import com.ly.banghead.listener.OnBackClickListener;
import com.ly.banghead.listener.OnRightClickListener;

public class HeadView extends LinearLayout {
    boolean multi = false;//是否有多个
    int headRightSrc = -1;
    String headRightStr = "";
    private OnBackClickListener onBackClickListener;
    private OnRightClickListener onRightClickListener;
    public HeadView(Context context) {
        super(context);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banghead_layout, null);
        RelativeLayout rlHead = view.findViewById(R.id.rl_head);
        ImageView ivBack = view.findViewById(R.id.iv_back);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        LinearLayout llRight = view.findViewById(R.id.ll_right);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BangHead, defStyleAttr, 0);
        String title = a.getString(R.styleable.BangHead_head_title);//标题
        int headBgSrc=a.getInt(R.styleable.BangHead_head_bg_src,R.color.white);
        multi = a.getBoolean(R.styleable.BangHead_head_right_multi, false);//是否有多个
        int headBackSrc = a.getInt(R.styleable.BangHead_head_back_src, R.drawable.back_w_d);
        int headTitleColor = a.getInt(R.styleable.BangHead_head_back_src, R.color.white);
        int headRightColor = a.getInt(R.styleable.BangHead_head_back_src, R.color.white);
        headRightSrc = a.getInt(R.styleable.BangHead_head_right_src, -1);
        headRightStr = a.getString(R.styleable.BangHead_head_right_title);
        ivBack.setImageResource(headBackSrc);
        tvTitle.setText(title);
        tvTitle.setTextColor(headTitleColor);
        rlHead.setBackground(getResources().getDrawable(headBgSrc));
        if (!multi) {
            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.head_right_item, null);
            llRight.addView(item);
            ImageView ivRight = item.findViewById(R.id.iv_right);
            TextView tvRight = item.findViewById(R.id.tv_right);
            if (headRightSrc != -1) {
                ivRight.setImageResource(headRightSrc);
            }
            if (!TextUtils.isEmpty(headRightStr)) {
                tvRight.setText(headRightStr);
                tvRight.setTextColor(headRightColor);
            }
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRightClickListener!=null){
                        onRightClickListener.onRightClick(v);
                    }
                }
            });
        }
        boolean isbang = false;
        if (context instanceof Activity) {
            isbang = BangScreenTools.getBangScreenTools().hasBangScreen(((Activity) context).getWindow());
        }
        if (isbang) {//是异形屏
            int padding = dip2px(10);
            int paddingTop = dip2px(50);
            int height = dip2px(84);
            rlHead.setPadding(0, paddingTop, 0, padding);
            ViewGroup.LayoutParams layoutParams = rlHead.getLayoutParams();
            layoutParams.height = height;
            rlHead.setLayoutParams(layoutParams);
        } else {//非异形屏
            int padding = dip2px(10);
            int paddingTop = dip2px(35);
            int height = dip2px(74);
            rlHead.setPadding(0, paddingTop, 0, padding);
            ViewGroup.LayoutParams layoutParams = rlHead.getLayoutParams();
            layoutParams.height = height;
            rlHead.setLayoutParams(layoutParams);
        }
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackClickListener!=null){
                    onBackClickListener.onBackClick(v);
                }else {
                    if (context instanceof Activity){
                        ((Activity) context).finish();
                    }
                }
            }
        });

        a.recycle();
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        this.onRightClickListener = onRightClickListener;
    }
}
