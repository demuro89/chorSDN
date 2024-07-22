from flask import Flask, request, jsonify
import subprocess, os

app = Flask(__name__)

@app.route('/verify', methods=['POST'])
def verify():
    if request.is_json:
        # Parse the JSON into a Python dictionary
        req_data = request.get_json()

        # Extract "Model" and "Data_Set_Type" from the request
        model = req_data.get('Model')
        dataset_type = req_data.get('Data_Set_Type')

        if not model or not dataset_type:
            return jsonify({"error": "Missing Model or Data_Set_Type"}), 400

        # Here, you would add your logic to utilize the model and dataset_type
        # For demonstration, we'll just return them
        # Define the Bash command
        bash_command = "/home/demuro89/workspace/chorsdn/vnf2/home/ml.py"
        os.system(bash_command + "> /tmp/log &")
        # Execute the Bash command
        #result = subprocess.run(bash_command, shell=True, check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        # Check if the command executed successfully
        if False and result.returncode == 0:
            print("Command executed successfully.")
            # Print the output
            print("Output:")
            print(result.stdout)
        elif False:
            # Print the error
            print("Error:", result.stderr)

        return jsonify({
            "Model": model,
            "Data_Set_Type": dataset_type,
            "Status": "Received"
        }), 200
    else:
        return jsonify({"error": "Request must be JSON"}), 415

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)
