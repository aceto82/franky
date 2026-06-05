## Context

El README.md actual documenta cómo iniciar el proyecto con comandos manuales (`docker compose up -d`, `export DB_URL=...`, `./mvnw spring-boot:run`) pero no menciona la existencia del script `run.sh` que encapsula todas estas tareas. El script existe desde etapas tempranas del proyecto y es la herramienta recomendada para interactuar con el proyecto.

## Goals / Non-Goals

**Goals:**
- Documentar el script `run.sh` y todas sus opciones (modo interactivo y CLI) en README.md
- Simplificar la sección "Inicio rápido" usando `run.sh` en lugar de comandos manuales

**Non-Goals:**
- Modificar el comportamiento de `run.sh`
- Agregar nuevas funcionalidades al script
- Cambiar la estructura del README más allá de agregar la nueva sección

## Decisions

| Decisión | Opción elegida | Alternativas | Razón |
|---|---|---|---|
| Sección dedicada vs mención breve | Sección separada "Script de utilidad (run.sh)" | Un párrafo en Inicio rápido | El script tiene suficientes comandos para justificar su propia sección con tabla |
| Formato de tabla vs lista | Tabla para comandos CLI | Lista con viñetas | La tabla es más escaneable para 8+ comandos |
| Reemplazar vs mantener inicio rápido manual | Reemplazar comandos manuales con `run.sh` | Mantener ambos | Menos fricción: un solo camino recomendado |
| Documentar modo interactivo | Captura de pantalla textual (menú) | Solo mencionarlo | El menú es informativo y ayuda a nuevos usuarios |

## Risks / Trade-offs

- [Riesgo bajo] Alguien podría tener una versión antigua de `run.sh` sin todos los comandos → El README refleja el script actual del repo, están siempre sincronizados
