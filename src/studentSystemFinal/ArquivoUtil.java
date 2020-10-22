package studentSystemFinal;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ArquivoUtil {
	
	private String arquivo;
	
	public ArquivoUtil(String arquivo) {
		this.arquivo = arquivo;
	}
	public String mostraDadosArquivo() {
		
		try {
			FileInputStream file = new FileInputStream(this.arquivo);
			InputStreamReader ist = new InputStreamReader(file);
			BufferedReader br = new BufferedReader(ist);
			
			String linha = br.readLine();
			String conteudo = "";
			
			while(linha != null) {
				conteudo = conteudo + linha + System.lineSeparator();
				linha = br.readLine();
			}
			
			br.close();
			return conteudo;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	public void escreveDadosArquivo(Student s) {
		
		try {
			FileWriter fw = new FileWriter(this.arquivo, true);
			fw.append(s.getNome() + ";" + s.getSexo() + ";" + s.getMatricula() + ";" + s.getEndereco() +";" + System.lineSeparator());
			
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void limpaArquivo() {
		
		try {
			FileWriter fw = new FileWriter(this.arquivo, false);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String pesquisa (String palavra, ArrayList<Student> list2) {
		
		char sexo = palavra.charAt(0);
		
		for (Student s : list2) {
			
			if(s.getNome().equals(palavra) ||
				Integer.parseInt(palavra) == s.getMatricula() ||
				s.getEndereco().equals(palavra) ||
				s.getSexo() == sexo) {
				System.out.println("Aqui está: \n"+ s.getMatricula() + " " + s.getNome()  + " " + s.getEndereco() + " " + s.getSexo());
				String dados =  s.getMatricula() + ";" + s.getNome()  + ";" + s.getEndereco() + ";" + s.getSexo();
				return dados;
			}
		}
		return null;
	}
	
	public boolean salvaDados( ArrayList<Student> list) {
		if (confereMatricula(list) == false) {
			return false;
		}else {
			for (Student s : list) {
				escreveDadosArquivo(s);	
			}
			list.clear();
			
		}
		return true;
	}
	
	private boolean confereMatricula(ArrayList<Student> list) {
		
		boolean v = true;
		ArrayList<Student> list2 = new ArrayList<>();
		montaObjeto(list2);
				
		Student student;
		student = list.get(list.size() - 1) ;
		
		for (Student s : list2) {
			if (student.getMatricula() == s.getMatricula()) {
				JOptionPane.showMessageDialog(null, "Essa matrícula já existe, digite uma nova por favor.");
				v = false;
				break;
				
			}else {
				v = true;
			}
		}
		return v;
	}
	
	public void excluiDados(ArrayList<Student> list2, String palavra) {
		
		montaObjeto(list2);
		boolean  checa = false;
		
		for (Student s1 : list2) {
			System.out.println(s1.getNome());
			if(s1.getMatricula()== Integer.parseInt(palavra)) {
				list2.remove(s1);
				checa = true;
				break;
			}
		}
		
		if (checa == true) {
			limpaArquivo();
			for (Student s : list2) {
				escreveDadosArquivo(s);	
			}
		}
	}
	
	public void alteraArquivo(ArrayList<Student> list2, int number, Student student) {
		
;		montaObjeto(list2);

		for (Student s : list2) {
			if(number == s.getMatricula()){
				
				s.setNome(student.getNome());
				s.setMatricula(student.getMatricula());
				s.setEndereco(student.getEndereco());
				s.setSexo(student.getSexo());
				
				System.out.println("Novo: "+ s.getNome() + s.getMatricula() + s.getEndereco());
				
				if (student.getNome().equals(s.getNome()) && student.getMatricula() == s.getMatricula() && student.getEndereco().equals(s.getEndereco()) && student.getSexo() == s.getSexo()) {
					limpaArquivo();
					
//					for (Student s1 : list2) {
//						escreveDadosArquivo(s1);	
//					}
//					list2.clear();
					salvaDados(list2);
					
				}
			}
			
			if(list2.isEmpty()) {
				break;
			}
		}
	}

	public void montaObjeto(ArrayList<Student> list2) {
		
		list2.clear();
		//remontando os dados do txt em um array de Student  
		//buscar pessoas e montar uma tabela
		ArrayList<String> arrayTemporario= new ArrayList<String>();//array de string para receber as linhas do txt
		try {
			FileInputStream file = new FileInputStream(this.arquivo);
			InputStreamReader ist = new InputStreamReader(file);
			BufferedReader br = new BufferedReader(ist);
			
			String linha = br.readLine();
			
			while(linha != null) {
				
				if(linha != null && !linha.isEmpty()) {
					arrayTemporario.add(linha);//adicionando a linha do txt no array
				}
				linha = br.readLine();
			}
			
			br.close();
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String s : arrayTemporario) {
			
			String [] recebeSplit = s.split(";");
			char sexo = recebeSplit[1].charAt(0);
			
			Student studentTemporario = new Student(recebeSplit[0], sexo, Integer.parseInt(recebeSplit[2]), recebeSplit[3]);
			list2.add(studentTemporario);
		}
		
	}
}
