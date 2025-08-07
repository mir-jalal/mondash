package net.mirjalal.mondash.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.mirjalal.mondash.configuration.GitlabConfiguration;
import net.mirjalal.mondash.gitlab.Job;
import net.mirjalal.mondash.task.Command;
import net.mirjalal.mondash.task.TriggerJobCommand;

@Service
public class EnvironmentService {
    private final Command restartEnvironmentCommand;

    public EnvironmentService(GitlabConfiguration gitlabConfiguration) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("token", gitlabConfiguration.getToken());
        params.put("ref", "main");
        params.put("variables[environment]", "skip");
        params.put("variables[job]", "deploy");

        Job restartEnvironmentJob = new Job(gitlabConfiguration.getJobUrl(), params);

        this.restartEnvironmentCommand = new TriggerJobCommand(restartEnvironmentJob);
    }

    public void restartEnvironment() {
        restartEnvironmentCommand.execute();
    }
}
