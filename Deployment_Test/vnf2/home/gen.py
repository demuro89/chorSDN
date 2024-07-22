import pandas as pd
import numpy as np

# Parameters for the dataset
n_samples = 1000
ip_addresses = ["192.168.1.{}".format(i) for i in range(1, 255)]
ports = range(1024, 65536)
packet_lengths = range(40, 1501)  # Typical range for packet size (in bytes)

# Generate synthetic data
data = {
    "packet_length": np.random.choice(packet_lengths, n_samples),
    "src_ip": np.random.choice(ip_addresses, n_samples),
    "src_port": np.random.choice(ports, n_samples),
    "dst_ip": np.random.choice(ip_addresses, n_samples),
    "dst_port": np.random.choice(ports, n_samples),
    "label": np.random.choice([0, 1], n_samples, p=[0.7, 0.3])  # 70% normal, 30% anomalous
}

df = pd.DataFrame(data)

# Save the dataset to a CSV file
df.to_csv("traffic_data.csv", index=False)
