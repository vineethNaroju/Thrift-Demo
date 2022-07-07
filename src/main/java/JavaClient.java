
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;


import tutorial.*;
import shared.*;

public class JavaClient {

    public static void main(String[] args) {

        // System.exit(0);`

        try {
            TTransport transport = new TSocket("localhost", 9090);

            transport.open();


            TProtocol protocol = new TBinaryProtocol(transport);

            Calculator.Client client = new Calculator.Client(protocol);

            perform(client);

            transport.close();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    private static void perform(Calculator.Client client) throws TException {
        client.ping();

        System.out.println("ping()");

        int sum = client.add(1, 1);

        System.out.println("1+1=" + sum);

        Work work = new Work();

        work.op = Operation.DIVIDE;
        work.num1 = 1;
        work.num2 = 0;

        try {
            int res = client.calculate(1, work);
        } catch (InvalidOperation iop) {
            System.out.println("invalid operation: " + iop.why);
        }

        SharedStruct sharedStruct = client.getStruct(1);

        System.out.println("check log: " + sharedStruct.value);
    }
}
