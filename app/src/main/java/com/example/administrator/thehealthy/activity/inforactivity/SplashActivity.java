package com.example.administrator.thehealthy.activity.inforactivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.administrator.thehealthy.R;
import com.example.administrator.thehealthy.activity.BaseActivity;
import com.example.administrator.thehealthy.activity.MainActivity;
import com.example.administrator.thehealthy.entity.UpdataInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SplashActivity extends BaseActivity {
    private TextView titleText, discripText, versionText;
    private Handler handler;
    Animation animation = null;
    private UpdataInfo info;
    private CheckVersionTask checkVersionTask;
    // 升级程序
    private static final int UPDATA_CLIENT = 0;
    // 服务器超时
    private static final int GET_UNDATINFO_ERROR = 1;
    // 下载失败
    private static final int DOWN_ERROR = 2;
    private long time;

    @Override
    protected int setLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        titleText = findView(R.id.text_splash_title);
        discripText = findView(R.id.text_splash_discrip);
        versionText = findView(R.id.text_version);
        animation = AnimationUtils.loadAnimation(this, R.anim.splash_text);
        checkVersionTask = new CheckVersionTask();
        time = System.currentTimeMillis();

// 注册监听有无网络的广播
//        Intent intent = new Intent();
//        intent.setAction("android.net.conn.CONNECTIVITY_CHANGE");
//        sendBroadcast(intent);
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:
                        Typeface discripType = Typeface.createFromAsset(getAssets(), "fonts/splash_discrip_text_type.ttf");
                        Typeface titleType = Typeface.createFromAsset(getAssets(), "fonts/splash_text_title_type.ttf");
                        titleText.setTypeface(titleType);
                        titleText.setText("公共卫生");
                        discripText.setTypeface(discripType);
                        discripText.setText("让你对健康, 了如指掌");
                        versionText.setTypeface(discripType);
                        versionText.setText("V 2.1");

                        titleText.startAnimation(animation);
                        discripText.startAnimation(animation);
                        versionText.startAnimation(animation);
                        Log.i("Splash", "------->" + titleText.getText().toString());
                        break;
                    case 200:
                        Log.i("Splash", "------->" + "Intent.toString()");
                        LoginMain();
//                        checkVersionTask.run();
//                        finish();

                        break;
                    case UPDATA_CLIENT:
                        // 通知用户升级程序
                        showUpdataDialog();
                        break;
                    case GET_UNDATINFO_ERROR:
                        // 服务器超时
                        Toast.makeText(SplashActivity.this, "获取服务器更新信息失败", 1).show();
                        LoginMain();
                        break;
                    case DOWN_ERROR:
                        // 下载apk失败
                        Toast.makeText(SplashActivity.this, "下载新版本失败", 1).show();
                        LoginMain();
                        break;

                }

                return false;
            }
        });
        handler.sendEmptyMessageDelayed(100, 500);
        handler.sendEmptyMessageDelayed(200, 3500);


    }


    /**
     * 版本匹配，自动安装
     * 从服务器获取xml解析并进行比对版本
     */
    public class CheckVersionTask implements Runnable {
        @Override
        public void run() {
//            try {
//                // 服务器地址
////                String path = "******";
////                URL url = new URL(path);
////                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////                connection.setConnectTimeout(5000);
////                InputStream is = connection.getInputStream();
////                info = getUpdataInfo(is);
////                Log.i("CheckVersion", "版本号相同，无需升级 "+ info.getVersion());
////                if (info.getVersion().equals(getVersionName())) {
////                    Log.i("CheckVersion", "版本号相同，无需升级");
////                    LoginMain();
////                } else {
////                    Log.i("CheckVersion", "版本号不同，提示用户升级");
////
////                    Message msg = new Message();
////                    msg.what = UPDATA_CLIENT;
////                    handler.sendMessage(msg);
////
////                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                // 待处理
//                Message msg = new Message();
//                msg.what = GET_UNDATINFO_ERROR;
//                handler.sendMessage(msg);
//                e.printStackTrace();
//            }
            long temp = System.currentTimeMillis();
            if (temp - time < 3000) {
                SystemClock.sleep(temp - time);
            }
            StringRequest request = new StringRequest("www.baidu.com",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject object = new JSONObject(response);
                                String url = object.getString("updateUrl");
                                String updateMessage = object.getString("updateMessage");
                                int updateVersion = object.getInt("updateVersion");
                                int localVersion = getVersionCode();
                                if (updateVersion > localVersion) {
                                    Message msg = new Message();
                                    msg.what = UPDATA_CLIENT;
                                    handler.sendMessage(msg);
                                } else {
                                    LoginMain();
                                }
                            } catch (PackageManager.NameNotFoundException ignored) {
                            } catch (JSONException e) {
                                Log.e("SplashActivity", "parse json error", e);
                            } catch (Exception e) {
                                Message msg = new Message();
                                msg.what = GET_UNDATINFO_ERROR;
                                handler.sendMessage(msg);
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

        }
    }

    private int getVersionCode() throws Exception {

        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名， 0代表是获取版本信息
        PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packageInfo.versionCode;
    }

    /*
     * 读取服务器版本号
     * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号)
     */

    public static UpdataInfo getUpdataInfo(InputStream is) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        UpdataInfo info = new UpdataInfo();
        parser.setInput(is, "utf-8");// 设置解析的数据源
        int type = parser.getEventType();
        while (type != XmlPullParser.END_DOCUMENT) {
            switch (type) {
                case XmlPullParser.START_TAG:
                    if ("version".equals(parser.getName())) {
                        info.setVersion(parser.nextText());// 获取版本号
                    } else if ("url".equals(parser.getName())) {
                        info.setUrl(parser.nextText());// 获取要升级的APK文件
                    } else if ("description".equals(parser.getName())) {
                        info.setDescription(parser.nextText());// 获取该文件的信息
                    }
                    break;
            }
            type = parser.next();
        }
        return info;
    }

    /**
     * 下载
     */
    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂在在手机上，并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    /*
 *
 * 弹出对话框通知用户更新程序
 *
 * 弹出对话框的步骤：
 *  1.创建alertDialog的builder.
 *  2.要给builder设置属性, 对话框的内容,样式,按钮
 *  3.通过builder 创建一个对话框
 *  4.对话框show()出来
 */
    private void showUpdataDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("升级版本");
        builder.setMessage(info.getDescription());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginMain();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /*
 * 从服务器中下载APK
 */
    private void downLoadApk() {
        final ProgressDialog pd; // 进度条对话框
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();

        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(info.getUrl(), pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss();
                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);

    }

    private void LoginMain() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
