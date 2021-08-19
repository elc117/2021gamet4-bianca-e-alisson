# Paradigmas: T4 Produção de Jogos.
_Alisson Costa Schmidt e Bianca Sabrina Bublitz._


## Proposta:
Criar um jogo de aventura inspirado na vida universitária na UFSM, usando um framework orientado a objetos em Java (LibGDX).

### Regras:
1.  O jogo deverá ter diversos elementos que serão representados por classes e objetos no código.

1.  O jogo deverá ter pelo menos 2 telas.

1.  Além de imagens, o jogo também deverá apresentar algum texto.

1.  O jogo deverá funcionar em desktop e em navegador web, usando as facilidades da LibGDX para aproveitar o mesmo código em diferentes plataformas.

1.  O jogo deverá ser disponibilizado no itch.io, em uma página de "game jam" que será criada pela professora.


## Idéia da dupla:
Um jogo de plataforma inspirado na vida acadêmica, com um recorte para a época de fim do semestre, onde entramos em uma verdadeira corrida para entregar os trabalhos e ir bem nas provas. Essa aventura terá como cenário um ambiente que faz referência ao CT e aos seus Laboratórios, como estamos em EAD desde o ingresso no curso, essa será a maneira encontrada de desfrutar desses espaços.

Uma história simples e pela qual todos nós passamos: Um(a) aluno(a) matriculado na cadeira de paradigmas que quer fazer as práticas para aprender 3 novas linguagens de programação (não espera por Prolog), o personagem deverá ser controlado pelo mapa a fim de coletar todos as partes das práticas e entregar/fazer commit a tempo.

### Idéias extras:

Algumas fases podem contar com personalizações para evitar a repetição, como por exemplo aumentar o grau de dificuldade justificando o acúmulo de matéria ou vários trabalhos para a mesma data e/ou o/a estudante estará cansado(a), então se movendo mais lentamente...

***Talvez*** o uso de sistemas de bônus, como por exemplo, conseguir fazer uma prática de Prolog no tempo estipulado.

## Objetivo do jogador(a):
Coletar e entregar as partes dos trabalhos/práticas que estão espalhados pelo mapa a tempo para que não reprove na cadeira.
Deve fazer um caminho inteligente para otimizar o tempo.

**OBS:** Com mais _tempo_ e _trabalho em cima do jogo_, poderíamos exibir o código dentro de cada parte, e pedir para que o estudante pegasse na ordem correta, assim fazendo uma **revisão** do conteúdo, até aqui.

## Jogabilidade/Mecânica:
A movimentação do personagem pelas plataformas se dá usando as setas do teclado e a barra de espaço.

* `Seta_Cima / Espaço` - Pula;

* `Seta_Direita` - Anda para a direita;

* `Seta_Esquerda` - Anda para a esquerda;

* `Seta_Baixo` - Agacha.

#### Talvez:

* `Z` - Toma café (buff de velocidade);

* `X` - _Ainda não decidido_;

* `V` - Desliza (se estiver parado, apenas agacha);

* `V + Seta_pra_cima / espaço` - Salto maior;

* `P` - Tranca o semestre (Pause)/Destranca (Continue);


## OBS: Devido ao excesso de bugs, ainda não roda na web.
### Erro:
```
Execution failed for task ':html:compileGwt'.
Process 'command 'C:\Program Files\Java\jdk-11.0.12\bin\java.exe'' finished with non-zero exit value 1.
```

Optei pela minha sanidade atual.
