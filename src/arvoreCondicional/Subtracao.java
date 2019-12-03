/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreCondicional;

import Leitura.Dataframe;

/**
 *
 * @author glauc
 */
public class Subtracao extends ExpressaoBinaria {

    public Subtracao(ExpressaoAritmetica esquerda, ExpressaoAritmetica direita) {
        super(esquerda, direita);
    }

    @Override
    public double processa(Dataframe df, int linha) {
        return esquerda.processa(df, linha) - direita.processa(df, linha);
    }

    @Override
    public String toString() {
        return "(" + esquerda + " - " + direita + ")";
    }

}
