package br.com.motor.financiamento.executa;

import java.math.*;
import java.math.RoundingMode;



public class runMotor {

    public static void main(String[] args){
        converterTaxaDeJurosDia();
    }

    private static void calcularCotaAmortizacao(){
        BigDecimal valorFinanciamento = new BigDecimal("10000");
        BigDecimal prazoFinancimento = new BigDecimal("360");

        BigDecimal cotaAmortizacao;
        cotaAmortizacao = valorFinanciamento.divide(prazoFinancimento, 2, RoundingMode.HALF_UP);
        System.out.println("Cota Amortizacao = " + cotaAmortizacao);
    }

    private static BigDecimal converterTaxaDeJurosMes(){
        System.out.println("Convertendo Juros Mes - INICIO");
        // receber numero inteiro e dividir por 100
        BigDecimal taxa = new BigDecimal("0.09");

        // potencia
        BigDecimal potencia = BigDecimal.ONE.divide(new BigDecimal("12"), 8, RoundingMode.FLOOR);
        System.out.println("POTENCIA = " +  potencia); // tem que dar 0.083333

        taxa = BigDecimal.ONE.add(taxa).setScale(8, RoundingMode.FLOOR);

        double taxaDouble = Double.parseDouble(taxa.toString());
        double potenciaDouble = Double.parseDouble(potencia.toString());

        double resultCalcTaxa = Math.pow(taxaDouble, potenciaDouble);

        double taxaJurosMes = (resultCalcTaxa - 1) * 100;

        BigDecimal taxaJurosDecimal = new BigDecimal(taxaJurosMes);

        System.out.println(taxaJurosDecimal);
        System.out.println("Convertendo Juros Mes - FIM");
        return taxaJurosDecimal;
    }

    private static void converterTaxaDeJurosDia(){
        System.out.println("Convertendo Juros Dia - INICIO");
        // receber taxa
        BigDecimal taxa = converterTaxaDeJurosMes();
        taxa = taxa.divide(new BigDecimal("100"));

        // potencia
        BigDecimal potencia = BigDecimal.ONE.divide(new BigDecimal("30"), 8, RoundingMode.FLOOR);
        System.out.println("POTENCIA = " +  potencia); // tem que dar 0.03333

        taxa = BigDecimal.ONE.add(taxa).setScale(8, RoundingMode.FLOOR);

        double taxaDouble = Double.parseDouble(taxa.toString());
        double potenciaDouble = Double.parseDouble(potencia.toString());

        double resultCalcTaxa = Math.pow(taxaDouble, potenciaDouble);

        double taxaJurosDia = (resultCalcTaxa - 1) * 100;
        System.out.println(taxaJurosDia);
        System.out.println("Convertendo Juros Dia - FIM");
    }
}
