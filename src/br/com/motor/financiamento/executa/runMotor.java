package br.com.motor.financiamento.executa;

import java.math.*;
import java.math.RoundingMode;

public class runMotor {

    public static void main(String[] args){
        calcIOF();
    }

    private static BigDecimal calcularCotaAmortizacao(){
        // receber valor de financiamento
        BigDecimal valorFinanciamento = new BigDecimal("10000");
        // receber prazo de financiamento
        BigDecimal prazoFinancimento = new BigDecimal("360");

        // CALCULO ---- valorFinaniamento/prazoFinaniamento
        BigDecimal cotaAmortizacao;
        cotaAmortizacao = valorFinanciamento.divide(prazoFinancimento, 2, RoundingMode.HALF_UP);
        System.out.println("Cota Amortizacao = " + cotaAmortizacao);

        return cotaAmortizacao;
    }

    private static BigDecimal converterTaxaDeJurosMes(){
        // receber taxa -  numero inteiro e dividir por 100
        BigDecimal taxa = new BigDecimal("0.1");

        // potencia
        BigDecimal potencia = BigDecimal.ONE.divide(new BigDecimal("12"), 8, RoundingMode.FLOOR);

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
        // receber taxa
        BigDecimal taxa = converterTaxaDeJurosMes();
        taxa = taxa.divide(new BigDecimal("100"));

        // CALCULO ----
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

        // CALCULO ----
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

        // processa - taxaJurosMes
        BigDecimal taxaMes = converterTaxaDeJurosMes();

        // CALCULO --- valor * taxaMes
        BigDecimal somaJurosFinanciamento = valor.multiply(taxaMes).divide(new BigDecimal("100"));
        somaJurosFinanciamento = somaJurosFinanciamento.setScale(2, RoundingMode.FLOOR);
        System.out.println("Soma Juros/MES Financiamento = " + somaJurosFinanciamento);

        return somaJurosFinanciamento;
    }

    private static BigDecimal calcJurosDia(){
        // receber - valorFananciamento
        BigDecimal valorFananciamento = new BigDecimal("1500");

        // processa - taxaJurosDia
        BigDecimal taxaJurosDia = converterTaxaDeJurosDia().setScale(8, RoundingMode.FLOOR);

        // CALCULO ----
        taxaJurosDia = taxaJurosDia.divide(new BigDecimal("100"));
        taxaJurosDia = taxaJurosDia.add(BigDecimal.ONE).setScale(8, RoundingMode.FLOOR);
        
        double taxaDouble = Double.parseDouble(taxaJurosDia.toString());
        double resultCalcTaxa = Math.pow(taxaDouble, 10); // 1.0023966
        System.out.println("TAXA DIAS ELEVADO A QUANTIDADE DE DIAS = " + resultCalcTaxa);
        BigDecimal taxaDecimal = new BigDecimal(resultCalcTaxa);
        taxaDecimal = taxaDecimal.subtract(BigDecimal.ONE).setScale(8, RoundingMode.HALF_UP);

        BigDecimal somaJurosFinanciamento = valorFananciamento.multiply(taxaDecimal).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Soma juros/DIA Financiamento = " + somaJurosFinanciamento);
        return null;
    }

    private static BigDecimal calcRDM(){
        // receber
        BigDecimal prazoFinancimento = new BigDecimal("360");
        // valor do juros da primeira parcela
        BigDecimal valorJurosPrimeiraParcela = new BigDecimal("4897.25");

        // CALCULO ----  valorJurosPrimeiraParcela / prazoFinancimento
        BigDecimal valorRDM;
        valorRDM = valorJurosPrimeiraParcela.divide(prazoFinancimento, 6, RoundingMode.FLOOR);
        System.out.println("VALOR CALCULADO RDM = R$" + valorRDM);
        return null;
    }

    private static BigDecimal calcCESH(){
        //receber fator de carrecao CESH
        BigDecimal fatorCorrecaoCESH = new BigDecimal("1.008");

        // processa - dias corridos
        BigDecimal diasCorridos = new BigDecimal("31");
        // processa - valor seguro
        BigDecimal valorSeguro = new BigDecimal("136.29");

        //CALCULO ---- valorSeguro/(fatorCorrecaoCESH)^((12*diasCorridos)/365)
        // x = (12*diasCorridos)
        BigDecimal parteUm = new BigDecimal("12").multiply(diasCorridos);
        System.out.println("x = (12*diasCorridos) = " + parteUm); // FAZENDO COM 31 DIAS = 372

        // y = (x/365)
        BigDecimal parteDois = parteUm.divide(new BigDecimal("365"), 8, RoundingMode.FLOOR);
        System.out.println("y = (x/365) = " + parteDois); // FAZENDO COM 31 DIAS = 1.01917

        // z = (fatorCorrecaoCESH ^ y)
        Double fatorCorrecaoCESHDouble = Double.parseDouble(fatorCorrecaoCESH.toString());
        Double parteDoisDouble = Double.parseDouble(parteDois.toString());
        double parteTresDouble = Math.pow(fatorCorrecaoCESHDouble, parteDoisDouble);
        System.out.println("(fatorCorrecaoCESH ^ y) =  " + parteTresDouble); // FAZENDO COM 31 DIAS = 1.008154

        // (valorSeguro / z)
        BigDecimal parteTres = new BigDecimal(parteTresDouble);
        BigDecimal resultado = valorSeguro.divide(parteTres, 8, RoundingMode.FLOOR);
        System.out.println("RESULTADO calcCESH = " + resultado);

        return null;
        // REPETIR PARA TODAS AS PARCELAS, APOS SOMAR VALOR TOTAL E DIVIDIR PELO FINANCIAMENTO
    }

    private static void calcIOF(){
        // receber aliquota IOF diaria - 0.0082%
        BigDecimal aliquotaIOFDiaria = new BigDecimal("0.0082").divide(new BigDecimal("100"));

        // receber aliquota IOF adicional - 0.3800%
        BigDecimal aliquotaIOFAdicional = new BigDecimal("0.3800").divide(new BigDecimal("100"));

        // processa - dias corridos
        BigDecimal diasCorridos = new BigDecimal("31");

        // CALCULO ---- (diasCorridos * aliquotaIOFDiaria) + aliquotaIOFAdicional))* calcularCotaAmortizacao)
        // x = (diasCorridos * aliquotaIOFDiaria)
        BigDecimal parteUm = diasCorridos.multiply(aliquotaIOFDiaria);
        System.out.println("x = (diasCorridos * aliquotaIOFDiaria) = " + parteUm);

        // y = (x + aliquotaIOFAdicional)
        BigDecimal parteDois = parteUm.add(aliquotaIOFAdicional);
        System.out.println("y = (x + aliquotaIOFAdicional) = " + parteDois);

        // y * calcularCotaAmortizacao
        BigDecimal cotaAmortizacao = calcularCotaAmortizacao();
        BigDecimal resultado = parteDois.multiply(cotaAmortizacao).setScale(2, RoundingMode.HALF_UP);
        System.out.println("y * calcularCotaAmortizacao = " + resultado);

    }

}
