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
public class mainTeste {

    public static void main(String[] args) {

        LinkedList<Linha> tdsLin = new LinkedList();
        Linha a = new Linha();
        a.x = 0;
        a.y = 2;
        a.cluster = 1;
        tdsLin.add(a);

        a = new Linha();
        a.x = 1;
        a.y = 5;
        a.cluster = 1;
        tdsLin.add(a);

        a = new Linha();
        a.x = 2;
        a.y = 8;
        a.cluster = 1;
        tdsLin.add(a);

        a = new Linha();
        a.x = 3;
        a.y = 11;
        a.cluster = 1;
        tdsLin.add(a);

   

      
        RegressaoLinearSimples r = new RegressaoLinearSimples(tdsLin);
        r.execOperacoes(1);
        System.out.println(r.toString());
        System.out.println("a: " + r.a + "\nb: " + r.b);
        
        
    }

}
