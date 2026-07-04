# E-Commerce Body Painting — Backend

Backend de una plataforma de e-commerce especializada en insumos de body painting, desarrollado como Trabajo Práctico Integrador para la materia **Metodología de Sistemas II** (UTN FRVM). Construido en equipo (Grupo 4) a lo largo de tres sprints SCRUM.

El frontend (React + Vite) vive en un repositorio aparte: [MetodologiaDeSistemasIIFrontend](https://github.com/EfrainCruzTurrin/MetodologiaDeSistemasIIFrontend).

## Funcionalidades principales

- Catálogo de productos y kits, con cálculo dinámico de stock.
- Asistente de reposición de stock impulsado por IA (integración con n8n + Groq LLaMA 3.3 vía webhooks).
- Sistema de cupones de descuento.
- Envío automático de emails de confirmación de pedido (n8n + Gmail OAuth2).
- Rol de vendedor con panel de gestión de pedidos.
- Autenticación y autorización basada en JWT.

## Stack tecnológico

- Java 21
- Spring Boot 4
- Spring MVC + Spring WebFlux
- Spring Data JPA
- Spring Security + JWT (jjwt)
- Spring Mail
- Spring Actuator
- Base de datos H2
- Lombok
- Maven

## Arquitectura

La automatización de emails y el asistente de reposición con IA se resolvieron con **n8n**, que se comunica con esta API vía webhooks — no son dependencias del proyecto Java, sino workflows externos.

## Cómo levantar el proyecto localmente

### Prerrequisitos
- JDK 21
- Maven (o el wrapper incluido, `./mvnw`)

### Pasos

```bash
git clone https://github.com/josepani299/MetodologiaDeSistemasII.git
cd MetodologiaDeSistemasII
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`. La consola de H2 puede consultarse en `/h2-console` (revisar `application.properties` para la configuración de conexión).

> Las credenciales de Gmail OAuth2 y los webhooks de n8n no están incluidos en el repositorio por motivos de seguridad — deben configurarse por separado.

## Equipo

Proyecto desarrollado en equipo (Grupo 4) para la materia Metodología de Sistemas II, Tecnicatura Universitaria en Programación — UTN Facultad Regional Villa María.
