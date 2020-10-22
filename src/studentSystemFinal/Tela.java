package studentSystemFinal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * @author João Lopes Santana Neto
 * Email: joaoneto7499@gmail.com
 *
 */

public class Tela extends JFrame {
	
	private JTextField inputName;
	private JTextField inputSexo;
	private JTextField inputEndereco;
	private JTextField inputMatricula;
	
	private JButton btSalvar;
	private JButton btAlterar;
	private JButton btExcluir;
	private JButton btPesquisar;
	private JButton btLimpar;
	
	private JLabel jnome;
	private JLabel jsexo;
	private JLabel jendereco;
	private JLabel jmatricula;
	
	private JTable table;
	
	private JRadioButton rbMacho;
	private JRadioButton rbFemea;
	private ButtonGroup group;
	
	private ArrayList<Student> listTable = new ArrayList<>();
	private ArrayList<Student> listCopy = new ArrayList<>();
	
	private JScrollPane scrollPane;
	private MyModel myModel;
	
	private char sexo;
	
	private ArquivoUtil arquivo;

	
	public Tela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setBounds(10, 10, 600, 460);
		this.setTitle("Mantém Alunos");
		
		jnome = new JLabel("Nome:");
		inputName = new JTextField();
		
		jsexo = new JLabel("Sexo:");
		inputSexo = new JTextField();
		
		jendereco = new JLabel("Endereco:");
		inputEndereco = new JTextField();
		
		jmatricula = new JLabel("Matricula:");
		inputMatricula = new JTextField();
		
		btSalvar = new JButton("Salvar");
		btSalvar.addActionListener(listenerOfSalvar());
		
		btAlterar = new JButton("Alterar");
		btAlterar.addActionListener(listenerOfAlterar());
		
		btPesquisar = new JButton("Pesquisar");
		btPesquisar.addActionListener(listenerOfPesquisar());
		
		btExcluir = new JButton("Excluir");
		btExcluir.addActionListener(listenerOfExcluir());
		
		btLimpar = new JButton("Limpar");
		btLimpar.addActionListener(listenerOfLimpar());
		
		myModel = new MyModel(listTable);
		table = new JTable(myModel);
		
		table.addMouseListener(mouseListener());
		
		rbMacho = new JRadioButton();
		rbFemea = new JRadioButton();
		group = new ButtonGroup();	
		
		rbMacho.setText("Homem");
		rbFemea.setText("Mulher");
		
		//table.setBounds(100, 170, 445, 200);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(76, 170, 445, 200);
		
		arquivo = new ArquivoUtil("estudantes.txt");
		
		arquivo.montaObjeto(listTable);//Montando arquivo no array
		
		rbMacho.setBounds(400, 40, 80, 30);
		rbFemea.setBounds(490, 40, 80, 30);
		
		jnome.setBounds(76, 15, 50, 30);
		jsexo.setBounds(430, 15, 50, 30);
		jendereco.setBounds(76, 65, 100, 30);
		jmatricula.setBounds(430, 65, 100, 30);
		
		inputName.setBounds(76, 40, 300, 30);
		inputSexo.setBounds(430, 40, 90, 30);
		inputEndereco.setBounds(76, 90, 350, 30);
		inputMatricula.setBounds(430, 90, 90, 30);
		
		btSalvar.setBounds(76, 130, 100, 30);
		btPesquisar.setBounds(185, 130, 120, 30);
		btExcluir.setBounds(314, 130, 100, 30);
		btAlterar.setBounds(423, 130, 94, 30);
		btLimpar.setBounds(225, 375,100, 30);
		
		this.add(rbFemea);
		this.add(rbMacho);
		
		this.add(inputName);
		this.add(jnome);
		//this.add(inputSexo);
		//this.add(jsexo);
		
		this.add(inputEndereco);
		this.add(jendereco);
		
		this.add(inputMatricula);
		this.add(jmatricula);
		
		this.add(btSalvar);
		this.add(btAlterar);
		this.add(btPesquisar);
		this.add(btExcluir);
		this.add(btLimpar);
		
