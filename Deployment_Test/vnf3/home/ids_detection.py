#!/bin/python3

import os
import time
pipe_path = "/tmp/test2"


def tail(filename, delay=0.1):
    """
    Generator function that yields lines appended to a file.
    """
    with open(filename, 'r') as f:
        # Move file pointer to the end
        f.seek(0, os.SEEK_END)

        while True:
            line = f.readline()
            if not line:
                time.sleep(delay)
                continue
            yield line

def send_message_to_pipe(message, pipe_path):
    """
    Sends a message to the named pipe.
    """
    with open(pipe_path, 'w') as pipe:
        pipe.write(message + '\n')

if __name__ == "__main__":
    # Path to the Suricata log file
    suricata_log_file = "/var/log/suricata/fast.log"
    

    # Start tailing the Suricata log file
    for line in tail(suricata_log_file):
        # Check if the line contains "Chor Test" alert
        if "Chor Test" in line:
            # Send "Malign" message through the named pipe
            send_message_to_pipe("MALICIOUS", pipe_path)
