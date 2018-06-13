package wifeofxardas.com.github;

import org.neo.smartcontract.framework.SmartContract;
import org.neo.smartcontract.framework.Helper;
import org.neo.smartcontract.framework.services.neo.Storage;
import org.neo.smartcontract.framework.services.neo.Runtime;

import java.util.HashMap;
import java.util.Map;

public class SecondPriceAuction extends org.neo.smartcontract.framework.SmartContract {
    public static Object Main(String operation, Object[] args){
        Runtime.log(operation);

        if(args.length == 1) Runtime.log((String) args[0]);
        if(args.length == 2) Runtime.log((String) args[1]);

        if (operation.equals("openLot")) {
            if (args.length < 3) {
                throw new Error("Not enough arguments");
            }
            return SecondPriceAuction.openLot((String) args[0], (String) args[1], (String) args[2]);
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

    public static Object openLot (String owner, String name, String desc) {
        Map <String, String> hashMap = new HashMap<String, String>();

        int id =

        hashMap.put("id", String.valueOf(id);

        hashMap.put("owner", owner);
        hashMap.put("name", name);
        hashMap.put("desc", desc);

        Storage.put(Storage.currentContext(), String.format("%s", owner), hashMap.toString());

        return true;
    }

    public static int getId () {
        int id = Integer.parseInt((String.valueOf(Storage.get(Storage.currentContext(), "currentId"))));

        Storage.put(Storage.currentContext(), "currentId", String.valueOf(id));

        return id;
    }

    public static Object cancelLot () {
        return new String[]{"aa", "sb"};
    }

    public static Object payTo () {
        Map <Integer, String> hashMap = new HashMap<Integer, String>();
        hashMap.put(228, "papirosim");
        return hashMap;
    }

    public static Object confirmPay () {
        return "a";
    }
}