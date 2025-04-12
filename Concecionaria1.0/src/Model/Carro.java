/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.Serializable;
import java.util.Calendar;


public class Carro implements carrosMetodos, Serializable {
private String modelo, tracao;
private int numMotor, numChassi, quilometosPercoridos;
private boolean estadoVenda ;
 private String cor ;
 int Id;
  private String categoria,  tipoDeComustivel, fabricante;
  private int anoDeFabrico, idProduto;
  private double preco;

      public int getId() {
        return Id;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipoDeComustivel() {
        return tipoDeComustivel;
    }

    public void setTipoDeComustivel(String tipoDeComustivel) {
        this.tipoDeComustivel = tipoDeComustivel;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public int getAnoDeFabrico() {
        return anoDeFabrico;
    }

    public void setAnoDeFabrico(int anoDeFabrico) {
        this.anoDeFabrico = anoDeFabrico;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setId(int Id) {
        this.Id = Id;
    }


 

  
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTracao() {
        return tracao;
    }

    public void setTracao(String tracao) {
        this.tracao = tracao;
    }

    public int getNumMotor() {
        return numMotor;
    }

    public void setNumMotor(int numMotor) {
        this.numMotor = numMotor;
    }

    public int getNumChassi() {
        return numChassi;
    }

    public void setNumChassi(int numChassi) {
        this.numChassi = numChassi;
    }

    public int getQuilometosPercoridos() {
        return quilometosPercoridos;
    }

    public void setQuilometosPercoridos(int quilometosPercoridos) {
        this.quilometosPercoridos = quilometosPercoridos;
    }

    public boolean isEstado() {
        return estadoVenda;
    }

    public void setEstado(boolean estado) {
        this.estadoVenda = estado;
    }

    
  
    @Override
    public String estadoDoVeiculo(int quilometosPercoridos) {
    if(quilometosPercoridos>0)  { return "usado";}else
        return "novo";
    }
    
}
