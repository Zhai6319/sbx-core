package com.sbx.core.wx.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/2
 */
@Data
public class MessageModel implements Serializable {
    private static final long serialVersionUID = -2136722348986319573L;

    private String templateId;

    private List<String> code;

    private String emphasisKeyword;

}
