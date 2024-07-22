from flask import Flask, request, jsonify
import os

app = Flask(__name__)

@app.route('/signature_rule', methods=['POST'])
def signature_rule():
    # Validate and parse the JSON data sent with the POST request
    data = request.json
    try:
        # Extracting fields from the JSON data
        signature = data.get('Signature')
        data_time = data.get('Data_Time')
        ip_source = data.get('IP_source')
        ip_dest = data.get('Ip_dest')
        port_source = data.get('port_source')
        port_dest = data.get('port_dest')
        payload = data.get('payload')

        # Format the data into a single line of text
        formatted_data = f"Signature: {signature}, Data_Time: {data_time}, IP_source: {ip_source}, Ip_dest: {ip_dest}, port_source: {port_source}, port_dest: {port_dest}, payload: {payload}\n"

        # Append the formatted data to the file
        with open("rules.ids", "a") as file:
            file.write(formatted_data)

        # Return a response indicating successful processing
        return jsonify({
            "message": "Signature rule data received and logged successfully",
            "receivedData": {
                "Signature": signature,
                "Data_Time": data_time,
                "IP_source": ip_source,
                "Ip_dest": ip_dest,
                "port_source": port_source,
                "port_dest": port_dest,
                "payload": payload
            }
        })
    except Exception as e:
        # Handle errors and invalid data
        return jsonify({"error": "Error processing request", "details": str(e)}), 400

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)

