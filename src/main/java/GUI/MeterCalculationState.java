package GUI;

import model.Calculation;

public class MeterCalculationState implements CalculationState {
    @Override
    public void calculate(
            Calculation calculation,
            double oplag,
            double etiketHoejde,
            double mellemspildRundt,
            double etiketterTvaers
    ) {
        calculation.meterBasicCalculation(oplag, etiketHoejde, mellemspildRundt, etiketterTvaers);
    }
}
