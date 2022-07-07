import org.apache.thrift.TException;
import shared.SharedStruct;
import tutorial.Calculator;
import tutorial.InvalidOperation;
import tutorial.Work;

import java.util.HashMap;

public class CalculatorHandler implements Calculator.Iface {


    private HashMap<Integer, SharedStruct> log;

    public CalculatorHandler() {
        log = new HashMap<>();
    }

    public void ping() {
        System.out.println("ping()");
    }

    @Override
    public int add(int num1, int num2) throws TException {
        System.out.println("add(" + num1 + "," + num2 + ")");
        return num1 + num2;
    }

    @Override
    public int calculate(int logid, Work w) throws InvalidOperation {
        System.out.println("calculate(" + logid + ",{" + w.op + "," + w.num1 + "," + w.num2 + "})");
        int res = 0;
        switch (w.op) {
            case ADD:
                res = w.num1 + w.num2;
                break;
            case SUBTRACT:
                res = w.num1 - w.num2;
                break;
            case MULTIPLY:
                res = w.num1 * w.num2;
                break;
            case DIVIDE:
                if(w.num2 == 0) {
                    InvalidOperation badop = new InvalidOperation();
                    badop.whatOp = w.op.getValue();
                    badop.why = "Cannot divide by 0";
                    throw badop;
                }

                res = w.num1 / w.num2;
                break;
            default:
                InvalidOperation badop = new InvalidOperation();
                badop.whatOp = w.op.getValue();
                badop.why = "Unknown operation";
                throw badop;
        }

        SharedStruct ss = new SharedStruct();
        ss.key = logid;
        ss.value = Integer.toString(res);

        log.put(logid, ss);

        return res;
    }

    @Override
    public void zip() {
        System.out.println("wtf is zip()");
    }

    @Override
    public SharedStruct getStruct(int key) {
        System.out.println("getStruct(" + key + ")");
        return log.get(key);
    }
}
