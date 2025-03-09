package com.example.universal;

import androidx.cardview.widget.CardView;

import com.example.universal.Models.Notes;

public interface NotesClickListener {

    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);


}
