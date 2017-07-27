package com.inkdrop.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inkdrop.application.helpers.TokenGeneratorHelper;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
    @NamedEntityGraph(name = "with-join-rooms",
        attributeNodes = {@NamedAttributeNode("rooms")}))
public class User extends BasePersistable {

  private static final long serialVersionUID = 1492535311821424305L;

  @Column(nullable = false, unique = true)
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

  @ManyToMany(targetEntity = Room.class)
  @JoinTable(name = "room_users",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "room_id")})
  @JsonIgnore
  private Set<Room> rooms = new HashSet<>();

  @Transient
  @JsonProperty(value = "firebase_token")
  private String firebaseJwt = "";


  @PrePersist
  public void onCreate() {
    if (backendAccessToken == null) {
      backendAccessToken = TokenGeneratorHelper.newToken(25);
    }
  }
}
