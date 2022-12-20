package passagemDeObjetosViaSocket.threads;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Elvir Moraes
 * 
 * Implementação Genérica de envio de Abstração de Objetos de um 
 * dispositivo terminal para dispositivos de armazenamento.
 * 
 */
public class Cliente implements ActionListener {
	
	private static Socket             socket;

	
	@SuppressWarnings("unused")
	private static String nome  = "";
	
	@SuppressWarnings("unused")
	private static int    idade = 0;
	
	private static Pessoa  pessoa = new Pessoa( );
	private static Cliente cliente = new Cliente( );
	
	public Cliente(  ) {
		
	}
	
	public Cliente( String nome, int idade  ) {
		Cliente.nome  = nome;
		Cliente.idade = idade;
	}
	
	public static void main( String[ ] args ) {
		//JFrame.setDefaultLookAndFeelDecorated(true);
		frCadastrarPessoa( );
	}


	
	/**
	 * 
	 */
	public boolean validaTextFields() {
		boolean preenchido = false;
		
		System.out.println( "Capturados da tela txtNome.getText( )" + txtNome.getText( ) );
		System.out.println( "Capturados da tela txtIdade.getText( )" + txtIdade.getText( ) );
		
		if( txtNome .getText().isEmpty() || 
			txtIdade.getText().isEmpty() ){
			JOptionPane.showMessageDialog( null, "Preencha o formulário", "Atenção", JOptionPane.OK_OPTION);
		}
		else {
			preenchido = true;
			
			try {
				
				pessoa.setNome(txtNome .getText( ));
				pessoa.setIdade(Integer.parseInt( txtIdade.getText( ) ));
				
				
				
			}catch (NumberFormatException nfe) {
				// TODO: handle exception
				
				JOptionPane.showMessageDialog(null, "Preencha idade corretamente" );
				
			}
		}
		
		
		return preenchido;
	}
	
	static int largura = 400, altura = 360; 
	static FlowLayout flowLayoutPadrao  = new FlowLayout( FlowLayout.LEFT, 20, 11 );
	//static GridLayout gridLayoutPadrao = new GridLayout(  );
	
	private static JFrame frameCadPessoa = new JFrame( "Cadastro de Pessoas" );
	private static JButton btCadPessoa   = new JButton( "Enviar" );
	
	private static JLabel     lblNome   = new JLabel( "Nome" );
    private static JTextField txtNome  = new JTextField( 30 );	
    private static JLabel     lblIdade    = new JLabel( "Idade" );
    private static JTextField txtIdade   = new JTextField( 30 );	
    private static JLabel     lblRetorno       = new JLabel( "Retorno do Servidor" );
	private static JTextArea  txaRetornoServer = new JTextArea( 7, 30  );
    
	public static void frCadastrarPessoa() {
		// TODO Implementar Cadastro com Frames.
		
		frameCadPessoa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameCadPessoa.setSize       ( largura, altura     );
        frameCadPessoa.setLocation( 200, 200 );
        frameCadPessoa.setLayout( flowLayoutPadrao );

        
        frameCadPessoa.add( lblNome  );
        frameCadPessoa.add( txtNome  );
        
        frameCadPessoa.add( lblIdade );
        frameCadPessoa.add( txtIdade );
        
        frameCadPessoa.add( lblRetorno       );
        frameCadPessoa.add( txaRetornoServer );
        
        // Botões
        btCadPessoa   .setText( "Cadastrar" );
        //frameCadPessoa.add    ( btCadPessoa );
        frameCadPessoa.add( btCadPessoa );
        
        // Escutar os Botões
        btCadPessoa  .addActionListener( cliente );
		
        flowLayoutPadrao.setAlignOnBaseline(false);
		frameCadPessoa.setVisible( true );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Implementar botões
		
		if( e.getSource( ).equals( btCadPessoa )  ) {
			try {
				validaTextFields() ;
				enviarCadPessoa();
				/*
				String novaFrase = entradaTexto.readUTF( ).toString( );

	            System.out.println(novaFrase);
				
	            txaRetornoServer.setText( novaFrase );
				*/
			}catch( Exception ee ) {
				
			}
		}
		
	}

	private static DataInputStream entradaTexto;
	private static ObjectOutputStream saidaObjeto;
    
	private void enviarCadPessoa() {
		// TODO Auto-generated method stub
		
        try {
			
        	System.out.println( "Enviando..." );
        	
			socket                 = new Socket( "127.0.0.1", 50000 );
			System.out.println("socket criado no cliente");
			
			saidaObjeto      = new ObjectOutputStream( socket.getOutputStream( ) );
			System.out.println( "Saída criada no cliente "  );
			
			saidaObjeto.writeObject( pessoa );
			System.out.println("Enviou cliente ");

			
			
			entradaTexto   = new DataInputStream( socket.getInputStream( ) );
			
           String novaFrase = entradaTexto.readUTF( );

           System.out.println(novaFrase);
			
			
           //txaRetornoServer.setText(  "Apareça no Frame"  );
           txaRetornoServer.setText(  novaFrase  );
			

			socket.close();
			
		} catch( IOException ee ) {
			// TODO Só dispare a exceção.
			System.out.println( "Não enviou de cliente" );
		}

	}

	

}
