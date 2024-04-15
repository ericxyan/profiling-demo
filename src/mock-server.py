# create a mock http server that listens on port 8080 and serves a mock 1 MB dummy data via endpoint GET /data

import http.server
import socketserver
import time

class MyHttpRequestHandler(http.server.SimpleHTTPRequestHandler):
    def do_GET(self):
        if self.path == '/data':
            self.send_response(200)
            self.send_header('Content-type', 'text/plain')
            self.end_headers()
            # Generate 10 MB of dummy data
            dummy_data = 'A' * (1024 * 1024 * 10)
            self.wfile.write(bytes(dummy_data, 'utf8'))
        else:
            self.send_response(404)
            self.end_headers()

# Create an object of the above class
handler_object = MyHttpRequestHandler

PORT = 8080
my_server = socketserver.TCPServer(("localhost", PORT), handler_object)

# Start the server
my_server.serve_forever()
