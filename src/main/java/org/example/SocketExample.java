package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketExample {

  private static final String CRLF = "\r\n";

  public String getRequest(String host, String path, int port) throws IOException {
    try (Socket socket = new Socket()) {
      socket.connect(new InetSocketAddress(InetAddress.getByName(host), port));
      try (BufferedWriter writer = new BufferedWriter(
          new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
          BufferedReader reader = new BufferedReader(
              new InputStreamReader(socket.getInputStream()))) {
        writeRequest(writer, host, path);
        return readResponse(reader);
      }
    }
  }

  private static void writeRequest(BufferedWriter writer, String host, String path)
      throws IOException {
    writer.write("GET " + path + " HTTP/1.1" + CRLF);
    writer.write("Host: " + host + CRLF);
    writer.write("Connection: close" + CRLF);
    writer.write(CRLF);
    writer.flush();
  }

  private static String readResponse(BufferedReader reader) throws IOException {
    StringBuilder response = new StringBuilder();
    String line = null;
    while ((line = reader.readLine()) != null) {
      response.append(line).append(System.lineSeparator());
    }
    return response.toString();
  }
}
