package com.sbx.core.oss.model;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/1/9
 */
@Data
public class Signature implements Serializable {
    private static final long serialVersionUID = -5367904308632310790L;

    private String signature;

    private String encodedPolicy;

    public Signature(String signature,String encodedPolicy){
        this.signature = signature;
        this.encodedPolicy = encodedPolicy;
    }
}
