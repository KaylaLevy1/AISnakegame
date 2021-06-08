import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyAgent extends za.ac.wits.snake.MyAgent {
           
     int xHead, yHead, xTail,yTail, xHeadOther, yHeadOther,Length,S1,S2,S3;
     int k,p,t=0;
     double Decay=50;
     String myState,SnakeOnBoard,HeadsOthers;
     ArrayList<String> Apples = new ArrayList<String>();
     ArrayList<String> Heads = new ArrayList<String>();
     ArrayList<Integer> HeadsX = new ArrayList<Integer>();
     ArrayList<Integer> HeadsY = new ArrayList<Integer>();
     
    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }
    @Override
    public void run() {
           
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            
            int nSnakes = Integer.parseInt(temp[0]);
            int Width = Integer.parseInt(temp[1]);
            int Height = Integer.parseInt(temp[2]);
            
            int[][] Board = new int[Width][Height];
         
            for (int i = 0; i < Board.length; i++) {
                Arrays.fill(Board[i], 0);
            }

            int x = 0;
            while (true) {
                String line = br.readLine();
                System.out.println(line);
                if (line.contains("Game Over")) {
                    break;
                }
                String apple1 = line;
              //  apple1 = "40 49";
                //do stuff with apples
                String[] StoreCoords = apple1.split(" ");
                int xApple = Integer.parseInt(StoreCoords[0]);
                int yApple = Integer.parseInt(StoreCoords[1]);

               Apples.add(line);
               if(Apples.size()>2) {
                          if(Apples.get(t+1).equals(Apples.get(t))) {
                                    Decay=Decay-1;
                          }else if (!Apples.get(t+1).equals(Apples.get(t))){
                                    Decay =50;
                          }
                          t++;
               }

        //       String[] obstacleArray = new String[3]; 
               int nObstacles = 3;
               for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                   String obs = br.readLine();
                   /// do something with obs
             //      String[] obstacleArray = new String[3]; 
//                   String o1 = "48,20 48,21 48,22 48,23 48,24";
//                   String o2 = "3,4 3,5 3,6 3,7 3,8";
//                   String o3 = "24,48 25,48 26,48 27,48 27,48";
//	          	   obstacleArray[0] = o1;
//	          	   obstacleArray[1] = o2;
//	          	   obstacleArray[2] = o3;
                   String[] StoreMyObs = obs.split(" ");
                   String Coordinates1 = StoreMyObs[0];
                   String Coordinates2 = StoreMyObs[4];
                   Board = drawLine(Board, Coordinates1, Coordinates2,-1);
                   
                   
//	          	 String[] StoreMyObs = obstacleArray[obstacle].split(" ");
//                 String Coordinates1 = StoreMyObs[0];
//                 String Coordinates2 = StoreMyObs[4];
//                 Board = drawLine(Board, Coordinates1, Coordinates2,1);
                   
               }
               
//               String l = "8 15";
//       		String[] obstacleArray = new String[3]; 
//       		String o0 = "4,1 4,2 4,3 4,4 4,5";
//       		String o1 = "0,5 1,5 2,5 3,5 4,5";
//       		String o2 = "9,9 9,10 9,11 9,12 9,13";
//       		obstacleArray[0] = o0;
//       		obstacleArray[1] = o1;
//       		obstacleArray[2] = o2;
//       		int sNu = 0;
//       		String[] snakeArray = new String[4];
//       		String s0 = "alive 3 2 14,16 16,16";
//       		String s1 = "dead 50 6 0,39 49,39";
//       		String s2 = "alive 7 1 12,16 12,19 15,19";
//       		String s3 = "dead 17 1 31,14 21,14 15,14 15,13";


                int mySnakeNum = Integer.parseInt(br.readLine());
                //mySnakeNum = 0;

           	//	String[] snakeArray = new String[4];
           		
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    
////	           		int sNu = 0;
//	           		String s0 = "alive 17 1 47,47 47,21 40,21";;//"alive 35 2 40,48 40,25 25,25 25,32";
//	           		String s1 = "alive 42 2 41,46 41,24 40,24 24,24 24,30";
//	           		String s2 = "alive 7 1 42,30 42,23 40,22";
//	           		String s3 = "alive 35 2 40,48 40,25 25,25 25,32";//"alive 17 1 47,47 47,21 40,21";
//                    
//	           		snakeArray[0] = s0;
//	           		snakeArray[1] = s1;
//	           		snakeArray[2] = s2;
//	           		snakeArray[3] = s3;
//	           		
                    String[] Snake = snakeLine.split(" ");
	           	  //  String[] Snake = snakeArray[i].split(" ");
                    String state = Snake[0];
                   
                    if(state.equals("alive"))
                    {
                      p++;
                       StringBuffer sb = new StringBuffer();
                       for (int p = 3; p < Snake.length; p++) {
                           sb.append(Snake[p] + " ");
                       }
                       SnakeOnBoard = sb.toString();                       
                       Board=drawSnake(SnakeOnBoard, 1, Board);  
                       
                    }
                     // Stored heads of other snakes so can check for head on collisions
                      if(i != mySnakeNum){
                        if(state.equals("alive")){
                                    String[] StoreOtherSnake = SnakeOnBoard.split(" ", SnakeOnBoard.length()) ;                   
                                    HeadsOthers = StoreOtherSnake[0];
                                    String[] HeadsOther = HeadsOthers.split(",", HeadsOthers.length());
                                    xHeadOther = Integer.parseInt(HeadsOther[0]);
                                    yHeadOther = Integer.parseInt(HeadsOther[1]);
                                    Board[yHeadOther][xHeadOther] = 1;
                                    if(inBounds(yHeadOther+1)){
                                    	// if(Board[yHeadOther+1][xHeadOther]==0) {
                                    		 Board[yHeadOther+1][xHeadOther] = 1;
                                    	 //}
                                             
	                                  }if(inBounds(yHeadOther-1)){
	                                	//  if(Board[yHeadOther-1][xHeadOther]==0) {
	                                             Board[yHeadOther-1][xHeadOther] = 1;
	                                	  //}
	                                  }if(inBounds(xHeadOther+1)){
	                                	  //if(Board[yHeadOther][xHeadOther+1]==0) {
	                                             Board[yHeadOther][xHeadOther+1] = 1;
	                                	  //}
	                                  }if(inBounds(xHeadOther-1)){
	                                	  //if(Board[yHeadOther][xHeadOther-1]==0) {
	                                             Board[yHeadOther][xHeadOther-1] = 1;
	                                	  //}
	                                  }
                                    
                                  Heads.add(HeadsOthers);
                                  HeadsX.add(xHeadOther);
                                  HeadsY.add(yHeadOther);

                        }                              
                       
                       }else if (i == mySnakeNum) {
                        //hey! That's me :)                        
                                   String[] StoreMySnake = snakeLine.split(" ");
                                   myState = StoreMySnake[0];
                                   String L = Snake[1];;
                                   Length = Integer.parseInt(L);
                                   if(myState.equals("alive")){
                                             // Board = drawSnake(SnakeOnBoard, 1, Board);
                                              String myHead = StoreMySnake[3];
                                              String[] SeperateMyHead = myHead.split(",");
                                              xHead = Integer.parseInt(SeperateMyHead[0]);
                                              yHead = Integer.parseInt(SeperateMyHead[1]);  
                                            //Kink 1
                                            String kink = StoreMySnake[4];
                                            String[] SeperateKink = kink.split(",");
                                          //  xFirstKink = Integer.parseInt(SeperateKink[0]);
                                            //yFirstKink = Integer.parseInt(SeperateKink[1]);                          

                                           
                            for (int j = 0; j < StoreMySnake.length; j++) {
                               if ((j + 1) == StoreMySnake.length) {
                                    String myTail = StoreMySnake[j];
                                    String[] SeperateMyTail = myTail.split(",");
                                    xTail = Integer.parseInt(SeperateMyTail[0]);
                                    yTail = Integer.parseInt(SeperateMyTail[1]);
                                }
                            }


                                  }
                    }
                }
                int move=7;
               // int a=0;
                long startTime = System.currentTimeMillis() ;
                
                if(Board[yApple][xApple] == 0) {
	             //  	if(LengthPath(Board,xHead,yHead,xApple,yApple).get(0) < 50) {
		                    if(Decay > 0) {   
		                    	//a=0;
		                        move =Astar(Board,xHead, yHead,  xApple, yApple);  
		                        //System.err.println("a1");
		                    }else if(Decay < 0) {                   	
		                        if(Board[24][24] == 0) {
		                            move =Astar(Board,xHead, yHead,  24, 24);
		                        //    a=1;
		                        }else {
		                        //	System.err.println("a2");
		                        	move =  ChaseTail(Board,  xTail,  yTail,  xHead,  yHead);
		                        	//a=2;
		                        }
		                    }else {
		                    //	System.err.println("a3");
		                    	move =  ChaseTail(Board,  xTail,  yTail,  xHead,  yHead);
		                    //	a=3;
		                    }
//	                }else{
//	                	//System.err.println("a4");
//	                	// move =MakeMovePathNull(xHead,yHead,Board); 
//	                	if(Board[24][24] == 0) {
//                            move =Astar(Board,xHead, yHead,  24, 24);
//                        //    a=1;
//                        }else {
//                        //	System.err.println("a2");
//                        	move =  ChaseTail(Board,  xTail,  yTail,  xHead,  yHead);
//                        	//a=2;
//                        }
	                //	move = ChaseTail(Board,  xTail,  yTail,  xHead,  yHead);// apple is about to be blocked, move somewehere else for now! 	                
	                	//a=4;
//	                }
                }else {
                	move = ChaseTail(Board,  xTail,  yTail,  xHead,  yHead);
//                	if(Board[24][24] == 0) {
//                        move =Astar2(Board,xHead, yHead,  24, 24);
//                        if(CheckAvaliable(xHead, yHead,Board, move) == false){
                        //    move =MakeMovePathNull(xHead,yHead,Board);  
                        }
                
