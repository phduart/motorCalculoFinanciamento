package br.com.motor.financiamento.executa;

import java.math.*;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class runMotor {

    public static void main(String[] args) throws ParseException {
        BigDecimal valorFinanciamento = new BigDecimal("1000");
        BigDecimal valorImovel = new BigDecimal("500000.00");
        BigDecimal prazoFinanciamento = new BigDecimal("360");
        BigDecimal taxaJurosEfetiva = new BigDecimal("0.1");

        Calendar c = Calendar.getInstance();
        Date dataSimulacao = c.getTime();
        DateFormat f = DateFormat.getDateInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



    }

    private static BigDecimal calcularValorAmortizacao(){
        // receber valor de financiamento
        BigDecimal valorFinanciamento = new BigDecimal("1000");
        // receber prazo de financiamento
        BigDecimal prazoFinancimento = new BigDecimal("360");

        // CALCULO ---- cotaAmortizacao = valorFinaniamento/prazoFinaniamento
        BigDecimal cotaAmortizacao;
        cotaAmortizacao = valorFinanciamento.divide(prazoFinancimento, 2, RoundingMode.HALF_UP);
        System.out.println("valorFinaniamento/prazoFinaniamento = R$" + cotaAmortizacao);

        return cotaAmortizacao;
    }

    private static BigDecimal converterTaxaDeJurosMes(){
        // Transformar
        // receber taxaAno-  numero inteiro e dividir por 100
        BigDecimal taxaAno = new BigDecimal("0.1");

        // CALCULO ---- jurosMes = (((1+ taxaAno) ^ (1/12)) - 1) * 100
        // x = (1 + taxaAno)
        BigDecimal taxaMes = BigDecimal.ONE.add(taxaAno).setScale(8, RoundingMode.FLOOR);
        System.out.println("x = (1 + taxaAno) = " + taxaMes);

        // y = (1/12)
        BigDecimal potencia = BigDecimal.ONE.divide(new BigDecimal("12"), 8, RoundingMode.FLOOR);
        System.out.println("y = (1/12) = " + potencia);

        double taxaDouble = Double.parseDouble(taxaMes.toString());
        double potenciaDouble = Double.parseDouble(potencia.toString());

        // z = x^y
        double resultCalcTaxa = Math.pow(taxaDouble, potenciaDouble);
        System.out.println("z = x^y = " + resultCalcTaxa);

        // a = (z - 1) * 100
        double taxaJurosMes = (resultCalcTaxa - 1) * 100;
        BigDecimal taxaJurosDecimal = new BigDecimal(taxaJurosMes);
        System.out.println("a = (z - 1) * 100 = " + taxaJurosDecimal + "%");

        return taxaJurosDecimal;
    }

    private static BigDecimal converterTaxaDeJurosDia(){
        // receber taxaMes = 0.79741401
        BigDecimal taxaMes = new BigDecimal("0.79741401");
        System.out.println("taxa mes " + taxaMes);

        // tratando taxaMes = taxaMes / 100
        taxaMes = taxaMes.divide(new BigDecimal("100"));

        // CALCULO ---- (((1+ taxa) ^ (1/30)) - 1) * 100
        // x = (1+ taxa)
        BigDecimal taxaDia = BigDecimal.ONE.add(taxaMes).setScale(8, RoundingMode.FLOOR);
        System.out.println("x = (1+taxa) = " + taxaDia);

        // y = (1/d)
        BigDecimal potencia = BigDecimal.ONE.divide(new BigDecimal("30"), 8, RoundingMode.FLOOR);
        System.out.println("y = (1/360) = " +  potencia); // tem que dar 0.03333

        double taxaDouble = Double.parseDouble(taxaDia.toString());
        double potenciaDouble = Double.parseDouble(potencia.toString());

        // z = x^y
        double resultCalcTaxa = Math.pow(taxaDouble, potenciaDouble);
        System.out.println("z = x^y = " + resultCalcTaxa);

        // a = (z - 1) * 100
        double taxaJurosDia = (resultCalcTaxa - 1) * 100;
        BigDecimal taxaJurosDecimal = new BigDecimal(taxaJurosDia);
        System.out.println("a = (z - 1) * 100 = " + taxaJurosDecimal  + "%");

        return taxaJurosDecimal;
    }

    private static BigDecimal converterTaxaDeJurosNominal(){
        // receber Taxa Efetiva - Numero inteiro e dividir por 100
        BigDecimal taxaEfetiva = new BigDecimal("0.1");

        // CALCULO ---- ((taxa efetiva +1)^(1/12)-1)*12
        // x = (taxa efetiva +1)
        BigDecimal taxaNominal = BigDecimal.ONE.add(taxaEfetiva).setScale(8, RoundingMode.FLOOR);
        System.out.println("x = (taxa efetiva +1) = " + taxaNominal);

        // y = (1/12)
        BigDecimal potencia = BigDecimal.ONE.divide(new BigDecimal("12"), 8, RoundingMode.FLOOR);
        System.out.println("y = (1/12) = " +  potencia); // tem que dar 0.083333

        double taxaDouble = Double.parseDouble(taxaNominal.toString());
        double potenciaDouble = Double.parseDouble(potencia.toString());

        // z = x ^ y
        double resultCalcTaxa = Math.pow(taxaDouble, potenciaDouble);
        System.out.println("z = x ^ y = " + resultCalcTaxa);

        // a = (z - 1) * 100
        resultCalcTaxa = (resultCalcTaxa - 1) * 100;
        System.out.println("a = (z - 1) * 100 = " + resultCalcTaxa);

        // b = a * 21
        BigDecimal taxaJurosNominal= new BigDecimal(resultCalcTaxa).multiply(new BigDecimal("12"));
        taxaJurosNominal = taxaJurosNominal.setScale(2, RoundingMode.HALF_UP);
        System.out.println("b = a * 21 = " + taxaJurosNominal  + "%");

        return taxaJurosNominal;
    }

    private static BigDecimal calcJurosMes(){
        // valorFinanciamento - Recebe
        BigDecimal valor = new BigDecimal("1000");

        // receber taxaMes = 0.79741401
        BigDecimal taxaMes = new BigDecimal("0.79741401");

        // CALCULO --- valorFinanciamento * taxaMes
        BigDecimal somaJurosFinanciamento = valor.multiply(taxaMes).divide(new BigDecimal("100"));
        somaJurosFinanciamento = somaJurosFinanciamento.setScale(2, RoundingMode.FLOOR);

        System.out.println("valorFinanciamento * taxaMes = R$" + somaJurosFinanciamento);

        return somaJurosFinanciamento;
    }

    private static BigDecimal calcJurosDia(){
        // receber - valorFinanciamento
        BigDecimal valorFinanciamento = new BigDecimal("1000");

        // processa - taxaJurosDia
        BigDecimal taxaJurosDia = new BigDecimal("0.02647855");
        taxaJurosDia = taxaJurosDia.divide(new BigDecimal("100"));

        // CALCULO ---- (valorFinanciamento * ((1+taxaJurosDia) ^ qtdDias)) - valorFinanciamento
        // x = (1+taxa)
        taxaJurosDia = taxaJurosDia.add(BigDecimal.ONE).setScale(8, RoundingMode.FLOOR);
        System.out.println("x = (1+taxa) = " + taxaJurosDia);

        // y = (1+taxaJurosDia) ^ qtdDias
        double taxaDouble = Double.parseDouble(taxaJurosDia.toString());
        double resultCalcTaxa = Math.pow(taxaDouble, 40); // 1.0023966
        System.out.println("(1+taxaJurosDia) ^ qtdDias = " + resultCalcTaxa);

        // a = valorFinanciamento * y
        BigDecimal taxaDecimal = new BigDecimal(resultCalcTaxa);
        BigDecimal somaJurosFinanciamento = valorFinanciamento.multiply(taxaDecimal).setScale(2, RoundingMode.HALF_UP);
        System.out.println("a = valorFinanciamento * y = " + somaJurosFinanciamento);

        // b = a - valorFinanciamento
        somaJurosFinanciamento = somaJurosFinanciamento.subtract(valorFinanciamento).setScale(8, RoundingMode.HALF_UP);
        System.out.println("b = a - valorFinanciamento = R$" + somaJurosFinanciamento);

        return null;
    }

    private static BigDecimal calcRDM(){
        // receber
        BigDecimal prazoFinancimento = new BigDecimal("360");
        // valor do juros da primeira parcela
        BigDecimal valorJurosPrimeiraParcela = new BigDecimal("10.65");

        // CALCULO ----  valorJurosPrimeiraParcela / prazoFinancimento
        BigDecimal valorRDM;
        valorRDM = valorJurosPrimeiraParcela.divide(prazoFinancimento, 2, RoundingMode.HALF_UP);
        System.out.println("valorJurosPrimeiraParcela / prazoFinancimento = R$" + valorRDM);
        return null;
    }

    private static BigDecimal calcCESH(){
        //receber fator de carrecao CESH
        BigDecimal fatorCorrecaoCESH = new BigDecimal("1.008");

        // processa - dias corridos
        BigDecimal diasCorridos = new BigDecimal("40");
        // processa - valor seguro
        BigDecimal valorSeguro = new BigDecimal("85.43");

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
        System.out.println("z = (fatorCorrecaoCESH ^ y) =  " + parteTresDouble); // FAZENDO COM 31 DIAS = 1.008154

        // (valorSeguro / z)
        BigDecimal parteTres = new BigDecimal(parteTresDouble);
        BigDecimal resultado = valorSeguro.divide(parteTres, 2, RoundingMode.HALF_UP);
        System.out.println("(valorSeguro / z) = R$" + resultado);

        return null;
        // REPETIR PARA TODAS AS PARCELAS, APOS SOMAR VALOR TOTAL E DIVIDIR PELO FINANCIAMENTO
    }

    private static void calcIOF(){
        // receber aliquota IOF diaria - 0.0082%
        BigDecimal aliquotaIOFDiaria = new BigDecimal("0.0082").divide(new BigDecimal("100"));

        // receber aliquota IOF adicional - 0.3800%
        BigDecimal aliquotaIOFAdicional = new BigDecimal("0.3800").divide(new BigDecimal("100"));

        // processa - dias corridos
        BigDecimal diasCorridos = new BigDecimal("40");

        // CALCULO ---- (diasCorridos * aliquotaIOFDiaria) + aliquotaIOFAdicional))* calcularCotaAmortizacao)
        // x = (diasCorridos * aliquotaIOFDiaria)
        BigDecimal parteUm = diasCorridos.multiply(aliquotaIOFDiaria);
        System.out.println("x = (diasCorridos * aliquotaIOFDiaria) = " + parteUm);

        // y = (x + aliquotaIOFAdicional)
        BigDecimal parteDois = parteUm.add(aliquotaIOFAdicional);
        System.out.println("y = (x + aliquotaIOFAdicional) = " + parteDois);

        // y * calcularCotaAmortizacao
        BigDecimal valorAmortizacao = new BigDecimal("2.78");
        BigDecimal resultado = parteDois.multiply(valorAmortizacao).setScale(2, RoundingMode.HALF_UP);
        System.out.println("y * calcularCotaAmortizacao = R$" + resultado);

    }

    private static void calcRendaMinima(){
        // Receber percecntual de renda minima
        BigDecimal percentualRendaMinima = new BigDecimal("30");

        // Processa -  ValorPrestacao
        BigDecimal valorPrestacao = new BigDecimal("123.85");

        // CALCULO ---- (valorPrestacao * 100) / percentualRendaMinima
        // x = valorPrestacao * 100
        BigDecimal valorRendaMinima = valorPrestacao.multiply(new BigDecimal("100"));
        System.out.println("x = valorPrestacao * 100 = " + valorRendaMinima);

        // y =  x / percentualRendaMinima
        valorRendaMinima = valorRendaMinima.divide(percentualRendaMinima, 8, RoundingMode.FLOOR);
        System.out.println("y = x / percentuaRendaMinima = R$" + valorRendaMinima);
    }

}
