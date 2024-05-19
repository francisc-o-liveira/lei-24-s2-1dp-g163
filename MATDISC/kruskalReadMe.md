# Análise de Complexidade Temporal

## numberOfVertices

- Loop externo: `O(E)` onde `E` é o número de arestas.
- Loop interno: `O(V)` onde `V` é o número de vértices acumulados.
- Comparações dentro do loop interno: `O(1)`.
- Inserções na lista de vértices: `O(1)`.
- **Complexidade total**: `O(E * V)`.

## readFromFile

- Loop: `O(L)` onde `L` é o número de linhas no arquivo (ou arestas).
- **Complexidade total**: `O(L)`.

## sortArrayListPrimitivePerPrice

- Dois loops aninhados sobre as arestas: `O(E^2)`.
- **Complexidade total**: `O(E^2)`.

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
- **Complexidade total**: `O(E * V) + O(E * α(V))` que simplifica para `O(E * V)` no pior caso.

## findVertexIndex

- Loop linear sobre os vértices: `O(V)`.
- **Complexidade total**: `O(V)`.
