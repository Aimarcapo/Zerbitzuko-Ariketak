/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package azterketaariketa;

import java.util.Scanner;

/**
 *
 * @author aimar
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         Scanner scanner = new Scanner(System.in);
        boolean validar = false;
        int numberOfCustomers;
        do {
            System.out.println("Enter the number of customers (must be a multiple of 3):");
            numberOfCustomers = scanner.nextInt();

            if (numberOfCustomers<9) {
                System.out.println("Number of customers must be bigger than 3 times the amount of chefs");

            } else {
                validar = true;
            }
        } while (!validar);

        int numberOfChefs =  3;

        Restaurante restaurant = new Restaurante(numberOfChefs);

       

        // Creating customers
        for (int i = 0; i < numberOfCustomers; i++) {
            Cliente cliente = new Cliente(restaurant,i+1);
            cliente.setName("Customer " + (i + 1));
            cliente.start();
        }
    }
    
}
