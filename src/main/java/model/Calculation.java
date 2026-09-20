package model;

public class Calculation implements CalculationMethods{
    private double calculatedValue;
    private double oplagStk;
    private double distanceMeter;
    private double højdeAfEtiket;
    private double mellemspildRundt;
    private int antalEtiketterPaaTvaers;

    public Calculation(){
        this.calculatedValue = 0;
    }

    public double getCalculatedValue() {
        return calculatedValue;
    }

    public double getOplagStk() {
        return oplagStk;
    }

    public double getDistanceMeter() {
        return distanceMeter;
    }

    public double getHøjdeAfEtiket() {
        return højdeAfEtiket;
    }

    public double getMellemspildRundt() {
        return mellemspildRundt;
    }

    public int getAntalEtiketterPaaTvaers() {
        return antalEtiketterPaaTvaers;
    }

    @Override
    public void meterBasicCalculation(double meter, double højde, double mellemspild, double etiketterTværs) {
        calculatedValue = (meter*1000)/(højde+mellemspild)*etiketterTværs;
    }

    @Override
    public void pieceBasicCalculation(double piece, double højde, double mellemspild, double etiketterTværs) {
        calculatedValue = (piece/1000)*(højde+mellemspild)/etiketterTværs;
    }

    @Override
    public String toString() {
        return ""+calculatedValue;
    }
}
