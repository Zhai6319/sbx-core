package com.sbx.core.model.exception;

import com.sbx.core.model.enums.EResultCode;

/**
 * 获取锁失败
 *
 * @author ye2moe
 * @date 2020-08-18 21:54
 **/
public class AcquireRedisLockFailedException extends CustomException {

    public AcquireRedisLockFailedException() {
        super(EResultCode.SYSTEM_BUSY);
    }

}
