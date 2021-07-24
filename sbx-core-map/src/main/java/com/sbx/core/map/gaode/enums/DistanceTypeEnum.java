package com.sbx.core.map.gaode.enums;

/**
 * <p>说明：测距类型枚举</p>
 *
 * @author Z.jc
 * @version 1.0.0
 * @since 2021/3/8
 */
public enum DistanceTypeEnum {

    STRAIGHT_LINE_DISTANCE(0,"直线距离"),
    DRIVE_DISTANCE(1,"驾车距离"),
    WALK_DISTANCE(2,"步行距离")
    ;
    public final Integer type;

    public final String name;

    DistanceTypeEnum(Integer type,String name){
        this.type = type;
        this.name = name;
    }
}
