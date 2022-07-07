import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import tutorial.Calculator;

public class JavaServer {

    public static CalculatorHandler handler;
    public static Calculator.Processor processor;

    public static void main(String[] args) {
        try {
            handler = new CalculatorHandler();
            processor = new Calculator.Processor<>(handler);

            new Thread(() -> {
                simple(processor);
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void simple(Calculator.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);

            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

            System.out.println("Starting a simple server ...");

            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
