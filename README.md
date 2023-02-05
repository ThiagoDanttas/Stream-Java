# Stream - Java

---

## Stream
�  � uma sequencia de elementos advinda de uma fonte de dados que oferece suporte a "opera��es agregadas". <br>
�   Fonte de dados: cole��o, array, fun��o de itera��o, recurso de E/S <br>
�  Sugest�o de leitura:
http://www.oracle.com/technetwork/pt/articles/java/streams-api-java-8-3410098-ptb.html

## Caracter�sticas
�  Stream � uma solu��o para processar sequ�ncias de dados de forma: <br>
�   Declarativa (itera��o interna: escondida do programador) <br>
�   Parallel-friendly (imut�vel -> thread safe) <br>
�   Sem efeitos colaterais <br>
�   Sob demanda (lazy evaluation) <br>
�  Acesso sequencial (n�o h� �ndices) <br>
�  Single-use: s� pode ser "usada" uma vez <br>
�  Pipeline: opera��es em streams retornam novas streams. Ent�o � poss�vel criar uma cadeia de opera��es (fluxo de processamento).

## Opera��es intermedi�rias e terminais
�  O pipeline � composto por zero ou mais opera��es intermedi�rias e uma terminal. <br>

### Opera��o intermedi�ria: <br>
�   Produz uma nova streams (encadeamento) <br>
�   S� executa quando uma opera��o terminal � invocada (lazy evaluation) <br>

###  Opera��o terminal:
�   Produz um objeto n�o-stream (cole��o ou outro)<br>
�   Determina o fim do processamento da stream<br>

## Opera��es intermedi�rias
�  filter <br>
�  map <br>
�  flatmap <br>
�  peek <br>
�  distinct <br>
�  sorted <br>
�  skip <br>
�  limit (*) <br>
* short-circuit

## Opera��es terminais
  � forEach <br>
  � forEachOrdered <br>
  �    toArray <br>
  �     reduce <br>
  �     collect <br>
  �     min <br>
  �     max<br>
  �     count<br>
  �     anyMatch (*)<br>
  �     allMatch (*)<br>
  �     noneMatch (*)<br>
  �     findFirst (*)<br>
  �     findAny (*)<br>
* short-circuit

## Criar uma stream
  �       Basta chamar o m�todo stream()ou parallelStream()a partir de qualquer objeto Collection.
  https://docs.oracle.com/javase/10/docs/api/java/util/Collection.html <br>
  ### Outras formas de se criar uma stream incluem: 
  �       Stream.of <br>
  �       Stream.ofNullable <br>
  �       Stream.iterate
  
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

## Exerc�cio de Fixa��o 

Fazer um programa para ler os dados (nome, email e sal�rio) de funcion�rios a partir de um arquivo em formato .csv.
Em seguida mostrar, em ordem alfab�tica, o email dos funcion�rios cujo sal�rio seja superior a um dado valor fornecido pelo usu�rio.
Mostrar tamb�m a soma dos sal�rios dos funcion�rios cujo nome come�a com a letra 'M'.
Veja exemplo na pr�xima p�gina.

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

            // Filtrando e ordenando funcion�rios com sal�rio maior que x valor
            List<String> EmployeeEmail = list.stream()
                    .filter(e -> e.getSalary() > salary)
                    .map(Employees::getEmail)
                    .sorted()
                    .toList();

            System.out.printf("Email of people whose salary is more than %.2f:\n", salary);
            EmployeeEmail.forEach(System.out::println);

            // Retornando soma de salarios de funcion�rios que o nome come�a com M
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

- Podemos usar Streams para executar v�rias tarefas que antes precisavam de muito c�digo e que poderiam ser escritas de maneiras distintas. Com Streams essas tarefas ficam mais simples, estruturadas e padronizadas.