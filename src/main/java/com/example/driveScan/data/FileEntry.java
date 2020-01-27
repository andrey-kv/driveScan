package com.example.driveScan.data;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Builder
@Data
@Table(name="FILES", indexes={ @Index(name = "FULL_PATH", columnList = "fullName")})
public class FileEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 511)
    private String fullName;

    private String justName;

    private String justExt;

    private BigInteger size;

    private String hash;
}
