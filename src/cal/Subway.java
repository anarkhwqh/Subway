package cal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Subway {
	private static Map<String, Integer> name_num = new HashMap<String, Integer>();
	private static Map<Integer, String> num_name = new HashMap<Integer, String>();
	private static List<List<String>> station = new ArrayList<List<String>>();
	private static int[][] c = new int[500][500];
	private static int[] prev = new int[500];
	private static int[] dist = new int[500];
	private static boolean[] s = new boolean[500];
	private static int n = 332;

	public void loadSubwayFile(String subway_file) {
		BufferedReader reader = null;
		File subway = new File(subway_file);
		try {

			reader = new BufferedReader(new FileReader(subway));
			String t = null;

			while ((t = reader.readLine()) != null) {
				List<String> line = Arrays.asList(t.split(" "));
				station.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void mapping() {
		int cnt = 1;
		for (List<String> line : station) {
			for (int i = 1; i < line.size(); i++) {
				if (!name_num.containsKey(line.get(i))) {
					num_name.put(cnt, line.get(i));
					name_num.put(line.get(i), cnt++);
				}
				if (i != 1) {
					int a = name_num.get(line.get(i));
					int b = name_num.get(line.get(i - 1));
					c[a][b] = c[b][a] = 1;
				}

			}
		}
	}

	void Dijkstra(int v) {
		Arrays.fill(s, false);
		Arrays.fill(prev, 0);
		for (int i = 1; i <= n; i++) {
			dist[i] = c[v][i];
			if (dist[i] != 999999)
				prev[i] = v;
		}
		dist[v] = 0;
		s[v] = true;

		for (int i = 2; i <= n; i++) {
			int tmp = 999999;
			int u = v;
			for (int j = 1; j <= n; j++) {
				if ((!s[j]) && dist[j] < tmp) {
					u = j;
					tmp = dist[j];
				}
			}

			s[u] = true;

			for (int j = 1; j <= n; j++) {
				if (dist[u] + c[u][j] < dist[j]) {
					dist[j] = dist[u] + c[u][j];
					prev[j] = u;
				} else if (dist[u] + c[u][j] == dist[j]) {
					int a = searchPath(v, u);
					int b = searchPath(v, prev[j]);
					if (u != j && a < b) {
						if (a < b)
							prev[j] = u;
						else {
							for (List<String> line : station) {
								if (line.contains(num_name.get(u)) && line.contains(num_name.get(j)))
									prev[j] = u;
							}
						}
					}
				}
			}
		}
	}

	int searchPath(int v, int u) {
		int[] que = new int[500];
		int cnt = 0;
		int tot = 1;
		que[tot] = u;
		tot++;
		int tmp = prev[u];
		while (tmp != v) {
			que[tot] = tmp;
			tot++;
			tmp = prev[tmp];
		}
		que[tot] = v;
		for (int i = tot; i >= 1; i--) {
			boolean f = false;
			if (i == tot)
				f = true;
			else if (i != 1) {
				for (List<String> line : station) {
					if (line.contains(num_name.get(que[i + 1])) && line.contains(num_name.get(que[i - 1]))) {
						f = true;
						break;
					}
				}
			}
			if (i != 1 && !f) {
				cnt++;
			}
		}
		return cnt;
	}

	String print(int v, int u) {
		String path = "";
		int[] que = new int[500];
		int tot = 1;
		que[tot] = u;
		tot++;
		int tmp = prev[u];
		while (tmp != v) {
			que[tot] = tmp;
			tot++;
			tmp = prev[tmp];
		}
		que[tot] = v;
		for (int i = tot; i >= 1; i--) {
			boolean f = false;
			String x = null;
			if (i == tot)
				f = true;
			else if (i != 1) {
				for (List<String> line : station) {
					if (line.contains(num_name.get(que[i + 1])) && line.contains(num_name.get(que[i - 1]))) {
						f = true;
						break;
					}
					if (line.contains(num_name.get(que[i])) && line.contains(num_name.get(que[i - 1]))) {
						x = line.get(0);
						break;
					}
				}
			}
			if (i != 1) {
				if (f)
					path = path + num_name.get(que[i]) + ",";
				else {
					path = path + num_name.get(que[i]) + ",换乘" + x + ",";
				}
			} else
				path = path + num_name.get(que[i]);
		}
		return path;
	}

	public static String Main(String a, String b) {
		Subway s = new Subway();
		s.loadSubwayFile("C:\\Users\\ANARKHWQH\\Desktop\\subway.txt");

		for (int i = 1; i < 500; i++) {
			for (int j = 1; j < 500; j++) {
				if (i == j)
					c[i][j] = 0;
				else
					c[i][j] = 999999;
			}
		}
		s.mapping();
		int begin = 0;
		int end = 0;
		String path = null;
		if (name_num.containsKey(a) && name_num.containsKey(b)) {
			begin = name_num.get(a);
			end = name_num.get(b);
			if (begin==end) {
				path = "起点和终点相同";
			} else {
				s.Dijkstra(begin);
				path = s.print(begin, end);
			}
		} else {
			path = "起点或终点不存在";
		}
		return path;
	}

}