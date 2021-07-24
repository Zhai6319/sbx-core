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
public class ReGeoCodeResult implements Serializable {


    private String formatted_address;

    private AddressComponent addressComponent;
}
