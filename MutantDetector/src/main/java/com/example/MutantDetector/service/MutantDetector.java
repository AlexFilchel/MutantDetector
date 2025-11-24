package com.example.MutantDetector.service;

import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class MutantDetector {

    private static final Set<Character> VALIDOS = Set.of('A','T','C','G');
    private static final int SEQ = 4;

    public boolean esMutante(String[] dna) {

        
        if (dna == null || dna.length < SEQ) return false;

        int n = dna.length;

        
        for (String fila : dna) {
            if (fila == null || fila.length() != n) return false;

            for (char c : fila.toCharArray()) {
                if (!VALIDOS.contains(c)) return false;
            }
        }

        // Convertir a matriz char[][]
        char[][] matriz = new char[n][n];
        for (int i = 0; i < n; i++) {
            matriz[i] = dna[i].toCharArray();
        }

       
        int contador = 0;

        for (int fila = 0; fila < n; fila++) {
            for (int col = 0; col < n; col++) {

                boolean puedeHoriz = col <= n - SEQ;
                boolean puedeVert = fila <= n - SEQ;
                boolean puedeDiagDesc = puedeHoriz && puedeVert;
                boolean puedeDiagAsc = puedeHoriz && fila >= SEQ - 1;

                if (puedeHoriz && haySecuenciaHorizontal(matriz, fila, col)) contador++;
                if (puedeVert && haySecuenciaVertical(matriz, fila, col)) contador++;
                if (puedeDiagDesc && haySecuenciaDiagonalDesc(matriz, fila, col)) contador++;
                if (puedeDiagAsc && haySecuenciaDiagonalAsc(matriz, fila, col)) contador++;

                if (contador > 1) return true; 
            }
        }

        return false;
    }

    
    private boolean haySecuenciaHorizontal(char[][] m, int f, int c) {
        char x = m[f][c];
        return m[f][c + 1] == x &&
               m[f][c + 2] == x &&
               m[f][c + 3] == x;
    }

    private boolean haySecuenciaVertical(char[][] m, int f, int c) {
        char x = m[f][c];
        return m[f + 1][c] == x &&
               m[f + 2][c] == x &&
               m[f + 3][c] == x;
    }

    private boolean haySecuenciaDiagonalDesc(char[][] m, int f, int c) {
        char x = m[f][c];
        return m[f + 1][c + 1] == x &&
               m[f + 2][c + 2] == x &&
               m[f + 3][c + 3] == x;
    }

    private boolean haySecuenciaDiagonalAsc(char[][] m, int f, int c) {
        char x = m[f][c];
        return m[f - 1][c + 1] == x &&
               m[f - 2][c + 2] == x &&
               m[f - 3][c + 3] == x;
    }
}
