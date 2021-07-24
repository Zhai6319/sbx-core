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
public class AddressComponent implements Serializable {

    private String province;
    private String adcode;
    private String district;
    private String towncode;
    private String country;
    private String township;
    private String citycode;

}
