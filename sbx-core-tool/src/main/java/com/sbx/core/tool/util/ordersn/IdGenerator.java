package com.sbx.core.tool.util.ordersn;



import com.sbx.core.model.exception.CustomException;
import com.sbx.core.tool.util.DateUtil;
import com.sbx.core.tool.util.ordersn.populator.LockIdPopulator;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdGenerator {

    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    private static final String DATE_YYYY_MM_DD_HH_MM_SS_PATTERN = "yyyyMMddHHmmss";

    private static final String DATE_SSS_PATTERN = "SSS";

    private static final int MACHINE_ID_MAX = 999;

    private LockIdPopulator lockIdPopulator;

    public IdGenerator() {
        this.lockIdPopulator = new LockIdPopulator();
    }

    /**
     * 时间（秒）+机器号+时序+时间（毫秒）
     */
    public String nextIdStr() {

        int machineId = MachineIdFactory.getId();
        logger.info("机器号：" + machineId);
        if (machineId == 0 || machineId > MACHINE_ID_MAX) {
            logger.error("机器号异常：" + machineId);
            throw new CustomException("机器号异常，machineId：" + machineId);
        }

        IdInfo idInfo = new IdInfo();
        idInfo.setNowTimestamp(DateUtil.now().getTime());
        lockIdPopulator.populateId(idInfo);


        String dateStrStart = DateFormatUtils.format(idInfo.getNowTimestamp(), DATE_YYYY_MM_DD_HH_MM_SS_PATTERN);
        String dateStrEnd = DateFormatUtils.format(idInfo.getNowTimestamp(), DATE_SSS_PATTERN);
        String machineIdStr = String.format("%03d", machineId);
        String sequenceStr = String.format("%02d", idInfo.getSequence());

        String idStr = new StringBuffer(dateStrStart).append(machineIdStr).append(sequenceStr).append(dateStrEnd).toString();

        return idStr;
    }
}

