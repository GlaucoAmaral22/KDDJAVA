/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leitura;

import java.util.LinkedList;

/**
 *
 * @author glauc
 */
public class Bloco {

    int qtdLinhas;

    Linha[] linhas; //vetor de linhas

    public Bloco(int qtdLinha, LinkedList<Linha> tdsLin) {
        this.qtdLinhas = qtdLinha;
        linhas = new Linha[qtdLinhas];
        int i = 0;
        for (Linha l : tdsLin) linhas[i++] = l;

    }

}
