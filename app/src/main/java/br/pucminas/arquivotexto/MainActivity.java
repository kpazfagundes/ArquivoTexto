package br.pucminas.arquivotexto;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    private EditText edtTexto;
    private static final String nomeArquivo = "BlocoDeNotas.txt";
    private static final String PREFERENCES = "NomeArquivo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtTexto = findViewById(R.id.edtTextoId);
        lerCor();
        lerArquivo();
    }

    private void lerCor() {
        SharedPreferences shared = getSharedPreferences(PREFERENCES, 0);
        edtTexto.setTextColor(shared.getInt("cor", Color.BLACK));
    }

    public void print(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    // ADICIONAR O MENU A NOSSA ACTIVITY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // DEFININDO A ACAO DO BOTAO NO MENU

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mnSalvarId:
                salvarArquivo();
                print("Texto Salvo com Sucesso!");
                break;
            case R.id.mnAzulId:
                defineCor(Color.BLUE);
                break;
            case R.id.mnVermelhoId:
                defineCor(Color.RED);
                break;
            case R.id.mnVerdeId:
                defineCor(Color.GREEN);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void defineCor(int cor) {

        edtTexto.setTextColor(cor);
        SharedPreferences shared = getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = shared.edit();

        // SALVA OS DADOS NO ARQUIVO
        editor.putInt("cor", cor);
        editor.commit();
    }


    //PROCEDIMENTO PARA SALVAR OS DADOS NO ARQUIVO
    public void salvarArquivo(){

        String texto = edtTexto.getText().toString();

        if(texto == null || texto.equals("")){
            print("Informe o texto!");
        }else{

            try{

                FileOutputStream fileOutputStream = openFileOutput(nomeArquivo, Context.MODE_PRIVATE);
                OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
                output.write(texto);
                output.close();

                Log.i("MainActivity", "Texto salvo!");

            }catch (IOException e){
                Log.e("MainActivity",e.toString());
            }
        }
    }

    // PROCEDIMENTO PARA LER OS DADOS DO ARQUIVO

    public void lerArquivo(){

        try{

            FileInputStream fileInputStream = openFileInput(nomeArquivo);
            InputStreamReader input = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(input);

            String line = "", texto = "";

            while( (line = bufferedReader.readLine()) != null ){
                texto += line;
            }

            edtTexto.setText(texto);

            Log.i("MainActivity", "Texto lido com sucesso!");


        }catch (IOException e){
            Log.e("MainActivity", e.toString());
        }
    }
}
