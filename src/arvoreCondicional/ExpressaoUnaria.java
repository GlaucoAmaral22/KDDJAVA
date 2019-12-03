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
public class ExpressaoUnaria implements ExpressaoAritmetica {

    public int feature;

    public double constante;

    public boolean isFeature;

    public ExpressaoUnaria(double constante) {
        this.constante = constante;
        this.isFeature = false;
    }

    public ExpressaoUnaria(int feature) {
        this.feature = feature;
        this.isFeature = true;
    }

    @Override
    public double processa(Dataframe data, int linha) {
        if (isFeature) {
            return data.df[linha][feature];
        } else {
            return constante;
        }

    }

    @Override
    public String toString() {

        if (isFeature) {
            return 'X' + Integer.toString(feature);
        } else {
            return Double.toString(constante);
        }
    }

}
