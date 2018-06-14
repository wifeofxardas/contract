package wifeofxardas.com.github;

import org.neo.smartcontract.framework.Helper;
import org.neo.smartcontract.framework.services.neo.Storage;
import org.neo.smartcontract.framework.services.neo.Runtime;

import java.math.BigInteger;

public class SecondPriceAuction extends org.neo.smartcontract.framework.SmartContract {
    public static Object Main(String operation, Object[] args){
        Runtime.log(operation);

//        if(args.length == 1) Runtime.log((String) args[0]);
//        if(args.length == 2) Runtime.log((String) args[1]);

        if (operation.equals("openLot")) {
            if (args.length < 4) {
                return false;
            }

            return SecondPriceAuction.openLot(args[0], (String) args[1], (String) args[2], args[3]);
        } else if (operation.equals("cancelLot")) {
            return SecondPriceAuction.cancelLot();
        } else if (operation.equals("payTo")) {
            return SecondPriceAuction.payTo();
        } else if (operation.equals("confirmPay")) {
            return SecondPriceAuction.confirmPay();
        } else if (operation.equals("getId")) {
            return SecondPriceAuction.getId();
        }

        Storage.put(Storage.currentContext(), "Greeting to the World", "Hello World!");
        return Storage.get(Storage.currentContext(),"Greeting to the World");
    }

    public static String openLot (Object owner, String name, String desc, Object price) {
        if(!Runtime.checkWitness((byte[]) owner)) {
            Runtime.log("Failed witness check");
            return "false";
        }

        BigInteger id = SecondPriceAuction.getId();

        Runtime.log(SecondPriceAuction.stringConcat("a", "b"));

        String lotId = SecondPriceAuction.stringConcat("lots.", String.valueOf(id));
        String idsListId = SecondPriceAuction.stringConcat(
            SecondPriceAuction.stringConcat(
               SecondPriceAuction.stringConcat("lots.", String.valueOf(id)), String.valueOf(owner)
            ),
        ".ids"
        );

        String currentOwnerIds = String.valueOf(Storage.get(Storage.currentContext(), idsListId));

        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".owner"), (String) owner);
        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".name"), name);
        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".desc"), desc);

        Storage.put(
            Storage.currentContext(),
                idsListId,
            SecondPriceAuction.stringConcat(
                SecondPriceAuction.stringConcat(currentOwnerIds, ";"),
                String.valueOf(id)
            )
        );

        return "true";
    }

    public static String stringConcat (String a, String b) {
        return Helper.asString(Helper.concat(Helper.asByteArray(a), Helper.asByteArray(b)));
    }

    public static BigInteger getId () {
        BigInteger id = Helper.asBigInteger(Storage.get(Storage.currentContext(), "currentId"));

        if (Helper.asString(Helper.asByteArray(id)) == "") {
            id = BigInteger.valueOf(1);
        } else {
            id = id.add(BigInteger.ONE);
        }

        Storage.put(Storage.currentContext(), "currentId", id);

        return id;
    }

    public static Object cancelLot () {
        return new String[]{"aa", "sb"};
    }

    public static Object payTo () {
//        Map <BigInteger, String> hashMap = new HashMap<BigInteger, String>();
//        hashMap.put(228, "papirosim");
        return "";
    }

    public static Object confirmPay () {
        return "a";
    }
}