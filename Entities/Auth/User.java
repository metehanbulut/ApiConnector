package com.linklegel.apiContact.Entities.Auth;

import com.linklegel.apiContact.Entities.Base.BaseEntity;
import com.linklegel.apiContact.Entities.Dao.IUserRoleDao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "c_users")
public class User extends BaseEntity {
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_users_seq")
  @SequenceGenerator(name="c_users_seq", sequenceName = "c_users_seq", allocationSize = 1)
  @Id
  @Column(name = "id",nullable = false,columnDefinition = "NUMBER")
  private Long id;

  @Column(name="username",nullable = false,columnDefinition = "varchar(20)")
  @NotNull(message = "Kullanıcı Adı Boş Bırakılamaz.")
  private String username;

  @Column(name="email",nullable = false,columnDefinition = "varchar(100)")
  @NotNull(message = "Mail Alanı  Boş Bırakılamaz.")
  private String email;

  @Column(name="password",nullable = false,columnDefinition = "varchar(100)")
  @NotNull(message = "Parola Alanı  Boş Bırakılamaz.")
  private String password;

/*  @ManyToMany(fetch = FetchType.LAZY) // TODO burası duzenlenecek
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();*/
}
