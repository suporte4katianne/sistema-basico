package br.com.hsi.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CodigoBarrasValidator implements ConstraintValidator<CodigoBarras, String> {


    @Override
    public void initialize(CodigoBarras codigoBarras) {

    }

    @Override
    public boolean isValid(String barCode, ConstraintValidatorContext constraintValidatorContext) {
        if(barCode.isEmpty()){
            return true;
        }else {
            int digit;
            int calculated;
            String ean;
            String checkSum = "131313131313";
            int sum = 0;
            if (barCode.length() == 8 || barCode.length() == 13) {
                digit = Integer.parseInt("" + barCode.charAt(barCode.length() - 1));
                ean = barCode.substring(0, barCode.length() - 1);
                for (int i = 0; i <= ean.length() - 1; i++) {
                    sum += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)));
                }
                calculated = 10 - (sum % 10);
                return (digit == calculated);
            } else {
                return false;
            }
        }
    }


}
