import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Criar um grafo de geolocalização
        GrafoGeolocalizacao grafo = new GrafoGeolocalizacao();

        // Adicionar localizações (vértices) ao grafo
        grafo.adicionarLocalizacao("A");
        grafo.adicionarLocalizacao("B");
        grafo.adicionarLocalizacao("C");
        grafo.adicionarLocalizacao("D");
        grafo.adicionarLocalizacao("E");
        grafo.adicionarLocalizacao("F");
        grafo.adicionarLocalizacao("G");
        grafo.adicionarLocalizacao("H");
        grafo.adicionarLocalizacao("I");
        grafo.adicionarLocalizacao("J");
        grafo.adicionarLocalizacao("K");
        grafo.adicionarLocalizacao("L");
        grafo.adicionarLocalizacao("M");
        grafo.adicionarLocalizacao("N");
        grafo.adicionarLocalizacao("O");
        grafo.adicionarLocalizacao("P");
        grafo.adicionarLocalizacao("Q");
        grafo.adicionarLocalizacao("R");
        grafo.adicionarLocalizacao("S");
        grafo.adicionarLocalizacao("T");
        grafo.adicionarLocalizacao("U");
        grafo.adicionarLocalizacao("V");
        grafo.adicionarLocalizacao("X");

        // Adicionar rotas (arestas) entre as localizações no grafo
        grafo.adicionarRota("A", "B", 300.0);
        grafo.adicionarRota("B", "C", 47.0);
        grafo.adicionarRota("C", "D", 62.0);
        grafo.adicionarRota("C", "H", 141.0);
        grafo.adicionarRota("D","E", 8.0);
        grafo.adicionarRota("E","F", 13.0);
        grafo.adicionarRota("E","G", 230.0);
        grafo.adicionarRota("H","I", 138.0);
        grafo.adicionarRota("I","J", 153.0);
        grafo.adicionarRota("J","K", 512.0);
        grafo.adicionarRota("K","L", 135.0);
        grafo.adicionarRota("L","M", 0.0);
        grafo.adicionarRota("L","N", 187.0);
        grafo.adicionarRota("N","O", 108.0);
        grafo.adicionarRota("O","P", 82.0);
        grafo.adicionarRota("P","Q", 215.0);
        grafo.adicionarRota("Q","R", 97.0);
        grafo.adicionarRota("R","S", 33.0);
        grafo.adicionarRota("R","T", 243.0);
        grafo.adicionarRota("S","T", 207.0);
        grafo.adicionarRota("S","V", 38.0);
        grafo.adicionarRota("T","U", 22.0);
        grafo.adicionarRota("U","V", 210.0);
        grafo.adicionarRota("V","A", 370.0);
        grafo.adicionarRota("U","X", 107.0);
        grafo.adicionarRota("X","A", 317.0);

        // Solicitar a origem e o destino ao usuário
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nDigite o Ponto de origem: "); 
        String origem = scanner.nextLine();
        System.out.print("Digite o Ponto de destino: ");
        String destino = scanner.nextLine();

        // Calcular a distância e obter o caminho mais rápido entre origem e destino
        List<String> caminhoMaisRapido = grafo.calcularCaminhoMaisRapido(origem, destino);
        double distancia = grafo.calcularDistancia(origem, destino);

        // Imprimir a distância e o caminho mais rápido
        System.out.println("\nDistância entre " + origem + " e " + destino + ": " + distancia + " metros");
        System.out.println("\nCaminho mais rápido: " + caminhoMaisRapido);

        List<String> segundoCaminhoMaisRapido = grafo.calcularSegundoCaminhoMaisRapido(origem, destino);
        System.out.println("Segundo caminho mais rápido: " + segundoCaminhoMaisRapido);

    }
}
