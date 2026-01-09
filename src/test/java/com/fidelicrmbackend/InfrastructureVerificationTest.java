package com.fidelicrmbackend;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InfrastructureVerificationTest {

    @Test
    void verifyDockerComposeSyntax() throws Exception {
        ProcessBuilder builder = new ProcessBuilder("docker-compose", "config");
        builder.redirectErrorStream(true);
        Process process = builder.start();

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        assertTrue(finished, "docker-compose config timed out");

        String output = readOutput(process);
        assertEquals(0, process.exitValue(), "docker-compose config failed: " + output);
    }

    @Test
    void verifyDockerDaemonRunning() throws Exception {
        ProcessBuilder builder = new ProcessBuilder("docker", "info");
        builder.redirectErrorStream(true);
        Process process = builder.start();

        boolean finished = process.waitFor(5, TimeUnit.SECONDS);
        assertTrue(finished, "docker info timed out");

        String output = readOutput(process);
        assertEquals(0, process.exitValue(), "Docker Daemon is not running or not accessible: " + output);
    }

    private String readOutput(Process process) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
