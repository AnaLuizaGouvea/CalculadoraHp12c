package com.example.calculadorahp12c;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.function.BiFunction;

public class Calculadora {

    public static final int MODO_EDITANDO = 0;
    public static final int MODO_EXIBINDO = 1;
    public static final int MODO_ERROR = 2;
    private double numero;
    private Deque<Double> operandos;
    private int modo = MODO_EXIBINDO;

    public Calculadora() {
        numero = 0;
        operandos = new LinkedList<>();
    }

    public Deque<Double> getOperandos() {
        return operandos;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
        modo = MODO_EDITANDO;
    }

    public int getModo(){
        return modo;
    }

    public int setModo(int n){
        this.modo = n;
        return this.modo;
    }

    public void enter(){
        if(modo == MODO_ERROR){
            modo = MODO_EXIBINDO;
        }
        if (modo == MODO_EDITANDO){
            operandos.push(numero);
            modo = MODO_EXIBINDO;
        }
    }


    public void executarOperacao(BiFunction<Double, Double, Double> operacao){
        if(modo == MODO_EDITANDO || modo == MODO_ERROR){
            enter();
        }
        double op1 = Optional.ofNullable(operandos.pollFirst()).orElse(0.0);
        double op2 = Optional.ofNullable(operandos.pollFirst()).orElse(0.0);
        numero = operacao.apply(op1, op2);
        System.out.println(numero);
        operandos.push(numero);
    }

    public void soma(){
        executarOperacao((op1, op2) -> op1 + op2);
    }

    public void subtracao(){
        executarOperacao((op1, op2) -> op2 - op1);
    }

    public void multiplicacao(){
        executarOperacao((op1, op2) -> op1 * op2);
    }

    public void divisao(){
        double denominador = Optional.ofNullable(operandos.peek()).orElse(0.0);

        if(denominador == 0){
            modo = MODO_ERROR;
            return;
        }
        executarOperacao((op1, op2) -> op2 / op1);
    }

}
