package com.diefesson.rb;

import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 *
 * @author Diefesson de Sousa Silva
 */
public class RB {

    public static void main(String[] args) {
        iniciar(args);
    }
    
    public static void testar(){
        Relacao r = new Relacao(Arrays.asList(1, 2, 3, 4 ,5));
        
        r.definirRelacao(1, 2, true);
        r.definirRelacao(2, 3, true);
        
        System.out.println(r.verificarTransitiva());
    }
    
    public static void iniciar(String[] args){
        String caminho = (args.length != 0) ? args[0] : "relacoes.txt";

        Relacao r;
        try {
            System.out.println("Abrindo " + caminho);
            r = new Relacao(caminho);
            System.out.println("Relacões carregadas: " + r);

        } catch (FileNotFoundException ex) {
            System.out.println("Não foi póssivel ler o arquivo " + caminho);
            return;
        }
        
        System.out.println();

        Relacao reflexiva = r.verificarReflexiva();
        if (reflexiva.vazia()) {
            System.out.println("Reflexiva: V");
        } else {
            System.out.println("Reflexiva: F");
            System.out.println(reflexiva);
        }
        
        System.out.println();

        Relacao irreflexiva = r.verificarIrreflexivo();
        if (irreflexiva.vazia()) {
            System.out.println("Irreflexiva: V");
        } else {
            System.out.println("Irreflexiva: F");
            System.out.println(irreflexiva);
        }
        
        System.out.println();
        
        Relacao simetrica = r.verificarSimetrica();
        if(simetrica.vazia()){
            System.out.println("Simetrica: V");
        } else {
            System.out.println("Simetrica: F");
            System.out.println(simetrica);
        }
        
        System.out.println();
        
        Relacao antiSimetrica = r.verificarAntiSimetrica();
        if(antiSimetrica.vazia()){
            System.out.println("Anti simetrica: V");
        } else {
            System.out.println("Anti simetrica: F");
            System.out.println(antiSimetrica);
        }
        
        System.out.println();
        
        if(irreflexiva.vazia() && antiSimetrica.vazia()){
            System.out.println("Assimetrica: V");
        } else {
            System.out.println("Assimetrica: F");
        }
        
        Relacao transitiva = r.verificarTransitiva();
        if(transitiva.vazia()){
            System.out.println("Transitiva: V");
        } else {
            System.out.println("Transitiva: F");
            System.out.println(transitiva);
        }
        
        boolean equivalencia = false, ordemParcial = false;
        if(reflexiva.vazia() && transitiva.vazia()){
            if(simetrica.vazia()){
                equivalencia = true;
            }
            if(antiSimetrica.vazia()){
                ordemParcial = true;
            }
        }
        
        System.out.println();
        
        System.out.println("Relação de equivalência: " + ((equivalencia) ? "V" : "F"));
        System.out.println("Relação de ordem parcial: " + ((ordemParcial) ? "V" : "F"));
        
        System.out.println();
        
        reflexiva.unir(r);
        simetrica.unir(r);
        transitiva.unir(r);
        
        System.out.println("Fecho reflexivo: " + reflexiva);
        System.out.println("Fecho simetrico: " + simetrica);
        System.out.println("Fecho transitivo: " + transitiva);
    }
}