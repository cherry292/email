package com.email.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;


@Data
public class Attachment implements Serializable {
    private static final long serialVersionUID = 527852707311417285L;
    /**
     * ID
     */
    private Integer id;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Integer fileSize;
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 创建人
     */
    private String creator;



}

