![Android CI](https://github.com/TosinRoger/ListGithubUsers/actions/workflows/android_CI_main.yml/badge.svg?branch=main)
<!-- [![Coverage](.github/badges/jacoco.svg)](https://github.com/TosinRoger/ListGithubUsers/actions/workflows/androidCI_main.yml) -->
![GitHub Release](https://img.shields.io/github/v/release/TosinRoger/ListGithubUsers)

# List Github Users

Projeto que lista usuários e projetos dos usuários do Github. Projeto desenvolvido em Kotlin Android.

# Escopo do projeto:
O aplicativo deve consultar a API public do Github e retornar a lista de usuário e mostrar a lista em tela, ao selecionar um usuário deve mostrar os detalhes deste usuário e os repositórios que este usuário tem.
Na tela de lista de usuário deve ser possível buscar um usuário por uma busca por texto.

Host da API: `https://api.github.com`

- [x] Criar uma tela para lista os usuário;
- [x] Consumir a API para retornar a lista de usuários;
- [x] Paginar a lista de usuários;
- [x] Criar os testes de unidade da lista de usuários;
- [x] Criar a tela de detalhes do usuário;
- [x] Consumir a API para retornar os detalhes do usuário;
- [x] Criar os testes de unidade da tela de detalhes do usuário;
- [x] ~Criar a tela de lista de repositórios do usuário~ Foi adicionado na mesma tela de detalhes do usuário;
- [x] Consumir a API da retornar a lista de repositórios do usuário;
- [x] Criar os teste de unidade da lista de repositórios do usuário;
- [x] Implementar a busca de usuário por noma na tela de lista de usuário;
- [x] Criar os teste de unidade para a busca de usuário por texto;
- [ ] EXTRA: Implementar divisão de tela no modo tablet ~(Definir as telas)~ Como foram feitas duas telas, o modo tablet será nas duas telas existentes;

# Pré requisitos
- Ter conhecimento com GIT para clonar o projeto;
- Ter instalado o Java JDK 17 no computado - [download](https://www.oracle.com/java/technologies/downloads/#java17);
- Ter o Android Studio instalado no computador;

# Como testar/rodar o projeto

1 - Executando o projeto a partir do APK de ~release~ debug

Foi criado um APK para executar o projeto sem a necessidade de instalar o Android Studio ou ter que rodar o projeto. Para acessar este APK basta entrar na raiz do projeto e acessar a pasta `release`, dentro desta pasta haverá um arquivo `app-debug - 1.0.0.apk`. Este arquivo pode ser baixado em um telefone Android e instalado.
> Lembre que o telefone tem que esta destravado para executar APK instalado fora da loja, segue um [link](https://support-pt.wd.com/app/answers/detailweb/a_id/28583/~/instalar-aplicativos-móveis-android-manualmente-.apk) com instruções para instalar app desconhecido.

2 - Executando o projeto a partir do Android Studio

Esse é um método mais complicado e exige um pouco mais de conhecimento e vai precisar de alguns requisitos:
- Ter conhecimento com GIT para clonar o projeto;
- Ter instalado o Java JDK 17 no computado;
- Ter o Android Studio instalado no computador;
- Ter um telefone Android ou um emulador com o Android 7 ou maior;

Para baixar e executar o projeto no Android Stuio, clone o projeto a partir do link `https://github.com/TosinRoger/ListGithubUsers.git`. No Android Studio rode o projeto no emulador ou telefone físico.
