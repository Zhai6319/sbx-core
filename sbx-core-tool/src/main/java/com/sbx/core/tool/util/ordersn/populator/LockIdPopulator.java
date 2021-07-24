package com.sbx.core.tool.util.ordersn.populator;


import com.sbx.core.tool.util.ordersn.IdInfo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockIdPopulator extends BasePopulator {

    private Lock lock = new ReentrantLock();

    public LockIdPopulator() {
        super();
    }

    @Override
    public void populateId(IdInfo idInfo) {
        lock.lock();
        try {
            super.populateId(idInfo);
        } finally {
            lock.unlock();
        }
    }

}