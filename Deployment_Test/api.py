from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/window', methods=['POST'])
def window():
    # Example: Process the JSON data sent with the POST request
    data = request.json  # Assuming JSON data is sent with the POST request
    # You can process the data here as needed

    # For demonstration, let's just return the received data with a message
    return jsonify({"message": "Data received successfully", "yourData": data})

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True, port=5000)
