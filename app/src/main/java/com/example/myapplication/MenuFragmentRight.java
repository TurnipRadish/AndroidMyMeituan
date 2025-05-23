package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.List;

public class MenuFragmentRight extends Fragment {
    public MenuFragmentRight(){

    }
    public MenuFragmentRight getInstance(List<Dish> list){
        // 使用bundle存储数据
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list);

        // 利用bundle传递参数，以便在其他生命周期函数中使用
        MenuFragmentRight rightFragment = new MenuFragmentRight();
        rightFragment.setArguments(bundle);
        return rightFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 膨胀右碎片布局文件，变为视图对象
        View view = inflater.inflate(R.layout.menu_right, container, false);
        // 为有碎片布局文件添加显示数据（利用了适配器）
        if (getArguments() != null) {
            // 保证获取到的串行化列表是可转换为菜品列表的，注解SuppressWarnings可消除类型转换警告
            @SuppressWarnings("unchecked")
            List<Dish> list = (List<Dish>) getArguments().getSerializable("list");
            // 获取要应用适配器的控件
            ListView listView = view.findViewById(R.id.lv_list);
            // 应用适配器
            listView.setAdapter(new Adapter(getActivity(), list));
        }
        return  view;
    }

    static class Adapter extends BaseAdapter {
        Context context;
        List<Dish> adapterList;
    
        Adapter(Context context, List<Dish> list) {
            this.context = context;
            this.adapterList = list;
        }
    
        @Override
        public int getCount() {
            // 原错误：返回0导致无数据显示，应返回列表实际大小
            return adapterList.size();
        }
    
        @Override
        public Object getItem(int position) {
            return adapterList.get(position);
        }
    
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(this.context, R.layout.menu_list_item, null);
                holder = new MenuFragmentRight.ViewHolder();
                holder.tv_name = convertView.findViewById(R.id.tv_name);
                holder.tv_sale = convertView.findViewById(R.id.tv_sale);
                holder.tv_price = convertView.findViewById(R.id.tv_price);
                holder.iv_img = convertView.findViewById(R.id.iv_img);
                // 使用tag存储holder
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            Dish dish = adapterList.get(position);
            holder.tv_name.setText(dish.getName());
            holder.tv_sale.setText(dish.getSales());
            holder.tv_price.setText(dish.getPrice());
            holder.iv_img.setImageResource(dish.getImg());
            return convertView;
        }
    }

    static class ViewHolder {
        TextView tv_name,tv_sale,tv_price;
        ImageView iv_img;
    }
}