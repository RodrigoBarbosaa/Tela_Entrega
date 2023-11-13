package com.example.funcioalidadecep

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import java.text.DecimalFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var texto_cep: EditText
    lateinit var btn_cep: Button
    lateinit var textviewcep: TextView
    lateinit var textviewfrete: TextView
    lateinit var txtprecofrete: TextView
    lateinit var texttotalcompra: TextView
    lateinit var framelayoutpedido: FrameLayout
    lateinit var txtStrTroco: TextView
    lateinit var txtValorTroco: TextView
    lateinit var btnContinuar: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        texto_cep = findViewById(R.id.editTextTextPostalAddress)
        btn_cep = findViewById(R.id.btn_calcular_cep)
        textviewcep = findViewById(R.id.txt_resultadofrete)
        textviewfrete = findViewById(R.id.txt_str_frete)
        txtprecofrete = findViewById(R.id.txt_preco_frete)
        texttotalcompra = findViewById(R.id.text_total_compra)
        framelayoutpedido = findViewById(R.id.frameLayoutPedido)

        val buttonpedido: CheckBox = findViewById(R.id.checkBox)


        fun formatarDouble(decimal: Double): String {
            val formato = DecimalFormat("#.##")
            return formato.format(decimal)
        }

        fun definirTotal(valor: String) {
            texttotalcompra.text = valor
        }


        texto_cep.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 8) {
                    texto_cep.clearFocus()

                    // Fechar o teclado virtual
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(texto_cep.windowToken, 0)
                }
            }
        })


        var valorFrete = 0.0
        var recife = false

        btn_cep.setOnClickListener {

            val cep = texto_cep.text.toString()
            Log.d("MainActivity", "Texto digitado: $cep")

            //verificar se o código postal é de recife

            val numeroCep = cep.toInt()

            if (numeroCep in 50000000..52999999) {
                valorFrete = 5.48
                recife = true
            } else {
                valorFrete = 12.40
                recife = false
            }

            val freteFinal = "R$$valorFrete"

            textviewcep.text = freteFinal

            textviewfrete.visibility = View.VISIBLE

            txtprecofrete.text = freteFinal
            txtprecofrete.visibility = View.VISIBLE

            val totalCompraValor = (92.90 + valorFrete)
            val compraFormatada = formatarDouble(totalCompraValor)
            val totalCompra = "R$$compraFormatada"

            definirTotal(totalCompra)

            if (recife) {
                framelayoutpedido.visibility = View.VISIBLE
                buttonpedido.visibility = View.VISIBLE

            } else {
                framelayoutpedido.visibility = View.INVISIBLE
                buttonpedido.visibility = View.INVISIBLE
            }



            // Configurar um ouvinte para o CheckBox
            buttonpedido.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    txtStrTroco = findViewById(R.id.txt_str_troco)
                    txtValorTroco = findViewById(R.id.txt_valor_troco)

                    // criando o troco
                    val valorAntigo = compraFormatada.toDouble()
                    val valorTotal = ceil(valorAntigo).toInt()
                    val valorTroco = (valorTotal - valorAntigo)

                    val valorTrocoStr = formatarDouble(valorTroco)
                    val valorTrocoFinal = "R$$valorTrocoStr"

                    txtValorTroco.text = valorTrocoFinal

                    val totalCompraTroco = "R$$valorTotal"

                    definirTotal(totalCompraTroco)

                    txtStrTroco.visibility = View.VISIBLE
                    txtValorTroco.visibility = View.VISIBLE

                } else {

                    definirTotal(totalCompra)

                    txtStrTroco.visibility = View.INVISIBLE
                    txtValorTroco.visibility = View.INVISIBLE

                }
            }
        }
        btnContinuar = findViewById(R.id.button_continuar)
        btnContinuar.setOnClickListener {
            val activityFim = Intent(this, FinalizacaoActivity::class.java)
            startActivity(activityFim)
        }

    }
}