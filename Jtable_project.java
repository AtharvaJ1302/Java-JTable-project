package myPack;

import java.awt.Component;
import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Jtable_project extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField id;
	private JTextField fname;
	private JTextField lname;
	private JTextField contact;
	private JTextField email;
	private JTextField address;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Jtable_project frame = new Jtable_project();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	private JTable dispTable;

	public void clearFields() {
		id.setText(null);
		fname.setText(null);
		lname.setText(null);
		contact.setText(null);
		email.setText(null);
		address.setText(null);
		
	}
	
	public void refresh() {
		try {
			String sql = "select * from stud_details";
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root","Riojos1508@$");
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			
			dispTable.setModel(DbUtils.resultSetToTableModel(rs));// passing rs query to DefaultTableModel so that JTable gets refreshed in real time.
		}catch(Exception e) {
			
		}
	}
	
	
	/**
	 * Create the frame.
	 */
	public Jtable_project() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 977, 654);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					if(id.getText().isEmpty()||fname.getText().isEmpty()||lname.getText().isEmpty()||contact.getText().isEmpty()||email.getText().isEmpty()||address.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Filling all fields is mandatory");
						return;
					}
					
					String sql = "insert into stud_details(ID,first_name,last_name,contact_number,email_id,address) values(?,?,?,?,?,?)";
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root","Riojos1508@$");
					pst = conn.prepareStatement(sql);
					pst.setInt(1,Integer.parseInt(id.getText()));
					pst.setString(2,fname.getText());
					pst.setString(3,lname.getText());
					pst.setLong(4,Long.parseLong(contact.getText()));
					pst.setString(5,email.getText());
					pst.setString(6,address.getText());
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Inserted successfully!!");
					clearFields();
				}catch(Exception ex1) {
					JOptionPane.showMessageDialog(null, ex1);
				}
				refresh();
				
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnInsert.setBounds(30, 505, 147, 46);
		contentPane.add(btnInsert);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						
						String sql = "UPDATE stud_details SET first_name = COALESCE(?, first_name), " +
				                "last_name = COALESCE(?, last_name), " +
				                "contact_number = COALESCE(?, contact_number), " +
				                "email_id = COALESCE(?, email_id), " +
				                "address = COALESCE(?, address) " +
				                "WHERE ID = ?";

				        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "Riojos1508@$");
				        pst = conn.prepareStatement(sql);

				        if (id.getText().isEmpty()) {
				            JOptionPane.showMessageDialog(null, "ID is required for update.");
				            return;
				        } else {
				            pst.setInt(6, Integer.parseInt(id.getText()));
				        }

				        pst.setString(1, fname.getText().isEmpty() ? null : fname.getText());
				        pst.setString(2, lname.getText().isEmpty() ? null : lname.getText());
				        
				        if (!contact.getText().isEmpty()) {
				            pst.setLong(3, Long.parseLong(contact.getText()));
				        } else {
				            pst.setNull(3, java.sql.Types.BIGINT);
				        }
				        
				        pst.setString(4, email.getText().isEmpty() ? null : email.getText());
				        pst.setString(5, address.getText().isEmpty() ? null : address.getText());
				        
				        if (fname.getText().isEmpty() && lname.getText().isEmpty() && contact.getText().isEmpty()
				                && email.getText().isEmpty() && address.getText().isEmpty()) {
				            JOptionPane.showMessageDialog(null, "Please fill in at least one field (other than ID) for update.");
				            return;
				        }
				       
					
					
					pst.executeUpdate();
					
					
					
					JOptionPane.showMessageDialog(null, "Updated successfully!!");
					clearFields();
				}catch(Exception ex1) {
					JOptionPane.showMessageDialog(null, ex1);
				}
					refresh();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnUpdate.setBounds(202, 505, 147, 46);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
