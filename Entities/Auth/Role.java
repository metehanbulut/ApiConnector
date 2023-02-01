package com.linklegel.apiContact.Entities.Auth;

import com.linklegel.apiContact.Entities.Base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "c_roles")
public class Role extends BaseEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_roles_seq")
  @SequenceGenerator(name="c_roles_seq", sequenceName = "c_roles_seq", allocationSize = 1)
  @Id
  @Column(name = "id",nullable = false,columnDefinition = "NUMBER")
  private Long id;

  @Column(name="name",nullable = false,columnDefinition = "varchar(20)")
  @NotNull(message = "Rol Adı Boş Bırakılamaz.")
  private String name;

}