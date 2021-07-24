package com.sbx.core.tool.util.ordersn.populator;


import com.sbx.core.tool.util.DateUtil;
import com.sbx.core.tool.util.ordersn.IdInfo;

public abstract class BasePopulator implements IdPopulator {

    protected long sequence = 0;
    protected long lastTimestamp = -1;

    public BasePopulator() {
        super();
    }

    @Override
    public void populateId(IdInfo idInfo) {
        long nowTimestamp = idInfo.getNowTimestamp();
        if (nowTimestamp < lastTimestamp) {
            throw new IllegalStateException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d second/milisecond.",
                    lastTimestamp - nowTimestamp));
        }

        if (nowTimestamp == lastTimestamp) {
            sequence++;
            if (sequence >= 99) {
                nowTimestamp = this.getNextTime();
            }
        } else {
            lastTimestamp = nowTimestamp;
            sequence = 0;
        }

        idInfo.setSequence(sequence);
        idInfo.setNowTimestamp(nowTimestamp);
    }


    private long getNextTime() {
        long timestamp = DateUtil.now().getTime();
        while (timestamp <= lastTimestamp) {
            timestamp = DateUtil.now().getTime();
        }
        return timestamp;
    }
}
