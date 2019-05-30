package spbu.apmath.mytaskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StuffActivity extends AppCompatActivity {
    protected static final String STUFF_FILE_NAME = "Stuff.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stuff);
        FileInputStream fis = null;
        EditText editTextStuff = findViewById(R.id.editTextStuff);

        try {

            fis = openFileInput(STUFF_FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;
            String text = "";


            while ((line = br.readLine()) != null) {
                text += line;

            }

            editTextStuff.setText(text);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void save(View v) {
        EditText editTextStuff = findViewById(R.id.editTextStuff);
        String stuff = editTextStuff.getText().toString();

            FileOutputStream fos = null;
            try {
                fos = openFileOutput(STUFF_FILE_NAME, MODE_PRIVATE);

                fos.write((stuff).getBytes());
                Toast.makeText(this, "Your stuff is saved",
                        Toast.LENGTH_LONG).show();

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

    }


