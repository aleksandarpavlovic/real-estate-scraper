package com.paki.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class SchedulerService {
    public static final int MIN_JOB_PERIOD = 15;

    private ScheduledThreadPoolExecutor scheduler;
    private ScheduledFuture scheduledJob = null;
    private Runnable job = null;

    @Autowired
    public SchedulerService() {
        scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.setRemoveOnCancelPolicy(true);
    }

    public void schedule(Runnable job, int periodInMinutes) {
        if (this.job != null) {
            if (this.scheduledJob != null)
                this.scheduledJob.cancel(false);
        }
        this.job = wrappedJob(job);
        schedule(periodInMinutes);
    }

    public void schedule(int periodInMinutes) {
        unschedule();
        scheduledJob = scheduler.scheduleWithFixedDelay(job, 0, Math.max(periodInMinutes, MIN_JOB_PERIOD), TimeUnit.MINUTES);
    }

    public void unschedule() {
        if (scheduledJob != null) {
            scheduledJob.cancel(false);
            scheduledJob = null;
        }
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

    private static Runnable wrappedJob(Runnable job) {
        return () -> {
            try {
                job.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
