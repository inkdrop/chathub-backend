package com.inkdrop.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.inkdrop.domain.user.events.UserCreatedEvent;
import com.inkdrop.domain.user.events.UserJoinedRoomEvent;
import com.inkdrop.domain.user.events.UserLeftRoomEvent;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "users", indexes = {
    @Index(unique = true, columnList = "uid"),
    @Index(unique = true, columnList = "backendAccessToken"),
    @Index(unique = true, columnList = "login"),
}, schema = "users")
@Data
@EqualsAndHashCode(callSuper = true, of = {"login"})
@ToString(of = {"login", "uid"})
@JsonInclude(content = Include.NON_NULL)
@NamedEntityGraphs(
    @NamedEntityGraph(name = "with-subscriptions",
        attributeNodes = {@NamedAttributeNode("subscriptions")}))
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractAggregateRoot implements Serializable {

  private static final long serialVersionUID = 1492535311821424305L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  private Date createdAt;

  @LastModifiedDate
  private Date updatedAt;

  @Column(nullable = false)
  private String login;

  @Column(nullable = false)
  private Integer uid;

  @Column(nullable = false)
  private String backendAccessToken;

  @Column
  private String name;

  @Column
  private String email;

  @Column
  private String location;

  @Column
  private String company;

  @Column
  private String avatar;

  @Column
  private String accessToken;

  @CreatedDate
  private Date memberSince;

  @Transient
  private String firebaseToken = "";

  @ElementCollection
  @CollectionTable(name = "subscriptions", joinColumns = @JoinColumn(name = "user_id"))
  private Set<Subscription> subscriptions = new HashSet<>();

  @Version
  private Long version;

  public List<Long> subscribedRoomsId() {
    return getSubscriptions()
        .stream().map(s -> s.getRoomId())
        .collect(Collectors.toList());
  }

  public void subscribeToRoom(Long roomId) {
    getSubscriptions().add(new Subscription(roomId));
    registerEvent(new UserJoinedRoomEvent(this, roomId));
  }

  public void leaveRoom(Long roomId) {
    Subscription toBeRemoved = getSubscriptions().stream()
        .filter(s -> s.getRoomId().equals(roomId)).findFirst().get();
    getSubscriptions().remove(toBeRemoved);
    registerEvent(new UserLeftRoomEvent(this, roomId));
  }

  @PostPersist
  public void postPersist() {
    registerEvent(new UserCreatedEvent(this));
  }

  @PostUpdate
  public void postUpdate(){
    System.out.println("HERE");
  }
}
