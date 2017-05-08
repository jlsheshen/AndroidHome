package com.edu.refreshdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.edu.refreshdemo.dialog.MyProgressDialog;
import com.edu.refreshdemo.listview.RefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity implements RefreshListView.OnRefreshListener,RefreshListView.OnLoadMoreListener {
    private RefreshListView mListView;
    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;
    private final static int REFRESH_COMPLETE = 0;
    private final static int LOAD_COMPLETE = 1;

    MyProgressDialog pb;

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mListView.setOnRefreshComplete();
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(0);
                    break;
                case LOAD_COMPLETE:
                    mListView.setOnLoadMoreComplete();
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(mDatas.size());
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb=new MyProgressDialog(this);
        pb.setCancelable(false);
        pb.setMessage("正在加载数据...");
        pb.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView = (RefreshListView) findViewById(R.id.listview);
                String[] data = new String[]{"仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新",
                        "仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新",
                        "仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新", "仿美团下拉刷新",};
                mDatas = new ArrayList<String>(Arrays.asList(data));
                mAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, mDatas);
                mListView.setAdapter(mAdapter);
                mListView.setOnRefreshListener(MainActivity.this);
                mListView.setOnLoadMoreListener(MainActivity.this);
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(MainActivity.this,"position="+position, Toast.LENGTH_SHORT).show();
                    }
                });
                pb.dismiss();
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mDatas.add(0, "new仿美团下拉刷新");
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onLoadMore() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mDatas.add("more仿美团下拉刷新");
                    mHandler.sendEmptyMessage(LOAD_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
