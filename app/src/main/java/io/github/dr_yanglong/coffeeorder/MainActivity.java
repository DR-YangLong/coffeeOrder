package io.github.dr_yanglong.coffeeorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    /**
     * 初始咖啡数量
     */
    private Integer num = 0;
    /**
     * 默认咖啡单价
     */
    private static final int DEFAULT_PRICE = 6;
    /**
     * 咖啡价格
     */
    private static int price = DEFAULT_PRICE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.coffee_price);
        textView.setText("Please select coffee num!");
    }

    /**
     * 将coffee数量显示在view中
     *
     * @param num 数量
     */
    private void displayNum(int num) {
        TextView numView = (TextView) findViewById(R.id.coffee_num);
        numView.setText(num + "");
    }

    /**
     * 减少咖啡数量
     */
    public void minusCoffee(View view) {
        synchronized (num) {
            num = --num > 0 ? num : 0;
            this.displayNum(num);
        }
    }

    /**
     * 增加咖啡数量
     */
    public void plusCoffee(View view) {
        synchronized (num) {
            num = ++num >= Integer.MAX_VALUE ? Integer.MAX_VALUE : num;
            this.displayNum(num);
        }
    }

    /*
     *下单，计算总价
     */
    public void order(View view) {
        int totalPrice = num * price;
        this.displayPrice(totalPrice);
    }

    /**
     * 显示价格
     *
     * @param price 价格
     */
    private void displayPrice(int price) {
        TextView priceView = (TextView) findViewById(R.id.coffee_price);
        priceView.setText("Total: "+NumberFormat.getCurrencyInstance().format(price)+"\nTank You!");
    }
}
