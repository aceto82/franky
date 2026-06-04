#!/usr/bin/env bash
set -euo pipefail

APP_NAME="franky"

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
CYAN='\033[0;36m'
NC='\033[0m'

info()  { echo -e "${CYAN}[INFO]${NC}  $*"; }
ok()    { echo -e "${GREEN}[OK]${NC}    $*"; }
warn()  { echo -e "${YELLOW}[WARN]${NC}  $*"; }
err()   { echo -e "${RED}[ERROR]${NC} $*" >&2; }

check_mvnw() {
    if [ ! -f "./mvnw" ]; then
        err "mvnw not found in current directory"
        exit 1
    fi
    chmod +x ./mvnw 2>/dev/null || true
}

cmd_dc_up() {
    docker compose up -d
    ok "PostgreSQL started on port 5432"
}

cmd_dc_down() {
    docker compose down
    ok "PostgreSQL stopped"
}

cmd_dc_status() {
    if docker ps --format '{{.Names}}' 2>/dev/null | grep -q "${APP_NAME}"; then
        docker ps --filter "name=${APP_NAME}" --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"
    else
        warn "No containers running for ${APP_NAME}"
    fi
}

cmd_dev() {
    check_mvnw
    if [ -z "${DB_URL:-}" ]; then
        warn "DB_URL not set, exporting defaults for local docker compose"
        export DB_URL=jdbc:postgresql://localhost:5432/franky
        export DB_USER=franky
        export DB_PASS=franky
    fi
    info "Starting ${APP_NAME} with PostgreSQL..."
    ./mvnw spring-boot:run
}

cmd_test() {
    check_mvnw
    info "Running tests with H2 in-memory database..."
    ./mvnw test -Dspring.profiles.active=h2
    ok "Tests passed"
}

cmd_compile() {
    check_mvnw
    info "Compiling..."
    ./mvnw compile -q
    ok "Compilation successful"
}

cmd_clean_compile() {
    check_mvnw
    info "Cleaning and compiling..."
    ./mvnw clean compile -q
    ok "Clean compilation successful"
}

cmd_clean_test() {
    check_mvnw
    info "Clean build and test with H2..."
    ./mvnw clean test -Dspring.profiles.active=h2
    ok "Clean build and tests passed"
}

menu() {
    local title="${APP_NAME} - Menu de opciones"
    echo
    echo -e "${CYAN}╔══════════════════════════════════════╗${NC}"
    echo -e "${CYAN}║  ${title}${NC}"
    echo -e "${CYAN}╚══════════════════════════════════════╝${NC}"
    echo
    echo "  1)  Arrancar PostgreSQL (docker compose)"
    echo "  2)  Iniciar aplicacion (dev)"
    echo "  3)  Ejecutar tests (H2)"
    echo "  4)  Compilar"
    echo "  5)  Clean + compilar"
    echo "  6)  Clean + tests (H2)"
    echo "  7)  Estado de containers"
    echo "  8)  Detener PostgreSQL"
    echo "  0)  Salir"
    echo
}

if [ $# -eq 0 ]; then
    while true; do
        menu
        read -rp "  Opcion: " opt
        echo
        case "$opt" in
            1) cmd_dc_up ;;
            2) cmd_dev ;;
            3) cmd_test ;;
            4) cmd_compile ;;
            5) cmd_clean_compile ;;
            6) cmd_clean_test ;;
            7) cmd_dc_status ;;
            8) cmd_dc_down ;;
            0) echo "Chau!"; exit 0 ;;
            *) warn "Opcion invalida: $opt" ;;
        esac
        echo
        read -rp "Presiona Enter para continuar..."
    done
else
    case "${1}" in
        dc:up)     shift; cmd_dc_up "$@" ;;
        dc:down)   shift; cmd_dc_down "$@" ;;
        dc:status) shift; cmd_dc_status "$@" ;;
        dev)       shift; cmd_dev "$@" ;;
        test)      shift; cmd_test "$@" ;;
        compile)   shift; cmd_compile "$@" ;;
        clean)     shift; cmd_clean_compile "$@" ;;
        clean-test) shift; cmd_clean_test "$@" ;;
        *)
            echo "Uso: ./run.sh [comando]"
            echo
            echo "Comandos:"
            echo "  dc:up       Arrancar PostgreSQL"
            echo "  dc:down     Detener PostgreSQL"
            echo "  dc:status   Estado de containers"
            echo "  dev         Iniciar aplicacion"
            echo "  test        Ejecutar tests (H2)"
            echo "  compile     Compilar"
            echo "  clean       Clean + compilar"
            echo "  clean-test  Clean + tests (H2)"
            echo
            echo "Sin argumentos: menu interactivo"
            exit 1
            ;;
    esac
fi
