package com.end.summer.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.end.summer.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;
import java.util.Random;

public class PayActivity extends BaseActivity {
    private int w=800,h=800;
    private TextView info_er;
    private ImageView er_code;
    private boolean is=true;
    private String money;
    private Random random;
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            er_code.setImageBitmap(createQRCodeImage(money+random.nextInt()));
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_layout);
        setText("违章支付");
        inView();
        setListener();
        er_code.setImageBitmap(createQRCodeImage(money));
        startThread();
    }
    private void startThread(){
        new Thread(){
            @Override
            public void run() {
                while (is){
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    private void setListener(){
        er_code.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                info_er.setText(money);
                return false;
            }
        });
    }
    private void inView(){
        info_er=findViewById(R.id.info_er);
        er_code=findViewById(R.id.er_code);
        money="违章支付罚款："+getIntent().getStringExtra("money")+"元";
        random=new Random();
    }

    public Bitmap createQRCodeImage(String url) {
        Bitmap bitmap=null;
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();/**哈希表<编码提示类型,串>*/
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");/**字符集*/
            //图像数据转换，使用了矩阵转换
            /**位矩阵                  ????           编码        条形码格式    二维码    */
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
            int[] pixels = new int[w * h];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * w + x] = 0xff000000;
                    } else {
                        pixels[y * w + x] = 0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
            //显示到我们的ImageView上面
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPause() {
        super.onPause();
        is=false;
    }
}
