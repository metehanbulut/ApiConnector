package com.linklegel.apiContact.Entities.Dao;

import com.linklegel.apiContact.Entities.Auth.Role;
import com.linklegel.apiContact.Entities.Auth.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRoleDao extends JpaRepository<UserRole,Long> {

    List<UserRole> getUserRoleByUserId(Long userId);

    @Query(value = "select cur.role_id from c_users cu inner join c_user_role cur on (cu.id=cur.user_id)",nativeQuery = true)
    List<Role> getRolesByUserId(
            @Param("userId") Long userId
    );


}
