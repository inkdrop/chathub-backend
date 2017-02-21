package com.inkdrop.app.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkdrop.app.domain.models.Message;

public class PushToFirebaseCommand {

    private DatabaseReference getDatabase(Message message) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference("/messages");
        return db.child(message.getRoom().getUid().toString());
    }

    public void pushToFirebase(Message message){
        try{
            DatabaseReference db = getDatabase(message);
            db.child(message.getId().toString()).setValue(new ObjectMapper().writeValueAsString(message));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
