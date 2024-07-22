#!/bin/python3
import os
import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from scapy.all import sniff, IP, TCP
pipe_path = "/tmp/test"

print("Sto eseguendo come", os.getlogin())

def send_message_to_pipe(message, pipe_path):
    """
    Sends a message to the named pipe.
    """
    with open(pipe_path, 'w') as pipe:
        pipe.write(message + '\n')

def ip_to_int(ip):
    """Convert an IP address from string to integer."""
    octets = ip.split('.')
    return sum(int(octet) * 256 ** i for i, octet in enumerate(reversed(octets)))


# Load and prepare the dataset
data = pd.read_csv('traffic_data.csv')  # Ensure your dataset path is correct

data['src_ip'] = data['src_ip'].apply(ip_to_int)
data['dst_ip'] = data['dst_ip'].apply(ip_to_int)

X = data.drop('label', axis=1)  # Features
y = data['label']  # Target variable (0 for normal, 1 for anomaly)

# Split the dataset into training and testing sets
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Feature scaling
scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

# Train the model
model = RandomForestClassifier(n_estimators=100, random_state=42)
model.fit(X_train_scaled, y_train)

def extract_packet_features(packet):
    if IP in packet and TCP in packet:
        return [len(packet), packet[IP].src, packet[IP].dst, packet[TCP].sport, packet[TCP].dport]
    return None

def preprocess_features(features):
    # Dummy preprocessing function. Replace it with your actual preprocessing logic.
    # This should match how you preprocessed the training data.
    return scaler.transform([features])

def predict_traffic_anomaly(features):
    prediction = model.predict(features)
    return prediction[0]

def packet_callback(packet):
    if IP in packet and TCP in packet:
        src_ip = ip_to_int(packet[IP].src)
        dst_ip = ip_to_int(packet[IP].dst)
        packet_length = len(packet)
        src_port = packet[TCP].sport
        dst_port = packet[TCP].dport

        # Assuming your model is trained on these features in this order
        features = [packet_length, src_ip, src_port, dst_ip, dst_port]
        preprocessed_features = preprocess_features(features)  # Scale or normalize if needed

        prediction = predict_traffic_anomaly(preprocessed_features)
        pred_result = "MALICIOUS" if prediction else "BENIGN"
        # bash_command = "echo "+ pred_result + " > /tmp/test"
        # print(bash_command)
        # os.system(bash_command)
        send_message_to_pipe(pred_result, pipe_path)
        
        print(f"Traffic is {'anomalous' if prediction else 'normal'}: {features}")
    else:
        print("Packet does not contain IP/TCP layers")

def capture_real_time_traffic(interface):
    print(f"Starting traffic capture on interface {interface}...")
    sniff(iface=interface, prn=packet_callback, filter="ip", store=False)

# Define the network interface to monitor, e.g., "eth0", "wlan0"
network_interface = 'lo'

# Start the real-time traffic monitoring and prediction
capture_real_time_traffic(network_interface)
