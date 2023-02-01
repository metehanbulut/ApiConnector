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
@Table(name = "c_user_role")
public class UserRole extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "c_user_role_seq")
    @SequenceGenerator(name="c_user_role_seq", sequenceName = "c_user_role_seq", allocationSize = 1)
    @Id
    @Column(name = "id",nullable = false,columnDefinition = "NUMBER")
    private Long id;

    @Column(name="user_id",nullable = false,columnDefinition = "NUMBER")
    @NotNull(message = "User_ID Boş Bırakılamaz.")
    private Long userId;

    @Column(name="role_id",nullable = false,columnDefinition = "NUMBER")
    @NotNull(message = "Role_ID Boş Bırakılamaz.")
    private Long roleId;


}
