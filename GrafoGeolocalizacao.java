import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GrafoGeolocalizacao {
    private Map<String, Map<String, Double>> grafo;

    public GrafoGeolocalizacao() {
        this.grafo = new HashMap<>();
    }

    public void adicionarRota(String origem, String destino, double distancia) { // esse método ele serve para adicionar uma aresta entre as duas vértices.
        origem = origem.toLowerCase(); // criei para ignorar se esta em upcase ou lower case
        destino = destino.toLowerCase(); // criei para ignorar se esta em upcase ou lower case
        
        if (!grafo.containsKey(origem)) {
            grafo.put(origem, new HashMap<>());
        }
        grafo.get(origem).put(destino, distancia); // recebe a origem e o destino 

        if (!grafo.containsKey(destino)) {
            grafo.put(destino, new HashMap<>());
        }
        grafo.get(destino).put(origem, distancia); // recebe também a distância
    }

    public void adicionarLocalizacao(String localizacao) { // esse método adiciona uma vértice que e a localização para o grafo.
        localizacao = localizacao.toLowerCase(); // criei para ignorar se esta em upcase ou lower case
        
        if (!grafo.containsKey(localizacao)) {
            grafo.put(localizacao, new HashMap<>());
        }
    }

    public double calcularDistancia(String origem, String destino) { // esse método ele recebe a origem e o destino como parametro e retona a sua distância.
        origem = origem.toLowerCase(); // criei para ignorar se esta em upcase ou lower case
        destino = destino.toLowerCase();// criei para ignorar se esta em upcase ou lower case
        
        List<String> caminho = calcularCaminhoMaisRapido(origem, destino);
        return calcularDistanciaDoCaminho(caminho);
    }

    public List<String> calcularCaminhoMaisRapido(String origem, String destino) { // esse método ele utiliza o algoritmo de Dijkstra para encontrar o caminho com a menor distância. O método retorna uma lista de localizações que representam o caminho mais rápido.
        origem = origem.toLowerCase();// criei para ignorar se esta em upcase ou lower case
        destino = destino.toLowerCase();// criei para ignorar se esta em upcase ou lower case
        
        if (!grafo.containsKey(origem) || !grafo.containsKey(destino)) {
            throw new IllegalArgumentException("Localização não encontrada no grafo.");
        }

        Map<String, Double> distancias = new HashMap<>();
        for (String localizacao : grafo.keySet()) {
            distancias.put(localizacao, Double.MAX_VALUE);
        }

        distancias.put(origem, 0.0);

        Map<String, Boolean> visitado = new HashMap<>();
        Map<String, String> caminhoAnterior = new HashMap<>();

        for (int i = 0; i < grafo.size(); i++) {
            String verticeAtual = obterVerticeComMenorDistancia(distancias, visitado);
            visitado.put(verticeAtual, true);

            Map<String, Double> vizinhos = grafo.get(verticeAtual);
            for (String vizinho : vizinhos.keySet()) {
                double distanciaAtual = distancias.get(vizinho);
                double distanciaNova = distancias.get(verticeAtual) + vizinhos.get(vizinho);
                if (distanciaNova < distanciaAtual) {
                    distancias.put(vizinho, distanciaNova);
                    caminhoAnterior.put(vizinho, verticeAtual);
                }
            }
        }

        List<String> caminho = new ArrayList<>();
        String verticeAtual = destino;
        while (verticeAtual != null) {
            caminho.add(0, verticeAtual);
            verticeAtual = caminhoAnterior.get(verticeAtual);
        }

        return caminho;
    }

    public List<String> calcularSegundoCaminhoMaisRapido(String origem, String destino) { // método ele primeiro encontra o caminho mais rápido e, em seguida, remove as rotas desse caminho para encontrar o segundo caminho mais rápido. O método retorna uma lista de localizações que representam o segundo caminho mais rápido.
        origem = origem.toLowerCase();// criei para ignorar se esta em upcase ou lower case
        destino = destino.toLowerCase();// criei para ignorar se esta em upcase ou lower case
        
        if (!grafo.containsKey(origem) || !grafo.containsKey(destino)) {
            throw new IllegalArgumentException("Localização não encontrada no grafo.");
        }

        List<String> caminhoMaisRapido = calcularCaminhoMaisRapido(origem, destino);

        // Remover as rotas do caminho mais rápido para encontrar o segundo caminho mais rápido
        for (int i = 0; i < caminhoMaisRapido.size() - 1; i++) {
            String localizacaoAtual = caminhoMaisRapido.get(i);
            String proximaLocalizacao = caminhoMaisRapido.get(i + 1);
            grafo.get(localizacaoAtual).remove(proximaLocalizacao);
            grafo.get(proximaLocalizacao).remove(localizacaoAtual);
        }

        List<String> segundoCaminhoMaisRapido = calcularCaminhoMaisRapido(origem, destino);

        // Restaurar as rotas removidas anteriormente
        for (int i = 0; i < caminhoMaisRapido.size() - 1; i++) {
            String localizacaoAtual = caminhoMaisRapido.get(i);
            String proximaLocalizacao = caminhoMaisRapido.get(i + 1);
            grafo.get(localizacaoAtual).put(proximaLocalizacao, calcularDistancia(localizacaoAtual, proximaLocalizacao));
            grafo.get(proximaLocalizacao).put(localizacaoAtual, calcularDistancia(localizacaoAtual, proximaLocalizacao));
        }

        if (segundoCaminhoMaisRapido.isEmpty()) {
            throw new IllegalArgumentException("Não foi possível encontrar outro segundo caminho.");
        }

        return segundoCaminhoMaisRapido;
    }

    private String obterVerticeComMenorDistancia(Map<String, Double> distancias, Map<String, Boolean> visitado) { // essa função ela vai auxiliar a encontrar o vértice com a menor distância não visitada.
        double menorDistancia = Double.MAX_VALUE;
        String verticeMenorDistancia = null;

        for (String localizacao : distancias.keySet()) {
            double distancia = distancias.get(localizacao);
            if (!visitado.containsKey(localizacao) && distancia < menorDistancia) {
                menorDistancia = distancia;
                verticeMenorDistancia = localizacao;
            }
        }

        return verticeMenorDistancia;
    }

    private double calcularDistanciaDoCaminho(List<String> caminho) { // esse método calcula a distância de um caminho, somando as distâncias entre as localizações.
        double distanciaTotal = 0.0;
        for (int i = 0; i < caminho.size() - 1; i++) {
            String localizacaoAtual = caminho.get(i);
            String proximaLocalizacao = caminho.get(i + 1);
            distanciaTotal += grafo.get(localizacaoAtual).get(proximaLocalizacao);
        }
        return distanciaTotal;
    }
}
