/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package azterketaariketa;

/**
 *
 * @author aimar
 */
public class Pedido {
   private String tipo;
   private Cliente cliente;
   private int numero;
   private Cocinero chef;
    
    public Pedido(String tipo, Cliente cliente, int numero) {
        this.tipo = tipo;
        this.cliente = cliente;
        this.numero=numero;
    }

    public int getNumero() {
        return numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public Cocinero getCocinero(){
        return chef;
    }
    
    public void setCocinero(Cocinero chef){
        this.chef = chef;
    }
}
