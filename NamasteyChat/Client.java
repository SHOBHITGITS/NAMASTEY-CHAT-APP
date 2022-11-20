/***********************************************************/
/**********COPYRIGHT @SHOBHIT SRIVASTAVA********************/
/*********NAMASTEY CHAT APP - A SIMPLE FRIENDLY CHAT APP************/
/***********************************************************/
import java.net.*;

// import javax.swing.JLabel;
// import javax.swing.JTextArea;
// import javax.swing.JTextField;
// import javax.swing.JFrame;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Client extends JFrame
{

    Socket socket; //object of socket

    BufferedReader br;
    PrintWriter out;

    //Declare Components
    private JLabel heading =new JLabel("Namastey Client");
    private JTextArea messageArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);


    public Client() //constructor
    {
        try {
            System.out.println("Sending request to server");
            socket=new Socket("192.168.29.181",777);
            System.out.println("Connected Successfully!");



            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();
            startReading();
            // startWriting();

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    private void handleEvents() 
    {

        messageInput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                // System.out.println("Key released "+ e.getKeyCode());
                if(e.getKeyCode()==10)
                {
                    // System.out.println("You have pressed enter button!");
                    String contentToSend=messageInput.getText();
                    messageArea.append("Me :"+contentToSend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                }
                
            }
        
        });
    }


    private void createGUI() 
    {
        //GUI code
        this.setTitle("Namastey Client");
        
        this.setSize(400,600);
        this.setLocationRelativeTo(null); //sets location on centre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //coding for component
        heading.setFont(font);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
       // heading.setIcon(new ImageIcon("Logo.png"));
        messageArea.setFont(font);
        messageInput.setFont(font);

        //frame ka layout set karenge
        this.setLayout(new BorderLayout());
        
        //adding the components to frame
        this.add(heading,BorderLayout.NORTH);
        this.add(messageArea,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);


        this.setVisible(true);
    }


    //Start Reading
    public void startReading()
    {
      //  thread- data read karke deta rahega

      Runnable r1=()->{
        System.out.println("reader started...");

        try{

        while(true)  //infinite loop => kyoki baar-baar data read krna hai
        {    
            
            String msg=br.readLine();  //read poori single  line
            if(msg.equals("exit"))
            {
                System.out.println("Server terminated the chat");

                socket.close(); 
                break; 
            }
            //System.out.println("Server :" +msg);
            messageArea.append("Server :" +msg+"\n");
        }
    }
           catch(Exception e){
            e.printStackTrace();
           }
        
      };
        // start both thread
        new Thread(r1).start();

    }
    
    //start writing -> sending msg
    public void startWriting() 
    {
        //thread- data user se lega and fir send karega client tak
        Runnable r2=()->{
          System.out.println("Writer started...");

          try{

          while(true) 
          {
           
              BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));

              String content=br1.readLine();
              out.println(content);
              out.flush(); // zabarjasti data bhej dega server pe


          }
            } catch (Exception e) {
              // TODO: handle exception
              e.printStackTrace();
            }
          

        };
        // thread starts
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("This is client...");
        new Client(); //constructor called
    }
}
/***********************************************************/
/**********COPYRIGHT @SHOBHIT SRIVASTAVA********************/
/*********NAMASTEY CHAT APP - A SIMPLE FRIENDLY CHAT APP************/
/***********************************************************/
