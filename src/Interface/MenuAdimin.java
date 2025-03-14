package Interface;

import auxiliares.*;

// Classe do menu principal do usuário admin
public class MenuAdimin {
    private ContextoSistema ctx;

    public MenuAdimin(ContextoSistema ctx) {
        this.ctx = ctx;
    }

    public void exibir() {
        Auxi.clearTerminal();
        while (ctx.isProgramaAtivo()) {
            System.out.println("======== MENU ========\n");
            System.out.println(
                    "[1] - Cadastrar candidato\n" +
                            "[2] - Cadastrar eleitor\n" +
                            "[3] - Votar\n" +
                            "[4] - Configurações da eleição\n" +
                            "[5] - Exibir resultados\n" +
                            "[6] - Voltar ao login\n" +
                            "[0] - Sair do programa");

            String strOpcao = ctx.scan.nextLine();

            if (!Auxi.isValidInt(strOpcao)) {
                Auxi.fixError("Digite apenas números");
                Auxi.clearRange(10, "a");
                continue;
            }

            int opcao = Integer.parseInt(strOpcao);

            // Chamada dos outros menus
            switch (opcao) {
                case 1 -> ctx.exibirMenuCadastroCandidato();
                case 2 -> ctx.exibirMenuCadastroEleitor();
                case 3 -> { // Verifica se a eleiçao está ativa e se o eleitor esta apto a votar
                    if (ctx.eleitor.getJaVotou()) {
                        Auxi.fixError("ERRO! Você não pode votar duas vezes");
                        Auxi.clearRange(10, "a");
                        continue;
                    } else if (ctx.listaCandidatos.getList().isEmpty() || !ctx.eleicao.isAtiva()) {
                        Auxi.fixError("Eleição desativada ou sem candidatos cadastrados até o momento");
                        Auxi.clearRange(10, "a");
                        continue;
                    } else {
                        ctx.exibirMenuVotacao();
                    }
                }
                case 4 -> ctx.exibirMenuConfigEleicao();
                case 5 -> ctx.exibirMenuResultados();
                case 6 -> ctx.exibirMenuLogin();
                case 0 -> {
                    Auxi.fixedError();
                    System.out.println("Saindo...");
                    ctx.encerrarPrograma();
                    return;
                }
                default -> {
                    Auxi.fixError("Opção inválida");
                    Auxi.clearRange(10, "a");
                }
            }
        }
    }
}