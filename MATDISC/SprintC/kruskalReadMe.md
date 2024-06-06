# Análise de Complexidade Temporal

## numberOfVertices

- Loop externo: `O(E)` onde `E` é o número de arestas.
- Loop interno: `O(V)` onde `V` é o número de vértices acumulados.
- Comparações dentro do loop interno: `O(1)`.
- Inserções na lista de vértices: `O(1)`.
- **Complexidade total**: `O(E * V)`.





## sortArrayListPrimitivePerPrice

Pseudo:
function sortArrayListPrimitivePerPrice(edges):
    for i from 0 to size(edges) - 1:
        for j from i + 1 to size(edges):
            if edges[j].getPrice() < edges[i].getPrice():
                swap edges[i] and edges[j]

 
- Dois loops aninhados sobre as arestas: `O(E^2)`.
- **Complexidade total**: `O(E^2)`.


- Esta função é uma implementação de ordenação (talvez Bubble Sort ou Selection Sort).
- O loop externo percorre todas as arestas (n elementos).
- O loop interno também percorre todas as arestas (n elementos).
- Dentro do loop interno, há uma comparação de preços.
- Portanto, a complexidade temporal é \(O(n^2)\).

\sum\limits_{i=1}^{n - 1} n - i   =  \sum\limits_{i=1}^{n - 1} n    -  \sum\limits_{i=1}^{n - 1}  i  = [ n \cdot n - \frac{{1 + (n-1)}}{2} \cdot (n - 1) ]



## getVertices

- Loop sobre as arestas: `O(E)`.
- Checagem e inserção em lista de vértices: `O(V)` no pior caso para cada aresta.
- **Complexidade total**: `O(E * V)`.

## find

- Operação amortizada de `find` (usando compressão de caminho): `O(α(V))` onde `α` é a inversa da função de Ackermann, muito lenta, praticamente constante.
- **Complexidade total**: `O(α(V))`.

## union

- Operação amortizada de `union` (usando união por ranking): `O(α(V))`.
- **Complexidade total**: `O(α(V))`.

## kruskalAlgorithm

- Obtenção de vértices: `O(E * V)`.
- Inicialização de estruturas Union-Find: `O(V)`.
- Loop principal: `O(E)` operações de `find` e `union`, cada uma `O(α(V))`.

- **Complexidade total**: `O(E * V) + O(E * α(V))` que simplifica para `O(E * V)` no pior caso pelo Teorema 3.3 da sebenta, da pagina 70.

## findVertexIndex

- Loop linear sobre os vértices: `O(V)`.
- **Complexidade total**: `O(V)`.




Claro! Vamos analisar a complexidade temporal dos três algoritmos presentes no pseudocódigo:

1. **Função `numberOfVertices(edges)`**:
    - Essa função cria uma lista de vértices a partir de uma lista de arestas.
    - O loop externo percorre todas as arestas (n elementos).
    - O loop interno percorre todos os vértices (m elementos, onde m é o número de vértices já encontrados).
    - Portanto, a complexidade temporal é \(O(n \cdot m)\).

2. **Função `sortArrayListPrimitivePerPrice(edges)`**:
    - Essa função parece ser uma implementação de ordenação (talvez Bubble Sort ou Selection Sort).
    - O loop externo percorre todas as arestas (n elementos).
    - O loop interno também percorre todas as arestas (n elementos).
    - Dentro do loop interno, há uma comparação de preços.
    - Portanto, a complexidade temporal é \(O(n^2)\).

3. **Função `kruskalAlgorithm(edges)`**:
    - Essa função implementa o algoritmo de Kruskal para encontrar a árvore geradora mínima de um grafo.
    - A complexidade temporal do algoritmo de Kruskal é dominada pela ordenação das arestas.
    - Portanto, a complexidade temporal é \(O(n \log n)\) (onde \(n\) é o número de arestas).

Em resumo:
- A complexidade da função `numberOfVertices` é \(O(n \cdot m)\).
- A complexidade da função `sortArrayListPrimitivePerPrice` é \(O(n^2)\).
- A complexidade da função `kruskalAlgorithm` é \(O(n \log n)\).

Lembre-se de que essas análises consideram apenas o tempo de execução em termos de operações e não levam em conta fatores como hardware ou sistema operacional. Além disso, a complexidade espacial não foi discutida aqui, mas é importante considerá-la ao avaliar algoritmos em cenários reais.¹²³
