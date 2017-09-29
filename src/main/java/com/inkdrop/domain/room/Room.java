package com.inkdrop.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inkdrop.domain.room.events.MessageSavedEvent;
import com.inkdrop.domain.room.events.RoomCreatedEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Table(name = "rooms", indexes = {
    @Index(columnList = "uid", unique = true),
    @Index(columnList = "fullName", name = "full_name_idx", unique = true)
}, schema = "rooms")
@JsonInclude(content = Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true, of = {"uid"})
@ToString(of = {"name", "uid", "fullName"})
@NamedEntityGraphs(
    @NamedEntityGraph(name = "with-messages",
        attributeNodes = {@NamedAttributeNode("messages")}))
public class Room extends AbstractAggregateRoot implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Long id;

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  @JsonIgnore
  private Date updatedAt;

  @Column(nullable = false)
  private Integer uid;

  @Column(nullable = false)
  private String name;

  @Column
  private String fullName;

  @Column(length = 500)
  private String description;

  @Column
  private String homepage;

  @Column(nullable = false)
  private String owner;

  @Column(length = 500)
  private String organization;

  @Column(length = 500)
  private String avatar;

  @OneToMany(mappedBy = "room", cascade = {CascadeType.MERGE, CascadeType.PERSIST,
      CascadeType.REMOVE})
  @JsonIgnore
  private List<Message> messages = new ArrayList<>();

  @Column
  @JsonProperty(value = "private")
  private Boolean privateRoom = false;

  public void sendMessage(Message message) {
    message.setRoom(this);
    messages.add(message);
    registerEvent(new MessageSavedEvent((message)));
  }

  @PostPersist
  public void afterCreate(){
    registerEvent(new RoomCreatedEvent(this));
  }
}
