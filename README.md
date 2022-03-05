# gerenciadoreventos
Gerenciador de Eventos

Esse projeto foi baseado em:

# mvi 
O mvi é utililzado para gerenciamento de estado do app, onde há apenas um estado para todas as camadas do projeto.

# mvvm
A arquitetura mvvm é utilizada para armazenar e gerenciar dados relacionados à interface do usuário.

# clean architecture
Na camada de domínio criamos os casos de uso da aplicação que tem como objetivo serem mediadores entre suas “ViewModel”s e os “Repository”.

# injeção de dependência
Injeção de dependência é a técnica que delega a responsabilidade de inicializar dependências. Utilizamos o Koin que fornece uma instância dos objetos automaticamente de acordo com o escopo.

# corroutines
Utilizamos corroutines para gereenciar execuções aasíncronas podendo suspender um atividade e retomar em um momento futuro.

# navigation
Para fazer a navegação das telas do app usamos os gráficos de navegação que contém os destinos e ações de todas as telas.

# retrofit
O acesso a API utilizamos o retrofit para criar um cliente HTTP.

# Testes
Utilizamos a api mockk para fazer o mock nos objetos, cucumber para nosso cenários de teste, junit para implementar os testes e espresso para teste de UI.



