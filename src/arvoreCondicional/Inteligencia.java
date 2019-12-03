/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreCondicional;

import Leitura.Dataframe;
import arvoreCondicional.ExpressaoAritmetica;
import arvoreCondicional.ExpressaoUnaria;
import arvoreCondicional.Multiplicacao;
import arvoreCondicional.Soma;
import arvoreCondicional.Subtracao;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author glauc
 */


//LEMBRAR QUE TEM A QTDFEATRURES NA MONTAEM DA ARVORE
public class Inteligencia {

    public Dataframe d = null;
    public double[][] target = null;
    public int[][] mC = null;

    public double[] saida;
    private double v;

    private Random r;

    public Inteligencia(Dataframe d) {
        this.d = d;
        this.target = d.target;
        saida = new double[d.df.length];
        this.r = new Random();
        System.out.println("saida lenght:" + saida.length);
    }
    
  

    public void vai() {
        for (int i = 0; i < saida.length; i++) {
            System.out.print(saida[i] + "        ");
        }
        System.out.println("");
    }

    public void executaArvoreExpressao(ExpressaoAritmetica raiz, int inicio, int fim) {
        //RAIZ É UMA Expressao Aritmetica
        for (int j = inicio; j < fim; j++) {
            saida[j] = raiz.processa(d, j);
        }

    }

    public double calculaRMSEArvoreExpressao(int inicio, int fim) {
        double RMSE = 0;
        for (int i = inicio; i < fim; i++) {
            RMSE += Math.pow((this.target[i][1] - (1 / (1 + Math.exp(-this.saida[i])))), 2);
        }
        RMSE = RMSE / this.target.length;
        return Math.sqrt(RMSE);
    }

    public double mae() {
        double mae = 0;
        for (int i = 0; i < target.length; i++) {
            mae += (target[i][1] - saida[i]);
        }
        return mae / target.length;
    }


    public int numeroAleatorio(int min, int max) {

        int randomNum = r.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    
    public ExpressaoAritmetica montaArvoreExpressao(int nivel) {

        if (nivel == 1) {
            if (r.nextDouble() > 0.5) {
                return new ExpressaoUnaria(r.nextDouble());//gero uma constante

            } else {
                return new ExpressaoUnaria(numeroAleatorio(1, 26));//gero uma feature
            }
        } else {
            int numeroAleatorio = this.numeroAleatorio(1, 3);

            switch (numeroAleatorio) {
                case 1:
                    return new Soma(montaArvoreExpressao(nivel - 1), montaArvoreExpressao(nivel - 1));
                case 2:
                    return new Subtracao(montaArvoreExpressao(nivel - 1), montaArvoreExpressao(nivel - 1));
                case 3:
                    return new Multiplicacao(montaArvoreExpressao(nivel - 1), montaArvoreExpressao(nivel - 1));
            }
        }
        return null;

    }

    public LinkedList<double[][]> getTreinos(int kfolds, double[][] dfTrainValid) {

        //montar os grupos de treinos - linkedlist..
        LinkedList<double[][]> gruposTreinos = new LinkedList();

        int qtdLinhasCadaFold = 568 / kfolds; //113

        int qtdTotalLinhasSemOFold = (kfolds - 1) * qtdLinhasCadaFold;//452

        int qtdFeat = dfTrainValid[0].length;

        for (int k = 0; k < kfolds; k++) {//para cada fold eu crio um grupo de treino 

            int inicioExcluidos = k * qtdLinhasCadaFold;
            int fimExcluidos = ((k + 1) * qtdLinhasCadaFold) - 1;

            double[][] m = new double[qtdTotalLinhasSemOFold][qtdFeat];

            int l = 0;//para controle das linhas do novo grupo

            for (int i = 0; i < kfolds * qtdLinhasCadaFold; i++) { //percorrer por todas as linhas do TRAIN junto com o VALID

                //pegando somente os valores necessarios se o fold k
                if (i < inicioExcluidos || i > fimExcluidos) {
                    for (int j = 0; j < qtdFeat; j++) {
                        m[l][j] = dfTrainValid[i][j];
                    }
                    l++;
                }
            }
            gruposTreinos.add(m);
        }

        return gruposTreinos;
    }

    public void executaArvoreExpressaof(ExpressaoAritmetica raiz, int incioF, int fimF) {
        //RAIZ É UMA Expressao Aritmetica
        for (int j = 0; j < saida.length; j++) {
            if (j < incioF || j > fimF) {
                saida[j] = raiz.processa(d, j);
            }
        }
    }

    public double calculaRMSEArvoreExpressaof(int inicioF, int fimF) {
        double RMSE = 0;
        for (int i = 0; i < target.length; i++) {
            if (i < inicioF || i > fimF) {
                RMSE += Math.pow((this.target[i][1] - (1 / (1 + Math.exp(-this.saida[i])))), 2);
            }
        }
        RMSE = RMSE / this.target.length;
        return Math.sqrt(RMSE);
    }

}
