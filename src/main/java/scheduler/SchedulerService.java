package scheduler;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SchedulerService {
    private final ScheduledThreadPoolExecutor scheduler;
    ScheduledFuture scheduledJob = null;
    Runnable job;

    public SchedulerService(Runnable job) {
        scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.setRemoveOnCancelPolicy(true);
        this.job = wrappedJob(job);
    }

    public void schedule(int periodInMinutes) {
        if (scheduledJob != null) {
            scheduledJob.cancel(false);
        }
        scheduledJob = scheduler.scheduleWithFixedDelay(job, 0, periodInMinutes, TimeUnit.MINUTES);
    }

    public void unschedule() {
        if (scheduledJob == null)
            return;
        else {
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
