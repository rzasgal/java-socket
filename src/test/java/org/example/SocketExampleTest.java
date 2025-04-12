package org.example;

import java.io.IOException;
import org.junit.jupiter.api.Test;

class SocketExampleTest {

  @Test
  void testSingleGetRequest() throws IOException {
    SocketExample socketExample = new SocketExample();
    System.out.println(socketExample.getRequest("localhost", "/", 7000));
  }

}