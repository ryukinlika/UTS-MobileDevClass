package id.ac.umn.uts_29240;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongViewHolder> {
    private List<SongModel> songLists;
    private LayoutInflater inflater;
    private Context context;

    public SongListAdapter(Context context, List<SongModel> list){
        this.context = context;
        this.songLists = list;
        this.inflater = LayoutInflater.from(context);
    }

    class SongViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView tvTitle, tvUri, tvDuration;
        private SongListAdapter mAdapter;
        private int pos;
        private SongModel songModel;

        public SongViewHolder(@NonNull View itemView,
                                   SongListAdapter adapter) {
            super(itemView);
            mAdapter = adapter;
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            pos = getLayoutPosition();
            songModel = songLists.get(pos);
            Intent playSong = new Intent(context, SongPlayer.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Detail", songModel);
            playSong.putExtras(bundle);
            context.startActivity(playSong);
        }
    }


    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.song_item_layout, parent, false);
        return new SongViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder,
                                 int pos) {
        SongModel mSumberVideo = songLists.get(pos);
        holder.tvTitle.setText(mSumberVideo.getTitle());
        holder.tvDuration.setText(mSumberVideo.getDurationFormatted());
        //holder.tvUri.setText(mSumberVideo.getsongURI());
    }

    @Override
    public int getItemCount() {
        return songLists.size();
    }

}
