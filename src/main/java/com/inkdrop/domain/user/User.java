package com.inkdrop.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inkdrop.domain.BasePersistable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "users", indexes = {
    @Index(unique = true, columnList = "uid"),
    @Index(unique = true, columnList = "backendAccessToken"),
    @Index(unique = true, columnList = "login"),
})
@Data
@EqualsAndHashCode(callSuper = true, of = {"login"})
@ToString(of = {"login", "uid"})
@JsonInclude(content = Include.NON_NULL)
@NamedEntityGraphs(
    @NamedEntityGraph(name = "with-subscriptions",
        attributeNodes = {@NamedAttributeNode("subscriptions")}))
public class User extends BasePersistable {

  private static final long serialVersionUID = 1492535311821424305L;

  @Column(nullable = false)
  private String login;

  @Column(nullable = false)
  private Integer uid;

  @Column(nullable = false)
  private String backendAccessToken;

  @Column
  private String name;

  @Column
  @JsonIgnore
  private String email;

  @Column
  private String location;

  @Column
  private String company;

  @Column
  private String avatar;

  @Column
  @JsonIgnore
  private String accessToken;

  @CreatedDate
  private Date memberSince;

  @Transient
  @JsonProperty(value = "firebase_token")
  private String firebaseJwt = "";

  @ElementCollection
  @Fetch(FetchMode.JOIN)
  @CollectionTable(name = "subscriptions", joinColumns = @JoinColumn(name = "user_id"))
  @JsonIgnore
  private Set<Subscription> subscriptions = new HashSet<>();

  public List<Long> subscribedRoomsId() {
    return getSubscriptions()
        .stream().map(s -> s.getRoomId())
        .collect(Collectors.toList());
  }

  public void subscribeToRoom(Long roomId) {
    getSubscriptions().add(new Subscription(roomId));
  }
}
