package application;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;

import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.Pane;


public class Controller extends Main implements Initializable {
	@FXML TabPane system;
	@FXML BorderPane root;
	@FXML TextField userName,IPText,portText,input;
	@FXML Pane hbox;
	@FXML Button sendButton, connectionButton;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		root. setPadding(new Insets(5));
		userName.setPrefWidth(150);
		userName.setPromptText("닉네임을 입력하세요.");
		textArea.setEditable(false);
		textArea.setWrapText(true);
		input.setDisable(true);
		sendButton.setDisable(true);
		connectionButton.requestFocus();
		
		
	}

	public void inputAction() {
		send(userName.getText() + " : " +  input.getText()+ " ★ " + CurrentDateTime() +" \n");
		input.setText("");
		input.requestFocus();
		
	}
	
	public void sendAction() {
		send(userName.getText() + " : " +  input.getText()+ " ★ " + CurrentDateTime() +" \n");
		input.setText("");
		input.requestFocus();

	}
	
	public void connectionAction() {
		if (connectionButton.getText().equals("접속하기")) {
			int port = 9876;
			try {
				port = Integer.parseInt(portText.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			startClient(IPText.getText(),port);
			Platform.runLater(()-> {
				textArea.appendText("[ 채팅방 접속] \n");
			});
			connectionButton.setText("종료하기");
			input.setDisable(false);
			sendButton.setDisable(false);
			input.requestFocus();
		}else {
			stopClient();
			Platform.runLater(()-> {
				textArea.appendText("[채팅창 퇴장]\n");
			});
			connectionButton.setText("접속하기");
			input.setDisable(true);
			sendButton.setDisable(true);
			}
		send("접속했습니다.");
	
	}
	

}
