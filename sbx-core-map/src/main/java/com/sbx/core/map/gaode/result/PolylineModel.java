package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/13
 */
@Data
public class PolylineModel implements Serializable {
    private static final long serialVersionUID = -5468017812591346840L;

    private String longitude;
    private String latitude;
}
