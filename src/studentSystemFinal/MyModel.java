package studentSystemFinal;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel {

	private ArrayList<Student> list;
	private String[] header = {"Nome", "Sexo", "Endereço", "Matricula"};
	
	public MyModel(ArrayList<Student> list) {
		this.list = list;
	}


	@Override
	public String getColumnName(int col) {
		// TODO Auto-generated method stub
		return header[col];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Student student = list.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return student.getNome();
			case 1:
				return student.getSexo();
			case 2:
				return student.getEndereco();
			case 3:
				return student.getMatricula();
	
			default:
				return null;
		}
	}
	
	public Student getRow(int rowIndex) {
		return list.get(rowIndex);
	}
	
	public void add(Student s) {
		this.list.add(s);
		fireTableDataChanged();
	}
	
	public void remove(int index) {
		this.list.remove(index);
		fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}
}
