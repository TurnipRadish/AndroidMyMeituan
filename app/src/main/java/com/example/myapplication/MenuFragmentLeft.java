package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class MenuFragmentLeft extends Fragment {
    MenuActivity menuActivity;

    TextView lastSelectedTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuActivity = (MenuActivity) requireActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 加载 menu_left.xml 布局
        return inflater.inflate(R.layout.menu_left, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 获取视图中的控件。
        TextView recommendTab = view.findViewById(R.id.tv_recommend);
        TextView mustBuyTab = view.findViewById(R.id.tv_must_buy);
        // 为控件添加点击事件和背景资源
        recommendTab.setOnClickListener(v -> handleTabClick(recommendTab, "1"));
        recommendTab.setBackgroundResource(R.drawable.selector_textview_background);

        mustBuyTab.setOnClickListener(v -> handleTabClick(mustBuyTab, "2"));
        mustBuyTab.setBackgroundResource(R.drawable.selector_textview_background);
        // 默认显示推荐菜品
        handleTabClick(recommendTab, "1");
    }

    private void handleTabClick(TextView tab, String key) {
        // 切换主活动中的菜品，即令右碎片加载新数据
        menuActivity.switchData(menuActivity.getMenuList(key));
        // 将上一次点击过的控件恢复为未选中状态
        if (lastSelectedTextView != null) {
            lastSelectedTextView.setSelected(false);
        }
        // 将当前点击的控件设置为选中状态，这将改变其颜色
        tab.setSelected(true);
        // 将当前控件预备为已经点击过的控件
        lastSelectedTextView = tab;
    }



}