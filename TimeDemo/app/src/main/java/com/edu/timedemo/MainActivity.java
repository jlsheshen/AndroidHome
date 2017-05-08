package com.edu.timedemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView timeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeTv = (TextView) findViewById(R.id.tv);
        CountDownTask timeTask = new CountDownTask();

        timeTask.execute(3600000);
    }

    class CountDownTask extends AsyncTask<Integer, 	String, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            int time= params[0];//拿到了从0到穿进来的个数
            SimpleDateFormat sdf= new SimpleDateFormat("mm:ss");


            // TODO Auto-generated method stub

            while(time>1){
                try {
                    Thread.sleep(1000);

                    java.util.Date dt = new Date(time);
                    Log.d("CountDownTask", sdf.format(dt));
                    publishProgress( sdf.format(dt));
                    time = time -1000;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }



            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            timeTv.setText(values[0]);

        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }



    }

}
