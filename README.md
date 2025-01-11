# SkinMod

Um mod para Minecraft Forge 1.18.2 que permite aos jogadores mudarem suas skins em servidores offline, mantendo-as salvas mesmo após reconectar.

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.18.2-green)
![Forge Version](https://img.shields.io/badge/Forge-40.2.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Funcionalidades

- **Comando `/skin`**: Permite aplicar a skin de qualquer jogador premium
- **Sistema de Armazenamento**: Salva automaticamente a última skin escolhida
- **Atualização em Tempo Real**: As skins são atualizadas instantaneamente
- **Persistência**: As skins são mantidas mesmo após desconectar/reconectar
- **Compatibilidade**: Funciona em servidores offline/piratas

## 🚀 Instalação

1. Certifique-se de ter o [Minecraft Forge 1.18.2](https://files.minecraftforge.net/) instalado
2. Baixe o arquivo `.jar` mais recente da seção [Releases](https://github.com/seu-usuario/skinmod/releases)
3. Coloque o arquivo na pasta `mods` do seu servidor
4. Inicie o servidor

## 💡 Comandos

| Comando | Descrição | Permissão |
|---------|-----------|-----------|
| `/skin <nickname>` | Aplica a skin do jogador premium especificado | Nenhuma |

### Exemplos
- `/skin Notch` - Aplica a skin do Notch
- `/skin Dream` - Aplica a skin do Dream

## 📁 Estrutura de Arquivos

O mod cria automaticamente um arquivo `skin_data.json` na pasta principal do servidor para armazenar as skins escolhidas pelos jogadores.

Exemplo do arquivo:
```json
{
  "jogador1": "Notch",
  "jogador2": "Dream"
}
```

## 📜 Licença

Este projeto está sob a licença MIT.

## ✨ Créditos

- Inspirado no [SkinsRestorer](https://github.com/SkinsRestorer/SkinsRestorerX)
