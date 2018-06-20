package wifeofxardas.com.github;

import org.neo.smartcontract.framework.Helper;
import org.neo.smartcontract.framework.services.neo.Storage;
import org.neo.smartcontract.framework.services.neo.Runtime;

import java.math.BigInteger;

public class SecondPriceAuction extends org.neo.smartcontract.framework.SmartContract {
    public static Object Main(String operation, Object[] args){
        if (operation.equals("openLot")) {
            if (args.length < 4) {
                return false;
            }

            return SecondPriceAuction.openLot(
                    args[0], (String) args[1], (String) args[2],
                    (String) args[3]
            );
        } else if (operation.equals("cancelLot")) {
            if (args.length < 2) {
                return false;
            }

            return SecondPriceAuction.cancelLot(args[0], args[1]);
        } else if (operation.equals("getLots")) {
            if (args.length < 1) {
                return false;
            }

            return SecondPriceAuction.getLots(args[0]);
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

    public static String openLot (Object owner, String name, String desc, String price) {
        if(!Runtime.checkWitness((byte[]) owner)) {
            Runtime.log("Failed witness check");
            return "false";
        }

//        long now = System.currentTimeMillis();
//
//        if((dueTime - now) > 86400000 || (dueTime - now) <= 600000) {
//            Runtime.log("Wrong due date");
//            return "false";
//        }


        BigInteger id = SecondPriceAuction.getId();
        String lotId = SecondPriceAuction.stringConcat("lots.", String.valueOf(id));

        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".owner"), (String) owner);
        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".name"), name);
        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".desc"), desc);
        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".price"), price);
//        Storage.put(
//            Storage.currentContext(),
//            SecondPriceAuction.stringConcat(lotId, ".dueTime"),
//            String.valueOf(dueTime)
//        );
        Storage.put(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".state"), "open");

        SecondPriceAuction.addIdToOwner(owner, String.valueOf(id));

        return "true";
    }

    public static void getLot () {

    }

    public static void closeLot () {

    }

    public static void addIdToOwner (Object owner, String id) {
        String idsListId = SecondPriceAuction.stringConcat(
                String.valueOf(owner), ".lots"
        );

        String currentOwnerIds = Helper.asString(Storage.get(Storage.currentContext(), idsListId));

        Storage.put(
                Storage.currentContext(),
                idsListId,
                SecondPriceAuction.stringConcat(
                        SecondPriceAuction.stringConcat(currentOwnerIds, id),
                        ";"
                )
        );
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

    public static String cancelLot (Object caller, Object id) {
        if(!Runtime.checkWitness((byte[]) caller)) {
            Runtime.log("Failed witness check");
            return "false";
        }

        String lotId = SecondPriceAuction.stringConcat("lots.", String.valueOf(id));
        byte[] owner = Storage.get(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".owner"));

        if (owner == (byte[]) caller) {
            String idsListId = SecondPriceAuction.stringConcat(
                    String.valueOf(caller), ".lots"
            );
            SecondPriceAuction.deleteLot(lotId);

            SecondPriceAuction.deleteLotFromOwner(owner, String.valueOf(id));
        } else {
            return "false";
        }

        return "true";
    }

    public static String deleteLotFromOwner (Object owner, String id) {
        String idsListId = SecondPriceAuction.stringConcat(
                String.valueOf(owner), ".lots"
        );
        String currentOwnerIds = Helper.asString(Storage.get(Storage.currentContext(), idsListId));
        int index = SecondPriceAuction.indexOf(currentOwnerIds, id);



        Storage.put(
            Storage.currentContext(),
            idsListId,
            SecondPriceAuction.stringConcat(
                Helper.asString(
                    Helper.range(
                            Helper.asByteArray(currentOwnerIds), 0, index
                    )
                ),
                Helper.asString(
                        Helper.range(
                                // id.length + 1 bcs we have ';'лщ
                                Helper.asByteArray(currentOwnerIds), index + id.length() + 1, currentOwnerIds.length()
                        )
                )
            )
        );

        return String.valueOf(index);
    }

    public static void deleteLot (String lotId) {
        Storage.delete(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".owner"));
        Storage.delete(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".name"));
        Storage.delete(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".desc"));
        Storage.delete(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".price"));
        Storage.delete(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".dueTime"));
        Storage.delete(Storage.currentContext(), SecondPriceAuction.stringConcat(lotId, ".state"));
    }

    public static String getLots (Object owner) {
        return Helper.asString(
                Storage.get(Storage.currentContext(), SecondPriceAuction.stringConcat((String) owner, ".lots"))
        );
    }

    public static int indexOf (String where, String what) {
        int resultIndex = -1;

        byte[] string = Helper.asByteArray(where);
        byte[] search = Helper.asByteArray(what);

        if (string.length < search.length) {
            return resultIndex;
        }

        for(int currentIndex = 0; currentIndex < string.length; currentIndex++) {
            if (string[currentIndex] == search[0]) {
                resultIndex = currentIndex;

                for (int searchIndex = 0; searchIndex < search.length; searchIndex++) {
                    if (search[searchIndex] != string[currentIndex + searchIndex]) {
                        resultIndex = -1;
                        break;
                    }
                }

                if (resultIndex != -1) {
                    break;
                }
            }
        }

        return resultIndex;
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