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
