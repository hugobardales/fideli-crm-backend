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
        // 'cmd.exe /c' es necesario para que Windows reconozca "docker compose" correctamente
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "docker compose config");
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // Leemos la salida mientras el proceso corre para evitar que el buffer se llene y se congele
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        // Esperamos hasta 20 segundos (con el YAML optimizado debería tardar menos de 2)
        boolean finished = process.waitFor(20, TimeUnit.SECONDS);

        assertTrue(finished, "El comando docker compose config se colgó (timeout)");
        assertEquals(0, process.exitValue(), "Error de sintaxis en Docker Compose. Salida:\n" + output);
    }

    @Test
    void verifyDockerDaemonRunning() throws Exception {
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "docker info");
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // También leemos la salida aquí por seguridad
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        assertTrue(finished, "El comando docker info se colgó");
        assertEquals(0, process.exitValue(), "Docker Desktop no parece estar corriendo. Salida:\n" + output);
    }
}