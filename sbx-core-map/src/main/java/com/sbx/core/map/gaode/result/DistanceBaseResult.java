package com.sbx.core.map.gaode.result;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>说明：</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/8
 */
@Data
public class DistanceBaseResult implements Serializable {
    private static final long serialVersionUID = -927190677434354205L;

    private String status;
    private String info;
    private String infocode;
    private List<DistanceResult> results;
}
