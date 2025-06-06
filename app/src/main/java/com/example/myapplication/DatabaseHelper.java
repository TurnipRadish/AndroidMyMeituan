package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {
    // 数据库基本信息
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_MEITUAN_NAME = "meituan1.db";
    // 菜品表结构
    private static final String TABLE_MENU_LIST = "dishes";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CATEGORY = "category";  // 类别（1:推荐 2:必买）
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SALES = "sales";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_IMG = "img";

    private final String databaseName;

    // 建表语句
    private static final String CREATE_TABLE_MENU_LIST = "CREATE TABLE IF NOT EXISTS " + TABLE_MENU_LIST + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORY + " TEXT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_SALES + " TEXT, "
            + COLUMN_PRICE + " TEXT, "
            + COLUMN_IMG + " INTEGER)";

    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version);
        this.databaseName = databaseName;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MENU_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_LIST);
        onCreate(db);
    }

    // 插入示例数据（可根据需要修改）
    public void insertDish(Dish dish) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, dish.getCategory());
        values.put(COLUMN_NAME, dish.getName());
        values.put(COLUMN_SALES, dish.getSales());
        values.put(COLUMN_PRICE, dish.getPrice());
        values.put(COLUMN_IMG, dish.getImg());
        db.insert(TABLE_MENU_LIST, null, values);
    }

    // 生成测试数据
    public void generateTestData(int n) {
        Random random = new Random();
        String[] names = {"爆款*肥牛鱼豆腐骨肉相连三荤五素一份米饭", "豪华双人套餐", "素菜主义一人套餐", "精选牛排套餐", "海鲜大拼盘"};
        String[] sales = {"月售520 好评度80%", "月售300 好评度90%", "月售450 好评度85%"};
        String[] prices = {"23", "41", "35", "50", "28"};
        int[] imgMustIds = {R.drawable.must_buy_one, R.drawable.must_buy_two, R.drawable.must_buy_three};
        int[] imgRecomIds = {R.drawable.recom_one, R.drawable.recom_two, R.drawable.recom_three};

        for (int i = 0; i < n; i++) {
            String category = random.nextBoolean() ? "1" : "2";
            String name = names[random.nextInt(names.length)];
            String sale = sales[random.nextInt(sales.length)];
            String price = "¥" + prices[random.nextInt(prices.length)];
            int imgRes;
            if (category.equals("1")) {
                imgRes = imgMustIds[random.nextInt(imgMustIds.length)];
            }
            else {
                imgRes = imgMustIds[random.nextInt(imgRecomIds.length)];
            }
            insertDish(new Dish(category, name, sale, price, imgRes));
        }
    }

    // 根据类别查询菜品列表
    public List<Dish> getDishesByCategory(String category) {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_NAME, COLUMN_SALES, COLUMN_PRICE, COLUMN_IMG};
        String selection = COLUMN_CATEGORY + " = ?";
        String[] selectionArgs = {category};

        Cursor cursor = db.query(TABLE_MENU_LIST, columns, selection, selectionArgs,
                null, null, null);

        while (cursor.moveToNext()) {
            Dish dish = new Dish();
            dish.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            dish.setSales(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SALES)));
            dish.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
            dish.setImg(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMG)));
            dishes.add(dish);
        }
        cursor.close();
        return dishes;
    }
}