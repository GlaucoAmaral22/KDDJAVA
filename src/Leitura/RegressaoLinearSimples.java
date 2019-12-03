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
public class RegressaoLinearSimples {

    double somatX = 0;
    double somatX2 = 0;
    double somatY = 0;
    double somatXY = 0;
    LinkedList<Linha> bloco;

    double a = 0;
    double b = 0;

    public RegressaoLinearSimples(LinkedList<Linha> bloco) {
        this.bloco = bloco;
    }

    public void execOperacoes(int cluster) {
        zerarVariaveis();
        for (Linha l : bloco) {
            if (l.cluster == cluster) {
                somatX += l.x;
                somatX2 += (l.x * l.x);
                somatY += l.y;
                somatXY += l.x * l.y;
            }
        }
        double cima = somatXY - ((somatX) * (somatY) / bloco.size());
        double baixo = somatX2 - (somatX * somatX / bloco.size());
        b = (cima / baixo);

        a = (somatY / bloco.size()) - b * (somatX / bloco.size());

    }

    public void zerarVariaveis() {
        somatX = 0;
        somatX2 = 0;
        somatY = 0;
        somatXY = 0;

        a = 0;
        b = 0;
    }

    @Override
    public String toString() {
        return "soma X: " + somatX + "\nsoma X*Y:   " + somatXY + "\nsoma Y: " + somatY + "\nsoma X²: " + somatX2;
    }

}
