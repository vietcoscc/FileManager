package com.t3h.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vaio.filemanager.FileManager;
import com.example.vaio.filemanager.MainActivity;
import com.example.vaio.filemanager.R;
import com.t3h.adapter.ListViewMainAdapter;
import com.t3h.model.ItemViewFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * Created by vaio on 10/14/2016.
 */

public class FragmentMain extends Fragment {


    private View v;
    private ListView listView;
    private ListViewMainAdapter listViewMainAdapter;
    private ArrayList<ItemViewFile> arrItemViewFile = new ArrayList<>();
    private FileManager fileManager = new FileManager();
    private TranslateAnimation animation;
    private ArrayList<String> arrHistotyPath;
    private TextView tvFolderStatus;
    private ProgressBar progressBar;

    public FragmentMain(ArrayList<String> arrHistotyPath) {
        this.arrHistotyPath = arrHistotyPath;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        initData(Environment.getExternalStorageDirectory().getPath());
        tvFolderStatus = (TextView) v.findViewById(R.id.folder_status);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        setOnItemClick(listView);
        return v;
    }

    public void setOnItemClick(final ListView listView) {
        tvFolderStatus.setVisibility(View.INVISIBLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doItemClick(position);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.pop_up_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.open:
                                doItemClick(position);
                                break;
                            case R.id.copy:
                                MainActivity mainActivity = (MainActivity) getActivity();
                                mainActivity.setVisiblePaste();
                                mainActivity.setFileCopied(arrItemViewFile.get(position).getPath(), true);
                                break;
                            case R.id.cut:
                                MainActivity mainActivity2 = (MainActivity) getActivity();
                                mainActivity2.setVisiblePaste();
                                mainActivity2.setFileCopied(arrItemViewFile.get(position).getPath(), false);

                                break;
                            case R.id.delete:
                                deleteFileOrDirectory(arrItemViewFile.get(position).getPath());
                                initData(arrHistotyPath.get(arrHistotyPath.size() - 1));
                                break;
                        }
                        listViewMainAdapter.notifyDataSetChanged();
                        return false;
                    }
                });


                popupMenu.show();
                return true;
            }
        });
    }
    public void deleteFileOrDirectory(String path) {
        File file = new File(path);
        if (file.isDirectory() && file.listFiles().length == 0) {
            file.delete();
        }
        if (!file.isDirectory()) {
            file.delete();
        } else {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFileOrDirectory(files[i].getPath());
            }
        }
        file.delete();
    }


    public static void copyFileOrDirectory(String srcDir, String dstDir) {

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());

            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);

                }
            } else {
                copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public void doItemClick(int position) {
        String path = arrItemViewFile.get(position).getPath();
        String name = arrItemViewFile.get(position).getTv();
        Boolean isDirectory = arrItemViewFile.get(position).isDirectory();

        if (isDirectory) {
            arrItemViewFile.clear();
            arrHistotyPath.add(path);
            arrItemViewFile = fileManager.getArrayFile(path);
            if (arrItemViewFile.isEmpty()) {
                tvFolderStatus.setVisibility(View.VISIBLE);
            }
            animation = (TranslateAnimation) AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.list_view_animation);
            listView.startAnimation(animation);
        } else {
            File file = new File(path);
            Intent intent = new Intent();
            if (file.getName().contains(".jpg")) {
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);
            } else if (file.getName().contains(".mp4")) {
                intent.setDataAndType(Uri.fromFile(file), "video/*");
                startActivity(intent);
            }else if (file.getName().contains(".mp3")||file.getName().contains(".wav")) {
                intent.setDataAndType(Uri.fromFile(file), "audio/*");
                startActivity(intent);
            }else {
                intent.setData(Uri.fromFile(file));
                startActivity(intent);
            }
        }
        listViewMainAdapter.notifyDataSetChanged();
    }

    public void onBackPress() {
        tvFolderStatus.setVisibility(View.INVISIBLE);
        if (arrHistotyPath.size() > 1) {
            arrHistotyPath.remove(arrHistotyPath.size() - 1);
            initData(arrHistotyPath.get(arrHistotyPath.size() - 1));
            listView.startAnimation(animation);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle("Do you wanna quit ?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.show();

        }

    }


    public void initData(String path) {
        listView = (ListView) v.findViewById(R.id.lv_main);
        arrItemViewFile.clear();
        arrItemViewFile = fileManager.getArrayFile(path);
        listViewMainAdapter = new ListViewMainAdapter(getActivity().getBaseContext(), arrItemViewFile);
        listView.setAdapter(listViewMainAdapter);
    }

}
