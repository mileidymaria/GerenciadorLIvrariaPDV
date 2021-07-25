package Utilitarios;

import javax.swing.*; 


public class JOptionPaneEscolhaCliente{
	   
	JComboBox comboboxTipoCliente;
	JPanel myPanel;
	String auxiliar;
	
	public void escolherCliente() {
	      
	      
	      myPanel = new JPanel();
	      myPanel.add(new JLabel(" "));
	      myPanel.add(new JLabel("Tipo do cliente: "));
	      comboboxTipoCliente.setModel(new DefaultComboBoxModel(new String[] {"CLIENTE FÍSICO", "CLIENTE JURÍDICO"}));
	      myPanel.add(comboboxTipoCliente);
	      myPanel.add(Box.createHorizontalStrut(7)); // a spacer


	      int result = JOptionPane.showConfirmDialog(null, myPanel, 
	               "Entrada de valores", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  auxiliar = comboboxTipoCliente.getSelectedItem().toString();
	    	  System.out.print("\nOpção: " + auxiliar);
	      }

	   }
}
