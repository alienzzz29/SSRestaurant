
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class Restaurant implements ActionListener{
    
   JTable orderTable = new JTable();
   OrderTableModel orderModel = new OrderTableModel();
   JTextField totalPriceField = new JTextField();
//    Runner
        public static void main(String[] args) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    new Restaurant();
                }
            };
            SwingUtilities.invokeLater(runnable);
//            init
           
            
        }
        public Restaurant(){
        
             init();
//            Scanner sc = new Scanner(System.in);
//            First Panel
            JPanel firstPanel = new JPanel();
            JTextField ufield = new JTextField(10);
            JTextField pfield = new JPasswordField(10);
            
            firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));
            firstPanel.add(new JLabel("Input Username:"));
            firstPanel.add(Box.createVerticalStrut(15)); // a spacer
            firstPanel.add(ufield);
            firstPanel.add(Box.createVerticalStrut(15)); // a spacer
            firstPanel.add(new JLabel("Input Password:"));
            firstPanel.add(Box.createVerticalStrut(15)); // a spacer
            firstPanel.add(pfield);
            firstPanel.add(Box.createVerticalStrut(15)); // a spacer
            int result = JOptionPane.showConfirmDialog(null, firstPanel, "Welcome to SSRestaurant", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//            int menu;
            if(result == JOptionPane.OK_OPTION){
                if((ufield.getText().equals("") && pfield.getText().equals("")) || ufield.getText().equals("") || pfield.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Username or Password Incorrect", null, JOptionPane.PLAIN_MESSAGE);
                    return;
                }else{
                    if(userNameExists(ufield.getText(), pfield.getText()) == true){
                             if (loginUser(ufield.getText(),pfield.getText()) == 1) {
                        //                Execute Owner Authorization
                                        JPanel ownerPanel = new JPanel();
                                        JTextField inputField = new JTextField();
                                        ownerPanel.setLayout(new BoxLayout(ownerPanel, BoxLayout.Y_AXIS));
                                        ownerPanel.add(new JLabel("1.)Users Options"));
                                        ownerPanel.add(Box.createVerticalStrut(15)); // a spacer
                                        ownerPanel.add(new JLabel("2.)Menu Options"));
                                        ownerPanel.add(Box.createVerticalStrut(15)); // a spacer
                                        ownerPanel.add(inputField);
                                        int ownerResult = JOptionPane.showConfirmDialog(null, ownerPanel, "Owner Menu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                                        if (ownerResult == JOptionPane.OK_OPTION) {
                                            switch(Integer.parseInt(inputField.getText())){
                                                case 1:
                            //                        show Users table
                                                   usersTable();
                                                    break;
                                                case 2:
                            //                        shows Menu table
                                                    menuTable();
                                                    break;
                                                default:

                                                    break;
                                            }
                                        }
                                       
                                        return;
                                  }
                                    if (loginUser(ufield.getText(),pfield.getText()) == 2){
                        //                Execute Cashier Authorization
                                            int cid = getID(ufield.getText(),pfield.getText());
                                            System.out.println("User ID: "+cid);
                                            JTextField cashierField = new JTextField();

                                            JFrame frame = new JFrame("SSRestaurant Menu");
                                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        //                    Preferred Size of the frame
                                            frame.setPreferredSize(new Dimension(1000, 550));
                        //                    Borderlayout
                                            frame.setLayout(new BorderLayout());
                        //                    Resizable
                        //                    frame.setResizable(false);

                                            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                                            JPanel midPanel = new JPanel(new BorderLayout());
                                            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                                            Box box = Box.createVerticalBox();
                                            JPanel leftmidPanel = new JPanel();
                                            JPanel rightmidPanel = new JPanel(new BorderLayout());

                                            leftmidPanel.setPreferredSize(new Dimension(150, 100));
                                            leftmidPanel.setLayout(new BoxLayout(leftmidPanel, BoxLayout.Y_AXIS));

                                           
                                            
                                            List<Order> orderList = new ArrayList<>();
                                            orderModel.setList(orderList);
                                            orderTable.setModel(orderModel);
                                             orderTable.setAutoCreateRowSorter(true);
                                         ((DefaultRowSorter)orderTable.getRowSorter()).toggleSortOrder(0);
                                            rightmidPanel.add(new JScrollPane(orderTable));

                                            JLabel totalPriceLabel = new JLabel("Total Price:");
                                            JTextField customerField = new JTextField();
                                            JTextField amountField = new JTextField();
                                            totalPriceField.setEditable(false);
                                            JButton punchButton = new JButton("Punch");
                                            JButton removeButton = new JButton("Remove");
                                            totalPriceField.setPreferredSize(new Dimension(100, 20));
                                            customerField.setPreferredSize(new Dimension(150, 20));
                                            amountField.setPreferredSize(new Dimension(100, 20));
                                             
                                            removeButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    double pricePerQty = 0;
                                                    double sumTotal = 0;
                                                   orderModel.remove(orderTable.getSelectedRow());
                                                    for (int i = 0; i <= orderModel.getRowCount()-1; i++) {
                                                        double x = Double.parseDouble(orderModel.getValueAt(i, 2).toString());
                                                        double y = Double.parseDouble(orderModel.getValueAt(i, 3).toString());
                                                        pricePerQty = x * y;
                                                        sumTotal = sumTotal + pricePerQty;
                                                    }
                                                  totalPriceField.setText("Php "+String.valueOf(sumTotal));
                                                }
                                            });
                                            punchButton.addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {
                                                    if(amountField.getText().equals("")){
                                                        JOptionPane.showMessageDialog(null, "Please Input Amount First", null, JOptionPane.PLAIN_MESSAGE);
                                                        return;
                                                    }else{
                                                        double totalPrice = Double.parseDouble(totalPriceField.getText().substring(4));
                                                        double change = Double.parseDouble(amountField.getText()) - totalPrice;
                                                        if(Double.parseDouble(amountField.getText())<totalPrice){
                                                            JOptionPane.showMessageDialog(null, "Amount is lower than the total price", null, JOptionPane.PLAIN_MESSAGE);
                                                            return;
                                                        }else{
                                                            String result = "";
                                                            StringBuilder sb = new StringBuilder();
                                                             for (Order order: orderModel.getData()) {
                                                                 sb.append(order.getQuantity()+" "+order.getFoodname()+"  Php "+order.getPrice()+" \t\tPhp  "+(order.getQuantity()*order.getPrice())+"\n\n");
                                                             }
                                                             result = sb.toString();
                                                             JTextArea receiptTextArea = new JTextArea( "\t-- SS-RESTAURANT --\n\n" +
                                                             "CASHIER ID: " + cid + "\n\n" +
                                                             "CASHIER NAME: " + ufield.getText() + "\n\n" +
                                                             "CUSTOMER NAME: " + customerField.getText() + "\n\n" +
                                                             "-------------------------------------------------------------------------\n\n"+
                                                             result +
                                                             "-------------------------------------------------------------------------\n\n"+
                                                             "TOTAL: \t\t\tPhp " + totalPrice + "\n\n" +
                                                             "CASH: \t\t\tPhp " + Double.parseDouble(amountField.getText()) + "\n\n" +
                                                             "CHANGE: \t\t\tPhp " + change + "\n\n" +
                                                             "-- THANK YOU! --" 
                                                                    , 10, 10);
                                                             JPanel receiptPanel = new JPanel(new BorderLayout());

                                                             receiptPanel.setBorder(BorderFactory.createTitledBorder("Receipt"));
                                                             receiptTextArea.setEditable(false);
                                                             receiptPanel.setPreferredSize(new Dimension(350, 600));
                                                              receiptPanel.add(new JScrollPane(receiptTextArea));  
         //                                                     int ownerResult = 
                                                            JOptionPane.showConfirmDialog(null, receiptPanel, "Receipt", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                                                        }
                                                        
                                                    }
                                                }
                                            });
                                            bottomPanel.add(new JLabel("Customer Name:"));
                                            bottomPanel.add(customerField);
                                            bottomPanel.add(new JLabel("Input Amount:"));
                                             bottomPanel.add(amountField);
                                            bottomPanel.add(totalPriceLabel);
                                            bottomPanel.add(totalPriceField);
                                            bottomPanel.add(punchButton);
                                            bottomPanel.add(removeButton);
                                            try {
                                                 BufferedReader br = new BufferedReader(new FileReader("menu.txt"));
                                                String l;
                                                while((l = br.readLine()) != null){
                                                    String[] data = new String[0];
                                                    data = l.split(",");
                                                          JButton button = new JButton(data[1] +"  Php "+data[2]);
                                                          button.putClientProperty("id", data[0]);
                                                          button.putClientProperty("name", data[1]);
                                                          button.putClientProperty("price", data[2]);
                                                          button.addActionListener(this);
                                                          box.add(button);
                                              }
                                            } catch (Exception e) {
                                                System.out.println(e);
                                            }

                                            leftmidPanel.add(new JScrollPane(box));


                                            cashierField.setEditable(false);
                                            cashierField.setText(ufield.getText());
                                            topPanel.add(cashierField);

                                            topPanel.setBorder(BorderFactory.createTitledBorder("Cashier"));
                                            midPanel.setBorder(BorderFactory.createTitledBorder("Orders"));
                                            midPanel.add(leftmidPanel, BorderLayout.LINE_START);
                                            midPanel.add(rightmidPanel, BorderLayout.CENTER);
                                            bottomPanel.setBorder(BorderFactory.createTitledBorder(""));

                                            frame.add(topPanel, BorderLayout.PAGE_START);
                                            frame.add(midPanel, BorderLayout.CENTER);
                                            frame.add(bottomPanel, BorderLayout.PAGE_END);

                                            frame.pack();
                                            frame.setLocationRelativeTo(null);
                                            frame.setVisible(true);
                                    }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Username does not exist", null, JOptionPane.PLAIN_MESSAGE);
                        return;
                    }
                
                }
            }else{
            
                
            }
        }
        
         @Override
        public void actionPerformed(ActionEvent a) {
            double pricePerQty = 0;
            double sumTotal = 0;
           JButton button = (JButton) a.getSource();
           int id = Integer.parseInt(button.getClientProperty("id").toString());
           String name = (String) button.getClientProperty("name");
           double price = Double.parseDouble(button.getClientProperty("price").toString());
           int quantity = 1;
           Order order = new Order(id,name,price,quantity);
           orderModel.add(order);
             for (int i = 0; i <= orderModel.getRowCount()-1; i++) {
                 double x = Double.parseDouble(orderModel.getValueAt(i, 2).toString());
                 double y = Double.parseDouble(orderModel.getValueAt(i, 3).toString());
                 pricePerQty = x * y;
                 sumTotal += pricePerQty;
             }
           totalPriceField.setText("Php "+String.valueOf(sumTotal));
       }
        
        private boolean userNameExists(String u, String p){
            try {
                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
//                Get line && Compare Username
                String l,un,pw;
                while ((l = br.readLine()) !=null) {     
//                    {id,username,password,user_role}
                    un = l.split(",")[1];
                    pw = l.split(",")[2];
//                    Compare Username and Password
                    if (un.equals(u) && pw.equals(p)) {
                            return true;
                    }
                }
                return false;
            } catch (IOException e) {
                System.out.println("Connection Error: "+e.getMessage());
            }
            return false;
         }  
        
        private static int getID(String u, String p){
        int val = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
//                Get line && Compare Username and Password
                String l,un,pw;
//                 Get User Role
                int id;
                while ((l = br.readLine()) !=null) {     
//                    {id,username,password,user_role}
                    un = l.split(",")[1];
                    pw = l.split(",")[2];
                     id = Integer.parseInt(l.split(",")[0]);
                   
//                    Compare Username and Password
                    if (un.equals(u) && pw.equals(p)) {
//                        if (ur == 1) {
//                            val = 1;
//                        }
                            val = id;
//                        else{
//                            val = 2;
//                        }
                    }
                }
                
            } catch (IOException e) {
                System.out.println("Connection Error: "+e.getMessage());
            }
            return val;
        
        }
