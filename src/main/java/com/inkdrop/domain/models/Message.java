package com.inkdrop.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inkdrop.application.helpers.TokenGeneratorHelper;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "messages", indexes = {
    @Index(columnList = "room_id", name = "room_index"),
    @Index(columnList = "sender_id", name = "sender_index"),
    @Index(columnList = "uid", name = "uid_message_idx")
})
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BasePersistable {

  private static final long serialVersionUID = -5293724621181603251L;

  @ManyToOne(optional = false)
  @JsonIgnoreProperties({"description", "homepage", "owner", "organization", "messages", "users",
      "_private", "joined"})
  private Room room;

  @Column(nullable = false)
  @Lob
  @NotEmpty
  private String content;

  @ManyToOne(optional = false)
  @JsonIgnoreProperties({"backendAccessToken", "email", "memberSince", "firebaseJwt", "rooms",
      "location", "company"})
  private User sender;

  @Column(nullable = false, unique = true, length = 15)
  private String uid;

  @PrePersist
  public void onCreate() {
    if (uid == null) {
      uid = TokenGeneratorHelper.newToken(15);
    }
  }

}
