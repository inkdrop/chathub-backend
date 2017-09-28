package com.inkdrop.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inkdrop.application.helpers.TokenGeneratorHelper;
import com.inkdrop.domain.BasePersistable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
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

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "subscriptions", joinColumns = @JoinColumn(name="user_id"))
  private Set<Subscription> subscriptions = new HashSet<>();

  @PrePersist
  public void onCreate() {
    if (backendAccessToken == null) {
      backendAccessToken = TokenGeneratorHelper.newToken(25);
    }
  }
}
