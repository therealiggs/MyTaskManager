package spbu.apmath.mytaskmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    protected NotificationManager nm;
    Button buttonStuff;
    protected static final String FILE_NAME = "dates.txt";
    private static final int NOTIFICATION_ID = 127;
    CalendarView calendarView;
    TextView textDate;
    EditText editText;
    EditText editTime;
    long time_millis = 0;
    ArrayList<CountDownTimer> timers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        calendarView = findViewById(R.id.calendarView);
        textDate = findViewById(R.id.textDate);
        editText = findViewById(R.id.editText);
        editTime = findViewById(R.id.editTime);
        buttonStuff = findViewById(R.id.buttonStuff);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = (dayOfMonth) + "/" + (month) + "/" + (year);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                textDate.setText(date);
                time_millis = calendar.getTimeInMillis() - System.currentTimeMillis();


            }
        });



    }

    public void openAllEvents(View v) {
        Intent intent = new Intent(this, EventsActivity.class );
        startActivity(intent);
    }

    public void openStuff(View v){
        Intent intent = new Intent(this, StuffActivity.class);
        startActivity(intent);
    }


    public void save(final View v) {
        String date = textDate.getText().toString();
        final String note = editText.getText().toString();
        String time = editTime.getText().toString();

        Calendar calendar = Calendar.getInstance();

        if (!time.equals("")){

            time_millis += (parseInt(time.substring(0,2)) - calendar.get(Calendar.HOUR_OF_DAY))*3600*1000 + (parseInt(time.substring(3)) -  calendar.get(Calendar.MINUTE))*60*1000 ;
        }

        System.out.println(time_millis/1000);

        if (!date.equals("Activity Date")) {
            FileOutputStream fos = null;
                try {
                    fos = openFileOutput(FILE_NAME, MODE_APPEND);

                    fos.write((date + "-" + note + "+" + time + "\n").getBytes());
                    Toast.makeText(this, "Your date is saved",
                            Toast.LENGTH_LONG).show();

                    CountDownTimer timer =  new CountDownTimer (time_millis, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            showNotification(v, note);
                        }

                    };

                    timer.start();
                    timers.add(timer);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
        else{
            Toast.makeText(this, "No date was selected",
                    Toast.LENGTH_LONG).show();
        }

        editText.setText("", TextView.BufferType.EDITABLE);
    }

    public void erase(View v){
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);

            fos.write("".getBytes());
            Toast.makeText(this, "All dates erased",
                    Toast.LENGTH_LONG).show();

            for (CountDownTimer timer : timers){
                timer.cancel();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void showNotification(View view, String text){

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MY_ID", "My channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getApplicationContext(), StuffActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "MY_ID")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntent)
                        .setTicker("Пора")
                        .setShowWhen(true)
                        .setAutoCancel(true)
                        .setWhen(1)
                        .setContentTitle(text);


        Notification notification = builder.build();
        nm.notify(NOTIFICATION_ID,  notification);
    }



}




