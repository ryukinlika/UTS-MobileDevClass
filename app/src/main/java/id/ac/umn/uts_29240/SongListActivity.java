package id.ac.umn.uts_29240;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongListActivity extends AppCompatActivity {
    RecyclerView rvSongList;
    SongListAdapter adapter;
    List<SongModel> songLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list_activity);



        songLists = getSongFromDevices(this.getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath());
        rvSongList = findViewById(R.id.recyclerView);
        adapter = new SongListAdapter(this, songLists);
        rvSongList.setAdapter(adapter);
        rvSongList.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        if(intent.getStringExtra("From") != null && intent.getStringExtra("From").equals("login")){
            DialogFragment welcome = new WelcomeDialog();
            welcome.show(getSupportFragmentManager(), "welcome_tag");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profileMenu:
                openProfile();
                return true;
            case R.id.logoutMenu:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        finish();
    }

    private void openProfile() {
        Intent intent = new Intent(SongListActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private List<SongModel> getSongFromDevices(final Context context, String args) {
        List<SongModel> temp = new ArrayList<>();
        MediaMetadataRetriever metadata = new MediaMetadataRetriever();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Method available on Oreo version or higher (API 27++)
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.TITLE};
            Cursor c = context.getContentResolver().query(uri, projection, null, null);

            if (c != null) {
                while (c.moveToNext()) {

                    String path = c.getString(0);
                    String duration = c.getString(1);
                    String title = c.getString(2);

                    //filename
                    //String name = path.substring(path.lastIndexOf("/") + 1);

                    SongModel songModel = new SongModel(title, duration, path);

                    Log.e("Name :",  title);
                    Log.e("Duration : ", duration);
                    Log.e("Path :", path);
                    temp.add(songModel);
                }
                c.close();
            }
        }
        else{
            //Older version (API 21~26), slow method.
            Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media.TITLE};
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
            Cursor c = loader.loadInBackground();
            if (c != null) {
                while (c.moveToNext()) {

                    String path = c.getString(0);
                    String duration = c.getString(1);
                    String title = c.getString(2);

                    //filename
                    //String name = path.substring(path.lastIndexOf("/") + 1);

                    SongModel songModel = new SongModel(title, duration, path);

                    Log.e("Name :",  title);
                    Log.e("Duration : ", duration);
                    Log.e("Path :", path);
                    temp.add(songModel);
                }
                c.close();
            }
//            try {
//                File rootFolder = new File(args);
//                File[] files = rootFolder.listFiles();
//                for (File file : files) {
//                    if (file.isDirectory()) {
//                        Log.d("in if directory", file.getAbsolutePath());
//                        if (getSongFromDevices(context, file.getAbsolutePath()) != null) {
//                            temp.addAll(getSongFromDevices(context, file.getAbsolutePath()));
//                        } else {
//                            break;
//                        }
//                    } else if (file.getName().endsWith(".mp3")) {
//                        String path = file.getAbsolutePath();
//                        metadata.setDataSource(path);
//
//                        String duration = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
//                        String title = metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
//
//                        SongModel songModel = new SongModel(title, duration, path);
//
//                        Log.e("Name :",  title);
//                        Log.e("Duration : ", duration);
//                        Log.e("Path :", path);
//                        temp.add(songModel);
//                    }
//                }
//
//            } catch (Exception e) {
//                return null;
//            }
        }
        return temp;
    }

}
