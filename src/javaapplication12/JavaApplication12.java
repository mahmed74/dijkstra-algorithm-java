/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication12;

/**
 *
 * @author Ahmed
 */


class DisPar // distance and parent
{           // items stored in sPath array
    public int distance; // distance from start to this vertex
    public int parentVert; // current parent of this vertex
    
    public DisPar(int pv , int d) // constructor
    {
        distance = d;
        parentVert = pv;
    } // constructor ends
} // class DisPar ends

class Vertex{
    public String label; // label (e.g A)
    public boolean isInTree; 
    
   //-----------------------------------
    
    public Vertex(String lab) // constructor
    {
        label = lab;
        isInTree = false;
    }// constructor ends
    
} // end class Vertex


class Graph{

    private final int MAX_VERTS = 20;
    private final int INFINITY = 1000000;
    private Vertex VertexList[]; // list of vertices
    private int adjMat[][]; // adjacency matrix
    private int nVerts; // current number of vertices
    private int nTree; //number of vertices in tree
    private DisPar sPath[]; // array of the shortest path data
    private int currentVert; //current vertex
    private int startToCurrent; //distance to current vertex(currentVert)
    String res;
    //------------------------------------------------------------------
    
    public Graph() // constructor
    {
        VertexList = new Vertex[MAX_VERTS];
        adjMat = new int[MAX_VERTS][MAX_VERTS]; // adjacency matrix
        nVerts = 0;
        nTree = 0;
        
        
        for(int j=0 ; j<MAX_VERTS; j++) // set Adjacency
        {
            for(int k=0 ; k<MAX_VERTS; k++) //matrix
            {
                adjMat[j][k] = INFINITY; // to infinity
               
            }
        }
        sPath = new DisPar[MAX_VERTS]; // shortest paths
    } // constructor ends
    
    //-----------------------------------------------------------------
    
    
    public void addVertex(String lab)
    {
        VertexList[nVerts++] = new Vertex(lab);
    }
    
    //------------------------------------------------------------------
    
    public void addEdge(int start , int end , int weight)
    {
        adjMat[start][end] = weight; // (directed)
    }
    
    //----------------------------------------------------------------
    
    public void path() // find all shortest paths
            
    {
    
        int startTree = 0; // start at the vertex zero
        VertexList[startTree].isInTree = true; 
        nTree = 1; // put it in tree
        
        // transfer row of distances from adjMat to sPath
        
        for(int j = 0 ; j<nVerts ; j++)
        {
            int tempDist = adjMat[startTree][j];
            sPath[j] = new DisPar(startTree , tempDist);
        }
        // untill all vertices are in tree
        
        while(nTree < nVerts)
        {
            int indexMin = getMin();// get minimum from sPath
            int minDist = sPath[indexMin].distance;
            
            if(minDist == INFINITY) //if all infinite , or in tree
            {
                System.out.println("There are unreachable vertices");
                break;
            }
            else
            {
                currentVert = indexMin; // reset current vertex to closest vertex
                startToCurrent = sPath[indexMin].distance; // minimun distance from startTree is to currentVert( current vertex), and is startToCurrent
            }
            // put current vertex into tree
            
            VertexList[currentVert].isInTree = true;
            nTree++;
            adjust_sPath(); //update the sPath[] array
        } // end while (nTree < nVerts)
        
        displayPaths(); // display sPath[] contents
        
        nTree = 0; // clear tree
        for(int j = 0;  j < nVerts ; j++)
        {
            VertexList[j].isInTree= false;
        }
    }// end path()
    
     // --------------------------------------------------
    
            public int getMin() //get entry from sPath with minimum distance assume minimum
        { 
             int minDist = INFINITY;
             int indexMin = 0;
             for(int j=1 ; j<nVerts ; j++ ) //for each vertex if it is in tree and smaller than old one 
             {
                 if(!VertexList[j].isInTree && sPath[j].distance<minDist )
                 {
                     minDist = sPath[j].distance;
                     indexMin = j; // update the minimum
                 }
                 
             } // end for
             return indexMin;
        } // end getMin();
    
            
       //---------------------------------------------------------------
            
            
            public void adjust_sPath()
            {
                // adjust values in shortest-path array sPath
                int column = 1; // skip starting vertex
                while(column < nVerts) // go accross coloumns
                {
                    // if the column's vertex already in tree, skip it
                    if(VertexList[column].isInTree)
                    {
                        column++;
                        continue;
                    }
                    // calculate distance for one sPath entry
                    // get edge from currentVert to column
                    int currentToFringe = adjMat[currentVert][column];
                    // add distance from start
                    int startToFringe = startToCurrent + currentToFringe;
                    //get distance of current sPath entry
                    int sPathDist = sPath[column].distance;
                    
                    // compare distance from start with sPath entry
                    
                    if(startToFringe < sPathDist) // if shorter , update path
                    {
                        sPath[column].parentVert = currentVert;
                        sPath[column].distance = startToFringe;
                    }
                    column++;
                }// end while(column < nVerts)
            }// end adjust_sPath()
    
            // -----------------------------------------------------------------
            
            public void displayPaths()
            {
                for(int j=0; j<nVerts ; j++)
                {
                    System.out.print(VertexList[j].label +" = "); // B=
                    if(sPath[j].distance == INFINITY)
                    {
                        System.out.print("infinite");
                    }
                    else
                    {
                        System.out.print(sPath[j].distance); // 50
                        this.res = ""+sPath[j].distance+" km";
                    }
                    String parent = VertexList[sPath[j].parentVert].label;
                    System.out.print(" ( "+parent+" ) "); // (A)
//                    this.res = parent;
                }
                System.out.println("");
                
            }
        // ---------------------------------------------------------------------
} // end class Graph

public class JavaApplication12 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
//        Graph theGraph = new Graph();
//        theGraph.addVertex('A'); // 0 (Start)
//        theGraph.addVertex('B'); // 1
//        theGraph.addVertex('C'); // 2
//        theGraph.addVertex('D'); // 3
//        theGraph.addVertex('E'); // 4
//        
//        
//        theGraph.addEdge(0, 1, 50); //AB 50
//        theGraph.addEdge(0, 3, 80); // AD 80
//        theGraph.addEdge(1, 2, 60); //BC 60
//        theGraph.addEdge(1, 3, 90); // BD 90
//        theGraph.addEdge(2, 4, 40); //CE 40
//        theGraph.addEdge(3, 2, 20); // DC 20
//        theGraph.addEdge(4,1,50); // EB 50
        
//        System.out.println("Shortest paths");
//        theGraph.path(); // shortest paths
//        System.out.println();
        
    } // end main
     
} //end public class
