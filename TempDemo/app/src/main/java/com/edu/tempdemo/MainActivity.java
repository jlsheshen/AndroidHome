package com.edu.tempdemo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView textView ;
    StartCustomTextView sTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.tv);
        sTv = (StartCustomTextView) findViewById(R.id.s_tv);
        String s = "阿阿斯顿萨11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111这是介当官的法国队多个地方地方给对方地方地方高度地方多个的绍111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111阿斯达所多阿斯顿按时按时按时按时按时的1";
        textView.setText(s);
//        textView.setText(ToSBC(s));
        sTv.setMText(s);
//        sTv.setLines(1);
    }
    public static String ToDBC(String str) {

        char[] c = str.toCharArray();

        for (int i = 0; i < c.length; i++) {

            if (c[i] == 12288) {

                c[i] = (char) 32;

                continue;

            }

            if (c[i] > 65280 && c[i] < 65375)

                c[i] = (char) (c[i] - 65248);

        }

        return new String(c);

    }
    public static String stringFilter(String str) {

        str = str.replaceAll("【", "[").replaceAll("】", "]")

                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号

        String regEx = "[『』]"; // 清除掉特殊字符

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        return m.replaceAll("").trim();

    }
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    public static String fullToHalf(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i< c.length; i++)
        {
            if (c[i] == 12288) //全角空格
            {
                c[i] = (char) 32;
                continue;
            }

            if (c[i]> 65280&& c[i]< 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }
}
