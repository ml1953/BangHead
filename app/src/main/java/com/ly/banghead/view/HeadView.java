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
import com.ly.banghead.listener.OnCustomItemAddListener;
import com.ly.banghead.listener.OnItemAddListener;
import com.ly.banghead.listener.OnRightClickListener;

public class HeadView extends LinearLayout {
    private boolean leftMutli, rightMutli = false;//是否有多个
    private int headRightSrc = -1;
    private String headRightStr = "";
    private OnBackClickListener onBackClickListener;
    private OnRightClickListener onRightClickListener;
    private LinearLayout llLeft, llRight;
    private LayoutInflater inflater;

    public HeadView(Context context) {
        super(context);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banghead_layout, null);
        RelativeLayout rlHead = view.findViewById(R.id.rl_head);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        llLeft = view.findViewById(R.id.ll_left);
        llRight = view.findViewById(R.id.ll_right);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BangHead, defStyleAttr, 0);
        String title = a.getString(R.styleable.BangHead_head_title);//标题
        int headBgSrc = a.getInt(R.styleable.BangHead_head_bg_src, R.color.white);

        leftMutli = a.getBoolean(R.styleable.BangHead_head_left_multi, false);//是否有多个
        rightMutli = a.getBoolean(R.styleable.BangHead_head_right_multi, false);//是否有多个

        int headBackSrc = a.getInt(R.styleable.BangHead_head_back_src, R.drawable.back_w_d);
        int headBackColor = a.getInt(R.styleable.BangHead_head_back_color, R.color.white);
        String headBackTitle = a.getString(R.styleable.BangHead_head_back_title);

        int headTitleColor = a.getInt(R.styleable.BangHead_head_back_src, R.color.white);
        int headRightColor = a.getInt(R.styleable.BangHead_head_back_src, R.color.white);

        headRightSrc = a.getInt(R.styleable.BangHead_head_right_src, -1);
        headRightStr = a.getString(R.styleable.BangHead_head_right_title);

        tvTitle.setText(title);
        tvTitle.setTextColor(headTitleColor);
        rlHead.setBackground(getResources().getDrawable(headBgSrc));
        if (!leftMutli) {
            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.head_horizontal_item, null);
            llRight.addView(item);
            ImageView ivBack = item.findViewById(R.id.iv_image);
            TextView tvBack = item.findViewById(R.id.tv_title);

            ivBack.setImageResource(headRightSrc);
            ivBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBackClickListener != null) {
                        onBackClickListener.onBackClick(v);
                    } else {
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                }
            });
            if (!TextUtils.isEmpty(headBackTitle)) {
                tvBack.setText(headBackTitle);
                tvBack.setTextColor(headBackColor);
            }
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRightClickListener != null) {
                        onRightClickListener.onRightClick(v);
                    }
                }
            });

        }
        if (!rightMutli) {
            LinearLayout item = (LinearLayout) inflater.inflate(R.layout.head_vertical_item, null);
            llRight.addView(item);
            ImageView ivRight = item.findViewById(R.id.iv_image);
            TextView tvRight = item.findViewById(R.id.tv_title);
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
                    if (onRightClickListener != null) {
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

    public void addLeftItem(OnItemAddListener listener) {
        View item = inflater.inflate(R.layout.head_horizontal_item, null);
        llLeft.addView(item);
        TextView tv=item.findViewById(R.id.tv_title);
        ImageView im=item.findViewById(R.id.iv_image);
        listener.onItemAdd(tv,im);
    }

    public void addLeftItem(View view, OnCustomItemAddListener listener) {
        llLeft.addView(view);
        listener.onItemAdd(view);
    }
    public void addRightItem(OnItemAddListener listener) {
        View item = inflater.inflate(R.layout.head_vertical_item, null);
        llRight.addView(item);
        TextView tv=item.findViewById(R.id.tv_title);
        ImageView im=item.findViewById(R.id.iv_image);
        listener.onItemAdd(tv,im);
    }

    public void addRightItem(View view, OnCustomItemAddListener listener) {
        llRight.addView(view);
        listener.onItemAdd(view);
    }
}
