package com.tom.patientservice.pay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.tom.patientservice.R;
import com.tom.patientservice.pay.util.OrderInfoUtil2_0;

import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class PayActivity extends AppCompatActivity {

    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2021000116690703";
    private static final int RESULT_OK = 999;

    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "";

    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";


    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCUU6SupGrQCbj6Ds2lCUslUZMxi+jSkqYpKItEoWFvCBjT2Kw6MqnRrnOIspOSX0J/XWT3M4x8PSbNxkekd7l5hyhEMI1CRknMkiAFMbl5EO2gSddqjXxQwoycYru4l81O6DlMWrnfPpgVONl6P/WaBjfjg2yJQA+LKTHfaCumka13Bf8Q65PTl1FAStxZal0c3oyRASUuFnc8+A36tp7CHT5oa7HHmQvPWFsI5LJuNBL0VR8j+e1PVpaeh4SrShAl6u9JKaYiPSypgT2uU9SHJ65hUNIRX4plZmEzLBeRs36WbQvllinOuZ3dFRqlVRtl61MJG7am45z4Xe93luzbAgMBAAECggEAEqkXPdV3jqa6e9Mi8hFiP9ascv5MEznZ57GvG7pF3McjAHvw5v16ueN9q4Pz5uVPI2Dnk+YLVZIH8LlyR/G8BtsHKNUG4IChRjhYCO9Bi0ai7ph7nm8gZbSkJgSR415NuRCkLMP5QGnKjljxm1yylGcV3uyo2feSPQq4uIeNfk4CHCLwv5cGlmT6WMFVp09CjQxrb7kRb5jQLqNjtMHvYUhKKwJhpMCARUFMMaWUl9DTgXtoqqZaeJ40ZaiWeBDt+aXQEtu3nCeS2Buy4PqcTipn+jRuAoP/EttAvqQS9ZsT8grdtein+YU6lUcOmUE2R6xK1Rz/5sms4KrXNVfaqQKBgQDdTOwH7Qc/JVz9d6opL2Tw2B/iyGqslMEZXmi0zt7dMUGX0M4/K50FeZrzwIq69nqbyYhkD+mRBqRdxvAWufdOIHHT06JOaF0o12bVt5GiytVKgalirBZe31M6kMJJBaVCWJdUozynohGfnXph2fjO2WL3cd1GABnTYjYEwHM3bQKBgQCrlYeia/bEuUt5dF7NCgMhLqOC3+PXL1rmeLN6k1c1X1BZtJEZ1fLdU5xiYOsVM09sOnN9sUPWDzv8jIvYHEB3+3tZ+J/jU6/LsZvhn9Y+QWBTpaRC71xu56btEply1oKfETEpxnu6bhIFzwgMteFj1uClUeKoi+mvbrt3VMYgZwKBgQCfj3fbmx6PfAwZQW+yNFbuhJYw+NpZRwQXm4eZXlM4XuxxXv9mcrbLnMuJlrkwZskujFfH9eUtSQG4D9lKOZnDfgc0eTJcoEkTSoP98laSbvbsuqJ736Jdk146Kue6qNRoCuJKB0yTfXqBgJUd+oC3++usiC/H6gi07eoO8Qfh3QKBgEgtLlD0eWlnSIhem0JE4+whYka7R2xNTz+Dpnv5XsXkwjf57M+hX/gaI75+ZmUUoGQ2cj59QrvYzLL/rBPMTpIqB0lhfGBz448btybBxa2Kcqq9EsvVRFyitWUkXcgg0/F1meioxYM2qf4niItQNu94hCcGuwGLWVGiktkvn+ghAoGBALNdpa47q+9ARqHECBiNzT0SBw5zwOI8++R0cgasFYYVyXxPUjWdIMKk5+t0GP/AEbMLUMxWnIwP453bCVb15yU1uv5MHJjMNreJzUjf8n63rCI2awJqFddmOPIymQMy2tYG1O1x3jAhSIfDr8wYxlKMjLXreMSPB8ACYqTPNidg";
    public static final String RSA_PRIVATE = "";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    boolean result;
    String amount;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Intent intent=getIntent();
                        intent.putExtra("amount",amount);
                        setResult(RESULT_OK,intent);
                        finish();

                    } else {
                        new SweetAlertDialog(returnThis(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("支付失败")
                                .setContentText("请返回确认")
                                .show();
                        result=false;
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(PayActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(PayActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);//沙箱环境需要的代码
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_main);
        View root=this.getCurrentFocus();
        EditText editText=findViewById(R.id.pay_input);
        Button confirmButton=findViewById(R.id.pay_confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount=editText.getText().toString();
                payV2(root,amount);

                //finish();
            }
        });

    }


    public void payV2(View v,String amount) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }


        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,"",amount);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;


                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝账户授权业务示例
     */
    public void authV2(View v) {
        if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
                || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
                || TextUtils.isEmpty(TARGET_ID)) {
            showAlert(this, getString(R.string.error_auth_missing_partner_appid_rsa_private_target_id));
            return;
        }


        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(PayActivity.this);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_AUTH_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    /**
     * 获取支付宝 SDK 版本号。
     */
    public void showSdkVersion(View v) {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        showAlert(this, getString(R.string.alipay_sdk_version_is) + version);
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static void showToast(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            sb.append(key).append("=>").append(bundle.get(key)).append("\n");
        }
        return sb.toString();
    }

    private Activity returnThis(){
        return this;
    }



}
