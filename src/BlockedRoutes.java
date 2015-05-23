import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class BlockedRoutes extends JFrame {

	/**
	 * @param args
	 */

	static JPanel p1;
	static JButton BackButton;
	static JLabel Message,distance;
	final static int N=23;
	final static int IN=9999999;
	static HashMap<String,Integer> stationIndex;//for indexing station->number
	static int laty[],lonx[],SOURCE,DESTN;//for storing the latitude & longitude
	static int path[],count;//for storing the path
	static boolean hasPath,isBlocked,wasSearched;
	static int currMatrix[][]=new int[N][N];//Doing all the manipulations in this matrix
	static ArrayList<Integer>blockSt1,blockSt2;//Keeping track of Blocked Route
	static String stName[];
	
	
	 public BlockedRoutes(ArrayList<Integer> blockSt12, ArrayList<Integer> blockSt22, int[][] zoneMatrix) {
		// TODO Auto-generated constructor stub
		this.blockSt1=blockSt12;
		this.blockSt2=blockSt22;
		this.currMatrix=zoneMatrix;
		p1=new JPanel();
		
		
		BackButton=new JButton("Back to Home");
		
		//Message=new JLabel("All Blocked Routes");
		
		
		p1.setBackground(Color.WHITE);
		
		//p1.add(Message);
		//p1.add(BackButton);
		
		//g.drawOval(40, 50,5,5);
		add(p1);
		//System.out.print("width"+getMaximumSize().width+"\n"+"height"+getMaximumSize().height);
		setTitle("Blocked Routes");
		setSize(700,550);
		setResizable(false);
		setVisible(true);
		
	}
	
	 
	//for plotting the Main junctions(Station)
		public void paint(Graphics g) {
	
			super.paint(g);
			//Graphics2D g2 = (Graphics2D) g;
			//FileRead fObj=new FileRead(g);
		  // g.drawOval(20, 50, 10, 10);
		    //g.fillRect(100, 100,10,10);
				// The name of the file to open.
				String fileName = "masterFile.txt";
			
			        			//kolkata             delhi           365km             
			       // HashMap<ArrayList<Integer>,HashMap<ArrayList<Integer>,ArrayList<Integer>>> list;
			        // This will reference one line at a time
			        String line=null;

			        try {
			            // FileReader reads text files in the default encoding.
			            FileReader fileReader = 
			                new FileReader(fileName);

			            // Always wrap FileReader in BufferedReader.
			            BufferedReader bufferedReader = 
			                new BufferedReader(fileReader);
			            stationIndex=new HashMap<>();
			           
			            laty=new int[N];
			            lonx=new int[N];
			            stName=new String[N];
			            int i=0;
			           
			            while((line = bufferedReader.readLine()) != null) {
			            	
			            	// \\s+ means any number of whitespaces between tokens
			                String [] tokens = line.split("\\s+");
			                float lat = Float.parseFloat(tokens[0]);
			                float lon =  Float.parseFloat(tokens[1]);
			                String station = tokens[2];
			                stName[i]=station;
			                stationIndex.put(station,i);
			                 laty[i] = (int)(36-lat)*(500/27);//30instead of 13.71
			        		 lonx[i] =(int) (lon-71)*(700/25);//35

			        		
			                g.fillOval(lonx[i],laty[i],10,10);
			               // g.setColor(Color.GRAY);
			                g.drawString(station, lonx[i], laty[i]);
			                
			                i++;
			               // System.out.println(line);
			                //System.out.println(var_1+" "+var_2+" "+var_3);
			            }    
			            
			            // Always close files.
			            bufferedReader.close();            
			        }
			        catch(FileNotFoundException ex) {
			            System.out.println(
			                "Unable to open file '" + 
			                fileName + "'");                
			        }
			        catch(IOException ex) {
			            System.out.println(
			                "Error reading file '" 
			                + fileName + "'");                   
			            // Or we could just do this: 
			            // ex.printStackTrace();
			        }
			
			        
			      /*
			       * Draw black line as default connection
			       */
			        g.setColor(Color.BLACK);
			        
			        for(int i=0; i<N; i++)
			        	for(int j=0; j<N; j++)
			        	{
			        		
			        		if(currMatrix[i][j]!=0 && currMatrix[i][j]!=IN)
			        		g.drawLine(lonx[i],laty[i], lonx[j],laty[j]);
			        	}

			        
			       ((Graphics2D) g).setStroke(new BasicStroke(2));
			       if(blockSt1.size()>0)//if there is any blocked path then show it
			       {
			    	   for(int i=0; i<blockSt1.size(); i++)
			        	{
			    		   g.setColor(Color.RED);
			    		   
			    		   g.drawLine(lonx[blockSt1.get(i)],laty[blockSt1.get(i)], lonx[blockSt2.get(i)],laty[blockSt2.get(i)]);
			        	}
			       }
			        
			    
			    
		}

	
	
	

}
