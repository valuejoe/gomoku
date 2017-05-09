package gomoku;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class gomoku extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	static gomoku frm = new gomoku();
	static MyJButton[][] btns = new MyJButton[19][19];
	static MyJButton Re = new MyJButton();
	static MyJButton AI = new MyJButton();
	static ImageIcon[] imgs = new ImageIcon[3]; //圖片陣列
	
	static int turn = 2;
	static final int color_null = 0;
	static final int color_white = 1;
	static final int color_black = 2;
	static final int dont_care = 3;
	
	static Label lab01 = new Label("");
	static Label lab02 = new Label("黑子先攻");
	
	static boolean end = false;
	
	static JButton btnStart= null;
	static JButton btnEnd= null;

	public static void main(String args[]) {
		frm.setSize(800, 700);
		frm.setLayout(null);		
		imgs[0] = new ImageIcon(gomoku.class.getResource("/images/null.png"));
		imgs[1] = new ImageIcon(gomoku.class.getResource("/images/white.png"));
		imgs[2] = new ImageIcon(gomoku.class.getResource("/images/black.png"));

		for (int row = 0; row < 19; row++) {
			for (int col = 0; col < 19; col++) {
				//設定圖片
				btns[row][col] = new MyJButton();
				//產生遊戲盤面
				btns[row][col].pid = color_null;
				btns[row][col].setIcon( imgs[color_null] );
				btns[row][col].r = row;
				btns[row][col].c = col;
				btns[row][col].setSize(32, 32);
				btns[row][col].setLocation(30+col*31, 30+row*31);
				btns[row][col].setContentAreaFilled(false);
				//按鈕加入視窗
				frm.add(btns[row][col]); 
				//註冊
				btns[row][col].addActionListener(frm);
			}
		}
		lab01.setBounds(630,200,150,30);
		lab01.setFont(new Font(Font.DIALOG,Font.BOLD,30));
		lab02.setBounds(650,100,100,20);
		lab02.setFont(new Font(Font.DIALOG,Font.BOLD,20));
		Re = new MyJButton();//restart
		Re.setBounds(650,30,100,30);
		Re.setText("開啟新局");
		Re.setContentAreaFilled(false);
		Re.addActionListener(frm);
		AI = new MyJButton();//restart
		AI.setBounds(650,60,100,30);
		AI.setText("AI");
		AI.setContentAreaFilled(false);
		AI.addActionListener(frm);
		frm.add(lab01);
		frm.add(lab02);
		frm.add(Re);
		frm.add(AI);
   		frm.setVisible(true);
	}
	//點擊

	@Override

	public void actionPerformed(ActionEvent e) {
		MyJButton jbn = (MyJButton) (e.getSource()); 		
//		if(turn == color_white && jbn != Re) {
//			//player's turn
//			if(jbn.pid == color_null){
//				jbn.pid = turn;
//				jbn.setIcon(imgs[jbn.pid]);
//				
//				turn = color_black;
//				lab02.setText("換黑子下");
//			}
//			
//		}else if(turn == color_black && jbn != Re){
//		    
//			if(jbn.pid == color_null){
//				jbn.pid = turn;
//				jbn.setIcon(imgs[jbn.pid]);
//				
//				turn = color_white;
//				lab02.setText("換白子下");
//			}
//		}else{}
		
		if(turn == color_white && jbn != Re && jbn != AI) {
			//player's turn
			if(jbn.pid == color_null){
				jbn.pid = turn;
				jbn.setIcon(imgs[jbn.pid]);
				int[] w_3 = {0,1,1,1,0};
				int[] w_2_1 = {0,0,1,1,0};
				int[] w_2_2 = {0,1,1,0,0};
				int[] b_1_1 = {0,2,3,3,3};
				int[] b_1_2 = {3,2,0,3,3};
				if( GO_r(w_3,0,2)||GO_r(w_3,4,2)||
					GO_r(w_2_1,1,2)||GO_r(w_2_1,4,2)||
					GO_r(w_2_2,0,2)||GO_r(w_2_2,3,2)||
					GO_r(b_1_1,0,2)||GO_r(b_1_2,2,2)){
					turn = color_white;
					lab02.setText("換白子下");
				}else{
					turn = color_black;
					lab02.setText("換黑子下");
				}
				
			}
			
		}else if(turn == color_black && jbn != Re && jbn != AI){
		    
			if(btns[10][10].pid == color_null){
				btns[10][10].pid = turn;
				btns[10][10].setIcon(imgs[btns[10][10].pid]);
				turn = color_white;
				lab02.setText("換白子下");
			}
		}else{}
		
		//win
		int[] w_win = {1,1,1,1,1};
		int[] b_win = {2,2,2,2,2};
		
		if(GO_r(w_win,0,1)||GO_c(w_win)||GO_lu(w_win)||GO_ru(w_win)){
			lab02.setText("");
			lab01.setText("白子獲勝!");
			System.out.println("White Win!");
			turn = 3;
		}else if(GO_r(b_win,0,2)||GO_c(b_win)||GO_lu(b_win)||GO_ru(b_win)){
			lab02.setText("");
			lab01.setText("黑子獲勝!");
			System.out.println("Black Win!");
			turn = 3;
		}
		
		//RE
		if(jbn == Re){
			for(int row = 0; row < 19; row++){
				for(int col = 0; col < 19; col++){
					btns[row][col].pid = color_null;
					btns[row][col].setIcon( imgs[color_null] );
				}
			}
			lab01.setText("");
			lab02.setText("黑子先攻");
			turn = 2;
		}
	}
	
	static boolean GO_r(int[] pattern,int pos, int color){
		boolean finished = false;
		boolean found = false;
		
		for(int r = 0; r < 19; r++){
			if(finished)
				break;
			for(int c = 0; c+4 < 19; c++){
				if(finished)
					break;
				if( (pattern[0] == btns[r][c+0].pid || pattern[0] == 3) &&
					(pattern[1] == btns[r][c+1].pid || pattern[1] == 3) &&
					(pattern[2] == btns[r][c+2].pid || pattern[2] == 3) &&
					(pattern[3] == btns[r][c+3].pid || pattern[3] == 3) &&
					(pattern[4] == btns[r][c+4].pid || pattern[4] == 3) ){
					
					btns[r][c+pos].pid = color;
					btns[r][c+pos].setIcon(imgs[btns[r][c+pos].pid]);
					found = true;
					finished = true;
				}
			}
		}
		return found;
	}
	
	static boolean GO_c(int[] pattern){
		boolean finished = false;
		boolean found = false;
		
		for(int r = 0; r+5 < 19; r++){
			if(finished)
				break;
			for(int c = 0; c < 19; c++){
				if(finished)
					break;
				if( pattern[0] == btns[r][c].pid   &&
					pattern[1] == btns[r+1][c].pid &&
					pattern[2] == btns[r+2][c].pid &&
					pattern[3] == btns[r+3][c].pid &&
					pattern[4] == btns[r+4][c].pid ){
					
					
					found = true;
					finished = true;
				}
			}
		}
		return found;
	}
	
	static boolean GO_lu(int[] pattern){
		boolean finished = false;
		boolean found = false;
		
		for(int r = 0; r+4 < 19; r++){
			if(finished)
				break;
			for(int c = 0; c+4 < 19; c++){
				if(finished)
					break;
				if( pattern[0] == btns[r][c].pid     &&
					pattern[1] == btns[r+1][c+1].pid &&
					pattern[2] == btns[r+2][c+2].pid &&
					pattern[3] == btns[r+3][c+3].pid &&
					pattern[4] == btns[r+4][c+4].pid ){
					
					
					found = true;
					finished = true;
				}
			}
		}
		return found;
	}
	
	static boolean GO_ru(int[] pattern){
		boolean finished = false;
		boolean found = false;
		
		for(int r = 0; r+5 < 19; r++){
			if(finished)
				break;
			for(int c = 18; c-5 >= 0; c--){
				if(finished)
					break;
				if( pattern[0] == btns[r][c].pid     &&
					pattern[1] == btns[r+1][c-1].pid &&
					pattern[2] == btns[r+2][c-2].pid &&
					pattern[3] == btns[r+3][c-3].pid &&
					pattern[4] == btns[r+4][c-4].pid ){
					
					
					found = true;
					finished = true;
				}
			}
		}
		return found;
	}
}
