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
public abstract class ExpressaoBinaria implements ExpressaoAritmetica {

    ExpressaoAritmetica esquerda;
    ExpressaoAritmetica direita;

    public ExpressaoBinaria(ExpressaoAritmetica esquerda, ExpressaoAritmetica direita) {
        this.esquerda = esquerda;
        this.direita = direita;
    }

    public ExpressaoAritmetica getEsquerdo() {
        return esquerda;
    }

    public void setEsquerdo(ExpressaoAritmetica esquerda) {
        this.esquerda = esquerda;
    }

    public ExpressaoAritmetica getDireito() {
        return direita;
    }

    public void setDireito(ExpressaoAritmetica direita) {
        this.direita = direita;
    }

}