//                if (CheckAvaliableFuture( xHead,  yHead, Board,   move) == true){
//                	System.out.println(move);
//                }else {
//                	move =  ChaseTail(Board,  xTail,  yTail,  xHead,  yHead); 
//                }
//                	}
//           	
//                }
//                int k = 0;
//                Board[HeadsY.get(k)][HeadsX.get(k)] = 5;
//                Board[HeadsY.get(k+1)][HeadsX.get(k+1)] = 5;
//                Board[HeadsY.get(k+1)][HeadsX.get(k+1)] = 5;
//                
//                if(CheckHeads( xHead,  yHead,  Board,  move) == false){
//                	System.out.println(move);
//                }else {
//                	move =MakeMovePathNull(xHead,yHead,Board); 
                	System.out.println(move);
              //  }
                long timer =(System.currentTimeMillis() -startTime);                  
//                if (timer >= 10) { 
//                	System.err.println(/*a + " " + timer + " " +"move is " + move +*/  "The time is  " +timer + "ms");// + " xHead,yHead"+ "( " + xHead +"," +yHead +")"
//                			//+" xTail,yTail (" +  +xTail+"," + yTail +")" + " xApple,yApple (" +  +xApple+"," + yApple +")" );
//            	};
            	k=k+2;

            	//System.err.println(move);
