package testeiniflex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

public class TesteIniflex {
    
    static ArrayList<Funcionario> funcionarios = new ArrayList<>();
    
    static void novoFuncionario(String nome, int ano, int mes, int dia, String salario, String funcao){
        funcionarios.add(new Funcionario(nome, LocalDate.of(ano,mes,dia), new BigDecimal(salario), funcao));
    }
    
    static void aumento(){
        funcionarios.stream().forEach(f -> f.setSalario(f.getSalario().add(f.getSalario().multiply(new BigDecimal("0.1")))));
    }
    
    static Stream<Funcionario> getListaByFuncao(String f){
        return funcionarios.stream().filter( func -> func.getFuncao().equals(f));
            
    }
    
    static int calculaIdade(Funcionario f){
        LocalDate dataAtual = LocalDate.now();
        int diaAgora = dataAtual.getDayOfMonth();
        int mesAgora = dataAtual.getMonth().getValue();
        int anoAgora = dataAtual.getYear();
        
        int idade = anoAgora - f.getDataNascimento().getYear();

        if(mesAgora < f.getDataNascimento().getMonth().getValue()) idade--;
        else{
            if(diaAgora < f.getDataNascimento().getDayOfMonth()) idade --;
        }
        
        return idade;
    }
    
    public static void main(String[] args) {
        
        // 3.1 – Inserir todos os funcionários, na mesma ordem e informações da tabela acima.
        
        novoFuncionario("Maria", 2000, 10, 18, "2009.44", "Operador");
        novoFuncionario("João", 1990, 5, 12, "2284.38", "Operador");
        novoFuncionario("Caio", 1961, 5, 2, "9836.14", "Coordenador");
        novoFuncionario("Miguel", 1988, 10, 14, "19119.88", "Diretor");
        novoFuncionario("Alice", 1995, 1, 05, "2234.68", "Recepcionista");
        novoFuncionario("Heitor", 1999, 11, 19, "1582.72", "Operador");
        novoFuncionario("Arthur", 1993, 3, 31, "4071.84", "Contador");
        novoFuncionario("Laura", 1994, 7, 8, "3017.45", "Gerente");
        novoFuncionario("Heloísa", 2003, 5, 24, "1606.85", "Eletricista");
        novoFuncionario("Helena", 1996, 9, 2, "2799.93", "Gerente");
        
        // 3.2 – Remover o funcionário “João” da lista.
        
        int count = 0;
        boolean flag = true;
            
        do{
            for(int i = 0 ; i < funcionarios.size() ; i++){
                if(funcionarios.get(i).getNome().equals("João")) {
                    funcionarios.remove(count);
                    flag = false;
                }
                
                count++;
            }
            
        }while(flag);       
        
        // Agrupando funcionários por função em map
        
        Map<String, Stream> map = new HashMap<>();
        
        // 3.3
        
        /* Comentei para o console não ficar sujo, pois são muitos requisitos.
        
        for(int i = 0 ; i < funcionarios.size() ; i++){
            System.out.println("\n");
            System.out.println(funcionarios.get(i).getNome());
            System.out.println(funcionarios.get(i).getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            System.out.println(NumberFormat.getInstance().format(funcionarios.get(i).getSalario()));
            System.out.println(funcionarios.get(i).getFuncao());
            System.out.println("\n");
        };

        */
        
        // 3.4 – Os funcionários receberam 10% de aumento de salário, atualizar a lista de funcionários com novo valor.
        
        aumento();
                
        // 3.5 – Agrupar os funcionários por função em um MAP, sendo a chave a “função” e o valor a “lista de funcionários”.
        
        map.put("Operador", getListaByFuncao("Operador"));
        map.put("Coordenador", getListaByFuncao("Coordenador"));
        map.put("Diretor", getListaByFuncao("Diretor"));
        map.put("Recepcionista", getListaByFuncao("Recepcionista"));
        map.put("Contador", getListaByFuncao("Contador"));
        map.put("Gerente", getListaByFuncao("Gerente"));
        map.put("Eletricista", getListaByFuncao("Eletricista"));
        
        // 3.6 – Imprimir os funcionários, agrupados por função.

        for(Map.Entry<String, Stream> entry : map.entrySet()){
            System.out.println("\n" + entry.getKey() + ":");
                entry.getValue().forEach(f -> {
                System.out.println("     " + ((Funcionario) f).getNome());
            });
        }
        
        // 3.8 – Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        
        System.out.println("--------------------------------------------------------------------");
        
        System.out.println("\nFuncionários que fazem aniversário no mês 10 e 12");
        
        funcionarios.stream().forEach(f -> {
            if(f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12){
                System.out.println(f.getNome());
            }
        });
        
        // 3.9 – Imprimir o funcionário com a maior idade, exibir os atributos: nome e idade.
        
        System.out.println("\n--------------------------------------------------------------------");
        
        System.out.println("\nImprimir o funcionário com a maior idade, exibir os atributos: nome e idade.");
        
        Funcionario maior = funcionarios.stream().max(Comparator.comparing(Funcionario::getSalario)).get();
        
        System.out.println("\nNome: " + maior.getNome());
        System.out.println("Idade: " + calculaIdade(maior));
        
        //3.10 – Imprimir a lista de funcionários por ordem alfabética.
        
        System.out.println("\n--------------------------------------------------------------------");
        
        SortedSet<String> nomes = new TreeSet<>();
        
        funcionarios.stream().forEach(f -> {
            nomes.add(f.getNome());
        });
        
        System.out.println("Funcionários em ordem alfabética:");
        
        nomes.stream().forEach(n -> System.out.println(n));
        
        System.out.println("\n--------------------------------------------------------------------");
        
        // 3.11 – Imprimir o total dos salários dos funcionários.
        
        BigDecimal total = BigDecimal.ZERO;
        
        for(int i = 0 ; i < funcionarios.size() ; i++){
            total = total.add(funcionarios.get(i).getSalario());
        }
        
        System.out.println("Soma dos salários: " + total);
        
        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário, considerando que o salário mínimo é R$1212.00.
        
        System.out.println("\n--------------------------------------------------------------------");
        
        funcionarios.stream().forEach(f -> {
            System.out.print(f.getNome() + " ganha ");
            System.out.println(f.getSalario().divide(new BigDecimal("1212"), 2, RoundingMode.CEILING) + " salários mínimos");
        });
            
   
    }
    
}
