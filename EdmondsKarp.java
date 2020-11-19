import java.util.*;
import java.io.*;

public class EdmondsKarp 
{
	public static void main(String[] args) throws FileNotFoundException
	{	
		Scanner sc = new Scanner(new File("C:\\Users\\Kyle's PC\\eclipse-workspace\\hw\\src\\graph.txt"));
		
		int graphs = sc.nextInt();
		System.out.println("there are " + graphs + " graphs being read");
		
		while(graphs --> 0)
		{
			System.out.println();
			System.out.println("Graph #" + (graphs + 1));
			
			int vertices = sc.nextInt();
			System.out.println("there are " + vertices + " vertices in the graph");
			
			int graph[][] = new int[vertices][vertices];
			
			while(sc.hasNext())
			{
				int source = sc.nextInt();
				
				if(source == -1)
					break;
				
				int dest = sc.nextInt();
				int weight = sc.nextInt();
				graph[source][dest] = weight;
			}
			
			System.out.println("\nAdjacency Matrix:");
			for(int i = 0; i < vertices; i++)
			{
				for(int j = 0; j < vertices; j++)
				{
					System.out.print("[" + graph[i][j] + "]");
				}
				System.out.println();
			}
			System.out.println();
			
			int maxMaxFlow = 0;
			int src = -1;
			int dest = -1;
			
			for(int i = 0; i < vertices; i++)
			{
				for(int j = 0; j < vertices; j++)
				{
					//System.out.println(i + " " + j);
					if(i == j)
						continue;
					int maxFlow = EdmondsKarpAlg(graph, vertices, i, j);
					System.out.println("MaxFlow from " + i + " to " + j + " is " + maxFlow);
					
					if(maxFlow > maxMaxFlow)
					{
						maxMaxFlow = maxFlow;
						src = i;
						dest = j;
					}
				}
			}
			
			System.out.println();
			System.out.println("maxMaxFlow is from " + src + " to " + dest + " and is " + maxMaxFlow);
			
			/*
			for(int i = 1; i < vertices; i++)
			{
				int maxFlow = EdmondsKarpAlg(graph, vertices, 0, i);
				System.out.println("MaxFlow from 0 to " + i + " is " + maxFlow);
			}*/
		}
		
		sc.close();
	}
	
	static boolean BFS(int graph[][], int vertices, int s, int t, int path[])
	{
		boolean visited[] = new boolean[vertices];
		for(int i = 0; i < vertices; i++)
		{
			visited[i] = false;
		}
		
		/*
		for(int i = 0; i < vertices; i++)
		{
			if(visited[i])
				System.out.print("[T]");
			else
				System.out.print("[F]");
		}
		System.out.println();
		*/
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		queue.add(s);
		visited[s] = true;
		path[s] = -1;
		
		/*
		for(int i = 0; i < vertices; i++)
		{
			if(visited[i])
				System.out.print("[T]");
			else
				System.out.print("[F]");
		}
		System.out.println();
		*/
		
		/*
		for(int i = 0; i < vertices; i++)
		{
			System.out.print("[" + path[i] + "]");
		}
		System.out.println();
		*/
		
		while(queue.size() != 0)
		{
			int u = queue.poll();
			//System.out.println("u is " + u);
			
			for(int v = 0; v < vertices; v++)
			{
				if(visited[v] == false && graph[u][v] > 0)
				{
					queue.add(v);
					path[v] = u;
					visited[v] = true;
				}
			}
		}
		
		/*
		for(int i = 0; i < vertices; i++)
		{
			System.out.print("[" + path[i] + "]");
		}
		System.out.println();
		*/
		//System.out.println();
		return (visited[t] == true);
	}
	
	
	static int EdmondsKarpAlg(int graph[][], int vertices, int s, int t)
	{
		int u, v;
		int resGraph[][] = new int[vertices][vertices];
		
		for(u = 0; u < vertices; u++)
		{
			for(v = 0; v < vertices; v++)
			{
				resGraph[u][v] = graph[u][v];
			}
		}
		
		//System.out.println("Residual Graph Created and Initialized");
		
		int path[] = new int[vertices];
		int maxFlow = 0;
		
		//System.out.println("Path and MaxFlow Created and Initialized");
		
		while(BFS(resGraph, vertices, s, t, path))
		{
			//System.out.println("A path exists from S to T");
			
			int pathFlow = Integer.MAX_VALUE;
			for(v = t; v != s; v = path[v])
			{
				u = path[v];
				pathFlow = Math.min(pathFlow,  resGraph[u][v]);
			}
			
			for(v = t; v != s; v = path[v])
			{
				u = path[v];
				resGraph[u][v] -= pathFlow;
				resGraph[v][u] += pathFlow;
			}
			
			maxFlow += pathFlow;
		}
		
		return maxFlow;
	}
}
