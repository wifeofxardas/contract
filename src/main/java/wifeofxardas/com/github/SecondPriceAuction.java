package wifeofxardas.com.github;

import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.Helper;
import org.neo.smartcontract.framework.services.neo.Storage;

public class SecondPriceAuction extends org.neo.smartcontract.framework.SmartContract {

    public static byte[] Main(String operation, Object[] args){
        if (operation.equals("openLot")) {

        } else if (operation.equals("cancelLot")) {

        } else if (operation.equals("payTo")) {

        } else if (operation.equals("confirmPay")) {

        }

        Storage.put(Storage.currentContext(), "Greeting to the World", "Hello World!");
        return Storage.get(Storage.currentContext(),"Greeting to the World");
    }

    public String openLot () {
        return "a";
    }

}