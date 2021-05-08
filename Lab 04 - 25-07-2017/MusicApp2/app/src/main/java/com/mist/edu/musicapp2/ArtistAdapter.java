package com.mist.edu.musicapp2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mist.edu.musicapp2.Model.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/1/2017.
 */

public class ArtistAdapter extends ArrayAdapter<Artist> {
    private Activity context;
    List<Artist> artistList;

    public ArtistAdapter (Activity context, List<Artist> artistList) {
        super(context, R.layout.item_artist_list, artistList );

        this.context = context;
        this.artistList = artistList;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.item_artist_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.textViewGenre);


        Artist temp = artistList.get(position);

        textViewName.setText(temp.artistName);
        textViewGenre.setText(temp.artistGenre);

        return listViewItem;

    }
}
