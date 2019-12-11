import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.util.ArrayList;
import java.util.List;
import ai.AI;
import ai.Map;
import ai.Point;
public class clientGame {
        public AI ai;
	private final Socket socket;
	private int width_board;
	private int[][] main_board;
	//Team co 2 gia tri X hoac O
	private static final String team="X";
	private JSONObject prepoint;
	public static void main(String[] args) {
		try {
			List members = new ArrayList<>();
		//Thong tin cac thanh vien trong nhom
			members.add( new JSONObject("{name: Nguyen The Duc, mssv: 20170057}"));
			members.add( new JSONObject("{name: Nguyen Minh Hieu, mssv: 20173115}"));
                        members.add( new JSONObject("{name: Nguyen Ky Tung, mssv: 20173455}"));
                        members.add( new JSONObject("{name: Nguyen Minh Dang, mssv: 20172998}"));
			
			
			JSONObject infor = new JSONObject();
			infor.put("team",team);
		//Ten team vd:  Germany ....
			infor.put("name", "TEAM 21");
			infor.put("members", members);
//			System.out.print(infor.toString());
			new clientGame(infor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public clientGame(final JSONObject infor) 
			throws Exception {

		IO.Options opts = new IO.Options();
		opts.query = "team=".concat(team);
		socket = IO.socket("http://localhost:3000", opts);
		socket.connect();
		socket.emit("Infor", infor);
		
		socket.on("new_Map", new Emitter.Listener() {
			public void call(Object... args) {
				// TODO Auto-generated method stub
				JSONObject data = (JSONObject) args[0];
				try {
				// Lay thong tin vi tri hien tai gom 3 thanh phan (x,y,type)
					prepoint = (JSONObject) data.get(team);
					System.out.println(prepoint);
					width_board = (int) data.get("ncol");
					JSONArray map = (JSONArray) data.get("map");
				// Gia tri 0 la o chua ai di qua, 1 team X da di qua, 2 team O da di qua, 3 la vat can
					main_board = new int[width_board+2][width_board+2];
					for (int i=0; i<map.length(); i++) {
						for (int j=0; j<map.getJSONArray(i).length(); j++) {
							main_board[i+1][j+1] = map.getJSONArray(i).getInt(j);
						}
					}
                                        Point p = new Point(prepoint.getInt("x")+1,prepoint.getInt("y")+1);
                                        ai = new AI(new Map(main_board,width_board,p));
                                        ai.move(ai.map.ai);
                                        ai.move(ai.map.player);
                                        ai.curturn = false;
                                        
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		socket.on("connect", new Emitter.Listener() {
			public void call(Object... args) {
				System.out.println("Connection established successfully");
			}
		});
		
		socket.on("change_Turn", new Emitter.Listener() {
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					// rivalpoint la toa do diem doi thu vua di gom  3 thang phan (x,y, type)
					JSONObject rivalpoint = (JSONObject) data.get("point");
					String turn = (String) data.get("turn");
					String result = (String) data.get("result");
					if (result.compareTo(team)==0) {
						System.out.println("You winnnn!");
					} else if (result.compareTo("C")==0) {
						
					}else {
						System.out.println("You lose!");
					}
					
					//Den luot ban tim nuoc di 
					if (turn.compareTo(team)==0) {
						//code thuat toan trong day de tim nuoc di moi toa do x,y;
						
						//CODE o day
                                                ai.move(new Point(rivalpoint.getInt("x")+1,rivalpoint.getInt("y")+1));
                                                long startTime = System.nanoTime();
						Point p = ai.caculate();
                                                long endTime   = System.nanoTime();
                                                long totalTime = endTime - startTime;
                                                System.out.print("Time :");
                                                System.out.println((double)totalTime/1000000000);
                                                System.out.print(p.x-1);
                                                System.out.print(" ");
                                                System.out.println(p.y-1);
						//gui toa do nuoc di
						send_message(p.x-1,p.y-1,team);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		socket.on("time_Out", new Emitter.Listener() {
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String team_losed = (String) data.get("team");
					if (team_losed.equals(team)) {
						System.out.println("You lose!");
					} else {
						System.out.println("You winnnnn!");
					}
					//socket.close();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		

	}
	

	public void send_message(int row, int col, String team) throws JSONException {
		JSONObject message = new JSONObject();
		message.put("x", row);
		message.put("y", col);
		message.put("type", team);
		this.socket.emit("moving", message);
	}
}
