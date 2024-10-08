package com.example.calculadorahp12c;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn0;

    private Button btnEnter;
    private Button btnBackSpace;
    private Button btnAdicao;
    private Button btnSubtracao;
    private Button btnMultiplicacao;
    private Button btnDivisao;
    private  Button btnClear;

    private Button btnPV;
    private Button btnFV;
    private Button btnPMT;
    private Button btniTaxa;
    private Button btnnPeriodo;

    private EditText visor;

    private Button btnVirgula;
    private Calculadora calculadora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        calculadora = new ViewModelProvider(this).get(Calculadora.class);

        btn1 = findViewById(R.id.btnUm);
        btn2 = findViewById(R.id.btnDois);
        btn3 = findViewById(R.id.btnTres);
        btn4 = findViewById(R.id.btnQuatro);
        btn5 = findViewById(R.id.btnCInco);
        btn6 = findViewById(R.id.btnSeis);
        btn7 = findViewById(R.id.btnSete);
        btn8 = findViewById(R.id.btnOIto);
        btn9 = findViewById(R.id.btnNove);
        btn0 = findViewById(R.id.btnZero);

        btnEnter = findViewById(R.id.btnEnter);
        btnBackSpace = findViewById(R.id.btnBack);
        btnAdicao = findViewById(R.id.btnAdicao);
        btnSubtracao = findViewById(R.id.btnSubtracao);
        btnMultiplicacao = findViewById(R.id.btnMultiplicacao);
        btnDivisao = findViewById(R.id.btnDivisao);
        btnClear = findViewById(R.id.btnClear);

        btnPV = findViewById(R.id.btnPv);
        btnFV = findViewById(R.id.btnFV);
        btnPMT = findViewById(R.id.btnPmt);
        btniTaxa = findViewById(R.id.bntTaxa); // TERMINAR DE ARRUMAR AQUIII
        btnnPeriodo = findViewById(R.id.btnN);

        visor = findViewById(R.id.visor);
        visor.setShowSoftInputOnFocus(false); // nao deixa o teclado do proprio celular aparecer

        btnVirgula = findViewById(R.id.btnVirgula);

        btn1.setOnClickListener(botaoCLick("1"));
        btn2.setOnClickListener(botaoCLick("2"));
        btn3.setOnClickListener(botaoCLick("3"));
        btn4.setOnClickListener(botaoCLick("4"));
        btn5.setOnClickListener(botaoCLick("5"));
        btn6.setOnClickListener(botaoCLick("6"));
        btn7.setOnClickListener(botaoCLick("7"));
        btn8.setOnClickListener(botaoCLick("8"));
        btn9.setOnClickListener(botaoCLick("9"));
        btn0.setOnClickListener(botaoCLick("0"));

        btnEnter.setOnClickListener((v) -> {
            calculadora.enter();
        });

        btnBackSpace.setOnClickListener((v) -> {
            int inicioSelecao = visor.getSelectionStart() - 1;
            inicioSelecao = Math.max(inicioSelecao, 0);
            int finalSelecao = visor.getSelectionEnd();
            visor.getText().delete(inicioSelecao, finalSelecao);
            atualizarNumero();
        });

        btnAdicao.setOnClickListener((v) -> {
            calculadora.soma();
            atualizarVisor();

        });

        btnSubtracao.setOnClickListener((v) -> {
            calculadora.subtracao();
            atualizarVisor();
        });

        btnMultiplicacao.setOnClickListener((v) -> {
            calculadora.multiplicacao();
            atualizarVisor();
        });

        btnDivisao.setOnClickListener((v) -> {
            calculadora.divisao();
            atualizarVisor();
        });

        btnClear.setOnClickListener((v) -> {
            visor.setText("");
            calculadora = new Calculadora();
        });

        //não ta funcionando :(
        btnVirgula.setOnClickListener((v) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO){
                visor.setText("0,0");
                calculadora.setModo(Calculadora.MODO_EDITANDO);
            }
            int inicioSelecao = visor.getSelectionStart();
            int finalSelecao = visor.getSelectionEnd();
            if (calculadora.getModo() == Calculadora.MODO_ERROR) {
                visor.getText().replace(inicioSelecao, finalSelecao, "0,");
            } else {
                visor.getText().replace(inicioSelecao, finalSelecao, ",");
            }
        });

        //JUROS COMPOSTO
        btnPV.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = String.format("%.2f", calculadora.calculaPV());
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                    String visorText = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(visorText);
                    calculadora.setPv(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setPv(valor);
                }
                visor.setText("0,0");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btnFV.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = String.format("%.2f", calculadora.calculAFV());
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                    String visorText = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(visorText);
                    calculadora.setFv(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setFv(valor);
                }
                visor.setText("0,0");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btnPMT.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = String.format("%.2f", calculadora.calculaPMT());
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                    String visorText = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(visorText);
                    calculadora.setPmt(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setPmt(valor);
                }
                visor.setText("0,0");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btniTaxa.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = String.format("%.2f", calculadora.calculaTaxa());
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                    String visorText = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(visorText);
                    calculadora.setiTaxa(valor/100);
                } else {
                    double valor = 0.0;
                    calculadora.setiTaxa(valor/100);
                }
                visor.setText("0,0");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });

        btnnPeriodo.setOnClickListener((V) -> {
            if (calculadora.getModo() == Calculadora.MODO_EXIBINDO) {
                String valor = String.format("%.2f", calculadora.calcularPeriodo());
                visor.setText(valor);
            }
            else {
                if (!(calculadora.getModo() == Calculadora.MODO_ERROR)) {
                    String visorText = visor.getText().toString().replace(",", ".");
                    double valor = Double.valueOf(visorText);
                    calculadora.setnPeriodo(valor);
                } else {
                    double valor = 0.0;
                    calculadora.setnPeriodo(valor);
                }
                visor.setText("0,0");
            }
            calculadora.setModo(calculadora.MODO_EXIBINDO);
        });
    }
    //ACABA AQUI

    public View.OnClickListener botaoCLick(final String s) {
        return (v) -> {
            if(calculadora.getModo() == Calculadora.MODO_EXIBINDO){
                visor.setText("");
            }
            int  inicioSelecao = visor.getSelectionStart();
            int finalSelecao = visor.getSelectionEnd();
            visor.getText().replace(inicioSelecao, finalSelecao, s);
            atualizarNumero();
        };
    }

    public void atualizarNumero(){
        String s = visor.getText().toString();
        s = "".equals(s) ? "0" : s;
        calculadora.setNumero(Double.valueOf(s));
    }

    public void atualizarVisor(){
        double numero = calculadora.getNumero();
        visor.setText(String.format("%.2f", numero));
    }
}