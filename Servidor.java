package passagemDeObjetosViaSocket.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor extends Thread {
	
	private Socket socket;
	@SuppressWarnings("unused")
	private ServerSocket server;
	private ObjectInputStream entradaObj;
	@SuppressWarnings("unused")
	private ObjectOutputStream saidaObj;
	
	private static DataOutputStream saidaTxt;
	
    public Servidor( Socket conexao ){
        this.socket = conexao;
    }	
	
    @Override
    public void run() {
        try{
        	
        	
        	System.out.println( "Tread 01" );
			
        	entradaObj = new ObjectInputStream( socket.getInputStream( ) );
        	System.out.println("Entrada criada no Servidor");
        	
        	
			Pessoa pessoa = ( Pessoa ) entradaObj.readObject( );
			System.out.println("Pessoa Recebida");
			
			
			System.out.println( "Nome: " + pessoa.getNome( ) + " Idade: " + pessoa.getIdade( ) );
			
			
			saidaTxt   = new DataOutputStream( socket.getOutputStream( ) );
			saidaTxt.writeUTF(  "Nome: " + pessoa.getNome( ) + " Idade: " + pessoa.getIdade( ) );
			
			
			socket.close( );
			
		}catch( Exception e ) {
			// Saída de exeção
			System.out.println( "Não chegou no saidaTxt" );
		}	
    }
    
    static int clientes = 0;
	public static void main( String[] args ) throws IOException {
		// TODO Implementar main
        try ( ServerSocket server = new ServerSocket( 50000 ) ) {
            while(  true  ){
                Socket   conexao = server.accept( );
                Servidor sTread  = new Servidor( conexao );
                
                System.out.println( "Cadastro de Clientes: " + ++clientes );
                sTread.start( );
            }
        }
	}
}