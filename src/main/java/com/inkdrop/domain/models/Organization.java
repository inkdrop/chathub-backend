package com.inkdrop.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "organizations", indexes = {
    @Index(unique = true, columnList = "uid"),
    @Index(columnList = "uid", name = "uid_org_idx"),
    @Index(columnList = "login", name = "login_org_idx")
})
@Data
@EqualsAndHashCode(callSuper = true, of = {"uid"})
@ToString(of = {"name", "uid", "login"})
@JsonInclude(content = Include.NON_NULL)
@NamedEntityGraphs(
    @NamedEntityGraph(name = "with-rooms",
        attributeNodes = {@NamedAttributeNode("rooms")}))
public class Organization extends BasePersistable {

  private static final long serialVersionUID = -7119760968529447945L;

  @Column(nullable = false)
  @NotEmpty
  private String name;

  @Column(nullable = false, unique = true)
  @NotNull
  private Integer uid;

  @Column
  private String avatar;

  @Column
  private String blog;

  @Column
  private String company;

  @Column(nullable = false)
  @NotNull
  private String login;

  @Column(nullable = true)
  private String location;

  @OneToMany(mappedBy = "organization")
  @JsonIgnoreProperties({"users", "joined", "organization"})
  private List<Room> rooms = new ArrayList<>();

//	@Column(name="members", columnDefinition="text[]")
//	@Converter(converterClass=ListToArrayConverter.class, name = "arrayConverter")
//	private List<String> members = new ArrayList<>();

}
