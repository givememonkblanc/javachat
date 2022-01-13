package application;
    
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
 
public class Main extends Application {
	Socket socket;
	@FXML TextArea textArea;
	
	// Ŭ���̾�Ʈ ���� �޼ҵ�
	
	
	public void startClient(String IP, int port) {
		Thread thread = new Thread() {
			public void run() {
				try {
					socket = new Socket(IP, port);
					receive();
				}catch (Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[���� ���� ����]");
						Platform.exit();
				}
				}
			}
		};
		thread.start();
		
		
	}
	
	// Ŭ���̾�Ʈ ���α׷� ���� �޼ҵ�
	
	public void stopClient() {
		try {
			if (socket != null && !socket.isClosed()) {
				socket.close();
			} 
		}catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	
	// �����κ��� �޽����� ���޹޴� �޼ҵ��ԴϤ���.
	
	public void receive() {
		while(true) {
			try {
			InputStream in = socket.getInputStream();
			byte[] buffer =  new byte[512];
			int length = in.read(buffer);
			if (length == -1) throw new IOException();
			String message = new String(buffer, 0, length,"UTF-8");
			Platform.runLater(() -> {
				textArea.appendText(message);
			});
		}catch(Exception e){
			stopClient();
			break;
		}
		}
		
	}
	
	
	
	// �޽��� �߼۽ð��� Ȯ�����ִ� �޼ҵ��Դϴ�.
	public String CurrentDateTime(){
		LocalDateTime now = LocalDateTime.now();
		String formatedNow = now.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
		return formatedNow;
	
	}
	
	// �����κ��� �޽����� �����ϴ� �޼ҵ��Դϴ�.
	
	public void send(String message) {
		Thread thread = new Thread() {
			public void run() {
				try {
					OutputStream out = socket.getOutputStream();					
					byte[] buffer = message.getBytes("UTF-8");
					out.write(buffer);
					out.flush();
				}catch (Exception e) {
				stopClient();
				}
			};	
		};
		thread.start();
		
	}

	
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("[ä�� Ŭ���̾�Ʈ]");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(event -> stopClient());
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
 