//                	if(x==0) {
//	                	PrintBoard(Board);
//	                	x++;
//	                	break;
//                	}
                for(int row =0; row < 50; row++)
                {
                    for(int column =0; column < 50; column ++)
                    {
                        Board[row][column] = 0;
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
}
    public boolean CheckHeads(int x, int y, int[][] Board, int move)
    {
    	
    	 if(move == 0){
             if(inBounds(y-2) && Board[y-2][x] == 5){ 
                        return true;
             }else {
                        return false;
             }
             
  }else if(move == 1){
             if(inBounds(y+2) && Board[y+2][x] ==5){ 
                        return true;
             } else {
                        return false;
             }
             
  }else if(move == 2){
	  if(inBounds(x-2) && Board[y][x-2] ==5){ 
                        return true;
             } else {
                        return false;
             }
  	}else {
             if(inBounds(x+2) && Board[y][x+2] ==5){ 
                        return true;
             } else {
                        return false;
             }
  }
  
             
    }
    public int AstarNullPath(int[][] Board, int x, int y, int ax, int ay) {
      	 int nx = 0,ny = 0;
        int m =0;
          long timer;
        long startTime = System.currentTimeMillis() ;
           	 AStar as = new AStar(Board, x, y, false); 
           	 if(inBounds(xTail-1) && Board[yTail][xTail-1] == 0) {
         	 List<Node> path= as.findPathTo(xTail-1, yTail);
                if(path != null) {
   		       	  nx =path.get(1).x;
   		       	  ny =path.get(1).y;
   		  	 	  m= MakeMove(x,y,nx,ny);
                }}else if (inBounds(xTail+1) && Board[yTail][xTail+1] == 0) {
	   	         	 List<Node> path= as.findPathTo(xTail+1, yTail);
	                 if(path != null) {
	    		       	  nx =path.get(1).x;
	    		       	  ny =path.get(1).y;
	    		  	 	  m= MakeMove(x,y,nx,ny);
	   	        
	   		  	 }}else if (inBounds(yTail-1) && Board[yTail-1][xTail] == 0) {
	   	         	 List<Node> path= as.findPathTo(xTail, yTail-1);
	                 if(path != null) {
	    		       	  nx =path.get(1).x;
	    		       	  ny =path.get(1).y;
	    		  	 	  m= MakeMove(x,y,nx,ny);
	   	        
	   		  	 }}else if (inBounds(yTail+1) && Board[yTail+1][xTail] == 0) {
	   	         	 List<Node> path= as.findPathTo(xTail, yTail+1);
	                 if(path != null) {
	    		       	  nx =path.get(1).x;
	    		       	  ny =path.get(1).y;
	    		  	 	  m= MakeMove(x,y,nx,ny);
	   	        
	   		  	 }}else {
		   		  	if ( Board[24][24] == 0) {
		   	         	 List<Node> path= as.findPathTo(24, 24);
		                 if(path != null) {
		    		       	  nx =path.get(1).x;
		    		       	  ny =path.get(1).y;
		    		  	 	  m= MakeMove(x,y,nx,ny);
		   	        
		   		  	 }else {
		   	        	// System.err.println("a4ob.1");
		   	             m = MakeMovePathNull(xHead,yHead,Board);
		   	         }
   		  	 	timer =(System.currentTimeMillis() -startTime);   
//   		  	 	if(timer>=50) {
//   		  	 		System.err.println(path.size()-1 + "Bamb");
//   		  	 	}
   	            } 
                }  	
               // System.err.println(timer);
              return m;
      }  
    public boolean CheckAvaliableFuture(int xHead, int yHead, int [][] Board,  int move) {
        boolean yes = true;
               if(move == 0){
                  if(inBounds(yHead-1)){
                             if(Board[0][0]==0) {
                                        if(Astar2(Board,xHead, yHead-1,  0, 0).get(2) == 1) {
                                                   yes = true;
                                        }else {
                                                   yes = false;
                                        }
                             }else if(Board[49][49]==0) {
                                        if(Astar2(Board,xHead, yHead-1,  49, 49).get(2) == 1) {
                                                   yes = true;
                                        }else {
                                                   yes = false;
                                        }
                             }
                  else if(Board[0][49]==0) {
                             if(Astar2(Board,xHead, yHead-1,  0, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[49][0]==0) {
                             if(Astar2(Board,xHead, yHead-1,  49, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[24][24]==0) {
                                        if(Astar2(Board,xHead, yHead-1,  49, 49).get(2) == 1) {
                                                   yes = true;
                                        }else {
                                                   yes = false;
                                        }
                            
                  }
                  }
               }else if(move == 1){
                  if(inBounds(yHead-1)) {
                  if(Board[0][0]==0) {
                             if(Astar2(Board,xHead, yHead+1,  0, 0).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[49][49]==0) {
                             if(Astar2(Board,xHead, yHead+1,  49, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[0][49]==0) {
                             if(Astar2(Board,xHead, yHead+1,  0, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[49][0]==0) {
                             if(Astar2(Board,xHead, yHead+1,  49, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                       yes = false;
                             }
                  }else if(Board[24][24]==0) {
                                        if(Astar2(Board,xHead, yHead+1,  49, 49).get(2) == 1) {
                                                   yes = true;
                                        }else {
                                                   yes = false;
                                        }
                            
                  } 
               }else if(move == 2){
                  if(inBounds(xHead-1)) {
                  if(Board[0][0]==0) {
                             if(Astar2(Board,xHead-1, yHead,  0, 0).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[49][49]==0) {
                             if(Astar2(Board,xHead-1, yHead,  49, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[0][49]==0) {
                             if(Astar2(Board,xHead, yHead-1,  0, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[49][0]==0) {
                             if(Astar2(Board,xHead-1, yHead,  49, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[24][24]==0) {
                                        if(Astar2(Board,xHead-1, yHead,  49, 49).get(2) == 1) {
                                                   yes = true;
                                        }else {
                                                   yes = false;
                                        }
                  }
               }else {
                  if(inBounds(xHead+1)) {
                  if(Board[0][0]==0) {
                             if(Astar2(Board,xHead+1, yHead,  0, 0).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[49][49]==0) {
                             if(Astar2(Board,xHead+1, yHead,  49, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[0][49]==0) {
                             if(Astar2(Board,xHead+1, yHead,  0, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[49][0]==0) {
                             if(Astar2(Board,xHead+1, yHead,  49, 49).get(2) == 1) {
                                        yes = true;
                             }else {
                                        yes = false;
                             }
                  }else if(Board[24][24]==0) {
                                        if(Astar2(Board,xHead+1, yHead,  49, 49).get(2) == 1) {
                                                   yes = true;
                                        }else {
                                                   yes = false;
                                        }
                  }
               }
               }}}
                          return yes;
                  
               
                          
               
        }
    public ArrayList<Integer> LengthPath(int[][] Board, int x, int y, int ax, int ay) {
        ArrayList<Integer> Length = new ArrayList<Integer>();
        int nx,ny;
        int pathLength;
        int m =0;
        AStar as = new AStar(Board, x, y, false);            
        List<Node> path= as.findPathTo(ax, ay);
        if(path != null) {
            pathLength = path.size()-1;
            Length.add(pathLength);            
        }else {
            pathLength = 51;
            Length.add(pathLength);
           
      }       
        return Length;
      }   
    public int ChaseTail(int [][] Board, int xTail, int yTail, int xHead, int yHead) {
        int move =0;
        
      if(inBounds(xTail-1) && Board[yTail][xTail-1] == 0){
           move = Astar(Board,xHead, yHead,xTail-1, yTail);
        
     }else if(inBounds(xTail+1) && Board[yTail][xTail+1] == 0){
        move = Astar(Board,xHead, yHead,xTail+1, yTail);
        
     }else if(inBounds(yTail-1) && Board[yTail-1][xTail] == 0){
        move = Astar(Board,xHead, yHead,xTail, yTail-1);
        
     }else if(inBounds(yTail+1) && Board[yTail+1][xTail] == 0){
        move = Astar(Board,xHead, yHead, xTail, yTail+1);
        
     }else {
        if(Board[24][24] == 0) {
             move =Astar(Board,xHead, yHead,  24, 24);
         }else {
        	// System.err.println("a4ob.1");
             move = MakeMovePathNull(xHead,yHead,Board);
         }
        
     }
        return move;
 }   
    public boolean CheckAvaliable(int x, int y, int [][] Board,  int move) {
        
        if(move == 0){
                   if(inBounds(y-1) && Board[y-1][x] == 0){ 
                              return true;
                   }else {
                              return false;
                   }
                   
        }else if(move == 1){
                   if(inBounds(y+1) && Board[y+1][x] ==0){ 
                              return true;
                   } else {
                              return false;
                   }
                   
        }else if(move == 2){
                   if(inBounds(x-1) && Board[y][x-1] ==0){ 
                              return true;
                   } else {
                              return false;
                   }
        }else {
                   if(inBounds(x+1) && Board[y][x+1] ==0){ 
                              return true;
                   } else {
                              return false;
                   }
        }
        
                   
        
 }
    public static boolean inBounds(int Coordinate) {
		if(Coordinate >= 0 && Coordinate < 50) {
			return true;   	
		}
		return false;
    }
    public void DrawAroundApple(int xApple,int yApple, int[][]Board) {
    	 if(inBounds(yApple+1)){
        	 Board[yApple+1][xApple] = -1;
    	 }if(inBounds(yApple-1)){
        	 Board[yApple-1][xApple] = -1;
    	 }if(inBounds(xApple+1)){
            	 Board[yApple][xApple+1] = -1;
    	 }if(inBounds(xApple-1)){
            	 Board[yApple][xApple-1] = -1;
    	 }
    }
    public int Astar(int[][] Board, int x, int y, int ax, int ay) {
   	 int nx = 0,ny = 0;
     int m =0;
       long timer;
     long startTime = System.currentTimeMillis() ;
        	 AStar as = new AStar(Board, x, y, false);            
          	 List<Node> path= as.findPathTo(ax, ay);
             if(path != null) {
		       	  nx =path.get(1).x;
		       	  ny =path.get(1).y;
		  	 	  m= MakeMove(x,y,nx,ny);
		  	 	
		  	 	timer =(System.currentTimeMillis() -startTime);   
//		  	 	if(timer>=50) {
//		  	 		System.err.println(path.size()-1 + "Bamb");
//		  	 	}
	            } else {
	            	m= MakeMovePathNull(x,y,Board);
	            	//m =AstarNullPath(Board, x,  y,  ax,  ay);
//	            	timer =(System.currentTimeMillis() -startTime);  
//	            	if(timer>=50) {
//	            		System.err.println("Crappppp");
//	            	}
	            }       	
            // System.err.println(timer);
           return m;
   }  
    public int MakeMove(int hx, int hy, int hxn, int hyn) {
	   	if(hy == hyn && hx > hxn) {
	   		return 2;
	   	}else if(hy == hyn && hx < hxn) {
	   		return 3;
	   	}else if(hx == hxn && hy > hyn) {
	   		return 0;
	   	}else {
	   		return 1;
	   	}
   }  
    public int MakeMovePathNull(int xHead, int yHead, int[][] Board) {
      
	    if(inBounds(yHead-1) && Board[yHead-1][xHead] ==0 ) {
           return 0;
		}else if(inBounds(xHead+1) && Board[yHead][xHead+1] == 0 ) {
           return 3;
		}else if(inBounds(xHead-1) && Board[yHead][xHead-1] ==0 ) {
           return 2;
		} else{
          return 1;
		}
	
	
  }
    public static void PrintBoard(int[][] Board)
    {

        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                System.err.print((Board[i][j]));
                if(j < (50-1)) {
                    System.err.print(" ");
                }
            }
            System.err.println("");
        }
    } 
    public static int[][] drawLine(int [][]Board, String PointOne, String PointTwo, int SNakeNum )
    {
        String[] CoordOne = PointOne.split(",");
        String[] CoordTwo = PointTwo.split(",");

        String x1 = CoordOne[0];
        String x2 = CoordTwo[0];
        String y1 = CoordOne[1];
        String y2 = CoordTwo[1];

        int X1= Integer.parseInt(x1);
        int X2= Integer.parseInt(x2);
        int Y1= Integer.parseInt(y1);
        int Y2= Integer.parseInt(y2);

        int MaxX;
        int MinX;
        int MaxY;
        int MinY;

        if(X1 < X2)
        {
            MinX = X1;
            MaxX = X2;
        } else{
            MinX = X2;
            MaxX = X1;
        }

        if(Y1 < Y2)
        {
            MinY = Y1;
            MaxY = Y2;
        } else{
            MinY = Y2;
            MaxY = Y1;
        }

        for (int i = 0; i < Board.length; i++)
        {
            for (int j = 0; j <Board[0].length; j++)
            {
                if(j >= MinX && j <= MaxX &&  i >= MinY && i <= MaxY)
                {
                    Board[i][j] =  1;  
                }
            }
        }
        return Board;
    }
    public static  int[][] drawSnake(String Snake, int SNakeNum , int[][] Board)
    {

        String[] SnakeInput = Snake.split(" ");

        for (int k = 0 ; k < SnakeInput.length  ; k++)
        {
            if(( k+1) >= SnakeInput.length) {
                break;
            }
            String PointOne = SnakeInput[k];
            String PointTwo = SnakeInput[k+1];
            Board = drawLine(Board,PointOne,PointTwo, SNakeNum);
        }

        return Board;
    }   
    public ArrayList<Integer> Astar2(int[][] Board, int x, int y, int ax, int ay) {
        ArrayList<Integer> MovePath = new ArrayList<Integer>();
               int nx,ny;
               int a = 1;
               int b = 0;
               int pathLength;
              int m =0;
               AStar as = new AStar(Board, x, y, false);            
               List<Node> path= as.findPathTo(ax, ay);
               if(path != null) {
                             nx =path.get(1).x;
                             ny =path.get(1).y;
                              pathLength = path.size()-1;
                              MovePath.add(pathLength);
                              m= MakeMove(x,y,nx,ny);
                              MovePath.add(m);
                              MovePath.add(a); //path
                     } else {
                               pathLength = 51;
                               m= MakeMovePathNull(x,y,Board);
                               MovePath.add(pathLength);
                               MovePath.add(m);
                               MovePath.add(b); //no path
                     }       
              return MovePath;
      }   
}
