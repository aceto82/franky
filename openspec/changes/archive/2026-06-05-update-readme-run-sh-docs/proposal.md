## Why

El script `run.sh` es la herramienta principal para interactuar con el proyecto (iniciar PostgreSQL, compilar, ejecutar tests, generar cobertura, etc.), pero no está documentado en el README. Los desarrolladores que se incorporan al proyecto no saben que existe ni cómo usarlo, lo que genera fricción y consultas innecesarias.

## What Changes

- Agregar sección "Script de utilidad (run.sh)" en README.md
- Documentar el modo interactivo (menú) y sus opciones numeradas
- Documentar el modo CLI con cada comando disponible (`dc:up`, `dc:down`, `dc:status`, `dev`, `test`, `compile`, `clean`, `clean-test`, `coverage`)
- Incluir ejemplo de uso típico (flujo completo: arrancar DB → correr app → ejecutar tests)
- Reemplazar el bloque de "Inicio rápido" actual para que use `run.sh` en lugar de comandos manuales

## Capabilities

### New Capabilities
- `run-sh-docs`: documentación del script run.sh, sus comandos CLI y su menú interactivo

### Modified Capabilities

<!-- No existing capabilities are changing — this is purely additive documentation -->

## Impact

- `README.md`: se agrega una sección nueva y se actualiza "Inicio rápido"
