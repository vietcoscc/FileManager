package com.example.vaio.filemanager;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.t3h.adapter.ListViewDrawerAdapter;
import com.t3h.fragment.FragmentDocument;
import com.t3h.fragment.FragmentDownLoad;
import com.t3h.fragment.FragmentImage;
import com.t3h.fragment.FragmentMain;
import com.t3h.fragment.FragmentMusic;
import com.t3h.fragment.FragmentVideo;
import com.t3h.model.ItemViewDrawer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int REQUEST_READWRITE_STORAGE = 1;
    public static final int FOLDER = R.drawable.folder;
    public static final int FILE = R.drawable.file;
    private DrawerLayout drawerLayout;
    private ListView lvDrawer;
    private ActionBarDrawerToggle toggle;
    private ArrayList<ItemViewDrawer> arrItemViewDrawer = new ArrayList<>();
    private final int iv[] = {R.drawable.image, R.drawable.video, R.drawable.music, R.drawable.document, R.drawable.download, R.drawable.storage};
    private final String tv[] = {"Image", "Video", "Music", "Document", "Download", "Internal Storage"};
    private ListViewDrawerAdapter listViewDrawerAdapter;


    private FragmentMain fragmentMain = new FragmentMain();
    private FragmentImage fragmentImage = new FragmentImage();
    private FragmentVideo fragmentVideo = new FragmentVideo();
    private FragmentMusic fragmentMusic = new FragmentMusic();
    private FragmentDocument fragmentDocument = new FragmentDocument();
    private FragmentDownLoad fragmentDownLoad = new FragmentDownLoad();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READWRITE_STORAGE);
        }

        initData();
        initViews();
    }

    private void initData() {

        lvDrawer = (ListView) findViewById(R.id.lv_drawer);
        lvDrawer.setAdapter(listViewDrawerAdapter);
        lvDrawer.setOnItemClickListener(this);

        for (int i = 0; i < iv.length; i++) {
            arrItemViewDrawer.add(new ItemViewDrawer(iv[i], tv[i]));
        }
        listViewDrawerAdapter = new ListViewDrawerAdapter(this, arrItemViewDrawer);
        lvDrawer.setAdapter(listViewDrawerAdapter);
    }

    private void initViews() {

        addFragment(fragmentMain);
        hideFragment();
        addFragment(fragmentImage);
        addFragment(fragmentVideo);
        addFragment(fragmentMusic);
        addFragment(fragmentDocument);
        addFragment(fragmentDownLoad);
        showFragemnt(fragmentMain);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //

    }

    public void addFragment(Fragment fragment) {
        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.panel, fragment);
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    public void showFragemnt(Fragment show) {
        hideFragment();
        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.show(show);
        fragmentTransaction.commit();
    }

    public void hideFragment() {
        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragmentImage);
        fragmentTransaction.hide(fragmentMain);
        fragmentTransaction.hide(fragmentVideo);
        fragmentTransaction.hide(fragmentDownLoad);
        fragmentTransaction.hide(fragmentDocument);
        fragmentTransaction.hide(fragmentMusic);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                fragmentImage.initData();
                showFragemnt(fragmentImage);
                break;
            case 1:
                showFragemnt(fragmentVideo);
                break;
            case 2:
                showFragemnt(fragmentMusic);
                break;
            case 3:
                showFragemnt(fragmentDocument);
                break;
            case 4:
                showFragemnt(fragmentDownLoad);
                break;
            case 5:
                fragmentMain.initData();
                showFragemnt(fragmentMain);
                break;
        }
        drawerLayout.closeDrawers();
    }
}
