package io.github.dr_yanglong.coffeeorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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
    private boolean isAdCream = false;
    private boolean isAdChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.coffee_price);
        textView.setText(getResources().getString(R.string.select_tips));
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
        price = DEFAULT_PRICE;
        price += isAdCream ? 2 : 0;
        price += isAdChocolate ? 3 : 0;
        int totalPrice = num * price;
        //生成订单信息
        String msg = this.createOrderSummary(totalPrice);
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //调用email类型的app
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order.");
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        if(intent.resolveActivity(getPackageManager())!=null){//判断唤起的email类型app有没有
            startActivity(intent);
        }
        this.displayPrice(msg);
    }

    /**
     * 显示
     *
     * @param priceMsg 价格信息
     */
    private void displayPrice(String priceMsg) {
        TextView priceView = (TextView) findViewById(R.id.coffee_price);
        priceView.setText(priceMsg);
    }

    /**
     * 封装订单
     *
     * @param price 价格
     * @return 订单详情字符串
     */
    private String createOrderSummary(int price) {
        String name = ((EditText) findViewById(R.id.edit_name_view)).getText().toString();
        String priceMsg = getResources().getString(R.string.name_line) + name;
        priceMsg += "\n"+getResources().getString(R.string.ad_cream_line) + isAdCream;
        priceMsg += "\n"+getResources().getString(R.string.ad_choco_line) + isAdChocolate;
        priceMsg += "\n"+getResources().getString(R.string.detail_num_line) + num;
        priceMsg += "\n"+getResources().getString(R.string.detail_total) + NumberFormat.getCurrencyInstance().format(price);
        priceMsg += "\n"+getResources().getString(R.string.detail_thank_line);
        return priceMsg;
    }

    /**
     * 加料
     *
     * @param view
     */
    public void addCream(View view) {
        CheckBox checkBox = (CheckBox) view;
        String id = view.getResources().getResourceName(view.getId());
        if (id.contains("whipped_cream")) {
            isAdCream = checkBox.isChecked() ? true : false;
        }
        if (id.contains("chocolate")) {
            isAdChocolate = checkBox.isChecked() ? true : false;
        }
    }

    /**
     * 清空输入提示
     *
     * @param view EditText
     */
    public void clearTips(View view) {
        EditText editText = (EditText) view;
        ((EditText) view).setHint(null);
    }
}
