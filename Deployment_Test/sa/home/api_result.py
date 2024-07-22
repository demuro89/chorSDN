from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/add_result', methods=['POST'])
def add_result():
    # Validate and parse the JSON data sent with the POST request
    data = request.json
    try:
        # Extracting fields from the JSON data
        flow = data.get('Flow')
        value = data.get('Value')
        indicator_type = data.get('Type')
        vnf_id = data.get('VNF_Id')
        confidence = data.get('Confidence')

        # Process the data here (e.g., appending to a file, logging, etc.)

        # For demonstration, let's just log the received data
        print(f"Received data: Flow={flow}, Value={value}, Type={indicator_type}, VNF_Id={vnf_id}, Confidence={confidence}")

        # Return a response indicating successful processing
        return jsonify({
            "message": "Result data received successfully",
            "receivedData": {
                "Flow": flow,
                "Value": value,
                "Type": indicator_type,
                "VNF_Id": vnf_id,
                "Confidence": confidence
            }
        })
    except Exception as e:
        # Handle errors and invalid data
        return jsonify({"error": "Error processing request", "details": str(e)}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
