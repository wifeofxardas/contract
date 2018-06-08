package wifeofxardas.com.github;

import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.Helper;
import org.neo.smartcontract.framework.services.neo.Storage;
import org.neo.smartcontract.framework.services.neo.Runtime;

public class SecondPriceAuction extends org.neo.smartcontract.framework.SmartContract {

    public static Object Main(String operation, Object[] args){
        Runtime.log(operation);

        if(args.length == 1) Runtime.log((String) args[0]);
        if(args.length == 2) Runtime.log((String) args[1]);

        if (operation.equals("openLot")) {
            return SecondPriceAuction.openLot();
        } else if (operation.equals("cancelLot")) {
            return SecondPriceAuction.cancelLot();
        } else if (operation.equals("payTo")) {
            return SecondPriceAuction.payTo();
        } else if (operation.equals("confirmPay")) {
            return SecondPriceAuction.confirmPay();
        }

        Storage.put(Storage.currentContext(), "Greeting to the World", "Hello World!");
        return Storage.get(Storage.currentContext(),"Greeting to the World");
    }

    public static Object openLot () {
        return "a";
    }

    public static Object cancelLot () {
        return "a";
    }

    public static Object payTo () {
        return "a";
    }

    public static Object confirmPay () {
        return "a";
    }
}