/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplinas: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 *
 * Baseado nos slides 104 da aula do dia 27/10/2017  
 *
 * Página 504 Thomas H. Cormen 3a Ed 
 *
 * Caminho mínimos para grafo dirigido com Algoritmo de Floyd-Warshall
 *
 */

/**
 * @author Osmar de Oliveira Braz Junior
 */

import java.util.Stack;

public class Principal {

    //Matriz dos pesos
    static int[][][] D;

    //Matriz dos predecessores
    static int pi[][][];

    /**
     * Imprime na saída padrão a matriz
     *
     * @param X Uma matriz a ser exibida
     */
    public static void imprimir(int[][] X) {
        int ti = X.length; //Colunas        
        int tj = X[0].length; //Linhas
        for (int i = 0; i < ti; i++) {
            for (int j = 0; j < tj; j++) {
                char letra = 8734;
                if (X[i][j] == Integer.MAX_VALUE) {
                    System.out.printf("%c \t", letra);
                } else if (X[i][j] == -Integer.MAX_VALUE) {
                    System.out.printf("-%c \t", letra);
                } else {
                    System.out.printf("%d \t", X[i][j]);
                }
            }
            System.out.println();
        }
    }

    /**
     * Imprime na saída padrão a matriz.
     *
     * @param X Uma matriz a ser exibida
     */
    public static void imprimir(int[][][] X) {
        int tk = X.length; //Colunas        
        int ti = X[0].length; //Colunas        
        int tj = X[0][0].length; //Linhas        
        for (int k = 0; k < tk; k++) {
            System.out.println("X(" + k + ")=");
            for (int i = 0; i < ti; i++) {
                for (int j = 0; j < tj; j++) {
                    char letra = 8734;
                    if (X[k][i][j] == Integer.MAX_VALUE) {
                        System.out.printf("%c \t", letra);
                    } else if (X[k][i][j] == -Integer.MAX_VALUE) {
                        System.out.printf("-%c \t", letra);
                    } else {
                        System.out.printf("%d \t", X[k][i][j]);
                    }
                }
                System.out.println();
            }
        }
    }

    /**
     * Retorna o menor valor entre dois valores inteiros.
     *
     * Em java pode ser utilizando Math.min(int a, int b)
     *
     * @param a primeiro valor inteiro.
     * @param b segundo valor inteiro.
     * @return o menor valor entre os a e b
     */
    public static int min(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }
  
    /**
     * Executa o algoritmo de Floyd-Marshall para Caminhos Mínimos.
     *
     * Calcula os pesos dos caminhos mínimos.
     *
     * @param G Matriz de indicência da grafo
     * @return Matriz calculada
     */
    public static int[][] algoritmoFloydWarshall(int[][] G) {

        //Quantidade de vértices do grafo G
        int n = G.length;
        //Inicializando as matrizes
        D = new int[n + 1][n][n];
        pi = new int[n + 1][n][n];
        
        for (int i = 0; i < G.length; i++) {
            for (int j = 0; j < G.length; j++) {
                //Inicializa das distâncias
                D[0][i][j] = G[i][j];                
                //Inicializa a matriz de predecessores
                if ((i == j) || (D[0][i][j] == Integer.MAX_VALUE)) {
                    pi[0][i][j] = Integer.MAX_VALUE;
                } else {
                    pi[0][i][j] = i + 1;
                }                
            }
        }

        for (int k = 1; k < D.length; k++) {
            for (int i = 0; i < G.length; i++) {
                for (int j = 0; j < G.length; j++) {                    
                    D[k][i][j] = D[k - 1][i][j];
                    pi[k][i][j] = pi[k - 1][i][j];                    
                    if ((D[k - 1][i][k - 1] != Integer.MAX_VALUE) && (D[k - 1][k - 1][j] != Integer.MAX_VALUE)) {
                        //Verifica se tem custo melhor
                        if (D[k - 1][i][j] > D[k - 1][i][k-1] + D[k - 1][k-1][j]) {
                            //Armazena a distância
                            D[k][i][j] = D[k - 1][i][k - 1] + D[k - 1][k - 1][j];
                            //Armazena o predecessor
                            pi[k][i][j] = pi[k - 1][k-1][j];
                        }                        
                    }
                }
            }
        }
        return D[n];
    }

    /**
     * Mostra o caminho mínimo de i até j.
     * 
     * @param i Origem do caminho
     * @param j Final do caminho
     */
    public static void mostrarCaminhoMinimo(int i, int j) {
        Stack<Integer> pilha = new Stack<Integer>();
        System.out.println("\nMenor caminho de " + (i) + " para " + (j));
        System.out.print((i)+"-");
        pilha.add(j);
        //Qtde de vértices
        int n = D[0].length;        
        while (i != pi[n][i-1][j-1]) {            
            pilha.add(pi[n][i-1][j-1]);
            j = pi[n][i-1][j-1];
        } 
        for (int k = 0; k <= pilha.size() + 1; k++) {
            int x = pilha.peek();
            System.out.print((x)+"-");
            pilha.pop();
        }
        System.out.println();
    }

    public static void main(String args[]) {

        //Grafo da página 465 Thomas H. Cormen 3 ed
        //int I = 999;
        int I = Integer.MAX_VALUE;
        int G[][]
                = //1  2  3  4  5
                {{0, 3, 8, I, -4}, //1
                {I, 0, I, 1, 7}, //2
                {I, 4, 0, I, I}, //3
                {2, I, -5, 0, I}, //4
                {I, I, I, 6, 0}};//5               

//    int G[][]
//             = //1  2  3  
//               {{0, 8, 5}, //1
//                {3, 0, I}, //2
//                {I, 2, 0}};//3

        System.out.println(">>> Caminho mínimos Algoritmo de Floyd-Warshall <<<");

        System.out.println("Matriz Inicial:");
        imprimir(G);

        //Executa o algoritmo        
        int[][] X = algoritmoFloydWarshall(G);

        System.out.println("\nMatriz D (Matriz da menor distância entre cada par de vértices):");
        imprimir(D);

        System.out.println("\nMatriz P (Matriz dos predecessores):");
        imprimir(pi);

        mostrarCaminhoMinimo(2,5);
    }
}