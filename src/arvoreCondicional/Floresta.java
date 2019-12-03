/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvoreCondicional;

/**
 *
 * @author glauc
 */
public class Floresta {

    ExpressaoAritmetica[] floresta = null;

    public Floresta(int qtdArvores) {
        floresta = new ExpressaoAritmetica[qtdArvores];
    }

    public void addArvore(int posicao, ExpressaoAritmetica arvore){
        floresta[posicao] = arvore;
    }
    
    public ExpressaoAritmetica getArvore(int posicao){
        return floresta[posicao];
    }
    
    public void printa(int i){
        System.out.print(floresta[i]);
    }

}
