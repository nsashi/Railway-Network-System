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
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class RandomGraph extends JFrame{
	
	JPanel p1 ;
	final static int N=23;
	final static int IN=9999999;
	static int currMatrix[][]=new int[N][N];//Doing all the manipulations in this matrix
	static int laty[],lonx[],SOURCE,DESTN;//for storing the latitude & longitude
	static int path[],count;//for storing the path
	static boolean hasPath,isBlocked,wasSearched;
	static ArrayList<Integer>blockSt1,blockSt2;//Keeping track of Blocked Route
	static String stName[];
	
	
	public RandomGraph(ArrayList<Integer> blockSt1,
			ArrayList<Integer> blockSt2, int[][] zoneMatrix, int[] path2, boolean hasPath2, int count) {
		// TODO Auto-generated constructor stub
		
		this.blockSt1=blockSt1;
		this.blockSt2=blockSt2;
		this.currMatrix=zoneMatrix;
		this.path=path2;
		this.hasPath=hasPath2;
		this.count=count;
		 p1 = new JPanel();
		
		p1.setBackground(Color.WHITE);
		
		//p1.add(Message);
		
		
		//g.drawOval(40, 50,5,5);
		add(p1);
		//System.out.print("width"+getMaximumSize().width+"\n"+"height"+getMaximumSize().height);
		setTitle("Random Graph");
		setSize(700,550);
		setResizable(false);
		setVisible(true);
	}

		//for plotting the Main junctions(Station)
		public void paint(Graphics g) {
			
			super.paint(g);
			
				// The name of the file to open.
				String fileName = "masterFile.txt";
		
			        // This will reference one line at a time
			        String line=null;

			        try {
			            // FileReader reads text files in the default encoding.
			            FileReader fileReader = 
			                new FileReader(fileName);

			            // Always wrap FileReader in BufferedReader.
			            BufferedReader bufferedReader = 
			                new BufferedReader(fileReader);
			            
			           
			            laty=new int[N];
			            lonx=new int[N];
			            stName=new String[N];
			            int i=0;
			           Random rn=new Random();
			            while((line = bufferedReader.readLine()) != null) {
			            	
			            	// \\s+ means any number of whitespaces between tokens
			                String [] tokens = line.split("\\s+");
			                float lat = Float.parseFloat(tokens[0]);
			                float lon =  Float.parseFloat(tokens[1]);
			                String station = tokens[2];
			                stName[i]=station;
			                laty[i]= rn.nextInt(500);
			                lonx[i] =rn.nextInt(500);
			                 //laty[i] = (int)(39-lat)*(500/27);//30instead of 13.71
			        		 //lonx[i] =(int) (lon-71)*(700/25);//35

			        		
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

			        ((Graphics2D) g).setStroke(new BasicStroke(2));//for thick line
			        if(hasPath)
			        {
			        	int i;
			        	//if path exists then draw Line
			        	
			        	for(i=0; i<count-1; i++)
			        	{
			        		 g.setColor(Color.CYAN);
			        		 g.fillOval(lonx[path[i]],laty[path[i]],10,10);
			        		 g.setColor(Color.GREEN);
			        	g.drawLine(lonx[path[i]],laty[path[i]], lonx[path[i+1]],laty[path[i+1]]);
			        	}
			        	g.setColor(Color.CYAN);
			        	 g.fillOval(lonx[path[i]],laty[path[i]],10,10);
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
