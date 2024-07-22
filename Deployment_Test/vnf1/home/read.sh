#!/bin/bash

# Define the register name and index to read
REGISTER_NAME="notify"
INDEX=0

# Named pipe path
PIPE_PATH="/tmp/test1"

# Function to send date to named pipe
send_date_to_pipe() {
    local current_date=$(date)
    echo "BENIGN" > "$PIPE_PATH"
}


#pkill simple_switch
#echo "killing all switch"
sleep 2

#simple_switch -i 1@eth0 /home/asym.json &
echo "switch runned"


# Run the simple_switch_CLI tool in a while loop
while true; do
    #result=$(simple_switch_CLI <<< "register_read $REGISTER_NAME")
    result=99
    if [[ $result == *"99"* ]]; then
        echo "Value 99 found in register. Sending date to named pipe."
        send_date_to_pipe
    fi
    sleep 1  # Adjust sleep duration as needed
done
