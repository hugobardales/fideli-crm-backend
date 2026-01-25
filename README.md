# 🚀 Sentiment Analysis Backend API

<div align="center">

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen.svg?style=for-the-badge&logo=spring-boot)
![Java](https://img.shields.io/badge/Java-21-orange.svg?style=for-the-badge&logo=openjdk)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg?style=for-the-badge&logo=mysql)
![Redis](https://img.shields.io/badge/Redis-7.2-red.svg?style=for-the-badge&logo=redis)
![Docker](https://img.shields.io/badge/Docker-Container-blue.svg?style=for-the-badge&logo=docker)

**API REST de Análisis de Sentimiento Multilingüe con Traducción Automática en Tiempo Real**

*Proyecto desarrollado para el Hackathon Oracle ONE*

[📖 Documentación](#-documentación) • [🚀 Guía Rápida](#-guía-rápida) • [🔧 Configuración](#-configuración) • [📊 Endpoints](#-endpoints) • [🐳 Docker](#-docker)

</div>

---

## 📋 Tabla de Contenido

- [🎯 Sobre el Proyecto](#-sobre-el-proyecto)
- [✨ Características Principales](#-características-principales)
- [🏗️ Arquitectura](#️-arquitectura)
- [🚀 Guía Rápida](#-guía-rápida)
- [🔧 Configuración](#-configuración)
- [📊 API Endpoints](#-api-endpoints)
- [🔐 Autenticación](#-autenticación)
- [🐳 Docker & Docker Compose](#-docker--docker-compose)
- [🧪 Testing](#-testing)
- [📈 Monitoramiento](#-monitoramiento)
- [🤝 Contribución](#-contribución)

---

## 🎯 Sobre el Proyecto

Este backend es una **API REST robusta y escalable** diseñada para analizar el sentimiento de textos en múltiples idiomas utilizando técnicas de Machine Learning y traducción automática en tiempo real.

### 🌍 Característica Única: Traducción Inteligente
- **Análisis multilingüe**: Textos en cualquier idioma son automáticamente traducidos al español antes del análisis
- **Cache inteligente**: Las traducciones frecuentes se almacenan en Redis para optimizar el rendimiento
- **Procesamiento paralelo**: Múltiples traducciones simultáneas mediante `WebClient` y `CompletableFuture`

---

## ✨ Características Principales

### 🧠 Análisis de Sentimiento
- **Machine Learning Integration**: Conexión con microservicio Python para predicciones
- **Procesamiento Individual y en Lote**: Soporte para CSV con múltiples textos
- **Estadísticas en Tiempo Real**: Métricas agregadas de todos los análisis

### 🌐 Internacionalización
- **Traducción Automática**: Integración con LibreTranslate (100+ idiomas)
- **Cache de Traducciones**: Redis para traducciones frecuentes (TTL: 1 hora)
- **API REST i18n**: Endpoints para gestionar traducciones dinámicas

### 🔐 Seguridad
- **JWT Authentication**: Tokens seguros con expiración configurable
- **Roles de Usuario**: Sistema de autorización basado en roles
- **Spring Security**: Protección comprehensive contra ataques comunes

### 📊 Gestión de Datos
- **MySQL 8.0**: Base de datos persistente con Flyway migrations
- **Redis Cache**: Sistema de caché distribuido para alto rendimiento
- **JPA/Hibernate**: ORM optimizado con configuración avanzada

### 🚀 Performance
- **Caching Multinivel**: Redis + Spring Cache
- **Procesamiento Asíncrono**: Traducciones concurrentes
- **Connection Pooling**: HikariCP para gestión eficiente de conexiones

---

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Backend API   │    │  Data Science   │
│   (React)       │◄──►│   (Spring Boot) │◄──►│    (Python)     │
│   :3000         │    │   :8080         │    │   :8000         │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                               │
                    ┌──────────┼──────────┐
                    │          │          │
            ┌───────▼───┐ ┌────▼────┐ ┌───▼─────┐
            │   MySQL   │ │  Redis  │ │LibreT.  │
            │   :3306   │ │ :6379   │ │ :5000   │
            └───────────┘ └─────────┘ └─────────┘
```

### 📁 Estructura del Proyecto

```
src/main/java/com/hackaton/sentiment/
├── 📂 controller/          # Endpoints REST
│   ├── SentimentController.java
│   ├── AuthController.java
│   ├── I18nController.java
│   └── ...
├── 📂 service/             # Lógica de negocio
│   ├── SentimentService.java
│   ├── TranslationService.java
│   └── impl/
├── 📂 repository/          # Acceso a datos
├── 📂 entity/             # Modelos JPA
├── 📂 dto/                # Data Transfer Objects
├── 📂 security/           # Configuración de seguridad
├── 📂 config/             # Configuración Spring
└── 📂 util/               # Utilidades
```

---

## 🚀 Guía Rápida

### 📋 Prerrequisitos

- **Java 21** o superior
- **Maven 3.9+**
- **MySQL 8.0+**
- **Redis 7.2+**
- **Docker & Docker Compose** (opcional pero recomendado)

### ⚡ Inicio Rápido con Docker

```bash
# 1. Clonar el repositorio
git clone <repository-url>
cd backend/sentiment-backend

# 2. Configurar variables de entorno
cp .env.example .env
# Editar .env con tus credenciales

# 3. Iniciar todos los servicios
docker-compose up -d

# 4. Verificar estado
docker-compose ps
```

### 🛠️ Ejecución Local

```bash
# 1. Iniciar servicios dependientes
docker-compose up -d mysql redis libretranslate

# 2. Configurar base de datos
mysql -u root -p < init-scripts/01-init.sql

# 3. Ejecutar la aplicación
./mvnw spring-boot:run

# 4. Acceder a la API
curl http://localhost:8080/health
```

---

## 🔧 Configuración

### 🌍 Perfiles Disponibles

| Perfil | Descripción | Uso |
|--------|-------------|-----|
| `local` | Desarrollo local con MySQL local | 💻 Desarrollo |
| `docker` | Producción con Docker Compose | 🐳 Producción |
| `test` | Testing con H2 en memoria | 🧪 Testing |

### 📝 Variables de Entorno

```bash
# Base de Datos
DB_ADMIN0_1_PASSWORD=tu_password_secreto

# JWT
JWT_SECRET=8jLc0f8Tz/b3CEIIu5u5o7W6KbFc3cWWnmlQMULdSqA=
JWT_EXPIRATION=86400000

# Servicios Externos
LIBRETRANSLATE_URL=http://localhost:5000
ML_SERVICE_URL=http://localhost:8000
```

### 🔌 Configuración de Aplicación

El archivo `application.yml` contiene toda la configuración:

```yaml
# Ejemplo de configuración clave
spring:
  profiles:
    active: local  # Cambiar a 'docker' para producción
  
  datasource:
    url: jdbc:mysql://localhost:3306/sentimentdb
    username: root
    password: ${DB_ADMIN0_1_PASSWORD}
  
  data:
    redis:
      host: localhost
      port: 6379

libretranslate:
  url: http://localhost:5000
  enabled: true
  cache-enabled: true
```

---

## 📊 API Endpoints

### 🔐 Autenticación

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `POST` | `/auth/register` | Registro de nuevos usuarios |
| `POST` | `/auth/login` | Inicio de sesión y obtención de JWT |

### 🧠 Análisis de Sentimiento

| Método | Endpoint | Descripción | Autenticación |
|--------|----------|-------------|---------------|
| `POST` | `/sentiment` | Analizar sentimiento de un texto | ✅ Requerida |
| `POST` | `/sentiment/batch` | Análisis en lote (CSV) | ✅ Requerida |
| `GET` | `/sentiment/stats` | Estadísticas globales | ❌ Pública |
| `GET` | `/sentiment/my-analyses` | Historial personal | ✅ Requerida |

### 🌐 Internacionalización

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/i18n/languages` | Lista idiomas disponibles |
| `GET` | `/i18n/translations/{lang}` | Traducciones por idioma |
| `POST` | `/i18n/translate` | Traducir texto específico |

### 💡 Ejemplos de Uso

#### Analizar Sentimiento
```bash
curl -X POST http://localhost:8080/sentiment \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{
    "text": "Este producto es increíble, me encanta mucho",
    "language": "es"
  }'
```

#### Respuesta Esperada
```json
{
  "id": 123,
  "text": "Este producto es increíble, me encanta mucho",
  "sentiment": "Positivo",
  "probability": 0.92,
  "language": "es",
  "createdAt": "2026-01-25T10:30:00Z"
}
```

#### Análisis en Lote (CSV)
```bash
curl -X POST http://localhost:8080/sentiment/batch \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -F "file=@comentarios.csv"
```

---

## 🔐 Autenticación

### 🎫 JWT Token Flow

1. **Login**: Usuario envía credenciales a `/auth/login`
2. **Token**: Sistema retorna JWT válido por 24h
3. **Access**: Incluir token en header `Authorization: Bearer <token>`

### 🛡️ Seguridad Implementada

- **Password Encoding**: BCrypt con salt aleatorio
- **CORS Configurable**: Orígenes permitidos configurables
- **Rate Limiting**: Protección contra ataques de fuerza bruta
- **Input Validation**: Validación comprehensive con Bean Validation

---

## 🐳 Docker & Docker Compose

### 🏗️ Arquitectura Docker

La aplicación está diseñada con **multi-stage builds** para optimización:

```dockerfile
# Stage 1: Build (Maven + Java 21)
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
# ... compilación y testing ...

# Stage 2: Runtime (JRE ligero)
FROM eclipse-temurin:21-jre-alpine
# ... ejecución optimizada ...
```

### 📦 Servicios del Ecosistema

| Servicio | Imagen | Puerto | Función |
|----------|--------|--------|---------|
| **backend** | Custom Build | 8080 | API Spring Boot |
| **mysql** | mysql:8.0 | 3307 | Base de datos |
| **redis** | redis:alpine | 6379 | Cache distribuido |
| **libretranslate** | libretranslate/libretranslate | 5000 | Traducción IA |

### 🚀 Comandos Docker

```bash
# Iniciar todos los servicios
docker-compose up -d

# Ver logs en tiempo real
docker-compose logs -f backend

# Escalar backend (balanceo de carga)
docker-compose up -d --scale backend=3

# Detener y limpiar
docker-compose down -v
```

---

## 🧪 Testing

### 📊 Cobertura de Pruebas

El proyecto incluye **testing comprehensivo** con JaCoCo:

```bash
# Ejecutar todos los tests
./mvnw test

# Generar reporte de cobertura
./mvnw jacoco:report

# Verificar cobertura (mínimo 80%)
./mvnw verify
```

### 🧪 Tipos de Tests

- **Unit Tests**: Servicios y utilidades con JUnit 5 + Mockito
- **Integration Tests**: Repositorios con Testcontainers
- **API Tests**: Endpoints REST con MockMvc
- **Security Tests**: Autenticación y autorización

### 📈 Reportes Generados

- **JaCoCo**: Cobertura de código en `target/site/jacoco`
- **Surefire**: Resultados de tests en `target/surefire-reports`
- **Javadoc**: Documentación API en `target/site/apidocs`

---

## 📈 Monitoramiento

### 🔍 Endpoints de Salud

| Endpoint | Descripción |
|----------|-------------|
| `/health` | Estado general de la aplicación |
| `/health/diskSpace` | Espacio en disco disponible |
| `/health/db` | Conexión con base de datos |
| `/health/redis` | Conexión con Redis |

### 📊 Métricas Disponibles

- **Spring Boot Actuator**: Métricas integradas
- **Custom Health Checks**: Verificación de servicios externos
- **Performance Monitoring**: Tiempos de respuesta de API
- **Cache Statistics**: Hit ratio de Redis

### 📋 Logging Configurado

```yaml
logging:
  level:
    com.hackaton.sentiment: INFO
    org.springframework.cache: INFO
  file:
    name: logs/sentiment-app.log
```

---

## 🤝 Contribución

### 🔄 Flujo de Trabajo

1. **Fork** del repositorio
2. **Branch** feature/nueva-funcionalidad
3. **Commit** con mensajes convencionales
4. **Push** al branch
5. **Pull Request** con descripción detallada

### 📝 Estándares de Código

- **Java 21**: Usar features modernas (records, pattern matching)
- **Lombok**: Reducir código repetitivo
- **Swagger**: Documentación de API actualizada
- **Tests**: Mínimo 80% cobertura

### 🧪 PR Requirements

- [ ] Tests funcionando
- [ ] Código formateado
- [ ] Documentación actualizada
- [ ] SonarQube sin issues críticos

---

## 📚 Documentación Adicional

- **[API Documentation](http://localhost:8080/swagger-ui.html)**: Swagger UI interactiva
- **[Javadoc](target/site/apidocs/index.html)**: Documentación de código
- **[JaCoCo Report](target/site/jacoco/index.html)**: Cobertura de tests
- **[JWT Setup](JWT_SETUP.md)**: Configuración detallada de JWT

---



*© 2026 Hackathon Oracle ONE - Backend Team. Todos los derechos reservados.*

</div>
