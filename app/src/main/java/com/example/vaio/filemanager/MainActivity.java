package com.example.vaio.filemanager;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.t3h.adapter.ListViewDrawerAdapter;
import com.t3h.fragment.FragmentMain;
import com.t3h.model.ItemViewDrawer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final int REQUEST_READWRITE_STORAGE = 1;
    private DrawerLayout drawerLayout;
    private ListView lvDrawer;
    private ActionBarDrawerToggle toggle;
    private ArrayList<ItemViewDrawer> arrItemViewDrawer = new ArrayList<>();
    private final int iv[] = {R.drawable.image, R.drawable.video, R.drawable.music, R.drawable.document, R.drawable.download, R.drawable.storage};
    private final String tv[] = {"Image", "Video", "Music", "Document", "Download", "Internal Storage"};
    private ListViewDrawerAdapter listViewDrawerAdapter;
    private ArrayList<String> arrHistoryPath = new ArrayList<>();
    private FragmentMain fragmentMain;
    private ImageView ivPaste;
    private String fileCopied;
    private boolean checkCopyOrCut;
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
        arrHistoryPath.add(Environment.getExternalStorageDirectory().getPath());
        fragmentMain = new FragmentMain(arrHistoryPath);
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
        initFragment();
        //
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        toggle.syncState();
        drawerLayout.setDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        customActionBar();



    }

    public void customActionBar() {

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ivPaste = new ImageView(actionBar.getThemedContext());
        ivPaste.setScaleType(ImageView.ScaleType.CENTER);
        ivPaste.setImageResource(R.drawable.paste);
        android.support.v7.app.ActionBar.LayoutParams layoutParams = new
                android.support.v7.app.ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT
                | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        ivPaste.setLayoutParams(layoutParams);
        actionBar.setCustomView(ivPaste);
        ivPaste.setVisibility(View.INVISIBLE);
        ivPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCopyOrCut) {

                    FragmentMain.copyFileOrDirectory(fileCopied, arrHistoryPath.get(arrHistoryPath.size() - 1));
                    fragmentMain.initData(arrHistoryPath.get(arrHistoryPath.size() - 1));
                    ivPaste.setVisibility(View.INVISIBLE);
                }else {
                    FragmentMain.copyFileOrDirectory(fileCopied, arrHistoryPath.get(arrHistoryPath.size() - 1));
                    ivPaste.setVisibility(View.INVISIBLE);
                    fragmentMain.deleteFileOrDirectory(fileCopied);
                    fragmentMain.initData(arrHistoryPath.get(arrHistoryPath.size() - 1));
                }
            }
        });

    }
    public void setFileCopied(String fileCopied, boolean checkCopyOrCut){
        this.fileCopied = fileCopied;
        this.checkCopyOrCut = checkCopyOrCut;
    }
    public void initFragment() {
        android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.panel, fragmentMain);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        arrHistoryPath.clear();
        switch (position) {
            case 0:
                arrHistoryPath.add(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DCIM);
                fragmentMain.initData(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DCIM);
                break;
            case 1:
                arrHistoryPath.add(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_MOVIES);
                fragmentMain.initData(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_MOVIES);
                break;
            case 2:
                arrHistoryPath.add(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_MUSIC);
                fragmentMain.initData(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_MUSIC);
                break;
            case 3:
                arrHistoryPath.add(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOCUMENTS);
                fragmentMain.initData(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOCUMENTS);
                break;
            case 4:
                arrHistoryPath.add(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOWNLOADS);
                fragmentMain.initData(Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOWNLOADS);
                break;
            case 5:
                arrHistoryPath.add(Environment.getExternalStorageDirectory().getPath());
                fragmentMain.initData(Environment.getExternalStorageDirectory().getPath());
                break;
        }
        drawerLayout.closeDrawers();
    }
    public void setVisiblePaste(){
        ivPaste.setVisibility(View.VISIBLE);
    }
    @Override
    public void onBackPressed() {
        fragmentMain.onBackPress();
    }
}
