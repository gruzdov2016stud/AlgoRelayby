package process;

import objects.data.enums.SeqT;
import objects.data.enums.Validity;
import objects.data.typeData.Quality;
import objects.info.ACT;
import objects.measured.SEQ;

public class main {
    public static void main(String[] args) {
//        Quality qualityIa, qualityIb, qualityIc, qualityUa, qualityUb = null, qualityUc = new Quality();
//        qualityUb.getValidity().setValue(Validity.GOOD);
//        System.out.println();
//        SEQ SeqA = new SEQ();
//        boolean a = SeqA.getSeqDIR().getValue() == SeqT.DIR;
//
//
//        String binaryValue = Integer.toBinaryString((3070));
//        String[] stringBinaryValue = (binaryValue).split("");
//        if (stringBinaryValue.length < 13) {
//            for (int i = 0; i < (13 - stringBinaryValue.length); i++) {
//                binaryValue = binaryValue+"0";
//            }
//            stringBinaryValue = binaryValue.split("");
//            System.out.println(stringBinaryValue);
//        }
        int count = 0;
        boolean trip = false;
        for (int signal : new int[]{1, 2, 3, 4, 5,6}) {
            count++;
            System.out.println(count);
        }


    }
}
