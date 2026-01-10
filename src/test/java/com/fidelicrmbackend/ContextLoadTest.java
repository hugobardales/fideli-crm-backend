package com.fidelicrmbackend;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Prueba de humo (Smoke Test) para la verificación del contexto de la aplicación.
 * * <p>Esta clase se encarga de validar que el ecosistema de Spring Boot, las
 * configuraciones de los Beans y las conexiones con la infraestructura
 * (definidas en application.yml) se inicialicen correctamente sin errores críticos.</p>
 * * @author Tu Nombre (o el nombre de tu perfil de GitHub)
 * @since 1.0.0
 * @version 2026.01.10
 */
@SpringBootTest
@ActiveProfiles("test") // Opcional: si usas perfiles específicos para pruebas
@DisplayName("Verificación de Carga de Contexto de la Aplicación")
class ContextLoadTest {

    /**
     * Verifica que el contenedor de Inyección de Dependencias de Spring
     * pueda arrancar todos los componentes definidos en el proyecto.
     * * Si este método termina sin lanzar excepciones, se garantiza que la
     * configuración base del proyecto es válida.
     */
    @Test
    @DisplayName("Debería cargar el contexto sin errores")
    void contextLoads() {
        // No se requiere lógica adicional.
        // Si el contexto no carga, JUnit marcará este test como fallido automáticamente.
    }
}