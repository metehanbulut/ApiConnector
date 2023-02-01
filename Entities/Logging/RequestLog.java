package com.linklegel.apiContact.Entities.Logging;

import com.linklegel.apiContact.Entities.Base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="request_log")
/**
 * Buradaki entity DTO kullanmadan logger classının otomatik olarak üretip veritabanına kaydı yapılacağı tasarlandığından
 * class 'ta yeterli kontrol etmediğimden burada tüm kontrolleri gerçekleştirdim. Burada Hata Çıkarsa LoggingFilter Class'ına Bakılmalı.
 * @author MetehanBulut
 *
 * */

/***        LOGGER.info("filter Logs: METHOD={};
 * REQUESTURI={}; REQUESTBODY= {}; RESPONSECODE ={};
 * RESPONSEBODY={} TIMETAKEN={}",request.getMethod(),request.getRequestURI(),
 *
 *
 * Method henüz çalıştırılmadı 13 Ocak 18.39
 *
 **/
public class RequestLog extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "request_log_seq")
    @SequenceGenerator(name="request_log_seq", sequenceName = "request_log_seq", allocationSize = 1)
    @Id
    @Column(name = "id",nullable = false,columnDefinition = "NUMBER")
    private Long id;

    @Column(name="method_type",nullable = false,columnDefinition = "varchar(8)")
    @NotNull(message = "Log'dan gelen İstek Tipi Boş Geliyor.")
    private String methodType;

    @NotNull(message = "Log'dan  gelen istek boş geliyor.")
    @Column(name="url",nullable = false,columnDefinition = "varchar(max)")
    private String Url;

    @NotNull(message = "Log'dan gelen Ip Adresi  boş geliyor.")
    @Column(name="ip_address",nullable = false,columnDefinition = "varchar(max)")
    private String ipAdress;
    @NotNull(message = "Log'dan gelen body boş geliyor.")
    @Column(name="request_body",nullable = false,columnDefinition = "varchar(max)")
    private String requestBody;

    @NotNull(message = "Log'dan gelen code boş geliyor.")
    @Column(name="response_code",nullable = false,columnDefinition = "varchar(max)")
    private String responseCode;
    @NotNull(message = "Log'dan gelen body boş geliyor.")
    @Column(name="response_body",nullable = false,columnDefinition = "varchar(max)")
    private String responseBody;


    @NotNull(message = "Log'dan gelen hesaplanan süre  boş geliyor.")
    @Column(name="elapsed_time",nullable = false,columnDefinition = "NUMBER")
    private Long elapsedTime;


}
