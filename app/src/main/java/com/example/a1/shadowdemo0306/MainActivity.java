package com.example.a1.shadowdemo0306;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    /**
     * 应用的gridView
     */
    private GridView appGrid;
    /**
     * 界面左边的各种信息listView
     *
     */
    private ListView appWallListView;
    /**
     * 自定义动画类
     */
    ScaleAnimEffect animEffect = new ScaleAnimEffect();
    /**
     * 应用右侧的适配器
     */
    AppWallBaseAdapter appWallBaseAdapter;
    /**
     * 当前选择的item位置
     */
    int selected = -1;
    /**
     * 上一次选择的item位置
     */
    int last = -1;
    /**
     * 是否onItemSelected方法执行了
     */
    boolean isSelect = false;
    /**
     * 界面左边上下的箭头图片
     */
    ImageView top_image, bottom_iamge;
    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_app_wall);
        initWidgets();
        appRightGridShow();
        appLeftListViewShow();
        appWallListView.requestFocus();

    }
    private void initWidgets() {
        top_image = (ImageView) findViewById(R.id.top);
        bottom_iamge = (ImageView) findViewById(R.id.bottom);
        appGrid = (GridView) findViewById(R.id.app_wall);
    }
    /**
     * TV应用右侧展示界面
     */
    private void appRightGridShow() {
        appWallBaseAdapter = new AppWallBaseAdapter(this);
        appGrid.setAdapter(appWallBaseAdapter);
        appGrid.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                isSelect = true;
                last = selected;
                selected = position;
                appWallBaseAdapter.notifyDataSetChanged();
                appGrid.smoothScrollToPositionFromTop(position, 300);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("tag", "");
            }
        });
        appGrid.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 如果获取到焦点
                    // 开启线程等待50ms，看是否setOnItemSelectedListener执行，没有执行则执行此方法
                    new Thread(run).start();
                } else {
                    last = selected;
                    Log.i("tag", last + "onfoceus 出来的这里是几..............");
                    selected = -1;
                    appWallBaseAdapter.notifyDataSetChanged();
                    isSelect = false;
                }
            }
        });
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(50);
                handler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (isSelect) {
                isSelect = false;
            } else {
                // 如果是第一次进入该gridView，则进入第一个item，如果不是第一次进去，则选择上次出来的item
                if (last == -1) {
                    selected = 0;
                } else {
                    selected = last;
                }
                last = -1;
                appWallBaseAdapter.notifyDataSetChanged();
            }
        };
    };

    class AppWallBaseAdapter extends BaseAdapter {
        private Context context;
        public AppWallBaseAdapter(Context context) {
            this.context = context;
        }
        @Override
        public int getCount() {
            return 25;
        }
        @Override
        public Object getItem(int position) {

            return null;
        }
        @Override
        public long getItemId(int position) {

            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_app_wall2, parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView
                        .findViewById(R.id.app_icon);
                holder.name = (TextView) convertView
                        .findViewById(R.id.app_name);
                holder.size = (TextView) convertView
                        .findViewById(R.id.app_size);
                holder.count = (TextView) convertView
                        .findViewById(R.id.app_count);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText("第"+getFileIndex(position)+"TV应用");
            if (selected == position) {
                // 如果选中的是当前item，则将当前item变大。
                convertView.bringToFront();
                animEffect.setAttributs(1.0F, 1.06F, 1.0F, 1.06F, 100L);
                Animation localAnimation = animEffect.createAnimation();
                convertView.startAnimation(localAnimation);
            }
            if (last == position) {
                // 将上一个选中的item恢复原样。
                animEffect.setAttributs(1.06F, 1.0F, 1.06F, 1.0F, 0L);
                convertView.startAnimation(animEffect.createAnimation());
            }
            return convertView;
        }
        class ViewHolder {
            ImageView icon, grade;
            TextView name, size, count;
        }
    }
    /**
     * 应用左边展示
     */
    private void appLeftListViewShow() {
        for (int i = 0; i < 10; i++) {
            list.add("网" + i);
        }
        if (list.size() <= 10) {
            bottom_iamge.setVisibility(View.INVISIBLE);
        }
        appWallListView = (ListView) findViewById(R.id.app_wall_listview);
        appWallListView.setAdapter(new AppWallListBaseAdapter(this, list));

        appWallListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            }
        });
        appWallListView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position > 4) {
                    top_image.setVisibility(View.VISIBLE);
                }
                if (position == list.size() - 1) {
                    bottom_iamge.setVisibility(View.INVISIBLE);
                }
                if (position == 0) {
                    top_image.setVisibility(View.INVISIBLE);
                }

                if (position < list.size() - 5) {
                    bottom_iamge.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //为每一个节目台添加数字
    private String getFileIndex(int position) {
        position++;
        String index;
        if (position < 10) {
            index = "0" + position + "  ";
        } else {
            index = position + "   ";
        }
        return index;
    }
}
