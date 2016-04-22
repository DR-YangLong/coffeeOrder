package io.github.dr_yanglong.coffeeorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Integer a_score = 0;
    private Integer b_score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 显示分数
     *
     * @param score 分数
     * @param id    view的id
     */
    private void displayScore(int score, int id) {
        TextView view = (TextView) findViewById(id);
        view.setText(String.valueOf(score));
    }

    /**
     * 得分
     *
     * @param view 调用此方法的视图控件
     */
    public void plusScore(View view) {
        //获取到调用此回调函数对应的View的id
        String id = view.getResources().getResourceName(view.getId());
        if (id != null && !"".equals(id)) {
            id=id.substring(id.lastIndexOf("id/")+3,id.length());
            int score = 0;
            if (id.contains("1")) {
                score += 1;
            } else if (id.contains("2")) {
                score += 2;
            } else {
                score += 3;
            }
            //如果是team a计分
            if (id.contains("a")) {
                a_score += score;
                displayScore(a_score, R.id.a_score);
            }
            //如果是team b计分
            if (id.contains("b")) {
                b_score += score;
                displayScore(b_score, R.id.b_score);
            }
        }
    }

    /**
     * 重置得分
     *
     * @param view 重置按钮
     */
    public void reset(View view) {
        a_score = 0;
        b_score = 0;
        displayScore(a_score, R.id.a_score);
        displayScore(b_score, R.id.b_score);
    }
}
