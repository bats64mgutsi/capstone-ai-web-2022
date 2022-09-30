# Script to setup the aiweb SQL database for use with the aiweb application.
# 
# 23 Sept 2022
#
# The AIWeb team

from getpass import getpass
from subprocess import run, PIPE
from hashlib import sha256

def runSql(dbUsername: str, drivingInput: str):
    completedProcess = run(['mysql', '-u', dbUsername, "-p"], stdout=PIPE,
        input=drivingInput, encoding='ascii')
    if(completedProcess.returncode != 0):
        exit(completedProcess.returncode)

def insertAdmins(dbUsername: str, admins):
    drivingInput = "USE aiweb;\n"
    for admin in admins:
        username = admin["username"]
        password = admin["password"]

        hashedPassword = sha256(password.encode("utf-8")).hexdigest()
        drivingInput += f'INSERT INTO admins (username, hashedPassword) VALUES ("{username}", "{hashedPassword}");\n'

    print("Updating system admins. You will be asked for your MySQL database password.")
    runSql(dbUsername, drivingInput)
    print("Successfully inserted system administartors\n")

def main():
    print(
        "Hello, this script will help you setup your MySQL database for use with the aiweb application.",
        "This script assumes you are following the installation manual that is included with this release.\n")

    dbSetupScript = open("db_setup.sql").read()

    dbUsername = input("Enter your MYSQL database username: ")
    drivingInput = dbSetupScript + "\nquit\n"

    print("Database setup is starting. You will be asked for your MySQL database password.")
    runSql(dbUsername, drivingInput)

    print("Database setup complete!\n")
    print("Now let's add system administrators to the system.")
    admins = []
    while True:
        username = input("Enter administrator email (Enter quit to save): ")
        if(username.strip().lower() == "quit"):
            break

        password = getpass(f"Enter password for administator {username} (hidden input): ")
        confirmPassword = getpass(f"Enter password for administator {username} (hidden input): ")
        if(password != confirmPassword):
            print("Passwords don't match. Let's try adding the administrator again.\n")
            continue

        admins.append({"username": username, "password": password})
        print(f"Captured administrator {username}\n")
        
    insertAdmins(dbUsername, admins)
    print("Setup complete! The system database is now ready for use. Check the manual for the next steps.\n")

if __name__ == "__main__":
    main()
