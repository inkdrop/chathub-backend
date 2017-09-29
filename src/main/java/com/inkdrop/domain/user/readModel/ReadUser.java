package com.inkdrop.domain.user.readModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name = "read_users", schema = "users")
@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    ),
    @TypeDef(
        name = "int-array",
        typeClass = IntArrayType.class
    )
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadUser implements Serializable {

  @Id
  @JsonIgnore
  private Long id;

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
  private Date memberSince;

  @Column(columnDefinition = "TEXT NOT NULL")
  private String firebaseToken = "";

  @Type(type = "int-array")
  @Column(
      name = "rooms",
      columnDefinition = "integer[]"
  )
  @JsonIgnore
  private Integer[] rooms;
}
