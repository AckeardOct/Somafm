package com.example.atg.somafm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Timer;
import java.util.TimerTask;

public class SomaPlayerActivity extends AppCompatActivity {

    private MusicService musicService;
    private Intent playIntent;
    private boolean musicBound = false;

    private RadioInfo radio;
    private TextView textRadioName;
    private ImageView radioImage;
    private ImageView favoriteImage;
    private Timer timer;
    private TimerTask timerTask;
    private HistoryListAdapter adapter;
    private RecyclerView historyView;

    private static int mId = 1488;


    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicService = binder.getService();
            //pass list
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soma_player);

        textRadioName = (TextView)findViewById(R.id.textRadioName);
        radioImage = (ImageView)findViewById(R.id.radioImage);
        favoriteImage = (ImageView)findViewById(R.id.favoriteImage);
        historyView = (RecyclerView)findViewById(R.id.historyView);
        timer = new Timer();

        Intent intent = getIntent();
        radio = (RadioInfo)intent.getSerializableExtra(RadioListActivity.RADIO_INFO);

        textRadioName.setText(radio.getName());
        radioImage.setImageResource(radio.getIcon());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateHistory();
            }
        }, 0, 1000);

        adapter = new HistoryListAdapter();
        LinearLayoutManager lm = new LinearLayoutManager(this);
        historyView.setLayoutManager(lm);
        historyView.setAdapter(adapter);
        historyView.setHasFixedSize(true);

        if(!radio.isFavorite())
            favoriteImage.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onDestroy() {
        //stopService(playIntent);
        //musicService = null;
        super.onDestroy();
    }

    public void onPlayClick(View v) {
        play();
    }

    public void onStopClick(View v) {
        stop();
    }

    @Override
    public void onBackPressed() {
        timer.cancel();
        super.onBackPressed();


    }

    private void play() {
        stop();
        final RequestQueue queue = Volley.newRequestQueue(SomaPlayerActivity.this);
        queue.add(new StringRequest(Request.Method.GET, radio.getUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String pls = parsePlsLink(response);
                        if(pls.isEmpty())
                            return;

                        queue.add(new StringRequest(Request.Method.GET, pls,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        String mp3 = parsePls(response);
                                        if(mp3.isEmpty())
                                            return;
                                        musicService.play(mp3);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(SomaPlayerActivity.this, "Request Error: " + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SomaPlayerActivity.this, "Request Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }));
    }

    private String parsePlsLink(String response) {
        Document doc = Jsoup.parse(response);
        Elements elements = doc.select("nobr");
        for (Element element : elements) {
            if(element.text().contains("MP3")) {
                String link = element.select("a").attr("href").toString();
                String pls = "http://somafm.com" + link;
                return pls;
            }
        }
        return "";
    }

    private String parsePls(String pls) {
        String arr[] = pls.split("\n");
        for(String row : arr) {
            if(row.contains("File1=")) {
                return row.substring(6);
            }
        }
        return "";
    }

    private void stop() {
        musicService.stop();
    }

    private void updateHistory() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = radio.getUrl() + "songhistory.html";

        queue.add(new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseHistory(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SomaPlayerActivity.this, "Request Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }));
    }

    private void parseHistory(String response) {
        adapter.clearItems();
        adapter.notifyDataSetChanged();

        Document doc = Jsoup.parse(response);
        Elements elements = doc.select("tr").next().next();

        String str = "";
        for(Element item : elements) {
            Elements tds = item.select("td");

            if(tds.size() < 3)
                continue;

            String[] arr = new String[4];
            int j = 0;
            for(Element td : tds) {
                if(j == tds.size() - 1)
                    break;
                arr[j] = td.text();
                j++;
            }

            String time = arr[0];
            String artist = arr[1];
            String song = arr[2];

            if(artist.isEmpty())
                continue;

            HistoryItem historyItem = new HistoryItem(artist, song, time);
            adapter.addItem(historyItem);
            adapter.notifyItemInserted(adapter.getItemCount() - 1);
            str += historyItem.toString() + "\n";
        }
    }

    public void onFavoriteClick(View view) {
        FavoriteFile favoriteFile = new FavoriteFile(this);
        if(radio.isFavorite()) {
            favoriteFile.removeFavorite(radio.getName());
            radio.setFavorite(false);
            favoriteImage.setVisibility(View.INVISIBLE);
        }
        else {
            favoriteFile.setFavorite(radio.getName());
            radio.setFavorite(true);
            favoriteImage.setVisibility(View.VISIBLE);
        }
    }
}
