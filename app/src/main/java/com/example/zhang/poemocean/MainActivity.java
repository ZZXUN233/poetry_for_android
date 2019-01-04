package com.example.zhang.poemocean;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterViewFlipper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhang.poemocean.Adapter.Adapter_filpper;
import com.example.zhang.poemocean.Class.Poem;
import com.example.zhang.poemocean.Db.Db_readed;
import com.example.zhang.poemocean.Helper.MyConfig;
import com.example.zhang.poemocean.Url.Get_Random_one;
import com.example.zhang.poemocean.activity.activity_collections;
import com.example.zhang.poemocean.activity.activity_poemList;
import com.example.zhang.poemocean.activity.activity_poem_detail;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class MainActivity extends Activity
        implements NavigationView.OnNavigationItemSelectedListener {


    @ViewInject(R.id.randomOne)
    private TextView randomOne;

    @ViewInject(R.id.read_logs)
    private ListView lis_readLogs;


    @ViewInject(R.id.poem_flipper)
    private AdapterViewFlipper adapterViewFlipper;
    private static final int[] IMAGES = {R.drawable.flipper_1, R.drawable.flipper_2, R.drawable.flipper_3};
    private int mPosition = -1;

    private MyConfig myConfig;
    private Db_readed readLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, activity_poemList.class));
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        init();
        initLogs();
    }


    private void init() {

        //初始化最近阅读的数据库
        myConfig = new MyConfig(this);

        randomOne.setTypeface(myConfig.getTf());
        new Get_Random_one(randomOne).execute(MyConfig.URL_SINGLE_LINE);
        Adapter_filpper adapter = new Adapter_filpper(this, IMAGES);
        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setFlipInterval(2500);
        adapterViewFlipper.setAutoStart(true);

    }

    private void initLogs() {
        readLog = new Db_readed(this);
        final ArrayList<Poem> readeds = readLog.getAllRecords();
        ArrayList<String> poem_headers = new ArrayList<String>();
        if (readeds.size() == 0) {
            poem_headers.add("还没有最近的阅读记录！你可以点击搜索按钮进行内容索引！");
        }
        for (int i = 0; i < readeds.size(); i++) {
            poem_headers.add(readeds.get(i).getHead());
        }
        ArrayAdapter<String> readed_adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, poem_headers);
        lis_readLogs.setAdapter(readed_adapter);
        lis_readLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("itemClick", id + "Click!");
                Intent mIntent = new Intent(MainActivity.this, activity_poem_detail.class);
                mIntent.putExtra("title", readeds.get((int) id).getTitle());
                mIntent.putExtra("authors", readeds.get((int) id).getAuthors());
                mIntent.putExtra("content", readeds.get((int) id).getContent());
                startActivity(mIntent);
                readLog.close();
            }
        });
    }

    @Event(R.id.reload)
    private void reloadOneLine(View view) {
        init();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initLogs();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        //menu图标设置

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_personal_info) {
            // Handle the camera action
        } else if (id == R.id.nav_collections) {
            startActivity(new Intent(MainActivity.this, activity_collections.class));
        } else if (id == R.id.nav_comments) {

        } else if (id == R.id.nav_settings) {

        }
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
