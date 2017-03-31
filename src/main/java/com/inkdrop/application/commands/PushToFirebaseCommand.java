package com.inkdrop.application.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkdrop.domain.models.Message;
import com.inkdrop.infrastructure.annotations.Command;

@Command
public class PushToFirebaseCommand {

  public void pushToFirebase(Message message) {
    try {
      DatabaseReference db = getDatabase(message);
      db.child(message.getId().toString()).setValue(new ObjectMapper().writeValueAsString(message));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private DatabaseReference getDatabase(Message message) {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference("/messages");
    return db.child(message.getRoom().getUid().toString());
  }
}
