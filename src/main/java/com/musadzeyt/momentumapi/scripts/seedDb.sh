#!/bin/bash

# seed-db.sh - Script to (re)populate or clear the application's database via Docker Compose
#
# Usage:
#   ./seed-db.sh           # prompts for choice
#   ./seed-db.sh [1|2|3]   # run non-interactively by passing the option
#
# Options:
#   1: Populate DB        - Runs the application in a temporary container to populate the database.
#   2: Depopulate DB      - Clears or drops test data from the database.
#   3: Repopulate DB      - Clears existing data then populates the database anew.
#
# Note:
# - Ensure you have Docker and Docker Compose installed and running.
# - The script invokes commands inside the 'app' service defined in docker-compose.yml.
# - To enable this script, run: chmod +x seed-db.sh
#
# Alternative modes (uncomment to use):
#   # Run commands directly via Java jar:
#   #  java -jar app.jar --server.port=9000 --populate-db
#   #  java -jar app.jar --server.port=9000 --depopulate-db
#   #  java -jar app.jar --server.port=9000 --repopulate-db

# Determine choice: support passing argument or interactive prompt
if [[ -n "$1" ]]; then
  choice="$1"
else
  echo "Choose an option:"
  echo "1) Populate DB"
  echo "2) Depopulate DB"
  echo "3) Repopulate DB"
  read -p "Enter choice [1-3]: " choice
fi

# Execute the corresponding command
case "$choice" in
  1)
    echo "Populating database..."
    docker-compose run --rm app --populate-db
    ;;
  2)
    echo "Depopulating database..."
    docker-compose run --rm app --depopulate-db
    ;;
  3)
    echo "Repopulating database..."
    docker-compose run --rm app --repopulate-db
    ;;
  *)
    echo "Invalid choice: '$choice'. Please enter 1, 2, or 3."
    exit 1
    ;;
esac
