package com.diefesson.rb;

import com.diefesson.dmatrix.model.Matriz;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Diefesso de Sousa Silva
 */
public class Relacao {

    private List<Integer> conjunto;
    private Matriz relacoes;

    public Relacao(String caminho) throws FileNotFoundException {
        conjunto = new LinkedList<>();

        Scanner leitor = new Scanner(new File(caminho));

        String linha = leitor.nextLine();
        String[] tokens = linha.trim().split(" ");
        for (var t : tokens) {
            conjunto.add(Integer.parseInt(t));
        }

        relacoes = new Matriz(conjunto.size());

        while (leitor.hasNext()) {
            linha = leitor.nextLine();
            tokens = linha.trim().split(" ");
            int a = Integer.parseInt(tokens[0]);
            int b = Integer.parseInt(tokens[1]);
            definirRelacao(a, b, true);
        }
    }

    public Relacao(Collection<Integer> conjunto) {
        this.conjunto = new LinkedList<>(conjunto);
        relacoes = new Matriz(conjunto.size());
    }

    public Relacao(Relacao original) {
        this(original, true);
    }


    /**
     * 
     * @param original O objeto original
     * @param relacoes Se deve copiar as relaçoes
     */
    public Relacao(Relacao original, boolean relacoes){
        conjunto = new LinkedList<>(original.conjunto);
        if(relacoes){
            this.relacoes = new Matriz(original.relacoes);
        } else {
            this.relacoes = new Matriz(conjunto.size());
        }
    }

    public void definirRelacao(int a, int b, boolean estado) {
        relacoes.definirBooleano(conjunto.indexOf(a), conjunto.indexOf(b), estado);
    }
    
    public boolean obterRelacao(int a, int b){
        return relacoes.obterBooleano(conjunto.indexOf(a), conjunto.indexOf(b));
    }

    public Relacao verificarReflexiva() {
        Relacao pares = new Relacao(conjunto);

        for (int i = 0, o = relacoes.obterAltura(); i < o; i++) {
            if (!relacoes.obterBooleano(i, i)) {
                pares.relacoes.definirBooleano(i, i, true);
            }
        }

        return pares;
    }

    public Relacao verificarIrreflexivo() {
        Relacao pares = new Relacao(conjunto);

        for (int i = 0, o = relacoes.obterAltura(); i < o; i++) {
            if (relacoes.obterBooleano(i, i)) {
                pares.relacoes.definirBooleano(i, i, true);
            }
        }

        return pares;
    }

    public Relacao verificarSimetrica() {
        Relacao pares = new Relacao(conjunto);

        int ordem = relacoes.obterAltura();
        for (int i = 0; i < ordem; i++) {
            for (int j = i + 1; j < ordem; j++) {
                boolean a = relacoes.obterBooleano(i, j);
                boolean b = relacoes.obterBooleano(j, i);
                if (a != b) {//Um é 0 e o outro é 1
                    if (a) {
                        pares.relacoes.definirBooleano(j, i, true);//b vira 1
                    } else {
                        pares.relacoes.definirBooleano(i, j, true);//a vira 1
                    }
                }
            }
        }

        return pares;
    }

    public Relacao verificarAntiSimetrica() {
        Relacao pares = new Relacao(conjunto);

        int ordem = relacoes.obterAltura();
        for (int i = 0; i < ordem; i++) {
            for (int j = i + 1; j < ordem; j++) {
                if (relacoes.obterBooleano(i, j) && relacoes.obterBooleano(j, i)) {
                    pares.relacoes.definirBooleano(i, j, true);
                    pares.relacoes.definirBooleano(j, i, true);
                }
            }
        }

        return pares;
    }

    public Relacao verificarTransitiva() {
        Relacao pares = new Relacao(this);// R^1 = R
        
        Matriz r = new Matriz(relacoes, false, true);
        for(int i = 1, o = relacoes.obterAltura(); i < o; i++){
            r = r.multiplicacaoBooleana(relacoes);
            pares.relacoes.ou(r);
        }
        
        pares.relacoes.subtracaoBooleana(relacoes);//Quero apenas os valores que devem ser adcionados
        return pares;
    }

    
    public void unir(Relacao outra){
        relacoes.ou(outra.relacoes);
    }
    
    public void remover(Relacao outra){
        relacoes.subtracaoBooleana(outra.relacoes);
    }

    public boolean vazia() {
        return relacoes.verificarNula();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');

        for (int i = 0, m = relacoes.obterAltura(); i < m; i++) {
            for (int j = 0, n = relacoes.obterLargura(); j < n; j++) {
                if (relacoes.obterBooleano(i, j)) {
                    sb
                            .append("(")
                            .append(conjunto.get(i))
                            .append(",")
                            .append(conjunto.get(j))
                            .append("), ");
                }
            }
        }
        int rem = sb.lastIndexOf(", ");
        if(rem != -1){
            sb.delete(rem, sb.length());
        }
        sb.append("}");

        return sb.toString();
    }
}
