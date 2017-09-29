package com.inkdrop.domain.room.readModel;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "rooms", name = "read_rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadRoom {

  @Id
  private Long id;

  @Column
  private Date createdAt;

  @Column
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
}
