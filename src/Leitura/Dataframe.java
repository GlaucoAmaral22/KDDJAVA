/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Leitura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author glauc
 */
public class Dataframe {

    public double[][] df = null;//todos blocos

    public double[][] target = null;

    public void ler(String endereco, int qtdBlocos) {

        df = new double[qtdBlocos][27];

        String arquivo = endereco;

        BufferedReader conteudoCSV = null;

        String linha = "";

        String csvSeparador = ",";

        int qtdColunas;

        LinkedList<Linha> linhasDoBloco = new LinkedList();

        try {
            conteudoCSV = new BufferedReader(new FileReader(arquivo));

            linha = conteudoCSV.readLine(); //leitura primeira linha com o nome de todas colunas

            qtdColunas = linha.split(csvSeparador).length;

            int idbloco = 0;
            int idnovalinha = 0;
            int qtBl = 0;

            Linha l = new Linha();

            //leitura segunda linha para saber o ID
            linha = conteudoCSV.readLine();
            String[] valoresLinha = linha.split(csvSeparador);
            //pego todas informações e coloco no id;
            l.scatid = Integer.parseInt(valoresLinha[0]);
            idbloco = l.scatid; //id do primeiro bloco
            l.datapointid = Integer.parseInt(valoresLinha[1]);
            l.sampletype = convertSample(valoresLinha[2]);
            l.x = Integer.parseInt(valoresLinha[3]);
            l.y = Integer.parseInt(valoresLinha[4]);
            l.cluster = converteCluster(valoresLinha[5]);

            l.silhueta = Double.parseDouble(valoresLinha[6]);
            linhasDoBloco.add(l);

            while ((linha = conteudoCSV.readLine()) != null) {

                valoresLinha = linha.split(csvSeparador);
                //pego todas informações e coloco no id;
                l = new Linha();
                l.scatid = Integer.parseInt(valoresLinha[0]);
                idnovalinha = l.scatid;
                l.datapointid = Integer.parseInt(valoresLinha[1]);
                l.sampletype = convertSample(valoresLinha[2]);
                l.x = Integer.parseInt(valoresLinha[3]);
                l.y = Integer.parseInt(valoresLinha[4]);
                l.cluster = converteCluster(valoresLinha[5]);
                l.silhueta = Double.parseDouble(valoresLinha[6]);

                if (idnovalinha == idbloco) {
                    linhasDoBloco.add(l);
                } else {
                    //criacao da linha da matriz de treino

                   // System.out.println("idbloc | idnova linhda: " + idbloco + "," + idnovalinha);

                    Porcentagens m = calculaPorcentagens(linhasDoBloco);
                    df[qtBl][0] = idbloco;
                    df[qtBl][1] = l.datapointid;
                    df[qtBl][2] = l.sampletype;
                    df[qtBl][3] = l.x;
                    df[qtBl][4] = l.y;
                    df[qtBl][5] = l.cluster;
                    df[qtBl][6] = l.silhueta;
                    df[qtBl][7] = m.x;
                    df[qtBl][8] = m.y;
                    df[qtBl][9] = m.xy;
                    df[qtBl][10] = m.e;
                    df[qtBl][11] = m.l;

                    //medias
                    m = calculaMedias(linhasDoBloco);
                    df[qtBl][12] = m.x;
                    df[qtBl][13] = m.y;
                    df[qtBl][14] = m.xy;
                    df[qtBl][15] = m.e;
                    df[qtBl][16] = m.l;

                    //
                    df[qtBl][17] = this.qtdSample(linhasDoBloco);
                    df[qtBl][18] = qtdNegative(linhasDoBloco);

                    //distancia entre os centroides
                    //regressao linear para cada cluster
                    
                    RegressaoLinearSimples r = new RegressaoLinearSimples(linhasDoBloco);
                    r.execOperacoes(1);
                    df[qtBl][19] =r.a;
                    df[qtBl][20] =r.b;
                    
                    r.execOperacoes(2);
                    df[qtBl][21] =r.a;
                    df[qtBl][22] =r.b;
                    
                    r.execOperacoes(4);
                    df[qtBl][23] =r.a;
                    df[qtBl][24] =r.b;
                    
                    r.execOperacoes(1);
                    df[qtBl][25] =r.a;
                    df[qtBl][25] =r.b;


                    qtBl++;
                    if (qtBl == qtdBlocos) {
                        System.out.println("OK");
                        break;
                    }

                    //criacao novo bloco
                    linhasDoBloco = new LinkedList();
                    linhasDoBloco.add(l);
                    idbloco = idnovalinha;
                }
            }

        } catch (Exception e) {
        }
    }

