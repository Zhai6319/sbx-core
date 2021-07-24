package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/4/23
 */
@Data
public class ReGeoCodeBase implements Serializable {
    private static final long serialVersionUID = 8704344993721845207L;

    private String status;
    private String info;
    private String infocode;
    private ReGeoCodeResult regeocode;
}
