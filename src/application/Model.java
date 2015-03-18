package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

public class Model {
	
	public int chatIndex = 0;
	public String lastMessage = "none";
	private String UserLast = "UserLast";
	private String Receivedlast = "ReceivedLast";
	
	public String endOfFile = " ~END_OF_CHAT~ ";
	
	private ObservableList<Text> chatList = 
			FXCollections.observableArrayList();
	
	ObservableList<Text> getObservable(){
		return chatList;
	}
	
	public void initializeChat(Text message){
		chatList.add(message);
		chatList.get(0).setTranslateX(240);
		chatList.get(0).translateXProperty();
	}
	
	public void receiveMessage(Text message){
		if (message.getText().length() > 0){
			Text text = new Text();
			Text newline = new Text();
			String temp = "\n";
			newline.setText(temp);
			int index = chatIndex;
			text.setTranslateX(300);
			text.setWrappingWidth(300);
			System.out.println("Size is: " + chatList.size());
			System.out.println("Index is: " + chatIndex);
			if (lastMessage.equals(Receivedlast)){
				text.setText(message.getText() + newline.getText());
				text.setText((chatList.get(index).getText() + message.getText() + newline.getText()));
				chatList.remove(chatIndex);
				chatList.add(chatIndex, text);
				text.translateXProperty();
			}
			else{
				addChatIndex();
				text.setText(message.getText() + newline.getText());
				chatList.add(text);
				text.translateXProperty();
				this.lastMessage = Receivedlast;
			}
			
		}
	}
	
	public void addMessage(Text message){
		if (message.getText().length() > 0){
			Text text = new Text();
			Text newline = new Text();
			String temp = "\n";
			newline.setText(temp);
			int index = chatIndex;
			text.setWrappingWidth(300);
			System.out.println("Size is: " + chatList.size());
			System.out.println("Index is: " + chatIndex);
			if (lastMessage.equals(UserLast)){
				text.setText(message.getText() + newline.getText());
				text.setText((chatList.get(index).getText() + message.getText() + newline.getText()));
				chatList.remove(chatIndex);
				chatList.add(chatIndex, text);
			}
			else{
				text.setText(message.getText() + newline.getText());
				chatList.add(text);
				addChatIndex();
				this.lastMessage = UserLast;
			}
			
		}
	}
	
	public void addChatIndex(){
		this.chatIndex++;
		//otherstuff
	}
	
	public void saveChat(File location) throws IOException{
		try {
			File file = new File(location.getAbsolutePath()+".txt");
			file.createNewFile();
	        FileWriter fwriter = new FileWriter(file.getAbsoluteFile());
	        for (int i = 0; i < chatList.size(); i++){
		        fwriter.write(chatList.get(i).getText() + "\r\n");
	        }
	        fwriter.write(endOfFile);
	        fwriter.close();
			}
	           catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
}
