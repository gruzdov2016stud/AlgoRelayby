package nodes.registration;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.enums.Source;
import objects.data.enums.Validity;
import objects.data.typeData.Quality;
import objects.measured.SAV;
@Getter
@Setter
public class ParseQuality extends LN {
    /**Качество мгновенных значений Ia,Ib,Ic,Ua,Ub,Uc представляющего значение данных.*/
    private Quality qualityIa,qualityIb,qualityIc,qualityUa,qualityUb,qualityUc;
    /** Мгновенные значения аналоговых сигналов Ia,Ib,Ic,Ua,Ub,Uc для которых производиться проверка качества*/
    private SAV sQualityIa, sQualityIc, sQualityIb, sQualityUa, sQualityUc, sQualityUb;

    @Override
    public void process() {
        parseQ(sQualityIa, qualityIa = new Quality());
        parseQ(sQualityIb, qualityIb = new Quality());
        parseQ(sQualityIc, qualityIc = new Quality());
        parseQ(sQualityUa, qualityUa = new Quality());
        parseQ(sQualityUb, qualityUb = new Quality());
        parseQ(sQualityUc, qualityUc = new Quality() );
    }

    private void parseQ(SAV sav, Quality quality) {
        String binaryValue = Integer.toBinaryString((int) (sav.getInstMag().getF().getValue() / 1000));
        String[] stringBinaryValue = (binaryValue).split("");
        if (stringBinaryValue.length < 13) {
            for (int i = 0; i < (13 - stringBinaryValue.length); i++) {
                binaryValue = "0"+binaryValue;
            }
            stringBinaryValue = binaryValue.split("");
        }

        if (stringBinaryValue[12].equals("0") & stringBinaryValue[11].equals("0"))
            quality.getValidity().setValue(Validity.GOOD);
        if (stringBinaryValue[12].equals("0") & stringBinaryValue[11].equals("1"))
            quality.getValidity().setValue(Validity.INVALID);
        if (stringBinaryValue[12].equals("1") & stringBinaryValue[11].equals("0"))
            quality.getValidity().setValue(Validity.RESERVED);
        if (stringBinaryValue[12].equals("1") & stringBinaryValue[11].equals("1"))
            quality.getValidity().setValue(Validity.QUESTIONABLE);

        if (stringBinaryValue[10].equals("1")) {
            quality.getOverflow().setValue(true);
            System.out.println();
        } else quality.getOverflow().setValue(false);

        if (stringBinaryValue[9].equals("1")) {
            quality.getOutOfRange().setValue(true);
            System.out.println();
        } else quality.getOutOfRange().setValue(false);

        if (stringBinaryValue[8].equals("1")) {
            quality.getBadReference().setValue(true);
            System.out.println();
        } else quality.getBadReference().setValue(false);

        if (stringBinaryValue[7].equals("1")) {
            quality.getOscillatory().setValue(true);
            System.out.println();
        } else quality.getOscillatory().setValue(false);

        if (stringBinaryValue[6].equals("1")) {
            quality.getFailure().setValue(true);
            System.out.println();
        } else quality.getFailure().setValue(false);

        if (stringBinaryValue[5].equals("1")) {
            quality.getOldData().setValue(true);
            System.out.println();
        } else quality.getOldData().setValue(false);

        if (stringBinaryValue[4].equals("1")) {
            quality.getInconsistent().setValue(true);
            System.out.println();
        } else quality.getInconsistent().setValue(false);

        if (stringBinaryValue[3].equals("1")) {
            quality.getInaccurate().setValue(true);
            System.out.println();
        } else quality.getInaccurate().setValue(false);

        if (stringBinaryValue[2].equals("1")) {
            quality.getSource().setValue(Source.PROCESS_SOURSE);
            System.out.println();
        } else quality.getSource().setValue(Source.SUBSTITUTED_DEF_PROCESS);

        if (stringBinaryValue[1].equals("1")) {
            quality.getTest().setValue(true);
            System.out.println();
        } else quality.getTest().setValue(false);

        if (stringBinaryValue[0].equals("1")) {
            quality.getOperatorBlocked().setValue(true);
            System.out.println();
        } else quality.getOperatorBlocked().setValue(false);
    }
}
