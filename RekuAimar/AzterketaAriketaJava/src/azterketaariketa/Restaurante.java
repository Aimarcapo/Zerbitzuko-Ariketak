/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azterketaariketa;


import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author aimar
 */
public class Restaurante {

    private final Cocinero[] chefs;

    private static ArrayList<Pedido> pedidosSushi = new ArrayList<Pedido>();
    private static ArrayList<Pedido> pedidosPasta = new ArrayList<Pedido>();
    private static ArrayList<Pedido> pedidosMarmitako = new ArrayList<Pedido>();
    private static Integer contadorPedido = 0;

    public Restaurante(int numberOfChefs) {

        chefs = new Cocinero[numberOfChefs];

        //for (int i = 0; i < numberOfChefs; i++) {
        // chefSemaphores[i] = new Semaphore(0, true); // Fairness enabled
        // }
        // Creating chefs
        String[] chefTypes = {"sushi", "pasta", "marmitako"};
        for (int i = 0; i < numberOfChefs; i++) {
            Cocinero chef = new Cocinero(chefTypes[i % 3], i + 1);
            chef.setName("Chef " + (i + 1) + " - " + chefTypes[i % 3]);
            this.assignChef(i, chef);
            chef.start();
        }

    }

    public static Pedido makeOrder(String tipo, Cliente cliente) throws InterruptedException {

        synchronized (contadorPedido) {
            contadorPedido++;
            Pedido pedido = new Pedido(tipo, cliente, contadorPedido);
            System.out.println(new Date().toString() + " MAKEORDER(" + pedido.getNumero() + "): Cliente" + pedido.getCliente().getIdu() + " nuevo pedido(" + pedido.getNumero() + ")de " + pedido.getTipo());
            if (pedido.getTipo().equals("sushi")) {
                synchronized (pedidosSushi) {
                    pedidosSushi.add(pedido);
                }
            } else if (pedido.getTipo().equals("pasta")) {
                synchronized (pedidosPasta) {
                    pedidosPasta.add(pedido);
                }
            } else {
                synchronized (pedidosMarmitako) {
                    pedidosMarmitako.add(pedido);
                }
            }
            return pedido;
        }

        // makeOrderSemaphore.acquire();
        // makeOrderSemaphore.release();
    }

    public static Pedido nextOrder(String tipo, Cocinero chef) throws InterruptedException {
        //nextOrderSemaphore.acquire();
        Pedido p = null;
        if (tipo.equals("sushi")) {
            synchronized (pedidosSushi) {
                if (!pedidosSushi.isEmpty()) {
                    p = pedidosSushi.remove(0);
                    p.setCocinero(chef);
                    System.out.println(new Date().toString() + " NEXTORDER(" + p.getNumero() + "): Cliente" + p.getCliente().getIdu() + " va a ser atendido " + p.getTipo() + " por el Cocinero " + chef.getIdu());
                    return p;
                }

            }
        } else if (tipo.equals("pasta")) {
            synchronized (pedidosPasta) {
                if (!pedidosPasta.isEmpty()) {
                    p = pedidosPasta.remove(0);
                    p.setCocinero(chef);
                    System.out.println(new Date().toString() + " NEXTORDER(" + p.getNumero() + "): Cliente" + p.getCliente().getIdu() + " va a ser atendido " + p.getTipo() + " por el Cocinero " + chef.getIdu());
                    return p;
                }

            }
        } else {
            synchronized (pedidosMarmitako) {
                if (!pedidosMarmitako.isEmpty()) {
                    p = pedidosMarmitako.remove(0);
                    p.setCocinero(chef);
                    System.out.println(new Date().toString() + " NEXTORDER(" + p.getNumero() + "): Cliente" + p.getCliente().getIdu() + " va a ser atendido " + p.getTipo() + " por el Cocinero " + chef.getIdu());
                    return p;
                }

            }
        }

        // nextOrderSemaphore.release();
        return null;
    }

    public static void finPedido(Pedido pedido) {
        synchronized (pedido) {
            pedido.notify();
            System.out.println(new Date().toString() + " FINORDER(" + pedido.getNumero() + "): Cocinero" + pedido.getCocinero().getIdu() + " de tipo " + pedido.getTipo() + " ha terminado con cliente" + pedido.getCliente().getIdu() + " de tipo " + pedido.getTipo());

        }

    }

    public void assignChef(int index, Cocinero chef) {
        chefs[index] = chef;
    }
}
