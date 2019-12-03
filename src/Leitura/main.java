/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leitura;

import Leitura.Dataframe;
import arvoreCondicional.ExpressaoAritmetica;
import arvoreCondicional.Floresta;
import arvoreCondicional.Inteligencia;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 *
 * @author glauc
 */
public class main {

    public static void main(String[] args) throws IOException {

        int qtdBlocosTreino = 10000;

        Dataframe train = new Dataframe();
        train.ler("D:\\DS\\kdd\\training_dataset.csv", qtdBlocosTreino);
        train.lerTarget("D:\\DS\\kdd\\training_data_labels.csv", qtdBlocosTreino);

        Dataframe valid = new Dataframe();
        valid.ler("D:\\DS\\kdd\\test_dataset.csv", 20000);

        Inteligencia iTrain = new Inteligencia(train);
        Inteligencia iValid = new Inteligencia(valid);

        int qtdArvores = 5;
        int h = 5;

        Floresta f = new Floresta(qtdArvores);

        ExpressaoAritmetica melhorExpressao = null;

        double rmseMenor = 99999999;

        //HILLCLIMB
        for (int j = 0; j < 1500; j++) {

            ExpressaoAritmetica raiz = iTrain.montaArvoreExpressao(h);//monto a expressao

            iTrain.executaArvoreExpressao(raiz, 0, qtdBlocosTreino);//executo essa arvore da expressao

            if (iTrain.calculaRMSEArvoreExpressao(0, qtdBlocosTreino) < rmseMenor) {
                melhorExpressao = raiz; //salvo a melhor expressao encontrada
                rmseMenor = iTrain.calculaRMSEArvoreExpressao(0, qtdBlocosTreino);
                System.out.println("Novo rmseMenor: " + rmseMenor);
            }

        }

        iTrain.executaArvoreExpressao(melhorExpressao, 0, qtdBlocosTreino);//executo essa arvore da expressao

        System.out.println(iTrain.calculaRMSEArvoreExpressao(0, qtdBlocosTreino));

        iValid.executaArvoreExpressao(melhorExpressao, 0, 20000);

        try (FileWriter arq = new FileWriter("D:\\DS\\kdd\\test_data_labels.csv")) {
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.printf("\"scatterplotID\",\"score\"\n");
            for (int i = 0; i < iValid.saida.length; i++) {
                gravarArq.println((int) iValid.d.df[i][0] + "," + iValid.saida[i]);
            }
        }
        /*
        
        int qtdFolds = 5;
        int qtdLinhasFold = 568 / qtdFolds;

        Dataframe dfTrainValid = new Dataframe();
        dfTrainValid.ler("C:\\Users\\glauc\\Desktop\\DS\\train-valid.csv", 568);

        Inteligencia iCV = new Inteligencia(dfTrainValid);

        //LinkedList<double[][]> treinos = iCV.getTreinos(qtdFolds, dfTrainValid.df);
        double[] rmseFolds = new double[qtdFolds];

        for (int fold = 0; fold < qtdFolds; fold++) {

            int inicioFold = fold * qtdLinhasFold;
            int fimFold = (fold + 1) * qtdLinhasFold;

            ExpressaoAritmetica melhorExpressao = null;

            double rmseMenor = 99999999;

            for (int i = 0; i < 1000; i++) {
                ExpressaoAritmetica raiz = iCV.montaArvoreExpressao(h);//monto a expressao

                iCV.executaArvoreExpressaof(raiz, inicioFold, fimFold);//executo essa arvore da expressao

                if (iCV.calculaRMSEArvoreExpressaof(inicioFold, fimFold) < rmseMenor) {
                    melhorExpressao = raiz; //salvo a melhor expressao encontrada
                    rmseMenor = iCV.calculaRMSEArvoreExpressaof(inicioFold, fimFold);
                    System.out.println("melhor rmse para o treino do fold" + fold + ": " + rmseMenor);
                }

            }

            iCV.executaArvoreExpressao(melhorExpressao, inicioFold, fold);
            rmseFolds[fold] = iCV.calculaRMSEArvoreExpressao(inicioFold, fimFold);
            System.out.println("Fold- " + fold + " :" + rmseFolds[fold]);

        }
        double sum = 0;
        for (int i = 0; i < rmseFolds.length; i++) {
            sum += rmseFolds[i];
        }
        double media = sum / rmseFolds.length;
        System.out.println("Media: " + sum / rmseFolds.length);

        sum = 0;
        for (int i = 0; i < rmseFolds.length; i++) {
            sum += Math.pow(rmseFolds[i] - media, 2);
        }

        System.out.println("Desvio Padrão: " + Math.sqrt(sum/rmseFolds.length));
         */
    }

}
