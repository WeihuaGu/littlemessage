package weihuagu.com.lmessage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.telephony.SmsManager;
import android.content.Context;
import android.app.Activity;
import android.util.Log;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import weihuagu.com.lmessage.util.PhoneNumber;

public class MainActivity extends AppCompatActivity {
    private EditText messagetext;
    private EditText sendnum;
    private EditText phonenum;
    private Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.initUiResouces();
    }

    public void initUiResouces(){
        this.send=(Button)findViewById(R.id.send);
        this.messagetext=(EditText)findViewById(R.id.messagetext);
        this.sendnum=(EditText)findViewById(R.id.sendnum);
        this.phonenum=(EditText)findViewById(R.id.phonenum);

    }

    public void sendMeassgeByButton(View v){
        if(messagetext.getText().length()==0)
            return;

        if(phonenum.getText().length()>0){
            if(sendnum.getText().length()>0){
                if(sendnum.getText().toString().startsWith("root")){
                    String numberstr=sendnum.getText().toString().substring(4);
                    int number=Integer.valueOf(numberstr).intValue();
                    Log.v("root mode","num is "+number);
                    for (int j=0;j<number;j++){
                         this.sendMessage(phonenum.getText().toString(),messagetext.getText().toString());
                    }
                    return;
                }

                int num=Integer.valueOf(sendnum.getText().toString()).intValue();
                if(num<5){

                    for (int i=0;i<num;i++){
                        this.sendMessage(phonenum.getText().toString(),messagetext.getText().toString());
                    }
                    return;

                }

            }else {
                this.sendMessage(phonenum.getText().toString(),messagetext.getText().toString());
                return;
            }



        }else{
            if(sendnum.getText().length()>0){
                if(sendnum.getText().toString().startsWith("root")){
                    String numberstr=sendnum.getText().toString().substring(4);
                    int number=Integer.valueOf(numberstr).intValue();
                    Log.v("root mode","num is "+number);
                    for (int j=0;j<number;j++){
                        this.sendMessage(PhoneNumber.getPhonenum(),messagetext.getText().toString());
                    }
                    return;
                }



                int num=Integer.valueOf(sendnum.getText().toString()).intValue();
                if(num<10){

                    for (int i=0;i<num;i++){
                        this.sendMessage(PhoneNumber.getPhonenum(),messagetext.getText().toString());
                    }
                    return;

                }


            }
            else{
                this.sendMessage(PhoneNumber.getPhonenum(),messagetext.getText().toString());
                return;
            }
        }
    }

    private void sendMessage(String number, String message){

        Log.v("start to send:","num is :"+number);
        String SENT = "sms_sent";
        String DELIVERED = "sms_delivered";

        PendingIntent sentPI = PendingIntent.getActivity(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getActivity(this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.i("====>", "Activity.RESULT_OK");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.i("====>", "RESULT_ERROR_GENERIC_FAILURE");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.i("====>", "RESULT_ERROR_NO_SERVICE");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.i("====>", "RESULT_ERROR_NULL_PDU");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.i("====>", "RESULT_ERROR_RADIO_OFF");
                        break;
                }
            }
        }, new IntentFilter(SENT));

        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent){
                switch(getResultCode())
                {
                    case Activity.RESULT_OK:
                        Log.i("====>", "RESULT_OK");
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i("=====>", "RESULT_CANCELED");
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager smsm = SmsManager.getDefault();
        smsm.sendTextMessage(number, null, message, sentPI, deliveredPI);
        Toast.makeText(getApplicationContext(), "发送成功，号码"+number+"内容："+message,
                Toast.LENGTH_SHORT).show();
    }









}
