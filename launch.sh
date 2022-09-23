
# This is the launch script for the AI-Web application
# Use this script to start the application.

# This script assumes that the frontend has already been built
# and ready to be served by the java server.

# [ -z "${MY_ENV}" ] is true when $MY_ENV is not set
if [[ -z "${AI_WEB_DB_USERNAME}" ]]; then
    echo "ERROR: Environment variable AI_WEB_DB_USERNAME should be set the username for the MySQL database to use."
    exit 1
fi

if [[ -z "${AI_WEB_DB_PASSWORD}" ]]; then
    echo "ERROR: Environment variable AI_WEB_DB_PASSWORD should be set the password for the MySQL database to use."
    exit 1
fi

cd backend
source ./gradlew run