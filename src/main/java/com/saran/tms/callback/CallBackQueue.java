package com.saran.tms.callback;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CallBackQueue {
    private BlockingQueue<CallBackFunction> queue;

    public CallBackQueue(int size) {
        queue = new ArrayBlockingQueue<>(size);
    }

    private Object waitAndExecute(CallBackFunction callBack, Object value) throws InterruptedException {
        while (true) {
            CallBackFunction front = queue.peek();

            if (callBack.equals(front)) {
                return queue.take().callBack(value);
            }

            Thread.sleep(10);
        }
    }

    public Object executeOrReturn(CallBackFunction callBack, Object value) throws InterruptedException {
        boolean isEnqueued = queue.offer(callBack);
        if(!isEnqueued) {
        	return isEnqueued;
        }
        return waitAndExecute(callBack, value);
    }
}
