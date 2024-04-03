<!-- ![Android CI](https://github.com/TosinRoger/ListGithubUsers/actions/workflows/androidCI_main.yml/badge.svg?branch=main) -->
[![Coverage](.github/badges/jacoco.svg)](https://github.com/TosinRoger/ListGithubUsers/actions/workflows/androidCI_main.yml)
<!--![GitHub release (latest by date)](https://img.shields.io/github/v/release/TosinRoger/ListGithubUsers) -->

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
- [ ] Implementar a busca de usuário por noma na tela de lista de usuário;
- [ ] Criar os teste de unidade para a busca de usuário por texto;
- [ ] EXTRA: Implementar divisão de tela no modo tablet ~(Definir as telas)~ Como foram feitas duas telas, o modo tablet será nas duas telas existentes;
