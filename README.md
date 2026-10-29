# Jogo da Vida — Simulação de Ecossistema em Java

Simulação gráfica de um ecossistema em Java, onde criaturas procuram alimento, consomem energia e se reproduzem, enquanto predadores procuram capturá-las. O utilizador pode observar a evolução da população e participar na simulação através de um predador controlado pelo teclado.

Apesar do nome, este projeto implementa uma simulação de criaturas e predadores com regras próprias, e não o autómato celular clássico de Conway.

## Funcionalidades

- Geração inicial de 50 criaturas e distribuição aleatória de alimento.
- Movimento autónomo e procura de alimento pelas criaturas.
- Gestão de energia: movimento com consumo de energia, alimentação e remoção das criaturas sem energia.
- Reprodução de criaturas mediante condições de encontro, energia e aleatoriedade.
- Dois predadores autónomos que procuram e consomem criaturas.
- Um predador controlado pelo utilizador, com movimento horizontal, vertical e diagonal.
- Crescimento de alimento ao longo da simulação.
- Estatísticas no ecrã: criaturas vivas, total de criaturas criadas, quantidade de posições com erva e número de capturas.
- Apresentação das capturas por predador e dos respetivos valores de energia.

## Tecnologias utilizadas

- **Java** — lógica da simulação e organização em classes.
- **Swing** — janela e painel da aplicação (`JFrame` e `JPanel`).
- **AWT / Graphics2D** — desenho dos elementos gráficos.
- **ImageIO** — carregamento das imagens.
- **Java Collections** — gestão da população com `ArrayList` e `ListIterator`.
- **Threads e KeyListener** — ciclo de atualização e tratamento do teclado.

O projeto utiliza bibliotecas da plataforma Java, sem dependências externas de compilação.

## Como executar

### Requisitos

- JDK instalado, com os comandos `java` e `javac` disponíveis no terminal.
- Ambiente de trabalho com interface gráfica.

### Passos

1. Descarrega o repositório e extrai o ZIP, ou utiliza uma cópia local do projeto.
2. Abre um terminal na pasta que contém `jogo.java` e as imagens.
3. Compila os ficheiros:

```bash
javac -encoding UTF-8 *.java
```

4. Inicia a aplicação:

```bash
java jogo
```

A classe principal chama-se `jogo`, com letra inicial minúscula.

**Importante:** executa os comandos a partir da pasta do projeto. As imagens `criatura.jpg`, `papatudo.jpg` e `papatudo2.jpg` são carregadas através de caminhos relativos e devem permanecer nessa pasta.

Também podes abrir o projeto num IDE Java e executar o método `main` de `jogo.java`, definindo a pasta do projeto como diretório de trabalho.

## Controlos

| Tecla | Ação |
| --- | --- |
| W | Mover para cima |
| A | Mover para a esquerda |
| S | Mover para baixo |
| D | Mover para a direita |
| Duas teclas de direções compatíveis, como W + D | Mover na diagonal |
| Fechar a janela | Terminar a aplicação |

A simulação inicia automaticamente. Se as teclas não responderem, clica na área do jogo para lhe dar foco.

## Organização do código

| Ficheiro | Responsabilidade |
| --- | --- |
| `jogo.java` | Ponto de entrada; criação da janela e arranque da simulação. |
| `PainelJogo.java` | Ciclo de atualização, população, alimento, estatísticas e desenho do cenário. |
| `Criatura.java` | Movimento, procura de alimento, energia e condições de reprodução das criaturas. |
| `Predador.java` | Comportamento dos predadores autónomos e captura de criaturas. |
| `papatudo.java` | Especialização de `Predador` com movimento controlado pelo utilizador. |
| `KeyHandler.java` | Registo das teclas pressionadas e libertadas. |
| `Sound.java` | Estrutura de carregamento e reprodução de áudio. |

## Conceitos de programação presentes

- Programação orientada a objetos e herança.
- Matrizes bidimensionais para representar ocupação e alimento.
- Gestão de coleções de objetos durante a simulação.
- Comportamentos baseados em condições e aleatoriedade.
- Tratamento de eventos do teclado.
- Separação entre atualização do estado e desenho da interface.

## Estado atual e possíveis melhorias

O código inclui uma estrutura de áudio, mas a chamada de reprodução está comentada. Além disso, `Sound.java` procura os ficheiros numa pasta `som/`, enquanto os recursos fornecidos estão na raiz. O áudio necessita, por isso, de ajustes antes de ser ativado.

Possíveis evoluções:

- Adicionar pausa, reinício e menu inicial.
- Permitir configurar a população e a velocidade da simulação.
- Rever os limites de movimento e os acessos às matrizes.
- Melhorar a sincronização entre a atualização da população e o desenho da interface.
- Reorganizar os recursos gráficos e sonoros.
- Adicionar testes à lógica de movimento, alimentação e reprodução.
