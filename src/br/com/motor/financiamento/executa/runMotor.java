package br.com.motor.financiamento.executa;

import java.math.*;
import java.math.RoundingMode;

public class runMotor {

    public static void main(String[] args){
        calcJurosDia();
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
        BigDecimal taxa = new BigDecimal("0.1");

        // potencia
        BigDecimal potencia = BigDecimal.ONE.divide(new BigDecimal("12"), 8, RoundingMode.FLOOR);
        System.out.println("POTENCIA = " +  potencia); // tem que dar 0.083333

        taxa = BigDecimal.ONE.add(taxa).setScale(8, RoundingMode.FLOOR);

        double taxaDouble = Double.parseDouble(taxa.toString());
        double potenciaDouble = Double.parseDouble(potencia.toString());

        double resultCalcTaxa = Math.pow(taxaDouble, potenciaDouble);

        double taxaJurosMes = (resultCalcTaxa - 1) * 100;

        BigDecimal taxaJurosDecimal = new BigDecimal(taxaJurosMes);

        System.out.println("Taxa Juros Mes = " + taxaJurosDecimal);
        System.out.println("Convertendo Juros Mes - FIM");
        return taxaJurosDecimal;
    }

    private static BigDecimal converterTaxaDeJurosDia(){
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
        BigDecimal taxaJurosDecimal = new BigDecimal(taxaJurosDia);

        System.out.println("Taxa Juros Dia = "+ taxaJurosDia);
        System.out.println("Convertendo Juros Dia - FIM");
        return taxaJurosDecimal;
    }

    private static BigDecimal converterTaxaDeJurosNominal(){
        System.out.println("Convertendo Juros para taxa nominal - INICIO");
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

        BigDecimal taxaJurosNominal= new BigDecimal(taxaJurosMes).multiply(new BigDecimal("12"));
        taxaJurosNominal = taxaJurosNominal.setScale(2, RoundingMode.HALF_UP);

        System.out.println("Taxa Juros Nominal = " + taxaJurosNominal);
        System.out.println("Convertendo Juros para taxa nominal  - FIM");
        return taxaJurosNominal;
    }

    private static BigDecimal calcJurosMes(){
        // valorFinanciamento - Recebe
        BigDecimal valor = new BigDecimal("1000");
        BigDecimal taxaMes = converterTaxaDeJurosMes();
        BigDecimal somaJurosFinanciamento = valor.multiply(taxaMes).divide(new BigDecimal("100"));
        somaJurosFinanciamento = somaJurosFinanciamento.setScale(2, RoundingMode.FLOOR);
        System.out.println("Soma Juros/MES Financiamento = " + somaJurosFinanciamento);
        return somaJurosFinanciamento;
    }

    private static BigDecimal calcJurosDia(){
        // diasCorridos = 10
        // valorFananciamento - Recebe
        BigDecimal valor = new BigDecimal("1500");
        BigDecimal taxaDia = converterTaxaDeJurosDia().setScale(8, RoundingMode.FLOOR);

        taxaDia = taxaDia.divide(new BigDecimal("100"));
        taxaDia = taxaDia.add(BigDecimal.ONE).setScale(8, RoundingMode.FLOOR);
        
        double taxaDouble = Double.parseDouble(taxaDia.toString());
        double resultCalcTaxa = Math.pow(taxaDouble, 10); // 1.0023966
        System.out.println("TAXA DIAS ELEVADO A QUANTIDADE DE DIAS = " + resultCalcTaxa);
        BigDecimal taxaDecimal = new BigDecimal(resultCalcTaxa);
        taxaDecimal = taxaDecimal.subtract(BigDecimal.ONE).setScale(8, RoundingMode.HALF_UP);

        BigDecimal somaJurosFinanciamento = valor.multiply(taxaDecimal).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Soma juros/DIA Financiamento = " + somaJurosFinanciamento);
        return null;
    }
}
