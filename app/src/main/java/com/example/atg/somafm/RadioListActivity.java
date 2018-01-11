package com.example.atg.somafm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RadioListActivity extends AppCompatActivity {

    public static final String RADIO_INFO = "com.example.atg.somafm.SomaPlayer.radio_info";

    private List<RadioInfo> radioData = new ArrayList<>();



    private RecyclerView reView;
    private RadioListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_list);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setupReView();
    }

    private void setupReView() {
        initData();
        reView = (RecyclerView)findViewById(R.id.reView);
        GridLayoutManager lm = new GridLayoutManager(this, 3);
        reView.setLayoutManager(lm);
        reView.setHasFixedSize(true);

        adapter = new RadioListAdapter(radioData);
        adapter.setOnItemClickListener(new RadioListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RadioInfo info = (RadioInfo)adapter.getItem(position);
                Intent intent = new Intent(RadioListActivity.this, SomaPlayerActivity.class);
                intent.putExtra(RADIO_INFO, info);
                startActivity(intent);
            }
        });

        reView.setAdapter(adapter);
    }

    private void initData() {
        radioData.clear();
        radioData.add(new RadioInfo("Jolly Ol' Soul", R.drawable.jollysoul120, "http://somafm.com/jollysoul/"));
        radioData.add(new RadioInfo("Xmas in Frisko", R.drawable.xmasinfrisco120, "http://somafm.com/xmasinfrisko/"));
        radioData.add(new RadioInfo("Christmas Rocks!", R.drawable.xmasrocks120, "http://somafm.com/xmasrocks/"));
        radioData.add(new RadioInfo("Christmas Lounge", R.drawable.christmaslounge120, "http://somafm.com/christmas/"));
        //radioData.add(new RadioInfo("SF in SF Podcast", R.drawable.sfinsf_400, "http://somafm.com/sfinsf/"));
        radioData.add(new RadioInfo("PopTron", R.drawable.poptron120, "http://somafm.com/poptron/"));
        radioData.add(new RadioInfo("BAGeL Radio", R.drawable.bagel120, "http://somafm.com/bagel/"));
        radioData.add(new RadioInfo("Seven Inch Soul", R.drawable.seven_soul120, "http://somafm.com/7soul/"));
        radioData.add(new RadioInfo("Beat Blender", R.drawable.blender120, "http://somafm.com/beatblender/"));
        radioData.add(new RadioInfo("The Trip", R.drawable.thetrip120, "http://somafm.com/thetrip/"));
        radioData.add(new RadioInfo("cliqhop idm", R.drawable.cliqhop120, "http://somafm.com/cliqhop/"));
        radioData.add(new RadioInfo("Dub Step Beyond", R.drawable.dubstep120, "http://somafm.com/dubstep/"));
        radioData.add(new RadioInfo("ThistleRadio", R.drawable.thistle120, "http://somafm.com/thistle/"));
        radioData.add(new RadioInfo("Folk Forward", R.drawable.folkfwd120, "http://somafm.com/folkfwd/"));
        radioData.add(new RadioInfo("Covers", R.drawable.covers120, "http://somafm.com/covers/"));
        radioData.add(new RadioInfo("Secret Agent", R.drawable.secretagent120, "http://somafm.com/secretagent/"));
        radioData.add(new RadioInfo("Groove Salad", R.drawable.groovesalad120, "http://somafm.com/groovesalad/"));
        radioData.add(new RadioInfo("Drone Zone", R.drawable.dronezone120, "http://somafm.com/dronezone/"));
        radioData.add(new RadioInfo("Fluid", R.drawable.fluid120, "http://somafm.com/fluid/"));
        radioData.add(new RadioInfo("Lush", R.drawable.lush_x120, "http://somafm.com/lush/"));
        radioData.add(new RadioInfo("Illinois Street Lounge", R.drawable.illstreet, "http://somafm.com/illstreet/"));
        radioData.add(new RadioInfo("Indie Pop Rocks!", R.drawable.indychick, "http://somafm.com/indiepop/"));
        radioData.add(new RadioInfo("Left Coast 70s", R.drawable.seventies120, "http://somafm.com/seventies/"));
        radioData.add(new RadioInfo("Underground 80s", R.drawable.u80s_120, "http://somafm.com/u80s/"));
        radioData.add(new RadioInfo("Boot Liquor", R.drawable.bootliquor120, "http://somafm.com/bootliquor/"));
        radioData.add(new RadioInfo("Digitalis", R.drawable.digitalis120, "http://somafm.com/digitalis/"));
        radioData.add(new RadioInfo("Metal Detector", R.drawable.metal120, "http://somafm.com/metal/"));
        radioData.add(new RadioInfo("Mission Control", R.drawable.missioncontrol120, "http://somafm.com/missioncontrol/"));
        radioData.add(new RadioInfo("SF 10-33", R.drawable.sf1033120, "http://somafm.com/sf1033/"));
        radioData.add(new RadioInfo("Deep Space One", R.drawable.deepspaceone120, "http://somafm.com/deepspaceone/"));
        radioData.add(new RadioInfo("Space Station Soma", R.drawable.sss, "http://somafm.com/spacestation/"));
        radioData.add(new RadioInfo("Sonic Universe", R.drawable.sonicuniverse120, "http://somafm.com/sonicuniverse/"));
        radioData.add(new RadioInfo("Suburbs of Goa", R.drawable.sog120, "http://somafm.com/suburbsofgoa/"));
        radioData.add(new RadioInfo("Black Rock FM", R.drawable.brfm, "http://somafm.com/brfm/"));
        radioData.add(new RadioInfo("DEF CON Radio", R.drawable.defcon120, "http://somafm.com/defcon/"));
        radioData.add(new RadioInfo("Earwaves", R.drawable.earwaves120, "http://somafm.com/earwaves/"));
        radioData.add(new RadioInfo("The Silent Channel", R.drawable.silent120, "http://somafm.com/silent/"));

        FavoriteFile ff = new FavoriteFile(RadioListActivity.this);
        for(RadioInfo info : radioData) {
            if(ff.isFavorite(info.getName()))
                info.setFavorite(true);
        }

        // sort
        Collections.sort(radioData, new Comparator<RadioInfo>() {
            @Override
            public int compare(RadioInfo radioInfo, RadioInfo t1) {
                int one = radioData.indexOf(radioInfo);
                int two = radioData.indexOf(t1);

                if(!radioInfo.isFavorite())
                    one += radioData.size();
                if(!t1.isFavorite())
                    two += radioData.size();

                return one - two;
            }
        });
    }
}
