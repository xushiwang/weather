package com.example.a69430;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    static String url = "https://free-api.heweather.net/s6/weather/";
    String type="now";
    String type1="lifestyle";
    String city= "beijing";
    String shuju;
    static String key="f453fcfc85204d079f3b7528ae82c61f";
    String[] str = new String[]{"beijing","tianjin","shanghai"};
    JavaBean data = new JavaBean();
    static SwipeRefreshLayout swipeRefresh;
    private boolean isRefresh = false;//是否刷新中
    TextView tx,tx1,tx2,tx3,tx4,tx5,tx6;
    EditText et;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tx = findViewById(R.id.id);
        et = findViewById(R.id.et);
        btn = findViewById(R.id.btnn);
        tx1 = findViewById(R.id.id1);
        tx2 = findViewById(R.id.id2);
        tx3 = findViewById(R.id.id3);
        tx4 = findViewById(R.id.id4);
        tx5 = findViewById(R.id.id5);
        setTitle("小天气——实时天气查询");
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefresh.setDistanceToTriggerSync(300);//下拉距离
        swipeRefresh.setOnRefreshListener(this);
        init("beijing");
    }

    private void init(final String city) {
        final String[] comf = new String[1];
        new Thread(new Runnable() {
            @Override
            public void run() {
                String qurl = url+type+"?location="+city+"&key="+key;
                OkHttpClient okHttpClient = new OkHttpClient();
                //服务器返回的地址
                Request request = new Request.Builder()
                        .url(qurl).build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    //获取到数据
                    shuju = response.body().string();
                    //把数据传入解析josn数据方法
                    Log.e("asd",shuju);
                    new jsonJX(shuju,data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(data.getStatus().equals("ok")) {
                                tx.setText("服务器状态：" + data.getStatus()+"\n更新时间："+data.getLoc());
                                tx1.setText(data.getFl()+"℃");
                                tx2.setText(data.getCond_txt()+"\n\n能见度："+data.getVis());
                                tx3.setText("城市："+data.getLocation());
                                tx4.setText("风向："+data.getWind_dir()+"\n风力："+data.getWind_sc()+"\n风速："+
                                        data.getWind_spd()+"公里/小时\n降水量："+data.getPcpn()
                                        +"\n大气压强："+data.getPres()+"\n云量："+data.getCloud());
                            }
                            if(shuju.equals("{\"HeWeather6\":[{\"status\":\"unknown location\"}]}"))
                            {
                                Toast.makeText(MainActivity.this, "查询失败，请检查输入", Toast.LENGTH_SHORT).show();
                            }
                            swipeRefresh.setRefreshing(false);
                        }
                    });
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onRefresh() {
        init(city);
    }
    public void btn(View view){
        city=et.getText().toString();
        init(city);
    }
}
