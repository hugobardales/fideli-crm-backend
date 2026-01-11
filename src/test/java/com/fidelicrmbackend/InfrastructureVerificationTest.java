package com.fidelicrmbackend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Clase de prueba encargada de validar la disponibilidad y correcta configuración
 * de la infraestructura de contenedores Docker.
 * * <p>Esta suite de pruebas asegura que tanto el motor de Docker como las
 * configuraciones de Docker Compose sean válidas antes de proceder con
 * las pruebas de integración del aplicativo.</p>
 * * <p>Soporta ejecución multiplataforma detectando automáticamente si el
 * entorno es Windows (local) o Linux (GitHub Actions/CI).</p>
 * * @author Hugo Bardales
 * @version 1.1.0
 * @since 2026-01-10
 */
@DisplayName("Verificación de Infraestructura Docker")
public class InfrastructureVerificationTest {

    /**
     * Valida que el archivo docker-compose.yml no contenga errores de sintaxis
     * y sea interpretable por el binario de Docker.
     * * @throws Exception Si ocurre un error al intentar ejecutar el proceso del sistema.
     */
    @Test
    @DisplayName("Validar sintaxis de docker-compose.yml")
    void verifyDockerComposeSyntax() throws Exception {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder builder = isWindows
                ? new ProcessBuilder("cmd.exe", "/c", "docker compose config")
                : new ProcessBuilder("docker", "compose", "config");

        builder.redirectErrorStream(true);
        runProcess(builder, 20, "Error de sintaxis detectado en el archivo Docker Compose");
    }

    /**
     * Verifica que el motor de Docker (Docker Daemon) esté activo y responda
     * a comandos básicos de estado.
     * * @throws Exception Si ocurre un error de E/S al comunicarse con el sistema operativo.
     */
    @Test
    @DisplayName("Verificar estado del Motor Docker")
    void verifyDockerDaemonRunning() throws Exception {
        boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

        ProcessBuilder builder = isWindows
                ? new ProcessBuilder("cmd.exe", "/c", "docker info")
                : new ProcessBuilder("docker", "info");

        builder.redirectErrorStream(true);
        runProcess(builder, 15, "El motor de Docker no parece estar en ejecución");
    }

    /**
     * Método auxiliar para la ejecución controlada de procesos del sistema.
     * Captura la salida estándar y de error para facilitar la depuración en caso de fallos.
     * * @param builder        El configurador del proceso a ejecutar.
     * @param timeoutSeconds Tiempo máximo de espera para la ejecución.
     * @param errorMessage   Mensaje personalizado que se mostrará en caso de fallo.
     * @throws Exception     Lanzada si el proceso es interrumpido o falla la lectura de buffers.
     */
    private void runProcess(ProcessBuilder builder, int timeoutSeconds, String errorMessage) throws Exception {
        Process process = builder.start();
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);

        assertTrue(finished, "TIMEOUT: " + errorMessage + " (Tiempo excedido)");
        assertEquals(0, process.exitValue(), errorMessage + ". Detalles de la salida:\n" + output);
    }
}