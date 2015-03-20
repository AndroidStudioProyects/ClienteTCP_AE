package com.example.laboratorio.tcp_cliente;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends ActionBarActivity {

    EditText Mensaje,edit_IP ,edit_Puerto;
    String IP;
    int Puerto=9001;
    Button Btn_Enviar;
    TextView textServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LevantoXML();

        Boton();



    }

    private void Boton() {

        Btn_Enviar.setOnClickListener(new View.OnClickListener() {

            String IP= edit_IP.getText().toString();
            String puerto=edit_Puerto.getText().toString();


            @Override
            public void onClick(View v) {
                String mensaje1=Mensaje.getText().toString();
                ClientAsyncTask clientAST;
                clientAST  = new ClientAsyncTask();
                clientAST.execute(new String[] { IP, puerto,mensaje1});

            }
        });
    }

    private void LevantoXML() {

        Btn_Enviar=(Button)findViewById(R.id.btn_Enviar);
        textServer=(TextView)findViewById(R.id.textServer);
        edit_Puerto=(EditText)findViewById(R.id.editPuerto);
        Mensaje=(EditText)findViewById(R.id.editMensaje);
    }


    class ClientAsyncTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... params) {
            String result = null;
            try {
                //Create a client socket and define internet address and the port of the server
                Socket socket = new Socket(params[0],Integer.parseInt(params[1]));
                //Get the input stream of the client socket
                InputStream is = socket.getInputStream();
                //Get the output stream of the client socket
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                //Write data to the output stream of the client socket
                out.println(params[2]);
                //Buffer the data coming from the input stream
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is));
                //Read data in the input buffer
                result = br.readLine();
                //Close the client socket
                socket.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            //Write server message to the text view
            textServer.append("\n"+s);
        }
    }
}
