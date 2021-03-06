package com.inkdrop.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "rooms", indexes = {
    @Index(columnList = "uid", unique = true),
    @Index(columnList = "fullName", name = "full_name_idx")
})
@JsonInclude(content = Include.NON_NULL)
@Data
@EqualsAndHashCode(callSuper = true, of = {"uid"})
@ToString(of = {"name", "uid", "fullName"})
public class Room extends BasePersistable {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private Integer uid;

  @Column(nullable = false)
  private String name;

  @Column
  private String fullName;

  @Column(length = 500)
  private String description;

  @Column()
  private String homepage;

  @Column(nullable = false)
  private String owner;

  @ManyToOne
  @JsonIgnoreProperties({"rooms", "members"})
  private Organization organization;

  @OneToMany(mappedBy = "room")
  @JsonIgnore
  private List<Message> messages = new ArrayList<>();

  @ManyToMany(mappedBy = "rooms", targetEntity = User.class)
  @JsonIgnore
  private Set<User> users = new HashSet<>();

  @Column(name = "private")
  @JsonProperty(value = "private")
  private Boolean _private = false;

  @Transient
  private boolean joined = false;
}
