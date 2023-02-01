package com.linklegel.apiContact.Entities.Base;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Created By Murat DEMIRKOL 2.05.2022
 **/
@MappedSuperclass
@Data
public class BaseEntity {

    @Column(name="created_by",nullable = false)
    private String createdBy="Unknown";
    @Column(name = "created_date", nullable = false)
    private Date createDate= new Date();
    @Column(name="updated_by",nullable = false)
    private String updatedBy="Unknown";
    @Column(name = "updated_date", nullable = false)
    private Date updatedDate=new Date();
}