		this.add(scrollPane);
		
		group.add(rbMacho);
		group.add(rbFemea);

	}
		
	private MouseListener mouseListener() {
			MouseListener mouse = new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent evt) {
					int n = table.getSelectedRow();
					Student s = myModel.getRow(n);
					
					inputName.setText(s.getNome());
					
					if(s.getSexo() == 'M') {
						rbMacho.setSelected(true);;
					}else {
						rbFemea.setSelected(true);
					}
					
					inputMatricula.setText(String.valueOf(s.getMatricula()));
					inputEndereco.setText(s.getEndereco());
				
				}
				
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent arg0) {
					// TODO Auto-generated method stub
	
				}
			};
			return mouse;
		}

	private ActionListener listenerOfExcluir() {
		ActionListener myListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				arquivo.excluiDados(listTable, inputMatricula.getText().trim());
				int n = table.getSelectedRow();
				
				arquivo.montaObjeto(listTable);
				myModel.fireTableRowsDeleted(n, n);
				myModel.fireTableDataChanged();
				
				limpaCampos();
			}
		};
		return myListener;
	}

	private ActionListener listenerOfPesquisar() {
		ActionListener myListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String dados = arquivo.pesquisa(inputMatricula.getText().trim(), listTable);
				
				String [] recebeSplit = dados.split(";");
				inputName.setText(recebeSplit[1]);
				inputSexo.setText(recebeSplit[3]);
				inputMatricula.setText(recebeSplit[0]);
				inputEndereco.setText(recebeSplit[2]);
				
			}
		};
		return myListener;
	}

	private ActionListener listenerOfAlterar() {
		ActionListener myListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int n = table.getSelectedRow();
				sexo = radioSexo();
				Student s = new Student(inputName.getText(), sexo, Integer.parseInt(inputMatricula.getText()), inputEndereco.getText());

				listTable.add(n, s);
				listCopy.addAll(listTable);
				
				arquivo.alteraArquivo(listCopy, Integer.parseInt(inputMatricula.getText()), s);
				arquivo.montaObjeto(listTable);
				
				myModel.fireTableRowsUpdated(n, n);

			}
		};
		return myListener;
	}

	private ActionListener listenerOfSalvar() {
		ActionListener myListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sexo = radioSexo();
				if(inputName.getText().trim().equals("") || inputEndereco.getText().trim().equals("") ||inputMatricula .getText().trim().equals("") || sexo == 'n') {
					JOptionPane.showMessageDialog(Tela.this, "Todos os campos são obrigatórios", "Campo Obrigatório", JOptionPane.WARNING_MESSAGE);
				}else {
						
						boolean limpa;
						listTable.add(new Student(inputName.getText(), sexo, Integer.parseInt(inputMatricula.getText()), inputEndereco.getText()));								
						
						if(listTable.size() > 0) {	
							listCopy.add(listTable.get(listTable.size() -1));
						}
						
						limpa = arquivo.salvaDados(listCopy);
						arquivo.montaObjeto(listTable);
						myModel.fireTableDataChanged();
						
						if (limpa == true) {
							limpaCampos();
						}
						
					
				}
			}
			
		};
		return myListener;
	}
	
	private char radioSexo() {
		char sexo = ' ';
		if(rbMacho.isSelected()) {
			sexo = 'M';
		} else if(rbFemea.isSelected()) {
			sexo = 'F';
		} else {
			JOptionPane.showMessageDialog(Tela.this, "Escolha o sexo");
			return sexo = 'n';
		}
		return sexo;
	}
	
	private ActionListener listenerOfLimpar() {
		ActionListener listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				limpaCampos();
			}
		};
		return listener;
	}
	
	private void limpaCampos() {
		inputName.setText("");
		group.clearSelection();
		inputEndereco.setText("");
		inputMatricula.setText("");
	}

	
	  public static void main(String[] args) {
		  Tela tela = new Tela();
		  tela.setVisible(true);
	  }
	 
	
}
