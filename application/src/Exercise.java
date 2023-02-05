import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Exercise {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Product> list = new ArrayList<>();

        System.out.print("Enter full file path: ");
        String path = sc.next();

        try(BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line = br.readLine();
            while(line != null) {
                String[] fields = line.split(";");
                list.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }

            double avg = list.stream()
                    .map(Product::getPrice)
                    .reduce(0.0, (x, y) -> x + y / list.size());

            System.out.println("Average price: " + String.format("%.2f", avg));


            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> names = list.stream()
                    .filter(p -> p.getPrice() < avg)
                    .map(Product::getName)
                    .sorted(comp.reversed()).toList();

            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        sc.close();
    }

}
