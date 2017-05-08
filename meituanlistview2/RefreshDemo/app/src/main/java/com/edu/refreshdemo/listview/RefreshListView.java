package com.edu.refreshdemo.listview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.refreshdemo.R;


/**
 * Created by baiyuliang on 15/12/2.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    private static final int REFRESH_DONE = 0;//下拉刷新完成
    private static final int PULL_TO_REFRESH = 1;//下拉中（下拉高度未超出headview高度）
    private static final int RELEASE_TO_REFRESH = 2;//下拉中（下拉高度超出headview高度）
    private static final int REFRESHING = 3;//刷新中
    private static final float REFRESH_RATIO = 2.0f;//下拉系数
    private LinearLayout headerView;//headerView布局
    private int headerViewHeight;//headerView高度
    private int refreshstate;//下拉刷新状态
    private boolean isScrollFirst;//是否滑动到顶部
    private boolean isRefreshable;//是否启用下拉刷新

    private static final int LOAD_DONE = 4;//上拉加载完成
    private static final int PULL_TO_LOAD = 5;//上拉中（上拉高度未超出footerview高度）
    private static final int RELEASE_TO_LOAD = 6;//上拉中（上拉高度超出footerview高度）
    private static final int LOADING = 7;//加载中
    private static final float LOAD_RATIO = 3;//上拉系数
    private LinearLayout footerView;//footerView布局
    private int footerViewHeight;//footerView高度
    private int loadstate;//上拉加载状态
    private boolean isScrollLast;//是否滑动到底部
    private boolean isLoadable;//是否启用上拉加载
    private int totalcount;//item总数量
    private TextView tv_load;//footview布局中显示的文字

    private float startY,//手指落点
            offsetY;//手指滑动的距离

    RefreshBgView bg_view;
    AnimationDrawable bg_viewAnimation;

    //headview中的动画实现
    private RefreshAnimView refreshAnimView;
    private RefreshLoadingView refreshLoadingView;
    private AnimationDrawable loadAnimation;

    //监听接口
    private OnRefreshListener mOnRefreshListener;
    private OnLoadMoreListener mOnLoadMoreListener;

    public RefreshListView(Context context) {
        super(context);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化view
     *
     * @param context
     */
    private void init(Context context) {
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setOnScrollListener(this);

        headerView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listview_head_view, null, false);
        footerView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listview_foot_view, null, false);
        tv_load = (TextView) footerView.findViewById(R.id.tv_load);

        //背景图片
        bg_view= (RefreshBgView) headerView.findViewById(R.id.bg_view);
        bg_view.setBackgroundResource(R.drawable.loading_bg1);
        bg_viewAnimation = (AnimationDrawable) bg_view.getBackground();
        bg_viewAnimation.start();
        //背景动画
        refreshAnimView = (RefreshAnimView) headerView.findViewById(R.id.first_step_view);
        refreshLoadingView= (RefreshLoadingView) headerView.findViewById(R.id.second_step_view);
        refreshLoadingView.setBackgroundResource(R.drawable.anim_refresh);
        loadAnimation = (AnimationDrawable) refreshLoadingView.getBackground();

        measureView(headerView);
        measureView(footerView);

        addHeaderView(headerView);
        addFooterView(footerView);

        headerViewHeight = headerView.getMeasuredHeight();
        footerViewHeight = footerView.getMeasuredHeight();

        headerView.setPadding(0, -headerViewHeight, 0, 0);
        footerView.setPadding(0, 0, 0, -footerViewHeight);

        refreshstate = REFRESH_DONE;
        loadstate = LOAD_DONE;

        isRefreshable = true;
        isLoadable = true;

    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        totalcount = totalItemCount;
        if (firstVisibleItem == 0) {
            isScrollFirst = true;//滑动到顶部
        } else {
            isScrollFirst = false;
        }
        if (firstVisibleItem + visibleItemCount == totalItemCount) {
            isScrollLast = true;//滑动到底部
        } else {
            isScrollLast = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                offsetY = ev.getY() - startY;
                /**
                 * 下拉刷新
                 */
                if (isRefreshable && offsetY > 0 && loadstate == LOAD_DONE && isScrollFirst && refreshstate != REFRESHING) {
                    //offsetY/RATIO表示下拉时headview露出部分的高度，是手指在屏幕上滑动的距离/3,
                    // 结合下面下拉时执行的操作>>headerView.setPadding(0, -(int) (headerViewHeight-headerViewShowHeight), 0, 0)，
                    //offsetY/RATIO表示手指滑动3个像素时，headview露出1个像素，其实就相当于一个滑动灵敏度，
                    // 如果不好理解就把RATIO去掉直接用offsetY，表示手指滑动多少距离，headview就显示多少就好理解了
                    float headerViewShowHeight = offsetY / REFRESH_RATIO;//headview露出的高度
                    float currentProgress = headerViewShowHeight / headerViewHeight;//根据此比例，在滑动时改变动图大小
                    if (currentProgress >= 1) {
                        currentProgress = 1;
                    }
                    //对下拉时的几个状态，我们可以这样理解：
                    // 当state=PULL_TO_REFRESH时，headerViewShowHeight<headerViewHeight,
                    // 当state=RELEASE_TO_REFRESH，headerViewShowHeight>headerViewHeight,
                    switch (refreshstate) {
                        case REFRESH_DONE://刚开始下拉，则将状态置为PULL_TO_REFRESH
                            refreshstate = PULL_TO_REFRESH;
                            break;
                        case PULL_TO_REFRESH:
                            setSelection(0);
                            //当state=PULL_TO_REFRESH时，如果headerViewShowHeight超过了headerViewHeight，那么此时已经达到可刷新状态了，
                            //意思是准备刷新中，如果此时用户松手，则执行刷新操作
                            if (headerViewShowHeight - headerViewHeight >= 0) {
                                refreshstate = RELEASE_TO_REFRESH;
                                changeHeaderByState(refreshstate);
                            }
                            break;
                        case RELEASE_TO_REFRESH:
                            setSelection(0);
                            //当state=RELEASE_TO_REFRESH时，已达到了可刷新状态，但如果用户此时未松手并又向上滑动，
                            //直到headerView缩了回去，那么此时的状态又回到PULL_TO_REFRESH
                            if (headerViewShowHeight - headerViewHeight < 0) {
                                refreshstate = PULL_TO_REFRESH;
                                changeHeaderByState(refreshstate);
                            }
                            break;
                    }

                    //PULL_TO_REFRESH和RELEASE_TO_REFRESH都属于下拉中的状态，因此将根据手指滑动的距离动态去修改headview的paddingTop
                    if (refreshstate == PULL_TO_REFRESH || refreshstate == RELEASE_TO_REFRESH) {
                        headerView.setPadding(0, (int) (headerViewShowHeight - headerViewHeight), 0, 0);
                        refreshAnimView.setCurrentProgress(currentProgress);//绘制headview中的动画
                        refreshAnimView.postInvalidate();
                    }
                }

                /**
                 * 上拉加载更多
                 */
                if (isLoadable && offsetY < 0 && refreshstate == REFRESH_DONE && isScrollLast && loadstate != LOADING) {
                    float footerViewShowHeight = -offsetY / LOAD_RATIO;
                    switch (loadstate) {
                        case LOAD_DONE:
                            loadstate = PULL_TO_LOAD;
                            break;
                        case PULL_TO_LOAD:
                            setSelection(totalcount);
                            if (footerViewShowHeight - footerViewHeight >= 0) {
                                loadstate = RELEASE_TO_LOAD;
                                changeFooterByState(loadstate);
                            }
                            break;
                        case RELEASE_TO_LOAD:
                            setSelection(totalcount);
                            if (footerViewShowHeight - footerViewHeight < 0) {
                                loadstate = PULL_TO_LOAD;
                                changeFooterByState(loadstate);
                            }
                            break;
                    }

                    if (loadstate == PULL_TO_LOAD || loadstate == RELEASE_TO_LOAD) {
                        footerView.setPadding(0, 0, 0, (int) (footerViewShowHeight - footerViewHeight));
                    }

                }
                break;
            case MotionEvent.ACTION_UP:
                /**
                 * 下拉刷新
                 */
                if (isRefreshable) {//只有当启用下拉刷新时触发
                    if (refreshstate == PULL_TO_REFRESH) {
                        refreshstate = REFRESH_DONE;
                        changeHeaderByState(refreshstate);
                    }
                    if (refreshstate == RELEASE_TO_REFRESH) {
                        refreshstate = REFRESHING;
                        changeHeaderByState(refreshstate);
                        mOnRefreshListener.onRefresh();
                    }
                }
                /**
                 * 上拉加载
                 */
                if (isLoadable) {//只有当启用上拉加载时触发
                    if (loadstate == PULL_TO_LOAD) {
                        loadstate = LOAD_DONE;
                        changeFooterByState(loadstate);
                    }
                    if (loadstate == RELEASE_TO_LOAD) {
                        loadstate = LOADING;
                        changeFooterByState(loadstate);
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 改变headview状态
     *
     * @param state
     */
    private void changeHeaderByState(int state) {
        switch (state) {
            case REFRESH_DONE:
                headerView.setPadding(0, -headerViewHeight, 0, 0);
                refreshAnimView.setVisibility(View.VISIBLE);
                refreshLoadingView.setVisibility(View.GONE);
                loadAnimation.stop();
                break;
            case RELEASE_TO_REFRESH:
                Log.e("jj", "RELEASE_TO_REFRESH");
                break;
            case PULL_TO_REFRESH:
                Log.e("jj", "PULL_TO_REFRESH");
                break;
            case REFRESHING:
                refreshAnimView.setVisibility(View.GONE);
                refreshLoadingView.setVisibility(View.VISIBLE);
                loadAnimation.start();
                headerView.setPadding(0, 0, 0, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 改变footerview状态
     *
     * @param loadstate
     */
    private void changeFooterByState(int loadstate) {
        switch (loadstate) {
            case LOAD_DONE:
                footerView.setPadding(0, 0, 0, -footerViewHeight);
                tv_load.setText("上拉加载更多");
                break;
            case RELEASE_TO_LOAD:
                tv_load.setText("松开加载更多");
                break;
            case PULL_TO_LOAD:
                tv_load.setText("上拉加载更多");
                break;
            case LOADING:
                tv_load.setText("正在加载...");
                footerView.setPadding(0, 0, 0, 0);
                break;
            default:
                break;
        }
    }


    /**
     * 下拉刷新监听
     */
    public interface OnRefreshListener {
        void onRefresh();
    }

    /**
     * 加载更多监听
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    /**
     * 设置下拉刷新
     *
     * @param onRefreshListener
     */
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    /**
     * 设置加载更多监听
     *
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    /**
     * 下拉刷新完成
     */
    public void setOnRefreshComplete() {
        refreshstate = REFRESH_DONE;
        changeHeaderByState(refreshstate);
    }

    /**
     * 加载更多完成
     */
    public void setOnLoadMoreComplete() {
        loadstate = LOAD_DONE;
        changeFooterByState(loadstate);
    }

    /**
     * 设置是否启用下拉刷新
     *
     * @param isRefreshable
     */
    public void setIsRefreshable(boolean isRefreshable) {
        this.isRefreshable = isRefreshable;
    }

    /**
     * 设置是否启用加载更多
     *
     * @param isLoadable
     */
    public void setIsLoadable(boolean isLoadable) {
        this.isLoadable = isLoadable;
    }

    /**
     * 计算控件宽高
     *
     * @param child
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

}
