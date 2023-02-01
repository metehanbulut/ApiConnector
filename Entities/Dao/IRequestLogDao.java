package com.linklegel.apiContact.Entities.Dao;

import com.linklegel.apiContact.Entities.Logging.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRequestLogDao extends JpaRepository<RequestLog,Long> {

    RequestLog getRequestLogById(Long Id);

}
