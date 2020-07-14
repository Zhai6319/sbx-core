package com.sbx.core.oss.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明:</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2020/5/5
 */
@Data
public class OssFile implements Serializable {
    private static final long serialVersionUID = -8115128799841935751L;

    private String fileName;

    private String objectName;

    private String fileUrl;
}