try {
					
					String sql = "delete from stud_details where ID = ?";
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root","Riojos1508@$");
					pst = conn.prepareStatement(sql);
					pst.setInt(1,Integer.parseInt(id.getText()));
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Deleted successfully!!");
					clearFields();
				}catch(Exception ex1) {
					JOptionPane.showMessageDialog(null, "Select a record to delete");
				}
				refresh();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDelete.setBounds(202, 561, 147, 46);
		contentPane.add(btnDelete);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClear.setBounds(30, 561, 147, 46);
		contentPane.add(btnClear);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(30, 17, 36, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblFirstName.setBounds(30, 94, 86, 33);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLastName.setBounds(30, 176, 86, 33);
		contentPane.add(lblLastName);
		
		JLabel lblContact = new JLabel("Contact");
		lblContact.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblContact.setBounds(30, 255, 86, 33);
		contentPane.add(lblContact);
		
		JLabel lblAddress = new JLabel("Email");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddress.setBounds(30, 333, 86, 33);
		contentPane.add(lblAddress);
		
		JLabel lblAddress_1 = new JLabel("Address");
		lblAddress_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblAddress_1.setBounds(30, 405, 86, 33);
		contentPane.add(lblAddress_1);
		
		id = new JTextField();
		id.setBounds(141, 10, 188, 51);
		contentPane.add(id);
		id.setColumns(10);
		
		fname = new JTextField();
		fname.setColumns(10);
		fname.setBounds(141, 87, 188, 51);
		contentPane.add(fname);
		
		lname = new JTextField();
		lname.setColumns(10);
		lname.setBounds(141, 169, 188, 51);
		contentPane.add(lname);
		
		contact = new JTextField();
		contact.setColumns(10);
		contact.setBounds(141, 248, 188, 51);
		contentPane.add(contact);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(141, 326, 188, 51);
		contentPane.add(email);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(141, 398, 188, 51);
		contentPane.add(address);
		
		JButton btnDisplayData = new JButton("Display Data");
		btnDisplayData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String sql = "select * from stud_details";
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root","Riojos1508@$");
					pst = conn.prepareStatement(sql);
					rs = pst.executeQuery();
					
					ResultSetMetaData rsmd = rs.getMetaData();
					DefaultTableModel model = (DefaultTableModel) dispTable.getModel(); //getting rows and columns from database
					
					model.setRowCount(0); //setting rows to 0
					
					int cols = rsmd.getColumnCount();   //storing the number of columns in an integer variable
					String colName[] = new String[cols];   //creating an array according to the size of columns i.e for 5 columns array will be from 0-4 index
					for(int i = 0;i<cols;i++) 
						colName[i]=rsmd.getColumnName(i+1); //fetching the names of column headings
					model.setColumnIdentifiers(colName);  //this method is used to display the column headings in the JTable.
					String ID,fname,lname,contact,email,address; //strings used to store the data that is in the specific column
					while(rs.next()) {
						ID = rs.getString(1);  //displaying ID of all in the column
						fname = rs.getString(2);
						lname = rs.getString(3);
						contact = rs.getString(4);
						email = rs.getString(5);
						address = rs.getString(6);
						String[] row = {ID,fname,lname,contact,email,address}; //once the data is stored in the variables, we store those variables in an array to display them row wise
						model.addRow(row);//displaying the data in row format using the method
						
					}
					
				}catch(Exception ex) {
					
				}
			}
		});
		btnDisplayData.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnDisplayData.setBounds(423, 505, 147, 46);
		contentPane.add(btnDisplayData);
		
		JButton btnClearTable = new JButton("Clear Table");
		btnClearTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispTable.setModel(new DefaultTableModel());
			}
		});
		btnClearTable.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnClearTable.setBounds(788, 505, 147, 46);
		contentPane.add(btnClearTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		scrollPane.setBounds(423, 17, 512, 433);
		contentPane.add(scrollPane);
		
		dispTable = new JTable();
		dispTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = dispTable.getSelectedRow();
				DefaultTableModel model = (DefaultTableModel) dispTable.getModel();
				
				
				id.setText(model.getValueAt(i, 0).toString());
				fname.setText(model.getValueAt(i, 1).toString());
				lname.setText(model.getValueAt(i, 2).toString());
				contact.setText(model.getValueAt(i, 3).toString());
				email.setText(model.getValueAt(i, 4).toString());
				address.setText(model.getValueAt(i, 5).toString());
			}
		});
		scrollPane.setViewportView(dispTable);
	}
}
