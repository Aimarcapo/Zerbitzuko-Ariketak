/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azterketaariketa;

import java.util.Date;

/**
 *
 * @author aimar
 */
public class Cocinero extends Thread {
    private final String type;
    private int id;

    public Cocinero(String type, int id) {
        this.type = type;
        this.id = id;
    }
    public String getType() {
        return this.type;
    }
    public int getIdu(){
        return id;
    }
 
    private void cocinar(Pedido pedido) throws InterruptedException{
        
            System.out.println(new Date().toString()+" COCINAR("+pedido.getNumero()+"): Cocinero"+this.getIdu() + " de tipo " + this.getType()+" comienza pedido de cliente"+pedido.getCliente().getIdu() +" de tipo "+pedido.getTipo()); 
            Thread.sleep(2000);
       
    }
    @Override
    public void run() {
        while (true) {
            try {
               Pedido pedido = Restaurante.nextOrder(type, this);
               
                if(pedido!=null){
                    cocinar(pedido);
                    Restaurante.finPedido(pedido);
                }
            
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}