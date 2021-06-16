// Autores - Claudinei Gomes - Adriel Dias - Vitor Targino - Matheus Meduneckas 
//Lingaguem de Programacao - Fatec PraiaGrande - Vespertino - ADS - 16/06/21

//Pacote que armazena as classes.
package javaapplication9;
//Classe utilizada para o tratamento de exceções da classe EOF.
import java.io.EOFException;
//Classe utilizada para criação de arquivos/objetos.
import java.io.File;
//Classe utilizada para tranformar dados em binario.
import java.io.FileOutputStream;
//Clase utilizada para Escrever em arquivos.
import java.io.FileWriter;
//Classe utilizada para tratamento de execessões IO caso a operação falhe.
import java.io.IOException;
//Classe Garante que o fluxo de entrada se transforme em um objeto.
import java.io.ObjectInputStream;
//Classe Garante que o fluxo de saida se transforme em um objeto/ Utilizada para ler objetos do InputStream.
import java.io.ObjectOutputStream;
//Classe E uma classe habilitada pela classe que implementa o java I.O.
import java.io.Serializable;
//Classe manipula, arquivos diretorios, com extensoes diferentes.
import java.nio.file.Files;
//Classe que localiza/Cria o caminho.
import java.nio.file.Path;
//Classe de objeto que retornam um path utilizando uma string/ exclusivo para "static void".
import java.nio.file.Paths;
//Classe que copia a data que um objeto, define as opcoes de copia padrao.
import java.nio.file.StandardCopyOption;
//Classe que salva a mensagem de erro para depois elas serem acessadas.
import java.util.NoSuchElementException;
//Classe que registra a entrada de dados.
import java.util.Scanner;

//Classe principal do objeto imobi, que armazena todo os paradigmas.
public class Imobi implements Serializable {

    //Variaveis constantes, armazenam dados, que só podem ser acessados dentro da classe imobi.
    private int ref;
    private String tipo;
    private int quarto;
    private String bairro;
    private float val;
    
    //Classe que main que contem o chamador (SwithCase), que tras acesso aos paradgnimas atraves da escolha do usuario.
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Try que testa o scanner para mostrar o swicht.
        try (Scanner in = new Scanner(System.in)) {
            // Variavel de controle do Swicht Case.
            int op = 0;
            //Inicio do laco, para que o programa inicie e nao pare apos selecionar uma opcao.
            do {
               //Testando o switch case.
                try {
                    System.out.println("\n\n");
                    System.out.println("Sistema geral - IMOBILIARIA");
                    System.out.println("---------------------------------------------");
                    System.out.println("|\t[1] - Inserir novo imovel           |");
                    System.out.println("|\t[2] - Lista de imoveis              |");
                    System.out.println("|\t[3] - Mostrar detalhes do imovel    |");
                    System.out.println("|\t[4] - Remover imovel                |");
                    System.out.println("|\t[5] - Importar imoveis para .DAT    |");
                    System.out.println("|\t[6] - Exportar imoveis para .CSV    |");
                    System.out.println("|\t[0] - Sair                          |");
                    System.out.println("---------------------------------------------");
                    System.out.print("Selecione a opcao desejada: \n");
                    System.out.println("---------------------------------------------");
                    //Variavel que armazena a opcao do usuario ao escolher a funcao do sistema.
                    op = in.nextInt();
                    //InicioSwichtCase.
                    switch (op) {
                        case 1:
                            //Chamador do metodo.
                            adicionarImoveis();
                            break;
                        case 2:
                            //Chamador do metodo.
                            listarImoveis();
                            break;
                        case 3:
                            System.out.print("\nMostrar detalhes\nReferecia: ");
                            //Chamador do metodo strings.
                            mostrarDetalhes(in.nextInt());
                            break;
                        case 4:
                            System.out.print("\nExcluir Imoveis\nReferencia: ");
                            //Chamador do metodo strings.
                            excluirImoveis(in.nextInt());
                            break;
                        case 5:
                            //Chamador do metodo.
                            importarImoveis();
                            break;
                        case 6:
                            //Chamador do metodo.
                            exportarImoveis();
                            break;

                        case 0:
                            break;
                        //Caso a entrada do usuario seja invalida "> 6". 
                        default:
                            System.out.println("Opcao invalida!");
                    }
                  //Esta realizando o tratamento da excecao.  
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
              //Laço repete enquanto a opcao do usuario for diferente de "0". 
            } while (op != 0);
        }
    }
    
