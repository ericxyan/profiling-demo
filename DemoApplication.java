import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoApplication {

  public static void main(String[] args) throws IOException {
    // wait for press any key to start the demo
    System.out.println("Press any key to start the demo");
    System.in.read();

    List<Thread> cpuThreads = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      Thread cpuThread = new Thread(() -> cpuIntensiveTask(Duration.ofSeconds(3)), "cpu-task-" + i);
      cpuThreads.add(cpuThread);
    }

    for (Thread thread : cpuThreads) {
      thread.start();
    }

    System.out.println("All threads started ...");

//    Thread ioThread = new Thread(() -> ioIntensiveTask(), "ioIntensiveTask");
//    ioThread.start();
    System.in.read();
  }

  public static void cpuIntensiveTask(Duration duration) {
//    System.out.println("Start ...");
    try {
      for (int i = 0; i < 5; i++) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime < duration.toMillis()) {
          double x = Math.sqrt(Math.random());
        }
        Thread.sleep(1000);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

//    System.out.println("End ...");
    // java -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false DemoApplication
  }

  public static void ioIntensiveTask() {
    System.out.println("Start io task ...");

    try {
      for (int i = 0; i < 10; i++) {
        httpRequest();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    System.out.println("End io task ...");
  }

  public static void httpRequest() {
    try {
      URL url = new URL("http://localhost:8080/data");
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      int responseCode = conn.getResponseCode();
      System.out.println("Response Code : " + responseCode);

      BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String output;
      StringBuffer response = new StringBuffer();

      while ((output = in.readLine()) != null) {
        response.append(output);
      }

      in.close();

//      System.out.println("Response: " + response.toString());

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
