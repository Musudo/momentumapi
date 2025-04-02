#!/bin/bash

# don't forget to make this script executable: chmod +x seed-db.sh

echo "Choose an option:"
echo "1. Populate DB"
echo "2. Depopulate DB"
echo "3. Repopulate DB"

# shellcheck disable=SC2162
read -p "Enter choice [1-3]: " choice

case $choice in
  1)
    docker-compose run --rm app --populate-db
    ;;
  2)
    docker-compose run --rm app --depopulate-db
    ;;
  3)
    docker-compose run --rm app --repopulate-db
    ;;
  *)
    echo "Invalid choice. You had one job."
    exit 1
    ;;
esac
