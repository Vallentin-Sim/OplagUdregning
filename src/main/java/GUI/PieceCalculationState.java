package GUI;

import model.Calculation;

public class PieceCalculationState implements CalculationState {
    @Override
    public void calculate(
            Calculation calculation,
            double oplag,
            double etiketHoejde,
            double mellemspildRundt,
            double etiketterTvaers
    ) {
        calculation.pieceBasicCalculation(oplag, etiketHoejde, mellemspildRundt, etiketterTvaers);
    }
}
