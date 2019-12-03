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
public interface ExpressaoAritmetica {
    
    public double processa(Dataframe df, int linha);
    
    public String toString();
    
}
