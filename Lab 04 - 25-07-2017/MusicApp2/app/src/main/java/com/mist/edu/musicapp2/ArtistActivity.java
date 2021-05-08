package com.mist.edu.musicapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mist.edu.musicapp2.Model.Artist;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ArtistActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextName;
    private Spinner spinnerGenres;
    private Button buttonAddArtist;

    //database reference
    DatabaseReference databaseArtists;

    List<Artist> artistList;
    ListView artistsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        editTextName = (EditText) findViewById(R.id.editTextName);
        spinnerGenres = (Spinner) findViewById(R.id.spinnerGenres);
        buttonAddArtist = (Button) findViewById(R.id.buttonAddArtist);

        buttonAddArtist.setOnClickListener(this);

        databaseArtists = FirebaseDatabase.getInstance().getReference("artists");

        artistsListView = (ListView) findViewById(R.id.listViewArtists);
        artistList = new ArrayList<>();


    }

    @Override
    protected void onStart() {
        super.onStart();


        databaseArtists.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                artistList.clear();

                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    //getting artist
                    Artist temp = snapshot.getValue(Artist.class);

                    //adding artist to the list
                    artistList.add(temp);

                }


                ArtistAdapter artistAdapter = new ArtistAdapter(ArtistActivity.this, artistList);

                //set the adapter to the list view
                artistsListView.setAdapter(artistAdapter);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view==buttonAddArtist){
            addArtist();
        }

    }

    private void addArtist() {
        String name = editTextName.getText().toString().trim();
        String genre = spinnerGenres.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){
            //action
            String Id = databaseArtists.push().getKey();

            Artist artist = new Artist(name,genre);

            databaseArtists.child(Id).setValue(artist);

            editTextName.setText("");
            Toast.makeText(this, "Artist Added!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Please enter name!", Toast.LENGTH_SHORT).show();
        }
    }
}
