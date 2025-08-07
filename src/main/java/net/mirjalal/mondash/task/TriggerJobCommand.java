package net.mirjalal.mondash.task;

import net.mirjalal.mondash.gitlab.Job;

public class TriggerJobCommand implements Command {

    private final Job job;

    public TriggerJobCommand(Job job) {
        this.job = job;
    }

    @Override
    public void execute() {
        job.triggerJob();
    }
    
}
