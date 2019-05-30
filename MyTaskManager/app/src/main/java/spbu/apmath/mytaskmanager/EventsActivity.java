package spbu.apmath.mytaskmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        FileInputStream fis = null;
        ArrayList<SimpleEvent> eventList = new ArrayList<>();
        int counter = 0;

        try {

            fis = openFileInput(MainActivity.FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            String text = "";


            while ((line = br.readLine()) != null) {
                text = line;
                System.out.println(text);
                String date = text.substring(0, text.indexOf("-"));
                String note = text.substring(text.indexOf("-") + 1, text.indexOf("+"));
                String time = text.substring(text.indexOf("+") + 1);

                if (!time.equals("")) {
                    time = " at " + time;
                }

                eventList.add(new SimpleEvent(date + time, note));
                counter ++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10 - counter; i++){
            eventList.add(new SimpleEvent("dd/mm/yyyy", "Event description"));
        }

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SimpleEventAdapter(eventList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

