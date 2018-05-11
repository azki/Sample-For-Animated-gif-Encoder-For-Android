package org.azki.mytestapplication;

import android.Manifest;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnPerm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
            }
        });

        final Resources resources = getResources();
        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab1));
                makeGif(bitmaps);
            }
        });
        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab1));
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab2));
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab3));
                makeGif(bitmaps);
            }
        });
        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Bitmap> bitmaps = new ArrayList<>();
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab1));
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab2));
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab3));
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab4));
                bitmaps.add(BitmapFactory.decodeResource(resources, R.raw.rab5));
                makeGif(bitmaps);
            }
        });
    }

    private void makeGif(@NonNull ArrayList<Bitmap> bitmaps) {
        File gifMaker = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "output" + bitmaps.size() + ".gif");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        encoder.start(bos);
        int duration = 300;
        encoder.setDelay(duration);
        encoder.setRepeat(0);

        for (Bitmap bitmap : bitmaps) {
            encoder.addFrame(bitmap);
        }

        encoder.finish();

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(gifMaker);
            outputStream.write(bos.toByteArray());
            outputStream.flush();
            outputStream.close();
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "FileNotFoundException: " + e, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        TextView viewById = findViewById(R.id.tempTextView);
        viewById.setText(gifMaker.getAbsolutePath());
    }
}
