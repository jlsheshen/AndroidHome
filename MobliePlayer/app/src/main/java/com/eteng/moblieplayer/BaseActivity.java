package com.eteng.moblieplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by gch on 2016/7/5.
 *
 */
@ContentView(R.layout.activity_base)
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.image_left)
    private ImageView image_left;
    @ViewInject(R.id.image_right)
    private ImageView image_right;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ll_content)
    private LinearLayout ll_content;
    @ViewInject(R.id.tabtitle_layout)
    private FrameLayout tabframe_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        image_left.setOnClickListener(this);
        image_right.setOnClickListener(this);
        View content = setcontent();
        if (content != null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            ll_content.addView(content,params);
        }
    }

    //左边点击
    public abstract void leftonclick();
    //右边点击
    public abstract void rightonclick();
    //Content布局View
    public abstract View setcontent();

    //设置标题
    public void settabtitle(String title) {
        tv_title.setText(title);
    }

    //设置左边是否可见
    public void setleftvisible(int vis){
       image_left.setVisibility(vis);
    }
    //设置右边是否可见
    public void setrightvisible(int vis){
        image_right.setVisibility(vis);
    }
    //设置标题栏可见状态
    public void settabvisible(int vis){
        tabframe_layout.setVisibility(vis);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_left:
                leftonclick();
                break;
            case R.id.image_right:
                rightonclick();
                break;
        }
    }
}
