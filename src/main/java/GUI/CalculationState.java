package GUI;

import model.Calculation;

public interface CalculationState {
    void calculate(
            Calculation calculation,
            double oplag,
            double etiketHoejde,
            double mellemspildRundt,
            double etiketterTvaers
    );
}
