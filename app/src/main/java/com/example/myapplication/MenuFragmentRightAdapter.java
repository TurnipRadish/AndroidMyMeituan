package com.example.myapplication;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class MenuFragmentRightAdapter extends BaseAdapter {
    private final int MAX_QANTITY = 60;

    private Context mContext;
    private List<Dish> list;
    public MenuFragmentRightAdapter(Context context , List<Dish>list){
        this.mContext = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     *
     * @param position 当前项目在适配器中的位置
     * @param convertView 要重用的旧视图。若没有，则创建新视图。该参数的存在有助于优化内存使用和性能，避免了在滑动时频繁加载适配器项目
     * @param parent 该视图最终所附加的父视图。这一般与xml文件中的父标签元素一致。
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // 若没有旧的可重用视图，则创建新视图
        if(convertView == null) {
            // 膨胀列表布局文件，将xml节点树转换为可操纵的视图对象
            convertView = View.inflate(mContext, R.layout.menu_right, null);
            // 从视图对象中获取相应组件
            holder= new ViewHolder();
            holder.tv_decrease_amount = convertView.findViewById(R.id.tv_decrease_amount);
            holder.tv_increase_amount = convertView.findViewById(R.id.tv_increase_amount);
            holder.tv_qantity = convertView.findViewById(R.id.tv_quantity);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_sale = convertView.findViewById(R.id.tv_sale);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.iv_img = convertView.findViewById(R.id.iv_img);

            holder.tv_decrease_amount.setOnClickListener(v -> {
                int quantity = Integer.parseInt(holder.tv_qantity.getText().toString());
                Log.d("test amount", "test" + quantity);
                if (quantity > 2) {
                    quantity--;
                    holder.tv_qantity.setText(String.valueOf(quantity));
                }
            });

            Log.e("test", "???");

            holder.tv_increase_amount.setOnClickListener(v -> {
                Log.e("test", "点击增加数量按钮");
                int quantity = Integer.parseInt(holder.tv_qantity.getText().toString());
                Log.e("test", "当前数量: " + quantity);
                if (quantity < MAX_QANTITY) {
                    quantity++;
                    holder.tv_qantity.setText(String.valueOf(quantity));
                    Log.e("test", "增加后数量: " + quantity);
                }
            });
            // 使用tag存储holder
            convertView.setTag(holder);
        } else {
            // 获取之前存储的holder
            holder = (ViewHolder) convertView.getTag();
        }
        // 为布局中的控件设置要显示出的数据
        Dish bean = list.get(position);
        holder.tv_name.setText(bean.getName());
        holder.tv_sale.setText(bean.getSales());
        holder.tv_price.setText(bean.getPrice());
        holder.iv_img.setBackgroundResource(bean.getImg());
        return convertView;
    }

    /**
     * 使用holder静态内部类封装视图控件对象
     */
    static class ViewHolder {
        TextView tv_name,tv_sale,tv_price, tv_decrease_amount, tv_increase_amount, tv_qantity;
        ImageView iv_img;
    }
}
