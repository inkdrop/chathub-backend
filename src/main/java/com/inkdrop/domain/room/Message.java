package com.inkdrop.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.inkdrop.domain.user.User;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "messages", indexes = {
    @Index(columnList = "room_id", name = "room_index"),
    @Index(columnList = "sender_id", name = "sender_index")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"id", "uid"})
public class Message implements Serializable {

  private static final long serialVersionUID = -5293724621181603251L;

  @Id
  @Type(type = "pg-uuid")
  private UUID id = UUID.randomUUID();

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  @JsonIgnore
  private Date updatedAt;

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
}
