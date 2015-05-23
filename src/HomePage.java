import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComboBox.KeySelectionManager;
import javax.swing.text.ZoneView;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomePage extends JFrame{

	static JPanel p1;
	JButton searchButton,blockButton,clearBlockage,unblockButton,resetBut,showBlockdPath,randomButt;
	JTextField source,dest;
	static JLabel src,targ,distance,blocked;
	static JComboBox cbSRC;
	static JComboBox cbDEST;
	static Graphics g=null;
	final static int N=23;
	final static int IN=9999999;
	static HashMap<String,Integer> stationIndex;//for indexing station->number
	static int laty[],lonx[],SOURCE,DESTN;//for storing the latitude & longitude
	static int path[],count;//for storing the path
	static boolean hasPath,isBlocked,wasSearched;
	static int currMatrix[][]=new int[N][N];//Doing all the manipulations in this matrix
	
	static ArrayList<Integer>blockSt1,blockSt2;//Keeping track of Blocked Route
	static String stName[];
	//constructor
	public HomePage() {
		// TODO Auto-generated constructor stub
		String st[]={"hey","khan","hello"};
		p1=new JPanel();
		
		
		searchButton=new JButton("Search Route");
		/* try {
			    Image img = ImageIO.read(getClass().getResource("round.png"));
			    searchButton.setIcon(new ImageIcon(img));
			  } catch (IOException ex) {
			  }*/
		blockButton=new JButton("Block Route");
		unblockButton=new JButton("Unblock a Route");
		clearBlockage=new JButton("Clear All Blocked Route");
		resetBut=new JButton("Reset ");
		randomButt=new JButton("Show Random Map");
		showBlockdPath=new JButton("Show Blocked Route");
		src=new JLabel("Source:   ");
		targ=new JLabel("Destination:    ");
		source=new JTextField(15);
		dest=new JTextField(15);
		distance=new JLabel("                     ");
		//blocked=new JLabel("fctfuyv");
		//blocked.setBounds(56,62,55, 56);
		
		//Combobox
		cbSRC=new JComboBox(comboBoxStation());
		//cbSRC.setEditable(true);
		
		//cbSRC.setKeySelectionManager(aManager);
		cbSRC.setMaximumRowCount(5);
		
		cbDEST=new JComboBox(comboBoxStation());
		//cbDEST.setEditable(true);
		
		//cbSRC.setKeySelectionManager(aManager);
		cbDEST.setMaximumRowCount(5);
		p1.setBackground(Color.WHITE);
		p1.add(src);
		p1.add(cbSRC);
		p1.add(targ);
		p1.add(cbDEST);
		p1.add(searchButton);
		p1.add(distance);
		p1.add(blockButton);
		p1.add(clearBlockage);
		p1.add(unblockButton);
		p1.add(showBlockdPath);
		p1.add(resetBut);
		p1.add(randomButt);
		//p1.add(blocked);
		//g.drawOval(40, 50,5,5);
		add(p1);
		//System.out.print("width"+getMaximumSize().width+"\n"+"height"+getMaximumSize().height);
		setTitle("Railway Zonal Map Of India");
		setSize(700,555);
		setResizable(false);
		setVisible(true);
		
	}
	
	//for plotting the Main junctions(Station)
	public void paint(Graphics g) {
		this.g=g;
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
		                 laty[i] = (int)(39-lat)*(500/27);//30instead of 13.71
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
		       g.setColor(Color.BLACK);
		      ((Graphics2D) g).setStroke(new BasicStroke(3));
		       g.drawString("Blocked Routes",450,120);
		       g.setColor(Color.RED);
		       g.drawLine(550,115, 670,115);
		       
		       g.setColor(Color.BLACK);
		       g.drawString("Best Routes",450,140);
		       g.setColor(Color.GREEN);
		       g.drawLine(550,135, 670,135);
		       
		       g.setColor(Color.BLACK);
		       g.drawString("Broad Guage",450,160);
		       g.drawLine(550,155, 670,155);
		    
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int co;
		blockSt1=new ArrayList<>();
		blockSt2=new ArrayList<>();
	final HomePage gui=new HomePage();
	
	//
							 // 0 ,  1,  2,  3,  4,  5,    6,  7,  	8,  9,   10,  11, 12, 13,    14 , 15 , 16,  17 ,  18 , 19, 20 , 21 , 22
	final int zoneMatrix[][]={{0  , 0,  0,  0,  0,  307,   0,  0,   0,   0,   0 ,  0,  0,  0,   630 ,  0,    0 ,  0,  0,   0 ,  0 ,  0 , 0},//0->Jaipur
							  {0  , 0,  0,  0,  0,   0 ,    0, 1284, 0,  0,   0,   0, 820,  0 ,   0 , 263,  420 ,  0,  0,  0 ,  0 ,  0,  0},//1->Mumbai
							  {0  , 0,  0,440,  0,  0 ,     0,  0,   0,   0,  0,   0,  0,  0  ,   0 ,  0,   0 ,    0, 792,  0 ,  0 , 0,  0},//2->Bhubane
							  {0  , 0,440,  0,970,  0 ,     0,  0,   0,  551, 812, 720, 0, 1173 ,  0,  0,   0 ,   0,  0,   0 ,  0 ,  0,  0},//3->kolkata
							  {0  , 0,  0,970,  0,  0 ,   	0,  0,   0,   0,  0,    0,  0,  0 ,   0 ,  0,   0 ,   0,  0,   0 ,  0 ,  0,  0 },//4->guwahati
							  {307, 0,  0,  0,  0,  0 ,   	0,  0,   0,   0,  634,  0, 0,   0,   957, 1121, 0 ,   0,  0,   0 , 410 ,  0,  576},//5->delhi
							  {0  , 0,  0,  0,  0,  0 ,   	0,  0,   0, 332,  0 ,   0,  0,  0,    0 ,  0,   0 ,   0,  0,   0 ,  0 ,  0,   0},//6->gorakhpur
							  {0 ,1284,  0,  0,  0,  0 ,   	0,  0,   0,   0,  0,    0, 834,  0,   0 ,  0,   0 ,  742, 431,  0 ,  0 , 0 ,  0},//7->chennai
							  {0  , 0,  0,  0,  0,  0 ,   	0,  0,   0,   0,  0,    0,  0,  0  ,   0 ,  0,  0 ,   0,  303, 577,  0 , 0 ,  0},//8->secndera
							  {0  , 0,  0,551,  0,  0 ,   332,  0,   0,   0,  0,    0,  0,  0  ,   0 ,  0,  0 ,   0,   0,   0 ,  0,  512 , 0},//9->hajipur
							  {0  , 0,  0,812,  0,  634 ,   0,  0,   0,   0,  0,    0,  0, 360 ,   0 ,  0,  940 ,  0,  0,   0,  392 , 200, 0},//10->allahabad 
							  {0  , 0,  0,720,  0,  0 ,   	0,  0,   0,   0,  0,    0,  0,  0  ,   0 ,  0,  0 ,    0,  0,  410,  0 ,   0,  0},//11-> Bilaspur  
							  {0  ,820,  0,  0,  0,  0  ,   0,  834, 0,   0,  0 ,   0,  0,  0  ,   0 ,  0,  0 ,    0,  0,   0 ,  0 ,   0,  0},//12-> Hubli
							  {0  , 0,  0,1173,  0,  0  ,   0,  0,   0,   0, 360 ,  0,  0,  0  ,   0 ,  0, 580 ,   0,  0,   0 ,  0 ,    0, 0},//13-> jabalpur 
							  {630, 0,  0,  0,  0,  957,   0,   0,   0,   0,  0 ,   0,  0,  0  ,   0 , 229,  0 ,   0,  0,   0 ,  0 ,    0,  0},//14-> Ahmeadabad
							  {0  ,263,  0,  0,  0, 1121,   0,  0,   0,   0,  0 ,   0,  0,  0  ,  229,  0,  0 ,    0,  0,   0 ,  0 ,    0, 0},//15-> Surat
							  {0  ,420,  0,  0,  0,  0  ,   0,  0,   0,   0, 940,   0,  0, 580 ,   0 ,  0,  0 ,    0,  0,  418 ,  0 ,  0,   0},//16-> Jalgaon
							  {0  , 0,  0,  0,  0,  0  ,   0,  742,  0,   0,  0,    0,  0,  0  ,   0 ,  0,  0 ,    0,  0,   0 ,  0 ,   0,  0},//17-> Kanyakumari 
							  {0  , 0,792,  0,  0,  0  ,   0,  431, 303,   0, 0,    0,  0,  0  ,   0 ,  0,  0 ,    0,  0,   0 ,  0 ,   0,  0},//18-> Vijaywada
							  {0  , 0,  0,  0,  0,  0  ,   0,  0,  577,   0,  0,   410,  0,  0  ,  0 ,  0, 418 ,   0,  0,   0 ,  0 ,   0,  0},//19-> Nagpur 
							  {0  , 0,  0,  0,  0,  410,    0,  0,    0,   0, 392 ,  0,  0,  0  ,   0 ,  0,  0 ,    0,  0,   0 ,  0 ,   0,  0},//20-> Jhansi 
							  {0  , 0,  0,  0,  0,  0  ,   0,  0,    0,  512, 200,  0,  0,  0  ,   0 ,  0,  0 ,    0,  0,   0 ,  0 ,   0 , 0},//21-> Lucknow 
							  {0  , 0,  0,  0,  0, 576  ,   0,  0,   0,   0,   0,   0,  0,  0  ,   0 ,  0,  0 ,    0,  0,   0 ,  0 ,   0, 0}//22->  JammuTawi 
							  
							  
							};
	

	//for initializing the matrix with Infinite for no Connection
	for(int i=0;i<N;i++)
		for(int j=0;j<N;j++)
			{
		if(i==j)//if diagonal then leave it to 0
		continue;
		if(zoneMatrix[i][j]==0)//replace zero with IN=99999
			zoneMatrix[i][j]= IN;
		}
	
	//Creating a duplicate Matrix to keep kepp track of blocking a route
	for(int i=0;i<N;i++)
		for(int j=0;j<N;j++)
		{
			currMatrix[i][j]=zoneMatrix[i][j];
		}
	
		
		gui.searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int src = 0,dest = 0;
				
				if(gui.cbSRC.getSelectedItem().equals("Select") || gui.cbDEST.getSelectedItem().equals("Select"))
					{
					JOptionPane.showMessageDialog(p1, "Please specify source and destination  !");
					
					}
				else
				{
					
					src=gui.stationIndex.get(gui.cbSRC.getSelectedItem());
					dest=gui.stationIndex.get(gui.cbDEST.getSelectedItem());

					if(src!=dest)
					{
						SOURCE=src;
						DESTN=dest;
						// System.out.println("src"+src +" dest"+dest);

						int co=dijsktra(currMatrix,SOURCE,DESTN,"search");
						if(co!=IN)	
						distance.setText(""+co+"Km");
						else distance.setText("");
						System.out.print("shortest Path::"+co);
					}
					else
						JOptionPane.showMessageDialog(p1, "Source and Destination Cannot be Same !");
				}
			}
		});
		
		//Onclicking BlockButton
		gui.blockButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				

				if(gui.cbSRC.getSelectedItem().equals("Select") || gui.cbDEST.getSelectedItem().equals("Select"))
				{
					JOptionPane.showMessageDialog(p1, "Please specify source and destination to block !");

				}
				else
				{
					int src=gui.stationIndex.get(gui.cbSRC.getSelectedItem());
					int dest=gui.stationIndex.get(gui.cbDEST.getSelectedItem());

					if(src!=dest)
					{
						//Disconnecting the two stations Route (Blocking it)
						if(currMatrix[src][dest]==IN)
							JOptionPane.showMessageDialog(p1, "There is no direct Route to block");
						else if(((blockSt1.contains((Integer)src) && (blockSt2.contains((Integer)dest)))) ||
								((blockSt1.contains((Integer)src) && (blockSt2.contains((Integer)dest)))))
							JOptionPane.showMessageDialog(p1, "This Route is Already blocked!");
						else
						{
							currMatrix[src][dest]=currMatrix[dest][src]=IN;

							blockSt1.add(src);
							blockSt2.add(dest);


							//hasPath=false;
							//count=0;
							if(SOURCE!=DESTN)
							{
								int n=dijsktra(currMatrix,SOURCE,DESTN,"blocked");
								if(n==JOptionPane.NO_OPTION || n==JOptionPane.CLOSED_OPTION)
						    	{
						    		
						    		currMatrix[src][dest]=currMatrix[dest][src]=zoneMatrix[dest][src];

									blockSt1.remove((Integer)(src));
									blockSt2.remove((Integer)(dest));
						    		
						    		
						    	}
								else
								{
									distance.setText(""+n+" Km");
									p1.repaint();
								}

							}
							
						}
					}
					else
						JOptionPane.showMessageDialog(p1, "Source and Destination Cannot be Same !");

				}
			}
		});
		
		gui.unblockButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(gui.cbSRC.getSelectedItem().equals("Select") || gui.cbDEST.getSelectedItem().equals("Select"))
				{
					JOptionPane.showMessageDialog(p1, "Please specify source and destination to block !");

				}
				else if(gui.cbSRC.getSelectedItem().equals(gui.cbDEST.getSelectedItem()))
					JOptionPane.showMessageDialog(p1, "Source and Destination Cannot be Same !");
				else if(blockSt1.size()>0)
				{
					int src=gui.stationIndex.get(gui.cbSRC.getSelectedItem());
					int dest=gui.stationIndex.get(gui.cbDEST.getSelectedItem());
					
					if(((blockSt1.contains((Integer)src) && (blockSt2.contains((Integer)dest)))) ||
							((blockSt1.contains((Integer)src) && (blockSt2.contains((Integer)dest)))))
					{
						blockSt1.remove((Integer)src);
					
					blockSt2.remove((Integer)dest);

					hasPath=false;
					count=0;

					//Free the path -->initialize its distance from the original matrix

					currMatrix[src][dest]=currMatrix[dest][src]=zoneMatrix[src][dest];


					int co=dijsktra(currMatrix,SOURCE,DESTN,"unblock");
					//p1.repaint();
					distance.setText(""+co+" Km");
					}
					else
				
						JOptionPane.showMessageDialog(p1, "This route is not blocked!");
				}
				else
					JOptionPane.showMessageDialog(p1, "There is no blocked path!");
				
			}
		});
		gui.clearBlockage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				if(blockSt1.size()>0)
				{
					blockSt1.clear();
					blockSt2.clear();
					hasPath=false;
					count=0;
					//now CurrMatrix will be free from Blocked route
					for(int i=0;i<N;i++)
						for(int j=0;j<N;j++)
						{
							currMatrix[i][j]=zoneMatrix[i][j];
						}
					int src=gui.stationIndex.get(gui.cbSRC.getSelectedItem());
					int dest=gui.stationIndex.get(gui.cbDEST.getSelectedItem());
					int co=dijsktra(currMatrix,SOURCE,DESTN,"clear");
					distance.setText(""+co+" Km");
					p1.repaint();
					
				}
				else
					JOptionPane.showMessageDialog(p1, "There are no blocked paths to Unblock !");
				
			}
		});
		gui.resetBut.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				gui.cbSRC.setSelectedIndex(0);
				gui.cbDEST.setSelectedIndex(0);
				SOURCE=-1;
				DESTN=-1;
				    distance.setText("");
					blockSt1.clear();
					blockSt2.clear();
					hasPath=false;
					
					count=0;
					//now CurrMatrix will be free from Blocked route
					for(int i=0;i<N;i++)
						for(int j=0;j<N;j++)
						{
							currMatrix[i][j]=zoneMatrix[i][j];
						}
					
					p1.repaint();
				
					//JOptionPane.showMessageDialog(p1, "There are no blocked paths to Unblock !");
				
			}
		});
		gui.showBlockdPath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				if(blockSt1.size()>0)
				{
					BlockedRoutes br=new BlockedRoutes(blockSt1,blockSt2,zoneMatrix);
					//HomePage.g.setColor(Color.RED);
				//	HomePage.g.drawString("Blocked Routes",500,400 );
					//p1.repaint();
				}
				else
				JOptionPane.showMessageDialog(p1, "There is no blocked route to show !!");
				
			}
		});
		
		gui.randomButt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
					RandomGraph rg=new RandomGraph(blockSt1,blockSt2,zoneMatrix,path,hasPath,count);
				
			}
		});
	}

	public static int dijsktra(int cost[][],int source,int target, String string)
	{
	    int dist[],prev[],selected[];
	    int i,m,min,start,d,j,noPath = 0;
	    boolean hasNoPath = false;
	    path=new int[N];
	    dist=new int[N];
	    prev=new int[N];
	    selected=new int[N];
	    System.out.println("Entered Djikstra");
	    
	    for(i=0;i< N;i++)
	    {
	        dist[i] = IN;
	        prev[i] = -1;
	    }
	    start = source;
	    selected[start]=1;
	    dist[start] = 0;
	    while(selected[target] ==0)
	    {
	        min = IN;
	        m = 0;
	        for(i=0;i<N;i++)
	        {
	            d = dist[start] +cost[start][i];
	            if(d< dist[i]&&selected[i]==0)
	            {
	                dist[i] = d;
	                prev[i] = start;
	            }
	            if(min>dist[i] && selected[i]==0)
	            {
	                min = dist[i];
	                m = i;
	            }
	        }
	        start = m;
	        selected[start] = 1;
	        System.out.println("Within while");
	        noPath++;
	        if(noPath>100)//it catches the noPath logic
	        	{
	        	hasNoPath=true;
	        	break;
	        	}
	    }
	    System.out.println("Out of while");
	    
	    if(hasNoPath==true)
	    	{
	    	hasPath=false;
	    	
	    	if(string.equals("blocked"))
	    	{
	    		int n=JOptionPane.showConfirmDialog(
	    		    p1,
	    		    "Do you really want to block this route ?\nThere is no alternate path available\n if you block this route.",
	    		    "Message",
	    		    JOptionPane.YES_NO_OPTION);
	    	
	    		return n;
	    	}
	    	else if(string.equals("search"))
	    		JOptionPane.showMessageDialog(p1, "There is no route available !");;
	    	
	    		
	    	
	    	}
	    else
	    {
	    	start = target;
	    	j = 0;
	    	count=0;
	    	while(start != -1)
	    	{
	    		path[j] = start;
	    		start = prev[start];
	    		count++;
	    		j++;

	    		if(count>50)
	    		{
	    			System.out.println("No path");
	    			break;
	    		}
	    		System.out.println("within while start");
	    	}
	    	// path[j]='\0';
	    	// strrev(path);
	    	for(j=0; j<count; j++)
	    		System.out.print(path[j]+"->");

	    	if(count>=2)
	    		hasPath=true;

	    	// p1.repaint();
	    	// p1.revalidate();
	    	//p1.validate();
	    	p1.updateUI();

	    }
	  //  printf("%s", path)

	    return dist[target];
	}

	public String[] comboBoxStation()
	{
		String fileName = "masterFile.txt";
		String stName[]=new String[24];
		ArrayList<String> stName1=new ArrayList<>();
		
		String line=null;
		stName[0]="";
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = 
					new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);
			

			int i=1;

			while((line = bufferedReader.readLine()) != null) {

				// \\s+ means any number of whitespaces between tokens
				String [] tokens = line.split("\\s+");
				float lat = Float.parseFloat(tokens[0]);
				float lon =  Float.parseFloat(tokens[1]);
				String station = tokens[2];
				stName1.add(station);
				stName[i]=station;
				

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
		Arrays.sort(stName);
		stName[0]="Select";
	//	Collections.sort((java.util.List<T>) stName1);
		return stName;

	}
	

}


