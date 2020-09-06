package com.end.summer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.end.summer.AppClient;
import com.end.summer.R;

public class IpSettingActivity extends BaseActivity {
    private Button ok;
    private EditText ip_set4,ip_set1,ip_set3,ip_set2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_layout);
        inView();
        setText("IP设置");
        setListener();
    }
    private void setListener(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip1=ip_set1.getText().toString().trim();
                String ip2=ip_set2.getText().toString().trim();
                String ip3=ip_set3.getText().toString().trim();
                String ip4=ip_set4.getText().toString().trim();
                String ipAddress=ip1+"."+ip2+"."+ip3+"."+ip4;
                if (ip1.equals("")||ip2.equals("")||ip3.equals("")||ip4.equals("")){
                    Toast.makeText(IpSettingActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
                }else if (!getIpNum(ipAddress)){
                    Toast.makeText(IpSettingActivity.this, "您输入的有误，请重新输入", Toast.LENGTH_SHORT).show();
                    ip_set1.setText("");
                    ip_set2.setText("");
                    ip_set3.setText("");
                    ip_set4.setText("");
                }else {
                    AppClient.setIpAddress(ipAddress);
                    Toast.makeText(IpSettingActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    private void inView(){
        ok=findViewById(R.id.ok);
        ip_set1=findViewById(R.id.ip_set1);
        ip_set2=findViewById(R.id.ip_set2);
        ip_set3=findViewById(R.id.ip_set3);
        ip_set4=findViewById(R.id.ip_set4);
        if (!AppClient.getIpAddress().equals("")){
            String[] ip=AppClient.getIpAddress().split("\\.");
            ip_set1.setText(ip[0]);
            ip_set2.setText(ip[1]);
            ip_set3.setText(ip[2]);
            ip_set4.setText(ip[3]);
        }
    }
    private boolean getIpNum(String str){
        boolean is=true;
        String[] ip=str.split("\\.");
        if (ip.length==4){
            if (Integer.parseInt(ip[0])>255||ip[0].equals("0")||Integer.parseInt(ip[0])<0){
                is=false;
            }else {
                for (int i=1;i<ip.length;i++){
                    if (Integer.parseInt(ip[i])>255||Integer.parseInt(ip[i])<0){
                        is=false;
                        break;
                    }else {
                        is=true;
                    }
                }
            }

        }else {
            is=false;
        }
        return is;
    }
}