    //Metodo excluirImovei declarado como inteiro.
    private static void excluirImoveis(int numero) {
        // Var achei inicializada e mantendo a variavel falsa.
        boolean achei = false;
        // Var cria e le o objeto.
        ObjectInputStream input = null;
        ObjectOutputStream output = null;
        //Testando 
        try {
            //Paths.get seleciona o endereco e testa, se existir Files.copy vai realizar a copia dos arquivos.
            Files.copy(Paths.get("imoveis.dat"), Paths.get("imoveis.bak"), StandardCopyOption.REPLACE_EXISTING);
            //Lendo 
            input = new ObjectInputStream(Files.newInputStream(Paths.get("imoveis.bak")));
            //Escrevendo
            output = new ObjectOutputStream(Files.newOutputStream(Paths.get("imoveis.dat")));
            //Laço inciado para a exclusão do arquivo, enquanto verdadeiro...
            while (true) {
                //lendo o objeto imobi constante.
                Imobi c = (Imobi) input.readObject();
                //Se Referencia for diferente de numero...
                if (c.ref != numero) {
                   //Escreve na variavel c.
                    output.writeObject(c);
                  // Retorna a variavel boleana achei em true.
                } else {
                    achei = true;
                }
            }
          //Tratamento de excecoes.
        } catch (EOFException e) {
            // se achei for verdadeira.
            if (achei) {
                System.out.println("Imovel excluido com sucesso! ");
              // se nao...
            } else {
                System.out.println("Erro: imovel nao encontrado! ");
            }
            
          //Caso de um erro ao fazer cópia.
        } catch (IOException e) {
            System.out.println("Erro ao fazer a copia do arquivo! ");
            e.printStackTrace();
            
          //Caso de um erro ao ler o arquivo.
        } catch (ClassNotFoundException e) {
            System.out.println("Erro de leitura/escrita! ");
          
          //Caso nenhuma das opcoes sejam realizada o finally encerrara o programa.
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                System.out.println("Erro ao fechar os arquivos! ");
            }
        }

    }

    //Metodo que armazena a funcao para mostrar os detalhes.
    private static void mostrarDetalhes(int numero) {
        //Declarado objeto como vazio.
        ObjectInputStream input = null;
        //testando imoveis.dat.
        try {
            //Verificando se o arquivo.dat se encontra no caminho.
            input = new ObjectInputStream(Files.newInputStream(Paths.get("imoveis.dat")));
            //laco para ler o objeto.
            while (true) {
                //Lendo o input e armazenando na variavel c.
                Imobi c = (Imobi) input.readObject();
                //Se ler todo. ele da a saida.
                if (c.ref == numero) {
                    System.out.println("TIPO: " + c.tipo + "\tQUARTOS: " + c.quarto + "\tBAIRRO: " + c.bairro + "\tVALOR: R$" + c.val);
                    return;
                }
            }
            //Trata o erro caso ache um erro no endereco.
        } catch (EOFException e) {
            System.out.println("Erro: imovel nao encontrado! ");
            //Caso o objeto for invalido, extensao diferente.
        } catch (ClassNotFoundException e) {
            System.out.println("Tipo de objeto invalido! ");
            //Erro caso nao consigo ler o objeto.
        } catch (IOException e) {
            System.out.println("Erro de leitura/escrita! ");
            //caso aconteca um erro nao tratado, encerra o programa.
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo! ");
                }
            }
        }
    }

    //Metodo que armazena a funcao para listar os imoveis.
    private static void listarImoveis() {
        //Declarando como vazio
        ObjectInputStream input = null;
        //testando...
        try {
            //Verificando se o arquivo.dat se encontra no caminho.
            input = new ObjectInputStream(Files.newInputStream(Paths.get("imoveis.dat")));
            //Enquanto verdadeiro
            while (true) {
                //Lendo o input e armazenando na variavel c.
                Imobi c = (Imobi) input.readObject();
                System.out.println("REFERENCIA\t" + c.ref + "\tVALOR\t" + c.val);
            }
          //Caso forem listados com sucesso.  
        } catch (EOFException e) {
            System.out.println("---------------------------------------------");
            System.out.println("Registros listados \n");
          
          //Caso objeto nao for compativel.
        } catch (ClassNotFoundException e) {
            System.out.println("Tipo de objeto invalido! ");
          //Caso tenho algum erro de leitura ou escrita.
        } catch (IOException e) {
            System.out.println("Erro de leitura/escrita");
            //Fecha o programa, caso apareca um erro diferente.
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Erro ao fechar o arquivo! ");
                }
            }
        }
    }
    
    //Metodo que armazena o adcionarImoveis.
    private static void adicionarImoveis() {
        //Declarando o Imobi=c, pega as informações do objeto e passa para o c.
        Imobi c = new Imobi();
        //Testa a escrita de registros
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("Referencia: ");
            //Armazena no ref na varivel c
            c.ref = in.nextInt();

            System.out.print("Tipo: ");
            in.nextLine();
            //Armazena no tipo na varivel c
            c.tipo = in.nextLine();

            System.out.print("quartos: ");
            //Armazena no quarto na varivel c
            c.quarto = in.nextInt();

            System.out.print("Bairro: ");
            in.nextLine();
            //Armazena no bairro na varivel c
            c.bairro = in.nextLine();

            System.out.print("Saldo: R$ ");
            //Armazena no val na varivel c
            c.val = in.nextFloat();
            
            //Pega o caminho do imoveis.dat em objeto
            Path path = Paths.get("imoveis.dat");
            //Se o caminho existir
            if (Files.exists(path)) {
                //OutputStream é declarado como true para anexar dados ao arquivo
                FileOutputStream fos = new FileOutputStream("imoveis.dat", true);
                //Seleciona a variavel FOS, e pega os dados do arquivo e armazena na variavel output.
                AppendingObjectOutputStream output = new AppendingObjectOutputStream(fos);
                //escreve o output para escrever na variavel C
                output.writeObject(c);
                output.close();
              //Se o arquivo nao existir 
            } else {
                //cria um arquivo usando o caminho selecionado
                try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(path))) {
                    //escreve na variavel c
                    output.writeObject(c);
                }
            }
            //caso de um erro na leitura ou escrita
        } catch (IOException e) {
            System.out.println("Erro de escrita/escrita");
        }
    }

    //criar metodo exportar
    private static void exportarImoveis() throws IOException, ClassNotFoundException {
        System.out.println("EXPORTAR ARQUIVOS BINARIOS PARA CSV");
        //Declara var myWriter como vazio
        FileWriter myWriter = null;
        //testando o objeto...
        try {
            //Objeto input vazio, para receber data
            ObjectInputStream input = null;
            //Recebe as informacoes do arquivo.dat na variavel input
            input = new ObjectInputStream(Files.newInputStream(Paths.get("imoveis.dat")));

            // myWrite = e o caminho do arquivo
            myWriter = new FileWriter("imoveis.csv");
            //Escreve o que esta dentro dos parenteses - cabecalho
            myWriter.write("REF;TIPO;QUARTOS;BAIRRO;VALOR\n");
            //laco para ler a data do imobi e passar para a var C, e escrever no arquivo.
            while (true) {
                Imobi c = (Imobi) input.readObject();
                
                myWriter.write(c.ref+ ";" + c.tipo+ ";" + c.quarto+";"  + c.bairro +";" +c.val+"\n" );

            }

            //Apos finalizar o escrita
        } catch (EOFException e) {
            System.out.println("---------------------------------------------");
            System.out.println("Registros listados \n");
            System.out.println("---------------------------------------------");
            myWriter.close();
        }
    }

    //Metodo para armazenar o metodo importarImoveis
    private static void importarImoveis() throws IOException {
        //Declarando o Imobi=c, pega as informações do objeto e passa para o c.
        Imobi c = new Imobi();
        //Declarando a String data como vazia.
        String data = null;

        //Testando o caminho do imoveis.csv.
        try {

            //Pegando Imoveis.Csv e transformando em objeto.
            File myObj = new File("imoveis.csv");
            //Scanner esta lendo o objeto e armazenando na string myreader.
            Scanner myReader = new Scanner(myObj);
            //Selecionando as strings my.Reader e armazenando na var data, enquanto estiver linhas.
            while (myReader.hasNextLine()) {
                //data lendo e passando para o data, tranformando em string
                data = myReader.nextLine();
               
               //Cria novo arquivo objeto 
               Path path = Paths.get("imoveis1.dat");
         //se existir o arquivo na variavel path
        if (Files.exists(path)) {
           //OutputStream é declarado como true para anexar dados ao arquivo
            FileOutputStream fos = new FileOutputStream("imoveis1.dat", true);
           //Seleciona a variavel FOS, e pega os dados do arquivo e armazena na variavel output.
            AppendingObjectOutputStream output = new AppendingObjectOutputStream(fos);
           //Pega a string data e converte em objeto Arquivo
            File archive = new File(data);
           //Esta escrevendo o objeto Arquivo
            output.writeObject(archive);
           //Apos escrever, encerra
            output.close();
            
          //Se der errado a primeira parte
        } else {
            //Cria novo imovel utilizando o path, e escreve a a string data
            try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(path))) {
                output.writeObject(data);
                output.close();
            }
        }
            
            }
        System.out.println("Importado com sucesso");
        } catch (IOException e) {

        }
     

    }
}
//Fim do codigo


