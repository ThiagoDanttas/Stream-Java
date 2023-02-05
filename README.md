# Stream - Java

---

## Stream
•  É uma sequencia de elementos advinda de uma fonte de dados que oferece suporte a "operações agregadas". <br>
•   Fonte de dados: coleção, array, função de iteração, recurso de E/S <br>
•  Sugestão de leitura:
http://www.oracle.com/technetwork/pt/articles/java/streams-api-java-8-3410098-ptb.html

## Características
•  Stream é uma solução para processar sequências de dados de forma: <br>
•   Declarativa (iteração interna: escondida do programador) <br>
•   Parallel-friendly (imutável -> thread safe) <br>
•   Sem efeitos colaterais <br>
•   Sob demanda (lazy evaluation) <br>
•  Acesso sequencial (não há índices) <br>
•  Single-use: só pode ser "usada" uma vez <br>
•  Pipeline: operações em streams retornam novas streams. Então é possível criar uma cadeia de operações (fluxo de processamento).

## Operações intermediárias e terminais
•  O pipeline é composto por zero ou mais operações intermediárias e uma terminal. <br>

### Operação intermediária: <br>
•   Produz uma nova streams (encadeamento) <br>
•   Só executa quando uma operação terminal é invocada (lazy evaluation) <br>

###  Operação terminal:
•   Produz um objeto não-stream (coleção ou outro)<br>
•   Determina o fim do processamento da stream<br>

## Operações intermediárias
•  filter <br>
•  map <br>
•  flatmap <br>
•  peek <br>
•  distinct <br>
•  sorted <br>
•  skip <br>
•  limit (*) <br>
* short-circuit

## Operações terminais
  • forEach <br>
  • forEachOrdered <br>
  •    toArray <br>
  •     reduce <br>
  •     collect <br>
  •     min <br>
  •     max<br>
  •     count<br>
  •     anyMatch (*)<br>
  •     allMatch (*)<br>
  •     noneMatch (*)<br>
  •     findFirst (*)<br>
  •     findAny (*)<br>
* short-circuit

## Criar uma stream
  •       Basta chamar o método stream()ou parallelStream()a partir de qualquer objeto Collection.
  https://docs.oracle.com/javase/10/docs/api/java/util/Collection.html <br>
  ### Outras formas de se criar uma stream incluem: 
  •       Stream.of <br>
  •       Stream.ofNullable <br>
  •       Stream.iterate
  
~~~~java 
List<Integer> list = Arrays.asList(3, 4, 5, 10, 7); 
Stream<Integer> st1 = list.stream();
System.out.println(Arrays.toString(st1.toArray()));

Stream<String> st2 = Stream.of("Maria", "Alex", "Bob"); 
System.out.println(Arrays.toString(st2.toArray()));

Stream<Integer> st3 = Stream.iterate(0, x -> x + 2);
System.out.println(Arrays.toString(st3.limit(10).toArray()));

Stream<Long> st4 = Stream.iterate(new long[]{ 0L, 1L }, p->new long[]{ p[1], p[0]+p[1] }).map(p -> p[0]); 
System.out.println(Arrays.toString(st4.limit(10).toArray()));
~~~~
## Pipeline

---

~~~~java 
List<Integer> list = Arrays.asList(3, 4, 5, 10, 7); 

Stream<Integer> st1 = list.stream().map(x -> x * 10);
System.out.println(Arrays.toString(st1.toArray())); 

int sum = list.stream().reduce(0, (x, y) -> x + y);
System.out.println("Sum = " + sum); 

List<Integer> newList = list.stream()
.filter(x -> x % 2 == 0) 
.map(x -> x * 10)
.collect(Collectors.toList());
System.out.println(Arrays.toString(newList.toArray()));
~~~~

## Exercício de Fixação 

Fazer um programa para ler os dados (nome, email e salário) de funcionários a partir de um arquivo em formato .csv.
Em seguida mostrar, em ordem alfabética, o email dos funcionários cujo salário seja superior a um dado valor fornecido pelo usuário.
Mostrar também a soma dos salários dos funcionários cujo nome começa com a letra 'M'.
Veja exemplo na próxima página.

~~~~java

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ExercicioDeFixacao {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Employees> list = new ArrayList<>();

        System.out.print("Enter full file path: ");
        String path = sc.next();

        System.out.print("Enter salary: ");
        Double salary = sc.nextDouble();


        try(BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();
            while(line != null) {
                String[] fields = line.split(";");
                list.add(new Employees(fields[0], fields[1], Double.parseDouble(fields[2])));
                line = br.readLine();
            }

            // Filtrando e ordenando funcionários com salário maior que x valor
            List<String> EmployeeEmail = list.stream()
                    .filter(e -> e.getSalary() > salary)
                    .map(Employees::getEmail)
                    .sorted()
                    .toList();

            System.out.printf("Email of people whose salary is more than %.2f:\n", salary);
            EmployeeEmail.forEach(System.out::println);

            // Retornando soma de salarios de funcionários que o nome começa com M
            double EmployeeSum = list.stream()
                    .filter(name -> name.getName().startsWith("M"))
                    .map(Employees::getSalary)
                    .reduce(0.0, Double::sum);

            System.out.println("Sum of salary of people whose name starts with 'M': " + String.format("%.2f", EmployeeSum));


        }catch(IOException e) {
            System.out.println("Error: " + e.getMessage());
        }


        sc.close();
    }

}

~~~~


## Resumo

---

- Podemos usar Streams para executar várias tarefas que antes precisavam de muito código e que poderiam ser escritas de maneiras distintas. Com Streams essas tarefas ficam mais simples, estruturadas e padronizadas.