    public void lerTarget(String endereco, int qtdBlocos) {

        target = new double[qtdBlocos][2];

        String arquivo = endereco;

        BufferedReader conteudoCSV = null;

        String linha = "";

        String csvSeparador = ",";

        try {

            conteudoCSV = new BufferedReader(new FileReader(arquivo));

            linha = conteudoCSV.readLine(); //leitura primeira linha com o nome de todas colunas

            String[] valoresLinha;

            int i = 0;

            while ((linha = conteudoCSV.readLine()) != null) {

                valoresLinha = linha.split(csvSeparador);

                target[i][0] = Double.parseDouble(valoresLinha[0]);
                target[i][1] = Double.parseDouble(valoresLinha[1]);

                i++;

            }

        } catch (Exception e) {
        }

    }

    public Bloco criaBloco(LinkedList<Linha> linhas) {
        Bloco novoBloco = new Bloco(linhas.size(), linhas);
        return novoBloco;
    }

    public int converteCluster(String x) {

        switch (x) {
            case "\"X\"":
                return 1;
            case "\"Y\"":
                return 2;
            case "\"XY\"":
                return 3;
            case "\"L\"":
                return 4;
            default:
                //caso seja E
                System.out.println("TENHO GRUPO EEEEE");
                return 5;
        }

    }

    public int convertSample(String x) {
        switch (x) {
            case "\"Sample\"":
                return 1;
            default:
                //caso seja Negative
                return 2;
        }
    }

    public Porcentagens calculaPorcentagens(LinkedList<Linha> bloco) {
        int somax = 0;
        int somay = 0;
        int somaxy = 0;
        int somal = 0;
        int somee = 0;

        for (Linha l : bloco) {
            //System.out.println("Valor cluser: " + l.cluster);
            switch (l.cluster) {
                case 1:
                    somax++;
                    break;
                case 2:
                    somay++;
                    break;
                case 3:
                    somaxy++;
                    break;
                case 4:
                    somal++;
                    break;
                default:
                    somee++;
                    break;
            }
        }
        int qtdTotalAmostras = bloco.size();

        Porcentagens m = new Porcentagens();

        m.x = (double) somax / qtdTotalAmostras;

        m.y = (double) somay / qtdTotalAmostras;

        m.xy = (double) somaxy / qtdTotalAmostras;

        m.l = (double) somal / qtdTotalAmostras;

        m.e = (double) somee / qtdTotalAmostras;
        return m;
    }

    public Porcentagens calculaMedias(LinkedList<Linha> bloco) {
        double somax = 0;
        int qtdx = 0;

        double somay = 0;
        int qtdy = 0;

        double somaxy = 0;
        int qtdxy = 0;

        double somal = 0;
        int qtdl = 0;

        double somee = 0;
        int qtde = 0;

        for (Linha l : bloco) {
            //System.out.println("Valor cluser: " + l.cluster);
            switch (l.cluster) {
                case 1:
                    somax += l.cluster;
                    somax++;
                    break;
                case 2:
                    somay += l.cluster;
                    qtdy++;
                    break;
                case 3:
                    somaxy += l.cluster;
                    qtdxy++;
                    break;
                case 4:
                    somal += l.cluster;
                    qtdl++;
                    break;
                default:
                    somee += l.cluster;
                    qtde++;
                    break;
            }
        }
        int qtdTotalAmostras = bloco.size();

        Porcentagens m = new Porcentagens();

        m.x = (double) somax / qtdx;

        m.y = (double) somay / qtdy;

        m.xy = (double) somaxy / qtdxy;

        m.l = (double) somal / qtdl;

        m.e = (double) somee / qtde;
        return m;
    }

    public int qtdSample(LinkedList<Linha> bloco) {
        int som = 0;
        for (Linha l : bloco) {
            if (l.sampletype == 1) {
                som++;
            }
        }
        return som;
    }

    public int qtdNegative(LinkedList<Linha> bloco) {
        int som = 0;
        for (Linha l : bloco) {
            if (l.sampletype == 2) {
                som++;
            }
        }
        return som;
    }

//    public double d(int origem, int destino){
//        
//    }
}
