package com.sbx.core.tool.util.ordersn;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
public class SnProducer {

    private IdGenerator idGenerator;

    private static volatile SnProducer instance;

    public static SnProducer getInstance() { // 对获取实例的方法进行同步
        if (instance == null) {
            synchronized (SnProducer.class) {
                if (instance == null) {
                    instance = new SnProducer(new IdGenerator());
                }
            }
        }
        return instance;
    }

    public SnProducer(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public String getOrdersPaySn() {
        return "P" + this.idGenerator.nextIdStr();
    }

    public String getOrdersSn() {
        return "E" + this.idGenerator.nextIdStr();
    }

    public String getWaybillSn() {
        return "W" + this.idGenerator.nextIdStr();
    }

    public String getAccountFlowSn() {
        return "AF" + this.idGenerator.nextIdStr();
    }

    public String getEnsureAmountBillSn() {
        return "EB" + this.idGenerator.nextIdStr();
    }

    public String getRefundsSn() {
        return "R" + this.idGenerator.nextIdStr();
    }

    public String getSendSn() {
        return "S" + this.idGenerator.nextIdStr();
    }

    public String getTicketSn() {
        return "T" + this.idGenerator.nextIdStr();
    }

    public String getApplyWithdrawalSn() {
        return "WA" + this.idGenerator.nextIdStr();
    }

    public String getViolationTicketSn(){
        return "V"+this.idGenerator.nextIdStr();
    }

    public String[] getSnBySn(int size, String sn) {
        Set<String> set = new LinkedHashSet<>();
//        Random random = new Random();
//        while (set.size() != size) {
//            set.add(sn + (random.nextInt(size)));
//        }
        for (int i = 1; i <= size; i++) {
            set.add(sn + i);
        }
        return set.toArray(new String[size]);
    }

}