//        Login username & password Method
        private static int loginUser(String u, String p) {
            int val = 0;
            try {
                BufferedReader br = new BufferedReader(new FileReader("users.txt"));
//                Get line && Compare Username and Password
                String l,un,pw;
//                 Get User Role
                int ur;
                while ((l = br.readLine()) !=null) {     
//                    {id,username,password,user_role}
                    un = l.split(",")[1];
                    pw = l.split(",")[2];
                     ur = Integer.parseInt(l.split(",")[3]);
                   
//                    Compare Username and Password
                    if (un.equals(u) && pw.equals(p)) {
                        if (ur == 1) {
                            val = 1;
                        }
                        else{
                            val = 2;
                        }
                    }
                }
                
            } catch (IOException e) {
                System.out.println("Connection Error: "+e.getMessage());
            }
            return val;
        }
//        Connection
        private static void init(){
            System.out.println("Initiating...");
//            users database
            usersInit();
//            food database
            menuInit();
            System.out.println("Init Complete");
        }
        
        private static void usersInit(){
            try {
                File f = new File("users.txt");
                if (f.createNewFile()) {
                    System.out.println("Users Creation Success!");
                }
                else if(f.exists()){
                    System.out.println("User file exists!");
                }
                else
                {
                    System.out.println("Users Creation Failed!");
                }
                System.out.println("Users Initiated!");
            } 
            
            catch (IOException e) {
                System.out.println("Connection Error: "+e.getMessage());
            }
        }
        
         private static void menuInit(){
            try {
                File f = new File("menu.txt");
                if (f.createNewFile()) {
                    System.out.println("Menu Creation Success!");
                }
                else if(f.exists()){
                    System.out.println("Menu file exists!");
                }
                else
                {
                    System.out.println("Menu Creation Failed!");
                }
                System.out.println("Menu Initiated!");
            } 
            
            catch (IOException e) {
                System.out.println("Connection Error: "+e.getMessage());
            }
        }
         
          public static void usersTable() {
             try {
                 JPanel panel = new JPanel();
                 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                 JButton insert = new JButton("Insert");
                 JButton delete = new JButton("Delete");
                 JButton update = new JButton("Update");
//                  firstPanel.setLayout(new BoxLayout(firstPanel, BoxLayout.Y_AXIS));
                JFrame frame = new JFrame();
                frame.setLayout(new BorderLayout());
                JTable table = new JTable();
                 BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                 String l;
                 UserTableModel model = new UserTableModel();
                 List<User> userList = new ArrayList<User>();
                 while((l = br.readLine()) != null){
                     String[] data = new String[0];
                     data = l.split(",");
                         User user = new User();
                         user.setId(Integer.parseInt(data[0]));
                         user.setUsername(data[1]);
                         user.setPassword(data[2]);
                         user.setRole(Integer.parseInt(data[3]));
                         userList.add(user);
                 }
                 model.setList(userList);
                 table.setModel(model);
                  table.setAutoCreateRowSorter(true);
                ((DefaultRowSorter)table.getRowSorter()).toggleSortOrder(0);
                table.removeColumn(table.getColumnModel().getColumn(2));
//                 b1.setVerticalTextPosition(AbstractButton.BOTTOM);
//                 b1.setHorizontalTextPosition(AbstractButton.CENTER);
//                 b1.setMnemonic(KeyEvent.VK_S);
                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 frame.add(new JScrollPane(table));
                 frame.setTitle("Users Table");
                 frame.pack();
                 frame.setLocationRelativeTo(null);
                 frame.setVisible(true);
                 
                 
                 panel.add(insert);
                 panel.add(delete);
                 panel.add(update);
                 frame.add(panel, BorderLayout.EAST);
                 
                 delete.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Deleting User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                         if (result == JOptionPane.OK_OPTION ) {
//                             delete data
                            model.deleteRow(table.getSelectedRow());
                         }
                         
                     }
                 });
                 update.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                            JPanel updatePanel = new JPanel();
                            JTextField idField = new JTextField();
                            JTextField unameField = new JTextField();
                            JTextField pwordField = new JTextField();
                            JTextField roleField = new JTextField();
                            
                            updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
                            updatePanel.add(new JLabel("Input ID:"));
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(idField);
                            updatePanel.add(Box.createVerticalStrut(15));
                            updatePanel.add(new JLabel("Input Username:"));
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(unameField);
                            updatePanel.add(Box.createVerticalStrut(15));
                            updatePanel.add(new JLabel("Input Password:"));
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(pwordField);
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(new JLabel("Input Role:"));
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(roleField);
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            idField.setEditable(false);
                            idField.setText(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
                            unameField.setText(table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
                            pwordField.setText(table.getModel().getValueAt(table.getSelectedRow(), 2).toString());
                            roleField.setText(table.getModel().getValueAt(table.getSelectedRow(), 3).toString());
                            
                            int result = JOptionPane.showConfirmDialog(null, updatePanel, "Updating User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            
                            if(result == JOptionPane.OK_OPTION){
                                // insert user to database
                                     table.getModel().setValueAt(Integer.parseInt(idField.getText()), table.getSelectedRow(), 0);
                                    if (!unameField.getText().equals("")) {
                                        if(!model.userNameExists(unameField.getText())){
//                                            usr.setUsername(unameField.getText());
                                            table.getModel().setValueAt(unameField.getText(), table.getSelectedRow(), 1);
                                        }else{
                                               JOptionPane.showMessageDialog(null, "Username already taken", null, JOptionPane.PLAIN_MESSAGE);
                                               return;
                                        }
                                   }else{
                                         JOptionPane.showMessageDialog(null, "Username missing", null, JOptionPane.PLAIN_MESSAGE);
                                         return;
                                    }
                                    if(!pwordField.getText().equals("")){
//                                         usr.setPassword(pwordField.getText());
                                        table.getModel().setValueAt(pwordField.getText(), table.getSelectedRow(), 2);
                                          
                                    }else{
                                            JOptionPane.showMessageDialog(null, "Password missing", null, JOptionPane.PLAIN_MESSAGE);
                                            return;
                                    }
                                    if(!roleField.getText().equals("")){
//                                         usr.setRole(Integer.parseInt(roleField.getText()));
                                        table.getModel().setValueAt(Integer.parseInt(roleField.getText()), table.getSelectedRow(), 3);
                                          
                                    }else{
                                            JOptionPane.showMessageDialog(null, "Password missing", null, JOptionPane.PLAIN_MESSAGE);
                                            return;
                                    }
                                    model.updateRow();
                              }
                     }
                 });
                 insert.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         
                            JPanel insertPanel = new JPanel();
                            JTextField idField = new JTextField();
                            JTextField unameField = new JTextField();
                            JTextField pwordField = new JTextField();
                            JTextField roleField = new JTextField();
                            
                            insertPanel.setLayout(new BoxLayout(insertPanel, BoxLayout.Y_AXIS));
                            insertPanel.add(new JLabel("Input ID:"));
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(idField);
                            insertPanel.add(Box.createVerticalStrut(15));
                            insertPanel.add(new JLabel("Input Username:"));
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(unameField);
                            insertPanel.add(Box.createVerticalStrut(15));
                            insertPanel.add(new JLabel("Input Password:"));
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(pwordField);
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(new JLabel("Input Role:"));
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(roleField);
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            idField.setEditable(false);
                            String getLastID = table.getModel().getValueAt(table.getRowCount()-1, 0).toString();
                            int incrementID = Integer.valueOf(getLastID) + 1;
                            idField.setText(String.valueOf(incrementID));
                            int result = JOptionPane.showConfirmDialog(null, insertPanel, "Inserting new User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            
                            if(result == JOptionPane.OK_OPTION){
                                // insert user to database
                                    User usr = new User();
                                     usr.setId(Integer.parseInt(idField.getText()));
                                    if (!unameField.getText().equals("")) {
                                        if(!model.userNameExists(unameField.getText())){
                                            usr.setUsername(unameField.getText());
                                           
                                        }else{
                                               JOptionPane.showMessageDialog(null, "Username already taken", null, JOptionPane.PLAIN_MESSAGE);
                                               return;
                                        }
                                   }else{
                                         JOptionPane.showMessageDialog(null, "Username missing", null, JOptionPane.PLAIN_MESSAGE);
                                         return;
                                    }
                                    if(!pwordField.getText().equals("")){
                                         usr.setPassword(pwordField.getText());
                                          
                                    }else{
                                            JOptionPane.showMessageDialog(null, "Password missing", null, JOptionPane.PLAIN_MESSAGE);
                                            return;
                                    }
                                    if(!roleField.getText().equals("")){
                                         usr.setRole(Integer.parseInt(roleField.getText()));
                                          
                                    }else{
                                            JOptionPane.showMessageDialog(null, "Password missing", null, JOptionPane.PLAIN_MESSAGE);
                                            return;
                                    }
//                                    usr.setId(Integer.parseInt(idField.getText()));
//                                    usr.setUsername(unameField.getText());
//                                    usr.setPassword(pwordField.getText());
//                                    usr.setRole(Integer.parseInt(roleField.getText()));
                                    model.addRow(usr);
                              }
                     }
                 });
                  System.out.println("Users Table Loaded");
              
             } catch (Exception e) {
                  System.out.println("Connection Error: "+e.getMessage());
             }
            
        }
         
         public static void menuTable() {
             try {
                  JPanel panel = new JPanel();
                 panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                 JButton insert = new JButton("Insert");
                 JButton delete = new JButton("Delete");
                 JButton update = new JButton("Update");
                JFrame frame = new JFrame();
                frame.setLayout(new BorderLayout());
                JTable table = new JTable();
                 BufferedReader br = new BufferedReader(new FileReader("menu.txt"));
                 String l;
                 MenuTableModel model = new MenuTableModel();
                 List<Menu> menuList = new ArrayList<Menu>();
                 while((l = br.readLine()) != null){
                     String[] data = new String[0];
                     data = l.split(",");
                         Menu menu = new Menu();
                         menu.setId(Integer.parseInt(data[0]));
                         menu.setFoodname(data[1]);
                         menu.setPrice(Double.parseDouble(data[2]));
                         menuList.add(menu);
                 }
                 model.setList(menuList);
                 table.setModel(model);
                 table.setAutoCreateRowSorter(true);
                 ((DefaultRowSorter)table.getRowSorter()).toggleSortOrder(0);
//                 table.removeColumn(table.getColumnModel().getColumn(2));
                 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 frame.add(new JScrollPane(table));
                 frame.setTitle("Menu Table");
                 frame.pack();
                 frame.setLocationRelativeTo(null);
                 frame.setVisible(true);
                 
                 
                 panel.add(insert);
                 panel.add(delete);
                 panel.add(update);
                 frame.add(panel, BorderLayout.EAST);
                 
                 delete.addActionListener(new ActionListener() {
                      @Override
                      public void actionPerformed(ActionEvent e) {
                          int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Deleting User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                         if (result == JOptionPane.OK_OPTION ) {
//                             delete data
                            model.deleteRow(table.getSelectedRow());
                         }
                      }
                  });
                  insert.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                         
                            JPanel insertPanel = new JPanel();
                            JTextField idField = new JTextField();
                            JTextField fnameField = new JTextField();
                            JTextField priceField = new JTextField();
                            
                            insertPanel.setLayout(new BoxLayout(insertPanel, BoxLayout.Y_AXIS));
                            insertPanel.add(new JLabel("Input ID:"));
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(idField);
                            insertPanel.add(Box.createVerticalStrut(15));
                            insertPanel.add(new JLabel("Input Food Name:"));
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(fnameField);
                            insertPanel.add(Box.createVerticalStrut(15));
                            insertPanel.add(new JLabel("Input Price:"));
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            insertPanel.add(priceField);
                            insertPanel.add(Box.createVerticalStrut(15)); // a spacer
                            idField.setEditable(false);
                            String getLastID = table.getModel().getValueAt(table.getRowCount()-1, 0).toString();
                            int incrementID = Integer.valueOf(getLastID) + 1;
                            idField.setText(String.valueOf(incrementID));
                            int result = JOptionPane.showConfirmDialog(null, insertPanel, "Inserting new Food", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            
                            if(result == JOptionPane.OK_OPTION){
                                // insert user to database
                                    Menu menu = new Menu();
                                     menu.setId(Integer.parseInt(idField.getText()));
                                    if(!fnameField.getText().equals("")){
                                        menu.setFoodname(fnameField.getText());

                                    }else{
                                           JOptionPane.showMessageDialog(null, "Food Name missing", null, JOptionPane.PLAIN_MESSAGE);
                                           return;
                                    }
                                    if(!priceField.getText().equals("")){
                                         menu.setPrice(Double.parseDouble(priceField.getText()));
                                          
                                    }else{
                                            JOptionPane.showMessageDialog(null, "Price missing", null, JOptionPane.PLAIN_MESSAGE);
                                            return;
                                    }
                                    model.addRow(menu);
                              }
                     }
                 });
                  update.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                            JPanel updatePanel = new JPanel();
                            JTextField idField = new JTextField();
                            JTextField fnameField = new JTextField();
                            JTextField priceField = new JTextField();
                            
                            updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
                            updatePanel.add(new JLabel("Input ID:"));
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(idField);
                            updatePanel.add(Box.createVerticalStrut(15));
                            updatePanel.add(new JLabel("Input Food Name:"));
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(fnameField);
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(new JLabel("Input Price:"));
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            updatePanel.add(priceField);
                            updatePanel.add(Box.createVerticalStrut(15)); // a spacer
                            idField.setEditable(false);
                            idField.setText(table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
                            fnameField.setText(table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
                            priceField.setText(table.getModel().getValueAt(table.getSelectedRow(), 2).toString());
                            
                            int result = JOptionPane.showConfirmDialog(null, updatePanel, "Updating Menu", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                            
                            if(result == JOptionPane.OK_OPTION){
                                     table.getModel().setValueAt(Integer.parseInt(idField.getText()), table.getSelectedRow(), 0);
                                     if(!fnameField.getText().equals("")){
                                     
                                         table.getModel().setValueAt(fnameField.getText(), table.getSelectedRow(), 1);

                                    }else{
                                           JOptionPane.showMessageDialog(null, "Food Name missing", null, JOptionPane.PLAIN_MESSAGE);
                                           return;
                                    }
                                    if(!priceField.getText().equals("")){
                                          table.getModel().setValueAt(priceField.getText(), table.getSelectedRow(), 2);
                                          
                                    }else{
                                            JOptionPane.showMessageDialog(null, "Price missing", null, JOptionPane.PLAIN_MESSAGE);
                                            return;
                                    }
                                    model.updateRow();
                              }
                     }
                 });
                  System.out.println("Menu Table Loaded");
             } catch (Exception e) {
                  System.out.println("Connection Error: "+e.getMessage());
             }
             
        }
}       