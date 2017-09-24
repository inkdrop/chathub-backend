package com.inkdrop.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inkdrop.application.helpers.TokenGeneratorHelper;
import com.inkdrop.domain.BasePersistable;
import com.inkdrop.domain.room.Room;
import com.inkdrop.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "messages", indexes = {
    @Index(columnList = "room_id", name = "room_index"),
    @Index(columnList = "sender_id", name = "sender_index"),
    @Index(columnList = "uid", name = "uid_message_idx", unique = true)
})
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"room", "sender"}, of = {"id", "uid"})
public class Message extends BasePersistable {

  private static final long serialVersionUID = -5293724621181603251L;

  @ManyToOne(optional = false)
  @JsonIgnoreProperties({"description", "homepage", "owner", "organization", "messages", "users",
      "_private", "joined"})
  private Room room;

  @Column(nullable = false, columnDefinition = "TEXT")
  @NotEmpty
  private String content;

  @ManyToOne(optional = false)
  @JsonIgnoreProperties({"backendAccessToken", "email", "memberSince", "firebaseJwt", "rooms",
      "location", "company"})
  private User sender;

  @Column(nullable = false, length = 36)
  private String uid;
}
